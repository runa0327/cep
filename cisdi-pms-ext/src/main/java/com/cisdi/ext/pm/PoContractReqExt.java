package com.cisdi.ext.pm;

import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author dlt
 * @date 2022/12/15 周四
 */
@Slf4j
public class PoContractReqExt {

    /**
     * 法律顾问审批
     */
    public void layerCheck(){
        checkApproval("layerCheck");
    }

    /**
     * 法律部审批
     */
    public void legalCheck(){
        checkApproval("legalCheck");
    }

    /**
     * 财务审批
     */
    public void financeCheck(){
        checkApproval("financeCheck");
    }

    /**
     * 法务财务审批
     */
    public void legalFinanceCheck(){
        checkApproval("legalFinanceCheck");
    }

    /**
     * 部门领导审批
     */
    public void deptLeaderCheck(){
        checkApproval("deptLeaderCheck");
    }

    /**
     * 分管领导审批
     */
    public void chargeLeaderCheck(){
        checkApproval("chargeLeaderCheck");
    }


    private void checkApproval(String status){
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
        // 审批意见
        String sql = "select tk.USER_COMMENT,tk.USER_ATTACHMENT " +
                "from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id and tk.status='ap' " +
                "join ad_user u on tk.ad_user_id=u.id" +
                " where u.id = ?";
        List<Map<String, Object>> commentFileList = myJdbcTemplate.queryForList(sql, procInstId,userId);

        String comment = "";
        String file = "";
        String sbComment = "";
        String sbFile = "";
        StringBuilder upSql = new StringBuilder();
        if (!CollectionUtils.isEmpty(commentFileList)) {
            comment = commentFileList.get(0).get("USER_COMMENT") == null ? null : commentFileList.get(0).get("USER_COMMENT").toString();
            file = commentFileList.get(0).get("USER_ATTACHMENT") == null ? null : commentFileList.get(0).get("USER_ATTACHMENT").toString();
        }
        //判断是否是当轮拒绝回来的、撤销回来的
        String entryNumSql = "select count(*) as num from wf_task where WF_NODE_INSTANCE_ID = ? and IS_CLOSED = 1 and AD_USER_ID != ?";
        List<Map<String,Object>> entryNumList = myJdbcTemplate.queryForList(entryNumSql,nodeId,userId);
        //法律顾问审批
        if ("layerCheck".equals(status)){
            //流程中的审批意见
            String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_ONE");
            String processFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_TWO");

            if (!CollectionUtils.isEmpty(entryNumList)){
                String num = JdbcMapUtil.getString(entryNumList.get(0),"num");
                if (SharedUtil.isEmptyString(num) || "0".equals(num)){
                    Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                            .set("APPROVAL_COMMENT_ONE", null).set("FILE_ID_TWO",null).exec();
                    processComment = "";
                    processFile = "";
                }
            } else {
                Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                        .set("APPROVAL_COMMENT_ONE", null).set("FILE_ID_TWO",null).exec();
                processComment = "";
                processFile = "";
            }
            sbComment = autoComment(comment,processComment,userName);
            sbFile = autoFile(file,processFile);
            if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                upSql.append("update PO_ORDER_CHANGE_REQ set ");
                if (!SharedUtil.isEmptyString(sbComment)){
                    upSql.append(" APPROVAL_COMMENT_ONE = '"+sbComment+"', ");
                }
                if (!SharedUtil.isEmptyString(sbFile)){
                    upSql.append(" FILE_ID_TWO = '"+sbFile+"', ");
                }
                upSql.append(" LAST_MODI_DT = now() where id = ?");
                Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                log.info("已更新：{}", exec);
            }
        }else if ("legalCheck".equals(status)){//法务审批
            //流程中的审批意见
            String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_TWO");
            String processFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_THREE");
            if (!CollectionUtils.isEmpty(entryNumList)){
                String num = JdbcMapUtil.getString(entryNumList.get(0),"num");
                if (SharedUtil.isEmptyString(num) || "0".equals(num)){
                    Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                            .set("APPROVAL_COMMENT_TWO", null).set("FILE_ID_THREE",null).exec();
                    processComment = "";
                    processFile = "";
                }
            } else {
                Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                        .set("APPROVAL_COMMENT_TWO", null).set("FILE_ID_THREE",null).exec();
                processComment = "";
                processFile = "";
            }
            sbComment = autoComment(comment,processComment,userName);
            sbFile = autoFile(file,processFile);
            if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                upSql.append("update PO_ORDER_CHANGE_REQ set ");
                if (!SharedUtil.isEmptyString(sbComment)){
                    upSql.append(" APPROVAL_COMMENT_TWO = '"+sbComment+"', ");
                }
                if (!SharedUtil.isEmptyString(sbFile)){
                    upSql.append(" FILE_ID_THREE = '"+sbFile+"', ");
                }
                upSql.append(" LAST_MODI_DT = now() where id = ?");
                Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                log.info("已更新：{}", exec);
            }
        }else if ("financeCheck".equals(status)){//财务审批
            //流程中的审批意见
            String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_THREE");
            String processFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_FOUR");
            if (!CollectionUtils.isEmpty(entryNumList)){
                String num = JdbcMapUtil.getString(entryNumList.get(0),"num");
                if (SharedUtil.isEmptyString(num) || "0".equals(num)){
                    Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                            .set("APPROVAL_COMMENT_THREE", null).set("FILE_ID_FOUR",null).exec();
                    processComment = "";
                    processFile = "";
                }
            } else {
                Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                        .set("APPROVAL_COMMENT_THREE", null).set("FILE_ID_FOUR",null).exec();
                processComment = "";
                processFile = "";
            }
            sbComment = autoComment(comment,processComment,userName);
            sbFile = autoFile(file,processFile);
            if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                upSql.append("update PO_ORDER_CHANGE_REQ set ");
                if (!SharedUtil.isEmptyString(sbComment)){
                    upSql.append(" APPROVAL_COMMENT_THREE = '"+sbComment+"', ");
                }
                if (!SharedUtil.isEmptyString(sbFile)){
                    upSql.append(" FILE_ID_FOUR = '"+sbFile+"', ");
                }
                upSql.append(" LAST_MODI_DT = now() where id = ?");
                Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                log.info("已更新：{}", exec);
            }
        }else if ("legalFinanceCheck".equals(status)){//法务财务审批
            //流程中的审批意见
            String legalComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_TWO");//法务意见
            String legalFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_THREE");//法务意见
            String financialComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_THREE");//财务意见
            String financialFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_FOUR");//财务附件
            //查询该人员角色信息
            String roleSql = "select b.id,b.name from ad_role_user a left join ad_role b on a.AD_ROLE_ID = b.id where a.AD_USER_ID = ? and b.id in ('0100070673610711083','0099902212142039415')";
            userId = ProcessCommon.getOriginalUser(nodeId,userId,myJdbcTemplate);
            List<Map<String,Object>> roleList = myJdbcTemplate.queryForList(roleSql,userId);
            if (!CollectionUtils.isEmpty(roleList)){
                if (!CollectionUtils.isEmpty(entryNumList)){
                    String num = JdbcMapUtil.getString(entryNumList.get(0),"num");
                    if (SharedUtil.isEmptyString(num) || "0".equals(num)){
                        Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                                .set("APPROVAL_COMMENT_TWO", null).set("FILE_ID_THREE",null).set("APPROVAL_COMMENT_THREE",null)
                                .set("FILE_ID_FOUR",null).exec();
                        legalComment = "";
                        legalFile = "";
                        financialComment = "";
                        financialFile = "";
                    }
                } else {
                    Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                            .set("APPROVAL_COMMENT_TWO", null).set("FILE_ID_THREE",null).set("APPROVAL_COMMENT_THREE",null)
                            .set("FILE_ID_FOUR",null).exec();
                    legalComment = "";
                    legalFile = "";
                    financialComment = "";
                    financialFile = "";
                }
                // 0099902212142039415 = 法务部门;0100070673610711083=财务部
                String id = JdbcMapUtil.getString(roleList.get(0),"id");

                if ("0099902212142039415".equals(id)){ //法务
                    sbComment = autoComment(comment,legalComment,userName);
                    sbFile = autoFile(file,legalFile);
                    if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                        upSql.append("update PO_ORDER_CHANGE_REQ set ");
                        if (!SharedUtil.isEmptyString(sbComment)){
                            upSql.append(" APPROVAL_COMMENT_TWO = '"+sbComment+"', ");
                        }
                        if (!SharedUtil.isEmptyString(sbFile)){
                            upSql.append(" FILE_ID_THREE = '"+sbFile+"', ");
                        }
                        upSql.append(" LAST_MODI_DT = now() where id = ?");
                        Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                        log.info("已更新：{}", exec);
                    }
                }
                if ("0100070673610711083".equals(id)){
                    sbComment = autoComment(comment,financialComment,userName);
                    sbFile = autoFile(file,financialFile);
                    if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                        upSql.append("update PO_ORDER_CHANGE_REQ set ");
                        if (!SharedUtil.isEmptyString(sbComment)){
                            upSql.append(" APPROVAL_COMMENT_THREE = '"+sbComment+"', ");
                        }
                        if (!SharedUtil.isEmptyString(sbFile)){
                            upSql.append(" FILE_ID_FOUR = '"+sbFile+"', ");
                        }
                        upSql.append(" LAST_MODI_DT = now() where id = ?");
                        Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                        log.info("已更新：{}", exec);
                    }
                }
            }
        }else if ("deptLeaderCheck".equals(status)){//部门领导审批
            //流程中的审批意见
            String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_FOUR");
            String processFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_FIVE");
            if (!CollectionUtils.isEmpty(entryNumList)){
                String num = JdbcMapUtil.getString(entryNumList.get(0),"num");
                if (SharedUtil.isEmptyString(num) || "0".equals(num)){
                    Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                            .set("APPROVAL_COMMENT_FOUR", null).set("FILE_ID_FIVE",null).exec();
                    processComment = "";
                    processFile = "";
                }
            } else {
                Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                        .set("APPROVAL_COMMENT_FOUR", null).set("FILE_ID_FIVE",null).exec();
                processComment = "";
                processFile = "";
            }
            sbComment = autoComment(comment,processComment,userName);
            sbFile = autoFile(file,processFile);
            if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                upSql.append("update PO_ORDER_CHANGE_REQ set ");
                if (!SharedUtil.isEmptyString(sbComment)){
                    upSql.append(" APPROVAL_COMMENT_FOUR = '"+sbComment+"', ");
                }
                if (!SharedUtil.isEmptyString(sbFile)){
                    upSql.append(" FILE_ID_FIVE = '"+sbFile+"', ");
                }
                upSql.append(" LAST_MODI_DT = now() where id = ?");
                Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                log.info("已更新：{}", exec);
            }
        }else if ("chargeLeaderCheck".equals(status)){//分管领导审批
            //流程中的审批意见
            String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_FIVE");
            String processFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_SIX");
            if (!CollectionUtils.isEmpty(entryNumList)){
                String num = JdbcMapUtil.getString(entryNumList.get(0),"num");
                if (SharedUtil.isEmptyString(num) || "0".equals(num)){
                    Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                            .set("APPROVAL_COMMENT_FIVE", null).set("FILE_ID_SIX",null).exec();
                    processComment = "";
                    processFile = "";
                }
            } else {
                Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                        .set("APPROVAL_COMMENT_FIVE", null).set("FILE_ID_SIX",null).exec();
                processComment = "";
                processFile = "";
            }
            sbComment = autoComment(comment,processComment,userName);
            sbFile = autoFile(file,processFile);
            if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                upSql.append("update PO_ORDER_CHANGE_REQ set ");
                if (!SharedUtil.isEmptyString(sbComment)){
                    upSql.append(" APPROVAL_COMMENT_FIVE = '"+sbComment+"', ");
                }
                if (!SharedUtil.isEmptyString(sbFile)){
                    upSql.append(" FILE_ID_SIX = '"+sbFile+"', ");
                }
                upSql.append(" LAST_MODI_DT = now() where id = ?");
                Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                log.info("已更新：{}", exec);
            }
        }
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

    // 审核意见附件自动拼接
    private String autoFile(String newFile, String oldFile) {
        StringBuilder sbFile = new StringBuilder();
        if (!SharedUtil.isEmptyString(newFile)){
            if (!SharedUtil.isEmptyString(oldFile)){
                sbFile.append(oldFile).append(",").append(newFile);
            } else {
                sbFile.append(newFile);
            }
        }
        return sbFile.toString();
    }
}
