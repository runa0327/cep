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
 * @title ProjectStatisticsManualFeeListResp
 * @package com.cisdi.ext.nt.model
 * @description
 * @date 2022/10/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProjectStatisticsManualFeeListResp {

    /** 项目ID */
    private String projectId;

    /** 年份 */
    private Integer year;

    /** 月份 */
    private Integer month;

    /** 是否已经存在数据 */
    private Integer existFlag;

    /**
     * 填报费用
     */
    private BigDecimal fee;
}
