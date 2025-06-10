package com.cisdi.cisdipreview.model;

import lombok.Data;

import java.util.List;

@Data
public class PanoData {
    private String shareId;
    private String dataType;
    private String orgId;

    private boolean allFile;
    private List<String> selectedFolders;
}
