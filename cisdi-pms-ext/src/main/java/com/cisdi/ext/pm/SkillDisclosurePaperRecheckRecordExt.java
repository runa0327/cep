package com.cisdi.ext.pm;

import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.interaction.EntityRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 技术交底与图纸会审记录 扩展
 */
@Slf4j
public class SkillDisclosurePaperRecheckRecordExt {

    /**
     * 技术交底与图纸会审记录-按照先后顺序审批-第一次审批
     */
    public void checkFirst() {
        String status = "first";
        checkFirst(status);
    }

    private void checkFirst(String status) {
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
        String comment = "";
        if (!CollectionUtils.isEmpty(list)) {
            comment = list.get(0).get("user_comment") == null ? null : list.get(0).get("user_comment").toString();
        }
        if ("first".equals(status)) {
            Integer exec = Crud.from("SKILL_DISCLOSURE_PAPER_RECHECK_RECORD").where().eq("ID", csCommId).update()
                    .set("COMMENT_PUBLISH_USER", userId).set("COMMENT_PUBLISH_DATE", now).set("REMARK", comment).exec();
            log.info("已更新：{}", exec);
        }
    }
}
