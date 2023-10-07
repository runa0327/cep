package com.cisdi.pms.job.excel.imports;

import com.cisdi.pms.job.excel.export.BaseController;
import com.cisdi.pms.job.excel.model.ScientificResearchEquipmentModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.ReflectUtil;
import com.cisdi.pms.job.utils.Util;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
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
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ScientificResearchEquipmentImportController
 * @package com.cisdi.pms.job.excel.imports
 * @description
 * @date 2023/9/28
 */
@RestController
@RequestMapping("/scientificResearchEquipment")
public class ScientificResearchEquipmentImportController extends BaseController {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    private final static Map<String, String> typeMap = new HashMap<>();

    static {
        typeMap.put("范围内", "1707205325321089024");
        typeMap.put("范围外", "1707205346074505216");
    }

    @SneakyThrows(IOException.class)
    @RequestMapping(value = "/import")
    public Map<String, Object> importData(MultipartFile file, HttpServletResponse response) {
        List<String> res = new ArrayList<>();
        List<ScientificResearchEquipmentModel> equipmentModelList = EasyExcelUtil.read(file.getInputStream(), ScientificResearchEquipmentModel.class);
        //去除空行
        List<ScientificResearchEquipmentModel> modelList = equipmentModelList.stream().filter(p -> !ReflectUtil.isObjectNull(p)).collect(Collectors.toList());
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from pm_prj where status ='ap'");
        for (ScientificResearchEquipmentModel model : modelList) {
            Optional<Map<String, Object>> optional = list.stream().filter(p -> model.getBeLongProject().equals(JdbcMapUtil.getString(p, "NAME"))).findAny();
            if (optional.isPresent()) {
                Map<String, Object> dataMap = optional.get();
                String belongId = JdbcMapUtil.getString(dataMap, "ID");
                String pmCode = getPmCode(JdbcMapUtil.getString(dataMap, "PM_CODE"));
                String id = Util.insertData(jdbcTemplate, "pm_prj");
                jdbcTemplate.update("update pm_prj set `NAME`=?,SUBORDINATE_PROJECT=?,EQUIPMENT_PURCHASE_BUDGET_AMOUNT=?,RESEARCH_RANGE=?,REPLY_NO=?,PRJ_SITUATION=?,PROJECT_CLASSIFICATION_ID=?,PM_CODE=? where ID=?",
                        model.getName(), belongId, model.getBudgetAmount(), typeMap.get(model.getResearchRange()), model.getReplyNo(), model.getDescription(), "1704686735267102720", pmCode, id);
            }
        }

        Map<String, Object> result = new HashMap<>();
        if (CollectionUtils.isEmpty(res)) {
            result.put("code", 200);
            result.put("message", "导入成功！");
            return result;
        } else {
            super.exportTxt(response, res, "项目纳统填报日志");
            return null;
        }
    }

    private String getPmCode(String code) {
        return null;
    }

}
