package com.cisdi.pms.job.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title WeeklyEnum
 * @package com.cisdi.pms.job.enums
 * @description 报告类型
 * @date 2022/10/18
 */
@Getter
@AllArgsConstructor
public enum WeeklyEnum {
    weekly_report("weekly_report", "周报"),
    montly_report("montly_report", "月报"),
    quarterly_report("quarterly_report", "季报");

    private String code;
    private String des;

}
