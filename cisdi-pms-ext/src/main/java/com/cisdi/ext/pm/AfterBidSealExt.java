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

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 中选单位及标后用印审批 流程扩展
 */

@Slf4j
public class AfterBidSealExt {
    /**
     * 中选单位及标后用印审批-按照先后顺序审批-第一次审批
     */
    public void checkFirst() {
        String status = "first";
        check(status);
    }

    /**
     * 中选单位及标后用印审批 审批先后顺序 第二次审批
     */
    public void checkSecond() {
        String status = "second";
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
        if ("first".equals(status)) {
            Integer exec = Crud.from("PM_USE_CHAPTER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_COMMENT_ONE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("second".equals(status)) {
            Integer exec = Crud.from("PM_USE_CHAPTER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_COMMENT_TWO", comment).exec();
            log.info("已更新：{}", exec);
        }
    }

    /**
     * 审批通过流程结束，数据写入项目库（招标台账）
     */
    public void createBidAccount(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);

        //流程id
        String csCommId = entityRecord.csCommId;
        String sql1 = "select * from PM_USE_CHAPTER_REQ where id = ?";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,csCommId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("中选单位及标后用印审批流程数据为空，写入招标台账失败！");
        }
        //写入招标台账表
        Map<String,Object> map = list1.get(0);
        String id = Crud.from("PO_PUBLIC_BID").insertData();
        Crud.from("PO_PUBLIC_BID").where().eq("id",id).update()
                .set("NAME",JdbcMapUtil.getString(map,"NAME_ONE")) //招标名称
                .set("APPROVE_PMS_RELEASE_WAY_ID",JdbcMapUtil.getString(map,"BUY_MATTER_ID")) //招标类别
                .set("WIN_BID_UNIT_TXT",JdbcMapUtil.getString(map,"WIN_BID_UNIT_ONE")) // 中标单位
                .set("BID_OPEN_DATE",JdbcMapUtil.getString(map,"DATE_ONE")) //开标日期
                .set("BUY_TYPE_ID",JdbcMapUtil.getString(map,"BUY_TYPE_ID")) //招标方式
                .set("BID_CTL_PRICE_LAUNCH",JdbcMapUtil.getString(map,"PAY_AMT_TWO")) //招标控制价
                .set("TENDER_OFFER",JdbcMapUtil.getString(map,"AMT_ONE")) //投标报价
                .set("CRT_USER_ID",JdbcMapUtil.getString(map,"CRT_USER_ID")) //经办人
                .set("BID_AGENCY",JdbcMapUtil.getString(map,"BID_AGENCY")) //招标代理商
                .set("PM_PRJ_ID",JdbcMapUtil.getString(map,"PM_PRJ_ID")) //项目id
                .exec();


    }
}
