package com.cisdi.ext.pm.financePay;

import com.cisdi.ext.model.HrDept;
import com.cisdi.ext.model.PoGuaranteeLetterRequireReq;
import com.cisdi.ext.model.PoGuaranteeLetterRequireReqFeeDetail;
import com.cisdi.ext.model.WfProcessInstance;
import com.cisdi.ext.model.view.process.PoGuaranteeLetterRequireReqView;
import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.checkerframework.checker.units.qual.C;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 新增保函-扩展
 */
public class PoGuaranteeLetterRequireReqExt {

    /**
     * 发起时数据校验
     */
    public void guaranteeStartCheck(){
        EntityRecord entityRecordList = ExtJarHelper.entityRecordList.get().get(0);
        String id = entityRecordList.csCommId;
        //处理费用明细信息
        feeDetail(id);
    }

    /**
     * 新增保函-结束时数据校验
     */
    public void guaranteeEndCheck(){
        EntityRecord entityRecordList = ExtJarHelper.entityRecordList.get().get(0);
        String id = entityRecordList.csCommId;
        String processInstanceId = ExtJarHelper.procInstId.get(); // 流程实例id
        //流程名称写入name
        updateName(id,processInstanceId);
    }

    /**
     * 流程实例名称更新流程汇总name字段
     * @param id 流程业务表记录id
     * @param processInstanceId 流程实例id
     */
    private void updateName(String id, String processInstanceId) {
        String name = WfProcessInstance.selectById(processInstanceId).getName();
        Crud.from("PO_GUARANTEE_LETTER_REQUIRE_REQ").where().eq("ID",id).update().set("NAME",name).exec();
    }

    /**
     * 费用明细信息计算及汇总
     * @param id 主表单id
     */
    private void feeDetail(String id) {
        List<PoGuaranteeLetterRequireReqFeeDetail> list = PoGuaranteeLetterRequireReqFeeDetail.selectByWhere(new Where()
                .eq(PoGuaranteeLetterRequireReqFeeDetail.Cols.PO_GUARANTEE_LETTER_REQUIRE_REQ_ID,id));
        if (!CollectionUtils.isEmpty(list)){
            for (PoGuaranteeLetterRequireReqFeeDetail tmp : list) {
                // 获取id
                String feeId = tmp.getId();
                // 合同金额
                BigDecimal contractAmount = tmp.getAmtFive();
                // 预付款比例
                BigDecimal percent = tmp.getAdvanceChargePercent();
                // 预付款金额
                BigDecimal amount = contractAmount.multiply(percent).divide(new BigDecimal(100),2, RoundingMode.HALF_UP);
                // 修改预付款金额
                Crud.from(PoGuaranteeLetterRequireReqFeeDetail.ENT_CODE).where().eq("ID",feeId).update()
                        .set(PoGuaranteeLetterRequireReqFeeDetail.Cols.AMT_SIX,amount)
                        .exec();
            }
            BigDecimal allAmt = getAllAmt(list);
            // 修改主表单合同金额信息
            Crud.from(PoGuaranteeLetterRequireReq.ENT_CODE).where().eq("ID",id).update()
                    .set(PoGuaranteeLetterRequireReq.Cols.AMT_FIVE,allAmt)
                    .exec();
        }
    }

    /**
     * 汇总求和合同总金额
     * @param list 合同明细信息
     * @return 汇总总金额
     */
    private BigDecimal getAllAmt(List<PoGuaranteeLetterRequireReqFeeDetail> list) {
        BigDecimal allAmt = new BigDecimal(0);
        if (!CollectionUtils.isEmpty(list)){
            for (PoGuaranteeLetterRequireReqFeeDetail tmp : list) {
                allAmt = allAmt.add(tmp.getAmtFive());
            }
        }
        return allAmt;
    }

    /**
     * 流程操作-新增保函-确认操作
     */
    public void guaranteeCheckOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程操作-新增保函-拒绝操作
     */
    public void guaranteeCheckRefuse(){
        String status = "refuse";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程流程操作详细处理逻辑
     * @param nodeStatus 节点状态码
     * @param status 流程操作状态码
     */
    private void processHandle(String nodeStatus, String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        String procInstId = ExtJarHelper.procInstId.get();
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus)){
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            } else {
                //获取审批意见信息
                Map<String,String> message = ProcessCommon.getCommentNew(nodeInstanceId,userId,myJdbcTemplate,procInstId);
                String comment = message.get("comment");

                if ("caiWuWangOK".equals(nodeStatus)){ // 2-财务审批(王胜仁)
                    //获取流程中的意见信息
                    String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"COMMENT_PUBLISH_CONTENT");
                    //生成最终的意见信息
                    String commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                    Crud.from("PO_GUARANTEE_LETTER_REQUIRE_REQ").where().eq("id",csCommId).update()
                            .set("COMMENT_PUBLISH_CONTENT",commentEnd).exec();
                }
            }
        } else {
            if ("caiWuWangRefuse".equals(nodeStatus)){
                Crud.from("PO_GUARANTEE_LETTER_REQUIRE_REQ").where().eq("id",csCommId).update()
                        .set("COMMENT_PUBLISH_CONTENT",null).exec();
            }
        }
    }

    /**
     * 节点状态赋值
     * @param status 状态码
     * @param nodeId 节点id
     * @return 节点状态码
     */
    private String getNodeStatus(String status, String nodeId) {
        String nodeName = "";
        if ("OK".equals(status)){
            if ("0099902212142023898".equals(nodeId)){ //1-发起
                nodeName = "start";
            } else if ("0099902212142023900".equals(nodeId)){ // 2-财务审批(王胜仁)
                nodeName = "caiWuWangOK";
            }
        } else {
            if ("0099902212142023900".equals(nodeId)){ // 2-财务审批(王胜仁)-拒绝
                nodeName = "caiWuWangRefuse";
            }
        }
        return nodeName;
    }

    /**
     * 新增保函-台账-列表
     */
    public void pageGuaranteeRequire(){
        // 获取输入：
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PoGuaranteeLetterRequireReqView param = JsonUtil.fromJson(json, PoGuaranteeLetterRequireReqView.class);

        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;

        String sql1 = "SELECT r.id as id,r.GUARANTEE_LETTER_TYPE_ID guaranteeLetterTypeId, " +
                "(select name from gr_set_value where id = r.GUARANTEE_LETTER_TYPE_ID) as guaranteeLetterTypeName, " +
                "r.PM_EXP_TYPE_IDS pmExpTypeIds,c.name as pmExpTypeName, " +
                "IFNULL(r.PROJECT_NAME_WR,GROUP_CONCAT( p1.NAME )) as projectName, " +
                "r.SUPPLIER as supplier,r.GUARANTEE_MECHANISM as guaranteeMechanism, " +
                "r.GUARANTEE_CODE as guaranteeCode,r.AMT_SIX as guaranteeAmt, " +
                "r.GUARANTEE_START_DATE as guaranteeStartDate,r.GUARANTEE_END_DATE as guaranteeEndDate, " +
                "r.REMARK_ONE as remarkOne,r.BENEFICIARY as beneficiary, " +
                "r.CRT_USER_ID as createUserId,r.CRT_DEPT_ID as deptId, " +
                "(select name from ad_user where id = r.CRT_USER_ID) as createUserName, " +
                "(select name from hr_dept where id = r.CRT_DEPT_ID) as deptName, " +
                "(select name from pm_party where id = r.CUSTOMER_UNIT_ONE) as companyName, " +
                "r.CUSTOMER_UNIT_ONE as companyId,r.CRT_DT as createDate " +
                "FROM po_guarantee_letter_require_req r " +
                "LEFT JOIN pm_prj p1 ON FIND_IN_SET( p1.id, r.PM_PRJ_IDS ) " +
                "LEFT JOIN pm_prj P2 ON p2.NAME = r.PROJECT_NAME_WR " +
                "LEFT JOIN pm_exp_type c on r.PM_EXP_TYPE_IDS = c.id " +
                "WHERE r.status = 'AP' and r.crt_dt >= '2022-11-22 00:00:00' ";
        StringBuilder sb = new StringBuilder(sql1);
        appendString(sb,param);
        sb.append(" GROUP BY r.id order by r.crt_dt desc ");
        StringBuilder sb2 = new StringBuilder(sb);
        sb.append(limit);
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sb.toString());
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sb2.toString());
        List<PoGuaranteeLetterRequireReqView> list3 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list1)){
            for (Map<String, Object> tmp : list1) {
                PoGuaranteeLetterRequireReqView poGuaranteeLetterRequireReqView = new PoGuaranteeLetterRequireReqView();

                poGuaranteeLetterRequireReqView.setId(JdbcMapUtil.getString(tmp,"id")); //id
                poGuaranteeLetterRequireReqView.setCreateUserName(JdbcMapUtil.getString(tmp,"createUserName")); // 创建人
                poGuaranteeLetterRequireReqView.setDeptName(JdbcMapUtil.getString(tmp,"deptName")); // 创建部门
                String createDate = JdbcMapUtil.getString(tmp,"createDate");
                if (StringUtils.hasText(createDate)){
                    poGuaranteeLetterRequireReqView.setCreateDate(createDate.replace("T"," ")); // 创建日期
                }
                poGuaranteeLetterRequireReqView.setCompanyName(JdbcMapUtil.getString(tmp,"companyName")); // 合同签订公司
                poGuaranteeLetterRequireReqView.setProjectName(JdbcMapUtil.getString(tmp,"projectName")); // 项目名称
                poGuaranteeLetterRequireReqView.setPmExpTypeName(JdbcMapUtil.getString(tmp,"pmExpTypeName")); // 费用类型名称
                poGuaranteeLetterRequireReqView.setSupplier(JdbcMapUtil.getString(tmp,"supplier")); // 供应商完整名称
                poGuaranteeLetterRequireReqView.setGuaranteeLetterTypeName(JdbcMapUtil.getString(tmp,"guaranteeLetterTypeName")); // 保函类型名称
                String amt = JdbcMapUtil.getString(tmp,"guaranteeAmt"); // 担保金额
                if (StringUtils.hasText(amt)){
                    poGuaranteeLetterRequireReqView.setGuaranteeAmt(new BigDecimal(amt));
                }
                poGuaranteeLetterRequireReqView.setGuaranteeMechanism(JdbcMapUtil.getString(tmp,"guaranteeMechanism")); // 担保银行
                poGuaranteeLetterRequireReqView.setGuaranteeCode(JdbcMapUtil.getString(tmp,"guaranteeCode")); // 保单号
                String signDate = JdbcMapUtil.getString(tmp,"guaranteeStartDate"); // 签订日期
                if (StringUtils.hasText(signDate)){
                    poGuaranteeLetterRequireReqView.setGuaranteeStartDate(signDate.replace("T"," "));
                }
                String endDate = JdbcMapUtil.getString(tmp,"guaranteeEndDate");
                if (StringUtils.hasText(endDate)){
                    poGuaranteeLetterRequireReqView.setGuaranteeEndDate(endDate.replace("T"," ")); // 担保期限
                }
                poGuaranteeLetterRequireReqView.setRemarkOne(JdbcMapUtil.getString(tmp,"remarkOne")); // 备注
                poGuaranteeLetterRequireReqView.setBeneficiary(JdbcMapUtil.getString(tmp,"beneficiary")); // 受益人

                list3.add(poGuaranteeLetterRequireReqView);
            }
            Map<String,Object> map1 = new HashMap<>();
            map1.put("total",list2.size());
            map1.put("reqs",list3);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map1), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(null);
        }
    }

    /**
     * 拼接筛选条件
     * @param sb sql
     * @param param 参数
     */
    private void appendString(StringBuilder sb, PoGuaranteeLetterRequireReqView param) {
        // 创建人
        if (StringUtils.hasText(param.getCreateBy())){
            sb.append(" and r.CRT_USER_ID = '").append(param.getCreateBy()).append("'");
        }
        // 所属部门
        String deptId = param.getDeptId();
        if (StringUtils.hasText(deptId)){
            HrDept hrDept = HrDept.selectById(deptId);
            if (hrDept != null){
                String deptName = hrDept.getName();
                List<String> deptIds = HrDept.selectByWhere(new Where().eq(HrDept.Cols.STATUS,"AP").eq(HrDept.Cols.NAME,deptName)).stream().map(HrDept::getId).collect(Collectors.toList());
                String deptIdStr = String.join("','",deptIds);
                sb.append(" and r.CRT_DEPT_ID in ('").append(deptIdStr).append("') ");
            } else {
                throw new BaseException("查询不到该部门信息！");
            }
        }
        // 创建日期
        if (StringUtils.hasText(param.getCreateDateMin())){
            sb.append(" and r.CRT_DT >= '").append(param.getCreateDateMin()).append("' ");
        }
        if (StringUtils.hasText(param.getCreateDateMax())){
            sb.append(" and r.CRT_DT <= '").append(param.getCreateDateMax()).append("' ");
        }
        // 合同签订公司
        if (StringUtils.hasText(param.getCompanyId())){
            sb.append(" and r.CUSTOMER_UNIT_ONE = '").append(param.getCompanyId()).append("' ");
        }
        // 项目id
        if (StringUtils.hasText(param.getProjectId())){
            sb.append(" and FIND_IN_SET('").append(param.getProjectId()).append("',IFNULL(r.PM_PRJ_IDS,p2.id)) ");
        }
        // 费用名称
        if (StringUtils.hasText(param.getPmExpTypeIds())){
            sb.append(" and find_in_set('").append(param.getPmExpTypeIds()).append("',r.PM_EXP_TYPE_IDS) ");
        }
        // 供应商完整名称
        if (StringUtils.hasText(param.getSupplier())){
            sb.append(" and r.SUPPLIER like ('%").append(param.getSupplier()).append("%') ");
        }
        // 保函类型
        if (StringUtils.hasText(param.getGuaranteeLetterTypeId())){
            sb.append(" and r.GUARANTEE_LETTER_TYPE_ID = '").append(param.getGuaranteeLetterTypeId()).append("' ");
        }
        // 担保金额
        if (param.getGuaranteeAmtMin() != null){
            sb.append(" and r.AMT_SIX >= '").append(param.getGuaranteeAmtMin()).append("' ");
        }
        if (param.getGuaranteeAmtMax() != null){
            sb.append(" and r.AMT_SIX <= '").append(param.getGuaranteeAmtMax()).append("' ");
        }
    }
}
