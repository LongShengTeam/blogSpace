package com.site.blog.my.core.controller.problem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/problem")
public class ProblemController {

    @GetMapping("/next")
    public String next(HttpServletRequest request) {
        request.setAttribute("path", "links");
        return null;
    }
    @GetMapping("/next2")
    public String linkPage(HttpServletRequest request) {
        request.setAttribute("path", "links");
        return null;
    }

}
