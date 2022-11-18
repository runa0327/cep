package com.cisdi.pms.job.excel.imports;

import com.cisdi.pms.job.excel.model.FundSpecialModel;
import com.cisdi.pms.job.excel.model.PmDeptUserModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.Util;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmDeptUserImportController
 * @package com.cisdi.pms.job.excel.imports
 * @description
 * @date 2022/11/18
 */
@RestController
@RequestMapping(value = "/pmDept")
public class PmDeptUserImportController {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @SneakyThrows(IOException.class)
    @RequestMapping("/import")
    public Map<String, Object> importData(MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        List<String> res = new ArrayList<>();
        List<PmDeptUserModel> dataList = EasyExcelUtil.read(file.getInputStream(), PmDeptUserModel.class);
        Map<String, List<PmDeptUserModel>> pmMapData = dataList.stream().collect(Collectors.groupingBy(PmDeptUserModel::getProjectName));
        List<Map<String, Object>> projectList = jdbcTemplate.queryForList("select * from PM_PRJ where `status` = 'ap'");
        for (String pk : pmMapData.keySet()) {
            Optional<Map<String, Object>> pjOptional = projectList.stream().filter(q -> Objects.equals(pk, JdbcMapUtil.getString(q, "NAME"))).findAny();
            if (pjOptional.isPresent()) {
                List<PmDeptUserModel> ownerData = pmMapData.get(pk);
                Map<String, List<PmDeptUserModel>> mapData = ownerData.stream().collect(Collectors.groupingBy(PmDeptUserModel::getDeptName));
                List<Map<String, Object>> deptList = jdbcTemplate.queryForList("select * from hr_dept where `status` = 'ap'");
                List<Map<String, Object>> userList = jdbcTemplate.queryForList("select * from ad_user where `status` = 'ap'");
                for (String key : mapData.keySet()) {
                    Optional<Map<String, Object>> optional = deptList.stream().filter(p -> Objects.equals(key, JdbcMapUtil.getString(p, "NAME"))).findAny();
                    if (optional.isPresent()) {
                        List<PmDeptUserModel> userModelList = mapData.get(key);
                        List<String> userIds = new ArrayList<>();
                        for (PmDeptUserModel pmDeptUserModel : userModelList) {
                            Optional<Map<String, Object>> userOptional = userList.stream().filter(m -> Objects.equals(pmDeptUserModel.getUserName(), JdbcMapUtil.getString(m, "NAME"))).findAny();
                            if (userOptional.isPresent()) {
                                userIds.add(String.valueOf(userOptional.get().get("ID")));
                            } else {
                                res.add("用户名称为：" + pmDeptUserModel.getUserName());
                            }
                        }
                        String projectId = String.valueOf(pjOptional.get().get("ID"));
                        String id = Util.insertData(jdbcTemplate, "PM_DEPT");
                        jdbcTemplate.update("update PM_DEPT set PM_PRJ_ID=? ,HR_DEPT_ID=?,USER_IDS=? where id=?", projectId, optional.get().get("ID"), String.join(",", userIds), id);
                    } else {
                        res.add("部门名称为：" + key);
                    }
                }
            } else {
                res.add("项目名称为：" + pk);
            }

        }


        if (CollectionUtils.isEmpty(res)) {
            result.put("code", 200);
            result.put("message", "导入成功！");
            return result;
        } else {
            result.put("code", 500);
            result.put("message", String.join(",", res) + "不存在，未导入！");
            return result;
        }
    }
}
