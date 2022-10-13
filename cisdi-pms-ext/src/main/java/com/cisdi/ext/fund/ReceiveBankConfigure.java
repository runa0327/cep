package com.cisdi.ext.fund;

import com.cisdi.ext.constants.FundConstant;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 收款银行配置
 */
public class ReceiveBankConfigure {
    //新增单位、银行、账户
    public void addBank() {
        Map<String, Object> bankMap = ExtJarHelper.extApiParamMap.get();
        Bank bank = JsonUtil.fromJson(JsonUtil.toJson(bankMap), Bank.class);

        if (checkDuplicate(bank.name, bank.parentId)) {
            throw new BaseException("配置名称重复！");
        }
        String id = Crud.from("RECEIVING_BANK").insertData();
        if (Strings.isNullOrEmpty(bank.parentId)){
            bank.parentId = null;
        }
        Crud.from("RECEIVING_BANK").where().eq("ID", id).update().set("RECEIVING_BANK_PID", bank.parentId).set("NAME", bank.name).set("level",
                bank.level).exec();
    }

    //银行配置列表
    public void bankList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select id,RECEIVING_BANK_PID parentId,if(level = 1,name,'') unitName,if(level = 2,name,'') bankName,if(level = 3,name,'') " +
                "account,level from receiving_bank rb";
        List<Map<String, Object>> bankMaps = myJdbcTemplate.queryForList(sql);
        List<BankResp> bankResps = bankMaps.stream().map(item -> {
            BankResp bankResp = new BankResp();
            bankResp.id = JdbcMapUtil.getString(item, "id");
            bankResp.pid = JdbcMapUtil.getString(item, "parentId");
            bankResp.level = JdbcMapUtil.getString(item, "level");
            bankResp.UnitName = JdbcMapUtil.getString(item, "unitName");
            bankResp.bankName = JdbcMapUtil.getString(item, "bankName");
            bankResp.account = JdbcMapUtil.getString(item, "account");
//            String level = JdbcMapUtil.getString(item, "level");
//            bankResp.level = level;
//            String name = JdbcMapUtil.getString(item, "name");
//            if ("1".equals(level)) {
//                bankResp.UnitName = name;
//            } else if ("2".equals(level)) {
//                bankResp.bankName = name;
//            } else if ("3".equals(level)) {
//                bankResp.account = name;
//            }
            return bankResp;
        }).collect(Collectors.toList());
        List<BankResp> roots = bankResps.stream().filter(item -> item.level.equals("1")).collect(Collectors.toList());
        for (BankResp root : roots) {
            getChild(bankResps,root);
        }
        HashMap<String, Object> rootMaps = new HashMap<>();
        rootMaps.put("roots",roots);
        Map result = JsonUtil.fromJson(JsonUtil.toJson(rootMaps), Map.class);
        ExtJarHelper.returnValue.set(result);
    }

    //删除银行节点
    public void delBank() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select id,RECEIVING_BANK_PID parentId,name from RECEIVING_BANK";
        List<Map<String, Object>> allTypes =
                myJdbcTemplate.queryForList(sql);
        //删除所有后代
        delOffspring(allTypes, inputMap, myJdbcTemplate);
    }

    //编辑银行节点
    public void editBank() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        Bank bank = JsonUtil.fromJson(JsonUtil.toJson(inputMap), Bank.class);
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "update receiving_bank set name = ? where id = ?";
        myJdbcTemplate.update(sql, bank.name, bank.id);
    }

    //递归成树
    public void getChild(List<BankResp> data, BankResp parent) {
        List<BankResp> childList = data.stream()
                .filter(item -> parent.id.equals(item.pid))
                .peek(child -> getChild(data, child))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(childList)) {
            parent.children = childList;
        }
    }

    //递归删除所有的后代
    public void delOffspring(List<Map<String, Object>> data, Map<String, Object> parent, MyJdbcTemplate myJdbcTemplate) {
        data.stream()
                .filter(item -> JdbcMapUtil.getString(parent, "id").equals(JdbcMapUtil.getString(item, "parentId")))
                .peek(child -> delOffspring(data, child, myJdbcTemplate))
                .collect(Collectors.toList());
        String sql = "delete from RECEIVING_BANK where id = ?";
        myJdbcTemplate.update(sql, JdbcMapUtil.getString(parent, "id"));
    }


    //检查是否有名称重复 false没有重复、true重复
    public boolean checkDuplicate(String name, String pid) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "SELECT count(*) count FROM receiving_bank where name = ? and RECEIVING_BANK_PID = ?";
        Map<String, Object> countMap = myJdbcTemplate.queryForMap(sql, name, pid);
        if (JdbcMapUtil.getInt(countMap, "count") == 0) {
            return false;
        }
        return true;
    }

    //获取父id字段名称
    public String getParentIdName(String configName) {
        String parentIdName = "";
        if (FundConstant.FUND_TYPE.equals(configName)) {
            parentIdName = "FUND_TYPE_PID";
        }
        if (FundConstant.RECEIVING_BANK.equals(configName)) {
            parentIdName = "RECEIVING_BANK_PID";
        }
        return parentIdName;
    }

    public static class Bank {
        //id
        public String id;
        //父id
        public String parentId;
        //名称
        public String name;
        //层级
        public String level;
    }

    public static class BankResp {
        //id
        public String id;
        public String pid;
        public String UnitName;
        public String bankName;
        public String account;
        public String level;
        public List<BankResp> children;
    }
}
