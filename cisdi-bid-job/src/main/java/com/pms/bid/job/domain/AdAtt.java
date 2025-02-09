package com.pms.bid.job.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("AD_ATT")
@Data
public class AdAtt extends  BaseDomain{

    private String  id;

    @TableField("FILE_PATH_ID")
    private String filePathId;

}
