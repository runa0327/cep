package com.cisdi.ext.pm.bidPurchase.detail;

import com.cisdi.ext.model.PmBuyDemandReqPrjDetail;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.sql.Crud;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class PmBuyDemandReqPrjDetailExt {

    /**
     * 新增信息
     * @param buyDemandId 采购需求审批id
     * @param projectId 项目id
     */
    public static void insertData(String buyDemandId, String projectId) {
        Crud.from(PmBuyDemandReqPrjDetail.ENT_CODE).where().eq(PmBuyDemandReqPrjDetail.Cols.PM_BUY_DEMAND_REQ_ID,buyDemandId).delete().exec();
        List<String> projectList = StringUtil.getStrToList(projectId,",");
        if (!CollectionUtils.isEmpty(projectList)){
            for (String tmp : projectList) {
                String id = Crud.from(PmBuyDemandReqPrjDetail.ENT_CODE).insertData();
                Crud.from(PmBuyDemandReqPrjDetail.ENT_CODE).where().eq("id",id).update()
                        .set(PmBuyDemandReqPrjDetail.Cols.PM_BUY_DEMAND_REQ_ID,buyDemandId)
                        .set("PM_PRJ_ID",tmp).exec();
            }
        }
    }
}
