package com.cisdi.pms.job.excel.imports;

import com.cisdi.pms.job.excel.export.BaseController;
import com.cisdi.pms.job.excel.model.FundPaymentImportModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.ReflectUtil;
import com.cisdi.pms.job.utils.Util;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.bouncycastle.jcajce.provider.digest.MD2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hgh
 * @date 2022/10/31
 * @apiNote
 */

@RestController
@RequestMapping("/paymentImport")
public class FundPaymentImportController extends BaseController {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void Constructor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @SneakyThrows(IOException.class)
    @RequestMapping(value = "/import")
    public Map<String, Object> importData(MultipartFile file, HttpServletResponse response) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取100条数据 然后返回过来 直接调用使用数据就行
        List<FundPaymentImportModel> reads = EasyExcelUtil.read(file.getInputStream(), FundPaymentImportModel.class);
        List<FundPaymentImportModel> List = reads.stream().filter(p -> !ReflectUtil.isObjectNull(p)).collect(Collectors.toList());

        List<Map<String, Object>> bankList = jdbcTemplate.queryForList("select id,ifnull(name,'0') as `NAME` from receiving_bank");
        List<String> res = new ArrayList<>();
        for (FundPaymentImportModel read : List) {
            List<String> singleRes = this.importData(read, bankList);
            if (!CollectionUtils.isEmpty(singleRes)) {
                res.addAll(singleRes);
            }
        }
        Map<String, Object> result = new HashMap<>();
        if (CollectionUtils.isEmpty(res)) {
            result.put("code", 200);
            result.put("message", "导入成功！");
            return result;
        } else {
            super.exportTxt(response, res, "资金支付明细导入日志");
            return null;
        }
    }


    private List<String> importData(FundPaymentImportModel model, List<Map<String, Object>> bankList) {
        List<String> res = new ArrayList<>();
        //判断资金来源，项目--查询资金到位
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select fr.*,FUND_CATEGORY_FIRST,fi.id as FUND_IMPLEMENTATION_V_ID from fund_reach fr \n" +
                "left join pm_prj pj on fr.PM_PRJ_ID = pj.id \n" +
                "left join fund_implementation fi on fr.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT \n" +
                "where pj.`NAME`=? and fr.FUND_SOURCE_TEXT=? ", model.getPmPrjId(), model.getFundImplementationVId());
        //资金批复明细来代表fundImplementationVId
        List<Map<String, Object>> fidList = jdbcTemplate.queryForList("select fid.id from fund_implementation fi\n" +
                "left join fund_implementation_detail fid on fid.FUND_IMPLEMENTATION_ID = fi.id\n" +
                "where fi.FUND_SOURCE_TEXT = '321' and fid.PM_PRJ_ID = '1619959989231816704' limit 1");
        if (CollectionUtils.isEmpty(list)) {
            res.add("序号为：" + model.getSerialNumber());
        } else {
            //付款单位
            String payUnit = null;
            if (model.getPayUnit() != null) {
                Optional<Map<String, Object>> optional = bankList.stream().filter(p -> JdbcMapUtil.getString(p, "Name").equals(model.getPayUnit())).findAny();
                if (optional.isPresent()) {
                    payUnit = String.valueOf(optional.get().get("ID"));
                }
            }

            String payBank = null;
            if (model.getPayBank() != null) {
                Optional<Map<String, Object>> bankOptional = bankList.stream().filter(p -> JdbcMapUtil.getString(p, "Name").equals(model.getPayBank())).findAny();
                if (bankOptional.isPresent()) {
                    payBank = String.valueOf(bankOptional.get().get("ID"));
                }
            }


            String payAccount = null;
            if (model.getPayAccount() != null) {
                Optional<Map<String, Object>> accountOptional = bankList.stream().filter(p -> JdbcMapUtil.getString(p, "Name").equals(model.getPayAccount())).findAny();
                if (accountOptional.isPresent()) {
                    payAccount = String.valueOf(accountOptional.get().get("ID"));
                }
            }


            String costCategoryId = null;
            if (model.getCostCategoryId() != null) {
                List<Map<String, Object>> costList = jdbcTemplate.queryForList("select * from gr_set_value where `NAME`=''", model.getCostCategoryId());
                if (!CollectionUtils.isEmpty(costList)) {
                    costCategoryId = String.valueOf(costList.get(0).get("ID"));
                }
            }

            String fundImplementationVId = null;
            if (!CollectionUtils.isEmpty(fidList)){
                fundImplementationVId = String.valueOf(fidList.get(0).get("id"));
            }

            String fundPayInfoId = Util.insertData(jdbcTemplate, "fund_pay_info");
            String fundNewlyIncreasedDetailId = Util.insertData(jdbcTemplate, "fund_newly_increased_detail");
            jdbcTemplate.update(
                    "update fund_pay_info set FUND_REACH_CATEGORY = ?," +
                            "COST_CATEGORY_ID = ?," +
                            "FEE_DETAIL = ? ," +
                            "PAY_AMT = ?," +
                            "PAY_UNIT = ?," +
                            "RECEIPT_BANK = ?," +
                            "RECEIPT_ACCOUNT = ?," +
                            "PAYEE = ?," +
                            "RECEIVE_BANK = ?," +
                            "RECEIVE_ACCOUNT = ?," +
                            "FUND_NEWLY_INCREASED_DETAIL_ID=? ," +
                            "COLLECTION_ACCOUNT=? " +
                            "where ID = ?",
                    list.get(0).get("FUND_REACH_CATEGORY"), costCategoryId,
                    model.getFeeDetail(), model.getPayAmt().replace(",", ""),
                    payUnit, payBank, payAccount, model.getPayee(), model.getPayBank(), model.getPayAccount(), fundNewlyIncreasedDetailId, model.getCollectionAccount(),
                    fundPayInfoId);


            jdbcTemplate.update(
                    "update fund_newly_increased_detail set REMARK=?," +
                            "ACCOUNT_SET=?," +
                            "PM_PRJ_ID=?," +
                            "CUSTOMER_UNIT=?," +
                            "VOUCHER_NUM=?," +
                            "FUND_IMPLEMENTATION_V_ID=?," +
                            "FUND_CATEGORY_FIRST=?," +
                            "NPER=? where ID=?",
                    model.getRemarke(), model.getAccountSet(), list.get(0).get("PM_PRJ_ID"),
                    model.getCustomerUnit(), model.getVoucherNum(), fundImplementationVId,
                    list.get(0).get("FUND_CATEGORY_FIRST"), model.getNper(),
                    fundNewlyIncreasedDetailId);
        }
        return res;
    }

}
