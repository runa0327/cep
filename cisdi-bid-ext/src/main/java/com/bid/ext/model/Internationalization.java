package com.bid.ext.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 国际化类
 */
@Data
public class Internationalization {

    /**
     * 英文
     */
    @JsonProperty("EN")
    private String en;

    /**
     * 简体中文
     */
    @JsonProperty("ZH_CN")
    private String ch_cn;

    /**
     * 繁体中文
     */
    @JsonProperty("ZH_TW")
    private String ch_tw;

    public Internationalization(){

    }

    public Internationalization(String en, String ch_cn, String ch_tw) {
        this.en = en;
        this.ch_cn = ch_cn;
        this.ch_tw = ch_tw;
    }
}
