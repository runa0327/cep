package com.cisdi.ext.pm.bidPurchase.detail;

import com.cisdi.ext.model.PmPurchaseProcessReqPrjDetail;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.sql.Crud;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class PmPurchaseProcessPrjDetailExt {

    /**
     * 新增信息
     * @param purchaseProcessId 采购过程管理流程id
     * @param projectId 项目id
     */
    public static void insertData(String purchaseProcessId, String projectId) {
        Crud.from(PmPurchaseProcessReqPrjDetail.ENT_CODE).where().eq(PmPurchaseProcessReqPrjDetail.Cols.PM_PURCHASE_PROCESS_REQ_ID,purchaseProcessId).delete().exec();
        List<String> projectList = StringUtil.getStrToList(projectId,",");
        if (!CollectionUtils.isEmpty(projectList)){
            for (String tmp : projectList) {
                String id = Crud.from(PmPurchaseProcessReqPrjDetail.ENT_CODE).insertData();
                Crud.from(PmPurchaseProcessReqPrjDetail.ENT_CODE).where().eq("id",id).update()
                        .set(PmPurchaseProcessReqPrjDetail.Cols.PM_PURCHASE_PROCESS_REQ_ID,purchaseProcessId)
                        .set("PM_PRJ_ID",tmp).exec();
            }
        }
    }
}
