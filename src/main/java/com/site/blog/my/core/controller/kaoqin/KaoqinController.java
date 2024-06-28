package com.site.blog.my.core.controller.kaoqin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.site.blog.my.core.entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Controller
@Slf4j
@CrossOrigin("*")
public class KaoqinController {
    public static String theme = "amaze";

    public static Map<String, OABean> params = new HashMap<>();

    //"beginNum=21&endNum=40&beginDate=2024-06-01&endDate=2024-06-30&status=&subcompanyId=&departmentId=&hrmId=1378"
    @GetMapping("/kaoqin")
    @ResponseBody
    public List<OABean> kaoqin(@RequestParam("hrmId") Long hrmId, @RequestParam("offset") Integer offset) {
        log.info("hrmId:{}", hrmId);
        List<OABean> result1;
        result1 = getOaBeans(hrmId, offset);
        log.info("result1:{}", result1);
        return result1;

    }

    public static List<OABean> getOaBeans(Long hrmId, Integer offset) {
        List<OABean> result1 ;
        String todayDate = DateUtil.date().toString("yyyy-MM-dd");
        String todayDateTime = DateUtil.date().toString("yyyy-MM-dd HH:mm:ss");
        String offDateTime = DateUtil.date().toString("yyyy-MM-dd 18:00:00");


        if(params.get(todayDate) != null){
            OABean oaBean = params.get(todayDate);

            if(todayDateTime.compareTo(offDateTime)<0){
                if(!oaBean.getBeginsigntime().contains("---")){
                    result1 = new ArrayList<>();
                    log.info("查缓存1.........");
                    params.keySet().stream().sorted().forEach(key -> result1.add(params.get(key)));
                }else {
                    log.info("查oa1.........");
                    result1=sendRequest(hrmId, offset);
                }
            }else {
                if(oaBean.getEndsigntime().contains("---")
                        ||oaBean.getEndsigntime().contains("08:")
                        ||oaBean.getEndsigntime().contains("09:")
                        ||oaBean.getEndsigntime().contains("10:")
                        ||oaBean.getEndsigntime().contains("11:")
                        ||oaBean.getEndsigntime().contains("12:")
                        ||oaBean.getEndsigntime().contains("13:")
                        ||oaBean.getEndsigntime().contains("14:")
                ){
                    log.info("查oa3.........");
                    result1=sendRequest(hrmId, offset);

                }else {
                    result1 = new ArrayList<>();
                    log.info("查缓存2.........");
                    params.keySet().stream().sorted().forEach(key -> result1.add(params.get(key)));
                }
            }

        }else{
            log.info("查oa4.........");
            result1=sendRequest(hrmId, offset);
        }
        return result1;
    }

    private static List<OABean> sendRequest(Long hrmId, Integer offset) {
        log.info("查oa");
        String mm = DateUtil.date().offset(DateField.MONTH, offset).toString("MM");
        String lastMonth = DateUtil.date().offset(DateField.MONTH, -1).toString("MM");
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("beginNum", "1");
        paramMap.put("endNum", "40");
        paramMap.put("beginDate", "2024-" + mm + "-01");
        mm = DateUtil.date().offset(DateField.MONTH, offset + 1).toString("MM");
        paramMap.put("endDate", "2024-" + mm + "-01");
        paramMap.put("hrmId", hrmId);
        String result = HttpUtil.post("http://oa.dongyinghk.com:8000/custom/hrm/action/gethrmschedule.jsp", paramMap);
        log.info("result:{}", result);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        List<OABean> result1 = BeanUtil.copyToList(jsonObject.getJSONArray("datas"), OABean.class);
        result1.forEach(row -> {
            if (!StringUtils.isEmpty(row.getKqrequest())) {
                row.setKqrequest(row.getKqrequest().substring(0, 2)); //换行();
            }
            params.put(row.getScheduledate(), row);
        });
        params.keySet().stream().sorted().forEach(key -> {
            if (key.compareTo("2024-" + lastMonth + "-01") < 0) {
                params.remove(key);
            }
        });
        return result1;
    }

    @GetMapping("/kaoqin22")
    public String kaoqin22() {
        log.info("kaoqin22");
        return "blog/" + theme + "/jquerydemo02";
    }

}
