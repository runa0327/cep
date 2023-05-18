package com.cisdi.pms.job.serviceImpl;

import com.cisdi.pms.job.service.PrjInventoryService;
import com.cisdi.pms.job.utils.ListUtils;
import com.cisdi.pms.job.utils.Util;
import com.google.common.base.Strings;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author dlt
 * @date 2023/5/15 周一
 */
@Service
@Slf4j
public class PrjInventoryServiceImpl implements PrjInventoryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.task.execution.pool.core-size}")
    private Integer coreSize;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor executor;

    @Override
    public void init() {
        //清空明细
//        jdbcTemplate.update("delete from PRJ_INVENTORY_DETAIL");

        List<Map<String, Object>> prjMaps = jdbcTemplate.queryForList("select * from pm_prj where status = 'AP'");
        List<Map<String, Object>> materialInventoryTypeMaps = jdbcTemplate.queryForList("select * from MATERIAL_INVENTORY_TYPE where status = 'AP'");
        List<Map<String, Object>> prjInventoryMaps = jdbcTemplate.queryForList("select * from PRJ_INVENTORY where status = 'AP'");

        if (!CollectionUtils.isEmpty(prjMaps)){
            int size = prjMaps.size() / coreSize + 1;
            List<List<Map<String, Object>>> splits = ListUtils.split(prjMaps, size);
            for (List<Map<String, Object>> split : splits) {
                executor.execute(() -> {
                    for (Map<String, Object> prjMap : split) {
                        this.doAddPrjInventory(prjMap,materialInventoryTypeMaps,prjInventoryMaps);
                    }
                });
            }
        }

        //初始化清单明细
        initInventoryDetail();

        log.info("success!");
    }

    //初始化明细
    private void initInventoryDetail() {

        //流程表的项目字段
        List<Map<String, Object>> processPrjFields = jdbcTemplate.queryForList("select e.code entCode,GROUP_CONCAT(a.code) attCodes from wf_process p\n" +
                "left join AD_SINGLE_ENT_VIEW ev on ev.id = p.STARTABLE_SEV_IDS\n" +
                "left join ad_ent e on e.id = ev.AD_ENT_ID\n" +
                "left join ad_ent_att ea on ea.AD_ENT_ID = e.id\n" +
                "left join ad_att a on a.id = ea.AD_ATT_ID\n" +
                "where p.status = 'AP' and (a.code = 'PM_PRJ_ID' or a.code = 'PM_PRJ_IDS')\n" +
                "group by e.code");

        //和清单相关的流程实例，带清单对应的字段
        List<Map<String, Object>> instanceList = jdbcTemplate.queryForList("select i.id instanceId,ty.id typeId,i.AD_ENT_ID entId,i.ENT_CODE entCode,i" +
                ".ENTITY_RECORD_ID entRecordId,ty.AD_ENT_ATT_V_ID entAttId,a.code attCode,a.name attName from wf_process_instance i \n" +
                "left join material_inventory_type ty on ty.WF_PROCESS_ID = i.WF_PROCESS_ID\n" +
                "left join ad_ent_att ea on ea.id = ty.AD_ENT_ATT_V_ID\n" +
                "left join ad_att a on a.id = ea.AD_ATT_ID\n" +
                "where ty.WF_PROCESS_ID is not null and i.status = 'AP' and ty.status = 'AP' and ea.status = 'AP' and a.status = 'AP'");

        //全部清单明细
        List<Map<String, Object>> inventoryDtlList = jdbcTemplate.queryForList("select id,PRJ_INVENTORY_ID inventoryId,FL_FILE_ID fileId,WF_PROCESS_INSTANCE_ID" +
                " instanceId from PRJ_INVENTORY_DETAIL");

        for (Map<String, Object> instance : instanceList) {
            String instanceId = instance.get("instanceId").toString();//流程实例id
            String typeId = instance.get("typeId").toString();//清单类型id
            String entCode = instance.get("entCode").toString().toUpperCase();//申请单表名
            String attCode = instance.get("attCode").toString();//字段名
            String recordId = instance.get("entRecordId").toString();//记录id
            String sql = "";
            try {
                isSinglePrj(processPrjFields, entCode);//判断项目报错,直接跳过
            }catch (Exception e){
                continue;
            }

            if (isSinglePrj(processPrjFields,entCode)){
                sql = "select i.id inventoryId,t." + attCode + " fileIds from " + entCode + " t " +
                        "left join prj_inventory i on i.PM_PRJ_ID = t.PM_PRJ_ID " +
                        "where t.id = ? and i.MATERIAL_INVENTORY_TYPE_ID = ? " +
                        "and t.status = 'AP' and i.status = 'AP'";
            }else {
                sql = "select i.id inventoryId,t." + attCode + " fileIds from " + entCode + " t " +
                        "left join prj_inventory i on FIND_IN_SET(i.PM_PRJ_ID,t.PM_PRJ_IDS) " +
                        "where t.id = ? and i.MATERIAL_INVENTORY_TYPE_ID = ? " +
                        "and t.status = 'AP' and i.status = 'AP'";
            }

            List<Map<String, Object>> recordList = jdbcTemplate.queryForList(sql, recordId, typeId);
            if (CollectionUtils.isEmpty(recordList)){
                continue;
            }
            for (Map<String, Object> recordMap : recordList) {
                if (CollectionUtils.isEmpty(recordMap)){
                    continue;
                }
                String fileIds = JdbcMapUtil.getString(recordMap, "fileIds");
                if (Strings.isNullOrEmpty(fileIds)){
                    continue;
                }
                //项目清单id
                String inventoryId = JdbcMapUtil.getString(recordMap, "inventoryId");
                //走到这里，fileIds不为空
                String[] fileIdArr = fileIds.split(",");
                for (String fileId : fileIdArr) {
                    Optional<Map<String, Object>> anyDlt = inventoryDtlList.stream()
                            .filter(dlt -> inventoryId.equals(String.valueOf(dlt.get("inventoryId"))) && fileId.equals(String.valueOf(dlt.get(
                                    "fileId"))) && instanceId.equals(String.valueOf(dlt.get("instanceId"))))
                            .findAny();
                    if (anyDlt.isPresent()){//存在dtl，不插入，跳过
                        continue;
                    }
                    //插入清单明细
                    String detailId = Util.insertData(jdbcTemplate, "PRJ_INVENTORY_DETAIL");
                    jdbcTemplate.update("update PRJ_INVENTORY_DETAIL set prj_inventory_id = ?,fl_file_id = ?,wf_process_instance_id = ? where id = ?"
                            ,inventoryId,fileId,instanceId,detailId);
                    log.info("新增明细" + detailId);
                }
            }
        }
    }

    private void doAddPrjInventory(Map<String, Object> prjMap, List<Map<String, Object>> materialInventoryTypeMaps, List<Map<String, Object>> prjInventoryMaps) {
        for (Map<String,Object> typeMap : materialInventoryTypeMaps) {
            //如果该条项目清单已有，跳过
            if (!CollectionUtils.isEmpty(prjInventoryMaps)) {
                Optional<Map<String, Object>> opPrjInventory = prjInventoryMaps.stream()
                        .filter(prjInventory -> prjInventory.get("PM_PRJ_ID").equals(prjMap.get("ID")) && prjInventory.get(
                                "MATERIAL_INVENTORY_TYPE_ID").equals(typeMap.get("ID")))
                        .findAny();

                if (opPrjInventory.isPresent()) {
                    log.info("跳过" + prjMap.get("NAME") + typeMap.get("NAME"));
                    continue;
                }
            }
            //没有该条项目清单，插入
            String inventoryId = Util.insertData(jdbcTemplate, "PRJ_INVENTORY");
            jdbcTemplate.update("update PRJ_INVENTORY set pm_prj_id = ?,material_inventory_type_id = ?,is_involved = true where id = ?",prjMap.get("ID"),typeMap.get("ID"),inventoryId);
            log.info("插入PRJ_INVENTORY" + inventoryId);
        }
    }

    /**
     * 判断流程表是否是单项目
     * @param processPrjFields
     * @param entCode 表code
     */
    private boolean isSinglePrj(List<Map<String, Object>> processPrjFields,String entCode){
        Optional<Map<String, Object>> op = processPrjFields.stream()
                .filter(item -> entCode.equals(item.get("entCode").toString()))
                .findAny();
        Map<String, Object> processPrjField = op.get();
        String attCodes = processPrjField.get("attCodes").toString();
        //包含s，false；不包含s，ture
        return !attCodes.contains("PM_PRJ_IDS");
    }
}
