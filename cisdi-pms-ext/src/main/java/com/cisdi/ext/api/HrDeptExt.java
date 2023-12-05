package com.cisdi.ext.api;

import com.cisdi.ext.model.HrDept;
import com.cisdi.ext.model.HrDeptUser;
import com.cisdi.ext.model.view.base.HrDeptView;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
     * 根据用户id获取部门负责人
     * @param userId 用户id
     * @param myJdbcTemplate 数据源
     * @return 部门负责人id集合
     */
    public static ArrayList<String> getDeptChiefUserByUserIds(String userId, MyJdbcTemplate myJdbcTemplate) {
        List<Map<String, Object>> list = getCompany(myJdbcTemplate);
        // 遍历获取人员所在公司及部门
        for (Map<String, Object> map : list) {
            String companyId = JdbcMapUtil.getString(map, "id");
            List<String> deptIdList = getCompanyChildDeptId(companyId, myJdbcTemplate);
            String deptIdStr = String.join("','", deptIdList);
            List<Map<String, Object>> deptList = myJdbcTemplate.queryForList("select b.CHIEF_USER_ID from hr_dept_user a left join hr_dept b on a.hr_dept_id = b.id where a.ad_user_id in ('"+userId+"') and a.status = 'ap' and a.hr_dept_id in ('"+deptIdStr+"') and b.status = 'ap' order by a.SYS_TRUE desc limit 1", userId);
            if (!CollectionUtils.isEmpty(deptList)) {
                return deptList.stream().map(p -> JdbcMapUtil.getString(p, "CHIEF_USER_ID")).collect(Collectors.toCollection(ArrayList::new));
            }
        }
        return null;
    }

    /**
     * 根据用户id查询所属部门
     * @param userId 用户id
     * @param myJdbcTemplate 数据源
     * @return 部门id
     */
    public static String getDeptIdByUser(String userId, MyJdbcTemplate myJdbcTemplate){
        String deptId = "";
        List<HrDeptUser> hrDeptUserList = HrDeptUser.selectByWhere(new Where()
                .eq(HrDeptUser.Cols.STATUS,"AP")
                .eq(HrDeptUser.Cols.AD_USER_ID,userId));
        if (!CollectionUtils.isEmpty(hrDeptUserList)){
            List<String> deptList = hrDeptUserList.stream().map(HrDeptUser::getHrDeptId).distinct().collect(Collectors.toList());
            if (deptList.size() > 1){
                List<Map<String, Object>> list = getCompany(myJdbcTemplate);
                List<String> companyIdList = list.stream().map(p->JdbcMapUtil.getString(p,"id")).collect(Collectors.toList());
                List<String> newDeptList = new ArrayList<>(); // 公司下所有部门
                companyIdList.forEach(p->{
                    newDeptList.addAll(getCompanyChildDeptId(p,myJdbcTemplate));
                });
                String deptIdStr = String.join("','",newDeptList);
                List<Map<String,Object>> list2 = myJdbcTemplate.queryForList("select hr_dept_id from hr_dept_user a left join hr_dept b on a.hr_dept_id = b.id where a.status = 'ap' and b.status = 'ap' and a.hr_dept_id in ('"+deptIdStr+"') order by SYS_TRUE desc limit 1");
                deptId = JdbcMapUtil.getString(list2.get(0),"hr_dept_id");
            } else {
                deptId = deptList.get(0);
            }
        }
        return deptId;
    }

    /**
     * 根据部门负责人id获取该部门编码
     * @param userId 部门负责人id
     * @param myJdbcTemplate 数据源
     * @return 部门编码
     */
    public static List<String> getDeptChiefApprovalCodeByUser(String userId, MyJdbcTemplate myJdbcTemplate) {
        List<Map<String, Object>> list = getCompany(myJdbcTemplate);
        List<String> deptList = getCompanyChildDeptId(list,myJdbcTemplate);
        String deptIdStr = String.join("','",deptList);
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList("select distinct code from hr_dept where CHIEF_USER_ID = ? and id in ('"+deptIdStr+"')",userId);
        if (!CollectionUtils.isEmpty(list2)){
            return list2.stream().filter(p-> StringUtils.hasText(JdbcMapUtil.getString(p,"code"))).map(p->JdbcMapUtil.getString(p,"code")).collect(Collectors.toList());
        } else {
            return null;
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
     * 查询内部管理单位/公司
     */
    public void interiorCompany() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = getCompany(myJdbcTemplate);
        Map<String, Object> result = new HashMap<>();
        result.put("deptList", list);
        ExtJarHelper.returnValue.set(result);
    }

    /**
     * 获取内部管理单位
     * @param myJdbcTemplate 数据源
     * @return 内部管理单位
     */
    private static List<Map<String, Object>> getCompany(MyJdbcTemplate myJdbcTemplate) {
        return myJdbcTemplate.queryForList("select id,`NAME` from hr_dept where id in ('0099799190825079019','1650795835505721344')");
    }

    /**
     * 根据父级id获取所有子级部门id
     * @param fatherId 父级id
     * @param myJdbcTemplate 数据源
     * @return 所有子级部门id
     */
    private static List<String> getCompanyChildDeptId(String fatherId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "(WITH RECURSIVE department_hierarchy AS ( SELECT id, hr_dept_pid FROM hr_dept WHERE hr_dept_pid = ? UNION ALL SELECT d.id, d.hr_dept_pid FROM department_hierarchy dh JOIN hr_dept d ON d.hr_dept_pid = dh.id ) SELECT id FROM department_hierarchy ORDER BY id)";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,fatherId);
        if (!CollectionUtils.isEmpty(list)){
            return list.stream().map(p -> JdbcMapUtil.getString(p, "id")).distinct().collect(Collectors.toList());
        } else {
            return null;
        }
    }

    /**
     * 根据父级id获取所有子级部门id
     * @param fatherList 父级id
     * @param myJdbcTemplate 数据源
     * @return 所有子级部门id
     */
    private static List<String> getCompanyChildDeptId(List<Map<String,Object>> fatherList, MyJdbcTemplate myJdbcTemplate) {
        List<String> deptList = new ArrayList<>();
        for (Map<String, Object> map : fatherList) {
            String fatherId = JdbcMapUtil.getString(map,"id");
            String sql = "(WITH RECURSIVE department_hierarchy AS ( SELECT id, hr_dept_pid FROM hr_dept WHERE hr_dept_pid = ? UNION ALL SELECT d.id, d.hr_dept_pid FROM department_hierarchy dh JOIN hr_dept d ON d.hr_dept_pid = dh.id ) SELECT id FROM department_hierarchy ORDER BY id)";
            List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,fatherId);
            if (!CollectionUtils.isEmpty(list)){
                deptList.addAll(list.stream().map(p -> JdbcMapUtil.getString(p, "id")).distinct().collect(Collectors.toList()));
            }
        }
        return deptList;
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
