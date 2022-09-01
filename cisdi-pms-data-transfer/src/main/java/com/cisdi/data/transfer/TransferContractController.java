package com.cisdi.data.transfer;

import com.cisdi.data.domain.PoOrder;
import com.cisdi.data.util.ListUtils;
import com.qygly.ext.jar.helper.debug.event.AsyncConfig;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 招标合同数据同步
 */
@RequestMapping("/transferContract")
@RestController()
@Slf4j
public class TransferContractController {


    @Autowired
    @Qualifier("testJdbcTemplate")
    JdbcTemplate testJdbcTemplate;

    @Autowired
    @Qualifier("cpmsJdbcTemplate")
    JdbcTemplate cpmsJdbcTemplate;

    @GetMapping(value = "/transferContract")
    public void TransferContract(){
        //查询项目合同信息
        String sql1 = "SELECT id,contract_id, project_id,contract_name, indicators_id, counterparty,sign_date, tender_way,shal_review_price, goods_evaluation_price,ifnull(change_price,contract_price) as contract_price, " +
                "agent,agent_phone, other, other_phone, del_flag, remakes,version " +
                "FROM project_contract " +
                "WHERE del_flag = 0  and (version is null or version != 9) ORDER BY id asc";
        List<Map<String,Object>> oldList = cpmsJdbcTemplate.queryForList(sql1);
        if (!CollectionUtils.isEmpty(oldList)){
            List<String> projectList = oldList.stream().map(x->JdbcMapUtil.getString(x,"project_id")).distinct().collect(Collectors.toList());
            //查询赛迪云方项目信息
            String sql2 = "select id,CPMS_UUID from pm_prj order by CRT_DT desc";
            List<Map<String,Object>> sdPrjList = testJdbcTemplate.queryForList(sql2);
            if (!CollectionUtils.isEmpty(sdPrjList)){
                projectList.forEach(mapInfoLists->{
                    sdPrjList.forEach(tp->{
                        String newPrjId = JdbcMapUtil.getString(tp, "CPMS_UUID");
                        if (mapInfoLists.equals(newPrjId)) {
                            oldList.forEach(tmp->{
                                if (mapInfoLists.equals(JdbcMapUtil.getString(tmp,"project_id"))){
                                    PoOrder poOrder = new PoOrder();
                                    poOrder.setVer("1");
                                    poOrder.setCRT_USER_ID("99250247095871681");
                                    poOrder.setLAST_MODI_USER_ID("99250247095871681");
                                    poOrder.setSTATUS("AP");
                                    poOrder.setCONTRACT_AMOUNT(JdbcMapUtil.getString(tmp, "contract_price"));
                                    poOrder.setAGENT((JdbcMapUtil.getString(tmp, "agent") == null) ? "" : JdbcMapUtil.getString(tmp, "agent"));
                                    poOrder.setAGENT_PHONE((JdbcMapUtil.getString(tmp, "agent_phone") == null) ? "" : JdbcMapUtil.getString(tmp, "agent_phone"));
                                    poOrder.setOPPO_SITE_LINK_MAN((JdbcMapUtil.getString(tmp, "other") == null) ? "" : JdbcMapUtil.getString(tmp, "other"));
                                    poOrder.setOPPO_SITE_CONTACT((JdbcMapUtil.getString(tmp, "other_phone") == null) ? "" : JdbcMapUtil.getString(tmp, "other_phone"));
                                    poOrder.setPM_PRJ_ID(JdbcMapUtil.getString(tp, "id"));

                                    //查询老系统文件信息
                                    String sql3 = "select file_id from project_relevance where relevance_id = '"+JdbcMapUtil.getString(tmp,"contract_id")+"'";
                                    List<Map<String,Object>> map3 = cpmsJdbcTemplate.queryForList(sql3);
                                    if (!CollectionUtils.isEmpty(map3)){
                                        String fileStr = String.join(",",map3.stream().map(p->JdbcMapUtil.getString(p,"file_id")).filter(x->!" ".equals(x) && !"".equals(x)).collect(Collectors.toList()));
                                        if (!SharedUtil.isEmptyString(fileStr)){
                                            fileStr = fileStr.replace(",","','");
                                            String sql4 = "select FL_FILE_ID from pf_file where CPMS_UUID in ('"+fileStr+"')";
                                            List<Map<String,Object>> list4 = testJdbcTemplate.queryForList(sql4);
                                            if (!CollectionUtils.isEmpty(list4)){
                                                fileStr = String.join(",",list4.stream().map(x->JdbcMapUtil.getString(x,"FL_FILE_ID")).collect(Collectors.toList()));
                                                poOrder.setFILE_ATTACHMENT_URL(fileStr);
                                            }
                                        }
                                    }

                                    String insertSql = "insert into po_order (ID,VER,TS,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,CONTRACT_AMOUNT,AGENT," +
                                            "AGENT_PHONE,OPPO_SITE_CONTACT,FILE_ATTACHMENT_URL,OPPO_SITE_LINK_MAN,PM_PRJ_ID) values((select UUID_SHORT()),?,now(),now(),?,now(),?,?,?,?,?,?,?,?,?)";
                                    testJdbcTemplate.update(insertSql,poOrder.getVer(),poOrder.getCRT_USER_ID(),poOrder.getLAST_MODI_USER_ID(),poOrder.getSTATUS(),poOrder.getCONTRACT_AMOUNT(),
                                            poOrder.getAGENT(),poOrder.getAGENT_PHONE(),poOrder.getOPPO_SITE_CONTACT(),poOrder.getFILE_ATTACHMENT_URL(),poOrder.getOPPO_SITE_LINK_MAN(),poOrder.getPM_PRJ_ID());
                                }

                            });
                        }
                    });
                });

            }
        }
    }

}
