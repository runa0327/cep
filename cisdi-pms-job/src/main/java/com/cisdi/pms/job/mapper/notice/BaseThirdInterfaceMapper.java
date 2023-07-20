package com.cisdi.pms.job.mapper.notice;

import com.cisdi.pms.job.domain.notice.BaseThirdInterface;

public interface BaseThirdInterfaceMapper {

    /**
     * 根据第三方接口编码查询接口是否启用
     * @param interfaceCode 接口编码
     * @return 是否启用
     */
    BaseThirdInterface getSysTrue(String interfaceCode);
}
