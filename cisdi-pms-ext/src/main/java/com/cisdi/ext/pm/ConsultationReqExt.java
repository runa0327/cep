package com.cisdi.ext.pm;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
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
                if (i != list.size()-1){
                    sb.append(JdbcMapUtil.getString(list.get(i),"value")).append("\n");
                    if (!SharedUtil.isEmptyString(file1)){
                        file.append(file1).append(",");
                    }
                } else {
                    sb.append(JdbcMapUtil.getString(list.get(i),"value"));
                    file.append(file1);
                }
            }
            StringBuilder updateSql = new StringBuilder("update CONSULTATION_REQ set APPROVAL_COMMENT_TWO = ?");
            if (file.length()>0){
                updateSql.append(",FILE_ID_TWO=?");
            }
            updateSql.append(" where id = ?");

            Integer exec = myJdbcTemplate.update(updateSql.toString(),sb.toString(),file.toString(),csCommId);
            log.info("已执行：{}",exec);
        }
    }
}
