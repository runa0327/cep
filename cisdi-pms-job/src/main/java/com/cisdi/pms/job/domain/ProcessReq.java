package com.cisdi.pms.job.domain;

import lombok.Data;

/**
 * @author dlt
 * @date 2023/3/21 周二
 */
@Data
public class ProcessReq {
    //开始日期
    private String startDate;

    //结束日期
    private String endDate;
}
