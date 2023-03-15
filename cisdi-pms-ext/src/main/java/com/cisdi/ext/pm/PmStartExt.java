package com.cisdi.ext.pm;

import com.cisdi.ext.util.*;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
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
                " PM_CODE," +
                "ROUND(ifnull(PRJ_TOTAL_INVEST, 0 ),2) AS PRJ_TOTAL_INVEST," +
                " gsv.`NAME` AS PROJECT_TYPE_ID," +
                " pp.`NAME` AS BUILDER_UNIT," +
                " ps.START_TIME," +
                " ps.AGENT," +
                " ss.`NAME` AS START_STATUS ," +
                " au.`name` as agentValue " +
                "FROM " +
                " PRJ_START ps  " +
                " LEFT JOIN gr_set_value gsv ON gsv.id = ps.PROJECT_TYPE_ID " +
                " LEFT JOIN pm_party pp ON ps.BUILDER_UNIT = pp.id " +
                " LEFT JOIN gr_set_value ss ON ss.id = ps.PRJ_START_STATUS_ID  " +
                " left join ad_user au on au.id = ps.AGENT " +
                "WHERE " +
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
                " pj.ID as projectId " +
                "FROM " +
                " PRJ_START ps " +
                " left join gr_set_value gg on gg.id = ps.INVESTMENT_SOURCE_ID " +
                " LEFT JOIN gr_set_value gsv ON gsv.id = ps.PROJECT_TYPE_ID " +
                " LEFT JOIN pm_party pp ON ps.BUILDER_UNIT = pp.id " +
                " LEFT JOIN gr_set_value ss ON ss.id = ps.PRJ_START_STATUS_ID " +
                " LEFT JOIN ad_user au ON au.id = ps.AGENT " +
                " LEFT JOIN PM_PRJ pj ON pj.PM_CODE = ps.PM_CODE " +
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
                pmStart.parcels = getParcel(pmStart.projectId);
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
        String dataStatus = "";
        Date startTime = new Date();
        if (Strings.isNullOrEmpty(input.id)) {
            id = Crud.from("PRJ_START").insertData();
            prjCode = PmPrjCodeUtil.getPrjCode();
            dataStatus = "add";
        }

        Crud.from("PRJ_START").where().eq("ID", id).update()
                .set("PM_CODE", prjCode).set("NAME", input.name).set("PRJ_TOTAL_INVEST", input.invest).set("PROJECT_TYPE_ID", input.typeId)
                .set("BUILDER_UNIT", input.unit).set("START_TIME", startTime).set("AGENT", input.userId).set("PRJ_START_STATUS_ID", "1626110930922467328")
                .set("ATT_FILE_GROUP_ID", input.fileIds).set("INVESTMENT_SOURCE_ID", input.sourceTypeId).set("PRJ_SITUATION", input.description).exec();
        String projectId = "";
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
            Crud.from("PM_PRJ").where().eq("ID", projectId).update().set("NAME", input.name).set("PM_CODE", prjCode)
                    .set("INVESTMENT_SOURCE_ID", input.sourceTypeId).set("PROJECT_TYPE_ID", input.typeId).set("BUILDER_UNIT", input.unit).set("CUSTOMER_UNIT", input.unit)
                    .set("PRJ_SITUATION", input.description).set("PM_SEQ", seq).set("BUILDER_UNIT",input.unit)
                    .set("ESTIMATED_TOTAL_INVEST", input.invest.multiply(new BigDecimal(10000))).exec();
        } else {
            projectId = String.valueOf(list.get(0).get("ID"));
            Crud.from("PM_PRJ").where().eq("ID", projectId).update().set("NAME", input.name).set("PM_CODE", prjCode)
                    .set("INVESTMENT_SOURCE_ID", input.sourceTypeId).set("PROJECT_TYPE_ID", input.typeId).set("BUILDER_UNIT", input.unit).set("CUSTOMER_UNIT", input.unit)
                    .set("PRJ_SITUATION", input.description).set("BUILDER_UNIT",input.unit)
                    .set("ESTIMATED_TOTAL_INVEST", input.invest.multiply(new BigDecimal(10000))).exec();
        }

        //先删除项目关联的地块
        myJdbcTemplate.update("delete from PRJ_PARCEL where PM_PRJ_ID=?", projectId);
        myJdbcTemplate.update("delete from parcel_point where PARCEL_ID in (select PARCEL_ID from PRJ_PARCEL where PM_PRJ_ID =?)", projectId);
        myJdbcTemplate.update("delete from PARCEL where id in (select PARCEL_ID from PRJ_PARCEL where PM_PRJ_ID =?)", projectId);

        //再新增 ---新增地块
        List<parcel> parcelList = input.parcels;
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
        if ("add".equals(dataStatus)) {
            //新增项目进展
            PrjPlanUtil.createPlan(projectId);
            //刷新进度节点时间
            PrjPlanUtil.refreshProPlanTime(projectId, startTime);
        }
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

        public List<parcel> parcels;

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

    public static void main(String[] args) {
        String str = "23022402";

    }

}
