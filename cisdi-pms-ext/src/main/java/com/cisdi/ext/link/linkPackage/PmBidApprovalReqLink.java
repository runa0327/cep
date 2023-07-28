package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.link.AttLinkParam;
import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 招标文件审批属性联动
 */
public class PmBidApprovalReqLink {

    /**
     * 招标文件审批-属性联动-入口
     * @param myJdbcTemplate 数据源
     * @param attValue 属性联动值
     * @param entCode 业务表名
     * @param sevId 视图id
     * @param param 参数
     * @return 属性联动结果
     */
    public static AttLinkResult linkPM_BID_APPROVAL_REQ_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId, AttLinkParam param) {
        AttLinkResult attLinkResult = new AttLinkResult();
        if ("PM_USE_CHAPTER_REQ".equals(entCode)){ //中选单位及标后用印审批
            String sql1 = "select a.BID_AGENCY,a.id,a.name,a.NAME_ONE,a.status,(select name from ad_status where id=a.status) as statusName from PM_BID_APPROVAL_REQ a where id = ?";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,attValue);
            if (CollectionUtils.isEmpty(list1)){ //全为空
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = "";
                    linkedAtt.text = "";
                    attLinkResult.attMap.put("APPROVED_AMOUNT",linkedAtt);
                    attLinkResult.attMap.put("STATUS_ONE",linkedAtt);
                    attLinkResult.attMap.put("NAME_ONE",linkedAtt);
                }
            } else {
                // 关联招标文件审批
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"id");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"name");
                    attLinkResult.attMap.put("APPROVED_AMOUNT",linkedAtt);
                }
                // 审批状态
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"status");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"statusName");
                    attLinkResult.attMap.put("STATUS_ONE",linkedAtt);
                }
                // 投标名称
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"NAME_ONE");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"NAME_ONE");
                    attLinkResult.attMap.put("NAME_ONE",linkedAtt);
                }
                //招标代理商
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"BID_AGENCY");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"BID_AGENCY");
                    attLinkResult.attMap.put("BID_AGENCY",linkedAtt);
                }
            }
        }
        return attLinkResult;
    }
}
