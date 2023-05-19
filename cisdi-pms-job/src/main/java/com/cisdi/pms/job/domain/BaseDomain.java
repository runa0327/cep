package com.cisdi.pms.job.domain;

import lombok.Data;

@Data
public class BaseDomain {

    //版本
    private String ver;

    //时间戳
    private String ts;

    //创建时间
    private String crtDt;

    //创建人
    private String crtUserId;

    //上次修改时间
    private String lastModiDt;

    //上次修改人
    private String lastModiUserId;

}
