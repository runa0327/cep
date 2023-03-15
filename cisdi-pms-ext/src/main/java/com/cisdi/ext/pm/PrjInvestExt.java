package com.cisdi.ext.pm;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author dlt
 * @date 2023/3/7 周二
 */
@Slf4j
public class PrjInvestExt {

    /**
     * 处理历史数据
     * 将立项、可研、概算中的投资金额按优先级写到pm_prj
     */
    public void updatePrjInvest(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //需要写入的项目信息
        List<Map<String, Object>> prjMaps = myJdbcTemplate.queryForList("SELECT id prjId,name prjName FROM pm_prj WHERE status = 'AP'");
//        List<Map<String, Object>> prjMaps = myJdbcTemplate.queryForList("SELECT id prjId,name prjName FROM pm_prj WHERE status = 'AP' and name = '测试2'");//测试
        //立项表
        List<Map<String, Object>> prjReqMaps = myJdbcTemplate.queryForList("SELECT '1631460206540161024' priorityId, pj.id prjId, pr.PRJ_NAME " +
                "prjName, pr.EQUIPMENT_COST, pr.PREPARE_AMT, pr.LAND_AMT, pr.PROJECT_OTHER_AMT, pr.EQUIP_AMT, pr.PROJECT_AMT, pr.CONSTRUCT_AMT, pr.PRJ_TOTAL_INVEST FROM \n" +
                "pm_prj_req pr left join pm_prj pj on pj.name = pr.PRJ_NAME\n" +
                "left join (select PRJ_NAME,max(CRT_DT) maxCrtDt from pm_prj_req group by PRJ_NAME) temp on temp.PRJ_NAME = pr.PRJ_NAME\n" +
                "where pr.status = 'AP' and pr.CRT_DT = temp.maxCrtDt and pj.id is not null and (pr.EQUIPMENT_COST is not null or pr.PREPARE_AMT is" +
                " not null or pr.LAND_AMT is not null or pr.PROJECT_OTHER_AMT is not null or pr.EQUIP_AMT is not null or pr.PROJECT_AMT is not null" +
                " or pr.CONSTRUCT_AMT is not null or PRJ_TOTAL_INVEST is not null)");
        //可研表
        List<Map<String, Object>> feasibilityMaps = myJdbcTemplate.queryForList("SELECT '1631460270226472960' priorityId, i.EQUIPMENT_COST, i" +
                ".PREPARE_AMT, i.LAND_AMT, i\n" +
                ".PROJECT_OTHER_AMT, i.EQUIP_AMT, i.CONSTRUCT_AMT, i.PROJECT_AMT, i.PRJ_TOTAL_INVEST,i.CONSTRUCT_PERIOD_INTEREST, i.PM_PRJ_ID prjId" +
                " FROM pm_prj_invest1 i \n" +
                "left join (select PM_PRJ_ID,max(CRT_DT) maxCrtDt from pm_prj_invest1 group by PM_PRJ_ID) temp on temp.PM_PRJ_ID = i.PM_PRJ_ID \n" +
                "where i.status = 'AP' and i.PM_PRJ_ID is not null and i.CRT_DT = temp.maxCrtDt and (i.EQUIPMENT_COST is not null or i.PREPARE_AMT " +
                "is not null or i.LAND_AMT is not null or i.PROJECT_OTHER_AMT is not null or i.EQUIP_AMT is not null or i.PROJECT_AMT is not null " +
                "or i.CONSTRUCT_AMT is not null or i.PRJ_TOTAL_INVEST is not null or i.CONSTRUCT_PERIOD_INTEREST is not null)");
        //概算表
        List<Map<String, Object>> estimateMaps = myJdbcTemplate.queryForList("SELECT '1631460343433854976' priorityId, i.EQUIPMENT_COST, i" +
                ".PREPARE_AMT, i.LAND_AMT, i\n" +
                ".PROJECT_OTHER_AMT, i.EQUIP_AMT, i.CONSTRUCT_AMT, i.PROJECT_AMT, i.PRJ_TOTAL_INVEST,i.CONSTRUCT_PERIOD_INTEREST, i.PM_PRJ_ID prjId" +
                " FROM pm_prj_invest2 i \n" +
                "left join (select PM_PRJ_ID,max(CRT_DT) maxCrtDt from pm_prj_invest2 group by PM_PRJ_ID) temp on temp.PM_PRJ_ID = i.PM_PRJ_ID \n" +
                "where i.status = 'AP' and i.PM_PRJ_ID is not null and i.CRT_DT = temp.maxCrtDt and (i.EQUIPMENT_COST is not null or i.PREPARE_AMT " +
                "is not null or i.LAND_AMT is not null or i.PROJECT_OTHER_AMT is not null or i.EQUIP_AMT is not null or i.PROJECT_AMT is not null " +
                "or i.CONSTRUCT_AMT is not null or i.PRJ_TOTAL_INVEST is not null or i.CONSTRUCT_PERIOD_INTEREST is not null)");
        //财评表
        List<Map<String, Object>> evaluationMaps = myJdbcTemplate.queryForList("SELECT '1631460383644647424' priorityId, i.PM_PRJ_ID prjId, i" +
                ".PRJ_TOTAL_INVEST, i.PREPARE_AMT, i\n" +
                ".LAND_AMT, i.PROJECT_OTHER_AMT, i.EQUIP_AMT, i.CONSTRUCT_AMT, i.PROJECT_AMT,i.CONSTRUCT_PERIOD_INTEREST FROM pm_prj_invest3 i \n" +
                "left join (select PM_PRJ_ID,max(CRT_DT) maxCrtDt from pm_prj_invest3 group by PM_PRJ_ID) temp on temp.PM_PRJ_ID = i.PM_PRJ_ID\n" +
                "where i.status = 'AP' and i.PM_PRJ_ID is not null and i.CRT_DT = temp.maxCrtDt and (i.PREPARE_AMT is not null or i.LAND_AMT is not" +
                " null or i.PROJECT_OTHER_AMT is not null or i.EQUIP_AMT is not null or i.PROJECT_AMT is not null or i.CONSTRUCT_AMT is not null or" +
                " i.PRJ_TOTAL_INVEST is not null or i.CONSTRUCT_PERIOD_INTEREST is not null)");

        //投资链表，按优先级排列
        List<List<Map<String, Object>>> investLists = new ArrayList<>();
        investLists.add(evaluationMaps);
        investLists.add(estimateMaps);
        investLists.add(feasibilityMaps);
        investLists.add(prjReqMaps);

        //依次到投资链表找，找到就更新并退出
        for (Map<String, Object> prjMap : prjMaps) {
            for (List<Map<String, Object>> investList : investLists) {
                Optional<Map<String, Object>> investOp =
                        investList.stream().filter(invest -> prjMap.get("prjId").toString().equals(invest.get("prjId").toString())).findAny();
                if (investOp.isPresent()){
                    Map<String, Object> investMap = investOp.get();
                    this.doUpdatePrjInvest(investMap);
                    break;
                }
            }
        }



    }

    /**
     * 写入pm_prj
     * @param investMap
     */
    private void doUpdatePrjInvest(Map<String,Object> investMap){
        Crud.from("pm_prj").where().eq("ID",investMap.get("prjId")).update()
                .set("ESTIMATED_TOTAL_INVEST",investMap.get("PRJ_TOTAL_INVEST"))
                .set("PROJECT_AMT",investMap.get("PROJECT_AMT"))
                .set("CONSTRUCT_PRJ_AMT",investMap.get("CONSTRUCT_AMT"))
                .set("EQUIP_BUY_AMT",investMap.get("EQUIP_AMT"))
                .set("EQUIPMENT_COST",investMap.get("EQUIPMENT_COST"))
                .set("PROJECT_OTHER_AMT",investMap.get("PROJECT_OTHER_AMT"))
                .set("LAND_BUY_AMT",investMap.get("LAND_AMT"))
                .set("PREPARE_AMT",investMap.get("PREPARE_AMT"))
                .set("INVEST_PRIORITY",investMap.get("priorityId"))
                .set("CONSTRUCT_PERIOD_INTEREST",investMap.get("CONSTRUCT_PERIOD_INTEREST"))
                .exec();
    }

    //pm_prj中的投资数据
    public static class prjInvest{
        //总投资
        public String ESTIMATED_TOTAL_INVEST;
        //工程费用
        public String PROJECT_AMT;
        //建安工程费
        public String CONSTRUCT_PRJ_AMT;
        //设备采购费
        public String EQUIP_BUY_AMT;
        //科研设备费
        public String EQUIPMENT_COST;
        //工程其他费用
        public String PROJECT_OTHER_AMT;
        //土地征拆费用
        public String LAND_BUY_AMT;
        //预备费
        public String PREPARE_AMT;
    }

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 20, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                for (int j = 100; j < 200; j++) {
                    System.out.println("线程" + finalI + ":输出" + j);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
        }

    }
}
