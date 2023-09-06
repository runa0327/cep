package com.cisdi.ext.weeklyReport;

import cn.hutool.core.util.IdUtil;
import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.file.BaseFileExt;
import com.cisdi.ext.model.*;
import com.cisdi.ext.model.base.PmPrj;
import com.cisdi.ext.model.view.project.PmPrjView;
import com.cisdi.ext.model.view.weekReport.PmProgressWeeklyPrjProblemDetailView;
import com.cisdi.ext.model.view.weekReport.PmProgressWeeklyView;
import com.cisdi.ext.model.view.weekReport.SortBean;
import com.cisdi.ext.model.view.weekReport.WeekMessage;
import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
        String projectName = param.getProjectName();

        String sql1 = "select distinct a.pm_prj_id,c.name,ifnull(c.IZ_START_REQUIRE,'1') as weatherStart,ifnull(c.IZ_END,'0') as weatherCompleted " +
                "from PM_ROSTER a left join POST_INFO b on a.POST_INFO_ID = b.id LEFT JOIN pm_prj c on a.PM_PRJ_ID = c.id " +
                "where b.code = 'AD_USER_TWENTY_THREE_ID' and a.AD_USER_ID = ? and a.status = 'ap' AND c.PROJECT_SOURCE_TYPE_ID = '0099952822476441374' " +
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
                pmPrjView.setId(JdbcMapUtil.getString(p,"pm_prj_id"));
                pmPrjView.setProjectName(JdbcMapUtil.getString(p,"name"));
                pmPrjView.setWeatherStart(JdbcMapUtil.getInt(p,"weatherStart"));
                pmPrjView.setWeatherCompleted(JdbcMapUtil.getInt(p,"weatherCompleted"));
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
        String projectId = param.getProjectId();
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
                    "a.TEXT_REMARK_ONE as progressRemark,ifnull(c.IZ_START_REQUIRE,'1') as weatherStart,ifnull(c.IZ_END,'0') as weatherCompleted,'new' as dataType, " +
                    "a.PM_PROGRESS_WEEKLY_ID as weekId,a.PM_PROGRESS_WEEKLY_PRJ_ID as weekPrjId,b.PM_PRJ_ID as projectId,b.IZ_WRITE as izWrite,a.FILE_ID_TWO as aerialImg " +
                    "from pm_progress_weekly_prj_detail a left join PM_PROGRESS_WEEKLY_PRJ b on a.PM_PROGRESS_WEEKLY_PRJ_ID = b.id left join pm_prj c on b.pm_prj_id = c.id " +
                    "where a.STATUS = 'ap' and b.status = 'ap' and b.pm_prj_id = ? and a.PM_PROGRESS_WEEKLY_ID = '"+weekId+"' and b.PM_PROGRESS_WEEKLY_ID = '"+weekId+"' ";
            List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,projectId);
            if (!CollectionUtils.isEmpty(list)) {
                Map<String, Object> map1 = new HashMap<>();
                List<WeekMessage> returnList = list.stream().map(p -> {
                    WeekMessage weekMessage = new WeekMessage();
                    weekMessage.setId(JdbcMapUtil.getString(p, "id"));  //id
                    weekMessage.setWriteDate(JdbcMapUtil.getString(p, "writeDate")); //填报日期
                    String progress = JdbcMapUtil.getString(p, "progress"); //整体形象进度
                    if (SharedUtil.isEmptyString(progress)) {
                        weekMessage.setProgress(new BigDecimal(0));
                    } else {
                        weekMessage.setProgress(new BigDecimal(progress));
                    }
                    weekMessage.setProgressDescribe(JdbcMapUtil.getString(p, "progressDescribe")); //累计形象进度说明
                    weekMessage.setProgressWeek(JdbcMapUtil.getString(p, "progressWeek")); //本周项目进展
                    weekMessage.setProgressRemark(JdbcMapUtil.getString(p, "progressRemark")); //备注说明
                    weekMessage.setWeatherCompleted(JdbcMapUtil.getInt(p, "weatherCompleted")); // 是否已竣工
                    weekMessage.setWeatherStart(JdbcMapUtil.getInt(p, "weatherStart")); // 是否符合开工条件
                    weekMessage.setIzWrite(JdbcMapUtil.getInt(p, "izWrite")); // 是否填写
                    String weekPrjId = JdbcMapUtil.getString(p, "weekPrjId");
                    weekMessage.setWeekPrjId(weekPrjId); //周项目id
                    weekMessage.setProjectId(JdbcMapUtil.getString(p, "projectId")); //项目id
                    weekMessage.setWeekId(JdbcMapUtil.getString(p, "weekId")); //周批次id
                    String fileId = JdbcMapUtil.getString(p, "fileId"); //文件id
                    if (!SharedUtil.isEmptyString(fileId)) {
                        weekMessage.setFileId(fileId);
                        weekMessage.setFileList(BaseFileExt.getFile(fileId));
                    }
                    String aerialImg = JdbcMapUtil.getString(p,"aerialImg"); // 航拍图
                    if (StringUtils.hasText(aerialImg)){
                        weekMessage.setAerialImgId(aerialImg);
                        weekMessage.setAerialImg(BaseFileExt.getFile(aerialImg));
                    }
                    // 获取项目问题明细信息
                    List<PmProgressWeeklyPrjProblemDetailView> problemDetailList = getProblemDetailListByWeekPrjId(weekPrjId,myJdbcTemplate);
                    if (!CollectionUtils.isEmpty(problemDetailList)){
                        weekMessage.setProblemDetailList(problemDetailList);
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
     * 根据周项目id查询项目问题明细
     * @param weekPrjId 周项目id
     * @param myJdbcTemplate 数据源
     * @return 查询结果
     */
    private List<PmProgressWeeklyPrjProblemDetailView> getProblemDetailListByWeekPrjId(String weekPrjId, MyJdbcTemplate myJdbcTemplate) {
        List<PmProgressWeeklyPrjProblemDetailView> list = new ArrayList<>();
        String sql = "select A.ID,A.PRJ_PUSH_PROBLEM_TYPE_ID,A.PM_PROGRESS_WEEKLY_PRJ_ID,A.TEXT_REMARK_ONE,B.NAME from PM_PROGRESS_WEEKLY_PRJ_PROBLEM_DETAIL A LEFT JOIN GR_SET_VALUE B ON A.PRJ_PUSH_PROBLEM_TYPE_ID = B.ID where PM_PROGRESS_WEEKLY_PRJ_ID = ? and a.status = 'AP'";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql,weekPrjId);
        if (!CollectionUtils.isEmpty(list1)){
            list1.forEach(p->{
                PmProgressWeeklyPrjProblemDetailView pmProgressWeeklyPrjProblemDetailView = new PmProgressWeeklyPrjProblemDetailView();
                pmProgressWeeklyPrjProblemDetailView.setId(JdbcMapUtil.getString(p,"ID"));
                pmProgressWeeklyPrjProblemDetailView.setPmProgressWeeklyPrjId(JdbcMapUtil.getString(p,"PM_PROGRESS_WEEKLY_PRJ_ID"));
                pmProgressWeeklyPrjProblemDetailView.setPrjPushProblemTypeId(JdbcMapUtil.getString(p,"PRJ_PUSH_PROBLEM_TYPE_ID"));
                pmProgressWeeklyPrjProblemDetailView.setPrjPushProblemTypeName(JdbcMapUtil.getString(p,"NAME"));
                pmProgressWeeklyPrjProblemDetailView.setProblemDescribe(JdbcMapUtil.getString(p,"TEXT_REMARK_ONE"));
                list.add(pmProgressWeeklyPrjProblemDetailView);
            });
        }
        return list;
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
        String id = param.getId();
        String projectId = param.getProjectId();
        String aerialImg = param.getAerialImgId(); // 航拍图
        //判断是否可改
        List<PmProgressWeeklyPrjDetail> list1 = PmProgressWeeklyPrjDetail.selectByWhere(new Where().eq("ID",id));
        boolean isSubmit = list1.get(0).getIsWeeklyReportSubmit();
        if (isSubmit){
            throw new BaseException("本周周报已超过提交时间，不允许修改");
        }
        String writeDate = param.getWriteDate(); //填报日期
        String weekPrjId = param.getWeekPrjId(); //进度周报-周项目信息id
        String weekId = param.getWeekId(); // 周期信息
        if (SharedUtil.isEmptyString(id)){
            throw new BaseException("记录id不能为空！");
        }
        if (SharedUtil.isEmptyString(writeDate)){
            throw new BaseException("填报日期不能为空！");
        }
        String start = writeDate.substring(0,10);
        String end = writeDate.substring(11,21);
        String now = DateTimeUtil.dttmToString(new Date());
        //形象进度说明逻辑处理
        BigDecimal progress = param.getProgress();
        progressHandle(progress,projectId,param);
        //数据保存
        Crud.from("PM_PROGRESS_WEEKLY_PRJ_DETAIL").where().eq("id",id).update()
                .set("DATE",writeDate).set("PM_PRJ_ID",projectId)
                .set("VISUAL_PROGRESS",progress).set("PROCESS_REMARK_TEXT",param.getProgressWeek())
                .set("VISUAL_PROGRESS_DESCRIBE",param.getProgressDescribe()).set("FILE_ID_ONE",param.getFileId())
                .set("TEXT_REMARK_ONE",param.getProgressRemark()).set("SYS_TRUE",param.getWeatherStart())
                .set("IZ_END",param.getWeatherCompleted()).set("FROM_DATE",start).set("TO_DATE",end)
                .set("PM_PROGRESS_WEEKLY_ID",weekId).set("PM_PROGRESS_WEEKLY_PRJ_ID",weekPrjId)
                .set("AD_USER_ID",userId).set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",userId)
                .set("TS",now).set("FILE_ID_TWO",param.getAerialImgId())
                .exec();
        //主表更新，该项目该周已更新
        Crud.from("PM_PROGRESS_WEEKLY_PRJ").where().eq("id",weekPrjId).update()
                .set("IZ_WRITE",1)
                .set("AD_USER_ID",userId).set("LAST_MODI_USER_ID",userId)
                .set("TS",now).set("LAST_MODI_DT",now)
                .exec();
        // 更新项目航拍图信息
        updatePrjImg(projectId,aerialImg,id);
        // 保存修改项目问题
        List<PmProgressWeeklyPrjProblemDetailView> proList = param.getProblemDetailList();
        updateProDetail(projectId,weekPrjId,proList,weekId);

        // 发起流程
//        startProcess(param,now);
    }

    // 流程发起
    private void startProcess(WeekMessage param, String now) {
        List<PmProgressWeeklyPrjProblemDetailView> list = param.getProblemDetailList();
        if (!CollectionUtils.isEmpty(list)){
            String projectId = param.getProjectId();
            for (PmProgressWeeklyPrjProblemDetailView tmp : list) {
                String wfProcessInstanceId = Crud.from("WF_PROCESS_INSTANCE").insertData();
                String pmPrjProblemId = Crud.from("PM_PROJECT_PROBLEM_REQ").insertData();
                String wfNodeInstanceId2 = Crud.from("WF_NODE_INSTANCE").insertData();
                String userId = ExtJarHelper.loginInfo.get().userId;
                String handleUser = tmp.getHandleUserIds();
                if (!StringUtils.hasText(handleUser)){
                    handleUser = "0099250247095871681";
                }
                ProcessCommon.autoStartProcess(wfProcessInstanceId,pmPrjProblemId,wfNodeInstanceId2,"1679779416055611392",now,userId,handleUser,param.getProjectId());
                Crud.from("PM_PROJECT_PROBLEM_REQ").where().eq("ID",pmPrjProblemId).update()
                        .set("LK_WF_INST_ID",wfProcessInstanceId)
                        .set("VER","1").set("TS",now).set("STATUS","AP").set("CRT_DT",now).set("CRT_USER_ID",userId).set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",userId)
                        .set("PM_PRJ_ID",projectId).set("PRJ_PUSH_PROBLEM_TYPE_ID",tmp.getPrjPushProblemTypeId())
                        .set("TEXT_REMARK_ONE",tmp.getProblemDescribe()).set("TO_USER_IDS",handleUser)
                        .exec();
            }
        }
    }

    /**
     * 项目问题明细修改
     * @param projectId 项目id
     * @param weekPrjId 项目周id
     * @param proList 问题明细信息
     * @param weekId 周期信息
     */
    private void updateProDetail(String projectId, String weekPrjId, List<PmProgressWeeklyPrjProblemDetailView> proList, String weekId) {
        // 删除上一次问题明细信息
        PmProgressWeeklyPrjProblemDetail.deleteByWhere(new Where().eq(PmProgressWeeklyPrjProblemDetail.Cols.PM_PROGRESS_WEEKLY_PRJ_ID,weekPrjId));
        if (!CollectionUtils.isEmpty(proList)){
            for (PmProgressWeeklyPrjProblemDetailView tmp : proList) {
                String typeId = tmp.getPrjPushProblemTypeId();
                String describe = tmp.getProblemDescribe();
                String id = Crud.from(PmProgressWeeklyPrjProblemDetail.ENT_CODE).insertData();
                Crud.from(PmProgressWeeklyPrjProblemDetail.ENT_CODE).where().eq("ID",id).update()
                        .set("PM_PROGRESS_WEEKLY_PRJ_ID",weekPrjId).set("PRJ_PUSH_PROBLEM_TYPE_ID",typeId)
                        .set("TEXT_REMARK_ONE",describe).set("PM_PRJ_ID",projectId).set("STATUS","AP")
                        .set("PM_PROGRESS_WEEKLY_ID",weekId)
                        .exec();
            }
        }
    }

    /**
     * 更新项目航拍图信息
     * @param projectId 项目id
     * @param aerialImg 航拍图id
     * @param id 本条明细表记录id
     */
    private void updatePrjImg(String projectId, String aerialImg, String id) {
        String prjImg = "";
        // 查询项目所有历史航拍图信息
        List<PmProgressWeeklyPrjDetail> list = PmProgressWeeklyPrjDetail.selectByWhere(new Where().eq(PmProgressWeeklyPrjDetail.Cols.PM_PRJ_ID,projectId)
                .neq(PmProgressWeeklyPrjDetail.Cols.ID,id).eq(PmProgressWeeklyPrjDetail.Cols.STATUS,"AP"));
        // 查询项目航拍图信息
        String projectImg = PmPrj.selectById(projectId).getPrjImg();
        List<String> imgStrList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            List<PmProgressWeeklyPrjDetail> imgList = list.stream().filter(p-> StringUtils.hasText(p.getFileIdTwo())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(imgList)){
                imgStrList = imgList.stream().map(PmProgressWeeklyPrjDetail::getFileIdTwo).distinct().collect(Collectors.toList());
            }
        }
        if (StringUtils.hasText(aerialImg)){
            if (!StringUtils.hasText(projectImg) && CollectionUtils.isEmpty(imgStrList)){
                prjImg = aerialImg;
            } else if (StringUtils.hasText(projectImg) && CollectionUtils.isEmpty(imgStrList)){
                prjImg = projectImg + "," + aerialImg;
            } else if (StringUtils.hasText(projectImg) && !CollectionUtils.isEmpty(imgStrList)){
                List<String> arr = new ArrayList<>(Arrays.asList(projectImg.split(",")));
                for (String tmp : imgStrList) {
                    if (projectImg.contains(tmp)){
                        arr.remove(tmp);
                    }
                }
                prjImg = String.join(",",arr) + "," + aerialImg;
            }
        }
        if (StringUtils.hasText(prjImg)){
            PmPrjExt.updateOneColValue(projectId,prjImg,"PRJ_IMG");
        }
    }

    /**
     * 形象进度处理
     * @param progress 形象进度
     * @param projectId 项目id
     * @param weekMessage 形象进度对象
     */
    private void progressHandle(BigDecimal progress, String projectId, WeekMessage weekMessage) {
        if (progress.compareTo(new BigDecimal(100)) == 1 || progress.compareTo(new BigDecimal(0)) == -1){
            throw new BaseException("整体形象进度只能处于 0-100 间");
        }
        if (progress.compareTo(new BigDecimal(100)) == 0){
            Crud.from("PM_PRJ").where().eq("id",projectId).update().set("IZ_END",1).set("PROJECT_PHASE_ID","0099799190825080708").exec();
            Crud.from("PM_PROGRESS_WEEKLY_PRJ").where().eq("id",weekMessage.getWeekPrjId()).update().set("IZ_END",1).exec();
            Crud.from("PM_PROGRESS_WEEKLY_PRJ_DETAIL").where().eq("id",weekMessage.getId()).update().set("IZ_END",1).exec();
        }
    }

    /**
     * 形象进度工程周报-填写-符合开工条件/是否竣工 关闭/打开
     */
    public void openOrClose(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        WeekMessage param = JsonUtil.fromJson(json,WeekMessage.class);
        Integer buttonType = param.getButtonType();
        Integer buttonStatus = param.getButtonStatus();
        String projectId = param.getProjectId();
        String weekPrjId = param.getWeekPrjId();
        String id = param.getId();
        if ( buttonType == null ){
            throw new BaseException("按钮类型名称不能为空");
        }
        if ( buttonStatus == null){
            throw new BaseException("开关状态不能为空");
        }
        PmPrj pmPrj = new PmPrj();
        pmPrj.setId(param.getProjectId());
        if (buttonType == 0){ //处理开工条件
            if (buttonStatus == 1){
                pmPrj.setIzStartRequire(true);
            } else {
                pmPrj.setIzStartRequire(false);
            }
//            handleStart(projectId,weekPrjId,id,buttonStatus);
        } else if (buttonType == 1){ //处理竣工条件
//            handleComplete(projectId,weekPrjId,id,buttonStatus);
            if (buttonStatus == 1){
                pmPrj.setIzEnd(true);
            } else {
                pmPrj.setIzEnd(false);
            }
        }
        pmPrj.updateById();
    }

    /**
     * 是否竣工判断
     * @param projectId 项目id
     * @param weekPrjId 周-项目id
     * @param id 周-项目-详情id
     * @param buttonStatus 状态 0关闭 1开启
     */
    private void handleComplete(String projectId, String weekPrjId, String id, Integer buttonStatus) {
        if (buttonStatus == 1){ //已竣工
            Crud.from("PM_PRJ").where().eq("id",projectId).update().set("IZ_END",buttonStatus).set("PROJECT_PHASE_ID","0099799190825080708").exec();
        } else {
            Crud.from("PM_PRJ").where().eq("id",projectId).update().set("IZ_END",buttonStatus).exec();
        }
        Crud.from("PM_PROGRESS_WEEKLY_PRJ").where().eq("id",weekPrjId).update().set("IZ_END",buttonStatus).exec();
        Crud.from("PM_PROGRESS_WEEKLY_PRJ_DETAIL").where().eq("id",id).update().set("IZ_END",buttonStatus).exec();
    }

    /**
     * 处理开工条件
     * @param projectId 项目id
     * @param weekPrjId 周-项目id
     * @param id 周-项目-详情id
     * @param buttonStatus 状态 0关闭 1开启
     */
    private void handleStart(String projectId, String weekPrjId, String id, Integer buttonStatus) {
        Crud.from("PM_PRJ").where().eq("id",projectId).update().set("IZ_START_REQUIRE",buttonStatus).exec();
        Crud.from("PM_PROGRESS_WEEKLY_PRJ").where().eq("id",weekPrjId).update().set("SYS_TRUE",buttonStatus).exec();
        Crud.from("PM_PROGRESS_WEEKLY_PRJ_DETAIL").where().eq("id",id).update().set("SYS_TRUE",buttonStatus).exec();
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
        String projectName = param.getProjectName();
        String projectId = param.getProjectId();

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
                pmPrjView.setId(JdbcMapUtil.getString(p,"PM_PRJ_ID"));
                pmPrjView.setProjectName(JdbcMapUtil.getString(p,"name"));
                pmPrjView.setWeatherStart(JdbcMapUtil.getInt(p,"weatherStart"));
                pmPrjView.setWeatherCompleted(JdbcMapUtil.getInt(p,"weatherCompleted"));
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
        String projectId = param.getProjectId();
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
                "a.TEXT_REMARK_ONE as progressRemark,ifnull(e.IZ_START_REQUIRE,'1') as weatherStartt,ifnull(e.IZ_END,'0') as weatherCompleted ," +
                "a.AD_USER_ID as recordById,d.name as recordByName,a.FILE_ID_ONE as fileId,b.PM_PRJ_ID as projectId," +
                "(select name from pm_prj where id = b.id) as projectName " +
                "from PM_PROGRESS_WEEKLY_PRJ_DETAIL a left join PM_PROGRESS_WEEKLY_PRJ b on a.PM_PROGRESS_WEEKLY_PRJ_ID = b.id " +
                "LEFT JOIN pm_progress_weekly c on a.PM_PROGRESS_WEEKLY_ID = c.id " +
                "left join ad_user d on a.AD_USER_ID = d.id left join pm_prj e on b.pm_prj_id = e.id " +
                "where a.status = 'ap' and b.status = 'ap' and c.status = 'ap' and b.PM_PRJ_ID = ?";
        StringBuilder sb = new StringBuilder(sql);
        if (!SharedUtil.isEmptyString(param.getWriteDateMin())){
            sb.append(" and c.FROM_DATE >= '").append(param.getWriteDateMin()).append("' ");
        }
        if (!SharedUtil.isEmptyString(param.getWriteDateMax())){
            sb.append(" and c.TO_DATE <= '").append(param.getWriteDateMax()).append("' ");
        }
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(String.valueOf(sb),projectId);
        sb.append(" order by a.LAST_MODI_DT desc ").append(limit);
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sb.toString(),projectId);
        if (!CollectionUtils.isEmpty(list1)){
            Map<String, Object> map1 = new HashMap<>();
            List<WeekMessage> returnList = list1.stream().map(p->{
                WeekMessage weekMessage = new WeekMessage();
                weekMessage.setId(JdbcMapUtil.getString(p,"id")); //id
                weekMessage.setProjectId(JdbcMapUtil.getString(p,"projectId")); //项目id
                weekMessage.setProjectName(JdbcMapUtil.getString(p,"projectName")); //项目名称
                weekMessage.setWriteDate(JdbcMapUtil.getString(p,"writeDate")); //填报时间
                weekMessage.setWeatherStart(JdbcMapUtil.getInt(p,"weatherStart")); //是否符合开工条件 1符合0不符合
                weekMessage.setWeatherCompleted(JdbcMapUtil.getInt(p,"weatherCompleted")); //是否竣工 1已竣工0未竣工
                String progress = JdbcMapUtil.getString(p,"progress"); //整体形象进度
                if (SharedUtil.isEmptyString(progress)){
                    weekMessage.setProgress(new BigDecimal(0));
                } else {
                    weekMessage.setProgress(new BigDecimal(progress));
                }
                weekMessage.setProgressDescribe(JdbcMapUtil.getString(p,"progressDescribe")); //累计形象进度说明
                weekMessage.setProgressWeek(JdbcMapUtil.getString(p,"progressWeek")); //本周项目进展
                weekMessage.setProgressRemark(JdbcMapUtil.getString(p,"progressRemark")); //备注说明
                weekMessage.setRecordById(JdbcMapUtil.getString(p,"recordById")); //记录人
                weekMessage.setRecordByName(JdbcMapUtil.getString(p,"recordByName")); //记录人
                String fileId = JdbcMapUtil.getString(p,"fileId"); //形象进度影像
                if (!SharedUtil.isEmptyString(fileId)){
                    weekMessage.setFileList(BaseFileExt.getFile(fileId));
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
        String weekId = param.getWeekId();
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
        String projectName = param.getProjectName(); //项目名称
        String writeDateMin = param.getWriteDateMin(); //最小填写日期
        String writeDateMax = param.getWriteDateMax(); //最大填写日期
        String manageUserName = param.getManageUserName(); //项目负责人
        BigDecimal progressMin = param.getProgressMin(); //最小进度
        BigDecimal progressMax = param.getProgressMax(); //最大进度
        Integer weatherStart = param.getWeatherStart(); //是否符合开工条件
        Integer weatherCompleted = param.getWeatherCompleted(); //是否竣工
        String limit = canMap.get("limit").toString();
        String sql = "select c.id as weekId,a.id,c.DATE as writeDate,a.VISUAL_PROGRESS as progress,a.VISUAL_PROGRESS_DESCRIBE as progressDescribe,a.PROCESS_REMARK_TEXT as progressWeek," +
                "a.TEXT_REMARK_ONE as progressRemark,ifnull(e.IZ_START_REQUIRE,'1') as weatherStart,ifnull(e.IZ_END,'0') as weatherCompleted," +
                "(select name from ad_user where id = a.ad_user_id) as recordByName,a.AD_USER_ID as recordById, " +
                "a.FILE_ID_ONE as fileId,b.PM_PRJ_ID as projectId,a.FILE_ID_TWO as aerialImgId," +
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
        if (!SharedUtil.isEmptyString(param.getManageUserId())){
            sb.append(" and d.ad_user_id = '").append(param.getManageUserId()).append("' "); //项目负责人
        }
        if (progressMin != null){
            sb.append(" and a.VISUAL_PROGRESS >= '").append(progressMin).append("' "); //最小进度
        }
        if (progressMax != null){
            sb.append(" and a.VISUAL_PROGRESS <= '").append(progressMax).append("' "); //最大进度
        }
        if (weatherStart != null){
            sb.append(" and e.IZ_START_REQUIRE = '").append(weatherStart).append("' "); //是否符合开工条件
        }
        if (weatherCompleted != null){
            sb.append(" and e.IZ_END = '").append(weatherCompleted).append("' "); //是否竣工
        }
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(String.valueOf(sb));
        sb.append(" order by b.IZ_END asc,b.SYS_TRUE desc,");
        List<SortBean> sortList = param.getSort();;
        if (CollectionUtils.isEmpty(sortList)){
            sb.append(" a.ts desc,b.PM_PRJ_ID desc ");
        } else {
            resultSort(sb,sortList);
        }
        sb.append(" ").append(limit);
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sb.toString());
        Map<String,Object> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(list1)){
            List<WeekMessage> returnList = getWeekMessageList(list1,myJdbcTemplate);
            List<WeekMessage> allList = getWeekMessageList(list2,myJdbcTemplate);
            map.put("total",list2.size());
            map.put("all",allList);
            map.put("result", returnList);
        }
        return map;
    }

    /**
     * 自定义排序
     * @param sb sql字符串
     * @param sortList 排序字段
     */
    private void resultSort(StringBuilder sb, List<SortBean> sortList) {
        for (SortBean sort : sortList) {
            switch (sort.getColName()){
                case "projectName" :
                    if (sort.isIzUp()){
                        sb.append(" convert(e.name using gbk) asc,");
                    } else {
                        sb.append(" convert(e.name using gbk) desc,");
                    }
                    break;
                case "manageUserName" :
                    if (sort.isIzUp()){
                        sb.append(" convert(f.name using gbk) asc,");
                    } else {
                        sb.append(" convert(f.name using gbk) desc,");
                    }
                    break;
                case "progress" :
                    if (sort.isIzUp()){
                        sb.append(" a.VISUAL_PROGRESS asc,");
                    } else {
                        sb.append(" a.VISUAL_PROGRESS desc,");
                    }
                    break;
                case "progressWeek" : // 本周工作进展
                    if (sort.isIzUp()){
                        sb.append(" convert(a.PROCESS_REMARK_TEXT using gbk) asc,");
                    } else {
                        sb.append(" convert(a.PROCESS_REMARK_TEXT using gbk) desc,");
                    }
                    break;
                case "progressDescribe" : // 累计详细进度/问题说明
                    if (sort.isIzUp()){
                        sb.append(" convert(a.VISUAL_PROGRESS_DESCRIBE using gbk) asc,");
                    } else {
                        sb.append(" convert(a.VISUAL_PROGRESS_DESCRIBE using gbk) desc,");
                    }
                    break;
                default: break;
            }
        }
        sb.deleteCharAt(sb.length()-1);
    }

    /**
     * Map数据转实体对象
     * @param list1 查询Map数据
     * @param myJdbcTemplate 数据源
     * @return 转换结果
     */
    private List<WeekMessage> getWeekMessageList(List<Map<String, Object>> list1, MyJdbcTemplate myJdbcTemplate) {
        return list1.stream().map(p->{
            WeekMessage weekMessage = new WeekMessage();
            weekMessage.setId(JdbcMapUtil.getString(p,"id")); //id
            weekMessage.setWriteDate(JdbcMapUtil.getString(p,"writeDate")); //填报日期
            String progress = JdbcMapUtil.getString(p,"progress"); //整体形象进度
            if (SharedUtil.isEmptyString(progress)){
                weekMessage.setProgress(new BigDecimal(0));
            } else {
                weekMessage.setProgress(new BigDecimal(progress));
            }
//            weekMessage.progressDescribe = JdbcMapUtil.getString(p,"progressDescribe"); //累计形象进度说明
            weekMessage.setProgressWeek(JdbcMapUtil.getString(p,"progressWeek")); //本周项目进展
            weekMessage.setProgressRemark(JdbcMapUtil.getString(p,"progressRemark")); //备注说明
            weekMessage.setWeatherCompleted(JdbcMapUtil.getInt(p,"weatherCompleted")); // 是否已竣工
            weekMessage.setWeatherStart(JdbcMapUtil.getInt(p,"weatherStart")); // 是否符合开工条件
            weekMessage.setIzWrite(JdbcMapUtil.getInt(p,"izWrite")); // 是否填写
            weekMessage.setManageUserId(JdbcMapUtil.getString(p,"manageUserId")); // 项目负责人
            weekMessage.setManageUserName(JdbcMapUtil.getString(p,"manageUserName")); // 项目负责人
            weekMessage.setRecordById(JdbcMapUtil.getString(p,"recordById")); // 记录人
            weekMessage.setRecordByName(JdbcMapUtil.getString(p,"recordByName")); // 记录人
            weekMessage.setProjectId(JdbcMapUtil.getString(p,"projectId")); // 项目id
            weekMessage.setProjectName(JdbcMapUtil.getString(p,"projectName")); // 项目名称
            String weekPrjId = JdbcMapUtil.getString(p,"weekPrjId");
            weekMessage.setWeekPrjId(weekPrjId); // 项目周id
            weekMessage.setWeekId(JdbcMapUtil.getString(p,"weekId")); // 周id
            String fileId = JdbcMapUtil.getString(p,"fileId");
            if (!SharedUtil.isEmptyString(fileId)){
                weekMessage.setFileList(BaseFileExt.getFile(fileId));
            }
            // 航拍图
            String aerialImg = JdbcMapUtil.getString(p,"aerialImgId");
            if (StringUtils.hasText(aerialImg)){
                weekMessage.setAerialImgId(aerialImg);
                weekMessage.setAerialImg(BaseFileExt.getFile(aerialImg));
            }
            // 查询问题明细
            String progressDescribe = getPrjDescribe(weekPrjId,myJdbcTemplate);
            if (StringUtils.hasText(progressDescribe)){
                weekMessage.setProgressDescribe(progressDescribe); //累计形象进度说明
            }
            // 获取项目问题明细信息
            List<PmProgressWeeklyPrjProblemDetailView> problemDetailList = getProblemDetailListByWeekPrjId(weekPrjId,myJdbcTemplate);
            if (!CollectionUtils.isEmpty(problemDetailList)){
                weekMessage.setProblemDetailList(problemDetailList);
            }
            return weekMessage;
        }).collect(Collectors.toList());
    }

    /**
     * 根据周项目id获取问题明细信息
     * @param weekPrjId 周项目明细
     * @param myJdbcTemplate 数据源
     * @return 查询结果
     */
    private String getPrjDescribe(String weekPrjId, MyJdbcTemplate myJdbcTemplate) {
        StringBuilder sb = new StringBuilder();
        String sql = "select a.TEXT_REMARK_ONE,a.PRJ_PUSH_PROBLEM_TYPE_ID,(select name from gr_set_value where id = a.PRJ_PUSH_PROBLEM_TYPE_ID) as typeName from pm_progress_weekly_prj_problem_detail a where a.PM_PROGRESS_WEEKLY_PRJ_ID = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,weekPrjId);
        if (!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                String name = JdbcMapUtil.getString(list.get(i),"typeName");
                String describe = JdbcMapUtil.getString(list.get(i),"TEXT_REMARK_ONE");
                if (i != list.size() -1){
                    if (StringUtils.hasText(name)){
                        sb.append(name).append("：");
                    }
                    sb.append(describe).append("；");
                } else {
                    if (StringUtils.hasText(name)){
                        sb.append(name).append("：");
                    }
                    sb.append(describe);
                }
            }
        }
        return sb.toString();
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
            //本周项目进展不为空数
            int progressWeekNum = (int) list.stream().filter(p->!SharedUtil.isEmptyString(p.getProgressWeek())).count();
            //不符合开工条件
            int noStarts = (int) list.stream().filter(p-> p.getWeatherStart() == 0).count();
            //已竣工
            int completes = (int) list.stream().filter(p->p.getWeatherCompleted() == 1).count();
            int writes = progressWeekNum - noStarts - completes;
            writes = writes < 0 ? 0 : writes;
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
        String id = param.getId();
        String projectId = param.getProjectId();
        String aerialImg = param.getAerialImgId(); // 航拍图
        String writeDate = param.getWriteDate(); //填报日期
        String weekPrjId = param.getWeekPrjId(); //进度周报-周项目信息id
        Integer weatherStart = param.getWeatherStart(); // 是否符合开工条件
        Integer weatherCompleted = param.getWeatherCompleted(); // 是否竣工
        String weekId = param.getWeekId(); // 周期id
        if (SharedUtil.isEmptyString(id)){
            throw new BaseException("记录id不能为空！");
        }
        if (SharedUtil.isEmptyString(writeDate)){
            throw new BaseException("填报日期不能为空！");
        }
        String start = writeDate.substring(0,10);
        String end = writeDate.substring(11,21);
        String now = DateTimeUtil.dttmToString(new Date());
        //形象进度说明逻辑处理
        BigDecimal progress = param.getProgress();
        progressHandle(progress,projectId,param);
        //数据保存
        Crud.from("PM_PROGRESS_WEEKLY_PRJ_DETAIL").where().eq("id",id).update()
                .set("DATE",writeDate).set("PM_PRJ_ID",param.getProjectId())
                .set("VISUAL_PROGRESS",progress).set("PROCESS_REMARK_TEXT",param.getProgressWeek())
                .set("VISUAL_PROGRESS_DESCRIBE",param.getProgressDescribe()).set("FILE_ID_ONE",param.getFileId())
                .set("TEXT_REMARK_ONE",param.getProgressRemark()).set("SYS_TRUE",param.getWeatherStart())
                .set("IZ_END",param.getWeatherCompleted()).set("FROM_DATE",start).set("TO_DATE",end)
                .set("PM_PROGRESS_WEEKLY_ID",weekId).set("PM_PROGRESS_WEEKLY_PRJ_ID",weekPrjId)
                .set("AD_USER_ID",userId).set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",userId)
                .set("TS",now).set("SYS_TRUE",weatherStart).set("IZ_END",weatherCompleted).set("FILE_ID_TWO",param.getAerialImgId())
                .exec();
        //主表更新，该项目该周已更新
        Crud.from("PM_PROGRESS_WEEKLY_PRJ").where().eq("id",weekPrjId).update()
                .set("IZ_WRITE",1)
                .set("AD_USER_ID",userId).set("LAST_MODI_USER_ID",userId)
                .set("TS",now).set("LAST_MODI_DT",now)
                .exec();
        handleStart(projectId,weekPrjId,id,weatherStart);
        handleComplete(projectId,weekPrjId,id,weatherCompleted);
        // 更新项目航拍图信息
        updatePrjImg(projectId,aerialImg,id);
        // 保存修改项目问题
        List<PmProgressWeeklyPrjProblemDetailView> proList = param.getProblemDetailList();
        updateProDetail(projectId,weekPrjId,proList,weekId);
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
                "a.TEXT_REMARK_ONE as progressRemark,ifnull(e.IZ_START_REQUIRE,'1') as weatherStart,ifnull(e.IZ_END,'0') as weatherCompleted," +
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
                weekMessage.setProgress(StringUtil.valueNullToBig(JdbcMapUtil.getString(tmp,"progress")));
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
                    weekMessage.setFileId(fileId);
                    weekMessage.setFileList(BaseFileExt.getFile(fileId));
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

    /**
     * 形象进度周报-项目施工进度问题汇总-列表
     */
    public void prjWeeklyProgressSum(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        WeekMessage param = JsonUtil.fromJson(json,WeekMessage.class);
        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;

        String weekId = param.getWeekId(); // 周id
        String pushProblemTypeId = param.getPrjPushProblemTypeId();
        LinkedHashMap<String,String> typeMap = getTypeMap(pushProblemTypeId,myJdbcTemplate);
        List<String> resArr = typeMap.values().stream().collect(Collectors.toList());
        String keyIds = String.join("','",typeMap.keySet().stream().collect(Collectors.toList()));
        resArr.add(0,"项目名称");
        resArr.add("projectId");
        //查询当前页项目id

        StringBuilder sb = new StringBuilder("select group_concat(a.prjIds SEPARATOR ''',''') as prjIds from ( select a.pm_prj_id as prjIds,ifnull(b.IZ_END,'0') as weatherCompleted,ifnull(b.IZ_START_REQUIRE,'1') as weatherStart from pm_progress_weekly_prj_problem_detail a left join pm_prj b on a.pm_prj_id = b.id where a.PM_PROGRESS_WEEKLY_ID = ? ");
        StringBuilder sb1 = new StringBuilder("select count(*) as num from (SELECT DISTINCT a.pm_prj_id from pm_progress_weekly_prj_problem_detail a where a.PM_PROGRESS_WEEKLY_ID = ? ");
        if (StringUtils.hasText(param.getProjectId())){
            sb.append(" and a.pm_prj_id in ('").append(param.getProjectId().replace(",","','")).append("')");
            sb1.append(" and a.pm_prj_id in ('").append(param.getProjectId().replace(",","','")).append("')");
        }
        if (StringUtils.hasText(pushProblemTypeId)){
            sb.append("and a.PRJ_PUSH_PROBLEM_TYPE_ID in ('").append(keyIds).append("')");
            sb1.append("and a.PRJ_PUSH_PROBLEM_TYPE_ID in ('").append(keyIds).append("')");
        }
        sb1.append(" GROUP BY a.PM_PRJ_ID ) a ");
        sb.append(" GROUP BY a.PM_PRJ_ID order by weatherCompleted asc,weatherStart desc,a.pm_prj_id desc ").append(limit).append(" ) a");
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sb.toString(),weekId);
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sb1.toString(),weekId);
        if (!CollectionUtils.isEmpty(list1)){
            String prjIds = JdbcMapUtil.getString(list1.get(0),"prjIds");
            StringBuilder sb2 = new StringBuilder("select a.projectName as '项目名称',any_value(a.projectId) as projectId,");
            for (String key: typeMap.keySet()){
                sb2.append("ifnull(group_concat(case when a.typeId = '").append(key).append("' then a.describeValue else null END SEPARATOR ''),'未涉及') AS '").append(typeMap.get(key)).append("',");
            }
            sb2.deleteCharAt(sb2.length()-1);
            sb2.append(" from ( select c.name as projectName,a.pm_prj_id as projectId,a.TEXT_REMARK_ONE as describeValue,a.ts,")
                    .append("a.PRJ_PUSH_PROBLEM_TYPE_ID as typeId,(select name from gr_set_value where id = a.PRJ_PUSH_PROBLEM_TYPE_ID) as typeName ")
                    .append("from pm_progress_weekly_prj_problem_detail a left join pm_prj c on a.PM_PRJ_ID = c.id where ")
                    .append("a.PM_PROGRESS_WEEKLY_ID = ? ");
            if (StringUtils.hasText(prjIds)){
                sb2.append(" and a.pm_prj_id in ('").append(prjIds).append("') ");
            }
            if (StringUtils.hasText(pushProblemTypeId)){
                sb2.append(" and a.PRJ_PUSH_PROBLEM_TYPE_ID in ('").append(keyIds).append("') ");
            }
            sb2.append("ORDER BY a.ts desc ) a GROUP BY a.projectName ORDER BY any_value(a.ts) desc ");
            List<Map<String,Object>> list3 = myJdbcTemplate.queryForList(sb2.toString(),weekId);
            if (!CollectionUtils.isEmpty(list3)){
                Map<String,Object> resMap = new HashMap<>();
                resMap.put("total",Integer.valueOf(JdbcMapUtil.getString(list2.get(0),"num")));
                resMap.put("header",resArr);
                resMap.put("list",list3);
                Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resMap), Map.class);
                ExtJarHelper.returnValue.set(outputMap);
            }
        } else {
            ExtJarHelper.returnValue.set(null);
        }
    }

    /**
     * 准备汇总统计表头信息
     * @param pushProblemTypeId 类型id
     * @param myJdbcTemplate 数据源
     * @return 汇总集合
     */
    private LinkedHashMap<String, String> getTypeMap(String pushProblemTypeId, MyJdbcTemplate myJdbcTemplate) {
        LinkedHashMap<String,String> typeMap = new LinkedHashMap<>();
        if (!StringUtils.hasText(pushProblemTypeId)){
            List<GrSetValue> typeList = GrSetValue.selectByWhere(new Where().eq("GR_SET_ID","1679759005775429632")
                    .eq("STATUS","AP")).stream().sorted(Comparator.comparing(GrSetValue::getSeqNo)).collect(Collectors.toList());
            getMap(typeMap,typeList);
        } else {
            Map<String, Object> mapSql = new HashMap<>();
            pushProblemTypeId = pushProblemTypeId.replace(",","','");
            List<Map<String,Object>> resMap = myJdbcTemplate.queryForList("select id,name from gr_set_value where status = 'ap' and id in ('" + pushProblemTypeId + "') order by seq_no asc");
            getMapByList(typeMap,resMap);
        }
        return typeMap;
    }

    /**
     * 有序放入
     * @param linkedHashMap 项目问题有序集合
     * @param resMap 数据源值
     * @return 结果值
     */
    private LinkedHashMap<String, String> getMapByList(LinkedHashMap<String, String> linkedHashMap, List<Map<String, Object>> resMap) {
        for (Map<String, Object> map : resMap) {
            String key = JdbcMapUtil.getString(map,"id");
            String value = JdbcMapUtil.getString(map,"name");
            linkedHashMap.put(key,value);
        }
        return linkedHashMap;
    }


    /**
     * 有序放入
     * @param linkedHashMap 项目问题有序集合
     * @param list 数据源值
     * @return 结果值
     */
    private LinkedHashMap<String, String> getMap(LinkedHashMap<String,String> linkedHashMap, List<GrSetValue> list) {
        for (GrSetValue tmp : list) {
            linkedHashMap.put(tmp.getId(),tmp.getName());
        }
        return linkedHashMap;
    }
}
