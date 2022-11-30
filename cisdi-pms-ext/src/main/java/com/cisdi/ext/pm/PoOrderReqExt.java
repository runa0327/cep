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
     * 采购合同签订扩展-法律审批
     */
    public void checkThird() {
        String status = "third";
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
        // 当前登录人
        String userId = ExtJarHelper.loginInfo.get().userName;

        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        // 审批意见
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select tk.USER_ATTACHMENT,u.id u_id,u.code u_code,u.name u_name,tk.user_comment from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id join ad_user u on tk.ad_user_id=u.id", procInstId);
        String comment = "";
        String file = "";
        if (!CollectionUtils.isEmpty(list)) {
            comment = list.get(0).get("user_comment") == null ? null : list.get(0).get("user_comment").toString();
            file = list.get(0).get("USER_ATTACHMENT") == null ? null : list.get(0).get("USER_ATTACHMENT").toString();
        }
        if ("first".equals(status)) {
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
        try {
            newAddress = "C:\\Users\\Administrator\\Desktop\\Demo\\demo.pdf";
            fileSize = ProFileUtils.testExt(address,newAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //文件名称
        String fileName = JdbcMapUtil.getString(entityRecord.valueMap,"NAME");
        //显示名称
        String showName = "";
        //当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());

        //将生成的pdf写入该流程
//        Crud.from("PO_ORDER_REQ").where().eq("id",csId).update().set("FIRST_INSPECTION_REPORT_FILE",newAddress).exec();
        Crud.from("TEST_CLASS").where().eq("id",csId).update().set("FILE_ID_ONE",newId).exec();

        Crud.from("fl_file").where().eq("id",newId).update()
                .set("CODE",newId).set("NAME",fileName).set("VER","1").set("FL_PATH_ID","99250247095872690").set("EXT","pdf")
                .set("STATUS","AP").set("CRT_DT",now).set("CRT_USER_ID",userId).set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",userId)
                .set("SIZE_KB",fileSize).set("TS",now).set("UPLOAD_DTTM",now).set("PHYSICAL_LOCATION",newAddress).set("DSP_NAME",showName+"K")
                .set("DSP_SIZE",fileSize).exec();
    }
}
