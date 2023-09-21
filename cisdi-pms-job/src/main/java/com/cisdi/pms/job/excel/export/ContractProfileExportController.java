package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.excel.model.ContractProfileExportModel;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ContractProfileExportController
 * @package com.cisdi.pms.job.excel.export
 * @description 合同资料清单导出
 * @date 2023/9/21
 */
@RestController
@RequestMapping("contractProfile")
public class ContractProfileExportController extends BaseController {

    @Autowired
    private JdbcTemplate myJdbcTemplate;


    @SneakyThrows
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_prj where status='ap'");
        List<ContractProfileExportModel> objList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Map<String, Object> objectMap : list) {
//                String sql = "SET sql_mode = 'STRICT_TRANS_TABLES';" +
                String sql = "select i.id inventoryId,i.IS_INVOLVED isInvolved,i.remark,ty.name typeName,v1.name contractTypeName,v2.name buyMatterName," +
                        "GROUP_CONCAT(f.id) fileId, " +
                        "if(i.IS_INVOLVED = 1,SUBSTRING_INDEX(GROUP_CONCAT(f.DSP_NAME order by f.CRT_DT desc),',',1) ,'不涉及') fileName, " +
                        "SUBSTRING_INDEX(GROUP_CONCAT(f.DSP_SIZE order by f.CRT_DT desc),',',1) fileSize,SUBSTRING_INDEX(GROUP_CONCAT(f.UPLOAD_DTTM order " +
                        "by f.CRT_DT desc),',',1) uploadTime, " +
                        "SUBSTRING_INDEX(GROUP_CONCAT(u.name order by f.CRT_DT desc),',',1) uploadUser from prj_inventory i  " +
                        "left join prj_inventory_detail d on i.id = d.PRJ_INVENTORY_ID " +
                        "left join material_inventory_type ty on ty.id = i.MATERIAL_INVENTORY_TYPE_ID " +
                        "left join fl_file f on f.id = d.FL_FILE_ID " +
                        "left join ad_user u on u.id = f.CRT_USER_ID  " +
                        "left join gr_set_value v1 on v1.id = i.CONTRACT_CATEGORY_ONE_ID " +
                        "left join gr_set_value v2 on v2.id = i.BUY_MATTER_ID " +
                        "where v1.name is not null and i.PM_PRJ_ID = ?  " +
                        "group by i.id order by ty.SEQ_NO ";
                List<Map<String, Object>> list1 = myJdbcTemplate.queryForList(sql, objectMap.get("ID"));
                if (!CollectionUtils.isEmpty(list1)) {
                    for (Map<String, Object> map : list1) {
                        ContractProfileExportModel model = new ContractProfileExportModel();
                        model.setProjectName(JdbcMapUtil.getString(objectMap, "NAME"));
                        model.setContractType(JdbcMapUtil.getString(map, "contractTypeName"));
                        model.setContractItem(JdbcMapUtil.getString(map, "typeName"));
                        model.setFileName(JdbcMapUtil.getString(map, "fileName"));
                        objList.add(model);
                    }
                }
            }
        }
        super.setExcelRespProp(response, "合同资料清单");
        EasyExcel.write(response.getOutputStream())
                .head(ContractProfileExportModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("合同资料清单")
                .doWrite(objList);
    }
}
