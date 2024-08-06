package com.pms.bid.job.cronTask;

import com.pms.bid.job.domain.zhanJiang.PressurePipeline;
import com.pms.bid.job.mapper.zhanJiang.PressurePipelineMapper;
import com.pms.bid.job.service.zhanJiang.PressurePipelineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PipIngTask {


    @Autowired
    private PressurePipelineService pressurePipelineService;

    /**
     * 每日六点检查是否需要推送代办消息
     */
//    @Scheduled(cron = "0 0 6 * * ?")
    public void pushMessage(){

        pressurePipelineService.pushMessage();
    }
}
