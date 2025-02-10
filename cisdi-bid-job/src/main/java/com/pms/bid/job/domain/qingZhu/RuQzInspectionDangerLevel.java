package com.pms.bid.job.domain.qingZhu;

import com.baomidou.mybatisplus.annotation.TableName;
import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

/**
 * 轻筑-巡检性质
 */
@Data
@TableName("RU_QZ_INSPECTION_DANGER_LEVEL")
public class RuQzInspectionDangerLevel extends BaseDomain {

    private String id;

    private String name;

    private String remark;

    private Integer ruQzInspectionType;

}
