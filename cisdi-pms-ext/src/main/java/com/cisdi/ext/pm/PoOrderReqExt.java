package com.cisdi.ext.pm;

import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.ProFileUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 采购合同签订扩展
 */
@Slf4j
public class PoOrderReqExt {

    /**
     * 采购合同签订扩展-财务审批
     */
    public void checkFirst() {
        String status = "first";
        checkFirst(status);
    }

    /**
     * 采购合同签订扩展-法务审批
     */
    public void checkSecond() {
        String status = "second";
        checkFirst(status);
    }

    /**
     * 采购合同签订扩展-律师审批
     */
    public void orderLawyerCheck() {
        String status = "orderLawyerCheck";
        checkFirst(status);
    }

    /**
     * 合同签订扩展-法务财务审批
     */
    public void orderLegalFinanceCheck(){
        String status = "orderLegalFinanceCheck";
        checkFirst(status);
    }

    /**
     * 合同签订扩展-法务财务审批-拒绝
     */
    public void orderLegalFinanceReject(){
        String status = "orderLegalFinanceReject";
        checkFirst(status);
    }

    /**
     * 采购合同签订扩展 审批先后顺序 第四次审批
     */
    public void checkFourth() {
        String status = "fourth";
        checkFirst(status);
    }

    /**
     * 采购合同签订扩展 审批先后顺序 第五次审批
     */
    public void checkFifth() {
        String status = "fifth";
        checkFirst(status);
    }

    private void checkFirst(String status) {
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
//        String sql = "select GROUP_CONCAT(tk.USER_COMMENT order by tk.LAST_MODI_DT desc SEPARATOR '；') as user_comment, " +
//                "GROUP_CONCAT(tk.USER_ATTACHMENT order by tk.LAST_MODI_DT desc SEPARATOR',') as USER_ATTACHMENT " +
//                "from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id join ad_user u on tk.ad_user_id=u.id";
        // 审批意见
        String sql = "select tk.USER_COMMENT,tk.USER_ATTACHMENT " +
                "from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id join ad_user u on tk.ad_user_id=u.id" +
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
        if ("orderLawyerCheck".equals(status)){ //律师审核
            //流程中的审批意见
            String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"TEXT_REMARK_TWO");
            String processFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_SIX");
            //判断是否是当轮拒绝回来的、撤销回来的
            String sql2 = "select count(*) as num from wf_task where WF_NODE_INSTANCE_ID = ? and IS_CLOSED = 1 and AD_USER_ID != ?";
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,nodeId,userId);
            if (!CollectionUtils.isEmpty(list2)){
                String num = JdbcMapUtil.getString(list2.get(0),"num");
                if (SharedUtil.isEmptyString(num) || "0".equals(num)){
                    Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                            .set("TEXT_REMARK_TWO", null).set("FILE_ID_SIX",null).exec();
                    processComment = "";
                    processFile = "";
                }
            } else {
                Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("TEXT_REMARK_TWO", null).set("FILE_ID_SIX",null).exec();
                processComment = "";
                processFile = "";
            }
            sbComment = autoComment(comment,processComment,userName);
            sbFile = autoFile(file,processFile);
            if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                upSql.append("update PO_ORDER_REQ set ");
                if (!SharedUtil.isEmptyString(sbComment)){
                    upSql.append(" TEXT_REMARK_TWO = '"+sbComment+"', ");
                }
                if (!SharedUtil.isEmptyString(sbFile)){
                    upSql.append(" FILE_ID_SIX = '"+sbFile+"', ");
                }
                upSql.append(" LAST_MODI_DT = now() where id = ?");
                Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                log.info("已更新：{}", exec);
            }

//            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
//                    .set("TEXT_REMARK_TWO", sbComment).set("FILE_ID_SIX",sbFile).exec();

        }  else if ("orderLegalFinanceCheck".equals(status)) { //法务财务审批
            //流程中的审批意见
            String processLegalComment = JdbcMapUtil.getString(entityRecord.valueMap,"TEXT_REMARK_THREE"); //法务意见
            String processLegalFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_THREE"); //法务修订稿
            String processFinanceComment = JdbcMapUtil.getString(entityRecord.valueMap,"TEXT_REMARK_FOUR"); //财务意见
            String processFinanceFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_TWO"); //财务修订稿
            //查询该人员角色信息
            String sql1 = "select b.id,b.name from ad_role_user a left join ad_role b on a.AD_ROLE_ID = b.id where a.AD_USER_ID = ? and b.id in ('0100070673610711083','0099902212142039415')";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,userId);
            if (!CollectionUtils.isEmpty(list1)){
                //判断是否是当轮拒绝回来的、撤销回来的
                String sql2 = "select count(*) as num from wf_task where WF_NODE_INSTANCE_ID = ? and IS_CLOSED = 1 and AD_USER_ID != ?";
                List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,nodeId,userId);
                if (!CollectionUtils.isEmpty(list2)){
                    String num = JdbcMapUtil.getString(list2.get(0),"num");
                    if (SharedUtil.isEmptyString(num) || "0".equals(num)){
                        Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                                .set("TEXT_REMARK_THREE", null).set("FILE_ID_THREE",null).set("TEXT_REMARK_FOUR",null)
                                .set("FILE_ID_TWO",null).exec();
                        processLegalComment = "";
                        processLegalFile = "";
                        processFinanceComment = "";
                        processFinanceFile = "";
                    }
                } else {
                    Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                            .set("TEXT_REMARK_THREE", null).set("FILE_ID_THREE",null).set("TEXT_REMARK_FOUR",null)
                            .set("FILE_ID_TWO",null).exec();
                    processLegalComment = "";
                    processLegalFile = "";
                    processFinanceComment = "";
                    processFinanceFile = "";
                }
                // 0099902212142039415 = 法务部门;0100070673610711083=财务部
                String id = JdbcMapUtil.getString(list1.get(0),"id");

                if ("0099902212142039415".equals(id)){ //法务
                    sbComment = autoComment(comment,processLegalComment,userName);
                    sbFile = autoFile(file,processLegalFile);
                    if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                        upSql.append("update PO_ORDER_REQ set ");
                        if (!SharedUtil.isEmptyString(sbComment)){
                            upSql.append(" TEXT_REMARK_THREE = '"+sbComment+"', ");
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
                    sbComment = autoComment(comment,processFinanceComment,userName);
                    sbFile = autoFile(file,processFinanceFile);
                    if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                        upSql.append("update PO_ORDER_REQ set ");
                        if (!SharedUtil.isEmptyString(sbComment)){
                            upSql.append(" TEXT_REMARK_FOUR = '"+sbComment+"', ");
                        }
                        if (!SharedUtil.isEmptyString(sbFile)){
                            upSql.append(" FILE_ID_TWO = '"+sbFile+"', ");
                        }
                        upSql.append(" LAST_MODI_DT = now() where id = ?");
                        Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                        log.info("已更新：{}", exec);
                    }
                }
            }
        } else if ("orderLegalFinanceReject".equals(status)){
            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("TEXT_REMARK_THREE", null).set("FILE_ID_THREE",null).set("TEXT_REMARK_FOUR",null)
                    .set("FILE_ID_TWO",null).exec();
        } else if ("first".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_ONE", userId).set("APPROVAL_DATE_ONE", now).set("APPROVAL_MESSAGE", comment).set("FILE_ID_TWO",file).exec();
            log.info("已更新：{}", exec);
        } else if ("second".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_TWO", userId).set("APPROVAL_DATE_TWO", now).set("EARLY_COMMENT", comment).set("FILE_ID_THREE",file).exec();
            log.info("已更新：{}", exec);
        } else if ("third".equals(status)) {
            if (SharedUtil.isEmptyString(file)){
                throw new BaseException("审核附件不能为空");
            }
            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_THREE", userId).set("APPROVAL_DATE_THREE", now).set("DESIGN_COMMENT", comment).set("FILE_ID_SIX",file).exec();
            log.info("已更新：{}", exec);
        } else if ("fourth".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_FOUR", userId).set("APPROVAL_DATE_FOUR", now).set("APPROVAL_COMMENT_ONE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("fifth".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_FIVE", userId).set("APPROVAL_DATE_FIVE", now).set("USER_COMMENT", comment).exec();
            log.info("已更新：{}", exec);
        }
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

    /**
     * 流程发起之时，相关数据校验
     */
    public void checkData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        // 查询明细表合同总金额
        String sql = "select * from PM_ORDER_COST_DETAIL where CONTRACT_ID = ?";
        List<Map<String, Object>> list1 = myJdbcTemplate.queryForList(sql, entityRecord.csCommId);
        if (CollectionUtils.isEmpty(list1)) {
            throw new BaseException("费用明细不能为空！");
        }
        for (Map<String, Object> tmp : list1) {
            String detailId = JdbcMapUtil.getString(tmp,"id");
            //含税金额
            BigDecimal shuiAmt = new BigDecimal(JdbcMapUtil.getString(tmp,"AMT_ONE"));
            //税率
            BigDecimal shuiLv = new BigDecimal(JdbcMapUtil.getString(tmp,"AMT_THREE")).divide(new BigDecimal(100));
            //含税金额
            BigDecimal noShuiAmt = shuiAmt.divide(shuiLv.add(new BigDecimal(1)),2, RoundingMode.HALF_UP);
            Integer integer = myJdbcTemplate.update("update PM_ORDER_COST_DETAIL set AMT_TWO=? where id = ?",noShuiAmt,detailId);
            tmp.put("AMT_TWO",noShuiAmt);
        }
//        BigDecimal account = getSumAmt(list1);
        //含税总金额
        BigDecimal amtShui = getSumAmtBy(list1,"AMT_ONE");
        //不含税总金额
        BigDecimal amtNoShui = getSumAmtBy(list1,"AMT_TWO");
        //税率
        BigDecimal shuiLv = getShuiLv(list1,"AMT_THREE");
        //更新合同表合同总金额数
        String sql2 = "update PO_ORDER_REQ set AMT_TWO = ?,AMT_THREE=?,AMT_FOUR=? where id = ?";
        myJdbcTemplate.update(sql2,amtShui,amtNoShui,shuiLv,entityRecord.csCommId);

        //是否涉及保函
        String baoHan = JdbcMapUtil.getString(entityRecord.valueMap,"IS_REFER_GUARANTEE");
        //保函类型
        String baoHanType = JdbcMapUtil.getString(entityRecord.valueMap,"GUARANTEE_LETTER_TYPE_IDS");
        if ("true".equals(baoHan)){
            if (SharedUtil.isEmptyString(baoHanType)){
                throw new BaseException("保函类型不能为空！");
            }
        }
        if ("false".equals(baoHan)){
            if (!SharedUtil.isEmptyString(baoHanType)){
                throw new BaseException("未涉及保函，请勿勾选保函类型！");
            }
        }

        //是否填写联系人
        List<Map<String, Object>> contactList = myJdbcTemplate.queryForList("SELECT OPPO_SITE_LINK_MAN, OPPO_SITE_CONTACT " +
                "FROM CONTRACT_SIGNING_CONTACT where PARENT_ID = ?", entityRecord.csCommId);
        if (CollectionUtils.isEmpty(contactList)){
            throw new BaseException("联系人不能为空！");
        }
    }

    //获取税率
    private BigDecimal getShuiLv(List<Map<String, Object>> list, String str) {
        BigDecimal sum = new BigDecimal(0);
        tp: for (Map<String, Object> tmp : list) {
            String value =JdbcMapUtil.getString(tmp,str);
            if (!SharedUtil.isEmptyString(value)){
                sum = new BigDecimal(value);
                break tp;
            }
        }
        return sum;
    }

    // 根据字段求和
    private BigDecimal getSumAmtBy(List<Map<String, Object>> list, String str) {
        BigDecimal sum = new BigDecimal(0);
        for (Map<String, Object> tmp : list) {
            String value =JdbcMapUtil.getString(tmp,str);
            if (SharedUtil.isEmptyString(value)){
                value = "0";
            }
            sum = sum.add(new BigDecimal(value));
        }
        return sum;
    }

    // 汇总求和
    private BigDecimal getSumAmt(List<Map<String, Object>> list) {
        BigDecimal sum = new BigDecimal(0);
        for (Map<String, Object> tmp : list) {
            String value = tmp.get("AMT").toString();
            sum = sum.add(new BigDecimal(value));
        }
        return sum;
    }

    /**
     * 合同最终版 word转pdf 不经过法务
     */
    public void wordToPdf(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //用户姓名
        String waterMark = "工程项目协调系统-" + ExtJarHelper.loginInfo.get().userName;
        //用户id
        String userId = ExtJarHelper.loginInfo.get().userId;
        //流程id
        String csId = entityRecord.csCommId;
        //获取文件id
        String fileId = JdbcMapUtil.getString(entityRecord.valueMap,"ATT_FILE_GROUP_ID");
        if (SharedUtil.isEmptyString(fileId)){
            throw new BaseException("合同文本不能为空，请上传合同文件");
        }
        String sql1 = "select PHYSICAL_LOCATION,EXT,NAME from fl_file where id = ?";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,fileId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("没有找到文件！");
        }

        //模拟上传文件写入数据
        String newId = Crud.from("fl_file").insertData();

        //文件类型
        String fileType = list1.get(0).get("EXT").toString();
        //输出地址
        String newAddress = "";
        //文件存放地址
//        String address = list1.get(0).get("PHYSICAL_LOCATION").toString();
        String address = "C:\\Users\\Administrator\\Desktop\\Demo\\demo.doc";
        if (!"doc".equals(fileType) && !"docx".equals(fileType)){
            throw new BaseException("合同附件格式为文档");
        }
//        String addressFront = address.substring(0,address.lastIndexOf("/"));
        System.out.println("address:"+address);
//        newAddress = addressFront+newId+".pdf";
        //文件大小
        float fileSize = 0l;
        //显示名称
        String showName = "";
        try {
            newAddress = "C:\\Users\\Administrator\\Desktop\\Demo\\demo.pdf";
            Map map = ProFileUtils.testExt(address,newAddress);
            fileSize = Float.valueOf(map.get("size").toString());
            showName = map.get("name").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //文件名称
        String fileName = JdbcMapUtil.getString(entityRecord.valueMap,"NAME");

        //当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());

        //将生成的pdf写入该流程
//        Crud.from("PO_ORDER_REQ").where().eq("id",csId).update().set("FIRST_INSPECTION_REPORT_FILE",newAddress).exec();
        Crud.from("TEST_CLASS").where().eq("id",csId).update().set("FILE_ID_ONE",newId).exec();

        Crud.from("fl_file").where().eq("id",newId).update()
                .set("CODE",newId).set("NAME",fileName).set("VER","1").set("FL_PATH_ID","0099250247095872690").set("EXT","pdf")
                .set("STATUS","AP").set("CRT_DT",now).set("CRT_USER_ID",userId).set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",userId)
                .set("SIZE_KB",fileSize).set("TS",now).set("UPLOAD_DTTM",now).set("PHYSICAL_LOCATION",newAddress).set("DSP_NAME",showName)
                .set("DSP_SIZE",fileSize).exec();
    }

    // 合同签订原本费用明细更新到新税率上
    public void updatePayData(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //查询所有合同主表数据
        String sql1 = "select id from PO_ORDER_REQ order by TS asc";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1);
        if (!CollectionUtils.isEmpty(list1)){
            for (Map<String, Object> tmp1 : list1) {
                String orderId = JdbcMapUtil.getString(tmp1,"id");
                //根据合同id去费用明细表查询数据
                String sql2 = "select * from PM_ORDER_COST_DETAIL where CONTRACT_ID = ? and status = 'ap'";
                List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,orderId);
                if (!CollectionUtils.isEmpty(list2)){
                    for (Map<String, Object> tmp : list2) {
                        int update = myJdbcTemplate.update("update pm_order_cost_detail set AMT_ONE=AMT WHERE id = ?",JdbcMapUtil.getString(tmp,"id"));
                    }
                    //含税总金额
                    BigDecimal amtShui = getSumAmtBy(list2,"AMT_ONE");
                    //不含税总金额
//                BigDecimal amtNoShui = getSumAmtBy(list2,"AMT_TWO");
                    //税率
//                BigDecimal shuiLv = getShuiLv(list2,"AMT_THREE");
                    //更新合同表合同总金额数
                    String sql3 = "update PO_ORDER_REQ set AMT_TWO = ? where id = ?";
                    myJdbcTemplate.update(sql3,amtShui,orderId);
                }
            }
        }
    }
}
