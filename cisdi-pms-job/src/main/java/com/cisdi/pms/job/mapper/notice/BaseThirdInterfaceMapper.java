package com.cisdi.pms.job.mapper.notice;

import com.cisdi.pms.job.domain.notice.BaseThirdInterface;

import java.util.List;

public interface BaseThirdInterfaceMapper {

    /**
     * 根据第三方接口编码查询接口是否启用
     * @param interfaceCode 接口编码
     * @return 是否启用
     */
    BaseThirdInterface getSysTrue(String interfaceCode);

    /**
     * 根据id动态修改数据
     * @param baseThirdInterface 实体信息
     */
    void updateConditionById(BaseThirdInterface baseThirdInterface);

    /**
     * 自动执行三方接口未执行成功的数据
     * 失败次数5次以下，创建时间1天以内 10分钟以前
     * @return 查询结果
     */
    List<BaseThirdInterface> queryUnExecuteThird();

    /**
     * 获取该接口调用失败次数
     * @param interfaceId 接口id
     * @return 失败次数
     */
    Integer getFailNumsById(String interfaceId);
}
