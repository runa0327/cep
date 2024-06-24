package com.pms.bid.job.domain.zhanJiang;

import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

@Data
public class CcDrawingManagement extends BaseDomain {

    /**
     * id
     */
    private String id;

    /**
     * 套图号
     */
    private String ccConstructionDrawingId;

    /**
     * 湛江钢铁业主图号
     */
    private String ccSteelOwnerDrawingId;

    /**
     *  发图状态    DONE=已完成    TODO=未完成
     */
    private String ccDrawingStatusId;

    /**
     * 三维实际日期
     */
    private String threeDActDate;

    /**
     * 从mq获取的消息实体
     */
    private String mqMsgJson;

    /**
     * 从mq获取消息的时间
     */
    private String mqReceiveDateTime;

    /**
     * 单元工程名称编码
     */
    private String unitProjectCode;

    /**
     * 单元工程名称
     */
    private String unitProjectName;

    /**
     * 单元工程id
     */
    private String unitProjectId;
}
