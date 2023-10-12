package com.site.blog.my.core.controller.problem;

import cn.hutool.core.io.resource.ResourceUtil;
import com.site.blog.my.core.process.data.ProcessingQuestion;
import com.site.blog.my.core.dao.QuestionMapper;
import com.site.blog.my.core.entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.io.BufferedReader;
import java.util.HashMap;

/**
 * to do
 *
 * @author Hj
 * @date 2023/10/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class ProblemControllerTest {

    @Test
    public void next() {

    }

    @Resource
    private QuestionMapper questionMapper;

    @Test
    public  void demo(){
        HashMap<Long, ProcessingQuestion> integerQuestionHashMap = fenxiData();
        integerQuestionHashMap.forEach((k,v)->{
            Question question = new Question();
            question.setId(0L);
            question.setQuestionDesc(v.getQuestion());
            question.setQuestionType(0);
            question.setOption1(v.getA());
            question.setOption2(v.getB());
            question.setOption3(v.getC());
            question.setOption4(v.getD());
            question.setOption5(v.getE());
            question.setOption6("");
            question.setQuestionAnswer(v.getAnswerList().toString());
            questionMapper.insert(question);
        });


    }

    private HashMap<Long, ProcessingQuestion> fenxiData() {
        BufferedReader utf8Reader = ResourceUtil.getUtf8Reader("aaaa.sql");
        HashMap<Long, ProcessingQuestion> questionHashMap = new HashMap<>();
        final Long[] count = {1L};
        utf8Reader.lines().forEach(row -> {
                    if (row.contains("第") && row.contains("页")) {

                    } else {
                        if (count[0].toString().equals(row.split(" ")[0])) {
                            //问题
                            ProcessingQuestion question = new ProcessingQuestion();
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
                                ProcessingQuestion question = questionHashMap.get((count[0] - 1));
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