package com.cisdi.data.transfer;

import com.cisdi.data.domain.PoOrder;
import com.cisdi.data.domain.PoPublicBid;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
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

    /** 合同同步 **/
    @GetMapping(value = "/transferContract")
    public void TransferContract(){

        //删除数据
        testJdbcTemplate.update("delete from po_order where CPMS_UUID is not null");

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
                                    poOrder.setCPMS_ID(JdbcMapUtil.getString(tmp,"id"));
                                    poOrder.setCPMS_UUID(mapInfoLists);

                                    //查询老系统文件信息
                                    String sql3 = "select file_id from project_relevance where relevance_id = '"+JdbcMapUtil.getString(tmp,"contract_id")+"'";
                                    String fileStr = getFileStr(sql3);
                                    if (!SharedUtil.isEmptyString(fileStr)){
                                        poOrder.setFILE_ATTACHMENT_URL(fileStr);
                                    }

                                    String insertSql = "insert into po_order (ID,VER,TS,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,CONTRACT_AMOUNT,AGENT," +
                                            "AGENT_PHONE,OPPO_SITE_CONTACT,FILE_ATTACHMENT_URL,OPPO_SITE_LINK_MAN,PM_PRJ_ID,CPMS_UUID,CPMS_ID) values((select UUID_SHORT()),?,now(),now(),?,now(),?,?,?,?,?,?,?,?,?,?,?)";
                                    testJdbcTemplate.update(insertSql,poOrder.getVer(),poOrder.getCRT_USER_ID(),poOrder.getLAST_MODI_USER_ID(),poOrder.getSTATUS(),poOrder.getCONTRACT_AMOUNT(),
                                            poOrder.getAGENT(),poOrder.getAGENT_PHONE(),poOrder.getOPPO_SITE_CONTACT(),poOrder.getFILE_ATTACHMENT_URL(),poOrder.getOPPO_SITE_LINK_MAN(),poOrder.getPM_PRJ_ID(),
                                            poOrder.getCPMS_UUID(),poOrder.getCPMS_ID());
                                }

                            });
                        }
                    });
                });

            }
        }
    }

    /** 查询文件是否存在，老系统的文件信息匹配新系统id**/
    private String getFileStr(String sql3) {
        String fileStr = "";
        List<Map<String,Object>> map3 = cpmsJdbcTemplate.queryForList(sql3);
        if (!CollectionUtils.isEmpty(map3)){
            fileStr = String.join(",",map3.stream().map(p->JdbcMapUtil.getString(p,"file_id")).filter(x->!" ".equals(x) && !"".equals(x)).collect(Collectors.toList()));
            if (!SharedUtil.isEmptyString(fileStr)){
                fileStr = fileStr.replace(",","','");
                String sql4 = "select FL_FILE_ID from pf_file where CPMS_UUID in ('"+fileStr+"')";
                List<Map<String,Object>> list4 = testJdbcTemplate.queryForList(sql4);
                if (!CollectionUtils.isEmpty(list4)){
                    fileStr = String.join(",",list4.stream().map(x->JdbcMapUtil.getString(x,"FL_FILE_ID")).collect(Collectors.toList()));
                }
            }
        }
        return fileStr;
    }

    /**
     * 招标同步
     */
    @RequestMapping(value = "/transferBidding")
    public void transferBidding(){
        //删除招标历史数据
        int del = testJdbcTemplate.update("delete from PO_PUBLIC_BID where CPMS_UUID is not null");
        log.info("删除数据条数："+del);

        //查询老数据库招标信息
        String sql1 = "select id,tender_id,project_id,tender_name," +
                "(SELECT dict_code FROM sys_dict_data WHERE dict_type = 'pms_tender_way' and dict_value = project_tender.tender_way) as tender_way," +
                "tender_type,(SELECT dict_code FROM sys_dict_data WHERE dict_type = 'pms_release_way' and dict_value = project_tender.release_way) as release_way," +
                "agent_company,tender_status,bid_open_time,win_company,check_price," +
                "win_price,win_type,handle_dept_id,handle_member_id,(SELECT user_name FROM sys_user WHERE user_id = project_tender.handle_member_id) as phone," +
                "is_failure_bid from project_tender where del_flag = 0 order by project_id asc,id asc";
        List<Map<String,Object>> list1 = cpmsJdbcTemplate.queryForList(sql1);
        if (!CollectionUtils.isEmpty(list1)){

            //项目集合
            List<String> prjList = list1.stream().map(p->JdbcMapUtil.getString(p,"project_id")).distinct().collect(Collectors.toList());
            //人员集合
            List<String> phoneList = list1.stream().map(p->JdbcMapUtil.getString(p,"phone")).filter(x->!SharedUtil.isEmptyString(x)).distinct().collect(Collectors.toList());

            //查询人员信息
            String sql5 = "select id,code from ad_user order by code asc";
            List<Map<String,Object>> list5 = testJdbcTemplate.queryForList(sql5);
            if (CollectionUtils.isEmpty(list5)){
                throw new BaseException("人员信息为空，不能同步数据，请先维护人员信息！");
            }
            List<String> user = list5.stream().map(p->JdbcMapUtil.getString(p,"code")).collect(Collectors.toList());
            String msg = checkUser(phoneList,user,list1,list5);
            if (!SharedUtil.isEmptyString(msg)){
                throw new BaseException("人员信息关联失败，以下账号在新系统不存在，请先维护。账号有："+msg);
            }

            //查询赛迪云边项目信息
            String sql2 = "select id,CPMS_UUID from pm_prj order by CRT_DT desc";
            List<Map<String,Object>> list2 = testJdbcTemplate.queryForList(sql2);
            if (CollectionUtils.isEmpty(list2)){
                throw new BaseException("项目信息为空，不能同步数据，请先维护项目信息！");
            }
            //查询招标方式
            String sql3 = "SELECT b.id,b.NAME,b.CPMS_ID FROM gr_set a LEFT JOIN gr_set_value b on a.id = b.GR_SET_ID WHERE a.CODE = 'pms_tender_way'";
            List<Map<String,Object>> list3 = testJdbcTemplate.queryForList(sql3);
            if (CollectionUtils.isEmpty(list3)){
                throw new BaseException("招标方式信息为空，不能同步数据，请先维护招标方式信息！");
            }
            //查询招标类型
            String sql4 = "SELECT b.id,b.NAME,b.CPMS_ID FROM gr_set a LEFT JOIN gr_set_value b on a.id = b.GR_SET_ID WHERE a.CODE = 'pms_release_way'";
            List<Map<String,Object>> list4 = testJdbcTemplate.queryForList(sql4);
            if (CollectionUtils.isEmpty(list4)){
                throw new BaseException("招标类型为空，不能同步数据，请先维护类型信息！");
            }

            //匹配处理招标方式、招标类型
            checkBidType(list1,list3,list4);

            prjList.forEach(prj->{
                list2.forEach(project->{
                    String oldProjectId = JdbcMapUtil.getString(project,"CPMS_UUID");
                    if (prj.equals(oldProjectId)){
                        for (Map<String, Object> tmp : list1) {
                            String prjId = tmp.get("project_id").toString();
                            if (prjId.equals(prj)){

                                PoPublicBid poPublicBid = new PoPublicBid();
                                poPublicBid.setCPMS_ID(JdbcMapUtil.getString(tmp,"id")); //老系统自增id
                                poPublicBid.setCPMS_UUID(JdbcMapUtil.getString(tmp,"tender_id"));  //老系统招标id
                                poPublicBid.setPM_PRJ_ID(JdbcMapUtil.getString(project,"id"));  //项目id
                                poPublicBid.setNAME(JdbcMapUtil.getString(tmp,"tender_name"));  //招标名称
                                poPublicBid.setAPPROVE_PURCHASE_TYPE(JdbcMapUtil.getString(tmp,"tender_way"));  //招标方式
                                poPublicBid.setPMS_RELEASE_WAY_ID(JdbcMapUtil.getString(tmp,"release_way"));  //招标类型
                                poPublicBid.setBID_AGENCY(JdbcMapUtil.getString(tmp,"agent_company"));  //招标代理单位
                                poPublicBid.setWIN_BID_UNIT_TXT(JdbcMapUtil.getString(tmp,"win_company")); //中标单位
                                poPublicBid.setBID_CTL_PRICE_LAUNCH(JdbcMapUtil.getString(tmp,"check_price"));  //招标控制价
                                poPublicBid.setTENDER_OFFER(JdbcMapUtil.getString(tmp,"win_price"));  //中标价
                                poPublicBid.setBID_USER_ID(JdbcMapUtil.getString(tmp,"phone"));  //经办人
                                String dat = JdbcMapUtil.getString(tmp,"bid_open_time");
                                poPublicBid.setBID_OPEN_DATE(SharedUtil.isEmptyString(dat) ? null : dat.replace("T"," "));  //开标时间

                                //查询该条数据是否存在文件信息
                                String sql6 = "select file_id from project_relevance where relevance_id = '"+JdbcMapUtil.getString(tmp,"tender_id")+"'";
                                String fileStr = getFileStr(sql6);
                                if (!SharedUtil.isEmptyString(fileStr)){
                                    poPublicBid.setBID_FILE_GROUP_ID(fileStr);
                                }

                                //插入
                                String sql7 = "insert into PO_PUBLIC_BID(id,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,NAME,PM_PRJ_ID,BID_CTL_PRICE_LAUNCH," +
                                        "APPROVE_PMS_RELEASE_WAY_ID,APPROVE_PURCHASE_TYPE,BID_USER_ID,BID_AGENCY,BID_FILE_GROUP_ID,TENDER_OFFER,CPMS_UUID,CPMS_ID,BID_OPEN_DATE) values(" +
                                        "(select UUID_SHORT()),now(),'99250247095871681',now(),'99250247095871681','AP',?,?,?,?,?,?,?,?,?,?,?,?)";
                                int num = testJdbcTemplate.update(sql7,poPublicBid.getNAME(),poPublicBid.getPM_PRJ_ID(),poPublicBid.getBID_CTL_PRICE_LAUNCH(),poPublicBid.getAPPROVE_PMS_RELEASE_WAY_ID(),
                                        poPublicBid.getAPPROVE_PURCHASE_TYPE(),poPublicBid.getBID_USER_ID(),poPublicBid.getBID_AGENCY(),poPublicBid.getBID_FILE_GROUP_ID(),poPublicBid.getTENDER_OFFER(),
                                        poPublicBid.getCPMS_UUID(),poPublicBid.getCPMS_ID(),poPublicBid.getBID_OPEN_DATE());
                                log.info("执行"+num+"成功");
                            }
                        }
                    }
                });
            });
            log.info("end");
        }
    }

    //循环匹配招标方式、招标类型 list3为招标方式 list4为招标类型
    private List<Map<String, Object>> checkBidType(List<Map<String, Object>> list1, List<Map<String, Object>> list3, List<Map<String, Object>> list4) {
        list1.forEach(p->{
            //招标方式id
            String mode = JdbcMapUtil.getString(p,"tender_way");
            if (!SharedUtil.isEmptyString(mode)){
                for (Map<String, Object> tmp : list3) {
                    String id = SharedUtil.isEmptyString(JdbcMapUtil.getString(tmp,"CPMS_ID")) ? "-999" : JdbcMapUtil.getString(tmp,"CPMS_ID");
                    if (mode.equals(id)){
                        p.put("tender_way",JdbcMapUtil.getString(tmp,"id"));
                        break;
                    }
                }
            }
            //招标类型
            String type = JdbcMapUtil.getString(p,"release_way");
            if (!SharedUtil.isEmptyString(type)){
                for (Map<String, Object> tmp : list4) {
                    String id = SharedUtil.isEmptyString(JdbcMapUtil.getString(tmp,"CPMS_ID")) ? "-999" : JdbcMapUtil.getString(tmp,"CPMS_ID");
                    if (mode.equals(id)){
                        p.put("release_way",JdbcMapUtil.getString(tmp,"id"));
                        break;
                    }
                }
            }
        });
        return list1;
    }

    //新老系统人员信息判断
    private String checkUser(List<String> phoneList, List<String> user,List<Map<String,Object>> list,List<Map<String,Object>> list5) {
        StringBuilder sb = new StringBuilder();
        phoneList.forEach(p->{
            int size = list5.size();
            int num = 0;
            for (Map<String, Object> tmp : list5) {
                if (!p.equals(JdbcMapUtil.getString(tmp,"code"))){
                    num++;
                } else {
                    list.stream().map(q->q.put("phone",JdbcMapUtil.getString(tmp,"id"))).collect(Collectors.toList());
                    break;
                }
                if (num == size){
                    sb.append(p).append(",");
                }
            }
        });
        return sb.toString();
    }

}
