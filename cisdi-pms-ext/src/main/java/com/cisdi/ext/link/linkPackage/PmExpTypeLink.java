package com.cisdi.ext.link.linkPackage;

import cn.hutool.core.util.IdUtil;
import com.cisdi.ext.link.AttLinkParam;
import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedRecord;
import com.cisdi.ext.model.PmExpType;
import com.cisdi.ext.model.PoGuaranteeLetterRequireReqFeeDetail;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 多选-费用类型属性联动
 */
public class PmExpTypeLink {

    /**
     * 费用类型属性联动-入口
     * @param myJdbcTemplate 数据源
     * @param attValue 联动值
     * @param entCode 表名
     * @param sevId 实体视图id
     * @param param 表单所有值
     * @return 属性联动结果
     */
    public static AttLinkResult linkForPmExpType(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId, AttLinkParam param) {
        AttLinkResult attLinkResult = new AttLinkResult();
        // 目前只涉及到新增保函
        String id = param.id;
        String[] arr = attValue.split(",");
        // 获取新增保函明细信息
        List<PoGuaranteeLetterRequireReqFeeDetail> feeDetailsList = getFeeDetail(id,entCode,arr,myJdbcTemplate);
        if ("0099902212142023567".equals(sevId)){ // 新增保函-发起
            createFeeDetail(attLinkResult, feeDetailsList);
        }
        return attLinkResult;
    }

    /**
     * 新增保函属性联动显示费用明细
     * @param attLinkResult 回显数据
     * @param feeDetailsList 回显数据源值
     */
    private static void createFeeDetail(AttLinkResult attLinkResult, List<PoGuaranteeLetterRequireReqFeeDetail> feeDetailsList) {
        Map<String,Object> map = new HashMap<>();
        String viewId = "1685894950677577728"; // 视图-视图部分-费用明细id
        map.put("viewId",viewId);
        map.put("createTable",true);

        List<LinkedRecord> linkedRecordList = new ArrayList<>();

        for (PoGuaranteeLetterRequireReqFeeDetail tmp : feeDetailsList) {
            LinkedRecord linkedRecord = new LinkedRecord();

            // id
            linkedRecord.valueMap.put("ID",tmp.getId());
            linkedRecord.textMap.put("ID",tmp.getId());

            // ver
            linkedRecord.valueMap.put("VER",tmp.getVer());
            linkedRecord.textMap.put("VER",tmp.getVer().toString());

            // 费用名称
            String expTypeId = tmp.getPmExpTypeId();
            String expTypeName = PmExpType.selectById(expTypeId).getName();
            linkedRecord.valueMap.put("PM_EXP_TYPE_ID",expTypeId);
            linkedRecord.textMap.put("PM_EXP_TYPE_ID",expTypeId);
//            linkedRecord.textMap.put("PM_EXP_TYPE_ID",expTypeName);

            // 主表单id
            linkedRecord.valueMap.put("PO_GUARANTEE_LETTER_REQUIRE_REQ_ID",tmp.getPoGuaranteeLetterRequireReqId());
            linkedRecord.textMap.put("PO_GUARANTEE_LETTER_REQUIRE_REQ_ID",tmp.getPoGuaranteeLetterRequireReqId());

            // 合同金额
            BigDecimal amt = tmp.getContractAmount();
            if (amt != null){
                linkedRecord.valueMap.put("CONTRACT_AMOUNT",amt);
                linkedRecord.textMap.put("CONTRACT_AMOUNT",amt.toString());
            }

            // 预付款比例
            BigDecimal pre = tmp.getAdvanceChargePercent();
            if (pre != null){
                linkedRecord.valueMap.put("ADVANCE_CHARGE_PERCENT",pre);
                linkedRecord.textMap.put("ADVANCE_CHARGE_PERCENT",pre.toString());
            }

            // 预付款金额
            BigDecimal payAmt = tmp.getAdvanceChargeAmt();
            if (payAmt != null){
                linkedRecord.valueMap.put("ADVANCE_CHARGE_AMT",payAmt);
                linkedRecord.textMap.put("ADVANCE_CHARGE_AMT",payAmt.toString());
            }

            linkedRecordList.add(linkedRecord);
        }
        attLinkResult.childData.put(viewId, linkedRecordList);
        attLinkResult.childCreatable.put(viewId, false);
        attLinkResult.childClear.put(viewId, true);
    }

    /**
     * 费用类型属性联动-新增保函-费用明细
     * @param id 主表单记录id
     * @param entCode 主表单表名
     * @param arr 多选值数组
     * @param myJdbcTemplate 数据源
     * @return 费用明细信息
     */
    public static List<PoGuaranteeLetterRequireReqFeeDetail> getFeeDetail(String id, String entCode,String[] arr, MyJdbcTemplate myJdbcTemplate) {
        List<PoGuaranteeLetterRequireReqFeeDetail> feeDetailsList = new ArrayList<>();
        String detailCode = entCode + "_FEE_DETAIL";
        String sql = "select * from " + detailCode + " where PO_GUARANTEE_LETTER_REQUIRE_REQ_ID = ? and PM_EXP_TYPE_ID = ?";
        for (String value : arr) {
            PoGuaranteeLetterRequireReqFeeDetail poGuaranteeLetterRequireReqFeeDetail = new PoGuaranteeLetterRequireReqFeeDetail();
            List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,id,value);
            int ver = 1;
            String mainId;
            if (!CollectionUtils.isEmpty(list)){
                ver = (int)list.stream().max(Comparator.comparing(p->JdbcMapUtil.getInt(p,"VER"))).get().get("VER") + 1;
                Map<String,Object> map = list.get(0);
                String amt = JdbcMapUtil.getString(map,"CONTRACT_AMOUNT"); // 合同金额
                String pre = JdbcMapUtil.getString(map,"ADVANCE_CHARGE_PERCENT"); // 预付款比例
                String payAmt = JdbcMapUtil.getString(map,"ADVANCE_CHARGE_AMT"); // 预付款金额
                if (!SharedUtil.isEmptyString(amt)){
                    poGuaranteeLetterRequireReqFeeDetail.setContractAmount(new BigDecimal(amt));
                }
                if (!SharedUtil.isEmptyString(pre)){
                    poGuaranteeLetterRequireReqFeeDetail.setAdvanceChargePercent(new BigDecimal(pre));
                }
                if (!SharedUtil.isEmptyString(payAmt)){
                    poGuaranteeLetterRequireReqFeeDetail.setAdvanceChargeAmt(new BigDecimal(payAmt));
                }
                mainId = JdbcMapUtil.getString(map,"ID");
            } else {
                mainId = IdUtil.getSnowflakeNextIdStr();
            }
            poGuaranteeLetterRequireReqFeeDetail.setPoGuaranteeLetterRequireReqId(id);
            poGuaranteeLetterRequireReqFeeDetail.setVer(ver);
//            poGuaranteeLetterRequireReqFeeDetail.setId(mainId);
            poGuaranteeLetterRequireReqFeeDetail.setPmExpTypeId(value); // 费用类型
            feeDetailsList.add(poGuaranteeLetterRequireReqFeeDetail);
        }
        return feeDetailsList;
    }
}
