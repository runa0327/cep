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

    /**
     * 岗位指派-根据项目id自动发起岗位指派流程
     * @param pmPostAppoint 岗位指派实体数据
     */
    @GetMapping(value = "/automaticPmPostAppoint")
    public void automaticPmPostAppoint(PmPostAppoint pmPostAppoint){
        if (!StringUtils.hasText(pmPostAppoint.getProjectId())){
            throw new BaseException("[projectId]项目id不能为空！");
        }
        pmPostAppointService.automaticPmPostAppoint(pmPostAppoint);
    }
}
