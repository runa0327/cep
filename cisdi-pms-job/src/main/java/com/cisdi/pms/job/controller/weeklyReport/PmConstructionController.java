package com.cisdi.pms.job.controller.weeklyReport;

import com.cisdi.pms.job.domain.weeklyReport.PmConstruction;
import com.cisdi.pms.job.service.weeklyReport.PmConstructionService;
import com.qygly.shared.BaseException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/PmConstruction")
public class PmConstructionController {

    @Resource
    private PmConstructionService pmConstructionService;

    /**
     * 工程建安费用需求填报统计-导出
     */
    @GetMapping(value = "/downloadConstruction")
    public void downloadConstruction(PmConstruction pmConstruction, HttpServletResponse response){
        if (!StringUtils.hasText(pmConstruction.getBaseYearId())){
            throw new BaseException("查询年份信息不能为空！");
        }
        List<Map<String,Object>> list = pmConstructionService.queryPmConstructionList(pmConstruction);
        pmConstructionService.downloadConstruction(list,"工程建安费需求",response);
    }

}
