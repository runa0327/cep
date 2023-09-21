package com.cisdi.pms.job.weeklyReport;

import com.cisdi.pms.job.service.weeklyReport.PmConstructionService;
import com.cisdi.pms.job.service.weeklyReport.PmProgressWeeklyPrjService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class WeeklyReportJob {

    @Resource
    WeeklyReportService weeklyReportService;

    @Resource
    private PmProgressWeeklyPrjService pmProgressWeeklyPrjService;

    @Resource
    private PmConstructionService pmConstructionService;

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

    /**
     * 工程建安需求填报生成 5分钟生成一次
     */
    @Scheduled(cron = "0 2/5 * * * ?")
    public void generateJianAn(){
        try {
            log.info("工程建安费用需求生成-开始");
            pmConstructionService.generateJianAn();
            log.info("工程建安费用需求生成-成功");
        } catch (Exception e){
            log.error("工程建安费用需求生成-失败：",e);
        }
    }

    /**
     * 工程建安需求填报-月初待确认任务生成
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void monthCheckAmt(){
        try {
            log.info("工程建安费用当月待确认任务生成-开始");
            pmConstructionService.monthCheckAmt();
            log.info("工程建安费用当月待确认任务生成-成功");
        } catch (Exception e){
            log.error("工程建安费用当月待确认任务生成-失败：",e);
        }
    }
}
