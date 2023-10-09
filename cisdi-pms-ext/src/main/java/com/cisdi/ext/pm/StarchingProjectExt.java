package com.cisdi.ext.pm;

import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.PmPrjCodeUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title StarchingProjectExt
 * @package com.cisdi.ext.pm
 * @description 零星项目库
 * @date 2023/10/8
 */
public class StarchingProjectExt {


    /**
     * 列表查询
     */
    public void starchingList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam requestParam = JsonUtil.fromJson(json, RequestParam.class);
        int pageSize = requestParam.pageSize;
        int pageIndex = requestParam.pageIndex;
        StringBuilder sb = new StringBuilder();
        sb.append("select pm.id as prj_id,pm.pm_code as prj_code,pm.`NAME` as prj_name,pp.`NAME` as owners,gs.`NAME` as type,gg.`NAME` as location  from pm_prj pm\n" +
                "left join pm_party pp on pm.CUSTOMER_UNIT = pp.id\n" +
                "left join gr_set_value gs on pm.PROJECT_TYPE_ID = gs.id\n" +
                "left join gr_set_value gg on pm.BASE_LOCATION_ID = gg.id\n" +
                "where pm.PROJECT_CLASSIFICATION_ID ='1704686841101975552'");
        if (StringUtils.hasText(requestParam.name)) {
            sb.append(" and pm.`NAME` like '%").append(requestParam.name).append("%'");
        }
        if (StringUtils.hasText(requestParam.code)) {
            sb.append(" and pm.pm_code like '%").append(requestParam.code).append("%'");
        }
        if (StringUtils.hasText(requestParam.owner)) {
            sb.append(" and pp.name like '%").append(requestParam.owner).append("%'");
        }
        if (StringUtils.hasText(requestParam.type)) {
            sb.append(" and pm.PROJECT_TYPE_ID ='").append(requestParam.type).append("'");
        }
        if (StringUtils.hasText(requestParam.location)) {
            sb.append(" and pm.BASE_LOCATION_ID ='").append(requestParam.location).append("'");
        }

        sb.append(" order by pm.PM_CODE desc ");
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<ObjInfo> dataInfoList = list.stream().map(this::convertData).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(dataInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide resData = new OutSide();
            resData.total = totalList.size();
            resData.objInfos = dataInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 详情
     */
    public void starchingView() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pm.id as prj_id,pm.pm_code as prj_code,pm.`NAME` as prj_name,pp.`NAME` as owners,pm.CUSTOMER_UNIT as owner_id,gs.`NAME` as type,\n" +
                "pm.PROJECT_TYPE_ID as type_id,gg.`NAME` as location,pm.BASE_LOCATION_ID as location_id,REPORT_FILE as report_file,PRJ_SITUATION,ESTIMATED_TOTAL_INVEST from pm_prj pm\n" +
                "left join pm_party pp on pm.CUSTOMER_UNIT = pp.id\n" +
                "left join gr_set_value gs on pm.PROJECT_TYPE_ID = gs.id\n" +
                "left join gr_set_value gg on pm.BASE_LOCATION_ID = gg.id\n" +
                "where pm.PROJECT_CLASSIFICATION_ID ='1704686841101975552' and pm.id=?", map.get("id"));
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            Map<String, Object> dataMap = list.get(0);
            ObjInfo info = this.convertData(dataMap);
            String fileIds = JdbcMapUtil.getString(dataMap, "report_file");
            if (StringUtils.hasText(fileIds)) {
                List<String> ids = Arrays.asList(fileIds.split(","));

                Map<String, Object> queryFileParams = new HashMap<>();// 创建入参map
                queryFileParams.put("ids", ids);
                List<Map<String, Object>> fileList = myNamedParameterJdbcTemplate.queryForList("select ff.ID as ID, DSP_NAME,SIZE_KB,UPLOAD_DTTM,au.`NAME` as USER_NAME,FILE_INLINE_URL,FILE_ATTACHMENT_URL from fl_file ff left join ad_user au on ff.CRT_USER_ID = au.id  where ff.id in (:ids)", queryFileParams);
                AtomicInteger index = new AtomicInteger(0);
                List<FileObj> fileObjList = fileList.stream().map(p -> {
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
        }
    }

    /**
     * 新增/修改
     */
    public void starchingModify() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        InputInfo inputData = JsonUtil.fromJson(json, InputInfo.class);
        StringBuilder sb = new StringBuilder();
        sb.append("update pm_prj set LAST_MODI_DT =NOW(),PROJECT_CLASSIFICATION_ID='1704686841101975552' ");
        String id = inputData.id;
        if (!StringUtils.hasText(id)) {
            id = Crud.from("pm_prj").insertData();
            String code = PmPrjCodeUtil.getPrjCode();
            sb.append(" ,PM_CODE='").append(code).append("'");
        }
        if (StringUtils.hasText(inputData.name)) {
            sb.append(" ,NAME='").append(inputData.name).append("'");
        } else {
            sb.append(" ,NAME=null");
        }
        if (StringUtils.hasText(inputData.owner)) {
            sb.append(" ,CUSTOMER_UNIT='").append(inputData.owner).append("'");
        } else {
            sb.append(" ,CUSTOMER_UNIT=null");
        }
        if (StringUtils.hasText(inputData.type)) {
            sb.append(" ,PROJECT_TYPE_ID='").append(inputData.type).append("'");
        } else {
            sb.append(" ,PROJECT_TYPE_ID=null");
        }
        if (StringUtils.hasText(inputData.location)) {
            sb.append(" ,BASE_LOCATION_ID='").append(inputData.location).append("'");
        } else {
            sb.append(" ,BASE_LOCATION_ID=null");
        }
        if (StringUtils.hasText(inputData.amt)) {
            sb.append(" ,ESTIMATED_TOTAL_INVEST='").append(inputData.amt).append("'");
        } else {
            sb.append(" ,ESTIMATED_TOTAL_INVEST=null");
        }
        if (StringUtils.hasText(inputData.desc)) {
            sb.append(" ,PRJ_SITUATION='").append(inputData.desc).append("'");
        } else {
            sb.append(" ,PRJ_SITUATION=null");
        }
        if (StringUtils.hasText(inputData.fileIds)) {
            sb.append(" ,REPORT_FILE='").append(inputData.fileIds).append("'");
        } else {
            sb.append(" ,REPORT_FILE=null");
        }

        sb.append(" where id='").append(id).append("'");
        myJdbcTemplate.update(sb.toString());
    }


    private ObjInfo convertData(Map<String, Object> dataMap) {
        ObjInfo info = new ObjInfo();
        info.id = JdbcMapUtil.getString(dataMap, "prj_id");
        info.code = JdbcMapUtil.getString(dataMap, "prj_code");
        info.name = JdbcMapUtil.getString(dataMap, "prj_name");
        info.owner = JdbcMapUtil.getString(dataMap, "owners");
        info.ownerId = JdbcMapUtil.getString(dataMap, "owner_id");
        info.type = JdbcMapUtil.getString(dataMap, "type");
        info.typeId = JdbcMapUtil.getString(dataMap, "type_id");
        info.location = JdbcMapUtil.getString(dataMap, "location");
        info.locationId = JdbcMapUtil.getString(dataMap, "location_id");
        info.amt = JdbcMapUtil.getString(dataMap, "ESTIMATED_TOTAL_INVEST");
        info.desc = JdbcMapUtil.getString(dataMap, "PRJ_SITUATION");
        return info;
    }

    public static class RequestParam {
        public String name;
        public String code;
        public String owner;
        public String type;
        public String location;
        public Integer pageSize;
        public Integer pageIndex;
    }


    public static class ObjInfo {
        public String id;
        public String code;
        public String name;
        public String owner;
        public String ownerId;
        public String type;
        public String typeId;
        public String location;
        public String locationId;
        public String amt;
        public String desc;
        public List<FileObj> fileObjList;
    }

    public static class FileObj {
        // 序号
        public Integer num;
        // 文件名称
        public String fileName;
        // 文件大小
        public String fileSize;
        // 上传人
        public String uploadUser;
        // 上传时间
        public String uploadDate;

        public String id;

        public String viewUrl;

        public String downloadUrl;

    }

    public static class OutSide {
        public Integer total;
        public List<ObjInfo> objInfos;
    }

    public static class InputInfo {
        public String id;
        public String name;
        public String owner;
        public String type;
        public String location;
        public String amt;
        public String desc;
        public String fileIds;
    }
}
