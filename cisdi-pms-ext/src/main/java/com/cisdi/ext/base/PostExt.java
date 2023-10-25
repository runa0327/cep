package com.cisdi.ext.base;

import com.cisdi.ext.model.base.BasePointPost;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 岗位信息相关扩展
 */
public class PostExt {

    /**
     * 通过 流程节点岗位信息 获取改节点需要参与审批的岗位
     * @param nodeId 节点id
     * @return 岗位信息
     */
    public static List<String> getPostByPointPost(String nodeId) {
        List<String> list = new ArrayList<>();
        List<BasePointPost> postList = BasePointPost.selectByWhere(new Where().eq(BasePointPost.Cols.STATUS,"AP")
                .eq(BasePointPost.Cols.WF_NODE_ID,nodeId));
        if (!CollectionUtils.isEmpty(postList)){
            list = postList.stream().map(BasePointPost::getPostInfoId).collect(Collectors.toList());
        }
        return list;
    }

    /**
     * 根据流程id查询该流程审批涉及到的所有岗位编码
     * @param processId 流程id
     * @param myJdbcTemplate 数据源
     * @return 岗位编码
     */
    public static String getPostByProcess(String processId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select group_concat(distinct c.code) as code from BASE_POINT_POST a left join wf_node b on a.WF_NODE_ID = b.id left join post_info c on a.post_info_id = c.id where b.WF_PROCESS_ID = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,processId);
        if (!CollectionUtils.isEmpty(list)){
            return JdbcMapUtil.getString(list.get(0),"code");
        } else {
            return null;
        }
    }
}
