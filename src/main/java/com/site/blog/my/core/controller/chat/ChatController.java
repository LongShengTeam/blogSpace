package com.site.blog.my.core.controller.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ChatController {

    /**
     * 聊天入口页面
     */
    @GetMapping("/chat")
    public String chat() {
        return "blog/amaze/chat";
    }
}
