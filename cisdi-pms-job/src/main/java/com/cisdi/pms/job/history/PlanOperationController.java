package com.cisdi.pms.job.history;

import com.cisdi.pms.job.excel.export.BaseController;
import com.cisdi.pms.job.utils.StringUtil;
import com.cisdi.pms.job.utils.Util;
import com.qygly.shared.util.DateTimeUtil;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
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
    public static final String NOT_INVOLVE = "0099902212142036278";//未涉及

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
        //先判断项目名称是不是为空
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
                    Cell cellObj = dataRow.getCell(i);
                    String bgColor = null;//背景颜色
                    CellStyle cellStyle = dataRow.getCell(i).getCellStyle();
                    XSSFColor xssfColor = (XSSFColor) cellStyle.getFillForegroundColorColor();
                    byte[] bytes;
                    if (xssfColor != null) {
                        bytes = xssfColor.getRGB();
                        bgColor = String.format("#%02X%02X%02X", bytes[0], bytes[1], bytes[2]);
                    }

                    if (!isEmptyCell(cellObj)) {
                        if ("备注".equals(headOrg)) {
                            String projectRemark = null;
                            projectRemark = cellObj.toString();
                            insertRemark(projectId, null, projectRemark, "1");
                        } else {
                            List<Map<String, Object>> nodeList = jdbcTemplate.queryForList("select pn.* from pm_pro_plan_node pn left join pm_pro_plan pl on pn.PM_PRO_PLAN_ID = pl.ID where PM_PRJ_ID=? and pn.`NAME`=?", projectId, headOrg);
                            if (!CollectionUtils.isEmpty(nodeList)) {
                                Map<String, Object> nodeMap = nodeList.get(0);
                                //先判断颜色,如果为黄色，节点为超期未完成
                                if ("#FFFF00".equals(bgColor)) {
                                    jdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID=?,IZ_OVERDUE='1' where id=?", NOT_STARTED, nodeMap.get("ID"));
                                } else {
                                    if ("未涉及".equals(cellObj.toString())) {
                                        jdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID=? where id=?", NOT_INVOLVE, nodeMap.get("ID"));
                                    } else {
                                        String[] split = cellObj.toString().split("\\.");
                                        System.out.println("内容为：" + headOrg + ":---:" + cellObj);
                                        String dateOrg = split[0] + "-" + StringUtil.addZeroForNum(split[1], 2) + "-" + split[2];
                                        Date comDate = DateTimeUtil.stringToDate(dateOrg);
                                        if (comDate.before(new Date())) {
                                            jdbcTemplate.update("update pm_pro_plan_node set ACTUAL_COMPL_DATE=?,PROGRESS_STATUS_ID=? where id=?", comDate, COMPLETED, nodeMap.get("ID"));
                                        } else {
                                            jdbcTemplate.update("update pm_pro_plan_node set PLAN_COMPL_DATE=?,PROGRESS_STATUS_ID=? where id=?", comDate, NOT_STARTED, nodeMap.get("ID"));
                                        }
                                    }
                                }
                                //处理批注
                                Comment cellComment = dataRow.getCell(i).getCellComment();
                                if (cellComment != null) {
                                    String commentStr = cellComment.getString().toString();
                                    insertRemark(projectId, JdbcMapUtil.getString(nodeMap, "SCHEDULE_NAME"), commentStr, "2");
                                }


                            } else {
                                res.add("序号为:" + dataRow.getCell(0) + "的数据，全景节点【" + headOrg + "】不存在！");
                            }
                        }
                    }
                }
                for (int i = 0; i < 5; i++) {
                    refreshNodeStatus(projectId);
                }
                //发送任务
//                insertWeekTask(projectId);
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

    /**
     * 新增备注信息
     *
     * @param projectId
     * @param baseNodeId
     * @param content
     * @param type       1-项目备注 2-节点备注
     */
    private void insertRemark(String projectId, String baseNodeId, String content, String type) {
        String id = Util.insertData(jdbcTemplate, "REMARK_INFO");
        if ("1".equals(type)) {
            jdbcTemplate.update("update REMARK_INFO set PM_PRJ_ID=?,CONTENT=?,SUBMIT_TIME=?,REMARK_TYPE=1 where id=?", projectId, content, new Date(), id);
        } else {
            jdbcTemplate.update("update REMARK_INFO set PM_PRJ_ID=?,CONTENT=?,SUBMIT_TIME=?,REMARK_TYPE=2,SCHEDULE_NAME=? where id=?", projectId, content, new Date(), baseNodeId, id);
        }

    }


    private void insertWeekTask(String projectId) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select pn.* from pm_pro_plan_node pn left join pm_pro_plan pl on pn.PM_PRO_PLAN_ID = pl.id where level=3 and pn.PROGRESS_STATUS_ID= '0099799190825106802' and  PM_PRJ_ID=? ", projectId);
        List<Map<String, Object>> taskList = jdbcTemplate.queryForList("select * from week_task where WEEK_TASK_TYPE_ID='1635080848313290752' and PM_PRJ_ID=?", projectId);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(item -> {
                //查询项目后置节点(状态是未开始的)
                List<Map<String, Object>> preList = jdbcTemplate.queryForList("select pm.`NAME` as prjName,pppn.*,pi.AD_USER_ID as default_user,pm.id as projectId,pppn.PLAN_COMPL_DATE as PLAN_COMPL_DATE from pm_pro_plan_node pppn \n" +
                        "left join pm_pro_plan ppp on ppp.id = pppn.PM_PRO_PLAN_ID \n" +
                        "left join pm_prj pm on pm.id = ppp.PM_PRJ_ID  \n" +
                        "left join POST_INFO pi on pi.id = pppn.POST_INFO_ID  \n" +
                        "where pppn.PROGRESS_STATUS_ID = '0099799190825106800' and  PRE_NODE_ID =?", item.get("ID"));
                if (!CollectionUtils.isEmpty(preList)) {
                    preList.forEach(m -> {
                        Optional<Map<String, Object>> optional = taskList.stream().filter(p -> Objects.equals(m.get("id"), p.get("RELATION_DATA_ID"))).findAny();
                        if (!optional.isPresent()) {
                            //新增任务
                            insertData(m);
                        }
                    });
                }

            });
        }
    }

    /**
     * @param objectMap
     */
    private void insertData(Map<String, Object> objectMap) {
        String id = Util.insertData(jdbcTemplate, "WEEK_TASK");
        String userId = JdbcMapUtil.getString(objectMap, "CHIEF_USER_ID");
        if (Objects.isNull(objectMap.get("CHIEF_USER_ID"))) {
            userId = JdbcMapUtil.getString(objectMap, "default_user");
        }
        String processName = JdbcMapUtil.getString(objectMap, "NAME");
        if (Objects.nonNull(objectMap.get("LINKED_WF_PROCESS_ID"))) {
            // 取流程名称
            List<Map<String, Object>> processlist = jdbcTemplate.queryForList("select * from WF_PROCESS where id=?", objectMap.get("LINKED_WF_PROCESS_ID"));
            if (!CollectionUtils.isEmpty(processlist)) {
                Map<String, Object> dataMap = processlist.get(0);
                processName = JdbcMapUtil.getString(dataMap, "NAME");
            }
        }
        String dateOrg = "";
        if (Objects.nonNull(objectMap.get("PLAN_COMPL_DATE"))) {
            Date compDate = JdbcMapUtil.getDate(objectMap, "PLAN_COMPL_DATE");
            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
            dateOrg = sp.format(compDate);
        }

        String title = objectMap.get("prjName") + "-" + processName;
        String content = MessageFormat.format("{0}【{1}】计划将在{2}完成，请及时处理！", objectMap.get("prjName"), processName, dateOrg);
        jdbcTemplate.update("update WEEK_TASK set AD_USER_ID=?,TITLE=?,CONTENT=?,PUBLISH_START=?,WEEK_TASK_STATUS_ID=?,WEEK_TASK_TYPE_ID=?,RELATION_DATA_ID=?,CAN_DISPATCH='0',PM_PRJ_ID=?,PLAN_COMPL_DATE=? where id=?",
                userId, title, content, new Date(), "1634118574056542208", "1635080848313290752", objectMap.get("ID"), objectMap.get("projectId"), objectMap.get("PLAN_COMPL_DATE"), id);
    }

    /**
     * 判断单元格是否为空
     *
     * @param cell
     * @return
     */
    public static boolean isEmptyCell(Cell cell) {
        if (cell == null || cell.getCellType().equals(CellType.BLANK)) {
            return true;
        }
        return false;
    }
}
