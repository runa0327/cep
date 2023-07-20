package com.cisdi.pms.job.mapper.notice;

import java.util.List;

public interface SmsWhiteListMapper {

    /**
     * 获取微信通知白名单
     * @return 白名单人员信息
     */
    List<String> getWxWhiteList();
}
