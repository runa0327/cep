package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmDateTimeModel
 * @package com.cisdi.pms.job.excel.model
 * @description
 * @date 2023/8/30
 */
@Data
public class PmDateTimeModel {

    @ExcelProperty("项目名称")
    public String projectName;
    @ExcelProperty("开工日期")
    public String begin;
    @ExcelProperty("完工日期")
    public String end;
}
