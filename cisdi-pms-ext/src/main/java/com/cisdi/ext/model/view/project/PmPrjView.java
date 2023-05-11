package com.cisdi.ext.model.view.project;

import com.cisdi.ext.model.BasePageEntity;

public class PmPrjView extends BasePageEntity {

    //id
    public String id;
    public String projectId;
    //名称
    public String projectName;
    //是否符合开工条件 1符合0不合格
    public Integer weatherStart;
    //是否竣工 1已竣工0未竣工
    public Integer weatherCompleted;
}
