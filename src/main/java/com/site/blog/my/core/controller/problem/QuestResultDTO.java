package com.site.blog.my.core.controller.problem;

import lombok.Data;

import java.util.List;

/**
 * to do
 *
 * @author Hj
 * @date 2023/10/5
 */
@Data
public class QuestResultDTO {
    private Integer id;
    private List<String> answerList;
    private Integer type;
    private String userName;
}
