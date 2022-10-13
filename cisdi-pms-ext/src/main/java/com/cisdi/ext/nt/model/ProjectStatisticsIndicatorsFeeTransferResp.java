package com.cisdi.ext.nt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProjectStatisticsIndicatorsFeeTransferResp
 * @package com.cisdi.ext.nt.model
 * @description 纳统报送报表数据返回
 * @date 2022/10/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProjectStatisticsIndicatorsFeeTransferResp {

    /** 指标ID */
    private String indicatorsId;

    /** 指标名称 */
    private String indicatorsName;

    /** 年份 */
    private Integer year;

    /** 月份 */
    private Integer month;

    /** 金额 */
    private BigDecimal fee;

    /** 累计金额 */
    private BigDecimal reduceFee;
}
