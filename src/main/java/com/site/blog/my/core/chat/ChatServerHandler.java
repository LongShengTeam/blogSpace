package com.site.blog.my.core.chat;

import cn.hutool.json.JSONUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * 聊天消息处理器
 */
@Slf4j
public class ChatServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 用户上线绑定，收到首条消息后根据昵称绑定通道
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        String json = frame.text();
        Channel channel = ctx.channel();

        ChatMessage message = JSONUtil.toBean(json, ChatMessage.class);

        // 上线绑定
        if (message.getType() == 2) {
            String nickname = message.getFrom();
            ChannelManager.bind(nickname, channel);
            log.info("用户[{}]上线，当前在线人数：{}", nickname, ChannelManager.onlineCount());

            // 通知所有人更新在线列表
            ChatMessage notice = new ChatMessage();
            notice.setType(4);
            notice.setFrom(nickname);
            notice.setContent(JSONUtil.toJsonStr(ChannelManager.onlineUsers()));
            notice.setTimestamp(System.currentTimeMillis());
            ChannelManager.broadcast(JSONUtil.toJsonStr(notice));
            return;
        }

        // 正常文本消息
        String from = message.getFrom();
        String to = message.getTo();
        message.setTimestamp(System.currentTimeMillis());

        Integer type = message.getType();

        // 视频通话信令（10-16）：点对点转发，不回显
        if (type != null && type >= 10 && type <= 16) {
            ChannelManager.sendTo(to, JSONUtil.toJsonStr(message));
            log.info("[{}] -> [{}] 信令 type={}", from, to, type);
            return;
        }

        if (to == null || to.trim().isEmpty()) {
            // 群发
            ChannelManager.broadcast(json);
            log.info("[{}] 广播消息：{}", from, message.getContent());
        } else {
            // 点对点发送
            ChannelManager.sendTo(to, JSONUtil.toJsonStr(message));
            // 也回显给自己
            ChannelManager.sendTo(from, JSONUtil.toJsonStr(message));
            log.info("[{}] -> [{}]：{}", from, to, message.getContent());
        }
    }

    /**
     * 连接断开，清理用户
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String nickname = ChannelManager.unbind(ctx.channel());
        if (nickname != null) {
            log.info("用户[{}]下线，当前在线人数：{}", nickname, ChannelManager.onlineCount());

            ChatMessage notice = new ChatMessage();
            notice.setType(4);
            notice.setFrom(nickname);
            notice.setContent(JSONUtil.toJsonStr(ChannelManager.onlineUsers()));
            notice.setTimestamp(System.currentTimeMillis());
            ChannelManager.broadcast(JSONUtil.toJsonStr(notice));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("聊天通道异常", cause);
        ctx.close();
    }
}
