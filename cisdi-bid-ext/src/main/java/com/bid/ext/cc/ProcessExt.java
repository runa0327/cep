package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcessExt {

    //支付申请流程相关扩展

    /**
     * 所有需要使用监理单位工程师的流程，都需要调用该方法
     * 获取监理单位工程师
     */
    public void getSuperviseUsers() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String entCode = ExtJarHelper.getSevInfo().entityInfo.code;//表名
            String csCommId = entityRecord.csCommId;
            String param = "cc_process_supervise_user_ids";

            getUsersByParam(entCode,csCommId,param);
        }
    }

    /**
     * 所有需要使用监理单位工程师的流程，都需要调用该方法
     * 获取管理单位工程师
     */
    public void getManagementUsers() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String entCode = ExtJarHelper.getSevInfo().entityInfo.code;//表名
            String csCommId = entityRecord.csCommId;
            String param = "cc_process_management_user_ids";

            getUsersByParam(entCode,csCommId,param);
        }
    }

    public void getUsersByParam(String entCode,String csCommId,String param){
        String sqlStr = "select " + param +" from " + entCode + " where ID = ?";
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        List<Map<String, Object>> ccUserIdsList = myJdbcTemplate.queryForList(sqlStr,csCommId);
        if (!CollectionUtils.isEmpty(ccUserIdsList)) {
            Map<String, Object> map = ccUserIdsList.get(0);
            String ccUserIds = map.get(param).toString();
            String[] memberIds = ccUserIds.split(",");
            ArrayList<String> memberIdList = new ArrayList<>(Arrays.asList(memberIds));
            ArrayList<String> userIdList = new ArrayList<>();
            for (String memberId : memberIdList) {
                CcPrjMember ccPrjMember = CcPrjMember.selectById(memberId);
                String adUserId = ccPrjMember.getAdUserId();
                userIdList.add(adUserId);
            }
            ExtJarHelper.setReturnValue(userIdList);
        }
    }


    /**
     * 获取支付申请流程中所有参与审批的用户
     */
    public void getPayReqAllUser() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String WfProcessInstanceId = ExtJarHelper.getProcInstId();//获取当前流程实例id
            ArrayList<String> userIdList = new ArrayList<>();//用户ID列表

            //获取对应的节点实例
            Where wfNodeInstanceWhere = new Where();
            wfNodeInstanceWhere.eq("WF_PROCESS_INSTANCE_ID", WfProcessInstanceId)
                    .eq("STATUS", "AP");
            List<WfNodeInstance> wfNodeInstanceList = WfNodeInstance.selectByWhere(wfNodeInstanceWhere);
            ArrayList<String> wfNodeInstanceIdList = new ArrayList<>();//节点实例ID列表
            if (wfNodeInstanceList != null) {
                for (WfNodeInstance wfNodeInstance : wfNodeInstanceList) {
                    wfNodeInstanceIdList.add(wfNodeInstance.getId());
                }

                //获取对应任务
                Where wfTaskWhere = new Where();
                wfTaskWhere.in("WF_NODE_INSTANCE_ID", wfNodeInstanceIdList.toArray() )
                        .eq("STATUS", "AP");
                List<WfTask> wfTaskList = WfTask.selectByWhere(wfTaskWhere);
                if (wfTaskList != null) {
                    for (WfTask wfTask : wfTaskList) {
                        userIdList.add(wfTask.getAdUserId());
                    }

                    //去重
                    List<String> distinctUserIdList = userIdList.stream()
                            .distinct()
                            .collect(Collectors.toList());

                    ExtJarHelper.setReturnValue(distinctUserIdList);
                }else{
                    throw new BaseException("获取审批用户失败");
                }
            }else{
                throw new BaseException("获取审批用户失败");
            }
        }
    }

    //施工方案流程相关扩展

    /**
     * 获取发起单位项目负责人
     */
    public void getInitiatingLead(){
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            ArrayList<String> userIdList = new ArrayList<>();//用户ID列表

            String WfProcessInstanceId = ExtJarHelper.getProcInstId();//获取当前流程实例id
            WfProcessInstance wfProcessInstance = WfProcessInstance.selectById(WfProcessInstanceId);
            String userId = wfProcessInstance.getStartUserId();//流程发起用户

            //一个人可能涉及多个部门，需要筛选
            CcPrjMember ccPrjMember = null;
            Where ccPrjMemberWhere = new Where();
            ccPrjMemberWhere.eq("AD_USER_ID", userId);//项目用户信息
            List<CcPrjMember> ccPrjMemberList = CcPrjMember.selectByWhere(ccPrjMemberWhere);//项目用户信息
            if (ccPrjMemberList != null && ccPrjMemberList.size() > 0) {
                for (CcPrjMember ccPrjMemberItem : ccPrjMemberList) {
                    if(ccPrjMemberItem.getIsPrimaryPos()){
                        //主岗位
                        ccPrjMember = ccPrjMemberItem;
                    }
                }
                if(ccPrjMember == null){
                    //取第一条数据
                    ccPrjMember = ccPrjMemberList.get(0);
                }
            }else{
                throw new BaseException("发起人不在项目岗位中");
            }

            Where postWhere = new Where();
            postWhere.eq("CODE", "PROJECT_LEAD");//项目负责人
            CcPost ccPost = CcPost.selectOneByWhere(postWhere);//项目用户岗位信息
            if (ccPost != null) {
                Where where = new Where();
                where.eq("CC_PRJ_ID", ccPrjMember.getCcPrjId()) //项目
                        .eq("CC_PARTY_ID", ccPrjMember.getCcPartyId()) //参建方
                        .eq("CC_COMPANY_ID", ccPrjMember.getCcCompanyId()) //参建方公司
                        .eq("CC_POST_ID", ccPost.getId()); //岗位
                List<CcPrjMember> ccPrjMemberInitiatingLeadList = CcPrjMember.selectByWhere(where);
                if (ccPrjMemberInitiatingLeadList != null && ccPrjMemberInitiatingLeadList.size() > 0) {
                    for (CcPrjMember ccPrjMemberInitiatingLead : ccPrjMemberInitiatingLeadList) {
                        userIdList.add(ccPrjMemberInitiatingLead.getAdUserId());
                    }
                    ExtJarHelper.setReturnValue(userIdList);
                }else{
                    throw new BaseException("获取发起单位项目负责人失败");
                }
            }else{
                throw new BaseException("获取发起单位项目负责人失败");
            }
        }
    }
}
