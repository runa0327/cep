package com.cisdi.ext.pm;

import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 意见征询流程-扩展
 */
@Slf4j
public class ConsultationReqExt {

    /**
     * 审批意见回显
     */
    public void allUserCheck(){
        String type = "allUser";
        commentShow(type);
    }

    /**
     *
     * @param type 回显类型
     */
    private void commentShow(String type) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 流程实例id
        String procInstId = ExtJarHelper.procInstId.get();
        //
        String csCommId = ExtJarHelper.entityRecordList.get().get(0).csCommId;

        String sql = "select CONCAT(c.name,': ',a.USER_COMMENT) as value,a.USER_ATTACHMENT from wf_task a " +
                "left join wf_node_instance b on a.WF_NODE_INSTANCE_ID = b.id left join ad_user c on a.ad_user_id = c.id " +
                "where a.wf_process_instance_id = ? and a.WF_NODE_ID = '1621440689575469056' and b.status = 'ap'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,procInstId);
        if (!CollectionUtils.isEmpty(list)){
            StringBuilder sb = new StringBuilder();
            StringBuilder file = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                String file1 = JdbcMapUtil.getString(list.get(i),"USER_ATTACHMENT");
                String value1 = JdbcMapUtil.getString(list.get(i),"value");
                if (i != list.size()-1){
                    if (!SharedUtil.isEmptyString(value1)){
                        sb.append(value1).append("\n");
                    }
                    if (!SharedUtil.isEmptyString(file1)){
                        file.append(file1).append(",");
                    }
                } else {
                    if (!SharedUtil.isEmptyString(value1)){
                        sb.append(value1);
                    }
                    if (!SharedUtil.isEmptyString(file1)){
                        file.append(file1);
                    }
                }
            }
            StringBuilder updateSql = new StringBuilder("update CONSULTATION_REQ set APPROVAL_COMMENT_TWO = ?");
            if (file.length()>0){
                updateSql.append(",FILE_ID_TWO = '").append(file).append("'");
            }
            updateSql.append(" where id = ?");

            Integer exec = myJdbcTemplate.update(updateSql.toString(),sb.toString(),csCommId);
            log.info("已执行：{}",exec);
        }
    }

    /**
     * 意见征询-批准按钮
     */
    public void consultationCheck(){
        String status = "checkOk";
        processHandle(status);
    }

    /**
     * 意见征询-拒绝按钮
     */
    public void consultationCheckRefuse(){
        String status = "checkRefuse";
        processHandle(status);
    }

    /**
     * 流程流转逻辑处理
     * @param status 状态码
     */
    private void processHandle(String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        // 流程实例id
        String procInstId = ExtJarHelper.procInstId.get();
        // 流程业务表id
        String csCommId = entityRecord.csCommId;
        if ("checkRefuse".equals(status)){
            Crud.from("CONSULTATION_REQ").where().eq("id",csCommId).update()
                    .set("APPROVAL_COMMENT_TWO",null).set("FILE_ID_TWO",null).exec();
        } else {
            //当前操作人
            String userName = ExtJarHelper.loginInfo.get().userName;
            String userId = ExtJarHelper.loginInfo.get().userId;
            //获取提交产生的附件和意见信息
            Map<String,String> map = ProcessCommon.getComment(procInstId,userId,myJdbcTemplate);
            String comment = map.get("comment");
            String file = "";
            if (map.containsKey("file")){
                file = map.get("file");
            }
            //获取流程中的附件和意见信息
            String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_TWO");
            String processFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_TWO");
            //判断生成最终的意见和附件信息
            Map<String,String> map2 = ProcessCommon.getEndComment(userId,userName,processComment,processFile,comment,file,myJdbcTemplate);
            String newCommentStr = map2.get("comment");
            String newCommentFile = SharedUtil.isEmptyString(map2.get("file")) ? null:map2.get("file");
            Crud.from("CONSULTATION_REQ").where().eq("id",csCommId).update()
                    .set("APPROVAL_COMMENT_TWO",newCommentStr).set("FILE_ID_TWO",newCommentFile).exec();
        }

    }

}
