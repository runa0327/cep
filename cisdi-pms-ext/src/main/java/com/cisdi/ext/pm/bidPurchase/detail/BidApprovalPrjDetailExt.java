package com.cisdi.ext.pm.bidPurchase.detail;

import com.cisdi.ext.model.PmBidApprovalPrjDetail;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.sql.Crud;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 招标文件审批-项目明细扩展
 */
public class BidApprovalPrjDetailExt {

    /**
     *新增信息
     * @param csCommId 流程主表单id
     * @param projectIds 项目id
     */
    public static void createData(String csCommId, String projectIds) {
        Crud.from(PmBidApprovalPrjDetail.ENT_CODE).where().eq(PmBidApprovalPrjDetail.Cols.PM_BID_APPROVAL_REQ_ID,csCommId).delete().exec();
        List<String> prjList = StringUtil.getStrToList(projectIds,",");
        if (!CollectionUtils.isEmpty(prjList)){
            for (String tmp : prjList) {
                insertData(tmp,csCommId);
            }
        }
    }

    /**
     * 插入单条数据
     * @param projectId 项目id
     * @param csCommId 主表单id
     */
    public static void insertData(String projectId, String csCommId) {
        String id = Crud.from(PmBidApprovalPrjDetail.ENT_CODE).insertData();
        Crud.from(PmBidApprovalPrjDetail.ENT_CODE).where().eq(PmBidApprovalPrjDetail.Cols.ID,id).update()
                .set(PmBidApprovalPrjDetail.Cols.PM_PRJ_ID,projectId)
                .set(PmBidApprovalPrjDetail.Cols.PM_BID_APPROVAL_REQ_ID,csCommId)
                .set(PmBidApprovalPrjDetail.Cols.STATUS,"AP")
                .exec();
    }
}
