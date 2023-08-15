package com.cisdi.ext.history;

import com.cisdi.ext.util.WfPmInvestUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ReversalDataExt
 * @package com.cisdi.ext.history
 * @description
 * @date 2023/8/14
 */
public class ReversalDataExt {


    private static final String invest0 = "0099799190825099302";//立项匡算
    private static final String invest1 = "0099799190825099305";//可研估算
    private static final String invest2 = "0099799190825099306";//初设概算
    private static final String invest3 = "0099799190825099307";//预算财评
    private static final String invest4 = "1640608706054045696";//项目结算

    public static final Map<String, String> Obj = new HashMap<>();

    static {
        Obj.put("PM_PRJ_REQ", invest0);
        Obj.put("PM_PRJ_INVEST1", invest1);
        Obj.put("PM_PRJ_INVEST2", invest2);
        Obj.put("PM_PRJ_INVEST3", invest3);
        Obj.put("PM_PRJ_SETTLE_ACCOUNTS", invest4);
    }

    /**
     * 台账刷新项目信息
     */
    public void reversalData() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        switch (entCode) {
            case "PM_PRJ_REQ":
            case "PM_PRJ_INVEST1":
            case "PM_PRJ_INVEST2":
            case "PM_PRJ_INVEST3":
            case "PM_PRJ_SETTLE_ACCOUNTS":
                refreshInvest(entCode);
                break;
        }
    }


    /**
     * 立项台账(PM_PRJ_REQ)/可研估算(PM_PRJ_INVEST1)/初设概算(PM_PRJ_INVEST2)/预算财评(PM_PRJ_INVEST3)/项目结算(PM_PRJ_SETTLE_ACCOUNTS)
     */
    private void refreshInvest(String tableName) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from " + tableName + " where `STATUS`='ap'");
        list.forEach(item -> {
            String projectId = JdbcMapUtil.getString(item, "PM_PRJ_ID");
            String typeId = Obj.get(tableName);
            insertInvestData(typeId, item, projectId);
        });
    }

    /**
     * 生成数据
     *
     * @param typeId
     * @param dataMap
     * @param projectId
     */
    private void insertInvestData(String typeId, Map<String, Object> dataMap, String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //清除原来的数据
        myJdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0;delete from PM_INVEST_EST_DTL where PM_INVEST_EST_ID in (select id from PM_INVEST_EST where INVEST_EST_TYPE_ID =? and PM_PRJ_ID=?);SET FOREIGN_KEY_CHECKS = 1;", typeId, projectId);
        myJdbcTemplate.update("delete from PM_INVEST_EST where INVEST_EST_TYPE_ID =? and PM_PRJ_ID=?", typeId, projectId);

        String newInvestEtsId = Crud.from("PM_INVEST_EST").insertData();
        // 新增测算明细
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,SEQ_NO,ifnull(PM_EXP_TYPE_PID,0) as PM_EXP_TYPE_PID,CALC_BY_PAYMENT,CALC_BY_PROGRESS from PM_EXP_TYPE");

        List<WfPmInvestUtil.pmInvestEstDtl> dtlList = new ArrayList<>();
        // 通过构建树新增数据
        list.stream().filter(p -> Objects.equals("0", String.valueOf(p.get("PM_EXP_TYPE_PID")))).peek(m -> {
            String id = Crud.from("PM_INVEST_EST_DTL").insertData();
            WfPmInvestUtil.pmInvestEstDtl dtl = new WfPmInvestUtil.pmInvestEstDtl();
            dtl.id = id;
            dtl.pid = null;
            dtl.amt = String.valueOf(dataMap.get(String.valueOf(m.get("CODE"))));
            dtl.InvestEstId = newInvestEtsId;
            dtl.expTypeId = String.valueOf(m.get("ID"));
            dtl.code = String.valueOf(m.get("CODE"));
            dtl.seq = String.valueOf(m.get("SEQ_NO"));
            dtlList.add(dtl);
            getChildren(m, list, dataMap, newInvestEtsId, dtlList, id);
        }).collect(Collectors.toList());

        dtlList.forEach(item -> {
            Crud.from("PM_INVEST_EST_DTL").where().eq("ID", item.id).update().set("AMT", Objects.equals("null", item.amt) ? "0" : item.amt).set("PM_INVEST_EST_ID", item.InvestEstId)
                    .set("PM_EXP_TYPE_ID", item.expTypeId).set("PM_INVEST_EST_DTL_PID", item.pid).set("SEQ_NO", item.seq).exec();
        });
        Object obj = 0;
        Optional<WfPmInvestUtil.pmInvestEstDtl> optional = dtlList.stream().filter(p -> Objects.equals("PRJ_TOTAL_INVEST", String.valueOf(p.code))).findAny();
        if (optional.isPresent()) {
            obj = optional.get().amt;
        }
        Crud.from("PM_INVEST_EST").where().eq("ID", newInvestEtsId).update().set("IS_TEMPLATE", 0).set("PM_PRJ_ID", projectId).set("INVEST_EST_TYPE_ID", typeId).set("PRJ_TOTAL_INVEST", obj).exec();
    }

    private static List<Map<String, Object>> getChildren(Map<String, Object> root, List<Map<String, Object>> allData, Map<String, Object> dataMap, String newInvestEtsId, List<WfPmInvestUtil.pmInvestEstDtl> dtlList, String pId) {
        return allData.stream().filter(p -> Objects.equals(String.valueOf(p.get("PM_EXP_TYPE_PID")), String.valueOf(root.get("ID")))).peek(m -> {
            String id = Crud.from("PM_INVEST_EST_DTL").insertData();
            WfPmInvestUtil.pmInvestEstDtl dtl = new WfPmInvestUtil.pmInvestEstDtl();
            dtl.id = id;
            dtl.pid = pId;
            dtl.amt = String.valueOf(dataMap.get(String.valueOf(m.get("CODE"))));
            dtl.InvestEstId = newInvestEtsId;
            dtl.expTypeId = String.valueOf(m.get("ID"));
            dtl.code = String.valueOf(m.get("CODE"));
            dtl.seq = String.valueOf(m.get("SEQ_NO"));
            dtlList.add(dtl);
            getChildren(m, allData, dataMap, newInvestEtsId, dtlList, id);
        }).collect(Collectors.toList());
    }

}
