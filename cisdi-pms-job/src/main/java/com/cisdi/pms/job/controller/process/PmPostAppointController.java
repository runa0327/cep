package com.cisdi.pms.job.controller.process;

import com.cisdi.pms.job.domain.process.PmPostAppoint;
import com.cisdi.pms.job.service.process.PmPostAppointService;
import com.qygly.shared.BaseException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/pmPostAppoint")
public class PmPostAppointController {

    @Resource
    private PmPostAppointService pmPostAppointService;

    @GetMapping(value = "/automaticPmPostAppoint")
    public void automaticPmPostAppoint(PmPostAppoint pmPostAppoint){
        if (!StringUtils.hasText(pmPostAppoint.getProjectId())){
            throw new BaseException("[projectId]项目id不能为空！");
        }
        pmPostAppointService.automaticPmPostAppoint(pmPostAppoint);
    }
}
