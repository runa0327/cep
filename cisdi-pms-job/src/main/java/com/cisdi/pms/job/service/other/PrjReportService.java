package com.cisdi.pms.job.service.other;

import javax.servlet.http.HttpServletResponse;

public interface PrjReportService {

    /**
     * 工程项目信息协同系统效果评价指标 导出
     * @param response 响应
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    void downPrjUserMsg(HttpServletResponse response, String startTime, String endTime);
}
