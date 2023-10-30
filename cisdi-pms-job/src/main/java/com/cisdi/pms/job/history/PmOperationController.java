package com.cisdi.pms.job.history;

import com.cisdi.pms.job.excel.export.BaseController;
import com.cisdi.pms.job.excel.model.PmDateTimeModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.ReflectUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
 * @title PmOperationController
 * @package com.cisdi.pms.job.history
 * @description
 * @date 2023/8/30
 */
@Slf4j
@RestController
@RequestMapping("pm")
public class PmOperationController extends BaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 项目实际开工/竣工日期导入
     *
     * @param file
     * @param response
     * @return
     */
    @SneakyThrows(IOException.class)
    @RequestMapping("/import")
    public Map<String, Object> importData(MultipartFile file, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        List<PmDateTimeModel> list = EasyExcelUtil.read(file.getInputStream(), PmDateTimeModel.class);
        //去除空行
        List<PmDateTimeModel> modelList = list.stream().filter(p -> !ReflectUtil.isObjectNull(p)).collect(Collectors.toList());
        //如果有不能处理的字段，响应提示
        List<String> res = new ArrayList<>();
        List<Map<String, Object>> prjList = jdbcTemplate.queryForList("select id,name from pm_prj where status = 'AP'");

        for (int i = modelList.size() - 1; i >= 0; i--) {//保证列表显示顺序和表格顺序一致
            List<String> singleRes = this.insertData(modelList.get(i), prjList);
            if (!CollectionUtils.isEmpty(singleRes)) {
                res.addAll(singleRes);
            }
        }

        if (CollectionUtils.isEmpty(res)) {
            result.put("code", 200);
            result.put("message", "导入成功！");
            return result;
        } else {
            super.exportTxt(response, res, "项目开工/完工时间导入日志");
            return null;
        }
    }

    private List<String> insertData(PmDateTimeModel model, List<Map<String, Object>> prjList) {
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
        jdbcTemplate.update("update PM_PRJ set ACTUAL_START_TIME =?,ACTUAL_END_TIME=? where id=?", "/".equals(model.begin) ? null : model.begin, "/".equals(model.end) ? null : model.end, prjId);
        return res;
    }
}
