package com.cisdi.ext.contract;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.fundManage.FileCommon;
import com.cisdi.ext.model.view.file.File;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author dlt
 * @date 2022/12/22 周四
 * 合同台账
 */
public class ContractAccount {
    //获取入参
    private RequestParam getRequestParam(){
        Map<String, Object> requestMap = ExtJarHelper.extApiParamMap.get();
        RequestParam requestParam = JsonUtil.fromJson(JsonUtil.toJson(requestMap), RequestParam.class);
        return requestParam;
    }

    /**
     * 合同台账列表查询
     */
    public void contractAccountList(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        LoginInfo loginInfo = ExtJarHelper.loginInfo.get();
        List<String> rootUsers = this.getRootUsers();
        RequestParam requestParam = this.getRequestParam();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * from (select * from \n" +
                "(SELECT o.DATE_FIVE as expireDate,o.id,p.id as projectId,\n" +
                "IFNULL(p.name,o.PROJECT_NAME_WR) prjName,\n" +
                "o.CONTRACT_NAME contractName,o.CUSTOMER_UNIT_ONE contractCompanyId,pa.name contractCompanyName,temp.cooperationUnit,o" +
                ".CONTRACT_CODE contractCode,o.CONTRACT_CATEGORY_ONE_ID contractCategoryId,va.name contractCategoryName,ifnull(o.AMT_THREE,0) amtExcludeTax,o" +
                ".AMT_FOUR taxRate,ifnull(o.AMT_TWO,0) amtIncludeTax,o.SIGN_DATE createTime,i.END_DATETIME endTime,o.REMARK_LONG_ONE remark,o.CRT_USER_ID " +
                "userId,u.name userName,o.CRT_DT,o.FILE_ID_FIVE fileIds\n" +
                "FROM PO_ORDER_REQ o\n" +
                "left join pm_prj p on p.id = o.PM_PRJ_ID  \n" +
                "left join pm_party pa on pa.id = o.CUSTOMER_UNIT_ONE\n" +
                "left join gr_set_value va on va.id = o.CONTRACT_CATEGORY_ONE_ID\n" +
                "left join gr_set se on se.id = va.GR_SET_ID and se.code = 'contract_type_one'\n" +
                "left join ad_user u on u.id = o.CRT_USER_ID\n" +
                "left join wf_process_instance i on i.id = o.LK_WF_INST_ID\n" +
                "left join (select o.id,GROUP_CONCAT(c.WIN_BID_UNIT_ONE) cooperationUnit from po_order_req o left join contract_signing_contact c " +
                "on c.PARENT_ID = o.id group by o.id) temp on temp.id = o.id\n" +
                " and o.PROJECT_SOURCE_TYPE_ID = '0099952822476441374'\n" +
                " where p.PROJECT_SOURCE_TYPE_ID = '0099952822476441374' and o.status = 'AP'\n" +
                "order by o.id desc) a\n" +
                "union all\n" +
                "SELECT * FROM\n" +
                "(SELECT o.DATE_FIVE as expireDate,o.id,p.id as projectId,\n" +
                "IFNULL(p.name,o.PROJECT_NAME_WR) prjName,\n" +
                "o.CONTRACT_NAME contractName,o.CUSTOMER_UNIT_ONE contractCompanyId,pa.name contractCompanyName,temp.cooperationUnit,o" +
                ".CONTRACT_CODE contractCode,o.CONTRACT_CATEGORY_ONE_ID contractCategoryId,va.name contractCategoryName,o.AMT_THREE amtExcludeTax,o" +
                ".AMT_FOUR taxRate,o.AMT_TWO amtIncludeTax,o.SIGN_DATE createTime,i.END_DATETIME endTime,o.REMARK_LONG_ONE remark,o.CRT_USER_ID " +
                "userId,u.name userName,o.CRT_DT,o.FILE_ID_FIVE fileIds\n" +
                "FROM PO_ORDER_REQ o\n" +
                "left join pm_prj p on p.name = o.PROJECT_NAME_WR\n" +
                "left join pm_party pa on pa.id = o.CUSTOMER_UNIT_ONE\n" +
                "left join gr_set_value va on va.id = o.CONTRACT_CATEGORY_ONE_ID\n" +
                "left join gr_set se on se.id = va.GR_SET_ID and se.code = 'contract_type_one'\n" +
                "left join ad_user u on u.id = o.CRT_USER_ID\n" +
                "left join wf_process_instance i on i.id = o.LK_WF_INST_ID\n" +
                "left join (select o.id,GROUP_CONCAT(c.WIN_BID_UNIT_ONE) cooperationUnit from po_order_req o left join contract_signing_contact c " +
                "on c.PARENT_ID = o.id group by o.id) temp on temp.id = o.id\n" +
                " and o.PROJECT_SOURCE_TYPE_ID = '0099952822476441375'\n" +
                "where p.PROJECT_SOURCE_TYPE_ID = '0099952822476441375' and o.status = 'AP'\n" +
                "order by o.id desc) b \n" +
                "order by projectId desc ) o where 1 = 1 ");
        if (!rootUsers.contains(loginInfo.userId)){
            sb.append(" and o.projectId in (select DISTINCT pm_prj_id from pm_dept WHERE STATUS = 'ap' and FIND_IN_SET('").append(loginInfo.userId).append("', USER_IDS ))");
        }
        ResponseData responseData = new ResponseData();
        int start = requestParam.pageSize * (requestParam.pageIndex - 1);
        String sql = sb.toString();
        List<Map<String, Object>> contractList = myJdbcTemplate.queryForList(sql);
        //条件过滤
        List<Map<String, Object>> totalResultList = contractList.stream().filter(m -> {
            if (Strings.isNotEmpty(requestParam.prjId)){
                return requestParam.prjId.equals(m.get("projectId"));
            }
            return true;
        }).filter(m -> {
            if (Strings.isNotEmpty(requestParam.contractName)){
                Pattern pattern = Pattern.compile(requestParam.contractName);
                Matcher matcher = pattern.matcher(String.valueOf(m.get("contractName")));
                if (!matcher.find()){
                    return false;
                }
            }
            return true;
        }).filter(m -> {
            if (Strings.isNotEmpty(requestParam.contractCompanyId)){
                return requestParam.contractCompanyId.equals(m.get("contractCompanyId"));
            }
            return true;
        }).filter(m -> {
            if (Strings.isNotEmpty(requestParam.contractCategoryId)){
                return requestParam.contractCategoryId.equals(m.get("contractCategoryId"));
            }
            return true;
        }).filter(m -> {
            if (Strings.isNotEmpty(requestParam.amtIncludeTaxStart) && Strings.isNotEmpty(requestParam.amtIncludeTaxEnd)){
                BigDecimal amtIncludeTax = new BigDecimal(m.get("amtIncludeTax").toString());
                if (amtIncludeTax.compareTo(new BigDecimal(requestParam.amtIncludeTaxStart)) < 0 || amtIncludeTax.compareTo(new BigDecimal(requestParam.amtIncludeTaxEnd)) > 0){
                    return false;
                }
            }
            return true;
        }).filter(m -> {
            if (Strings.isNotEmpty(requestParam.createTimeStart) && Strings.isNotEmpty(requestParam.createTimeEnd)){
                if (m.get("CRT_DT") == null){
                    return false;
                }
                String crt = m.get("CRT_DT").toString();
                if (crt.compareTo(requestParam.createTimeStart) < 0 || crt.compareTo(requestParam.createTimeEnd) > 0){
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
        //计算总数
        responseData.total = totalResultList.size();
        //封装其他
        List<Map<String, Object>> resultList = totalResultList.stream().skip(start).limit(requestParam.pageSize).collect(Collectors.toList());
        List<ContractInfo> contractInfos = new ArrayList<>();
        for (Map<String, Object> contractMap : resultList) {
            ContractInfo contractInfo = JSONObject.parseObject(JSONObject.toJSONString(contractMap), ContractInfo.class);
            contractInfo.createTime = StringUtil.withOutT(contractInfo.createTime);
            contractInfo.endTime = StringUtil.withOutT(contractInfo.endTime);
            contractInfo.files = FileCommon.getFileResp(JdbcMapUtil.getString(contractMap, "fileIds"), myJdbcTemplate);
            contractInfos.add(contractInfo);
        }
        responseData.contractInfos = contractInfos;
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(responseData), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }
    /**
     * 获取拥有所有权限用户id
     */
    private List<String> getRootUsers(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> rootList = myJdbcTemplate.queryForList("select du.AD_USER_ID from hr_dept hd\n" +
                "left join hr_dept_user du on du.HR_DEPT_ID = hd.id\n" +
                "where hd.name in ('成本合约部','财务金融部') and du.AD_USER_ID is not null");
        List<String> rootUsers = rootList.stream().map(item -> String.valueOf(item.get("AD_USER_ID"))).collect(Collectors.toList());
        rootUsers.add("0099250247095871681");//系统管理员
        return rootUsers;
    }

    /**
     * 获取合同签订公司
     */
    public void getContractCompany(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> companyList = myJdbcTemplate.queryForList("select pa.id,pa.name\n" +
                "from po_order_req o\n" +
                "left join pm_party pa on pa.id = o.CUSTOMER_UNIT_ONE\n" +
                "where pa.id is not null\n" +
                "group by pa.id");
        Map<String, Object> result = new HashMap<>();
        result.put("companyList",companyList);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 项目下拉
     */
    public void contractPrjDrop(){
        LoginInfo loginInfo = ExtJarHelper.loginInfo.get();
        List<String> rootUsers = this.getRootUsers();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
//        String sql = "SELECT DISTINCT IFNULL(o.PM_PRJ_ID,p2.id) prjId,IFNULL(p.name,o.PROJECT_NAME_WR) prjName\n" +
//                "FROM PO_ORDER_REQ o left join pm_prj p on p.id = o.PM_PRJ_ID \n" +
//                "left join pm_prj p2 on p2.name = o.PROJECT_NAME_WR \n" +
//                "left join wf_process_instance i on i.id = o.LK_WF_INST_ID where o.STATUS = 'AP' ";
//        if (!rootUsers.contains(loginInfo.userId)){
//            sql += " and IFNULL(o.PM_PRJ_ID,p2.id) in (select DISTINCT pm_prj_id from pm_dept where STATUS = 'AP' and FIND_IN_SET('" + loginInfo.userId + "',USER_IDS))";
//        }
        String sql = "select prjId,max(prjName) prjName from (\n" +
                "\tselect p.id prjId,IFNULL(p.name,o.PROJECT_NAME_WR) prjName from po_order_req o left join pm_prj p on p.id = o.PM_PRJ_ID \n" +
                "\twhere p.PROJECT_SOURCE_TYPE_ID = '0099952822476441374' and o.status = 'AP'\n" +
                "\tunion all\n" +
                "\tselect p.id prjId,IFNULL(p.name,o.PROJECT_NAME_WR) prjName from po_order_req o left join pm_prj p on p.name = o.PROJECT_NAME_WR " +
                "\n" +
                "\twhere p.PROJECT_SOURCE_TYPE_ID = '0099952822476441375' and o.status = 'AP'\n" +
                ") o group by prjId";
        List<Map<String, Object>> prjList = myJdbcTemplate.queryForList(sql);
        HashMap<Object, Object> result = new HashMap<>();
        result.put("prjList",prjList);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 请求参数
     */
    public static class RequestParam{
        public Integer pageSize;
        public Integer pageIndex;
        //项目id
        public String prjId;
        //合同名称
        public String contractName;
        //合同签订公司
        public String contractCompanyId;
        //合作单位
        public String cooperationUnit;
        //合同类型
        public String contractCategoryId;
        //不含税金额
        public String amtExcludeTaxStart;
        public String amtExcludeTaxEnd;
        //含税金额
        public String amtIncludeTaxStart;
        public String amtIncludeTaxEnd;
        //签订时间
        public String createTimeStart;
        public String createTimeEnd;
        //经办人
        public String userId;
        //到期日期
        public String expireDate;
    }

    /**
     * 响应数据
     */
    public static class ContractInfo{
        //合同签订id
        public String id;
        //项目id
        public String prjId;
        //项目名称
        public String prjName;
        //合同名称
        public String contractName;
        //合同编号
        public String contractCode;
        //合同签订公司
        public String contractCompanyId;
        public String contractCompanyName;
        //合作单位
        public String cooperationUnit;
        //合同类型
        public String contractCategoryId;
        public String contractCategoryName;
        //合同不含税金额
        public BigDecimal amtExcludeTax;
        //税率
        public BigDecimal taxRate;
        //合同含税金额
        public BigDecimal amtIncludeTax;
        //签订时间
        public String createTime;
        //签收时间
        public String endTime;
        //备注
        public String remark;
        //采购经办人
        public String userId;
        public String userName;
        //合同盖章版文件
        public List<File> files;
        //到期日期
        public String expireDate;

    }

    public static class ResponseData{
        public List<ContractInfo> contractInfos;
        public Integer total;
    }

}

