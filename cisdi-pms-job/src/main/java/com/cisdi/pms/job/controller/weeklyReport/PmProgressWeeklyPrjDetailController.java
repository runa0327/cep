package com.cisdi.pms.job.controller.weeklyReport;

import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeeklyPrjDetail;
import com.cisdi.pms.job.excel.export.BaseController;
import com.cisdi.pms.job.service.weeklyReport.PmProgressWeeklyPrjDetailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/progressWeekly")
public class PmProgressWeeklyPrjDetailController extends BaseController {

    @Resource
    private PmProgressWeeklyPrjDetailService pmProgressWeeklyPrjDetailService;

    /**
     * 形象工程周报-填报记录导出
     * @param pmProgressWeeklyPrjDetail 查询参数
     * @param response 响应结果
     */
    @GetMapping(value = "/downloadPrjUserRecords")
    public void downloadPrjUserRecords(PmProgressWeeklyPrjDetail pmProgressWeeklyPrjDetail, HttpServletResponse response){
        pmProgressWeeklyPrjDetailService.downloadPrjUserRecords(pmProgressWeeklyPrjDetail,response);
    }

    /**
     * 形象工程周报-老版本数据写入新版本明细信息
     */
    @GetMapping(value = "/prjProblemDetailOldToNew")
    public void prjProblemDetailOldToNew(){
        pmProgressWeeklyPrjDetailService.updateOldPrjProblemToDetail();
    }
}
