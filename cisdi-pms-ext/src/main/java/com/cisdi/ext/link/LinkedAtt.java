package com.cisdi.ext.link;

import com.fasterxml.jackson.annotation.JsonView;
import com.qygly.shared.BaseOfBase;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.end.B;
import com.qygly.shared.end.F;

public class LinkedAtt extends BaseOfBase {

    /**
     * 属性类型。
     */
    @JsonView({F.class, B.class})
    public AttDataTypeE type;

    /**
     * 属性值。
     */
    @JsonView({F.class, B.class})
    public Object value;

    /**
     * 属性文本。
     */
    @JsonView({F.class, B.class})
    public String text;

    /**
     * 改变为名称。空不改变。
     */
    @JsonView({F.class, B.class})
    public String changeToName;

    /**
     * 改变为是否显示。空不改变。
     */
    @JsonView({F.class, B.class})
    public Boolean changeToShown;

    /**
     * 改变为是否可改。空不改变。
     */
    @JsonView({F.class, B.class})
    public Boolean changeToEditable;

    /**
     * 改变为是否必填。空不改变。
     */
    @JsonView({F.class, B.class})
    public Boolean changeToMandatory;
}
