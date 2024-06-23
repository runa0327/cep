package com.pms.bid.job.domain.rocketmq;

import lombok.Data;

@Data
public class DeviceMsg {

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 设备位号
     */
    private String deviceTagNo;

    /**
     * 设备状态
     * 0-设备未订货、1-设备已发订货清单、2-合同已签订、3-设备已到货、4-设备已安装
     */
    private Integer deviceStatus;

    /**
     * 单元工程名称
     */
    private String unitProjectName;

    /**
     * 单元工程编码
     */
    private String unitProjectCode;
}
