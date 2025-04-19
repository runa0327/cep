package com.pms.bid.job.domain.ru;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

@TableName("AD_USER")
@Data
public class AdRole extends BaseDomain {

    private String  id;

    @TableField("NAME")
    private String  name;

    @TableField("EXTRA_INFO")
    private String  extraInfo;

}
