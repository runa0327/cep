package com.cisdi.pms.job.serviceImpl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.cisdi.pms.job.domain.BriskUser;
import com.cisdi.pms.job.domain.BriskUserExportModel;

import com.cisdi.pms.job.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate myJdbcTemplate;

    @Override
    public List<BriskUserExportModel> briskUserExport(BriskUser briskUser) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());
        //开始时间
        String startTime = briskUser.getStartTime();
        if (StringUtils.isEmpty(startTime)){
            startTime = "2022-11-22 00:00:00";
        }
        //结束日期
        String endTime = briskUser.getEndTime();
        if (StringUtils.isEmpty(endTime)){
            endTime = now;
        }

        List<Map<String, Object>> originList = myJdbcTemplate.queryForList("select l.AD_USER_ID userId,u.name userName,max(l.LOGIN_DATETIME) lastLoginDate," +
                "count(*) loginNum,max(d.name) deptName,IFNULL(max(ft.initiateProcessNum),0) initiateProcessNum,IFNULL(max(lt.handleProcessNum),0) handleProcessNum\n" +
                "from main.ad_login l\n" +
                "left join ad_user u on u.id = l.AD_USER_ID\n" +
                "left join hr_dept_user du on du.AD_USER_ID = u.id\n" +
                "left join hr_dept d on d.id = du.HR_DEPT_ID\n" +
                "left join (select count(*) initiateProcessNum,ta.AD_USER_ID from wf_task ta where ta.status = 'AP' and ta" +
                ".IS_PROC_INST_FIRST_TODO_TASK = 1 group by ta.AD_USER_ID) ft on ft.AD_USER_ID = u.id\n" +
                "left join (select count(*) handleProcessNum,ta.AD_USER_ID from wf_task ta where ta.status = 'AP' and ta" +
                ".IS_USER_LAST_CLOSED_TODO_TASK = 1 group by ta.AD_USER_ID) lt on lt.AD_USER_ID = u.id\n" +
                "where u.name is not null\n" +
                "and l.LOGIN_DATETIME >= ? and l.LOGIN_DATETIME <= ?\n" +
                "group by l.AD_USER_ID order by loginNum desc", startTime, endTime);
        List<BriskUserExportModel> models = originList.stream().map(userMap -> {
            BriskUserExportModel model = JSONObject.parseObject(JSONObject.toJSONString(userMap), BriskUserExportModel.class);
            model.lastLoginDate = com.cisdi.pms.job.utils.StringUtils.replaceCode(model.lastLoginDate,"T"," ");
            return model;
        }).collect(Collectors.toList());
        return models;
    }
}
