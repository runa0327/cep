package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.cisdi.pms.job.excel.model.GuaranteeModel;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
            String param = " where 1=1 ";
            String temp = " ";
            if (!StringUtils.isEmpty(guaranteeLetterTypeId)) {
                temp += "and GUARANTEE_LETTER_TYPE_ID= '" + guaranteeLetterTypeId + "'";
            }
            if (!StringUtils.isEmpty(guaranteeCostTypeId)) {
                temp += "and GUARANTEE_COST_TYPE_ID= '" + guaranteeCostTypeId + "'";
            }
            if (!ObjectUtils.isEmpty(guaranteeStartDate) && !ObjectUtils.isEmpty(guaranteeEndDate)) {
                temp += "and GUARANTEE_END_DATE <= '" + guaranteeEndDate + "' and GUARANTEE_START_DATE >= '" + guaranteeStartDate + "'";
            }

            //GUARANTEE_LETTER_TYPE_ID 保函类型   GUARANTEE_COST_TYPE_ID  费用类型    PROJECT_NAME_WR 项目名称    GUARANTEE_END_DATE  保函到期日期
            String sql = "select GUARANTEE_LETTER_TYPE_ID guaranteeLetterTypeId,GUARANTEE_COST_TYPE_ID guaranteeCostTypeId,PROJECT_NAME_WR projectNameWr,APPLICANT supplier,GUARANTEE_MECHANISM guaranteeMechanism,GUARANTEE_CODE guaranteeCode,AMT_WR_ONE guaranteeAmt,GUARANTEE_START_DATE guaranteeStartDate,GUARANTEE_END_DATE guaranteeEndDate,REMARK_LONG_ONE remark,AUTHOR author from po_guarantee_letter_return_oa_req " + param + temp + "  order by GUARANTEE_START_DATE ";
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
            String projectNameWr = guaranteeModel.getGuaranteeCostTypeId();
            Date guaranteeEndDate = guaranteeModel.getGuaranteeEndDate();
            Date guaranteeStartDate = guaranteeModel.getGuaranteeStartDate();
            String param = " where 1=1 ";
            String temp = " ";
            if (!StringUtils.isEmpty(guaranteeLetterTypeId)) {
                temp += "and GUARANTEE_LETTER_TYPE_ID= '" + guaranteeLetterTypeId + "'";
            }
            if (!StringUtils.isEmpty(projectNameWr)) {
                temp += "and PROJECT_NAME_WR= '" + projectNameWr + "'";
            }
            if (!ObjectUtils.isEmpty(guaranteeStartDate) && !ObjectUtils.isEmpty(guaranteeEndDate)) {
                temp += "and GUARANTEE_END_DATE <= '" + guaranteeEndDate + "' and GUARANTEE_START_DATE >= '" + guaranteeStartDate + "'";
            }

            //GUARANTEE_LETTER_TYPE_ID 保函类型   GUARANTEE_COST_TYPE_ID  费用类型    PROJECT_NAME_WR 项目名称    GUARANTEE_END_DATE  保函到期日期
            String sql = "select GUARANTEE_LETTER_TYPE_ID guaranteeLetterTypeId,PM_EXP_TYPE_IDS guaranteeCostTypeId,PROJECT_NAME_WR projectNameWr,SUPPLIER supplier,GUARANTEE_MECHANISM guaranteeMechanism,GUARANTEE_CODE guaranteeCode,GUARANTEE_AMT guaranteeAmt,GUARANTEE_START_DATE guaranteeStartDate,GUARANTEE_END_DATE guaranteeEndDate,REMARK_ONE remark,BENEFICIARY author from po_guarantee_letter_require_req " + param + temp + "  order by GUARANTEE_START_DATE ";
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
                    String costTypeSql = "select NAME from pm_exp_type where ID=?";
                    List<Map<String, Object>> costTypeList = jdbcTemplate.queryForList(costTypeSql, map.get("pmExpTypeIds"));

                    if (costTypeList.size() > 0 && StringUtils.isNotEmpty(costTypeList.get(0).get("NAME").toString())){
                        map.replace("pmExpTypeIds",costTypeList.get(0).get("NAME"));
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
