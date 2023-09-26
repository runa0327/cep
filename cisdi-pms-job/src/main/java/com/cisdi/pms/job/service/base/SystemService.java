package com.cisdi.pms.job.service.base;

import java.util.Map;

public interface SystemService {

    /**
     * 根据周信息查询系统使用情况
     * @param weekId 周id
     * @return 周内系统使用情况
     */
    Map<String, Object> querySystemUsage(String weekId);
}
