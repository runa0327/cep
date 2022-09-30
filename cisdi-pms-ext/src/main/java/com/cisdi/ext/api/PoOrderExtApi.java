package com.cisdi.ext.api;

import com.cisdi.ext.model.view.order.PoOrderContactsView;
import com.cisdi.ext.model.view.order.PoOrderDtlProView;
import com.cisdi.ext.model.view.order.PoOrderDtlView;
import com.cisdi.ext.model.view.order.PoOrderView;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PoOrderExtApi {

    // 采购合同列表
    public void getPoOrderList() {
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PoOrderView param = JsonUtil.fromJson(json, PoOrderView.class);
        if (SharedUtil.isEmptyString(param.projectId)) {
            throw new BaseException("接口调用失败，项目id不能为空");
        }
        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        String sql = "SELECT a.ORDER_PROCESS_TYPE,a.id, a.PM_PRJ_ID, b.`NAME` as projectName, a.name as contractName,c.`NAME` as statusName, a.OPPO_SITE, a.OPPO_SITE_LINK_MAN, a.OPPO_SITE_CONTACT,a.AGENT, a.AGENT_PHONE, a.CONTRACT_AMOUNT, a.FILE_ATTACHMENT_URL,a.REMARK FROM PO_ORDER a LEFT JOIN pm_prj b on a.PM_PRJ_ID = b.ID LEFT JOIN ad_status c on a.`STATUS` = c.id where 1 = 1 and a.PM_PRJ_ID = ?";
        String sql2 = "SELECT count(*) as num FROM PO_ORDER a LEFT JOIN pm_prj b on a.PM_PRJ_ID = b.ID LEFT JOIN ad_status c on a.`STATUS` = c.id where 1 = 1 and a.PM_PRJ_ID = ? ";
        sb.append(sql);
        sb2.append(sql2);
        // 合同名称
        if (!SharedUtil.isEmptyString(param.projectName)) {
            sb.append(" and b.Name like ('%" + param.projectName + "%') ");
            sb2.append(" and b.Name like ('%" + param.projectName + "%') ");
        }
        if (!SharedUtil.isEmptyString(param.contractName)) {
            sb.append(" and a.Name like ('%" + param.contractName + "%') ");
            sb2.append(" and a.Name like ('%" + param.contractName + "%') ");
        }
        // 合同总价
        if (param.contractAmount != null) {
            sb.append(" and a.CONTRACT_AMOUNT like ('%" + param.contractAmount + "%') ");
            sb2.append(" and a.CONTRACT_AMOUNT like ('%" + param.contractAmount + "%') ");
        }
        // 来源
        if (!SharedUtil.isEmptyString(param.orderProcessType)){
            sb.append(" and a.order_process_type like ('%" + param.orderProcessType + "%') ");
            sb2.append(" and a.ORDER_PROCESS_TYPE like ('%" + param.orderProcessType + "%') ");
        }
        sb.append("order BY a.CRT_DT DESC ").append(limit);
        sb2.append("order BY a.CRT_DT DESC");
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString(), param.projectId);
        List<Map<String, Object>> list2 = myJdbcTemplate.queryForList(sb2.toString(), param.projectId);
        Map<String, Object> map1 = new HashMap<>();
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(null);
        } else {
            List<PoOrderView> inputList = list.stream().map(p -> {
                PoOrderView poOrderView = new PoOrderView();
                poOrderView.id = p.get("id").toString();
                poOrderView.projectId = p.get("PM_PRJ_ID").toString();
                poOrderView.projectName = JdbcMapUtil.getString(p, "projectName");
                poOrderView.contractName = JdbcMapUtil.getString(p, "contractName");
                poOrderView.oppoSite = JdbcMapUtil.getString(p, "OPPO_SITE");
                poOrderView.oppoSiteLinkMan = JdbcMapUtil.getString(p, "OPPO_SITE_LINK_MAN");
                poOrderView.oppoSiteContact = JdbcMapUtil.getString(p, "OPPO_SITE_CONTACT");
                poOrderView.agent = JdbcMapUtil.getString(p, "AGENT");
                poOrderView.agentPhone = JdbcMapUtil.getString(p, "AGENT_PHONE");
                poOrderView.contractAmount = new BigDecimal(JdbcMapUtil.getString(p, "CONTRACT_AMOUNT"));
                poOrderView.statusName = JdbcMapUtil.getString(p, "statusName");
                poOrderView.remark = JdbcMapUtil.getString(p, "REMARK");
                poOrderView.orderProcessType = JdbcMapUtil.getString(p, "ORDER_PROCESS_TYPE");
                poOrderView.fileId = SharedUtil.isEmptyString(JdbcMapUtil.getString(p, "FILE_ATTACHMENT_URL")) ? "" : JdbcMapUtil.getString(p, "FILE_ATTACHMENT_URL");
                return poOrderView;
            }).collect(Collectors.toList());
            map1.put("result", inputList);
            map1.put("total", Integer.valueOf(list2.get(0).get("num").toString()));
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map1), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    // 采购合同明细
    public void getPoOrderDetail() {
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PoOrderDtlView param = JsonUtil.fromJson(json, PoOrderDtlView.class);
        if (SharedUtil.isEmptyString(param.poOrderId)) {
            throw new BaseException("接口调用失败，采购合同id不能为空");
        }
        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        String sql1 = "SELECT a.TOTAL_AMT,a.FEE_DETAIL,a.id,a.CONTRACT_ID,b.NAME,a.COST_TYPE_TREE_ID,c.CODE as feeCode,c.NAME as feeName,a.PAY_TYPE,a.AMT,a.WORK_CONTENT,a.FILE_ATTACHMENT_URL,a.REMARK " +
                "FROM po_order_dtl a LEFT JOIN po_order b on a.CONTRACT_ID = b.id LEFT JOIN gr_set_value c on a.COST_TYPE_TREE_ID = c.id WHERE 1=1 and a.PO_ORDER_ID = ? ";
        String sql2 = "SELECT count(*) as num FROM po_order_dtl a LEFT JOIN po_order b on a.CONTRACT_ID = b.id LEFT JOIN gr_set_value c on a.COST_TYPE_TREE_ID = c.id WHERE 1=1 and a.PO_ORDER_ID = ? ";
        sb.append(sql1);
        sb2.append(sql2);
        if (!SharedUtil.isEmptyString(param.pmExpTypeId)) {
            sb.append(" and a.COST_TYPE_TREE_ID = '" + param.pmExpTypeId + "' ");
            sb2.append(" and a.COST_TYPE_TREE_ID = '" + param.pmExpTypeId + "' ");
        }
        if (!SharedUtil.isEmptyString(param.payType)) {
            sb.append(" and a.pay_type like ('%").append(param.payType).append("%') ");
            sb2.append(" and a.pay_type like ('%").append(param.payType).append("%') ");
        }
        if (!SharedUtil.isEmptyString(param.amt)) {
            sb.append(" and a.amt like ('%").append(param.amt).append("%') ");
            sb2.append(" and a.amt like ('%").append(param.amt).append("%') ");
        }
        if (!SharedUtil.isEmptyString(param.workContent)) {
            sb.append(" and a.WORK_CONTENT like ('%").append(param.workContent).append("%') ");
            sb2.append(" and a.WORK_CONTENT like ('%").append(param.workContent).append("%') ");
        }
        sb.append("order BY a.CRT_DT DESC ").append(limit);
        sb2.append("order BY a.CRT_DT DESC");
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString(), param.poOrderId);
        List<Map<String, Object>> list2 = myJdbcTemplate.queryForList(sb2.toString(), param.poOrderId);
        Map<String, Object> ret = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            List<PoOrderDtlView> view = list.stream().map(p -> {
                PoOrderDtlView poOrderDtlView = new PoOrderDtlView();
                poOrderDtlView.id = JdbcMapUtil.getString(p, "id");
                poOrderDtlView.poOrderId = p.get("CONTRACT_ID").toString();
                poOrderDtlView.poOrderName = JdbcMapUtil.getString(p, "NAME");
                poOrderDtlView.pmExpTypeId = p.get("COST_TYPE_TREE_ID").toString();
                poOrderDtlView.pmExpTypeName = JdbcMapUtil.getString(p, "feeName");
                poOrderDtlView.pmExpTypeCode = JdbcMapUtil.getString(p, "feeCode");
                poOrderDtlView.payType = JdbcMapUtil.getString(p, "PAY_TYPE");
                poOrderDtlView.amt = JdbcMapUtil.getString(p, "AMT");
                poOrderDtlView.totalAmt = JdbcMapUtil.getString(p, "TOTAL_AMT");
                poOrderDtlView.workContent = JdbcMapUtil.getString(p, "WORK_CONTENT");
                poOrderDtlView.fileId = JdbcMapUtil.getString(p, "FILE_ATTACHMENT_URL");
                poOrderDtlView.remark = JdbcMapUtil.getString(p, "REMARK");
                poOrderDtlView.feeDetail = JdbcMapUtil.getString(p, "FEE_DETAIL");
                return poOrderDtlView;
            }).collect(Collectors.toList());
            ret.put("result", view);
            ret.put("total", Integer.valueOf(list2.get(0).get("num").toString()));
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(ret), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(null);
        }
    }

    // 采购合同明细工作进度
    public void getPoOrderDetailProgress() {
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PoOrderDtlProView param = JsonUtil.fromJson(json, PoOrderDtlProView.class);
        if (SharedUtil.isEmptyString(param.poOrderDtlId)) {
            throw new BaseException("接口调用失败，采购合同明细id不能为空");
        }
        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        String sql1 = "SELECT a.id,a.PO_ORDER_DTL_ID,b.NAME as poOrderDtlName,a.COMPL_QTY,a.COMPL_UNIT_AMT,a.COMPL_TOTAL_AMT, " +
                "a.COMPL_DATE,a.WORK_CONTENT,a.FILE_ATTACHMENT_URL,a.REMARK " +
                "FROM po_order_dtl_pro a LEFT JOIN po_order_dtl b ON a.PO_ORDER_DTL_ID = b.id WHERE 1 = 1 and a.PO_ORDER_DTL_ID = ? ";
        String sql2 = "SELECT count(*) as num FROM po_order_dtl_pro a LEFT JOIN po_order_dtl b ON a.PO_ORDER_DTL_ID = b.id WHERE 1 = 1 and a.PO_ORDER_DTL_ID = ? ";
        sb.append(sql1);
        sb2.append(sql2);
        if (!SharedUtil.isEmptyString(param.complQty)) {
            sb.append(" and a.COMPL_QTY like ('%").append(param.complQty).append("%') ");
            sb2.append(" and a.COMPL_QTY like ('%").append(param.complQty).append("%') ");
        }
        if (!SharedUtil.isEmptyString(param.complUnitAmt)) {
            sb.append(" and a.COMPL_UNIT_AMT like ('%").append(param.complUnitAmt).append("%') ");
            sb2.append(" and a.COMPL_UNIT_AMT like ('%").append(param.complUnitAmt).append("%') ");
        }
        if (!SharedUtil.isEmptyString(param.complTotalAmt)) {
            sb.append(" and a.COMPL_TOTAL_AMT like ('%").append(param.complTotalAmt).append("%') ");
            sb2.append(" and a.COMPL_TOTAL_AMT like ('%").append(param.complTotalAmt).append("%') ");
        }
        if (!SharedUtil.isEmptyString(param.complDate)) {
            sb.append(" and a.COMPL_DATE = '").append(param.complDate).append("' ");
            sb2.append(" and a.COMPL_DATE = '").append(param.complDate).append("' ");
        }
        if (!SharedUtil.isEmptyString(param.workContent)) {
            sb.append(" and a.WORK_CONTENT like ('%").append(param.workContent).append("%')");
            sb2.append(" and a.WORK_CONTENT like ('%").append(param.workContent).append("%')");
        }
        sb.append("order BY a.CRT_DT DESC ").append(limit);
        sb2.append("order BY a.CRT_DT DESC");
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString(), param.poOrderDtlId);
        List<Map<String, Object>> list2 = myJdbcTemplate.queryForList(sb2.toString(), param.poOrderDtlId);
        Map<String, Object> ret = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            List<PoOrderDtlProView> view = list.stream().map(p -> {
                PoOrderDtlProView poOrderDtlProView = new PoOrderDtlProView();
                poOrderDtlProView.id = p.get("id").toString();
                poOrderDtlProView.poOrderDtlId = p.get("PO_ORDER_DTL_ID").toString();
                poOrderDtlProView.poOrderDtlName = JdbcMapUtil.getString(p, "NAME");
                poOrderDtlProView.complQty = JdbcMapUtil.getString(p, "COMPL_QTY");
                poOrderDtlProView.complUnitAmt = JdbcMapUtil.getString(p, "COMPL_UNIT_AMT");
                poOrderDtlProView.complTotalAmt = JdbcMapUtil.getString(p, "COMPL_TOTAL_AMT");
                poOrderDtlProView.complDate = JdbcMapUtil.getString(p, "COMPL_DATE");
                poOrderDtlProView.workContent = JdbcMapUtil.getString(p, "WORK_CONTENT");
                poOrderDtlProView.remark = JdbcMapUtil.getString(p, "REMARK");
                poOrderDtlProView.fileId = JdbcMapUtil.getString(p, "FILE_ATTACHMENT_URL");
                return poOrderDtlProView;

            }).collect(Collectors.toList());
            ret.put("result", view);
            ret.put("total", Integer.valueOf(list2.get(0).get("num").toString()));
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(ret), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(null);
        }
    }

    // 采购合同联系人明细
    public void getContractsDetail() {
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PoOrderContactsView param = JsonUtil.fromJson(json, PoOrderContactsView.class);
        if (SharedUtil.isEmptyString(param.parentId)) {
            throw new BaseException("接口调用失败，采购合同id不能为空");
        }
        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        String sql1 = "select id,OPPO_SITE_LINK_MAN,OPPO_SITE_CONTACT from PO_ORDER_CONTACTS where PARENT_ID = ? ";
        String sql2 = "select count(*) as num from PO_ORDER_CONTACTS where PARENT_ID = ? ";
        sb.append(sql1);
        sb2.append(sql2);
        sb.append("order BY OPPO_SITE_LINK_MAN asc ").append(limit);
        sb2.append("order BY OPPO_SITE_LINK_MAN asc");
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString(), param.parentId);
        List<Map<String, Object>> list2 = myJdbcTemplate.queryForList(sb2.toString(), param.parentId);
        Map<String, Object> ret = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            List<PoOrderContactsView> view = list.stream().map(p -> {
                PoOrderContactsView poOrderDtlView = new PoOrderContactsView();
                poOrderDtlView.id = JdbcMapUtil.getString(p, "id");
                poOrderDtlView.oppoSiteLinkMan = p.get("OPPO_SITE_LINK_MAN").toString();
                poOrderDtlView.oppoSiteContact = JdbcMapUtil.getString(p, "OPPO_SITE_CONTACT");
                return poOrderDtlView;
            }).collect(Collectors.toList());
            ret.put("result", view);
            ret.put("total", Integer.valueOf(list2.get(0).get("num").toString()));
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(ret), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(null);
        }

    }
}
