package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.base.GrSetValueExt;
import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.att.AttDataTypeE;

import java.math.BigDecimal;

/**
 * 采购方式属性联动
 */
public class BuyTypeLink {

    /**
     * 采购方式属性联动-入口
     * @param myJdbcTemplate 数据源
     * @param attValue 属性联动值
     * @param entCode 业务表名
     * @param sevId 视图id
     * @return 属性联动结果
     */
    public static AttLinkResult linkBUY_TYPE_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        String code = GrSetValueExt.getGrSetCode(myJdbcTemplate,attValue);
        String name = GrSetValueExt.getValueNameById(attValue);
        // 公开招标(open_bidding),竞争性磋商(competitive_consultations),竞争性谈判(competitive_negotiation),公开询价(public_inquiry),单一来源(single_source),直接委托(direct_entrustment)
        if ("PM_BID_APPROVAL_REQ".equals(entCode)){ //招标文件审批
            boolean changeToMandatory = false; //必填
            if ("open_bidding".equals(code) || "competitive_consultations".equals(code) || "competitive_negotiation".equals(code)){
                changeToMandatory = true;
            }
            //BID_AGENCY
            LinkUtils.mapAddAllValue("BID_AGENCY",AttDataTypeE.TEXT_LONG,(String)null,null,true,changeToMandatory,true,attLinkResult);
        } else if ("PM_PURCHASE_PROCESS_REQ".equals(entCode)){ // 采购过程管理
            purchaseProAttLink(attLinkResult,name);
        }
        return attLinkResult;
    }

    /**
     * 采购过程管理-采购方式-属性联动
     * @param attLinkResult 返回结果只
     * @param name 属性联动名称
     */
    private static void purchaseProAttLink(AttLinkResult attLinkResult, String name) {
        boolean gongKaiShow = false, gongKaiEdit = false; // 公开类
        boolean notGongKaiShow = false, notGongKaiEdit = false; // 非公开类

        if (name.contains("公开")){
            gongKaiShow = true;
            gongKaiEdit = true;
            attLinkResult.childShow.put("1686574747338604544", false);
            attLinkResult.childShow.put("1686574170722467840", true);
        } else {
            notGongKaiEdit = true;
            notGongKaiShow = true;
            attLinkResult.childShow.put("1686574747338604544", true);
            attLinkResult.childShow.put("1686574170722467840", false);
        }
        LinkUtils.mapAddAllValue("DATE_ONE",AttDataTypeE.DATE,(String) null,null,gongKaiShow,false,gongKaiEdit,attLinkResult); // 报名时间
        LinkUtils.mapAddAllValue("DATE_TWO",AttDataTypeE.DATE,(String) null,null,gongKaiShow,false,gongKaiEdit,attLinkResult); // 开标日期
        LinkUtils.mapAddAllValue("RATE_ONE",AttDataTypeE.TEXT_LONG,(BigDecimal) null,null,gongKaiShow,false,gongKaiEdit,attLinkResult); // 下浮率
        LinkUtils.mapAddAllValue("WIN_BID_UNIT_ONE",AttDataTypeE.TEXT_LONG,(String) null,null,gongKaiShow,false,gongKaiEdit,attLinkResult); // 中标单位
        LinkUtils.mapAddAllValue("CONTACTS_ONE",AttDataTypeE.TEXT_LONG,(String) null,null,gongKaiShow,false,gongKaiEdit,attLinkResult); // 中选企业联系人
        LinkUtils.mapAddAllValue("CONTACT_MOBILE_ONE",AttDataTypeE.TEXT_LONG,(String) null,null,gongKaiShow,false,gongKaiEdit,attLinkResult); // 联系电话
        LinkUtils.mapAddAllValue("TEXT_REMARK_ONE",AttDataTypeE.TEXT_LONG,(String) null,null,gongKaiShow,false,gongKaiEdit,attLinkResult); // 详细评审情况
        LinkUtils.mapAddAllValue("FILE_ID_ONE",AttDataTypeE.FILE_GROUP,(String) null,null,gongKaiShow,false,gongKaiEdit,attLinkResult); // 公开招标项目评标报告
        LinkUtils.mapAddAllValue("FILE_ID_TWO",AttDataTypeE.FILE_GROUP,(String) null,null,gongKaiShow,false,gongKaiEdit,attLinkResult); // 中标通知书
        LinkUtils.mapAddAllValue("FILE_ID_THREE",AttDataTypeE.FILE_GROUP,(String) null,null,gongKaiShow,false,false,attLinkResult); // 备案回执
        LinkUtils.mapAddAllValue("FILE_ID_FOUR",AttDataTypeE.FILE_GROUP,(String) null,null,gongKaiShow,false,false,attLinkResult); // 采购其他资料
        LinkUtils.mapAddAllValue("AMT_FIVE",AttDataTypeE.TEXT_LONG,(BigDecimal) null,null,notGongKaiShow,false,notGongKaiEdit,attLinkResult); // 预算金额
        LinkUtils.mapAddAllValue("AMT_SIX",AttDataTypeE.TEXT_LONG,(BigDecimal) null,null,notGongKaiShow,false,notGongKaiEdit,attLinkResult); // 最低报价
        LinkUtils.mapAddAllValue("AMT_SEVEN",AttDataTypeE.TEXT_LONG,(BigDecimal) null,null,notGongKaiShow,false,notGongKaiEdit,attLinkResult); // 中标金额
        LinkUtils.mapAddAllValue("SELECT_METHOD",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,notGongKaiEdit,attLinkResult); // 选取方法
        LinkUtils.mapAddAllValue("DATE_THREE",AttDataTypeE.DATE,(String) null,null,notGongKaiShow,false,notGongKaiEdit,attLinkResult); // 选取日期
        LinkUtils.mapAddAllValue("WIN_BID_UNIT_TWO",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,notGongKaiEdit,attLinkResult); // 选取企业
        LinkUtils.mapAddAllValue("CONTACTS_TWO",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,notGongKaiEdit,attLinkResult); // 选取企业联系人
        LinkUtils.mapAddAllValue("CONTACT_MOBILE_TWO",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,notGongKaiEdit,attLinkResult); // 联系电话
        LinkUtils.mapAddAllValue("TEXT_REMARK_TWO",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,notGongKaiEdit,attLinkResult); // 选取结论
        LinkUtils.mapAddAllValue("TEXT_REMARK_THREE",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,notGongKaiEdit,attLinkResult); // 备注说明
        LinkUtils.mapAddAllValue("TEXT_REMARK_FOUR",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 需求部门负责人意见
        LinkUtils.mapAddAllValue("TEXT_REMARK_FIVE",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 成本部负责人意见
        LinkUtils.mapAddAllValue("TEXT_REMARK_SIX",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 采购部负责人意见
        LinkUtils.mapAddAllValue("PM_PRJ_ID_ONE",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 项目名称
        LinkUtils.mapAddAllValue("BUY_TYPE_ONE_ID",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 采购方式
        LinkUtils.mapAddAllValue("AMT_EIGHTH",AttDataTypeE.TEXT_LONG,(BigDecimal) null,null,notGongKaiShow,false,false,attLinkResult); // 预算金额
        LinkUtils.mapAddAllValue("DATE",AttDataTypeE.DATE,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 备案时间
        LinkUtils.mapAddAllValue("AMT_NINTH",AttDataTypeE.TEXT_LONG,(BigDecimal) null,null,notGongKaiShow,false,false,attLinkResult); // 中标金额
        LinkUtils.mapAddAllValue("WIN_BID_UNIT_THREE",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 委托供应商名称
        LinkUtils.mapAddAllValue("WIN_BID_UNIT_FOUR",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 代理机构名称
        LinkUtils.mapAddAllValue("CONTACT_MOBILE_THREE",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 联系方式
        LinkUtils.mapAddAllValue("START_BASIS",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 启动依据
        LinkUtils.mapAddAllValue("SYS_TRUE_ONE",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 采购项目审批表
        LinkUtils.mapAddAllValue("SYS_TRUE_TWO",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 采购需求及预算表
        LinkUtils.mapAddAllValue("SYS_TRUE_THREE",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 平台选取记录
        LinkUtils.mapAddAllValue("SYS_TRUE_FOUR",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 中标公示
        LinkUtils.mapAddAllValue("SYS_TRUE_FIVE",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // (1)招标代理协议书
        LinkUtils.mapAddAllValue("SYS_TRUE_SIX",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // (2)招标代理机构资质材料
        LinkUtils.mapAddAllValue("SYS_TRUE_SEVEN",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // (3)招标代理机构营业执照
        LinkUtils.mapAddAllValue("TEXT_REMARK_SEVEN",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 回执意见
        LinkUtils.mapAddAllValue("AD_USER_ID",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 采购需求经办人:
        LinkUtils.mapAddAllValue("AD_USER_ONE_ID",AttDataTypeE.TEXT_LONG,(String) null,null,notGongKaiShow,false,false,attLinkResult); // 材料接收人:

    }
}
