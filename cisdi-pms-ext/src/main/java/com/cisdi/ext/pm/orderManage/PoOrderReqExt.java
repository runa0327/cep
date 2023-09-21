package com.cisdi.ext.pm.orderManage;

import com.alibaba.fastjson.JSON;
import com.cisdi.ext.api.PoOrderExtApi;
import com.cisdi.ext.base.GrSetValueExt;
import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.commons.HttpClient;
import com.cisdi.ext.model.ContractSigningContact;
import com.cisdi.ext.model.GrSetValue;
import com.cisdi.ext.model.PoOrder;
import com.cisdi.ext.model.PoOrderReq;
import com.cisdi.ext.model.view.order.PoOrderReqView;
import com.cisdi.ext.pm.PmInLibraryExt;
import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.pm.processCommon.ProcessRoleExt;
import com.cisdi.ext.pm.orderManage.detail.PoOrderPrjDetailExt;
import com.cisdi.ext.util.*;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 合约管理-采购合同签订扩展
 */
@Slf4j
public class PoOrderReqExt {

    /**
     * 采购合同签订扩展-财务审批
     */
    public void checkFirst() {
        String status = "first";
        checkFirst(status);
    }

    /**
     * 采购合同签订扩展-法务审批
     */
    public void checkSecond() {
        String status = "second";
        checkFirst(status);
    }

    /**
     * 采购合同签订扩展-律师审批
     */
    public void orderLawyerCheck() {
        String status = "orderLawyerCheck";
        checkFirst(status);
    }

    /**
     * 合同签订扩展-法务财务审批
     */
    public void orderLegalFinanceCheck(){
        String status = "orderLegalFinanceCheck";
        checkFirst(status);
    }

    /**
     * 合同签订扩展-法务财务审批-拒绝
     */
    public void orderLegalFinanceReject(){
        String status = "orderLegalFinanceReject";
        checkFirst(status);
    }

    /**
     * 采购合同签订扩展 审批先后顺序 第四次审批
     */
    public void checkFourth() {
        String status = "fourth";
        checkFirst(status);
    }

    /**
     * 采购合同签订扩展 审批先后顺序 第五次审批
     */
    public void checkFifth() {
        String status = "fifth";
        checkFirst(status);
    }

    private void checkFirst(String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Date date = new Date();
        String now = DateTimeUtil.dateToString(date);

        //获取当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();

        // 当前登录人
        String userName = ExtJarHelper.loginInfo.get().userName;

        // 当前登录人id
        String userId = ExtJarHelper.loginInfo.get().userId;

        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        // 审批意见
        String sql = "select tk.USER_COMMENT,tk.USER_ATTACHMENT " +
                "from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id and tk.status='ap' " +
                "join ad_user u on tk.ad_user_id=u.id" +
                " where u.id = ?";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, procInstId,userId);
        String comment = "";
        String file = "";
        if (!CollectionUtils.isEmpty(list)) {
            comment = list.get(0).get("user_comment") == null ? null : list.get(0).get("user_comment").toString();
            file = list.get(0).get("USER_ATTACHMENT") == null ? null : list.get(0).get("USER_ATTACHMENT").toString();
        }
        String sbComment;
        String sbFile;
        StringBuilder upSql = new StringBuilder();
        if ("orderLawyerCheck".equals(status)){ //律师审核
            //流程中的审批意见
            String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"TEXT_REMARK_TWO");
            String processFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_SIX");
            //判断是否是当轮拒绝回来的、撤销回来的
            String sql2 = "select count(*) as num from wf_task where WF_NODE_INSTANCE_ID = ? and IS_CLOSED = 1 and AD_USER_ID != ?";
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,nodeInstanceId,userId);
            if (!CollectionUtils.isEmpty(list2)){
                String num = JdbcMapUtil.getString(list2.get(0),"num");
                if (SharedUtil.isEmptyString(num) || "0".equals(num)){
                    Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                            .set("TEXT_REMARK_TWO", null).set("FILE_ID_SIX",null).exec();
                    processComment = "";
                    processFile = "";
                }
            } else {
                Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("TEXT_REMARK_TWO", null).set("FILE_ID_SIX",null).exec();
                processComment = "";
                processFile = "";
            }
            sbComment = autoComment(comment,processComment,userName);
            sbFile = autoFile(file,processFile);
            if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                upSql.append("update PO_ORDER_REQ set ");
                if (!SharedUtil.isEmptyString(sbComment)){
                    upSql.append(" TEXT_REMARK_TWO = '").append(sbComment).append("', ");
                }
                if (!SharedUtil.isEmptyString(sbFile)){
                    upSql.append(" FILE_ID_SIX = '").append(sbFile).append("', ");
                }
                upSql.append(" LAST_MODI_DT = now() where id = ?");
                Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                log.info("已更新：{}", exec);
            }

        }  else if ("orderLegalFinanceCheck".equals(status)) { //法务财务审批

            String nodeId = ExtJarHelper.nodeId.get();
            String processId = ExtJarHelper.procId.get();
            //流程中的审批意见
            String processLegalComment = JdbcMapUtil.getString(entityRecord.valueMap,"TEXT_REMARK_THREE"); //法务意见
            String processLegalFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_THREE"); //法务修订稿
            String processFinanceComment = JdbcMapUtil.getString(entityRecord.valueMap,"TEXT_REMARK_FOUR"); //财务意见
            String processFinanceFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_TWO"); //财务修订稿
            //查询该人员角色信息
            String sql1 = "select b.id,b.name from ad_role_user a left join ad_role b on a.AD_ROLE_ID = b.id where a.AD_USER_ID = ? and b.id in ('0100070673610711083','0099902212142039415')";
            userId = ProcessCommon.getOriginalUser(nodeInstanceId,userId,myJdbcTemplate);
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,userId);
            if (!CollectionUtils.isEmpty(list1)){
                //判断是否是当轮拒绝回来的、撤销回来的
                String sql2 = "select count(*) as num from wf_task where WF_NODE_INSTANCE_ID = ? and IS_CLOSED = 1 and AD_USER_ID != ?";
                List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,nodeInstanceId,userId);
                if (!CollectionUtils.isEmpty(list2)){
                    String num = JdbcMapUtil.getString(list2.get(0),"num");
                    if (SharedUtil.isEmptyString(num) || "0".equals(num)){
                        Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                                .set("TEXT_REMARK_THREE", null).set("FILE_ID_THREE",null).set("TEXT_REMARK_FOUR",null)
                                .set("FILE_ID_TWO",null).exec();
                        processLegalComment = "";
                        processLegalFile = "";
                        processFinanceComment = "";
                        processFinanceFile = "";
                    }
                } else {
                    Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                            .set("TEXT_REMARK_THREE", null).set("FILE_ID_THREE",null).set("TEXT_REMARK_FOUR",null)
                            .set("FILE_ID_TWO",null).exec();
                    processLegalComment = "";
                    processLegalFile = "";
                    processFinanceComment = "";
                    processFinanceFile = "";
                }
                // 0099902212142039415 = 法务部门;0100070673610711083=财务部
                String id = JdbcMapUtil.getString(list1.get(0),"id");

                if ("0099902212142039415".equals(id)){ //法务
                    sbComment = autoComment(comment,processLegalComment,userName);
                    sbFile = autoFile(file,processLegalFile);
                    if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                        upSql.append("update PO_ORDER_REQ set ");
                        if (!SharedUtil.isEmptyString(sbComment)){
                            upSql.append(" TEXT_REMARK_THREE = '").append(sbComment).append("', ");
                        }
                        if (!SharedUtil.isEmptyString(sbFile)){
                            upSql.append(" FILE_ID_THREE = '").append(sbFile).append("', ");
                        }
                        upSql.append(" LAST_MODI_DT = now() where id = ?");
                        Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                        log.info("已更新：{}", exec);
                    }
                }
                if ("0100070673610711083".equals(id)){
                    sbComment = autoComment(comment,processFinanceComment,userName);
                    sbFile = autoFile(file,processFinanceFile);
                    if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                        upSql.append("update PO_ORDER_REQ set ");
                        if (!SharedUtil.isEmptyString(sbComment)){
                            upSql.append(" TEXT_REMARK_FOUR = '").append(sbComment).append("', ");
                        }
                        if (!SharedUtil.isEmptyString(sbFile)){
                            upSql.append(" FILE_ID_TWO = '").append(sbFile).append("', ");
                        }
                        upSql.append(" LAST_MODI_DT = now() where id = ?");
                        Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                        log.info("已更新：{}", exec);
                    }
                }
                // 判断当前用户是否是财务第一个审批的
                boolean izFirst = ProcessRoleExt.getUserFinanceRole(userId,"0099952822476412306");
                if (izFirst){
                    // 将后续审批人员信息写入任务
                    ProcessCommon.createOrderFinanceCheckUser(nodeInstanceId,"0099952822476412308",processId,procInstId,nodeId);
                }
            }
        } else if ("orderLegalFinanceReject".equals(status)){
            Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("TEXT_REMARK_THREE", null).set("FILE_ID_THREE",null).set("TEXT_REMARK_FOUR",null)
                    .set("FILE_ID_TWO",null).exec();
        } else if ("first".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_ONE", userId).set("APPROVAL_DATE_ONE", now).set("APPROVAL_MESSAGE", comment).set("FILE_ID_TWO",file).exec();
            log.info("已更新：{}", exec);
        } else if ("second".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_TWO", userId).set("APPROVAL_DATE_TWO", now).set("EARLY_COMMENT", comment).set("FILE_ID_THREE",file).exec();
            log.info("已更新：{}", exec);
        } else if ("third".equals(status)) {
            if (SharedUtil.isEmptyString(file)){
                throw new BaseException("审核附件不能为空");
            }
            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_THREE", userId).set("APPROVAL_DATE_THREE", now).set("DESIGN_COMMENT", comment).set("FILE_ID_SIX",file).exec();
            log.info("已更新：{}", exec);
        } else if ("fourth".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_FOUR", userId).set("APPROVAL_DATE_FOUR", now).set("APPROVAL_COMMENT_ONE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("fifth".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_FIVE", userId).set("APPROVAL_DATE_FIVE", now).set("USER_COMMENT", comment).exec();
            log.info("已更新：{}", exec);
        }
    }

    // 审核意见附件自动拼接
    private String autoFile(String newFile, String oldFile) {
        StringBuilder sbFile = new StringBuilder();
        if (!SharedUtil.isEmptyString(newFile)){
            if (!SharedUtil.isEmptyString(oldFile)){
                sbFile.append(oldFile).append(",").append(newFile);
            } else {
                sbFile.append(newFile);
            }
        }
        return sbFile.toString();
    }

    // 审核意见自动拼接
    private String autoComment(String newComment, String oldComment, String userName) {
        StringBuilder sbComment = new StringBuilder();
        if (!SharedUtil.isEmptyString(newComment)){
            if (!SharedUtil.isEmptyString(oldComment)){
                sbComment.append(oldComment).append("; \n").append(userName).append(":").append(newComment);
            } else {
                sbComment.append(userName).append(":").append(newComment);
            }
        }
        return sbComment.toString();
    }

    /**
     * 流程发起之时，相关数据校验
     */
    public void checkData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        Map<String,Object> valueMap = entityRecord.valueMap;
        String id = entityRecord.csCommId;
        //表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        // 费用明细处理
        dealFeeDetail(id,myJdbcTemplate);
        // 联系人明细处理
        dealPersonDetail(id,myJdbcTemplate);
        //是否涉及保函
        String baoHan = JdbcMapUtil.getString(valueMap,"IS_REFER_GUARANTEE_ID");
        //保函类型
        String baoHanType = JdbcMapUtil.getString(valueMap,"GUARANTEE_LETTER_TYPE_IDS");
        dealBaoHan(baoHan,baoHanType);
        //采购事项一级分类判断处理
        String matterId = JdbcMapUtil.getString(valueMap,"BUY_MATTER_ID");
        if (!SharedUtil.isEmptyString(matterId)){
            ProcessCommon.updateMatterTypeId(matterId,entCode,id);
        }

    }

    /**
     * 保函判断
     * @param baoHan 是否涉及保函
     * @param baoHanType 保函类型
     */
    private void dealBaoHan(String baoHan, String baoHanType) {
        if ("0099902212142031851".equals(baoHan)){
            if (SharedUtil.isEmptyString(baoHanType)){
                throw new BaseException("保函类型不能为空！");
            }
        } else {
            if (!SharedUtil.isEmptyString(baoHanType)){
                throw new BaseException("未涉及保函，请勿勾选保函类型！");
            }
        }
    }

    /**
     * 联系人明细判断
     * @param id 业务主表id
     * @param myJdbcTemplate 数据源
     */
    private void dealPersonDetail(String id, MyJdbcTemplate myJdbcTemplate) {
        //是否填写联系人
        List<Map<String, Object>> contactList = myJdbcTemplate.queryForList("SELECT OPPO_SITE_LINK_MAN, OPPO_SITE_CONTACT " +
                "FROM CONTRACT_SIGNING_CONTACT where PARENT_ID = ?", id);
        if (CollectionUtils.isEmpty(contactList)){
            throw new BaseException("联系人不能为空！");
        }
    }

    /**
     * 费用明细处理
     * @param id 业务主表id
     * @param myJdbcTemplate 数据源
     */
    private void dealFeeDetail(String id, MyJdbcTemplate myJdbcTemplate) {
        // 查询明细表合同总金额
        String sql = "select * from PM_ORDER_COST_DETAIL where CONTRACT_ID = ?";
        List<Map<String, Object>> list1 = myJdbcTemplate.queryForList(sql,id);
        if (CollectionUtils.isEmpty(list1)) {
            throw new BaseException("费用明细不能为空！");
        }
        for (Map<String, Object> tmp : list1) {
            String detailId = JdbcMapUtil.getString(tmp,"id");
            //含税金额
            BigDecimal taxAmt = new BigDecimal(JdbcMapUtil.getString(tmp,"AMT_ONE"));
            //税率
            BigDecimal tax = new BigDecimal(JdbcMapUtil.getString(tmp,"AMT_THREE")).divide(new BigDecimal(100));
            //不含税金额
            BigDecimal noTaxAmt = taxAmt.divide(tax.add(new BigDecimal(1)),2, RoundingMode.HALF_UP);
            myJdbcTemplate.update("update PM_ORDER_COST_DETAIL set AMT_TWO=? where id = ?",noTaxAmt,detailId);
            tmp.put("AMT_TWO",noTaxAmt);
        }
        //含税总金额
        BigDecimal amtTax = getSumAmtBy(list1,"AMT_ONE");
        //不含税总金额
        BigDecimal amtNoTax = getSumAmtBy(list1,"AMT_TWO");
        //税率
        BigDecimal tax = getTax(list1,"AMT_THREE");
        //更新合同表合同总金额数
        String sql2 = "update PO_ORDER_REQ set AMT_TWO = ?,AMT_THREE=?,AMT_FOUR=? where id = ?";
        myJdbcTemplate.update(sql2,amtTax,amtNoTax,tax,id);
    }

    //获取税率
    private BigDecimal getTax(List<Map<String, Object>> list, String str) {
        BigDecimal sum = new BigDecimal(0);
        tp: for (Map<String, Object> tmp : list) {
            String value =JdbcMapUtil.getString(tmp,str);
            if (!SharedUtil.isEmptyString(value)){
                sum = new BigDecimal(value);
                break tp;
            }
        }
        return sum;
    }

    // 根据字段求和
    private BigDecimal getSumAmtBy(List<Map<String, Object>> list, String str) {
        BigDecimal sum = new BigDecimal(0);
        for (Map<String, Object> tmp : list) {
            String value =JdbcMapUtil.getString(tmp,str);
            if (SharedUtil.isEmptyString(value)){
                value = "0";
            }
            sum = sum.add(new BigDecimal(value));
        }
        return sum;
    }

    // 汇总求和
    private BigDecimal getSumAmt(List<Map<String, Object>> list) {
        BigDecimal sum = new BigDecimal(0);
        for (Map<String, Object> tmp : list) {
            String value = tmp.get("AMT").toString();
            sum = sum.add(new BigDecimal(value));
        }
        return sum;
    }

    // 合同签订原本费用明细更新到新税率上
    public void updatePayData(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //查询所有合同主表数据
        String sql1 = "select id from PO_ORDER_REQ order by TS asc";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1);
        if (!CollectionUtils.isEmpty(list1)){
            for (Map<String, Object> tmp1 : list1) {
                String orderId = JdbcMapUtil.getString(tmp1,"id");
                //根据合同id去费用明细表查询数据
                String sql2 = "select * from PM_ORDER_COST_DETAIL where CONTRACT_ID = ? and status = 'ap'";
                List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,orderId);
                if (!CollectionUtils.isEmpty(list2)){
                    for (Map<String, Object> tmp : list2) {
                        myJdbcTemplate.update("update pm_order_cost_detail set AMT_ONE=AMT WHERE id = ?",JdbcMapUtil.getString(tmp,"id"));
                    }
                    //含税总金额
                    BigDecimal amtShui = getSumAmtBy(list2,"AMT_ONE");
                    //更新合同表合同总金额数
                    String sql3 = "update PO_ORDER_REQ set AMT_TWO = ? where id = ?";
                    myJdbcTemplate.update(sql3,amtShui,orderId);
                }
            }
        }
    }

    /**
     * 合同签订-历史数据处理-已完成流程数据汇总至合同数据表
     */
    public void historyDataCollect(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //查询所有已经走完的合同
        String sql = "select * from po_order_req where status = 'ap'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        //根据编码code查询数据来源id
        String sourceTypeId = GrSetValueExt.getValueId("order_data_source_type","po_order_req",myJdbcTemplate);
        if (!CollectionUtils.isEmpty(list)){
            for (Map<String, Object> tmp : list) {
                PoOrderExtApi.createOrderHistoryData(tmp,sourceTypeId,"0100070673610715078",myJdbcTemplate);
            }
        }
    }

    /**
     * 合同签订-历史数据处理-项目写入合同签订-项目名称(流程内)明细表
     */
    public void historyPrjInsertPrjDetail(){

        // PM_PRJ_IDS赋值
        List<PoOrderReq> list1 = PoOrderReq.selectByWhere(new Where().nin(PoOrderReq.Cols.STATUS,"VD","VDING")
                .neq(PoOrderReq.Cols.PROJECT_SOURCE_TYPE_ID,"0099952822476441375"));
        if (!CollectionUtils.isEmpty(list1)){
            for (PoOrderReq tmp : list1) {
                String projectId = tmp.getPmPrjId();
                String id = tmp.getId();
                if (!SharedUtil.isEmptyString(projectId)){
                    Crud.from("PO_ORDER_REQ").where().eq("ID",id).update()
                            .set("PM_PRJ_IDS",projectId).exec();
                }
            }
        }

        List<PoOrderReq> list = PoOrderReq.selectByWhere(new Where().eq(PoOrderReq.Cols.STATUS,"AP"));
        if (!CollectionUtils.isEmpty(list)){
            for (PoOrderReq tmp : list) {
                String poOrderReqId = tmp.getId();
                String prjIds = tmp.getPmPrjIds();
                if (!SharedUtil.isEmptyString(prjIds)){
                    PoOrderPrjDetailExt.insertData(poOrderReqId,prjIds);
                }
            }
        }
    }

    /**
     * 合同签订-非系统项目转系统项目
     */
    public void noSystemPrjCode(){

        // 非系统项目转系统项目
        List<PoOrderReq> list1 = PoOrderReq.selectByWhere(new Where()
                .nin(PoOrderReq.Cols.STATUS,"VD","VDING")
                .eq(PoOrderReq.Cols.PROJECT_SOURCE_TYPE_ID,"0099952822476441375"));
        if (!CollectionUtils.isEmpty(list1)){
            for (PoOrderReq tmp : list1) {
                String projectName = tmp.getProjectNameWr();
                String projectId = PmPrjExt.createPrjByMoreName(projectName);
                String id = tmp.getId();
                Crud.from(PoOrderReq.ENT_CODE).where().eq(PoOrderReq.Cols.ID,id).update()
                        .set(PoOrderReq.Cols.PM_PRJ_IDS,projectId)
                        .exec();
            }
        }

        //系统项目id写入pm_prj_ids
        List<PoOrderReq> list2 = PoOrderReq.selectByWhere(new Where()
                .nin(PoOrderReq.Cols.STATUS,"VD","VDING")
                .neq(PoOrderReq.Cols.PROJECT_SOURCE_TYPE_ID,"0099952822476441375"));
        if (!CollectionUtils.isEmpty(list2)){
            for (PoOrderReq tp : list2) {
                String id = tp.getId();
                String projectId = tp.getPmPrjId();
                Crud.from(PoOrderReq.ENT_CODE).where().eq(PoOrderReq.Cols.ID,id).update()
                        .set(PoOrderReq.Cols.PM_PRJ_IDS,projectId)
                        .exec();
            }
        }

        //已批准流程项目id写入明细表
        List<PoOrderReq> list3 = PoOrderReq.selectByWhere(new Where().eq(PoOrderReq.Cols.STATUS,"AP"));
        if (!CollectionUtils.isEmpty(list3)){
            for (PoOrderReq tmp : list3) {
                String id = tmp.getId();
                String projectId = tmp.getPmPrjIds();
                if (SharedUtil.isEmptyString(projectId)){
                    projectId = tmp.getPmPrjId();
                }
                PoOrderPrjDetailExt.insertData(id,projectId);
            }
        }
    }

    /**
     * 合同签订作废数据更新
     */
    public void orderCancel(){
        List<PoOrderReq> list = PoOrderReq.selectByWhere(new Where().eq(PoOrderReq.Cols.STATUS,"VD"));
        if (!CollectionUtils.isEmpty(list)){
            for (PoOrderReq tmp : list) {
                Crud.from("po_order").where().eq("CONTRACT_APP_ID",tmp.getId()).update().set("STATUS","VD").exec();
            }
        }
    }

    /**
     * 合同签订-历史导入数据-合同签订单位及合同类型写入数据表
     */
    public void historyCompanyToData(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<PoOrderReq> list = PoOrderReq.selectByWhere(new Where().eq(PoOrderReq.Cols.STATUS,"AP")
                .eq(PoOrderReq.Cols.IS_IMPORT,"1"));
        if (!CollectionUtils.isEmpty(list)){
            int fromIndex = 0;
            int toIndex = 500;
            while (true){
                if (toIndex < list.size()){
                    List<PoOrderReq> list1 = list.subList(fromIndex,toIndex);
                    log.error("索引{}-{}正在执行修改",fromIndex,toIndex);
                    updatePoOrder(list1);
                    myJdbcTemplate.update("commit;");
                } else if (toIndex == list.size()){
                    List<PoOrderReq> list1 = list.subList(fromIndex,toIndex);
                    if (list1.size() > 0){
                        log.error("索引{}-{}正在执行修改",fromIndex,toIndex);
                        updatePoOrder(list1);
                        myJdbcTemplate.update("commit;");
                    }
                    break;
                } else {
                    List<PoOrderReq> list1 = list.subList(fromIndex,list.size());
                    if (list1.size() > 0){
                        log.error("索引{}-{}正在执行修改",fromIndex,toIndex);
                        updatePoOrder(list1);myJdbcTemplate.update("commit;");

                    }
                    break;
                }
                fromIndex = toIndex;
                toIndex = toIndex+500;
            }
        }
    }

    private void updatePoOrder(List<PoOrderReq> list) {
        for (PoOrderReq tmp : list) {
            Crud.from("PO_ORDER").where().eq(PoOrder.Cols.CONTRACT_APP_ID,tmp.getId()).update()
                    .set(PoOrder.Cols.CUSTOMER_UNIT,tmp.getCustomerUnitOne())
                    .set(PoOrder.Cols.CONTRACT_CATEGORY_ONE_ID,tmp.getContractCategoryOneId())
                    .exec();
        }
    }

    /**
     * 合同签订-word转pdf-发起时
     */
    public void wordToPdfStart(){
        String status = "start";
        wordToPdfNew(status);
    }

    /**
     * 合同签订-word转pdf-发起时
     */
    public void wordToPdfSecondStart(){
        String status = "secondStart";
        wordToPdfNew(status);
    }

    /**
     * 合同签订不同情况转pdf
     * @param status 状态码
     */
    private void wordToPdfNew(String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        //用户id
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        //流程id
        String csId = entityRecord.csCommId;
        //流程实例id
        String procInstId = ExtJarHelper.procInstId.get();
        // 流程id
        String processId = ExtJarHelper.procId.get();
        String nodeId = ExtJarHelper.nodeId.get(); // 节点id
        String nodeInstId = ExtJarHelper.nodeInstId.get(); // 节点id
        //是否标准模板 0099799190825080669 = 是，0099799190825080670=否
        String isModel = JdbcMapUtil.getString(entityRecord.valueMap,"YES_NO_THREE");

        if (("0099799190825080669".equals(isModel) && "start".equals(status)) || ("0099799190825080670".equals(isModel) && "secondStart".equals(status))){
            //查询接口地址
            String httpSql = "select HOST_ADDR from BASE_THIRD_INTERFACE where code = 'order_word_to_pdf' and SYS_TRUE = 1";
            List<Map<String,Object>> listUrl = myJdbcTemplate.queryForList(httpSql);

            //公司名称
            String companyId = JdbcMapUtil.getString(entityRecord.valueMap,"CUSTOMER_UNIT_ONE");
            String companyName = myJdbcTemplate.queryForList("select name from PM_PARTY where id = ?",companyId).get(0).get("name").toString();

            new Thread(() -> {
                if (!CollectionUtils.isEmpty(listUrl)){
                    String url = listUrl.get(0).get("HOST_ADDR").toString();
                    if (SharedUtil.isEmptyString(url)){
                        //写入日志提示表
                        String id = Crud.from("AD_REMIND_LOG").insertData();
                        Crud.from("AD_REMIND_LOG").where().eq("id",id).update().set("AD_ENT_ID","0099799190825103145")
                                .set("ENT_CODE",entCode).set("ENTITY_RECORD_ID",csId).set("REMIND_USER_ID","0099250247095871681")
                                .set("REMIND_METHOD","日志提醒").set("REMIND_TARGET","admin").set("REMIND_TIME",new Date())
                                .set("REMIND_TEXT","用户"+userName+"在合同签订上传的合同文本转化为pdf失败").exec();
                    } else {
                        PoOrderReqView poOrderReqView = getOrderModel(entityRecord,procInstId,userId,status,companyName,entCode,processId,nodeId);
                        if (poOrderReqView != null){
                            String param = JSON.toJSONString(poOrderReqView);
                            //调用接口
                            HttpClient.doPost(url,param,"UTF-8");
                        }

                    }

                }
            }).start();
        }
    }

    /**
     * 合同签订实体装数
     * @param entityRecord 流程所有数据
     * @param procInstId 流程实例id
     * @param userId 操作人id
     * @param status 状态，区分发起还是二次发起
     * @param companyName 公司名称
     * @param entCode 表名
     * @param nodeType 节点类型
     * @return 合同信息实体
     */
    private PoOrderReqView getOrderModel(EntityRecord entityRecord, String procInstId, String userId, String status, String companyName, String entCode,String processId,String nodeType) {
        List<Map<String,String>> list = new ArrayList<>();
        // 是否标准模板 0099799190825080669=是 0099799190825080670=否
        String isModel = JdbcMapUtil.getString(entityRecord.valueMap,"YES_NO_THREE");

        Map<String,String> map = new HashMap<>();
        if ("PO_ORDER_REQ".equals(entCode) || "po_order_req".equals(entCode)){ //合同签订
            if ("START_EVENT".equals(nodeType) && "0099799190825080669".equals(isModel)){
                map.put("code","ATT_FILE_GROUP_ID");
                map.put("file",JdbcMapUtil.getString(entityRecord.valueMap,"ATT_FILE_GROUP_ID"));
            } else {
                if ("USER_TASK".equals(nodeType) && "0099799190825080670".equals(isModel)){ //合同文本
                    map.put("code","FILE_ID_ONE");
                    map.put("file",JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_ONE"));
                }
            }
//            if ("0099799190825080670".equals(isModel)){ //合同修编稿
//                map.put("code","FILE_ID_ONE");
//                map.put("file",JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_ONE"));
//            } else { //合同文本
//                map.put("code","ATT_FILE_GROUP_ID");
//                map.put("file",JdbcMapUtil.getString(entityRecord.valueMap,"ATT_FILE_GROUP_ID"));
//            }
            if (!map.isEmpty()){
                list.add(map);
            }
        } else if ("PO_ORDER_SUPPLEMENT_REQ".equals(entCode) || "po_order_supplement_req".equals(entCode)){ //补充协议
            String code1 = "FILE_ID_TENTH"; //合同修编稿
            String file1 = JdbcMapUtil.getString(entityRecord.valueMap,code1);
            if (!SharedUtil.isEmptyString(file1)){
                map.put("code",code1);
                map.put("file",file1);
                list.add(map);
            }
        }
        if (!CollectionUtils.isEmpty(list)){
            PoOrderReqView poOrderReqView = new PoOrderReqView();
            poOrderReqView.setColMap(list);
            poOrderReqView.setProcessId(processId);
            poOrderReqView.setId(entityRecord.csCommId);
            poOrderReqView.setProcessInstanceId(procInstId);
            poOrderReqView.setCreateBy(userId);
            poOrderReqView.setTableCode(entCode);
            if (SharedUtil.isEmptyString(companyName)){
                companyName = "三亚崖州湾科技城开发建设有限公司";
            }
            poOrderReqView.setCompanyName(companyName);
            return poOrderReqView;
        } else {
            return null;
        }
    }

    /**
     * 合同签订-流程完结时扩展
     */
    public void OrderProcessEnd(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String id = entityRecord.csCommId;
        Map<String, Object> valueMap = entityRecord.valueMap;
        String projectId = JdbcMapUtil.getString(valueMap,"PM_PRJ_IDS");
        //合同工期
        int duration = JdbcMapUtil.getInt(entityRecord.valueMap,"PLAN_TOTAL_DAYS");
        //合同签订日期
        Date signDate = DateTimeUtil.stringToDate(JdbcMapUtil.getString(entityRecord.valueMap,"SIGN_DATE"));
        //计算到期日期
        Date expireDate = DateTimeUtil.addDays(signDate,duration);
        //更新到期日期字段
        Crud.from("PO_ORDER_REQ").where().eq("id",id).update().set("DATE_FIVE",expireDate).exec();
        //生产合同编号
        String orderCode = createOrderCode(id,valueMap,myJdbcTemplate);
        valueMap.put("CONTRACT_CODE",orderCode);
        //将合同数据写入传输至合同数据表(po_order)
        PoOrderExtApi.createData(entityRecord,"PO_ORDER_REQ","0100070673610715078",myJdbcTemplate);
        //项目信息写入明细表
        PoOrderPrjDetailExt.createData(entityRecord);
        //采购事项判断-施工类型更新项目状态
        updateProjectStatus(projectId,id,entCode,valueMap);
        //更新项目勘察设计施工监理单位信息
        updatePrjParty(id,projectId,valueMap);
    }

    /**
     * 更新项目勘察设计施工监理单位信息
     * @param id 合同签订唯一id
     * @param projectId 项目id
     * @param valueMap 源数据map
     */
    private void updatePrjParty(String id, String projectId, Map<String, Object> valueMap) {
        String matterId = JdbcMapUtil.getString(valueMap,"BUY_MATTER_ID"); // 采购事项id
        List<String> list = getNeedUpdateList();
        if (list.contains(matterId)){
            // 查询相对方公司 默认只取第一条
            ContractSigningContact contractSigningContact = ContractSigningContact.selectByWhere(new Where().eq(ContractSigningContact.Cols.PARENT_ID,id)).get(0);
            String partyId = "", partyName = "";
            if (contractSigningContact != null){
                partyName = contractSigningContact.getWinBidUnitOne();
            }
            if ("1622794410934886400".equals(matterId) || "1622794493038387200".equals(matterId)){ // 勘察单位
                partyId = PmInLibraryExt.createOrUpdateParty(partyName,"IS_SURVEYOR");
                projectValue(projectId,partyId,"SURVEYOR_UNIT");
            } else if ("0099799190825080980".equals(matterId) || "1635119644153196544".equals(matterId) || "1675783677050761216".equals(matterId)){ // 设计单位
                partyId = PmInLibraryExt.createOrUpdateParty(partyName,"IS_DESIGNER");
                projectValue(projectId,partyId,"DESIGNER_UNIT");
            } else if ("0099799190825080731".equals(matterId) || "0099799190825080728".equals(matterId)){ // 施工单位
                partyId = PmInLibraryExt.createOrUpdateParty(partyName,"IS_CONSTRUCTOR");
                projectValue(projectId,partyId,"CONSTRUCTOR_UNIT");
            } else if ("0099799190825080729".equals(matterId)){ // 监理单位
                partyId = PmInLibraryExt.createOrUpdateParty(partyName,"IS_SUPERVISOR");
                projectValue(projectId,partyId,"SUPERVISOR_UNIT");
            }
        }
    }

    /**
     * 项目封装数据，更新项目合作方数据
     * @param projectId 项目id
     * @param partyId 合作方id
     * @param supervisorUnit 合作方类型
     */
    private void projectValue(String projectId, String partyId, String supervisorUnit) {
        String[] arr = projectId.split(",");
        for (String prj : arr) {
            PmPrjExt.updateOneColValue(prj,partyId,supervisorUnit);
        }
    }

    /**
     * 需要更新合作单位的采购事项
     * @return 采购事项集合
     */
    private List<String> getNeedUpdateList() {
        List<String> list = new ArrayList<>();
        list.add("1622794410934886400"); // 工程勘察（不含测绘）
        list.add("1622794493038387200"); // 工程勘察（含测绘）
        list.add("0099799190825080980"); // 施工图设计
        list.add("1635119644153196544"); // 设计（初步设计+施工图）
        list.add("1675783677050761216"); // 初步设计和概算
        list.add("0099799190825080731"); // 施工工程
        list.add("0099799190825080728"); // 设计施工总承包（EPC）
        list.add("0099799190825080729"); // 工程监理
        return list;
    }

    /**
     * 更新项目相关信息
     * @param projectId 项目id
     * @param id 表id
     * @param entCode 表名
     * @param valueMap 源数据map
     */
    private void updateProjectStatus(String projectId, String id, String entCode, Map<String,Object> valueMap) {
        String matterTypeId = JdbcMapUtil.getString(valueMap,"BUY_MATTER_TYPE_ID"); // 采购事项一级类型id
        String matterId = JdbcMapUtil.getString(valueMap,"BUY_MATTER_ID"); // 采购事项id
        if (!SharedUtil.isEmptyString(matterId)){
            if (SharedUtil.isEmptyString(matterTypeId)){
                //根据采购事项id反推采购事项分类id
                matterTypeId = ProcessCommon.updateMatterTypeId(matterId,entCode,id);
            }
            String name = GrSetValue.selectById(matterTypeId).getName();
            if ("施工".equals(name)){
                PmPrjExt.updatePrjStatus(projectId,"1673502467645648896");
            }
        }

    }

    /**
     * 流程完结生成合同编号
     * @param id 唯一id
     * @param valueMap 表单值
     * @param myJdbcTemplate 数据源
     * @return 合同编号
     */
    public String createOrderCode(String id, Map<String, Object> valueMap, MyJdbcTemplate myJdbcTemplate) {
        String orderCode = "";
        List<PoOrderReq> list = PoOrderReq.selectByWhere(new Where().eq(PoOrderReq.Cols.ID,id));
        if (!CollectionUtils.isEmpty(list)){
            String code = list.get(0).getContractCode();
            if (SharedUtil.isEmptyString(code)){
                // 查询当前已审批通过的招标合同数量
                List<Map<String, Object>> map = myJdbcTemplate.queryForList("select count(*) as num from PO_ORDER_REQ where status = 'AP' ");
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String year = sdf.format(date).substring(0, 7).replace("-", "");
                // 合同编码规则
                int num = Integer.parseInt(map.get(0).get("num").toString()) + 1;
                Format formatCount = new DecimalFormat("0000");
                String formatNum = formatCount.format(num);
                code = "gc-" + year + "-" + formatNum;
            }
            String name = valueMap.get("CONTRACT_NAME").toString();
            myJdbcTemplate.update("update PO_ORDER_REQ set CONTRACT_CODE = ? , NAME = ? where id = ?",code, name, id);
            orderCode = code;
        }
        return orderCode;
    }

    /**
     * 流程操作-合同签订-确认操作
     */
    public void orderProcessOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程操作-合同签订-拒绝操作
     */
    public void orderProcessRefuse(){
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
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        String procInstId = ExtJarHelper.procInstId.get();
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String processId = ExtJarHelper.procId.get();
        String nodeId = ExtJarHelper.nodeId.get();
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus) || "caiHuaStart".equals(nodeStatus) || "secondStart".equals(nodeStatus)){
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            } else if ("financeLegalOK".equals(nodeStatus)){ // 7-财务部法务部审批 节点点击操作逻辑
                // 判断当前用户是否是财务第一个审批的
                boolean izFirst = ProcessRoleExt.getUserFinanceRole(userId,"0099952822476412306");
                if (izFirst){
                    // 将后续审批人员信息写入任务
                    ProcessCommon.createOrderFinanceCheckUser(nodeInstanceId,"0099952822476412308",processId,procInstId,nodeId);
                }
            }
        } else {
            if ("lawyerRefuse".equals(nodeStatus)){ // 法律审批拒绝
                ProcessCommon.clearData("FILE_ID_SIX,TEXT_REMARK_TWO",csCommId,entCode,myJdbcTemplate);
            } else if ("financeLegalRefuse".equals(nodeStatus)){ //财务部法务部审批-通过
                ProcessCommon.clearData("FILE_ID_THREE,TEXT_REMARK_THREE,FILE_ID_TWO,TEXT_REMARK_FOUR",csCommId,entCode,myJdbcTemplate);
            }
        }
    }

    /**
     * 各审批节点赋值
     * @param status 状态码
     * @param nodeId 节点id
     * @return 节点状态名称
     */
    private String getNodeStatus(String status, String nodeId) {
        String nodeName = "";
        if ("OK".equals(status)){
            if ("0099952822476409137".equals(nodeId)){ //1-发起
                nodeName = "start";
            } else if ("0099952822476409156".equals(nodeId)){ //4-才华预审
                nodeName = "caiHuaStart";
            } else if ("0099952822476409150".equals(nodeId)){ //8-发起人重新提交
                nodeName = "secondStart";
            } else if ("0099952822476412228".equals(nodeId)){ //6-法律审核意见-通过
                nodeName = "lawyerOK";
            } else if ("0099952822476409144".equals(nodeId)){ //7-财务部法务部审批-通过
                nodeName = "financeLegalOK";
            }
        } else {
            if ("0099952822476412228".equals(nodeId)){ //6-法律审核意见-拒绝
                nodeName = "lawyerRefuse";
            } else if ("0099952822476409144".equals(nodeId)){ //7-财务部法务部审批-拒绝
                nodeName = "financeLegalRefuse";
            }
        }
        return nodeName;
    }

    /**
     * 通用-合同模块word转pdf
     * @Param status 状态码
     */
    public void publicWordToPdf() {
        String status = "all";
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        //用户id
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        //流程id
        String processId = ExtJarHelper.procId.get();
        String csId = entityRecord.csCommId;
        //流程实例id
        String procInstId = ExtJarHelper.procInstId.get();
        String nodeType = ProcessCommon.getNodeType(ExtJarHelper.flowId.get()); // 流转id
        //查询接口地址
        String httpSql = "select HOST_ADDR from BASE_THIRD_INTERFACE where code = 'order_word_to_pdf' and SYS_TRUE = 1";
        List<Map<String,Object>> listUrl = myJdbcTemplate.queryForList(httpSql);
        //公司名称
        String companyId = JdbcMapUtil.getString(entityRecord.valueMap,"CUSTOMER_UNIT_ONE");
        String companyName = myJdbcTemplate.queryForList("select name from PM_PARTY where id = ?",companyId).get(0).get("name").toString();

        new Thread(() -> {
            if (!CollectionUtils.isEmpty(listUrl)){
                String url = listUrl.get(0).get("HOST_ADDR").toString();
                if (SharedUtil.isEmptyString(url)){
                    //写入日志提示表
                    String id = Crud.from("AD_REMIND_LOG").insertData();
                    Crud.from("AD_REMIND_LOG").where().eq("id",id).update().set("AD_ENT_ID","0099799190825103145")
                            .set("ENT_CODE","PO_ORDER_REQ").set("ENTITY_RECORD_ID",csId).set("REMIND_USER_ID","0099250247095871681")
                            .set("REMIND_METHOD","日志提醒").set("REMIND_TARGET","admin").set("REMIND_TIME",new Date())
                            .set("REMIND_TEXT","用户"+userName+"在合同签订上传的合同文本转化为pdf失败").exec();
                } else {
                    PoOrderReqView poOrderReqView = getOrderModel(entityRecord,procInstId,userId,status,companyName,entCode,processId,nodeType);
                    String param = JSON.toJSONString(poOrderReqView);
                    //调用接口
                    HttpClient.doPost(url,param,"UTF-8");
                }
            }
        }).start();

    }


}
