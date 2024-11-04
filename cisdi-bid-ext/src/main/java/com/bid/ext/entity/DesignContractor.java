package com.bid.ext.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
/**
 * 设计单位实体类
 */
public class DesignContractor implements Serializable {
    /**
     * 设计单位
     */
    private String designContractor;

    /**
     * 设计单位负责人
     */
    private String designContractorChiefUserIds;
}