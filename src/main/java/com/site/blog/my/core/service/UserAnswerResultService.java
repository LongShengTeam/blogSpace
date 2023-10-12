package com.site.blog.my.core.service;

import com.site.blog.my.core.controller.problem.QuestResultDTO;
import com.site.blog.my.core.entity.UserAnswerResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserAnswerResultService extends IService<UserAnswerResult> {

    UserAnswerResult selectUserInfo(QuestResultDTO questResultDTO);

    UserAnswerResult selectErrorQuestion(String userName);
}
