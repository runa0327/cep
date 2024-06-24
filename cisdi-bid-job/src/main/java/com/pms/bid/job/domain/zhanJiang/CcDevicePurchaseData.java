package com.pms.bid.job.domain.zhanJiang;

import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

/**
 * 轻呈-设备采购进度数据
 */
@Data
public class CcDevicePurchaseData extends BaseDomain {

    /**
     * id
     */
    private String id;

    /**
     * 单元工程名称编码
     */
    private String unitProjectCode;

    /**
     * 单元工程名称
     */
    private String unitProjectName;

    /**
     * 设备安装进度状态 1已安装 0未安装
     */
    private Integer deviceInstallStatus;

    /**
     * 采购设备编号
     */
    private String purchaseDeviceNo;

    /**
     * 设备位号
     */
    private String deviceTagNo;

    /**
     * 从mq获取的消息实体
     */
    private String mqMsgJson;

    /**
     * 从mq获取消息的时间
     */
    private String mqReceiveDateTime;

    /**
     * 项目id
     */
    private String projectId;
}
