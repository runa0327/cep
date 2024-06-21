package com.pms.bid.job.cronTask;

import com.pms.bid.job.service.process.ConstructionPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class CronTask {

    @Resource
    private ConstructionPlanService constructionPlanService;

    /**
     * 每天早上6点执行施工方案计划预警定时任务
     */
    @Scheduled(cron = "0 0 6 * * ?")
    public void generateCreateConstructionPlan(){
        try {
            log.error("施工方案计划预警任务-开始");
            constructionPlanService.generateCreateConstructionPlan();
            log.error("施工方案计划预警任务-结束");
        } catch (Exception e) {
            log.error("施工方案计划预警失败");
        }
    }
}
