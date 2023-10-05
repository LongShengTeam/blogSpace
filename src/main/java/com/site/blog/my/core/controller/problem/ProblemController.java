package com.site.blog.my.core.controller.problem;

import cn.hutool.core.io.resource.ResourceUtil;
import com.site.blog.my.core.service.ConfigService;
import com.site.blog.my.core.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;


@Controller
@RequestMapping("/problem")
@Slf4j
public class ProblemController {
    public static String theme = "amaze";
    @Resource
    private ConfigService configService;
    @Resource
    private LinkService linkService;
    @Resource
    HttpServletRequest request;

    @GetMapping("/next")
    public String next(@RequestParam("id") Integer id) {

        log.info("刷题日志");
        request.setAttribute("pageName", "友情链接");
        HashMap<Integer, Question> questionHashMap = fenxiData();

//        request.setAttribute("tiMap", tiMap);
        request.setAttribute("question", questionHashMap.get(id));
        log.info("---->{}", questionHashMap.get(id));
//        ArrayList arrayList = new ArrayList();
//        arrayList.add("")
        request.setAttribute("name", "韩静");

        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/problem";
    }



    @PostMapping("/next2")
    @ResponseBody
    public String next2(@RequestBody QuestResultDTO questResultDTO) {
        log.info("questResultDTO-->{}",questResultDTO);
        HashMap<Integer, Question> questionHashMap = fenxiData();
        Question question = questionHashMap.get(questResultDTO.getId());
       AtomicReference<String> result = new AtomicReference<>("答对:" + question.getAnswerList());
        question.getAnswerList().forEach(k->{
            if(!questResultDTO.getAnswerList().contains(k)){
                result.set("答错:正确答案是" + question.getAnswerList().toString());
            }
        });
        questResultDTO.getAnswerList().forEach(k->{
            if(!question.getAnswerList().contains(k)){
                result.set("答错:正确答案是" + question.getAnswerList().toString());
            }
        });
        request.setAttribute("configurations", configService.getAllConfigs());
        return result.get();
    }


    private HashMap<Integer, Question> fenxiData() {
        BufferedReader utf8Reader = ResourceUtil.getUtf8Reader("aaaa.sql");
        HashMap<Integer, Question> questionHashMap = new HashMap<>();
        final Integer[] count = {1};
        utf8Reader.lines().forEach(row -> {
                    if (row.contains("第") && row.contains("页")) {

                    } else {
                        if (count[0].toString().equals(row.split(" ")[0])) {
                            //问题
                            Question question = new Question();
                            question.setQuestion(row);
                            question.setId(count[0]);
                            questionHashMap.put(count[0], question);
                            count[0]++;
                        } else {
                            //选项
                            if (row.contains("A．")) {
                                questionHashMap.get((count[0] - 1)).setA(row);
                            } else if (row.contains("B．")) {
                                questionHashMap.get((count[0] - 1)).setB(row);
                            } else if ((row.contains("C．"))) {
                                questionHashMap.get((count[0] - 1)).setC(row);
                            } else if (row.contains("D．")) {
                                questionHashMap.get((count[0] - 1)).setD(row);
                            } else if (row.contains("E．")) {
                                questionHashMap.get((count[0] - 1)).setE(row);
                            } else {
                                //问题
                                Question question = questionHashMap.get((count[0] - 1));
                                question.setQuestion(question.getQuestion() + row);
                            }
                        }
                        //答案
                        if ((row.contains("A") && !row.contains("A．"))) {
                            questionHashMap.get((count[0] - 1)).getAnswerList().add("A");
                        } else if ((row.contains("B") && !row.contains("B．"))) {
                            questionHashMap.get((count[0] - 1)).getAnswerList().add("B");
                        } else if ((row.contains("C") && !row.contains("C．"))) {
                            questionHashMap.get((count[0] - 1)).getAnswerList().add("C");
                        } else if ((row.contains("D") && !row.contains("D．"))) {
                            questionHashMap.get((count[0] - 1)).getAnswerList().add("D");
                        } else if ((row.contains("E") && !row.contains("E．"))) {
                            questionHashMap.get((count[0] - 1)).getAnswerList().add("E");
                        }
                    }
                }

        );

        questionHashMap.forEach((k, v) -> {
            v.setQuestion(v.getQuestion().replaceAll("A", ""));
            ;
            v.setQuestion(v.getQuestion().replaceAll("B", ""));
            ;
            v.setQuestion(v.getQuestion().replaceAll("C", ""));
            ;
            v.setQuestion(v.getQuestion().replaceAll("D", ""));
            ;
            v.setQuestion(v.getQuestion().replaceAll("E", ""));
            ;
        });
        return questionHashMap;
    }




}
