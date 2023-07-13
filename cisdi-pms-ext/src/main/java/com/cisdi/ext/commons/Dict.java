package com.cisdi.ext.commons;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dlt
 * @date 2022/10/14 周五
 */
public class Dict {
    /**
     * 字典
     */
    public void getDict(){
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> dictList = myJdbcTemplate.queryForList("select sv.id,sv.name,sv.remark from gr_set se left join gr_set_value sv on sv.gr_set_id" +
                " = se.id where se.code = ? and se.status = 'AP' and sv.status = 'AP'", inputMap.get("code").toString());
        HashMap<String, Object> result = new HashMap<>();
        result.put("dict",dictList);
        ExtJarHelper.returnValue.set(result);
    }

    /**
     * 人员下拉
     */
    public void userDrop(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> userList = myJdbcTemplate.queryForList("select id,name from ad_user where status = 'AP'");
        HashMap<String, Object> result = new HashMap<>();
        result.put("userList",userList);
        ExtJarHelper.returnValue.set(result);
    }

    /**
     * 三亚崖州湾科技城开发建设有限公司
     */
    public void devCompanyDeptDrop(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> deptList = myJdbcTemplate.queryForList("select d.id,d.name from hr_dept d left join hr_dept_user du on du.HR_DEPT_ID " +
                "= d.id where d.status = 'AP' and du.id is not null and d.CUSTOMER_UNIT = '0099902212142008831' group by d.id");
        HashMap<String, Object> result = new HashMap<>();
        result.put("deptList",deptList);
        ExtJarHelper.returnValue.set(result);
    }
}
