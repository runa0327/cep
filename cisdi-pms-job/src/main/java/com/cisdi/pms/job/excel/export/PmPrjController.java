package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.cisdi.pms.job.excel.model.PmPrjExportModel;
import com.cisdi.pms.job.excel.model.request.PrjRequest;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dlt
 * @date 2022/11/28 周一
 */
@RestController
@RequestMapping("prj")
@Slf4j
public class PmPrjController extends BaseController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 项目库导出
     */
    @SneakyThrows(IOException.class)
    @GetMapping("export")
    public void prjExcel(PrjRequest request, HttpServletResponse response, HttpServletRequest req){
//        String userId = this.getLoginUser(req.getHeader("qygly-session-id"));
//        Map<String, String> cookieMap = StringUtils.cookieToMap(req.getHeader("Cookie"));
//        String username = cookieMap.get("username");
//        //用户id
//        String userId = this.getUserId(username);
        //项目名
        String name = request.getName();
        //业主
        String customerUnitId = request.getCustomerUnitId();
        //项目类型
        String projectTypeId = request.getProjectTypeId();
        //项目状态
        String projectPhaseId = request.getProjectPhaseId();
        //进度阶段
        String transitionPhaseId = request.getTransitionPhaseId();
        //计划开工时间
//        String startDate = request.getStartDate();
//        String endDate = request.getEndDate();
        //计划开工年份
        String year = request.getYear();

        //获取财务人员和管理员id
        List<Map<String, Object>> financialUsersList = jdbcTemplate.queryForList("select u.id userId\n" +
                "from ad_user u\n" +
                "left join hr_dept_user du on du.AD_USER_ID = u.id\n" +
                "left join hr_dept d on d.id = du.HR_DEPT_ID\n" +
                "where d.name = '财务金融部' or u.id = '0099250247095871681' ");

        //所有项目权限的人员id
        String userIds = financialUsersList.stream().map(item -> JdbcMapUtil.getString(item,"userId")).collect(Collectors.joining(","));

        StringBuffer baseSql = new StringBuffer();
//        baseSql.append("select t.ID AS id, t.STATUS AS STATUS, t.CRT_DT AS CRT_DT, t.CODE AS code, t.NAME AS name, t.CUSTOMER_UNIT AS CUSTOMER_UNIT, t.PROJECT_TYPE_ID AS PROJECT_TYPE_ID, t.BASE_LOCATION_ID AS BASE_LOCATION_ID, t.PRJ_MANAGE_MODE_ID AS PRJ_MANAGE_MODE_ID, t.PROJECT_PHASE_ID AS PROJECT_PHASE_ID, t.TRANSITION_PHASE_ID AS TRANSITION_PHASE_ID, t.CPMS_ID AS CPMS_ID, t.CPMS_UUID AS CPMS_UUID, tmp.PRJ_TOTAL_INVEST AS PRJ_TOTAL_INVEST, tmp2.PLAN_START_DATE AS PLAN_START_DATE, tmp2.ACTUAL_START_DATE AS ACTUAL_START_DATE, tmp2.PLAN_COMPL_DATE AS PLAN_COMPL_DATE, tmp2.ACTUAL_COMPL_DATE AS ACTUAL_COMPL_DATE, tmp2.PLAN_CURRENT_PRO_PERCENT AS PLAN_CURRENT_PRO_PERCENT, tmp2.ACTUAL_CURRENT_PRO_PERCENT AS ACTUAL_CURRENT_PRO_PERCENT,pp.name CUSTOMER_UNIT_NAME,va1.name PROJECT_TYPE_NAME,va2.name BASE_LOCATION_NAME,va3.name PRJ_MANAGE_MODE_NAME,va4.name PROJECT_PHASE_NAME,va5.name TRANSITION_PHASE_NAME from ((pm_prj t  left join (select e.ID AS ID, e.VER AS VER, e.TS AS TS, e.IS_PRESET AS IS_PRESET, e.CRT_DT AS CRT_DT, e.CRT_USER_ID AS CRT_USER_ID, e.LAST_MODI_DT AS LAST_MODI_DT, e.LAST_MODI_USER_ID AS LAST_MODI_USER_ID, e.STATUS AS STATUS, e.LK_WF_INST_ID AS LK_WF_INST_ID, e.CODE AS CODE, e.NAME AS NAME, e.REMARK AS REMARK, e.PM_PRJ_ID AS PM_PRJ_ID, e.INVEST_EST_TYPE_ID AS INVEST_EST_TYPE_ID, e.PRJ_TOTAL_INVEST AS PRJ_TOTAL_INVEST, e.REPLY_NO AS REPLY_NO, e.REPLY_ACTUAL_DATE AS REPLY_ACTUAL_DATE, e.REPLY_FILE AS REPLY_FILE, e.IS_TEMPLATE AS IS_TEMPLATE, e.TEMPLATE_FOR_PROJECT_TYPE_ID AS TEMPLATE_FOR_PROJECT_TYPE_ID, e.CPMS_UUID AS CPMS_UUID, e.CPMS_ID AS CPMS_ID from (pm_invest_est e join gr_set_value v on (((e.INVEST_EST_TYPE_ID = v.ID) and (v.CODE = 'invest2') and (e.IS_TEMPLATE = 0))))) tmp on ((t.ID = tmp.PM_PRJ_ID))) left join pm_party pp on pp.id = t.CUSTOMER_UNIT left join gr_set_value va1 on va1.id = t.PROJECT_TYPE_ID left join gr_set se1 on se1.id = va1.GR_SET_ID and se1.code='project_type' left join gr_set_value va2 on va2.id = t.BASE_LOCATION_ID left join gr_set se2 on se2.id = va2.GR_SET_ID and se2.code='base_location' left join gr_set_value va3 on va3.id = t.PRJ_MANAGE_MODE_ID left join gr_set se3 on se3.id = va3.GR_SET_ID and se3.code='management_unit' left join gr_set_value va4 on va4.id = t.PROJECT_PHASE_ID left join gr_set se4 on se4.id = va4.GR_SET_ID and se4.code='project_phase' left join gr_set_value va5 on va5.id = t.TRANSITION_PHASE_ID left join gr_set se5 on se5.id = va5.GR_SET_ID and se5.code='transition_phase' left join (select p.ID AS ID, p.VER AS VER, p.TS AS TS, p.IS_PRESET AS IS_PRESET, p.CRT_DT AS CRT_DT, p.CRT_USER_ID AS CRT_USER_ID, p.LAST_MODI_DT AS LAST_MODI_DT, p.LAST_MODI_USER_ID AS LAST_MODI_USER_ID, p.STATUS AS STATUS, p.LK_WF_INST_ID AS LK_WF_INST_ID, p.CODE AS CODE, p.NAME AS NAME, p.REMARK AS REMARK, p.ACTUAL_START_DATE AS ACTUAL_START_DATE, p.PROGRESS_RISK_REMARK AS PROGRESS_RISK_REMARK, p.IS_TEMPLATE AS IS_TEMPLATE, p.PM_PRJ_ID AS PM_PRJ_ID, p.PLAN_START_DATE AS PLAN_START_DATE, p.PLAN_TOTAL_DAYS AS PLAN_TOTAL_DAYS, p.PLAN_CARRY_DAYS AS PLAN_CARRY_DAYS, p.ACTUAL_CARRY_DAYS AS ACTUAL_CARRY_DAYS, p.ACTUAL_TOTAL_DAYS AS ACTUAL_TOTAL_DAYS, p.PLAN_CURRENT_PRO_PERCENT AS PLAN_CURRENT_PRO_PERCENT, p.ACTUAL_CURRENT_PRO_PERCENT AS ACTUAL_CURRENT_PRO_PERCENT, p.PLAN_COMPL_DATE AS PLAN_COMPL_DATE, p.ACTUAL_COMPL_DATE AS ACTUAL_COMPL_DATE, p.TEMPLATE_FOR_PROJECT_TYPE_ID AS TEMPLATE_FOR_PROJECT_TYPE_ID, p.PROGRESS_STATUS_ID AS PROGRESS_STATUS_ID, p.PROGRESS_RISK_TYPE_ID AS PROGRESS_RISK_TYPE_ID, p.START_DAY AS START_DAY, p.CPMS_UUID AS CPMS_UUID, p.CPMS_ID AS CPMS_ID from pm_pro_plan p where (p.IS_TEMPLATE = 0)) tmp2 on ((t.ID = tmp2.PM_PRJ_ID))) WHERE t.PROJECT_SOURCE_TYPE_ID = '0099952822476441374'  and IF(FIND_IN_SET(?,?),1=1,t.id in (select DISTINCT pm_prj_id from pm_dept WHERE STATUS = 'ap' and FIND_IN_SET(?, USER_IDS ))) AND T.STATUS='AP'");
        baseSql.append("select pm.PM_SEQ as num,pm.id as id,pm.`NAME` as name,pt.`NAME` as customerUnit,gsv.`NAME` as projectType,pm.PM_CODE as code,ggg.`NAME` as baseLocation, \n" +
                "gsvv.`NAME` as prjManageMode,format(pm.ESTIMATED_TOTAL_INVEST/10000,6) as prjTotalInvest,gss.`NAME` as projectPhase,pm.ver as ver, \n" +
                "concat_ws('/',ifnull(ppp.PLAN_START_DATE,'无'),ifnull(ppp.ACTUAL_START_DATE,'无')) startDate," +
                "concat_ws('/',ifnull(ppp.PLAN_COMPL_DATE,'无'),ifnull(ppp.ACTUAL_COMPL_DATE,'无')) complDate," +
                "concat_ws('/',ifnull(ppp.PLAN_CURRENT_PRO_PERCENT,'0'),ifnull(ppp.ACTUAL_CURRENT_PRO_PERCENT,'0')) percent," +
                "ggt.name AS transitionPhase  \n" +
                "from pm_prj pm \n" +
                "left join pm_party pt on pm.CUSTOMER_UNIT = pt.id \n" +
                "left join gr_set_value gsv on gsv.id = pm.PROJECT_TYPE_ID \n" +
                "left join gr_set_value gsvv on gsvv.id = pm.PRJ_MANAGE_MODE_ID \n" +
                "left join gr_set_value gss on gss.id = pm.PROJECT_PHASE_ID \n" +
                "left join pm_pro_plan ppp on ppp.PM_PRJ_ID = pm.id \n" +
                "left join gr_set_value ggg on ggg.id = pm.BASE_LOCATION_ID "+
                "left join gr_set_value ggt on ggt.id = pm.TRANSITION_PHASE_ID "+
                "where pm.PROJECT_SOURCE_TYPE_ID = '0099952822476441374' and pm.`STATUS`='ap' ");
        if (Strings.isNotEmpty(name)){
            baseSql.append(" and pm.name like '%" + name + "%'");
        }
        if (Strings.isNotEmpty(customerUnitId)){
            baseSql.append(" and pm.CUSTOMER_UNIT = '"+customerUnitId+"'");
        }
        if (Strings.isNotEmpty(projectTypeId)){
            baseSql.append(" and pm.PROJECT_TYPE_ID = '"+projectTypeId+"'");
        }
        if (Strings.isNotEmpty(projectPhaseId)){
            baseSql.append(" and pm.PROJECT_PHASE_ID = '"+projectPhaseId+"'");
        }
        if (Strings.isNotEmpty(transitionPhaseId)){
            baseSql.append(" and pm.TRANSITION_PHASE_ID = '"+transitionPhaseId+"'");
        }
//        if (Strings.isNotEmpty(startDate) && Strings.isNotEmpty(endDate)){
//            baseSql.append(" and IFNULL(tmp2.ACTUAL_START_DATE,tmp2.PLAN_START_DATE) between '"+startDate+"' and '"+endDate+"'");
//        }
        if (Strings.isNotEmpty(year)) {
            baseSql.append(" and DATE_FORMAT(ppp.PLAN_START_DATE,'%Y-%m-%d') = DATE_FORMAT('").append(year).append("','%Y-%m-%d')");
        }
//        baseSql.append(" and IF('").append(userId).append("' in (select ad_user_id from ad_role_user where ad_role_id = '0099250247095870406') ,1=1, ");
//        baseSql.append(" pm.id in (select DISTINCT pm_prj_id from pm_dept WHERE STATUS = 'ap' and FIND_IN_SET('").append(userId).append("', USER_IDS )))");
//        baseSql.append(" order by pm.CRT_DT DESC");

        List<Map<String, Object>> prjList = jdbcTemplate.queryForList(baseSql.toString());
//        List<PmPrjExportModel> models = prjList.stream().map(item -> covertData(item)).collect(Collectors.toList());
        List<PmPrjExportModel> models = prjList.stream()
                .map(item -> JSONObject.parseObject(JSONObject.toJSONString(item),PmPrjExportModel.class))
                .collect(Collectors.toList());
        super.setExcelRespProp(response,"项目表");
        EasyExcel.write(response.getOutputStream())
                .head(PmPrjExportModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("项目表")
                .doWrite(models);
    }

    //封装
    private PmPrjExportModel covertData(Map<String,Object> data){
        PmPrjExportModel pmPrjExportModel = new PmPrjExportModel();
        //项目名
        pmPrjExportModel.setName(JdbcMapUtil.getString(data,"NAME"));
        //项目业主
        pmPrjExportModel.setCustomerUnit(JdbcMapUtil.getString(data,"CUSTOMER_UNIT_NAME"));
        //项目类型
        pmPrjExportModel.setProjectType(JdbcMapUtil.getString(data,"PROJECT_TYPE_NAME"));
        //建设地点
        pmPrjExportModel.setBaseLocation(JdbcMapUtil.getString(data,"BASE_LOCATION_NAME"));
        //管理单位
        pmPrjExportModel.setPrjManageMode(JdbcMapUtil.getString(data,"PRJ_MANAGE_MODE_NAME"));
        //概算总投资
        pmPrjExportModel.setPrjTotalInvest(JdbcMapUtil.getString(data,"PRJ_TOTAL_INVEST"));
        //项目状态
        pmPrjExportModel.setProjectPhase(JdbcMapUtil.getString(data,"PROJECT_PHASE_NAME"));
        //进度状态
        pmPrjExportModel.setTransitionPhase(JdbcMapUtil.getString(data,"TRANSITION_PHASE_NAME"));
        //开工/计划开工
        String planStartDate = JdbcMapUtil.getString(data,"PLAN_START_DATE");
        String actualStartDate = JdbcMapUtil.getString(data,"ACTUAL_START_DATE");
        StringBuffer startDate = new StringBuffer();
        startDate.append(planStartDate == null ? "无" : planStartDate);
        startDate.append("/");
        startDate.append(actualStartDate == null ? "无" : actualStartDate);
        pmPrjExportModel.setStartDate(startDate.toString());
        //竣工/计划竣工
        String planComplDate = JdbcMapUtil.getString(data,"PLAN_COMPL_DATE");
        String actualComplDate = JdbcMapUtil.getString(data,"ACTUAL_COMPL_DATE");
        StringBuffer complDate = new StringBuffer();
        complDate.append(planComplDate == null ? "无" : planComplDate);
        complDate.append("/");
        complDate.append(actualComplDate == null ? "无" : actualComplDate);
        pmPrjExportModel.setComplDate(complDate.toString());
        //形象进度/预计形象进度
        String planCurrentProPercent = JdbcMapUtil.getString(data,"PLAN_CURRENT_PRO_PERCENT");
        String actualCurrentProPercent = JdbcMapUtil.getString(data,"ACTUAL_CURRENT_PRO_PERCENT");
        StringBuffer percent = new StringBuffer();
        percent.append(actualCurrentProPercent == null ? "0" : getPercent(actualCurrentProPercent,0));
        percent.append("/");
        percent.append(planCurrentProPercent == null ? "0" : getPercent(planCurrentProPercent,0));
        pmPrjExportModel.setPercent(percent.toString());

        return pmPrjExportModel;
    }

    //小数转百分比
    private String getPercent(String data, int digit) {
        if (Strings.isEmpty(data) || data == "null"){
            return "0";
        }
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(digit);
        return numberFormat.format(Double.valueOf(data));
    }

    //通过用户名获取用户id
    private String getUserId(String username){
        List<Map<String, Object>> userList = jdbcTemplate.queryForList("select id from ad_user where code = ?", username);
        if (CollectionUtils.isEmpty(userList)){
            throw new BaseException("用户不存在！");
        }
        return String.valueOf(userList.get(0).get("id"));
    }



    /**
     * 通过session id获取用户id
     */
    private String getLoginUser(String value){
        String[] strings = value.split("_");
        String phone = strings[1];
        List<Map<String, Object>> userList = jdbcTemplate.queryForList("select id from ad_user where code = ?", phone);
        if (CollectionUtils.isEmpty(userList)){
            return null;
        }
        return JdbcMapUtil.getString(userList.get(0),"id");
    }
}
