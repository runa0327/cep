package com.cisdi.ext.model.view.file;

import lombok.Data;

@Data
public class FlFile {

    private String ID;
    private String CODE;
    private String NAME;
    private String REMARK;
    private String VER;
    private String FL_PATH_ID;
    private String EXT;
    private String LK_WF_INST_ID;
    private String STATUS;
    private String CRT_DT;
    private String CRT_USER_ID;
    private String LAST_MODI_DT;
    private String LAST_MODI_USER_ID;
    private String SIZE_KB;
    private String IS_PRESET;
    private String FILE_INLINE_URL;
    private String FILE_ATTACHMENT_URL;
    private String TS;
    private String UPLOAD_DTTM;
    private String PHYSICAL_LOCATION;
    private String DSP_NAME;
    private String DSP_SIZE;
    private String IS_PUBLIC_READ;
    private String DIR;
}
