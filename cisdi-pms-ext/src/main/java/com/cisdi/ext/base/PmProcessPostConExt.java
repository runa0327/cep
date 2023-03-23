package com.cisdi.ext.base;

import com.cisdi.ext.model.PmProcessPostCon;
import com.qygly.ext.jar.helper.sql.Where;

import java.util.List;
import java.util.stream.Collectors;

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
    public static List<String> getDeptIdByNode(String nodeId, String companyId) {
        List<PmProcessPostCon> list = PmProcessPostCon.selectByWhere(new Where().eq(PmProcessPostCon.Cols.WF_NODE_ID,nodeId).eq(PmProcessPostCon.Cols.CUSTOMER_UNIT,companyId));
        List<String> deptList = list.stream().map(PmProcessPostCon::getBaseProcessPostId).collect(Collectors.toList());
        return deptList;
    }
}
