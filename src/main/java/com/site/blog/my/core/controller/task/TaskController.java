package com.site.blog.my.core.controller.task;

import com.site.blog.my.core.controller.kaoqin.KaoqinController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

/**
 * to do
 *
 * @author Hj
 * @date 2025/6/27
 */
@Controller
@Slf4j
public class TaskController {
    @Scheduled(cron = "* 0/4 0,9,18,19,20,21,22,23 ? * MON-FRI")
//    @Scheduled(cron = "* * 0,9,10,18,19,20,21,22,23 ? * MON-FRI")
    public void test() {
        log.info("test");
        KaoqinController.getOaBeans(1378L, 0);
        log.info("test");
    }
}
