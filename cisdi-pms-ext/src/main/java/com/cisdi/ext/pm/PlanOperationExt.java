package com.cisdi.ext.pm;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.PlanOperation;
import com.cisdi.ext.model.PrjFollower;
import com.cisdi.ext.model.PrjTag;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 计划运营扩展
 * @author dlt
 * @date 2023/5/22 周一
 */
public class PlanOperationExt {

    //筛选条件
    public void getConditions(){
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        Map<String, Object> sqlParams = new HashMap<>(input);
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        OperationSelectReq selectReq = JSONObject.parseObject(JSONObject.toJSONString(input), OperationSelectReq.class);

        StringBuffer sqlSb = new StringBuffer();
        sqlSb.append("select po.id operationId,po.PM_PRJ_ID prjId,pp.name prjName,po.KEY_PROJECT_TYPE_ID keyProjectTypeId,v1.name " +
                "keyProjectTypeName,pp.ESTIMATED_TOTAL_INVEST totalInvest,pp.PROJECT_TYPE_ID prjTypeId,v2.name prjTypeName,pp.BASE_LOCATION_ID " +
                "locationId,v3.name locationName,pp.PRJ_MANAGE_MODE_ID prjManageModeId,v4.name prjManageModeName,rtemp.AD_USER_ID earlyUserId,rtemp" +
                ".name earlyUserName,pp.PROJECT_PHASE_ID prjPhaseId,v5.name prjPhaseName,po.PRJ_TAG_IDS tagIds,ftemp.AdUserIds,IF(FIND_IN_SET" +
                "(:loginId,ftemp.AdUserIds),true,false) isFollow\n" +
                "from PLAN_OPERATION po\n" +
                "left join pm_prj pp on pp.id = po.PM_PRJ_ID\n" +
                "left join gr_set_value v1 on v1.id = po.KEY_PROJECT_TYPE_ID\n" +
                "left join gr_set_value v2 on v2.id = pp.PROJECT_TYPE_ID\n" +
                "left join gr_set_value v3 on v3.id = pp.BASE_LOCATION_ID\n" +
                "left join gr_set_value v4 on v4.id = pp.PRJ_MANAGE_MODE_ID\n" +
                "left join (select ro.PM_PRJ_ID,ro.AD_USER_ID,u.name from PM_ROSTER ro left join ad_user u on u.id = ro.AD_USER_ID where " +
                "POST_INFO_ID = '1633731474912055296') rtemp on rtemp.PM_PRJ_ID = po.PM_PRJ_ID\n" +
                "left join gr_set_value v5 on v5.id = pp.PROJECT_PHASE_ID\n" +
                "left join (select PM_PRJ_ID,GROUP_CONCAT(AD_USER_ID) AdUserIds from prj_follower group by PM_PRJ_ID) ftemp on ftemp.PM_PRJ_ID = po" +
                ".PM_PRJ_ID where 1=1");
        sqlParams.put("loginId",ExtJarHelper.loginInfo.get().userId);
        if (!CollectionUtils.isEmpty(selectReq.prjIds)){
            sqlSb.append(" and po.PM_PRJ_ID in (:prjIds)");
        }
        if (!Strings.isNullOrEmpty(selectReq.totalInvestStart)){
            sqlSb.append(" and pp.ESTIMATED_TOTAL_INVEST >= :totalInvestStart");
        }
        if (!Strings.isNullOrEmpty(selectReq.totalInvestEnd)){
            sqlSb.append(" and pp.ESTIMATED_TOTAL_INVEST <= :totalInvestEnd");
        }
        if (!CollectionUtils.isEmpty(selectReq.locationIds)){
            sqlSb.append(" and pp.BASE_LOCATION_ID in (:locationIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.earlyUserIds)){
            sqlSb.append(" and rtemp.AD_USER_ID in (:earlyUserIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.keyProjectTypeIds)){
            sqlSb.append(" and po.KEY_PROJECT_TYPE_ID in (:keyProjectTypeIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.prjManageModeIds)){
            sqlSb.append(" and pp.PRJ_MANAGE_MODE_ID in (:prjManageModeIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.prjPhaseIds)){
            sqlSb.append(" and pp.PROJECT_PHASE_ID in (:prjPhaseIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.prjTypeIds)){
            sqlSb.append(" and pp.PROJECT_TYPE_ID in (:prjTypeIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.prjTagIds)){
            sqlSb.append(" and (");
            for (int i = 0;i < selectReq.prjTagIds.size();i++) {
                if (i > 0){
                    sqlSb.append(" or ");
                }
                sqlSb.append(" FIND_IN_SET(:prjTagId" + i + ",po.PRJ_TAG_IDS" + ")");
                sqlParams.put("prjTagId" + i,selectReq.prjTagIds.get(i));
            }
            sqlSb.append(" )");
        }
        if (selectReq.isFollow != null && selectReq.isFollow == true){
            sqlSb.append(" and FIND_IN_SET(:loginId,ftemp.AdUserIds)");
        }

        List<Map<String, Object>> totalList = myNamedParameterJdbcTemplate.queryForList(sqlSb.toString(), sqlParams);
        //标签
        List<Map<String, Object>> tagMaps = myJdbcTemplate.queryForList("select ta.id tagId,ta.name tagName,v.name colorNo,v.seq_no seqNo from PRJ_TAG ta left join gr_set_value v on v.id = ta.color");
        List<PlanOperationResp> planOperationResps = this.covertToPlanOperation(totalList, tagMaps);
        if (CollectionUtils.isEmpty(planOperationResps)){
            return;
        }
        //条件map
        Map<String, Object> conditionMap = new HashMap<>();

        Map<String, Object> earlyUserMap = new HashMap<>();
        Map<String, Object> keyProjectTypeMap = new HashMap<>();
        Map<String, Object> prjTypeMap = new HashMap<>();
        Map<String, Object> prjManageModeMap = new HashMap<>();
        Map<String, Object> prjPhaseMap = new HashMap<>();
        Map<String, Object> locationMap = new HashMap<>();
        Map<String, Object> tagMap = new HashMap<>();

        for (PlanOperationResp planOperationResp : planOperationResps) {
           earlyUserMap.put(planOperationResp.earlyUserId,planOperationResp.earlyUserName);
           keyProjectTypeMap.put(planOperationResp.keyProjectTypeId,planOperationResp.keyProjectTypeName);
           prjTypeMap.put(planOperationResp.prjTypeId,planOperationResp.prjTypeName);
           prjManageModeMap.put(planOperationResp.prjManageModeId,planOperationResp.prjManageModeName);
           prjPhaseMap.put(planOperationResp.prjPhaseId,planOperationResp.prjPhaseName);
           locationMap.put(planOperationResp.locationId,planOperationResp.locationName);
           if (!CollectionUtils.isEmpty(planOperationResp.tags)){
               for (Tag tag : planOperationResp.tags) {
                   tagMap.put(tag.tagId,tag);
               }
           }
        }
        earlyUserMap.remove(null);
        keyProjectTypeMap.remove(null);
        prjTypeMap.remove(null);
        prjManageModeMap.remove(null);
        prjPhaseMap.remove(null);
        locationMap.remove(null);
        tagMap.remove(null);

        conditionMap.put("earlyUserMap",earlyUserMap);
        conditionMap.put("keyProjectTypeMap",keyProjectTypeMap);
        conditionMap.put("prjTypeMap",prjTypeMap);
        conditionMap.put("prjManageModeMap",prjManageModeMap);
        conditionMap.put("prjPhaseMap",prjPhaseMap);
        conditionMap.put("locationMap",locationMap);
        conditionMap.put("tagMap",tagMap);


        Map output = JsonUtil.fromJson(JsonUtil.toJson(conditionMap), Map.class);
        ExtJarHelper.returnValue.set(output);
    }

    //标签回显
    public void echoTag(){
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        String tagName = input.get("tagName").toString();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select ta.id tagId,ta.name tagName,v.id colorId,v.name colorNo from prj_tag ta\n" +
                "left join gr_set_value v on v.id = ta.color\n" +
                "where ta.name like '%" + tagName + "%' order by v.SEQ_NO";
        List<Map<String, Object>> originList = myJdbcTemplate.queryForList(sql);

        Map<String, Object> result = new HashMap<>();
        if (!CollectionUtils.isEmpty(originList)){
            List<Tag> tags = JSONObject.parseArray(JSONObject.toJSONString(originList), Tag.class);
            result.put("tags",tags);
        }

        Map output = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(output);
    }

    //新增、修改标签
    public void addAndModifyTag(){
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        TagReq tagReq = JSONObject.parseObject(JSONObject.toJSONString(input), TagReq.class);

        //查重
        List<PrjTag> prjTags = PrjTag.selectByWhere(new Where().eq("NAME", tagReq.name).eq("COLOR", tagReq.colorId));
        if (!CollectionUtils.isEmpty(prjTags)){
            throw new BaseException("该标签已存在！");
        }

        if (Strings.isNullOrEmpty(tagReq.id)){//没有传id，新增
            PrjTag prjTag = PrjTag.newData();
            prjTag.setName(tagReq.name);
            prjTag.setColor(tagReq.colorId);
            prjTag.insertById();
        }else {//修改
            PrjTag prjTag = PrjTag.selectById(tagReq.id);
            prjTag.setName(tagReq.name);
            prjTag.setColor(tagReq.colorId);
            prjTag.updateById();
        }
    }

    /**
     * 删除标签
     */
    public void delTag(){
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String tagId = JdbcMapUtil.getString(input, "tagId");
        if (Strings.isNullOrEmpty(tagId)){
            throw new BaseException("标签id不能为空！");
        }
        List<Map<String, Object>> originList = myJdbcTemplate.queryForList("select id,PRJ_TAG_IDS tagIds from PLAN_OPERATION where FIND_IN_SET (?,PRJ_TAG_IDS)",tagId);
        if (!CollectionUtils.isEmpty(originList)){
            for (Map<String, Object> originMap : originList) {
                String tagIds = originMap.get("tagIds").toString();
                List<String> tagIdList = new ArrayList<>(Arrays.asList(tagIds.split(",")));
                //移除掉删除的id
                tagIdList.remove(tagId);
                String newTagIs = String.join(",", tagIdList);
                if (Strings.isNullOrEmpty(newTagIs)){
                    newTagIs = null;
                }
                //更新
                myJdbcTemplate.update("update PLAN_OPERATION set PRJ_TAG_IDS = ? where id = ?",newTagIs,originMap.get("id").toString());
            }
        }
        PrjTag.deleteById(tagId);
    }


    public void addPrjDrop(){
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        Map<String, Object> sqlParams = new HashMap<>(input);
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        PrjDropReq prjDropReq = JSONObject.parseObject(JSONObject.toJSONString(input), PrjDropReq.class);
        String sql = "select pp.name prjName,pp.id prjId,pp.ESTIMATED_TOTAL_INVEST totalInvest,v3.name locationName,v4.name prjManageModeName,rtemp" +
                ".name earlyUserName\n" +
                "from pm_prj pp \n" +
                "left join (select ro.PM_PRJ_ID,ro.AD_USER_ID,u.name from PM_ROSTER ro left join ad_user u on u.id = ro.AD_USER_ID where " +
                "POST_INFO_ID = '1633731474912055296') rtemp on rtemp.PM_PRJ_ID = pp.id\n" +
                "left join gr_set_value v3 on v3.id = pp.BASE_LOCATION_ID\n" +
                "left join gr_set_value v4 on v4.id = pp.PRJ_MANAGE_MODE_ID\n" +
                "left join plan_operation po on po.PM_PRJ_ID = pp.id\n" +
                "where pp.PROJECT_SOURCE_TYPE_ID = '0099952822476441374' and IZ_FORMAL_PRJ = 1 and po.PM_PRJ_ID is null";
        if (!Strings.isNullOrEmpty(prjDropReq.prjName)){
            sql += " and pp.name like '%" + prjDropReq.prjName + "%'";
        }
        if (!CollectionUtils.isEmpty(prjDropReq.prjIds)){
            sql += " and pp.id in (:prjIds)";
        }
        List<Map<String, Object>> totalList = myNamedParameterJdbcTemplate.queryForList(sql,sqlParams);

        sql += " limit " + (prjDropReq.pageIndex - 1) * prjDropReq.pageSize + " , " + prjDropReq.pageSize;
        List<Map<String, Object>> originList = myNamedParameterJdbcTemplate.queryForList(sql,sqlParams);
        List<PrjDropResp> prjDropResps = new ArrayList<>();
        if (!CollectionUtils.isEmpty(originList)){
            for (Map<String, Object> originMap : originList) {
                PrjDropResp prjDropResp = JSONObject.parseObject(JSONObject.toJSONString(originMap), PrjDropResp.class);
                prjDropResps.add(prjDropResp);
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("prjDropResps",prjDropResps);
        result.put("total",totalList.size());
        Map output = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(output);
    }

    /**
     * 添加项目进入计划运营
     */
    public void addOperation(){
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        OperationAddReq addOperationReq = JSONObject.parseObject(JSONObject.toJSONString(input), OperationAddReq.class);
        if (addOperationReq.prjIds == null){
            return;
        }
        for (String prjId : addOperationReq.prjIds) {
            PlanOperation planOperation = PlanOperation.newData();
            planOperation.setPmPrjId(prjId);
            planOperation.insertById();
        }
    }

    /**
     * 修改计划运营
     */
    public void modifyOperation(){
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        OperationModifyReq modifyReq = JSONObject.parseObject(JSONObject.toJSONString(input), OperationModifyReq.class);
        if (Strings.isNullOrEmpty(modifyReq.operationId) || Strings.isNullOrEmpty(modifyReq.prjId)){
            throw new BaseException("项目id、计划运营id为必填");
        }

        //增量修改
        String sql = "update PLAN_OPERATION set ";
        Map<String, Object> paramMap = new HashMap<>();
        if (!Strings.isNullOrEmpty(modifyReq.tagIds)){
            sql += "PRJ_TAG_IDS = :tagIds";
            paramMap.put("tagIds",modifyReq.tagIds);
        }
        if (!Strings.isNullOrEmpty(modifyReq.keyProjectTypeId)){
            if (!CollectionUtils.isEmpty(paramMap)){
                sql += ",";
            }
            sql += "KEY_PROJECT_TYPE_ID = :keyProjectTypeId";
            paramMap.put("keyProjectTypeId",modifyReq.keyProjectTypeId);
        }
        if (!CollectionUtils.isEmpty(paramMap)){
            sql += " where id = :operationId";
            paramMap.put("operationId",modifyReq.operationId);
            System.out.println(paramMap.get("tagIds"));
            myNamedParameterJdbcTemplate.update(sql,paramMap);
        }

        if (modifyReq.isFollow != null && modifyReq.isFollow == true){//跟用户相关单独处理
            String userId = ExtJarHelper.loginInfo.get().userId;
            PrjFollower prjFollower = PrjFollower.newData();
            prjFollower.setPmPrjId(modifyReq.prjId);
            prjFollower.setAdUserId(userId);
            prjFollower.insertById();
        }else if (modifyReq != null && modifyReq.isFollow != null && modifyReq.isFollow == false){
            PrjFollower.deleteByWhere(new Where().eq("PM_PRJ_ID", modifyReq.prjId).eq("AD_USER_ID",ExtJarHelper.loginInfo.get().userId));
        }
    }

    /**
     * 删除计划运营
     */
    public void delOperation(){
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        if (input.get("operationId") == null){
            throw new BaseException("计划运营id不能为null");
        }
        String operationId = input.get("operationId").toString();
        PlanOperation.deleteById(operationId);
    }

    /**
     * 查询计划运营
     */
    public void listOperations(){
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        Map<String, Object> sqlParams = new HashMap<>(input);
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        OperationSelectReq selectReq = JSONObject.parseObject(JSONObject.toJSONString(input), OperationSelectReq.class);

        StringBuffer sqlSb = new StringBuffer();
        sqlSb.append("select po.id operationId,po.PM_PRJ_ID prjId,pp.name prjName,po.KEY_PROJECT_TYPE_ID keyProjectTypeId,v1.name " +
                "keyProjectTypeName,pp.ESTIMATED_TOTAL_INVEST totalInvest,pp.PROJECT_TYPE_ID prjTypeId,v2.name prjTypeName,pp.BASE_LOCATION_ID " +
                "locationId,v3.name locationName,pp.PRJ_MANAGE_MODE_ID prjManageModeId,v4.name prjManageModeName,rtemp.AD_USER_ID earlyUserId,rtemp" +
                ".name earlyUserName,pp.PROJECT_PHASE_ID prjPhaseId,v5.name prjPhaseName,po.PRJ_TAG_IDS tagIds,ftemp.AdUserIds,IF(FIND_IN_SET" +
                "(:loginId,ftemp.AdUserIds),true,false) isFollow\n" +
                "from PLAN_OPERATION po\n" +
                "left join pm_prj pp on pp.id = po.PM_PRJ_ID\n" +
                "left join gr_set_value v1 on v1.id = po.KEY_PROJECT_TYPE_ID\n" +
                "left join gr_set_value v2 on v2.id = pp.PROJECT_TYPE_ID\n" +
                "left join gr_set_value v3 on v3.id = pp.BASE_LOCATION_ID\n" +
                "left join gr_set_value v4 on v4.id = pp.PRJ_MANAGE_MODE_ID\n" +
                "left join (select ro.PM_PRJ_ID,ro.AD_USER_ID,u.name from PM_ROSTER ro left join ad_user u on u.id = ro.AD_USER_ID where " +
                "POST_INFO_ID = '1633731474912055296') rtemp on rtemp.PM_PRJ_ID = po.PM_PRJ_ID\n" +
                "left join gr_set_value v5 on v5.id = pp.PROJECT_PHASE_ID\n" +
                "left join (select PM_PRJ_ID,GROUP_CONCAT(AD_USER_ID) AdUserIds from prj_follower group by PM_PRJ_ID) ftemp on ftemp.PM_PRJ_ID = po" +
                ".PM_PRJ_ID where 1=1");
        sqlParams.put("loginId",ExtJarHelper.loginInfo.get().userId);
        if (!CollectionUtils.isEmpty(selectReq.prjIds)){
            sqlSb.append(" and po.PM_PRJ_ID in (:prjIds)");
        }
        if (!Strings.isNullOrEmpty(selectReq.totalInvestStart)){
            sqlSb.append(" and pp.ESTIMATED_TOTAL_INVEST >= :totalInvestStart");
        }
        if (!Strings.isNullOrEmpty(selectReq.totalInvestEnd)){
            sqlSb.append(" and pp.ESTIMATED_TOTAL_INVEST <= :totalInvestEnd");
        }
        if (!CollectionUtils.isEmpty(selectReq.locationIds)){
            sqlSb.append(" and pp.BASE_LOCATION_ID in (:locationIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.earlyUserIds)){
            sqlSb.append(" and rtemp.AD_USER_ID in (:earlyUserIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.keyProjectTypeIds)){
            sqlSb.append(" and po.KEY_PROJECT_TYPE_ID in (:keyProjectTypeIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.prjManageModeIds)){
            sqlSb.append(" and pp.PRJ_MANAGE_MODE_ID in (:prjManageModeIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.prjPhaseIds)){
            sqlSb.append(" and pp.PROJECT_PHASE_ID in (:prjPhaseIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.prjTypeIds)){
            sqlSb.append(" and pp.PROJECT_TYPE_ID in (:prjTypeIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.prjTagIds)){
            sqlSb.append(" and (");
            for (int i = 0;i < selectReq.prjTagIds.size();i++) {
                if (i > 0){
                    sqlSb.append(" or ");
                }
                sqlSb.append(" FIND_IN_SET(:prjTagId" + i + ",po.PRJ_TAG_IDS" + ")");
                sqlParams.put("prjTagId" + i,selectReq.prjTagIds.get(i));
            }
            sqlSb.append(" )");
        }
        if (selectReq.isFollow != null && selectReq.isFollow == true){
            sqlSb.append(" and FIND_IN_SET(:loginId,ftemp.AdUserIds)");
        }

        List<Map<String, Object>> totalList = myNamedParameterJdbcTemplate.queryForList(sqlSb.toString(), sqlParams);
        sqlSb.append(" limit " + (selectReq.pageIndex - 1) * selectReq.pageSize + " , " + selectReq.pageSize);
        List<Map<String, Object>> originList = myNamedParameterJdbcTemplate.queryForList(sqlSb.toString(), sqlParams);
        //标签
        List<Map<String, Object>> tagMaps = myJdbcTemplate.queryForList("select ta.id tagId,ta.name tagName,v.name colorNo,v.seq_no seqNo from PRJ_TAG ta left join gr_set_value v on v.id = ta.color");
        List<PlanOperationResp> planOperationResps = this.covertToPlanOperation(originList, tagMaps);

        //返回
        Map<String, Object> result = new HashMap<>();
        result.put("planOperationResps",planOperationResps);
        result.put("total",totalList.size());
        Map output = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(output);
    }

    /**
     * 项目类型统计项目
     */
    public void statistic(){
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        Map<String, Object> sqlParams = new HashMap<>(input);
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        OperationSelectReq selectReq = JSONObject.parseObject(JSONObject.toJSONString(input), OperationSelectReq.class);

        StringBuffer sqlSb = new StringBuffer();
        sqlSb.append("select count(po.id) num,pp.PROJECT_TYPE_ID prjTypeId,v2.name prjTypeName\n" +
                "from PLAN_OPERATION po\n" +
                "left join pm_prj pp on pp.id = po.PM_PRJ_ID\n" +
                "left join gr_set_value v1 on v1.id = po.KEY_PROJECT_TYPE_ID\n" +
                "left join gr_set_value v2 on v2.id = pp.PROJECT_TYPE_ID\n" +
                "left join gr_set_value v3 on v3.id = pp.BASE_LOCATION_ID\n" +
                "left join gr_set_value v4 on v4.id = pp.PRJ_MANAGE_MODE_ID\n" +
                "left join (select ro.PM_PRJ_ID,ro.AD_USER_ID,u.name from PM_ROSTER ro left join ad_user u on u.id = ro.AD_USER_ID where " +
                "POST_INFO_ID = '1633731474912055296') rtemp on rtemp.PM_PRJ_ID = po.PM_PRJ_ID\n" +
                "left join gr_set_value v5 on v5.id = pp.PROJECT_PHASE_ID\n" +
                "where 1=1");
        if (!CollectionUtils.isEmpty(selectReq.prjIds)){
            sqlSb.append(" and po.PM_PRJ_ID in (:prjIds)");
        }
        if (!Strings.isNullOrEmpty(selectReq.totalInvestStart)){
            sqlSb.append(" and pp.ESTIMATED_TOTAL_INVEST >= :totalInvestStart");
        }
        if (!Strings.isNullOrEmpty(selectReq.totalInvestEnd)){
            sqlSb.append(" and pp.ESTIMATED_TOTAL_INVEST <= :totalInvestEnd");
        }
        if (!CollectionUtils.isEmpty(selectReq.locationIds)){
            sqlSb.append(" and pp.BASE_LOCATION_ID in (:locationIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.earlyUserIds)){
            sqlSb.append(" and rtemp.AD_USER_ID in (:earlyUserIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.keyProjectTypeIds)){
            sqlSb.append(" and po.KEY_PROJECT_TYPE_ID in (:keyProjectTypeIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.prjManageModeIds)){
            sqlSb.append(" and pp.PRJ_MANAGE_MODE_ID in (:prjManageModeIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.prjPhaseIds)){
            sqlSb.append(" and pp.PROJECT_PHASE_ID in (:prjPhaseIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.prjTypeIds)){
            sqlSb.append(" and pp.PROJECT_TYPE_ID in (:prjTypeIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.prjTagIds)){
            sqlSb.append(" and (");
            for (int i = 0;i < selectReq.prjTagIds.size();i++) {
                if (i > 0){
                    sqlSb.append(" or ");
                }
                sqlSb.append(" FIND_IN_SET(:prjTagId" + i + ")");
                sqlParams.put("prjTagId" + i,selectReq.prjTagIds.get(i));
            }
            sqlSb.append(" )");
        }
        sqlSb.append(" group by pp.PROJECT_TYPE_ID");
        List<Map<String, Object>> originList = myNamedParameterJdbcTemplate.queryForList(sqlSb.toString(), sqlParams);
        Map<String, Object> result = new HashMap<>();
        if (!CollectionUtils.isEmpty(originList)){
            int sum = originList.stream().mapToInt(originMap -> Integer.parseInt(originMap.get("num").toString())).sum();
            StringBuffer otherStatistic = new StringBuffer();
            originList.forEach(originMap -> otherStatistic.append(" ").append(originMap.get("prjTypeName")).append(":").append(originMap.get("num")));
            result.put("prjSum",sum);
            result.put("otherStatistic",otherStatistic);
        }

        Map output = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(output);
    }

    /**
     * maps转PlanOperationResps
     * 并且封装标签
     */
    private List<PlanOperationResp> covertToPlanOperation(List<Map<String, Object>> originList,List<Map<String, Object>> tagMaps){
        List<PlanOperationResp> planOperationResps = new ArrayList<>();

        if (!CollectionUtils.isEmpty(originList)){
            for (Map<String, Object> originMap : originList) {
                PlanOperationResp planOperationResp = JSONObject.parseObject(JSONObject.toJSONString(originMap), PlanOperationResp.class);
                //封装标签
                if (!Strings.isNullOrEmpty(planOperationResp.tagIds)){
                    List<Tag> tags = Stream.of(planOperationResp.tagIds.split(",")).map(tagId -> {
                        //这里面是通过逗号分隔的标签id，分装为标签对象
                        Optional<Map<String, Object>> tagOp = tagMaps.stream().filter(tagMap -> tagMap.get("tagId").toString().equals(tagId)).findAny();
                        if (tagOp.isPresent()){
                            Map<String, Object> tagMap = tagOp.get();
                            Tag tag = JSONObject.parseObject(JSONObject.toJSONString(tagMap), Tag.class);
                            return tag;
                        }
                        return null;
                    }).filter(tag -> tag != null).sorted(Comparator.comparing(tag -> tag.seqNo)).collect(Collectors.toList());
                    planOperationResp.tags = tags;
                }
                planOperationResps.add(planOperationResp);
            }
        }

        return planOperationResps;
    }


    /**
     * 新增项目下拉
     */
    @Data
    private static class PrjDropReq{
        private List<String> prjIds;
        private String prjName;
        private Integer pageIndex;
        private Integer pageSize;
    }

    /**
     * 项目下拉响应
     */
    @Data
    private static class PrjDropResp{
        private String prjId;
        private String prjName;
        //总投资
        private String totalInvest;
        //建设地点
        private String locationName;
        //管理模式
        private String prjManageModeName;
        //前期岗用户
        private String earlyUserName;

    }
    /**
     * 列表响应
     */
    @Data
    private static class PlanOperationResp{
        //计划运营id
        private String operationId;
        //项目id
        private String prjId;
        //项目名称
        private String prjName;
        //重点项目类型id
        private String keyProjectTypeId;
        //重点项目类型名称
        private String keyProjectTypeName;
        //总投资
        private String totalInvest;
        //项目类型id
        private String prjTypeId;
        //项目类型名称
        private String prjTypeName;
        //建设地点id
        private String locationId;
        //建设地点名称
        private String locationName;
        //管理模式id
        private String prjManageModeId;
        //管理模式名词
        private String prjManageModeName;
        //前期岗用户id
        private String earlyUserId;
        //前期岗用户名称
        private String earlyUserName;
        //项目状态id
        private String prjPhaseId;
        //项目状态名
        private String prjPhaseName;
        //是否关注
        private Boolean isFollow;
        //标签
        private List<Tag> tags;
        private String tagIds;
    }

    /**
     * 标签
     */
    @Data
    private static class Tag{
        //标签id
        private String tagId;
        //标签名称
        private String tagName;
        //颜色
        private String colorNo;
        //
        private String colorId;
        //序号
        private Double seqNo;
    }

    /**
     * 新增、修改标签请求
     */
    @Data
    private static class TagReq{
        private String id;
        private String name;
        private String colorId;
    }

    /**
     * 查询请求
     */
    @Data
    public static class OperationSelectReq{
        //项目ids
        private List<String> prjIds;
        //总投资开始金额
        private String totalInvestStart;
        //总投资结束金额
        private String totalInvestEnd;
        //前期岗人员ids
        private List<String> earlyUserIds;
        //重点项目类型ids
        private List<String> keyProjectTypeIds;
        //项目管理模式
        private List<String> prjManageModeIds;
        //项目标签
        private List<String> prjTagIds;
        //项目状态
        private List<String> prjPhaseIds;
        //建设地点
        private List<String> locationIds;
        //项目类型
        private List<String> prjTypeIds;
        //页码
        private Integer pageIndex;
        //页面大小
        private Integer pageSize;
        //是否关注
        private Boolean isFollow;
    }

    /**
     * 修改计划运营请求
     */
    @Data
    private static class OperationModifyReq{
        //计划运营id
        private String operationId;
        //项目id
        private String prjId;


        //是否关注
        private Boolean isFollow;
        //标签ids
        private String tagIds;
        //重点项目分类id
        private String keyProjectTypeId;
    }

    /**
     * 添加计划运营请求
     */
    @Data
    private static class OperationAddReq{
        private List<String> prjIds;
    }

    public static void main(String[] args) {
        Boolean aBoolean = new Boolean("t");
        System.out.println(aBoolean == true);
    }
}
