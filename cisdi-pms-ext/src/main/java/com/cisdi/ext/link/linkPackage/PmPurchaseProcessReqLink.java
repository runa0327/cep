package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.api.HrDeptExt;
import com.cisdi.ext.base.GrSetValueExt;
import com.cisdi.ext.base.PmPartyExt;
import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.model.PmPurchaseProcessReq;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class PmPurchaseProcessReqLink {

    /**
     * 招标选取与中标管理-属性联动
     * @param attValue 属性联动值
     * @param myJdbcTemplate 数据源
     * @return 属性联动结果
     */
    public static AttLinkResult linkForPM_PURCHASE_PROCESS_REQ_ID(String attValue, MyJdbcTemplate myJdbcTemplate) {
        AttLinkResult attLinkResult = new AttLinkResult();
        List<Map<String,Object>> list = myJdbcTemplate.queryForList("select * from PM_PURCHASE_PROCESS_REQ where id = ?",attValue);
        Map<String,Object> map = list.get(0);

        String projectId = JdbcMapUtil.getString(map,"PM_PRJ_IDS"); // 项目
        if (StringUtils.hasText(projectId)){
            String projectName = PmPrjExt.queryProjectNameByIds(projectId,myJdbcTemplate);
            LinkUtils.mapAddAllValue("PM_PRJ_IDS",AttDataTypeE.TEXT_LONG,projectId,projectName,true,true,false,attLinkResult);
        } else {
            throw new BaseException("项目信息不能为空，请联系管理员查看");
        }

        LinkUtils.mapAddAllValue("PM_PURCHASE_PROCESS_REQ_ID", AttDataTypeE.TEXT_LONG,JdbcMapUtil.getString(map,"ID"),JdbcMapUtil.getString(map,"NAME"),true,true,true,attLinkResult);   // 招标选取与中标管理

        String customerId = JdbcMapUtil.getString(map,"CUSTOMER_UNIT");  // 业主单位
        String customerName = PmPartyExt.getNameById(customerId);
        LinkUtils.mapAddAllValue("CUSTOMER_UNIT",AttDataTypeE.TEXT_LONG,customerId,customerName,true,false,false,attLinkResult);

        String companyId = JdbcMapUtil.getString(map,"COMPANY_ID");  // 公司
        String companyName = HrDeptExt.getNameById(companyId,myJdbcTemplate);
        LinkUtils.mapAddAllValue("COMPANY_ID",AttDataTypeE.TEXT_LONG,companyId,companyName,true,false,false,attLinkResult);

        String buyMatterId = JdbcMapUtil.getString(map,"BUY_MATTER_ID");  // 采购事项
        String buyMatterName = GrSetValueExt.getValueNameById(buyMatterId);
        LinkUtils.mapAddAllValue("BUY_MATTER_ID",AttDataTypeE.TEXT_LONG,buyMatterId,buyMatterName,true,false,false,attLinkResult);

        String bidPlatId = JdbcMapUtil.getString(map,"BID_PLATFORM"); // 招标平台
        String bidPlatName = GrSetValueExt.getValueNameById(bidPlatId);
        LinkUtils.mapAddAllValue("BID_PLATFORM",AttDataTypeE.TEXT_LONG,bidPlatId,bidPlatName,true,false,false,attLinkResult);
        return attLinkResult;
    }
}
