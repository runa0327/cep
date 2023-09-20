package com.cisdi.pms.job.service.base;

public interface BaseThirdInterfaceService {

    /**
     * 自动执行三方接口未执行成功的数据
     * 失败次数5次以下，创建时间1天以内 10分钟以前
     */
    void generateExecuteHttpThird();
}
