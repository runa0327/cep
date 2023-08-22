package com.cisdi.pms.job.weeklyReport;

import com.cisdi.pms.job.service.weeklyReport.PmProgressWeeklyPrjService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class WeeklyReportJob {

    @Autowired
    WeeklyReportService weeklyReportService;

    @Resource
    private PmProgressWeeklyPrjService pmProgressWeeklyPrjService;

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
     * 进度周报生成 5分钟执行一次
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void createProgressWeekly(){
        try {
            log.info("【形象进度周报】自动生成开始");
            pmProgressWeeklyPrjService.createProgressWeekly();
            log.info("【形象进度周报】自动生成结束");
        } catch (Exception e){
            log.error("【形象进度周报】自动生成失败");
        }
    }

    /**
     * 进度周报提交 每周四晚上 10点02分执行
     */
    @Scheduled(cron = "0 02 22 * * ?")
    public void autoSubmitProgressWeekly(){
        try {
            log.info("【形象进度周报】自动提交开始");
            weeklyReportService.submitProgressWeekly();
            log.info("【形象进度周报】自动提交结束");
        } catch (Exception e){
            log.error("【形象进度周报】自动提交失败");
        }
    }
}
