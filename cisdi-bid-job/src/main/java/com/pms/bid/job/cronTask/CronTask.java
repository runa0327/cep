package com.pms.bid.job.cronTask;

import com.pms.bid.job.service.process.CcSpecialEquipPreVeService;
import com.pms.bid.job.service.process.ConstructionPlanService;
import com.pms.bid.job.service.zhanJiang.CcAssemblingPressureVesselsService;
import com.pms.bid.job.service.zhanJiang.CcElevatorService;
import com.pms.bid.job.service.zhanJiang.CcHoistingMachineryService;
import com.pms.bid.job.service.zhanJiang.CcIotEquipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class CronTask {

    @Resource
    private ConstructionPlanService constructionPlanService;

    @Resource
    private CcSpecialEquipPreVeService  specialEquipPreVeService;

    @Resource
    private CcIotEquipService iotEquipService;

    @Resource
    private CcHoistingMachineryService hoistingMachineryService;

    @Resource
    private CcElevatorService elevatorService;

    @Resource
    private CcAssemblingPressureVesselsService assemblingPressureVesselsService;


    /**
     * 每天早上6点执行施工方案计划预警定时任务
     */
//    @Scheduled(cron = "0 0 6 * * ?")
    public void generateCreateConstructionPlan(){
        try {
            log.error("施工方案计划预警任务-开始");
            constructionPlanService.generateCreateConstructionPlan();
            log.error("施工方案计划预警任务-结束");
        } catch (Exception e) {
            log.error("施工方案计划预警失败");
        }
    }


    /**
     * 每天早上6点执行特种设备计划预警定时任务
     */
//    @Scheduled(cron = "0 0 6 * * ?")
    public void checkSpecialEquipPlan(){
        System.out.println("CronTask.checkSpecialEquipPlan");
        try {
            log.info("特种设备安装提醒-开始");
            specialEquipPreVeService.checkRecord();
            log.info("特种设备安装-结束");
        } catch (Exception e) {
            log.error("特种设备提醒失败:"+e.getMessage());
        }
    }

//    @Scheduled(fixedRate = 1000*60*10)
    public void checkIotStatus(){
        log.info("检查物联网设备在线状态");
        iotEquipService.checkOnlineStatus();
    }


//    @Scheduled(fixedRate = 1000*60*60)
    public void checkHoistingMachineryTodo(){
        hoistingMachineryService.checkDate();
        elevatorService.checkDate();
        assemblingPressureVesselsService.checkDate();
    }


}
