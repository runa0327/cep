package com.pms.bid.job.cronTask;

import com.pms.bid.job.service.process.CcSpecialEquipPreVeService;
import com.pms.bid.job.service.process.ConstructionPlanService;
import com.pms.bid.job.service.ru.RuQzInspectionAttService;
import com.pms.bid.job.service.ru.RuQzInspectionInfoService;
import com.pms.bid.job.service.ru.RuQzInspectionItemService;
import com.pms.bid.job.service.zhanJiang.CcAssemblingPressureVesselsService;
import com.pms.bid.job.service.zhanJiang.CcElevatorService;
import com.pms.bid.job.service.zhanJiang.CcHoistingMachineryService;
import com.pms.bid.job.service.zhanJiang.CcIotEquipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class RuQzInspectionSyncTask {

    @Resource
    private RuQzInspectionInfoService inspectionInfoService;

    @Resource
    private RuQzInspectionAttService inspectionAttService;

    @Resource
    private RuQzInspectionItemService inspectionItemService;

    /**
     * 每天早上4点执行施工方案计划预警定时任务
     */

    @Scheduled(cron = "0 0 4 ? * SUN")
    public void syncInspectionAtt(){
        try {
            log.error(" 同步巡检性质-开始");
            inspectionAttService.syncQzInspectionAtt();
            log.error("同步巡检性质-结束");
        } catch (Exception e) {
            log.error("同步巡检性质");
        }
    }


//        @Scheduled(cron = "0 0 4 ? * SUN")
//    public void syncInspectionItem(){
//        try {
//            log.error(" 同步巡检项-开始");
//            inspectionItemService.syncQzInspectionItem();
//            log.error("同步巡检项-结束");
//        } catch (Exception e) {
//            log.error("同步巡检项");
//        }
//    }

        @Scheduled(cron = "0 0 4 * * ?")
//@Scheduled(fixedRate = 1000*60*60*3)
    public void syncInspectionInfo(){
        try {
            log.error(" 同步巡检列表-开始");
            inspectionInfoService.syncQzInspectionInfo();
            log.error("同步巡检列表-结束");
        } catch (Exception e) {
            log.error("同步巡检列表失败");
        }
    }

}
