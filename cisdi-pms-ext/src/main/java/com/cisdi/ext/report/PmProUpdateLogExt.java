package com.cisdi.ext.report;

import com.cisdi.ext.model.PmProUpdateLog;
import com.qygly.ext.jar.helper.sql.Crud;

import java.util.Date;

/**
 * 流程台账修改日志
 */
public class PmProUpdateLogExt {

    /**
     * 数据创建
     * @param entCode 业务表id
     * @param sourceId 业务表记录id
     * @param prjId 项目id
     * @param proInstanceId 流程实例id
     * @param userId 操作人id
     * @param now 当前时间
     * @param msg 操作说明
     * @param operationType 操作类型
     */
    public static void createData(String entCode, String sourceId, String prjId, String proInstanceId, String userId, String now, String msg, String operationType) {
        String id = Crud.from(PmProUpdateLog.ENT_CODE).insertData();
        Crud.from(PmProUpdateLog.ENT_CODE).where().eq(PmProUpdateLog.Cols.ID,id).update()
                .set(PmProUpdateLog.Cols.WF_PROCESS_INSTANCE_ID,proInstanceId)
                .set(PmProUpdateLog.Cols.PM_PRJ_ID,prjId)
                .set(PmProUpdateLog.Cols.AD_ENT_ID,entCode)
                .set(PmProUpdateLog.Cols.ENTITY_RECORD_ID,sourceId)
                .set(PmProUpdateLog.Cols.AD_USER_ID,userId)
                .set(PmProUpdateLog.Cols.OPERATION_TYPE_ID,operationType)
                .set(PmProUpdateLog.Cols.STATUS,"AP")
                .set(PmProUpdateLog.Cols.SUBMIT_TIME,now)
                .set(PmProUpdateLog.Cols.TEXT_REMARK_ONE,msg)
                .exec();
    }
}
