package com.cisdi.pms.job.excel.export;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
public class WeekTaskStandingBookController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static final String TASK_NOT_STARTED = "1634118574056542208";
    public static final String TASK_IN_PROCESSING = "1634118609016066048";
    public static final String TASK_COMPLETED = "1634118629769482240";
    public static final String TASK_NOT_INVOLVE = "1644140265205915648";
    public static final String TASK_OVERDUE = "1644140821106384896";

    @GetMapping("export")
    public void exportExcel(String projectId, String taskName, String userName, String status, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select wt.ID as id,TITLE,ad.`NAME` as user,gsv.`NAME` as status,if(ifnull(CAN_DISPATCH,'0') = '0','否','是') as iz_tran,TRANSFER_TIME,au.`NAME` as tran_user,REASON_EXPLAIN from week_task wt \n" +
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
        if (!Strings.isNullOrEmpty(userName)) {
            sb.append(" and ad.`NAME` like '%").append(userName).append("%'");
        }
        sb.append(" order by wt.PUBLISH_START desc ");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
    }
}
