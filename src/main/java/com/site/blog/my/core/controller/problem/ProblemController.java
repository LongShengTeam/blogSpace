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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    private static HashMap<String, List<Question>> moniHashMap = new HashMap<>();
    private static HashMap<String, List<Question>> errorMap = new HashMap<String, List<Question>>();

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

    @GetMapping("/next3")
    public String next(@RequestParam("id") Integer id, @RequestParam("userName") String userName,
                       @RequestParam("type") Integer type) {
        log.info("id-->{}", id);
        log.info("userName-->{}", userName);
        log.info("type-->{}", type);
        log.info("刷题日志");
        request.setAttribute("pageName", "友情链接");
        HashMap<Integer, Question> questionHashMap = fenxiData();

//        request.setAttribute("tiMap", tiMap);

        request.setAttribute("userName", userName);
        request.setAttribute("type", type);
        switch (type) {
//              <option value="1" th:selected="${type == 1}"  autocomplete="off">顺序</option>
            case 1:
                request.setAttribute("question", questionHashMap.get(id));
                log.info("---->{}", questionHashMap.get(id));
                break;
//            <option value="2" th:selected="${type == 2}"  autocomplete="off">随机</option>
            case 2:
                id = new Random().nextInt(2311);
                log.info("---->{}", questionHashMap.get(id));
                request.setAttribute("question", questionHashMap.get(id));
                break;
//            <option value="3" th:selected="${type == 3}"  autocomplete="off">模拟100题</option>
            case 3:
                List<Question> questionList = moniHashMap.get(userName);
                List<Question> noDonecollect = null;

                if (questionList != null) {
                    Boolean done = true;
                    noDonecollect = questionList.parallelStream().filter(row -> !row.getDone()).collect(Collectors.toList());
                    if (noDonecollect.size() == 0) {
                        questionList = new ArrayList<>();
                        ArrayList<Integer> objects = new ArrayList<>();
                        while (questionList.size() < 100) {
                            id = new Random().nextInt(2311);
                            if (!objects.contains(id)) {
                                objects.add(id);
                                questionList.add(questionHashMap.get(id));
                            }

                        }
                        moniHashMap.put(userName, questionList);
                    }
                } else {

                    questionList = new ArrayList<>();
                    ArrayList<Integer> objects = new ArrayList<>();
                    while (questionList.size() < 100) {
                        id = new Random().nextInt(2311);
                        if (!objects.contains(id)) {
                            objects.add(id);
                            questionList.add(questionHashMap.get(id));
                        }

                    }
                    moniHashMap.put(userName, questionList);


                }
                questionList.forEach(row -> {
                    if (!row.getDone()) {
                        request.setAttribute("question", row);
                        log.info("---->{}", row);
                    }
                });

                break;
//            <option value="4" th:selected="${type == 4}"  autocomplete="off">错题</option>
            case 4:
                List<Question> errorQuestionList = errorMap.get("userName");
                if (errorQuestionList != null) {
                    request.setAttribute("question", errorQuestionList.get(0));
                } else {
                    request.setAttribute("question", questionHashMap.get(id));
                }
                break;
//
        }

//        ArrayList arrayList = new ArrayList();
//        arrayList.add("")
        request.setAttribute("name", "韩静");

        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/problem";
    }


    /**
     * 回答问题
     *
     * @param questResultDTO
     * @return
     */
    @PostMapping("/answer")
    @ResponseBody
    public String answer(@RequestBody QuestResultDTO questResultDTO) {
        log.info("questResultDTO-->{}", questResultDTO);
        HashMap<Integer, Question> questionHashMap = fenxiData();
        Question question = questionHashMap.get(questResultDTO.getId());
        List<Question> questions = errorMap.get(questResultDTO.getUserName());
        AtomicReference<String> result = null;
        if (questions == null) {
            questions = new ArrayList<Question>();
            errorMap.put(questResultDTO.getUserName(), questions);
        }
        switch (questResultDTO.getType()) {
            case 1:
                result = new AtomicReference<>("答对:" + question.getAnswerList());
                List<Question> finalQuestions = questions;
                AtomicReference<String> finalResult = result;
                Question finalQuestion = question;
                question.getAnswerList().forEach(k -> {
                    if (!questResultDTO.getAnswerList().contains(k)) {
                        finalQuestions.add(finalQuestion);
                        finalResult.set("答错:正确答案是" + finalQuestion.getAnswerList().toString());
                    }
                });
                AtomicReference<String> finalResult1 = result;
                questResultDTO.getAnswerList().forEach(k -> {
                    if (!finalQuestion.getAnswerList().contains(k)) {
                        finalQuestions.add(finalQuestion);
                        finalResult1.set("答错:正确答案是" + finalQuestion.getAnswerList().toString());
                    }
                });
                break;
            case 2:
                result = new AtomicReference<>("答对:" + question.getAnswerList());
                List<Question> finalQuestions2 = questions;
                AtomicReference<String> finalResult2 = result;
                Question finalQuestion1 = question;
                question.getAnswerList().forEach(k -> {
                    if (!questResultDTO.getAnswerList().contains(k)) {
                        finalQuestions2.add(finalQuestion1);
                        finalResult2.set("答错:正确答案是" + finalQuestion1.getAnswerList().toString());
                    }
                });
                questResultDTO.getAnswerList().forEach(k -> {
                    if (!finalQuestion1.getAnswerList().contains(k)) {
                        finalQuestions2.add(finalQuestion1);
                        finalResult2.set("答错:正确答案是" + finalQuestion1.getAnswerList().toString());
                    }
                });

                break;
            case 3:
                //模拟答题
                List<Question> moniQuestionList = moniHashMap.get(questResultDTO.getUserName());
                Question finalQuestion4 = question;
                List<Question> collect = moniQuestionList.parallelStream().filter(row -> row.getId().equals(finalQuestion4.getId())).collect(Collectors.toList());
                if(collect!=null && collect.size()>0){
                    question=collect.get(0);
                }else{
                    log.error("question--->{}",question);
                    log.error("moniQuestionList--->{}",moniQuestionList);
                }
                String s = "答对:" + question.getAnswerList();
                result = new AtomicReference<>(s);


                List<Question> finalQuestions3 = questions;
                AtomicReference<String> finalResult3 = result;
                question.setError(false);
                question.setDone(true);
                Question finalQuestion2 = question;
                question.getAnswerList().forEach(k -> {
                    if (!questResultDTO.getAnswerList().contains(k)) {
                        finalQuestion2.setError(true);
                        finalQuestions3.add(finalQuestion2);
                        finalResult3.set("答错:正确答案是" + finalQuestion2.getAnswerList().toString());
                    }
                });

                questResultDTO.getAnswerList().forEach(k -> {
                    if (!finalQuestion2.getAnswerList().contains(k)) {
                        finalQuestion2.setError(true);
                        finalQuestions3.add(finalQuestion2);
                        finalResult3.set("答错:正确答案是" + finalQuestion2.getAnswerList().toString());
                    }
                });
                s += "--->总共" + moniQuestionList.size() + "个,";
                long errorCount = moniQuestionList.parallelStream().filter(row -> row.getError() != null && row.getError()).count();
                s += "--->错了" + errorCount + "个,";
                long rightCount = moniQuestionList.parallelStream().filter(row -> row.getError() != null && !row.getError()).count();
                s += "--->对了" + rightCount + "个!";
                finalResult3.set(finalResult3.get() + s);
                break;
            case 4:
                result = new AtomicReference<>("答对:" + question.getAnswerList());
                List<Question> finalQuestions4 = questions;
                AtomicReference<String> finalResult4 = result;
                Question finalQuestion3 = question;
                question.getAnswerList().forEach(k -> {
                    if (!questResultDTO.getAnswerList().contains(k)) {
                        finalQuestions4.add(finalQuestion3);
                        finalResult4.set("答错:正确答案是" + finalQuestion3.getAnswerList().toString());
                    }
                });
                questResultDTO.getAnswerList().forEach(k -> {
                    if (!finalQuestion3.getAnswerList().contains(k)) {
                        finalQuestions4.add(finalQuestion3);
                        finalResult4.set("答错:正确答案是" + finalQuestion3.getAnswerList().toString());
                    }
                });

                break;


        }


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
