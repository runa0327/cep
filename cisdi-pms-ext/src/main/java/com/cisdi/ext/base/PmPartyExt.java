package com.cisdi.ext.base;

import com.cisdi.ext.model.PmParty;
import com.cisdi.ext.pm.PmDeptExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 合作方扩展
 */
@Slf4j
public class PmPartyExt {

    /**
     * 根据合作方id查询名称
     * @param id 合作方id
     * @return 合作方名称
     */
    public static String getNameById(String id) {
        PmParty pmParty = PmParty.selectById(id);
        if (pmParty != null){
            return pmParty.getName();
        } else {
            return null;
        }
    }

    /**
     * 新增合作方
     */
    public void cloneParty(){
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取业主单位
        String id = JdbcMapUtil.getString(entityRecord.valueMap,"ID");
        String name = JdbcMapUtil.getString(entityRecord.valueMap,"NAME");
        log.info("id:{},名称：{}",id,name);
        String customer = JdbcMapUtil.getString(entityRecord.valueMap,"IS_CUSTOMER");
        if ("true".equals(customer)){ //克隆业主单位相关信息(根据 三亚崖州湾科技城开发建设有限公司 业主单位进行克隆)
            //克隆所选业主单位部门信息
            PmDeptExt.cloneCustomer(id,name);
            //克隆流程节点岗位信息 业主单位+岗位
            BaseProcessPostExt.cloneProcessPostCompany(id);
            //岗位默认人员配置
            BaseProcessPostExt.cloneProcessPostNormalUser(id);
            //流程岗位项目岗位关联关系
            BaseProcessPostExt.clonePostDept(id);
        }
    }
}
