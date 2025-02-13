package com.bid.ext.cc;

import com.bid.ext.model.CcPrjStructNodeDir;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
public class DesignManagementExt {

    /**
     * 新建设计计划模板
     */
    public void saveDesignPlanTemplate(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;//当前操作用户
        //获取页面变量
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        String ccPrjWbsTypeId = JdbcMapUtil.getString(varMap, "P_CC_PRJ_WBS_TYPE_ID");//计划类型
        String ccPrjTypeId = JdbcMapUtil.getString(varMap, "P_CC_PRJ_TYPE_ID");//工程类型

        CcPrjStructNodeDir ccPrjStructNodeDir = CcPrjStructNodeDir.newData();

        ccPrjStructNodeDir.setTs(LocalDateTime.now());//时间戳
        ccPrjStructNodeDir.setCrtDt(LocalDateTime.now());//创建时间
        ccPrjStructNodeDir.setCrtUserId(userId);//创建用户
        ccPrjStructNodeDir.setLastModiDt(LocalDateTime.now());//最后修改日期时间
        ccPrjStructNodeDir.setLastModiUserId(userId);//最后修改用户
        ccPrjStructNodeDir.setCcPrjWbsTypeId(ccPrjWbsTypeId);//计划类型
        ccPrjStructNodeDir.setCcPrjTypeId(ccPrjTypeId);//工程类型
        ccPrjStructNodeDir.setStatus("AP"); // 状态

        ccPrjStructNodeDir.insertById();//插入数据

        //刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }
}
