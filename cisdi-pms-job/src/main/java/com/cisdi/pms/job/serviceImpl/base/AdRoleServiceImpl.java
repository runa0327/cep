package com.cisdi.pms.job.serviceImpl.base;

import com.cisdi.pms.job.domain.base.AdRole;
import com.cisdi.pms.job.domain.base.HrDept;
import com.cisdi.pms.job.mapper.base.AdRoleMapper;
import com.cisdi.pms.job.mapper.base.HrDeptMapper;
import com.cisdi.pms.job.service.base.AdRoleService;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class AdRoleServiceImpl implements AdRoleService {

    @Resource
    private AdRoleMapper adRoleMapper;

    @Resource
    private HrDeptMapper hrDeptMapper;

    /**
     * 根据角色id查询角色下用户
     *
     * @param roleId 角色id
     * @param processEntCode 流程所属主表单信息
     * @param map 流程所属主表单信息map
     * @return 角色人员信息
     */
    @Override
    public List<String> queryUserByRoleId(String roleId, String processEntCode, Map<String,Object> map) {
        AdRole adRole = adRoleMapper.queryById(roleId);
        String entityRecordId = map.get("id").toString();
        if (adRole != null){
            List<String> userList = new ArrayList<>();
            String userId;
            String adExtId = adRole.getAdExtId();
            if ("0099250247095869098".equals(adExtId)){ // 获取角色直接对应的用户 该角色直接配置人员，不需要进行自定义扩展
                userId = adRoleMapper.queryRoleUserByRoleId(adRole.getId());
                if (StringUtils.hasText(userId)){
                    userList.addAll(new ArrayList<>(Arrays.asList(userId.split(","))));
                }
            } else {
                String customerUnitId = JdbcMapUtil.getString(map,"customerUnitId");
                if ("1637094276171214848".equals(adExtId)){ // 工程管理部负责人
                    boolean res = checkIsNeedUser(processEntCode,entityRecordId);
                    if (res){
                        userId = getDeptManageUser("CHIEF_USER_ID","post_engineering",customerUnitId);
                        userList.add(userId);
                    }
                } else if ("1637094379275595776".equals(adExtId)){ // 成本合约部负责人
                    userId = getDeptManageUser("CHIEF_USER_ID","post_cost",customerUnitId);
                    userList.add(userId);
                } else if ("1637094324724477952".equals(adExtId)){ // 设计管理部负责人
                    userId = getDeptManageUser("CHIEF_USER_ID","post_design",customerUnitId);
                    userList.add(userId);
                } else if ("1637094217874583552".equals(adExtId)){ // 财务金融部负责人
                    userId = getDeptManageUser("CHIEF_USER_ID","post_finance",customerUnitId);
                    userList.add(userId);
                } else if ("1637093813136830464".equals(adExtId)){ // 前期管理部负责人
                    userId = getDeptManageUser("CHIEF_USER_ID","post_early",customerUnitId);
                    userList.add(userId);
                } else if ("1637093527248875520".equals(adExtId)){ // 采购管理部负责人
                    userId = getDeptManageUser("CHIEF_USER_ID","post_buy",customerUnitId);
                    userList.add(userId);
                }
            }
            return userList;
        } else {
            return null;
        }
    }

    /**
     * 获取部门主管、负责人、分管领导等信息
     * @param deptCode 需要查询的字段
     * @param deptPostCode 部门树部门代码
     * @param customerUnitId 业主单位
     */
    public String getDeptManageUser(String deptCode, String deptPostCode, String customerUnitId) {
        String stringBuilder = " select distinct " + deptCode + " as chiefUserId from HR_DEPT where status = 'AP'" +
                " AND code = '" + deptPostCode + "' " +
                " and CUSTOMER_UNIT = '" + customerUnitId + "'";
        HrDept hrDept = hrDeptMapper.selectBySql(stringBuilder);
        String userId = "";
        if (hrDept != null){
            userId = hrDept.getChiefUserId();
        }
        return userId;
    }

    /**
     * 判断是否需要审批人员
     * @param entCode 表单编码/表名
     * @param entityRecordId 记录id/表中id/流程业务记录id
     * @return 判断结果
     */
    private boolean checkIsNeedUser(String entCode, String entityRecordId) {
        boolean res = true;
        if ("PM_POST_APPOINT".equals(entCode)) { // 岗位指派流程判断
            res = checkNeedManageRole(entityRecordId);
        }
        return res;
    }

    /**
     * 岗位指派流程判断
     * @param entityRecordId 业务id
     * @return 判断结果
     */
    private boolean checkNeedManageRole(String entityRecordId) {
        boolean res = true;
        String sql = "select PROJECT_TYPE_ID AS projectTypeId from pm_post_appoint where id = '"+entityRecordId+"'";
        String projectTypeId = adRoleMapper.queryPrjType(sql);
        if ("1638731685728239616".equals(projectTypeId) || "0099799190825080994".equals(projectTypeId) || "0099799190825080740".equals(projectTypeId)){
            res = false;
        }
        return res;
    }
}
