package com.cisdi.ext.home;

import com.cisdi.ext.pm.PmChangeExt;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title WorkScheduleExt
 * @package com.cisdi.ext.home
 * @description 工作日程
 * @date 2023/2/8
 */
public class WorkScheduleExt {


    /**
     * 工程日程查询
     */
    public void scheduleInit() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String userId = String.valueOf(map.get("userId"));
        String dateTime = String.valueOf(map.get("dateTime"));
        if (StringUtils.isEmpty(dateTime)) {
            dateTime = String.valueOf(new Date());
        }
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select DATE_FORMAT(CRT_DT,'%Y-%m-%d') date_time ,count(TITLE) count from " +
                "WORK_SCHEDULE where DATE_FORMAT(CRT_DT,'%Y-%m') =? and AD_USER_ID=? group by DATE_FORMAT(CRT_DT,'%Y-%m-%d')", dateTime, userId);
        List<ScheduleData> dataList = list.stream().map(p -> {
            ScheduleData data = new ScheduleData();
            data.dateTime = JdbcMapUtil.getString(p, "date_time");
            data.count = JdbcMapUtil.getString(p, "count");
            return data;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(dataList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide resData = new OutSide();
            resData.dataList = dataList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 工作日程详情列表
     */
    public void workScheduleView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String dateTime = String.valueOf(map.get("dateTime"));
        String userId = String.valueOf(map.get("userId"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ws.ID as ID,ws.CRT_DT as CRT_DT,TITLE,CONTENT,URGENT_LEVEL,WARN_TYPE_ID,gsv.`name` as WARN_TYPE from WORK_SCHEDULE ws" +
                " left join gr_set_value gsv on gsv.id = ws.WARN_TYPE_ID "+
                " where DATE_FORMAT(ws.CRT_DT,'%Y-%m-%d') = DATE_FORMAT(?,'%Y-%m-%d') and ws.AD_USER_ID=?", dateTime, userId);
        List<WorkScheduleData> scheduleData = list.stream().map(p -> {
            WorkScheduleData schedule = new WorkScheduleData();
            schedule.id = JdbcMapUtil.getString(p, "ID");
            schedule.dateTime = StringUtil.withOutT(JdbcMapUtil.getString(p, "CRT_DT"));
            schedule.title = JdbcMapUtil.getString(p, "TITLE");
            schedule.content = JdbcMapUtil.getString(p, "CONTENT");
            schedule.level = JdbcMapUtil.getString(p, "URGENT_LEVEL");
            schedule.warnTypeId = JdbcMapUtil.getString(p, "WARN_TYPE_ID");
            schedule.warnType = JdbcMapUtil.getString(p, "WARN_TYPE");
            return schedule;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(scheduleData)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide resData = new OutSide();
            resData.scheduleData = scheduleData;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 添加/编辑日程
     */
    public void scheduleModify() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String dateTime = String.valueOf(map.get("dateTime"));
        String title = String.valueOf(map.get("title"));
        String content = String.valueOf(map.get("content"));
        String level = String.valueOf(map.get("level"));
        String warnTypeId = String.valueOf(map.get("warnTypeId"));
        String userId = String.valueOf(map.get("userId"));
        String id = "";
        if (StringUtils.isEmpty(map.get("id"))) {
            id = Crud.from("WORK_SCHEDULE").insertData();
        }else{
            id = String.valueOf(map.get("id"));
        }
        Crud.from("WORK_SCHEDULE").where().eq("ID", id).update()
                .set("AD_USER_ID", userId).set("CRT_DT", dateTime).set("TITLE", title).set("CONTENT", content).set("WARN_TYPE_ID", warnTypeId)
                .set("URGENT_LEVEL", level).exec();
        //TODO 新增记录到本周工作任务
    }

    /**
     * 删除工作日程
     */
    public void delWorkSchedule() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("id"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("delete from WORK_SCHEDULE where id = ?", id);
    }


    public static class ScheduleData {
        public String dateTime;
        public String count;
    }

    public static class WorkScheduleData {
        public String id;
        public String dateTime;
        public String title;
        public String content;
        public String level;
        public String warnTypeId;
        public String warnType;
    }

    public static class OutSide {
        public List<ScheduleData> dataList;

        public List<WorkScheduleData> scheduleData;
    }
}
