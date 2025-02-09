package com.pms.bid.job.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("FL_PATH")
@Data
public class FlPath extends  BaseDomain{

    private String  id;

    @TableField("FILE_ATTACHMENT_URL")
    private String  fileAttachmentUrl;

}
