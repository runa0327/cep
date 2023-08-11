package com.cisdi.pms.job.service.process;

import com.cisdi.pms.job.domain.process.PoGuaranteeLetterReturnReq;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface PoGuaranteeLetterReturnReqService {

    /**
     * 查询符合条件的所有数据
     * @param poGuaranteeLetterReturnReq poGuaranteeLetterReturnReq实体
     * @return 查询结果
     */
    List<PoGuaranteeLetterReturnReq> getAllList(PoGuaranteeLetterReturnReq poGuaranteeLetterReturnReq);

    /**
     * 保函退还导出
     * @param list 数据详细
     * @param response 响应
     * @param title 文件名
     */
    void exportListMsg(List<PoGuaranteeLetterReturnReq> list, HttpServletResponse response, String title);

    /**
     * 保函全字段导出
     * @param list 数据详细
     * @param response 响应
     * @param title 文件名
     */
    void exportAllMsg(List<PoGuaranteeLetterReturnReq> list, HttpServletResponse response, String title);
}
