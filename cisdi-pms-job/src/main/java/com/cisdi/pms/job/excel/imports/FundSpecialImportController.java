package com.cisdi.pms.job.excel.imports;

import com.cisdi.pms.job.excel.model.FundSpecialModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.Util;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundSpecialImportController
 * @package com.cisdi.pms.job.excel.imports
 * @description 专项资金
 * @date 2022/10/31
 */
@RestController
@RequestMapping("fundSpecial")
public class FundSpecialImportController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SneakyThrows(IOException.class)
    @PostMapping(value = "/import")
    public Map<String, Object> importData(MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        List<String> res = new ArrayList<>();
        List<FundSpecialModel> dataList = EasyExcelUtil.read(file.getInputStream(), FundSpecialModel.class);
        List<Map<String, Object>> proList = jdbcTemplate.queryForList("select * from pm_prj where status='AP'");
        List<Map<String, Object>> unitList = jdbcTemplate.queryForList("select * from RECEIVING_BANK where status='AP'");
        for (FundSpecialModel fundSpecialModel : dataList) {
            Optional<Map<String, Object>> optional = proList.stream().filter(p -> fundSpecialModel.getProjectName().equals(String.valueOf(p.get("NAME")))).findAny();
            if (optional.isPresent()) {
                //付款单位
                String unit = null;
                Optional<Map<String, Object>> unitOptional = unitList.stream().filter(m -> Objects.equals(fundSpecialModel.getPayUnit(), m.get("NAME"))).findAny();
                if (unitOptional.isPresent()) {
                    unit = String.valueOf(unitOptional.get().get("ID"));
                }
                //付款银行
                String bank = null;
                Optional<Map<String, Object>> bankOptional = unitList.stream().filter(m -> Objects.equals(fundSpecialModel.getPayBank(), m.get("NAME"))).findAny();
                if (bankOptional.isPresent()) {
                    bank = String.valueOf(bankOptional.get().get("ID"));
                }
                //付款账户
                String account=null;
                Optional<Map<String, Object>> accountOptional = unitList.stream().filter(m -> Objects.equals(fundSpecialModel.getPayBank(), m.get("NAME"))).findAny();
                if (accountOptional.isPresent()) {
                    account = String.valueOf(accountOptional.get().get("ID"));
                }

                String fundImplementationId = null;
                List<Map<String, Object>> detilList = jdbcTemplate.queryForList("select fid.* from FUND_IMPLEMENTATION_DETAIL fid \n" +
                        "left join FUND_IMPLEMENTATION fi on fid.FUND_IMPLEMENTATION_ID = fi.id\n" +
                        "where fid.PM_PRJ_ID =? and fi.FUND_SOURCE_TEXT = ?", optional.get().get("ID"), fundSpecialModel.getSourceName());
                if (!CollectionUtils.isEmpty(detilList)) {
                    fundImplementationId = String.valueOf(detilList.get(0).get("ID"));
                }

                String statusId = null;
                if(fundSpecialModel.getStatus() !=null){
                    List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from ad_status where `name` =?", fundSpecialModel.getStatus());
                    if(!CollectionUtils.isEmpty(list)){
                        statusId = String.valueOf(list.get(0).get("ID"));
                    }
                }

                String id = Util.insertData(jdbcTemplate, "FUND_SPECIAL");
                //支付码
                String payCode = this.getPayCode(fundSpecialModel.getPayDate());
                jdbcTemplate.update("update FUND_SPECIAL set PM_PRJ_ID=?,APPROVED_AMOUNT=?,CUM_REACH_AMT=?,CUM_PAY_AMT=?,NOT_REACH_AMT=?,CUM_BUILD_REACH_AMT=?," +
                                "CUM_ACQ_REACH_AMT=?,SUR_REACH_AMT=?,FUND_REACH_CATEGORY=?,NPER=?,COST_NAME=?,PAY_UNIT=?,RECEIPT_BANK=?,RECEIPT_ACCOUNT=?,PAY_DATE=?," +
                                "PAYABLE_AMT=?,PAID_AMT=?,UNPAD_AMT=?,PAYEE=?,GUARANTEE_STATES=?,APPROVAL_STATUS=?,FUND_IMPLEMENTATION_V_ID=?,FUND_PAY_CODE=? where ID=?",
                        optional.get().get("ID"), fundSpecialModel.getApprovedAmount(), fundSpecialModel.getLjdwAmt(), fundSpecialModel.getPayAmt(), fundSpecialModel.getWdwAmt(),
                        fundSpecialModel.getJsAmt(), fundSpecialModel.getZcAmt(), fundSpecialModel.getSyAmt(), null, fundSpecialModel.getPeriods(), fundSpecialModel.getFeeName(),
                        unit, bank, account, fundSpecialModel.getPayDate(), fundSpecialModel.getYfAmt(),
                        fundSpecialModel.getHasPayAmt(), 0, fundSpecialModel.getSkUnit(), fundSpecialModel.getBhqk(), statusId, fundImplementationId, payCode, id);

            } else {
                res.add(fundSpecialModel.getProjectName());
            }
        }
        if (CollectionUtils.isEmpty(res)) {
            result.put("code", 200);
            result.put("message", "导入成功！");
            return result;
        } else {
            result.put("code", 500);
            result.put("message", "项目名称为:" + String.join(",", res) + "不存在，未导入！");
            return result;
        }
    }

    private String getPayCode(Date payDate){
        //支付明细码(FUND_PAY_CODE),文本（短）
        //查询相同日期专项资金支付条数
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(payDate);
        calendar.add(Calendar.DATE,1);
        Date endDate = calendar.getTime();
        Map<String, Object> countToday = jdbcTemplate.queryForMap("select count(*) countToday from fund_special where PAY_DATE >= ? and PAY_DATE < ?",payDate,endDate);
        DecimalFormat df = new DecimalFormat("0000");
        String suffixNum = df.format(JdbcMapUtil.getInt(countToday, "countToday") + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fundPayCode = "zf" + sdf.format(payDate) + suffixNum;
        return fundPayCode;
    }
}
