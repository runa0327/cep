package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * BIM模型关联扩展类
 * Created on 2017/5/26.
 */
@Slf4j
public class BimModelRelExt {

    /**
     * 施工进度进化关联BIM模型更新操作
     */
    public void constructProgressRelUpdate(){
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        //获取表单提交信息
         Map<String, Object> varMap = ExtJarHelper.getVarMap();
        //判断valueMap是否包含CC_BIM_MODEL_REL_IDS
        String bimModelIds = "";
        if(varMap.containsKey("P_CC_BIM_MODEL_REL_IDS")){
            //模型IDs
            bimModelIds = varMap.get("P_CC_BIM_MODEL_REL_IDS").toString();
        }


        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            String constructProgressPlanId = valueMap.get("ID").toString();//施工进度计划ID

            //删除之前的关联关系
            deleteConstructProgressRel(constructProgressPlanId);
            //更新施工进度关联的BIM模型
            CcConstructProgressPlan plan = CcConstructProgressPlan.selectById(constructProgressPlanId);
            if(bimModelIds != null && !bimModelIds.isEmpty()){
                plan.setCcBimModelRelIds(bimModelIds);
            }else{
                plan.setCcBimModelRelIds(null);
            }
            plan.updateById();

            //插入数据：施工进度模型（CC_CONSTRUCT_PROGRESS_BIM_MODEL_REL）
            //对模型ID进行拆分
            String[] bimModelIdArray = bimModelIds.split(",");
            for (String bimModelId : bimModelIdArray) {

                //判断数据是否存在，不重复添加
                if(!isRelBimModelRelExist(constructProgressPlanId, bimModelId)){
                    //提示添加成功
                    continue;
                }

                CcConstructProgressBimModelRel rel = CcConstructProgressBimModelRel.newData();
                rel.setTs(LocalDateTime.now());
                rel.setCrtDt(LocalDateTime.now());
                rel.setCrtUserId(userId);
                rel.setLastModiDt(LocalDateTime.now());
                rel.setLastModiUserId(userId);
                rel.setStatus("AP");
                rel.setCcConstructProgressPlanId(constructProgressPlanId);//施工进度计划ID
                rel.setCcDocFileId(bimModelId);//BIM模型ID
                rel.insertById();
            }
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 更新进度计划，当模型关联为空时，需要删除已有的关联关系
     * @param constructProgressPlanId 施工进度计划ID
     */
    private void deleteConstructProgressRel(String constructProgressPlanId) {
        Where where = new Where();
        where.eq("CC_CONSTRUCT_PROGRESS_PLAN_ID", constructProgressPlanId);
        int delNumber = CcConstructProgressBimModelRel.deleteByWhere(where);
        //日志打印
        if(delNumber > 0){
            log.info("已删除：{}条数据", delNumber);
        }
    }

    /**
     * 判断进度关联模型数据是否存在，不重复添加
     * @param constructProgressPlanId 施工进度计划ID
     * @param bimModelId BIM模型ID(doc_file_id)
     * @return  true:数据不存在，false:数据存在
     */
    private boolean isRelBimModelRelExist(String constructProgressPlanId, String bimModelId) {
        Where where = new Where();
        where.eq("CC_CONSTRUCT_PROGRESS_PLAN_ID", constructProgressPlanId).eq("CC_DOC_FILE_ID", bimModelId);
        //判断CcConstructProgressBimModelRel.selectByWhere(where)是否为null
        if (CcConstructProgressBimModelRel.selectOneByWhere(where) == null) {
            return true;
        }else{
            //不报错，直接跳过
            //throw new RuntimeException("数据已存在，请勿重复添加！");
            return false;
        }
    }

    /**
     * 施工进度删除后取消模型关联（施工进度删除前）
     */
    public void constructProgressRelDelete(){

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            String constructProgressPlanId = valueMap.get("ID").toString();//施工进度计划ID

            //判断施工进度计划是否已被项目结构节点引用
            if(isExistByStructNode(constructProgressPlanId)){
                //提示：施工进度已被项目结构节点引用，不能删除
                continue;
            }

            //删除数据：施工进度模型（CC_CONSTRUCT_PROGRESS_BIM_MODEL_REL）
            deleteConstructProgressRel(constructProgressPlanId);
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 判断施工进度计划是否已被项目结构节点引用
     * @param constructProgressPlanId 施工进度计划ID
     * @return true:已被引用，false:未被引用
     */
    private boolean isExistByStructNode(String constructProgressPlanId) {
        Where where = new Where();
        where.eq("CC_CONSTRUCT_PROGRESS_PLAN_ID", constructProgressPlanId);
        //判断CC_PRJ_STRUCT_NODE中是否存在数据关联了CC_CONSTRUCT_PROGRESS_PLAN_ID
        List<CcPrjStructNode> nodes = CcPrjStructNode.selectByWhere(where);
        if(nodes!= null && nodes.size() > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 创建后模型关联
     */
    public void constructProgressRelCreate(){
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            String constructProgressPlanId = valueMap.get("ID").toString();//施工进度计划ID

            //判断valueMap是否包含CC_BIM_MODEL_REL_IDS
            if(valueMap.containsKey("CC_BIM_MODEL_REL_IDS")){
                //模型IDs
                String bimModelIds = valueMap.get("CC_BIM_MODEL_REL_IDS").toString();
                if(bimModelIds != null){
                    String[] bimModelIdArray = bimModelIds.split(",");
                    for (String bimModelId : bimModelIdArray) {

                        //判断数据是否存在，不重复添加
                        if(!isRelBimModelRelExist(constructProgressPlanId, bimModelId)){
                            //提示添加成功
                            continue;
                        }

                        CcConstructProgressBimModelRel rel = CcConstructProgressBimModelRel.newData();
                        rel.setTs(LocalDateTime.now());
                        rel.setCrtDt(LocalDateTime.now());
                        rel.setCrtUserId(userId);
                        rel.setLastModiDt(LocalDateTime.now());
                        rel.setLastModiUserId(userId);
                        rel.setStatus("AP");
                        rel.setCcConstructProgressPlanId(constructProgressPlanId);//施工进度计划ID
                        rel.setCcDocFileId(bimModelId);//BIM模型ID
                        rel.insertById();
                    }
                }
            }
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 施工计划更新后的操作，针对模型关联的更新
     */
    public void constructProgressUpdate(){
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            String constructProgressPlanId = valueMap.get("ID").toString();//施工进度计划ID
            //删除数据：施工进度模型（CC_CONSTRUCT_PROGRESS_BIM_MODEL_REL）
            deleteConstructProgressRel(constructProgressPlanId);
            //判断valueMap是否包含CC_BIM_MODEL_REL_IDS
            if(valueMap.containsKey("CC_BIM_MODEL_REL_IDS") && valueMap.get("CC_BIM_MODEL_REL_IDS")!= null){
                //模型IDs
                String bimModelIds = valueMap.get("CC_BIM_MODEL_REL_IDS").toString();
                if(bimModelIds != null){
                    String[] bimModelIdArray = bimModelIds.split(",");
                    for (String bimModelId : bimModelIdArray) {

                        //判断数据是否存在，不重复添加
                        if(!isRelBimModelRelExist(constructProgressPlanId, bimModelId)){
                            //提示添加成功
                            continue;
                        }

                        CcConstructProgressBimModelRel rel = CcConstructProgressBimModelRel.newData();
                        rel.setTs(LocalDateTime.now());
                        rel.setCrtDt(LocalDateTime.now());
                        rel.setCrtUserId(userId);
                        rel.setLastModiDt(LocalDateTime.now());
                        rel.setLastModiUserId(userId);
                        rel.setStatus("AP");
                        rel.setCcConstructProgressPlanId(constructProgressPlanId);//施工进度计划ID
                        rel.setCcDocFileId(bimModelId);//BIM模型ID
                        rel.insertById();
                    }
                }
            }
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 施工节点更新后的操作，针对模型关联的更新
     */
    public void constructNodeUpdate(){
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            String structNodeId = valueMap.get("ID").toString();//施工进度计划ID
            //删除数据：施工进度模型（CC_CONSTRUCT_PROGRESS_BIM_MODEL_REL）
            deleteStructNodeRel(structNodeId);
            //判断valueMap是否包含CC_BIM_MODEL_REL_IDS
            if(valueMap.containsKey("CC_BIM_MODEL_COMPONENTS_IDS") && valueMap.get("CC_BIM_MODEL_COMPONENTS_IDS")!= null){
                //模型IDs
                String bimComponentsIds = valueMap.get("CC_BIM_MODEL_COMPONENTS_IDS").toString();
                if(bimComponentsIds != null){
                    String[] bimComponentIdArray = bimComponentsIds.split(",");
                    for (String bimComponentId : bimComponentIdArray) {

                        //判断数据是否存在，不重复添加
                        if(!isRelBimComponentRelExist(structNodeId, bimComponentId)){
                            //提示添加成功
                            continue;
                        }

                        CcStructNodeBimComponentsRel rel = CcStructNodeBimComponentsRel.newData();
                        rel.setTs(LocalDateTime.now());
                        rel.setCrtDt(LocalDateTime.now());
                        rel.setCrtUserId(userId);
                        rel.setLastModiDt(LocalDateTime.now());
                        rel.setLastModiUserId(userId);
                        rel.setStatus("AP");
                        rel.setCcPrjStructNodeId(structNodeId);//施工进度节点ID
                        rel.setCcBimModelComponentsId(bimComponentId);//BIM构件ID
                        rel.insertById();
                    }
                }
            }
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 判断施工节点构件数据是否存在，不重复添加
     * @param structNodeId 施工进度节点ID
     * @param bimComponentId 构件ID
     * @return  true:数据不存在，false:数据存在
     */
    private boolean isRelBimComponentRelExist(String structNodeId, String bimComponentId) {
        Where where = new Where();
        where.eq("CC_PRJ_STRUCT_NODE_ID", structNodeId).eq("CC_BIM_MODEL_COMPONENTS_ID", bimComponentId);
        if (CcStructNodeBimComponentsRel.selectOneByWhere(where) == null) {
            return true;
        }else{
            //不报错，直接跳过
            //throw new RuntimeException("数据已存在，请勿重复添加！");
            return false;
        }
    }

    /**
     * 删除数据：施工进度模型（CC_CONSTRUCT_PROGRESS_BIM_MODEL_REL）
     * @param structNodeId 施工进度节点ID
     */
    private void deleteStructNodeRel(String structNodeId) {
        Where where = new Where();
        where.eq("CC_PRJ_STRUCT_NODE_ID", structNodeId);
        int delNumber = CcStructNodeBimComponentsRel.deleteByWhere(where);
        //日志打印
        if(delNumber > 0){
            log.info("已删除：{}条数据", delNumber);
        }
    }

    /**
     * 施工进度节点关联构件更新
     */
    public void structNodeRelUpdate(){
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        //获取表单提交信息
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        //判断valueMap是否包含CC_BIM_MODEL_REL_IDS
        String bimModelComponentIds = "";
        if(varMap.containsKey("P_CC_BIM_MODEL_COMPONENTS_IDS") && varMap.get("P_CC_BIM_MODEL_COMPONENTS_IDS")!= null){
            //模型IDs
            bimModelComponentIds = varMap.get("P_CC_BIM_MODEL_COMPONENTS_IDS").toString();
        }

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            String structNodeId = valueMap.get("ID").toString();//施工进度计划ID

            //删除之前的关联关系
            deleteStructNodeRel(structNodeId);

            //是否关联模型构件
            boolean isRelBimModelComponent = false;
            //更新施工进度关联的BIM模型
            CcPrjStructNode plan = CcPrjStructNode.selectById(structNodeId);
            if(bimModelComponentIds != null && !bimModelComponentIds.isEmpty()){
                plan.setCcBimModelComponentsIds(bimModelComponentIds);
                isRelBimModelComponent = true;
                //更新字段：是否关联模型构件。
                plan.setCcIsRelBimModelComponent(isRelBimModelComponent);
            }else{
                plan.setCcBimModelComponentsIds(null);
                isRelBimModelComponent = false;
                //更新字段：是否关联模型构件。
                plan.setCcIsRelBimModelComponent(isRelBimModelComponent);
            }
            plan.updateById();
            //判断该节点是否有子节点，将其子节点的“是否关联模型构件”一并更新
            updateChildStructNodeRel(structNodeId, isRelBimModelComponent);

            //插入数据：施工节点构件（CC_STRUCT_NODE_BIM_COMPONENTS_REL）
            //对构件ID进行拆分
            if(bimModelComponentIds == null || bimModelComponentIds.isEmpty()){
                continue;
            }else{
                String[] bimModelComponentIdArray = bimModelComponentIds.split(",");
                for (String bimModelComponentId : bimModelComponentIdArray) {

                    //判断数据是否存在，不重复添加
                    if(!isRelBimComponentRelExist(structNodeId, bimModelComponentId)){
                        //提示添加成功
                        continue;
                    }

                    CcStructNodeBimComponentsRel rel = CcStructNodeBimComponentsRel.newData();
                    rel.setTs(LocalDateTime.now());
                    rel.setCrtDt(LocalDateTime.now());
                    rel.setCrtUserId(userId);
                    rel.setLastModiDt(LocalDateTime.now());
                    rel.setLastModiUserId(userId);
                    rel.setStatus("AP");
                    rel.setCcPrjStructNodeId(structNodeId);//施工进度计划节点ID
                    rel.setCcBimModelComponentsId(bimModelComponentId);//BIM模型构件ID
                    rel.insertById();
                }
            }
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 更新子节点的关联状态
     * @param structNodeId 节点ID
     * @param isRelBimModelComponent 是否关联模型构件
     */
    private void updateChildStructNodeRel(String structNodeId, boolean isRelBimModelComponent) {
        Where where = new Where();
        where.eq("CC_PRJ_STRUCT_NODE_PID", structNodeId);
        List<CcPrjStructNode> ccPrjStructNodeList = CcPrjStructNode.selectByWhere(where);
        if(ccPrjStructNodeList == null || ccPrjStructNodeList.isEmpty()){
            return;
        }
        for (CcPrjStructNode ccPrjStructNode : ccPrjStructNodeList) {
            ccPrjStructNode.setCcIsRelBimModelComponent(isRelBimModelComponent);
            ccPrjStructNode.updateById();
            updateChildStructNodeRel(ccPrjStructNode.getId(), isRelBimModelComponent);
        }
    }

    /**
     * 施工进度节点更新前操作（主要针对层级维护的变更）
     */
    public void constructNodeUpdatePre(){

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            //找到变更数据的序号，根据ID查询数据库数据，对比序号是否变更
            String structNodeId = valueMap.get("ID").toString();
            CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectById(structNodeId);
            //当前序号
            String currentSeqNo = valueMap.get("SEQ_NO").toString();
            //数据库序号
            String dbSeqNo = ccPrjStructNode.getSeqNo().toString();
            if(currentSeqNo.equals(dbSeqNo)){
                //提示：序号变更后，可能会影响层级维护，请谨慎操作！
                continue;
            }
            //删除之前的关联关系
            deleteStructNodeRel(structNodeId);
            //判断新的父节点是否关联模型构件

            if(valueMap.get("CC_PRJ_STRUCT_NODE_PID") == null){
                //层级更新后，没有父节点，则只处理当前节点之前的关联关系
                //当前节点的CC_IS_REL_BIM_MODEL_COMPONENT为false
                ccPrjStructNode.setCcIsRelBimModelComponent(false);
                ccPrjStructNode.setCcBimModelComponentsIds(null);//构件关联置空
                ccPrjStructNode.updateById();
                //判断该节点是否有子节点，将其子节点的“是否关联模型构件”一并更新
                updateChildStructNodeRel(ccPrjStructNode.getId(), false);
            }else{
                //新的父节点ID
                String newParentId = valueMap.get("CC_PRJ_STRUCT_NODE_PID").toString();
                //移动层级后有了新的父节点
                //判断父节点是否关联模型构件
                CcPrjStructNode newParentNode = CcPrjStructNode.selectById(newParentId);
                if(newParentNode.getCcIsRelBimModelComponent()){
                    ccPrjStructNode.setCcIsRelBimModelComponent(true);
                    ccPrjStructNode.setCcBimModelComponentsIds(null);//构件关联置空
                    ccPrjStructNode.updateById();
                    //判断该节点是否有子节点，将其子节点的“是否关联模型构件”一并更新
                    updateChildStructNodeRel(ccPrjStructNode.getId(), true);
                }
            }
        }
    }


    /**
     * 根据模型ID获取施工进度及其进度节点，便得到节点当前状态及关联的模型构件ID
     */
    public void getStructNodeByModelId(){
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
//        String csCommId = JdbcMapUtil.getString(inputMap, "csCommId");
        //测试ID：1920288243449733120,1912024978913730560
        String csCommId  = "1868474414879682560";

        CcDocFile ccDocFile = CcDocFile.selectById(csCommId);
        String ccAttachment = ccDocFile.getCcAttachment();
        if (SharedUtil.isEmpty(ccAttachment)) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getStructNodeByModelId");
            throw new BaseException(message);
        }
        List<Map<String, Object>> outputList = new ArrayList<>() ;

        //根据模型ID获取施工进度
        Where where1 = new Where();
        where1.eq("CC_DOC_FILE_ID", csCommId);
        List<CcConstructProgressBimModelRel> rel = CcConstructProgressBimModelRel.selectByWhere(where1);
        if(rel != null && !rel.isEmpty()){
            for (CcConstructProgressBimModelRel ccConstructProgressBimModelRel : rel) {
                String planId = ccConstructProgressBimModelRel.getCcConstructProgressPlanId();//施工进度ID
                MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
                String executeSql = "SELECT mc.CODE as componentId, node.CC_WBS_PROGRESS_STATUS_ID as status FROM CC_STRUCT_NODE_BIM_COMPONENTS_REL rel left join " +
                        "CC_PRJ_STRUCT_NODE node on node.ID = rel.CC_PRJ_STRUCT_NODE_ID left join cc_bim_model_components mc on mc.ID = rel.CC_BIM_MODEL_COMPONENTS_ID " +
                        "WHERE node.CC_CONSTRUCT_PROGRESS_PLAN_ID = ?";
                List<Map<String, Object>> result = myJdbcTemplate.queryForList(executeSql, planId);
                if(!result.isEmpty()){
                    outputList.addAll(result);
                }
            }
        }

        // 将List封装到Map中
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("data", outputList);

        ExtJarHelper.setReturnValue(outputMap);
    }
}
