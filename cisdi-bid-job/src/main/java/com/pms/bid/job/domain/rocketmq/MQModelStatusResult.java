package com.pms.bid.job.domain.rocketmq;

import lombok.Data;

@Data
public class MQModelStatusResult {

    private String code;

    private ModelStatusUpdate data;
}
