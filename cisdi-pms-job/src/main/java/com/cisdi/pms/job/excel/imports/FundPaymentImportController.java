package com.cisdi.pms.job.excel.imports;

import com.cisdi.pms.job.excel.model.FundPaymentImportModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.Util;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author hgh
 * @date 2022/10/31
 * @apiNote
 */

@RestController
@RequestMapping("/paymentImport")
public class FundPaymentImportController {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void Constructor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @SneakyThrows(IOException.class)
    @RequestMapping(value = "/import")
    public String importData(MultipartFile file) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取100条数据 然后返回过来 直接调用使用数据就行
        List<FundPaymentImportModel> reads = EasyExcelUtil.read(file.getInputStream(), FundPaymentImportModel.class);
        try {
            reads.stream().forEach(read -> this.importData(read));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "导入成功";
    }

    private void importData(FundPaymentImportModel modelList) {

        //查询项目名称
        List<Map<String, Object>> proId = jdbcTemplate.queryForList("select id from pm_prj where name = ?", modelList.getPmPrjId());

        //查询付款单位
        List<Map<String, Object>> payId = jdbcTemplate.queryForList("select id from receiving_bank where name = ?", modelList.getPayUnit());

        //没有付款单位
        if(payId.size() <= 0 || proId.size() <= 0){
            return;
        }

        //资金支付明细
        String fundNewlyIncreasedDetailId = Util.insertData(jdbcTemplate, "fund_newly_increased_detail");

        //资金支付信息明细
        String fundPayInfoId = Util.insertData(jdbcTemplate, "fund_pay_info");

        jdbcTemplate.update(
                "update fund_pay_info set FUND_REACH_CATEGORY = ?,COST_CATEGORY_ID = ?,FEE_DETAIL = ? ,PAY_AMT = ?,PAY_UNIT = ?,RECEIPT_BANK = ?,RECEIPT_ACCOUNT = ?,PAYEE = ?,RECEIVE_BANK = ?,RECEIVE_ACCOUNT = ?,FUND_NEWLY_INCREASED_DETAIL_ID=? where ID = ?",
                modelList.getFundReachCategory(), "99902212142032084", modelList.getFeeDetail(), modelList.getPayAmt().replace(",",""), payId.get(0).get("id"), modelList.getReceiptBank(), modelList.getReceiptAccount(), modelList.getPayee(), modelList.getReceiveBank(), modelList.getReceiveAccount(), fundNewlyIncreasedDetailId,
                fundPayInfoId);

        jdbcTemplate.update(
                "update fund_newly_increased_detail set REMARK=?,ACCOUNT_SET=?,PM_PRJ_ID=?,CUSTOMER_UNIT=?,VOUCHER_NUM=? where ID=?",
                modelList.getRemarke(), modelList.getAccountSet(), proId.get(0).get("id"), modelList.getCustomerUnit(), modelList.getVoucherNum(),
                fundNewlyIncreasedDetailId);

    }

}
