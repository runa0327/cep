package com.cisdi.ext.pm.processCommon;

import cn.hutool.core.util.IdUtil;
import com.cisdi.ext.link.LinkSql;
import com.cisdi.ext.model.*;
import com.cisdi.ext.model.base.AdRoleUser;
import com.cisdi.ext.model.base.BaseMatterTypeCon;
import com.cisdi.ext.pm.PmRosterExt;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
     * @return 审批意见信息
     */
    public static Map<String, String> getCommentNew(String nodeInstanceId, String userId, MyJdbcTemplate myJdbcTemplate, String procInstId) {
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
    public static String getNewFile(String userId, String nodeInstanceId, String processFile, String file,MyJdbcTemplate myJdbcTemplate) {
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
        String sql1 = "select GROUP_CONCAT(DISTINCT USER_ATTACHMENT) as USER_ATTACHMENT from wf_task where AD_USER_ID = ? and WF_NODE_INSTANCE_ID = ? and USER_ATTACHMENT is not null and status = 'ap'";
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
     * 流程审批-意见回显-获取最终回显文件信息
     * @param userId 用户id
     * @param processFile 流程表单中文件
     * @param file 本次操作点击上传文件
     * @param myJdbcTemplate 数据源
     * @param specialCode 特殊情况代码
     * @return 最终需要回显的文件id
     */
    public static String getEndCommentFile(String userId, String processFile, String file, MyJdbcTemplate myJdbcTemplate, String specialCode) {
        String endFile = "";
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        if (SharedUtil.isEmptyString(processFile)){
            endFile = file;
        } else {
            if ("one".equals(specialCode)) { // 项目问题文件意见回显
                endFile = getNewFile(userId,nodeInstanceId,processFile,file,myJdbcTemplate);
            }
        }
        return endFile;
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
        String sql = "select * from "+entityCode+" where pm_prj_id = ? and status NOT in ('VD','VDING','DR') and id != ?";
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
        String sql = "select * from "+entityCode+" where PROJECT_NAME_WR = ? and status NOT in ('VD','VDING','DR') and id != ?";
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
     * @param tableName 表名
     * @param id 表数据id
     * @param myJdbcTemplate 数据源
     * @return 流程岗位id
     */
    public static String getUserProcessPost(String userId,String projectId, String companyId,String tableName, String id, MyJdbcTemplate myJdbcTemplate) {
        String processPostId;
        String sql = "select a.BASE_PROCESS_POST_ID FROM PM_POST_PROPRJ a " +
                "left join PM_ROSTER b on a.POST_INFO_ID = b.POST_INFO_ID " +
                "where b.PM_PRJ_ID = ? and b.CUSTOMER_UNIT = ? and b.AD_USER_ID = ? and a.STATUS = 'AP' and b.STATUS = 'AP'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,projectId,companyId,userId);
        if (!CollectionUtils.isEmpty(list)){
            processPostId = JdbcMapUtil.getString(list.get(0),"BASE_PROCESS_POST_ID");
        } else {
            processPostId = getUserPostByProcess(tableName,id,userId,myJdbcTemplate);
        }
        return processPostId;
    }

    /**
     * 获取人员在流程发起中选择的岗位id信息
     * @param tableName 流程表名
     * @param id 流程表数据id
     * @param userId 用户id
     * @param myJdbcTemplate 数据源
     * @return 岗位id
     */
    public static String getUserPostByProcess(String tableName, String id, String userId, MyJdbcTemplate myJdbcTemplate) {
        String postId = "",postCode = "";
        List<Map<String,Object>> list = LinkSql.getPrjProcessUserDept(id,tableName,myJdbcTemplate);
        if (!CollectionUtils.isEmpty(list)){
            tp:for (Map<String, Object> tp : list) {
                for (String tmp : tp.keySet()){
                    if (tmp.contains("AD_USER") && userId.equals(tp.get(tmp))){
                        postCode = tmp;
                        break tp;
                    }
                }
            }
        }
        switch (postCode) {
            case "AD_USER_FIFTEEN_ID":  //计划运营岗
                postId = "1649315416767717376";
                break;
            case "AD_USER_THIRTEEN_ID":  //土地管理岗
                postId = "1638733006686535680";
                break;
            case "AD_USER_TWENTY_ONE_ID":  //采购管理岗
                postId = "1637991453391237120";
                break;
            case "AD_USER_TWENTY_THREE_ID":  //工程管理岗
                postId = "1637988041987633152";
                break;
            case "AD_USER_NINETEEN_ID":  //合约管理岗
                postId = "1637988017434177536";
                break;
            case "AD_USER_TWENTY_FIVE_ID":  //财务管理岗
                postId = "1637988004163399680";
                break;
            case "AD_USER_TWENTY_TWO_ID":  //设计管理岗
                postId = "1637987984030740480";
                break;
            case "AD_USER_EIGHTEEN_ID":  //成本管理岗
                postId = "1637987965638717440";
                break;
            case "AD_USER_TWELVE_ID":  //前期报建岗
                postId = "1636192694655168512";
                break;
        }
        return postId;
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
            List<HrDeptUser> list1 = list.stream().filter(HrDeptUser::getSysTrue).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(list1)){
                list1 = list.stream().filter(p-> !p.getSysTrue()).collect(Collectors.toList());
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
     * 查询流程中所有经办人信息-发起人不在内
     * @param procInstId 流程实例id
     * @param processId 流程id
     * @param myJdbcTemplate 数据源
     * @return 经办人信息
     */
    public static String getProcessAllDealUserNotStart(String procInstId, String processId, MyJdbcTemplate myJdbcTemplate) {
        // 获取该流程发起节点信息
        String startNodeId = WfNode.selectByWhere(new Where().eq(WfNode.Cols.WF_PROCESS_ID,processId)
                .eq(WfNode.Cols.NODE_TYPE,"START_EVENT").eq(WfNode.Cols.STATUS,"AP")).get(0).getId();
        // 查询所有人员信息
        String sql = "select group_concat(distinct a.ad_user_id) as users from wf_task a left join wf_node_instance b on a.WF_NODE_INSTANCE_ID = b.id where b.status = 'ap' and a.status = 'ap' and b.WF_PROCESS_INSTANCE_ID = ? and b.WF_NODE_ID != ? and a.wf_task_type_id = 'TODO' and a.is_closed = '1'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,procInstId,startNodeId);
        String userIds = "";
        if (!CollectionUtils.isEmpty(list)){
            userIds = JdbcMapUtil.getString(list.get(0),"users");
        }
        return userIds;
    }

    /**
     * 流程实例数据作废-作废对应流程表
     */
    public void cancelData(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<WfProcessInstance> list = WfProcessInstance.selectByWhere(new Where().eq(WfProcessInstance.Cols.STATUS,"VD"));
        if (!CollectionUtils.isEmpty(list)){
            for (WfProcessInstance tmp : list) {
                String mainId = tmp.getId();
                String id = tmp.getEntityRecordId();
                String code = tmp.getEntCode();
                List<AdEnt> adEntList = AdEnt.selectByWhere(new Where().eq(AdEnt.Cols.CODE,code));
                if (!CollectionUtils.isEmpty(adEntList)){
                    Crud.from(code).where().eq("id",id).update().set("status","VD").exec();
                }
                Crud.from(WfProcessInstance.ENT_CODE).where().eq(WfProcessInstance.Cols.ID,mainId).update().set("STATUS","VD").exec();
                Crud.from("WF_NODE_INSTANCE").where().eq("WF_PROCESS_INSTANCE_ID",mainId).update().set("STATUS","VD").exec();
                Crud.from("WF_TASK").where().eq("WF_PROCESS_INSTANCE_ID",mainId).update().set("STATUS","VD").exec();
            }
        }

        //正式环境不启用以下代码
//        List<WfProcessInstance> list2 = WfProcessInstance.selectByWhere(new Where().eq(WfProcessInstance.Cols.STATUS,"AP"));
//        if (!CollectionUtils.isEmpty(list2)){
//            for (WfProcessInstance tmp : list2) {
//                String mainId = tmp.getId();
//                String id = tmp.getEntityRecordId();
//                String code = tmp.getEntCode();
//                List<AdEnt> adEntList = AdEnt.selectByWhere(new Where().eq(AdEnt.Cols.CODE,code));
//                if (!CollectionUtils.isEmpty(adEntList)){
//                    String sql = "select id from "+code+" where id = ?";
//                    List<Map<String,Object>> list3 = myJdbcTemplate.queryForList(sql,id);
//                    if (CollectionUtils.isEmpty(list3)){
//                        Crud.from(WfProcessInstance.ENT_CODE).where().eq(WfProcessInstance.Cols.ID,mainId).update().set("STATUS","VD").exec();
//                        Crud.from("WF_NODE_INSTANCE").where().eq("WF_PROCESS_INSTANCE_ID",mainId).update().set("STATUS","VD").exec();
//                        Crud.from("WF_TASK").where().eq("WF_PROCESS_INSTANCE_ID",mainId).update().set("STATUS","VD").exec();
//                    } else {
//                        String id2 = JdbcMapUtil.getString(list3.get(0),"id");
//                        if (SharedUtil.isEmptyString(id2)){
//                            Crud.from(WfProcessInstance.ENT_CODE).where().eq(WfProcessInstance.Cols.ID,mainId).update().set("STATUS","VD").exec();
//                            Crud.from("WF_NODE_INSTANCE").where().eq("WF_PROCESS_INSTANCE_ID",mainId).update().set("STATUS","VD").exec();
//                            Crud.from("WF_TASK").where().eq("WF_PROCESS_INSTANCE_ID",mainId).update().set("STATUS","VD").exec();
//                        }
//                    }
//                } else {
//                    Crud.from(WfProcessInstance.ENT_CODE).where().eq(WfProcessInstance.Cols.ID,mainId).update().set("STATUS","VD").exec();
//                    Crud.from("WF_NODE_INSTANCE").where().eq("WF_PROCESS_INSTANCE_ID",mainId).update().set("STATUS","VD").exec();
//                    Crud.from("WF_TASK").where().eq("WF_PROCESS_INSTANCE_ID",mainId).update().set("STATUS","VD").exec();
//                }
//            }
//        }
    }

    /**
     * 流程通用-多项目流程实时校验更新项目明细
     */
    public void checkProcessPrjDetail(){
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String pmPrjIds = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_IDS");
        if (!SharedUtil.isEmptyString(pmPrjIds)){
            String csCommId = entityRecord.csCommId;
            String[] prjArr = pmPrjIds.split(",");
            if (prjArr.length > 0){
                String detailCode = entCode + "_PRJ_DETAIL";
                String parentId = entCode + "_ID";
                Crud.from(detailCode).where().eq(parentId,csCommId).delete().exec();
                for (String tp : prjArr) {
                    String id = Crud.from(detailCode).insertData();
                    Crud.from(detailCode).where().eq("ID",id).update()
                            .set(parentId,csCommId).set("PM_PRJ_ID",tp)
                            .exec();
                }
            }
        }
    }

    /**
     * 合同签订模块-财务第一个人审批后添加后续人员审批-模拟先后顺序
     * @param nodeInstanceId 节点实例id
     * @param roleId 后续审批角色id
     * @param processId 流程id
     * @param procInstId 流程实例id
     * @param nodeId 节点id
     */
    public static void createOrderFinanceCheckUser(String nodeInstanceId, String roleId, String processId, String procInstId, String nodeId) {
        List<String> newRoleUserList = new ArrayList<>();
        // 查询人员信息
        List<AdRoleUser> list = AdRoleUser.selectByWhere(new Where().eq(AdRoleUser.Cols.AD_ROLE_ID,roleId).eq(AdRoleUser.Cols.STATUS,"AP"));
        if (!CollectionUtils.isEmpty(list)){
            //查询该节点实例审批人员信息
            List<WfTask> taskList = WfTask.selectByWhere(new Where().eq(WfTask.Cols.WF_NODE_INSTANCE_ID,nodeInstanceId)
                    .eq(WfTask.Cols.STATUS,"AP"));
            List<String> taskUserList = taskList.stream().map(WfTask::getAdUserId).collect(Collectors.toList());
            for (AdRoleUser tmp : list) {
                String newUserId = tmp.getAdUserId();
                if (!taskUserList.contains(newUserId)){
                    newRoleUserList.add(newUserId);
                }
            }
            //遍历新的审批人员信息写入任务表
            if (!CollectionUtils.isEmpty(newRoleUserList)){
                String now = DateTimeUtil.dateToString(new Date());
                for (String tp : newRoleUserList) {
                    String id = Crud.from("WF_TASK").insertData();
                    String seqNo = IdUtil.getSnowflakeNextIdStr();
                    Crud.from("WF_TASK").where().eq("ID",id).update()
                            .set("VER","1").set("WF_NODE_INSTANCE_ID",nodeInstanceId).set("AD_USER_ID",tp)
                            .set("RECEIVE_DATETIME",now).set("IS_CLOSED","0").set("STATUS","AP")
                            .set("WF_TASK_TYPE_ID","TODO").set("IN_CURRENT_ROUND","1").set("TS",now)
                            .set("SEQ_NO",seqNo) //序号需要重新生成
                            .set("WF_PROCESS_ID",processId).set("WF_PROCESS_INSTANCE_ID",procInstId).set("WF_NODE_ID",nodeId)
                            .set("IS_PROC_INST_FIRST_TODO_TASK",0).set("IS_USER_LAST_CLOSED_TODO_TASK",1)
                            .set("READ_RELATED",0).set("DISPATCH_RELATED",0).set("COMMENT_RELATED",0).set("REMIND_TIMES",0)
                            .exec();
                }
            }
        }
    }

    /**
     * 根据采购事项反推更新采购事项分类
     * @param matterId 采购事项id
     * @param entCode 业务表名
     * @param id 业务表唯一id
     * @return 采购事项分类id
     */
    public static String updateMatterTypeId(String matterId, String entCode, String id) {
        String matterTypeId = "";
        List<BaseMatterTypeCon> list = BaseMatterTypeCon.selectByWhere(new Where().eq(BaseMatterTypeCon.Cols.GR_SET_VALUE_ID,matterId)
                .eq(BaseMatterTypeCon.Cols.STATUS,"AP"));
        if (!CollectionUtils.isEmpty(list)){
            matterTypeId = list.get(0).getGrSetValueOneId();
            Crud.from(entCode).where().eq("ID",id).update().set("BUY_MATTER_TYPE_ID",matterTypeId).exec();
        } else {
            throw new BaseException("对不起，所选采购事项未匹配到采购事项一级分类，请联系管理员处理！");
        }
        return matterTypeId;
    }

/**====================================================================================================================**/
/**==========================================更新流程主表单字段开始========================================================**/
/**====================================================================================================================**/

     /**
     * 流程审批意见回显通用方法
     * @param colCode 回显字段
     * @param map 数据源集合
     * @param comment 本次审批提交意见
     * @param entCode 需要更新的表名
     * @param csCommId 更新的表的id
     * @param userName 本次操作人员名称
     */
    public static void updateComment(String colCode, Map<String, Object> map, String comment, String entCode, String csCommId, String userName) {
        //获取流程中的意见信息
        String processComment = JdbcMapUtil.getString(map,colCode);
        //生成最终的意见信息
        String commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
        updateProcColsValue(colCode,entCode,csCommId,commentEnd);
    }

    /**
     * 流程审批拒绝，清空某一字段
     * @param colCode 回显字段
     * @param entCode 需要更新的表
     * @param csCommId 需要更新的表的id
     * @param value 需要更新的值
     */
    public static void updateProcColsValue(String colCode, String entCode, String csCommId, String value) {
        Crud.from(entCode).where().eq("id",csCommId).update()
                .set(colCode,value).exec();
    }

/**====================================================================================================================**/
/**==========================================更新流程主表单字段结束========================================================**/
/**====================================================================================================================**/

/**====================================================================================================================**/
/**==========================================通用-历史数据处理开始*========================================================**/
/**====================================================================================================================**/

    /**
     * 流程历史数据处理-所有未作废数据写入项目明细表
     */
    public void prjDetailInsert(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String sql = "select * from " + entCode + " where status not in ('VD','VDING') ";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        if (!CollectionUtils.isEmpty(list)){
            String detailCode = entCode + "_PRJ_DETAIL";
            String parentId = entCode + "_ID";
            for (Map<String, Object> map : list) {
                String reqId = JdbcMapUtil.getString(map,"ID");
                String prjIds = JdbcMapUtil.getString(map,"PM_PRJ_IDS");
                if (StringUtils.hasText(prjIds)){
                    Crud.from(detailCode).where().eq(parentId,reqId).delete().exec();
                    String[] prjArr = prjIds.split(",");
                    for (String prjId : prjArr) {
                        String id = Crud.from(detailCode).insertData();
                        Crud.from(detailCode).where().eq("ID",id).update()
                                .set(parentId,reqId)
                                .set("PM_PRJ_ID",prjId)
                                .exec();
                    }
                }
            }
        }
    }

    /**
     * 流程历史数据处理-更新流程业务表name字段
     */
    public void updateProcName(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String sql = "select * from " + entCode + " where status not in ('VD','VDING') ";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        if (!CollectionUtils.isEmpty(list)){
            for (Map<String, Object> map : list) {
                String reqId = JdbcMapUtil.getString(map,"ID");
                String processInstanceId = JdbcMapUtil.getString(map,"LK_WF_INST_ID");
                List<Map<String,Object>> list2 = myJdbcTemplate.queryForList("select name from wf_process_instance where id = ?",processInstanceId);
                if (!CollectionUtils.isEmpty(list2)){
                    String name = JdbcMapUtil.getString(list2.get(0),"name");
                    Crud.from(entCode).where().eq("ID",reqId).update().set("NAME",name).exec();
                }
            }
        }
    }

    /**
     * 流程实例删除-对应业务表找不到数据即删除
     */
    public void deleteNotInEntCode() throws Exception{
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String,Object>> list = myJdbcTemplate.queryForList("select * from wf_process_instance where ENT_CODE not in ('PM_PRJ_REQ','PM_PRJ_INVEST1','PM_PRJ_INVEST2','PM_BUY_DEMAND_REQ','PO_ORDER_CHANGE_REQ','PM_PRJ_INVEST3','PO_ORDER_REQ','PO_GUARANTEE_LETTER_REQUIRE_REQ','PO_GUARANTEE_LETTER_RETURN_OA_REQ')");
        if (!CollectionUtils.isEmpty(list)){
            for (Map<String, Object> map : list) {
                String entCode = JdbcMapUtil.getString(map,"ENT_CODE");
                String id = JdbcMapUtil.getString(map,"ID");
                try {
//                    String recordId = JdbcMapUtil.getString(map,"ENTITY_RECORD_ID");

//                    String sql3 = "select id from " + entCode + " where id = ?";

//                    List<Map<String,Object>> list3 = myJdbcTemplate.queryForList(sql3,recordId);
//                    if (CollectionUtils.isEmpty(list3)){
                        String sql2 = "set foreign_key_checks = 0; delete from WF_TASK where WF_PROCESS_INSTANCE_ID = ?;" +
                                "delete from WF_NODE_INSTANCE where WF_PROCESS_INSTANCE_ID = ?;" +
                                "delete from wf_process_instance where id = ?;set foreign_key_checks = 1;";
                        myJdbcTemplate.update(sql2,id,id,id);
//                    } else {
//                        String sql = "select id from " + entCode + " where id = ? and status in ('VD','VDING')";
//                        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql,recordId);
//                        if (!CollectionUtils.isEmpty(list2)){
//                            String sql2 = "set foreign_key_checks = 0; update WF_TASK set status = 'VD' where WF_PROCESS_INSTANCE_ID = ?;" +
//                                    "UPDATE WF_NODE_INSTANCE SET STATUS = 'VD' where WF_PROCESS_INSTANCE_ID = ?;" +
//                                    "UPDATE wf_process_instance SET STATUS = 'VD' where id = ?;set foreign_key_checks = 1;";
//                            myJdbcTemplate.update(sql2,id,id,id);
//                        }
//                        }
//                    }
                } catch (Exception e){
                    System.out.println("错误信息：" + entCode + "; " + id);
                }

            }
        }
    }


/**====================================================================================================================**/
/**==========================================通用-历史数据处理结束*========================================================**/
/**====================================================================================================================**/

}
