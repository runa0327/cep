package com.cisdi.ext.pm;

import com.cisdi.ext.link.LinkSql;
import com.cisdi.ext.model.HrDeptUser;
import com.cisdi.ext.model.PmRoster;
import com.cisdi.ext.util.StringUtil;
import com.cisdi.ext.model.WfProcessInstance;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程通用扩展
 */
public class ProcessCommon {

    /**
     *
     * @param nodeId 节点id
     * @param userId 当前操作用户id
     * @param myJdbcTemplate 数据源
     * @return 状态
     */
    public static Boolean getFirstCheck(String nodeId, String userId, MyJdbcTemplate myJdbcTemplate) {
        //判断是否是当轮拒绝回来的、撤销回来的（是否是第一个进入该节点审批的人）
        String sql2 = "select count(*) as num from wf_task where WF_NODE_INSTANCE_ID = ? and IS_CLOSED = 1 and AD_USER_ID != ?";
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,nodeId,userId);
        boolean firstCheck = true; //是第一次审批
        if (!CollectionUtils.isEmpty(list2)){
            String num = JdbcMapUtil.getString(list2.get(0),"num");
            if (!SharedUtil.isEmptyString(num) && !"0".equals(num)){
                firstCheck = false;
            }
        }
        return firstCheck;
    }

    /**
     * 查询流程审批提交时的意见和附件信息
     * @param procInstId 流程实例id
     * @param userId 当前人员id
     * @param myJdbcTemplate 数据源
     * @return 意见信息
     */
    public static Map<String, String> getComment(String procInstId, String userId, MyJdbcTemplate myJdbcTemplate) {
        Map<String,String> map = new HashMap<>();
        String sql = "select tk.USER_COMMENT,tk.USER_ATTACHMENT from wf_node_instance ni " +
                "join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id and tk.status = 'ap' " +
                "join ad_user u on tk.ad_user_id=u.id " +
                "where u.id = ?";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, procInstId,userId);
        String comment,file;
        if (!CollectionUtils.isEmpty(list)) {
            comment = SharedUtil.isEmptyString(JdbcMapUtil.getString(list.get(0),"user_comment"))  ? "同意" : JdbcMapUtil.getString(list.get(0),"user_comment");
            file = JdbcMapUtil.getString(list.get(0),"USER_ATTACHMENT");
            if (!SharedUtil.isEmptyString(file)){
                map.put("file",file);
            }
            map.put("comment",comment);
        }
        return map;
    }

    /**
     * 获取流程当前审批人审批操作时评价信息
     * @param nodeInstanceId 节点实例id
     * @param userId 操作人id
     * @param myJdbcTemplate 数据源
     * @param procInstId 流程实例id
     * @param userName 操作人名称
     * @return 审批意见信息
     */
    public static Map<String, String> getCommentNew(String nodeInstanceId, String userId, MyJdbcTemplate myJdbcTemplate, String procInstId, String userName) {
        Map<String,String> map = new HashMap<>();
        String sql = "select a.USER_COMMENT,a.USER_ATTACHMENT from wf_task a " +
                "left join wf_node_instance b on a.WF_NODE_INSTANCE_ID = b.id and b.status = 'ap' " +
                "where b.id = ? and a.AD_USER_ID = ? and a.status = 'ap' and a.IS_CLOSED = 1 and a.WF_PROCESS_INSTANCE_ID = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,nodeInstanceId,userId,procInstId);
        if (!CollectionUtils.isEmpty(list)){
            String value = SharedUtil.isEmptyString(JdbcMapUtil.getString(list.get(0),"USER_COMMENT")) ? "同意" : JdbcMapUtil.getString(list.get(0),"USER_COMMENT");
            map.put("comment",value);
            map.put("file",JdbcMapUtil.getString(list.get(0),"USER_ATTACHMENT"));
        }
        return map;
    }

    /**
     * 判断去重，避免同一个人重复提交相同文件
     * @param userId 当前操作人id
     * @param userName 当前操作人名称
     * @param processComment 流程中的意见信息
     * @param processFile 流程中的文件信息
     * @param comment 本次提交填写的意见信息
     * @param file 本次提交填写的文件信息
     * @param myJdbcTemplate 数据源
     * @return 提交信息
     */
    public static Map<String, String> getEndComment(String userId, String userName, String processComment, String processFile, String comment, String file, MyJdbcTemplate myJdbcTemplate) {
        Map<String,String> map = new HashMap<>();
        String newCommentStr = getNewCommentStr(userName,processComment,comment);
        String newFileStr = getNewCommentFile(userId,processFile,file,myJdbcTemplate);
        map.put("comment",newCommentStr);
        map.put("file",newFileStr);
        return map;
    }

    /**
     * 获取最新的意见附件信息
     * @param userId 当前操作人id
     * @param processFile 流程文件信息
     * @param file 本次操作文件信息
     * @param myJdbcTemplate 数据源
     * @return 最新的意见附件信息
     */
    private static String getNewCommentFile(String userId, String processFile, String file, MyJdbcTemplate myJdbcTemplate) {
        String newFile = "";
        if (SharedUtil.isEmptyString(processFile)){
            if (!SharedUtil.isEmptyString(file)){
                newFile = file;
            }
        } else {
            //当前节点实例信息
            String nodeInstanceId = ExtJarHelper.nodeInstId.get();
            newFile = getNewFile(userId,nodeInstanceId,processFile,file,myJdbcTemplate);
        }
        return newFile;
    }

    /**
     * 获取最新的意见附件
     * @param userId 当前操作人id
     * @param nodeInstanceId 节点实例id
     * @param processFile 流程文件
     * @param file 本次提交意见附件
     * @param myJdbcTemplate 数据源
     * @return 最新需要回显的意见附件
     */
    private static String getNewFile(String userId, String nodeInstanceId, String processFile, String file,MyJdbcTemplate myJdbcTemplate) {
        String newFile;
        //查询该人员该节点是否有历史提交文件
        String historyFile = getHistoryFile(userId,nodeInstanceId,myJdbcTemplate);
        if (SharedUtil.isEmptyString(historyFile) && SharedUtil.isEmptyString(file)){ //历史审批文件为空，本次提交意见附件为空，取流程文件
            newFile = processFile;
        } else if (SharedUtil.isEmptyString(historyFile) && !SharedUtil.isEmptyString(file)){ //历史审批文件为空，本次提交意见附件不为空，直接合并
            newFile = processFile+","+file;
        } else if (!SharedUtil.isEmptyString(historyFile) && SharedUtil.isEmptyString(file)){ //历史审批文件不为空，本次提交意见附件为空,清除历史审批文件
            //清空流程文件中的历史文件信息
            List<String> oldFileList = clearHistory(processFile,historyFile);
            newFile = String.join(",",oldFileList);
        } else { //历史审批文件不为空，本次提交意见附件不为空,清除历史审批文件并添加本次
            List<String> oldFileList = clearHistory(processFile,historyFile);
            oldFileList.addAll(new ArrayList<>(Arrays.asList(file.split(","))));
            newFile = String.join(",",oldFileList);
        }
        return newFile;
    }

    /**
     * 清空流程文件中的历史文件信息
     * @param processFile 流程文件信息
     * @param historyFile 历史审批文件信息
     * @return 最新的流程文件信息
     */
    private static List<String> clearHistory(String processFile, String historyFile) {
        List<String> oldFileList = new ArrayList<>(Arrays.asList(processFile.split(",")));
        List<String> historyFileList = new ArrayList<>(Arrays.asList(historyFile.split(",")));
        oldFileList.removeAll(historyFileList);
        return oldFileList;
    }

    /**
     * 获取该节点该用户历史审批意见附件
     * @param userId 当前操作人id
     * @param nodeInstanceId 节点实例id
     * @param myJdbcTemplate 数据源
     * @return 历史文件id
     */
    public static String getHistoryFile(String userId, String nodeInstanceId, MyJdbcTemplate myJdbcTemplate) {
        String file = "";
        String sql1 = "select GROUP_CONCAT(DISTINCT USER_ATTACHMENT) as USER_ATTACHMENT from wf_task where AD_USER_ID = ? and WF_NODE_INSTANCE_ID = ? and USER_ATTACHMENT is not null";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,userId,nodeInstanceId);
        if (!CollectionUtils.isEmpty(list1)) {
            file = JdbcMapUtil.getString(list1.get(0), "USER_ATTACHMENT");
        }
        return file;
    }

    /**
     * 获取最新的意见信息
     * @param userName 当前操作人名称
     * @param processComment 流程中原意见内容
     * @param comment 本次操作意见内容
     * @return 最新意见内容
     */
    public static String getNewCommentStr(String userName, String processComment, String comment) {
        String newComment;
        String newUserComment = userName+":"+comment;
        if (SharedUtil.isEmptyString(processComment)){
            newComment = newUserComment;
        } else {
            if (processComment.contains(userName)){
                List<String> processCommentList = new ArrayList<>(Arrays.asList(processComment.split(";\n")));
                processCommentList = processCommentList.stream().filter(p->!p.contains(userName)).collect(Collectors.toList());
                processCommentList.add(newUserComment);
                newComment = String.join(";\n",processCommentList);
            } else {
                newComment = processComment + ";\n" + newUserComment;
            }
        }
        return newComment;
    }

    /**
     * 根据项目id查询该类型流程是否已发起-仅支持项目字段是 PM_PRJ_ID 的流程
     * @param entityCode 实体表名称
     * @param projectId 单个项目id,不支持多项目查询
     * @param id 业务表id
     * @param myJdbcTemplate 数据源
     * @return 返回信息
     */
    public static String prjRepeatStartById(String entityCode, String projectId, String id, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select * from "+entityCode+" where pm_prj_id = ? and status NOT in ('VD','VDING') and id != ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,projectId,id);
        String msg = "";
        if (!CollectionUtils.isEmpty(list)){
            msg = "该项目已发起过流程，请勿重复发起！";
        }
        return msg;
    }

    /**
     * 根据项目id查询该类型流程是否已发起-仅支持项目字段是 PROJECT_NAME_WR 的流程
     * @param entityCode 实体表名称
     * @param projectName 单个项目名称,不支持多项目查询
     * @param id 流程业务表id
     * @param myJdbcTemplate 数据源
     * @return 返回信息
     */
    public static String prjRepeatStartByName(String entityCode, String projectName, String id, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select * from "+entityCode+" where PROJECT_NAME_WR = ? and status NOT in ('VD','VDING') and id != ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,projectName,id);
        String msg = "";
        if (!CollectionUtils.isEmpty(list)){
            msg = "该项目已发起过流程，请勿重复发起！";
        }
        return msg;
    }

    /**
     * 根据节点实例id查询节点id
     * @param nodeInstanceId 节点实例id
     * @param myJdbcTemplate 数据源
     * @return 节点id
     */
    public static String getNodeIdByNodeInstanceId(String nodeInstanceId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select WF_NODE_ID from wf_node_instance where id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,nodeInstanceId);
        return JdbcMapUtil.getString(list.get(0),"WF_NODE_ID");
    }

    /**
     * 查询流程任务最原始的用户（考虑到转办用户）
     * @param nodeInstanceId 流程节点实例id
     * @param userId 用户id
     * @param myJdbcTemplate 数据源
     * @return 原始用户id
     */
    public static String getOriginalUser(String nodeInstanceId, String userId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select PREV_TASK_ID from wf_task where ad_user_id = ? and WF_NODE_INSTANCE_ID = ? and status = 'ap'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,userId,nodeInstanceId);
        String taskId;
        if (!CollectionUtils.isEmpty(list)){
            taskId = JdbcMapUtil.getString(list.get(0),"PREV_TASK_ID");
            if (!SharedUtil.isEmptyString(taskId)){
                String sql2 = "select ad_user_id from wf_task where id = ?";
                userId = myJdbcTemplate.queryForList(sql2,taskId).get(0).get("ad_user_id").toString();
            }
        }
        return userId;
    }

    /**
     * 获取人员在流程中的岗位信息
     * @param userId 用户id
     * @param projectId 项目id
     * @param companyId 业主单位id
     * @param myJdbcTemplate 数据源
     * @return 流程岗位id
     */
    public static String getUserProcessPost(String userId,String projectId, String companyId, MyJdbcTemplate myJdbcTemplate) {
        String processPostId;
        String sql = "select a.BASE_PROCESS_POST_ID FROM PM_POST_PROPRJ a " +
                "left join PM_ROSTER b on a.POST_INFO_ID = b.POST_INFO_ID " +
                "where b.PM_PRJ_ID = ? and b.CUSTOMER_UNIT = ? and b.AD_USER_ID = ? and a.STATUS = 'AP' and b.STATUS = 'AP'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,projectId,companyId,userId);
        if (!CollectionUtils.isEmpty(list)){
            processPostId = JdbcMapUtil.getString(list.get(0),"BASE_PROCESS_POST_ID");
        } else {
            throw new BaseException("该人员在该项目的花名册信息中未匹配到信息，请核对花名册审批人信息或联系管理员处理！");
        }
        return processPostId;
    }

    /**
     * 流程在发起时重写通用单据部门信息
     * @param id 业务表id
     * @param userId 发起人id
     * @param companyId 业主单位id
     * @param entCode 业务表名
     */
    public static void updateProcessDept(String id, String userId, String companyId, String entCode) {
        List<HrDeptUser> list = HrDeptUser.selectByWhere(new Where().eq(HrDeptUser.Cols.AD_USER_ID,userId)
                .eq(HrDeptUser.Cols.CUSTOMER_UNIT,companyId).eq(HrDeptUser.Cols.STATUS,"AP"));
        if (CollectionUtils.isEmpty(list)){
            throw new BaseException("对不起，您在该业主单位下不存在部门关系，请联系管理员处理！");
        } else {
            List<HrDeptUser> list1 = list.stream().filter(p->p.getSysTrue()==true).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(list1)){
                list1 = list.stream().filter(p->p.getSysTrue()!=true).collect(Collectors.toList());
            }
            String deptId = list1.get(0).getHrDeptId();
            Crud.from(entCode).where().eq("id",id).update().set("CRT_DEPT_ID",deptId).exec();
        }
    }

    /**
     * 流程数据清理
     * @param str 需求清除的字段数据
     * @param csCommId 业务流程id
     * @param entCode 业务表名
     * @param myJdbcTemplate 数据源
     */
    public static void clearData(String str, String csCommId, String entCode, MyJdbcTemplate myJdbcTemplate) {
        StringBuilder sb = new StringBuilder("UPDATE ");
        sb.append(entCode).append(" SET ");
        List<String> list = StringUtil.getStrToList(str,",");
        for (String tmp : list) {
            sb.append(tmp).append(" = null,");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" WHERE ID = ?");
        myJdbcTemplate.update(sb.toString(),csCommId);
    }

    /**
     * 流程审批意见回显
     * @param code 单个字段
     * @param comment 审批意见
     * @param csCommId 业务流程id
     * @param entCode 业务流程表名
     */
    public static void commentShow(String code, String comment, String csCommId, String entCode) {
        Crud.from(entCode).where().eq("ID",csCommId).update().set(code,comment).exec();
    }

    /**
     * 流程完结时，将岗位人员信息写入花名册
     * @param projectId 项目id
     * @param entCode 业务表名
     * @param processId 流程id
     * @param companyId 业主单位id
     * @param csCommId 业务记录id
     * @param myJdbcTemplate 数据源
     */
    public static void addPrjPostUser(String projectId, String entCode, String processId, String companyId, String csCommId, MyJdbcTemplate myJdbcTemplate) {
        //查询该流程所有流程岗位信息
        List<Map<String,Object>> procPostList = LinkSql.getProcessPostByProcessCompany(processId,companyId,myJdbcTemplate);
        if (!CollectionUtils.isEmpty(procPostList)){
            //取出流程岗位id
            List<String> deptId = procPostList.stream().map(p->JdbcMapUtil.getString(p,"id")).filter(p->!SharedUtil.isEmptyString(p)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(deptId)){
                //查询该表单所有字段
                List<String> adEntAtt = LinkSql.getEntCodeAtt(entCode,myJdbcTemplate);
                List<PmRoster> rosterList = new ArrayList<>();
                //循环遍历每个岗位人员(优先取数表单，其次花名册)
                for (String tp : deptId) {
                    //查询该流程岗位对应的字段
                    List<String> code = LinkSql.getProcessPostCode(tp,companyId,myJdbcTemplate);
                    code = code.stream().filter(adEntAtt::contains).collect(Collectors.toList());
                    for (String tmp : code) {
                        //根据流程岗位+项目岗位Code+业主单位 查询项目id
                        List<Map<String,Object>> postIdList = LinkSql.getPrjPostIdByCode(tp,companyId,tmp,myJdbcTemplate);
                        String postId = "";
                        if (!CollectionUtils.isEmpty(postIdList)){
                            postId = JdbcMapUtil.getString(postIdList.get(0),"id");
                        }
                        List<String> userList1 = ProcessRoleExt.getProcessUser(tmp,entCode,csCommId,myJdbcTemplate);
                        if (!CollectionUtils.isEmpty(userList1)){
                            PmRoster pmRoster = new PmRoster();
                            pmRoster.setCustomerUnit(companyId);
                            pmRoster.setPmPrjId(projectId);
                            pmRoster.setPostInfoId(postId); //岗位id
                            pmRoster.setAdUserId(userList1.get(0));
                            rosterList.add(pmRoster);
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(rosterList)){
                    PmRosterExt.updatePrjUser(rosterList);
                }
            }
        }

    }

    /**
     * 根据项目名称，将项目由非系统项目改为系统项目
     * @param projectName 项目名称
     * @param prjId 项目id
     */
    public static void updateProcessTitleByProjectName(String projectName, String prjId) {
        List<WfProcessInstance> list = WfProcessInstance.selectByWhere(new Where().contain(WfProcessInstance.Cols.NAME,projectName)
                .eq(WfProcessInstance.Cols.STATUS,"AP"));
        if (!CollectionUtils.isEmpty(list)){
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
            for (WfProcessInstance tmp : list) {
                //判断该条数据是否符合修改条件
                //业务表名
                String tableName = tmp.getEntCode();
                //该条记录id
                String recordId = tmp.getEntityRecordId();
                checkData(tableName,recordId,projectName,prjId,myJdbcTemplate);
            }
        }
    }

    /**
     * 判断单条流程语句是否符合修改为系统项目条件
     * @param tableName 业务表名
     * @param recordId 业务记录id
     * @param projectName 项目名称
     * @param projectId 项目id
     * @param myJdbcTemplate 数据源
     */
    public static void checkData(String tableName, String recordId, String projectName, String projectId, MyJdbcTemplate myJdbcTemplate) {
        //查询该表是否保函项目来源字段
        String sql2 = "select C.CODE from AD_ENT_ATT A LEFT JOIN AD_ENT B ON A.AD_ENT_ID = B.ID LEFT JOIN AD_ATT C ON A.AD_ATT_ID = C.ID WHERE B.CODE = ?";
        List<Map<String,Object>> codeList = myJdbcTemplate.queryForList(sql2,tableName);
        if (!CollectionUtils.isEmpty(codeList)){
            List<String> list = codeList.stream().map(p->JdbcMapUtil.getString(p,"CODE")).collect(Collectors.toList());
            if (list.contains("PROJECT_SOURCE_TYPE_ID")){
                String sql = "select * from " + tableName +" where id = ? and STATUS NOT IN ('VD','VDING') and PROJECT_NAME_WR = ?";
                List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql,recordId,projectName);
                if (!CollectionUtils.isEmpty(list2)){
                    Crud.from(tableName).where().eq("ID",recordId).update()
                            .set("PROJECT_SOURCE_TYPE_ID","0099952822476441374")
                            .set("PROJECT_NAME_WR",null)
                            .set("PM_PRJ_ID",projectId).exec();
                }
            }
        }
    }

    /**
     * 流程实例数据作废-作废对应流程表
     */
    public void cancelData(){
        List<WfProcessInstance> list = WfProcessInstance.selectByWhere(new Where().eq(WfProcessInstance.Cols.STATUS,"VD"));
        if (!CollectionUtils.isEmpty(list)){
            for (WfProcessInstance tmp : list) {
                String id = tmp.getEntityRecordId();
                String code = tmp.getEntCode();
                Crud.from(code).where().eq("id",id).update().set("status","VD").exec();
            }
        }
    }
}
