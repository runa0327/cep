package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.cisdi.pms.job.excel.model.ContractAccountModel;
import com.cisdi.pms.job.excel.model.request.ContractReq;
import com.cisdi.pms.job.utils.StringUtil;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
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
//        String loginUserId = this.getLoginUser(request.getHeader("qygly-session-id"));
//        List<String> rootUsers = this.getRootUsers();
        String sql = "SELECT A.PM_PRJ_ID AS prjId,B.NAME AS prjName,A.CONTRACT_NAME AS contractName,A.CONTRACT_APP_ID as id," +
                "A.CONTRACT_CODE AS contractCode,A.CUSTOMER_UNIT as contractCompanyId,D.NAME AS contractCompanyName," +
                "A.WIN_BID_UNIT_ONE AS cooperationUnit,A.CONTRACT_CATEGORY_ONE_ID as contractCategoryId," +
                "C.NAME AS contractCategoryName,A.AMT_FIVE AS amtExcludeTax,A.AMT_ONE AS taxRate," +
                "A.AMT_SIX AS amtIncludeTax,A.SIGN_DATE AS createTime,A.DATE_FIVE AS expireDate,A.FILE_ID_ONE AS fileIds " +
                "FROM PO_ORDER A " +
                "LEFT JOIN PM_PRJ B ON A.PM_PRJ_ID = B.ID " +
                "LEFT JOIN GR_SET_VALUE C ON A.CONTRACT_CATEGORY_ONE_ID = C.ID " +
                "LEFT JOIN pm_party D ON A.CUSTOMER_UNIT = D.ID " +
                "WHERE A.STATUS = 'AP' and ORDER_DATA_SOURCE_TYPE = '1630087650826432512' ";
        StringBuffer sb = new StringBuffer(sql);
//        if (!rootUsers.contains(loginUserId)){
//            sb.append(" and FIND_IN_SET('").append(loginUserId).append("',temp.USER_IDS)");
//            sb.append(" and IFNULL(o.PM_PRJ_ID,p2.id) in (select DISTINCT pm_prj_id from pm_dept WHERE STATUS = 'ap' and FIND_IN_SET('").append(loginUserId).append("', USER_IDS ))");
//        }
        // 项目id
        if (!SharedUtil.isEmptyString(requestParam.getPrjId())){
            sb.append(" and a.pm_prj_id = '").append(requestParam.getPrjId()).append("' ");
        }
        // 合同名称
        if (!SharedUtil.isEmptyString(requestParam.getContractName())){
            sb.append(" and a.CONTRACT_NAME like '%").append(requestParam.getContractName()).append("%'");
        }
        // 合同签订公司id
        if (!SharedUtil.isEmptyString(requestParam.getContractCompanyId())){
            sb.append(" and A.CUSTOMER_UNIT = '").append(requestParam.getContractCompanyId()).append("'");
        }
        // 合同类型
        if (!SharedUtil.isEmptyString(requestParam.getContractCategoryId())){
            sb.append(" and A.CONTRACT_CATEGORY_ONE_ID = '").append(requestParam.getContractCategoryId()).append("'");
        }
        if (Strings.isNotEmpty(requestParam.getCooperationUnit())){
            sb.append(" and o.WIN_BID_UNIT_ONE like '%").append(requestParam.getCooperationUnit()).append("%'");
        }
        // 最小含税金额
        if (!SharedUtil.isEmptyString(requestParam.getAmtIncludeTaxStart())){
            sb.append(" and A.AMT_SIX >= '").append(requestParam.getAmtIncludeTaxStart()).append("'");
        }
        // 最大含税金额
        if (!SharedUtil.isEmptyString(requestParam.getAmtIncludeTaxEnd())){
            sb.append(" and A.AMT_SIX <= '").append(requestParam.getAmtIncludeTaxEnd()).append("'");
        }
        // 最小签订时间
        if (!SharedUtil.isEmptyString(requestParam.getCreateTimeStart())){
            sb.append(" and A.SIGN_DATE >= '").append(requestParam.getCreateTimeStart()).append("'");
        }
        // 最大签订时间
        if (!SharedUtil.isEmptyString(requestParam.getCreateTimeEnd())){
            sb.append(" and A.SIGN_DATE <= '").append(requestParam.getCreateTimeEnd()).append("'");
        }

        sb.append(" order by A.ID DESC ");
        List<Map<String, Object>> contractList = myJdbcTemplate.queryForList(sb.toString());
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
