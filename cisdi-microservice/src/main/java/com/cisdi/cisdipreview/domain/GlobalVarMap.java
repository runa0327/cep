package com.cisdi.cisdipreview.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GlobalVarMap {
    /**
     * 全局项目变量
     */
    @JsonProperty("P_CC_PRJ_IDS")
    private String pCcPrjIds;
}
