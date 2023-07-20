package com.cisdi.pms.job.controller.report;

import com.cisdi.pms.job.domain.report.PmProjectProblemReq;
import com.cisdi.pms.job.excel.export.BaseController;
import com.cisdi.pms.job.service.report.PmProjectProblemReqService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/projectProblem")
public class PmProjectProblemReqController extends BaseController {

    @Resource
    private PmProjectProblemReqService pmProjectProblemReqService;

    /**
     * 项目问题导出
     * @param pmProjectProblemReq 项目问题入参
     * @param response http响应
     */
    @GetMapping(value = "/downLoadProjectProblem")
    public void downLoadProjectProblem(PmProjectProblemReq pmProjectProblemReq, HttpServletResponse response){
        pmProjectProblemReqService.downLoadProjectProblem(pmProjectProblemReq,response);
    }
}
