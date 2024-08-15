package com.pms.bid.job.domain.zhanJiang;

import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

import java.util.Date;

/**
 * 预警
 */
@Data
public class CcReviewProgress extends BaseDomain {

    /**
     * id
     */
    private String id;

    /**
     * 填报内容
     */
    private String content;

    /**
     * 压力管道id
     */
    private String pipelineId;

    /**
     * 填报周期开始日期
     */
    private Date fillDateFor;
    /**
     * 填报周期结束日期
     */
    private Date fillDateTo;

    /**
     * 是否填报
     */
    private Integer isFilled;
}
