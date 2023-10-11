package com.cisdi.pms.job.service.base;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface SystemService {

    /**
     * 根据周信息查询系统使用情况
     * @param weekId 周id
     * @return 周内系统使用情况
     */
    Map<String, Object> querySystemUsage(String weekId);

    /**
     * 系统使用情况导出
     * @param response 响应
     * @param map 数据详情
     * @param title 标题
     */
    void downloadSystemUsage(HttpServletResponse response, Map<String, Object> map, String title);
}
