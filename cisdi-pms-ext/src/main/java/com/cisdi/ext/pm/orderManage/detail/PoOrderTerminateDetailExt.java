package com.cisdi.ext.pm.orderManage.detail;

import com.cisdi.ext.model.PoOrderTerminateReqPrjDetail;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.sql.Crud;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class PoOrderTerminateDetailExt {

    /**
     * 项目写入明细表
     * @param terminateId 合同终止id
     * @param projectId 项目id
     */
    public static void insertData(String terminateId, String projectId) {
        Crud.from(PoOrderTerminateReqPrjDetail.ENT_CODE).where().eq(PoOrderTerminateReqPrjDetail.Cols.PO_ORDER_TERMINATE_REQ_ID,terminateId).delete().exec();
        List<String> projectList = StringUtil.getStrToList(projectId,",");
        if (!CollectionUtils.isEmpty(projectList)){
            for (String tmp : projectList) {
                String id = Crud.from(PoOrderTerminateReqPrjDetail.ENT_CODE).insertData();
                Crud.from(PoOrderTerminateReqPrjDetail.ENT_CODE).where().eq("id",id).update()
                        .set(PoOrderTerminateReqPrjDetail.Cols.PO_ORDER_TERMINATE_REQ_ID,terminateId)
                        .set("PM_PRJ_ID",tmp).exec();
            }
        }
    }
}
