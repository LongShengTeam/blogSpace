package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 用户回答的情况
 * @TableName user_answer_result
 */
@TableName(value ="user_answer_result")
public class UserAnswerResult implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * user_name
     */
    private String userName;

    /**
     * 问题答案
     */
    private Long questionId;

    /**
     * 回答错误数
     */
    private Long answerErrorCount;

    /**
     * 最近结果是否正确0正确 1错误
     */
    private Integer answerLastResult;

    /**
     * 回答总数
     */
    private Long answerCount;

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
     * user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * user_name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 问题答案
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * 问题答案
     */
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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
     * 最近结果是否正确0正确 1错误
     */
    public Integer getAnswerLastResult() {
        return answerLastResult;
    }

    /**
     * 最近结果是否正确0正确 1错误
     */
    public void setAnswerLastResult(Integer answerLastResult) {
        this.answerLastResult = answerLastResult;
    }

    /**
     * 回答总数
     */
    public Long getAnswerCount() {
        return answerCount;
    }

    /**
     * 回答总数
     */
    public void setAnswerCount(Long answerCount) {
        this.answerCount = answerCount;
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
        UserAnswerResult other = (UserAnswerResult) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getQuestionId() == null ? other.getQuestionId() == null : this.getQuestionId().equals(other.getQuestionId()))
            && (this.getAnswerErrorCount() == null ? other.getAnswerErrorCount() == null : this.getAnswerErrorCount().equals(other.getAnswerErrorCount()))
            && (this.getAnswerLastResult() == null ? other.getAnswerLastResult() == null : this.getAnswerLastResult().equals(other.getAnswerLastResult()))
            && (this.getAnswerCount() == null ? other.getAnswerCount() == null : this.getAnswerCount().equals(other.getAnswerCount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getQuestionId() == null) ? 0 : getQuestionId().hashCode());
        result = prime * result + ((getAnswerErrorCount() == null) ? 0 : getAnswerErrorCount().hashCode());
        result = prime * result + ((getAnswerLastResult() == null) ? 0 : getAnswerLastResult().hashCode());
        result = prime * result + ((getAnswerCount() == null) ? 0 : getAnswerCount().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userName=").append(userName);
        sb.append(", questionId=").append(questionId);
        sb.append(", answerErrorCount=").append(answerErrorCount);
        sb.append(", answerLastResult=").append(answerLastResult);
        sb.append(", answerCount=").append(answerCount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}