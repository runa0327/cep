package com.cisdi.data.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
            }
            testJdbcTemplate.update("delete from PM_INVEST_EST where PM_PRJ=?", pmPrjId);
            //投资测算类型
            List<Map<String, Object>> typeList = testJdbcTemplate.queryForList("select gsv.* from gr_set_value gsv left join gr_set gs on gsv.GR_SET_ID = gs.ID where gs.`CODE` ='invest_est_type'");
            for (Map<String, Object> stringObjectMap : typeList) {
                String id = Util.insertData(testJdbcTemplate, "PM_INVEST_EST");
                testJdbcTemplate.update("update PM_INVEST_EST set IS_TEMPLATE='0',PM_PRJ_ID=?,INVEST_EST_TYPE_ID=? where id=?", pmPrjId, stringObjectMap.get("ID"), id);
                String code = String.valueOf(stringObjectMap.get("CODE"));
                String str = String.valueOf(Integer.parseInt(code.substring(code.length() - 1)) + 1);
                //测算明细
                List<Map<String, Object>> detList = objMap.get(key);
                List<Map<String, Object>> org = detList.stream().filter(m->Objects.equals(str,m.get("budget_type"))).collect(Collectors.toList());

                List<Map<String, Object>> expTypeList = testJdbcTemplate.queryForList("select ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,SEQ_NO,ifnull(PM_EXP_TYPE_PID,0) as PM_EXP_TYPE_PID,CALC_BY_PAYMENT,CALC_BY_PROGRESS from PM_EXP_TYPE");


            }


        }

        return "success";
    }

    public static void main(String[] args) {
        String str = "invest1";
        String res = str.substring(str.length() - 1);
        System.out.println(res);
    }
}
