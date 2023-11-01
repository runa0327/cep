package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.api.HrDeptExt;
import com.cisdi.ext.base.AdUserExt;
import com.cisdi.ext.base.GrSetValueExt;
import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.pm.bidPurchase.PmBuyDemandReqExt;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 采购需求审批属性联动
 */
public class PmBuyDemandReqLink {

    /**
     * 采购需求审批字段属性联动
     * @param myJdbcTemplate 数据源
     * @param attValue 联动值
     * @param entCode 业务表名
     * @return 联动结果
     */
    public static AttLinkResult linkForPM_BUY_DEMAND_REQ_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();
        if ("PM_PURCHASE_PROCESS_REQ".equals(entCode)){ // 采购过程管理
            purchaseProcessAttLink(attValue,entCode,myJdbcTemplate,attLinkResult);
        } else if ("PM_USE_CHAPTER_REQ".equals(entCode)) { //中选单位及标后用印审批
            userChapterAttLink(attValue,myJdbcTemplate,attLinkResult);
        } else if ("PM_BID_APPROVAL_REQ".equals(entCode) || "PM_FILE_CHAPTER_REQ".equals(entCode)) { //招标文件审批 标前资料用印审批
            bidApprovalAttLink(attValue,myJdbcTemplate,attLinkResult);
        }
        return attLinkResult;
    }

    /**
     * 招标文件审批 标前资料用印审批 -采购需求审批属性联动
     * @param attValue 采购需求审批id
     * @param myJdbcTemplate 属性元
     * @param attLinkResult 属性联动返回值
     */
    private static void bidApprovalAttLink(String attValue, MyJdbcTemplate myJdbcTemplate, AttLinkResult attLinkResult) {
        List<Map<String,Object>> buyList = PmBuyDemandReqExt.queryById(attValue,myJdbcTemplate); // 获取采购需求审批信息
        if (!CollectionUtils.isEmpty(buyList)){
            String buyTypeId = JdbcMapUtil.getString(buyList.get(0),"BUY_TYPE_ID"); // 招采方式
            String buyTypeName = GrSetValueExt.getValueNameById(buyTypeId);
            LinkUtils.mapAddAllValue("BUY_TYPE_ID",AttDataTypeE.TEXT_LONG,buyTypeId,buyTypeName,false,false,false,attLinkResult);
        }
    }

    /**
     * 中选单位及标后用印审批-采购需求审批属性联动
     * @param attValue 采购需求审批id
     * @param myJdbcTemplate 属性元
     * @param attLinkResult 属性联动返回值
     */
    private static void userChapterAttLink(String attValue, MyJdbcTemplate myJdbcTemplate, AttLinkResult attLinkResult) {
        List<Map<String, Object>> preBidCheck = myJdbcTemplate.queryForList("select a.name,a.id,a.status,s.name statusName from pm_file_chapter_req a left join ad_status s on s.id = a.status where a.PM_BUY_DEMAND_REQ_ID = ? order by a.CRT_DT desc limit 1", attValue);
        List<Map<String, Object>> prjList = myJdbcTemplate.queryForList("select d.status,(select name from ad_status where id = d.status) as statusName,p.name,d.PM_PRJ_ID,d.BUY_TYPE_ID,d.PAY_AMT_TWO,d.BUY_MATTER_ID from PM_BUY_DEMAND_REQ d left join pm_prj p on p.id = d.PM_PRJ_ID where d.id = ?", attValue);
        if (!CollectionUtils.isEmpty(prjList)){
            Map<String,Object> prjMap = prjList.get(0);

            String projectId = JdbcMapUtil.getString(prjMap,"PM_PRJ_ID"); //项目
            String projectName = JdbcMapUtil.getString(prjMap,"name");
            LinkUtils.mapAddAllValue("PM_PRJ_ID",AttDataTypeE.TEXT_LONG,projectId,projectName,true,true,false,attLinkResult);

            String buyTypeId = JdbcMapUtil.getString(prjList.get(0),"BUY_TYPE_ID"); //采购方式
            LinkUtils.mapAddAllValue("BUY_TYPE_ID",AttDataTypeE.TEXT_LONG,buyTypeId,buyTypeId,false,false,false,attLinkResult);

            String payAmt = JdbcMapUtil.getString(prjList.get(0),"PAY_AMT_TWO"); //付款金额
            LinkUtils.mapAddAllValue("PAY_AMT_TWO",AttDataTypeE.DOUBLE,payAmt,payAmt,false,false,false,attLinkResult);

            String buyMatterId = JdbcMapUtil.getString(prjList.get(0),"BUY_MATTER_ID"); //采购事项
            LinkUtils.mapAddAllValue("BUY_MATTER_ID",AttDataTypeE.TEXT_LONG,buyMatterId,buyMatterId,false,false,false,attLinkResult);

            String status = JdbcMapUtil.getString(prjList.get(0),"status"); //审批状态
            String statusName = JdbcMapUtil.getString(prjList.get(0),"statusName");
            LinkUtils.mapAddAllValue("STATUS_THREE",AttDataTypeE.TEXT_LONG,status,statusName,true,false,false,attLinkResult);
        }
        if (!CollectionUtils.isEmpty(preBidCheck)){
            Map<String, Object> preBidCheckMap = preBidCheck.get(0);

            String id = JdbcMapUtil.getString(preBidCheckMap,"id"); //关联标前资料用印审批 PM_FILE_CHAPTER_REQ_ID
            String name = JdbcMapUtil.getString(preBidCheckMap,"name");
            LinkUtils.mapAddAllValue("PM_FILE_CHAPTER_REQ_ID",AttDataTypeE.TEXT_LONG,id,name,false,false,false,attLinkResult);

            String status = JdbcMapUtil.getString(preBidCheckMap,"status"); //关联标前资料用印审批状态 STATUS_TWO
            String statusName = JdbcMapUtil.getString(preBidCheckMap,"statusName");
            LinkUtils.mapAddAllValue("STATUS_TWO",AttDataTypeE.TEXT_LONG,status,statusName,false,false,false,attLinkResult);
        }
    }

    /**
     * 采购过程管理属性联动
     * @param attValue 属性值
     * @param entCode 业务表单
     * @param myJdbcTemplate 数据源
     * @param attLinkResult 属性联动返回值
     */
    private static void purchaseProcessAttLink(String attValue, String entCode, MyJdbcTemplate myJdbcTemplate, AttLinkResult attLinkResult) {
        List<Map<String,Object>> buyList = PmBuyDemandReqExt.queryById(attValue,myJdbcTemplate); // 获取采购需求审批信息
        if (!CollectionUtils.isEmpty(buyList)){
            String prjIds = JdbcMapUtil.getString(buyList.get(0),"PM_PRJ_IDS");
            String projectId = prjIds.split(",")[0];
            AttLinkExtDetail.autoPostUserNew(entCode,projectId,attLinkResult,myJdbcTemplate); // 岗位信息赋值
            PmPrjIdLink.prjNameAndCompanyValue(prjIds,projectId,attLinkResult,myJdbcTemplate); // 项目名称及内部管理单位赋值
            Map<String,Object> map = buyList.get(0);

            String needUserId = JdbcMapUtil.getString(map,"CRT_USER_ID"); // 需求经办人
            String needUserName = AdUserExt.getUserNameById(needUserId,myJdbcTemplate);
            LinkUtils.mapAddAllValue("OPERATOR_ONE_ID",AttDataTypeE.TEXT_LONG,needUserId,needUserName,true,true,true,attLinkResult);

            String buyUserId = JdbcMapUtil.getString(map,"AD_USER_TWENTY_ONE_ID"); // 采购经办人
            String buyName = AdUserExt.getUserName(buyUserId);
            LinkUtils.mapAddAllValue("AD_USER_TWENTY_ONE_ID",AttDataTypeE.TEXT_LONG,buyUserId,buyName,true,true,true,attLinkResult);

            String costUserId = JdbcMapUtil.getString(map,"AD_USER_EIGHTEEN_ID"); // 成本经办人
            String costName = AdUserExt.getUserName(costUserId);
            LinkUtils.mapAddAllValue("AD_USER_EIGHTEEN_ID",AttDataTypeE.TEXT_LONG,costUserId,costName,true,true,true,attLinkResult);

            String customerId = JdbcMapUtil.getString(map,"CUSTOMER_UNIT"); // 业主单位
            String customerName = HrDeptExt.getNameById(customerId,myJdbcTemplate);
            LinkUtils.mapAddAllValue("CUSTOMER_UNIT",AttDataTypeE.TEXT_LONG,customerId,customerName,true,true,false,attLinkResult);

            String buyMatterId = JdbcMapUtil.getString(map,"BUY_MATTER_ID"); // 采购事项
            String buyMatterName = GrSetValueExt.getValueNameById(buyMatterId);
            LinkUtils.mapAddAllValue("BUY_MATTER_ID",AttDataTypeE.TEXT_LONG,buyMatterId,buyMatterName,true,true,false,attLinkResult);

            String buyTypeId = JdbcMapUtil.getString(map,"BUY_TYPE_ID"); // 采购方式
            String buyTypeName = GrSetValueExt.getValueNameById(buyTypeId);
            LinkUtils.mapAddAllValue("BUY_TYPE_ID",AttDataTypeE.TEXT_LONG,buyTypeId,buyTypeName,true,true,false,attLinkResult);

            BuyTypeLink.purchaseProAttLink(attLinkResult,buyTypeName); // 采购方式引起的属性联动
        }
    }
}
