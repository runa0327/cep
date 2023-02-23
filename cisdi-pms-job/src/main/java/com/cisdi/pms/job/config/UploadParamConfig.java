package com.cisdi.pms.job.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author dlt
 * @date 2023/2/15 周三
 */
@Data
@Configuration
@ConfigurationProperties("history-data")
public class UploadParamConfig {
    //请求路径
    private String requestPath;
    //文件字段
    private String fileGroupAttCode;
    //实体视图id
    private String sevId;
    //非必填
    private String erId;
}
