package com.bid.ext.cc;

import com.bid.ext.model.CcPrjToProcInst;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.SharedConstants;
import com.qygly.shared.ad.entity.StatusE;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.SharedUtil;

public class WfExt {

    /**
     * 关联项目与流程。
     */
    public void linkPrjWithProcInst() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String procInstId = ExtJarHelper.getProcInstId();

            Object ccPrjId = Crud.from(ExtJarHelper.getSevInfo().entityInfo.code).where().eq(SharedConstants.Cols.ID, entityRecord.valueMap.get(SharedConstants.Cols.ID)).select().specifyCols(CcPrjToProcInst.Cols.CC_PRJ_ID).execForValue();

            Where where = new Where();
            where.eq(CcPrjToProcInst.Cols.WF_PROCESS_INSTANCE_ID, procInstId);
            CcPrjToProcInst ccPrjToProcInst = CcPrjToProcInst.selectOneByWhere(where);
            if (ccPrjToProcInst == null) {
                // 无对应记录：

                CcPrjToProcInst newCcPrjToProcInst = CcPrjToProcInst.newData();
                newCcPrjToProcInst.setWfProcessInstanceId(procInstId);
                newCcPrjToProcInst.setCcPrjId(ccPrjId == null ? null : ccPrjId.toString());
                newCcPrjToProcInst.insertById();
            } else {
                // 有对应记录：

                // 若此前记录的项目ID和当前的项目ID不同，则更新：
                if (!SharedUtil.toStringEquals(ccPrjId, ccPrjToProcInst.getCcPrjId())) {
                    ccPrjToProcInst.setCcPrjId(ccPrjId == null ? null : ccPrjId.toString());
                    ccPrjToProcInst.updateById();
                }
            }
        }


    }

    public void setStatusToDr() {
        setStatus(StatusE.DR);
    }

    public void setStatusToAping() {
        setStatus(StatusE.APING);
    }

    public void setStatusToDn() {
        setStatus(StatusE.DN);
    }

    public void setStatusToAp() {
        setStatus(StatusE.AP);
    }

    public void setStatusToVding() {
        setStatus(StatusE.VDING);
    }

    public void setStatusToVd() {
        setStatus(StatusE.VD);
    }

    private void setStatus(StatusE status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String entCode = ExtJarHelper.getSevInfo().entityInfo.code;
        ExtJarHelper.getEntityRecordList().forEach(entityRecord -> {
            String id = EntityRecordUtil.getId(entityRecord);
            myJdbcTemplate.update("update " + entCode + " t set t.status=?,t.ver=t.ver+1,t.ts=now() where t.id=?", status.toString(), id);
        });
    }
}
