package com.site.blog.my.core.mapper;

import com.site.blog.my.core.controller.problem.QuestResultDTO;
import com.site.blog.my.core.entity.UserAnswerResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Entity com.site.blog.my.core.entity.UserAnswerResult
 */
public interface UserAnswerResultMapper extends BaseMapper<UserAnswerResult> {
    UserAnswerResult selectUserInfo(QuestResultDTO questResultDTO);

    UserAnswerResult selectErrorQuestion(String userName);

}




