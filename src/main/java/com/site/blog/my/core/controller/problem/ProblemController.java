package com.site.blog.my.core.controller.problem;

import cn.hutool.core.io.resource.ResourceUtil;
import com.site.blog.my.core.process.data.ProcessingQuestion;
import com.site.blog.my.core.controller.vo.SimulationVO;
import com.site.blog.my.core.entity.Question;
import com.site.blog.my.core.service.ConfigService;
import com.site.blog.my.core.service.LinkService;
import com.site.blog.my.core.service.QuestionService;
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
    HttpServletRequest request;
    private static HashMap<String, List<SimulationVO>> moniHashMap = new HashMap<>();
    private static HashMap<String, List<ProcessingQuestion>> errorMap = new HashMap<String, List<ProcessingQuestion>>();

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
                id = Long.valueOf(new Random().nextInt(2311));
                log.info("---->{}", questionService.getBaseMapper().selectById(id));
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
                            id = Long.valueOf(new Random().nextInt(2311));
                            if (!objects.contains(id)) {
                                objects.add(id);
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
                List<ProcessingQuestion> errorQuestionList = errorMap.get("userName");
                if (errorQuestionList != null) {
                    request.setAttribute("question", errorQuestionList.get(0));
                } else {
                    request.setAttribute("question", questionService.getBaseMapper().selectById(id));
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
        Question question = questionService.getBaseMapper().selectById(questResultDTO.getId());


        List<ProcessingQuestion> questions = errorMap.get(questResultDTO.getUserName());
        if (questions == null) {
            questions = new ArrayList<ProcessingQuestion>();
            errorMap.put(questResultDTO.getUserName(), questions);
        }
        String result = "答对:" + question.getQuestionAnswer().toString();
        List<String> answerDTOList = questResultDTO.getAnswerList();
        List<String> answerQueryList = Arrays.asList(question.getQuestionAnswer().split(","));
        Boolean aBoolean=false;
        switch (questResultDTO.getType()) {
            case 1:
                aBoolean= compareAnswers(answerDTOList, answerQueryList);
                if(!aBoolean){
                    result="答错:正确答案是" + answerDTOList.toString();
                }
                break;
            case 2:
                 aBoolean = compareAnswers(answerDTOList, answerQueryList);
                if(!aBoolean){
                    result="答错:正确答案是" + answerDTOList.toString();
                }
                break;
            case 3:
                //模拟答题
                List<SimulationVO> moniQuestionList = moniHashMap.get(questResultDTO.getUserName());
                SimulationVO simulationVO=null;
                List<SimulationVO> collect = moniQuestionList.parallelStream().filter(row -> row.getId().equals(questResultDTO.getId())).collect(Collectors.toList());
                if (collect != null && collect.size() > 0) {
                    simulationVO= collect.get(0);
                } else {
                    log.error("question--->{}", question);
                    log.error("moniQuestionList--->{}", moniQuestionList);
                }


                simulationVO.setError(false);
                simulationVO.setDone(true);

                aBoolean = compareAnswers(answerDTOList, answerQueryList);
                if(!aBoolean){
                    result="答错:正确答案是" + answerDTOList.toString();
                }
                result += "--->总共" + moniQuestionList.size() + "个,";
                long errorCount = moniQuestionList.parallelStream().filter(row -> row.getError() != null && row.getError()).count();
                result += "--->错了" + errorCount + "个,";
                long rightCount = moniQuestionList.parallelStream().filter(row -> row.getError() != null && !row.getError()).count();
                result += "--->对了" + rightCount + "个!";
                break;
            case 4:
                aBoolean = compareAnswers(answerDTOList, answerQueryList);
                if(!aBoolean){
                    result="答错:正确答案是" + answerDTOList.toString();
                }
                break;
        }

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
