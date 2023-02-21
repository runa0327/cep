package com.cisdi.ext.pm;

import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;

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
     * 判断去重，避免同一个人重复提交相同文件
     * @param userName 当前操作人名称
     * @param processComment 流程中的意见信息
     * @param processFile 流程中的文件信息
     * @param comment 本次提交填写的意见信息
     * @param file 本次提交填写的文件信息
     * @return 提交信息
     */
    public static Map<String, String> getEndComment(String userName, String processComment, String processFile, String comment, String file) {
        Map<String,String> map = new HashMap<>();
        if (processComment.contains(userName)){
            List<String> processCommentList = new ArrayList<>(Arrays.asList(processComment.split(";")));
        }
        return map;
    }
}
