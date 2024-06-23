package com.pms.bid.job.domain.rocketmq;

import lombok.Data;

import java.util.List;

@Data
public class MQEngineeringMetricsResult {

    private String code;

    private List<EngineeringMetrics> data;
}
