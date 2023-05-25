package com.cisdi.ext.pm;

import com.cisdi.ext.model.PmPlan;
import com.cisdi.ext.model.base.PmPrj;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.PmPrjCodeUtil;
import com.cisdi.ext.util.StringUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmPlanExt
 * @package com.cisdi.ext.pm
 * @description 项目谋划
 * @date 2023/3/14
 */
public class PmPlanExt {

    /**
     * 项目库暂停更新谋划库
     * @param projectId 项目id
     * @param project 项目明细信息
     */
    public static void refreshPrj(String projectId, PmPrj project) {
        String prjName = project.getName();
        String now = DateTimeUtil.dateToString(new Date());
        String userId = ExtJarHelper.loginInfo.get().userId;
        List<com.cisdi.ext.model.PmPlan> pmPlanList = com.cisdi.ext.model.PmPlan.selectByWhere(new Where().eq(com.cisdi.ext.model.PmPlan.Cols.NAME,prjName).eq(com.cisdi.ext.model.PmPlan.Cols.STATUS,"AP"));
        if (CollectionUtils.isEmpty(pmPlanList)){
            String pmPlanId = Crud.from(com.cisdi.ext.model.PmPlan.ENT_CODE).insertData();
            String pmPlanCode = PmPrjCodeUtil.getPmPlanCode();
            Crud.from(com.cisdi.ext.model.PmPlan.ENT_CODE).where().eq(com.cisdi.ext.model.PmPlan.Cols.ID,pmPlanId).update()
                    .set(com.cisdi.ext.model.PmPlan.Cols.VER,99).set(com.cisdi.ext.model.PmPlan.Cols.CRT_USER_ID,userId)
                    .set(com.cisdi.ext.model.PmPlan.Cols.LAST_MODI_DT,now).set(com.cisdi.ext.model.PmPlan.Cols.LAST_MODI_USER_ID,userId)
                    .set(com.cisdi.ext.model.PmPlan.Cols.STATUS,"AP").set(com.cisdi.ext.model.PmPlan.Cols.CODE,pmPlanCode)
                    .set(com.cisdi.ext.model.PmPlan.Cols.NAME,prjName).set(com.cisdi.ext.model.PmPlan.Cols.BASE_LOCATION_ID,project.getBaseLocationId())
                    .set(com.cisdi.ext.model.PmPlan.Cols.PROJECT_TYPE_ID,project.getProjectTypeId()).set(com.cisdi.ext.model.PmPlan.Cols.PLAN_STATUS_ID,"1635456054244651008")
                    .set(com.cisdi.ext.model.PmPlan.Cols.AMT,project.getEstimatedTotalInvest())
                    .exec();
        } else {
            String pmPlanId = pmPlanList.get(0).getId();
            Crud.from(com.cisdi.ext.model.PmPlan.ENT_CODE).where().eq(com.cisdi.ext.model.PmPlan.Cols.ID,pmPlanId).update()
                    .set(com.cisdi.ext.model.PmPlan.Cols.VER,99).set(com.cisdi.ext.model.PmPlan.Cols.CRT_USER_ID,userId)
                    .set(com.cisdi.ext.model.PmPlan.Cols.LAST_MODI_DT,now).set(com.cisdi.ext.model.PmPlan.Cols.LAST_MODI_USER_ID,userId)
                    .set(com.cisdi.ext.model.PmPlan.Cols.STATUS,"AP")
                    .set(com.cisdi.ext.model.PmPlan.Cols.NAME,prjName).set(com.cisdi.ext.model.PmPlan.Cols.BASE_LOCATION_ID,project.getBaseLocationId())
                    .set(com.cisdi.ext.model.PmPlan.Cols.PROJECT_TYPE_ID,project.getProjectTypeId()).set(com.cisdi.ext.model.PmPlan.Cols.PLAN_STATUS_ID,"1635456054244651008")
                    .set(com.cisdi.ext.model.PmPlan.Cols.AMT,project.getEstimatedTotalInvest())
                    .exec();
        }
    }

    /**
     * 项目谋划列表查询
     */
    public void pmPlanList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        int pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        int pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
        StringBuffer sb = new StringBuffer();
        sb.append("select pp.ID,pp.CODE,pp.NAME,IFNULL(AMT,0) AS AMT,IFNULL(PLAN_PROGRESS,0) AS PLAN_PROGRESS,UPDATE_TIME,au.name as user," +
                " gsv.`NAME` as location,gg.`NAME` as type ,gv.`NAME` as `STATUS` from pm_plan pp \n" +
                "left join ad_user au on au.id = pp.AD_USER_ID  " +
                "left join gr_set_value gsv on gsv.id = pp.BASE_LOCATION_ID " +
                "left join gr_set_value gg on gg.id = pp.PROJECT_TYPE_ID " +
                "left join gr_set_value gv on gv.id = pp.PLAN_STATUS_ID where pp.`status` ='ap'");
        if (!StringUtils.isEmpty(map.get("name"))) {
            sb.append(" and pp.name like '%").append(map.get("name")).append("%'");
        }
        if (!StringUtils.isEmpty(map.get("user"))) {
            sb.append(" and pp.AD_USER_ID = '").append(map.get("user")).append("'");
        }
        if (!StringUtils.isEmpty(map.get("status"))) {
            sb.append(" and pp.PLAN_STATUS_ID = '").append(map.get("status")).append("'");
        }
        if (!StringUtils.isEmpty(map.get("startTime"))) {
            if (!StringUtils.isEmpty(map.get("endTime"))) {
                sb.append(" and pp.UPDATE_TIME between ").append("'").append(map.get("startTime")).append("' and ").append("'").append(map.get("endTime")).append("'");
            }
        }
        sb.append(" order by pp.crt_dt desc");
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<PmPlan> planList = list.stream().map(this::conventPmPlan).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(planList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide outSide = new OutSide();
            outSide.total = totalList.size();
            outSide.planList = planList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 项目谋划详情
     */
    public void pmPlanView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("id"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pp.ID,pp.CODE,pp.NAME,ATT_FILE_GROUP_ID,IFNULL(AMT,0)/10000 AS AMT,IFNULL(PLAN_PROGRESS,0) AS PLAN_PROGRESS," +
                "pp.ad_user_id,pp.PROJECT_TYPE_ID,pp.BASE_LOCATION_ID,PLAN_STATUS_ID,pp.crt_dt,pp.REMARK, " +
                "UPDATE_TIME,au.name as user,gsv.`NAME` as location,gg.`NAME` as type ,gv.`NAME` as `STATUS` from pm_plan pp  \n" +
                "left join ad_user au on au.id = pp.AD_USER_ID \n" +
                "left join gr_set_value gsv on gsv.id = pp.BASE_LOCATION_ID\n" +
                "left join gr_set_value gg on gg.id = pp.PROJECT_TYPE_ID\n" +
                "left join gr_set_value gv on gv.id = pp.PLAN_STATUS_ID where pp.status ='ap' and pp.id=?", id);
        if (list != null && list.size() > 0) {
            PmPlan plan = this.conventPmPlan(list.get(0));
            plan.fileInfoList = this.getFileList(String.valueOf(list.get(0).get("ATT_FILE_GROUP_ID")));
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(plan), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }

    /**
     * 进展情况
     */
    public void pmProgressList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String id = String.valueOf(map.get("id"));
        int pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        int pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
        StringBuilder sb = new StringBuilder();
        sb.append("select PPP.AD_USER_ID,PPP.REMARK,PPP.ATT_FILE_GROUP_ID,round(PPP.PLAN_PROGRESS,2) as PLAN_PROGRESS,UPDATE_TIME,PM_PLAN_ID,AU.`NAME` AS USER " +
                "from PM_PLAN_PROGRESS PPP LEFT JOIN AD_USER AU ON AU.ID = PPP.AD_USER_ID where PM_PLAN_ID='").append(id).append("'");
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<PlanProgress> planProgressList = list.stream().map(this::convertPlanProgress).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(planProgressList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide outSide = new OutSide();
            outSide.total = totalList.size();
            outSide.planProgressList = planProgressList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 项目谋划新增/修改
     */
    public void pmPlanModify() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        InputData input = JsonUtil.fromJson(json, InputData.class);
        String id = input.id;
        if (Strings.isNullOrEmpty(input.id)) {
            id = Crud.from("PM_PLAN").insertData();
        }
        String userId = ExtJarHelper.loginInfo.get().userId;
        String code = PmPrjCodeUtil.getPmPlanCode();
        Crud.from("PM_PLAN").where().eq("ID", id).update()
                .set("CODE", code).set("NAME", input.name).set("AMT", input.invest.multiply(new BigDecimal(10000))).set("PROJECT_TYPE_ID", input.type).set("BASE_LOCATION_ID", input.location)
                .set("AGENT", input.agent).set("REMARK", input.remark).set("ATT_FILE_GROUP_ID", input.fileIds).set("AD_USER_ID", userId).set("PLAN_STATUS_ID", "1635456054244651008").exec();
    }

    /**
     * 项目谋划删除
     */
    public void delPmPlan() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("id"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("delete from PM_PLAN_PROGRESS where PM_PLAN_ID=?", id);
        myJdbcTemplate.update("delete from PM_PLAN where id=?", id);
    }

    /**
     * 项目谋划-项目启动/谋划完成/谋划终止
     */
    public void changeStatus() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("id"));
        String status = String.valueOf(map.get("status"));
        String statusId = null;
        switch (status) {
            case "项目启动":
                statusId = "1635818406316060672";
                createPmStart(id);
                break;
            case "谋划启动":
                statusId = "1635456054244651008";
                break;
            case "谋划终止":
                statusId = "1635456205994569728";
                break;
        }
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("update PM_PLAN set PLAN_STATUS_ID=? where id=?", statusId, id);
    }

    /**
     * 项目谋划-更新进展
     */
    public void updateProgress() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        Progress progress = JsonUtil.fromJson(json, Progress.class);
        String id = Crud.from("PM_PLAN_PROGRESS").insertData();
        String userId = ExtJarHelper.loginInfo.get().userId;
        Crud.from("PM_PLAN_PROGRESS").where().eq("ID", id).update()
                .set("AD_USER_ID", userId).set("REMARK", progress.remark).set("ATT_FILE_GROUP_ID", progress.fileIds).set("PLAN_PROGRESS", progress.progress).set("PM_PLAN_ID", progress.planId)
                .set("UPDATE_TIME", new Date()).exec();
        Crud.from("PM_PLAN").where().eq("ID", progress.planId).update().set("PLAN_PROGRESS", progress.progress).set("UPDATE_TIME", new Date()).exec();
    }

    private PmPlan conventPmPlan(Map<String, Object> dataMap) {
        PmPlan plan = new PmPlan();
        plan.id = JdbcMapUtil.getString(dataMap, "ID");
        plan.code = JdbcMapUtil.getString(dataMap, "CODE");
        plan.name = JdbcMapUtil.getString(dataMap, "NAME");
        plan.invest = JdbcMapUtil.getBigDecimal(dataMap, "AMT");
        plan.progress = JdbcMapUtil.getBigDecimal(dataMap, "PLAN_PROGRESS");
        plan.userId = JdbcMapUtil.getString(dataMap, "AD_USER_ID");
        plan.user = JdbcMapUtil.getString(dataMap, "user");
        plan.locationId = JdbcMapUtil.getString(dataMap, "BASE_LOCATION_ID");
        plan.location = JdbcMapUtil.getString(dataMap, "location");
        plan.typeId = JdbcMapUtil.getString(dataMap, "PROJECT_TYPE_ID");
        plan.type = JdbcMapUtil.getString(dataMap, "type");
        plan.updateTime = StringUtil.withOutT(JdbcMapUtil.getString(dataMap, "UPDATE_TIME"));
        plan.statusId = JdbcMapUtil.getString(dataMap, "PLAN_STATUS_ID");
        plan.status = JdbcMapUtil.getString(dataMap, "STATUS");
        plan.startTime = StringUtil.withOutT(JdbcMapUtil.getString(dataMap, "CRT_DT"));
        plan.remark = JdbcMapUtil.getString(dataMap, "REMARK");
        return plan;
    }

    private PlanProgress convertPlanProgress(Map<String, Object> dataMap) {
        PlanProgress planProgress = new PlanProgress();
        planProgress.id = JdbcMapUtil.getString(dataMap, "ID");
        planProgress.remark = JdbcMapUtil.getString(dataMap, "REMARK");
        planProgress.progress = JdbcMapUtil.getString(dataMap, "PLAN_PROGRESS");
        planProgress.updateTime = StringUtil.withOutT(JdbcMapUtil.getString(dataMap, "UPDATE_TIME"));
        planProgress.user = JdbcMapUtil.getString(dataMap, "USER");
        planProgress.fileInfoList = this.getFileList(JdbcMapUtil.getString(dataMap, "ATT_FILE_GROUP_ID"));
        return planProgress;
    }

    private void createPmStart(String id) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_PLAN where id=?", id);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> dataMap = list.get(0);
            String startId = Crud.from("PRJ_START").insertData();
            String prjCode = PmPrjCodeUtil.getPrjCode();
            Crud.from("PRJ_START").where().eq("ID", startId).update()
                    .set("PM_CODE", prjCode).set("NAME", dataMap.get("name")).set("PRJ_TOTAL_INVEST", dataMap.get("AMT")).set("PROJECT_TYPE_ID", dataMap.get("PROJECT_TYPE_ID"))
                    .set("AGENT", dataMap.get("AGENT")).set("PRJ_START_STATUS_ID", "1635833910065868800")
                    .set("ATT_FILE_GROUP_ID", dataMap.get("ATT_FILE_GROUP_ID")).exec();
        }
    }


    public static class PmPlan {
        public String id;
        public String code;
        public String name;
        public BigDecimal invest;
        public BigDecimal progress;
        public String userId;
        public String user;
        public String locationId;
        public String location;
        public String typeId;
        public String type;
        public String updateTime;
        public String statusId;
        public String status;
        public String startTime;
        public String remark;
        public List<FileInfo> fileInfoList;
    }

    public static class PlanProgress {
        public String id;
        public String remark;
        public String progress;
        public String updateTime;
        public String user;
        public List<FileInfo> fileInfoList;
    }

    public static class FileInfo {
        //序号
        public Integer num;
        //文件名称
        public String fileName;
        //文件大小
        public String fileSize;
        //上传人
        public String uploadUser;
        //上传时间
        public String uploadDate;

        public String id;

        public String viewUrl;

        public String downloadUrl;
    }

    public static class OutSide {
        public Integer total;
        public List<PmPlan> planList;
        public List<PlanProgress> planProgressList;
    }

    public static class InputData {
        public String id;
        public String name;
        public BigDecimal invest;
        public String location;
        public String type;
        public String agent;
        public String remark;
        public String fileIds;
    }

    public static class Progress {
        public String planId;
        public String remark;
        public String progress;
        public String fileIds;
    }


    private List<FileInfo> getFileList(String fileIds) {
        if (Strings.isNullOrEmpty(fileIds)) {
            return null;
        }
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        Map<String, Object> queryParams = new HashMap<>();// 创建入参map
        queryParams.put("ids", Arrays.asList(fileIds.split(",")));
        List<Map<String, Object>> list = myNamedParameterJdbcTemplate.queryForList("select ff.ID as ID, DSP_NAME,SIZE_KB,UPLOAD_DTTM,au.`NAME` as USER_NAME,FILE_INLINE_URL,FILE_ATTACHMENT_URL from fl_file ff left join ad_user au on ff.CRT_USER_ID = au.id  where ff.id in (:ids)", queryParams);
        AtomicInteger index = new AtomicInteger(0);
        List<FileInfo> fileObjList = list.stream().map(p -> {
            FileInfo obj = new FileInfo();
            obj.num = index.getAndIncrement() + 1;
            obj.fileName = JdbcMapUtil.getString(p, "DSP_NAME");
            obj.fileSize = JdbcMapUtil.getString(p, "SIZE_KB");
            obj.uploadUser = JdbcMapUtil.getString(p, "USER_NAME");
            obj.uploadDate = StringUtil.withOutT(JdbcMapUtil.getString(p, "UPLOAD_DTTM"));
            obj.id = JdbcMapUtil.getString(p, "ID");
            obj.viewUrl = JdbcMapUtil.getString(p, "FILE_INLINE_URL");
            obj.downloadUrl = JdbcMapUtil.getString(p, "FILE_ATTACHMENT_URL");
            return obj;
        }).collect(Collectors.toList());
        return fileObjList;
    }
}
