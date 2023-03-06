package com.cisdi.ext.pm;

import com.cisdi.ext.base.PmPrj;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

/**
 * 可研报告审批-流程扩展
 */
public class PmPrjInvest1Ext {

    /**
     * 可研报告审批-发起时数据校验
     */
    public void invest1ProcessStartCheck(){
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
     * 可研报告审批-结束时数据校验处理
     */
    public void invest1ProcessEndCheck(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //更新项目信息(基础信息、资金信息)
        PmPrj.updatePrjBaseData(entityRecord,"PM_PRJ_INVEST1",2,myJdbcTemplate);

        //评审组织单位入库
        String reviewOrganizationName = JdbcMapUtil.getString(entityRecord.valueMap,"REVIEW_ORGANIZATION_UNIT"); //评审组织单位
        PmInLibraryExt.updateOrCreateParty(reviewOrganizationName,"IS_REVIEW",myJdbcTemplate);
    }
}
