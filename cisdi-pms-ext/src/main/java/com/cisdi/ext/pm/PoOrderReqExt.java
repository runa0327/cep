package com.cisdi.ext.pm;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONParser;
import com.alibaba.fastjson.JSON;
import com.cisdi.ext.commons.HttpClient;
import com.cisdi.ext.model.view.file.FlFile;
import com.cisdi.ext.model.view.order.PoOrderReq;
import com.cisdi.ext.util.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

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
        //公司名称
        String companyId = JdbcMapUtil.getString(entityRecord.valueMap,"CUSTOMER_UNIT_ONE");
        String companyName = myJdbcTemplate.queryForList("select name from PM_PARTY where id = ?",companyId).get(0).get("name").toString();
        //用户id
        String userId = ExtJarHelper.loginInfo.get().userId;
        //流程id
        String csId = entityRecord.csCommId;
        //是否标准模板 0099799190825080669 = 是，0099799190825080670=否
        String isModel = JdbcMapUtil.getString(entityRecord.valueMap,"YES_NO_THREE");
        //获取文件id
        String fileId = "";
        if ("0099799190825080669".equals(isModel)){
            fileId = JdbcMapUtil.getString(entityRecord.valueMap,"ATT_FILE_GROUP_ID"); //合同文本
        } else {
            fileId = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_ONE"); //合同修订稿
        }
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
        String address = "C:\\Users\\11376\\Desktop\\demo.docx";
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
            newAddress = "C:\\Users\\11376\\Desktop\\demo.pdf";
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
                    //更新合同表合同总金额数
                    String sql3 = "update PO_ORDER_REQ set AMT_TWO = ? where id = ?";
                    myJdbcTemplate.update(sql3,amtShui,orderId);
                }
            }
        }
    }

    /**
     * 合同签订-word转pdf-发起时
     */
    public void wordToPdfStart(){
        String status = "start";
        wordToPdfNew(status);
    }

    /**
     * 合同签订-word转pdf-发起时
     */
    public void wordToPdfSecondStart(){
        String status = "secondStart";
        wordToPdfNew(status);
    }

    /**
     * 合同签订不同情况转pdf
     * @param status 状态码
     */
    private void wordToPdfNew(String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //用户id
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        //流程id
        String csId = entityRecord.csCommId;
        //流程实例id
        String procInstId = ExtJarHelper.procInstId.get();
        //是否标准模板 0099799190825080669 = 是，0099799190825080670=否
//        String isModel = JdbcMapUtil.getString(entityRecord.valueMap,"YES_NO_THREE");

        //查询接口地址
        String httpSql = "select HOST_ADDR from BASE_THIRD_INTERFACE where code = 'order_word_to_pdf' and SYS_TRUE = 1";
        List<Map<String,Object>> listUrl = myJdbcTemplate.queryForList(httpSql);

        //公司名称
        String companyId = JdbcMapUtil.getString(entityRecord.valueMap,"CUSTOMER_UNIT_ONE");
        String companyName = myJdbcTemplate.queryForList("select name from PM_PARTY where id = ?",companyId).get(0).get("name").toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!CollectionUtils.isEmpty(listUrl)){
                    String url = listUrl.get(0).get("HOST_ADDR").toString();
                    if (SharedUtil.isEmptyString(url)){
                        //写入日志提示表
                        String id = Crud.from("AD_REMIND_LOG").insertData();
                        Crud.from("AD_REMIND_LOG").where().eq("id",id).update().set("AD_ENT_ID","0099799190825103145")
                                .set("ENT_CODE","PO_ORDER_REQ").set("ENTITY_RECORD_ID",csId).set("REMIND_USER_ID","0099250247095871681")
                                .set("REMIND_METHOD","日志提醒").set("REMIND_TARGET","admin").set("REMIND_TIME",new Date())
                                .set("REMIND_TEXT","用户"+userName+"在合同签订上传的合同文本转化为pdf失败").exec();
                    } else {
                        PoOrderReq poOrderReq = getOrderModel(entityRecord,procInstId,userId,status,companyName);
                        String param = JSON.toJSONString(poOrderReq);
                        //调用接口
                        HttpClient.doPost(url,param,"UTF-8");
//                        HttpUtils.sendPost(url,param);
                    }

                }
            }
        }).start();
    }

    /**
     * 合同签订实体装数
     * @param entityRecord 流程所有数据
     * @param procInstId 流程实例id
     * @param userId 操作人id
     * @param status 状态，区分发起还是二次发起
     * @param companyName 公司名称
     * @return
     */
    private PoOrderReq getOrderModel(EntityRecord entityRecord,String procInstId,String userId,String status,String companyName) {
        PoOrderReq poOrderReq = new PoOrderReq();
        poOrderReq.setId(entityRecord.csCommId);
        poOrderReq.setProcessInstanceId(procInstId);
        poOrderReq.setCreateBy(userId);
        if (SharedUtil.isEmptyString(companyName)){
            companyName = "三亚崖州湾科技城开发建设有限公司";
        }
        poOrderReq.setCompanyName(companyName);

        //获取文件id
        String fileId = "";
        if ("start".equals(status)){ //发起时校验
            fileId = JdbcMapUtil.getString(entityRecord.valueMap,"ATT_FILE_GROUP_ID"); //合同文本
            poOrderReq.setIsModel("1");
        } else {
            fileId = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_ONE"); //合同修订稿
            poOrderReq.setIsModel("0");
        }
        poOrderReq.setFileId(fileId);
        return poOrderReq;
    }

}
