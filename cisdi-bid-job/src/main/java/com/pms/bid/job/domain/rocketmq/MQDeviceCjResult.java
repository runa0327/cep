package com.pms.bid.job.domain.rocketmq;

import lombok.Data;

import java.util.List;

@Data
public class MQDeviceCjResult {

    private String code;

    private List<DeviceMsg> data;
}
