package com.cisdi.ext.pm;

import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.invest.InvestAmtExt;
import com.cisdi.ext.util.WfPmInvestUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

/**
 * 初设概算审批-流程扩展
 */
public class PmPrjInvest2Ext {

    /**
     * 初设概算审批-发起时数据校验
     */
    public void invest2ProcessStartCheck(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程表名
        String entityCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        //流程业务表id
        String id = entityRecord.csCommId;
        //校验该项目是否已经发起过
        String errorMsg = "";
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            String projectName = JdbcMapUtil.getString(entityRecord.valueMap,"PROJECT_NAME_WR");
            if (projectName.contains("、")){
                throw new BaseException("该流程不允许多个项目同时发起，请重新填写单个项目信息！");
            }
            errorMsg = ProcessCommon.prjRepeatStartByName(entityCode,projectName,id,myJdbcTemplate);
        } else {
            errorMsg = ProcessCommon.prjRepeatStartById(entityCode,projectId,id,myJdbcTemplate);
        }
        if (!SharedUtil.isEmptyString(errorMsg)){
            throw new BaseException(errorMsg);
        }
    }

    /**
     * 初设概算审批-结束时数据校验处理
     */
    public void invest2ProcessEndCheck(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //校验资金金额
        InvestAmtExt.checkAmt(entityRecord);
        //流程表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        //流程业务表id
        String csCommId = entityRecord.csCommId;
        //项目id
        String pmPrjId = String.valueOf(entityRecord.valueMap.get("PM_PRJ_ID"));
        // 获取建设年限
        String year = JdbcMapUtil.getString(entityRecord.valueMap, "BUILD_YEARS");
        // 修改项目建设年限信息：
        Crud.from("PM_PRJ").where().eq("ID", pmPrjId).update().set("BUILD_YEARS", year).exec();
        //更新项目信息(基础信息、资金信息)
        PmPrjExt.updatePrjBaseData(entityRecord,"PM_PRJ_INVEST2",3,myJdbcTemplate,entCode);
        //创建项目投资测算汇总可研数据
        WfPmInvestUtil.calculateData(csCommId, entCode, pmPrjId);
    }
}
