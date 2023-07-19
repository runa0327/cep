package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.excel.model.WeekTaskModel;
import com.google.common.base.Strings;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title WeekTaskStandingBookController
 * @package com.cisdi.pms.job.excel.export
 * @description 工作任务台账
 * @date 2023/7/10
 */
@RestController
@RequestMapping("weekTaskStandingBook")
public class WeekTaskStandingBookController extends BaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final String TASK_NOT_STARTED = "1634118574056542208";
    public static final String TASK_IN_PROCESSING = "1634118609016066048";
    public static final String TASK_COMPLETED = "1634118629769482240";
    public static final String TASK_NOT_INVOLVE = "1644140265205915648";
    public static final String TASK_OVERDUE = "1644140821106384896";


    @SneakyThrows
    @GetMapping("export")
    public void exportExcel(String projectId, String taskName, String user, String status, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select wt.ID as id,TITLE,ad.`NAME` as user,gsv.`NAME` as status,if(ifnull(CAN_DISPATCH,'0') = '0','否','是') as iz_tran,TRANSFER_TIME,au.`NAME` as tran_user,REASON_EXPLAIN,PM_PRJ_ID,pm.NAME as projectName from week_task wt \n" +
                " left join pm_prj pm on pm.id = wt.PM_PRJ_ID " +
                " left join ad_user ad on wt.AD_USER_ID = ad.id \n" +
                " left join gr_set_value gsv on wt.WEEK_TASK_STATUS_ID = gsv.id \n" +
                " left join ad_user au on wt.TRANSFER_USER = au.id where wt.status ='ap' ");
        if (!Strings.isNullOrEmpty(projectId)) {
            sb.append(" and wt.PM_PRJ_ID ='").append(projectId).append("'");
        }
        if (!Strings.isNullOrEmpty(status)) {
            if ("全部".equals(status)) {
                sb.append(" and 1=1 ");
            } else if ("超期完成".equals(status)) {
                sb.append(" and wt.WEEK_TASK_STATUS_ID='1634118629769482240' and wt.ACTUAL_COMPL_DATE > wt.PLAN_COMPL_DATE ");
            } else {
                String stausValue = null;
                switch (status) {
                    case "未开始":
                        stausValue = TASK_NOT_STARTED;
                        break;
                    case "进行中":
                        stausValue = TASK_IN_PROCESSING;
                        break;
                    case "已完结":
                        stausValue = TASK_COMPLETED;
                        break;
                    case "不涉及":
                        stausValue = TASK_NOT_INVOLVE;
                        break;
                    case "超期未完成":
                        stausValue = TASK_OVERDUE;
                        break;
                }
                sb.append(" and wt.WEEK_TASK_STATUS_ID ='").append(stausValue).append("'");
            }
        }
        if (!Strings.isNullOrEmpty(taskName)) {
            sb.append(" and wt.TITLE like '%").append(taskName).append("%'");
        }
        if (!Strings.isNullOrEmpty(user)) {
            sb.append(" and ad.`NAME` like '%").append(user).append("%'");
        }
        sb.append(" order by wt.PUBLISH_START desc ");

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
        List<WeekTaskModel> weekTaskInfoList = list.stream().map(this::convertWeekTaskInfo).collect(Collectors.toList());
        super.setExcelRespProp(response, "合同台账");
        EasyExcel.write(response.getOutputStream())
                .head(WeekTaskModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("合同台账")
                .doWrite(weekTaskInfoList);
    }

    public WeekTaskModel convertWeekTaskInfo(Map<String, Object> dataMap) {
        WeekTaskModel info = new WeekTaskModel();
//        info.id = JdbcMapUtil.getString(dataMap, "id");
        info.title = JdbcMapUtil.getString(dataMap, "TITLE");
        info.user = JdbcMapUtil.getString(dataMap, "user");
        info.status = JdbcMapUtil.getString(dataMap, "status");
        info.izTurn = JdbcMapUtil.getString(dataMap, "iz_tran");
        info.transferTime = JdbcMapUtil.getString(dataMap, "TRANSFER_TIME");
        info.transferUser = JdbcMapUtil.getString(dataMap, "tran_user");
        info.reasonExplain = JdbcMapUtil.getString(dataMap, "REASON_EXPLAIN");
//        info.projectId = JdbcMapUtil.getString(dataMap, "PM_PRJ_ID");
//        info.projectName = JdbcMapUtil.getString(dataMap, "projectName");
        int count = getDelayApplyList(JdbcMapUtil.getString(dataMap, "id")).size();
        info.count = count;
        return info;
    }

    public List<Map<String, Object>> getDelayApplyList(String id) {
        return jdbcTemplate.queryForList("select pe.*,ad.`NAME` as user_name,pnn.name as nodeName,ast.name as astName from PM_EXTENSION_REQUEST_REQ pe \n" +
                " left join pm_pro_plan_node pnn on pe.PM_PRO_PLAN_NODE_ID = pnn.id" +
                " left join ad_user ad on pe.CRT_USER_ID = ad.id \n" +
                " left join week_task wt on wt.RELATION_DATA_ID = pe.PM_PRO_PLAN_NODE_ID\n" +
                " left join ad_status ast on pe.status = ast.id " +
                " where wt.id = ?", id);
    }

}
