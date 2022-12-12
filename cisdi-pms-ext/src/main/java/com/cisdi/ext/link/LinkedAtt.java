package com.cisdi.ext.link;

import com.fasterxml.jackson.annotation.JsonView;
import com.qygly.shared.BaseOfBase;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.end.Front;

import java.util.List;

public class LinkedAtt extends BaseOfBase {

    /**
     * 属性类型。
     */
    @JsonView({Front.class})
    public AttDataTypeE type;

    /**
     * 属性值。
     */
    @JsonView({Front.class})
    public Object value;

    /**
     * 属性文本。
     */
    @JsonView({Front.class})
    public String text;

    @JsonView({Front.class})
    public List<LinkedAttFileInfo> fileInfoList;

    /**
     * 改变为名称。空不改变。
     */
    @JsonView({Front.class})
    public String changeToName;

    /**
     * 改变为是否显示。空不改变。
     */
    @JsonView({Front.class})
    public Boolean changeToShown;

    /**
     * 改变为是否可改。空不改变。
     */
    @JsonView({Front.class})
    public Boolean changeToEditable;

    /**
     * 改变为是否必填。空不改变。
     */
    @JsonView({Front.class})
    public Boolean changeToMandatory;
}
