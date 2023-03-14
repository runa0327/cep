package com.pms.cisdipmswordtopdf.serviceImpl;

import com.pms.cisdipmswordtopdf.api.BriskUser;
import com.pms.cisdipmswordtopdf.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate myJdbcTemplate;

    @Override
    public List<BriskUser> briskUserImport(BriskUser briskUser) {
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

        List<BriskUser> list = new ArrayList<>();

        //查询时间范围内登录人信息
        String sql = "select a.AD_USER_ID,b.name,count(*) as num from main.ad_login a left join ad_user b on a.AD_USER_ID = b.id  where a.LOGIN_DATETIME >= ? and a.LOGIN_DATETIME <= ? and b.name is not null GROUP BY a.AD_USER_ID ORDER BY num desc ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql,startTime,endTime);
        if (!CollectionUtils.isEmpty(list1)){
            for (Map<String, Object> tmp1 : list1) {
                BriskUser briskUser1 = new BriskUser();
                briskUser1.setUserName(tmp1.get("name").toString());
                briskUser1.setLoginNum(tmp1.get("num").toString());
                briskUser1.setId(tmp1.get("AD_USER_ID").toString());
                list.add(briskUser1);
            }
        }
        if (!CollectionUtils.isEmpty(list)){
            for (BriskUser tmp : list) {
                String userId = tmp.getId();
                //查询部门
                String sql2 = "SELECT a.name from hr_dept a left join hr_dept_user b on a.id = b.hr_dept_id where b.ad_user_id = ? and b.SYS_TRUE = 1 limit 1";
                String deptName = myJdbcTemplate.queryForMap(sql2,userId).get("name").toString();
                //查询最近登录时间
                String sql3 = "select LOGIN_DATETIME from main.ad_login where AD_USER_ID = ? order by LOGIN_DATETIME desc limit 1";
                String lastLoginTime = myJdbcTemplate.queryForMap(sql3,userId).get("LOGIN_DATETIME").toString();
                //查询累计发起流程
                String sql4 = "select count(*) as num from wf_task t where t.`STATUS`='AP' and t.IS_PROC_INST_FIRST_TODO_TASK=1 and ad_user_id = ?";
                String initiateProcessNum = myJdbcTemplate.queryForMap(sql4,userId).get("num").toString();
                //查询累计处理流程
                String sql5 = "select count(*) from wf_task t where t.`STATUS`='AP' and t.IS_USER_LAST_CLOSED_TODO_TASK=1 and ad_user_id = ?";
                String handleProcessNum = myJdbcTemplate.queryForMap(sql5,userId).get("num").toString();
                tmp.setDeptName(deptName);
                tmp.setHandleProcessNum(handleProcessNum);
                tmp.setInitiateProcessNum(initiateProcessNum);
                tmp.setLastLoginDate(lastLoginTime);
            }
        }
        return list;
    }
}
