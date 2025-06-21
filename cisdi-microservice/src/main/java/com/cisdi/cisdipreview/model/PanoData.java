package com.cisdi.cisdipreview.model;

import lombok.Data;

@Data
public class PanoData {
    private String shareId;
    private String dataType;
    private String orgId;

    private boolean allFile;
}
