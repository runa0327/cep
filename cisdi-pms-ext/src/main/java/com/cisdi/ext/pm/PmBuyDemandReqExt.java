package com.cisdi.ext.pm;

import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 采购需求审批 扩展
 */
@Slf4j
public class PmBuyDemandReqExt {

    /**
     * 采购需求审批扩展-按照先后顺序审批-第一次审批
     */
    public void checkFirst() {
        String status = "first";
        check(status);
    }

    /**
     * 采购需求审批扩展-按照先后顺序审批-第二次审批
     */
    public void checkSecond() {
        String status = "second";
        check(status);
    }

    /**
     * 采购需求审批扩展-按照先后顺序审批-第三次审批
     */
    public void checkThird() {
        String status = "third";
        check(status);
    }

    /**
     * 采购需求审批扩展-按照先后顺序审批-第四次审批
     */
    public void checkFourth() {
        String status = "fourth";
        check(status);
    }

    /**
     * 采购需求审批扩展-发起时数据校验
     */
    public void checkStart() {
        String status = "start";
        check(status);
    }

    /**
     * 采购需求审批扩展-发起时数据校验
     */
    public void checkDetail() {
        String status = "detail";
        check(status);
    }

    private void check(String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Date date = new Date();
        String now = DateTimeUtil.dateToString(date);
        // 当前登录人
        String userId = ExtJarHelper.loginInfo.get().userName;

        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        // 审批意见
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select u.id u_id,u.code u_code,u.name u_name,tk.user_comment from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id join ad_user u on tk.ad_user_id=u.id", procInstId);
        StringBuffer comment = new StringBuffer();
        if (!CollectionUtils.isEmpty(list)) {
            for (Map<String, Object> tmp : list) {
                String txt = JdbcMapUtil.getString(tmp,"user_comment");
                if (!SharedUtil.isEmptyString(txt)){
                    comment = comment.append(JdbcMapUtil.getString(tmp,"u_name")).append("： ").append(txt).append("; ");
                }

            }
        }
        if ("start".equals(status)){
            //获取预算金额下限 预算金额上线限
            BigDecimal min = new BigDecimal(entityRecord.valueMap.get("PAY_AMT_ONE").toString());
            BigDecimal max = new BigDecimal(entityRecord.valueMap.get("PAY_AMT_TWO").toString());
            if (min.compareTo(max) == 1){
                throw new BaseException("预算金额下限不能超过预算金额上限");
            }
        } else if("detail".equals(status)){
            //查询明细信息
            String sql1 = "select * from PM_BUY_DEMAND_DETAIL_REQ where PARENT_ID = ?";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,csCommId);
            if (CollectionUtils.isEmpty(list1)){
                throw new BaseException("费用明细信息不能为空！");
            }
        } else if ("first".equals(status)) {
            Integer exec = Crud.from("PM_BUY_DEMAND_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_COMMENT_ONE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("second".equals(status)) {
            Integer exec = Crud.from("PM_BUY_DEMAND_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_COMMENT_TWO", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("third".equals(status)) {
            Integer exec = Crud.from("PM_BUY_DEMAND_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_COMMENT_THREE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("fourth".equals(status)) {
            Integer exec = Crud.from("PM_BUY_DEMAND_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_COMMENT_FOUR", comment).exec();
            log.info("已更新：{}", exec);
        }
    }

    /** 采购需求审批-审批通过项目信息写入项目库 **/
    public void endDataHandle(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //项目来源类型  99952822476441375=非立项
        String projectType = JdbcMapUtil.getString(entityRecord.valueMap,"PROJECT_SOURCE_TYPE_ID");
        //部门
        String deptId = JdbcMapUtil.getString(entityRecord.valueMap, "CRT_DEPT_ID");
        if ("99952822476441375".equals(projectType)){
            //创建人
            String userId = JdbcMapUtil.getString(entityRecord.valueMap,"CRT_USER_ID");
            //项目名称
            String projectName = JdbcMapUtil.getString(entityRecord.valueMap,"PROJECT_NAME_WR");

            String sql1 = "select * from pm_prj where name = ? and PROJECT_SOURCE_TYPE_ID = ?";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectName,projectType);
            if (!CollectionUtils.isEmpty(list1)){
                throw new BaseException("非立项项目下,'"+projectName+" '项目已存在！");
            }
            String prjId = Crud.from("pm_prj").insertData();
            myJdbcTemplate.update("update pm_prj set CRT_USER_ID = ?,STATUS = ?,NAME = ?,PROJECT_SOURCE_TYPE_ID = ? where id = ?",userId,"AP",projectName,projectType,prjId);
//            String sql2 = "insert into pm_prj(id,CRT_USER_ID,STATUS,NAME,PROJECT_SOURCE_TYPE_ID) values((select uuid_short()),?,'AP',?,?)";
//            int update = myJdbcTemplate.update(sql2,userId,projectName,projectType);
            String pmDeptId = Crud.from("pm_dept").insertData();
            myJdbcTemplate.update("update pm_dept set PM_PRJ_ID = ?,HR_DEPT_ID = ?,USER_IDS = ?,ver = ? where id = ?",prjId,deptId,userId,1,pmDeptId);
//            String sql3 = "insert into pm_dept(id,PM_PRJ_ID,HR_DEPT_ID,USER_IDS,ver) values((select uuid_short()),?,?,?,?)";
//            myJdbcTemplate.update(sql3,prjId,deptId,userId,1);
        }
    }
}
