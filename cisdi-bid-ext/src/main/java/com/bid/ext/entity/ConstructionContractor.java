package com.bid.ext.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
/**
 * 施工单位实体类
 */
public class ConstructionContractor implements Serializable {
    /**
     * 施工单位
     */
    private String constructionContractor;

    /**
     * 施工单位负责人
     */
    private String constructionContractorChiefUserIds;
}