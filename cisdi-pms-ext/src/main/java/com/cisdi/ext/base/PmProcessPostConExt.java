package com.cisdi.ext.base;

import com.cisdi.ext.model.PmProcessPostCon;
import com.qygly.ext.jar.helper.sql.Where;

/**
 * 流程岗位关联信息-扩展
 */
public class PmProcessPostConExt {

    /**
     * 通过节点信息和业主单位获取岗位信息
     * @param nodeId 节点id
     * @param companyId 业主单位id
     * @return 岗位信息id
     */
    public static String getDeptIdByNode(String nodeId, String companyId) {
        String deptId = PmProcessPostCon.selectByWhere(new Where().eq(PmProcessPostCon.Cols.WF_NODE_ID,nodeId).eq(PmProcessPostCon.Cols.CUSTOMER_UNIT,companyId))
                .get(0).getBaseProcessPostId();
        return deptId;
    }
}
