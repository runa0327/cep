package com.cisdi.ext.pm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.PmPostAppoint;
import com.cisdi.ext.util.*;
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
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmStartExt
 * @package com.cisdi.ext.pm
 * @description 项目启动
 * @date 2023/2/16
 */
public class PmStartExt {

    /**
     * 项目启动列表查询
     */
    public void pmStartList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        int pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        int pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT " +
                " ps.ID AS ID," +
                " ps.`NAME` AS `NAME`," +
                " ps.PM_CODE," +
                "ROUND(ifnull(PRJ_TOTAL_INVEST, 0 ),2) AS PRJ_TOTAL_INVEST," +
                " gsv.`NAME` AS PROJECT_TYPE_ID," +
                " pp.`NAME` AS BUILDER_UNIT," +
                " ps.START_TIME," +
                " ps.AGENT," +
                " ps.AD_USER_ID,"+
                " ps.BASE_LOCATION_ID,"+
                " ps.PLAN_START_TIME,"+
                " ps.PLAN_END_TIME,"+
                " ss.`NAME` AS START_STATUS ," +
                " au.`name` as agentValue, " +
                " gg.`NAME` as tender_way, " +
                " ps.INVESTMENT_SOURCE_ID, " +
                " pj.id as project_id, " +
                " pj.name as project_name ,"+
                " pj.ver as project_ver "+
                "FROM " +
                " PRJ_START ps  " +
                " LEFT JOIN gr_set_value gsv ON gsv.id = ps.PROJECT_TYPE_ID " +
                " LEFT JOIN pm_party pp ON ps.BUILDER_UNIT = pp.id " +
                " LEFT JOIN gr_set_value ss ON ss.id = ps.PRJ_START_STATUS_ID  " +
                " left join ad_user au on au.id = ps.AGENT " +
                " left join gr_set_value gg on gg.id = ps.TENDER_MODE_ID " +
                " left join pm_prj pj on pj.pm_code = ps.pm_code " +
                " WHERE " +
                " ps.`STATUS` = 'ap'");
        if (!StringUtils.isEmpty(map.get("projectName"))) {
            sb.append(" and ps.`name` like '%").append(map.get("projectName")).append("%'");
        }
        if (!StringUtils.isEmpty(map.get("status"))) {
            sb.append(" and PRJ_START_STATUS_ID=").append(map.get("status"));
        }
        sb.append(" order by ps.CRT_DT desc");
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<PmStart> dataList = list.stream().map(m -> {
            PmStart pmStart = new PmStart();
            pmStart.id = JdbcMapUtil.getString(m, "ID");
            pmStart.name = JdbcMapUtil.getString(m, "NAME");
            pmStart.code = JdbcMapUtil.getString(m, "PM_CODE");
            pmStart.invest = JdbcMapUtil.getString(m, "PRJ_TOTAL_INVEST");
            pmStart.type = JdbcMapUtil.getString(m, "PROJECT_TYPE_ID");
            pmStart.unit = JdbcMapUtil.getString(m, "BUILDER_UNIT");
            pmStart.startTime = StringUtil.withOutT(JdbcMapUtil.getString(m, "START_TIME"));
            pmStart.agent = JdbcMapUtil.getString(m, "AGENT");
            pmStart.status = JdbcMapUtil.getString(m, "START_STATUS");
            pmStart.agentValue = JdbcMapUtil.getString(m, "agentValue");
            pmStart.tenderWay = JdbcMapUtil.getString(m, "tender_way");
            pmStart.sourceTypeId = JdbcMapUtil.getString(m, "INVESTMENT_SOURCE_ID");
            String projectId = JdbcMapUtil.getString(m, "project_id");
            pmStart.projectId = projectId;
            List<PmPostAppoint> postList = PmPostAppoint.selectByWhere(new Where().eq(PmPostAppoint.Cols.PM_PRJ_ID,projectId)
                    .nin(PmPostAppoint.Cols.STATUS,"VD,VDING"));
            if (!CollectionUtils.isEmpty(postList)){
                pmStart.postProTrue = 1;
            } else {
                pmStart.postProTrue = 0;
            }
            pmStart.projectId = JdbcMapUtil.getString(m, "project_id");
            pmStart.projectName = JdbcMapUtil.getString(m, "project_name");
            pmStart.projectVer = JdbcMapUtil.getString(m, "project_ver");
            return pmStart;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(dataList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide outSide = new OutSide();
            outSide.total = totalList.size();
            outSide.startList = dataList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 项目启动详情
     */
    public void pmStartView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("SELECT " +
                " ps.ID AS ID," +
                " ps.`NAME` AS `NAME`," +
                " ps.PM_CODE as PM_CODE, " +
                " ps.INVESTMENT_SOURCE_ID as INVESTMENT_SOURCE_ID, " +
                " gg.`NAME` as sourceTypeValue," +
                " round(ifnull( PRJ_TOTAL_INVEST, 0 ),2) AS PRJ_TOTAL_INVEST," +
                " ps.PROJECT_TYPE_ID as PROJECT_TYPE_ID," +
                " gsv.`NAME` AS typeValue," +
                " ps.BUILDER_UNIT as BUILDER_UNIT," +
                " pp.`NAME` AS unitValue," +
                " ps.START_TIME," +
                " ps.AGENT," +
                " ss.`NAME` AS START_STATUS," +
                " ps.PRJ_SITUATION as PRJ_SITUATION, " +
                " ps.ATT_FILE_GROUP_ID as ATT_FILE_GROUP_ID, " +
                " au.`NAME` AS agentValue, " +
                " pj.ID as projectId , " +
                " ps.TENDER_MODE_ID ," +
                " gq.`NAME` as tender_way, " +
                " ps.START_REMARK as  START_REMARK ,ps.PRJ_START_STATUS_ID as PRJ_START_STATUS_ID,ps.LOCATION_INFO as LOCATION_INFO " +
                "FROM " +
                " PRJ_START ps " +
                " left join gr_set_value gg on gg.id = ps.INVESTMENT_SOURCE_ID " +
                " LEFT JOIN gr_set_value gsv ON gsv.id = ps.PROJECT_TYPE_ID " +
                " LEFT JOIN pm_party pp ON ps.BUILDER_UNIT = pp.id " +
                " LEFT JOIN gr_set_value ss ON ss.id = ps.PRJ_START_STATUS_ID " +
                " LEFT JOIN ad_user au ON au.id = ps.AGENT " +
                " LEFT JOIN PM_PRJ pj ON pj.PM_CODE = ps.PM_CODE " +
                " left join gr_set_value gq on gq.id = ps.TENDER_MODE_ID" +
                " WHERE " +
                " ps.`STATUS` = 'ap' and ps.id=?", map.get("id"));
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<PmStart> dataList = list.stream().map(m -> {
                PmStart pmStart = new PmStart();
                pmStart.id = JdbcMapUtil.getString(m, "ID");
                pmStart.name = JdbcMapUtil.getString(m, "NAME");
                pmStart.code = JdbcMapUtil.getString(m, "PM_CODE");
                pmStart.sourceTypeId = JdbcMapUtil.getString(m, "INVESTMENT_SOURCE_ID");
                pmStart.invest = JdbcMapUtil.getString(m, "PRJ_TOTAL_INVEST");
                pmStart.type = JdbcMapUtil.getString(m, "PROJECT_TYPE_ID");
                pmStart.unit = JdbcMapUtil.getString(m, "BUILDER_UNIT");
                pmStart.description = JdbcMapUtil.getString(m, "PRJ_SITUATION");
                pmStart.startTime = StringUtil.withOutT(JdbcMapUtil.getString(m, "START_TIME"));
                pmStart.agent = JdbcMapUtil.getString(m, "AGENT");
                pmStart.status = JdbcMapUtil.getString(m, "START_STATUS");
                pmStart.fileInfoList = this.getFileList(JdbcMapUtil.getString(m, "ATT_FILE_GROUP_ID"));
                pmStart.sourceTypeValue = JdbcMapUtil.getString(m, "sourceTypeValue");
                pmStart.typeValue = JdbcMapUtil.getString(m, "typeValue");
                pmStart.unitValue = JdbcMapUtil.getString(m, "unitValue");
                pmStart.agentValue = JdbcMapUtil.getString(m, "agentValue");
                pmStart.projectId = JdbcMapUtil.getString(m, "projectId");
                pmStart.parcels = JSONObject.parseArray(JdbcMapUtil.getString(m, "LOCATION_INFO"), parcel.class);
                pmStart.tenderWayId = JdbcMapUtil.getString(m, "TENDER_MODE_ID");
                pmStart.tenderWay = JdbcMapUtil.getString(m, "tender_way");
                pmStart.startRemark = JdbcMapUtil.getString(m, "START_REMARK");
                pmStart.statusId = JdbcMapUtil.getString(m, "PRJ_START_STATUS_ID");
                return pmStart;
            }).collect(Collectors.toList());
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(dataList.get(0)), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 新增/修改项目启动
     */
    public void pmStartModify() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        inputData input = JsonUtil.fromJson(json, inputData.class);
        String id = input.id;
        String prjCode = input.code;
        String status = input.status;
        if (Strings.isNullOrEmpty(input.id)) {
            id = Crud.from("PRJ_START").insertData();
            prjCode = PmPrjCodeUtil.getPrjCode();
        }
        if (Strings.isNullOrEmpty(status)) {
            status = "1636549534274465792";
        }
        List<parcel> parcels = input.parcels;
        String location = JSON.toJSON(parcels).toString();
        Crud.from("PRJ_START").where().eq("ID", id).update()
                .set("PM_CODE", prjCode).set("NAME", input.name).set("PRJ_TOTAL_INVEST", input.invest).set("PROJECT_TYPE_ID", input.typeId).set("TENDER_MODE_ID", input.tenderWay)
                .set("BUILDER_UNIT", input.unit).set("START_TIME", input.startTime).set("AGENT", input.userId).set("PRJ_START_STATUS_ID", status).set("START_REMARK", input.startRemark)
                .set("ATT_FILE_GROUP_ID", input.fileIds).set("INVESTMENT_SOURCE_ID", input.sourceTypeId).set("PRJ_SITUATION", input.description).set("START_TIME", input.startTime)
                .set("LOCATION_INFO", location).set("AD_USER_ID",input.qqUserId).set("BASE_LOCATION_ID",input.locationId).set("PLAN_START_TIME",input.planStartTime).set("PLAN_END_TIME",input.planEndTime).exec();
    }

    /**
     * 删除
     */
    public void delPmStart() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("delete from PRJ_START where id=?", map.get("id"));
    }


    /**
     * 启动/取消启动
     */
    public void pmStartChangeStatus() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from gr_set_value where name=?", map.get("status"));
        if (!CollectionUtils.isEmpty(list)) {
            if (!"项目取消".equals(map.get("status"))) {
                List<Map<String, Object>> list1 = myJdbcTemplate.queryForList("select * from PRJ_START where id=?", map.get("id"));
                if (!CollectionUtils.isEmpty(list1)) {
                    this.createOtherInfo(list1.get(0));
                }
            }
            myJdbcTemplate.update("update PRJ_START set PRJ_START_STATUS_ID=? where id=?", list.get(0).get("ID"), map.get("id"));
        }

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

    /**
     * 创建项目进展等信息
     *
     * @param dataMap
     */
    private void createOtherInfo(Map<String, Object> dataMap) {
        String projectId = "";
        String prjCode = JdbcMapUtil.getString(dataMap, "PM_CODE");
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 新增项目---如果存在则修改项目
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_PRJ where PM_CODE=?", prjCode);
        if (CollectionUtils.isEmpty(list)) {
            projectId = Crud.from("PM_PRJ").insertData();
            int seq = 1;
            List<Map<String, Object>> list1 = myJdbcTemplate.queryForList("select pm_seq from pm_prj order by PM_SEQ desc limit 0,1");
            if (list1 != null && list1.size() > 0) {
                seq = Integer.parseInt(String.valueOf(list1.get(0).get("pm_seq"))) + 1;
            }
            Crud.from("PM_PRJ").where().eq("ID", projectId).update().set("NAME", dataMap.get("NAME")).set("PM_CODE", prjCode)
                    .set("INVESTMENT_SOURCE_ID", dataMap.get("INVESTMENT_SOURCE_ID")).set("PROJECT_TYPE_ID", dataMap.get("PROJECT_TYPE_ID")).set("BUILDER_UNIT", dataMap.get("BUILDER_UNIT"))
                    .set("CUSTOMER_UNIT", dataMap.get("BUILDER_UNIT")).set("PRJ_SITUATION", dataMap.get("PRJ_SITUATION")).set("PM_SEQ", seq).set("TENDER_MODE_ID", dataMap.get("TENDER_MODE_ID"))
                    .set("ESTIMATED_TOTAL_INVEST", dataMap.get("PRJ_TOTAL_INVEST")).set("BASE_LOCATION_ID",dataMap.get("BASE_LOCATION_ID")).exec();
        } else {
            projectId = String.valueOf(list.get(0).get("ID"));
            Crud.from("PM_PRJ").where().eq("ID", projectId).update().set("NAME", dataMap.get("NAME")).set("PM_CODE", prjCode)
                    .set("INVESTMENT_SOURCE_ID", dataMap.get("INVESTMENT_SOURCE_ID")).set("PROJECT_TYPE_ID", dataMap.get("PROJECT_TYPE_ID")).set("BUILDER_UNIT", dataMap.get("BUILDER_UNIT"))
                    .set("CUSTOMER_UNIT", dataMap.get("BUILDER_UNIT")).set("PRJ_SITUATION", dataMap.get("PRJ_SITUATION")).set("TENDER_MODE_ID", dataMap.get("TENDER_MODE_ID"))
                    .set("ESTIMATED_TOTAL_INVEST", dataMap.get("PRJ_TOTAL_INVEST")).set("BASE_LOCATION_ID",dataMap.get("BASE_LOCATION_ID")).exec();
        }

        //先删除项目关联的地块
        myJdbcTemplate.update("delete from PRJ_PARCEL where PM_PRJ_ID=?", projectId);
        myJdbcTemplate.update("delete from parcel_point where PARCEL_ID in (select PARCEL_ID from PRJ_PARCEL where PM_PRJ_ID =?)", projectId);
        myJdbcTemplate.update("delete from PARCEL where id in (select PARCEL_ID from PRJ_PARCEL where PM_PRJ_ID =?)", projectId);

        //再新增 ---新增地块
        List<parcel> parcelList = JSONObject.parseArray(JdbcMapUtil.getString(dataMap, "LOCATION_INFO"), parcel.class);
        for (parcel parcel : parcelList) {
            List<Point> pointList = parcel.pointList;
            String parcelId = Crud.from("PARCEL").insertData();

            List<List<BigDecimal>> param = pointList.stream().map(p -> {
                List<BigDecimal> b = new ArrayList<>();
                b.add(p.longitude);
                b.add(p.latitude);
                return b;
            }).collect(Collectors.toList());
            List<BigDecimal> bigDecimalList = ParcelUtil.getCenter(param, parcel.parcelShape);
            Object centerLongitude = null;
            Object centerLatitude = null;
            if (!CollectionUtils.isEmpty(bigDecimalList)) {
                centerLongitude = bigDecimalList.get(0);
                centerLatitude = bigDecimalList.get(1);
            }

            Crud.from("PARCEL").where().eq("ID", parcelId).update().set("FILL", parcel.fill).set("AREA", parcel.area)
                    .set("PLOT_RATIO", parcel.plotRatio).set("IDENTIFIER", parcel.identifier).set("PARCEL_SHAPE", parcel.parcelShape)
                    .set("CENTER_LONGITUDE", centerLongitude).set("CENTER_LATITUDE", centerLatitude).exec();

            String aaaid = Crud.from("PRJ_PARCEL").insertData();
            Crud.from("PRJ_PARCEL").where().eq("ID", aaaid).update().set("PM_PRJ_ID", projectId).set("PARCEL_ID", parcelId).exec();

            if ("Polygon".equals(parcel.parcelShape)) {
                Point first = pointList.get(0);
                pointList.add(first);
            }
            for (Point point : pointList) {
                String pointId = Crud.from("parcel_point").insertData();
                Crud.from("parcel_point").where().eq("ID", pointId).update().set("LONGITUDE", point.longitude).set("LATITUDE", point.latitude)
                        .set("PARCEL_ID", parcelId).exec();
            }
        }
        //新增项目进展
        PrjPlanUtil.createPlan(projectId, JdbcMapUtil.getString(dataMap, "PROJECT_TYPE_ID"), JdbcMapUtil.getString(dataMap, "INVESTMENT_SOURCE_ID")
                , BigDecimalUtil.divide(JdbcMapUtil.getBigDecimal(dataMap, "PRJ_TOTAL_INVEST"), new BigDecimal(10000)), JdbcMapUtil.getString(dataMap, "TENDER_MODE_ID"));
        //刷新进度节点时间
        PrjPlanUtil.refreshProPlanTime(projectId, JdbcMapUtil.getDate(dataMap, "START_TIME"));
        //发送本周任务
        sendWeekTask(projectId);

    }

    /**
     * 发送本周任务
     *
     * @param projectId
     */
    private void sendWeekTask(String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> nodeList = myJdbcTemplate.queryForList("select pm.`NAME` as prjName,pppn.*,pi.AD_USER_ID as default_user,pm.id as projectId from pm_pro_plan_node pppn \n" +
                "left join pm_pro_plan ppp on ppp.id = pppn.PM_PRO_PLAN_ID \n" +
                "left join pm_prj pm on pm.id = ppp.PM_PRJ_ID \n" +
                "left join POST_INFO pi on pi.id = pppn.POST_INFO_ID \n" +
                "where pm.id=?", projectId);
        if (!CollectionUtils.isEmpty(nodeList)) {
            List<Map<String, Object>> firstNodeList = nodeList.stream().filter(p -> 1 == JdbcMapUtil.getInt(p, "LEVEL")).sorted(Comparator.comparing(c -> JdbcMapUtil.getBigDecimal(c, "SEQ_NO"))).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(firstNodeList)) {
                Map<String, Object> firstNode = firstNodeList.get(0);
                List<Map<String, Object>> secondNodeList = nodeList.stream().filter(p -> Objects.equals(firstNode.get("ID"), p.get("PM_PRO_PLAN_NODE_PID"))).sorted(Comparator.comparing(c -> JdbcMapUtil.getBigDecimal(c, "SEQ_NO"))).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(secondNodeList)) {
                    Map<String, Object> secondNode = secondNodeList.get(0);
                    List<Map<String, Object>> threeNodeList = nodeList.stream().filter(p -> Objects.equals(secondNode.get("ID"), p.get("PM_PRO_PLAN_NODE_PID"))).sorted(Comparator.comparing(c -> JdbcMapUtil.getBigDecimal(c, "SEQ_NO"))).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(threeNodeList)) {
                        Map<String, Object> threeNode = threeNodeList.get(0);

                        String msg = "{0}【{1}】计划在{2}开始，请及时处理！";
                        String id = Crud.from("WEEK_TASK").insertData();

                        String userId = JdbcMapUtil.getString(threeNode, "CHIEF_USER_ID");
                        if (Objects.isNull(threeNode.get("CHIEF_USER_ID"))) {
                            userId = JdbcMapUtil.getString(threeNode, "default_user");
                        }
                        String processName = JdbcMapUtil.getString(threeNode, "NAME");
                        if (Objects.nonNull(threeNode.get("LINKED_WF_PROCESS_ID"))) {
                            //取流程名称
                            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from WF_PROCESS where id=?", threeNode.get("LINKED_WF_PROCESS_ID"));
                            if (!CollectionUtils.isEmpty(list)) {
                                Map<String, Object> dataMap = list.get(0);
                                processName = JdbcMapUtil.getString(dataMap, "NAME");
                            }
                        }
                        String dateOrg = "";
                        if (Objects.nonNull(threeNode.get("PLAN_COMPL_DATE"))) {
                            Date compDate = JdbcMapUtil.getDate(threeNode, "PLAN_COMPL_DATE");
                            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
                            dateOrg = sp.format(compDate);
                        }
                        String title = threeNode.get("prjName") + "-" + processName;
                        String content = MessageFormat.format(msg, threeNode.get("prjName"), processName, dateOrg);
                        myJdbcTemplate.update("update WEEK_TASK set AD_USER_ID=?,TITLE=?,CONTENT=?,PUBLISH_START=?,WEEK_TASK_STATUS_ID=?,WEEK_TASK_TYPE_ID=?,RELATION_DATA_ID=?,CAN_DISPATCH='0',PM_PRJ_ID=? where id=?",
                                userId, title, content, new Date(), "1634118574056542208", "1635080848313290752", threeNode.get("ID"), threeNode.get("projectId"), id);
                    }

                }

            }

        }
    }

    /**
     * 初始化项目编码
     */
    public void initPmCode() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_PRJ where status='ap' and PROJECT_SOURCE_TYPE_ID = '0099952822476441374' order by CRT_DT desc ");
        for (Map<String, Object> stringObjectMap : list) {
            String code = PmPrjCodeUtil.getPrjCode();
            Crud.from("PM_PRJ").where().eq("ID", stringObjectMap.get("ID")).update().set("PM_CODE", code).exec();
        }

    }

    /**
     * 测试用-刷新项目的时间
     */
    public void refreshNodeTime() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = JdbcMapUtil.getString(map, "projectId");
        Date paramDate = DateTimeUtil.stringToDate("2023-01-01");
        PrjPlanUtil.refreshProPlanTime(projectId, paramDate);
    }

    public static class OutSide {
        public Integer total;
        public List<PmStart> startList;
    }

    public static class PmStart {
        public String id;

        public String name;

        public String code;

        public String sourceTypeId;

        public String invest;

        public String type;

        public String unit;

        public String description;

        public String startTime;

        public String agent;

        public String status;

        public List<FileInfo> fileInfoList;

        public String sourceTypeValue;

        public String typeValue;

        public String unitValue;

        public String agentValue;

        public List<parcel> parcels;

        public String projectId;

        public String tenderWayId;

        public String tenderWay;

        public String startRemark;

        public String statusId;

        public String projectName;

        public String projectVer;

        //是否发起岗位指派流程 1已发起 0未发起
        public Integer postProTrue;

    }

    public static class inputData {
        public String id;

        public String name;

        public String sourceTypeId;

        public BigDecimal invest;

        public String typeId;

        public String unit;

        public String description;

        public String fileIds;

        public String userId;

        public String code;

        public String startTime;

        public List<parcel> parcels;

        public String tenderWay;

        public String startRemark;

        public String status;

        //前期报建岗人员
        public String qqUserId;

        //建设地点
        public String locationId;

        //计划开工时间
        public String planStartTime;

        //计划竣工时间
        public String planEndTime;
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

}
