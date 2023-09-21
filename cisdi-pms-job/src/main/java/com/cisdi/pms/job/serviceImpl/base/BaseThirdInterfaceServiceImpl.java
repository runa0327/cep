package com.cisdi.pms.job.serviceImpl.base;

import com.cisdi.pms.job.commons.HttpClient;
import com.cisdi.pms.job.domain.notice.BaseThirdInterface;
import com.cisdi.pms.job.mapper.notice.BaseThirdInterfaceMapper;
import com.cisdi.pms.job.service.base.BaseThirdInterfaceService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BaseThirdInterfaceServiceImpl implements BaseThirdInterfaceService {

    @Resource
    private BaseThirdInterfaceMapper baseThirdInterfaceMapper;

    /**
     * 自动执行三方接口未执行成功的数据
     * 失败次数5次以下，创建时间1天以内 10分钟以前
     */
    @Override
    public void generateExecuteHttpThird() {
        List<BaseThirdInterface> list = baseThirdInterfaceMapper.queryUnExecuteThird();
        if (!CollectionUtils.isEmpty(list)){
            for (BaseThirdInterface tmp : list) {
                String method = tmp.getMethod();
                String param = tmp.getParam();
                String url = tmp.getUrl();
                new Thread(()->{
                    if ("GET".equals(method)){
                        HttpClient.doGet(url,param);
                    } else {
                        HttpClient.doPost(url,param,"UTF-8");
                    }
                }).start();
            }
        }
    }
}
