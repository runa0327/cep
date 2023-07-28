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
 * 招采项目备案及归档属性联动
 */
public class PmBidKeepFileReqLink {

    /**
     * 招采项目备案及归档-属性联动-入口
     * @param myJdbcTemplate 数据源
     * @param attValue 属性联动值
     * @param entCode 业务表名
     * @param sevId 视图id
     * @param param 参数
     * @return 属性联动结果
     */
    public static AttLinkResult linkPM_BID_KEEP_FILE_REQ_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId, AttLinkParam param) {
        AttLinkResult attLinkResult = new AttLinkResult();
        //选择备案信息属性联系的流程
        List<String> needBeiAn = AttLinkDifferentProcess.getBeiAnList();
        if (needBeiAn.contains(entCode)){
            String sql1 = "select a.CUSTOMER_UNIT_ONE,(select name from pm_party where id = a.CUSTOMER_UNIT_ONE) as customerName," +
                    "(select name from ad_status where id = a.status) as statusName,a.status,a.CRT_USER_ID,CONTACTS_ONE,CONTACT_MOBILE_ONE," +
                    "(select name from ad_user where id = a.CRT_USER_ID) as userName,WIN_BID_UNIT_ONE from PM_BID_KEEP_FILE_REQ a where a.id = ?";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,attValue);
            if (!CollectionUtils.isEmpty(list1)){
                //审批状态
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"status");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"statusName");
                    attLinkResult.attMap.put("STATUS_ONE",linkedAtt);
                }
                //委托单位
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"CUSTOMER_UNIT_ONE");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"customerName");
                    attLinkResult.attMap.put("CUSTOMER_UNIT_ONE",linkedAtt);
                }
                //采购经办人
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"CRT_USER_ID");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"userName");
                    attLinkResult.attMap.put("PROCURE_USER_ID",linkedAtt);
                }
                //中标单位
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"WIN_BID_UNIT_ONE");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"WIN_BID_UNIT_ONE");
                    attLinkResult.attMap.put("WIN_BID_UNIT_ONE",linkedAtt);
                }
                //中标单位联系人
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"CONTACTS_ONE");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"CONTACTS_ONE");
                    attLinkResult.attMap.put("CONTACTS_ONE",linkedAtt);
                }
                //中标单位联系方式
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"CONTACT_MOBILE_ONE");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"CONTACT_MOBILE_ONE");
                    attLinkResult.attMap.put("CONTACT_MOBILE_ONE",linkedAtt);
                }
            }
        } else if ("PO_ORDER_REQ".equals(entCode)){ //合同签订
            //查询招采项目备案及归档信息
            String sql1 = "select PM_PRJ_ID,PAY_AMT_TWO,BUY_TYPE_ID,WIN_BID_UNIT_ONE,AMT_ONE,BUY_MATTER_ID from PM_BID_KEEP_FILE_REQ where id = ?";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,attValue);
            if (!CollectionUtils.isEmpty(list1)){
                //招标类别
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    String id = JdbcMapUtil.getString(list1.get(0),"BUY_TYPE_ID");
                    String name = "";
                    String sql2 = "select name from gr_set_value where id = ?";
                    List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,id);
                    if (!CollectionUtils.isEmpty(list2)){
                        name = JdbcMapUtil.getString(list2.get(0),"name");
                    }
                    linkedAtt.value = id;
                    linkedAtt.text = name;
                    attLinkResult.attMap.put("BUY_TYPE_ID",linkedAtt);
                }
                //招标控制价
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"PAY_AMT_TWO");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"PAY_AMT_TWO");
                    attLinkResult.attMap.put("BID_CTL_PRICE_LAUNCH",linkedAtt);
                }
                //采购方式
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    String id = JdbcMapUtil.getString(list1.get(0),"BUY_MATTER_ID");
                    String name = "";
                    String sql2 = "select name from gr_set_value where id = ?";
                    List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,id);
                    if (!CollectionUtils.isEmpty(list2)){
                        name = JdbcMapUtil.getString(list2.get(0),"name");
                    }
                    linkedAtt.value = id;
                    linkedAtt.text = name;
                    attLinkResult.attMap.put("BUY_MATTER_ID",linkedAtt);
                }
                //中标单位
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"WIN_BID_UNIT_ONE");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"WIN_BID_UNIT_ONE");
                    attLinkResult.attMap.put("WIN_BID_UNIT_ONE",linkedAtt);
                }
                //中标价
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"AMT_ONE");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"AMT_ONE");
                    attLinkResult.attMap.put("WINNING_BIDS_AMOUNT",linkedAtt);
                }
            }
        }
        return attLinkResult;
    }
}
