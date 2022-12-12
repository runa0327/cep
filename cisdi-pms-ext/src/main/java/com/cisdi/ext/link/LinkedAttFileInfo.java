package com.cisdi.ext.link;

import com.fasterxml.jackson.annotation.JsonView;
import com.qygly.shared.BaseInfo;
import com.qygly.shared.annotation.DbCol;
import com.qygly.shared.end.Front;

public class LinkedAttFileInfo extends BaseInfo {
    private static final long serialVersionUID = 0L;
    @DbCol(
            code = "EXT"
    )
    @JsonView({Front.class})
    public String ext;
    @DbCol(
            code = "SIZE_KB"
    )
    @JsonView({Front.class})
    public Double sizeKiloByte;
    @DbCol(
            code = "UPLOAD_DTTM"
    )
    @JsonView({Front.class})
    // public Date uploadDttm;
    public String uploadDttm;
    @DbCol(
            code = "FILE_INLINE_URL"
    )
    @JsonView({Front.class})
    public String inlineUrl;
    @DbCol(
            code = "FILE_ATTACHMENT_URL"
    )
    @JsonView({Front.class})
    public String attachmentUrl;
    @DbCol(
            code = "DSP_NAME"
    )
    @JsonView({Front.class})
    public String dspName;
    @DbCol(
            code = "DSP_SIZE"
    )
    @JsonView({Front.class})
    public String dspSize;
    @DbCol(
            code = "PHYSICAL_LOCATION"
    )
    @JsonView({Front.class})
    public String physicalLocation;
    @DbCol(
            code = "CRT_USER_CODE"
    )
    @JsonView({Front.class})
    public String crtUserCode;
    @DbCol(
            code = "CRT_USER_NAME"
    )
    @JsonView({Front.class})
    public String crtUserName;

    public LinkedAttFileInfo() {
    }
}
