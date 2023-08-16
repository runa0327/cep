package com.cisdi.pms.job.archive;

import cn.hutool.core.util.IdUtil;
import com.cisdi.pms.job.utils.StringUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.DateTimeUtil;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class ArchiveGenerationService {

    private String adminUserId = "0099250247095871681";


    @Autowired
    JdbcTemplate jdbcTemplate;

    public void backupAndTruncate() {
        Long timeStamp = DateTimeUtil.dateToTimeStamp(new Date());
        jdbcTemplate.update("create table pf_file_" + timeStamp + " as select * from pf_file");
        jdbcTemplate.update("delete from  pf_file");

        jdbcTemplate.update("create table pf_folder_" + timeStamp + " as select * from pf_folder");
        jdbcTemplate.update("delete from  pf_folder");
    }

    public void execute() {

        boolean test = false;
        if (test) {
            // backupAndTruncate();
        }

        // 测试数据：
        // 流程实例：
        // SELECT * FROM wf_process_instance PI WHERE PI.NAME='【紧急】采购需求审批-测试项目1219-设计-任晨鑫-19989661949-2023-01-06 15:41:03';
        // 表单：
        // select * from PM_BUY_DEMAND_REQ t where t.id='1611266296006242304'


        // 获取流程实例列表，条件为：
        // 1、已批准
        // 2、结束的
        // 3、生成未成功
        // 4、生成失败次数<3（失败时，不要老是生成，影响性能）
        List<Map<String, Object>> procInstList = jdbcTemplate.queryForList("select pi.* from wf_process_instance pi where not exists(select 1 from PF_GENERATION_LOG l where l.wf_process_instance_id=pi.id and l.is_succ=1)/*没有成功*/ and (select count(*) from PF_GENERATION_LOG l where l.wf_process_instance_id=pi.id and l.is_succ=0)<3/*失败小于3次*/ and pi.`STATUS`='AP' and pi.END_DATETIME is not null" + (test ? " and pi.id='1658352219441057792'" : ""));

        for (Map<String, Object> procInst : procInstList) {
            String newLogId = insertLog();

            String procInstId = JdbcMapUtil.getString(procInst, "ID");
            try {
                List<Map<String, Object>> folderList = jdbcTemplate.queryForList("select m.id m_id,m.name m_name,m.SEQ_NO m_seq_no,s.id s_id,s.name s_name,s.SEQ_NO s_seq_no,p.id p_id,p.name p_name,p.SEQ_NO p_seq_no,pi.id pi_id,pi.name pi_name,pi.id pi_seq_no from wf_process_instance pi join wf_process p on pi.id=? and pi.wf_process_id=p.id join wf_cate s on p.wf_cate_id=s.id join wf_cate m on s.WF_CATE_PID=m.id", procInstId);
                if (folderList.size() != 1) {
                    updateLog(false, "根据流程实例ID获取流程实例、流程、小类、大类的数量不能为" + folderList.size() + "！", procInstId, newLogId);
                    continue;
                }

                String entCode = JdbcMapUtil.getString(procInst, "ENT_CODE");
                String entityRecordId = JdbcMapUtil.getString(procInst, "ENTITY_RECORD_ID");
                List<Map<String, Object>> entityRecordlist = jdbcTemplate.queryForList("select * from " + entCode + " t where t.id=?", entityRecordId);
                if (entityRecordlist.size() != 1) {
                    updateLog(false, "没有对应的表单的实体记录！", procInstId, newLogId);
                    continue;
                }

                Map<String, Object> entityRecord = entityRecordlist.get(0);
                String prjId = getPrjId(entityRecord);
                if (SharedUtil.isEmptyString(prjId)) {
                    updateLog(true, "表单没有PM_PRJ_ID属性或PM_PRJ_ID属性值为空，无需生成资料。", procInstId, newLogId);
                    continue;
                }
                List<String> prjIdList = StringUtil.splitByCode(prjId,",");
                if (!CollectionUtils.isEmpty(prjIdList)){
                    for (String projectId : prjIdList) {
                        String folderIdForProcInst = getOrCreateFoldersForMainCateSubCateProcProcInst(projectId, folderList.get(0));
                        createFoldersAndFilesForEntityRecord(projectId, folderIdForProcInst, entCode, entityRecord);
                    }
                }
                updateLog(true, null, procInstId, newLogId);
            } catch (Exception ex) {
                log.error("根据流程生成资料出错！", ex);
                updateLog(false, ex.toString(), procInstId, newLogId);
            }
        }
    }

    /**
     * 为大类、小类、流程、流程实例获取或创建目录。
     *
     * @param folder
     * @return
     */
    private String getOrCreateFoldersForMainCateSubCateProcProcInst(String pmPrjId, Map<String, Object> folder) {
        // String m_id = JdbcMapUtil.getString(folder, "m_id");
        // String m_name = JdbcMapUtil.getString(folder, "m_name");
        // BigDecimal m_seq_no = JdbcMapUtil.getBigDecimal(folder, "m_seq_no");
        // String folderIdForMainCate = getOrCreateFolder(pmPrjId, m_name, m_id, null, m_seq_no);

        String s_id = JdbcMapUtil.getString(folder, "s_id");
        String s_name = JdbcMapUtil.getString(folder, "s_name");
        BigDecimal s_seq_no = JdbcMapUtil.getBigDecimal(folder, "s_seq_no");
        // String folderIdForSubCate = getOrCreateFolder(pmPrjId, s_name, s_id, folderIdForMainCate, s_seq_no);
        String folderIdForSubCate = getOrCreateFolder(pmPrjId, s_name, s_id, null, s_seq_no);

        String p_id = JdbcMapUtil.getString(folder, "p_id");
        String p_name = JdbcMapUtil.getString(folder, "p_name");
        BigDecimal p_seq_no = JdbcMapUtil.getBigDecimal(folder, "p_seq_no");
        String folderIdForProc = getOrCreateFolder(pmPrjId, p_name, p_id, folderIdForSubCate, p_seq_no);

        String pi_id = JdbcMapUtil.getString(folder, "pi_id");
        String pi_name = JdbcMapUtil.getString(folder, "pi_name");
        BigDecimal pi_seq_no = JdbcMapUtil.getBigDecimal(folder, "pi_seq_no");
        String folderIdForProcInst = getOrCreateFolder(pmPrjId, pi_name, pi_id, folderIdForProc, pi_seq_no);

        return folderIdForProcInst;
    }

    private String getOrCreateFolder(String pmPrjId, Object name, Object remark, String parentFolderId, BigDecimal seqNo) {
        List<Object> args = new ArrayList<>();
        args.add(remark);
        args.add(pmPrjId);

        if (!SharedUtil.isEmptyString(parentFolderId)) {
            args.add(parentFolderId);
        }

        List<Map<String, Object>> folderList = jdbcTemplate.queryForList("select * from pf_folder t where t.remark=? and t.pm_prj_id=?" + (!SharedUtil.isEmptyString(parentFolderId) ? " AND T.PF_FOLDER_PID=?" : ""), args.toArray());
        if (folderList.size() > 0) {
            return JdbcMapUtil.getString(folderList.get(0), "ID");
        } else {
            return insertFolder(name, remark, seqNo, pmPrjId, parentFolderId);
        }
    }

    private void createFoldersAndFilesForEntityRecord(String pmPrjId, String folderIdForProcInst, String entCode, Map<String, Object> entityRecord) {
        String entityRecordId = JdbcMapUtil.getString(entityRecord, "ID");

        List<Map<String, Object>> attList = jdbcTemplate.queryForList("select E.ID ENT_ID,E.CODE ENT_CODE,E.NAME ENT_NAME,A.ID ATT_ID,A.CODE ATT_CODE,A.NAME ATT_NAME from information_schema.`COLUMNS` c,AD_ENT E,ad_att a,ad_att_sub_type st where c.table_schema=database() and c.table_name=? and c.COLUMN_NAME=a.code and a.AD_ATT_SUB_TYPE_ID=st.id and st.AD_ATT_TYPE_ID='FILE_GROUP' AND E.CODE=? ORDER BY C.ORDINAL_POSITION", entCode, entCode);
        for (int i = 0; i < attList.size(); i++) {
            Map<String, Object> att = attList.get(i);
            Object attId = att.get("ATT_ID");
            Object attCode = att.get("ATT_CODE");
            List<Map<String, Object>> entAttList = jdbcTemplate.queryForList("select ea.ATT_NAME from ad_ent_att ea where ea.AD_ENT_ID=? and ea.AD_ATT_ID=? and ea.ENT_ATT_RELATION='INCLUDE_OR_OVERRIDE'", att.get("ENT_ID"), attId);
            Object attName = entAttList.size() > 0 && !SharedUtil.isEmptyObject(entAttList.get(0).get("ATT_NAME")) ? entAttList.get(0).get("ATT_NAME") : att.get("ATT_NAME");

            Object attValueObject = jdbcTemplate.queryForMap("select " + attCode + " from " + entCode + " where id=?", entityRecordId).get(attCode);
            if (!SharedUtil.isEmptyObject(attValueObject)) {
                String folderIdForAtt = getOrCreateFolder(pmPrjId, attName, attId, folderIdForProcInst, new BigDecimal(i));
                String attValueString = attValueObject.toString();

                List<String> attValueList = null;
                if (attValueString.contains(",")) {
                    attValueList = SharedUtil.splittedStrToStrList(attValueString);
                } else {
                    attValueList = new ArrayList<>(1);
                    attValueList.add(attValueString);
                }

                for (int j = 0; j < attValueList.size(); j++) {
                    String fileId = attValueList.get(j);
                    List<Map<String, Object>> fileList = jdbcTemplate.queryForList("select * from fl_file where id=?", fileId);
                    Map<String, Object> file = fileList.get(0);
                    Object fileName = fileList.size() == 0 ? "缺失此文件" : file.get("NAME");
                    BigDecimal fileSizeKB = JdbcMapUtil.getBigDecimal(file, "SIZE_KB");
                    getOrCreateFile(folderIdForAtt, fileName, fileId, fileSizeKB);
                }
            }
        }
    }

    private String getOrCreateFile(String folderId, Object name, Object flFileId, BigDecimal fileSizeKB) {
        List<Map<String, Object>> folderList = jdbcTemplate.queryForList("select * from pf_file t where t.remark=? and t.PF_FOLDER_ID=?", flFileId, folderId);
        if (folderList.size() > 0) {
            return JdbcMapUtil.getString(folderList.get(0), "ID");
        } else {
            String newId = insertFile(folderId, name, flFileId);
            incrFileSizeAndCountForFolderRecursively(new ArrayList<>(), folderId, fileSizeKB);
            return newId;
        }
    }

    private void incrFileSizeAndCountForFolderRecursively(List<String> processedIdList, String folderId, BigDecimal fileSize) {
        if (processedIdList.contains(folderId)) {
            throw new BaseException("增加目录的文件大小和数量时，出现死循环！路径上某个目录的ID：" + folderId);
        }

        processedIdList.add(folderId);

        Map<String, Object> folder = jdbcTemplate.queryForMap("select * from pf_folder where id=?", folderId);
        jdbcTemplate.update("update pf_folder t set t.FILE_SIZE=ifnull(t.FILE_SIZE,0)+?,t.FILE_COUNT=ifnull(t.FILE_COUNT,0)+1 where t.id=?", fileSize, folderId);
        String pid = JdbcMapUtil.getString(folder, "PF_FOLDER_PID");
        if (!SharedUtil.isEmptyString(pid)) {
            incrFileSizeAndCountForFolderRecursively(processedIdList, pid, fileSize);
        }
    }

    private String insertFile(String folderId, Object name, Object flFileId) {
        String newId = IdUtil.getSnowflakeNextIdStr();
        jdbcTemplate.update("INSERT INTO PF_FILE(`ID`,`VER`,`TS`,`IS_PRESET`,`CRT_DT`,`CRT_USER_ID`,`LAST_MODI_DT`,`LAST_MODI_USER_ID`,`STATUS`,`LK_WF_INST_ID`,`CODE`,`NAME`,`REMARK`,`CPMS_ID`,`PF_FOLDER_ID`,`CHIEF_USER_ID`,`FL_FILE_ID`,`CPMS_UUID`)VALUES(?/*ID*/,(1)/*VER*/,(NOW())/*TS*/,(null)/*IS_PRESET*/,(NOW())/*CRT_DT*/,(?)/*CRT_USER_ID*/,(NOW())/*LAST_MODI_DT*/,(?)/*LAST_MODI_USER_ID*/,('AP')/*STATUS*/,(null)/*LK_WF_INST_ID*/,(null)/*CODE*/,(?)/*NAME*/,(?)/*REMARK*/,(null)/*CPMS_ID*/,(?)/*PF_FOLDER_ID*/,(?)/*CHIEF_USER_ID*/,(?)/*FL_FILE_ID*/,(null)/*CPMS_UUID*/)", newId, adminUserId, adminUserId, name, flFileId, folderId, adminUserId, flFileId);
        return newId;
    }

    private String insertFolder(Object name, Object remark, BigDecimal seqNo, Object pmPrjId, String parentFolderId) {
        String newId = IdUtil.getSnowflakeNextIdStr();
        jdbcTemplate.update("INSERT INTO PF_FOLDER(`ID`,`VER`,`TS`,`IS_PRESET`,`CRT_DT`,`CRT_USER_ID`,`LAST_MODI_DT`,`LAST_MODI_USER_ID`,`STATUS`,`LK_WF_INST_ID`,`CODE`,`FILE_SIZE`,`FILE_COUNT`,`NAME`,`SEQ_NO`,`IS_TEMPLATE`,`PM_PRJ_ID`,`PF_FOLDER_PID`,`REMARK`)VALUES(?/*ID*/,(1)/*VER*/,(NOW())/*TS*/,(null)/*IS_PRESET*/,(NOW())/*CRT_DT*/,(?)/*CRT_USER_ID*/,(NOW())/*LAST_MODI_DT*/,(?)/*LAST_MODI_USER_ID*/,('AP')/*STATUS*/,(null)/*LK_WF_INST_ID*/,(null)/*CODE*/,(null)/*FILE_SIZE*/,(null)/*FILE_COUNT*/,(?)/*NAME*/,(?)/*SEQ_NO*/,(0)/*IS_TEMPLATE*/,(?)/*PM_PRJ_ID*/,(?)/*PF_FOLDER_PID*/,(?)/*REMARK*/)", newId, adminUserId, adminUserId, name, seqNo, pmPrjId, parentFolderId, remark);
        return newId;
    }

    private String insertLog() {
        String newId = IdUtil.getSnowflakeNextIdStr();
        jdbcTemplate.update("INSERT INTO PF_GENERATION_LOG(`ID`,`VER`,`TS`,`IS_PRESET`,`CRT_DT`,`CRT_USER_ID`,`LAST_MODI_DT`,`LAST_MODI_USER_ID`,`STATUS`,`LK_WF_INST_ID`,`CODE`,`NAME`,`REMARK`,`IS_SUCC`,`ERR_INFO`,`WF_PROCESS_INSTANCE_ID`)VALUES(?/*ID*/,(1)/*VER*/,(NOW())/*TS*/,(null)/*IS_PRESET*/,(NOW())/*CRT_DT*/,(?)/*CRT_USER_ID*/,(NOW())/*LAST_MODI_DT*/,(?)/*LAST_MODI_USER_ID*/,('AP')/*STATUS*/,(null)/*LK_WF_INST_ID*/,(null)/*CODE*/,(null)/*NAME*/,(null)/*REMARK*/,(null)/*IS_SUCC*/,(null)/*ERR_INFO*/,(null)/*WF_PROCESS_INSTANCE_ID*/)", newId, adminUserId, adminUserId);
        return newId;
    }

    private int updateLog(boolean succ, String errInfo, String procInstId, String logId) {
        return jdbcTemplate.update("update PF_GENERATION_LOG t set t.IS_SUCC=?,t.ERR_INFO=?,t.WF_PROCESS_INSTANCE_ID=? where t.id=?", succ, errInfo, procInstId, logId);
    }

    private String getPrjId(Map<String, Object> entityRecord) {
        String projectId = null;
        if (entityRecord != null && !SharedUtil.isEmptyObject(entityRecord.get("pm_prj_id"))){
            projectId = entityRecord.get("pm_prj_id").toString();
        } else if (!SharedUtil.isEmptyObject(entityRecord.get("pm_prj_ids"))){
            projectId = entityRecord.get("pm_prj_ids").toString();
        }
        return projectId;
    }

    public void createProcInstForImportedData() {
        // K为实体代码，V为对应的流程ID。
        Map<String, String> map = new HashMap<>();

        // K为实体代码，V为获取表单数据的ID。
        Map<String, String> map2 = new HashMap<>();

        // 立项：
        map.put("pm_prj_req", "0100031468511691070");
        map2.put("pm_prj_req", "select T.* from pm_prj_req t join gr_set_value v where t.IS_OMPORT=v.id and v.code='Y' and t.LK_WF_INST_ID is null and t.CRT_DT<date_add(now(),interval -5 minute)");

        // 可研：
        map.put("pm_prj_invest1", "0100031468512029141");
        map2.put("pm_prj_invest1", "select T.* from pm_prj_invest1 t join gr_set_value v where t.IS_OMPORT=v.id and v.code='Y' and t.LK_WF_INST_ID is null and t.CRT_DT<date_add(now(),interval -5 minute)");


        // 概算：
        map.put("pm_prj_invest2", "0100031468512030981");
        map2.put("pm_prj_invest2", "select T.* from pm_prj_invest2 t join gr_set_value v where t.IS_OMPORT=v.id and v.code='Y' and t.LK_WF_INST_ID is null and t.CRT_DT<date_add(now(),interval -5 minute)");

        // 合同签订：
        map.put("po_order_req", "0099952822476409136");
        map2.put("po_order_req", "select T.* from po_order_req t where t.IS_IMPORT=1 and t.LK_WF_INST_ID is null and t.CRT_DT<date_add(now(),interval -5 minute)");

        // 用地规划许可
        map.put("PM_LAND_USE_REQ", "0099902212142516744");
        map2.put("PM_LAND_USE_REQ", "select T.* from PM_LAND_USE_REQ t where t.IS_IMPORT=1 and t.LK_WF_INST_ID is null and t.CRT_DT<date_add(now(),interval -5 minute)");

        // 土地划拨
        map.put("PM_LAND_ALLOCATION_REQ", "0099902212142592327");
        map2.put("PM_LAND_ALLOCATION_REQ", "select T.* from PM_LAND_ALLOCATION_REQ t where t.IS_IMPORT=1 and t.LK_WF_INST_ID is null and t.CRT_DT<date_add(now(),interval -5 minute)");

        // 农转用手续办理
        map.put("PM_FARMING_PROCEDURES", "0099902212142514818");
        map2.put("PM_FARMING_PROCEDURES", "select T.* from PM_FARMING_PROCEDURES t where t.IS_IMPORT=1 and t.LK_WF_INST_ID is null and t.CRT_DT<date_add(now(),interval -5 minute)");

        // 工作联系单
        map.put("PM_WORK_LIST_REQ", "0099952822476361887");
        map2.put("PM_WORK_LIST_REQ", "select T.* from PM_WORK_LIST_REQ t where t.IS_IMPORT=1 and t.LK_WF_INST_ID is null and t.CRT_DT<date_add(now(),interval -5 minute)");

        // 监理规划及细则
        map.put("PM_SUPERVISE_PLAN_REQ", "0099902212142023273");
        map2.put("PM_SUPERVISE_PLAN_REQ", "select T.* from PM_SUPERVISE_PLAN_REQ t where t.IS_IMPORT=1 and t.LK_WF_INST_ID is null and t.CRT_DT<date_add(now(),interval -5 minute)");

        // 施工组织设计及施工方案
        map.put("PM_BUILD_ORGAN_PLAN_REQ", "0099952822476361252");
        map2.put("PM_BUILD_ORGAN_PLAN_REQ", "select T.* from PM_BUILD_ORGAN_PLAN_REQ t where t.IS_IMPORT=1 and t.LK_WF_INST_ID is null and t.CRT_DT<date_add(now(),interval -5 minute)");

        // 施工组织设计及施工方案
        map.put("SKILL_DISCLOSURE_PAPER_RECHECK_RECORD", "0099902212142038616");
        map2.put("SKILL_DISCLOSURE_PAPER_RECHECK_RECORD", "select T.* from SKILL_DISCLOSURE_PAPER_RECHECK_RECORD t where t.IS_IMPORT=1 and t.LK_WF_INST_ID is null and t.CRT_DT<date_add(now(),interval -5 minute)");

        // 工程规划许可证申请
        map.put("PM_PRJ_PLANNING_PERMIT_REQ", "0099902212142008086");
        map2.put("PM_PRJ_PLANNING_PERMIT_REQ", "select T.* from PM_PRJ_PLANNING_PERMIT_REQ t where t.IS_IMPORT=1 and t.LK_WF_INST_ID is null and t.CRT_DT<date_add(now(),interval -5 minute)");

        // 项目结算审批
        map.put("PM_PRJ_SETTLE_ACCOUNTS", "1640179993847930880");
        map2.put("PM_PRJ_SETTLE_ACCOUNTS", "select T.* from PM_PRJ_SETTLE_ACCOUNTS t where t.IS_IMPORT=1 and t.LK_WF_INST_ID is null and t.CRT_DT<date_add(now(),interval -5 minute)");

        for (String entCode : map.keySet()) {
            String procId = map.get(entCode);
            Map<String, Object> ent = jdbcTemplate.queryForMap("select * from ad_ent where code=?", entCode);
            Map<String, Object> proc = jdbcTemplate.queryForMap("select * from wf_process where id=?", procId);

            String sql = map2.get(entCode);
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            for (Map<String, Object> row : list) {

                Object crtUserId = row.get("CRT_USER_ID");
                Object crt_dt = row.get("CRT_DT");

                String procInstName = proc.get("NAME") + "-历史数据导入";

                Object entityRecordId = row.get("ID");
                String procInstId = insertProcInst(crtUserId, procInstName, procId, crt_dt, ent.get("ID"), entCode, entityRecordId);
                jdbcTemplate.update("update " + entCode + " set LK_WF_INST_ID=? WHERE ID=?", procInstId, entityRecordId);

                createNodeList(procId, crtUserId, crt_dt, procInstId);
            }
        }
    }

    private void createNodeList(String procId, Object crtUserId, Object crt_dt, String procInstId) {
        List<Map<String, Object>> nodeList = jdbcTemplate.queryForList("SELECT N.* FROM WF_NODE N WHERE N.WF_PROCESS_ID=? AND N.`STATUS`='AP' ORDER BY N.SEQ_NO", procId);
        Object lastNodeId = null;
        Object lastNodeInstId = null;
        Object lastNodeViewId = null;
        // 若有节点列表，则针对每个节点，插入节点实例：
        if (!SharedUtil.isEmptyList(nodeList)) {
            for (int i = 0; i < nodeList.size(); i++) {
                Map<String, Object> node = nodeList.get(i);
                boolean isLast = i == nodeList.size() - 1;
                Object nodeId = node.get("ID");
                String nodeInstId = insertNodeInst(crtUserId, procId, procInstId, nodeId, crt_dt, isLast, node.get("NAME"));
                if (isLast) {
                    lastNodeId = nodeId;
                    lastNodeInstId = nodeInstId;
                    lastNodeViewId = node.get("AD_VIEW_ID");
                }
            }
        }
        // 若有最末的节点实例ID，则设置流程实例的当前信息：
        if (!SharedUtil.isEmptyObject(lastNodeId)) {
            int update = jdbcTemplate.update("update wf_process_instance pi set pi.CURRENT_NODE_ID=?,pi.CURRENT_NI_ID=?,pi.CURRENT_VIEW_ID=? where pi.id=?", lastNodeId, lastNodeInstId, lastNodeViewId, procInstId);
            log.info("已更新流程实例：" + update);
        }
    }

    public void createNodeInstListForImportedData() {
        // 获取
        List<Map<String, Object>> procInstList = jdbcTemplate.queryForList("SELECT * FROM WF_PROCESS_INSTANCE PI WHERE PI.NAME LIKE '%-历史数据导入' AND NOT EXISTS(SELECT 1 FROM WF_NODE_INSTANCE N WHERE N.WF_PROCESS_INSTANCE_ID=PI.ID)/* AND PI.ID='1634108398195544064'*/");
        if (SharedUtil.isEmptyList(procInstList)) {
            return;
        }

        for (Map<String, Object> procInst : procInstList) {
            createNodeList(JdbcMapUtil.getString(procInst, "WF_PROCESS_ID"), JdbcMapUtil.getString(procInst, "START_USER_ID"), JdbcMapUtil.getString(procInst, "START_DATETIME"), JdbcMapUtil.getString(procInst, "ID"));
        }
    }

    private String insertProcInst(Object userId, Object name, Object procId, Object datetime, Object entId, Object entCode, Object entityRecordId) {
        java.lang.String newId = IdUtil.getSnowflakeNextIdStr();

        jdbcTemplate.update("INSERT INTO WF_PROCESS_INSTANCE(`ID`,`VER`,`TS`,`IS_PRESET`,`CRT_DT`,`CRT_USER_ID`,`LAST_MODI_DT`,`LAST_MODI_USER_ID`,`STATUS`,`LK_WF_INST_ID`,`CODE`,`NAME`,`REMARK`,`WF_PROCESS_ID`,`START_USER_ID`,`START_DATETIME`,`END_DATETIME`,`AD_ENT_ID`,`ENT_CODE`,`ENTITY_RECORD_ID`,`IS_URGENT`,`WF_INTERFERE_ID`,`CURRENT_NODE_ID`,`CURRENT_NI_ID`,`CURRENT_TODO_USER_IDS`,`CURRENT_VIEW_ID`)VALUES(?/*ID*/,(1)/*VER*/,(NOW())/*TS*/,(null)/*IS_PRESET*/,(NOW())/*CRT_DT*/,(?)/*CRT_USER_ID*/,(NOW())/*LAST_MODI_DT*/,(?)/*LAST_MODI_USER_ID*/,('AP')/*STATUS*/,(null)/*LK_WF_INST_ID*/,(null)/*CODE*/,(?)/*NAME*/,(null)/*REMARK*/,(?)/*WF_PROCESS_ID*/,(?)/*START_USER_ID*/,(?)/*START_DATETIME*/,(?)/*END_DATETIME*/,(?)/*AD_ENT_ID*/,(?)/*ENT_CODE*/,(?)/*ENTITY_RECORD_ID*/,(null)/*IS_URGENT*/,(null)/*WF_INTERFERE_ID*/,(null)/*CURRENT_NODE_ID*/,(null)/*CURRENT_NI_ID*/,(null)/*CURRENT_TODO_USER_IDS*/,(null)/*CURRENT_VIEW_ID*/)", newId, userId, userId, name, procId, userId, datetime, datetime, entId, entCode, entityRecordId);
        return newId;
    }

    private String insertNodeInst(Object userId, Object procId, Object procInstId, Object nodeId, Object datetime, Object isCurrent, Object name) {
        java.lang.String newId = IdUtil.getSnowflakeNextIdStr();

        jdbcTemplate.update("INSERT INTO WF_NODE_INSTANCE(`ID`,`VER`,`TS`,`IS_PRESET`,`CRT_DT`,`CRT_USER_ID`,`LAST_MODI_DT`,`LAST_MODI_USER_ID`,`STATUS`,`LK_WF_INST_ID`,`CODE`,`NAME`,`REMARK`,`WF_PROCESS_ID`,`WF_PROCESS_INSTANCE_ID`,`WF_NODE_ID`,`START_DATETIME`,`END_DATETIME`,`EFFECTIVE_ACT_ID`,`FROM_FLOW_ID`,`IN_CURRENT_ROUND`,`IS_CURRENT`,`SEQ_NO`,`WF_INTERFERE_ID`,`FORWARD_TO_NODE_ID`)VALUES(?/*ID*/,(1)/*VER*/,(NOW())/*TS*/,(null)/*IS_PRESET*/,(NOW())/*CRT_DT*/,(?)/*CRT_USER_ID*/,(NOW())/*LAST_MODI_DT*/,(?)/*LAST_MODI_USER_ID*/,('AP')/*STATUS*/,(null)/*LK_WF_INST_ID*/,(null)/*CODE*/,(?)/*NAME*/,(null)/*REMARK*/,(?)/*WF_PROCESS_ID*/,(?)/*WF_PROCESS_INSTANCE_ID*/,(?)/*WF_NODE_ID*/,(?)/*START_DATETIME*/,(?)/*END_DATETIME*/,(null)/*EFFECTIVE_ACT_ID*/,(null)/*FROM_FLOW_ID*/,(1)/*IN_CURRENT_ROUND*/,(?)/*IS_CURRENT*/,(?)/*SEQ_NO*/,(null)/*WF_INTERFERE_ID*/,(null)/*FORWARD_TO_NODE_ID*/)", newId, userId, userId, name, procId, procInstId, nodeId, datetime, datetime, isCurrent, IdUtil.getSnowflakeNextIdStr());
        return newId;
    }
}
