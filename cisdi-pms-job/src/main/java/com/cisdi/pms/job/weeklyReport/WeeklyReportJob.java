package com.cisdi.pms.job.weeklyReport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WeeklyReportJob {

    @Autowired
    WeeklyReportService weeklyReportService;

    @Scheduled(cron = "0 0 0/2 * * ?")
    public void run() {
        try {
            log.info("【流程周报】自动生成开始：");
            weeklyReportService.execute();
            log.info("【流程周报】自动生成结束（成功）。");
        } catch (Exception ex) {
            log.error("【流程周报】自动生成结束（失败）！", ex);
        }
    }

    /**
     * 进度周报生成 每周五凌晨执行一次
     */
    @Scheduled(cron = "0 5 0 * * ?")
    public void createProgressWeekly(){
        try {
            log.info("【形象进度周报】自动生成开始");
            weeklyReportService.createProgressWeekly();
            log.info("【形象进度周报】自动生成结束");
        } catch (Exception e){
            log.error("【形象进度周报】自动生成失败");
        }
    }
}
