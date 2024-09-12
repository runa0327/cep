package com.pms.bid.job.domain.zhanJiang;

import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;
import org.springframework.data.relational.core.sql.In;

/**
 * 特种设备
 */
@Data
public class CcSpecialEquip extends BaseDomain {

    private String id;

    /**
     * 锁定流程实例
     */
    private String lkWfInstId;

    /**
     * 快捷码
     */
    private String fastCode;

    /**
     * 图标
     */
    private String iconFileGroupId;

    /**
     * 名称
     */
    private String name;

    /**
     * 督办人
     */
    private String supervisorId;

    /**
     * 逾期提醒天数
     */
    private Integer slippageWarningDays;

    /**
     * 施工责任人
     */
    private  String  conHeadId;

    /**
     * 登记办理责任人
     */
    private String regProHeadId;

}
