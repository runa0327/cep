package com.cisdi.pms.job.excel.imports;

import com.cisdi.pms.job.excel.model.FundSpecialModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.Util;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public String importData(MultipartFile file) {
        List<String> res = new ArrayList<>();
        List<FundSpecialModel> dataList = EasyExcelUtil.read(file.getInputStream(), FundSpecialModel.class);
        List<Map<String, Object>> proList = jdbcTemplate.queryForList("select * from pm_prj where status='AP'");
        for (FundSpecialModel fundSpecialModel : dataList) {
            Optional<Map<String, Object>> optional = proList.stream().filter(p -> fundSpecialModel.getProjectName().equals(String.valueOf(p.get("NAME")))).findAny();
            if (optional.isPresent()) {
                String id = Util.insertData(jdbcTemplate, "FUND_SPECIAL");
                jdbcTemplate.update("update FUND_SPECIAL set PM_PRJ_ID=?,APPROVED_AMOUNT=?,CUM_REACH_AMT=?,CUM_PAY_AMT=?,NOT_REACH_AMT=?,CUM_BUILD_REACH_AMT=?," +
                                "CUM_ACQ_REACH_AMT=?,SUR_REACH_AMT=?,FUND_REACH_CATEGORY=?,NPER=?,COST_NAME=?,PAY_UNIT=?,RECEIPT_BANK=?,RECEIPT_ACCOUNT=?,PAY_DATE=?," +
                                "PAYABLE_AMT=?,PAID_AMT=?,UNPAD_AMT=?,PAYEE=?,GUARANTEE_STATES=?,APPROVAL_STATUS=?",
                        optional.get().get("ID"), fundSpecialModel.getApprovedAmount(), fundSpecialModel.getLjdwAmt(), fundSpecialModel.getPayAmt(), fundSpecialModel.getWdwAmt(),
                        fundSpecialModel.getJsAmt(), fundSpecialModel.getZcAmt(), fundSpecialModel.getSyAmt(), "", fundSpecialModel.getPeriods(), fundSpecialModel.getFeeName(),
                        fundSpecialModel.getPayUnit(), fundSpecialModel.getPayBank(), fundSpecialModel.getPayAccount(), fundSpecialModel.getPayDate(), fundSpecialModel.getYfAmt(),
                        fundSpecialModel.getHasPayAmt(), 0, fundSpecialModel.getSkUnit(), fundSpecialModel.getBhqk(), "", fundSpecialModel.getStatus(), id);

            } else {
                res.add(fundSpecialModel.getProjectName());
            }
        }
        if (CollectionUtils.isEmpty(res)) {
            return "导入成功！";
        } else {
            return "导入失败！" + res.stream().collect(Collectors.joining(",")) + "不存在！";
        }
    }
}
