package com.cisdi.pms.job.controller.process;

import com.cisdi.pms.job.domain.process.WfProcessInstance;
import com.cisdi.pms.job.service.process.WfProcessInstanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/wfProcessInstance")
public class WfProcessInstanceController {

    @Resource
    private WfProcessInstanceService wfProcessInstanceService;

    @GetMapping(value = "/download")
    public void download(WfProcessInstance wfProcessInstance, HttpServletResponse response) throws Exception{
        List<WfProcessInstance> list = wfProcessInstanceService.queryAllList(wfProcessInstance);
        wfProcessInstanceService.download(list,response,"流程监控");
    }
}
