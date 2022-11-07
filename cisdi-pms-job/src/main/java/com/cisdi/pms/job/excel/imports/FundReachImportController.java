package com.cisdi.pms.job.excel.imports;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.cisdi.pms.job.excel.model.FundReachExportModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.Util;
import com.google.common.base.Strings;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundReachImportController
 * @package com.cisdi.pms.job.excel.imports
 * @description 资金到位导入
 * @date 2022/10/31
 */
@RestController
@RequestMapping("/reach")
public class FundReachImportController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @SneakyThrows(IOException.class)
    @RequestMapping("/import")
    public Map<String, Object> importData(MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        List<FundReachExportModel> reachList = EasyExcelUtil.read(file.getInputStream(), FundReachExportModel.class);

        //如果有不能处理的字段，响应提示
        List<String> res = new ArrayList<>();
        List<Map<String, Object>> prjList = jdbcTemplate.queryForList("select id,name from pm_prj where status = 'AP'");

        for (FundReachExportModel reachData : reachList) {
            List<String> singleRes = this.insertReach(reachData, prjList);
            if (!CollectionUtils.isEmpty(singleRes)) {
                res.addAll(singleRes);
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

    private List<String> insertReach(FundReachExportModel reachData, List<Map<String, Object>> prjList) {
        List<String> res = new ArrayList<>();
        if (reachData == null) {
            return null;
        }
        //项目 先判断项目没有对应项目直接返回
        Optional<Map<String, Object>> optional = prjList.stream().filter(p -> String.valueOf(p.get("name")).equals(reachData.getProjectName())).findAny();
        if (!optional.isPresent()) {
            res.add(reachData.getProjectName());
            return res;
        }
        String prjId = String.valueOf(optional.get().get("id"));

        //插入一条空数据
        String id = Util.insertData(jdbcTemplate, "FUND_REACH");

        //到位资金类别
        String typeName = reachData.getTypeName();
        if ("建设资金".equals(typeName)) {
            typeName = "工程资金";
        }
        List<Map<String, Object>> fundReachTypeList = jdbcTemplate.queryForList("select id from gr_set_value where name = ?", typeName);
        String fundReachType = this.getStringFromList(fundReachTypeList, "id");
        //收款单位
        List<Map<String, Object>> unitList = jdbcTemplate.queryForList("select id from receiving_bank where level = 1 and name = ?", reachData.getUnit());
        String payee = this.getStringFromList(unitList, "id");
        if (payee == null) {//如果收款单位在配置中没有、插入一条
            payee = this.insertBank(reachData.getUnit(), 1, null);
        }
        //收款银行
        String bank = null;
        if (!Strings.isNullOrEmpty(reachData.getBank())) {
            List<Map<String, Object>> bankList = jdbcTemplate.queryForList("select id from receiving_bank where level = 2 and name = ? and RECEIVING_BANK_PID = ?", reachData.getBank(), payee);
            bank = this.getStringFromList(bankList, "id");
            if (bank == null) {//如果收款银行为空，插入一条
                bank = this.insertBank(reachData.getBank(), 2, payee);
            }
        }
        //收款账户
        String account = null;
        if (!Strings.isNullOrEmpty(reachData.getAccount())) {
            List<Map<String, Object>> accountList = jdbcTemplate.queryForList("select id from receiving_bank where level = 3 and name = ? and RECEIVING_BANK_PID = ?",
                    reachData.getAccount(), bank);
            account = this.getStringFromList(accountList, "id");
            if (account == null) {//如果没有该账户，插入一条
                account = this.insertBank(reachData.getAccount(), 3, bank);
            }
        }

        //更新插入的到位空数据
        jdbcTemplate.update("update FUND_REACH set FUND_SOURCE_TEXT = ?,PM_PRJ_ID = ?,REACH_TIMES = ?,FUND_REACH_CATEGORY = ?,REACH_AMOUNT = ?,REACH_DATE = ?,PAYEE = ?,RECEIVING_BANK_ID = ?,RECEIPT_ACCOUNT = ?,REMARK = ? where id = ?",
                reachData.getSourceName(), prjId, reachData.getCount(), typeName, reachData.getDwAmt(), reachData.getDwDate(), payee, bank, account, reachData.getRemark(), id);
        return res;
    }

    //插入银行配置
    private String insertBank(String name, Integer level, String parentId) {
        String id = Util.insertData(jdbcTemplate, "receiving_bank");
        jdbcTemplate.update("update receiving_bank set name = ?,level = ?,RECEIVING_BANK_PID = ? where id = ?", name, level, parentId, id);
        return id;
    }

    //queryForList获取string
    private String getStringFromList(List<Map<String, Object>> list, String key) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return JdbcMapUtil.getString(list.get(0), key);
    }
}
