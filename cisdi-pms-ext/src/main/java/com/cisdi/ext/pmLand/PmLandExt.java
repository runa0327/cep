package com.cisdi.ext.pmLand;

import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmLandExt
 * @package com.cisdi.ext.pmLand
 * @description 征地拆迁
 * @date 2022/9/6
 */
public class PmLandExt {

    /**
     * 获取拆迁记录
     */
    public void pmLandRecord() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String name = param.name;
        int pageSize = param.pageSize;
        int pageIndex = param.pageIndex;

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuilder sb = new StringBuilder();
        sb.append("select ID,CODE,NAME,REMARK,DEMOLITION_COST,DEMOLITION_PROGRESS,ATT_FILE_GROUP_ID from PM_LAND_ACQUISITION_RECORD where 1=1 ");
        if (Strings.isNotEmpty(name)) {
            sb.append(" and `NAME` like '%").append(name).append("%'");
        }
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<PmLandRecord> records = list.stream().map(this::convertPmLandRecord).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(records)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide outSide = new OutSide();
            outSide.total = totalList.size();
            outSide.list = records;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 征地拆迁记录详情
     */
    public void pmLandRecordView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String recordId = param.recordId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        try {
            Map<String, Object> stringObjectMap = myJdbcTemplate.queryForMap("select ID,CODE,NAME,REMARK,DEMOLITION_COST,DEMOLITION_PROGRESS,ATT_FILE_GROUP_ID from PM_LAND_ACQUISITION_RECORD where id=?", recordId);
            PmLandRecord record = this.convertPmLandRecord(stringObjectMap);
            if (Strings.isNotEmpty(record.attFileGroupId)) {
                NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(Objects.requireNonNull(myJdbcTemplate.getDataSource()));
                String sql = "select * from fl_file where id in (:ids)";
                Map<String, Object> dataParam = new HashMap<>();
                dataParam.put("ids", Arrays.asList(record.attFileGroupId.split(",")));
                List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(sql, dataParam);
                List<FileData> fileDataList = list.stream().map(p -> {
                    FileData fileData = new FileData();
                    fileData.id = JdbcMapUtil.getString(p, "ID");
                    fileData.fileName = JdbcMapUtil.getString(p, "NAME");
                    fileData.url = JdbcMapUtil.getString(p, "FILE_INLINE_URL");
                    fileData.durl = JdbcMapUtil.getString(p, "FILE_ATTACHMENT_URL");
                    fileData.size = JdbcMapUtil.getString(p, "DSP_SIZE");
                    fileData.uploadDate = StringUtil.withOutT(JdbcMapUtil.getString(p, "UPLOAD_DTTM"));
                    fileData.fName = JdbcMapUtil.getString(p, "DSP_NAME");
                    return fileData;
                }).collect(Collectors.toList());
                record.fileDataList = fileDataList;
            }
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(record), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } catch (Exception e) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }

    /**
     * 拆迁信息-热点
     */
    public void pmLandInfo() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String recordId = param.recordId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ID,CODE,NAME,REMARK,POSITION_X,POSITION_Y,CPMS_UUID,PM_LAND_ACQUISITION_RECORD_ID,CPMS_ID from PM_LAND_ACQUISITION_INFO where PM_LAND_ACQUISITION_RECORD_ID=?", recordId);
        List<PmLandInfo> landInfoList = list.stream().map(p -> {
            PmLandInfo info = new PmLandInfo();
            info.id = JdbcMapUtil.getString(p, "ID");
            info.recordId = JdbcMapUtil.getString(p, "PM_LAND_ACQUISITION_RECORD_ID");
            info.name = JdbcMapUtil.getString(p, "NAME");
            return info;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(landInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.landInfoList = landInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 拆迁进度
     */
    public void pmLandPro() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String infoId = param.infoId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ID,CODE,NAME,REMARK,CPMS_UUID,CPMS_ID,PROCESS_DATE,PROCESS_REMARK,PM_LAND_ACQUISITION_INFO_ID,AUTHOR_UNIT,OTHER_RESPONSOR from PM_LAND_ACQUISITION_PROGRESS where PM_LAND_ACQUISITION_INFO_ID=?", infoId);
        List<PmLandPro> landProList = list.stream().map(p -> {
            PmLandPro landPro = new PmLandPro();
            landPro.id = JdbcMapUtil.getString(p, "ID");
            landPro.status = JdbcMapUtil.getString(p, "STATUS");
            landPro.processDate = JdbcMapUtil.getString(p, "PROCESS_DATE");
            landPro.authorUnit = JdbcMapUtil.getString(p, "AUTHOR_UNIT");
            landPro.otherResponsor = JdbcMapUtil.getString(p, "OTHER_RESPONSOR");
            landPro.processRemark = JdbcMapUtil.getString(p, "PROCESS_REMARK");
            return landPro;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(landProList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.landProList = landProList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 征地拆迁支付
     */
    public void pmLandPay() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String projectId = param.projectId;
        String beginTime = param.beginTime;
        String endTime = param.endTime;
        int pageSize = param.pageSize;
        int pageIndex = param.pageIndex;
        StringBuilder sb = new StringBuilder();
        sb.append("select P.ID,P.CODE,P.NAME,P.REMARK,PM_PRJ_ID,pp.`NAME` as PROJECT_NAME,PAY_TIME,PAY_AMT,ATT_FILE_GROUP_ID from PM_LAND_ACQUISITION_PAY P left join pm_prj pp on P.PM_PRJ_ID = pp.id where 1=1 ");
        if (Strings.isNotEmpty(projectId)) {
            sb.append(" and PM_PRJ_ID = ").append(projectId);
        }
        if (Strings.isNotEmpty(beginTime) && Strings.isNotEmpty(endTime)) {
            sb.append(" and PAY_TIME between ").append(beginTime).append("and ").append(endTime);
        }
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<PmLandPay> payList = list.stream().map(p -> {
            PmLandPay pay = new PmLandPay();
            pay.id = JdbcMapUtil.getString(p, "ID");
            pay.projectId = JdbcMapUtil.getString(p, "PM_PRJ_ID");
            pay.projectName = JdbcMapUtil.getString(p, "PROJECT_NAME");
            pay.payAmt = JdbcMapUtil.getString(p, "PAY_AMT");
            pay.payTime = StringUtil.withOutT(JdbcMapUtil.getString(p, "PAY_TIME"));
            pay.attFileGroupId = JdbcMapUtil.getString(p, "ATT_FILE_GROUP_ID");
            return pay;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(payList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide outSide = new OutSide();
            outSide.total = totalList.size();
            outSide.payList = payList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 征地拆迁支付详情
     */
    public void pmLandPayView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String payId = param.payId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        try {
            Map<String, Object> stringObjectMap = myJdbcTemplate.queryForMap("select P.ID,P.CODE,P.NAME,P.REMARK,PM_PRJ_ID,pp.`NAME` as PROJECT_NAME,PAY_TIME,PAY_AMT,ATT_FILE_GROUP_ID from PM_LAND_ACQUISITION_PAY P left join pm_prj pp on P.PM_PRJ_ID = pp.id where P.id=?", payId);
            PmLandPay pay = new PmLandPay();
            pay.id = JdbcMapUtil.getString(stringObjectMap, "ID");
            pay.projectId = JdbcMapUtil.getString(stringObjectMap, "ID");
            pay.projectName = JdbcMapUtil.getString(stringObjectMap, "PROJECT_NAME");
            pay.payAmt = JdbcMapUtil.getString(stringObjectMap, "PAY_AMT");
            pay.payTime = StringUtil.withOutT(JdbcMapUtil.getString(stringObjectMap, "PAY_TIME"));
            pay.attFileGroupId = JdbcMapUtil.getString(stringObjectMap, "ATT_FILE_GROUP_ID");
            if (Strings.isNotEmpty(pay.attFileGroupId)) {
                NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(Objects.requireNonNull(myJdbcTemplate.getDataSource()));
                String sql = "select * from fl_file where id in (:ids)";
                Map<String, Object> dataParam = new HashMap<>();
                dataParam.put("ids", Arrays.asList(pay.attFileGroupId.split(",")));
                List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(sql, dataParam);
                List<FileData> fileDataList = list.stream().map(p -> {
                    FileData fileData = new FileData();
                    fileData.id = JdbcMapUtil.getString(p, "ID");
                    fileData.fileName = JdbcMapUtil.getString(p, "NAME");
                    fileData.url = JdbcMapUtil.getString(p, "FILE_INLINE_URL");
                    fileData.durl = JdbcMapUtil.getString(p, "FILE_ATTACHMENT_URL");
                    fileData.size = JdbcMapUtil.getString(p, "DSP_SIZE");
                    fileData.uploadDate = StringUtil.withOutT(JdbcMapUtil.getString(p, "UPLOAD_DTTM"));
                    fileData.fName = JdbcMapUtil.getString(p, "DSP_NAME");
                    return fileData;
                }).collect(Collectors.toList());
                pay.fileDataList = fileDataList;
            }
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(pay), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } catch (Exception e) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }


    private PmLandRecord convertPmLandRecord(Map<String, Object> data) {
        PmLandRecord record = new PmLandRecord();
        record.id = JdbcMapUtil.getString(data, "ID");
        record.name = JdbcMapUtil.getString(data, "NAME");
        record.demolitionCost = JdbcMapUtil.getString(data, "DEMOLITION_COST");
        record.demolitionProgress = JdbcMapUtil.getString(data, "DEMOLITION_PROGRESS");
        record.remark = JdbcMapUtil.getString(data, "REMARK");
        record.attFileGroupId = JdbcMapUtil.getString(data, "ATT_FILE_GROUP_ID");
        return record;
    }

    public static class RequestParam {
        public String recordId;
        public String name;
        public Integer pageSize;
        public Integer pageIndex;
        public String infoId;

        public String projectId;
        public String beginTime;
        public String endTime;

        public String payId;
    }

    /**
     * 征地拆迁记录
     */
    public static class PmLandRecord {
        public String id;
        public String name;
        public String demolitionCost;
        public String demolitionProgress;
        public String remark;
        public String attFileGroupId;

        public List<FileData> fileDataList;

    }

    /**
     * 文件信息
     */
    public static class FileData {
        public String id;
        public String fileName;
        public String url;
        public String durl;

        public String size;

        public String uploadDate;

        public String fName;
    }

    /**
     * 征地信息
     */
    public static class PmLandInfo {
        public String id;
        public String recordId;
        public String name;
    }

    /**
     * 拆迁进度
     */
    public static class PmLandPro {
        public String id;
        public String status;
        public String processDate;
        public String authorUnit;
        public String otherResponsor;
        public String processRemark;
    }

    /**
     * 征地拆迁付款
     */
    public static class PmLandPay {
        public String id;
        public String projectId;
        public String projectName;
        public String payAmt;
        public String payTime;
        public String attFileGroupId;
        public List<FileData> fileDataList;
    }

    public static class OutSide {
        public Integer total;

        /**
         * 征地拆迁记录
         */
        public List<PmLandRecord> list;

        /**
         * 征地信息
         */
        public List<PmLandInfo> landInfoList;

        /**
         * 进度信息
         */
        public List<PmLandPro> landProList;

        /**
         * 征地拆迁支付
         */
        public List<PmLandPay> payList;
    }
}
