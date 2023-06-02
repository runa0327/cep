package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.cisdi.pms.job.excel.model.ContractAccountModel;
import com.cisdi.pms.job.excel.model.request.ContractReq;
import com.cisdi.pms.job.utils.StringUtil;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dlt
 * @date 2022/12/23 周五
 */
@RestController
@RequestMapping("contract")
public class ContractAccountController extends BaseController{
    @Autowired
    private JdbcTemplate myJdbcTemplate;

    @SneakyThrows
    @GetMapping("export")
    public void contractExcel(ContractReq requestParam, HttpServletResponse response, HttpServletRequest request){
        String loginUserId = this.getLoginUser(request.getHeader("qygly-session-id"));
        List<String> rootUsers = this.getRootUsers();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT o.id,IFNULL(o.PM_PRJ_ID,p2.id) prjId,IFNULL(p.name,o.PROJECT_NAME_WR) prjName,o.CONTRACT_NAME contractName,o" +
                ".CUSTOMER_UNIT_ONE contractCompanyId,pa.name contractCompanyName,temp.cooperationUnit,o.CONTRACT_CATEGORY_ONE_ID " +
                "contractCategoryId,va.name contractCategoryName,o.AMT_THREE amtExcludeTax,o.AMT_FOUR taxRate,o.AMT_TWO amtIncludeTax,o.SIGN_DATE " +
                "createTime,i.END_DATETIME endTime,o.REMARK_LONG_ONE remark,o.CRT_USER_ID userId,u.name userName,o.FILE_ID_FIVE fileIds\n" +
                "FROM PO_ORDER_REQ o\n" +
                "left join pm_prj p on p.id = o.PM_PRJ_ID\n" +
                "left join pm_prj p2 on p2.name = o.PROJECT_NAME_WR\n" +
                "left join pm_party pa on pa.id = o.CUSTOMER_UNIT_ONE\n" +
                "left join gr_set_value va on va.id = o.CONTRACT_CATEGORY_ONE_ID\n" +
                "left join gr_set se on se.id = va.GR_SET_ID and se.code = 'contract_type_one'\n" +
                "left join ad_user u on u.id = o.CRT_USER_ID\n" +
                "left join wf_process_instance i on i.id = o.LK_WF_INST_ID\n" +
                "left join (select o.id,GROUP_CONCAT(c.WIN_BID_UNIT_ONE) cooperationUnit from po_order_req o left join contract_signing_contact c " +
                "on c.PARENT_ID = o.id group by o.id) temp on temp.id = o.id\n" +
                "where o.STATUS = 'AP'");
        if (!rootUsers.contains(loginUserId)){
//            sb.append(" and FIND_IN_SET('").append(loginUserId).append("',temp.USER_IDS)");
            sb.append(" and IFNULL(o.PM_PRJ_ID,p2.id) in (select DISTINCT pm_prj_id from pm_dept WHERE STATUS = 'ap' and FIND_IN_SET('").append(loginUserId).append("', USER_IDS ))");
        }
        if (Strings.isNotEmpty(requestParam.getPrjId())){
            sb.append(" and IFNULL(o.PM_PRJ_ID,p2.id) = '").append(requestParam.getPrjId()).append("'");
        }
        if (Strings.isNotEmpty(requestParam.getContractName())) {
            sb.append(" and o.CONTRACT_NAME like '%").append(requestParam.getContractName()).append("%'");
        }
        if (Strings.isNotEmpty(requestParam.getContractCompanyId())){
            sb.append(" and o.CUSTOMER_UNIT_ONE = '").append(requestParam.getContractCompanyId()).append("'");
        }
        if (Strings.isNotEmpty(requestParam.getCooperationUnit())){
            sb.append(" and o.WIN_BID_UNIT_ONE like '%").append(requestParam.getCooperationUnit()).append("%'");
        }
        if (Strings.isNotEmpty(requestParam.getContractCategoryId())){
            sb.append(" and o.CONTRACT_CATEGORY_ONE_ID = '").append(requestParam.getContractCategoryId()).append("'");
        }
        if (Strings.isNotEmpty(requestParam.getAmtExcludeTaxStart()) && Strings.isNotEmpty(requestParam.getAmtExcludeTaxEnd())) {
            sb.append(" and o.AMT_THREE between '").append(requestParam.getAmtExcludeTaxStart()).append("' and '").append(requestParam.getAmtExcludeTaxEnd()).append("'");
        }
        if (Strings.isNotEmpty(requestParam.getAmtIncludeTaxStart()) && Strings.isNotEmpty(requestParam.getAmtIncludeTaxEnd())) {
            sb.append(" and o.AMT_TWO between '").append(requestParam.getAmtIncludeTaxStart()).append("' and '").append(requestParam.getAmtIncludeTaxEnd()).append("'");
        }
        if (Strings.isNotEmpty(requestParam.getCreateTimeStart()) && Strings.isNotEmpty(requestParam.getCreateTimeEnd())) {
            sb.append(" and o.CRT_DT between '").append(requestParam.getCreateTimeStart()).append("' and '").append(requestParam.getCreateTimeEnd()).append("'");
        }
        if (Strings.isNotEmpty(requestParam.getUserId())){
            sb.append(" and o.CRT_USER_ID = '").append(requestParam.getUserId()).append("'");
        }

        sb.append(" order by o.CRT_DT desc");
        String sql = sb.toString();
        List<Map<String, Object>> contractList = myJdbcTemplate.queryForList(sql);
        List<ContractAccountModel> models = new ArrayList<>();
        for (Map<String, Object> contractMap : contractList) {
            ContractAccountModel model = JSONObject.parseObject(JSONObject.toJSONString(contractMap), ContractAccountModel.class);
            model.setCreateTime(StringUtil.getDateWithOutT(model.getCreateTime()));
            models.add(model);
        }

        super.setExcelRespProp(response,"合同台账");
        EasyExcel.write(response.getOutputStream())
                .head(ContractAccountModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("合同台账")
                .doWrite(models);

    }

    /**
     * 获取拥有所有权限用户id
     */
    private List<String> getRootUsers(){
        List<Map<String, Object>> rootList = myJdbcTemplate.queryForList("select du.AD_USER_ID from hr_dept hd\n" +
                "left join hr_dept_user du on du.HR_DEPT_ID = hd.id\n" +
                "where hd.name in ('成本合约部','财务金融部') and du.AD_USER_ID is not null");
        List<String> rootUsers = rootList.stream().map(item -> String.valueOf(item.get("AD_USER_ID"))).collect(Collectors.toList());
        rootUsers.add("0099250247095871681");//系统管理员
        return rootUsers;
    }

    /**
     * 通过session id获取用户id
     */
    private String getLoginUser(String value){
        String[] strings = value.split("_");
        String phone = strings[1];
        List<Map<String, Object>> userList = myJdbcTemplate.queryForList("select id from ad_user where code = ?", phone);
        if (CollectionUtils.isEmpty(userList)){
            return null;
        }
        return JdbcMapUtil.getString(userList.get(0),"id");
    }

}
