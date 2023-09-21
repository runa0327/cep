package com.cisdi.pms.job.controller.process;

import com.cisdi.pms.job.service.process.PoOrderReqService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 合同模块
 */
@RestController
@RequestMapping(value = "/poOrder")
public class PoOrderReqController {

    @Resource
    private PoOrderReqService poOrderReqService;

    /**
     * 合同签订历史数据导入-合同签订公司及合同类型写入数据表
     */
    @GetMapping(value = "/poOrderImportHistoryCompanyHandle")
    public void poOrderImportHistoryCompanyHandle(){
        poOrderReqService.poOrderImportHistoryCompanyHandle();
    }
}
