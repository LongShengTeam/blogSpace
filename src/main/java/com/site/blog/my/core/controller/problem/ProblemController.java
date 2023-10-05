package com.site.blog.my.core.controller.problem;

import com.site.blog.my.core.entity.BlogLink;
import com.site.blog.my.core.service.ConfigService;
import com.site.blog.my.core.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/problem")
@Slf4j
public class ProblemController {
    public static String theme = "amaze";
    @Resource
    private ConfigService configService;
    @Resource
    private LinkService linkService;
    @GetMapping("/next")
    public String next(HttpServletRequest request) {

        log.info("刷题日志");
        request.setAttribute("pageName", "友情链接");
        Map<Byte, List<BlogLink>> linkMap = linkService.getLinksForLinkPage();
        if (linkMap != null) {
            //判断友链类别并封装数据 0-友链 1-推荐 2-个人网站
            if (linkMap.containsKey((byte) 0)) {
                request.setAttribute("favoriteLinks", linkMap.get((byte) 0));
            }
            if (linkMap.containsKey((byte) 1)) {
                request.setAttribute("recommendLinks", linkMap.get((byte) 1));
            }
            if (linkMap.containsKey((byte) 2)) {
                request.setAttribute("personalLinks", linkMap.get((byte) 2));
            }
        }
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/problem";
    }
    @GetMapping("/next2")
    public String linkPage(HttpServletRequest request) {
        request.setAttribute("path", "links");
        return null;
    }

}
