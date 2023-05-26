package com.cisdi.pms.job.excel.export;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.pms.job.excel.model.PlanOperationModel;
import com.cisdi.pms.job.excel.model.request.OperationSelectReq;
import com.cisdi.pms.job.utils.ExportUtil;
import com.google.common.base.Strings;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 计划运营库导出
 * @author dlt
 * @date 2023/5/25 周四
 */
@RestController
@RequestMapping("/planOperation")
public class PlanOperationLibraryController {

    @Autowired
    private NamedParameterJdbcTemplate myNamedParameterJdbcTemplate;

    @SneakyThrows
    @GetMapping("/export")
    public void planOperationExport(OperationSelectReq selectReq,HttpServletResponse response){
        Map sqlParams = JSONObject.parseObject(JSONObject.toJSONString(selectReq), Map.class);

        StringBuffer sqlSb = new StringBuffer();
        sqlSb.append("select po.id operationId,po.PM_PRJ_ID prjId,pp.name prjName,po.KEY_PROJECT_TYPE_ID keyProjectTypeId,v1.name " +
                "keyProjectTypeName,pp.ESTIMATED_TOTAL_INVEST totalInvest,pp.PROJECT_TYPE_ID prjTypeId,v2.name prjTypeName,pp.BASE_LOCATION_ID " +
                "locationId,v3.name locationName,pp.PRJ_MANAGE_MODE_ID prjManageModeId,v4.name prjManageModeName,rtemp.AD_USER_ID earlyUserId,rtemp" +
                ".name earlyUserName,pp.PROJECT_PHASE_ID prjPhaseId,v5.name prjPhaseName,po.PRJ_TAG_IDS tagIds,ftemp.AdUserIds " +
                "from PLAN_OPERATION po\n" +
                "left join pm_prj pp on pp.id = po.PM_PRJ_ID\n" +
                "left join gr_set_value v1 on v1.id = po.KEY_PROJECT_TYPE_ID\n" +
                "left join gr_set_value v2 on v2.id = pp.PROJECT_TYPE_ID\n" +
                "left join gr_set_value v3 on v3.id = pp.BASE_LOCATION_ID\n" +
                "left join gr_set_value v4 on v4.id = pp.PRJ_MANAGE_MODE_ID\n" +
                "left join (select ro.PM_PRJ_ID,ro.AD_USER_ID,u.name from PM_ROSTER ro left join ad_user u on u.id = ro.AD_USER_ID where " +
                "POST_INFO_ID = '1633731474912055296') rtemp on rtemp.PM_PRJ_ID = po.PM_PRJ_ID\n" +
                "left join gr_set_value v5 on v5.id = pp.PROJECT_PHASE_ID\n" +
                "left join (select PM_PRJ_ID,GROUP_CONCAT(AD_USER_ID) AdUserIds from prj_follower group by PM_PRJ_ID) ftemp on ftemp.PM_PRJ_ID = po" +
                ".PM_PRJ_ID where 1=1");
        if (!CollectionUtils.isEmpty(selectReq.getPrjIds())){
            sqlSb.append(" and po.PM_PRJ_ID in (:prjIds)");
        }
        if (!Strings.isNullOrEmpty(selectReq.getTotalInvestStart())){
            sqlSb.append(" and pp.ESTIMATED_TOTAL_INVEST >= :totalInvestStart");
        }
        if (!Strings.isNullOrEmpty(selectReq.getTotalInvestEnd())){
            sqlSb.append(" and pp.ESTIMATED_TOTAL_INVEST <= :totalInvestEnd");
        }
        if (!CollectionUtils.isEmpty(selectReq.getLocationIds())){
            sqlSb.append(" and pp.BASE_LOCATION_ID in (:locationIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.getEarlyUserIds())){
            sqlSb.append(" and rtemp.AD_USER_ID in (:earlyUserIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.getKeyProjectTypeIds())){
            sqlSb.append(" and po.KEY_PROJECT_TYPE_ID in (:keyProjectTypeIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.getPrjManageModeIds())){
            sqlSb.append(" and pp.PRJ_MANAGE_MODE_ID in (:prjManageModeIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.getPrjPhaseIds())){
            sqlSb.append(" and pp.PROJECT_PHASE_ID in (:prjPhaseIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.getPrjTypeIds())){
            sqlSb.append(" and pp.PROJECT_TYPE_ID in (:prjTypeIds)");
        }
        if (!CollectionUtils.isEmpty(selectReq.getPrjTagIds())){
            sqlSb.append(" and (");
            for (int i = 0;i < selectReq.getPrjTagIds().size();i++) {
                if (i > 0){
                    sqlSb.append(" or ");
                }
                sqlSb.append(" FIND_IN_SET(:prjTagId" + i + ",po.PRJ_TAG_IDS" + ")");
                sqlParams.put("prjTagId" + i,selectReq.getPrjTagIds().get(i));
            }
            sqlSb.append(" )");
        }

        List<Map<String, Object>> originList = myNamedParameterJdbcTemplate.queryForList(sqlSb.toString(), sqlParams);
        List<PlanOperationModel> planOperationModels = JSONObject.parseArray(JSONObject.toJSONString(originList), PlanOperationModel.class);
        ExportUtil.exportExcel(response,"计划运营库",planOperationModels,PlanOperationModel.class);
    }
}
