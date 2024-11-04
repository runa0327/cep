package com.bid.ext.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
/**
 * 建设单位实体类
 */
public class ProjectOwner implements Serializable {
    /**
     * 建设单位
     */
    private String projectOwner;

    /**
     * 建设单位负责人
     */
    private String projectOwnerChiefUserIds;
}
