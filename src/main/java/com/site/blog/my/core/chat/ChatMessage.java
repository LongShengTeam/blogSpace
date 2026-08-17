package com.site.blog.my.core.chat;

import lombok.Data;

/**
 * 聊天消息模型
 */
@Data
public class ChatMessage {

    /**
     * 消息类型：1-文本 2-上线通知 3-下线通知
     */
    private Integer type;

    /**
     * 发送人昵称
     */
    private String from;

    /**
     * 接收人昵称（为空表示群发）
     */
    private String to;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 时间戳
     */
    private Long timestamp;
}
