package com.pms.bid.job.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.relational.core.sql.In;

@TableName("FL_FILE")
@Data
public class FlFile extends  BaseDomain{

    private String  id;

    @TableField("`NAME`")
    private String name;

    @TableField("FL_PATH_ID")
    private String flPathId;

    @TableField("FILE_INLINE_URL")
    private String fileInlineUrl;

    @TableField("FILE_ATTACHMENT_URL")
    private String fileAttachmentUrl;

    @TableField("UPLOAD_DTTM")
    private String uploadDttm;

    @TableField("IS_PUBLIC_READ")
    private Integer isPublicRead;

    @TableField("ORIGIN_FILE_PHYSICAL_LOCATION")
    private String originFilePhysicalLocation;

    @TableField("PHYSICAL_LOCATION")
    private String physicalLocation;

    @TableField("SIZE_KB")
    private Double sizeKb;

    @TableField("DSP_NAME")
    private String dspName;

    @TableField("DSP_SIZE")
    private String dspSize;

}
