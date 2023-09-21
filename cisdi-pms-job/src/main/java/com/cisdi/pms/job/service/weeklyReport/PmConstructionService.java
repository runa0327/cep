package com.cisdi.pms.job.service.weeklyReport;

import com.cisdi.pms.job.domain.weeklyReport.PmConstruction;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface PmConstructionService {

    /**
     * 工程建安需求填报生成 定时任务
     */
    void generateJianAn();

    /**
     * 工程建安需求填报-月初待确认任务生成
     */
    void monthCheckAmt();

    /**
     * 工程建安费用需求填报统计
     * @param pmConstruction 实体信息
     * @return 查询结果
     */
    List<Map<String,Object>> queryPmConstructionList(PmConstruction pmConstruction);

    /**
     * 工程建安费用需求填报统计-导出
     */
    void downloadConstruction(List<Map<String, Object>> list, String title, HttpServletResponse response);
}
