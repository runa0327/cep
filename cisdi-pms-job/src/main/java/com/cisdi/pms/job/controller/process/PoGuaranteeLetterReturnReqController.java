package com.cisdi.pms.job.controller.process;

import com.cisdi.pms.job.domain.process.PoGuaranteeLetterReturnReq;
import com.cisdi.pms.job.service.process.PoGuaranteeLetterReturnReqService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 保函退还申请
 */
@RestController
@RequestMapping(value = "/poGuaranteeLetterReturnReq")
public class PoGuaranteeLetterReturnReqController {

    @Resource
    private PoGuaranteeLetterReturnReqService poGuaranteeLetterReturnReqService;

    /**
     * 保函退还导出
     */
    @GetMapping(value = "/exportListMsg")
    public void exportListMsg(PoGuaranteeLetterReturnReq poGuaranteeLetterReturnReq, HttpServletResponse response){
        List<PoGuaranteeLetterReturnReq> list = poGuaranteeLetterReturnReqService.getAllList(poGuaranteeLetterReturnReq);
        poGuaranteeLetterReturnReqService.exportListMsg(list,response,"保函退还导出");
    }

    /**
     * 保函全字段导出
     */
    @GetMapping(value = "/exportAllMsg")
    public void exportAllMsg(PoGuaranteeLetterReturnReq poGuaranteeLetterReturnReq, HttpServletResponse response){
        List<PoGuaranteeLetterReturnReq> list = poGuaranteeLetterReturnReqService.getAllList(poGuaranteeLetterReturnReq);
        poGuaranteeLetterReturnReqService.exportAllMsg(list,response,"保函全字段导出");
    }
}
