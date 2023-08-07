package com.cisdi.pms.job.service.process;

import com.cisdi.pms.job.domain.process.PoGuaranteeLetterRequireReq;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface PoGuaranteeLetterRequireReqService {

    /**
     * 查询条件内所有信息
     * @param poGuaranteeLetterRequireReq 新增保函实体
     * @return 查询结果
     */
    List<PoGuaranteeLetterRequireReq> selectAllMess(PoGuaranteeLetterRequireReq poGuaranteeLetterRequireReq);

    /**
     * 新增保函台账-列表导出
     * @param list 数据名称
     * @param title 文件名称
     * @param response response
     */
    void exportList(List<PoGuaranteeLetterRequireReq> list, String title, HttpServletResponse response);
}
