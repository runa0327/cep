package com.cisdi.ext.weeklyReport;

import cn.hutool.core.date.DateUtil;
import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.model.BaseYear;
import com.cisdi.ext.model.PmConstruction;
import com.cisdi.ext.model.view.project.PmPrjView;
import com.cisdi.ext.model.view.weekReport.PmConstructionView;
import com.cisdi.ext.model.view.weekReport.WeekMessage;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 工程建安费用需求填报
 */
public class PmConstructionExt {

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
        String yearId = BaseYear.selectByWhere(new Where().eq(BaseYear.Cols.CODE,year).eq(BaseYear.Cols.STATUS,"AP")).get(0).getId(); // 年份id
        Map<String,Object> resMap = new HashMap<>();
        PmPrjView pmPrjView = PmPrjExt.queryPrjStatusById(projectId); // 获取项目基础信息
        List<PmConstructionView> detail = getPrjMonthDetail(projectId,yearId,myJdbcTemplate);
        resMap.put("project",pmPrjView);
        resMap.put("yearId",yearId);
    }

    /**
     * 获取项目当年个月明细信息
     * @param projectId 项目id
     * @param yearId 年份id
     * @param myJdbcTemplate 数据源
     * @return 查询结果
     */
    private List<PmConstructionView> getPrjMonthDetail(String projectId, String yearId,MyJdbcTemplate myJdbcTemplate) {
        List<PmConstructionView> list = new ArrayList<>();
        String sql = "SELECT a.BASE_YEAR_ID as baseYearId,c.name as year,a.PM_PRJ_ID as projectId,b.NUMBER as month,b.AMT_FIVE as firstAmt,b.AMT_SIX as checkAmt,a.SYS_TRUE_ONE as yearAmtNeed,b.id as id,a.id as pmConstructionId FROM PM_CONSTRUCTION a LEFT JOIN pm_construction_detail b ON a.id = b.pm_construction_id LEFT JOIN base_year c ON a.BASE_YEAR_ID = c.id WHERE a.pm_prj_id = ? and a.BASE_YEAR_ID = ? ORDER BY b.NUMBER asc";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql,projectId,yearId);
        if (!CollectionUtils.isEmpty(list1)){
            for (Map<String, Object> map : list1) {
                PmConstructionView pmConstructionView = new PmConstructionView();
                pmConstructionView.setId(JdbcMapUtil.getString(map,"id"));
                pmConstructionView.setYear(JdbcMapUtil.getString(map,"year"));
                pmConstructionView.setMonth(JdbcMapUtil.getString(map,"month"));
                list.add(pmConstructionView);
            }
        }
        return list;
    }
}
