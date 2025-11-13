package com.site.blog.my.core.controller.kaoqin;

import lombok.Data;
import lombok.ToString;

/**
 * to do
 *
 * @author Hj
 * @date 2025/6/25
 */
@Data
@ToString
public class OABean {
     private String subcompanyid;
     private String subcompanyname;
     private String departmentid;
     private String departmentname;
     private String hrmid;
     private String hrmname;
     private String scheduledate;
     private String scheduleweek;
     private String scheduletype;
     private String timeslot;
     private String beginsigntime;
     private String endsigntime;
     private String kqrequest;
     private String auditstatus;
}
