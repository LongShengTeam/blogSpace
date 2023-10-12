package com.site.blog.my.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.my.core.entity.Question;
import com.site.blog.my.core.service.QuestionService;
import com.site.blog.my.core.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

    @Autowired
    QuestionMapper questionMapper;
    @Override
    public Question getRandomQuestion() {
        return questionMapper.getRandomQuestion();
    }
}




