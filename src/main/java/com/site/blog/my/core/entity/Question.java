package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName question
 */
@TableName(value ="question")
public class Question implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 问题描述
     */
    private String questionDesc;

    /**
     * 问题描述
     */
    private Integer questionType;

    /**
     * 问题答案
     */
    private String questionAnswer;

    /**
     * 选项1
     */
    private String option1;

    /**
     * 选项2
     */
    private String option2;

    /**
     * 选项3
     */
    private String option3;

    /**
     * 选项4
     */
    private String option4;

    /**
     * 选项5
     */
    private String option5;

    /**
     * 选项6
     */
    private String option6;

    /**
     * 回答错误数
     */
    private Long answerErrorCount;

    /**
     * 回答总数
     */
    private Long answercount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 问题描述
     */
    public String getQuestionDesc() {
        return questionDesc;
    }

    /**
     * 问题描述
     */
    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    /**
     * 问题描述
     */
    public Integer getQuestionType() {
        return questionType;
    }

    /**
     * 问题描述
     */
    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    /**
     * 问题答案
     */
    public String getQuestionAnswer() {
        return questionAnswer;
    }

    /**
     * 问题答案
     */
    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    /**
     * 选项1
     */
    public String getOption1() {
        return option1;
    }

    /**
     * 选项1
     */
    public void setOption1(String option1) {
        this.option1 = option1;
    }

    /**
     * 选项2
     */
    public String getOption2() {
        return option2;
    }

    /**
     * 选项2
     */
    public void setOption2(String option2) {
        this.option2 = option2;
    }

    /**
     * 选项3
     */
    public String getOption3() {
        return option3;
    }

    /**
     * 选项3
     */
    public void setOption3(String option3) {
        this.option3 = option3;
    }

    /**
     * 选项4
     */
    public String getOption4() {
        return option4;
    }

    /**
     * 选项4
     */
    public void setOption4(String option4) {
        this.option4 = option4;
    }

    /**
     * 选项5
     */
    public String getOption5() {
        return option5;
    }

    /**
     * 选项5
     */
    public void setOption5(String option5) {
        this.option5 = option5;
    }

    /**
     * 选项6
     */
    public String getOption6() {
        return option6;
    }

    /**
     * 选项6
     */
    public void setOption6(String option6) {
        this.option6 = option6;
    }

    /**
     * 回答错误数
     */
    public Long getAnswerErrorCount() {
        return answerErrorCount;
    }

    /**
     * 回答错误数
     */
    public void setAnswerErrorCount(Long answerErrorCount) {
        this.answerErrorCount = answerErrorCount;
    }

    /**
     * 回答总数
     */
    public Long getAnswercount() {
        return answercount;
    }

    /**
     * 回答总数
     */
    public void setAnswercount(Long answercount) {
        this.answercount = answercount;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Question other = (Question) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getQuestionDesc() == null ? other.getQuestionDesc() == null : this.getQuestionDesc().equals(other.getQuestionDesc()))
            && (this.getQuestionType() == null ? other.getQuestionType() == null : this.getQuestionType().equals(other.getQuestionType()))
            && (this.getQuestionAnswer() == null ? other.getQuestionAnswer() == null : this.getQuestionAnswer().equals(other.getQuestionAnswer()))
            && (this.getOption1() == null ? other.getOption1() == null : this.getOption1().equals(other.getOption1()))
            && (this.getOption2() == null ? other.getOption2() == null : this.getOption2().equals(other.getOption2()))
            && (this.getOption3() == null ? other.getOption3() == null : this.getOption3().equals(other.getOption3()))
            && (this.getOption4() == null ? other.getOption4() == null : this.getOption4().equals(other.getOption4()))
            && (this.getOption5() == null ? other.getOption5() == null : this.getOption5().equals(other.getOption5()))
            && (this.getOption6() == null ? other.getOption6() == null : this.getOption6().equals(other.getOption6()))
            && (this.getAnswerErrorCount() == null ? other.getAnswerErrorCount() == null : this.getAnswerErrorCount().equals(other.getAnswerErrorCount()))
            && (this.getAnswercount() == null ? other.getAnswercount() == null : this.getAnswercount().equals(other.getAnswercount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getQuestionDesc() == null) ? 0 : getQuestionDesc().hashCode());
        result = prime * result + ((getQuestionType() == null) ? 0 : getQuestionType().hashCode());
        result = prime * result + ((getQuestionAnswer() == null) ? 0 : getQuestionAnswer().hashCode());
        result = prime * result + ((getOption1() == null) ? 0 : getOption1().hashCode());
        result = prime * result + ((getOption2() == null) ? 0 : getOption2().hashCode());
        result = prime * result + ((getOption3() == null) ? 0 : getOption3().hashCode());
        result = prime * result + ((getOption4() == null) ? 0 : getOption4().hashCode());
        result = prime * result + ((getOption5() == null) ? 0 : getOption5().hashCode());
        result = prime * result + ((getOption6() == null) ? 0 : getOption6().hashCode());
        result = prime * result + ((getAnswerErrorCount() == null) ? 0 : getAnswerErrorCount().hashCode());
        result = prime * result + ((getAnswercount() == null) ? 0 : getAnswercount().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", questionDesc=").append(questionDesc);
        sb.append(", questionType=").append(questionType);
        sb.append(", questionAnswer=").append(questionAnswer);
        sb.append(", option1=").append(option1);
        sb.append(", option2=").append(option2);
        sb.append(", option3=").append(option3);
        sb.append(", option4=").append(option4);
        sb.append(", option5=").append(option5);
        sb.append(", option6=").append(option6);
        sb.append(", answerErrorCount=").append(answerErrorCount);
        sb.append(", answercount=").append(answercount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}