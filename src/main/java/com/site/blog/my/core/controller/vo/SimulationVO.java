package com.site.blog.my.core.controller.vo;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * to do
 * 数据处理对象
 * @author Hj
 * @date 2023/10/5
 */
@Data
@ToString
public class SimulationVO {
    private Long id;
    private String questionDesc="";
    private List<String> answerList=new ArrayList<>();
    private String option1="";
    private String option2="";
    private String option3="";
    private String option4="";
    private String option5="";
    private String option6="";
    private Boolean error;
    private Boolean done=false;
}
