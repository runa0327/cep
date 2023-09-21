package com.cisdi.pms.job.controller.other;

import com.cisdi.pms.job.service.other.PrjReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/projectReport")
public class PrjReportController {

    @Resource
    private PrjReportService prjReportService;

    /**
     * 工程项目信息协同系统效果评价指标 导出
     */
    @GetMapping(value = "/downPrjUserMsg")
    public void downPrjUserMsg(HttpServletResponse response, String startTime, String endTime){
        prjReportService.downPrjUserMsg(response,startTime,endTime);
    }
}
