package com.site.blog.my.core.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Netty WebSocket 服务器，独立端口启动，与 Tomcat 分离
 */
@Component
@Slf4j
public class ChatServer {

    @Value("${chat.netty.port:9000}")
    private int port;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @PostConstruct
    public void start() {
        // 用单独线程启动，避免阻塞 Spring 启动
        new Thread(this::run, "netty-chat-server").start();
    }

    private void run() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChatServerInitializer());

            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("Netty 聊天服务器启动成功，端口：{}", port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("Netty 聊天服务器启动失败", e);
            Thread.currentThread().interrupt();
        } finally {
            shutdown();
        }
    }

    @PreDestroy
    public void shutdown() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        log.info("Netty 聊天服务器已关闭");
    }
}
