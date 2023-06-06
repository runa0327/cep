package com.cisdi.ext.pm.orderManage.detail;

import com.cisdi.ext.model.PoOrderChangeReqPrjDetail;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.sql.Crud;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class PoOrderChangeReqPrjDetailExt {

    /**
     *
     * @param orderChangeId 合同需求变更id
     * @param projectId 项目id
     */
    public static void insertData(String orderChangeId, String projectId) {
        Crud.from(PoOrderChangeReqPrjDetail.ENT_CODE).where().eq(PoOrderChangeReqPrjDetail.Cols.PO_ORDER_CHANGE_REQ_ID,orderChangeId).delete().exec();
        List<String> projectList = StringUtil.getStrToList(projectId,",");
        if (!CollectionUtils.isEmpty(projectList)){
            for (String tmp : projectList) {
                String id = Crud.from(PoOrderChangeReqPrjDetail.ENT_CODE).insertData();
                Crud.from(PoOrderChangeReqPrjDetail.ENT_CODE).where().eq("id",id).update()
                        .set(PoOrderChangeReqPrjDetail.Cols.PO_ORDER_CHANGE_REQ_ID,orderChangeId)
                        .set("PM_PRJ_ID",tmp).exec();
            }
        }
    }
}
