package com.cisdi.ext.demostration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProjectInfo
 * @package com.cisdi.ext.demostration
 * @description
 * @date 2023/2/1
 */
@Data
@Builder
@AllArgsConstructor
public class ProjectInfoTemp {

    private String name;

    private String unit;

    private String type;

    private BigDecimal amt;

    private BigDecimal rate;
}
