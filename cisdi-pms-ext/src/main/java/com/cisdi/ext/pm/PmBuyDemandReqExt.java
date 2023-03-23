package com.cisdi.ext.pm;

import cn.hutool.core.util.IdUtil;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.StringUtil;
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
import java.util.*;

/**
 * 采购需求审批 扩展
 */
@Slf4j
public class PmBuyDemandReqExt {

    public static final Map<String,String> POST_CODE_MAP = new HashMap();
    static {
        POST_CODE_MAP.put("AD_USER_TWO_ID","采购岗");
        POST_CODE_MAP.put("AD_USER_THREE_ID","成本岗");
        POST_CODE_MAP.put("AD_USER_FIVE_ID","财务岗");
    }

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

    /**
     * 采购需求审批-成本岗审批意见回显-批准
     */
    public void costCheck(){
        String status = "costCheck";
        check(status);
    }

    /**
     * 采购需求审批-成本岗审批-进入时扩展
     */
    public void costCheckInput(){
        String status = "costCheckInput";
        check(status);
    }

    /**
     * 采购需求审批-采购岗/财务岗审批-进入时扩展
     */
    public void purchaseAndFinanceCheckInput(){
        String status = "purchaseAndFinanceCheckInput";
        check(status);
    }

    /**
     * 采购需求审批-采购岗/财务岗审批-批准
     */
    public void purchaseAndFinanceCheck(){
        String status = "purchaseAndFinanceCheck";
        check(status);
    }

    /**
     * 采购需求审批-采购岗/财务岗/成本岗领导审批-进入时扩展
     */
    public void purchaseAndFinanceCostLeaderCheckInput(){
        String status = "purchaseAndFinanceCostLeaderCheckInput";
        check(status);
    }

    /**
     * 采购需求审批-采购岗/财务岗/成本岗领导审批-意见回显
     */
    public void purchaseAndFinanceCostLeaderCheck(){
        String status = "purchaseAndFinanceCostLeaderCheck";
        check(status);
    }

    private void check(String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Date date = new Date();
        //获取当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
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
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select u.id u_id,u.code u_code,u.name u_name,tk.user_comment from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id and tk.status='ap' join ad_user u on tk.ad_user_id=u.id", procInstId);
        StringBuffer comment = new StringBuffer();
        if (!CollectionUtils.isEmpty(list)) {
            for (Map<String, Object> tmp : list) {
                String txt = JdbcMapUtil.getString(tmp,"user_comment");
                if (!SharedUtil.isEmptyString(txt)){
                    comment = comment.append(JdbcMapUtil.getString(tmp,"u_name")).append("： ").append(txt).append("\n");
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
            //查询用户所在岗位 0099799190825079016=成本岗；0099799190825079033=采购岗
            userId = ProcessCommon.getOriginalUser(nodeInstanceId,userId,myJdbcTemplate);
            String sql1 = "select hr_dept_id FROM hr_dept_user WHERE AD_USER_ID = ? and hr_dept_id in ('0099799190825079016','0099799190825079033')";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,userId);
            if (!CollectionUtils.isEmpty(list1)){
                String deptId = JdbcMapUtil.getString(list1.get(0),"hr_dept_id");
                if ("0099799190825079016".equals(deptId)){
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
            //设置分管领导
            //获取部门信息
            String deptId = JdbcMapUtil.getString(entityRecord.valueMap,"CRT_DEPT_ID");
            String leader = "";
            if ("0099799190825079015".equals(deptId) || "0099799190825079017".equals(deptId) || "0099799190825079018".equals(deptId)){ //前期 工程 设计
                leader = "0099902212142088949"; //张景峰
            } else if ("0099799190825079033".equals(deptId) || "0099799190825079016".equals(deptId) ){ //采购 成本
                leader = "0099952822476371838"; //吴坤苗
            } else if ("0099799190825079028".equals(deptId) ){ //财务
                leader = "0099902212142027203"; //王小冬
            } else {
                leader = "0099250247095871681"; //管理员
            }
            //更新分管领导
            Integer exec = myJdbcTemplate.update("update PM_BUY_DEMAND_REQ set CHARGE_USER_IDS = ? where id = ?",leader,csCommId);
            log.info("已更新：{}",exec);
        } else if("detail".equals(status)){
            //获取预算金额下限 预算金额上线限
            BigDecimal min = new BigDecimal(entityRecord.valueMap.get("PAY_AMT_ONE").toString());
            BigDecimal max = new BigDecimal(entityRecord.valueMap.get("PAY_AMT_TWO").toString());
            if (min.compareTo(max) == 1){
                throw new BaseException("预算金额下限不能超过预算金额上限");
            }
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
        } else if ("costCheck".equals(status)){
            Integer exec = Crud.from("PM_BUY_DEMAND_REQ").where().eq("ID", csCommId).update()
                    .set("TEXT_REMARK_TWO", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("costCheckInput".equals(status)){
            Integer exec = Crud.from("PM_BUY_DEMAND_REQ").where().eq("ID", csCommId).update()
                    .set("TEXT_REMARK_TWO", null).exec();
            log.info("已更新：{}", exec);
        } else if ("purchaseAndFinanceCheckInput".equals(status)){
            Integer exec = Crud.from("PM_BUY_DEMAND_REQ").where().eq("ID", csCommId).update()
                    .set("TEXT_REMARK_FOUR", null).set("TEXT_REMARK_THREE",null).exec();
            log.info("已更新：{}", exec);
        } else if ("purchaseAndFinanceCheck".equals(status)){
            //获取采购岗人员id
            String purchaseId = JdbcMapUtil.getString(entityRecord.valueMap,"AD_USER_TWO_ID");
            //获取财务岗人员id
            String financeId = JdbcMapUtil.getString(entityRecord.valueMap,"AD_USER_FIVE_ID");
            //采购岗审批意见
            String purchaseComment = getComment(list,purchaseId);
            //获取财务岗审批意见
            String financeComment = getComment(list,financeId);
            Integer exec = Crud.from("PM_BUY_DEMAND_REQ").where().eq("ID", csCommId).update()
                    .set("TEXT_REMARK_FOUR", financeComment).set("TEXT_REMARK_THREE",purchaseComment).exec();
            log.info("已更新：{}", exec);
        } else if ("purchaseAndFinanceCostLeaderCheckInput".equals(status)){
            Integer exec = Crud.from("PM_BUY_DEMAND_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_COMMENT_ONE", null).set("APPROVAL_COMMENT_FIVE",null).set("APPROVAL_COMMENT_TWO",null).exec();
            log.info("已更新：{}", exec);
        } else if ("purchaseAndFinanceCostLeaderCheck".equals(status)){
            //查询成本、采购、财务部门负责人信息
            String sql = "select a.id,a.name,a.CHIEF_USER_ID,b.name as userName from hr_dept a left join ad_user b on a.CHIEF_USER_ID = b.id where a.id in ('0099799190825079016','0099799190825079033','0099799190825079028')";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql);
            if (!CollectionUtils.isEmpty(list1)){
                String costLeader = getLeader(list1,"0099799190825079016"); //成本部领导人
                String financeLeader = getLeader(list1,"0099799190825079028"); //财务部领导人
                String purchaseLeader = getLeader(list1,"0099799190825079033"); //采购部领导人
                String costComment = getComment(list,costLeader);
                String financeComment = getComment(list,financeLeader);
                String purchaseComment = getComment(list,purchaseLeader);
                Integer exec = Crud.from("PM_BUY_DEMAND_REQ").where().eq("ID", csCommId).update()
                        .set("APPROVAL_COMMENT_ONE", costComment).set("APPROVAL_COMMENT_FIVE",purchaseComment).set("APPROVAL_COMMENT_TWO",financeComment).exec();
                log.info("已更新：{}", exec);
            }
        }
    }

    //匹配部门负责人
    private String getLeader(List<Map<String, Object>> list1, String s) {
        String userId = "";
        if (!CollectionUtils.isEmpty(list1)){
            for (Map<String, Object> tmp : list1) {
                String id = JdbcMapUtil.getString(tmp,"id");
                if (s.equals(id)){
                    userId = JdbcMapUtil.getString(tmp,"CHIEF_USER_ID");
                }
            }
        }
        return userId;
    }

    // 匹配审批人意见
    private String getComment(List<Map<String, Object>> list, String purchaseId) {
        String comment = "";
        if (!CollectionUtils.isEmpty(list)){
            for (Map<String, Object> tmp : list) {
                String uid = JdbcMapUtil.getString(tmp,"u_id");
                if (purchaseId.equals(uid)){
                    String specificComment = JdbcMapUtil.getString(tmp,"user_comment") == null ? "拟同意" : JdbcMapUtil.getString(tmp,"user_comment");
                    comment = JdbcMapUtil.getString(tmp,"u_name") + ": " +specificComment;
                }
            }
        }
        return comment;
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
        String projectTypeName = JdbcMapUtil.getString(projectTypeList.get(0), "code");
        if ("non_system".equals(projectTypeName)){
            //项目名称
            String projectName = JdbcMapUtil.getString(entityRecord.valueMap,"PROJECT_NAME_WR");
            String[] prjNameArr = projectName.split(",");
            StringBuilder sb = new StringBuilder();
            for (String tmp : prjNameArr) {
                String sql1 = "select * from pm_prj where name = ? and PROJECT_SOURCE_TYPE_ID = ?";
                List<Map<String,Object>> existPrj = myJdbcTemplate.queryForList(sql1,tmp,projectTypeId);
                if (!CollectionUtils.isEmpty(existPrj)){
                    sb.append("非立项项目下,'"+tmp+" '项目已存在！").append("\n");
                }
            }
            if (sb.length() > 0){
                throw new BaseException(sb.toString());
            }

        }
    }

    /** 采购需求审批-审批通过项目信息写入项目库 **/
    public void endDataHandle(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //项目来源类型  0099952822476441375=非立项
        String projectType = JdbcMapUtil.getString(entityRecord.valueMap,"PROJECT_SOURCE_TYPE_ID");
        //部门
        String deptId = JdbcMapUtil.getString(entityRecord.valueMap, "CRT_DEPT_ID");
        if ("0099952822476441375".equals(projectType)){
            //创建人
            String userId = JdbcMapUtil.getString(entityRecord.valueMap,"CRT_USER_ID");
            //项目名称
            String projectName = JdbcMapUtil.getString(entityRecord.valueMap,"PROJECT_NAME_WR");
            projectName = StringUtil.chineseCodeToEnCode(projectName,",");
            List<String> prjNameList = new ArrayList<>();
            int index = projectName.indexOf(",");
            if (index == -1){
                prjNameList.add(projectName);
            } else {
                prjNameList = Arrays.asList(projectName.split(","));
            }
            for (String tmp : prjNameList) {
                String sql1 = "select * from pm_prj where name = ? and status = 'ap'";
                List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,tmp);
                if (CollectionUtils.isEmpty(list1)){
                    String prjId = Crud.from("pm_prj").insertData();
                    myJdbcTemplate.update("update pm_prj set CRT_USER_ID = ?,STATUS = ?,NAME = ?,PROJECT_SOURCE_TYPE_ID = ? where id = ?",userId,"AP",tmp,projectType,prjId);
                    String pmDeptId = Crud.from("pm_dept").insertData();
                    myJdbcTemplate.update("update pm_dept set PM_PRJ_ID = ?,HR_DEPT_ID = ?,USER_IDS = ?,ver = ? where id = ?",prjId,deptId,userId,1,pmDeptId);
                } else {
                    String prjId = JdbcMapUtil.getString(list1.get(0),"id");
                    String sql2 = "select * from pm_dept where PM_PRJ_ID=? and HR_DEPT_ID=? and status='ap'";
                    List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,prjId,deptId);
                    if (CollectionUtils.isEmpty(list2)){
                        String pmDeptId = Crud.from("pm_dept").insertData();
                        myJdbcTemplate.update("update pm_dept set PM_PRJ_ID = ?,HR_DEPT_ID = ?,USER_IDS = ?,ver = ? where id = ?",prjId,deptId,userId,1,pmDeptId);
                    } else {
                        String userIds = JdbcMapUtil.getString(list2.get(0),"USER_IDS");
                        if (!userIds.contains(userId)){
                            userIds = userIds+","+userId;
                            myJdbcTemplate.update("update pm_dept set USER_IDS = ? where id = ?",userIds,JdbcMapUtil.getString(list2.get(0),"id"));
                        }
                    }
                }

            }
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
            String sql1 = "select id from pm_prj where name = ? and PROJECT_SOURCE_TYPE_ID='0099952822476441375'";
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
        String processId = "0099952822476386063";
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
        String entId = "0099952822476385846";
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

    /**
     * 采购需求审批-历史数据-流程实例名称更新
     */
    public void updateHistoryName(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select * from PM_BUY_DEMAND_REQ where PROJECT_SOURCE_TYPE_ID = '0099952822476441375' and CRT_DT <= '2022-12-21 21:00:00' and name is not null";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        if (!CollectionUtils.isEmpty(list)){
            for (Map<String, Object> tmp : list) {
                //获取id
                String id = JdbcMapUtil.getString(tmp,"id");
                //获取项目类型
                String prjType = JdbcMapUtil.getString(tmp,"PROJECT_SOURCE_TYPE_ID");
                if ("0099952822476441375".equals(prjType)){
                    //获取流程标题
                    String name = JdbcMapUtil.getString(tmp,"name");
                    //获取项目名称
                    String prjName = JdbcMapUtil.getString(tmp,"PROJECT_NAME_WR");
                    String name1 = "采购需求审批-";
                    //获取采购事项
                    String name3 = JdbcMapUtil.getString(tmp,"BUY_MATTER_ID");
                    String sql2 = "select name from gr_set_value where id = ?";
                    List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,name3);
                    name3 = list2.get(0).get("name").toString();
                    //根据id查询发起人、发起时间
                    String sql3 = "select a.START_DATETIME,c.name from wf_process_instance a left JOIN pm_buy_demand_req b on a.id = b.LK_WF_INST_ID left join ad_user c on a.START_USER_ID = c.id where b.id = ?";
                    List<Map<String,Object>> list3 = myJdbcTemplate.queryForList(sql3,id);
                    String name4 = list3.get(0).get("name").toString();
                    String name5 = list3.get(0).get("START_DATETIME").toString().replace("T"," ");
                    String trueName = name1 + prjName + "-" + name3 + "-" + name4 + "-" + name5;
                    //更新名称
                    int update1 = myJdbcTemplate.update("update PM_BUY_DEMAND_REQ set name = ? where id = ?",trueName,id);
                    //更新流程实例名称
                    //获取流程实例id
                    String processInstanceId = JdbcMapUtil.getString(tmp,"LK_WF_INST_ID");
                    int update2 = myJdbcTemplate.update("update wf_process_instance set name = ? where id = ?",trueName,processInstanceId);
                }
            }
        }
    }

    //采购需求审批-财务岗角色
    public void getFinanceUser() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
            String user_id = myJdbcTemplate.queryForMap("select AD_USER_FIVE_ID from PM_BUY_DEMAND_REQ where id=?", csCommId).get("AD_USER_FIVE_ID").toString();
            ArrayList<Object> userIdList = new ArrayList<>(1);
            userIdList.add(user_id);
            ExtJarHelper.returnValue.set(userIdList);
        }
    }

    //采购需求审批-获取采购管理部负责人（未经审批过）
    public void getBuyDeptUser() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        //流程实例id
        String processInstanceId = ExtJarHelper.procInstId.get();
        //节点实例id
        String nodeId = ExtJarHelper.nodeInstId.get();
        //流程id
        String processId = ExtJarHelper.procId.get();
        for (EntityRecord entityRecord : entityRecordList) {
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
            //查询采购管理部负责人
            String user_id = myJdbcTemplate.queryForMap("select CHIEF_USER_ID from HR_DEPT where id = '0099799190825079033'").get("CHIEF_USER_ID").toString();
            //查询该流程已经
            List<String> checkUser = ProcessRoleExt.getCheckUser(processInstanceId,nodeId,processId,myJdbcTemplate);
            ArrayList<Object> userIdList = new ArrayList<>(1);
            if (!CollectionUtils.isEmpty(checkUser)){
                if (!checkUser.contains(user_id)){
                    userIdList.add(user_id);
                }
            } else {
                userIdList.add(user_id);
            }

            ExtJarHelper.returnValue.set(userIdList);
        }
    }

    /**
     * 采购需求审批-确认
     */
    public void buyDemandCheckOK(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String nodeStatus = processCheck("true",nodeInstanceId,myJdbcTemplate);
        handleCheckData(nodeStatus,nodeInstanceId,myJdbcTemplate);
    }

    /**
     * 采购需求审批-拒绝
     */
    public void buyDemandCheckRefuse(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String nodeStatus = processCheck("false",nodeInstanceId,myJdbcTemplate);
        handleCheckData(nodeStatus,nodeInstanceId,myJdbcTemplate);
    }

    /**
     * 采购需求审批-流程审批处理逻辑
     * @param nodeStatus 节点状态名称
     * @param nodeInstanceId 节点实例id
     * @param myJdbcTemplate 数据源
     */
    private void handleCheckData(String nodeStatus, String nodeInstanceId, MyJdbcTemplate myJdbcTemplate) {
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        //流程实例id
        String procInstId = ExtJarHelper.procInstId.get();
        //业务表id
        String csCommId = entityRecord.csCommId;
        //获取审批意见
        Map<String,String> message = ProcessCommon.getCommentNew(nodeInstanceId,userId,myJdbcTemplate,procInstId,userName);
        //审批意见、内容
        String comment = message.get("comment");
        //分支判断，逻辑处理
        if ("chargeLeaderOk".equals(nodeStatus)){

        }
    }

    /**
     * 节点赋值
     * @param status 状态码
     * @param nodeInstanceId 节点实例id
     * @param myJdbcTemplate 数据源
     */
    private String processCheck(String status, String nodeInstanceId, MyJdbcTemplate myJdbcTemplate) {
        String nodeId = ProcessCommon.getNodeIdByNodeInstanceId(nodeInstanceId,myJdbcTemplate);
        String name = "";
        if ("true".equals(status)){
            if ("1608274390628331520".equals(nodeId)){ //1-发起
                name = "startOk";
            } else if ("1608274390712217600".equals(nodeId)){ //2-业务部门主管审批
                name = "chargeLeaderOk";
            } else if ("1608274533448577024".equals(nodeId)){ //3-成本岗审批
                name = "costOk";
            } else if ("1608274390733189120".equals(nodeId)){ //4-采购岗/财务岗审批
                name = "buyAndFinanceOk";
            } else if ("1608274390846435328".equals(nodeId)){ //5-采购/成本/财务主管审批
                name = "costFinanceBuyLeaderOK";
            } else if ("1608274390896766976".equals(nodeId)){
                name = "chargeLeaderOK";
            }
        } else {
            if ("1608274390712217600".equals(nodeId)){ //2-业务部门主管审批
                name = "chargeLeaderRefuse";
            } else if ("1608274533448577024".equals(nodeId)){ //3-成本岗审批
                name = "costRefuse";
            } else if ("1608274390733189120".equals(nodeId)){ //4-采购岗/财务岗审批
                name = "buyAndFinanceRefuse";
            } else if ("1608274390846435328".equals(nodeId)){ //5-采购/成本/财务主管审批
                name = "costFinanceBuyLeaderRefuse";
            } else if ("1608274390896766976".equals(nodeId)){
                name = "chargeLeaderRefuse";
            }
        }
        return name;
    }
}
