package com.cisdi.pms.job.excel.imports;


import com.cisdi.pms.job.excel.model.ContractImportModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.Util;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dlt
 * @date 2023/1/6 周五
 */
@RestController
@RequestMapping("/contractAccount")
public class ContractAccountImport {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SneakyThrows
    @PostMapping("/import")
    public Map<String,Object> importData(MultipartFile file){
        //报错信息
        Map<String, List<Object>> errorMap = new HashMap<>();
        //项目报错信息: 不存在的项目
        errorMap.put("prjNotExist",new ArrayList<Object>());
        //读取所有的sheet页,表头为第3行,key为项目名
        Map<String, List<ContractImportModel>> sheets = EasyExcelUtil.readSheets(file.getInputStream(), ContractImportModel.class, 3);
        //整理为list
        List<ContractImportModel> models = this.arrange(sheets, errorMap);
        //插入数据
        this.insert(models);
        Map<String, Object> result = new HashMap<>();
        List<Object> prjNotExistList = errorMap.get("prjNotExist");
        if (CollectionUtils.isEmpty(errorMap.get("prjNotExist"))){
            result.put("code",200);
            result.put("message","导入成功！");
        }else {
            result.put("code",500);
            String message = "项目：";
            message.concat(String.join(",", (CharSequence) prjNotExistList));
            result.put("message",message);
        }
        return result;
    }

    /**
     * 插入 PO_ORDER_REQ合同签订、CONTRACT_SIGNING_CONTACT联系人明细
     * @param models
     */
    private void insert(List<ContractImportModel> models){
        for (ContractImportModel model : models) {
            //插入PO_ORDER_REQ合同签订
            String orderId = Util.insertData(jdbcTemplate, "PO_ORDER_REQ");
            jdbcTemplate.update("update PO_ORDER_REQ set PM_PRJ_ID = ?,set NAME = ?,set SIGN_DATE = ?,set AMT_TWO = ?,set REMARK_LONG_ONE = ? where id = ?",
                    model.getProjectId(),model.getContractName(),model.getSignDate(),model.getAmtIncludeTax(),model.getRemark(),orderId);
            //插入CONTRACT_SIGNING_CONTACT联系人明细
            String contactId = Util.insertData(jdbcTemplate, "CONTRACT_SIGNING_CONTACT");
            jdbcTemplate.update("update CONTRACT_SIGNING_CONTACT set PARENT_ID = ?,set WIN_BID_UNIT_ONE = ?,set OPPO_SITE_LINK_MAN = ? where id = ?"
                    ,orderId,model.getWinBidUnit(),model.getLinkMan(),contactId);
        }
    }

    /**
     * 检查是否有对应项目并整理为list
     * @param sheets 读出的sheet页
     * @param errorMap 报错信息
     * @return
     */
    private List<ContractImportModel> arrange(Map<String,List<ContractImportModel>> sheets,Map<String,List<Object>> errorMap){
        List<Map<String, Object>> prjList = jdbcTemplate.queryForList("select p.ID,p.NAME from pm_prj p left join gr_set_value v on v.ID = p.PROJECT_SOURCE_TYPE_ID where p.STATUS = 'AP' and v.code = 'system' and p.name is not null");
        List<String> prjNameList = prjList.stream().map(prj -> JdbcMapUtil.getString(prj, "NAME")).collect(Collectors.toList());
        //整理后的数据
        List<ContractImportModel> dataList = new ArrayList<>();
        for (String prjName:sheets.keySet()){
            if (!prjNameList.contains(prjName)){//没有找到该项目
                errorMap.get("prjNotExist").add(prjName);
            }else {
                //同一个项目的合同
                List<ContractImportModel> models = sheets.get(prjName);
                for (ContractImportModel model : models) {
                    if ("合计".equals(model.getContractName()) || "其他合同".equals(model.getContractName())){
                        continue;
                    }
                    model.setProjectName(prjName);
                    model.setProjectId(this.getPrjIdByName(prjList,prjName));
                    dataList.add(model);
                }
            }
        }
        return dataList;
    }

    /**
     * 通过项目名获取项目id
     * @param prjList
     * @return
     */
    private String getPrjIdByName(List<Map<String, Object>> prjList,String prjName){
        for (Map<String, Object> prjMap : prjList) {
            if (prjName.equals(JdbcMapUtil.getString(prjMap,"NAME"))){
                return JdbcMapUtil.getString(prjMap,"ID");
            }
        }
        return null;
    }
}
