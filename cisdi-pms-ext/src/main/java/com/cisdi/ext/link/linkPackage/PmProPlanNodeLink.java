package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.model.PmProPlanNode;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 进度计划节点属性联动
 */
public class PmProPlanNodeLink {

    /**
     *
     * @param myJdbcTemplate
     * @param attValue
     * @param entCode
     * @return
     */
    public static AttLinkResult linkForPM_PRO_PLAN_NODE_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();
        List<PmProPlanNode> list = PmProPlanNode.selectByWhere(new Where().eq(PmProPlanNode.Cols.ID,attValue));
        if (CollectionUtils.isEmpty(list)){
            throw new BaseException("对不起，未查询到该条记录进度计划明细，请联系管理员处理！");
        } else {
            int cha = list.get(0).getPlanTotalDays();
            LinkUtils.mapAddValueByValue("DURATION_ONE",String.valueOf(cha), cha,AttDataTypeE.INTEGER,attLinkResult);
        }
        return attLinkResult;
    }
}
