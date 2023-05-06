package com.cisdi.ext.weeklyReport;

import com.cisdi.ext.file.BaseFileExt;
import com.cisdi.ext.model.PmProgressWeeklyPrj;
import com.cisdi.ext.model.view.project.PmPrjView;
import com.cisdi.ext.model.view.weekReport.WeekMessage;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 形象进度工程周报
 */
public class ProgressWeekReport {

    /**
     * 形象进度工程周报-填写-项目列表
     */
    public void getWritePrjList(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PmPrjView param = JsonUtil.fromJson(json,PmPrjView.class);
        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;

        //获取当前登录用户
        String userId = ExtJarHelper.loginInfo.get().userId;
        String projectName = param.projectName;

        String sql1 = "select distinct a.pm_prj_id,c.name,ifnull(c.IZ_START_REQUIRE,'1') as weatherStart,ifnull(c.IZ_END,'0') as weatherCompleted f" +
                "rom PM_ROSTER a left join POST_INFO b on a.POST_INFO_ID = b.id LEFT JOIN pm_prj c on a.PM_PRJ_ID = c.id " +
                "where b.code = 'AD_USER_TWENTY_THREE_ID' and a.AD_USER_ID = ? and a.status = 'ap'";
        StringBuilder sb = new StringBuilder(sql1);
        if (!SharedUtil.isEmptyString(projectName)){
            sb.append(" and c.name like ('%").append(projectName).append("%') ");
        }
        StringBuilder sb2 = new StringBuilder(sb);
        sb.append(" order by c.IZ_START_REQUIRE desc,c.IZ_END asc,a.pm_prj_id desc").append(limit);
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sb.toString(),userId);
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sb2.toString(),userId);
        if (!CollectionUtils.isEmpty(list1)){
            Map<String, Object> map1 = new HashMap<>();
            List<PmPrjView> list = list1.stream().map(p->{
                PmPrjView pmPrjView = new PmPrjView();
                pmPrjView.id = JdbcMapUtil.getString(p,"pm_prj_id");
                pmPrjView.projectName = JdbcMapUtil.getString(p,"name");
                pmPrjView.weatherStart = JdbcMapUtil.getInt(p,"weatherStart");
                pmPrjView.weatherCompleted = JdbcMapUtil.getInt(p,"weatherCompleted");
                return pmPrjView;
            }).collect(Collectors.toList());
            map1.put("result", list);
            map1.put("total", list2.size());
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map1), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(null);
        }
    }

    /**
     * 形象进度工程周报-填写-根据项目带出上次填写记录
     */
    public void getLastMessageByPrj(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        WeekMessage param = JsonUtil.fromJson(json,WeekMessage.class);
        String projectId = param.projectId;
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("项目id信息不能为空！");
        }
        //判断该项目是否是第一次填写
        String sql = "";
        String weekId = "";
        Date now = new Date();
        Map<String,Object> dateMap = DateTimeUtil.getDateWeekMap(now);
        List<PmProgressWeeklyPrj> progressPrj = PmProgressWeeklyPrj.selectByWhere(new Where()
                .eq(PmProgressWeeklyPrj.Cols.PM_PRJ_ID,projectId)
                .eq(PmProgressWeeklyPrj.Cols.FROM_DATE,dateMap.get("startDate"))
                .eq(PmProgressWeeklyPrj.Cols.TO_DATE,dateMap.get("endDate")).eq(PmProgressWeeklyPrj.Cols.STATUS,"AP"));
        if (!CollectionUtils.isEmpty(progressPrj)){
            weekId = progressPrj.get(0).getPmProgressWeeklyId();
            sql = "select a.id,a.DATE as writeDate,A.VISUAL_PROGRESS AS progress,A.VISUAL_PROGRESS_DESCRIBE AS progressDescribe,A.PROCESS_REMARK_TEXT AS progressWeek," +
                    "a.TEXT_REMARK_ONE as progressRemark,c.SYS_TRUE as weatherStart,c.IS_END as weatherCompleted,'new' as dataType " +
                    "a.PM_PROGRESS_WEEKLY_ID as weekId,a.PM_PROGRESS_WEEKLY_PRJ as weekPrjId,b.PM_PRJ_ID as projectId,b.IZ_WRITE as izWrite " +
                    "from pm_progress_weekly_prj_detail a left join PM_PROGRESS_WEEKLY_PRJ b on a.PM_PROGRESS_WEEKLY_PRJ = b.id " +
                    "where a.STATUS = 'ap' and b.status = 'ap' and pm_prj_id = ? and a.PM_PROGRESS_WEEKLY_ID = '"+weekId+"' and b.PM_PROGRESS_WEEKLY_ID = '"+weekId+"' ";
        }
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,projectId);
        if (!CollectionUtils.isEmpty(list)){
            Map<String, Object> map1 = new HashMap<>();
            List<WeekMessage> returnList = list.stream().map(p->{
                WeekMessage weekMessage = new WeekMessage();
                weekMessage.id = JdbcMapUtil.getString(p,"id"); //id
                weekMessage.writeDate = JdbcMapUtil.getString(p,"writeDate"); //填报日期
                weekMessage.progress = SharedUtil.isEmptyString(JdbcMapUtil.getString(p,"progress")) ? 0:JdbcMapUtil.getInt(p,"progress"); //填报日期
                weekMessage.progressDescribe = JdbcMapUtil.getString(p,"progressDescribe"); //累计形象进度说明
                weekMessage.progressWeek = JdbcMapUtil.getString(p,"progressWeek"); //本周项目进展
                weekMessage.progressRemark = JdbcMapUtil.getString(p,"progressRemark"); //备注说明
                weekMessage.weatherCompleted = JdbcMapUtil.getInt(p,"weatherCompleted"); // 是否已竣工
                weekMessage.weatherStart = JdbcMapUtil.getInt(p,"weatherStart"); // 是否符合开工条件
                String fileId = JdbcMapUtil.getString(p,"fileId");
                if (!SharedUtil.isEmptyString(fileId)){
                    weekMessage.fileList =BaseFileExt.getFile(fileId);
                }
                return weekMessage;
            }).collect(Collectors.toList());
            map1.put("result", returnList);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map1), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(null);
        }
    }

    /**
     * 形象进度工程周报-填写-修改
     */
    public void updateProgressWeek(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        WeekMessage param = JsonUtil.fromJson(json,WeekMessage.class);
        String id = param.id;
        String dataType = param.dataType;
        String writeDate = param.writeDate; //填报日期
        String weekPrjId = param.weekPrjId; //进度周报-周项目信息id
        if (SharedUtil.isEmptyString(id)){
            throw new BaseException("记录id不能为空！");
        }
        if (SharedUtil.isEmptyString(dataType)){
            throw new BaseException("数据来源级别不能为空！");
        }
        if (SharedUtil.isEmptyString(writeDate)){
            throw new BaseException("填报日期不能为空！");
        }
        if ("old".equals(dataType)){
            id = Crud.from("PM_PROGRESS_WEEKLY_PRJ_DETAIL").insertData();
        }
        String start = writeDate.substring(0,9);
        String end = writeDate.substring(11,20);
        //数据保存
        Crud.from("PM_PROGRESS_WEEKLY_PRJ_DETAIL").where().eq("id",id).update()
                .set("DATE",writeDate).set("PM_PRJ_ID",param.projectId)
                .set("VISUAL_PROGRESS",param.progress).set("PROCESS_REMARK_TEXT",param.progressWeek)
                .set("VISUAL_PROGRESS_DESCRIBE",param.progressDescribe).set("FILE_ID_ONE",param.fileId)
                .set("TEXT_REMARK_ONE",param.progressRemark).set("SYS_TRUE",param.weatherStart)
                .set("IS_END",param.weatherCompleted).set("FROM_DATE",start).set("TO_DATE",end)
                .set("PM_PROGRESS_WEEKLY_ID",param.weekId).set("PM_PROGRESS_WEEKLY_PRJ",weekPrjId)
                .exec();
        //主表更新，该项目该周已更新
        Crud.from("PM_PROGRESS_WEEKLY_PRJ").where().eq("id",weekPrjId).update().set("IZ_WRITE",1).exec();
    }

    /**
     * 形象进度工程周报-填写-符合开工条件/是否竣工 关闭/打开
     */
    public void openOrClose(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        WeekMessage param = JsonUtil.fromJson(json,WeekMessage.class);
        Integer buttonType = param.buttonType;
        Integer buttonStatus = param.buttonStatus;
        String projectId = param.projectId;
        String weekPrjId = param.weekPrjId;
        String id = param.id;
        Map<String,Object> dateMap = new HashMap<>();
        if ( buttonType == null ){
            throw new BaseException("按钮类型名称不能为空");
        }
        if ( buttonStatus == null){
            throw new BaseException("按照状态不能为空");
        }
        if (buttonType == 1){ //处理开工条件
            Crud.from("PM_PRJ").where().eq("id",projectId).update().set("IZ_START_REQUIRE",buttonStatus).exec();
            Crud.from("PM_PROGRESS_WEEKLY_PRJ").where().eq("id",weekPrjId).update().set("SYS_TRUE",buttonStatus).exec();
            Crud.from("PM_PROGRESS_WEEKLY_PRJ_DETAIL").where().eq("id",id).update().set("SYS_TRUE",buttonStatus).exec();
        } else { //处理竣工条件
            Crud.from("PM_PRJ").where().eq("id",projectId).update().set("IZ_END",buttonStatus).exec();
            Crud.from("PM_PROGRESS_WEEKLY_PRJ").where().eq("id",weekPrjId).update().set("IZ_END",buttonStatus).exec();
            Crud.from("PM_PROGRESS_WEEKLY_PRJ_DETAIL").where().eq("id",id).update().set("IZ_END",buttonStatus).exec();
        }
    }

    /**
     * 形象进度工程周报-填写-填报记录-项目列表
     */
    public void getUserHistory(){
        String userId = ExtJarHelper.loginInfo.get().userId;
    }
}
