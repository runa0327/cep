package com.pms.bid.job.cronTask;

import com.pms.bid.job.service.ru.RuEmployeeEntryInfoService;
import com.pms.bid.job.service.ru.RuQzInspectionAttService;
import com.pms.bid.job.service.ru.RuQzInspectionInfoService;
import com.pms.bid.job.service.ru.RuQzInspectionItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class RuEmployeeEntryInfoSyncTask {

    @Resource
    private RuEmployeeEntryInfoService employeeEntryInfoService;




//        @Scheduled(cron = "0 0 4 * * ?")
@Scheduled(fixedRate = 1000*60*60*3)
    public void syncInspectionInfo(){
        try {
            log.error(" 俄罗斯检查员工签证-开始");
            employeeEntryInfoService.checkUserVisaExpire();
            log.error("俄罗斯检查员工签证-结束");
        } catch (Exception e) {
            log.error("俄罗斯检查员工签证");
        }
    }

}
