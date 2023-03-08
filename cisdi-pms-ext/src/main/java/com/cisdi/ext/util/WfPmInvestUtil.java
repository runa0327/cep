package com.cisdi.ext.util;

import com.cisdi.ext.base.PmPrjExt;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class WfPmInvestUtil {

    public static void calculateData(String csCommId, String entCode, String pmPrjId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String tableName = "";
        String code = "";
        switch (entCode) {
            case "PM_PRJ_REQ":
                tableName = "PM_PRJ_REQ";
                code = "invest0";
                break;
            case "PM_PRJ_INVEST1":
                tableName = "PM_PRJ_INVEST1";
                code = "invest1";
                break;
            case "PM_PRJ_INVEST2":
                tableName = "PM_PRJ_INVEST2";
                code = "invest2";
                break;
            case "PM_PRJ_INVEST3":
                tableName = "PM_PRJ_INVEST3";
                code = "invest3";
                break;
            default:
                throw new BaseException("数据错误！");
        }
        if (StringUtils.isEmpty(tableName)) {
            return;
        }
        // PM_PRJ_INVEST1  查询可研估算
        Map<String, Object> dataMap = myJdbcTemplate.queryForMap("select * from " + tableName + "  where ID= ?", csCommId);

        // 查询项目投资测算明细
        List<Map<String, Object>> investEstList = myJdbcTemplate.queryForList("select t.*,pet.code as PM_EXP_TYPE from PM_INVEST_EST_DTL t\n" +
                "left join PM_INVEST_EST a on t.PM_INVEST_EST_ID = a.id \n" +
                "left join PM_EXP_TYPE pet on pet.id = t.PM_EXP_TYPE_ID\n" +
                "left join gr_set_value gsv on gsv.id = a.INVEST_EST_TYPE_ID\n" +
                "left join gr_set gr on gr.id = gsv.GR_SET_ID and gr.`CODE`='invest_est_type'\n" +
                "where a.PM_PRJ_ID=? and gsv.`code`=?", pmPrjId, code);

        if (investEstList.size() > 0) {
            investEstList.forEach(item -> {
                String pmExpType = String.valueOf(item.get("PM_EXP_TYPE"));
                myJdbcTemplate.update("update PM_INVEST_EST_DTL set AMT=?,LAST_MODI_DT=? where id=?", dataMap.get(pmExpType), new Date(), item.get("ID"));
            });
        } else {
            // 新增项目投资测算
            // 查询项目测算类型
            Map<String, Object> investEstType = myJdbcTemplate.queryForMap("select gsv.* from  gr_set_value gsv left join gr_set gr on gr.id = gsv.GR_SET_ID where  gr.`CODE`='invest_est_type' and gsv.code=?", code);
            String investEstTypeId = String.valueOf(investEstType.get("ID"));

            String newInvestEtsId = Crud.from("PM_INVEST_EST").insertData();

            // 新增测算明细

            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,SEQ_NO,ifnull(PM_EXP_TYPE_PID,0) as PM_EXP_TYPE_PID,CALC_BY_PAYMENT,CALC_BY_PROGRESS from PM_EXP_TYPE");

            List<pmInvestEstDtl> dtlList = new ArrayList<>();
            // 通过构建树新增数据
            list.stream().filter(p -> Objects.equals("0", String.valueOf(p.get("PM_EXP_TYPE_PID")))).peek(m -> {
                String id = Crud.from("PM_INVEST_EST_DTL").insertData();
                pmInvestEstDtl dtl = new pmInvestEstDtl();
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
            Optional<pmInvestEstDtl> optional = dtlList.stream().filter(p -> Objects.equals("PRJ_TOTAL_INVEST", String.valueOf(p.code))).findAny();
            if (optional.isPresent()) {
                obj = optional.get().amt;
            }
            Crud.from("PM_INVEST_EST").where().eq("ID", newInvestEtsId).update().set("IS_TEMPLATE", 0).set("PM_PRJ_ID", pmPrjId).set("INVEST_EST_TYPE_ID", investEstTypeId).set("PRJ_TOTAL_INVEST", obj).exec();

        }
    }

    private static List<Map<String, Object>> getChildren(Map<String, Object> root, List<Map<String, Object>> allData, Map<String, Object> dataMap, String newInvestEtsId, List<pmInvestEstDtl> dtlList, String pId) {
        return allData.stream().filter(p -> Objects.equals(String.valueOf(p.get("PM_EXP_TYPE_PID")), String.valueOf(root.get("ID")))).peek(m -> {
            String id = Crud.from("PM_INVEST_EST_DTL").insertData();
            pmInvestEstDtl dtl = new pmInvestEstDtl();
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

    /**
     * 回滚项目投资测算
     * @param csCommId 流程记录id
     * @param entCode 表名
     * @param projectId 项目id
     */
    public static int backPrjData(String csCommId, String entCode, String projectId,MyJdbcTemplate myJdbcTemplate) {
        int sum = 0;
        // 查询项目投资测算明细
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select t.*,pet.code as PM_EXP_TYPE from PM_INVEST_EST_DTL t " +
                "left join PM_INVEST_EST a on t.PM_INVEST_EST_ID = a.id " +
                "left join PM_EXP_TYPE pet on pet.id = t.PM_EXP_TYPE_ID " +
                "left join gr_set_value gsv on gsv.id = a.INVEST_EST_TYPE_ID " +
                "left join gr_set gr on gr.id = gsv.GR_SET_ID and gr.`CODE`='invest_est_type' " +
                "where a.PM_PRJ_ID=? and gsv.`code`= 'invest0'", projectId);
        if (!CollectionUtils.isEmpty(list)){
            for (Map<String, Object> tmp : list) {
                String id = JdbcMapUtil.getString(list.get(0),"id");
                myJdbcTemplate.update("delete from PM_INVEST_EST_DTL where id = ?",id);
                sum++;
            }
        }
        return sum;
    }

    /**
     * 更新pm_prj的投资数据
     */
    public static void updatePrjInvest(EntityRecord entityRecord,String entCode){
        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> valueMap = entityRecord.valueMap;

        //查询新投资优先级 这里查询的name，和表明entCode对应
        List<Map<String, Object>> priorityList = jdbcTemplate.queryForList("select gv.id,gv.name,gv.code from gr_set_value gv left join gr_set se on se.id " +
                "= gv.GR_SET_ID where se.code = 'invest_priority'");
        Optional<Map<String,Object>> newPriorityOp = priorityList.stream()
                .filter(priorityMap -> priorityMap.get("name").toString().equals(entCode))
//                .map(priorityMap -> priorityMap.get("code").toString())
                .findAny();
        if (!newPriorityOp.isPresent()){
            throw new BaseException("entCode没有在字典‘投资测算优先级’中");
        }
        String newPriority = newPriorityOp.get().get("code").toString();
        String newPriorityId = newPriorityOp.get().get("id").toString();


        //获取pm_prj现有记录的优先级
        String prjIds = PmPrjExt.getProjectIdByProcess(valueMap, jdbcTemplate);
        List<Map<String, Object>> prjPriorityList = jdbcTemplate.queryForList("select pp.id prjId,gv.code priority from pm_prj pp left join gr_set_value gv on" +
                " gv.id = pp.INVEST_PRIORITY");
        String[] prjIdArray = prjIds.split(",");
        for (String prjId : prjIdArray) {
            List<String> oldPriorityList = prjPriorityList.stream()
                    .filter(prjPriorityMap -> String.valueOf(prjPriorityMap.get("prjId")).equals(prjId))
                    .map(prjPriorityMap -> {
                        if (prjPriorityMap.get("priority") == null){
                            return null;
                        }
                        return prjPriorityMap.get("priority").toString();
                    })
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(oldPriorityList)){
                throw new BaseException("没有找到项目id" + prjId);
            }
            String oldPriority = oldPriorityList.get(0);
            if (Strings.isNullOrEmpty(oldPriority) || oldPriority.compareTo(newPriority) < 0){
                //更新pm_prj金额数据
                Crud.from("pm_prj").where().eq("ID",prjId).update()
                        .set("ESTIMATED_TOTAL_INVEST",valueMap.get("PRJ_TOTAL_INVEST"))
                        .set("PROJECT_AMT",valueMap.get("PROJECT_AMT"))
                        .set("CONSTRUCT_PRJ_AMT",valueMap.get("CONSTRUCT_AMT"))
                        .set("EQUIP_BUY_AMT",valueMap.get("EQUIP_AMT"))
                        .set("EQUIPMENT_COST",valueMap.get("EQUIPMENT_COST"))
                        .set("PROJECT_OTHER_AMT",valueMap.get("PROJECT_OTHER_AMT"))
                        .set("LAND_BUY_AMT",valueMap.get("LAND_AMT"))
                        .set("PREPARE_AMT",valueMap.get("PREPARE_AMT"))
                        .set("INVEST_PRIORITY",newPriorityId)
                        .exec();
            }
        }

    }

    public static class pmInvestEstDtl {
        public String id;
        public String pid;
        public String amt;
        public String InvestEstId;
        public String expTypeId;
        public String code;
        public String seq;
    }

    public static void main(String[] args) {
//        String s = String.valueOf(null);
//        System.out.println(s);
//        System.out.println(Strings.isNullOrEmpty(null));
//        System.out.println("1".compareTo("null"));
        ArrayList<String> strings = new ArrayList<>();
//        strings.add(null);
        System.out.println(CollectionUtils.isEmpty(strings));
    }
}
