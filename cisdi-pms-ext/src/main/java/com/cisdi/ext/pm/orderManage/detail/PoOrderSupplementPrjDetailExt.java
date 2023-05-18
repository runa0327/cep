package com.cisdi.ext.pm.orderManage.detail;

import com.cisdi.ext.model.PoOrderSupplementPrjDetail;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.sql.Crud;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class PoOrderSupplementPrjDetailExt {

    /**
     * 新增信息
     * @param supplementId 补充协议流程id
     * @param projectId 项目id
     */
    public static void insertData(String supplementId, String projectId) {
        Crud.from(PoOrderSupplementPrjDetail.ENT_CODE).where().eq(PoOrderSupplementPrjDetail.Cols.PO_ORDER_SUPPLEMENT_REQ_ID,supplementId).delete().exec();
        List<String> projectList = StringUtil.getStrToList(projectId,",");
        if (!CollectionUtils.isEmpty(projectList)){
            for (String tmp : projectList) {
                String id = Crud.from(PoOrderSupplementPrjDetail.ENT_CODE).insertData();
                Crud.from(PoOrderSupplementPrjDetail.ENT_CODE).where().eq("id",id).update()
                        .set(PoOrderSupplementPrjDetail.Cols.PO_ORDER_SUPPLEMENT_REQ_ID,supplementId)
                        .set("PM_PRJ_ID",tmp).exec();
            }
        }
    }
}
