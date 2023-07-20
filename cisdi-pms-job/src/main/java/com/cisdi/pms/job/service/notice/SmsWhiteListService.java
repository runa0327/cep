package com.cisdi.pms.job.service.notice;

import java.util.List;

public interface SmsWhiteListService {

    /**
     * 获取微信通知白名单
     * @return 白名单人员信息
     */
    List<String> getWxWhiteList();
}
