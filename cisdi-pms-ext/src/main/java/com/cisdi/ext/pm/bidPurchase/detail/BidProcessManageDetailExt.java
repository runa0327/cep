package com.cisdi.ext.pm.bidPurchase.detail;

import com.cisdi.ext.model.BidProcessManagelPrjDetail;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.sql.Crud;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class BidProcessManageDetailExt {

    /**
     * 流程办结写入数据
     * @param processId 流程业务表id
     * @param projectId 项目id
     */
    public static void insertData(String processId, String projectId) {
        Crud.from(BidProcessManagelPrjDetail.ENT_CODE).where().eq(BidProcessManagelPrjDetail.Cols.BID_PROCESS_MANAGE_ID,processId).delete().exec();
        List<String> prjList = StringUtil.getStrToList(projectId,",");
        if (!CollectionUtils.isEmpty(prjList)){
            for (String tmp : prjList) {
                String id = Crud.from(BidProcessManagelPrjDetail.ENT_CODE).insertData();
                Crud.from(BidProcessManagelPrjDetail.ENT_CODE).where().eq("ID",id).update()
                        .set(BidProcessManagelPrjDetail.Cols.PM_PRJ_ID,tmp)
                        .set(BidProcessManagelPrjDetail.Cols.BID_PROCESS_MANAGE_ID,processId)
                        .set(BidProcessManagelPrjDetail.Cols.STATUS,"AP")
                        .exec();
            }
        }
    }
}
