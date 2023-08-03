package com.cisdi.pms.job.controller.process;

import com.cisdi.pms.job.domain.exportMain.PrjProgressRecords;
import com.cisdi.pms.job.domain.process.PoGuaranteeLetterRequireReq;
import com.cisdi.pms.job.service.process.PoGuaranteeLetterRequireReqService;
import com.cisdi.pms.job.utils.ExportUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 新增保函
 */
@RestController
@RequestMapping(value = "/guaranteeLetter")
public class PoGuaranteeLetterRequireReqController {

    @Resource
    private PoGuaranteeLetterRequireReqService poGuaranteeLetterRequireReqService;

    /**
     * 新增保函-台账-全字段导出
     */
    @GetMapping(value = "/exportAllMess")
    public void exportAllMess(PoGuaranteeLetterRequireReq poGuaranteeLetterRequireReq, HttpServletResponse response) throws Exception{
        List<PoGuaranteeLetterRequireReq> list = poGuaranteeLetterRequireReqService.selectAllMess(poGuaranteeLetterRequireReq);
        ExportUtil.exportExcel(response,"新增保函台账",list, PoGuaranteeLetterRequireReq.class);
    }
}
