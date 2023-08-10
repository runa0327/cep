package com.cisdi.ext.pm.financePay;

import com.cisdi.ext.model.HrDept;
import com.cisdi.ext.model.view.process.PoGuaranteeLetterReturnReqView;
import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.util.AmtUtil;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 保函退还申请-扩展
 */
public class PoGuaranteeLetterReturnOaReqExt {

    /**
     * 保函退还申请-发起时数据校验
     */
    public void guaranteeOAStartCheck(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程id
        String id = entityRecord.csCommId;
        //获取金额
        String amt = JdbcMapUtil.getString(entityRecord.valueMap,"AMT_FIVE").trim();
        String amtChina = AmtUtil.number2CNMontrayUnit(new BigDecimal(amt));
        String sql = "update PO_GUARANTEE_LETTER_RETURN_OA_REQ set REMARK_TWO = ? where id = ?";
        myJdbcTemplate.update(sql,amtChina,id);
    }

    /**
     * 流程操作-新增保函-确认操作
     */
    public void guaranteeOACheckOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程操作-新增保函-拒绝操作
     */
    public void guaranteeOACheckRefuse(){
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

                if ("caiLeaderOK".equals(nodeStatus)){ // 财务分管领导审批
                    ProcessCommon.updateComment("APPROVAL_COMMENT_FIVE",entityRecord.valueMap,comment,entCode,csCommId,userName);
                } else if ("wangOK".equals(nodeStatus)){ // 财务金融部会计审批(助理)
                    ProcessCommon.updateComment("APPROVAL_COMMENT_TWO",entityRecord.valueMap,comment,entCode,csCommId,userName);
                } else if ("caiAccountOK".equals(nodeStatus)){ // 财务金融部会计审批(会计)
                    ProcessCommon.updateComment("APPROVAL_COMMENT_THREE",entityRecord.valueMap,comment,entCode,csCommId,userName);
                } else if ("caiHeaderOK".equals(nodeStatus)){ // 财务金融部负责人审批
                    ProcessCommon.updateComment("APPROVAL_COMMENT_FOUR",entityRecord.valueMap,comment,entCode,csCommId,userName);
                } else if ("jingBanOK".equals(nodeStatus)){ // 经办部门负责人审批
                    ProcessCommon.updateComment("APPROVAL_COMMENT_ONE",entityRecord.valueMap,comment,entCode,csCommId,userName);
                }
            }
        } else {
            if ("caiLeaderRefuse".equals(nodeStatus)){
                ProcessCommon.updateProcColsValue("APPROVAL_COMMENT_FIVE",entCode,csCommId,null);
            } else if ("wangRefuse".equals(nodeStatus)){ // 财务金融部会计审批(助理)
                ProcessCommon.updateProcColsValue("APPROVAL_COMMENT_TWO",entCode,csCommId,null);
            } else if ("caiAccountRefuse".equals(nodeStatus)){ // 财务金融部会计审批(会计)
                ProcessCommon.updateProcColsValue("APPROVAL_COMMENT_THREE",entCode,csCommId,null);
            } else if ("caiHeaderRefuse".equals(nodeStatus)){ // 财务金融部负责人审批
                ProcessCommon.updateProcColsValue("APPROVAL_COMMENT_FOUR",entCode,csCommId,null);
            } else if ("jingBanRefuse".equals(nodeStatus)){ // 经办部门负责人审批
                ProcessCommon.updateProcColsValue("APPROVAL_COMMENT_ONE",entCode,csCommId,null);
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
            if ("0100031468512035336".equals(nodeId)){ //1-发起
                nodeName = "start";
            } else if ("1688729823704477696".equals(nodeId) || "0100031468512035341".equals(nodeId)){ // 财务分管领导审批
                nodeName = "caiLeaderOK";
            } else if ("0100031468512035339".equals(nodeId)){ // 财务金融部会计审批(助理)
                nodeName = "wangOK";
            } else if ("0100031468512112193".equals(nodeId) || "1635556049182502912".equals(nodeId)){ // 6-财务金融部会记审批
                nodeName = "caiAccountOK";
            } else if ("0100031468512035340".equals(nodeId) || "1635556295602057216".equals(nodeId)){ // 财务金融部负责人审批
                nodeName = "caiHeaderOK";
            } else if ("0100031468512035338".equals(nodeId)){ // 2-经办部门负责人审批
                nodeName = "jingBanOK";
            }
        } else {
            if ("1688729823704477696".equals(nodeId) || "0100031468512035341".equals(nodeId) ){ // 财务分管领导审批
                nodeName = "caiLeaderRefuse";
            } else if ("0100031468512035339".equals(nodeId)){ // 3-财务金融部会计审批
                nodeName = "wangRefuse";
            } else if ("0100031468512112193".equals(nodeId) || "1635556049182502912".equals(nodeId)){ // 6-财务金融部会记审批
                nodeName = "caiAccountRefuse";
            } else if ("0100031468512035340".equals(nodeId) || "1635556295602057216".equals(nodeId)){ // 财务金融部负责人审批
                nodeName = "caiHeaderRefuse";
            } else if ("0100031468512035338".equals(nodeId)){ // 2-经办部门负责人审批
                nodeName = "jingBanRefuse";
            }
        }
        return nodeName;
    }

    /**
     * 保函退还台账-列表
     */
    public void pageGuaranteeReturnOa(){
        // 获取输入：
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PoGuaranteeLetterReturnReqView param = JsonUtil.fromJson(json, PoGuaranteeLetterReturnReqView.class);

        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;

        String sql = "SELECT r.id as id,r.GUARANTEE_LETTER_TYPE_ID guaranteeLetterTypeId," +
                "(select name from gr_set_value where id = r.GUARANTEE_LETTER_TYPE_ID) as guaranteeLetterTypeName," +
                "r.GUARANTEE_COST_TYPE_ID guaranteeCostTypeId,r.CRT_DT as createDate," +
                "(select name from gr_set_value where id = r.GUARANTEE_COST_TYPE_ID) as guaranteeCostTypeName," +
                "IFNULL( p1.NAME, r.PROJECT_NAME_WR ) projectName," +
                "r.GUARANTEE_MECHANISM as guaranteeMechanism,r.GUARANTEE_CODE as guaranteeCode," +
                "r.AMT_FIVE as guaranteeAmt,r.GUARANTEE_START_DATE as guaranteeStartDate," +
                "r.GUARANTEE_END_DATE as guaranteeEndDate,r.REMARK_LONG_ONE as remarkLongOne," +
                "r.AUTHOR as author,(select name from pm_party where id = r.CUSTOMER_UNIT_ONE) as companyName," +
                "(select name from ad_user where id = r.CRT_USER_ID) as createUserName," +
                "(select name from hr_dept where id = r.CRT_DEPT_ID) as deptName " +
                "FROM po_guarantee_letter_return_oa_req r " +
                "LEFT JOIN pm_prj p1 ON p1.id = r.PM_PRJ_ID " +
                "LEFT JOIN pm_prj p2 ON p2.NAME = r.PROJECT_NAME_WR " +
                "WHERE r.status = 'AP' AND R.CRT_DT >= '2022-11-22 00:00:00' ";
        StringBuilder sb = new StringBuilder(sql);
        appendString(sb,param);
        sb.append(" GROUP BY r.id order by r.crt_dt desc ");
        String sql2 = sb.toString();
        sb.append(limit);
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sb.toString());
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2);
        List<PoGuaranteeLetterReturnReqView> list3 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list1)){
            for (Map<String, Object> tmp : list1) {
                PoGuaranteeLetterReturnReqView poGuaranteeLetterReturnReqView = new PoGuaranteeLetterReturnReqView();

                poGuaranteeLetterReturnReqView.setId(JdbcMapUtil.getString(tmp,"id")); //id
                poGuaranteeLetterReturnReqView.setCreateUserName(JdbcMapUtil.getString(tmp,"createUserName")); // 创建人
                poGuaranteeLetterReturnReqView.setDeptName(JdbcMapUtil.getString(tmp,"deptName")); // 创建部门
                String createDate = JdbcMapUtil.getString(tmp,"createDate");
                if (StringUtils.hasText(createDate)){
                    poGuaranteeLetterReturnReqView.setCreateDate(createDate.replace("T"," ")); // 创建日期
                }
                poGuaranteeLetterReturnReqView.setCompanyName(JdbcMapUtil.getString(tmp,"companyName")); // 合同签订公司
                poGuaranteeLetterReturnReqView.setProjectName(JdbcMapUtil.getString(tmp,"projectName")); // 项目名称
                poGuaranteeLetterReturnReqView.setGuaranteeCostTypeName(JdbcMapUtil.getString(tmp,"guaranteeCostTypeName")); // 费用名称
                poGuaranteeLetterReturnReqView.setGuaranteeLetterTypeName(JdbcMapUtil.getString(tmp,"guaranteeLetterTypeName")); // 保函类型名称
                String amt = JdbcMapUtil.getString(tmp,"guaranteeAmt"); // 担保金额
                if (StringUtils.hasText(amt)){
                    poGuaranteeLetterReturnReqView.setGuaranteeAmt(new BigDecimal(amt));
                }
                poGuaranteeLetterReturnReqView.setGuaranteeMechanism(JdbcMapUtil.getString(tmp,"guaranteeMechanism")); // 担保银行
                poGuaranteeLetterReturnReqView.setGuaranteeCode(JdbcMapUtil.getString(tmp,"guaranteeCode")); // 保单号
                String signDate = JdbcMapUtil.getString(tmp,"guaranteeStartDate"); // 签订日期
                if (StringUtils.hasText(signDate)){
                    poGuaranteeLetterReturnReqView.setGuaranteeStartDate(signDate.replace("T"," "));
                }
                String endDate = JdbcMapUtil.getString(tmp,"guaranteeEndDate");
                if (StringUtils.hasText(endDate)){
                    poGuaranteeLetterReturnReqView.setGuaranteeEndDate(endDate.replace("T"," ")); // 担保期限
                }
                poGuaranteeLetterReturnReqView.setRemarkLongOne(JdbcMapUtil.getString(tmp,"remarkLongOne")); // 备注
                poGuaranteeLetterReturnReqView.setAuthor(JdbcMapUtil.getString(tmp,"author")); // 受益人

                list3.add(poGuaranteeLetterReturnReqView);
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
    private void appendString(StringBuilder sb, PoGuaranteeLetterReturnReqView param) {
        // 创建人
        String createBy = param.getCreateUserId();
        if (StringUtils.hasText(createBy)){
            sb.append(" and r.CRT_USER_ID = '").append(createBy).append("' ");
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
            sb.append(" and '").append(param.getProjectId()).append("' = IFNULL(r.PM_PRJ_ID,p2.id) ");
        }
        // 费用名称
        if (StringUtils.hasText(param.getGuaranteeCostTypeId())){
            sb.append(" and r.GUARANTEE_COST_TYPE_ID = '").append(param.getGuaranteeLetterTypeId()).append("' ");
        }
        // 保函类型
        if (StringUtils.hasText(param.getGuaranteeLetterTypeId())){
            sb.append(" and r.GUARANTEE_LETTER_TYPE_ID = '").append(param.getGuaranteeLetterTypeId()).append("' ");
        }
        // 担保金额
        if (param.getGuaranteeAmtMin() != null){
            sb.append(" and r.AMT_FIVE >= '").append(param.getGuaranteeAmtMin()).append("' ");
        }
        if (param.getGuaranteeAmtMax() != null){
            sb.append(" and r.AMT_FIVE <= '").append(param.getGuaranteeAmtMax()).append("' ");
        }
    }

    /**
     * 历史数据处理-审批意见回显
     */
    public void commentShow(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<EntityRecord> entityRecord = ExtJarHelper.entityRecordList.get();
        for (EntityRecord tmp : entityRecord) {
            Map<String,Object> map = tmp.valueMap;
            String id = JdbcMapUtil.getString(map,"ID");
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList("select id from wf_process_instance where ENTITY_RECORD_ID = ?",id);
            if (!CollectionUtils.isEmpty(list1)){
                String processInstanceId = JdbcMapUtil.getString(list1.get(0),"id");
                String sql = "select concat(d.name,'：',IFNULL(a.USER_COMMENT,'同意')) as comment,b.WF_NODE_ID " +
                        "FROM wf_task a " +
                        "LEFT JOIN wf_node_instance b on a.WF_NODE_INSTANCE_ID = b.id " +
                        "LEFT JOIN wf_process_instance c on a.WF_PROCESS_INSTANCE_ID = c.id " +
                        "LEFT JOIN AD_USER d on a.AD_USER_ID = d.id " +
                        "WHERE a.status = 'AP' and b.status = 'AP' and c.status = 'AP' and a.ACT_DATETIME is not null and a.WF_TASK_TYPE_ID = 'TODO' AND C.ID = ? ";
                List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql,processInstanceId);
                if (!CollectionUtils.isEmpty(list2)){
                    String APPROVAL_COMMENT_ONE = null, APPROVAL_COMMENT_TWO = null, APPROVAL_COMMENT_THREE = null, APPROVAL_COMMENT_FOUR = null, APPROVAL_COMMENT_FIVE = null;
                    for (Map<String, Object> tp : list2) {
                        String nodeId = JdbcMapUtil.getString(tp,"WF_NODE_ID");
                        String value = JdbcMapUtil.getString(tp,"comment");
                        switch (nodeId) {
                            case "0100031468512035338" : // 经办部门负责人意见
                                APPROVAL_COMMENT_ONE = value;
                                break;
                            case "0100031468512035339" : // 财务会计(助理)
                                APPROVAL_COMMENT_TWO = value;
                                break;
                            case "0100031468512112193" : // 财务会计
                            case "1635556049182502912" :
                                APPROVAL_COMMENT_THREE = value;
                                break;
                            case "0100031468512035340" : // 财务金融部负责人
                            case "1635556295602057216" :
                                APPROVAL_COMMENT_FOUR = value;
                                break;
                            case "0100031468512035341" : // 财务分管领导
                            case "1688729823704477696" :
                                APPROVAL_COMMENT_FIVE = value;
                                break;
                            default:
                                break;
                        }
                    }
                    Crud.from("PO_GUARANTEE_LETTER_RETURN_OA_REQ").where().eq("ID",id).update()
                            .set("APPROVAL_COMMENT_ONE",APPROVAL_COMMENT_ONE)
                            .set("APPROVAL_COMMENT_TWO",APPROVAL_COMMENT_TWO)
                            .set("APPROVAL_COMMENT_THREE",APPROVAL_COMMENT_THREE)
                            .set("APPROVAL_COMMENT_FOUR",APPROVAL_COMMENT_FOUR)
                            .set("APPROVAL_COMMENT_FIVE",APPROVAL_COMMENT_FIVE)
                            .exec();
                }
            }
        }
    }
}
