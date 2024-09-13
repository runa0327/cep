package com.cisdi.cisdipreview.model;


import lombok.Data;

@Data
public class TranslateRequestBody {
    private Config config;
    private Source source;



    private String callback;

    // 构造函数
    public TranslateRequestBody(boolean toBimtiles, boolean compressed, String fileId, String callback) {
        this.config = new Config(toBimtiles);
        this.source = new Source(compressed, fileId);
        this.callback = callback;
    }

    // Config内部类
    public static class Config {
        private boolean toBimtiles;

        // 必须有一个公开的无参构造函数
        public Config() {
        }

        public Config(boolean toBimtiles) {
            this.toBimtiles = toBimtiles;
        }

        // 公开的getter方法
        public boolean isToBimtiles() {
            return toBimtiles;
        }

        // 公开的setter方法
        public void setToBimtiles(boolean toBimtiles) {
            this.toBimtiles = toBimtiles;
        }
    }

    // Source内部类
    public static class Source {
        private boolean compressed;
        private String fileId;

        // 必须有一个公开的无参构造函数
        public Source() {
        }

        public Source(boolean compressed, String fileId) {
            this.compressed = compressed;
            this.fileId = fileId;
        }

        // 公开的getter方法
        public boolean isCompressed() {
            return compressed;
        }

        public String getFileId() {
            return fileId;
        }

        // 公开的setter方法
        public void setCompressed(boolean compressed) {
            this.compressed = compressed;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }
    }

    // TranslateRequestBody的getter和setter方法
    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
