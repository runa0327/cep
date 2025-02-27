package com.pms.bid.job.domain.ru;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 俄罗斯-人员入场信息
 */
@Data
@TableName("RU_USER_ENTRY_INFO")
public class RuEmployeeEntryInfo extends BaseDomain {
    
    private String id;

    private String name;

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("RU_USER_ENTER_A_COUNTRY_DATE")
    private Date enterACountryDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("RU_USER_EXIT_A_COUNTRY_DATE")
    private Date exitACountryDate;

    @TableField("CC_PRJ_ID")
    private String projectId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("RU_USER_TIME_OF_PROCESSING_LANDING_VISA")
    private Date timeProcessingLandingVisa;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("RU_USER_VISA_EXPIRATION_DATE")
    private Date  visaExpirationDate;

}
