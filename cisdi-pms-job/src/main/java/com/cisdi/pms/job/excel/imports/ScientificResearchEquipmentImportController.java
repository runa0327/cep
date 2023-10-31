package com.cisdi.pms.job.excel.imports;

import com.cisdi.pms.job.excel.export.BaseController;
import com.cisdi.pms.job.excel.model.ScientificResearchEquipmentModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.ReflectUtil;
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
import java.math.BigDecimal;
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

    /**
     * 把已经存在于项目库中的设备项目数据转化城科研设备项目
     *
     * @param file
     * @param response
     * @return
     */
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
                String pmCode = getPmCode(belongId);
                Optional<Map<String, Object>> opt = list.stream().filter(m -> model.getName().equals(JdbcMapUtil.getString(m, "NAME"))).findAny();
                if (opt.isPresent()) {
                    String id = JdbcMapUtil.getString(opt.get(), "ID");
                    jdbcTemplate.update("update pm_prj set SUBORDINATE_PROJECT=?,EQUIPMENT_PURCHASE_BUDGET_AMOUNT=?,RESEARCH_RANGE=?,REPLY_NO=?,PRJ_SITUATION=?,PROJECT_CLASSIFICATION_ID=?,PM_CODE=? where ID=?",
                            belongId, new BigDecimal(10000).multiply(new BigDecimal(model.getBudgetAmount())), typeMap.get(model.getResearchRange()), model.getReplyNo(), model.getDescription(), "1704686735267102720", pmCode, id);
                } else {
                    res.add("项目：" + model.getName() + "不存在！");
                }
            } else {
                res.add("项目：" + model.getBeLongProject() + "不存在！");
            }
        }

        Map<String, Object> result = new HashMap<>();
        if (CollectionUtils.isEmpty(res)) {
            result.put("code", 200);
            result.put("message", "导入成功！");
            return result;
        } else {
            super.exportTxt(response, res, "科研设备项目导入日志");
            return null;
        }
    }

    private String getPmCode(String projectId) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from pm_prj where SUBORDINATE_PROJECT =?  order by pm_code desc", projectId);
        if (CollectionUtils.isEmpty(list)) {
            List<Map<String, Object>> list1 = jdbcTemplate.queryForList("select * from pm_prj where id =?", projectId);
            Map<String, Object> dataMap = list1.get(0);
            return JdbcMapUtil.getString(dataMap, "PM_CODE") + "-001";
        } else {
            String pmCode = JdbcMapUtil.getString(list.get(0), "PM_CODE");
            String leftPart = pmCode.substring(0, pmCode.length() - 4);
            String rightPart = pmCode.substring(pmCode.length() - 3);
            int count = Integer.parseInt(rightPart) + 1;
            return leftPart + "-" + String.format("%03d", count);
        }
    }
}
