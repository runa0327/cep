package com.cisdi.pms.job.cronTask;

import com.cisdi.pms.job.service.base.BaseThirdInterfaceService;
import com.cisdi.pms.job.service.base.BaseYearService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * job-基础服务
 */
@Component
@Slf4j
public class BaseServiceJob {

    @Resource
    private BaseYearService baseYearService;

    @Resource
    private BaseThirdInterfaceService baseThirdInterfaceService;

    /**
     * 自动生产年份信息
     * 每年1月1日凌晨1分执行
     */
    @Scheduled(cron = "0 1 1 1 1 ?")
    public void generateYear(){
        try {
            log.info("自动生成年份信息-开始");
            baseYearService.generateYear();
            log.info("自动生成年份信息-成功");
        } catch (Exception e){
            log.error("自动生成年份信息-失败",e);
        }
    }

    /**
     * 自动执行三方接口未执行成功的数据
     * 失败次数5次以下，创建时间1天以内 10分钟以前
     */
    @Scheduled(cron = "0 2/3 * * * ?")
    private void generateExecuteHttpThird(){
        try {
            log.info("自动调用三方接口执行结果-开始");
            baseThirdInterfaceService.generateExecuteHttpThird();
            log.info("自动调用三方接口执行结果-成功");
        } catch (Exception e){
            log.error("自动调用三方接口执行结果-失败",e);
        }
    }
}
