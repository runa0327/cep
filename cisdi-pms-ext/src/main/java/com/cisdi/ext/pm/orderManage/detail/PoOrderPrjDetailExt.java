package com.cisdi.ext.pm.orderManage.detail;

import com.cisdi.ext.model.PoOrderPrjDetail;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 合同签订-项目名称(流程内) 扩展
 */
public class PoOrderPrjDetailExt {

    /**
     * 新增信息
     * @param entityRecord 数据源
     */
    public static void createData(EntityRecord entityRecord) {
        String csCommId = entityRecord.csCommId;
        Crud.from("PO_ORDER_PRJ_DETAIL").delete().exec();
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_IDS");
        List<String> projectList = StringUtil.getStrToList(projectId,",");
        if (!CollectionUtils.isEmpty(projectList)){
            for (String tmp : projectList) {
                String id = Crud.from("PO_ORDER_PRJ_DETAIL").insertData();
                Crud.from("PO_ORDER_PRJ_DETAIL").where().eq("id",id).update()
                        .set("PO_ORDER_REQ_ID",csCommId).set("PM_PRJ_ID",tmp).exec();
            }
        }

    }
}
