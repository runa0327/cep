package com.cisdi.pms.job.excel.imports;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.cisdi.pms.job.excel.model.FundPaymentImportModel;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        EasyExcel.read(file.getInputStream(),
                FundPaymentImportModel.class,
                new PageReadListener<FundPaymentImportModel>(dataList -> {
                    for (FundPaymentImportModel model : dataList) {
                        // 这里就是你处理代码保存的逻辑了
                        this.importData(model);
                    }
                })).sheet().doRead();

        return "";
    }

    private void importData(FundPaymentImportModel modelList) {

        String sql = "insert into fund_newly_increased_detail(ID , VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, STATUS, REMARK, ACCOUNT_SET, PM_PRJ_ID, CUSTOMER_UNIT, VOUCHER_NUM, NPER) values (UUID_SHORT(), '1',NOW(),NOW(),?,NOW(),?,'AP',?,?,? ,? ,?,?)";
        //String sql = "insert into fund_newly_increased_detail" +
        //        "(ID , VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, STATUS," +
        //        "REMARK, ACCOUNT_SET, PM_PRJ_ID, CUSTOMER_UNIT, VOUCHER_NUM, NPER) " +
        //        "values " +
        //        "(UUID_SHORT(), '1',NOW(),NOW(),?,NOW(),?,'AP',?,?,? ,? ,?,?)";

        jdbcTemplate.update(sql, "CRT_USER_ID","LAST_MODI_USER_ID","REMARK","ACCOUNT_SET","PM_PRJ_ID","CUSTOMER_UNIT","VOUCHER_NUM","NPER");


    }

}
