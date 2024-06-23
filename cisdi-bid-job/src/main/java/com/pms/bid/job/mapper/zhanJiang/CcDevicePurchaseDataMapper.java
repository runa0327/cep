package com.pms.bid.job.mapper.zhanJiang;

import com.pms.bid.job.domain.zhanJiang.CcDevicePurchaseData;

public interface CcDevicePurchaseDataMapper {

    /**
     * 根据设备编号判断设备是否已存在
     * @param deviceTagNo 设备位号
     * @return 设备id
     */
    String queryIdByDeviceCode(String deviceTagNo);

    /**
     * 新增单条设备采购进度数据
     * @param ccDevicePurchaseData 设备采购进度数据实体
     */
    void create(CcDevicePurchaseData ccDevicePurchaseData);

    /**
     * 根据id动态修改 轻呈-设备采购进度数据 数据
     * @param ccDevicePurchaseData 设备采购进度数据实体
     */
    void updateConditionById(CcDevicePurchaseData ccDevicePurchaseData);
}
