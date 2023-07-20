package com.cisdi.pms.job.mapper.notice;

public interface BaseThirdInterfaceMapper {

    /**
     * 根据第三方接口编码查询接口是否启用
     * @param interfaceCode 接口编码
     * @return 是否启用
     */
    int getSysTrue(String interfaceCode);
}
