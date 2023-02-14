package com.cisdi.pms.job.excel.imports;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.excel.export.BaseController;
import com.cisdi.pms.job.excel.model.ProjectRosterModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.ReflectUtil;
import com.cisdi.pms.job.utils.Util;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
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
 * @title ProjectRosterImportController
 * @package com.cisdi.pms.job.excel.imports
 * @description 项目花名册导入
 * @date 2023/2/14
 */
@RestController
@RequestMapping("/projectRoster")
public class ProjectRosterImportController extends BaseController {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SneakyThrows(IOException.class)
    @GetMapping("exportTemplate")
    public void exportExcel(HttpServletResponse response) {
        super.setExcelRespProp(response, "项目花名册");
        EasyExcel.write(response.getOutputStream())
                .head(ProjectRosterModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("项目花名册")
                .doWrite(new ArrayList<>());
    }


    @SneakyThrows(IOException.class)
    @RequestMapping("/import")
    public Map<String, Object> importData(MultipartFile file, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        List<ProjectRosterModel> list = EasyExcelUtil.read(file.getInputStream(), ProjectRosterModel.class);
        //去除空行
        List<ProjectRosterModel> rosterModelList = list.stream().filter(p -> !ReflectUtil.isObjectNull(p)).collect(Collectors.toList());
        //如果有不能处理的字段，响应提示
        List<String> res = new ArrayList<>();
        List<Map<String, Object>> prjList = jdbcTemplate.queryForList("select id,name from pm_prj where status = 'AP'");

        List<Map<String, Object>> userList = jdbcTemplate.queryForList("select id,name from ad_user where status = 'AP'");

        for (int i = rosterModelList.size() - 1; i >= 0; i--) {//保证列表显示顺序和表格顺序一致
            List<String> singleRes = this.insertData(rosterModelList.get(i), prjList, userList);
            if (!CollectionUtils.isEmpty(singleRes)) {
                res.addAll(singleRes);
            }
        }

        if (CollectionUtils.isEmpty(res)) {
            result.put("code", 200);
            result.put("message", "导入成功！");
            return result;
        } else {
            super.exportTxt(response, res, "项目岗位导入日志");
            return null;
        }
    }

    private List<String> insertData(ProjectRosterModel model, List<Map<String, Object>> prjList, List<Map<String, Object>> userList) {
        List<String> res = new ArrayList<>();
        if (model == null) {
            return null;
        }
        //项目 先判断项目没有对应项目直接返回
        Optional<Map<String, Object>> optional = prjList.stream().filter(p -> String.valueOf(p.get("name")).equals(model.getProjectName())).findAny();
        if (!optional.isPresent()) {
            res.add("项目名称为:" + model.getProjectName() + "不存在，未导入！");
            return res;
        }
        String prjId = String.valueOf(optional.get().get("id"));

        Optional<Map<String, Object>> userOptional = userList.stream().filter(p -> String.valueOf(p.get("name")).equals(model.getUserName())).findAny();
        if (!userOptional.isPresent()) {
            res.add("用户名称为:" + model.getUserName() + "不存在，未导入！");
            return res;
        }
        String userId = String.valueOf(userOptional.get().get("id"));

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from PM_ROSTER where PM_PRJ_ID=? and PROJECT_POST=?", prjId, model.getPostName());
        if (!CollectionUtils.isEmpty(list)) {
            jdbcTemplate.update("update PM_ROSTER set AD_USER_ID=? where id=?", userId, list.get(0).get("ID"));
        } else {
            String id = Util.insertData(jdbcTemplate, "PM_ROSTER");
            jdbcTemplate.update("update PM_ROSTER set PM_PRJ_ID =?,PROJECT_POST=?,AD_USER_ID=? where id=?", prjId, model.getPostName(), userId, id);
        }
        return res;
    }
}
