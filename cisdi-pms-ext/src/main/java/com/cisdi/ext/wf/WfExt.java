package com.cisdi.ext.wf;

import com.cisdi.ext.enums.FileCodeEnum;
import com.cisdi.ext.link.linkPackage.AttLinkDifferentProcess;
import com.cisdi.ext.model.PmFundReqPlan;
import com.cisdi.ext.pm.PmPrjReqExt;
import com.cisdi.ext.util.ProFileUtils;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class WfExt {

    public void changeStatusToAping() {
        String newStatus = "APING";
        changeStatus(newStatus);
    }

    public void changeStatusToDr() {
        String newStatus = "DR";
        changeStatus(newStatus);
    }

    public void changeStatusToAp() {
        String newStatus = "AP";
        changeStatus(newStatus);
    }

    public void changeStatusToDn() {
        String newStatus = "DN";
        changeStatus(newStatus);
    }

    public void changeStatusToVding() {
        String newStatus = "VDING";
        changeStatus(newStatus);
    }

    public void changeStatusToVd() {
        String newStatus = "VD";
        changeStatus(newStatus);
    }

    public void syncFile(){
        saveFile();
    }

    private void changeStatus(String newStatus) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();

        if (entityRecordList != null) {
            for (EntityRecord entityRecord : entityRecordList) {
                String csCommId = entityRecord.csCommId;
                Map<String, Object> valueMap = entityRecord.valueMap;
                int update = myJdbcTemplate.update("update " + entityCode + " t set t.status = ? where t.id=?", newStatus, csCommId);
                log.info("已更新：{}", update);

                String processName = "", nowDate = "",userName = "";
                String sql0 = "SELECT c.name as processName,b.IS_URGENT,b.START_DATETIME," +
                        "(select name from ad_user where id = b.START_USER_ID) as userName FROM "+entityCode+" a " +
                        "LEFT JOIN wf_process_instance b on a.LK_WF_INST_ID = b.id LEFT JOIN wf_process c on b.WF_PROCESS_ID = c.id WHERE a.id = ?";
                List<Map<String,Object>> list0 = myJdbcTemplate.queryForList(sql0,csCommId);
                if (!CollectionUtils.isEmpty(list0)){
                    processName = JdbcMapUtil.getString(list0.get(0),"processName");
                    nowDate = JdbcMapUtil.getString(list0.get(0),"START_DATETIME").replace("T"," ");
                    userName = JdbcMapUtil.getString(list0.get(0),"userName");
                }

                //需要自定义标题的流程
                List<String> tableList = AttLinkDifferentProcess.getTableList();

                // 审批流审批通过
                if ("AP".equals(newStatus)) {
                    Format formatCount = new DecimalFormat("0000");

                    // 立项申请生成项目编号 2023-03-06弃用
//                    if ("PM_PRJ_REQ".equals(entityCode)) {
//                        Object prj_code = myJdbcTemplate.queryForMap("select t.PRJ_CODE from PM_PRJ_REQ t where t.id=?", csCommId).get("PRJ_CODE");
//
//                        // 若无项目编号：
//                        if (SharedUtil.isEmptyObject(prj_code)) {
//                            // 获取新的项目编号：
//                            Object new_prj_code = myJdbcTemplate.queryForMap("select concat('XM','-',lpad((select count(*) from pm_prj_req t),4,0)," +
//                                    "'-',replace(current_date,'-','')) prj_code").get("prj_code");
//
//                            // 设置项目编号、代码：
//                            int update1 = myJdbcTemplate.update("update PM_PRJ_REQ t set t.prj_code=?,t.code=? where t.id=?", new_prj_code,
//                                    new_prj_code, csCommId);
//                            log.info("已更新：{}", update1);
//                        }
//                    }

                    // 合同签订批准后生成合同编号 2023-05-10取消，换到流程完结时扩展中生产
//                    if ("PO_ORDER_REQ".equals(entityCode) || "po_order_req".equals(entityCode)) {
//                        // 查询当前已审批通过的招标合同数量
//                        List<Map<String, Object>> map = myJdbcTemplate.queryForList("select count(*) as num from PO_ORDER_REQ where status = 'AP' ");
//                        Date date = new Date();
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        String year = sdf.format(date).substring(0, 7).replace("-", "");
//                        // 合同编码规则
//                        int num = Integer.valueOf(map.get(0).get("num").toString()) + 1;
//
//                        String formatNum = formatCount.format(num);
//                        String code = "gc-" + year + "-" + formatNum;
//                        String name = valueMap.get("CONTRACT_NAME").toString();
//                        int update2 = myJdbcTemplate.update("update PO_ORDER_REQ set CONTRACT_CODE = ? , NAME = ? where id = ?",code, name, csCommId);
//                    }
                    // 补充合同批准后生成合同编号
                    if ("PO_ORDER_SUPPLEMENT_REQ".equals(entityCode)) {
                        // 查询当前审批通过的补充合同数量和该合同的name

                        String relationContractId = JdbcMapUtil.getString(valueMap,"RELATION_CONTRACT_ID");
                        String name = "";
                        String contractName = "";
                        if (!SharedUtil.isEmptyString(relationContractId)){
                            List<Map<String, Object>> nameMap = myJdbcTemplate.queryForList("SELECT CONTRACT_NAME FROM po_order_req where id = ?", relationContractId);
                            if (!CollectionUtils.isEmpty(nameMap)){
                                name = JdbcMapUtil.getString(nameMap.get(0),"CONTRACT_NAME");
//                                contractName = name + "补充协议" + formatNum;
                                contractName = name + "补充协议";
                                // 写入到补充合同表
                                int update1 = myJdbcTemplate.update("update PO_ORDER_SUPPLEMENT_REQ set CONTRACT_NAME = ?  " +
                                        "where id = ? ", contractName, csCommId);
                            }
                        }


                    }

                    //一些特殊流程发起后即结束。流程名称处理
                    List<String> endProcessList = getEndProcessList();
                    if (endProcessList.contains(entityCode)){
                        int update1 = myJdbcTemplate.update("update wf_process_instance pi join wf_process p on pi" +
                                ".WF_PROCESS_ID=p.id join ad_user u on pi.START_USER_ID=u.id join " + entityCode + " t on pi" +
                                ".ENTITY_RECORD_ID=t.id join pm_prj prj on t.PM_PRJ_ID=prj.id and t.id=? set pi.name=concat( p.name,'-', " +
                                "prj.name ,'-',u.name,'-',pi.START_DATETIME)", csCommId);
                    }

                    // 特殊流程，名称在流程发起时即生成，此处不生成
                    List<String> specialList = getSpecialList();
                    if (tableList.contains(entityCode)) {
                        if (specialList.contains(entityCode)){
                            continue;
                        } else if ("PM_PRJ_REQ".equals(entityCode)) {
                            int update1 = myJdbcTemplate.update("update PM_PRJ_REQ t set t.name=t.PRJ_NAME where t.id=?", csCommId);
                        } else if ("PO_ORDER_REQ".equals(entityCode)) {
                            String name = entityRecord.valueMap.get("CONTRACT_NAME").toString();
                            int update1 = myJdbcTemplate.update("update PM_PRJ_REQ t set t.name=? where t.id=?", name, csCommId);
                        } else if ("PM_PRJ_STOP_ORDER_REQ".equals(entityCode)){
                            int update1 = myJdbcTemplate.update("update PM_PRJ_STOP_ORDER_REQ t set t.name=t.REMARK_TWO where t.id=?", csCommId);
                        } else if ("PM_BID_KEEP_FILE_REQ".equals(entityCode)){
                            String sql = "update wf_process_instance a left join wf_process b on a.WF_PROCESS_ID = b.id LEFT JOIN pm_bid_keep_file_req c on a.id = c.LK_WF_INST_ID " +
                                    "LEFT JOIN pm_prj d on c.PM_PRJ_ID = d.id LEFT JOIN ad_user e on c.CRT_USER_ID = e.id " +
                                    "set a.name = concat(b.name,'-',d.name,'-',e.name,'-',now()) where c.id = ?";
                            int update1 = myJdbcTemplate.update(sql, csCommId);
                            update1 = myJdbcTemplate.update("update pm_bid_keep_file_req a LEFT JOIN wf_process_instance b on a.LK_WF_INST_ID = b.id set a.name = b.name where a.id = ?",csCommId);
                        } else if ("BID_PROCESS_MANAGE".equals(entityCode)){
                            String sysPrjSql = "update wf_process_instance a left join wf_process b on a.WF_PROCESS_ID = b.id LEFT JOIN BID_PROCESS_MANAGE c on a.id = c.LK_WF_INST_ID " +
                                    "LEFT JOIN pm_prj d on c.PM_PRJ_ID = d.id LEFT JOIN ad_user e on c.CRT_USER_ID = e.id " +
                                    "set a.name = concat(b.name,'-',d.name,'-',e.name,'-',now()) where c.id = ?";
                            String notSysPrjSql = "update wf_process_instance a left join wf_process b on a.WF_PROCESS_ID = b.id LEFT JOIN BID_PROCESS_MANAGE c on a.id = c.LK_WF_INST_ID " +
                                    "LEFT JOIN ad_user e on c.CRT_USER_ID = e.id " +
                                    "set a.name = concat(b.name,'-',?,'-',e.name,'-',now()) where c.id = ?";
                            String notSysPrj = JdbcMapUtil.getString(entityRecord.valueMap, "PROJECT_NAME_WR");
                            if (Strings.isNullOrEmpty(notSysPrj)){
                                myJdbcTemplate.update(sysPrjSql,csCommId);
                            }else {
                                myJdbcTemplate.update(notSysPrjSql,notSysPrj,csCommId);
                            }
                        } else {
                            String sql1 = "select a.NAME from wf_process_instance a left join " + entityCode + " b on a.id = b.LK_WF_INST_ID where " +
                                    "b.id = ?";
                            List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql1, csCommId);
                            if (CollectionUtils.isEmpty(list)) {
                                throw new BaseException("流程标题不能为空");
                            }
                            String name = list.get(0).get("NAME").toString();
                            String sql2 = "update " + entityCode + " set NAME = ? where id = ?";
                            int update2 = myJdbcTemplate.update(sql2, name, csCommId);
                        }

                    }

                    // 资金需求计划申请完成同步数据
                    if ("PM_FUND_REQUIRE_PLAN_REQ".equals(entityCode)) {
                        PmFundReqPlan pmFundReqPlan = PmFundReqPlan.insertData();
                        String id = pmFundReqPlan.getId();
                        String pmFundRequirePlanReqId = entityRecord.csCommId;
                        // 项目id
                        String prjId = valueMap.get("AMOUT_PM_PRJ_ID").toString();
                        // 发起部门id
                        String hrDeptId = valueMap.get("CRT_DEPT_ID").toString();
                        // 计划需求总金额 待修改
                        //        String totalAmt = valueMap.get("").toString();
                        BigDecimal totalAmt = new BigDecimal(10);
                        // 提出时间
                        String applyTime = valueMap.get("CRT_DT").toString();
                        // 备注
                        String remark = valueMap.get("REMARK").toString();
                        // 计划名称 待修改
                        String name = myJdbcTemplate.queryForMap("select name from pm_prj where id = ?", valueMap.get(
                                "AMOUT_PM_PRJ_ID")).get("name").toString() + "-资金需求计划";

                        String sql = "update pm_fund_req_plan set NAME = ?,PM_PRJ_ID = ?,HR_DEPT_ID = ?,TOTAL_AMT = ?,APPLY_TIME = ?," +
                                "PM_FUND_REQUIRE_PLAN_REQ_ID = ?,REMARK = ? where id = ?";
                        myJdbcTemplate.update(sql, name, prjId, hrDeptId, totalAmt, applyTime, pmFundRequirePlanReqId, remark, id);
                    }
                }

                // 审批流程创建
                if ("APING".equals(newStatus)) {

                    // 一些额外校验 该流程弃用 2022-02-20 暂时不删除代码，只注释
//                    if ("PO_PUBLIC_BID_REQ".equals(entityCode)) { // 招标校验
//                        checkBidReq(entityRecord, csCommId);
//                    }

                    //查询该实例紧急程度
                    String urgentSql = "SELECT a.IS_URGENT FROM WF_PROCESS_INSTANCE a left join "+entityCode+" b on a.id = b.LK_WF_INST_ID where b.id = ? ";
                    List<Map<String,Object>> urgentList = myJdbcTemplate.queryForList(urgentSql,csCommId);
                    String urgent = "";
                    if (!CollectionUtils.isEmpty(urgentList)){
                        String urgentType = JdbcMapUtil.getString(urgentList.get(0),"IS_URGENT");
                        if ("1".equals(urgentType)){
                            urgent = "【紧急】";
                        }
                    }

                    String sql = "";
                    //定义流程实例名称规则
                    String name = "",projectName = "", otherName = "";
                    int update1 = 0;

                    //判断该流程是否有项目信息
                    List<String> noProjectList = getNoProjectList();

                    if (noProjectList.contains(entityCode)){
                        sql = "update wf_process_instance pi join " + entityCode + " t on pi.ENTITY_RECORD_ID = t.id set pi.name = ? where t.id = ?";
                        String title = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_THREE");
                        name = concatProcessName("-",processName,title,userName,nowDate);
                        update1 = myJdbcTemplate.update(sql, name,csCommId);
                        return;
                    }

                    String sql2 = "SELECT (select name from pm_prj where id = a.PM_PRJ_ID) as projectName FROM "+entityCode+" a WHERE a.id = ?";
                    List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,csCommId);
                    if (!CollectionUtils.isEmpty(list2)){
                        projectName = JdbcMapUtil.getString(list2.get(0),"projectName");
                        if (SharedUtil.isEmptyString(projectName)){
                            projectName = getProjectName(myJdbcTemplate,entityRecord);
                        }
                        processName = urgent + processName;
                    }

                    //合同流程标题规则
                    List<String> orderNameTable = AttLinkDifferentProcess.getOrderProcessName();

                    if (!CollectionUtils.isEmpty(tableList)) {
                        if (tableList.contains(entityCode)) {
                            // 流程名称按规定创建

                            // 特殊流程 更新流程内name字段
                            List<String> specialList = getSpecialList();
                            if (specialList.contains(entityCode)) {
                                if ("PM_BUY_DEMAND_REQ".equals(entityCode)){ //采购需求审批
                                    sql = "select b.name from PM_BUY_DEMAND_REQ a left join gr_set_value b on a.BUY_MATTER_ID = b.id where a.id = ?";
                                    List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,csCommId);
                                    if (!CollectionUtils.isEmpty(list)){
                                        otherName = JdbcMapUtil.getString(list.get(0),"name");
                                    }
                                    name = concatProcessName("-",processName,projectName,otherName,userName,nowDate);
                                } else if ("PO_ORDER_REQ".equals(entityCode)){ //合同签订
                                    otherName = getContractName(entityCode,"CONTRACT_NAME",csCommId,myJdbcTemplate);
                                    name = concatProcessName("-",processName,projectName,otherName,userName,nowDate);
                                } else if ("PM_BID_APPROVAL_REQ".equals(entityCode)) { //招标文件审批
                                    otherName = getContractName(entityCode,"NAME_ONE",csCommId,myJdbcTemplate);
                                    name = concatProcessName("-",processName,otherName,userName,nowDate);
                                } else {
                                    if ("PM_SUPERVISE_PLAN_REQ".equals(entityCode)){
                                        otherName = JdbcMapUtil.getString(valueMap,"REMARK_ONE");
                                    } else if ("QUALITY_RECORD".equals(entityCode)){
                                        otherName = JdbcMapUtil.getString(valueMap,"REMARK_ONE");
                                    } else if ("PM_SUPERVISE_NOTICE_REQ".equals(entityCode)){
                                        otherName = JdbcMapUtil.getString(valueMap,"CODE_ONE");
                                    } else {
                                        otherName = getContractName(entityCode,"NAME_ONE",csCommId,myJdbcTemplate);
                                    }
                                    name = concatProcessName("-",processName,otherName,projectName,userName,nowDate);
                                }
                                if ("PM_SUPERVISE_NOTICE_REQ".equals(entityCode)){
                                    update1 = myJdbcTemplate.update("update "+entityCode+" set name = ? where id = ?", otherName,csCommId);
                                } else {
                                    update1 = myJdbcTemplate.update("update "+entityCode+" set name = ? where id = ?", name,csCommId);
                                }
                                update1 = myJdbcTemplate.update("update wf_process_instance pi join " + entityCode + " t on pi.ENTITY_RECORD_ID = t.id set pi.name = ? where t.id = ?",name,csCommId);
                                return;
                            }  else if (orderNameTable.contains(entityCode)){ //补充协议/合同需求审批/合同终止 流程标题规则
                                otherName = getContractName(entityCode,"CONTRACT_NAME",csCommId,myJdbcTemplate);
                                name = concatProcessName("-",processName,projectName,otherName,userName,nowDate);
                                update1 = myJdbcTemplate.update("update wf_process_instance pi join " + entityCode + " t on pi.ENTITY_RECORD_ID = t.id set pi.name = ? where t.id = ?",name,csCommId);
                            } else {
                                sql = "update wf_process_instance pi join " + entityCode + " t on pi.ENTITY_RECORD_ID = t.id set pi.name = ? where t.id = ?";
                                name = concatProcessName("-",processName,projectName,userName,nowDate);
                                update1 = myJdbcTemplate.update(sql, name,csCommId);
                            }
                            log.info("已更新：{}", update1);

                            // 发起人是否存在部门信息校验
                            try {
                                String hrDeptId = valueMap.get("CRT_DEPT_ID").toString();
                                if (SharedUtil.isEmptyString(hrDeptId) || "0".equals(hrDeptId)) {
                                    throw new BaseException("对不起，您尚未有部门信息，不允许进行流程提交");
                                }
                            } catch (Exception e) {
                                throw new BaseException("对不起，您尚未有部门信息，不允许进行流程提交");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 生成流程实例标题
     */
    public void generateProInstanceName(){

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;
            Map<String, Object> valueMap = entityRecord.valueMap;
            String processName = "", nowDate = "",userName = "";
            String sql0 = "SELECT c.name as processName,b.IS_URGENT,b.START_DATETIME," +
                    "(select name from ad_user where id = b.START_USER_ID) as userName FROM "+entityCode+" a " +
                    "LEFT JOIN wf_process_instance b on a.LK_WF_INST_ID = b.id LEFT JOIN wf_process c on b.WF_PROCESS_ID = c.id WHERE a.id = ?";
            List<Map<String,Object>> list0 = myJdbcTemplate.queryForList(sql0,csCommId);
            if (!CollectionUtils.isEmpty(list0)){
                processName = JdbcMapUtil.getString(list0.get(0),"processName");
                nowDate = JdbcMapUtil.getString(list0.get(0),"START_DATETIME").replace("T"," ");
                userName = JdbcMapUtil.getString(list0.get(0),"userName");
            }

            //查询该实例紧急程度
            String urgentSql = "SELECT a.IS_URGENT FROM WF_PROCESS_INSTANCE a left join "+entityCode+" b on a.id = b.LK_WF_INST_ID where b.id = ? ";
            List<Map<String,Object>> urgentList = myJdbcTemplate.queryForList(urgentSql,csCommId);
            String urgent = "";
            if (!CollectionUtils.isEmpty(urgentList)){
                String urgentType = JdbcMapUtil.getString(urgentList.get(0),"IS_URGENT");
                if ("1".equals(urgentType)){
                    urgent = "【紧急】";
                }
            }

            String sql = "";
            //定义流程实例名称规则
            String name = "",projectName = "", otherName = "";
            int update1 = 0;

            //判断该流程是否有项目信息
            List<String> noProjectList = getNoProjectList();

            if (noProjectList.contains(entityCode)){
                sql = "update wf_process_instance pi join " + entityCode + " t on pi.ENTITY_RECORD_ID = t.id set pi.name = ? where t.id = ?";
                String title = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_THREE");
                name = concatProcessName("-",processName,title,userName,nowDate);
                update1 = myJdbcTemplate.update(sql, name,csCommId);
                return;
            }

            String sql2 = "SELECT (select name from pm_prj where id = a.PM_PRJ_ID) as projectName FROM "+entityCode+" a WHERE a.id = ?";
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,csCommId);
            if (!CollectionUtils.isEmpty(list2)){
                projectName = JdbcMapUtil.getString(list2.get(0),"projectName");
                if (SharedUtil.isEmptyString(projectName)){
                    projectName = getProjectName(myJdbcTemplate,entityRecord);
                }
                processName = urgent + processName;
            }

            //合同流程标题规则
            List<String> orderNameTable = AttLinkDifferentProcess.getOrderProcessName();

            //需要自定义标题的流程
            List<String> tableList = AttLinkDifferentProcess.getAllProcessList();

            if (!CollectionUtils.isEmpty(tableList)) {
                if (tableList.contains(entityCode)) {
                    // 流程名称按规定创建

                    // 特殊流程 更新流程内name字段
                    List<String> specialList = getSpecialList();
                    if (specialList.contains(entityCode)) {
                        if ("PM_BUY_DEMAND_REQ".equals(entityCode)){ //采购需求审批
                            sql = "select b.name from PM_BUY_DEMAND_REQ a left join gr_set_value b on a.BUY_MATTER_ID = b.id where a.id = ?";
                            List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,csCommId);
                            if (!CollectionUtils.isEmpty(list)){
                                otherName = JdbcMapUtil.getString(list.get(0),"name");
                            }
                            name = concatProcessName("-",processName,projectName,otherName,userName,nowDate);
                        } else if ("PO_ORDER_REQ".equals(entityCode)){ //合同签订
                            otherName = getContractName(entityCode,"CONTRACT_NAME",csCommId,myJdbcTemplate);
                            name = concatProcessName("-",processName,projectName,otherName,userName,nowDate);
                        } else if ("PM_BID_APPROVAL_REQ".equals(entityCode)) { //招标文件审批
                            otherName = getContractName(entityCode,"NAME_ONE",csCommId,myJdbcTemplate);
                            name = concatProcessName("-",processName,otherName,userName,nowDate);
                        } else {
                            if ("PM_SUPERVISE_PLAN_REQ".equals(entityCode)){
                                otherName = JdbcMapUtil.getString(valueMap,"REMARK_ONE");
                            } else if ("QUALITY_RECORD".equals(entityCode)){
                                otherName = JdbcMapUtil.getString(valueMap,"REMARK_ONE");
                            } else if ("PM_SUPERVISE_NOTICE_REQ".equals(entityCode)){
                                otherName = JdbcMapUtil.getString(valueMap,"CODE_ONE");
                            } else {
                                otherName = getContractName(entityCode,"NAME_ONE",csCommId,myJdbcTemplate);
                            }
                            name = concatProcessName("-",processName,otherName,projectName,userName,nowDate);
                        }
                        if ("PM_SUPERVISE_NOTICE_REQ".equals(entityCode)){
                            update1 = myJdbcTemplate.update("update "+entityCode+" set name = ? where id = ?", otherName,csCommId);
                        } else {
                            update1 = myJdbcTemplate.update("update "+entityCode+" set name = ? where id = ?", name,csCommId);
                        }
                        update1 = myJdbcTemplate.update("update wf_process_instance pi join " + entityCode + " t on pi.ENTITY_RECORD_ID = t.id set pi.name = ? where t.id = ?",name,csCommId);
                        return;
                    }  else if (orderNameTable.contains(entityCode)){ //补充协议/合同需求审批/合同终止 流程标题规则
                        otherName = getContractName(entityCode,"CONTRACT_NAME",csCommId,myJdbcTemplate);
                        name = concatProcessName("-",processName,projectName,otherName,userName,nowDate);
                        update1 = myJdbcTemplate.update("update wf_process_instance pi join " + entityCode + " t on pi.ENTITY_RECORD_ID = t.id set pi.name = ? where t.id = ?",name,csCommId);
                    } else {
                        sql = "update wf_process_instance pi join " + entityCode + " t on pi.ENTITY_RECORD_ID = t.id set pi.name = ? where t.id = ?";
                        name = concatProcessName("-",processName,projectName,userName,nowDate);
                        update1 = myJdbcTemplate.update(sql, name,csCommId);
                    }
                    log.info("已更新：{}", update1);

                    // 发起人是否存在部门信息校验
                    try {
                        String hrDeptId = valueMap.get("CRT_DEPT_ID").toString();
                        if (SharedUtil.isEmptyString(hrDeptId) || "0".equals(hrDeptId)) {
                            throw new BaseException("对不起，您尚未有部门信息，不允许进行流程提交");
                        }
                    } catch (Exception e) {
                        throw new BaseException("对不起，您尚未有部门信息，不允许进行流程提交");
                    }
                }
            }
        }


    }

    /**
     * 查询流程中某一个字段
     * @param tableName 流程表名
     * @param colName 需要查询的字段
     * @param csCommId 该条记录id
     * @param myJdbcTemplate 数据源
     * @return
     */
    private String getContractName(String tableName, String colName, String csCommId, MyJdbcTemplate myJdbcTemplate) {
        String value = "";
        String sql = "select " + colName + " from " + tableName + " where id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,csCommId);
        if (!CollectionUtils.isEmpty(list)){
            value = JdbcMapUtil.getString(list.get(0),colName);
        }
        return value;
    }

    /**
     * 查询流程中某一个字段
     * @param tableName 流程表名
     * @param colName 需要查询的字段
     * @param csCommId 该条记录id
     * @param myJdbcTemplate 数据源
     * @return
     */
    public static String getContractNameStatic(String tableName, String colName, String csCommId, MyJdbcTemplate myJdbcTemplate) {
        String value = "";
        String sql = "select " + colName + " from " + tableName + " where id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,csCommId);
        if (!CollectionUtils.isEmpty(list)){
            value = JdbcMapUtil.getString(list.get(0),colName);
        }
        return value;
    }

    // 获取发起流程时的项目名称
    private String getProjectName(MyJdbcTemplate myJdbcTemplate, EntityRecord entityRecord) {
        String projectName = "";
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
            if (SharedUtil.isEmptyString(projectId)){
                projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_IDS");
            }
        }
        if (SharedUtil.isEmptyString(projectId)){
            projectName = JdbcMapUtil.getString(entityRecord.valueMap,"PROJECT_NAME_WR");
        } else {
            projectId = projectId.replace(",","','");
            String sql = "select GROUP_CONCAT(name SEPARATOR '-') as name from pm_prj where id in ('"+projectId+"')";
            List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
            if (!CollectionUtils.isEmpty(list)){
                projectName = JdbcMapUtil.getString(list.get(0),"name");
            } else {
                throw new BaseException("没有找到对应项目，请联系管理员处理");
            }
        }
        return projectName;
    }

    // 获取发起流程时的项目名称
    public static String getProjectNameStatic(MyJdbcTemplate myJdbcTemplate, EntityRecord entityRecord) {
        String projectName = "";
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
            if (SharedUtil.isEmptyString(projectId)){
                projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_IDS");
            }
        }
        if (SharedUtil.isEmptyString(projectId)){
            projectName = JdbcMapUtil.getString(entityRecord.valueMap,"PROJECT_NAME_WR");
        } else {
            projectId = projectId.replace(",","','");
            String sql = "select GROUP_CONCAT(name SEPARATOR '-') as name from pm_prj where id in ('"+projectId+"')";
            List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
            if (!CollectionUtils.isEmpty(list)){
                projectName = JdbcMapUtil.getString(list.get(0),"name");
            } else {
                throw new BaseException("没有找到对应项目，请联系管理员处理");
            }
        }
        return projectName;
    }

    // 获取项目id
    private String getProjectId(Map<String, Object> valueMap) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String projectId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(valueMap,"AMOUT_PM_PRJ_ID");
            if (SharedUtil.isEmptyString(projectId)){
                projectId = JdbcMapUtil.getString(valueMap,"PM_PRJ_IDS");
                if (SharedUtil.isEmptyString(projectId)){
                    projectId = PmPrjReqExt.getPrjIdNew(valueMap);
                }
            }
        }
        return projectId;
    }

    // 根据长度自动拼接规则
    private String concatProcessName(String start,String...values) {
        StringJoiner sb = new StringJoiner(start);
        Arrays.stream(values).filter(p->!SharedUtil.isEmptyString(p)).forEach(p->sb.add(p));
        return sb.toString();
    }

    // 根据长度自动拼接规则
    public static String concatProcessNameStatic(String start,String...values) {
        StringJoiner sb = new StringJoiner(start);
        Arrays.stream(values).filter(p->!SharedUtil.isEmptyString(p)).forEach(p->sb.add(p));
        return sb.toString();
    }

    // 公开招标流程校验
    private void checkBidReq(EntityRecord entityRecord, String csCommId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 根据id查询是否有明细信息
        List<Map<String, Object>> list1 = myJdbcTemplate.queryForList("select TOTAL_AMT from pm_cost_detail where BIDDING_NAME_ID = ?", csCommId);
        if (CollectionUtils.isEmpty(list1)) {
            throw new BaseException("费用明细不能为空，请填写费用明细！");
        }
        // 招标控制价
        BigDecimal price = new BigDecimal(entityRecord.valueMap.get("BID_CTL_PRICE_LAUNCH").toString());

        BigDecimal priceDetail = bigDecimalSum(list1);
        if (priceDetail.compareTo(price) != 0) {
            throw new BaseException("费用明细总金额数和招标总控价不等，请核查");
        }

    }

    // list内求和
    private BigDecimal bigDecimalSum(List<Map<String, Object>> list) {
        BigDecimal sum = new BigDecimal(0);
        for (Map<String, Object> tmp : list) {
            String date = tmp.get("TOTAL_AMT").toString();
            sum = sum.add(new BigDecimal(date));
        }
        return sum;
    }

    // 流程审批通过后需要更新Name字段的
    // 存文件
    private void saveFile() {
        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        Map<String, Object> valueMap = entityRecord.valueMap;
        String procInstId = ExtJarHelper.procInstId.get();

        //招采项目备案及归档
        if ("PM_BID_KEEP_FILE_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            if(SharedUtil.isEmptyString(prjId)){
                prjId = this.getWritePrjId(valueMap);
            }
            String FILE_ID_ONE = JdbcMapUtil.getString(valueMap, "FILE_ID_ONE");
            String FILE_ID_TWO = JdbcMapUtil.getString(valueMap, "FILE_ID_TWO");
            // 中标通知书
            ProFileUtils.insertProFile(prjId, FILE_ID_ONE,FileCodeEnum.PM_BID_KEEP_FILE_REQ_FILE_ID_ONE);
            // 备案回执
            ProFileUtils.insertProFile(prjId, FILE_ID_TWO,FileCodeEnum.PM_BID_KEEP_FILE_REQ_FILE_ID_TWO);
        }

        //中选单位及标后用印审批
        if ("PM_USE_CHAPTER_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            if(SharedUtil.isEmptyString(prjId)){
                prjId = this.getWritePrjId(valueMap);
            }
            String FILE_ID_ONE = JdbcMapUtil.getString(valueMap, "FILE_ID_ONE");
            String FILE_ID_TWO = JdbcMapUtil.getString(valueMap, "FILE_ID_TWO");
            // 其他依据
            ProFileUtils.insertProFile(prjId, FILE_ID_ONE,FileCodeEnum.PM_USE_CHAPTER_REQ_FILE_ID_ONE);
            //附件
            ProFileUtils.insertProFile(prjId, FILE_ID_TWO,FileCodeEnum.PM_USE_CHAPTER_REQ_FILE_ID_TWO);
        }

        //合同签订
        if ("PO_ORDER_REQ".equals(entityCode)){
            String projectId = getProjectId(valueMap);
            String[] arr = projectId.split(",");
            for (String tmp : arr) {
                String ATT_FILE_GROUP_ID = JdbcMapUtil.getString(valueMap, "ATT_FILE_GROUP_ID");
                String FILE_ID_TWO = JdbcMapUtil.getString(valueMap, "FILE_ID_TWO");
                String FILE_ID_THREE = JdbcMapUtil.getString(valueMap, "FILE_ID_THREE");
                String FILE_ID_FOUR = JdbcMapUtil.getString(valueMap, "FILE_ID_FOUR");
                String FILE_ID_ONE = JdbcMapUtil.getString(valueMap, "FILE_ID_ONE");
                String FILE_ID_FIVE = JdbcMapUtil.getString(valueMap, "FILE_ID_FIVE");
                // 附件
                ProFileUtils.insertProFile(tmp, ATT_FILE_GROUP_ID,FileCodeEnum.PO_ORDER_REQ_ATT_FILE_GROUP_ID);
                ProFileUtils.insertProFile(tmp, FILE_ID_TWO,FileCodeEnum.PO_ORDER_REQ_FILE_ID_TWO);
                ProFileUtils.insertProFile(tmp, FILE_ID_THREE,FileCodeEnum.PO_ORDER_REQ_FILE_ID_THREE);
                ProFileUtils.insertProFile(tmp, FILE_ID_FOUR,FileCodeEnum.PO_ORDER_REQ_FILE_ID_FOUR);
                ProFileUtils.insertProFile(tmp, FILE_ID_ONE,FileCodeEnum.PO_ORDER_REQ_FILE_ID_ONE);
                ProFileUtils.insertProFile(tmp, FILE_ID_FIVE,FileCodeEnum.PO_ORDER_REQ_FILE_ID_FIVE);
            }

        }

        //施工通知单
        if ("PM_SUPERVISE_NOTICE_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            String ATT_FILE_GROUP_ID = JdbcMapUtil.getString(valueMap, "ATT_FILE_GROUP_ID");
            // 附件
            ProFileUtils.insertProFile(prjId, ATT_FILE_GROUP_ID,FileCodeEnum.PM_SUPERVISE_NOTICE_REQ_ATT_FILE_GROUP_ID);
        }

        //施工通知回复单
        if ("PM_SUPERVISE_NOTICE_REPLY_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            String ATT_FILE_GROUP_ID = JdbcMapUtil.getString(valueMap, "ATT_FILE_GROUP_ID");
            // 附件
            ProFileUtils.insertProFile(prjId, ATT_FILE_GROUP_ID,FileCodeEnum.PM_SUPERVISE_NOTICE_REPLY_REQ_ATT_FILE_GROUP_ID);
        }

        //施工进度计划
        if ("PM_BUILD_PROGRESS_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            String ATT_FILE_GROUP_ID = JdbcMapUtil.getString(valueMap, "ATT_FILE_GROUP_ID");
            // 附件
            ProFileUtils.insertProFile(prjId, ATT_FILE_GROUP_ID,FileCodeEnum.CONSTRUCTION_SCHEDULE_ATTACHMENT);
        }

        //设计变更
        if ("PM_DESIGN_CHANGE_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            String ATT_FILE_GROUP_ID = JdbcMapUtil.getString(valueMap, "ATT_FILE_GROUP_ID");
            // 附件
            ProFileUtils.insertProFile(prjId, ATT_FILE_GROUP_ID,FileCodeEnum.PM_DESIGN_CHANGE_REQ_ATT_FILE_GROUP_ID);
        }

        //概念方案设计管理
        if ("PM_CONCEPTUAL_SCHEME_DESIGN".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            String DESIGN_SKETCH_FILE_ONE = JdbcMapUtil.getString(valueMap, "DESIGN_SKETCH_FILE_ONE");
            String APPROVE_FILE_ID_ONE = JdbcMapUtil.getString(valueMap, "APPROVE_FILE_ID_ONE");
            String DESIGN_SKETCH_FILE_TWO = JdbcMapUtil.getString(valueMap, "DESIGN_SKETCH_FILE_TWO");
            String APPROVE_FILE_ID_TWO = JdbcMapUtil.getString(valueMap, "APPROVE_FILE_ID_TWO");
            String DESIGN_SKETCH_FILE_THREE = JdbcMapUtil.getString(valueMap, "DESIGN_SKETCH_FILE_THREE");
            String APPROVE_FILE_ID_THREE = JdbcMapUtil.getString(valueMap, "APPROVE_FILE_ID_THREE");
            String APPROVE_FILE_ID_FOUR = JdbcMapUtil.getString(valueMap, "APPROVE_FILE_ID_FOUR");
            // 效果图1
            ProFileUtils.insertProFile(prjId, DESIGN_SKETCH_FILE_ONE,FileCodeEnum.CONCEPTUAL_RENDERING_ONE);
            //概念方案设计任务书1
            ProFileUtils.insertProFile(prjId, APPROVE_FILE_ID_ONE,FileCodeEnum.CONCEPTUAL_SCHEME_DESIGN_SPECIFICATION_ONE);
            //效果图2
            ProFileUtils.insertProFile(prjId, DESIGN_SKETCH_FILE_TWO,FileCodeEnum.CONCEPTUAL_RENDERING_TWO);
            //概念方案设计任务书2
            ProFileUtils.insertProFile(prjId, APPROVE_FILE_ID_TWO,FileCodeEnum.CONCEPTUAL_SCHEME_DESIGN_SPECIFICATION_TWO);
            //效果图3
            ProFileUtils.insertProFile(prjId, DESIGN_SKETCH_FILE_THREE,FileCodeEnum.CONCEPTUAL_RENDERING_THREE);
            //概念方案设计任务书3
            ProFileUtils.insertProFile(prjId, APPROVE_FILE_ID_THREE,FileCodeEnum.CONCEPTUAL_SCHEME_DESIGN_SPECIFICATION_THREE);
            //概念方案设计成果
            ProFileUtils.insertProFile(prjId, APPROVE_FILE_ID_FOUR,FileCodeEnum.CONCEPTUAL_SCHEME_DESIGN_RESULT_FILE);
        }

        //工程复工报审表
        if ("PM_PRJ_RESTART_TRIAL_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            String FILE_ID_ONE = JdbcMapUtil.getString(valueMap, "FILE_ID_ONE");
            // 附件
            ProFileUtils.insertProFile(prjId, FILE_ID_ONE,FileCodeEnum.PM_PRJ_RESTART_TRIAL_REQ_FILE_ID_ONE);
        }

        //工程复工令
        if ("PM_PRJ_RESTART_ORDER_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            String FILE_ID_ONE = JdbcMapUtil.getString(valueMap, "FILE_ID_ONE");
            // 附件
            ProFileUtils.insertProFile(prjId, FILE_ID_ONE,FileCodeEnum.PM_PRJ_RESTART_ORDER_REQ_FILE_ID_ONE);
        }

        //工程开工令
        if ("PM_START_ORDER_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            String FILE_ID_ONE = JdbcMapUtil.getString(valueMap, "FILE_ID_ONE");
            // 附件
            ProFileUtils.insertProFile(prjId, FILE_ID_ONE,FileCodeEnum.PM_START_ORDER_REQ_FILE_ID_ONE);
        }

        //工程暂停令
        if ("PM_PRJ_STOP_ORDER_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            String FILE_ID_ONE = JdbcMapUtil.getString(valueMap, "FILE_ID_ONE");
            // 附件
            ProFileUtils.insertProFile(prjId, FILE_ID_ONE,FileCodeEnum.PM_PRJ_STOP_ORDER_REQ_FILE_ID_ONE);
        }

        //采购需求审批
        if ("PM_BUY_DEMAND_REQ".equals(entityCode)) {
            String projectId = getProjectId(valueMap);
            String[] arr = projectId.split(",");
            for (String prjId : arr) {
                String FILE_ID_ONE = JdbcMapUtil.getString(valueMap, "FILE_ID_ONE");
                String FILE_ID_TWO = JdbcMapUtil.getString(valueMap, "FILE_ID_TWO");
                String FILE_ID_THREE = JdbcMapUtil.getString(valueMap, "FILE_ID_THREE");
                // 采购需求说明书
                ProFileUtils.insertProFile(prjId, FILE_ID_ONE,FileCodeEnum.PM_BUY_DEMAND_REQ_FILE_ID_ONE);
                // 采购预算表
                ProFileUtils.insertProFile(prjId, FILE_ID_TWO, FileCodeEnum.PM_BUY_DEMAND_REQ_FILE_ID_TWO);
                // 采购启动依据文件
                ProFileUtils.insertProFile(prjId, FILE_ID_THREE, FileCodeEnum.PM_BUY_DEMAND_REQ_FILE_ID_THREE);
            }
//            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
//            if(SharedUtil.isEmptyString(prjId)){
//                prjId = this.getWritePrjId(valueMap);
//            }

        }

        //招标文件审批
        if ("PM_BID_APPROVAL_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            if(SharedUtil.isEmptyString(prjId)){
                prjId = this.getWritePrjId(valueMap);
            }
            String FILE_ID_ONE = JdbcMapUtil.getString(valueMap, "FILE_ID_ONE");
            String FILE_ID_TWO = JdbcMapUtil.getString(valueMap, "FILE_ID_TWO");
            // 招标文件
            ProFileUtils.insertProFile(prjId, FILE_ID_ONE,FileCodeEnum.PM_BID_APPROVAL_REQ_FILE_ID_ONE);
            // 招标文件终稿
            ProFileUtils.insertProFile(prjId, FILE_ID_TWO, FileCodeEnum.PM_BID_APPROVAL_REQ_FILE_ID_TWO);
        }

        // 标前资料用印审批
        if ("PM_FILE_CHAPTER_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            if(SharedUtil.isEmptyString(prjId)){
                prjId = this.getWritePrjId(valueMap);
            }
            String FILE_ID_ONE = JdbcMapUtil.getString(valueMap, "FILE_ID_ONE"); //招标文件
            String FILE_ID_TWO = JdbcMapUtil.getString(valueMap, "FILE_ID_TWO"); //标前资料
            // 招标文件
            ProFileUtils.insertProFile(prjId, FILE_ID_ONE,FileCodeEnum.PM_FILE_CHAPTER_REQ_FILE_ID_ONE);
            // 标前资料
            ProFileUtils.insertProFile(prjId, FILE_ID_TWO, FileCodeEnum.PM_FILE_CHAPTER_REQ_FILE_ID_TWO);
        }

        // 水保
        if ("PM_WATER_PLAN".equals(entityCode)) {

            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            String conservationApplyFileIds = JdbcMapUtil.getString(valueMap, "CONSERVATION_APPLY_FILE");
            String conservationReplyFileIds = JdbcMapUtil.getString(valueMap, "CONSERVATION_REPLY_FILE");
            // 水保申请材料
            ProFileUtils.insertProFile(prjId, conservationApplyFileIds,
                    FileCodeEnum.WATER_SOIL_CONSERVATION_APPLICATION_MATERIAL);
            // 环评批复文件
            ProFileUtils.insertProFile(prjId, conservationReplyFileIds, FileCodeEnum.EIA_REPLY_DOCUMENT);
            // 水保流程附件
            ProFileUtils.insertProFile(prjId, getProcessFileByProcInstId(procInstId), FileCodeEnum.WATER_PLAN_PROCESS_ATTACHMENT);

        }

        // 施工规划许可
        if ("PM_PRJ_PLANNING_PERMIT_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            // 方案设计核查申请材料
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "EIA_REQ_FILE"),
                    FileCodeEnum.SCHEME_DESIGN_VERIFICATION_APPLICATION_MATERIALS);
            // 方案设计评审搞
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "REVIEW_REPORT_FILE"),
                    FileCodeEnum.SCHEME_DESIGN_REVIEW_OFFICE);
            // 方案设计专家意见
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "REVIEW_DRAFT_FILE"),
                    FileCodeEnum.SCHEME_DESIGN_EXPERT_OPINIONS);
            // 方案设计修编稿
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "REVISION_FILE"),
                    FileCodeEnum.SCHEME_DESIGN_REVISED_DRAFT);
            // 方案检查ppt文件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "PLAN_CHECK_PPT_FILE_GROUP_ID"),
                    FileCodeEnum.SCHEME_CHECK_PPT_FILE);
            // 管理局预审会市委会意见
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "ADMIN_COMMITTEE_OPINON_FILE_GROUP_ID"),
                    FileCodeEnum.ADMINISTRATION_REVIEW_MEETING_COMMITTEE_OPINIONS);
            // 规委会预审会市委会意见
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "PLAN_COMMITTEE_OPINON_FILE_GROUP_ID"),
                    FileCodeEnum.PLANNING_COMMITTEE_REVIEW_MEETING_OPINIONS);
            // 分管副市长预审会市委会意见
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "DEPUTY_MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID"),
                    FileCodeEnum.DEPUTY_MAYOR_REVIEW_MEETING_OPINIONS);
            // 分管副市长和市长预审会市委会意见
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID"),
                    FileCodeEnum.MAYOR_REVIEW_MEETING_OPINIONS);
            // 市规委会意见
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "COMMITTEE_OPINON_FILE_GROUP_ID"),
                    FileCodeEnum.MUNICIPAL_PLANNING_COMMISSION_OPINIONS);
            // 工程规划许可申请材料
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "PRJ_PLAN_REQ_FILE"),
                    FileCodeEnum.PROJECT_PLANNING_PERMIT_APPLICATION_MATERIALS);
            // 工程规划许可证
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "PRJ_PLAN_REQ_PERMIT_FILE_GROUP_ID"),
                    FileCodeEnum.PROJECT_PLANNING_LICENCE);
            // 工程规划许可流程附件
            ProFileUtils.insertProFile(prjId, getProcessFileByProcInstId(procInstId),
                    FileCodeEnum.PROJECT_PLANNING_PERMIT_PROCESS_ATTACHMENT);

        }

        // 采购公开招标
        if ("PO_PUBLIC_BID_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            // 招标需求附件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "BID_DEMAND_FILE_GROUP_ID"),
                    FileCodeEnum.BID_DEMAND_FILE_GROUP);
            // 领导审批附件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "LEADER_APPROVE_FILE_GROUP_ID"),
                    FileCodeEnum.LEADER_APPROVE_FILE_GROUP);
            // 招标文件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "BID_FILE_GROUP_ID"),
                    FileCodeEnum.BID_FILE_GROUP);
            // 发标招标文件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "BID_ISSUE_FILE_GROUP_ID"),
                    FileCodeEnum.BID_ISSUE_FILE_GROUP);
            // 标后附件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "BID_AFTER_FILE_GROUP_ID"),
                    FileCodeEnum.BID_AFTER_FILE_GROUP);
            // 中标通知书
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "BID_WIN_NOTICE_FILE_GROUP_ID"),
                    FileCodeEnum.BID_WIN_NOTICE_FILE_GROUP);
            // 采购公开招标流程附件
            ProFileUtils.insertProFile(prjId, getProcessFileByProcInstId(procInstId), FileCodeEnum.PURCHASE_TENDER_PROCESS_ATTACHMENT);
        }

        // 工程开工报审
        if ("PM_PRJ_KICK_OFF_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");

            // 开工申请附件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "ATT_FILE_GROUP_ID"),
                    FileCodeEnum.COMMENCEMENT_APPLICATION_ATTACHMENT);
            // 开工申请流程附件
            ProFileUtils.insertProFile(prjId, getProcessFileByProcInstId(procInstId),
                    FileCodeEnum.COMMENCEMENT_APPLICATION_PROCESS_ATTACHMENT);
        }

        // 采购合同补充协议申请
        if ("PO_ORDER_SUPPLEMENT_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            String FILE_ID_SEVEN = JdbcMapUtil.getString(valueMap, "FILE_ID_SEVEN");
            String FILE_ID_SIX = JdbcMapUtil.getString(valueMap, "FILE_ID_SIX");
            String FILE_ID_TWO = JdbcMapUtil.getString(valueMap, "FILE_ID_TWO");
            String FILE_ID_THREE = JdbcMapUtil.getString(valueMap, "FILE_ID_THREE");
            String FILE_ID_FOUR = JdbcMapUtil.getString(valueMap, "FILE_ID_FOUR");
            String ATT_FILE_GROUP_ID = JdbcMapUtil.getString(valueMap, "ATT_FILE_GROUP_ID");
            String FILE_ID_FIVE = JdbcMapUtil.getString(valueMap, "FILE_ID_FIVE");
            // 合同原稿
            ProFileUtils.insertProFile(prjId, FILE_ID_SEVEN,FileCodeEnum.PO_ORDER_SUPPLEMENT_REQ_FILE_ID_SEVEN);
            // 法律审核附件
            ProFileUtils.insertProFile(prjId, FILE_ID_SIX,FileCodeEnum.PO_ORDER_SUPPLEMENT_REQ_FILE_ID_SIX);
            // 财务部门修订稿
            ProFileUtils.insertProFile(prjId, FILE_ID_TWO,FileCodeEnum.PO_ORDER_SUPPLEMENT_REQ_FILE_ID_TWO);
            // 法务部门修订稿
            ProFileUtils.insertProFile(prjId, FILE_ID_THREE,FileCodeEnum.PO_ORDER_SUPPLEMENT_REQ_FILE_ID_THREE);
            // 采纳意见附件
            ProFileUtils.insertProFile(prjId, FILE_ID_FOUR,FileCodeEnum.PO_ORDER_SUPPLEMENT_REQ_FILE_ID_FOUR);
            // 补充协议附件
            ProFileUtils.insertProFile(prjId, ATT_FILE_GROUP_ID,FileCodeEnum.PO_ORDER_SUPPLEMENT_REQ_ATT_FILE_GROUP_ID);
            // 附件
            ProFileUtils.insertProFile(prjId, FILE_ID_FIVE,FileCodeEnum.PO_ORDER_SUPPLEMENT_REQ_FILE_ID_FIVE);
        }

        // 采购合同变更申请
        if ("PO_ORDER_CHANGE_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            // 变更附件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "ATT_FILE_GROUP_ID"), FileCodeEnum.CHANGE_ATTACHMENTS);
            // 采购合同变更申请流程附件
            ProFileUtils.insertProFile(prjId, getProcessFileByProcInstId(procInstId), FileCodeEnum.PO_ORDER_CHANGE_REQ_PROCESS_ATTACHMENT);
        }

        // 采购合同终止申请
        if ("PO_ORDER_TERMINATE_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            // 合同终止附件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "ATT_FILE_GROUP_ID"), FileCodeEnum.CONTRACT_TERMINATION_APPENDIX);
            // 合同终止申请流程附件
            ProFileUtils.insertProFile(prjId, getProcessFileByProcInstId(procInstId), FileCodeEnum.CONTRACT_TERMINATION_REQ_PROCESS_ATTACHMENT);
        }

        //初设概算
        if ("PM_PRJ_INVEST2".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //概算申报材料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"BUDGETESTIMATEDECLARATION_FILE"),FileCodeEnum.BUDGETESTIMATEDECLARATION_FILE);
            //初概报告评审稿
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"REVIEW_REPORT_FILE"),FileCodeEnum.GS_REVIEW_REPORT_FILE);
            //初概报告专家意见
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"EXPERT_FILE"),FileCodeEnum.GS_EXPERT_FILE);
            //初概报告修编稿
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"REVISION_FILE"),FileCodeEnum.GS_REVISION_FILE);
            //概算批复
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"REPLY_FILE"),FileCodeEnum.GS_REPLY_FILE);
        }

        //设计任务书
        if ("PM_DESIGN_ASSIGNMENT_BOOK".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap, "AMOUT_PM_PRJ_ID");
            //附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.DESIGN_SPECIFICATION_ATTACHMENT);
            //审批附件1
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.DESIGN_SPECIFICATION_ATTACHMENT_ONE);
            //审批附件2
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_TWO"),FileCodeEnum.DESIGN_SPECIFICATION_ATTACHMENT_TWO);
            //审批附件3
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_THREE"),FileCodeEnum.DESIGN_SPECIFICATION_ATTACHMENT_THREE);
        }

        //方案设计管理
        if ("PM_DESIGN_ASSIGNMENT".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            //效果图
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"DESIGN_SKETCH_FILE_ONE"),FileCodeEnum.SCHEMATIC_DESIGN_MANAGEMENT_RENDERING);
            //概念方案设计成果
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.DESIGN_MANAGEMENT_CONCEPTUAL_SCHEME_DESIGN_RESULT);
        }

        //施工图设计管理
        if ("PM_CONSTRUCTION_DRAWING_DESIGN".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            //效果图
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"DESIGN_SKETCH_FILE_ONE"),FileCodeEnum.CONSTRUCTION_DRAWING_DESIGN_MANAGEMENT_RENDERING);
            //概念方案设计成果
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.CONSTRUCTION_DRAWING_CONCEPTUAL_DESIGN_RESULT);
        }

        //可研估算
        if("PM_PRJ_INVEST1".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            //可研申请材料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"PM_PRJ_FILE"),FileCodeEnum.PM_PRJ_FILE);
            //修编稿文件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"REVISION_FILE"),FileCodeEnum.KY_REVISION_FILE);
            //评审报告文件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"REVIEW_DRAFT_FILE"),FileCodeEnum.KY_REVIEW_DRAFT_FILE);
            //评审稿文件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"REVIEW_REPORT_FILE"),FileCodeEnum.KY_REVIEW_REPORT_FILE);
            //专家意见文件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"EXPERT_FILE"),FileCodeEnum.KY_EXPERT_FILE);
            //可研批复文件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"REPLY_FILE"),FileCodeEnum.FEASIBLE_REPLY_FILE);
        }

        //施工许可
        if ("PM_CONSTRUCT_PERMIT_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            //基坑及土石方工程申请
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"PRJ_REQ_FILE"),FileCodeEnum.FOUNDATION_PIT_EARTHWORK_APPLICATION);
            //主体工程申请
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"KEEP_RECORD_FILE"),FileCodeEnum.MAIN_WORKS_APPLICATION);
            //基坑及土石方工程施工许可函
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"EARTHWORK_FILE"),FileCodeEnum.EARTHWORK_FILE);
            //主体工程施工许可证
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"SUBJECT_FILE"),FileCodeEnum.SUBJECT_FILE);
        }

        //社会稳定性评价
        if ("PM_STABLE_EVAL".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            //社会稳定性风险评估报告
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"KEEP_RECORD_FILE"),FileCodeEnum.SOCIAL_STABILITY_RISK_ASSESSMENT_REPORT);
        }

        //固定资产投资节能评价
        if ("PM_ENERGY_EVAL".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //固定资产投资节能评估报告
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"KEEP_RECORD_FILE"),FileCodeEnum.FIXED_ASSETS_INVESTMENT_SAVING_REPORT);
            //节能报告评审稿
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"REVIEW_REPORT_FILE"),FileCodeEnum.ENERGY_SAVING_REPORT_REVIEW_DRAFT);
            //节能报告专家意见
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"EXPERT_FILE"),FileCodeEnum.ENERGY_SAVING_REPORT_EXPERT_OPINION);
            //环评批复文件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"REPLY_FILE"),FileCodeEnum.EIA_APPROVAL_DOCUMENT);
        }

        //环评
        if ("PM_ENVIRONMENT_EVAL".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //环评申请材料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"EIA_REQ_FILE"),FileCodeEnum.EIA_APPLICATION_MATERIAL);
            //环评报告评审稿
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"REVIEW_REPORT_FILE"),FileCodeEnum.EIA_REPORT_REVIEW_DRAFT);
            //环评报告专家意见
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"EXPERT_FILE"),FileCodeEnum.EIA_REPORT_EXPERT_OPINION);
            //环评报告修编稿
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"REVISION_FILE"),FileCodeEnum.REVISION_OF_EIA_REPORT);
            //环评批复文件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"CONSERVATION_REPLY_FILE"),FileCodeEnum.EIA_APPROVAL_FILE);
        }

        //农转用手续办理
        if ("PM_FARMING_PROCEDURES".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //农转用手续附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.AGRICULTURAL_CONVERSION_PROCEDURES_ANNEX);
            //勘测定界材料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"SUBJECT_FILE"),FileCodeEnum.SURVEY_DELIMITATION_MATERIALS);
            //农转用批复文件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.AGRICULTURAL_CONVERSION_APPROVAL_DOCUMENT);
        }

        //林地调整办理手续
        if ("PM_WOODLAND_PROCEDURES".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //林地办理审核附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.FOREST_LAND_HANDLING_AUDIT_ATTACHMENT);
            //勘测定界报告
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"SUBJECT_FILE"),FileCodeEnum.SURVEY_DELIMITATION_REPORT);
            //林地使用可行性报告评审前
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.FOREST_LAND_FEASIBILITY_PRE_REPORT);
            //林地使用可行性报告专家意见
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_TWO"),FileCodeEnum.FOREST_FEASIBILITY_EXPERT_OPINIONS);
            //林地使用可行性报告
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_THREE"),FileCodeEnum.FOREST_LAND_USE_FEASIBILITY_REPORT);
            //林地使用同意书
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_FOUR"),FileCodeEnum.FOREST_LAND_USE_CONSENT);
        }

        //用地规划许可
        if ("PM_LAND_USE_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //红线核查意见书
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_FOUR"),FileCodeEnum.RED_LINE_VERIFICATION_OPINIONS);
            //用地勘测定界材料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.LAND_USE_SURVEY_AND_DELIMITATION_MATERIALS);
            //征地调查报告
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_TWO"),FileCodeEnum.LAND_ACQUISITION_INVESTIGATION_REPORT);
            //用地规划许可证
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_THREE"),FileCodeEnum.LAND_USE_PLANNING_PERMIT);
        }

        //招标核准
        if ("PM_TENDER_VERIFICATION".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //招标核准申请附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.BIDDING_APPROVAL_APPLICATION_ATTACHMENT);
            //招标核准批复附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_TWO"),FileCodeEnum.APPROVAL_OF_BIDDING_APPENDIX);
        }

        //征地调查
        if ("PM_LAND_EXAMINE_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //调查文件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.INVESTIGATION_DOCUMENTS);
            //核定界限材料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_TWO"),FileCodeEnum.APPROVED_BOUNDARY_MATERIALS);
            //组卷材料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_THREE"),FileCodeEnum.COILING_MATERIALS);
            //征地调查批复材料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_FOUR"),FileCodeEnum.LAND_ACQUISITION_INVESTIGATION_APPROVAL_MATERIALS);
        }

        //土地证办理
        if ("PM_LAND_CERTIFICATE".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //土地划拨决定书
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.LAND_ALLOCATION_DECISION);
            //土地证
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.LAND_CERTIFICATE);
        }

        //人防规划报建
        if ("PM_DEFENSE_PLAN_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //人防规划核准申请附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.CIVIL_AIR_DEFENSE_PLANNING_APPLICATION);
            //人防组卷材料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_TWO"),FileCodeEnum.CIVIL_AIR_DEFENSE_ROLL_FORMING_MATERIALS);
            //人防规划批复材料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_THREE"),FileCodeEnum.CIVIL_AIR_DEFENSE_PLANNING_APPROVAL_MATERIALS);
        }

        //人防施工报建
        if ("PM_DEFENSE_BUILD_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //人防施工报建资料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.CIVIL_AIR_DEFENSE_CONSTRUCTION_APPLICATION_DATA);
            //人防施工踏勘意见书
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.OPINIONS_ON_CIVIL_AIR_DEFENSE_CONSTRUCTION_SURVEY);
            //人防施工资料修编版
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_TWO"),FileCodeEnum.CIVIL_AIR_DEFENSE_CONSTRUCTION_DATA_REVISION);
            //人防施工申请资料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_THREE"),FileCodeEnum.CIVIL_AIR_DEFENSE_CONSTRUCT_APP_MATERIAL);
            //人防施工报建备案
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"KEEP_RECORD_FILE"),FileCodeEnum.CIVIL_AIR_DEFENSE_CONSTRUCT_APP_FILING);
        }

        //国安批复文件
        if ("PM_NATIONAL_BUILD_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //国安报建方案
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.NATIONAL_SECURITY_APPLICATION_PLAN);
            //国安批复文件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.NATIONAL_SECURITY_APPROVAL_DOCUMENT);
        }

        //白蚁防治
        if ("PM_TERMITE_CONTROL_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //白蚁防治方案
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.TERMITE_CONTROL_PLAN);
            //白蚁防治方案备案
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.TERMITE_CONTROL_PLAN_FILING);
            //白蚁防治实施附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_TWO"),FileCodeEnum.TERMITE_CONTROL_IMPLEMENT_ATTACHMENT);
            //白蚁防治验收附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_THREE"),FileCodeEnum.TERMITE_CONTROL_ACCEPTANCE_ATTACHMENT);
        }

        //耕作层剥离
        if ("PM_TOPSOIL_STRIPPING_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //耕作层剥离审核附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.TOPSOIL_STRIP_REVIEW_ATTACHMENT);
            //耕作层剥离方案编制成果
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.TOPSOIL_STRIP_PLAN_PREPARATION_RESULTS);
            //耕作层剥离实施评审稿
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_TWO"),FileCodeEnum.TOPSOIL_STRIP_IMPLEMENT_REVIEW_DRAFT);
            //耕作层剥离专家意见
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_THREE"),FileCodeEnum.TOPSOIL_STRIPPING_EXPERT_OPINION);
            //耕作层剥离备案
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_FOUR"),FileCodeEnum.FARMING_LAYER_STRIPPING_FILING);
            //耕作层剥离验收
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_FIVE"),FileCodeEnum.TOPSOIL_STRIPPING_ACCEPTANCE);
        }

        //五方责任主体及终身质量承诺书
        if ("PM_PRJ_PARTY_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //建设单位承诺书
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.EMPLOYER_COMMITMENT_LETTER);
            //代建单位承诺书
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.AGENT_COMMITMENT_LETTER);
            //勘察单位承诺书
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_TWO"),FileCodeEnum.INVESTIGATION_COMMITMENT_LETTER);
            //设计单位承诺书
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_THREE"),FileCodeEnum.DESIGNER_COMMITMENT_LETTER);
            //设计单位承诺书
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_FOUR"),FileCodeEnum.PRJ_CONTRACTOR_COMMITMENT_LETTER);
            //施工总承包单位承诺书
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_FIVE"),FileCodeEnum.CONSTRUCTION_CONTRACTOR_COMMITMENT_LETTER);
            //监理单位承诺书
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_SIX"),FileCodeEnum.SUPERVISOR_COMMITMENT_LETTER);
        }

        //监理规划及细则申请
        if ("PM_SUPERVISE_PLAN_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //监理规划附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.SUPERVISION_PLANNING_ANNEX);
        }

        //技术交底与图纸会审记录
        if ("SKILL_DISCLOSURE_PAPER_RECHECK_RECORD".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"AMOUT_PM_PRJ_ID");
            //图纸会审附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.DRAWING_JOINT_REVIEW_ATTACHMENT);
        }

        //施工进度计划
        if ("PM_SUPERVISE_PLAN_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //施工进度计划附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.CONSTRUCTION_SCHEDULE_ATTACHMENT);
        }

        //施工组织设计及施工方案
        if ("PM_SUPERVISE_PLAN_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //施工组织设计附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.CONSTRUCTION_ORGANIZATION_DESIGN_ATTACHMENT);
        }

        //工作联系单
        if ("PM_WORK_LIST_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //工作联系单附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.WORK_CONTACT_LIST_ATTACHMENT);
        }

        //报审报验
        if ("APPROVAL_INSPECTION".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //报审报验附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.APPROVAL_INSPECTION_ATTACHMENT);
        }

        //工程索赔通知书
        if ("PROJECT_CLAIM_NOTICE".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //工程索赔通知书附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.PROJECT_CLAIM_NOTICE_ATTACHMENTS);
        }

        //费用索赔报审表
        if ("EXPENSE_CLAIM_APPROVAL".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //费用索赔报审表附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.EXPENSE_CLAIM_APPROVAL_FORM);
        }

        //竣工预验收
        if ("COMPLETION_PRE_ACCEPTANCE".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //竣工预验收附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.COMPLETION_PRE_ACCEPTANCE_ATTACHMENT);
        }

        //质量交底记录
        if ("QUALITY_RECORD".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //质量交底记录附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.QUALITY_RECORD_ATTACHMENT);
        }

        //工程质量检查
        if ("PROJECT_QUALITY_INSPECTION".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //工程质量检查材料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.PROJECT_QUALITY_INSPECTION_MATERIALS);
        }

        //科研材料设备进场验收
        if ("SCIENTIFIC_MATERIAL_CHECK".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //科研材料设备进场验收材料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.SCIENTIFIC_MATERIAL_CHECK_ATTACHMENT);
        }

        //合同品牌变更申请
        if ("CONTRACT_BRAND_CHANGE_APPLICATION".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //合同品牌变更申请附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.CONTRACT_BRAND_CHANGE_APPLICATION_ATTACHMENT);
        }

        //工程材料设备及品牌报审
        if ("MATERIAL_EQUIPMENT_BRAND_APPROVAL".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //工程材料设备及品牌报审附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.MATERIAL_EQUIPMENT_BRAND_APPROVAL_ATTACHMENT);
        }

        //工程材料设备进场验收
        if ("MATERIAL_EQUIPMENT_ENTER_CHECK".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //工程材料设备进场验收附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.MATERIAL_EQUIPMENT_ENTER_CHECK_ATTACHMENT);
        }

        //材料退场
        if ("PM_MATERIAL_EXIT".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //材料退场附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.PM_MATERIAL_EXIT_ATTACHMENT);
        }

        //预算财评
        if ("BUDGET_MONEY_RATING".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //财评申报材料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"BUDGETESTIMATEDECLARATION_FILE"),FileCodeEnum.APPLICATION_MATERIALS);
            //施工图预算送审稿
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"WORK_DRAWING_TO_REVIEW_FILE"),FileCodeEnum.WORK_DRAWING_TO_REVIEW_FILE);
            //预算审核结论书
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"BUDGET_REVIEW_CONCLUSION_FILE"),FileCodeEnum.BUDGET_REVIEW_CONCLUSION_FILE);
            //财评批复
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"FINANCIAL_REVIEW_FILE"),FileCodeEnum.FINANCIAL_REVIEW_FILE);
        }

        //工程付款申请
        if ("BUDGET_MONEY_RATING".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"AMOUT_PM_PRJ_ID");
            //工程付款保函内容
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.PROJECT_PAYMENT_APPLICATION);
            //工程付款内容
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"SUBJECT_FILE"),FileCodeEnum.PROJECT_PAYMENT_CONTENT_ATTACHMENT);
            //工程付款保函内容
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"GUARANTEE_RESULT_FILE"),FileCodeEnum.PROJECT_PAYMENT_GUARANTEE_CONTENTS);
        }

        //资金需求计划申请
        if("PM_FUND_REQUIRE_PLAN_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap, "AMOUT_PM_PRJ_ID");
            //资金需求计划批复文件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"REPLY_FILE"),FileCodeEnum.CAPITAL_DEMAND_PLAN_APPROVAL_DOCUMENT);
            //预算评审批复文件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"CONSERVATION_REPLY_FILE"),FileCodeEnum.BUDGET_REVIEW_APPROVAL_DOCUMENT);
            //施工中标通知书
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"BID_WIN_NOTICE_FILE_GROUP_ID"),FileCodeEnum.CONSTRUCTION_ACCEPTANCE_LETTER);
            //开工令附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.COMMENCEMENT_ORDER_ATTACHMENT);
            //正式合同附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"BID_AFTER_FILE_GROUP_ID"),FileCodeEnum.FORMAL_CONTRACT_ANNEX);
        }

        //新增保函申请
        if ("PO_GUARANTEE_LETTER_REQUIRE_REQ".equals(entityCode)){
            String projectId = getProjectId(valueMap);
            String[] arr = projectId.split(",");
            for (String tmp : arr) {
                //保函正式合同
                ProFileUtils.insertProFile(tmp,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.GUARANTEE_FORMAL_CONTRACT);
                //正式保函
                ProFileUtils.insertProFile(tmp,JdbcMapUtil.getString(valueMap,"GUARANTEE_FILE"),FileCodeEnum.FORMAL_GUARANTEE);
                //保函结果
                ProFileUtils.insertProFile(tmp,JdbcMapUtil.getString(valueMap,"GUARANTEE_RESULT_FILE"),FileCodeEnum.GUARANTEE_RESULT);
            }
//            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");

        }

        //保函退还申请
        if ("PO_GUARANTEE_LETTER_RETURN_OA_REQ".equals(entityCode)){
            String projectId = getProjectId(valueMap);
            String[] arr = projectId.split(",");
            for (String prjId : arr) {
                //保函退还附件
                ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"GUARANTEE_FILE"),FileCodeEnum.GUARANTEE_RETURN_LETTER_ANNEX);
            }
//            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
//            if(SharedUtil.isEmptyString(prjId)){
//                prjId = this.getWritePrjId(valueMap);
//            }
        }

        //用章审批
        if ("APPROVAL_WITH_SEAL".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //用章审批附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.APPROVAL_WITH_SEAL_ATTACHMENT);
        }

        //防洪评价
        if ("PM_CONTROL_FLOOD_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //防洪方案材料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.FLOOD_CONTROL_PLAN_MATERIALS);
            //防洪批复文件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_TWO"),FileCodeEnum.FLOOD_CONTROL_APPROVAL_DOCUMENTS);
        }

        //交通安全评价
        if ("PM_TRAFFIC_SAFETY_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //交通安全方案材料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.TRAFFIC_SAFETY_PLAN_MATERIALS);
            //交通安全方案备案
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_TWO"),FileCodeEnum.TRAFFIC_SAFETY_PLAN_FILING);
        }

        //土地划拨
        if ("PM_LAND_ALLOCATION_REQ".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //土地划拨调查报告
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.LAND_ALLOCATION_SURVEY_REPORT);
            //土地划拨决定书申请材料
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_TWO"),FileCodeEnum.LAND_ALLOCATION_DECISION_APPLICATION_MATERIALS);
            //土地划拨决定书批复
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_THREE"),FileCodeEnum.LAND_ALLOCATION_DECISION_REPLY);
        }

        //竣工联合验收意见
        if ("COMPLETION_ACCEPTANCE_COMMENTS".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
            //资料准备附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_ONE"),FileCodeEnum.ACCEPTANCE_PREPARE_ATTACHMENTS);
            //备案申报附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_TWO"),FileCodeEnum.COMPLETION_ACCEPTANCE_FILING_ATTACHMENT);
            //备案批复附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_THREE"),FileCodeEnum.FILING_APPROVAL_ATTACHMENT);
            //资料组卷附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_FOUR"),FileCodeEnum.DATA_VOLUME_ATTACHMENT);
            //联合意见申报
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_FIVE"),FileCodeEnum.JOINT_OPINION_DECLARATION_ATTACHMENT);
            //联合验收批复
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"APPROVE_FILE_ID_SIX"),FileCodeEnum.JOINT_ACCEPTANCE_APPROVAL_ATTACHMENT);
        }

        //分包单位资质报审
        if ("SUBCONTRACTOR_QUALIFICATION_REPORT".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            //资质报审附件
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"CONTRACT_FILE_GROUP_ID"),FileCodeEnum.QUALIFICATION_REPORT_ATTACHMENT);
        }

        //招标过程管理
        if ("BID_PROCESS_MANAGE".equals(entityCode)){
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            if(SharedUtil.isEmptyString(prjId)){
                prjId = this.getWritePrjId(valueMap);
            }
            //中标通知书
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"BID_WIN_NOTICE_FILE_GROUP_ID"),FileCodeEnum.MANAGEMENT_BID_WINNING_NOTICE);
            //备案回执
            ProFileUtils.insertProFile(prjId,JdbcMapUtil.getString(valueMap,"ATT_FILE_GROUP_ID"),FileCodeEnum.MANAGEMENT_FILING_RECEIPT);
        }
    }


    /**
     * 获取流程启动用户的部门的负责人。
     */
    public void getDeptChief() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            getDeptChief(entityRecord);
        }
    }

    //获取填写项目的id
    private String getWritePrjId(Map<String, Object> valueMap){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String name = JdbcMapUtil.getString(valueMap,"PROJECT_NAME_WR");
        String PROJECT_SOURCE_TYPE_ID = JdbcMapUtil.getString(valueMap,"PROJECT_SOURCE_TYPE_ID");
        List<Map<String,Object>> list = myJdbcTemplate.queryForList("select id from pm_prj where name = ? and PROJECT_SOURCE_TYPE_ID = ?",name,PROJECT_SOURCE_TYPE_ID);
        if (CollectionUtils.isEmpty(list)){
            return PmPrjReqExt.getPrjId(valueMap);
        } else {
            return JdbcMapUtil.getString(list.get(0),"id");
        }
    }

    private void getDeptChief(EntityRecord entityRecord) {
        String csCommId = entityRecord.csCommId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        String procInstId = ExtJarHelper.procInstId.get();

        Object START_USER_ID = Crud.from("wf_process_instance").where().eq("id", procInstId).select().specifyCols(
                "START_USER_ID").execForValue();

        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select d.chief_user_id from hr_dept_user du join hr_dept d on du.HR_DEPT_ID=d.id and du.AD_USER_ID=? limit 1", START_USER_ID);
        if (CollectionUtils.isEmpty(list) || list.get(0).get("chief_user_id") == null) {
            throw new BaseException("启动用户没有对应的部门负责人！");
        } else if (list.size() > 1) {
            throw new BaseException("启动用户不能对应" + list.size() + "个部门负责人！");
        }

        String chief_user_id = list.get(0).get("chief_user_id").toString();

        ArrayList<Object> userIdList = new ArrayList<>(1);
        userIdList.add(chief_user_id);
        ExtJarHelper.returnValue.set(userIdList);

    }

    /**
     * 根据流程实例id获取文件id组
     * @param procInstId 流程实例id
     * @return
     */
    private String getProcessFileByProcInstId(String procInstId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> fileIdMap = myJdbcTemplate.queryForMap("select GROUP_CONCAT(t.USER_ATTACHMENT) " +
                "fileIds from wf_process_instance i left join wf_task t on t.WF_PROCESS_INSTANCE_ID = i.id where i.id" +
                " = ? and t.USER_ATTACHMENT is not null", procInstId);
        if (CollectionUtils.isEmpty(fileIdMap)) {
            return null;
        }
        String fileIds = JdbcMapUtil.getString(fileIdMap, "fileIds");
        return fileIds;
    }

    /**
     * 根据流程实例id 返回根据节点分组的附件id字符串组 根据节点顺序排序后
     * @param procInstId 流程实例id
     * @return
     */
    private List<Map<String, Object>> getProcessFileGroupByNode(String procInstId){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> fileIdsList = myJdbcTemplate.queryForList("select n.name,n.id,GROUP_CONCAT(t.USER_ATTACHMENT) fileIds " +
                "from wf_node_instance ni " +
                "left join wf_task t on ni.id=t.wf_node_instance_id " +
                "left join wf_node n on n.id = ni.WF_NODE_ID " +
                "where ni.wf_process_instance_id=? " +
                "GROUP BY n.id " +
                "order by n.name ", procInstId);
//        List<String> fileIds = fileIdsList.stream().map(fileIdsMap -> JdbcMapUtil.getString(fileIdsMap,"fileIds")).collect(Collectors.toList());
        return fileIdsList;
    }

    private List<String> getAmtPrjList() {
        List<String> list = new ArrayList<>();
        list.add("PO_ORDER_PAYMENT_REQ"); // 采购合同付款申请
        list.add("PM_FUND_REQUIRE_PLAN_REQ"); // 资金需求计划申请
        list.add("PM_DESIGN_ASSIGNMENT_BOOK"); // 设计任务书
        list.add("SKILL_DISCLOSURE_PAPER_RECHECK_RECORD"); // 技术交底与图纸会审记录
        return list;
    }

    /** 没有项目的流程 **/
    private List<String> getNoProjectList(){
        List<String> list = new ArrayList<>();
        list.add("CONSULTATION_REQ"); // 意见征询
        return list;
    }

    /** 没有项目的流程 **/
    public static List<String> getNoProjectStaticList(){
        List<String> list = new ArrayList<>();
        list.add("CONSULTATION_REQ"); // 意见征询
        return list;
    }

    //一些特殊流程，发起后即结束流程。
    public List<String> getEndProcessList() {
        List<String> list = new ArrayList<>();
        list.add("PM_FARMING_PROCEDURES"); //农转用手续办理
        list.add("PM_EARTHWORK_PRJ_REQ"); //土石方工程施工
        list.add("PM_BUILD_FOUNDATION_REQ"); //基坑支护工程施工
        list.add("PM_BUILD_PILE_REQ"); //桩基工程施工
        list.add("PM_BUILD_PLUS_MINUS_REQ"); //正负零施工
        list.add("PM_SUBJECT_CAPPING_REQ"); //主体结构封顶
        list.add("PM_SECOND_STRUCTURAL_REQ"); //二次结构工程
        list.add("PM_ROOFING_REQ"); //屋面工程
        list.add("PM_WAIL_REQ"); //幕墙工程
        list.add("PM_ELECTROMECHANICAL_REQ"); //机电
        list.add("PM_INTERIOR_DECORATION_REQ"); //室内精装工程
        list.add("PM_BUILD_INTELLIGENT_REQ"); //建筑智能化安装工程
        list.add("PM_ELEVATOR_INSTALL_REQ"); //电梯安装工程
        list.add("PM_FLOOD_LIGHTING_REQ"); //泛光照明工程
        list.add("PM_MUNICIPAL_MATCHED_REQ"); //配套市政工程
        list.add("PM_SIGN_PRJ_REQ"); //标识牌工程
        list.add("PM_LANDSCAPE_REQ"); //园林景观工程
        list.add("PM_ORGAN_SYS_DEBUG_REQ"); //组织系统联合调试
        list.add("PM_HOUSE_CADASTRE_REQ"); //房屋、地籍实测
        return list;
    }

    // 特殊流程，名称在流程发起时即生成，此处不生成
    public List<String> getSpecialList() {
        List<String> list = new ArrayList<>();
        list.add("PM_BUY_DEMAND_REQ"); //采购需求审批
        list.add("PM_BID_APPROVAL_REQ"); //招标文件审批
        list.add("PM_USE_CHAPTER_REQ"); //中选单位及标后用印申请
        list.add("PO_ORDER_REQ"); //合同签订
//        list.add("PM_PRJ_REQ"); // 立项申请
        list.add("PM_SUPERVISE_PLAN_REQ"); // 监理规划及细则申请
        list.add("QUALITY_RECORD"); // 质量交底记录
        list.add("PM_SUPERVISE_NOTICE_REQ"); // 监理通知单
        return list;
    }

    // 特殊流程，项目会多选
    public List<String> getMorePrjList() {
        List<String> list = new ArrayList<>();
        list.add("PO_GUARANTEE_LETTER_REQUIRE_REQ"); //新增保函
        list.add("PO_GUARANTEE_LETTER_RETURN_OA_REQ"); //保函退还申请(OA)
        return list;
    }

    /**
     * 流程标题生成
     * @param entityCode 流程表名
     * @param entityRecord 表单数据
     * @param myJdbcTemplate 数据源
     */
    public static void createProcessTitle(String entityCode, EntityRecord entityRecord, MyJdbcTemplate myJdbcTemplate) {
        String processName = "", nowDate = "",userName = "",projectName = "",otherName = "",name = "",sql = "";
        String csCommId = entityRecord.csCommId;

        String sql0 = "SELECT c.name as processName,b.IS_URGENT,b.START_DATETIME," +
                "(select name from ad_user where id = b.START_USER_ID) as userName FROM "+entityCode+" a " +
                "LEFT JOIN wf_process_instance b on a.LK_WF_INST_ID = b.id LEFT JOIN wf_process c on b.WF_PROCESS_ID = c.id WHERE a.id = ?";
        List<Map<String,Object>> list0 = myJdbcTemplate.queryForList(sql0,csCommId);
        if (!CollectionUtils.isEmpty(list0)){
            processName = JdbcMapUtil.getString(list0.get(0),"processName");
            nowDate = JdbcMapUtil.getString(list0.get(0),"START_DATETIME").replace("T"," ");
            userName = JdbcMapUtil.getString(list0.get(0),"userName");
        }

        //查询该实例紧急程度
        String urgentSql = "SELECT a.IS_URGENT FROM WF_PROCESS_INSTANCE a left join "+entityCode+" b on a.id = b.LK_WF_INST_ID where b.id = ? ";
        List<Map<String,Object>> urgentList = myJdbcTemplate.queryForList(urgentSql,csCommId);
        String urgent = "";
        if (!CollectionUtils.isEmpty(urgentList)){
            String urgentType = JdbcMapUtil.getString(urgentList.get(0),"IS_URGENT");
            if ("1".equals(urgentType)){
                urgent = "【紧急】";
            }
        }
        //判断是否该流程是否有项目信息
        List<String> noProjectList = getNoProjectStaticList();
        if (noProjectList.contains(entityCode)){
            sql = "update wf_process_instance pi join " + entityCode + " t on pi.ENTITY_RECORD_ID = t.id set pi.name = ? where t.id = ?";
            String title = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_THREE");
            name = concatProcessNameStatic("-",processName,title,userName,nowDate);
            myJdbcTemplate.update(sql, name,csCommId);
            return;
        }

        String sql2 = "SELECT (select name from pm_prj where id = a.PM_PRJ_ID) as projectName FROM "+entityCode+" a WHERE a.id = ?";
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,csCommId);
        if (!CollectionUtils.isEmpty(list2)){
            projectName = JdbcMapUtil.getString(list2.get(0),"projectName");
            if (SharedUtil.isEmptyString(projectName)){
                projectName = getProjectNameStatic(myJdbcTemplate,entityRecord);
            }
            processName = urgent + processName;
        }

        //合同流程标题规则
        List<String> orderNameTable = AttLinkDifferentProcess.getOrderProcessName();

        // 流程名称按规定创建

        // 特殊流程 更新流程内name字段
        List<String> specialList = AttLinkDifferentProcess.getSpecialList();
        if (specialList.contains(entityCode)) {
            if ("PM_BUY_DEMAND_REQ".equals(entityCode)){ //采购需求审批
                sql = "select b.name from PM_BUY_DEMAND_REQ a left join gr_set_value b on a.BUY_MATTER_ID = b.id where a.id = ?";
                List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,csCommId);
                if (!CollectionUtils.isEmpty(list)){
                    otherName = JdbcMapUtil.getString(list.get(0),"name");
                }
                name = concatProcessNameStatic("-",processName,projectName,otherName,userName,nowDate);
            } else if ("PO_ORDER_REQ".equals(entityCode)){ //合同签订
                otherName = getContractNameStatic(entityCode,"CONTRACT_NAME",csCommId,myJdbcTemplate);
                name = concatProcessNameStatic("-",processName,projectName,otherName,userName,nowDate);
            } else if ("PM_BID_APPROVAL_REQ".equals(entityCode)) { //招标文件审批
                otherName = getContractNameStatic(entityCode,"NAME_ONE",csCommId,myJdbcTemplate);
                name = concatProcessNameStatic("-",processName,otherName,userName,nowDate);
            } else {
                if ("PM_SUPERVISE_PLAN_REQ".equals(entityCode)){
                    otherName = JdbcMapUtil.getString(entityRecord.valueMap,"REMARK_ONE");
                } else if ("QUALITY_RECORD".equals(entityCode)){
                    otherName = JdbcMapUtil.getString(entityRecord.valueMap,"REMARK_ONE");
                } else if ("PM_SUPERVISE_NOTICE_REQ".equals(entityCode)){
                    otherName = JdbcMapUtil.getString(entityRecord.valueMap,"CODE_ONE");
                } else {
                    otherName = getContractNameStatic(entityCode,"NAME_ONE",csCommId,myJdbcTemplate);
                }
                name = concatProcessNameStatic("-",processName,otherName,projectName,userName,nowDate);
            }
            if ("PM_SUPERVISE_NOTICE_REQ".equals(entityCode)){
                myJdbcTemplate.update("update "+entityCode+" set name = ? where id = ?", otherName,csCommId);
            } else {
                myJdbcTemplate.update("update "+entityCode+" set name = ? where id = ?", name,csCommId);
            }
            myJdbcTemplate.update("update wf_process_instance pi join " + entityCode + " t on pi.ENTITY_RECORD_ID = t.id set pi.name = ? where t.id = ?",name,csCommId);
        }  else if (orderNameTable.contains(entityCode)){ //补充协议/合同需求审批/合同终止 流程标题规则
            otherName = getContractNameStatic(entityCode,"CONTRACT_NAME",csCommId,myJdbcTemplate);
            name = concatProcessNameStatic("-",processName,projectName,otherName,userName,nowDate);
            myJdbcTemplate.update("update wf_process_instance pi join " + entityCode + " t on pi.ENTITY_RECORD_ID = t.id set pi.name = ? where t.id = ?",name,csCommId);
        } else {
            sql = "update wf_process_instance pi join " + entityCode + " t on pi.ENTITY_RECORD_ID = t.id set pi.name = ? where t.id = ?";
            name = concatProcessNameStatic("-",processName,projectName,userName,nowDate);
            myJdbcTemplate.update(sql, name,csCommId);
        }
    }
}
