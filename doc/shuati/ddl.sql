 drop table  question;
 CREATE TABLE question (
  id BIGINT(25) PRIMARY KEY AUTO_INCREMENT,
  question_desc VARCHAR(2000) NOT NULL COMMENT '问题描述',
  question_type int(4) COMMENT '问题描述',
  option1 VARCHAR(500) NOT NULL COMMENT '选项1',
  option2 VARCHAR(500) NOT NULL COMMENT '选项2',
  option3 VARCHAR(500)  COMMENT '选项3',
  option4 VARCHAR(500)  COMMENT '选项4',
  option5 VARCHAR(500)  COMMENT '选项5',
  option6 VARCHAR(500)  COMMENT '选项6',
  answer_error_count BIGINT(25) NOT NULL DEFAULT 0 COMMENT '回答错误数',
  answerCount BIGINT(25) NOT NULL DEFAULT 0  COMMENT '回答总数'
);