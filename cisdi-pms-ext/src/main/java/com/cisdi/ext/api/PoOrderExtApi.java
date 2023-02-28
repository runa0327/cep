package com.cisdi.ext.api;

import com.cisdi.ext.base.GrSetValue;
import com.cisdi.ext.model.view.file.BaseFileView;
import com.cisdi.ext.model.view.order.PoOrderContactsView;
import com.cisdi.ext.model.view.order.PoOrderDtlProView;
import com.cisdi.ext.model.view.order.PoOrderDtlView;
import com.cisdi.ext.model.view.order.PoOrderView;
import com.cisdi.ext.base.PmPrj;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PoOrderExtApi {

    /**
     * 合同流程走完后，将数据汇总至合同数据表
     * @param entityRecord 流程业务数据
     * @param sourceType 数据来源流程类型
     * @param viewId 视图id
     * @param myJdbcTemplate 数据源
     */
    public static void createData(EntityRecord entityRecord, String sourceType, String viewId, MyJdbcTemplate myJdbcTemplate) {
        Map<String, Object> valueMap = entityRecord.valueMap;
        //流程id
        String csId = entityRecord.csCommId;
        //获取合作单位
        String cooperation = getCooperation(csId,myJdbcTemplate);
        //项目id
        String projectId = PmPrj.getProjectIdByProcess(valueMap,myJdbcTemplate);
        //根据编码code查询数据来源id
        String sourceTypeId = GrSetValue.getValueId("order_data_source_type",sourceType,myJdbcTemplate);
        if (!SharedUtil.isEmptyString(projectId)){
            List<String> list = StringUtil.getStrToList(projectId,",");
            for (String prjId : list) {
                /**=============此处需要添加合同已支付金额、累计支付比列查询逻辑===========================**/
                //判断是否已存在，存在则修改
                String poOrderId = getDateByProcessDateId(csId,prjId,myJdbcTemplate);
                if (SharedUtil.isEmptyString(poOrderId)){
                    poOrderId = Crud.from("po_order").insertData();
                }
                //修改合同数据表数据
                updatePoOrder(valueMap,csId,poOrderId,prjId,cooperation,sourceTypeId,viewId);
            }
        }
    }

    /**
     * 合同签订历史数据汇总
     * @param tmp 每条实体数据
     * @param sourceTypeId 合同来源集合值id
     * @param viewId 视图id
     * @param myJdbcTemplate 数据源
     */
    public static void createOrderHistoryData(Map<String, Object> tmp, String sourceTypeId, String viewId, MyJdbcTemplate myJdbcTemplate) {
        //流程id
        String csId = tmp.get("id").toString();
        //获取合作单位
        String cooperation = getCooperation(csId,myJdbcTemplate);
        //项目id
        String projectId = PmPrj.getProjectIdByProcess(tmp,myJdbcTemplate);
        if (!SharedUtil.isEmptyString(projectId)){
            List<String> list = StringUtil.getStrToList(projectId,",");
            for (String prjId : list) {
                /**=============此处需要添加合同已支付金额、累计支付比列查询逻辑===========================**/
                //判断是否已存在，存在则修改
                String poOrderId = getDateByProcessDateId(csId,prjId,myJdbcTemplate);
                if (SharedUtil.isEmptyString(poOrderId)){
                    poOrderId = Crud.from("po_order").insertData();
                }
                //修改合同数据表数据
                updatePoOrder(tmp,csId,poOrderId,prjId,cooperation,sourceTypeId,viewId);
            }
        }
    }

    /**
     * 查询合同流程的合作单位明细
     * @param csId 合同主表id
     * @param myJdbcTemplate 数据源
     * @return 合作单位名称
     */
    private static String getCooperation(String csId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select group_concat(WIN_BID_UNIT_ONE) as WIN_BID_UNIT_ONE from CONTRACT_SIGNING_CONTACT where PARENT_ID = ? and status = 'ap'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,csId);
        String value = null;
        if (!CollectionUtils.isEmpty(list)){
            value = JdbcMapUtil.getString(list.get(0),"WIN_BID_UNIT_ONE");
        }
        return value;
    }

    /**
     * 修改合同数据表数据
     * @param valueMap 主体值
     * @param csId 流程业务表id
     * @param poOrderId id
     * @param projectId 项目id
     * @param cooperation 合作单位
     * @param sourceTypeId 数据来源流程类型
     * @param viewId 视图id
     */
    private static void updatePoOrder(Map<String, Object> valueMap, String csId, String poOrderId, String projectId, String cooperation, String sourceTypeId, String viewId) {
        Crud.from("po_order").where().eq("id",poOrderId).update()
                .set("PM_PRJ_ID",projectId).set("CONTRACT_APP_ID",csId).set("ORDER_DATA_SOURCE_TYPE",sourceTypeId) //数据来源流程
                .set("CONTRACT_NAME",JdbcMapUtil.getString(valueMap,"CONTRACT_NAME")) //合同名称
                .set("WIN_BID_UNIT_ONE",cooperation) //合作单位
                .set("AMT_FIVE",JdbcMapUtil.getString(valueMap,"AMT_THREE")) // 不含税金额
                .set("AMT_ONE",JdbcMapUtil.getString(valueMap,"AMT_FOUR")) //税率
                .set("AMT_SIX",JdbcMapUtil.getString(valueMap,"AMT_TWO")) //含税总金额
                .set("AMT_SEVEN",null).set("AMT_TWO",null) //已支付金额 累计支付比例
                .set("AD_USER_ID",JdbcMapUtil.getString(valueMap,"AD_USER_ID")) //合同经办人
                .set("SIGN_DATE",JdbcMapUtil.getString(valueMap,"SIGN_DATE")) //签订日期
                .set("DATE_FIVE",JdbcMapUtil.getString(valueMap,"DATE_FIVE")) //到期日期
                .set("FILE_ATTACHMENT_URL",JdbcMapUtil.getString(valueMap,"FILE_ID_FIVE")) //合同盖章版文件
                .set("VIEW_ID",viewId) //视图id
                .exec();
    }

    /**
     * 根据业务流程id和项目id查询记录是否已经存在
     * @param csId 业务流程id
     * @param projectId 项目id
     * @param myJdbcTemplate 数据源
     * @return 存在/不存在
     */
    private static String getDateByProcessDateId(String csId, String projectId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select id from po_order where status = 'ap' and CONTRACT_APP_ID = ? and PM_PRJ_ID = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,csId,projectId);
        String id = "";
        if (!CollectionUtils.isEmpty(list)){
            id = JdbcMapUtil.getString(list.get(0),"id");
        }
        return id;
    }

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
        String sql = "SELECT a.VIEW_ID as viewId,a.id,a.PM_PRJ_ID as projectId,a.CONTRACT_APP_ID as orderProcessId,a.CONTRACT_NAME as contractName," +
                "a.WIN_BID_UNIT_ONE as cooperationUnit,a.AMT_FIVE as noShuiAmt,a.AMT_ONE as shuiLv,a.AMT_SIX as shuiAmt," +
                "a.AMT_SEVEN as aleadyPayAmt,a.AMT_TWO as payPrece,a.AD_USER_ID as agentUser,b.name as agentName," +
                "a.SIGN_DATE as signDate,a.DATE_FIVE as endDate,a.FILE_ATTACHMENT_URL as fileId " +
                "FROM po_order a left join ad_user b on a.AD_USER_ID = b.id " +
                "where a.PM_PRJ_ID = ? and a.status = 'ap'";
        sb.append(sql);
        // 合作单位
        if (!SharedUtil.isEmptyString(param.cooperationUnit)) {
            sb.append(" and a.WIN_BID_UNIT_ONE like ('%").append(param.cooperationUnit).append("%') ");
        }
        //合同名称
        if (!SharedUtil.isEmptyString(param.contractName)) {
            sb.append(" and a.CONTRACT_NAME like ('%").append(param.contractName).append("%') ");
        }
        //合同签订日期
        if (!SharedUtil.isEmptyString(param.signDateMin)){
            sb.append(" and a.SIGN_DATE >= '").append(param.signDateMin).append("'");
        }
        if (!SharedUtil.isEmptyString(param.signDateMax)){
            sb.append(" and a.SIGN_DATE <= '").append(param.signDateMax).append("'");
        }
        //合同到期日期
        if (!SharedUtil.isEmptyString(param.endDateMin)){
            sb.append(" and a.DATE_FIVE >= '").append(param.endDateMin).append("'");
        }
        if (!SharedUtil.isEmptyString(param.endDateMax)){
            sb.append(" and a.DATE_FIVE <= '").append(param.endDateMax).append("'");
        }
        StringBuilder sb2 = new StringBuilder(sb);
        sb.append("order BY a.sign_date asc ").append(limit);
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString(), param.projectId);
        List<Map<String, Object>> list2 = myJdbcTemplate.queryForList(sb2.toString(), param.projectId);
        Map<String, Object> map1 = new HashMap<>();
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(null);
        } else {
            List<PoOrderView> inputList = list.stream().map(p -> {
                PoOrderView poOrderView = new PoOrderView();
                poOrderView.id = p.get("id").toString();
                poOrderView.projectId = p.get("projectId").toString();
                poOrderView.contractName = JdbcMapUtil.getString(p, "contractName");
                poOrderView.orderProcessId = JdbcMapUtil.getString(p, "orderProcessId");
                poOrderView.cooperationUnit = JdbcMapUtil.getString(p, "cooperationUnit");
                poOrderView.noShuiAmt = SharedUtil.isEmptyString(JdbcMapUtil.getString(p, "noShuiAmt")) ? new BigDecimal("0") : new BigDecimal(JdbcMapUtil.getString(p, "noShuiAmt"));
                poOrderView.shuiLv = SharedUtil.isEmptyString(JdbcMapUtil.getString(p, "shuiLv")) ? new BigDecimal("0") : new BigDecimal(JdbcMapUtil.getString(p, "shuiLv"));
                poOrderView.shuiAmt = SharedUtil.isEmptyString(JdbcMapUtil.getString(p, "shuiAmt")) ? new BigDecimal("0") : new BigDecimal(JdbcMapUtil.getString(p, "shuiAmt"));
                poOrderView.aleadyPayAmt = SharedUtil.isEmptyString(JdbcMapUtil.getString(p, "aleadyPayAmt")) ? new BigDecimal("0") : new BigDecimal(JdbcMapUtil.getString(p, "aleadyPayAmt"));
                poOrderView.payPrece = SharedUtil.isEmptyString(JdbcMapUtil.getString(p, "payPrece")) ? new BigDecimal("0") : new BigDecimal(JdbcMapUtil.getString(p, "payPrece"));
                poOrderView.agentUser = JdbcMapUtil.getString(p, "agentUser");
                poOrderView.agentName = JdbcMapUtil.getString(p, "agentName");
                poOrderView.signDate = JdbcMapUtil.getString(p, "signDate");
                poOrderView.endDate = JdbcMapUtil.getString(p, "endDate");
                String fileId = SharedUtil.isEmptyString(JdbcMapUtil.getString(p, "fileId")) ? "" : JdbcMapUtil.getString(p, "fileId");
                if (!SharedUtil.isEmptyString(fileId)){
                    List<String> fileList = StringUtil.getStrToList(fileId,",");
                    fileId = StringUtil.replaceCode(fileId,",","','");
                    List<BaseFileView> fileDetail = FileApi.getFileList(fileId);
                    poOrderView.fileNum = fileList.size();
                    poOrderView.fileId = fileId;
                    poOrderView.fileList = fileDetail;
                }
                poOrderView.viewId = JdbcMapUtil.getString(p, "viewId");
                return poOrderView;
            }).collect(Collectors.toList());
            map1.put("result", inputList);
            map1.put("total", Integer.valueOf(list2.size()));
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
