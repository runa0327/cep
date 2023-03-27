package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.link.AttLinkParam;
import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkSql;
import com.cisdi.ext.link.LinkedAtt;
import com.cisdi.ext.model.LinkedAttModel;
import com.cisdi.ext.model.PmPrjInvest2;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目id属性联动
 */
public class PmPrjIdLink {

    /**
     * 概算回显对应map
     */
    public static final Map<String,String> INVEST2_MAP = new HashMap<>();
    static {
        INVEST2_MAP.put("PRJ_TOTAL_INVEST2","PRJ_TOTAL_INVEST"); //总投资
        INVEST2_MAP.put("PROJECT_AMT_INVEST2","PROJECT_AMT"); //工程费用
        INVEST2_MAP.put("CONSTRUCT_PRJ_AMT_INVEST2","CONSTRUCT_AMT"); //建安工程费
        INVEST2_MAP.put("EQUIP_BUY_AMT_INVEST2","EQUIP_AMT"); //设备采购费
        INVEST2_MAP.put("EQUIPMENT_COST_INVEST2","EQUIPMENT_COST"); //科研设备费
        INVEST2_MAP.put("PROJECT_OTHER_AMT_INVEST2","PROJECT_OTHER_AMT"); //工程其他费用
        INVEST2_MAP.put("LAND_AMT_INVEST2","LAND_AMT"); //土地征拆费用
        INVEST2_MAP.put("PREPARE_AMT_INVEST2","PREPARE_AMT"); //预备费
        INVEST2_MAP.put("CONSTRUCT_PERIOD_INVEST2","CONSTRUCT_PERIOD_INTEREST"); //建设期利息
        INVEST2_MAP.put("REPLY_DATE_INVEST2","REPLY_ACTUAL_DATE"); //批复日期
        INVEST2_MAP.put("REPLY_NO_INVEST2","REPLY_NO_WR"); //批复文号
        INVEST2_MAP.put("REPLY_FILE_INVEST2","REPLY_FILE"); //批复文件
    }

    /**
     * 财评回显对应map
     */
    public static final Map<String,String> INVEST3_MAP = new HashMap<>();
    static {
        INVEST3_MAP.put("PRJ_TOTAL_INVEST3","PRJ_TOTAL_INVEST"); //总投资
        INVEST3_MAP.put("PROJECT_AMT_INVEST3","PROJECT_AMT"); //工程费用
        INVEST3_MAP.put("CONSTRUCT_PRJ_AMT_INVEST3","CONSTRUCT_AMT"); //建安工程费
        INVEST3_MAP.put("EQUIP_BUY_AMT_INVEST3","EQUIP_AMT"); //设备采购费
        INVEST3_MAP.put("EQUIPMENT_COST_INVEST3","EQUIPMENT_COST"); //科研设备费
        INVEST3_MAP.put("PROJECT_OTHER_AMT_INVEST3","PROJECT_OTHER_AMT"); //工程其他费用
        INVEST3_MAP.put("LAND_AMT_INVEST3","LAND_AMT"); //土地征拆费用
        INVEST3_MAP.put("PREPARE_AMT_INVEST3","PREPARE_AMT"); //预备费
        INVEST3_MAP.put("CONSTRUCT_PERIOD_INVEST3","CONSTRUCT_PERIOD_INTEREST"); //建设期利息
        INVEST3_MAP.put("REPLY_DATE_INVEST3","FINANCIAL_ACTUAL_DATE"); //批复日期
        INVEST3_MAP.put("REPLY_NO_INVEST3","REPLY_NO_WR"); //批复文号
        INVEST3_MAP.put("REPLY_FILE_INVEST3","FINANCIAL_REVIEW_FILE"); //批复文件
    }

    /**
     * 项目id属性联动
     * @param myJdbcTemplate 数据源
     * @param attValue 属性联动值
     * @param entCode 业务表名
     * @return 回显值
     */
    public static AttLinkResult linkForPM_PRJ_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();
        if ("PM_POST_APPOINT".equals(entCode)){ //岗位指派单独查询项目信息逻辑
            AttLinkExtDetail.selectPostAppointLink(attLinkResult,attValue,myJdbcTemplate);
        } else {
            //查询项目相关信息
            List<Map<String, Object>> list = LinkSql.PmPrjIdLink(attValue,myJdbcTemplate);

            //清空项目基础信息
            AttLinkExtDetail.clearBaseProjectDataNOPrj(attLinkResult);
            AttLinkExtDetail.clearProjectAmtData(attLinkResult);

            if (!CollectionUtils.isEmpty(list)){
                //需要自动岗位人员的流程
//                List<String> processUserList = AttLinkDifferentProcess.getLinkUserProcess();
//                if (processUserList.contains(entCode)){
                //清除岗位信息
//                    AttLinkExtDetail.clearPostUserData(attLinkResult);
                //业主单位
//                    String companyId = JdbcMapUtil.getString(list.get(0),"customer_id");
//                    AttLinkExtDetail.processLinkUser(attValue,entCode,companyId,attLinkResult,myJdbcTemplate);
//                }

                Map row = list.get(0);
                if ("PM_PRJ_KICK_OFF_REQ".equals(entCode)) { // 工程开工报审
                    AttLinkProcessDetail.pmPrjKickOffReqPrjLink(attLinkResult,attValue,myJdbcTemplate);
                } else if ("PIPELINE_RELOCATION_REQ".equals(entCode)){ // 管线迁改
                    //设计部人员
                    String design = JdbcMapUtil.getString(row,"PRJ_DESIGN_USER_ID");
                    AttLinkProcessDetail.pipelineLink(design,attLinkResult);
                } else if("PM_BUY_DEMAND_REQ".equals(entCode) || "PIPELINE_RELOCATION_REQ".equals(entCode)) { // 采购需求审批 管线迁改
                    // 0099799190825080705 = 企业自筹
                    String id = JdbcMapUtil.getString(row, "INVESTMENT_SOURCE_ID");
                    AttLinkExtDetail.assignmentPrjYesNoOne(id,attLinkResult);
                } else if ("PM_PRJ_SETTLE_ACCOUNTS".equals(entCode)){ //项目结算审批
                    settlePrjLink(attLinkResult,attValue);
                }
                //赋值
                AttLinkExtDetail.assignmentAttLinkResult(attLinkResult,row,entCode,myJdbcTemplate);

                // 资金信息回显。优先级 可研估算<初设概算<预算财<项目结算
                List<String> amtList = AttLinkDifferentProcess.getAmtList();
                if (amtList.contains(entCode)) {
                    // 查询预算财评信息
                    Map resultRow1 = AttLinkExtDetail.getAmtMap(attValue,myJdbcTemplate);
                    attLinkResult = AttLinkExtDetail.getResult(resultRow1, attLinkResult);
                }
            }
        }
        return attLinkResult;
    }

    /**
     * 项目结算审批-关联带出概算、财评资金信息
     * @param attLinkResult 返回集合值
     * @param attValue 项目id
     */
    public static void settlePrjLink(AttLinkResult attLinkResult, String attValue) {
        List<LinkedAttModel> list = new ArrayList<>();
        //查询概算信息
        List<Map<String,Object>> list2 = Crud.from("PM_PRJ_INVEST2").where().eq("id",attValue).select().execForMapList();
        if (!CollectionUtils.isEmpty(list2)){
            for (String key : INVEST2_MAP.keySet()){
                LinkedAttModel linkedAttModel = new LinkedAttModel();
                String value = JdbcMapUtil.getString(list2.get(0),INVEST2_MAP.get(key));
                linkedAttModel.setKey(key);
                linkedAttModel.setValue(value);
            }
        }
    }
}
