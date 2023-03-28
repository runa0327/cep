package com.cisdi.ext.pm;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
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
        Boolean firstCheck = true; //是第一次审批
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
     * @return
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
     * @return
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
        String newFile = "";
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
        String newComment = "";
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
        String nodeId = JdbcMapUtil.getString(list.get(0),"WF_NODE_ID");
        return nodeId;
    }

    /**
     * 查询流程任务最原始的用户（考虑到转办用户）
     * @param nodeInstanceId 流程节点实例id
     * @param userId 用户id
     * @param myJdbcTemplate 数据源
     * @return
     */
    public static String getOriginalUser(String nodeInstanceId, String userId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select PREV_TASK_ID from wf_task where ad_user_id = ? and WF_NODE_INSTANCE_ID = ? and status = 'ap'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,userId,nodeInstanceId);
        String taskId = "";
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
}
