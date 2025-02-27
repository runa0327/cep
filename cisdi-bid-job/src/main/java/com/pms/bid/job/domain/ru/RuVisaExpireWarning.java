package com.pms.bid.job.domain.ru;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

@TableName("RU_VISA_EXPIRE_WARNING")
@Data
public class RuVisaExpireWarning extends BaseDomain {

    private String  id;

    @TableField("CC_PRJ_ID")
    private String  projectId;

    @TableField("RU_LEVEL_OF_RISK_ID")
    private String  levelOfRiskId;

    @TableField("RU_ADVANCE_WARNING_DAYS")
    private Integer  advanceWarningDays;

    @TableField("CC_PRJ_MEMBER_ID")
    private String  prjMemberId;

}
