package com.pms.bid.job.domain.qbq;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;
import org.springframework.data.relational.core.sql.In;

/**
 * 设计变更
 */
@Data
public class QbqCallbackResponse  {

    private Integer code;

    private String msg;

    private Data data;

    @Data
    public static class data{

    }

}
