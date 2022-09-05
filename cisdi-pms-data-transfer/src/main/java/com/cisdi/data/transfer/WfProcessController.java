package com.cisdi.data.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("wfProcess")
@RestController
public class WfProcessController {

    @Autowired
    @Qualifier("testJdbcTemplate")
    JdbcTemplate testJdbcTemplate;

    /**
     * http://localhost:11116/wfProcess/addAdminAsStartUser
     */
    @GetMapping("addAdminAsStartUser")
    public void addAdminAsStartUser() {
        List<Map<String, Object>> processList = testJdbcTemplate.queryForList("select * from wf_process p where 1=1 and not exists(select 1 from wf_node n where n.WF_PROCESS_Id=p.id)");
        for (Map<String, Object> process : processList) {
            String wfNodeId = Util.insertData(testJdbcTemplate, "WF_NODE");
            testJdbcTemplate.update("update wf_node n set n.WF_PROCESS_ID=?,n.name='发起',n.NODE_TYPE='START_EVENT' where n.id=?", process.get("ID"), wfNodeId);

            String wfNodeRoleId = Util.insertData(testJdbcTemplate, "wf_node_role");
            testJdbcTemplate.update("update wf_node_role t set t.WF_NODE_ID=?,t.AD_ROLE_ID='99250247095870406' where t.id=?", wfNodeId, wfNodeRoleId);
        }
    }
}
