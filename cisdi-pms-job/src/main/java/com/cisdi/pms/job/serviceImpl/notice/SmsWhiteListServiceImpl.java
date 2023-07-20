package com.cisdi.pms.job.serviceImpl.notice;

import com.cisdi.pms.job.mapper.notice.SmsWhiteListMapper;
import com.cisdi.pms.job.service.notice.SmsWhiteListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SmsWhiteListServiceImpl implements SmsWhiteListService {

    @Resource
    private SmsWhiteListMapper smsWhiteListMapper;

    /**
     * 获取微信通知白名单
     * @return 白名单人员信息
     */
    @Override
    public List<String> getWxWhiteList() {
        return smsWhiteListMapper.getWxWhiteList();
    }
}
