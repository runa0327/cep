package com.cisdi.pms.job.proPlan;

import com.qygly.ext.rest.helper.feign.client.DataFeignClient;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProPlanJob {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    DataFeignClient dataFeignClient;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 全景节点超期处理
     */
    @Scheduled(fixedDelayString = "5000")
//    @Scheduled(cron = "0 0 7 * * ? ")//每天7点执行一次
    public void planNodeOverdue() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select pn.*,pl.PM_PRJ_ID from pm_pro_plan_node pn left join pm_pro_plan pl on pn.PM_PRO_PLAN_ID = pl.ID \n" +
                "where pl.IS_TEMPLATE <> '1' and (IZ_OVERDUE<>'1' or IZ_OVERDUE is null) and  pn.PLAN_COMPL_DATE< NOW() and pn.PROGRESS_STATUS_ID in ('0099799190825106800','0099799190825106801') and `level` = '3'");
        if (!CollectionUtils.isEmpty(list)) {
            List<String> ids = list.stream().map(p -> JdbcMapUtil.getString(p, "ID")).collect(Collectors.toList());
            Map<String, Object> queryParams = new HashMap<>();// 创建入参map
            queryParams.put("ids", ids);
            namedParameterJdbcTemplate.update("update pm_pro_plan_node set IZ_OVERDUE='1' where id in (:ids)", queryParams);
        }
    }
}
