package com.bid.ext.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
/**
 * 勘察单位实体类
 */
public class SurveyContractor implements Serializable {
    /**
     * 勘察单位
     */
    private String surveyContractor;

    /**
     * 勘察单位负责人
     */
    private String surveyContractorChiefUserIds;
}