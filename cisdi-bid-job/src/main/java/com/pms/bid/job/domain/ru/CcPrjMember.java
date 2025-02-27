package com.pms.bid.job.domain.ru;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

@TableName("CC_PRJ_MEMBER")
@Data
public class CcPrjMember extends BaseDomain {

    private String  id;

    @TableField("NAME")
    private String  name;

    @TableField("AD_USER_ID")
    private String  adUserId;

}
