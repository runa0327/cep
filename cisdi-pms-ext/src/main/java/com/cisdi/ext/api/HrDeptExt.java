package com.cisdi.ext.api;

import com.cisdi.ext.model.BaseCompanyProjecttype;
import com.cisdi.ext.model.view.base.HrDeptView;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 部门扩展
 */
public class HrDeptExt {

    /**
     * 根据部门id获取部门及子级部门信息
     * @param deptId 部门id
     * @param myJdbcTemplate 数据源
     * @return 部门信息
     */
    public static List<HrDeptView> getDeptListById(String deptId, MyJdbcTemplate myJdbcTemplate) {
        List<HrDeptView> deptList = new ArrayList<>();
        addChild(deptId,deptList,myJdbcTemplate);
        return deptList;
    }

    /**
     * 部门信息添加子级部门信息
     * @param parentId 父级id
     * @param deptList 最终返回list
     * @param myJdbcTemplate 数据源
     */
    private static void addChild(String parentId, List<HrDeptView> deptList,MyJdbcTemplate myJdbcTemplate) {
        List<Map<String,Object>> list = myJdbcTemplate.queryForList("select id,name from hr_dept where hr_dept_pid = ?",parentId);
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(p->{
                HrDeptView hrDeptView = new HrDeptView();
                String id = JdbcMapUtil.getString(p,"id");
                String name = JdbcMapUtil.getString(p,"name");
                hrDeptView.setId(id);
                hrDeptView.setName(name);
                deptList.add(hrDeptView);
                addChild(id,deptList,myJdbcTemplate);
            });
        }
    }

    /**
     * 根据部门名称查询人员信息
     * @param nameList 部门名称
     * @param myJdbcTemplate 数据源
     * @return 部门人员信息
     */
    public static List<String> getUserByNameLike(List<String> nameList, MyJdbcTemplate myJdbcTemplate) {
        List<String> userList = new ArrayList<>();
        String nameStr = String.join("','",nameList);
        List<Map<String,Object>> list = myJdbcTemplate.queryForList("select distinct a.ad_user_id from hr_dept_user a left join ad_user b on a.ad_user_id = b.id where a.status = 'ap' and b.status = 'ap' and a.hr_dept_id in (select id from hr_dept where name in ('"+nameStr+"'))");
        if (!CollectionUtils.isEmpty(list)){
            userList = list.stream().map(p->JdbcMapUtil.getString(p,"ad_user_id")).collect(Collectors.toList());
        }
        return userList;
    }

    /**
     * 部门信息
     */
    public void getDistinctDept(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select any_value(id) as deptId,name from hr_dept where status = 'ap' and CUSTOMER_UNIT = '0099902212142008831' group by name";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        Map<String, Object> result = new HashMap<>();
        result.put("deptList",list);
        ExtJarHelper.returnValue.set(result);
    }
}
