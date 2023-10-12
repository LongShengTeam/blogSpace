package com.site.blog.my.core.process.data;

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
public class ProcessingQuestion {
    private Long id;
    private String question="";
    private List<String> answerList=new ArrayList<>();
    private String A="";
    private String B="";
    private String C="";
    private String D="";
    private String E="";
    private Boolean error;
    private Boolean done=false;


}
