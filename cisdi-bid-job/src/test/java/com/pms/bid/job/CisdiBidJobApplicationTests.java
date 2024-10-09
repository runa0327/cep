package com.pms.bid.job;

import com.pms.bid.job.domain.zhanJiang.CcReviewProgress;
import com.pms.bid.job.mapper.zhanJiang.PressurePipelineMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class CisdiBidJobApplicationTests {


    @Resource
    private PressurePipelineMapper pressurePipelineMapper;


    @Test
    void contextLoads() {

        List<CcReviewProgress> progresses = pressurePipelineMapper.selectByPipelineId("111");


    }

}
