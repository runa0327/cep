package com.bid.ext.cc;

import com.bid.ext.model.CcConstructProgressBimModelRel;
import com.bid.ext.model.CcConstructProgressPlan;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;


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
        //模型IDs
        String bimModelIds = varMap.get("P_CC_BIM_MODEL_REL_IDS").toString();

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            String constructProgressPlanId = valueMap.get("ID").toString();//施工进度计划ID

            //更新施工进度关联的BIM模型
            CcConstructProgressPlan plan = CcConstructProgressPlan.selectById(constructProgressPlanId);
            plan.setCcBimModelRelIds(bimModelIds);
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

            //更新施工进度关联的BIM模型
//            CcConstructProgressPlan plan = CcConstructProgressPlan.selectById(constructProgressPlanId);
//            plan.setCcBimModelRelIds(null);//将模型关联置空
//            plan.updateById();

            //删除数据：施工进度模型（CC_CONSTRUCT_PROGRESS_BIM_MODEL_REL）
            Where where = new Where();
            where.eq("CC_CONSTRUCT_PROGRESS_PLAN_ID", constructProgressPlanId);
            int delNumber = CcConstructProgressBimModelRel.deleteByWhere(where);
            //日志打印
            if(delNumber > 0){
                log.info("已删除：{}条数据", delNumber);
            }
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
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
}
