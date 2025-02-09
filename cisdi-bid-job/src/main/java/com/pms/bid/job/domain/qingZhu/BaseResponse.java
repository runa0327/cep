package com.pms.bid.job.domain.qingZhu;

import lombok.Data;

@Data
public class BaseResponse {

    private Integer code;

    private String  message;

    private Object data;
}
