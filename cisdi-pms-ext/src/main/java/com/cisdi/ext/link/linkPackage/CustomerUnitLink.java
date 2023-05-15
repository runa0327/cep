package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.link.AttLinkResult;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 业主单位属性联动
 */
public class CustomerUnitLink {

    /**
     * 业主单位数量联动处理逻辑
     * @param myJdbcTemplate 数据源
     * @param attValue 联动值
     * @param entCode 业务流程表名
     * @return 回显值
     */
    public static AttLinkResult linkCUSTOMER_UNIT_ONE(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();
        String userId = ExtJarHelper.loginInfo.get().userId;
        //通过选择业主单位变换发起人部门
        List<String> autoGetDept = AttLinkDifferentProcess.getAutoGetDept();
        if (autoGetDept.contains(entCode)){
            //每次切换清空原有数据
            AttLinkExtDetail.clearUser(attLinkResult);
            autoChangeDept(myJdbcTemplate,attValue,userId,attLinkResult);
        }
        return attLinkResult;
    }

    /**
     * 自动根据所选择的公司判断出对应的部门
     * @param myJdbcTemplate 数据源
     * @param attValue 属性联动值
     * @param userId 当前登录人
     * @param attLinkResult 返回结果
     */
    private static void autoChangeDept(MyJdbcTemplate myJdbcTemplate, String attValue, String userId, AttLinkResult attLinkResult) {
//        String value, id;
        //根据公司找到部门
        String sql1 = "select a.id,a.name from hr_dept a LEFT JOIN hr_dept_user b on a.id = b.HR_DEPT_ID where b.AD_USER_ID = ? and a.CUSTOMER_UNIT = ? and b.SYS_TRUE = 1";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,userId,attValue);
        if (CollectionUtils.isEmpty(list1)){
            sql1 = "select a.id,a.name from hr_dept a LEFT JOIN hr_dept_user b on a.id = b.HR_DEPT_ID where b.AD_USER_ID = ? and a.CUSTOMER_UNIT = ? and b.SYS_TRUE != 1 order by b.CRT_DT desc limit 1";
            list1 = myJdbcTemplate.queryForList(sql1,userId,attValue);
            if (CollectionUtils.isEmpty(list1)){
                throw new BaseException("对不起，您在该公司暂未有职位，请联系管理员维护职位信息！");
            }
        }
        AttLinkExtDetail.mapAddRefValue("CRT_DEPT_ID","id","name",list1.get(0),AttDataTypeE.TEXT_LONG,attLinkResult); //部门
//        id = JdbcMapUtil.getString(list1.get(0),"id");
//        value = JdbcMapUtil.getString(list1.get(0),"name");
        //部门
//        {
//            LinkedAtt linkedAtt = new LinkedAtt();
//            linkedAtt.type = AttDataTypeE.TEXT_LONG;
//            linkedAtt.value = id;
//            linkedAtt.text = value;
//            attLinkResult.attMap.put("CRT_DEPT_ID",linkedAtt);
//        }
    }
}
