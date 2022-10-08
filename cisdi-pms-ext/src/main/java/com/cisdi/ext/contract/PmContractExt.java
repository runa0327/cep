package com.cisdi.ext.contract;

import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmContractExt
 * @package com.cisdi.ext.contract
 * @description 合同扩展
 * @date 2022/9/15
 */
public class PmContractExt {

    public void createContract() {
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        try {
            Map<String, Object> contractDataMap = myJdbcTemplate.queryForMap("select * from PO_ORDER_REQ where ID=?", csCommId);
            String id = Crud.from("PO_ORDER").insertData();
            insertContract(id, contractDataMap);
            List<Map<String, Object>> detailList = myJdbcTemplate.queryForList("select * from PM_ORDER_COST_DETAIL where CONTRACT_ID=?", csCommId);
            insertContractDet(id, detailList);
            //查询联系人明细
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList("select OPPO_SITE_LINK_MAN,OPPO_SITE_CONTACT from CONTRACT_SIGNING_CONTACT where PARENT_ID = ?",csCommId);
            if (!CollectionUtils.isEmpty(list2)){
                insertContacts(id,list2);
            }
        } catch (Exception e) {
            throw new BaseException("查询合同申请数据异常！", e);
        }
    }


    /**
     * 新增合同信息
     *
     * @param id
     * @param data
     */
    private void insertContract(String id, Map<String, Object> data) {
        Crud.from("PO_ORDER").where().eq("ID", id).update()
                .set("NAME", JdbcMapUtil.getString(data, "CONTRACT_NAME"))
                .set("REMARK", JdbcMapUtil.getString(data, "REMARK"))
                .set("CONTRACT_AMOUNT", JdbcMapUtil.getString(data, "CONTRACT_PRICE"))
                .set("AGENT", JdbcMapUtil.getString(data, ""))
                .set("AGENT_PHONE", JdbcMapUtil.getString(data, ""))
                .set("OPPO_SITE_CONTACT", JdbcMapUtil.getString(data, "OPPO_SITE_CONTACT"))
                .set("FILE_ATTACHMENT_URL", JdbcMapUtil.getString(data, "ATT_FILE_GROUP_ID"))
                .set("OPPO_SITE", JdbcMapUtil.getString(data, ""))
                .set("OPPO_SITE_LINK_MAN", JdbcMapUtil.getString(data, "OPPO_SITE_LINK_MAN"))
                .set("PM_PRJ_ID", JdbcMapUtil.getString(data, "PM_PRJ_ID"))
                .set("CONTRACT_APP_ID",JdbcMapUtil.getString(data,"ID"))
                .set("ORDER_PROCESS_TYPE","合同签订")
                .exec();
    }

    /**
     * 新增合同明细信息
     *
     * @param id
     * @param detailList
     */
    private void insertContractDet(String id, List<Map<String, Object>> detailList) {
        detailList.forEach(item -> {
            String did = Crud.from("PO_ORDER_DTL").insertData();
            Crud.from("PO_ORDER_DTL").where().eq("ID", did).update()
                    .set("NAME", JdbcMapUtil.getString(item, "NAME"))
                    .set("REMARK", JdbcMapUtil.getString(item, "REMARK"))
                    .set("COST_TYPE_TREE_ID", JdbcMapUtil.getString(item, "COST_TYPE_TREE_ID"))
                    .set("PAY_TYPE", JdbcMapUtil.getString(item, ""))
                    .set("FILE_ATTACHMENT_URL", JdbcMapUtil.getString(item, ""))
                    .set("TOTAL_AMT", JdbcMapUtil.getString(item, "TOTAL_AMT"))
                    .set("AMT", JdbcMapUtil.getString(item, "AMT"))
                    .set("CONTRACT_ID", JdbcMapUtil.getString(item, "CONTRACT_ID"))
                    .set("WORK_CONTENT", JdbcMapUtil.getString(item, ""))
                    .set("FEE_DETAIL", JdbcMapUtil.getString(item, "FEE_DETAIL"))
                    .set("PO_ORDER_ID", id)
                    .exec();
        });
    }

    /** 相对方联系人插入 **/
    private void insertContacts(String id, List<Map<String, Object>> list2) {
        String now = DateTimeUtil.dttmToString(new Date());
        list2.forEach(item -> {
            String did = Crud.from("PO_ORDER_CONTACTS").insertData();
            Crud.from("PO_ORDER_CONTACTS").where().eq("ID", did).update()
                    .set("OPPO_SITE_LINK_MAN", JdbcMapUtil.getString(item, "OPPO_SITE_LINK_MAN"))
                    .set("OPPO_SITE_CONTACT", JdbcMapUtil.getString(item, "OPPO_SITE_CONTACT"))
                    .set("PARENT_ID", id)
                    .set("CRT_DT",now)
                    .exec();
        });
    }
}
