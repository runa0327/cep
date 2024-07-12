package com.bid.ext.cc;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bid.ext.model.*;
import com.bid.ext.utils.JsonUtil;
import com.bid.ext.utils.ProcessCommon;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.sun.org.apache.bcel.internal.generic.I2F;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellType.BLANK;

public class ZJCriticalPrjExt {

    //进入方案报审阶段
    public void enterProposalApprovalStep() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        String ctUserId = ExtJarHelper.getLoginInfo().userInfo.id;


        for( EntityRecord  entityRecord : entityRecordList) {

            Map<String, Object> valueMap = entityRecord.valueMap;

            String criticalPlanId = (String) valueMap.get("ID");

            String proposalApprovalHeadId = (String) valueMap.get("CC_PROPOSAL_APPROVAL_HEAD_ID");
            String tripartiteTestingHeadId = (String) valueMap.get("CC_TRIPARTITE_TESTING_HEAD_ID");

            List<String>  ids =  new ArrayList<>();
            if(!StringUtils.hasText(proposalApprovalHeadId)){
                throw new BaseException("方案报审责任人未选择！");
            }
            ids.add(proposalApprovalHeadId);
            if(StringUtils.hasText(tripartiteTestingHeadId)){
                if (!proposalApprovalHeadId.equals(tripartiteTestingHeadId)) {
                    ids.add(tripartiteTestingHeadId);
                }
            }

            List<CcPrjMember> ccPrjMembers = CcPrjMember.selectByIds(ids);
            StringBuffer taskUserIds =new StringBuffer( "");//待办人员

            for(int i= 0 ; i<ccPrjMembers.size() ; i++){//待办人id拼接
                if (i==ccPrjMembers.size()-1) {
                    taskUserIds.append(ccPrjMembers.get(0).getAdUserId());
                }else{
                    taskUserIds.append(ccPrjMembers.get(0).getAdUserId());
                    taskUserIds.append(",");
                }
            }

            String entCode = "CC_CRITICAL_PRJ_PLAN"; //危大工程方案表
            String processId = "1811280773218377728";//流程定义id

            CcCriticalPrjPlan ccCriticalPrjPlan = CcCriticalPrjPlan.selectById(criticalPlanId);//危大工程记录

            if (ccCriticalPrjPlan==null){
                throw new BaseException("危大工程数据id错误！");
            }
            if (!("0".equals(ccCriticalPrjPlan.getCcCriticalPrjImplPhaseId()) || "1".equals(ccCriticalPrjPlan.getCcCriticalPrjImplPhaseId()))){
                CcCriticalPrjImplPhase ccCriticalPrjImplPhase = CcCriticalPrjImplPhase.selectById(ccCriticalPrjPlan.getCcCriticalPrjImplPhaseId());//获取当前所处阶段
                throw new BaseException("当前阶段为'"+ JsonUtil.getCN(ccCriticalPrjImplPhase.getName())+"'，不可进入'方案报审阶段'！");
            }

           String now =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            String wfProcessInstanceId = null;//流程实例id
            String lkWfInstId = ccCriticalPrjPlan.getLkWfInstId();//获取绑定的流程实例
            if (lkWfInstId==null){//未绑定，发起新的流程
                 wfProcessInstanceId = Crud.from("WF_PROCESS_INSTANCE").insertData();
                //暂存流程
                ProcessCommon.autoSaveProcess(null,entCode,ctUserId,wfProcessInstanceId,processId,criticalPlanId, now,taskUserIds.toString());
                ccCriticalPrjPlan.setLkWfInstId(wfProcessInstanceId);
            }else{//已绑定

                Where   queryNodeInstance = new Where();
                queryNodeInstance.sql("T.STATUS='AP' AND  T.WF_PROCESS_INSTANCE_ID = '"+lkWfInstId+"' AND T.IS_CURRENT=1");
                WfNodeInstance wfNodeInstance = WfNodeInstance.selectOneByWhere(queryNodeInstance);//获取当前流程节点
                String nodeInstanceId = wfNodeInstance.getId();

                Where   queryTask = new Where();
                queryTask.sql("T.STATUS='AP' AND  T.WF_PROCESS_INSTANCE_ID = '"+lkWfInstId+"' AND T.IS_CLOSED=0 AND T.WF_NODE_INSTANCE_ID='"+nodeInstanceId+"'");
                List<WfTask> wfTasks = WfTask.selectByWhere(queryTask);
                for (WfTask task : wfTasks){//关闭之前的任务
                    task.setIsClosed(true);
                    task.setStatus("VD");
                    task.updateById();
                }
                ProcessCommon.createTask(lkWfInstId,nodeInstanceId,wfNodeInstance.getWfProcessId(),wfNodeInstance.getWfNodeId(),taskUserIds.toString(),ctUserId,now,null,null,"0","1","0");
            }

            //设置为方案报审阶段状态
            ccCriticalPrjPlan.setCcCriticalPrjImplPhaseId("1");
            //更新
            ccCriticalPrjPlan.updateById();

        }

    }


    //进入方实施前交底
    public void enterImplDiscloseStep() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        String ctUserId = ExtJarHelper.getLoginInfo().userInfo.id;

        for( EntityRecord  entityRecord : entityRecordList) {

            Map<String, Object> valueMap = entityRecord.valueMap;

            String criticalPlanId = (String) valueMap.get("ID");

            String specialProgramDiscloseHeadId = (String) valueMap.get("CC_SPECIAL_PROGRAM_DISCLOSE_HEAD_ID");
            String securityTechDiscloseHeadId = (String) valueMap.get("CC_SECURITY_TECH_DISCLOSE_HEAD_ID");
            String teamSecurityEduHeadId = (String) valueMap.get("CC_TEAM_SECURITY_EDU_HEAD_ID");
            String supervisionRegulationDiscloseHeadId = (String) valueMap.get("CC_SUPERVISION_REGULATION_DISCLOSE_HEAD_ID");

            if (!StringUtils.hasText(specialProgramDiscloseHeadId) && !StringUtils.hasText(securityTechDiscloseHeadId)&& !StringUtils.hasText(teamSecurityEduHeadId)&& !StringUtils.hasText(supervisionRegulationDiscloseHeadId)){
                return;
            }

                List<String>  ids =  new ArrayList<>();
            if(StringUtils.hasText(specialProgramDiscloseHeadId)){ //专项方案交底责任人
                ids.add(specialProgramDiscloseHeadId);
            }

            if(StringUtils.hasText(securityTechDiscloseHeadId)){
                if (!securityTechDiscloseHeadId.equals(specialProgramDiscloseHeadId)) { //安全技术交底责任人
                    ids.add(securityTechDiscloseHeadId);
                }
            }
            if(StringUtils.hasText(teamSecurityEduHeadId)){
                if (!teamSecurityEduHeadId.equals(specialProgramDiscloseHeadId) && !teamSecurityEduHeadId.equals(securityTechDiscloseHeadId)) { //班组安全教育责任人
                    ids.add(teamSecurityEduHeadId);
                }
            }
            if(StringUtils.hasText(supervisionRegulationDiscloseHeadId)){
                if (!supervisionRegulationDiscloseHeadId.equals(specialProgramDiscloseHeadId)&& !supervisionRegulationDiscloseHeadId.equals(securityTechDiscloseHeadId)
                && !supervisionRegulationDiscloseHeadId.equals(teamSecurityEduHeadId)) { //监理细则交底责任人
                    ids.add(supervisionRegulationDiscloseHeadId);
                }
            }

            List<CcPrjMember> ccPrjMembers = CcPrjMember.selectByIds(ids);
            StringBuffer taskUserIds =new StringBuffer( "");//待办人员

            for(int i= 0 ; i<ccPrjMembers.size() ; i++){//待办人id拼接
                if (i==ccPrjMembers.size()-1) {
                    taskUserIds.append(ccPrjMembers.get(i).getAdUserId());
                }else{
                    taskUserIds.append(ccPrjMembers.get(i).getAdUserId());
                    taskUserIds.append(",");
                }
            }


            String entCode = "CC_CRITICAL_PRJ_PLAN"; //危大工程方案表
            String processId = "1811696594000285696";//流程定义id

            CcCriticalPrjPlan ccCriticalPrjPlan = CcCriticalPrjPlan.selectById(criticalPlanId);//危大工程记录

            if (ccCriticalPrjPlan==null){
                throw new BaseException("危大工程数据id错误！");
            }
            if (!("1".equals(ccCriticalPrjPlan.getCcCriticalPrjImplPhaseId()) || "2".equals(ccCriticalPrjPlan.getCcCriticalPrjImplPhaseId()))){
                CcCriticalPrjImplPhase ccCriticalPrjImplPhase = CcCriticalPrjImplPhase.selectById(ccCriticalPrjPlan.getCcCriticalPrjImplPhaseId());//获取当前所处阶段
                throw new BaseException("当前阶段为'"+ JsonUtil.getCN(ccCriticalPrjImplPhase.getName())+"'，不可进入'实施前交底'！");
            }

            String now =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


            String wfProcessInstanceId = null;//流程实例id
            String lkWfInstId = ccCriticalPrjPlan.getLkWfInstId();//获取绑定的流程实例

            if (lkWfInstId==null){//未绑定,发起新的流程
                wfProcessInstanceId = Crud.from("WF_PROCESS_INSTANCE").insertData();
                //暂存流程
                ProcessCommon.autoSaveProcess(null,entCode,ctUserId,wfProcessInstanceId,processId,criticalPlanId, now,taskUserIds.toString());
                ccCriticalPrjPlan.setLkWfInstId(wfProcessInstanceId);
            }else {//已绑定或其他阶段流程，

                WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);
                if (processId.equals(wfProcessInstance.getWfProcessId())) {
                    Where queryNodeInstance = new Where();
                    queryNodeInstance.sql("T.STATUS='AP' AND  T.WF_PROCESS_INSTANCE_ID = '" + lkWfInstId + "' AND T.IS_CURRENT=1");
                    WfNodeInstance wfNodeInstance = WfNodeInstance.selectOneByWhere(queryNodeInstance);//获取当前流程节点
                    String nodeInstanceId = wfNodeInstance.getId();

                    Where queryTask = new Where();
                    queryTask.sql("T.STATUS='AP' AND  T.WF_PROCESS_INSTANCE_ID = '" + lkWfInstId + "' AND T.IS_CLOSED=0 AND T.WF_NODE_INSTANCE_ID='" + nodeInstanceId + "'");
                    List<WfTask> wfTasks = WfTask.selectByWhere(queryTask);
                    for (WfTask task : wfTasks) {//关闭之前的任务
                        task.setIsClosed(true);
                        task.setStatus("VD");
                        task.updateById();
                    }
                    ProcessCommon.createTask(lkWfInstId, nodeInstanceId, wfNodeInstance.getWfProcessId(), wfNodeInstance.getWfNodeId(), taskUserIds.toString(), ctUserId, now, null, null, "0", "1", "0");

                }else{

                    //-------结束之前的流程------
                    wfProcessInstance.setEndDatetime(LocalDateTime.now());
                    wfProcessInstance.updateById();

                    Where queryNodeInstance = new Where();
                    queryNodeInstance.sql("T.STATUS='AP' AND  T.WF_PROCESS_INSTANCE_ID = '" + lkWfInstId + "' AND T.IS_CURRENT=1");
                    WfNodeInstance wfNodeInstance = WfNodeInstance.selectOneByWhere(queryNodeInstance);//获取当前流程节点


                    String wfNodeId = wfNodeInstance.getWfNodeId();
                    WfNode wfNode = WfNode.selectById(wfNodeId);
                    if ("START_EVENT".equals(wfNode.getNodeType())){
                        wfNodeInstance.setIsCurrent(false);
                        wfNodeInstance.setEndDatetime(LocalDateTime.now());
                        wfNodeInstance.updateById();
                    }
                    Where queryTask = new Where();
                    queryTask.sql("T.STATUS='AP' AND  T.WF_PROCESS_INSTANCE_ID = '" + lkWfInstId + "' AND T.IS_CLOSED=0 AND T.WF_NODE_INSTANCE_ID='" + wfNodeInstance.getId() + "'");
                    List<WfTask> wfTasks = WfTask.selectByWhere(queryTask);
                    for (WfTask task : wfTasks) {//关闭之前的任务
                        task.setIsClosed(true);
                        task.setStatus("VD");
                        task.updateById();
                    }
                    WfNode endNode = WfNode.selectOneByWhere(new Where().eq(WfNode.Cols.WF_PROCESS_ID,wfProcessInstance.getWfProcessId()).eq(WfNode.Cols.NODE_TYPE,"END_EVENT").eq(WfNode.Cols.STATUS,"AP")); //结束节点信息
                    ProcessCommon.createWfNodeInstance(wfProcessInstance.getId(),wfNodeId,JsonUtil.getCN(endNode.getName()),wfProcessInstanceId,now,null,null,null,ctUserId,"1","1",processId); // 创建节点实例
                    //-------结束之前的流程------

                    //创建新的流程

                     wfProcessInstanceId = Crud.from("WF_PROCESS_INSTANCE").insertData();
                    //暂存流程
                    ProcessCommon.autoSaveProcess(null,entCode,ctUserId,wfProcessInstanceId,processId,criticalPlanId, now,taskUserIds.toString());
                    ccCriticalPrjPlan.setLkWfInstId(wfProcessInstanceId);
                }
            }

            //设置为方案报审阶段状态
            ccCriticalPrjPlan.setCcCriticalPrjImplPhaseId("2");
            //更新
            ccCriticalPrjPlan.updateById();
        }
    }

    //进入实施记录节点
    public void enterImplRecordStep() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        String ctUserId = ExtJarHelper.getLoginInfo().userInfo.id;

        for( EntityRecord  entityRecord : entityRecordList) {

            Map<String, Object> valueMap = entityRecord.valueMap;

            String criticalPlanId = (String) valueMap.get("ID");

            String implementationRecordDiscloseHeadId = (String) valueMap.get("CC_IMPLEMENTATION_RECORD_HEAD_ID");

            if (!StringUtils.hasText(implementationRecordDiscloseHeadId)){
                return;
            }

            List<String>  ids =  new ArrayList<>();
            if(StringUtils.hasText(implementationRecordDiscloseHeadId)){ //实施记录责任人
                ids.add(implementationRecordDiscloseHeadId);
            }

            List<CcPrjMember> ccPrjMembers = CcPrjMember.selectByIds(ids);
            StringBuffer taskUserIds =new StringBuffer( "");//待办人员

            for(int i= 0 ; i<ccPrjMembers.size() ; i++){//待办人id拼接
                if (i==ccPrjMembers.size()-1) {
                    taskUserIds.append(ccPrjMembers.get(i).getAdUserId());
                }else{
                    taskUserIds.append(ccPrjMembers.get(i).getAdUserId());
                    taskUserIds.append(",");
                }
            }

//            String implementedCheckAndAcceptHeadId = (String) valueMap.get("CC_IMPLEMENTED_CHECK_AND_ACCEPT_HEAD_ID");

            String entCode = "CC_CRITICAL_PRJ_PLAN"; //危大工程方案表
            String processId = "1811707809376243712";//流程定义id

            CcCriticalPrjPlan ccCriticalPrjPlan = CcCriticalPrjPlan.selectById(criticalPlanId);//危大工程记录

            if (ccCriticalPrjPlan==null){
                throw new BaseException("危大工程数据id错误！");
            }
            if (!("2".equals(ccCriticalPrjPlan.getCcCriticalPrjImplPhaseId()) || "3".equals(ccCriticalPrjPlan.getCcCriticalPrjImplPhaseId()))){
                CcCriticalPrjImplPhase ccCriticalPrjImplPhase = CcCriticalPrjImplPhase.selectById(ccCriticalPrjPlan.getCcCriticalPrjImplPhaseId());//获取当前所处阶段
                throw new BaseException("当前阶段为'"+ JsonUtil.getCN(ccCriticalPrjImplPhase.getName())+"'，不可进入'实施中记录'！");
            }

            String now =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            String wfProcessInstanceId = null;//流程实例id
            String lkWfInstId = ccCriticalPrjPlan.getLkWfInstId();//获取绑定的流程实例

            if (lkWfInstId==null){//未绑定,发起新的流程
                wfProcessInstanceId = Crud.from("WF_PROCESS_INSTANCE").insertData();
                //暂存流程
                ProcessCommon.autoSaveProcess(null,entCode,ctUserId,wfProcessInstanceId,processId,criticalPlanId, now,taskUserIds.toString());
                ccCriticalPrjPlan.setLkWfInstId(wfProcessInstanceId);
            }else {//已绑定或其他阶段流程，

                WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);
                if (processId.equals(wfProcessInstance.getWfProcessId())) {//是否为当前阶段流程id
                    Where queryNodeInstance = new Where();
                    queryNodeInstance.sql("T.STATUS='AP' AND  T.WF_PROCESS_INSTANCE_ID = '" + lkWfInstId + "' AND T.IS_CURRENT=1");
                    WfNodeInstance wfNodeInstance = WfNodeInstance.selectOneByWhere(queryNodeInstance);//获取当前流程节点
                    String nodeInstanceId = wfNodeInstance.getId();

                    Where queryTask = new Where();
                    queryTask.sql("T.STATUS='AP' AND  T.WF_PROCESS_INSTANCE_ID = '" + lkWfInstId + "' AND T.IS_CLOSED=0 AND T.WF_NODE_INSTANCE_ID='" + nodeInstanceId + "'");
                    List<WfTask> wfTasks = WfTask.selectByWhere(queryTask);
                    for (WfTask task : wfTasks) {//关闭之前的任务
                        task.setIsClosed(true);
                        task.setStatus("VD");
                        task.updateById();
                    }
                    ProcessCommon.createTask(lkWfInstId, nodeInstanceId, wfNodeInstance.getWfProcessId(), wfNodeInstance.getWfNodeId(), taskUserIds.toString(), ctUserId, now, null, null, "0", "1", "0");

                }else{

                    //-------结束之前的流程------
                    wfProcessInstance.setEndDatetime(LocalDateTime.now());
                    wfProcessInstance.updateById();

                    Where queryNodeInstance = new Where();
                    queryNodeInstance.sql("T.STATUS='AP' AND  T.WF_PROCESS_INSTANCE_ID = '" + lkWfInstId + "' AND T.IS_CURRENT=1");
                    WfNodeInstance wfNodeInstance = WfNodeInstance.selectOneByWhere(queryNodeInstance);//获取当前流程节点

                    String wfNodeId = wfNodeInstance.getWfNodeId();
                    WfNode wfNode = WfNode.selectById(wfNodeId);
                    if ("START_EVENT".equals(wfNode.getNodeType())){
                        wfNodeInstance.setIsCurrent(false);
                        wfNodeInstance.setEndDatetime(LocalDateTime.now());
                        wfNodeInstance.updateById();
                    }

                    Where queryTask = new Where();
                    queryTask.sql("T.STATUS='AP' AND  T.WF_PROCESS_INSTANCE_ID = '" + lkWfInstId + "' AND T.IS_CLOSED=0 AND T.WF_NODE_INSTANCE_ID='" + wfNodeInstance.getId() + "'");
                    List<WfTask> wfTasks = WfTask.selectByWhere(queryTask);
                    for (WfTask task : wfTasks) {//关闭之前的任务
                        task.setIsClosed(true);
                        task.setStatus("VD");
                        task.updateById();
                    }
                    WfNode endNode = WfNode.selectOneByWhere(new Where().eq(WfNode.Cols.WF_PROCESS_ID,wfProcessInstance.getWfProcessId()).eq(WfNode.Cols.NODE_TYPE,"END_EVENT").eq(WfNode.Cols.STATUS,"AP")); // 发起节点信息
                    ProcessCommon.createWfNodeInstance(wfProcessInstance.getId(),wfNodeId,JsonUtil.getCN(endNode.getName()),wfProcessInstanceId,now,null,null,null,ctUserId,"1","1",processId); // 创建节点实例
                    //-------结束之前的流程------


                    //创建新的流程
                    wfProcessInstanceId = Crud.from("WF_PROCESS_INSTANCE").insertData();
                    //暂存流程
                    ProcessCommon.autoSaveProcess(null,entCode,ctUserId,wfProcessInstanceId,processId,criticalPlanId, now,taskUserIds.toString());
                    ccCriticalPrjPlan.setLkWfInstId(wfProcessInstanceId);
                }
            }

            //设置为方案报审阶段状态
            ccCriticalPrjPlan.setCcCriticalPrjImplPhaseId("3");
            //更新
            ccCriticalPrjPlan.updateById();
        }
    }


    //进入实施后验收
    public void enterCheckAndAcceptStep() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        String ctUserId = ExtJarHelper.getLoginInfo().userInfo.id;

        for( EntityRecord  entityRecord : entityRecordList) {

            Map<String, Object> valueMap = entityRecord.valueMap;

            String criticalPlanId = (String) valueMap.get("ID");

            String checkAndAcceptHeadId = (String) valueMap.get("CC_IMPLEMENTED_CHECK_AND_ACCEPT_HEAD_ID");

            if (!StringUtils.hasText(checkAndAcceptHeadId)){
                return;
            }

            List<String>  ids =  new ArrayList<>();
            if(StringUtils.hasText(checkAndAcceptHeadId)){ //实施记录责任人
                ids.add(checkAndAcceptHeadId);
            }

            List<CcPrjMember> ccPrjMembers = CcPrjMember.selectByIds(ids);
            StringBuffer taskUserIds =new StringBuffer( "");//待办人员

            for(int i= 0 ; i<ccPrjMembers.size() ; i++){//待办人id拼接
                if (i==ccPrjMembers.size()-1) {
                    taskUserIds.append(ccPrjMembers.get(i).getAdUserId());
                }else{
                    taskUserIds.append(ccPrjMembers.get(i).getAdUserId());
                    taskUserIds.append(",");
                }
            }


            String entCode = "CC_CRITICAL_PRJ_PLAN"; //危大工程方案表
            String processId = "1811708279473836032";//流程定义id

            CcCriticalPrjPlan ccCriticalPrjPlan = CcCriticalPrjPlan.selectById(criticalPlanId);//危大工程记录

            if (ccCriticalPrjPlan==null){
                throw new BaseException("危大工程数据id错误！");
            }
            if (!("3".equals(ccCriticalPrjPlan.getCcCriticalPrjImplPhaseId()) || "4".equals(ccCriticalPrjPlan.getCcCriticalPrjImplPhaseId()))){
                CcCriticalPrjImplPhase ccCriticalPrjImplPhase = CcCriticalPrjImplPhase.selectById(ccCriticalPrjPlan.getCcCriticalPrjImplPhaseId());//获取当前所处阶段
                throw new BaseException("当前阶段为'"+ JsonUtil.getCN(ccCriticalPrjImplPhase.getName())+"'，不可进入'实施后验收'！");
            }

            String now =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            String wfProcessInstanceId = null;//流程实例id
            String lkWfInstId = ccCriticalPrjPlan.getLkWfInstId();//获取绑定的流程实例

            if (lkWfInstId==null){//未绑定,发起新的流程
                wfProcessInstanceId = Crud.from("WF_PROCESS_INSTANCE").insertData();
                //暂存流程
                ProcessCommon.autoSaveProcess(null,entCode,ctUserId,wfProcessInstanceId,processId,criticalPlanId, now,taskUserIds.toString());
                ccCriticalPrjPlan.setLkWfInstId(wfProcessInstanceId);
            }else {//已绑定或其他阶段流程，

                WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);
                if (processId.equals(wfProcessInstance.getWfProcessId())) {//是否为当前阶段流程id
                    Where queryNodeInstance = new Where();
                    queryNodeInstance.sql("T.STATUS='AP' AND  T.WF_PROCESS_INSTANCE_ID = '" + lkWfInstId + "' AND T.IS_CURRENT=1");
                    WfNodeInstance wfNodeInstance = WfNodeInstance.selectOneByWhere(queryNodeInstance);//获取当前流程节点
                    String nodeInstanceId = wfNodeInstance.getId();

                    Where queryTask = new Where();
                    queryTask.sql("T.STATUS='AP' AND  T.WF_PROCESS_INSTANCE_ID = '" + lkWfInstId + "' AND T.IS_CLOSED=0 AND T.WF_NODE_INSTANCE_ID='" + nodeInstanceId + "'");
                    List<WfTask> wfTasks = WfTask.selectByWhere(queryTask);
                    for (WfTask task : wfTasks) {//关闭之前的任务
                        task.setIsClosed(true);
                        task.setStatus("VD");
                        task.updateById();
                    }
                    ProcessCommon.createTask(lkWfInstId, nodeInstanceId, wfNodeInstance.getWfProcessId(), wfNodeInstance.getWfNodeId(), taskUserIds.toString(), ctUserId, now, null, null, "0", "1", "0");

                }else{

                    //-------结束之前的流程------
                    wfProcessInstance.setEndDatetime(LocalDateTime.now());
                    wfProcessInstance.updateById();

                    Where queryNodeInstance = new Where();
                    queryNodeInstance.sql("T.STATUS='AP' AND  T.WF_PROCESS_INSTANCE_ID = '" + lkWfInstId + "' AND T.IS_CURRENT=1");
                    WfNodeInstance wfNodeInstance = WfNodeInstance.selectOneByWhere(queryNodeInstance);//获取当前流程节点

                    String wfNodeId = wfNodeInstance.getWfNodeId();
                    WfNode wfNode = WfNode.selectById(wfNodeId);
                    if ("START_EVENT".equals(wfNode.getNodeType())){
                        wfNodeInstance.setIsCurrent(false);
                        wfNodeInstance.setEndDatetime(LocalDateTime.now());
                        wfNodeInstance.updateById();
                    }
                    Where queryTask = new Where();
                    queryTask.sql("T.STATUS='AP' AND  T.WF_PROCESS_INSTANCE_ID = '" + lkWfInstId + "' AND T.IS_CLOSED=0 AND T.WF_NODE_INSTANCE_ID='" + wfNodeInstance.getId() + "'");
                    List<WfTask> wfTasks = WfTask.selectByWhere(queryTask);
                    for (WfTask task : wfTasks) {//关闭之前的任务
                        task.setIsClosed(true);
                        task.setStatus("VD");
                        task.updateById();
                    }
                    WfNode endNode = WfNode.selectOneByWhere(new Where().eq(WfNode.Cols.WF_PROCESS_ID,wfProcessInstance.getWfProcessId()).eq(WfNode.Cols.NODE_TYPE,"END_EVENT").eq(WfNode.Cols.STATUS,"AP")); // 发起节点信息
                    ProcessCommon.createWfNodeInstance(wfProcessInstance.getId(),wfNodeId,JsonUtil.getCN(endNode.getName()),wfProcessInstanceId,now,null,null,null,ctUserId,"1","1",processId); // 创建节点实例
                    //-------结束之前的流程------

                    //创建新的流程
                    wfProcessInstanceId = Crud.from("WF_PROCESS_INSTANCE").insertData();
                    //暂存流程
                    ProcessCommon.autoSaveProcess(null,entCode,ctUserId,wfProcessInstanceId,processId,criticalPlanId, now,taskUserIds.toString());
                    ccCriticalPrjPlan.setLkWfInstId(wfProcessInstanceId);
                }
            }

            //设置为方案报审阶段状态
            ccCriticalPrjPlan.setCcCriticalPrjImplPhaseId("4");
            //更新
            ccCriticalPrjPlan.updateById();
        }
    }

    //验收完成
        public void completeCheckAndAcceptStep() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        String ctUserId = ExtJarHelper.getLoginInfo().userInfo.id;

        for( EntityRecord  entityRecord : entityRecordList) {

            Map<String, Object> valueMap = entityRecord.valueMap;

            String criticalPlanId = (String) valueMap.get("ID");

            CcCriticalPrjPlan ccCriticalPrjPlan = CcCriticalPrjPlan.selectById(criticalPlanId);//危大工程记录

            if (ccCriticalPrjPlan==null){
                throw new BaseException("危大工程数据id错误！");
            }

            if("5.".equals(ccCriticalPrjPlan.getCcCriticalPrjImplPhaseId())){
                return;
            }

            if (!"4".equals(ccCriticalPrjPlan.getCcCriticalPrjImplPhaseId())){
                CcCriticalPrjImplPhase ccCriticalPrjImplPhase = CcCriticalPrjImplPhase.selectById(ccCriticalPrjPlan.getCcCriticalPrjImplPhaseId());//获取当前所处阶段
                throw new BaseException("当前阶段为'"+ JsonUtil.getCN(ccCriticalPrjImplPhase.getName())+"'，不可完成验收！");
            }

            String now =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            String wfProcessInstanceId = null;//流程实例id
            String lkWfInstId = ccCriticalPrjPlan.getLkWfInstId();//获取绑定的流程实例

                WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(lkWfInstId);

                    //-------结束之前的流程------
                    wfProcessInstance.setEndDatetime(LocalDateTime.now());
                    wfProcessInstance.updateById();

                    Where queryNodeInstance = new Where();
                    queryNodeInstance.sql("T.STATUS='AP' AND  T.WF_PROCESS_INSTANCE_ID = '" + lkWfInstId + "' AND T.IS_CURRENT=1");
                    WfNodeInstance wfNodeInstance = WfNodeInstance.selectOneByWhere(queryNodeInstance);//获取当前流程节点

                    String wfNodeId = wfNodeInstance.getWfNodeId();
                    WfNode wfNode = WfNode.selectById(wfNodeId);
                    if ("START_EVENT".equals(wfNode.getNodeType())){
                        wfNodeInstance.setIsCurrent(false);
                        wfNodeInstance.setEndDatetime(LocalDateTime.now());
                        wfNodeInstance.updateById();
                    }
                    Where queryTask = new Where();
                    queryTask.sql("T.STATUS='AP' AND  T.WF_PROCESS_INSTANCE_ID = '" + lkWfInstId + "' AND T.IS_CLOSED=0 AND T.WF_NODE_INSTANCE_ID='" + wfNodeInstance.getId() + "'");
                    List<WfTask> wfTasks = WfTask.selectByWhere(queryTask);
                    for (WfTask task : wfTasks) {//关闭之前的任务
                        task.setIsClosed(true);
                        task.setStatus("VD");
                        task.updateById();
                    }
                    WfNode endNode = WfNode.selectOneByWhere(new Where().eq(WfNode.Cols.WF_PROCESS_ID,wfProcessInstance.getWfProcessId()).eq(WfNode.Cols.NODE_TYPE,"END_EVENT").eq(WfNode.Cols.STATUS,"AP")); // 发起节点信息
                    ProcessCommon.createWfNodeInstance(wfProcessInstance.getId(),wfNodeId,JsonUtil.getCN(endNode.getName()),wfProcessInstanceId,now,null,null,null,ctUserId,"1","1",wfProcessInstance.getWfProcessId()); // 创建节点实例
                    //-------结束之前的流程------


            //设置为方案报审阶段状态
            ccCriticalPrjPlan.setCcCriticalPrjImplPhaseId("5");
            //更新
            ccCriticalPrjPlan.updateById();
        }
    }

}
