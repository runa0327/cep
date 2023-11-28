package com.cisdi.ext.api;

import com.cisdi.ext.model.HrDept;
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
     *
     * @param deptId         部门id
     * @param myJdbcTemplate 数据源
     * @return 部门信息
     */
    public static List<HrDeptView> getDeptListById(String deptId, MyJdbcTemplate myJdbcTemplate) {
        List<HrDeptView> deptList = new ArrayList<>();
        addChild(deptId, deptList, myJdbcTemplate);
        return deptList;
    }

    /**
     * 部门信息添加子级部门信息
     *
     * @param parentId       父级id
     * @param deptList       最终返回list
     * @param myJdbcTemplate 数据源
     */
    private static void addChild(String parentId, List<HrDeptView> deptList, MyJdbcTemplate myJdbcTemplate) {
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select id,name from hr_dept where hr_dept_pid = ?", parentId);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(p -> {
                HrDeptView hrDeptView = new HrDeptView();
                String id = JdbcMapUtil.getString(p, "id");
                String name = JdbcMapUtil.getString(p, "name");
                hrDeptView.setId(id);
                hrDeptView.setName(name);
                deptList.add(hrDeptView);
                addChild(id, deptList, myJdbcTemplate);
            });
        }
    }

    /**
     * 根据部门名称查询人员信息
     *
     * @param nameList       部门名称
     * @param myJdbcTemplate 数据源
     * @return 部门人员信息
     */
    public static List<String> getUserByNameLike(List<String> nameList, MyJdbcTemplate myJdbcTemplate) {
        List<String> userList = new ArrayList<>();
        String nameStr = String.join("','", nameList);
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select distinct a.ad_user_id from hr_dept_user a left join ad_user b on a.ad_user_id = b.id where a.status = 'ap' and b.status = 'ap' and a.hr_dept_id in (select id from hr_dept where name in ('" + nameStr + "'))");
        if (!CollectionUtils.isEmpty(list)) {
            userList = list.stream().map(p -> JdbcMapUtil.getString(p, "ad_user_id")).collect(Collectors.toList());
        }
        return userList;
    }

    /**
     * 根据id查询名称
     * @param id id
     * @return 名称
     */
    public static String getNameById(String id, MyJdbcTemplate myJdbcTemplate) {
        String name = null;
        List<Map<String,Object>> list = myJdbcTemplate.queryForList("select name from hr_dept where id = ?",id);
        if (!CollectionUtils.isEmpty(list)){
            name = JdbcMapUtil.getString(list.get(0),"name");
        }
        return name;
    }

    /**
     * 根据内部管理单位及部门编码获取部门负责人
     * @param deptCode 部门编码
     * @param ancestorId 公司父级id
     * @param myJdbcTemplate 数据源
     * @return 部门负责人id
     */
    public static String getChiefUserByCompanyDept(String deptCode, String ancestorId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "SELECT CHIEF_USER_ID FROM hr_dept WHERE code = ? and id in (WITH RECURSIVE department_hierarchy AS ( SELECT id, hr_dept_pid FROM hr_dept WHERE hr_dept_pid = ? UNION ALL SELECT d.id, d.hr_dept_pid FROM department_hierarchy dh JOIN hr_dept d ON d.hr_dept_pid = dh.id ) SELECT id FROM department_hierarchy ORDER BY id)";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,deptCode,ancestorId);
        if (CollectionUtils.isEmpty(list)){
            return null;
        } else {
            return JdbcMapUtil.getString(list.get(0),"CHIEF_USER_ID");
        }
    }

    /**
     * 部门信息
     */
    public void getDistinctDept() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select any_value(id) as deptId,name from hr_dept where status = 'ap' and CUSTOMER_UNIT = '0099902212142008831' group by name";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql);
        Map<String, Object> result = new HashMap<>();
        result.put("deptList", list);
        ExtJarHelper.returnValue.set(result);
    }


    /**
     * 查询内部管理单位
     */
    public void interiorCompany() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select id,`NAME` from hr_dept where id in ('0099799190825079019','1650795835505721344')");
        Map<String, Object> result = new HashMap<>();
        result.put("deptList", list);
        ExtJarHelper.returnValue.set(result);
    }



    /**
     * 根据父级部门id获取所有子级部门id
     * @param deptList 父级部门集合
     * @return 左右子级部门
     */
    public static List<HrDept> getChildList(List<HrDept> deptList) {
        List<HrDept> childList = new ArrayList<>();
        foreachGetChild(childList,deptList);
        return childList;
    }

    /**
     * 遍历循环获取子级部门
     * @param childList 返回的list
     * @param deptList 父级部门
     */
    private static void foreachGetChild(List<HrDept> childList, List<HrDept> deptList) {
        for (HrDept hrDept : deptList) {
            String parentId = hrDept.getId();
            List<HrDept> list = HrDept.selectByWhere(new Where().eq(HrDept.Cols.HR_DEPT_PID,parentId).eq(HrDept.Cols.STATUS,"AP"));
            if (!CollectionUtils.isEmpty(list)){
                childList.addAll(list);
                foreachGetChild(childList,list);
            }
        }
    }

    /**
     * 根据部门id查询部门人员信息
     * @param deptIdList 部门id列表
     * @param myJdbcTemplate 数据源
     * @return 部门人员信息
     */
    public static List<String> getUserByDeptId(List<String> deptIdList, MyJdbcTemplate myJdbcTemplate) {
        String id = String.join("','",deptIdList);
        String sql = "select distinct a.ad_user_id from hr_dept_user a left join ad_user b on a.ad_user_id = b.id where a.hr_dept_id in ('"+id+"') and b.status = 'AP' and b.name != '未涉及'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        if (!CollectionUtils.isEmpty(list)){
            return list.stream().map(p->JdbcMapUtil.getString(p,"ad_user_id")).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
