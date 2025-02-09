package com.pms.bid.job.domain.qingZhu;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

/**
 * 轻筑-巡检项
 */
@Data
@TableName("RU_QZ_INSPECTION_ITEM")
public class RuQzInspectionItem extends BaseDomain {
    
    private String id;

    private String name;

    private String remark;

    private Integer ruQzInspectionType;

}
