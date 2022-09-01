package com.cisdi.data.transfer;

import com.cisdi.data.enums.CostTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProInvestContrller
 * @package com.cisdi.data.transfer
 * @description
 * @date 2022/8/31
 */
@RestController
@RequestMapping("invest")
public class ProInvestController {

    @Autowired
    @Qualifier("cpmsJdbcTemplate")
    JdbcTemplate cpmsJdbcTemplate;

    @Autowired
    @Qualifier("testJdbcTemplate")
    JdbcTemplate testJdbcTemplate;

    /**
     * 同步项目测算数据
     *
     * @return
     */
    @GetMapping("transferData")
    public String transferData() {
        //清除原有数据
        testJdbcTemplate.update("delete from PM_INVEST_EST_DTL where CPMS_ID is not null");

        List<Map<String, Object>> projectList = testJdbcTemplate.queryForList("select * from PM_PRJ where `STATUS` = 'ap'");

        List<Map<String, Object>> list = cpmsJdbcTemplate.queryForList("select * from project_budget_cost where del_flag='0'");
        Map<String, List<Map<String, Object>>> objMap = list.stream().collect(Collectors.groupingBy(p -> String.valueOf(p.get("project_id"))));
        for (String key : objMap.keySet()) {
            String pmPrjId = "";
            Optional<Map<String, Object>> optional = projectList.stream().filter(p -> Objects.equals(key, String.valueOf(p.get("CPMS_UUID")))).findAny();
            if (optional.isPresent()) {
                pmPrjId = String.valueOf(optional.get().get("ID"));
            } else {
                continue;
            }
            testJdbcTemplate.update("delete from PM_INVEST_EST where PM_PRJ_ID=?", pmPrjId);
            //投资测算类型
            List<Map<String, Object>> typeList = testJdbcTemplate.queryForList("select gsv.* from gr_set_value gsv left join gr_set gs on gsv.GR_SET_ID = gs.ID where gs.`CODE` ='invest_est_type'");
            for (Map<String, Object> stringObjectMap : typeList) {
                String investId = Util.insertData(testJdbcTemplate, "PM_INVEST_EST");
                testJdbcTemplate.update("update PM_INVEST_EST set IS_TEMPLATE='0',PM_PRJ_ID=?,INVEST_EST_TYPE_ID=?,CPMS_ID=?,CPMS_UUID=? where id=?", pmPrjId, stringObjectMap.get("ID"), key, key, investId);
                String code = String.valueOf(stringObjectMap.get("CODE"));
                String str = String.valueOf(Integer.parseInt(code.substring(code.length() - 1)) + 1);
                //测算明细
                List<Map<String, Object>> detList = objMap.get(key);
                //CPMS系统中的项目预算数据
                List<Map<String, Object>> budgetList = detList.stream().filter(m -> Objects.equals(str, m.get("budget_type"))).collect(Collectors.toList());
                //查询费用类型
                List<Map<String, Object>> expTypeList = testJdbcTemplate.queryForList("select ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,SEQ_NO,ifnull(PM_EXP_TYPE_PID,0) as PM_EXP_TYPE_PID,CALC_BY_PAYMENT,CALC_BY_PROGRESS from PM_EXP_TYPE");
                AtomicInteger index = new AtomicInteger(1);
                budgetList.stream().filter(p -> Objects.equals("0", String.valueOf(p.get("parent_id")))).peek(m -> {
                    String id = getInvestDtlId();
                    String pmExpTypeId = null;
                    Optional optionalName = Arrays.stream(CostTypeEnum.values()).filter(obj -> String.valueOf(m.get("indicators_name")).equals(obj.getDes())).findAny();
                    if (optionalName.isPresent()) {
                        CostTypeEnum typeEnum = (CostTypeEnum) optionalName.get();
                        String enumCode = typeEnum.getCode();
                        Optional<Map<String, Object>> optionalMap = expTypeList.stream().filter(q -> Objects.equals(enumCode, String.valueOf(q.get("CODE")))).findAny();
                        if (optionalMap.isPresent()) {
                            pmExpTypeId = String.valueOf(optionalMap.get().get("ID"));
                        }
                    }
                    testJdbcTemplate.update("update PM_INVEST_EST_DTL set `NAME`=?,PM_EXP_TYPE_ID=?,AMT=?,SEQ_NO=?,PM_INVEST_EST_ID=?,CPMS_UUID=?,CPMS_ID=? where ID=?", m.get("indicators_name"), pmExpTypeId, m.get("amount"), index.getAndIncrement(), investId, m.get("id"), m.get("id"), id);
                    getChildren(m, budgetList, investId, id, expTypeList);
                }).collect(Collectors.toList());
            }
        }
        return "success";
    }

    /**
     * 递归处理子级
     *
     * @param parent
     * @param allData
     * @param investId
     * @param pid
     * @param expTypeList
     * @return
     */
    private List<Map<String, Object>> getChildren(Map<String, Object> parent, List<Map<String, Object>> allData, String investId, String pid, List<Map<String, Object>> expTypeList) {
        AtomicInteger index = new AtomicInteger(1);
        return allData.stream().filter(p -> Objects.equals(String.valueOf(parent.get("id")), String.valueOf(p.get("parent_id")))).peek(m -> {
            String id = getInvestDtlId();
            String pmExpTypeId = null;
            Optional optionalName = Arrays.stream(CostTypeEnum.values()).filter(obj -> String.valueOf(m.get("indicators_name")).equals(obj.getDes())).findAny();
            if (optionalName.isPresent()) {
                CostTypeEnum typeEnum = (CostTypeEnum) optionalName.get();
                String enumCode = typeEnum.getCode();
                Optional<Map<String, Object>> optionalMap = expTypeList.stream().filter(q -> Objects.equals(enumCode, String.valueOf(q.get("CODE")))).findAny();
                if (optionalMap.isPresent()) {
                    pmExpTypeId = String.valueOf(optionalMap.get().get("ID"));
                }
            }
            testJdbcTemplate.update("update PM_INVEST_EST_DTL set `NAME`=?,PM_EXP_TYPE_ID=?,AMT=?,SEQ_NO=?,PM_INVEST_EST_ID=?,CPMS_UUID=?,CPMS_ID=?,PM_INVEST_EST_DTL_PID=? where ID=?", m.get("indicators_name"), pmExpTypeId, m.get("amount"), index.getAndIncrement(), investId, m.get("id"), m.get("id"), pid, id);
            getChildren(m, allData, investId, id, expTypeList);
        }).collect(Collectors.toList());
    }

    /**
     * 获取ID
     *
     * @return
     */
    private String getInvestDtlId() {
        return Util.insertData(testJdbcTemplate, "PM_INVEST_EST_DTL");
    }
}
