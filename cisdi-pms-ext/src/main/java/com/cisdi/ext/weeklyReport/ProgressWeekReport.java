package com.cisdi.ext.weeklyReport;

import com.cisdi.ext.file.BaseFileExt;
import com.cisdi.ext.model.PmProgressWeekly;
import com.cisdi.ext.model.PmProgressWeeklyPrj;
import com.cisdi.ext.model.PmProgressWeeklyPrjDetail;
import com.cisdi.ext.model.view.project.PmPrjView;
import com.cisdi.ext.model.view.weekReport.PmProgressWeeklyView;
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

import java.math.BigDecimal;
import java.util.*;
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

        String sql1 = "select distinct a.pm_prj_id,c.name,ifnull(c.IZ_START_REQUIRE,'1') as weatherStart,ifnull(c.IZ_END,'0') as weatherCompleted " +
                "from PM_ROSTER a left join POST_INFO b on a.POST_INFO_ID = b.id LEFT JOIN pm_prj c on a.PM_PRJ_ID = c.id " +
                "where b.code = 'AD_USER_TWENTY_THREE_ID' and a.AD_USER_ID = ? and a.status = 'ap' " +
                "AND (c.PROJECT_STATUS != '1661568714048413696' or c.PROJECT_STATUS is null ) ";
        StringBuilder sb = new StringBuilder(sql1);
        if (!SharedUtil.isEmptyString(projectName)){
            sb.append(" and c.name like ('%").append(projectName).append("%') ");
        }
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sb.toString(),userId);
        sb.append(" order by weatherCompleted asc,weatherStart desc,a.pm_prj_id desc ").append(limit);
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sb.toString(),userId);
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
            sql = "select a.file_id_one as fileId,a.id,a.DATE as writeDate,A.VISUAL_PROGRESS AS progress,A.VISUAL_PROGRESS_DESCRIBE AS progressDescribe,A.PROCESS_REMARK_TEXT AS progressWeek," +
                    "a.TEXT_REMARK_ONE as progressRemark,b.SYS_TRUE as weatherStart,b.IZ_END as weatherCompleted,'new' as dataType, " +
                    "a.PM_PROGRESS_WEEKLY_ID as weekId,a.PM_PROGRESS_WEEKLY_PRJ_ID as weekPrjId,b.PM_PRJ_ID as projectId,b.IZ_WRITE as izWrite " +
                    "from pm_progress_weekly_prj_detail a left join PM_PROGRESS_WEEKLY_PRJ b on a.PM_PROGRESS_WEEKLY_PRJ_ID = b.id " +
                    "where a.STATUS = 'ap' and b.status = 'ap' and b.pm_prj_id = ? and a.PM_PROGRESS_WEEKLY_ID = '"+weekId+"' and b.PM_PROGRESS_WEEKLY_ID = '"+weekId+"' ";
            List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,projectId);
            if (!CollectionUtils.isEmpty(list)) {
                Map<String, Object> map1 = new HashMap<>();
                List<WeekMessage> returnList = list.stream().map(p -> {
                    WeekMessage weekMessage = new WeekMessage();
                    weekMessage.id = JdbcMapUtil.getString(p, "id"); //id
                    weekMessage.writeDate = JdbcMapUtil.getString(p, "writeDate"); //填报日期
                    String progress = JdbcMapUtil.getString(p, "progress"); //整体形象进度
                    if (SharedUtil.isEmptyString(progress)) {
                        weekMessage.progress = new BigDecimal(0);
                    } else {
                        weekMessage.progress = new BigDecimal(progress);
                    }
                    weekMessage.progressDescribe = JdbcMapUtil.getString(p, "progressDescribe"); //累计形象进度说明
                    weekMessage.progressWeek = JdbcMapUtil.getString(p, "progressWeek"); //本周项目进展
                    weekMessage.progressRemark = JdbcMapUtil.getString(p, "progressRemark"); //备注说明
                    weekMessage.weatherCompleted = JdbcMapUtil.getInt(p, "weatherCompleted"); // 是否已竣工
                    weekMessage.weatherStart = JdbcMapUtil.getInt(p, "weatherStart"); // 是否符合开工条件
                    weekMessage.izWrite = JdbcMapUtil.getInt(p, "izWrite"); // 是否填写
                    weekMessage.weekPrjId = JdbcMapUtil.getString(p, "weekPrjId"); //周项目id
                    weekMessage.projectId = JdbcMapUtil.getString(p, "projectId"); //项目id
                    weekMessage.weekId = JdbcMapUtil.getString(p, "weekId"); //周批次id
                    String fileId = JdbcMapUtil.getString(p, "fileId"); //文件id
                    if (!SharedUtil.isEmptyString(fileId)) {
                        weekMessage.fileId = fileId;
                        weekMessage.fileList = BaseFileExt.getFile(fileId);
                    }
                    return weekMessage;
                }).collect(Collectors.toList());
                map1.put("result", returnList);
                Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map1), Map.class);
                ExtJarHelper.returnValue.set(outputMap);
            } else {
                ExtJarHelper.returnValue.set(null);
            }
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
        String userId = ExtJarHelper.loginInfo.get().userId;
        String id = param.id;
        //判断是否可改
        List<PmProgressWeeklyPrjDetail> list1 = PmProgressWeeklyPrjDetail.selectByWhere(new Where().eq("ID",id));
        boolean isSubmit = list1.get(0).getIsWeeklyReportSubmit();
        if (isSubmit){
            throw new BaseException("本周周报已超过提交时间，不允许修改");
        }
        String writeDate = param.writeDate; //填报日期
        String weekPrjId = param.weekPrjId; //进度周报-周项目信息id
        if (SharedUtil.isEmptyString(id)){
            throw new BaseException("记录id不能为空！");
        }
        if (SharedUtil.isEmptyString(writeDate)){
            throw new BaseException("填报日期不能为空！");
        }
        String start = writeDate.substring(0,10);
        String end = writeDate.substring(11,21);
        String now = DateTimeUtil.dttmToString(new Date());
        //数据保存
        Crud.from("PM_PROGRESS_WEEKLY_PRJ_DETAIL").where().eq("id",id).update()
                .set("DATE",writeDate).set("PM_PRJ_ID",param.projectId)
                .set("VISUAL_PROGRESS",param.progress).set("PROCESS_REMARK_TEXT",param.progressWeek)
                .set("VISUAL_PROGRESS_DESCRIBE",param.progressDescribe).set("FILE_ID_ONE",param.fileId)
                .set("TEXT_REMARK_ONE",param.progressRemark).set("SYS_TRUE",param.weatherStart)
                .set("IZ_END",param.weatherCompleted).set("FROM_DATE",start).set("TO_DATE",end)
                .set("PM_PROGRESS_WEEKLY_ID",param.weekId).set("PM_PROGRESS_WEEKLY_PRJ_ID",weekPrjId)
                .set("AD_USER_ID",userId).set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",userId)
                .set("TS",now)
                .exec();
        //主表更新，该项目该周已更新
        Crud.from("PM_PROGRESS_WEEKLY_PRJ").where().eq("id",weekPrjId).update()
                .set("IZ_WRITE",1)
                .set("AD_USER_ID",userId).set("LAST_MODI_USER_ID",userId)
                .set("TS",now).set("LAST_MODI_DT",now)
                .exec();
    }

    /**
     * 形象进度工程周报-填写-符合开工条件/是否竣工 关闭/打开
     */
    public void openOrClose(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        WeekMessage param = JsonUtil.fromJson(json,WeekMessage.class);
        Integer buttonType = param.buttonType;
        Integer buttonStatus = param.buttonStatus;
        String projectId = param.projectId;
        String weekPrjId = param.weekPrjId;
        String id = param.id;
        if ( buttonType == null ){
            throw new BaseException("按钮类型名称不能为空");
        }
        if ( buttonStatus == null){
            throw new BaseException("按照状态不能为空");
        }
        if (buttonType == 0){ //处理开工条件
            Crud.from("PM_PRJ").where().eq("id",projectId).update().set("IZ_START_REQUIRE",buttonStatus).exec();
            Crud.from("PM_PROGRESS_WEEKLY_PRJ").where().eq("id",weekPrjId).update().set("SYS_TRUE",buttonStatus).exec();
            Crud.from("PM_PROGRESS_WEEKLY_PRJ_DETAIL").where().eq("id",id).update().set("SYS_TRUE",buttonStatus).exec();
        } else if (buttonType == 1){ //处理竣工条件
            Crud.from("PM_PRJ").where().eq("id",projectId).update().set("IZ_END",buttonStatus).exec();
            Crud.from("PM_PROGRESS_WEEKLY_PRJ").where().eq("id",weekPrjId).update().set("IZ_END",buttonStatus).exec();
            Crud.from("PM_PROGRESS_WEEKLY_PRJ_DETAIL").where().eq("id",id).update().set("IZ_END",buttonStatus).exec();
        }
    }

    /**
     * 形象进度工程周报-填写-填报记录-项目列表
     */
    public void getUserHistoryPrj(){
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
        String projectId = param.projectId;

        String sql1 = "select DISTINCT b.PM_PRJ_ID,c.name,ifnull(c.IZ_START_REQUIRE,'1') as weatherStart,ifnull(c.IZ_END,'0') as weatherCompleted " +
                "from pm_roster a left join pm_progress_weekly_prj b on a.PM_PRJ_ID = b.PM_PRJ_ID left join pm_prj c on b.PM_PRJ_ID = c.id " +
                "where a.status = 'ap' and b.status = 'ap' and b.IZ_WRITE = '1' and a.AD_USER_ID = ? and a.POST_INFO_ID = '1633997482885255168' " +
                "AND (c.PROJECT_STATUS != '1661568714048413696' or c.PROJECT_STATUS is null ) ";
        StringBuilder sb = new StringBuilder(sql1);
        if (!SharedUtil.isEmptyString(projectName)){
            sb.append(" and c.name like ('%").append(projectName).append("%') ");
        }
        if (!SharedUtil.isEmptyString(projectId)){
            sb.append(" and b.pm_prj_id = '").append(projectId).append("' ");
        }
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sb.toString(),userId);
        sb.append(" order by weatherCompleted asc,weatherStart desc,b.pm_prj_id desc ").append(limit);
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sb.toString(),userId);
        if (!CollectionUtils.isEmpty(list1)){
            Map<String, Object> map1 = new HashMap<>();
            List<PmPrjView> list = list1.stream().map(p->{
                PmPrjView pmPrjView = new PmPrjView();
                pmPrjView.id = JdbcMapUtil.getString(p,"PM_PRJ_ID");
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
     * 形象进度工程周报-填写-填报记录-单项目历史填报记录
     */
    public void selectPrjHistory(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        WeekMessage param = JsonUtil.fromJson(json,WeekMessage.class);
        String projectId = param.projectId;
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("项目id信息不能为空！");
        }
        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;

        String sql = "select a.id,c.DATE as writeDate,a.VISUAL_PROGRESS as progress,a.VISUAL_PROGRESS_DESCRIBE as progressDescribe,a.PROCESS_REMARK_TEXT as progressWeek," +
                "a.TEXT_REMARK_ONE as progressRemark,a.SYS_TRUE as weatherStart,a.IZ_END as weatherCompleted," +
                "a.AD_USER_ID as recordById,d.name as recordByName,a.FILE_ID_ONE as fileId,b.PM_PRJ_ID as projectId," +
                "(select name from pm_prj where id = b.id) as projectName " +
                "from PM_PROGRESS_WEEKLY_PRJ_DETAIL a left join PM_PROGRESS_WEEKLY_PRJ b on a.PM_PROGRESS_WEEKLY_PRJ_ID = b.id " +
                "LEFT JOIN pm_progress_weekly c on a.PM_PROGRESS_WEEKLY_ID = c.id " +
                "left join ad_user d on a.AD_USER_ID = d.id " +
                "where a.status = 'ap' and b.status = 'ap' and c.status = 'ap' and b.PM_PRJ_ID = ?";
        StringBuilder sb = new StringBuilder(sql);
        if (!SharedUtil.isEmptyString(param.writeDateMin)){
            sb.append(" and c.FROM_DATE >= '").append(param.writeDateMin).append("' ");
        }
        if (!SharedUtil.isEmptyString(param.writeDateMax)){
            sb.append(" and c.TO_DATE <= '").append(param.writeDateMax).append("' ");
        }
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(String.valueOf(sb),projectId);
        sb.append(" order by a.LAST_MODI_DT desc ").append(limit);
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sb.toString(),projectId);
        if (!CollectionUtils.isEmpty(list1)){
            Map<String, Object> map1 = new HashMap<>();
            List<WeekMessage> returnList = list1.stream().map(p->{
                WeekMessage weekMessage = new WeekMessage();
                weekMessage.id = JdbcMapUtil.getString(p,"id"); //id
                weekMessage.projectId = JdbcMapUtil.getString(p,"projectId"); //项目id
                weekMessage.projectName = JdbcMapUtil.getString(p,"projectName"); //项目名称
                weekMessage.writeDate = JdbcMapUtil.getString(p,"writeDate"); //填报时间
                weekMessage.weatherStart = JdbcMapUtil.getInt(p,"weatherStart"); //是否符合开工条件 1符合0不符合
                weekMessage.weatherCompleted = JdbcMapUtil.getInt(p,"weatherCompleted"); //是否竣工 1已竣工0未竣工
                String progress = JdbcMapUtil.getString(p,"progress"); //整体形象进度
                if (SharedUtil.isEmptyString(progress)){
                    weekMessage.progress = new BigDecimal(0);
                } else {
                    weekMessage.progress = new BigDecimal(progress);
                }
                weekMessage.progressDescribe = JdbcMapUtil.getString(p,"progressDescribe"); //累计形象进度说明
                weekMessage.progressWeek = JdbcMapUtil.getString(p,"progressWeek"); //本周项目进展
                weekMessage.progressRemark = JdbcMapUtil.getString(p,"progressRemark"); //备注说明
                weekMessage.recordById = JdbcMapUtil.getString(p,"recordById"); //记录人
                weekMessage.recordByName = JdbcMapUtil.getString(p,"recordByName"); //记录人
                String fileId = JdbcMapUtil.getString(p,"fileId"); //形象进度影像
                if (!SharedUtil.isEmptyString(fileId)){
                    weekMessage.fileList = BaseFileExt.getFile(fileId);
                }
                return weekMessage;
            }).collect(Collectors.toList());
            map1.put("result", returnList);
            map1.put("total", list2.size());
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map1), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(null);
        }
    }

    /**
     * 进度周报统计-周列表
     */
    public void getWeekList(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String,Object>> list = myJdbcTemplate.queryForList("select id,DATE,FROM_DATE,TO_DATE from PM_PROGRESS_WEEKLY where status = 'ap' order by FROM_DATE desc");
        if (!CollectionUtils.isEmpty(list)){
            Map<String,Object> dateMap = DateTimeUtil.getDateWeekMap(new Date());
            String startDate = dateMap.get("startDate").toString();
            String endDate = dateMap.get("endDate").toString();
            List<PmProgressWeeklyView> returnList = list.stream().map(p->{
                PmProgressWeeklyView pmProgressWeeklyView = new PmProgressWeeklyView();
                pmProgressWeeklyView.id = JdbcMapUtil.getString(p,"id"); //id
                pmProgressWeeklyView.weekId = JdbcMapUtil.getString(p,"id"); //id
                String writeDate = JdbcMapUtil.getString(p,"DATE"); //填报时间
                String start = JdbcMapUtil.getString(p,"FROM_DATE"); //开始日期
                String end = JdbcMapUtil.getString(p,"TO_DATE"); //开始日期
                if (startDate.equals(start) && endDate.equals(end)){
                    writeDate = writeDate + "（本周）";
                }
                pmProgressWeeklyView.writeDate = writeDate;
                return pmProgressWeeklyView;

            }).collect(Collectors.toList());
            Map<String,Object> map1 = new HashMap<>();
            map1.put("result", returnList);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map1), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(null);
        }
    }

    /**
     * 进度周报统计-详情列表页
     */
    public void getPrjWeekHistory(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        WeekMessage param = JsonUtil.fromJson(json,WeekMessage.class);
        String weekId = param.weekId;
        if (SharedUtil.isEmptyString(weekId)){
            throw new BaseException("周信息不能为空！");
        }if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;

        //查询该周项目总览情况
        Map<String,Object> canMap = getMapCan(weekId,limit);
        Map<String,Object> records = getRecords(canMap,param,myJdbcTemplate);
        Map<String,Object> map1 = getPrjWeekAll(records);
        if (records.isEmpty()){
            ExtJarHelper.returnValue.set(null);
        } else {
            Map<String,Object> returnMap = new HashMap<>();
            returnMap.put("header",map1);
            records.remove("all");
            returnMap.put("record",records);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(returnMap), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 查询数据集合
     * @param canMap 查询参数
     * @param myJdbcTemplate 数据源
     * @param param 请求参数
     * @return 查询结果
     */
    public Map<String,Object> getRecords(Map<String, Object> canMap, WeekMessage param, MyJdbcTemplate myJdbcTemplate) {
        String weekId = getCanMapValue("weekId",canMap); //周id
        String projectId = getCanMapValue("projectId",canMap); //项目id
        String projectName = param.projectName; //项目名称
        String writeDateMin = param.writeDateMin; //最小填写日期
        String writeDateMax = param.writeDateMax; //最大填写日期
        String manageUserName = param.manageUserName; //项目负责人
        BigDecimal progressMin = param.progressMin; //最小进度
        BigDecimal progressMax = param.progressMax; //最大进度
        Integer weatherStart = param.weatherStart; //是否符合开工条件
        Integer weatherCompleted = param.weatherCompleted; //是否竣工
        String limit = canMap.get("limit").toString();
        String sql = "select c.id as weekId,a.id,c.DATE as writeDate,a.VISUAL_PROGRESS as progress,a.VISUAL_PROGRESS_DESCRIBE as progressDescribe,a.PROCESS_REMARK_TEXT as progressWeek," +
                "a.TEXT_REMARK_ONE as progressRemark,ifnull(b.SYS_TRUE,'1') as weatherStart,ifnull(b.IZ_END,'0') as weatherCompleted," +
                "(select name from ad_user where id = a.ad_user_id) as recordByName,a.AD_USER_ID as recordById, " +
                "a.FILE_ID_ONE as fileId,b.PM_PRJ_ID as projectId," +
                "e.name as projectName,d.AD_USER_ID as manageUserId," +
                "f.name as manageUserName,b.IZ_WRITE as izWrite,b.id as weekPrjId " +
                "from PM_PROGRESS_WEEKLY_PRJ_DETAIL a " +
                "left join PM_PROGRESS_WEEKLY_PRJ b on a.PM_PROGRESS_WEEKLY_PRJ_ID = b.id " +
                "LEFT JOIN pm_progress_weekly c on b.PM_PROGRESS_WEEKLY_ID = c.id " +
                "left join (select ad_user_id,PM_PRJ_ID from pm_roster where post_info_id = '1633997482885255168' " +
                "and ad_user_id is not null GROUP BY PM_PRJ_ID,ad_user_id) d on b.pm_prj_id = d.PM_PRJ_ID " +
                "left join pm_prj e on b.pm_prj_id = e.id " +
                "LEFT JOIN ad_user f ON d.ad_user_id = f.id " +
                "where a.status = 'ap' and b.status = 'ap' and c.status = 'ap' and f.name is not null " +
                "AND (e.PROJECT_STATUS != '1661568714048413696' or e.PROJECT_STATUS is null ) ";
        StringBuilder sb = new StringBuilder(sql);
        if (!SharedUtil.isEmptyString(weekId)){
            sb.append(" and c.id = '").append(weekId).append("' "); // 周-批次id
        }
        if (!SharedUtil.isEmptyString(projectId)){
            sb.append(" and b.pm_prj_id = '").append(projectId).append("' "); //项目id
        }
        if (!SharedUtil.isEmptyString(projectName)){
            sb.append(" and e.name like ('%").append(projectName).append("%') "); //项目名称
        }
        if (!SharedUtil.isEmptyString(writeDateMin)){
            sb.append(" and c.FROM_DATE >= '").append(writeDateMin).append("' "); //最小填写日期
        }
        if (!SharedUtil.isEmptyString(writeDateMax)){
            sb.append(" and c.TO_DATE <= '").append(writeDateMax).append("' "); //最大填写日期
        }
        if (!SharedUtil.isEmptyString(manageUserName)){
            sb.append(" and f.name like ('%").append(manageUserName).append("%') "); //项目负责人
        }
        if (!SharedUtil.isEmptyString(param.manageUserId)){
            sb.append(" and d.ad_user_id = '").append(param.manageUserId).append("' "); //项目负责人
        }
        if (progressMin != null){
            sb.append(" and a.VISUAL_PROGRESS >= '").append(progressMin).append("' "); //最小进度
        }
        if (progressMax != null){
            sb.append(" and a.VISUAL_PROGRESS <= '").append(progressMax).append("' "); //最大进度
        }
        if (weatherStart != null){
            sb.append(" and b.SYS_TRUE = '").append(weatherStart).append("' "); //是否符合开工条件
        }
        if (weatherCompleted != null){
            sb.append(" and b.IZ_END = '").append(weatherCompleted).append("' "); //是否竣工
        }
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(String.valueOf(sb));
        sb.append(" order by b.IZ_END asc,b.SYS_TRUE desc,a.ts desc,b.PM_PRJ_ID desc ").append(limit);
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sb.toString());
        Map<String,Object> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(list1)){
            List<WeekMessage> returnList = getWeekMessageList(list1);
            List<WeekMessage> allList = getWeekMessageList(list2);
            map.put("total",list2.size());
            map.put("all",allList);
            map.put("result", returnList);
        }
        return map;
    }

    /**
     * Map数据转实体对象
     * @param list1 查询Map数据
     * @return 转换结果
     */
    private List<WeekMessage> getWeekMessageList(List<Map<String, Object>> list1) {
        return list1.stream().map(p->{
            WeekMessage weekMessage = new WeekMessage();
            weekMessage.id = JdbcMapUtil.getString(p,"id"); //id
            weekMessage.writeDate = JdbcMapUtil.getString(p,"writeDate"); //填报日期
            String progress = JdbcMapUtil.getString(p,"progress"); //整体形象进度
            if (SharedUtil.isEmptyString(progress)){
                weekMessage.progress = new BigDecimal(0);
            } else {
                weekMessage.progress = new BigDecimal(progress);
            }
            weekMessage.progressDescribe = JdbcMapUtil.getString(p,"progressDescribe"); //累计形象进度说明
            weekMessage.progressWeek = JdbcMapUtil.getString(p,"progressWeek"); //本周项目进展
            weekMessage.progressRemark = JdbcMapUtil.getString(p,"progressRemark"); //备注说明
            weekMessage.weatherCompleted = JdbcMapUtil.getInt(p,"weatherCompleted"); // 是否已竣工
            weekMessage.weatherStart = JdbcMapUtil.getInt(p,"weatherStart"); // 是否符合开工条件
            weekMessage.izWrite = JdbcMapUtil.getInt(p,"izWrite"); // 是否填写
            weekMessage.manageUserId = JdbcMapUtil.getString(p,"manageUserId"); // 项目负责人
            weekMessage.manageUserName = JdbcMapUtil.getString(p,"manageUserName"); // 项目负责人
            weekMessage.recordById = JdbcMapUtil.getString(p,"recordById"); // 记录人
            weekMessage.recordByName = JdbcMapUtil.getString(p,"recordByName"); // 记录人
            weekMessage.projectId = JdbcMapUtil.getString(p,"projectId"); // 项目id
            weekMessage.projectName = JdbcMapUtil.getString(p,"projectName"); // 项目名称
            weekMessage.weekPrjId = JdbcMapUtil.getString(p,"weekPrjId"); // 项目周id
            weekMessage.weekId = JdbcMapUtil.getString(p,"weekId"); // 周id
            String fileId = JdbcMapUtil.getString(p,"fileId");
            if (!SharedUtil.isEmptyString(fileId)){
                weekMessage.fileList =BaseFileExt.getFile(fileId);
            }
            return weekMessage;
        }).collect(Collectors.toList());
    }

    /**
     * 获取参数值
     * @param str 需要查询的参数键
     * @param canMap 参数集合
     * @return 参数值
     */
    public String getCanMapValue(String str, Map<String, Object> canMap) {
        String value = "";
        for (String tmp : canMap.keySet()){
            if (str.equals(tmp)){
                value = canMap.get(tmp).toString();
            }
        }
        return value;
    }

    /**
     * 根据周id查询该周项目填报总情况
     * @param records 结果集
     * @return 周总览信息
     */
    public Map<String, Object> getPrjWeekAll(Map<String,Object> records) {
        Map<String,Object> map = new HashMap<>();
        if (!records.isEmpty()){
            List<WeekMessage> list = (List<WeekMessage>) records.get("all");
            //填报数
            int writes = (int) list.stream().filter(p->p.getIzWrite() == 1).count();
            //不符合开工条件
            int noStarts = (int) list.stream().filter(p-> p.getWeatherStart() == 0).count();
            //已竣工
            int completes = (int) list.stream().filter(p->p.getWeatherCompleted() == 1).count();
            map.put("projectNums",list.size());
            map.put("writes",writes);
            map.put("noStarts",noStarts);
            map.put("completes",completes);
        } else {
            map.put("projectNums",0);
            map.put("writes",0);
            map.put("noStarts",0);
            map.put("completes",0);
        }
        return map;
    }


    /**
     * 封装查询参数
     * @param weekId 周id
     * @param limit 限制语句
     * @return 参数集合
     */
    public Map<String, Object> getMapCan(String weekId, String limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("weekId",weekId);
        map.put("limit",limit);
        return map;
    }

    /**
     * 形象进度周报统计-统计-修改
     */
    public void updatePrjHistory(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        WeekMessage param = JsonUtil.fromJson(json,WeekMessage.class);
        String userId = ExtJarHelper.loginInfo.get().userId;
        String id = param.id;
        String writeDate = param.writeDate; //填报日期
        String weekPrjId = param.weekPrjId; //进度周报-周项目信息id
        if (SharedUtil.isEmptyString(id)){
            throw new BaseException("记录id不能为空！");
        }
        if (SharedUtil.isEmptyString(writeDate)){
            throw new BaseException("填报日期不能为空！");
        }
        String start = writeDate.substring(0,10);
        String end = writeDate.substring(11,21);
        String now = DateTimeUtil.dttmToString(new Date());
        //数据保存
        Crud.from("PM_PROGRESS_WEEKLY_PRJ_DETAIL").where().eq("id",id).update()
                .set("DATE",writeDate).set("PM_PRJ_ID",param.projectId)
                .set("VISUAL_PROGRESS",param.progress).set("PROCESS_REMARK_TEXT",param.progressWeek)
                .set("VISUAL_PROGRESS_DESCRIBE",param.progressDescribe).set("FILE_ID_ONE",param.fileId)
                .set("TEXT_REMARK_ONE",param.progressRemark).set("SYS_TRUE",param.weatherStart)
                .set("IZ_END",param.weatherCompleted).set("FROM_DATE",start).set("TO_DATE",end)
                .set("PM_PROGRESS_WEEKLY_ID",param.weekId).set("PM_PROGRESS_WEEKLY_PRJ_ID",weekPrjId)
                .set("AD_USER_ID",userId).set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",userId)
                .set("TS",now)
                .exec();
        //主表更新，该项目该周已更新
        Crud.from("PM_PROGRESS_WEEKLY_PRJ").where().eq("id",weekPrjId).update()
                .set("IZ_WRITE",1)
                .set("AD_USER_ID",userId).set("LAST_MODI_USER_ID",userId)
                .set("TS",now).set("LAST_MODI_DT",now)
                .exec();
    }

    /**
     * 形象进度周报统计-单个详情
     */
    public void progressOneDetail(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        WeekMessage param = JsonUtil.fromJson(json,WeekMessage.class);
        String id = param.getId();
        if (SharedUtil.isEmptyString(id)){
            throw new BaseException("id不能为空！");
        }
        String sql = "select c.id as weekId,a.id,c.DATE as writeDate,a.VISUAL_PROGRESS as progress,a.VISUAL_PROGRESS_DESCRIBE as progressDescribe,a.PROCESS_REMARK_TEXT as progressWeek," +
                "a.TEXT_REMARK_ONE as progressRemark,ifnull(b.SYS_TRUE,'1') as weatherStart,ifnull(b.IZ_END,'0') as weatherCompleted," +
                "(select name from ad_user where id = a.ad_user_id) as recordByName,a.AD_USER_ID as recordById, " +
                "a.FILE_ID_ONE as fileId,b.PM_PRJ_ID as projectId," +
                "e.name as projectName,d.AD_USER_ID as manageUserId," +
                "f.name as manageUserName,b.IZ_WRITE as izWrite,b.id as weekPrjId " +
                "from PM_PROGRESS_WEEKLY_PRJ_DETAIL a " +
                "left join PM_PROGRESS_WEEKLY_PRJ b on a.PM_PROGRESS_WEEKLY_PRJ_ID = b.id " +
                "LEFT JOIN pm_progress_weekly c on b.PM_PROGRESS_WEEKLY_ID = c.id " +
                "left join (select ad_user_id,PM_PRJ_ID from pm_roster where post_info_id = '1633997482885255168' " +
                "and ad_user_id is not null GROUP BY PM_PRJ_ID,ad_user_id) d on b.pm_prj_id = d.PM_PRJ_ID " +
                "left join pm_prj e on b.pm_prj_id = e.id " +
                "LEFT JOIN ad_user f ON d.ad_user_id = f.id " +
                "where a.id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,id);
        if (CollectionUtils.isEmpty(list)){
            ExtJarHelper.returnValue.set(null);
        } else {
            WeekMessage weekMessage = new WeekMessage();
            for (Map<String, Object> tmp : list) {
                weekMessage.setId(JdbcMapUtil.getString(tmp,"id"));
                weekMessage.setIzWrite(JdbcMapUtil.getInt(tmp,"izWrite"));
                weekMessage.setWeatherCompleted(JdbcMapUtil.getInt(tmp,"weatherCompleted"));
                weekMessage.setWeatherStart(JdbcMapUtil.getInt(tmp,"weatherStart"));
                weekMessage.setManageUserId(JdbcMapUtil.getString(tmp,"manageUserId"));
                weekMessage.setManageUserName(JdbcMapUtil.getString(tmp,"manageUserName"));
                weekMessage.setProgress(new BigDecimal(JdbcMapUtil.getString(tmp,"progress")));
                weekMessage.setProgressDescribe(JdbcMapUtil.getString(tmp,"progressDescribe"));
                weekMessage.setProgressRemark(JdbcMapUtil.getString(tmp,"progressRemark"));
                weekMessage.setProgressWeek(JdbcMapUtil.getString(tmp,"progressWeek"));
                weekMessage.setProjectId(JdbcMapUtil.getString(tmp,"projectId"));
                weekMessage.setProjectName(JdbcMapUtil.getString(tmp,"projectName"));
                weekMessage.setRecordById(JdbcMapUtil.getString(tmp,"recordById"));
                weekMessage.setRecordByName(JdbcMapUtil.getString(tmp,"recordByName"));
                weekMessage.setWeekId(JdbcMapUtil.getString(tmp,"weekId"));
                weekMessage.setWeekPrjId(JdbcMapUtil.getString(tmp,"weekPrjId"));
                weekMessage.setWriteDate(JdbcMapUtil.getString(tmp,"writeDate"));
                String fileId = JdbcMapUtil.getString(tmp,"fileId");
                if (!SharedUtil.isEmptyString(fileId)){
                    weekMessage.fileId = fileId;
                    weekMessage.fileList = BaseFileExt.getFile(fileId);
                }
            }
            Map<String,Object> returnMap = new HashMap<>();
            returnMap.put("result",weekMessage);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(returnMap), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 形象进度周报-将上周内容同步致本周
     */
    public void lastWeekToWeek(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //查询本周信息
        Map<String,Object> weekMap = DateTimeUtil.getDateWeekMap(new Date());
        String startDate = JdbcMapUtil.getString(weekMap,"startDate");
        String endDate = JdbcMapUtil.getString(weekMap,"endDate");
        //获取本周id
        List<PmProgressWeekly> weekList = PmProgressWeekly.selectByWhere(new Where()
                .eq(PmProgressWeekly.Cols.TO_DATE,endDate).eq(PmProgressWeekly.Cols.FROM_DATE,startDate));
        String weekId = weekList.get(0).getId();
        //获取上上周weekId
        String sql1 = "select id from pm_progress_weekly where id != ? order by FROM_DATE desc limit 1,1";
        String lastWeekId = JdbcMapUtil.getString(myJdbcTemplate.queryForMap(sql1,weekId),"id");
        //查询本周项目信息
        List<PmProgressWeeklyPrj> weeklyPrjList = PmProgressWeeklyPrj.selectByWhere(new Where()
                .eq(PmProgressWeeklyPrj.Cols.PM_PROGRESS_WEEKLY_ID,weekId));
        if (!CollectionUtils.isEmpty(weeklyPrjList)){
            for (PmProgressWeeklyPrj tmp : weeklyPrjList) {
                String weekPrjId = tmp.getId();
                //查询本周项目明细
                List<PmProgressWeeklyPrjDetail> prjDetail = PmProgressWeeklyPrjDetail.selectByWhere(new Where()
                        .eq(PmProgressWeeklyPrjDetail.Cols.PM_PROGRESS_WEEKLY_PRJ_ID,weekPrjId));
                if (!CollectionUtils.isEmpty(prjDetail)){
                    String id = prjDetail.get(0).getId();
                    String projectId = prjDetail.get(0).getPmPrjId();
                    BigDecimal progress = prjDetail.get(0).getVisualProgress();
                    String jinZhan = prjDetail.get(0).getProcessRemarkText();
                    String describle = prjDetail.get(0).getVisualProgressDescribe();
                    if (progress == null && SharedUtil.isEmptyString(jinZhan) && SharedUtil.isEmptyString(describle)){
                        //查询上上周的内容
                        List<PmProgressWeeklyPrjDetail> list2 = PmProgressWeeklyPrjDetail.selectByWhere(new Where()
                                .eq(PmProgressWeeklyPrjDetail.Cols.PM_PROGRESS_WEEKLY_ID,lastWeekId)
                                .eq(PmProgressWeeklyPrjDetail.Cols.PM_PRJ_ID,projectId));
                        if (!CollectionUtils.isEmpty(list2)){
                            Crud.from(PmProgressWeeklyPrjDetail.ENT_CODE).where().eq("ID",id).update()
                                    .set(PmProgressWeeklyPrjDetail.Cols.VISUAL_PROGRESS,list2.get(0).getVisualProgress())
                                    .set(PmProgressWeeklyPrjDetail.Cols.PROCESS_REMARK_TEXT,list2.get(0).getProcessRemarkText())
                                    .set(PmProgressWeeklyPrjDetail.Cols.VISUAL_PROGRESS_DESCRIBE,list2.get(0).getVisualProgressDescribe())
                                    .exec();
                        }
                    }
                }
            }
        }
    }
}
