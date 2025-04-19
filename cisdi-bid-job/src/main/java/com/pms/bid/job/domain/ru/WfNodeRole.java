package com.pms.bid.job.domain.ru;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

@TableName("WF_NODE_ROLE")
@Data
public class WfNodeRole extends BaseDomain {

    private String  id;

    @TableField("NAME")
    private String  name;

    @TableField("WF_NODE_ROLE")
    private String  wfNodeRole;

    @TableField("AD_ROLE_ID")
    private String  adRoleId;

}
