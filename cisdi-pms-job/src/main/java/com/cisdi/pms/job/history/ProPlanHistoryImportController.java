package com.cisdi.pms.job.history;

import com.cisdi.pms.job.excel.export.BaseController;
import com.cisdi.pms.job.excel.model.ProPlanHistoryModel;
import com.cisdi.pms.job.utils.DateUtil;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.ReflectUtil;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
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
 * @title ProPlanHistoryImportController
 * @package com.cisdi.pms.job.history
 * @description 全景历史数据导入
 * @date 2023/7/5
 */
@Slf4j
@RestController
@RequestMapping("proPlanHis")
public class ProPlanHistoryImportController extends BaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public static final String NOT_STARTED = "0099799190825106800";//未开始
    public static final String IN_PROCESSING = "0099799190825106801";//进行中
    public static final String COMPLETED = "0099799190825106802";//已完成
    public static final String NOT_INVOLVE = "0099902212142036278";//未涉及

    @SneakyThrows(IOException.class)
    @RequestMapping("/import")
    public Map<String, Object> importData(MultipartFile file, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        List<ProPlanHistoryModel> list = EasyExcelUtil.read(file.getInputStream(), ProPlanHistoryModel.class);
        //去除空行
        List<ProPlanHistoryModel> planHistoryModelList = list.stream().filter(p -> !ReflectUtil.isObjectNull(p)).collect(Collectors.toList());
        //如果有不能处理的字段，响应提示
        List<String> res = new ArrayList<>();
        List<Map<String, Object>> prjList = jdbcTemplate.queryForList("select id,name from pm_prj where status = 'AP'");
        List<Map<String, Object>> userList = jdbcTemplate.queryForList("select id,name from ad_user where status = 'AP'");

        Map<String, List<ProPlanHistoryModel>> collect = planHistoryModelList.stream().collect(Collectors.groupingBy(ProPlanHistoryModel::getProjectName));
        for (String key : collect.keySet()) {
            Optional<Map<String, Object>> optional = prjList.stream().filter(m -> key.equals(JdbcMapUtil.getString(m, "name"))).findAny();
            if (optional.isPresent()) {
                Map<String, Object> proObj = optional.get();
                List<ProPlanHistoryModel> modelList = collect.get(key);
                List<Map<String, Object>> nodeList = jdbcTemplate.queryForList("select pn.* from pm_pro_plan_node pn left join pm_pro_plan pl on pn.PM_PRO_PLAN_ID = pl.ID where PM_PRJ_ID=? ", proObj.get("ID"));
                if (!CollectionUtils.isEmpty(nodeList)) {
                    modelList.forEach(item -> {
                        Optional<Map<String, Object>> currentNodeOpt = nodeList.stream().filter(o -> Objects.equals(item.getNodeName(), o.get("NAME"))).findAny();
                        if (currentNodeOpt.isPresent()) {
                            Map<String, Object> currentNode = currentNodeOpt.get();
                            StringBuilder sb = new StringBuilder();
                            sb.append("update pm_pro_plan_node set LAST_MODI_DT='").append(DateUtil.getNormalTimeStr(new Date())).append("'");
                            if (Strings.isNotEmpty(item.getUserName())) {
                                //查询人员
                                Optional<Map<String, Object>> userOpt = userList.stream().filter(u -> Objects.equals(u.get("NAME"), item.getUserName())).findAny();
                                if (userOpt.isPresent()) {
                                    Map<String, Object> userMap = userOpt.get();
                                    sb.append(", CHIEF_USER_ID='").append(userMap.get("ID")).append("'");
                                }
                            }
                            if (Strings.isNotEmpty(item.getPlanBegin())) {
                                sb.append(", PLAN_START_DATE ='").append(item.getPlanBegin()).append("'");
                            }
                            if (Strings.isNotEmpty(item.getPlanEnd())) {
                                sb.append(", PLAN_COMPL_DATE ='").append(item.getPlanEnd()).append("'");
                            }
                            if (Strings.isNotEmpty(item.getActualBegin())) {
                                sb.append(", ACTUAL_START_DATE ='").append(item.getActualBegin()).append("'");
                            }
                            if (Strings.isNotEmpty(item.getActualEnd())) {
                                sb.append(", ACTUAL_COMPL_DATE ='").append(item.getActualEnd()).append("'");
                            }
                            if (Strings.isNotEmpty(item.getIzOverdue())) {
                                String izOverdue = null;
                                if ("否".equals(item.getIzOverdue())) {
                                    izOverdue = "0";
                                } else if ("是".equals(item.getIzOverdue())) {
                                    izOverdue = "1";
                                }
                                sb.append(", IZ_OVERDUE ='").append(izOverdue).append("'");
                            }
                            if (Strings.isNotEmpty(item.getStatus())) {
                                String status = null;
                                switch (item.getStatus()) {
                                    case "未开始":
                                        status = NOT_STARTED;
                                        break;
                                    case "进行中":
                                        status = IN_PROCESSING;
                                        break;
                                    case "已完成":
                                        status = COMPLETED;
                                        break;
                                    case "未涉及":
                                        status = NOT_INVOLVE;
                                        break;
                                }
                                if (Strings.isNotEmpty(status)) {
                                    sb.append(", PROGRESS_STATUS_ID ='").append(status).append("'");
                                }
                            }
                            sb.append(" where id='").append(currentNode.get("ID")).append("'");
                            jdbcTemplate.update(sb.toString());
                        } else {
                            res.add("节点名称为:" + item.getNodeName() + "不存在，未导入！");
                        }
                    });
                }
            } else {
                res.add("项目名称为:" + key + "不存在，未导入！");
            }
        }

        if (CollectionUtils.isEmpty(res)) {
            result.put("code", 200);
            result.put("message", "导入成功！");
            return result;
        } else {
            super.exportTxt(response, res, "全景数据导入日志");
            return null;
        }
    }
}
