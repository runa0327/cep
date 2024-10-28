package com.bid.ext.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
/**
 * 监理单位实体类
 */
public class SupervisingContractor implements Serializable {
    /**
     * 监理单位
     */
    private String supervisingContractor;

    /**
     * 监理单位负责人
     */
    private String supervisingContractorChiefUserIds;
}