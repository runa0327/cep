package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.cisdi.pms.job.excel.model.GuaranteeModel;
import com.google.common.base.Strings;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 保函清单导出
 *
 * @author hgh
 * @date 2022/12/20
 * @apiNote
 */

@RestController
@RequestMapping("guaranteeExport")
public class GuaranteeExportController extends BaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 保函退还申请导出
     *
     * @param guaranteeModel
     * @param response
     */
    @SneakyThrows
    @GetMapping("exportOaReq")
    public void exportOaReq(GuaranteeModel guaranteeModel, HttpServletResponse response) {
            //po_guarantee_letter_return_oa_req     保函退还申请       最下方数据
            //po_guarantee_letter_require_req        新增保函申请       全部数据
            //guaranteeModel.getGuaranteeLetterTypeId(), guaranteeModel.getGuaranteeCostTypeId(), guaranteeModel.getGuaranteeEndDate()
            String guaranteeLetterTypeId = guaranteeModel.getGuaranteeLetterTypeId();
            String guaranteeCostTypeId = guaranteeModel.getGuaranteeCostTypeId();
            Date guaranteeEndDate = guaranteeModel.getGuaranteeEndDate();
            Date guaranteeStartDate = guaranteeModel.getGuaranteeStartDate();
            String projectNameWr = guaranteeModel.getProjectNameWr();
        String param = " where 1=1 ";
            String temp = " ";
            if (!StringUtils.isEmpty(guaranteeLetterTypeId)) {
                temp += " and r.GUARANTEE_LETTER_TYPE_ID= '" + guaranteeLetterTypeId + "'";
            }
            if (!StringUtils.isEmpty(guaranteeCostTypeId)) {
                temp += " and r.GUARANTEE_COST_TYPE_ID= '" + guaranteeCostTypeId + "'";
            }
            if (!ObjectUtils.isEmpty(guaranteeStartDate) && !ObjectUtils.isEmpty(guaranteeEndDate)) {
                temp += " and r.GUARANTEE_END_DATE <= '" + guaranteeEndDate + "' and r.GUARANTEE_START_DATE >= '" + guaranteeStartDate + "'";
            }
            if (!Strings.isNullOrEmpty(projectNameWr)){
                temp += " and IFNULL(r.PM_PRJ_ID,p2.id) = '" + projectNameWr + "'";
            }

            //GUARANTEE_LETTER_TYPE_ID 保函类型   GUARANTEE_COST_TYPE_ID  费用类型    PROJECT_NAME_WR 项目名称    GUARANTEE_END_DATE  保函到期日期
        String sql = "select r.id,r.GUARANTEE_LETTER_TYPE_ID guaranteeLetterTypeId,r.GUARANTEE_COST_TYPE_ID guaranteeCostTypeId,IFNULL(p1.NAME,r" +
                ".PROJECT_NAME_WR) projectNameWr,r.APPLICANT applicant,r.GUARANTEE_MECHANISM guaranteeMechanism,r.GUARANTEE_CODE guaranteeCode,r" +
                ".AMT_WR_ONE amtWrOne,r.GUARANTEE_START_DATE guaranteeStartDate,r.GUARANTEE_END_DATE guaranteeEndDate,r.REMARK_LONG_ONE " +
                "remarkLongOne,r.AUTHOR author \n" +
                "from po_guarantee_letter_return_oa_req r\n" +
                "left join pm_prj p1 on p1.id = r.PM_PRJ_ID\n" +
                "left join pm_prj p2 on p2.name = r.PROJECT_NAME_WR " + param + temp + "  order by r.GUARANTEE_START_DATE ";
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
            List<GuaranteeModel> guaranteeList = new ArrayList<>();
            for (Map<String, Object> map : result) {
                //保函名称
                String letterSql = "select NAME from gr_set_value where ID=?";
                List<Map<String, Object>> letterList = jdbcTemplate.queryForList(letterSql, map.get("guaranteeLetterTypeId"));

                if (letterList.size() > 0 && StringUtils.isNotEmpty(letterList.get(0).get("NAME").toString())){
                    map.replace("guaranteeLetterTypeId",letterList.get(0).get("NAME"));
                }

                //费用类型
                String costTypeSql = "select NAME from gr_set_value where ID=?";
                List<Map<String, Object>> costTypeList = jdbcTemplate.queryForList(costTypeSql, map.get("guaranteeCostTypeId"));

                if (costTypeList.size() > 0 && StringUtils.isNotEmpty(costTypeList.get(0).get("NAME").toString())){
                    map.replace("guaranteeCostTypeId",costTypeList.get(0).get("NAME"));
                }
                GuaranteeModel model = JSONObject.parseObject(JSONObject.toJSONString(map), GuaranteeModel.class);
                guaranteeList.add(model);
            }

            super.setExcelRespProp(response, "保函退还申请");
            EasyExcel.write(response.getOutputStream())
                    .head(GuaranteeModel.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("保函退还申请")
                    .doWrite(guaranteeList);

    }

    /**
     * 新增保函申请导出
     *
     * @param guaranteeModel
     * @param response
     */
    @SneakyThrows
    @GetMapping("/exportReq")
    public void exportReq(GuaranteeModel guaranteeModel , HttpServletResponse response) {
            //po_guarantee_letter_return_oa_req     保函退还申请       最下方数据
            //po_guarantee_letter_require_req        新增保函申请       全部数据
            String guaranteeLetterTypeId = guaranteeModel.getGuaranteeLetterTypeId();
            String projectNameWr = guaranteeModel.getProjectNameWr();
            Date guaranteeEndDate = guaranteeModel.getGuaranteeEndDate();
            Date guaranteeStartDate = guaranteeModel.getGuaranteeStartDate();
            String param = " where 1=1 ";
            String temp = " ";
            if (!StringUtils.isEmpty(guaranteeLetterTypeId)) {
                temp += " and GUARANTEE_LETTER_TYPE_ID= '" + guaranteeLetterTypeId + "'";
            }
            if (!StringUtils.isEmpty(projectNameWr)) {
                temp += " and FIND_IN_SET(" + projectNameWr + ",IFNULL(r.PM_PRJ_IDS,p2.id))";
            }
            if (!ObjectUtils.isEmpty(guaranteeStartDate) && !ObjectUtils.isEmpty(guaranteeEndDate)) {
                temp += " and GUARANTEE_END_DATE <= '" + guaranteeEndDate + "' and GUARANTEE_START_DATE >= '" + guaranteeStartDate + "'";
            }

            //GUARANTEE_LETTER_TYPE_ID 保函类型   GUARANTEE_COST_TYPE_ID  费用类型    PROJECT_NAME_WR 项目名称    GUARANTEE_END_DATE  保函到期日期
            String sql = "select r.GUARANTEE_LETTER_TYPE_ID guaranteeLetterTypeId,r.PM_EXP_TYPE_IDS guaranteeCostTypeId,r.PM_PRJ_IDS,IFNULL(r.PROJECT_NAME_WR,GROUP_CONCAT(p1.name)) projectNameWr," +
                    "r.PROJECT_NAME_WR,r.SUPPLIER supplier,r.GUARANTEE_MECHANISM guaranteeMechanism,r" +
                    ".GUARANTEE_CODE guaranteeCode,r.GUARANTEE_AMT guaranteeAmt,r.GUARANTEE_START_DATE guaranteeStartDate,r.GUARANTEE_END_DATE " +
                    "guaranteeEndDate,r.REMARK_ONE remark,r.BENEFICIARY author \n" +
                    "from po_guarantee_letter_require_req r\n" +
                    "left join pm_prj p1 on FIND_IN_SET(p1.id,r.PM_PRJ_IDS)\n" +
                    "left join pm_prj P2 on p2.NAME = r.PROJECT_NAME_WR " + param + temp + " GROUP BY r.id order by GUARANTEE_START_DATE ";
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
            List<GuaranteeModel> guaranteeList = new ArrayList<>();
            //保函类型字典
            List<Map<String, Object>> guaranteeTypeList = jdbcTemplate.queryForList("select v.NAME,v.ID from gr_set_value v left join gr_set s on s.id = v.gr_set_id " +
                    "where s.code = 'guarantee_type'");
            //费用类型字典
            List<Map<String, Object>> costTypeList = jdbcTemplate.queryForList("select NAME,ID from pm_exp_type");
            for (Map<String, Object> map : result) {
                    //保函名称
//                    String letterSql = "select NAME from gr_set_value where ID=?";
//                    List<Map<String, Object>> letterList = jdbcTemplate.queryForList(letterSql, map.get("guaranteeLetterTypeId"));

//                    if (letterList.size() > 0 && StringUtils.isNotEmpty(letterList.get(0).get("NAME").toString())){
//                        map.replace("guaranteeLetterTypeId",letterList.get(0).get("NAME"));
//                    }
                Optional<Map<String, Object>> guaranteeTypeOptional = guaranteeTypeList.stream()
                        .filter(guaranteeType -> String.valueOf(guaranteeType.get("ID")).equals(String.valueOf(map.get("guaranteeLetterTypeId"))))
                        .findAny();
                if (guaranteeTypeOptional.isPresent()){
                    map.replace("guaranteeLetterTypeId", guaranteeTypeOptional.get().get("NAME"));
                }

                    //费用类型
//                    String costTypeSql = "select NAME from pm_exp_type where FIND_IN_SET(id,?)";
//                    List<Map<String, Object>> costTypeList = jdbcTemplate.queryForList(costTypeSql, map.get("guaranteeCostTypeId"));
//
//                    if (costTypeList.size() > 0 && StringUtils.isNotEmpty(costTypeList.get(0).get("NAME").toString())){
////                        map.replace("guaranteeCostTypeId",costTypeList.get(0).get("NAME"));
//                        String typeNames = costTypeList.stream().map(costType -> String.valueOf(costType.get("NAME"))).collect(Collectors.joining(","));
//                        map.replace("guaranteeCostTypeId",typeNames);
//                    }

                String pmExpTypeIds = String.valueOf(map.get("guaranteeCostTypeId"));
                List<String> pmExpTypeIdList = Arrays.asList(pmExpTypeIds.split(","));

                String costTypes = costTypeList.stream()
                        .filter(costType -> pmExpTypeIdList.contains(String.valueOf(costType.get("ID"))))
                        .map(costType -> String.valueOf(costType.get("NAME"))).collect(Collectors.joining(","));
                if (!Strings.isNullOrEmpty(costTypes)){
                    map.replace("guaranteeCostTypeId",costTypes);
                }
                GuaranteeModel model = JSONObject.parseObject(JSONObject.toJSONString(map), GuaranteeModel.class);
                guaranteeList.add(model);

            }

                super.setExcelRespProp(response, "新增保函申请");
                EasyExcel.write(response.getOutputStream())
                        .head(GuaranteeModel.class)
                        .excelType(ExcelTypeEnum.XLSX)
                        .sheet("新增保函申请")
                        .doWrite(guaranteeList);

    }

}
