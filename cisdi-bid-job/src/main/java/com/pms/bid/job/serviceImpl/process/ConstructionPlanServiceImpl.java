package com.pms.bid.job.serviceImpl.process;

import com.pms.bid.job.domain.process.ConstructionPlan;
import com.pms.bid.job.mapper.ConstructionPlanMapper;
import com.pms.bid.job.service.process.ConstructionPlanService;
import com.pms.bid.job.service.processInstance.WfProcessInstanceService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConstructionPlanServiceImpl implements ConstructionPlanService {

    @Resource
    private ConstructionPlanMapper constructionPlanMapper;

    @Resource
    private WfProcessInstanceService wfProcessInstanceService;

    /**
     * 施工方案计划预警任务启动
     */
    @Override
    public void generateCreateConstructionPlan() {
        //  获取计划完成时间是当天及之后、同时未发起、发起未完成的数据
        List<ConstructionPlan> list = constructionPlanMapper.queryCompleteDateAfterNow();
        if (!CollectionUtils.isEmpty(list)) {
            for (ConstructionPlan tmp : list) {
                String adVanceWarningDays = tmp.getAdVanceWarningDays();
                Integer diffDay = tmp.getDiffDay();
                //  提前预警天数包含当前时间-计划完成时间
                if (adVanceWarningDays.contains(String.valueOf(diffDay))){
                    //  开始预警
                    startWarning(tmp);
                }
            }
        }
    }

    /**
     * 单条施工方案计划预警判断处理
     * @param tmp 施工方案计划
     */
    private void startWarning(ConstructionPlan tmp) {
        //  校验是否已发起流程
        String wfProcessInstanceId = tmp.getWfProcessInstanceId();
        if (StringUtils.hasText(wfProcessInstanceId)) {
            //  发起新的通知
            wfProcessInstanceService.sendWfProcessNotice(tmp.getWfProcessInstanceId(),tmp.getAdUserId());
        } else {
            //  发起流程
            wfProcessInstanceId = wfProcessInstanceService.createWfProcessToSendNode(tmp);

            //  该条数据状态改为已发起,并写入流程实例id
            constructionPlanMapper.updateIsStartById(tmp.getId(),wfProcessInstanceId);
        }
    }
}
