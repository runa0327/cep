package com.cisdi.ext.pm;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.base.BaseThirdInterfaceDetailExt;
import com.cisdi.ext.commons.HttpClient;
import com.cisdi.ext.model.PmPostAppoint;
import com.cisdi.ext.model.PrjStart;
import com.cisdi.ext.pm.office.PmNodeAdjustReqExt;
import com.cisdi.ext.util.*;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
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
                " ps.AD_USER_ID," +
                " ps.BASE_LOCATION_ID," +
                " ps.PLAN_START_TIME," +
                " ps.PLAN_END_TIME," +
                " ss.`NAME` AS START_STATUS ," +
                " au.`name` as agentValue, " +
                " gg.`NAME` as tender_way, " +
                " ps.INVESTMENT_SOURCE_ID, " +
                " pj.id as project_id, " +
                " pj.name as project_name ," +
                " pj.ver as project_ver, " +
                " sv.`NAME` as PROJECT_CLASSIFICATION "+
                "FROM " +
                " PRJ_START ps  " +
                " LEFT JOIN gr_set_value gsv ON gsv.id = ps.PROJECT_TYPE_ID " +
                " LEFT JOIN pm_party pp ON ps.BUILDER_UNIT = pp.id " +
                " LEFT JOIN gr_set_value ss ON ss.id = ps.PRJ_START_STATUS_ID  " +
                " left join ad_user au on au.id = ps.AGENT " +
                " left join gr_set_value gg on gg.id = ps.TENDER_MODE_ID " +
                " left join pm_prj pj on pj.pm_code = ps.pm_code " +
                " left join gr_set_value sv on sv.id = ps.PROJECT_CLASSIFICATION_ID "+
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
            List<PmPostAppoint> postList = PmPostAppoint.selectByWhere(new Where().eq(PmPostAppoint.Cols.PM_PRJ_ID, projectId)
                    .nin(PmPostAppoint.Cols.STATUS, "VD,VDING"));
            if (!CollectionUtils.isEmpty(postList)) {
                pmStart.postProTrue = 1;
            } else {
                pmStart.postProTrue = 0;
            }
            pmStart.isNodeAdjust = PmNodeAdjustReqExt.getNodeAdjustByPrj(projectId);
            pmStart.projectId = JdbcMapUtil.getString(m, "project_id");
            pmStart.projectName = JdbcMapUtil.getString(m, "project_name");
            pmStart.projectVer = JdbcMapUtil.getString(m, "project_ver");
            pmStart.projectClassification = JdbcMapUtil.getString(m, "PROJECT_CLASSIFICATION");
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
                " ps.START_REMARK as  START_REMARK ,ps.PRJ_START_STATUS_ID as PRJ_START_STATUS_ID,ps.LOCATION_INFO as LOCATION_INFO, " +
                " ps.AD_USER_ID," +
                " aa.`NAME` as user_name ," +
                " ps.BASE_LOCATION_ID ," +
                " ggs.`NAME` as location," +
                " ps.PLAN_START_TIME," +
                " ps.PLAN_END_TIME, " +
                " ps.PROJECT_CLASSIFICATION_ID, "+
                " sv.`NAME` as PROJECT_CLASSIFICATION "+
                "FROM " +
                " PRJ_START ps " +
                " left join gr_set_value gg on gg.id = ps.INVESTMENT_SOURCE_ID " +
                " LEFT JOIN gr_set_value gsv ON gsv.id = ps.PROJECT_TYPE_ID " +
                " LEFT JOIN pm_party pp ON ps.BUILDER_UNIT = pp.id " +
                " LEFT JOIN gr_set_value ss ON ss.id = ps.PRJ_START_STATUS_ID " +
                " LEFT JOIN ad_user au ON au.id = ps.AGENT " +
                " LEFT JOIN PM_PRJ pj ON pj.PM_CODE = ps.PM_CODE " +
                " left join gr_set_value gq on gq.id = ps.TENDER_MODE_ID" +
                " left join ad_user aa on aa.id = ps.AD_USER_ID " +
                " left join gr_set_value ggs on ggs.id = ps.BASE_LOCATION_ID " +
                " left join gr_set_value sv on sv.id = ps.PROJECT_CLASSIFICATION_ID "+
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
                pmStart.qqUserId = JdbcMapUtil.getString(m, "AD_USER_ID");
                pmStart.qqUserName = JdbcMapUtil.getString(m, "user_name");
                pmStart.locationId = JdbcMapUtil.getString(m, "BASE_LOCATION_ID");
                pmStart.location = JdbcMapUtil.getString(m, "location");
                pmStart.planStartTime = JdbcMapUtil.getString(m, "PLAN_START_TIME");
                pmStart.planEndTime = JdbcMapUtil.getString(m, "PLAN_END_TIME");
                pmStart.projectClassificationId =  JdbcMapUtil.getString(m, "PROJECT_CLASSIFICATION_ID");
                pmStart.projectClassification =  JdbcMapUtil.getString(m, "PROJECT_CLASSIFICATION");
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
        String prjName = input.name;
        List<PrjStart> prjStartList = new ArrayList<>();
        if (!SharedUtil.isEmptyString(id)) {
            prjStartList = PrjStart.selectByWhere(new Where().eq(PrjStart.Cols.NAME, prjName)
                    .eq(PrjStart.Cols.STATUS, "AP").neq(PrjStart.Cols.ID, id));

        } else {
            prjStartList = PrjStart.selectByWhere(new Where().eq(PrjStart.Cols.NAME, prjName)
                    .eq(PrjStart.Cols.STATUS, "AP"));
        }
        if (!CollectionUtils.isEmpty(prjStartList)) {
            throw new BaseException("对不起，该项目已存在，请勿重复创建！");
        }
        if (Strings.isNullOrEmpty(input.id)) {
            id = Crud.from("PRJ_START").insertData();
            prjCode = PmPrjCodeUtil.getPrjCode();
        }
        if (Strings.isNullOrEmpty(status)) {
            status = "1636549534274465792";
        }
        String location = null;
        List<parcel> parcels = input.parcels;
        if (!CollectionUtils.isEmpty(parcels)) {
            location = JSON.toJSON(parcels).toString();
        }
        Crud.from("PRJ_START").where().eq("ID", id).update()
                .set("PM_CODE", prjCode).set("NAME", prjName).set("PRJ_TOTAL_INVEST", input.invest).set("PROJECT_TYPE_ID", input.typeId).set("TENDER_MODE_ID", input.tenderWay)
                .set("BUILDER_UNIT", input.unit).set("START_TIME", input.startTime).set("AGENT", input.userId).set("PRJ_START_STATUS_ID", status).set("START_REMARK", input.startRemark)
                .set("ATT_FILE_GROUP_ID", input.fileIds).set("INVESTMENT_SOURCE_ID", input.sourceTypeId).set("PRJ_SITUATION", input.description).set("START_TIME", input.startTime)
                .set("LOCATION_INFO", location).set("AD_USER_ID", input.qqUserId).set("BASE_LOCATION_ID", input.locationId).set("PLAN_START_TIME", input.planStartTime)
                .set("PLAN_END_TIME", input.planEndTime).set("PROJECT_CLASSIFICATION_ID", input.projectClassificationId).exec();
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
        String userId = ExtJarHelper.loginInfo.get().userId;
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from gr_set_value where name=?", map.get("status"));
        String id = map.get("id").toString();
        if (!CollectionUtils.isEmpty(list)) {
            if (!"项目取消".equals(map.get("status"))) {
                List<Map<String, Object>> list1 = myJdbcTemplate.queryForList("select * from PRJ_START where id=?", map.get("id"));
                if (!CollectionUtils.isEmpty(list1)) {
                    this.createOtherInfo(list1.get(0), id);
                }
                // 岗位指派流程自动发起
//                autoCreateProcess(userId, list1, myJdbcTemplate);
            }
            myJdbcTemplate.update("update PRJ_START set PRJ_START_STATUS_ID=? where id=?", list.get(0).get("ID"), id);
        }

    }

    /**
     * 岗位指派流程自动发起
     *
     * @param userId         当前操作人
     * @param list1          项目在项目启动表中信息
     * @param myJdbcTemplate 数据源
     */
    private void autoCreateProcess(String userId, List<Map<String, Object>> list1, MyJdbcTemplate myJdbcTemplate) {
        String projectName = JdbcMapUtil.getString(list1.get(0), "NAME");
        String sql = "select id from pm_prj where name = ? and status = 'AP' and project_source_type_id = '0099952822476441374'";
        List<Map<String, Object>> prjList = myJdbcTemplate.queryForList(sql, projectName);
        if (!CollectionUtils.isEmpty(prjList)) {
            String projectId = JdbcMapUtil.getString(prjList.get(0), "id");
            new Thread(() -> {
                String id = IdUtil.getSnowflakeNextIdStr();
                Map<String, Object> canMap = new HashMap<>();
                canMap.put("projectId", projectId);
                canMap.put("interfaceId", id);
                canMap.put("createBy", userId);
                String str = HttpClient.urlencode(canMap);
                BaseThirdInterfaceDetailExt.insert(userId, str, id, "GET", "automaticPmPostAppoint", myJdbcTemplate);
            }).start();
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
     * @param id      项目启动id
     */
    private void createOtherInfo(Map<String, Object> dataMap, String id) {
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
            //--修改20230424--项目启动产生的项目默认为前期状态
            Crud.from("PM_PRJ").where().eq("ID", projectId).update().set("NAME", dataMap.get("NAME")).set("PM_CODE", prjCode)
                    .set("INVESTMENT_SOURCE_ID", dataMap.get("INVESTMENT_SOURCE_ID")).set("PROJECT_TYPE_ID", dataMap.get("PROJECT_TYPE_ID")).set("BUILDER_UNIT", dataMap.get("BUILDER_UNIT"))
                    .set("CUSTOMER_UNIT", dataMap.get("BUILDER_UNIT")).set("PRJ_SITUATION", dataMap.get("PRJ_SITUATION")).set("PM_SEQ", seq).set("TENDER_MODE_ID", dataMap.get("TENDER_MODE_ID"))
                    .set("ESTIMATED_TOTAL_INVEST", dataMap.get("PRJ_TOTAL_INVEST")).set("BASE_LOCATION_ID", dataMap.get("BASE_LOCATION_ID")).set("PROJECT_PHASE_ID", "0099799190825080706")
                    .set("IZ_FORMAL_PRJ", 1).set("PROJECT_STATUS", null).set("PLAN_START_TIME", dataMap.get("PLAN_START_TIME")).set("PLAN_END_TIME", dataMap.get("PLAN_END_TIME"))
                    .set("PROJECT_CLASSIFICATION_ID", dataMap.get("PROJECT_CLASSIFICATION_ID")).exec();
            //为项目添加清单
            PrjMaterialInventory.addPrjInventory(projectId);

            //新增项目区位图信息------BEGION-------
            List<parcel> parcelList = JSONObject.parseArray(JdbcMapUtil.getString(dataMap, "LOCATION_INFO"), parcel.class);
            if (!CollectionUtils.isEmpty(parcelList)) {
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
            }
            //新增项目区位图信息-------END------

            //初始化默认岗位-- 把默认岗位刷给新的项目
            initPrjPost(projectId, JdbcMapUtil.getString(dataMap, "BUILDER_UNIT"), JdbcMapUtil.getString(dataMap, "AD_USER_ID"));
            //新增项目进展
            PrjPlanUtil.createPlan(projectId, JdbcMapUtil.getString(dataMap, "PROJECT_TYPE_ID"), JdbcMapUtil.getString(dataMap, "INVESTMENT_SOURCE_ID")
                    , BigDecimalUtil.divide(JdbcMapUtil.getBigDecimal(dataMap, "PRJ_TOTAL_INVEST"), new BigDecimal(10000)), JdbcMapUtil.getString(dataMap, "TENDER_MODE_ID"));
            //刷新进度节点时间
            PrjPlanUtil.refreshProPlanTime(projectId, JdbcMapUtil.getDate(dataMap, "START_TIME"));
            //发送本周任务
            sendWeekTask(projectId);
            //项目id写入项目启动、项目谋划
            Crud.from("PRJ_START").where().eq("ID", id).update().set("PM_PRJ_ID", projectId).exec();
            Crud.from("PM_PLAN").where().eq("NAME", dataMap.get("NAME")).update().set("PM_PRJ_ID", projectId).set("IZ_DISPLAY", 0).exec();
            //自动写入计划运营库
            String planOperationId = Crud.from("PLAN_OPERATION").insertData();
            Crud.from("PLAN_OPERATION").where().eq("ID", planOperationId).update().set("PM_PRJ_ID", projectId).exec();
        } else {
            projectId = String.valueOf(list.get(0).get("ID"));
            Crud.from("PM_PRJ").where().eq("ID", projectId).update().set("PROJECT_STATUS", null).exec();
        }

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

                        String msg = "{0}【{1}】计划在{2}完成，请及时处理！";
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
                        myJdbcTemplate.update("update WEEK_TASK set AD_USER_ID=?,TITLE=?,CONTENT=?,PUBLISH_START=?,WEEK_TASK_STATUS_ID=?,WEEK_TASK_TYPE_ID=?,RELATION_DATA_ID=?,CAN_DISPATCH='0',PM_PRJ_ID=?,PLAN_COMPL_DATE=? where id=?",
                                userId, title, content, new Date(), "1634118574056542208", "1635080848313290752", threeNode.get("ID"), threeNode.get("projectId"), threeNode.get("PLAN_COMPL_DATE"), id);
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
        Date paramDate = DateTimeUtil.stringToDate(map.get("paramDate").toString());
        PrjPlanUtil.refreshProPlanTime(projectId, paramDate);
    }

    /**
     * 初始化项目岗位--刷新默认岗位
     */
    private void initPrjPost(String projectId, String customerUnit, String qqUserId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> rosterList = myJdbcTemplate.queryForList("select * from pm_roster where  PM_PRJ_ID=?", projectId);
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from post_info where SYS_TRUE='1'");
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(item -> {
                Optional<Map<String, Object>> optional = rosterList.stream().filter(p -> Objects.equals(p.get("POST_INFO_ID"), item.get("ID"))).findAny();
                if (!optional.isPresent()) {
                    String id = Crud.from("PM_ROSTER").insertData();
                    if (Objects.equals("AD_USER_TWELVE_ID", item.get("CODE"))) {
                        //处理前期报建岗
                        Crud.from("PM_ROSTER").where().eq("ID", id).update().set("PM_PRJ_ID", projectId)
                                .set("POST_INFO_ID", item.get("ID")).set("CUSTOMER_UNIT", customerUnit).set("AD_USER_ID", qqUserId).exec();
                    } else {
                        Crud.from("PM_ROSTER").where().eq("ID", id).update().set("PM_PRJ_ID", projectId)
                                .set("POST_INFO_ID", item.get("ID")).set("CUSTOMER_UNIT", customerUnit).exec();
                    }
                }
            });
        }
    }

    public static class OutSide {
        public Integer total;
        public List<PmStart> startList;

        public List<NodeInfo> nodeInfoList;
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

        //是否存在未审批完成全景计划展示表 1存在正在审批中0不存在
        public Integer isNodeAdjust;

        //前期报建岗人员
        public String qqUserId;

        //前期报建岗人员名称
        public String qqUserName;

        //建设地点ID
        public String locationId;

        //建设地点
        public String location;

        //计划开工时间
        public String planStartTime;

        //计划竣工时间
        public String planEndTime;

        public String projectClassificationId;

        public String projectClassification;
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

        //项目类别
        public String projectClassificationId;
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

    public static class NodeInfo {
        public String nodeId;
        public String nodeName;
        public String completeDate;
    }


    public static List<Map<String, Object>> getChildrenNode(Map<String, Object> parent, List<Map<String, Object>> allData) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        return allData.stream().filter(p -> Objects.equals(parent.get("id"), p.get("PM_PRO_PLAN_NODE_PID"))).peek(m -> {
            List<Map<String, Object>> sonList = getChildrenNode(m, allData);
            if (!CollectionUtils.isEmpty(sonList)) {
                Map<String, Object> topDate = sonList.get(0);
                String start = JdbcMapUtil.getString(topDate, "PLAN_START_DATE");
                if (!SharedUtil.isEmptyString(start)) {
                    String end = topDate.get("PLAN_COMPL_DATE").toString();
                    int cha = getDateCha(end, start);
                    myJdbcTemplate.update("update pm_pro_plan_node set PLAN_COMPL_DATE=?,PLAN_TOTAL_DAYS = ? where id=?", end, cha, m.get("id"));
                }
            }
        }).sorted(Comparator.comparing(o -> DateTimeUtil.stringToDate(JdbcMapUtil.getString((Map<String, Object>) o, "PLAN_COMPL_DATE"))).reversed()).collect(Collectors.toList());
    }

    /**
     * 全景时间调整审批后刷新节点时间
     * 传参 projectId-项目ID  id-全景时间调整台账ID
     */
    public static void handleData(String id, String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_NODE_ADJUST_REQ_DETAIL where PM_NODE_ADJUST_REQ_ID=?", id);
        if (!CollectionUtils.isEmpty(list)) {
            StringBuilder sb = new StringBuilder();
            list.forEach(item -> {
                sb.append("update pm_pro_plan_node set PLAN_COMPL_DATE='").append(item.get("PLAN_COMPL_DATE")).append("' where id='").append(item.get("PM_PRO_PLAN_NODE_ID")).append("' ;");
            });
            myJdbcTemplate.update(sb.toString());
        }
        // 刷新父节点时间 2023-06-01注释，暂不删除代码
//        List<Map<String, Object>> nodeList = myJdbcTemplate.queryForList("select pn.*,ifnull(PM_PRO_PLAN_NODE_PID,0) as pid from pm_pro_plan_node pn left join pm_pro_plan pl on pn.PM_PRO_PLAN_ID = pl.id where PM_PRJ_ID=?", projectId);
//        nodeList.stream().filter(p -> Objects.equals("0", p.get("pid"))).peek(m -> {
//            List<Map<String, Object>> sonList = getChildrenNode(m, nodeList);
//            if (!CollectionUtils.isEmpty(sonList)) {
//                Map<String, Object> topDate = sonList.get(0);
//                String start = JdbcMapUtil.getString(topDate,"PLAN_START_DATE");
//                if (!SharedUtil.isEmptyString(start)){
//                    String end = topDate.get("PLAN_COMPL_DATE").toString();
//                    int cha = getDateCha(end,start);
//                    myJdbcTemplate.update("update pm_pro_plan_node set PLAN_COMPL_DATE=?,PLAN_TOTAL_DAYS = ? where id=?", end,cha, m.get("id"));
//                }
//            }
//        }).sorted(Comparator.comparing(o -> DateTimeUtil.stringToDate(JdbcMapUtil.getString((Map<String, Object>) o, "PLAN_COMPL_DATE"))).reversed()).collect(Collectors.toList());
    }

    public static int getDateCha(String end, String start) {
        int cha = 0;
        try {
            cha = DateTimeUtil.getTwoTimeStringDays(end, start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cha;
    }

}
