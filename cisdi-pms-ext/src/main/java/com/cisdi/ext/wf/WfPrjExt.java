package com.cisdi.ext.wf;

import com.cisdi.ext.model.base.BaseCustomerPostchecker;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WfPrjExt {
    public void parsePrjEarlyUserIdByPrj() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            parseUserIdByPrj(entityRecord, "PRJ_EARLY_USER_ID", "前期岗");
        }
    }

    public void parsePrjDesignUserIdByPrj() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            parseUserIdByPrj(entityRecord, "PRJ_DESIGN_USER_ID", "设计岗");
        }
    }

    public void parsePrjCostUserIdByPrj() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            parseUserIdByPrj(entityRecord, "PRJ_COST_USER_ID", "成本岗");
        }
    }

    private void parseUserIdByPrj(EntityRecord entityRecord, String colName, String dspName) {
        String csCommId = entityRecord.csCommId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;

        Object pm_prj_id = myJdbcTemplate.queryForMap("select t.pm_prj_id from " + entCode + " t where t.id=?", csCommId).get("pm_prj_id");
        String user_id = null;
        try {
            user_id = myJdbcTemplate.queryForMap("select t." + colName + " USER_ID from pm_prj t where t.id=?", pm_prj_id).get("USER_ID").toString();
        } catch (Exception ex) {
            throw new BaseException("项目没有设置对应的" + dspName + "用户！");
        }
        List<String> userIdList = new ArrayList<>(1);
        userIdList.add(user_id);
        ExtJarHelper.returnValue.set(userIdList);
    }

    public void parseAgentUserIdByBid() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            parseUserIdByBid(entityRecord);
        }
    }

    private void parseUserIdByBid(EntityRecord entityRecord) {
        String csCommId = entityRecord.csCommId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String user_id = myJdbcTemplate.queryForMap("select BID_USER_ID from po_public_bid_req where id=?", csCommId).get(
                "BID_USER_ID").toString();
        ArrayList<Object> userIdList = new ArrayList<>(1);
        userIdList.add(user_id);
        ExtJarHelper.returnValue.set(userIdList);

    }

    //招标文件经办人
    public void pmBidApprovalReqByBid() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
            String user_id = myJdbcTemplate.queryForMap("select AD_USER_ID from PM_BID_APPROVAL_REQ where id=?", csCommId).get("AD_USER_ID").toString();
            ArrayList<Object> userIdList = new ArrayList<>(1);
            userIdList.add(user_id);
            ExtJarHelper.returnValue.set(userIdList);
        }
    }

    //招标文件经办人所在部门负责人
    public void getBidFileDeptUser() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

            String procInstId = ExtJarHelper.procInstId.get();
            String START_USER_ID = myJdbcTemplate.queryForMap("select AD_USER_ID from PM_BID_APPROVAL_REQ where id=?", csCommId).get("AD_USER_ID").toString();

            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select d.chief_user_id from hr_dept_user du join " +
                    "hr_dept d on du.HR_DEPT_ID=d.id and du.AD_USER_ID=? limit 1", START_USER_ID);
            if (CollectionUtils.isEmpty(list) || list.get(0).get("chief_user_id") == null) {
                throw new BaseException("该经办人没有对应的部门负责人！");
            } else if (list.size() > 1) {
                throw new BaseException("该经办人不能对应" + list.size() + "个部门负责人！");
            }

            String chief_user_id = list.get(0).get("chief_user_id").toString();

            ArrayList<Object> userIdList = new ArrayList<>(1);
            userIdList.add(chief_user_id);
            ExtJarHelper.returnValue.set(userIdList);
        }
    }

    //合同签订-自动获取角色-法务审批
    public void getFaWu() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String companyId = entityRecord.valueMap.get("CUSTOMER_UNIT_ONE").toString();
            String user = "";
            if (StringUtils.hasText(companyId)){
                BaseCustomerPostchecker baseCustomerPostchecker = BaseCustomerPostchecker.selectOneByWhere(new Where().eq(BaseCustomerPostchecker.Cols.CUSTOMER_UNIT,companyId)
                        .eq(BaseCustomerPostchecker.Cols.STATUS,"AP"));
                if (baseCustomerPostchecker != null){
                    user = baseCustomerPostchecker.getAdUserEighthIds();
                }
            }
            if (!StringUtils.hasText(user)){
                throw new BaseException("该业主单位未配置法务审批人员，请先配置或联系管理员处理！");
            } else {
                List<String> userList = new ArrayList<>(Arrays.asList(user.split(",")));
                ArrayList<Object> userIdList = new ArrayList<>(1);
                userIdList.addAll(userList);
                ExtJarHelper.returnValue.set(userIdList);
            }
        }
    }

    //合同签订-自动获取角色-财务审批
    public void getCaiWu() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

            String sql1 = "";

            String procInstId = ExtJarHelper.procInstId.get();
            String companyId = entityRecord.valueMap.get("CUSTOMER_UNIT_ONE").toString();
            if ("0099902212142008832".equals(companyId)){ //三亚崖州湾科技城投资控股有限公司
                sql1 = "select AD_USER_ID from ad_role_user a WHERE a.AD_ROLE_ID = '0099952822476412310' ";
            } else if ("0099952822476380406".equals(companyId) || "0099952822476380407".equals(companyId)){ //海南城发建设工程有限公司 海南城发实业集团有限公司
                sql1 = "select AD_USER_ID from ad_role_user a WHERE a.AD_ROLE_ID = '0099952822476412312' ";
            } else { // 非以上情况都取开发公司
                sql1 = "select AD_USER_ID from ad_role_user a WHERE a.AD_ROLE_ID = '0099952822476412306' ";
            }

            List<Map<String, Object>> list1 = myJdbcTemplate.queryForList(sql1);
            if (CollectionUtils.isEmpty(list1)){
                throw new BaseException("下一节点“财务审核”暂未配置代办人员！");
            }
            List<String> userList = list1.stream().map(p->JdbcMapUtil.getString(p,"AD_USER_ID")).collect(Collectors.toList());

            ArrayList<Object> userIdList = new ArrayList<>(2);
            userIdList.addAll(userList);
            ExtJarHelper.returnValue.set(userIdList);
        }
    }

    //采购需求审批-采购岗角色
    public void getPurchaseUser() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
            String user_id = myJdbcTemplate.queryForMap("select AD_USER_TWO_ID from PM_BUY_DEMAND_REQ where id=?", csCommId).get("AD_USER_TWO_ID").toString();
            ArrayList<Object> userIdList = new ArrayList<>(1);
            userIdList.add(user_id);
            ExtJarHelper.returnValue.set(userIdList);
        }
    }

    //采购需求审批-成本岗角色
    public void getCostUser() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
            String user_id = myJdbcTemplate.queryForMap("select AD_USER_THREE_ID from PM_BUY_DEMAND_REQ where id=?", csCommId).get("AD_USER_THREE_ID").toString();
            ArrayList<Object> userIdList = new ArrayList<>(1);
            userIdList.add(user_id);
            ExtJarHelper.returnValue.set(userIdList);
        }
    }


}
