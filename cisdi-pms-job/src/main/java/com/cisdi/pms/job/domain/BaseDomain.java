package com.cisdi.pms.job.domain;

import lombok.Data;

@Data
public class BaseDomain {

    //版本
    private String ver;

    //时间戳
    private String ts;

    //创建时间
    private String createDate;

    //创建人
    private String createUserId;

    //上次修改时间
    private String lastUpdateDate;

    //上次修改人
    private String lastUpdateUserId;

}
