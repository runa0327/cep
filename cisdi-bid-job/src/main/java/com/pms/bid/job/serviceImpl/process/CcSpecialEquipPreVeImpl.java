package com.pms.bid.job.serviceImpl.process;

import com.pms.bid.job.domain.process.ConstructionPlan;
import com.pms.bid.job.domain.process.SpecialEquipPreVe;
import com.pms.bid.job.mapper.zhanJiang.CcSpecialEquipPreVeMapper;
import com.pms.bid.job.service.process.CcSpecialEquipPreVeService;
import com.pms.bid.job.service.processInstance.WfProcessInstanceService;
import com.pms.bid.job.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CcSpecialEquipPreVeImpl implements CcSpecialEquipPreVeService {

    @Resource
    private CcSpecialEquipPreVeMapper specialEquipPreVeMapper;

    @Resource
    private WfProcessInstanceService wfProcessInstanceService;


    //检查应发通知数据列表
    @Override
    public void checkRecord() {

        //基本信息应填写列表
        List<SpecialEquipPreVe> baseFillList = specialEquipPreVeMapper.queryBaseFillList();
        for (SpecialEquipPreVe equip : baseFillList) {
            startWarning(equip, 1);
        }

        //施工信息应填写列表
        List<SpecialEquipPreVe> conFillList = specialEquipPreVeMapper.queryConFillList();
        for (SpecialEquipPreVe equip : conFillList) {
            startWarning(equip, 2);
        }

        //计划登记信息填写
        List<SpecialEquipPreVe> plan = specialEquipPreVeMapper.queryPlanUseRegFillList();

        for (SpecialEquipPreVe equip : plan) {
            startWarning(equip, 3);
        }
        //完成登记信息填写
        List<SpecialEquipPreVe> comUseRegFillList = specialEquipPreVeMapper.queryComUseRegFillList();
        for (SpecialEquipPreVe equip : comUseRegFillList) {
            startWarning(equip, 4);
        }

        //逾期通知
        List<SpecialEquipPreVe> beOverdueList1 = specialEquipPreVeMapper.queryBeOverdueList1();
        for (SpecialEquipPreVe equip : beOverdueList1) {
            String wfProcessInstanceId = equip.getLkWfInstId();
            wfProcessInstanceService.sendWfProcessNotice(wfProcessInstanceId, equip.getAdUserId());
        }

        List<SpecialEquipPreVe> beOverdueList2 = specialEquipPreVeMapper.queryBeOverdueList2();
        for (SpecialEquipPreVe equip : beOverdueList2) {
            String wfProcessInstanceId = equip.getLkWfInstId();
            wfProcessInstanceService.sendWfProcessNotice(wfProcessInstanceId, equip.getAdUserId());
        }


    }

    //开始基本信息填写
    private void startWarning(SpecialEquipPreVe tmp, int step) {
        //  校验是否已发起流程
        String wfProcessInstanceId = tmp.getLkWfInstId();
        String now = DateUtil.getNormalTimeStr(new Date());
        String task=null;
        if (step == 1)
            task = tmp.getLkWfInstId1();
        else if (step == 2)
            task = tmp.getLkWfInstId2();
        else if (step == 3)
            task = tmp.getLkWfInstId3();
        else if (step == 4)
            task = tmp.getLkWfInstId4();

        if (StringUtils.hasText(wfProcessInstanceId)) {
            //  发起新的通知
//            wfProcessInstanceService.sendWfProcessNotice(tmp.getLkWfInstId1(), tmp.getAdUserId());

            if (step == 2 && StringUtils.hasText(task)) {
                specialEquipPreVeMapper.updateInstId2ById(tmp.getId(), wfProcessInstanceId);
                String taskId =  wfProcessInstanceService.createUserTask(wfProcessInstanceId,tmp.getAdUserId(),1,now,"0099250247095871681");
                specialEquipPreVeMapper.updateInstId2ById(tmp.getId(), taskId);
            }else if (step == 3 && StringUtils.hasText(task)) {
                specialEquipPreVeMapper.updateInstId3ById(tmp.getId(), wfProcessInstanceId);
                String taskId =  wfProcessInstanceService.createUserTask(wfProcessInstanceId,tmp.getAdUserId(),1,now,"0099250247095871681");
                specialEquipPreVeMapper.updateInstId3ById(tmp.getId(), taskId);
            }else if (step == 4 && StringUtils.hasText(task)) {
                specialEquipPreVeMapper.updateInstId4ById(tmp.getId(), wfProcessInstanceId);
                String taskId =  wfProcessInstanceService.createUserTask(wfProcessInstanceId,tmp.getAdUserId(),1,now,"0099250247095871681");
                specialEquipPreVeMapper.updateInstId4ById(tmp.getId(), taskId);
            }

        } else {

            //  发起流程
            Map<String,String> result= wfProcessInstanceService.createWfProcessToSendNode(tmp, "1816078511807934464", "1816043591190458368", "1813462684271403008", "CC_SPECIAL_EQUIP_PRE_VE", tmp.getAdUserId());
            wfProcessInstanceId =  result.get("wfProcessInstanceId");
            specialEquipPreVeMapper.updateInstIdById(tmp.getId(), wfProcessInstanceId);

            //  该条数据状态改为已发起,并写入流程实例id
            if (step == 1) {
                specialEquipPreVeMapper.updateInstId1ById(tmp.getId(), wfProcessInstanceId);
                specialEquipPreVeMapper.updateInstId1ById(tmp.getId(), result.get("taskId"));
            }else if (step == 2 && StringUtils.hasText(task)) {
                specialEquipPreVeMapper.updateInstId2ById(tmp.getId(), wfProcessInstanceId);
               String taskId =  wfProcessInstanceService.createUserTask(wfProcessInstanceId,tmp.getAdUserId(),1,now,"0099250247095871681");
                specialEquipPreVeMapper.updateInstId2ById(tmp.getId(), taskId);
            }else if (step == 3 && StringUtils.hasText(task)) {
                specialEquipPreVeMapper.updateInstId3ById(tmp.getId(), wfProcessInstanceId);
                String taskId =  wfProcessInstanceService.createUserTask(wfProcessInstanceId,tmp.getAdUserId(),1,now,"0099250247095871681");
                specialEquipPreVeMapper.updateInstId3ById(tmp.getId(), taskId);
            }else if (step == 4 && StringUtils.hasText(task)) {
                specialEquipPreVeMapper.updateInstId4ById(tmp.getId(), wfProcessInstanceId);
                String taskId =  wfProcessInstanceService.createUserTask(wfProcessInstanceId,tmp.getAdUserId(),1,now,"0099250247095871681");
                specialEquipPreVeMapper.updateInstId4ById(tmp.getId(), taskId);
            }
        }
    }

}
