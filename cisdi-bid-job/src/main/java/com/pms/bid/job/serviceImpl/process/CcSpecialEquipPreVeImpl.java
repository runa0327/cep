package com.pms.bid.job.serviceImpl.process;

import com.pms.bid.job.domain.process.ConstructionPlan;
import com.pms.bid.job.domain.process.SpecialEquipPreVe;
import com.pms.bid.job.mapper.zhanJiang.CcSpecialEquipPreVeMapper;
import com.pms.bid.job.service.process.CcSpecialEquipPreVeService;
import com.pms.bid.job.service.processInstance.WfProcessInstanceService;
import com.pms.bid.job.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

        //未办理完成列表
        List<SpecialEquipPreVe> dueList = specialEquipPreVeMapper.selectDueList();
        for (SpecialEquipPreVe equip : dueList) {
            startWarning(equip);
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
    @Transactional
    public void startWarning(SpecialEquipPreVe tmp) {
        //  校验是否已发起流程
        String wfProcessInstanceId = tmp.getLkWfInstId();
        String now = DateUtil.getNormalTimeStr(new Date());

        if (StringUtils.hasText(wfProcessInstanceId)) {
            //  发起新的通知

            String taskId = null;
            taskId = wfProcessInstanceService.createUserTask(wfProcessInstanceId, tmp.getAdUserId(), 1, now, "0099250247095871681");
            specialEquipPreVeMapper.updateTaskIdById(tmp.getId(), taskId, tmp.getRecordType());//设置任务id

        } else {

            //  发起流程
            Map<String, String> result = wfProcessInstanceService.createWfProcessToSendNode(tmp, "1816078511807934464", "1816043591190458368", "1813462684271403008", "CC_SPECIAL_EQUIP_PRE_VE", tmp.getAdUserId());
            wfProcessInstanceId = result.get("wfProcessInstanceId");
            specialEquipPreVeMapper.updateInstIdById(tmp.getId(), wfProcessInstanceId); //设置流程实例id

            //发任务提醒
            String taskId = wfProcessInstanceService.createUserTask(wfProcessInstanceId, tmp.getAdUserId(), 1, now, "0099250247095871681");
            specialEquipPreVeMapper.updateTaskIdById(tmp.getId(), taskId, tmp.getRecordType()); //修改任务提醒
        }
    }

}
