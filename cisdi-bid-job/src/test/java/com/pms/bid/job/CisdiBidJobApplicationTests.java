package com.pms.bid.job;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pms.bid.job.domain.AdAtt;
import com.pms.bid.job.domain.FlFile;
import com.pms.bid.job.domain.FlPath;
import com.pms.bid.job.domain.zhanJiang.CcReviewProgress;
import com.pms.bid.job.mapper.file.AdAttMapper;
import com.pms.bid.job.mapper.file.FlFileMapper;
import com.pms.bid.job.mapper.file.FlPathMapper;
import com.pms.bid.job.mapper.zhanJiang.PressurePipelineMapper;
import com.pms.bid.job.service.ru.RuQzInspectionAttService;
import com.pms.bid.job.service.ru.RuQzInspectionInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class CisdiBidJobApplicationTests {


    @Resource
    private PressurePipelineMapper pressurePipelineMapper;

    @Resource
    private RuQzInspectionAttService inspectionAttService;

    @Resource
    private RuQzInspectionInfoService inspectionInfoService;

    @Resource
    private AdAttMapper adAttMapper;

    @Resource
    private FlPathMapper flPathMapper;

    @Resource
    private FlFileMapper flFileMapper;

    @Test
    void contextLoads() {

        List<CcReviewProgress> progresses = pressurePipelineMapper.selectByPipelineId("111");

    }

    @Test
    void testGetInspection(){
        inspectionAttService.syncQzInspectionAtt();
    }

    @Test
    void testGetInspectionRepaly(){

        JSONObject data = inspectionInfoService.getInspectionDetail("ebc6e35f2b73453ebc3fb6aedee5f06c", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXlsb2FkIjoie1wiYXBwSWRcIjpcImEwNjU4YjZmNDdmNDQzNzI5ZTI4ZWFmY2JmY2NmYWQ2XCJ9IiwiZXhwIjoxNzM4OTk5OTI0fQ.sWmgxQLwE4yLUp7XRLJ6PNzpsor-uSNSOPbEAAKvr14");
        System.out.println(data);
    }

    @Test
    void testGetFilePath(){
        LambdaQueryWrapper<AdAtt>  adWrapper = new LambdaQueryWrapper<>();
        adWrapper.eq(AdAtt::getCode,"CC_ATTACHMENT");
        AdAtt adAtt = adAttMapper.selectOne(adWrapper);
        System.out.println(adAtt.getCode());

        LambdaQueryWrapper<FlPath>  fileWrapper = new LambdaQueryWrapper<>();
        fileWrapper.eq(FlPath::getId,adAtt.getFilePathId());
        FlPath flPath = flPathMapper.selectOne(fileWrapper);
        System.out.println(flPath);
    }

//    @Test
//    void  testSaveImage(){
//        String url = "https://qingzhu-prd.obs.cn-east-3.myhuaweicloud.com/572dc40b-6fa8-43eb-a07b-4ef3da50daf7.jpg?AccessKeyId=KZMZP7KJBOBNPXXONCQW&Expires=1739014541&Signature=nJ/f/RY6OrqrooQSmnHsbZ8Fkhs%3D";
//
//        try {
//            String s = inspectionInfoService.saveImg(url);
//
//            System.out.println(s);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

}
