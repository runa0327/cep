package com.cisdi.ext.pm;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.PlanOperation;
import com.cisdi.ext.model.PrjFollower;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.BaseException;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计划运营扩展
 * @author dlt
 * @date 2023/5/22 周一
 */
public class PlanOperationExt {

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
            myNamedParameterJdbcTemplate.update(sql,paramMap);
        }

        if (modifyReq.isFollow != null){//跟用户相关单独处理
            String userId = ExtJarHelper.loginInfo.get().userId;
            PrjFollower prjFollower = PrjFollower.newData();
            prjFollower.setPmPrjId(modifyReq.prjId);
            prjFollower.setAdUserId(userId);
            prjFollower.insertById();
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
        OperationSelectReq selectReq = JSONObject.parseObject(JSONObject.toJSONString(input), OperationSelectReq.class);

        StringBuffer sqlSb = new StringBuffer();
        sqlSb.append("select");
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
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        System.out.println(CollectionUtils.isEmpty(stringObjectHashMap));
    }
}
