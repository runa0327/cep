package com.cisdi.ext.pm;

import com.cisdi.ext.base.GrSetValueExt;
import com.cisdi.ext.util.JsonUtil;
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
 * @title EquipProjectExt
 * @package com.cisdi.ext.equip
 * @description 科研设备库
 * @date 2023/10/7
 */
public class EquipProjectExt {

    /**
     * 列表查询
     */
    public void equipList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        String json = JsonUtil.toJson(map);
        RequestParam requestParam = JsonUtil.fromJson(json, RequestParam.class);
        int pageSize = requestParam.pageSize;
        int pageIndex = requestParam.pageIndex;
        StringBuilder sb = new StringBuilder();
        sb.append("select pm.id as eq_id,pm.pm_code as eq_code,pm.`NAME` as eq_name,pp.`NAME` as owner_name,pm.EQUIPMENT_PURCHASE_BUDGET_AMOUNT as eq_amt,gs.`NAME` as eq_range,pm.PRJ_SITUATION as eq_situation,pm.PM_LANDING_POINT_ID,plp.name as pointName  \n" +
                "from pm_prj pm left join pm_prj pp on pm.SUBORDINATE_PROJECT = pp.ID\n" +
                "left join gr_set_value gs on gs.id = pm.RESEARCH_RANGE\n" +
                "left join PM_LANDING_POINT plp on plp.id = pm.PM_LANDING_POINT_ID " +
                "left join PLAN_OPERATION po on pm.id = po.PM_PRJ_ID " +
                "where pm.PROJECT_CLASSIFICATION_ID ='1704686735267102720' and pm.PROJECT_SOURCE_TYPE_ID = '0099952822476441374' and pm.`STATUS`='ap' and pm.IZ_FORMAL_PRJ = 1 and pm.PROJECT_STATUS != '1661568714048413696' ");
        if (StringUtils.hasText(requestParam.eqPrjName)) {
            sb.append(" and pm.`NAME` like '%").append(requestParam.eqPrjName).append("%'");
        }
        if (StringUtils.hasText(requestParam.prjName)) {
            sb.append(" and pp.`NAME` like '%").append(requestParam.prjName).append("%'");
        }
        if (StringUtils.hasText(requestParam.researchRange)) {
            sb.append(" and pm.RESEARCH_RANGE ='").append(requestParam.researchRange).append("'");
        }
        if (StringUtils.hasText(requestParam.keyTypeId)) {
            sb.append(" and po.KEY_PROJECT_TYPE_ID = '").append(requestParam.keyTypeId).append("'");
        }
        if (StringUtils.hasText(requestParam.tagId)) {
            sb.append(" and find_in_set('").append(requestParam.tagId).append("', PRJ_TAG_IDS) ");
        }
        Map<String, Object> queryFileParams = new HashMap<>();// 创建入参map
        if (StringUtils.hasText(requestParam.pointIds)) {
            List<String> ids = Arrays.asList(requestParam.pointIds.split(","));
            queryFileParams.put("ids", ids);
            sb.append(" and pm.PM_LANDING_POINT_ID  in (:ids)");
        }
        sb.append(" order by pm.PM_CODE desc ");
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        List<Map<String, Object>> list = myNamedParameterJdbcTemplate.queryForList(sb.toString(), queryFileParams);
        List<ObjInfo> dataInfoList = list.stream().map(this::convertData).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(dataInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myNamedParameterJdbcTemplate.queryForList(totalSql, queryFileParams);
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
    public void equipView() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pm.id as eq_id,pm.pm_code as eq_code,pm.`NAME` as eq_name,pp.`NAME` as owner_name,pp.id as prj_id," +
                "round(ifnull(pm.EQUIPMENT_PURCHASE_BUDGET_AMOUNT,0),2) as eq_amt,pm.RESEARCH_RANGE as eq_range_id,\n" +
                "gs.`NAME` as eq_range,pm.PRJ_SITUATION as eq_situation,pm.REPORT_FILE as report_file,pm.REPLY_NO,pm.COMPANY_ID,hd.name as companyName,pm.PM_LANDING_POINT_ID,plp.name as pointName \n" +
                "from pm_prj pm left join pm_prj pp on pm.SUBORDINATE_PROJECT = pp.ID\n" +
                "left join gr_set_value gs on gs.id = pm.RESEARCH_RANGE\n" +
                " left join hr_dept hd on hd.id = pm.COMPANY_ID " +
                "left join PM_LANDING_POINT plp on plp.id = pm.PM_LANDING_POINT_ID " +
                "where pm.PROJECT_CLASSIFICATION_ID ='1704686735267102720' and pm.id=?", map.get("id"));
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
     * 设备科研库新增/修改
     */
    public void equipModify() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        InputData inputData = JsonUtil.fromJson(json, InputData.class);
        String id = inputData.id;
        if (!StringUtils.hasText(id)) {
            id = Crud.from("pm_prj").insertData();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("update pm_prj set LAST_MODI_DT =NOW(),PROJECT_CLASSIFICATION_ID='1704686735267102720',PROJECT_SOURCE_TYPE_ID = '0099952822476441374',IZ_FORMAL_PRJ = 1,PROJECT_STATUS = '1661568765403471872' ");
        if (StringUtils.hasText(inputData.prjId)) {
            sb.append(" ,SUBORDINATE_PROJECT='").append(inputData.prjId).append("'");
            //处理编码
            String code = this.getCode(inputData.prjId, id);
            sb.append(" ,PM_CODE='").append(code).append("'");
        } else {
            sb.append(" ,SUBORDINATE_PROJECT=null");
            sb.append(" ,PM_CODE=null");
        }
        if (StringUtils.hasText(inputData.range)) {
            sb.append(" ,RESEARCH_RANGE='").append(inputData.range).append("'");
        } else {
            sb.append(" ,RESEARCH_RANGE=null");
        }
        if (StringUtils.hasText(inputData.name)) {
            sb.append(" ,NAME='").append(inputData.name).append("'");
            String remark = GrSetValueExt.getValueNameById("1704686735267102720");
            sb.append(" ,REMARK='").append(remark).append("'");
        } else {
            sb.append(" ,NAME=null");
            sb.append(" ,REMARK=null");
        }
        if (StringUtils.hasText(inputData.amt)) {
            sb.append(" ,EQUIPMENT_PURCHASE_BUDGET_AMOUNT='").append(inputData.amt).append("'");
        } else {
            sb.append(" ,EQUIPMENT_PURCHASE_BUDGET_AMOUNT=null");
        }
        if (StringUtils.hasText(inputData.reNo)) {
            sb.append(" ,REPLY_NO='").append(inputData.reNo).append("'");
        } else {
            sb.append(" ,REPLY_NO=null");
        }
        if (StringUtils.hasText(inputData.desc)) {
            sb.append(" ,PRJ_SITUATION='").append(inputData.desc).append("'");
        } else {
            sb.append(" ,PRJ_SITUATION=null");
        }
        if (StringUtils.hasText(inputData.companyId)) {
            sb.append(" ,COMPANY_ID='").append(inputData.companyId).append("'");
        } else {
            sb.append(" ,COMPANY_ID=null");
        }
        if (StringUtils.hasText(inputData.fileIds)) {
            sb.append(" ,REPORT_FILE='").append(inputData.fileIds).append("'");
        } else {
            sb.append(" ,REPORT_FILE=null");
        }
        if (StringUtils.hasText(inputData.pointId)) {
            sb.append(" ,PM_LANDING_POINT_ID='").append(inputData.pointId).append("'");
        } else {
            sb.append(" ,PM_LANDING_POINT_ID=null");
        }

        sb.append(" where id='").append(id).append("'");
        myJdbcTemplate.update(sb.toString());
    }


    /**
     * 工程项目列表
     */
    public void engineeringProject() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_prj where status='ap' and PROJECT_SOURCE_TYPE_ID = '0099952822476441374' and PROJECT_CLASSIFICATION_ID in ('1704686664114929664','1704686699527438336','1704686841525600256') order by pm_code desc");
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<ProjectInfo> projectInfoList = list.stream().map(p -> {
                ProjectInfo info = new ProjectInfo();
                info.id = JdbcMapUtil.getString(p, "ID");
                info.name = JdbcMapUtil.getString(p, "NAME");
                return info;
            }).collect(Collectors.toList());
            OutSide resData = new OutSide();
            resData.projectInfoList = projectInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    private ObjInfo convertData(Map<String, Object> dataMap) {
        ObjInfo info = new ObjInfo();
        info.id = JdbcMapUtil.getString(dataMap, "eq_id");
        info.eqCode = JdbcMapUtil.getString(dataMap, "eq_code");
        info.eqName = JdbcMapUtil.getString(dataMap, "eq_name");
        info.prjName = JdbcMapUtil.getString(dataMap, "owner_name");
        info.prjId = JdbcMapUtil.getString(dataMap, "prj_id");
        info.amt = JdbcMapUtil.getString(dataMap, "eq_amt");
        info.eqRange = JdbcMapUtil.getString(dataMap, "eq_range");
        info.eqRangId = JdbcMapUtil.getString(dataMap, "eq_range_id");
        info.reNo = JdbcMapUtil.getString(dataMap, "REPLY_NO");
        info.eqDes = JdbcMapUtil.getString(dataMap, "eq_situation");
        info.companyId = JdbcMapUtil.getString(dataMap, "COMPANY_ID");
        info.companyName = JdbcMapUtil.getString(dataMap, "companyName");
        info.pointId = JdbcMapUtil.getString(dataMap, "PM_LANDING_POINT_ID");
        info.pointName = JdbcMapUtil.getString(dataMap, "pointName");
        return info;
    }

    private String getCode(String projectId, String eqId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_prj where SUBORDINATE_PROJECT =? and id <> ? order by pm_code desc", projectId, eqId);
        if (CollectionUtils.isEmpty(list)) {
            List<Map<String, Object>> list1 = myJdbcTemplate.queryForList("select * from pm_prj where id =?", projectId);
            Map<String, Object> dataMap = list1.get(0);
            return JdbcMapUtil.getString(dataMap, "PM_CODE") + "-001";
        } else {
            String pmCode = JdbcMapUtil.getString(list.get(0), "PM_CODE");
            String leftPart = pmCode.substring(0, pmCode.length() - 4);
            String rightPart = pmCode.substring(pmCode.length() - 3);
            int count = Integer.parseInt(rightPart) + 1;
            return leftPart + "-" + String.format("%03d", count);
        }
    }

    public static class RequestParam {
        public String prjName;
        public String eqPrjName;
        public String researchRange;
        public String pointIds;
        public Integer pageSize;
        public Integer pageIndex;
        public String keyTypeId;//重点分类
        public String tagId;//标签
    }

    public static class ObjInfo {
        public String id;
        public String eqCode;
        public String eqName;
        public String prjName;
        public String prjId;
        public String amt;
        public String eqRangId;
        public String eqRange;
        public String reNo;
        public String eqDes;

        public String companyId;

        public String companyName;

        public String pointId;

        public String pointName;

        public List<FileObj> fileObjList;
    }

    public static class OutSide {
        public Integer total;
        public List<ObjInfo> objInfos;
        public List<ProjectInfo> projectInfoList;
        public List<LandingPoint> landingPoints;
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

    public static class ProjectInfo {
        public String id;
        public String name;
    }

    public static class InputData {
        public String id;
        public String prjId;
        public String range;
        public String name;
        public String amt;
        public String reNo;
        public String desc;
        public String fileIds;
        public String companyId;
        public String pointId;
    }


    /**
     * 落位点查询
     */
    public void landingPointList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_LANDING_POINT where status ='ap' ");
        List<LandingPoint> landingPoints = list.stream().map(p -> {
            LandingPoint point = new LandingPoint();
            point.id = JdbcMapUtil.getString(p, "ID");
            point.name = JdbcMapUtil.getString(p, "NAME");
            return point;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(landingPoints)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide resData = new OutSide();
            resData.landingPoints = landingPoints;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 落位点新增/修改
     */
    public void LandingPointModify() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = JdbcMapUtil.getString(map, "id");
        if (!StringUtils.hasText(id)) {
            id = Crud.from("PM_LANDING_POINT").insertData();
        }
        myJdbcTemplate.update("update PM_LANDING_POINT set NAME=? where id=?", map.get("name"), id);
    }

    /**
     * 落位点删除
     */
    public void LandingPointDel() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        myJdbcTemplate.update("update pm_prj set PM_LANDING_POINT_ID = null where PM_LANDING_POINT_ID=?", map.get("id"));
        myJdbcTemplate.update("delete from PM_LANDING_POINT where id=?", map.get("id"));
    }


    public static class LandingPoint {
        public String id;
        public String name;
    }

}
