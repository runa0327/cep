package com.cisdi.ext.history;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title HistoricalReventDataExt
 * @package com.cisdi.ext.history
 * @description 资料清单刷新节点数据
 * @date 2023/8/24
 */
public class InventoryReventDataExt {

    /**
     * 刷新数据
     */
    public void reventData() {
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

        }
    }
}
