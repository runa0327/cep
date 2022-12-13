package com.cisdi.ext.pm;

import cn.hutool.core.util.IdUtil;
import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

    /**
     * 采购需求审批扩展-采购岗、成本岗审批
     */
    public void buyDemandCostCheck() {
        String status = "buyDemandCostCheck";
        check(status);
    }

    /**
     * 采购需求审批扩展-业务主管审批
     */
    public void leaderCheck() {
        String status = "leaderCheck";
        check(status);
    }

    private void check(String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Date date = new Date();
        String now = DateTimeUtil.dateToString(date);
        // 当前登录人id
        String userId = ExtJarHelper.loginInfo.get().userId;
        //当前操作人名称
        String userName = ExtJarHelper.loginInfo.get().userName;

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
                    comment = comment.append(JdbcMapUtil.getString(tmp,"u_name")).append("： ").append(txt).append("; \n");
                }

            }
        }
        if ("buyDemandCostCheck".equals(status)){ //成本岗采购岗审批
            // 审批意见
            String sql2 = "select tk.USER_COMMENT,tk.USER_ATTACHMENT " +
                    "from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id join ad_user u on tk.ad_user_id=u.id" +
                    " where u.id = ?";
            List<Map<String, Object>> list2 = myJdbcTemplate.queryForList(sql2, procInstId,userId);
            String commentStr = "";
            if (!CollectionUtils.isEmpty(list2)) {
                commentStr = list2.get(0).get("user_comment") == null ? null : list2.get(0).get("user_comment").toString();
            }
            if (!SharedUtil.isEmptyString(commentStr)){
                commentStr = userName + ": "+commentStr;
            }
            //查询用户所在岗位 99799190825079016=成本岗；99799190825079033=采购岗
            String sql1 = "select hr_dept_id FROM hr_dept_user WHERE AD_USER_ID = ? and hr_dept_id in ('99799190825079016','99799190825079033')";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,userId);
            if (!CollectionUtils.isEmpty(list1)){
                String deptId = JdbcMapUtil.getString(list1.get(0),"hr_dept_id");
                if ("99799190825079016".equals(deptId)){
                    //验证成本岗是否填写明细表
                    this.checkDetail();
                    Integer exec = Crud.from("PM_BUY_DEMAND_REQ").where().eq("ID", csCommId).update()
                            .set("TEXT_REMARK_TWO", commentStr).exec();
                    log.info("已更新：{}", exec);
                } else {
                    Integer exec = Crud.from("PM_BUY_DEMAND_REQ").where().eq("ID", csCommId).update()
                            .set("TEXT_REMARK_THREE", commentStr).exec();
                    log.info("已更新：{}", exec);
                }
            } else {
                throw new BaseException("对不起，所在岗位不正确，请联系管理员处理！");
            }

        } else if ("leaderCheck".equals(status)){
            Integer exec = Crud.from("PM_BUY_DEMAND_REQ").where().eq("ID", csCommId).update()
                    .set("TEXT_REMARK_ONE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("start".equals(status)){
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

    /** 发起时校验非系统项目是否重复 **/
    public void checkPrjDuplicate(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //项目来源类型  99952822476441375=非立项
        String projectTypeId = JdbcMapUtil.getString(entityRecord.valueMap,"PROJECT_SOURCE_TYPE_ID");
        List<Map<String, Object>> projectTypeList = myJdbcTemplate.queryForList("select * from gr_set_value where id = ?", projectTypeId);
        if (CollectionUtils.isEmpty(projectTypeList)){
            return;
        }
        String projectTypeName = JdbcMapUtil.getString(projectTypeList.get(0), "NAME");
        if ("非系统".equals(projectTypeName)){
            //项目名称
            String projectName = JdbcMapUtil.getString(entityRecord.valueMap,"PROJECT_NAME_WR");
            String sql1 = "select * from pm_prj where name = ? and PROJECT_SOURCE_TYPE_ID = ?";
            List<Map<String,Object>> existPrj = myJdbcTemplate.queryForList(sql1,projectName,projectTypeId);
            if (!CollectionUtils.isEmpty(existPrj)){
                throw new BaseException("非立项项目下,'"+projectName+" '项目已存在！");
            }
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

    /** 采购需求审批-审批通过自动发起招标文件审批流程 **/
    @Transactional(rollbackFor = Exception.class)
    public void autoCreateBidApprovalProcess(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        Map<String, Object> valueMap = entityRecord.valueMap;
        String buyId = entityRecord.csCommId;
        //当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());
        //获取流程发起人
//        String createBy = JdbcMapUtil.getString(valueMap,"CRT_USER_ID");
//        String sql4 = "SELECT START_USER_ID FROM wf_process_instance WHERE id = (select LK_WF_INST_ID FROM pm_buy_demand_req WHERE id = ?)";
//        List<Map<String,Object>> list4 = myJdbcTemplate.queryForList(sql4,buyId);
//        String startBy = list4.get(0).get("START_USER_ID").toString();
        String startBy = JdbcMapUtil.getString(entityRecord.valueMap,"AD_USER_TWO_ID");
        String sql3 = "select name from ad_user where id = ?";
        List<Map<String,Object>> list3 = myJdbcTemplate.queryForList(sql3,startBy);
        String userName = list3.get(0).get("name").toString();
        //获取所属部门
        String CRT_DEPT_ID = "";
        String deptSql = "SELECT HR_DEPT_ID FROM hr_dept_user WHERE ad_user_id = ? and sys_true = 1 and status = 'AP'";
        List<Map<String,Object>> deptList = myJdbcTemplate.queryForList(deptSql,startBy);
        if (!CollectionUtils.isEmpty(deptList)){
           CRT_DEPT_ID = JdbcMapUtil.getString(deptList.get(0),"HR_DEPT_ID") ;
        }
//        String CRT_DEPT_ID = JdbcMapUtil.getString(valueMap,"CRT_DEPT_ID");
        //获取项目来源
        String PROJECT_SOURCE_TYPE_ID = JdbcMapUtil.getString(valueMap,"PROJECT_SOURCE_TYPE_ID");
        //获取项目名称
        String projectName = JdbcMapUtil.getString(valueMap,"PROJECT_NAME_WR");
        //获取项目信息
        String projectId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            String sql1 = "select id from pm_prj where name = ? and PROJECT_SOURCE_TYPE_ID='99952822476441375'";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectName);
            if (CollectionUtils.isEmpty(list1)){
                throw new BaseException("没有找到名为'"+projectName+"'的项目，请联系管理员处理");
            }
            projectId = list1.get(0).get("id").toString();
        }
        if (SharedUtil.isEmptyString(projectName)){
            String sql2 = "select name from pm_prj where id = ?";
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,projectId);
            projectName = list2.get(0).get("name").toString();
        }
        //定义流程标题
        String processName = "招标文件审批-"+projectName+"-"+userName+"-"+now;
        //定义审批状态
        String status = "DR";
        //定义招标流程id
        String processId = "99952822476386063";
        //获取招标文件审批流程id
        String sql5 = "select * from wf_process where id = '"+processId+"'";
        List<Map<String,Object>> list5 = myJdbcTemplate.queryForList(sql5);
        if (CollectionUtils.isEmpty(list5)){
            throw new BaseException("招标文件审批流程已变更，请联系管理员处理");
        } else {
            String processStatus = list5.get(0).get("STATUS").toString();
            if (!"AP".equals(processStatus)){
                throw new BaseException("当前绑定的招标文件审批流程已失效，请联系管理员绑定最新版本流程");
            }
        }
        //定义招标文件审批实体id
        String entId = "99952822476385846";
        //获取招标文件审批实体
        String sql6 = "select * from ad_ent where id = '"+entId+"'";
        List<Map<String,Object>> list6 = myJdbcTemplate.queryForList(sql6);
        if (CollectionUtils.isEmpty(list6)){
            throw new BaseException("招标文件实体已变更，请联系管理员处理");
        } else {
            String processStatus = list6.get(0).get("STATUS").toString();
            if (!"AP".equals(processStatus)){
                throw new BaseException("当前绑定的招标文件实体已失效，请联系管理员绑定最新版本流程");
            }
        }
        //获取实体
        String AD_ENT_ID = list6.get(0).get("id").toString();
        //获取实体代码
        String ENT_CODE = list6.get(0).get("name").toString();

        //获取写入招标文件审批表id
        String bidFileId = Crud.from("PM_BID_APPROVAL_REQ").insertData();
        //写入流程实例表
        String processInstanceId = Crud.from("wf_process_instance").insertData();
        Crud.from("wf_process_instance").where().eq("id",processInstanceId).update()
                .set("NAME",processName).set("ver","1")
                .set("START_USER_ID",startBy).set("START_DATETIME",now).set("WF_PROCESS_ID",processId)
                .set("STATUS","AP").set("AD_ENT_ID",AD_ENT_ID).set("ENTITY_RECORD_ID",bidFileId)
                .set("TS",now)
                .exec();

        //查询该流程第一个节点信息
        String sql7 = "select a.id,a.NAME from wf_node a LEFT JOIN wf_process b on a.WF_PROCESS_ID = b.id where b.id='"+processId+"' and a.status = 'AP' and b.`STATUS` = 'AP' order by a.SEQ_NO asc limit 1";
        List<Map<String,Object>> list7 = myJdbcTemplate.queryForList(sql7);
        if (CollectionUtils.isEmpty(list7)){
            throw new BaseException("招标文件审批自动发起绑定流程已废弃，请联系管理员重新绑定");
        }
        //节点id
        String processNodeId = JdbcMapUtil.getString(list7.get(0),"id");
        //节点名称
        String processNodeName = "1-"+JdbcMapUtil.getString(list7.get(0),"NAME");
        //获取序号
        String seqNo = IdUtil.getSnowflakeNextIdStr();
        //写入节点实例表
        String nodeId = Crud.from("wf_node_instance").insertData();
        Crud.from("wf_node_instance").where().eq("id",nodeId).update()
                .set("NAME",processNodeName).set("VER","1").set("WF_PROCESS_INSTANCE_ID",processInstanceId).set("WF_NODE_ID",processNodeId)
                .set("START_DATETIME",now).set("SEQ_NO",seqNo).set("STATUS","AP").set("IN_CURRENT_ROUND","1").set("TS",now)
                .set("IS_CURRENT","1").set("WF_PROCESS_ID",processId)
                .exec();

        //任务表序号
        String taskNo = IdUtil.getSnowflakeNextIdStr();
        //写入任务表
        String taskId = Crud.from("wf_task").insertData();
        Crud.from("wf_task").where().eq("id",taskId).update()
                .set("VER","2")
                .set("WF_NODE_INSTANCE_ID",nodeId).set("AD_USER_ID",startBy).set("RECEIVE_DATETIME",now).set("IS_CLOSED","0")
                .set("STATUS","AP").set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",startBy).set("WF_TASK_TYPE_ID","TODO")
                .set("SEQ_NO",taskNo).set("IN_CURRENT_ROUND","1").set("TS",now).set("WF_PROCESS_ID",processId)
                .set("WF_PROCESS_INSTANCE_ID",processInstanceId).set("WF_NODE_ID",processNodeId)
                .exec();

        //写入招标审批表
        Crud.from("PM_BID_APPROVAL_REQ").where().eq("id",bidFileId).update()
                .set("VER","1").set("TS",now).set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",startBy).set("NAME",processName)
                .set("PM_PRJ_ID",projectId).set("LK_WF_INST_ID",processInstanceId).set("PM_BUY_DEMAND_REQ_ID",buyId)
                .set("STATUS",status).set("CRT_USER_ID",startBy).set("CRT_DEPT_ID",CRT_DEPT_ID).set("CRT_DT",now)
                .set("AD_USER_ID",startBy).set("PROJECT_SOURCE_TYPE_ID",PROJECT_SOURCE_TYPE_ID)
                .exec();

    }
}
