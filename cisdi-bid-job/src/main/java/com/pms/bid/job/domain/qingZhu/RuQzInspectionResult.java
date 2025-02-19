package com.pms.bid.job.domain.qingZhu;

import com.baomidou.mybatisplus.annotation.TableName;
import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;
import lombok.ToString;

/**
 * 轻筑-巡检性质
 */
@ToString
@Data
@TableName("RU_QZ_INSPECTION_RESULT")
public class RuQzInspectionResult extends BaseDomain {

    private String id;

    private String name;

    private String remark;


}
