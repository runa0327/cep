package com.cisdi.ext.guarantee;

import com.cisdi.ext.guarantee.domin.*;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

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
        String projectNameWr = "";
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
        if (map.containsKey("projectNameWr")) {
            projectNameWr = map.get("projectNameWr").toString();
        }
        String param = " where 1=1 AND r.STATUS in ('ap','APING') ";
        String temp = " ";
        if (!StringUtils.isEmpty(guaranteeLetterTypeId)) {
            temp += " and r.GUARANTEE_LETTER_TYPE_ID= '" + guaranteeLetterTypeId + "'";
        }
        if (!StringUtils.isEmpty(guaranteeCostTypeId)) {
            temp += " and r.GUARANTEE_COST_TYPE_ID= '" + guaranteeCostTypeId + "'";
        }
        if (!StringUtils.isEmpty(guaranteeEndDate) && !StringUtils.isEmpty(guaranteeStartDate)) {
            temp += " and r.GUARANTEE_END_DATE <= '" + guaranteeEndDate + "' and r.GUARANTEE_START_DATE >= '" + guaranteeStartDate + "'";
        }
        if (!Strings.isNullOrEmpty(projectNameWr)){
            temp += " and IFNULL(r.PM_PRJ_ID,p2.id) = '" + projectNameWr + "'";
        }

        String sql = "select r.id,r.GUARANTEE_LETTER_TYPE_ID guaranteeLetterTypeId,r.GUARANTEE_COST_TYPE_ID guaranteeCostTypeId,IFNULL(p1.NAME,r" +
                ".PROJECT_NAME_WR) projectNameWr,r.APPLICANT applicant,r.GUARANTEE_MECHANISM guaranteeMechanism,r.GUARANTEE_CODE guaranteeCode,r" +
                ".AMT_WR_ONE amtWrOne,r.GUARANTEE_START_DATE guaranteeStartDate,r.GUARANTEE_END_DATE guaranteeEndDate,r.REMARK_LONG_ONE " +
                "remarkLongOne,r.AUTHOR author \n" +
                "from po_guarantee_letter_return_oa_req r\n" +
                "left join pm_prj p1 on p1.id = r.PM_PRJ_ID\n" +
                "left join pm_prj p2 on p2.name = r.PROJECT_NAME_WR " + param + temp + "  order by r.GUARANTEE_START_DATE " + limit;
        String totalSql = "select r.id,r.GUARANTEE_LETTER_TYPE_ID guaranteeLetterTypeId,r.GUARANTEE_COST_TYPE_ID guaranteeCostTypeId,IFNULL(p1" +
                ".NAME,r.PROJECT_NAME_WR) projectNameWr,r.APPLICANT applicant,r.GUARANTEE_MECHANISM guaranteeMechanism,r.GUARANTEE_CODE " +
                "guaranteeCode,r.AMT_WR_ONE amtWrOne,r.GUARANTEE_START_DATE guaranteeStartDate,r.GUARANTEE_END_DATE guaranteeEndDate,r" +
                ".REMARK_LONG_ONE remarkLongOne,r.AUTHOR author \n" +
                "from po_guarantee_letter_return_oa_req r\n" +
                "left join pm_prj p1 on p1.id = r.PM_PRJ_ID\n" +
                "left join pm_prj p2 on p2.name = r.PROJECT_NAME_WR " + param + temp + "  order by r.GUARANTEE_START_DATE ";
        List<Map<String, Object>> dataList = myJdbcTemplate.queryForList(sql);
        List<Map<String, Object>> total = myJdbcTemplate.queryForList(totalSql);
        List<PoGuaranteeLetterReturnOaReq> list = new ArrayList<>();
        Guarantee guarantee = new Guarantee();
        for (Map<String, Object> stringObjectMap : dataList) {

            //获取保函名称
            String nameSql = "select NAME from gr_set_value where ID=?";
            List<Map<String, Object>> nameMap = myJdbcTemplate.queryForList(nameSql, stringObjectMap.get("guaranteeLetterTypeId"));

            //获取费用类型
            String typeSql = "select NAME from gr_set_value where FIND_IN_SET(ID,?)";
            List<Map<String, Object>> typeMap = myJdbcTemplate.queryForList(typeSql, stringObjectMap.get("guaranteeCostTypeId"));

            if (nameMap.size() > 0 && nameMap.get(0).containsKey("NAME")) {
                stringObjectMap.replace("guaranteeLetterTypeId", nameMap.get(0).get("NAME"));
            }
            if (typeMap.size() > 0 && typeMap.get(0).containsKey("NAME")) {
//                stringObjectMap.replace("guaranteeCostTypeId", typeMap.get(0).get("NAME"));
                String typeNames = typeMap.stream().map(costType -> String.valueOf(costType.get("NAME"))).collect(Collectors.joining(","));
                stringObjectMap.replace("guaranteeCostTypeId",typeNames);
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
        String param = " where 1=1 and r.status in ('ap','APING') ";
        String temp = " ";
        if (!StringUtils.isEmpty(guaranteeLetterTypeId)) {
            temp += " and r.GUARANTEE_LETTER_TYPE_ID= '" + guaranteeLetterTypeId + "'";
        }
        if (!StringUtils.isEmpty(projectNameWr)) {
            temp += " and FIND_IN_SET('" + projectNameWr + "',IFNULL(r.PM_PRJ_IDS,p2.id))";
        }
        if (!StringUtils.isEmpty(guaranteeEndDate) && !StringUtils.isEmpty(guaranteeStartDate)) {
            temp += " and r.GUARANTEE_END_DATE <= '" + guaranteeEndDate + "' and r.GUARANTEE_START_DATE >= '" + guaranteeStartDate + "'";
        }
        String sql = "select r.id,GUARANTEE_LETTER_TYPE_ID guaranteeLetterTypeId,r.PM_EXP_TYPE_IDS pmExpTypeIds,r.PROJECT_NAME_WR projectNameWr,r" +
                ".SUPPLIER supplier,r.GUARANTEE_MECHANISM guaranteeMechanism,r.GUARANTEE_CODE guaranteeCode,r.GUARANTEE_AMT guaranteeAmt,r" +
                ".GUARANTEE_START_DATE guaranteeStartDate,r.GUARANTEE_END_DATE guaranteeEndDate,r.REMARK_ONE remarkOne,r.BENEFICIARY beneficiary \n" +
                "from po_guarantee_letter_require_req r\n" +
                "left join pm_prj p1 on FIND_IN_SET(p1.id,r.PM_PRJ_IDS)\n" +
                "left join pm_prj P2 on p2.NAME = r.PROJECT_NAME_WR " + param + temp + "  order by r.GUARANTEE_START_DATE " + limit;
        String totalSql = "select r.id,GUARANTEE_LETTER_TYPE_ID guaranteeLetterTypeId,r.PM_EXP_TYPE_IDS pmExpTypeIds,r.PROJECT_NAME_WR " +
                "projectNameWr,r.SUPPLIER supplier,r.GUARANTEE_MECHANISM guaranteeMechanism,r.GUARANTEE_CODE guaranteeCode,r.GUARANTEE_AMT " +
                "guaranteeAmt,r.GUARANTEE_START_DATE guaranteeStartDate,r.GUARANTEE_END_DATE guaranteeEndDate,r.REMARK_ONE remarkOne,r.BENEFICIARY " +
                "beneficiary \n" +
                "from po_guarantee_letter_require_req r\n" +
                "left join pm_prj p1 on FIND_IN_SET(p1.id,r.PM_PRJ_IDS)\n" +
                "left join pm_prj P2 on p2.NAME = r.PROJECT_NAME_WR " + param + temp + "  order by r.GUARANTEE_START_DATE ";
        List<Map<String, Object>> dataList = myJdbcTemplate.queryForList(sql);
        List<Map<String, Object>> total = myJdbcTemplate.queryForList(totalSql);
        List<PoGuaranteeLetterRequireReq> list = new ArrayList<>();
        GuaranteeModel guaranteeModel = new GuaranteeModel();
        //保函类型字典
        List<Map<String, Object>> guaranteeTypeList = myJdbcTemplate.queryForList("select v.NAME,v.ID from gr_set_value v left join gr_set s on s.id = v.gr_set_id " +
                "where s.code = 'guarantee_type'");
        //费用类型字典
        List<Map<String, Object>> costTypeList = myJdbcTemplate.queryForList("select NAME,ID from pm_exp_type");
        for (Map<String, Object> stringObjectMap : dataList) {
            //获取保函名称
//            String nameSql = "select NAME from gr_set_value where ID=?";
//            List<Map<String, Object>> nameMap = myJdbcTemplate.queryForList(nameSql, stringObjectMap.get("guaranteeLetterTypeId"));
            Optional<Map<String, Object>> guaranteeTypeOptional = guaranteeTypeList.stream()
                    .filter(guaranteeType -> String.valueOf(guaranteeType.get("ID")).equals(String.valueOf(stringObjectMap.get("guaranteeLetterTypeId"))))
                    .findAny();


            //获取费用类型
//            String typeSql = "select NAME from pm_exp_type where FIND_IN_SET(id,?)";
//            List<Map<String, Object>> typeMap = myJdbcTemplate.queryForList(typeSql, stringObjectMap.get("pmExpTypeIds"));
            String pmExpTypeIds = String.valueOf(stringObjectMap.get("pmExpTypeIds"));
            List<String> pmExpTypeIdList = Arrays.asList(pmExpTypeIds.split(","));

            String costTypes = costTypeList.stream()
                    .filter(costType -> pmExpTypeIdList.contains(String.valueOf(costType.get("ID"))))
                    .map(costType -> String.valueOf(costType.get("NAME"))).collect(Collectors.joining(","));

//            if (nameMap.size() > 0 && nameMap.get(0).containsKey("NAME")) {
//                stringObjectMap.replace("guaranteeLetterTypeId", nameMap.get(0).get("NAME"));
//            }
            if (guaranteeTypeOptional.isPresent()){
                stringObjectMap.replace("guaranteeLetterTypeId", guaranteeTypeOptional.get().get("NAME"));
            }
            if (!Strings.isNullOrEmpty(costTypes)){
                stringObjectMap.replace("pmExpTypeIds",costTypes);
            }
//            if (typeMap.size() > 0 && typeMap.get(0).containsKey("NAME")) {
////                stringObjectMap.replace("pmExpTypeIds", typeMap.get(0).get("NAME"));
//                String typeNames = typeMap.stream().map(costType -> String.valueOf(costType.get("NAME"))).collect(Collectors.joining(","));
//                map.replace("pmExpTypeIds",typeNames);
//            }
//            if (costTypeOptional.isPresent()) {
//                String typeNames = typeMap.stream().map(costType -> String.valueOf(costType.get("NAME"))).collect(Collectors.joining(","));
//                map.replace("pmExpTypeIds",typeNames);
//            }


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
