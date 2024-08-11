package com.pms.bid.job.domain.zhanJiang;

import lombok.Data;

import java.util.Date;

/**
 * 预警
 */
@Data
public class CcEarlyWarning {

    private String id;

    /**
     * 版本
     */
    private String  ver;

    /**
     * 时间戳
     */
    private String ts;

    /**
     * 是否预设
     */
    private int isPreset;

    /**
     * 创建日期时间
     */
    private String crtDt;

    /**
     * 创建用户
     */
    private String crtUserId;

    /**
     * 最后修改日期时间
     */
    private String lastModiDt;

    /**
     * 最后修改用户
     */
    private String lastModiUserId;

    /**
     * 记录状态
     */
    private String status;

    /**
     * 锁定流程实例
     */
    private String lkWfInstId;

    /**
     * 代码
     */
    private String code;

    /**
     * 快捷码
     */
    private String fastCode;

    /**
     * 图标
     */
    private String iconFileGroupId;

    /**
     * 消息
     */
    private String name;

    /**
     * 用户
     */
    private String  adUserIds;


}
