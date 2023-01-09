package com.cisdi.ext.contract;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.fundManage.FileCommon;
import com.cisdi.ext.model.view.File;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.util.*;
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
        sb.append("SELECT o.id,IFNULL(o.PM_PRJ_ID,p2.id) prjId,IFNULL(p.name,o.PROJECT_NAME_WR) prjName,o.CONTRACT_NAME contractName,o.CUSTOMER_UNIT_ONE" +
                " contractCompanyId,pa.name contractCompanyName,o.WIN_BID_UNIT_ONE cooperationUnit,o.CONTRACT_CATEGORY_ONE_ID contractCategoryId,va" +
                ".name contractCategoryName,o.AMT_THREE amtExcludeTax,o.AMT_FOUR taxRate,o.AMT_TWO amtIncludeTax,o.SIGN_DATE createTime,i.END_DATETIME" +
                " endTime,o.REMARK_LONG_ONE remark,o.CRT_USER_ID userId,u.name userName,o.FILE_ID_FIVE fileIds\n" +
                "FROM PO_ORDER_REQ o\n" +
                "left join pm_prj p on p.id = o.PM_PRJ_ID\n" +
                "left join pm_prj p2 on p2.name = o.PROJECT_NAME_WR\n" +
                "left join pm_party pa on pa.id = o.CUSTOMER_UNIT_ONE\n" +
                "left join gr_set_value va on va.id = o.CONTRACT_CATEGORY_ONE_ID\n" +
                "left join gr_set se on se.id = va.GR_SET_ID and se.code = 'contract_type_one'\n" +
                "left join ad_user u on u.id = o.CRT_USER_ID\n" +
                "left join wf_process_instance i on i.id = o.LK_WF_INST_ID\n" +
                "left join (select PM_PRJ_ID,GROUP_CONCAT(USER_IDS) USER_IDS from pm_dept group by PM_PRJ_ID) temp on temp.PM_PRJ_ID = IFNULL(o.PM_PRJ_ID,p2.id)\n" +
                "where o.STATUS = 'AP'");
        if (!rootUsers.contains(loginInfo.userId)){
            sb.append(" and FIND_IN_SET('").append(loginInfo.userId).append("',temp.USER_IDS)");
        }
        if (Strings.isNotEmpty(requestParam.prjId)){
            sb.append(" and IFNULL(o.PM_PRJ_ID,p2.id) = '").append(requestParam.prjId).append("'");
        }
        if (Strings.isNotEmpty(requestParam.contractName)) {
            sb.append(" and o.CONTRACT_NAME like '%").append(requestParam.contractName).append("%'");
        }
        if (Strings.isNotEmpty(requestParam.contractCompanyId)){
            sb.append(" and o.CUSTOMER_UNIT_ONE = '").append(requestParam.contractCompanyId).append("'");
        }
        if (Strings.isNotEmpty(requestParam.cooperationUnit)){
            sb.append(" and o.WIN_BID_UNIT_ONE like '%").append(requestParam.cooperationUnit).append("%'");
        }
        if (Strings.isNotEmpty(requestParam.contractCategoryId)){
            sb.append(" and o.CONTRACT_CATEGORY_ONE_ID = '").append(requestParam.contractCategoryId).append("'");
        }
        if (Strings.isNotEmpty(requestParam.amtExcludeTaxStart) && Strings.isNotEmpty(requestParam.amtExcludeTaxEnd)) {
            sb.append(" and o.AMT_THREE between '").append(requestParam.amtExcludeTaxStart).append("' and '").append(requestParam.amtExcludeTaxEnd).append("'");
        }
        if (Strings.isNotEmpty(requestParam.amtIncludeTaxStart) && Strings.isNotEmpty(requestParam.amtIncludeTaxEnd)) {
            sb.append(" and o.AMT_TWO between '").append(requestParam.amtIncludeTaxStart).append("' and '").append(requestParam.amtIncludeTaxEnd).append("'");
        }
        if (Strings.isNotEmpty(requestParam.createTimeStart) && Strings.isNotEmpty(requestParam.createTimeEnd)) {
            sb.append(" and o.CRT_DT between '").append(requestParam.createTimeStart).append("' and '").append(requestParam.createTimeEnd).append("'");
        }
        if (Strings.isNotEmpty(requestParam.userId)){
            sb.append(" and o.CRT_USER_ID = '").append(requestParam.userId).append("'");
        }

        String totalSql = sb.toString();
        sb.append(" order by o.CRT_DT desc");
        int start = requestParam.pageSize * (requestParam.pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(requestParam.pageSize);
        String sql = sb.toString();
        List<Map<String, Object>> contractList = myJdbcTemplate.queryForList(sql);
        ResponseData responseData = new ResponseData();
        List<ContractInfo> contractInfos = new ArrayList<>();
        for (Map<String, Object> contractMap : contractList) {
            ContractInfo contractInfo = JSONObject.parseObject(JSONObject.toJSONString(contractMap), ContractInfo.class);
            contractInfo.createTime = StringUtil.withOutT(contractInfo.createTime);
            contractInfo.endTime = StringUtil.withOutT(contractInfo.endTime);
            contractInfo.files = FileCommon.getFileResp(JdbcMapUtil.getString(contractMap, "fileIds"), myJdbcTemplate);
            contractInfos.add(contractInfo);
        }
        responseData.contractInfos = contractInfos;
        List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
        responseData.total = totalList.size();
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
                "where hd.name in ('成本合约部','财务部门') and du.AD_USER_ID is not null");
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

    }

    public static class ResponseData{
        public List<ContractInfo> contractInfos;
        public Integer total;
    }

}

