package com.cisdi.ext.home;

import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.cisdi.ext.util.WeeklyUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProjectHomeExt
 * @package com.cisdi.ext.home
 * @description 项目首页接口
 * @date 2023/2/28
 */
public class ProjectHomeExt {


    /**
     * 项目信息
     */
    public void projectInfo() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pm.name as `NAME`,g1.name as mode,pm.PRJ_SITUATION as des,g2.name as location ,g3.`NAME` as source,g4.`NAME` as type,\n" +
                "PLAN_START_TIME,PLAN_END_TIME,g5.`NAME` as tender,ACTUAL_START_TIME,ACTUAL_END_TIME,\n" +
                "p1.`NAME` as js, p2.`NAME` as kc, p3.`NAME` as sj, p4.`NAME` as sg, p5.`NAME` as jl from pm_prj pm \n" +
                "left join gr_set_value g1 on g1.id = pm.PRJ_MANAGE_MODE_ID\n" +
                "left join gr_set_value g2 on g2.id = pm.BASE_LOCATION_ID\n" +
                "left join gr_set_value g3 on g3.id = pm.INVESTMENT_SOURCE_ID\n" +
                "left join gr_set_value g4 on g4.id = pm.PROJECT_TYPE_ID\n" +
                "left join gr_set_value g5 on g5.id = pm.TENDER_MODE_ID\n" +
                "left join pm_party p1 on p1.id = pm.BUILDER_UNIT\n" +
                "left join pm_party p2 on p2.id = pm.SURVEYOR_UNIT\n" +
                "left join pm_party p3 on p3.id = pm.DESIGNER_UNIT\n" +
                "left join pm_party p4 on p4.id = pm.CONSTRUCTOR_UNIT\n" +
                "left join pm_party p5 on p5.id = pm.SUPERVISOR_UNIT\n" +
                "where pm.id=?", map.get("projectId"));
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> mapDate = list.get(0);
            projectInfo info = new projectInfo();
            info.name = JdbcMapUtil.getString(mapDate, "NAME");
            info.mode = JdbcMapUtil.getString(mapDate, "mode");
            info.description = JdbcMapUtil.getString(mapDate, "des");
            info.location = JdbcMapUtil.getString(mapDate, "location");
            info.source = JdbcMapUtil.getString(mapDate, "source");
            info.type = JdbcMapUtil.getString(mapDate, "type");
            info.planBeginTime = StringUtil.withOutT(JdbcMapUtil.getString(mapDate, "PLAN_START_TIME"));
            info.planEndTime = StringUtil.withOutT(JdbcMapUtil.getString(mapDate, "PLAN_END_TIME"));
            info.tenderType = JdbcMapUtil.getString(mapDate, "tender");
            info.actualBeginTime = StringUtil.withOutT(JdbcMapUtil.getString(mapDate, "ACTUAL_START_TIME"));
            info.actualEndTime = StringUtil.withOutT(JdbcMapUtil.getString(mapDate, "ACTUAL_END_TIME"));
            info.jsUnit = JdbcMapUtil.getString(mapDate, "js");
            info.kcUnit = JdbcMapUtil.getString(mapDate, "kc");
            info.sjUnit = JdbcMapUtil.getString(mapDate, "sj");
            info.sgUnit = JdbcMapUtil.getString(mapDate, "sg");
            info.jlUnit = JdbcMapUtil.getString(mapDate, "jl");
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(info), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }

    /**
     * 项目总投资
     */
    public void totalInvest() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        BigDecimal totalAmt = BigDecimal.ZERO;
        BigDecimal jaAmt = BigDecimal.ZERO;
        BigDecimal jaCompleteAmt = BigDecimal.ZERO;
        List<Map<String, Object>> dataList = myJdbcTemplate.queryForList("select ifnull(pie.PRJ_TOTAL_INVEST,0) as PRJ_TOTAL_INVEST,pie.id as id from pm_invest_est pie \n" +
                "left join gr_set_value gsv on gsv.id = pie.INVEST_EST_TYPE_ID \n" +
                "where pie.PM_PRJ_ID=? and PRJ_TOTAL_INVEST<>0 \n" +
                "order by gsv.code desc limit 0,1", projectId);
        if (!CollectionUtils.isEmpty(dataList)) {
            Map<String, Object> mapData = dataList.get(0);
            totalAmt = new BigDecimal(String.valueOf(mapData.get("PRJ_TOTAL_INVEST")));
            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ifnull(amt,0) as amt from pm_invest_est_dtl dt left join pm_exp_type et on dt.PM_EXP_TYPE_ID = et.id where et.`CODE`='CONSTRUCT_AMT' and PM_INVEST_EST_ID=? ", mapData.get("id"));
            if (!CollectionUtils.isEmpty(list)) {
                Map<String, Object> jaMapData = list.get(0);
                jaAmt = new BigDecimal(String.valueOf(jaMapData.get("amt")));
            }

        }

        OutSide outSide = new OutSide();
        outSide.totalAmt = totalAmt.multiply(new BigDecimal(10000));
        outSide.jaAmt = jaAmt.multiply(new BigDecimal(10000));
        outSide.jaCompleteAmt = jaCompleteAmt.multiply(new BigDecimal(10000));
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }


    /**
     * 获取轮播图
     */
    public void getCarousel() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select PRJ_IMG from pm_prj where id=?", projectId);
        List<FileObj> fileObjList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            String fileIds = JdbcMapUtil.getString(list.get(0), "PRJ_IMG");
            if (Strings.isNotEmpty(fileIds)) {
                List<String> ids = Arrays.asList(fileIds.split(","));
                MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
                Map<String, Object> queryParams = new HashMap<>();// 创建入参map
                queryParams.put("ids", ids);
                List<Map<String, Object>> fileList = myNamedParameterJdbcTemplate.queryForList("select ff.ID as ID, DSP_NAME,SIZE_KB,UPLOAD_DTTM,au.`NAME` as USER_NAME,FILE_INLINE_URL,FILE_ATTACHMENT_URL from fl_file ff left join ad_user au on ff.CRT_USER_ID = au.id  where ff.id in (:ids)", queryParams);
                AtomicInteger index = new AtomicInteger(0);
                fileObjList = fileList.stream().map(p -> {
                    FileObj obj = new FileObj();
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
            }
            if (CollectionUtils.isEmpty(fileObjList)) {
                ExtJarHelper.returnValue.set(Collections.emptyMap());
            } else {
                OutSide outSide = new OutSide();
                outSide.fileObjList = fileObjList;
                Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
                ExtJarHelper.returnValue.set(outputMap);
            }
        }
    }


    /**
     * 工作台-本周工作情况
     */
    public void weekWork() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String userId = ExtJarHelper.loginInfo.get().userId;
        Map<String, String> dateMap = WeeklyUtils.weekBeginningAndEnding(new Date());
        WeekWork weekWork = new WeekWork();
        //发起流程
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select a.* from wf_process_instance a left join wf_process b on a.WF_PROCESS_ID = b.id " +
                "where a.status = 'AP' and b.status in ('AP','VDING') " +
                "and a.START_DATETIME >= ? and a.START_DATETIME <= ? ", dateMap.get("begin"), dateMap.get("end"));
        if (list != null && list.size() > 0) {
            weekWork.fqTotal = list.size();
            List<Map<String, Object>> userCount = list.stream().filter(p -> userId.equals(JdbcMapUtil.getString(p, "START_USER_ID"))).collect(Collectors.toList());
            weekWork.fqCount = userCount.size();
        }

        //处理流程
        List<Map<String, Object>> list1 = myJdbcTemplate.queryForList("select * from wf_process_instance a left join wf_process b on a.WF_PROCESS_ID = b.id " +
                        "left join wf_task c on a.id = c.WF_PROCESS_INSTANCE_ID where a.status = 'AP' and b.status in ('AP','VDING') " +
                        "and c.status = 'AP'  and a.START_DATETIME >= ? and a.START_DATETIME <= ? ",
                dateMap.get("begin"), dateMap.get("end"));
        if (list1 != null && list1.size() > 0) {
            weekWork.clTotal = list1.size();
            List<Map<String, Object>> userCount = list1.stream().filter(p -> userId.equals(JdbcMapUtil.getString(p, "START_USER_ID"))).collect(Collectors.toList());
            weekWork.clCount = userCount.size();
        }

        //进行中流程
        List<Map<String, Object>> list2 = myJdbcTemplate.queryForList("select * from wf_process_instance a left join wf_process b on a.WF_PROCESS_ID = b.id " +
                        "where a.status = 'AP' and b.status in ('AP','VDING')  " +
                        "and a.START_DATETIME >= ? and a.START_DATETIME <= ? and a.END_DATETIME is null",
                dateMap.get("begin"), dateMap.get("end"));
        if (list2 != null && list2.size() > 0) {
            weekWork.jxzTotal = list2.size();
            List<Map<String, Object>> userCount = list2.stream().filter(p -> userId.equals(JdbcMapUtil.getString(p, "START_USER_ID"))).collect(Collectors.toList());
            weekWork.jxzCount = userCount.size();
        }
        weekWork.cyCount = weekWork.clCount + weekWork.fqCount;
        weekWork.cyTotal = weekWork.clTotal + weekWork.fqTotal;
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(weekWork), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 获取项目地块信息
     */
    public void getMapData() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        List<parcel> parcelList = getParcel(projectId);
        if (CollectionUtils.isEmpty(parcelList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.parcelList = parcelList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    private List<parcel> getParcel(String projectId) {
        List<parcel> res = new ArrayList<>();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ID,FILL,IDENTIFIER,PLOT_RATIO,PARCEL_SHAPE,ifnull(AREA,0) as AREA,ifnull(CENTER_LATITUDE,0) as CENTER_LATITUDE,ifnull(CENTER_LONGITUDE,0) as CENTER_LONGITUDE from PARCEL where id in (select PARCEL_ID from PRJ_PARCEL where PM_PRJ_ID=?)", projectId);
        if (!CollectionUtils.isEmpty(list)) {
            res = list.stream().map(p -> {
                parcel parcel = new parcel();
                parcel.id = JdbcMapUtil.getString(p, "ID");
                parcel.fill = JdbcMapUtil.getString(p, "FILL");
                parcel.area = JdbcMapUtil.getBigDecimal(p, "AREA");
                parcel.plotRatio = JdbcMapUtil.getBigDecimal(p, "PLOT_RATIO");
                parcel.identifier = JdbcMapUtil.getString(p, "IDENTIFIER");
                parcel.parcelShape = JdbcMapUtil.getString(p, "PARCEL_SHAPE");
                parcel.centerLongitude = JdbcMapUtil.getBigDecimal(p, "CENTER_LONGITUDE");
                parcel.centerLatitude = JdbcMapUtil.getBigDecimal(p, "CENTER_LATITUDE");
                parcel.pointList = getPoint(parcel.id);
                return parcel;
            }).collect(Collectors.toList());
        }
        return res;
    }

    private List<Point> getPoint(String parcelId) {
        List<Point> res = new ArrayList<>();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PARCEL_POINT where PARCEL_ID=?", parcelId);
        if (!CollectionUtils.isEmpty(list)) {
            res = list.stream().map(p -> {
                Point point = new Point();
                point.latitude = JdbcMapUtil.getBigDecimal(p, "LATITUDE");
                point.longitude = JdbcMapUtil.getBigDecimal(p, "LONGITUDE");
                return point;
            }).distinct().collect(Collectors.toList());
        }
        return res;
    }


    public static class OutSide {
        public BigDecimal totalAmt;

        public BigDecimal jaAmt;

        public BigDecimal jaCompleteAmt;

        public List<FileObj> fileObjList;

        public List<parcel> parcelList;

    }

    public static class FileObj {
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

    public static class WeekWork {
        //发起流程
        public Integer fqCount = 0;

        public Integer fqTotal = 0;

        //处理流程
        public Integer clCount = 0;

        public Integer clTotal = 0;
        //进行中流程
        public Integer jxzCount = 0;

        public Integer jxzTotal = 0;
        //参与审批
        public Integer cyCount = 0;

        public Integer cyTotal = 0;
    }

    /**
     * 经纬度
     */
    public static class Point {
        public BigDecimal longitude;

        public BigDecimal latitude;
    }

    /**
     * 地块
     */
    public static class parcel {
        public String id;
        public String fill;

        public BigDecimal area;

        public BigDecimal plotRatio;

        public String identifier;

        public String parcelShape;

        public BigDecimal centerLongitude;

        public BigDecimal centerLatitude;

        public List<Point> pointList;
    }

    public static class projectInfo {
        public String name;
        public String mode;
        public String description;
        public String location;
        public String source;
        public String type;
        public String planBeginTime;
        public String planEndTime;
        public String tenderType;
        public String actualBeginTime;
        public String actualEndTime;
        public String jsUnit;
        public String kcUnit;
        public String sjUnit;
        public String sgUnit;
        public String jlUnit;
    }

    /**
     * 施工进度
     */
    public void constructionProgress() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select round(ifnull(VISUAL_PROGRESS,0),2) as VISUAL_PROGRESS,ifnull(FILE_ID_ONE,'') as FILE_ID_ONE,DATE,LAST_MODI_DT from PM_PROGRESS_WEEKLY_PRJ_DETAIL where PM_PRJ_ID=? order by LAST_MODI_DT desc", map.get("projectId"));
        if (!CollectionUtils.isEmpty(list)) {
            DataInfo info = new DataInfo();
            Map<String, Object> mapData = list.get(0);
            info.imageProcess = JdbcMapUtil.getString(mapData, "VISUAL_PROGRESS");
            List<FileObj> fileObjList = new ArrayList<>();
            List<String> fileIds = list.stream().map(p -> JdbcMapUtil.getString(p, "FILE_ID_ONE")).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(fileIds)) {
                String objs = Strings.join(fileIds, ',');
                List<String> ids = Arrays.asList(objs.split(","));
                MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
                Map<String, Object> queryParams = new HashMap<>();// 创建入参map
                queryParams.put("ids", ids);
                List<Map<String, Object>> fileList = myNamedParameterJdbcTemplate.queryForList("select ff.ID as ID, DSP_NAME,SIZE_KB,UPLOAD_DTTM,au.`NAME` as USER_NAME,FILE_INLINE_URL,FILE_ATTACHMENT_URL from fl_file ff left join ad_user au on ff.CRT_USER_ID = au.id  where ff.id in (:ids)", queryParams);
                AtomicInteger index = new AtomicInteger(0);
                fileObjList = fileList.stream().map(p -> {
                    FileObj obj = new FileObj();
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
                info.fileObjList = fileObjList;
            }
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(info), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }


    public static class DataInfo {
        public String imageProcess;

        public List<FileObj> fileObjList;
    }
}
