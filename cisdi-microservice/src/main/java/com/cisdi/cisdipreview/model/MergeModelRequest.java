package com.cisdi.cisdipreview.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MergeModelRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("sources")
    private List<Source> sources;

    @JsonProperty("callback")
    private String callback;

    // 构造方法
    public MergeModelRequest() {
    }

    public MergeModelRequest(String name, List<Source> sources, String callback) {
        this.name = name;
        this.sources = sources;
        this.callback = callback;
    }

    // Getter 和 Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    // Source 内部类
    public static class Source {
        @JsonProperty("fileId")
        private Long fileId;

        public Source() {
        }

        public Source(Long fileId) {
            this.fileId = fileId;
        }

        public Long getFileId() {
            return fileId;
        }

        public void setFileId(Long fileId) {
            this.fileId = fileId;
        }
    }
}

