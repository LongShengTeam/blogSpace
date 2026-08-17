package com.site.blog.my.core.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Netty 通道初始化器
 */
public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        // HTTP 编解码
        pipeline.addLast(new HttpServerCodec());
        // 支持大数据流写入
        pipeline.addLast(new ChunkedWriteHandler());
        // HTTP 消息聚合
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        // WebSocket 握手处理，路径为 /ws
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 自定义消息处理
        pipeline.addLast(new ChatServerHandler());
    }
}
