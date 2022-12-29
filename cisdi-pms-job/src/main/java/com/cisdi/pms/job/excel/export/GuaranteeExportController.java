package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.excel.model.GuaranteeModel;
import com.cisdi.pms.job.excel.model.InvestStatisticModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/guaranteeExport")
public class GuaranteeExportController extends BaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 保函退还申请导出
     *
     * @param guaranteeModel
     * @param response
     */
    @PostMapping("/exportOaReq")
    public void exportOaReq(@RequestBody GuaranteeModel guaranteeModel, HttpServletResponse response) {
        try {
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
            String sql = "select GUARANTEE_LETTER_TYPE_ID guaranteeLetterTypeId,GUARANTEE_COST_TYPE_ID guaranteeCostTypeId,PROJECT_NAME_WR projectNameWr,APPLICANT applicant,GUARANTEE_MECHANISM guaranteeMechanism,GUARANTEE_CODE guaranteeCode,AMT_WR_ONE amtWrOne,GUARANTEE_START_DATE guaranteeStartDate,GUARANTEE_END_DATE guaranteeEndDate,REMARK_LONG_ONE remarkLongOne,AUTHOR author from po_guarantee_letter_return_oa_req " + param + temp + "  order by GUARANTEE_START_DATE ";
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);

            super.setExcelRespProp(response, "保函退还申请");
            EasyExcel.write(response.getOutputStream())
                    .head(GuaranteeModel.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("保函退还申请")
                    .doWrite(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增保函申请导出
     *
     * @param guaranteeModel
     * @param response
     */
    @PostMapping("/exportReq")
    public void exportReq(@RequestBody GuaranteeModel guaranteeModel , HttpServletResponse response) {
        try {
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
            String sql = "select GUARANTEE_LETTER_TYPE_ID guaranteeLetterTypeId,PM_EXP_TYPE_IDS pmExpTypeIds,PROJECT_NAME_WR projectNameWr,SUPPLIER supplier,GUARANTEE_MECHANISM guaranteeMechanism,GUARANTEE_CODE guaranteeCode,GUARANTEE_AMT guaranteeAmt,GUARANTEE_START_DATE guaranteeStartDate,GUARANTEE_END_DATE guaranteeEndDate,REMARK_ONE remarkOne,BENEFICIARY beneficiary from po_guarantee_letter_require_req " + param + temp + "  order by GUARANTEE_START_DATE ";
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);

            super.setExcelRespProp(response, "新增保函申请");
            EasyExcel.write(response.getOutputStream())
                    .head(InvestStatisticModel.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("新增保函申请")
                    .doWrite(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
