package com.cisdi.pms.job.domain.project;

import lombok.Data;

@Data
public class PmPrj {

    //id
    private String id;
    private String projectId;

    // 项目名称
    private String projectName;

    //是否符合开工条件 1符合0不符合
    private Integer izStart;

    //是否竣工 1已竣工 0未竣工
    private Integer izEnd;
}
