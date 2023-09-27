package com.cisdi.pms.job.controller.base;

import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeekly;
import com.cisdi.pms.job.service.base.SystemService;
import com.qygly.shared.BaseException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(value = "/system")
public class SystemController {

    @Resource
    private SystemService systemService;

    /**
     * 系统使用情况导出
     */
    @GetMapping(value = "/systemUsage")
    public void systemUsage(HttpServletResponse response, PmProgressWeekly weekly){
        String weekId = weekly.getId();
        if (!StringUtils.hasText(weekId)){
            throw new BaseException("【weekId】周信息不能为空!");
        }
        Map<String,Object> map = systemService.querySystemUsage(weekId);
        systemService.downloadSystemUsage(response,map,"系统使用情况");
    }
}
