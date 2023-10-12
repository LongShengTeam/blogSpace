 --  question 题
 drop table  question;
 CREATE TABLE question (
  id BIGINT(25) PRIMARY KEY AUTO_INCREMENT,
  question_desc VARCHAR(2000) NOT NULL COMMENT '问题描述',
  question_type int(4) COMMENT '问题type',
   int(4) COMMENT '问题类别',
  question_answer VARCHAR(20) COMMENT '问题答案',
  option1 VARCHAR(500) NOT NULL COMMENT '选项1',
  option2 VARCHAR(500) NOT NULL COMMENT '选项2',
  option3 VARCHAR(500)  COMMENT '选项3',
  option4 VARCHAR(500)  COMMENT '选项4',
  option5 VARCHAR(500)  COMMENT '选项5',
  option6 VARCHAR(500)  COMMENT '选项6',
  answer_error_count BIGINT(25) NOT NULL DEFAULT 0 COMMENT '回答错误数',
  answerCount BIGINT(25) NOT NULL DEFAULT 0  COMMENT '回答总数'
) COMMENT '题';

ALTER TABLE question ADD `category` int(4) DEFAULT NULL COMMENT '问题类别';


--  user_answer_result 用户回答的情况
 drop table  user_answer_result;
 CREATE TABLE user_answer_result (
  id BIGINT(25) PRIMARY KEY AUTO_INCREMENT,
  user_name VARCHAR(25) NOT NULL COMMENT 'user_name',
  question_id BIGINT(25) COMMENT '问题答案',
  answer_error_count BIGINT(25) NOT NULL DEFAULT 0 COMMENT '回答错误数',
  answer_last_result int(4)  COMMENT '最近结果是否正确0正确 1错误',
  answer_count BIGINT(25) NOT NULL DEFAULT 0  COMMENT '回答总数'
) COMMENT '用户回答的情况';



