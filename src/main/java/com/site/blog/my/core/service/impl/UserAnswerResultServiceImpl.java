package com.site.blog.my.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.my.core.controller.problem.QuestResultDTO;
import com.site.blog.my.core.entity.UserAnswerResult;
import com.site.blog.my.core.service.UserAnswerResultService;
import com.site.blog.my.core.mapper.UserAnswerResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserAnswerResultServiceImpl extends ServiceImpl<UserAnswerResultMapper, UserAnswerResult>
    implements UserAnswerResultService{
    @Autowired
    UserAnswerResultMapper userAnswerResultMapper;

    @Override
    public UserAnswerResult selectUserInfo(QuestResultDTO questResultDTO) {
        return userAnswerResultMapper.selectUserInfo(questResultDTO);
    }

    @Override
    public UserAnswerResult selectErrorQuestion(String userName) {
        return userAnswerResultMapper.selectErrorQuestion(userName);
    }
}




