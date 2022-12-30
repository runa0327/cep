package com.cisdi.ext.guarantee;

import com.cisdi.ext.guarantee.domin.*;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 保函清单台账
 *
 * @author hgh
 * @date 2022/12/27
 * @apiNote
 */

@Slf4j
public class GuaranteeExt {

    /**
     * po_guarantee_letter_return_oa_req   最下方数据
     * 分页查询保函清单台账--保函退还申请(OA)分页查询
     */
    public void pageGuaranteeReturnOa() throws ParseException {

        //获取jdbc
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        //获取参数
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();

        //参数校验
        if (StringUtils.isEmpty(map.get("pageIndex")) || StringUtils.isEmpty(map.get("pageSize"))) {
            return;
        }

        //当前页
        int pageIndex = Integer.parseInt(map.get("pageIndex").toString());
        //每页多少条数据
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        String limit = " limit " + (pageIndex - 1) * pageSize + "," + pageSize;
        String guaranteeLetterTypeId = null;
        String guaranteeCostTypeId = null;
        String guaranteeEndDate = null;
        String guaranteeStartDate = null;
        if (map.containsKey("guaranteeLetterTypeId")) {
            guaranteeLetterTypeId = map.get("guaranteeLetterTypeId").toString();
        }
        if (map.containsKey("guaranteeCostTypeId")) {
            guaranteeCostTypeId = map.get("guaranteeCostTypeId").toString();
        }
        if (map.containsKey("guaranteeEndDate")) {
            guaranteeEndDate = map.get("guaranteeEndDate").toString();
        }
        if (map.containsKey("guaranteeStartDate")) {
            guaranteeStartDate = map.get("guaranteeStartDate").toString();
        }
        String param = " where 1=1 ";
        String temp = " ";
        if (!StringUtils.isEmpty(guaranteeLetterTypeId)) {
            temp += "and GUARANTEE_LETTER_TYPE_ID= '" + guaranteeLetterTypeId + "'";
        } else if (!StringUtils.isEmpty(guaranteeCostTypeId)) {
            temp += "and GUARANTEE_COST_TYPE_ID= '" + guaranteeCostTypeId + "'";
        } else if (!StringUtils.isEmpty(guaranteeEndDate) && !StringUtils.isEmpty(guaranteeStartDate)) {
            temp += "and GUARANTEE_END_DATE <= '" + guaranteeEndDate + "' and GUARANTEE_START_DATE >= '" + guaranteeStartDate + "'";
        }

        String sql = "select GUARANTEE_LETTER_TYPE_ID guaranteeLetterTypeId,GUARANTEE_COST_TYPE_ID guaranteeCostTypeId,PROJECT_NAME_WR projectNameWr,APPLICANT applicant,GUARANTEE_MECHANISM guaranteeMechanism,GUARANTEE_CODE guaranteeCode,AMT_WR_ONE amtWrOne,GUARANTEE_START_DATE guaranteeStartDate,GUARANTEE_END_DATE guaranteeEndDate,REMARK_LONG_ONE remarkLongOne,AUTHOR author from po_guarantee_letter_return_oa_req " + param + temp + "  order by GUARANTEE_START_DATE " + limit;
        String totalSql = "select GUARANTEE_LETTER_TYPE_ID guaranteeLetterTypeId,GUARANTEE_COST_TYPE_ID guaranteeCostTypeId,PROJECT_NAME_WR projectNameWr,APPLICANT applicant,GUARANTEE_MECHANISM guaranteeMechanism,GUARANTEE_CODE guaranteeCode,AMT_WR_ONE amtWrOne,GUARANTEE_START_DATE guaranteeStartDate,GUARANTEE_END_DATE guaranteeEndDate,REMARK_LONG_ONE remarkLongOne,AUTHOR author from po_guarantee_letter_return_oa_req " + param + temp + "  order by GUARANTEE_START_DATE ";
        List<Map<String, Object>> dataList = myJdbcTemplate.queryForList(sql);
        List<Map<String, Object>> total = myJdbcTemplate.queryForList(totalSql);
        List<PoGuaranteeLetterReturnOaReq> list = new ArrayList<>();
        Guarantee guarantee = new Guarantee();
        for (Map<String, Object> stringObjectMap : dataList) {

            //获取保函名称
            String nameSql = "select NAME from gr_set_value where ID=?";
            List<Map<String, Object>> nameMap = myJdbcTemplate.queryForList(nameSql, stringObjectMap.get("guaranteeLetterTypeId"));

            //获取费用类型
            String typeSql = "select NAME from gr_set_value where ID=?";
            List<Map<String, Object>> typeMap = myJdbcTemplate.queryForList(typeSql, stringObjectMap.get("guaranteeCostTypeId"));

            if (nameMap.size() > 0 && nameMap.get(0).containsKey("NAME")) {
                stringObjectMap.replace("guaranteeLetterTypeId", nameMap.get(0).get("NAME"));
            }
            if (typeMap.size() > 0 && typeMap.get(0).containsKey("NAME")) {
                stringObjectMap.replace("guaranteeCostTypeId", typeMap.get(0).get("NAME"));
            }
            PoGuaranteeLetterReturnOaReq oaReq = JsonUtil.convertMapToObject(stringObjectMap, PoGuaranteeLetterReturnOaReq.class);
            list.add(oaReq);
        }

        guarantee.setReqs(list);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(guarantee), Map.class);
        outputMap.put("total", total.size());
        ExtJarHelper.returnValue.set(outputMap);

    }


    /**
     * po_guarantee_letter_require_req   全部数据
     * 分页查询保函清单台账--新增保函申请
     */
    public void pageGuaranteeRequire() throws ParseException {

        //获取jdbc
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        //获取参数
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();

        //参数校验
        if (StringUtils.isEmpty(map.get("pageIndex")) || StringUtils.isEmpty(map.get("pageSize"))) {
            return;
        }

        //当前页
        int pageIndex = Integer.parseInt(map.get("pageIndex").toString());
        //每页多少条数据
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        String limit = " limit " + (pageIndex - 1) * pageSize + "," + pageSize;


        String guaranteeLetterTypeId = null;
        String projectNameWr = null;
        String guaranteeEndDate = null;
        String guaranteeStartDate = null;
        if (map.containsKey("guaranteeLetterTypeId")) {
            guaranteeLetterTypeId = map.get("guaranteeLetterTypeId").toString();
        }
        if (map.containsKey("projectNameWr")) {
            projectNameWr = map.get("projectNameWr").toString();
        }
        if (map.containsKey("guaranteeLetterTypeId")) {
            guaranteeEndDate = map.get("guaranteeEndDate").toString();
        }
        if (map.containsKey("guaranteeStartDate")) {
            guaranteeStartDate = map.get("guaranteeStartDate").toString();
        }
        String param = " where 1=1 ";
        String temp = " ";
        if (!StringUtils.isEmpty(guaranteeLetterTypeId)) {
            temp += " and GUARANTEE_LETTER_TYPE_ID= '" + guaranteeLetterTypeId + "'";
        } else if (!StringUtils.isEmpty(projectNameWr)) {
            temp += " and PROJECT_NAME_WR= '" + projectNameWr + "'";
        } else if (!StringUtils.isEmpty(guaranteeEndDate) && !StringUtils.isEmpty(guaranteeStartDate)) {
            temp += "and GUARANTEE_END_DATE <= '" + guaranteeEndDate + "' and GUARANTEE_START_DATE >= '" + guaranteeStartDate + "'";
        }
        String sql = "select GUARANTEE_LETTER_TYPE_ID guaranteeLetterTypeId,PM_EXP_TYPE_IDS pmExpTypeIds,PROJECT_NAME_WR projectNameWr,SUPPLIER supplier,GUARANTEE_MECHANISM guaranteeMechanism,GUARANTEE_CODE guaranteeCode,GUARANTEE_AMT guaranteeAmt,GUARANTEE_START_DATE guaranteeStartDate,GUARANTEE_END_DATE guaranteeEndDate,REMARK_ONE remarkOne,BENEFICIARY beneficiary from po_guarantee_letter_require_req " + param + temp + "  order by GUARANTEE_START_DATE " + limit;
        String totalSql = "select GUARANTEE_LETTER_TYPE_ID guaranteeLetterTypeId,PM_EXP_TYPE_IDS pmExpTypeIds,PROJECT_NAME_WR projectNameWr,SUPPLIER supplier,GUARANTEE_MECHANISM guaranteeMechanism,GUARANTEE_CODE guaranteeCode,GUARANTEE_AMT guaranteeAmt,GUARANTEE_START_DATE guaranteeStartDate,GUARANTEE_END_DATE guaranteeEndDate,REMARK_ONE remarkOne,BENEFICIARY beneficiary from po_guarantee_letter_require_req " + param + temp + "  order by GUARANTEE_START_DATE ";
        List<Map<String, Object>> dataList = myJdbcTemplate.queryForList(sql);
        List<Map<String, Object>> total = myJdbcTemplate.queryForList(totalSql);
        List<PoGuaranteeLetterRequireReq> list = new ArrayList<>();
        GuaranteeModel guaranteeModel = new GuaranteeModel();
        for (Map<String, Object> stringObjectMap : dataList) {
            //获取保函名称
            String nameSql = "select NAME from gr_set_value where ID=?";
            List<Map<String, Object>> nameMap = myJdbcTemplate.queryForList(nameSql, stringObjectMap.get("guaranteeLetterTypeId"));

            //获取费用类型
            String typeSql = "select NAME from pm_exp_type where ID=?";
            List<Map<String, Object>> typeMap = myJdbcTemplate.queryForList(typeSql, stringObjectMap.get("pmExpTypeIds"));

            if (nameMap.size() > 0 && nameMap.get(0).containsKey("NAME")) {
                stringObjectMap.replace("guaranteeLetterTypeId", nameMap.get(0).get("NAME"));
            }
            if (typeMap.size() > 0 && typeMap.get(0).containsKey("NAME")) {
                stringObjectMap.replace("pmExpTypeIds", typeMap.get(0).get("NAME"));
            }


            list.add(JsonUtil.convertMapToObject(stringObjectMap, PoGuaranteeLetterRequireReq.class));
        }

        guaranteeModel.setReqs(list);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(guaranteeModel), Map.class);
        outputMap.put("total", total.size());
        ExtJarHelper.returnValue.set(outputMap);

    }


    /**
     * 获取费用类型
     */
    public void queryGuaranteeCostTypeId() {

        //获取jdbc
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        String querySql = "SELECT ID,NAME from gr_set_value t WHERE  exists(select 1 from gr_set s where t.gr_set_id=s.id and s.code='guarantee_cost_type')";
        List<Map<String, Object>> maps = myJdbcTemplate.queryForList(querySql);

        Temp temp = new Temp();
        temp.setData(maps);

        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(temp), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

}
