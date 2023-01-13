package com.cisdi.ext.pm;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 部门角色配置
 */
public class DeptRole {

    /**
     * 获取成本岗角色人员(区分公司)
     */
    public void getCostUser(){
        // 获取业主单位
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String customerUnit = JdbcMapUtil.getString(entityRecord.valueMap,"CUSTOMER_UNIT_ONE");
        //定义数据源链接
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        getDeptAllUser(myJdbcTemplate,customerUnit,"post_cost","成本岗");
    }

    /**
     * 获取合约岗角色人员(区分公司)
     */
    public void getContractUser(){
        // 获取业主单位
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String customerUnit = JdbcMapUtil.getString(entityRecord.valueMap,"CUSTOMER_UNIT_ONE");
        //定义数据源链接
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        getDeptAllUser(myJdbcTemplate,customerUnit,"post_contract","合约岗");

    }

    /**
     * 根据公司和岗位代码获取所有人员
     * @param myJdbcTemplate 数据源
     * @param customerUnit 公司id
     * @param postCode 岗位code
     * @param deptName 岗位说明
     */
    private void getDeptAllUser(MyJdbcTemplate myJdbcTemplate,String customerUnit, String postCode, String deptName) {
        String sql1 = "select a.AD_USER_ID FROM hr_dept_user a LEFT JOIN hr_dept b on a.HR_DEPT_ID = b.id where b.code = ? and b.CUSTOMER_UNIT = ? ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,postCode,customerUnit);
        if (!CollectionUtils.isEmpty(list1)){
            ArrayList<Object> userIdList = list1.stream().map(p -> JdbcMapUtil.getString(p, "AD_USER_ID")).collect(Collectors.toCollection(ArrayList::new));
            ExtJarHelper.returnValue.set(userIdList);
        } else {
            throwException(myJdbcTemplate,deptName,customerUnit);
        }
    }

    /**
     * 公司岗位无人时提示
     * @param deptName 岗位名称
     * @param customerUnit 公司名称
     */
    private void throwException(MyJdbcTemplate myJdbcTemplate,String deptName, String customerUnit) {
        String sql = "select name from pm_party where id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,customerUnit);
        String customerName = JdbcMapUtil.getString(list.get(0),"name");
        throw new BaseException(customerName+" 公司下，"+deptName+"暂未配置人员，请联系管理员或相关负责人维护");
    }
}
