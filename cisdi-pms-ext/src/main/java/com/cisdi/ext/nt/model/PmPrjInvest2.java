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
 * @title PmPrjInvest2
 * @package com.cisdi.ext.nt.model
 * @description
 * @date 2022/10/13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PmPrjInvest2 {

    private String pmPrjId;
    private BigDecimal prjTotalInvest;
    private BigDecimal projectAmt;
    private BigDecimal constructAmt;
    private BigDecimal equipAmt;
    private BigDecimal projectOtherAmt;
    private BigDecimal landAmt;
    private BigDecimal prepareAmt;
    private BigDecimal constructPeriodInterest;
}
