package com.cisdi.pms.job.history;

import com.cisdi.pms.job.excel.export.BaseController;
import com.cisdi.pms.job.utils.StringUtils;
import com.qygly.shared.util.DateTimeUtil;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
 * @title PlanOpreationController
 * @package com.cisdi.pms.job.history
 * @description
 * @date 2023/5/23
 */
@Slf4j
@RestController
@RequestMapping("plan")
public class PlanOperationController extends BaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final String NOT_STARTED = "0099799190825106800";
    public static final String IN_PROCESSING = "0099799190825106801";
    public static final String COMPLETED = "0099799190825106802";

    /**
     * 全景计划导入
     *
     * @param file
     * @param response
     * @return
     */
    @SneakyThrows(IOException.class)
    @RequestMapping("/import")
    public Map<String, Object> importData(MultipartFile file, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        //如果有不能处理的字段，响应提示
        List<String> res = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Row firstRow = sheet.getRow(sheet.getFirstRowNum()); //获取第一行数据
        if (null == firstRow) { //判断第一行数据是否为空
            res.add("第一行没有读取到任何数据！");
        }
        int rowStart = sheet.getFirstRowNum() + 1; //根据模板确定开始解析内容的行标
        int rowEnd = sheet.getPhysicalNumberOfRows(); //结束的行标

        for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
            Row row = sheet.getRow(rowNum); //根据下标逐次获取行对象
            List<String> singleRes = dealWithData(firstRow, row);
            if (!CollectionUtils.isEmpty(singleRes)) {
                res.addAll(singleRes);
            }
        }


        if (CollectionUtils.isEmpty(res)) {
            result.put("code", 200);
            result.put("message", "导入成功！");
            return result;
        } else {
            super.exportTxt(response, res, "全景历史数据导入日志");
            return null;
        }
    }

    /**
     * 数据处理
     *
     * @param headRow
     * @param dataRow
     * @return
     */
    public List<String> dealWithData(Row headRow, Row dataRow) {
        List<String> res = new ArrayList<>();
        Cell cell = dataRow.getCell(1);
        if (cell != null) {
            String projectName = cell.toString();
            List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from pm_prj where name =?", projectName);
            if (!CollectionUtils.isEmpty(list)) {
                Map<String, Object> dataMap = list.get(0);
                String projectId = JdbcMapUtil.getString(dataMap, "ID");
                int rowNum1 = dataRow.getLastCellNum() - dataRow.getFirstCellNum();
                for (int i = 2; i < rowNum1; i++) {
                    String headOrg = headRow.getCell(i).toString();
                    List<Map<String, Object>> nodeList = jdbcTemplate.queryForList("select pn.* from pm_pro_plan_node pn left join pm_pro_plan pl on pn.PM_PRO_PLAN_ID = pl.ID where PM_PRJ_ID=? and pn.`NAME`=?", projectId, headOrg);
                    if (!CollectionUtils.isEmpty(nodeList)) {
                        Map<String, Object> nodeMap = nodeList.get(0);
                        Object obj = dataRow.getCell(i);
                        if (null != obj) {
                            if (!"/".equals(obj.toString())) {
                                if ("完成".equals(obj.toString())) {
                                    jdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID=? where id=?", COMPLETED, nodeMap.get("ID"));
                                } else {
                                    String[] split = obj.toString().split("\\.");
                                    String dateOrg = split[0] + "-" + StringUtils.addZeroForNum(split[1], 2) + "-" + split[2];
                                    Date comDate = DateTimeUtil.stringToDate(dateOrg);
                                    jdbcTemplate.update("update pm_pro_plan_node set ACTUAL_COMPL_DATE=?,PROGRESS_STATUS_ID=? where id=?", comDate, COMPLETED, nodeMap.get("ID"));
                                }
                            }
                        }

                    } else {
                        res.add("序号为:" + dataRow.getCell(0) + "的数据，全景节点【" + headOrg + "】不存在！");
                    }

                }
                for (int i = 0; i < 5; i++) {
                    refreshNodeStatus(projectId);
                }
            } else {
                res.add("序号为:" + dataRow.getCell(0) + "的数据，项目不存在！");
            }

        } else {
            res.add("序号为:" + dataRow.getCell(0) + "的数据，项目不存在！");
        }
        return res;
    }

    private void refreshNodeStatus(String projectId) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select pn.*,ifnull(PM_PRO_PLAN_NODE_PID,'0') as pid,ifnull(pn.PROGRESS_STATUS_ID,'0') as statusOrg from pm_pro_plan_node pn left join pm_pro_plan pl on pn.PM_PRO_PLAN_ID = pl.id where PM_PRJ_ID=?", projectId);
        list.stream().filter(p -> "0".equals(JdbcMapUtil.getString(p, "pid"))).peek(m -> {
            List<Map<String, Object>> children = getChildren(m, list);
            if (!CollectionUtils.isEmpty(children)) {
                int total = children.size();
                int statusOrg1 = (int) children.stream().filter(l -> NOT_STARTED.equals(JdbcMapUtil.getString(l, "statusOrg"))).count();
                int statusOrg3 = (int) children.stream().filter(l -> COMPLETED.equals(JdbcMapUtil.getString(l, "statusOrg"))).count();
                if (total == statusOrg3) {
                    jdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID=? where id=?", COMPLETED, m.get("ID"));
                } else {
                    if (statusOrg1 != total) {
                        jdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID=? where id=?", IN_PROCESSING, m.get("ID"));
                    }
                }
            }
        }).collect(Collectors.toList());
    }


    private List<Map<String, Object>> getChildren(Map<String, Object> parent, List<Map<String, Object>> allData) {
        return allData.stream().filter(p -> JdbcMapUtil.getString(parent, "ID").equals(JdbcMapUtil.getString(p, "pid"))).peek(m -> {
            List<Map<String, Object>> children = getChildren(m, allData);
            if (!CollectionUtils.isEmpty(children)) {
                int total = children.size();
                int statusOrg1 = (int) children.stream().filter(l -> NOT_STARTED.equals(JdbcMapUtil.getString(l, "statusOrg"))).count();
                int statusOrg3 = (int) children.stream().filter(l -> COMPLETED.equals(JdbcMapUtil.getString(l, "statusOrg"))).count();
                if (total == statusOrg3) {
                    jdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID=? where id=?", COMPLETED, m.get("ID"));
                } else {
                    if (statusOrg1 != total) {
                        jdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID=? where id=?", IN_PROCESSING, m.get("ID"));
                    }
                }
            }
        }).collect(Collectors.toList());
    }
}
