package com.bid.ext.gy;

import com.bid.ext.model.*;
import com.bid.ext.utils.FlowUtils;
import com.bid.ext.utils.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.StatusE;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.EntityRecordUtil;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GyMaterialAcceptanceExt {

    //接收人
    public void getReceiver(){
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            GyMaterialAcceptance acceptance = GyMaterialAcceptance.selectById(csCommId);
            String gyReceiver = acceptance.getGyReceiver();

            CcPrjMember member = CcPrjMember.selectById(gyReceiver);
            ArrayList<String> userIdList = new ArrayList<>();
            userIdList.add(member.getAdUserId());

            ExtJarHelper.setReturnValue(userIdList);

        }
    }

    //监理单位
    public void getSupInspector(){
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            GyMaterialAcceptance acceptance = GyMaterialAcceptance.selectById(csCommId);
            String supInspectorId = acceptance.getGySupInspectorId();

            CcPrjMember member = CcPrjMember.selectById(supInspectorId);
            ArrayList<String> userIdList = new ArrayList<>();
            userIdList.add(member.getAdUserId());

            ExtJarHelper.setReturnValue(userIdList);

        }
    }

    //建设单位
    public void getBudInspector(){
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            GyMaterialAcceptance acceptance = GyMaterialAcceptance.selectById(csCommId);
            String budInspectorId = acceptance.getGyBudInspectorId();

            CcPrjMember member = CcPrjMember.selectById(budInspectorId);
            ArrayList<String> userIdList = new ArrayList<>();
            userIdList.add(member.getAdUserId());

            ExtJarHelper.setReturnValue(userIdList);

        }
    }
    //送检人
    public void getSubmitForInspectionPerson(){
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            GyMaterialAcceptance acceptance = GyMaterialAcceptance.selectById(csCommId);
            String submitForInspectionPerson = acceptance.getGySubmitForInspectionPerson();

            CcPrjMember member = CcPrjMember.selectById(submitForInspectionPerson);
            ArrayList<String> userIdList = new ArrayList<>();
            userIdList.add(member.getAdUserId());

            ExtJarHelper.setReturnValue(userIdList);
        }
    }


    //通知抄送人
    public void  sendNotify(){

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;

            GyMaterialAcceptance acceptance = GyMaterialAcceptance.selectById(csCommId);

            if(StringUtils.hasLength(acceptance.getGyCcIds())){

                List<String> userIds = new ArrayList<>();
                String[] memberIds = acceptance.getGyCcIds().split(",");
                List<String> memberIdList =  new ArrayList<>();
                for ( int i=0 ; i < memberIds.length ; i++ ){
                    memberIdList.add(memberIds[i]);
                }

                List<CcPrjMember> ccPrjMembers = CcPrjMember.selectByIds(memberIdList);

                //设置接收通知人id
                ccPrjMembers.forEach(ccPrjMember -> {
                    userIds.add(ccPrjMember.getAdUserId());
                });

                FlowUtils.sendNotify(userIds,csCommId);

            }

        }
    }

    //获取通知人
    public void  getCcUserId(){
        List<String> userIds = new ArrayList<>();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;

            GyMaterialAcceptance acceptance = GyMaterialAcceptance.selectById(csCommId);

            if(StringUtils.hasLength(acceptance.getGyCcIds())){


                String[] memberIds = acceptance.getGyCcIds().split(",");
                List<String> memberIdList =  new ArrayList<>();
                for ( int i=0 ; i < memberIds.length ; i++ ){
                    memberIdList.add(memberIds[i]);
                }

                List<CcPrjMember> ccPrjMembers = CcPrjMember.selectByIds(memberIdList);

                //设置接收通知人id
                ccPrjMembers.forEach(ccPrjMember -> {
                    userIds.add(ccPrjMember.getAdUserId());
                });
            }
        }
        ExtJarHelper.setReturnValue(userIds);

    }

    //新增进场扩展
    public void materialInit(){

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;

            GyMaterialAcceptance acceptance = GyMaterialAcceptance.selectById(csCommId);

            String gyMaterialNameIds = acceptance.getGyMaterialNameIds();
            //生成进场材料明细记录
            String[] materialIds = gyMaterialNameIds.split(",");
            for (int i=0 ; i<materialIds.length ; i++){
                GyAcceptanceMaterialList intoMaterial = GyAcceptanceMaterialList.newData();
                intoMaterial.setGyMaterialNameId(materialIds[i]);
                intoMaterial.setGyMaterialAcceptanceId(acceptance.getId());
                intoMaterial.insertById();
            }


            //材料进场名称自动生成
            GyPrjArea area = GyPrjArea.selectById(acceptance.getGyPrjAreaId());
            GyMaterialType materialType = GyMaterialType.selectById(acceptance.getGyMaterialTypeId());
            String materialName = materialType.getName();

            Map<String, Object> queryForMap = ExtJarHelper.getMyJdbcTemplate().queryForMap("select   count(*) total from  gy_material_acceptance t  where  t.cc_prj_id=?  and  t.gy_material_type_id=?", acceptance.getCcPrjId(), acceptance.getGyMaterialTypeId());

            String acceptanceName = JsonUtil.getCN(area.getName())+JsonUtil.getCN(materialName)+"进场第"+queryForMap.get("total")+"批";

            acceptance.setName(acceptanceName);

            acceptance.updateById();

        }
    }



    //通知流程参与人完成
    public void getNotifier(){
        List<String> userIds = new ArrayList<>();

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            GyMaterialAcceptance acceptance = GyMaterialAcceptance.selectById(csCommId);

            String lkWfInstId = acceptance.getLkWfInstId();

            Where where =  new Where();
            where.eq("WF_PROCESS_INSTANCE_ID",lkWfInstId);
            where.eq("WF_TASK_TYPE_ID","TODO");
            where.eq("IS_CLOSED",1);
            List<WfTask> wfTasks = WfTask.selectByWhere(where);
            wfTasks.forEach(task -> {
                userIds.add(task.getAdUserId());
            });

        }
        ExtJarHelper.setReturnValue(userIds);

    }

    //退回到发起节点扩展
    public void toStartNodeSetStatus(){
        setStatus(StatusE.DR,"1981243939715866624");

    }

    private void setStatus(StatusE status,String  acceptanceStatus) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        ExtJarHelper.getEntityRecordList().forEach(entityRecord -> {
            String id = EntityRecordUtil.getId(entityRecord);
            myJdbcTemplate.update("update gy_material_acceptance t set t.status=?,t.ver=t.ver+1,t.ts=now(),t.GY_MATERIAL_ACCEPTACE_STAUS_ID=? where t.id=?", status.toString(),acceptanceStatus, id);
        });
    }

}
