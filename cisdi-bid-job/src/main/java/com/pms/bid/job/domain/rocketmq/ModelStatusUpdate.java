package com.pms.bid.job.domain.rocketmq;

import lombok.Data;

@Data
public class ModelStatusUpdate {

    /**
     * 模型服务id
     */
    private Integer fid;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 状态
     * 10：解析中，15：解析失败，20：同步结构树，25：同步结构树异常，30：已发布，35：发布中，
     * 40：下载属性文件，45：下载属性文件失败，50：待发布，55：待映射，60：映射失败，65：发布失败，70：映射中
     */
    private Integer status;

    /**
     * 套图号
     */
    private String ttNumber;
}
