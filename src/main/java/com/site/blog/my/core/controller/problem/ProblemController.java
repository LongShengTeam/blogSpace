package com.site.blog.my.core.controller.problem;

import cn.hutool.core.io.resource.ResourceUtil;
import com.site.blog.my.core.entity.UserAnswerResult;
import com.site.blog.my.core.process.data.ProcessingQuestion;
import com.site.blog.my.core.controller.vo.SimulationVO;
import com.site.blog.my.core.entity.Question;
import com.site.blog.my.core.service.ConfigService;
import com.site.blog.my.core.service.LinkService;
import com.site.blog.my.core.service.QuestionService;
import com.site.blog.my.core.service.UserAnswerResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;


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
    private QuestionService questionService;

    @Resource
    private UserAnswerResultService userAnswerResultService;

    @Resource
    HttpServletRequest request;
    private static HashMap<String, List<SimulationVO>> moniHashMap = new HashMap<>();

    @GetMapping("/next")
    public String next(@RequestParam("id") Integer id) {

        log.info("刷题日志");
        request.setAttribute("pageName", "友情链接");
        Question question = questionService.getBaseMapper().selectById(id);
//        request.setAttribute("tiMap", tiMap);
        request.setAttribute("question", question);
        log.info("---->{}", question);
//        ArrayList arrayList = new ArrayList();
//        arrayList.add("")
        request.setAttribute("name", "韩静");

        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/problem";
    }

    @GetMapping("/next3")
    public String next(@RequestParam("id") Long id, @RequestParam("userName") String userName,
                       @RequestParam("type") Integer type) {
        log.info("id-->{}", id);
        log.info("userName-->{}", userName);
        log.info("type-->{}", type);
        log.info("刷题日志");
        request.setAttribute("pageName", "友情链接");
//        request.setAttribute("tiMap", tiMap);

        request.setAttribute("userName", userName);
        request.setAttribute("type", type);

        switch (type) {
//              <option value="1" th:selected="${type == 1}"  autocomplete="off">顺序</option>
            case 1:
                request.setAttribute("question", questionService.getBaseMapper().selectById(id));
                log.info("---->{}", questionService.getBaseMapper().selectById(id));
                break;
//            <option value="2" th:selected="${type == 2}"  autocomplete="off">随机</option>
            case 2:

                log.info("---->{}", questionService.getRandomQuestion());
                request.setAttribute("question", questionService.getBaseMapper().selectById(id));
                break;
//            <option value="3" th:selected="${type == 3}"  autocomplete="off">模拟100题</option>
            case 3:
                List<SimulationVO> questionList = moniHashMap.get(userName);
                List<SimulationVO> noDonecollect = null;

                if (questionList != null) {
                    Boolean done = true;
                    noDonecollect = questionList.parallelStream().filter(row -> !row.getDone()).collect(Collectors.toList());
                    if (noDonecollect.size() == 0) {
                        questionList = new ArrayList<>();
                        List<Long> objects = new ArrayList<>();
                        while (questionList.size() < 100) {
                            Question question = questionService.getRandomQuestion();
                            id = question.getId();
                            if (!objects.contains(id)) {
                                SimulationVO simulationVO = new SimulationVO();
                                simulationVO.setId(question.getId());
                                simulationVO.setQuestionDesc(question.getQuestionDesc());
                                String questionAnswer = question.getQuestionAnswer();
                                List<String> answerList = Arrays.asList(questionAnswer.split(","));
                                simulationVO.setAnswerList(answerList);
                                simulationVO.setOption1(question.getOption1());
                                simulationVO.setOption2(question.getOption2());
                                simulationVO.setOption3(question.getOption3());
                                simulationVO.setOption4(question.getOption4());
                                simulationVO.setOption5(question.getOption5());
                                simulationVO.setOption6(question.getOption6());
                                questionList.add(simulationVO);
                                objects.add(id);
                            }

                        }
                        moniHashMap.put(userName, questionList);
                    }
                } else {

                    questionList = new ArrayList<>();
                    ArrayList<Long> objects = new ArrayList<>();
                    while (questionList.size() < 100) {
                        id = Long.valueOf(new Random().nextInt(2311));
                        if (!objects.contains(id)) {
                            Question question = questionService.getBaseMapper().selectById(id);
                            SimulationVO simulationVO = new SimulationVO();
                            simulationVO.setId(question.getId());
                            simulationVO.setQuestionDesc(question.getQuestionDesc());
                            String questionAnswer = question.getQuestionAnswer();
                            List<String> answerList = Arrays.asList(questionAnswer.split(","));
                            simulationVO.setAnswerList(answerList);
                            simulationVO.setOption1(question.getOption1());
                            simulationVO.setOption2(question.getOption2());
                            simulationVO.setOption3(question.getOption3());
                            simulationVO.setOption4(question.getOption4());
                            simulationVO.setOption5(question.getOption5());
                            simulationVO.setOption6(question.getOption6());
                            questionList.add(simulationVO);

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
                // 错题 下一个
                UserAnswerResult userAnswerResult = userAnswerResultService.selectErrorQuestion(userName);
                if (userAnswerResult != null) {
                    request.setAttribute("question", questionService.getBaseMapper().selectById(userAnswerResult.getQuestionId()));
                } else {
                    request.setAttribute("question", questionService.getBaseMapper().selectById(id));
                }
                break;
        }
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
        Question questionQuery = questionService.getBaseMapper().selectById(questResultDTO.getId());
        UserAnswerResult userAnswerResult = userAnswerResultService.selectUserInfo(questResultDTO);
        if (Objects.isNull(userAnswerResult)) {
            userAnswerResult = new UserAnswerResult();
            userAnswerResult.setUserName(questResultDTO.getUserName());
            userAnswerResult.setQuestionId(questResultDTO.getId());
            userAnswerResult.setAnswerErrorCount(0L);
            userAnswerResult.setAnswerCount(0L);
            userAnswerResultService.getBaseMapper().insert(userAnswerResult);
            userAnswerResult = userAnswerResultService.selectUserInfo(questResultDTO);
        }
        userAnswerResult.setAnswerCount(userAnswerResult.getAnswerCount() + 1);
        List<String> answerDTOList = questResultDTO.getAnswerList();
        String answerQueryStr = questionQuery.getQuestionAnswer().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "");
        String result = "答对:" + answerQueryStr;
        List<String> answerQueryList = Arrays.asList(answerQueryStr.split(","));
        Boolean aBoolean = false;
        switch (questResultDTO.getType()) {
            case 1:
                aBoolean = compareAnswers(answerDTOList, answerQueryList);
                if (!aBoolean) {
                    result = "答错:正确答案是" + answerQueryList.toString();
                    userAnswerResult.setAnswerErrorCount(userAnswerResult.getAnswerErrorCount() + 1);
                    userAnswerResult.setAnswerLastResult(1);
                } else {
                    //正确
                    userAnswerResult.setAnswerLastResult(0);
                }
                break;
            case 2:
                aBoolean = compareAnswers(answerDTOList, answerQueryList);
                if (!aBoolean) {
                    result = "答错:正确答案是" + answerQueryList.toString();
                    userAnswerResult.setAnswerErrorCount(userAnswerResult.getAnswerErrorCount() + 1);
                    userAnswerResult.setAnswerLastResult(1);
                } else {
                    //正确
                    userAnswerResult.setAnswerLastResult(0);
                }
                break;
            case 3:
                //模拟答题
                List<SimulationVO> moniQuestionList = moniHashMap.get(questResultDTO.getUserName());
                SimulationVO simulationVO = null;
                List<SimulationVO> collect = moniQuestionList.parallelStream().filter(row -> row.getId().equals(questResultDTO.getId())).collect(Collectors.toList());
                if (collect != null && collect.size() > 0) {
                    simulationVO = collect.get(0);
                } else {
                    log.error("question--->{}", questionQuery);
                    log.error("moniQuestionList--->{}", moniQuestionList);
                }
                simulationVO.setError(false);
                simulationVO.setDone(true);
                aBoolean = compareAnswers(answerDTOList, answerQueryList);
                if (!aBoolean) {
                    result = "答错:正确答案是" + answerQueryList.toString();
                    userAnswerResult.setAnswerErrorCount(userAnswerResult.getAnswerErrorCount() + 1);
                    userAnswerResult.setAnswerLastResult(1);
                    simulationVO.setError(true);
                } else {
                    //正确
                    userAnswerResult.setAnswerLastResult(0);
                }
                result += "--->总共" + moniQuestionList.size() + "个,";
                long errorCount = moniQuestionList.parallelStream().filter(row -> row.getError() != null && row.getError()).count();
                result += "--->错了" + errorCount + "个,";
                long rightCount = moniQuestionList.parallelStream().filter(row -> row.getError() != null && !row.getError()).count();
                result += "--->对了" + rightCount + "个!";
                break;
            case 4:
                aBoolean = compareAnswers(answerDTOList, answerQueryList);
                if (!aBoolean) {
                    result = "答错:正确答案是" + answerQueryList.toString();
                    userAnswerResult.setAnswerErrorCount(userAnswerResult.getAnswerErrorCount() + 1);
                    userAnswerResult.setAnswerLastResult(1);
                } else {
                    //正确
                    userAnswerResult.setAnswerLastResult(0);
                }
                break;
        }
        userAnswerResultService.getBaseMapper().updateById(userAnswerResult);
        request.setAttribute("configurations", configService.getAllConfigs());
        return result;
    }


    public Boolean compareAnswers(List<String> answerDTOList, List<String> answerQueryList) {
        // 输出结果
        log.info("结果answerDTOList：" + answerDTOList);
        log.info("结果answerQueryList：" + answerQueryList);
        // 使用HashSet求差集
        HashSet<String> set1 = new HashSet<>(answerDTOList);
        set1.removeAll(answerQueryList);
        // 使用HashSet求差集
        HashSet<String> set2 = new HashSet<>(answerQueryList);
        set2.removeAll(answerDTOList);
        return set1.size() == 0 && set2.size() == 0;
    }


}
