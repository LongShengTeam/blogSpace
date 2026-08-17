package com.site.blog.my.core.chat;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 在线用户通道管理器（内存存储，不依赖数据库）
 */
public class ChannelManager {

    /**
     * 昵称 -> 通道
     */
    private static final Map<String, Channel> CHANNEL_MAP = new ConcurrentHashMap<>();

    /**
     * 通道 -> 昵称
     */
    private static final Map<Channel, String> NAME_MAP = new ConcurrentHashMap<>();

    private ChannelManager() {
    }

    /**
     * 绑定用户和通道
     */
    public static void bind(String nickname, Channel channel) {
        // 同一昵称重复上线，先移除旧的
        Channel old = CHANNEL_MAP.get(nickname);
        if (old != null && old != channel) {
            NAME_MAP.remove(old);
            old.close();
        }
        CHANNEL_MAP.put(nickname, channel);
        NAME_MAP.put(channel, nickname);
    }

    /**
     * 移除通道
     */
    public static String unbind(Channel channel) {
        String nickname = NAME_MAP.remove(channel);
        if (nickname != null) {
            CHANNEL_MAP.remove(nickname);
        }
        return nickname;
    }

    /**
     * 根据昵称获取通道
     */
    public static Channel getChannel(String nickname) {
        return CHANNEL_MAP.get(nickname);
    }

    /**
     * 根据通道获取昵称
     */
    public static String getNickname(Channel channel) {
        return NAME_MAP.get(channel);
    }

    /**
     * 判断用户是否在线
     */
    public static boolean isOnline(String nickname) {
        return CHANNEL_MAP.containsKey(nickname);
    }

    /**
     * 向指定用户发送消息
     */
    public static void sendTo(String nickname, String json) {
        Channel channel = CHANNEL_MAP.get(nickname);
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(new TextWebSocketFrame(json));
        }
    }

    /**
     * 群发消息
     */
    public static void broadcast(String json) {
        for (Channel channel : CHANNEL_MAP.values()) {
            if (channel.isActive()) {
                channel.writeAndFlush(new TextWebSocketFrame(json));
            }
        }
    }

    /**
     * 获取在线人数
     */
    public static int onlineCount() {
        return CHANNEL_MAP.size();
    }

    /**
     * 获取所有在线用户昵称
     */
    public static java.util.Set<String> onlineUsers() {
        return CHANNEL_MAP.keySet();
    }
}
