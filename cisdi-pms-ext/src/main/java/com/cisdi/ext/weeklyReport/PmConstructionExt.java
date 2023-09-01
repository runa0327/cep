package com.cisdi.ext.weeklyReport;

import com.cisdi.ext.model.BaseYear;
import com.cisdi.ext.model.PmConstruction;
import com.cisdi.ext.model.PmConstructionDetail;
import com.cisdi.ext.model.PmConstructionLog;
import com.cisdi.ext.model.view.weekReport.PmConstructionDetailView;
import com.cisdi.ext.model.view.weekReport.PmConstructionLogView;
import com.cisdi.ext.model.view.weekReport.PmConstructionView;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 工程建安费用需求填报
 */
public class PmConstructionExt {

    public static final BigDecimal WAN = new BigDecimal(10000);

    /**
     * 工程建安费需求填报-当年单项目详情
     */
    public void prjYearConstruct(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PmConstructionView param = JsonUtil.fromJson(json,PmConstructionView.class);
        String projectId = param.getProjectId(); // 项目id
        String year = DateTimeUtil.getYear(new Date());
        String baseYearId = BaseYear.selectByWhere(new Where().eq(BaseYear.Cols.CODE,year).eq(BaseYear.Cols.STATUS,"AP")).get(0).getId(); // 年份id
        Map<String,Object> resMap = new HashMap<>();
        PmConstructionView pmConstructionView = queryPrjStatusById(projectId,baseYearId,myJdbcTemplate); // 获取项目相关信息
        PmConstructionDetailView nowMonth = getMonth(pmConstructionView.getDetailList()); // 本月需确认或调整金额

        resMap.put("project",pmConstructionView);
        resMap.put("baseYearId",baseYearId);
        resMap.put("monthN",nowMonth);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resMap), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 根据项目id查询项目状态
     * @param projectId 项目id
     * @param baseYearId 年份id
     * @param myJdbcTemplate 数据源
     * @return
     */
    public PmConstructionView queryPrjStatusById(String projectId, String baseYearId, MyJdbcTemplate myJdbcTemplate) {
        PmConstructionView pmConstructionView = new PmConstructionView();

        String sql = "select a.pm_prj_id as projectId,ifnull(b.IZ_START_REQUIRE,1) as weatherStart,ifnull(b.IZ_END,0) as weatherComplete,ifnull(a.SYS_TRUE_ONE,0) as yearAmtNeed,a.id as pmConstructionId from pm_construction a left join pm_prj b on a.PM_PRJ_ID = b.id where a.base_year_id = ? and a.pm_prj_id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,baseYearId,projectId);
        if (!CollectionUtils.isEmpty(list)){
            pmConstructionView.setWeatherStart(JdbcMapUtil.getInt(list.get(0),"weatherStart"));
            pmConstructionView.setWeatherComplete(JdbcMapUtil.getInt(list.get(0),"weatherComplete"));
            pmConstructionView.setProjectId(JdbcMapUtil.getString(list.get(0),"projectId"));
            pmConstructionView.setYearAmtNeed(JdbcMapUtil.getInt(list.get(0),"yearAmtNeed"));
            pmConstructionView.setPmConstructionId(JdbcMapUtil.getString(list.get(0),"pmConstructionId"));

            List<PmConstructionDetailView> detail = getPrjMonthDetail(projectId,baseYearId,myJdbcTemplate); // 各月份明细信息
            pmConstructionView.setDetailList(detail);

            BigDecimal allAmt = detail.stream().map(PmConstructionDetailView::getFirstAmt).reduce(BigDecimal.ZERO,BigDecimal::add);// 本年需求总资金
            pmConstructionView.setYearAmt(allAmt);
        }
        return pmConstructionView;
    }

    /**
     * 获取当前月份信息
     * @param detail 全年明细
     * @return 当月明细
     */
    private PmConstructionDetailView getMonth(List<PmConstructionDetailView> detail) {
        String month = DateTimeUtil.getMonth(new Date());
        PmConstructionDetailView pmConstructionDetailView = detail.stream().filter(p->month.equals(p.getMonth())).collect(Collectors.toList()).get(0);
        pmConstructionDetailView.setCheckAmt(pmConstructionDetailView.getFirstAmt());
        String monthN = pmConstructionDetailView.getYear() + "年" +pmConstructionDetailView.getMonth() + "月";
        pmConstructionDetailView.setMonth(monthN);
        return pmConstructionDetailView;
    }

    /**
     * 获取项目当年个月明细信息
     * @param projectId 项目id
     * @param baseYearId 年份id
     * @param myJdbcTemplate 数据源
     * @return 查询结果
     */
    public List<PmConstructionDetailView> getPrjMonthDetail(String projectId, String baseYearId,MyJdbcTemplate myJdbcTemplate) {
        List<PmConstructionDetailView> list = new ArrayList<>();
        String sql = "SELECT a.BASE_YEAR_ID as baseYearId,c.name as year,a.PM_PRJ_ID as projectId,b.NUMBER as month,b.AMT_FIVE as firstAmt,b.AMT_SIX as checkAmt,a.SYS_TRUE_ONE as yearAmtNeed,b.id as id,a.id as pmConstructionId FROM PM_CONSTRUCTION a LEFT JOIN pm_construction_detail b ON a.id = b.pm_construction_id LEFT JOIN base_year c ON a.BASE_YEAR_ID = c.id WHERE a.pm_prj_id = ? and a.BASE_YEAR_ID = ? ORDER BY b.NUMBER asc";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql,projectId,baseYearId);
        if (!CollectionUtils.isEmpty(list1)){
            for (Map<String, Object> map : list1) {
                PmConstructionDetailView pmConstructionDetailView = new PmConstructionDetailView();
                pmConstructionDetailView.setId(JdbcMapUtil.getString(map,"id"));
                pmConstructionDetailView.setMonth(JdbcMapUtil.getString(map,"month"));
                BigDecimal amt = new BigDecimal(JdbcMapUtil.getString(map,"firstAmt")).divide(WAN);
                pmConstructionDetailView.setFirstAmt(amt);
                pmConstructionDetailView.setYear(JdbcMapUtil.getString(map,"year"));
                pmConstructionDetailView.setProjectId(JdbcMapUtil.getString(map,"projectId"));
                pmConstructionDetailView.setPmConstructionId(JdbcMapUtil.getString(map,"pmConstructionId"));
                pmConstructionDetailView.setBaseYearId(JdbcMapUtil.getString(map,"baseYearId"));
                list.add(pmConstructionDetailView);
            }
        }
        return list;
    }

    /**
     * 工程建安费需求填报-填报年度需求资金-列表
     */
    public void getConstructMonthList(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PmConstructionView param = JsonUtil.fromJson(json,PmConstructionView.class);
        String projectId = param.getProjectId();
        String baseYearId = param.getBaseYearId();
        PmConstructionView pmConstructionView = queryPrjStatusById(projectId,baseYearId,myJdbcTemplate); // 获取项目相关信息
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("list",pmConstructionView);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resMap), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 工程建安费需求填报-填报年度需求资金-填报
     */
    public void updateConstructMonthList(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PmConstructionView param = JsonUtil.fromJson(json,PmConstructionView.class);
        List<PmConstructionDetailView> list = param.getDetailList();
        if (!CollectionUtils.isEmpty(list)){
            String now = DateTimeUtil.dttmToString(new Date());
            String userId = ExtJarHelper.loginInfo.get().userId;
            for (PmConstructionDetailView tmp : list) {
                String id = tmp.getId();
                BigDecimal checkAmt = tmp.getCheckAmt();
                Crud.from(PmConstructionDetail.ENT_CODE).where().eq("ID",id).update()
                        .set("AMT_FIVE",checkAmt.multiply(WAN)).set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",userId)
                        .set("TS",now)
                        .exec();
                BigDecimal firstAmt = PmConstructionDetail.selectById(id).getAmtFive().divide(WAN);
                String month = param.getYear()+"年"+param.getMonth()+"月";
                tmp.setMonth(month);
                insertLog(firstAmt,checkAmt,userId,tmp,now);
            }
            Crud.from(PmConstruction.ENT_CODE).where().eq(PmConstruction.Cols.ID,param.getPmConstructionId()).update()
                    .set(PmConstruction.Cols.SYS_TRUE_ONE,1).set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",userId)
                    .exec();
        }
    }

    /**
     * 工程建安费需求填报-填报年度需求资金-修改单独一个月
     */
    public void updateOneMonth(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PmConstructionDetailView param = JsonUtil.fromJson(json,PmConstructionDetailView.class);
        String now = DateTimeUtil.dttmToString(new Date());
        String userId = ExtJarHelper.loginInfo.get().userId;
        String id = param.getId();
        BigDecimal firstAmt = PmConstructionDetail.selectById(id).getAmtFive().divide(WAN);
        BigDecimal updateAmt = param.getFirstAmt().multiply(WAN);
        BigDecimal updateAmtWan = updateAmt.divide(WAN);
        Crud.from(PmConstructionDetail.ENT_CODE).where().eq("ID",id).update()
                .set("AMT_FIVE",updateAmt).set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",userId)
                .set("TS",now)
                .exec();

        Crud.from(PmConstruction.ENT_CODE).where().eq(PmConstruction.Cols.ID,param.getPmConstructionId()).update()
                .set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",userId)
                .exec();
        // 修改记录写入
        String month = param.getYear()+"年"+param.getMonth()+"月";
        param.setMonth(month);
        insertLog(firstAmt,updateAmtWan,userId,param,now);

    }

    /**
     * 工程建安费需求填报-月份金额确认
     */
    public void checkMonthAmt(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PmConstructionDetailView param = JsonUtil.fromJson(json,PmConstructionDetailView.class);
        String userId = ExtJarHelper.loginInfo.get().userId;
        BigDecimal firstAmt = param.getFirstAmt();
        BigDecimal checkAmt = param.getCheckAmt();
        if (checkAmt.compareTo(firstAmt) != 0){
            BigDecimal checkAmtNotWan = checkAmt.multiply(WAN);
            String now = DateTimeUtil.dttmToString(new Date());
            Crud.from(PmConstructionDetail.ENT_CODE).where().eq(PmConstructionDetail.Cols.ID,param.getId()).update()
                    .set(PmConstructionDetail.Cols.AMT_FIVE,checkAmtNotWan)
                    .set(PmConstructionDetail.Cols.AMT_SIX,checkAmtNotWan)
                    .exec();
            // 修改记录写入
            insertLog(firstAmt,checkAmt,userId,param,now);
        }
    }

    /**
     * 填报记录存储
     * @param firstAmt 初始金额
     * @param checkAmt 确认金额
     * @param userId 操作人
     * @param param 单条记录实体
     * @param now 操作时间
     */
    public void insertLog(BigDecimal firstAmt, BigDecimal checkAmt, String userId, PmConstructionDetailView param, String now) {
        StringBuilder sb = new StringBuilder(param.getMonth()).append(",金额由'").append(firstAmt).append("'修改为'").append(checkAmt).append("'");
        String id = Crud.from(PmConstructionLog.ENT_CODE).insertData();
        Crud.from(PmConstructionLog.ENT_CODE).where().eq(PmConstructionLog.Cols.ID,id).update()
                .set(PmConstructionLog.Cols.VER,1).set("TS",now).set("CRT_DT",now).set("CRT_USER_ID",userId)
                .set("LAST_MODI_DT",now).set("LAST_MODI_USER_ID",userId).set("STATUS","AP").set("TEXT_REMARK_ONE",sb)
                .set("PM_PRJ_ID",param.getProjectId()).set("BASE_YEAR_ID",param.getBaseYearId())
                .exec();
    }

    /**
     * 年度需求修改记录-列表
     */
    public void getWriteRecordList(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PmConstructionLogView param = JsonUtil.fromJson(json,PmConstructionLogView.class);
        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;

        String sql = "select a.id as id,a.LAST_MODI_DT as updateTime,(select name from ad_user where id = a.LAST_MODI_USER_ID) as updateBy,a.TEXT_REMARK_ONE as content from PM_CONSTRUCTION_LOG a  where a.status = 'AP' AND a.pm_prj_id = ? and a.base_year_id = ? order by CRT_DT desc ";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql + " " + limit,param.getProjectId(),param.getBaseYearId());
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList("select count(*) as num from PM_CONSTRUCTION_LOG a  where a.status = 'AP' AND a.pm_prj_id = ? and a.base_year_id = ? order by CRT_DT desc ",param.getProjectId(),param.getBaseYearId());
        if (!CollectionUtils.isEmpty(list1)){
            List<PmConstructionLogView> resList = list1.stream().map(p->{
                PmConstructionLogView pmConstructionLogView = new PmConstructionLogView();
                pmConstructionLogView.setId(JdbcMapUtil.getString(p,"id"));
                pmConstructionLogView.setUpdateTime(JdbcMapUtil.getString(p,"updateTime").replace("T"," "));
                pmConstructionLogView.setUpdateBy(JdbcMapUtil.getString(p,"updateBy"));
                pmConstructionLogView.setContent(JdbcMapUtil.getString(p,"content"));
                return pmConstructionLogView;
            }).collect(Collectors.toList());
            Map<String,Object> map2 = new HashMap<>();
            map2.put("total",JdbcMapUtil.getInt(list2.get(0),"num"));
            map2.put("list",resList);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map2), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(null);
        }
    }

    /**
     * 工程建安费需求统计-列表
     */
    public void getConstructList(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PmConstructionView param = JsonUtil.fromJson(json,PmConstructionView.class);

        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;

        String baseYearId = param.getBaseYearId();
        Map<String,Object> listMap = getProject(param,limit,myJdbcTemplate);
        String prjIds = listMap.get("prjIds").toString();
        if (StringUtils.hasText(prjIds)){
            StringBuilder sb = new StringBuilder("select a.pm_prj_id as projectId,a.name as projectName,a.IZ_START_REQUIRE as weatherStart,")
                    .append("a.IZ_END as weatherComplete,");
            for (int i = 1; i <= 12; i++) {
                sb.append("group_concat(case when a.NUMBER = ").append(i).append(" then AMT_FIVE/10000 else '' end SEPARATOR '') as '").append(i).append("月',");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append(" FROM (select a.pm_prj_id,c.name,c.IZ_START_REQUIRE,c.IZ_END,b.NUMBER,b.AMT_FIVE FROM PM_CONSTRUCTION a LEFT JOIN pm_construction_detail b on a.id = b.PM_CONSTRUCTION_ID LEFT JOIN pm_prj c on a.pm_prj_id = c.id WHERE a.BASE_YEAR_ID = '").append(baseYearId).append("' and a.PM_PRJ_ID in ('").append(prjIds).append("') ) a LEFT JOIN (select a.pm_prj_id,any_value(b.LAST_MODI_DT) as date from PM_CONSTRUCTION a LEFT JOIN pm_construction_detail b on a.id = b.PM_CONSTRUCTION_ID where a.BASE_YEAR_ID = '").append(baseYearId).append("' and a.PM_PRJ_ID in ('").append(prjIds).append("') GROUP BY a.PM_PRJ_ID order by date desc  ) b on a.pm_prj_id = b.pm_prj_id GROUP BY a.pm_prj_id ORDER BY a.IZ_END asc,a.IZ_START_REQUIRE desc,any_value(b.date) desc");
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sb.toString());
            if (!CollectionUtils.isEmpty(list1)){
                Map<String,Object> resMap = new HashMap<>();
                resMap.put("total",JdbcMapUtil.getInt(resMap,"total"));
                resMap.put("list",list1);
                Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resMap), Map.class);
                ExtJarHelper.returnValue.set(outputMap);
            } else {
                ExtJarHelper.returnValue.set(null);
            }
        } else {
            ExtJarHelper.returnValue.set(null);
        }


    }

    /**
     * 工程建安费需求统计-列表页需要显示的项目
     * @param param 查询条件
     * @param param 分页条件
     * @param myJdbcTemplate 数据源
     * @return 列表相关信息
     */
    private Map<String,Object> getProject(PmConstructionView param, String limit, MyJdbcTemplate myJdbcTemplate) {
        Map<String,Object> map = new HashMap<>();
        StringBuilder sb1 = new StringBuilder(); // 项目是否竣工、是否符合开工条件判断
        if (param.getWeatherStart() != null){
            sb1.append(" and IZ_START_REQUIRE = '").append(param.getWeatherStart()).append("' ");
        }
        if (param.getWeatherComplete() != null){
            sb1.append(" and IZ_END = '").append(param.getWeatherComplete()).append("' ");
        }
        StringBuilder sb2 = new StringBuilder(); // 填报年份、项目名称判断
        if (StringUtils.hasText(param.getProjectId())){
            sb2.append(" and a.PM_PRJ_ID = '").append(param.getProjectId()).append("' ");
        }
        String sql1 = "select a.pm_prj_id from ( select a.pm_prj_id from PM_CONSTRUCTION a LEFT JOIN pm_construction_detail b on a.id = b.PM_CONSTRUCTION_ID where a.BASE_YEAR_ID = ? "+sb2+" GROUP BY a.PM_PRJ_ID order by any_value(b.LAST_MODI_DT) desc ) a left join (select id,ifnull(IZ_START_REQUIRE,1) as weatherStart,IFNULL(IZ_END,0) as weatherCompleted,name as projectName from pm_prj where PROJECT_SOURCE_TYPE_ID = '0099952822476441374' AND (PROJECT_STATUS != '1661568714048413696' or PROJECT_STATUS is null)"+sb1+"  order by crt_dt asc ) b on a.pm_prj_id = b.id order by b.weatherCompleted asc,b.weatherStart desc ";
        String sql2 = "select count(*) as num from ( select a.pm_prj_id from PM_CONSTRUCTION a LEFT JOIN pm_construction_detail b on a.id = b.PM_CONSTRUCTION_ID where a.BASE_YEAR_ID = ? "+sb2+" GROUP BY a.PM_PRJ_ID order by any_value(b.LAST_MODI_DT) desc ) a left join (select id,ifnull(IZ_START_REQUIRE,1) as weatherStart,IFNULL(IZ_END,0) as weatherCompleted,name as projectName from pm_prj where PROJECT_SOURCE_TYPE_ID = '0099952822476441374' AND (PROJECT_STATUS != '1661568714048413696' or PROJECT_STATUS is null)"+sb1+"  order by crt_dt asc ) b on a.pm_prj_id = b.id order by b.weatherCompleted asc,b.weatherStart desc";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1+limit,param.getBaseYearId());
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,param.getBaseYearId());
        if (!CollectionUtils.isEmpty(list1)){
            List<String> prjList = list1.stream().map(p->JdbcMapUtil.getString(p,"pm_prj_id")).collect(Collectors.toList());
            String prjIds = String.join("','",prjList);
            map.put("total",JdbcMapUtil.getInt(list2.get(0),"num"));
            map.put("prjIds",prjIds);
        } else {
            map.put("total",0);
            map.put("list",null);
        }
        return map;
    }
}
