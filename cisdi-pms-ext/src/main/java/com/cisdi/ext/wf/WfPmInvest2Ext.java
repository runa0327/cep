package com.cisdi.ext.wf;

import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.WfPmInvestUtil;
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
 * 概算。
 */
@Slf4j
public class WfPmInvest2Ext {
    public void setComments() {
        new WfPmInvest1Ext().setComments();
    }

    public void setEarlyComment() {
        new WfPmInvest1Ext().setEarlyComment();
    }

    /**
     * 插入或更新投资估算。
     */
    public void insertOrUpdateInvestEst() {
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        String pmPrjId = String.valueOf(entityRecord.valueMap.get("PM_PRJ_ID"));
        WfPmInvestUtil.calculateData(csCommId, entCode, pmPrjId);
    }

    /**
     * 初设概算-成本岗内部审核-批准
     */
    public void costCheck(){
        String status = "costCheck";
        checkComment(status);
    }

    /**
     * 初设概算-成本岗内部审核-拒绝
     */
    public void costCheckRefuse(){
        String status = "costCheckRefuse";
        checkComment(status);
    }

    /**
     * 初设概算-成本岗/设计岗并行意见-拒绝
     */
    public void costAndDesignCheckRefuse(){
        String status = "costAndDesignCheckRefuse";
        checkComment(status);
    }

    /**
     * 初设概算-成本岗/设计岗并行意见-批准
     */
    public void costAndDesignCheck(){
        String status = "costAndDesignCheck";
        checkComment(status);
    }

    /**
     * 初设概算-前期管理部负责人意见-拒绝
     */
    public void earlyCheckRefuse(){
        String status = "earlyCheckRefuse";
        checkComment(status);
    }

    /**
     * 初设概算-前期管理部负责人意见-批准
     */
    public void earlyCheck(){
        String status = "earlyCheck";
        checkComment(status);
    }

    private void checkComment(String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Date date = new Date();
        String now = DateTimeUtil.dateToString(date);

        //获取当前节点实例id
        String nodeId = ExtJarHelper.nodeInstId.get();

        // 当前登录人
        String userName = ExtJarHelper.loginInfo.get().userName;

        // 当前登录人id
        String userId = ExtJarHelper.loginInfo.get().userId;

        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        String sql = "select tk.USER_COMMENT,tk.USER_ATTACHMENT " +
                "from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id and tk.status='ap' " +
                "join ad_user u on tk.ad_user_id=u.id" +
                " where u.id = ?";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, procInstId,userId);
        String comment = "";
        String file = "";
        if (!CollectionUtils.isEmpty(list)) {
            comment = list.get(0).get("user_comment") == null ? null : list.get(0).get("user_comment").toString();
            file = list.get(0).get("USER_ATTACHMENT") == null ? null : list.get(0).get("USER_ATTACHMENT").toString();
        }
        String updateSql = "";
        String sbComment = "";
        String sbFile = "";
        StringBuilder upSql = new StringBuilder();
        String sql2 = "select count(*) as num from wf_task where WF_NODE_INSTANCE_ID = ? and IS_CLOSED = 1 and AD_USER_ID != ? and status = 'ap' ";
        String num = getNum(myJdbcTemplate,sql2,nodeId,userId);
        if ("costCheck".equals(status)){
            //流程中的审批意见
            String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"REMARK_LONG_ONE");
            if ("0".equals(num)){
                Integer exec = Crud.from("PM_PRJ_INVEST2").where().eq("ID", csCommId).update()
                        .set("REMARK_LONG_ONE", null).exec();
                processComment = "";
            }
            sbComment = autoComment(comment,processComment,userName);
            if (!SharedUtil.isEmptyString(sbComment)){
                upSql.append("update PM_PRJ_INVEST2 set ");
                if (!SharedUtil.isEmptyString(sbComment)){
                    upSql.append(" REMARK_LONG_ONE = '"+sbComment+"', ");
                }
                upSql.append(" LAST_MODI_DT = now() where id = ?");
                Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                log.info("已更新：{}", exec);
            }
        } else if ("costCheckRefuse".equals(status)){
            Integer exec = myJdbcTemplate.update("update PM_PRJ_INVEST2 set REMARK_LONG_ONE = null where id=?",csCommId);
        } else if ("costAndDesignCheck".equals(status)){
            //流程中的审批意见
            String processCommentCost = JdbcMapUtil.getString(entityRecord.valueMap,"COST_COMMENT");
            String processCommentDesign = JdbcMapUtil.getString(entityRecord.valueMap,"DESIGN_COMMENT");
            //查询当前项目设计岗、成本岗人员
            String prjId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
            String sql1 = "select PRJ_DESIGN_USER_ID,PRJ_COST_USER_ID from PM_PRJ where id = ?";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,prjId);
            if (CollectionUtils.isEmpty(list1)){
                throw new BaseException("该项目设计、成本岗人员为空，请联系管理员或补充相关数据!");
            }
            String costUserId = JdbcMapUtil.getString(list1.get(0),"PRJ_COST_USER_ID");
            String designUserId = JdbcMapUtil.getString(list1.get(0),"PRJ_DESIGN_USER_ID");
            if (userId.equals(costUserId)){
                if ("0".equals(num)){
                    Integer exec = Crud.from("PM_PRJ_INVEST2").where().eq("ID", csCommId).update()
                            .set("COST_COMMENT", null).exec();
                    processCommentCost = "";
                }
                sbComment = autoComment(comment,processCommentCost,userName);
                if (!SharedUtil.isEmptyString(sbComment)){
                    upSql.append("update PM_PRJ_INVEST2 set ");
                    if (!SharedUtil.isEmptyString(sbComment)){
                        upSql.append(" COST_COMMENT = '"+sbComment+"', ");
                    }
                    upSql.append(" LAST_MODI_DT = now() where id = ?");
                    Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                    log.info("已更新：{}", exec);
                }
            } else if (userId.equals(designUserId)){
                if ("0".equals(num)){
                    Integer exec = Crud.from("PM_PRJ_INVEST2").where().eq("ID", csCommId).update()
                            .set("DESIGN_COMMENT", null).exec();
                    processCommentDesign = "";
                }
                sbComment = autoComment(comment,processCommentDesign,userName);
                if (!SharedUtil.isEmptyString(sbComment)){
                    upSql.append("update PM_PRJ_INVEST2 set ");
                    if (!SharedUtil.isEmptyString(sbComment)){
                        upSql.append(" DESIGN_COMMENT = '"+sbComment+"', ");
                    }
                    upSql.append(" LAST_MODI_DT = now() where id = ?");
                    Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                    log.info("已更新：{}", exec);
                }
            }
        } else if ("costAndDesignCheckRefuse".equals(status)){
            Integer exec = myJdbcTemplate.update("update PM_PRJ_INVEST2 set COST_COMMENT = null,DESIGN_COMMENT = null where id=?",csCommId);
        } else if ("earlyCheck".equals(status)){
            //流程中的审批意见
            String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_TWO");
            if ("0".equals(num)){
                Integer exec = Crud.from("PM_PRJ_INVEST2").where().eq("ID", csCommId).update()
                        .set("APPROVAL_COMMENT_TWO", null).exec();
                processComment = "";
            }
            sbComment = autoComment(comment,processComment,userName);
            if (!SharedUtil.isEmptyString(sbComment)){
                upSql.append("update PM_PRJ_INVEST2 set ");
                if (!SharedUtil.isEmptyString(sbComment)){
                    upSql.append(" APPROVAL_COMMENT_TWO = '"+sbComment+"', ");
                }
                upSql.append(" LAST_MODI_DT = now() where id = ?");
                Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                log.info("已更新：{}", exec);
            }
        } else if ("earlyCheckRefuse".equals(status)){
            Integer exec = myJdbcTemplate.update("update PM_PRJ_INVEST2 set APPROVAL_COMMENT_TWO = null where id=?",csCommId);
        }
    }

    private String getNum(MyJdbcTemplate myJdbcTemplate,String sql2, String nodeId, String userId) {
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql2,nodeId,userId);
        String num = "";
        if (CollectionUtils.isEmpty(list)){
            num = "0";
        } else {
            num = JdbcMapUtil.getString(list.get(0),"num");
        }
        return num;
    }

    // 审核意见自动拼接
    private String autoComment(String newComment, String oldComment, String userName) {
        StringBuilder sbComment = new StringBuilder();
        if (!SharedUtil.isEmptyString(newComment)){
            if (!SharedUtil.isEmptyString(oldComment)){
                sbComment.append(oldComment).append("; \n").append(userName).append(":").append(newComment);
            } else {
                sbComment.append(userName).append(":").append(newComment);
            }
        }
        return sbComment.toString();
    }
}
