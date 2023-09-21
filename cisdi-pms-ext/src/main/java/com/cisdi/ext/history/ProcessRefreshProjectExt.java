package com.cisdi.ext.history;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.util.DateTimeUtil;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProcessRefreshProjectExt
 * @package com.cisdi.ext.history
 * @description 流程刷新项目基本信息
 * @date 2023/9/18
 */
public class ProcessRefreshProjectExt {


    public void refreshPmData() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String projectIds = JdbcMapUtil.getString(map, "projectIds");
        StringBuilder sb = new StringBuilder();
        sb.append("select * from pm_prj where status='ap' ");
        Map<String, Object> queryParams = new HashMap<>();// 创建入参map
        if (Strings.isNotEmpty(projectIds)) {
            sb.append(" and id in (:ids)");
            queryParams.put("ids", Arrays.asList(projectIds.split(",")));
        }
        List<Map<String, Object>> list = myNamedParameterJdbcTemplate.queryForList(sb.toString(), queryParams);
        if (!CollectionUtils.isEmpty(list)) {
            for (Map<String, Object> objectMap : list) {
                String pmCode = null;
                String pfCode = null;
                Date pfDate = null;
                String jzArea = null;
                String description = null;
                Date actualBegin = null;
                Date actualEnd = null;
                //项目立项查找
                List<Map<String, Object>> pmReqList = myJdbcTemplate.queryForList("select * from PM_PRJ_REQ where `status`='ap' and  PM_PRJ_ID=? order by CRT_DT desc limit 0,1 ", objectMap.get("ID"));
                if (!CollectionUtils.isEmpty(pmReqList)) {
                    Map<String, Object> pmReq = pmReqList.get(0);
                    pmCode = JdbcMapUtil.getString(pmReq, "CONSTRUCTION_PROJECT_CODE");
                    pfCode = JdbcMapUtil.getString(pmReq, "PRJ_REPLY_NO");
                    pfDate = DateTimeUtil.stringToDate(JdbcMapUtil.getString(pmReq, "PRJ_REPLY_DATE"));
                    jzArea = JdbcMapUtil.getString(pmReq, "FLOOR_AREA");
                    description = JdbcMapUtil.getString(pmReq, "PRJ_SITUATION");
                }
                //可研
                List<Map<String, Object>> investList = myJdbcTemplate.queryForList("select * from PM_PRJ_INVEST1  where  `status`='ap' and  PM_PRJ_ID=? order by CRT_DT desc limit 0,1 ", objectMap.get("ID"));
                if (!CollectionUtils.isEmpty(investList)) {
                    Map<String, Object> invest = investList.get(0);
                    pmCode = JdbcMapUtil.getString(invest, "PRJ_CODE");
                    jzArea = JdbcMapUtil.getString(invest, "FLOOR_AREA");
                    description = JdbcMapUtil.getString(invest, "PRJ_SITUATION");
                }
                //开工报审
                List<Map<String, Object>> kickOffList = myJdbcTemplate.queryForList("select * from PM_PRJ_KICK_OFF_REQ where `status`='ap' and PM_PRJ_ID=? order by CRT_DT desc limit 0,1 ", objectMap.get("ID"));
                if (!CollectionUtils.isEmpty(kickOffList)) {
                    Map<String, Object> kickOff = kickOffList.get(0);
                    actualBegin = DateTimeUtil.stringToDate(JdbcMapUtil.getString(kickOff, "PLAN_DATE"));
                }

                //竣工验收
                List<Map<String, Object>> qualityCompletedCheckList = myJdbcTemplate.queryForList("select * from PM_QUALITY_COMPLETED_CHECK_REQ where `status`='ap' and PM_PRJ_ID=? order by CRT_DT desc limit 0,1 ", objectMap.get("ID"));
                if (!CollectionUtils.isEmpty(qualityCompletedCheckList)) {
                    Map<String, Object> qualityCompletedCheck = qualityCompletedCheckList.get(0);
                    actualEnd = DateTimeUtil.stringToDate(JdbcMapUtil.getString(qualityCompletedCheck, "DATE_ONE"));
                }
                StringBuilder sbl = new StringBuilder();
                sbl.append("update PM_PRJ set LAST_MODI_DT='").append(new Date()).append("'");
                if (StringUtils.hasText(pmCode)) {
                    sbl.append(" , PRJ_CODE='").append(pmCode).append("'");
                }
                if (StringUtils.hasText(pfCode)) {
                    sbl.append(" , PRJ_REPLY_NO='").append(pfCode).append("'");
                }
                if (pfDate != null) {
                    sbl.append(" , PRJ_REPLY_DATE='").append(pfDate).append("'");
                }
                if (StringUtils.hasText(jzArea)) {
                    sbl.append(" , FLOOR_AREA='").append(jzArea).append("'");
                }
                if (StringUtils.hasText(description)) {
                    sbl.append(" , PRJ_SITUATION='").append(description).append("'");
                }
                if (actualBegin != null) {
                    sbl.append(" , ACTUAL_START_TIME='").append(actualBegin).append("'");
                }
                if (actualEnd != null) {
                    sbl.append(" , ACTUAL_END_TIME='").append(actualEnd).append("'");
                }

                sbl.append(" where ID='").append(objectMap.get("ID")).append("'");
                myJdbcTemplate.update(sbl.toString());
            }
        }
    }
}
