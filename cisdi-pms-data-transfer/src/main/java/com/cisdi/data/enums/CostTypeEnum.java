package com.cisdi.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title CostTypeEnum
 * @package com.cisdi.data.enums
 * @description
 * @date 2022/9/1
 */
@Getter
@AllArgsConstructor
public enum CostTypeEnum {

    PROJECT_AMT("PROJECT_AMT", "工程费用"),

    CONSTRUCT_AMT("CONSTRUCT_AMT", "建筑安装工程费"),

    EQUIP_AMT("EQUIP_AMT", "设备及工器具采购费"),

    PROJECT_OTHER_AMT("PROJECT_OTHER_AMT", "工程建设其他费用"),

    PREPARE_AMT("PREPARE_AMT", "预备费"),

    LAND_AMT("LAND_AMT", "土地费用"),

    CONSTRUCT_PERIOD_INTEREST("CONSTRUCT_PERIOD_INTEREST", "建设期利息");

    private String code;

    private String des;
}
