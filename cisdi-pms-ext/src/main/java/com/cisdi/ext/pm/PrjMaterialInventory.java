package com.cisdi.ext.pm;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.MaterialInventoryType;
import com.cisdi.ext.model.PmPrj;
import com.cisdi.ext.model.PrjInventory;
import com.cisdi.ext.model.PrjInventoryDetail;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dlt
 * @date 2023/4/18 周二
 */
@Slf4j
public class PrjMaterialInventory {

    /**
     * 初始化项目清单
     * 根据清单类型给每个项目加一套清单
     */
    public void initPrjInventory() {
        List<PmPrj> pmPrjs = PmPrj.selectByWhere(new Where().eq(PmPrj.Cols.STATUS, "AP"));
        List<MaterialInventoryType> materialInventoryTypes = MaterialInventoryType.selectByWhere(new Where().eq(MaterialInventoryType.Cols.STATUS,
                "AP"));
        List<PrjInventory> prjInventories = PrjInventory.selectByWhere(new Where().eq(PrjInventory.Cols.STATUS, "AP"));
        for (PmPrj pmPrj : pmPrjs) {
            for (MaterialInventoryType type : materialInventoryTypes) {
                //如果该条项目清单已有，跳过
                if (!CollectionUtils.isEmpty(prjInventories)) {
                    Optional<PrjInventory> opPrjInventory = prjInventories.stream()
                            .filter(prjInventory -> prjInventory.getPmPrjId().equals(pmPrj.getId()) && prjInventory.getMaterialInventoryTypeId().equals(type.getId()))
                            .findAny();
                    if (opPrjInventory.isPresent()) {
                        log.info("跳过" + pmPrj.getName() + type.getName());
                        continue;
                    }
                }
                //没有该条项目清单，插入
                PrjInventory prjInventory = PrjInventory.newData();
                prjInventory.setPmPrjId(pmPrj.getId()).setMaterialInventoryTypeId(type.getId()).setIsInvolved(true);
                prjInventory.insertById();
            }
        }
    }

    /**
     * 初始化清单明细
     * 将流程中的文件填入清单明细
     */
    public void initInventoryDetail() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        //和清单相关的流程实例，带清单对应的字段
        List<Map<String, Object>> instanceList = myJdbcTemplate.queryForList("select i.id instanceId,ty.id typeId,i.AD_ENT_ID entId,i.ENT_CODE entCode,i" +
                ".ENTITY_RECORD_ID entRecordId,ty.AD_ENT_ATT_V_ID entAttId,a.code attCode,a.name attName from wf_process_instance i \n" +
                "left join material_inventory_type ty on ty.WF_PROCESS_ID = i.WF_PROCESS_ID\n" +
                "left join ad_ent_att ea on ea.id = ty.AD_ENT_ATT_V_ID\n" +
                "left join ad_att a on a.id = ea.AD_ATT_ID\n" +
                "where ty.WF_PROCESS_ID is not null and i.status = 'AP' and ty.status = 'AP' and ea.status = 'AP' and a.status = 'AP'");

        for (Map<String, Object> instance : instanceList) {
            String instanceId = instance.get("instanceId").toString();//流程实例id
            String typeId = instance.get("typeId").toString();//清单类型id
            String entCode = instance.get("entCode").toString();//申请单表名
            String attCode = instance.get("attCode").toString();//字段名
            String recordId = instance.get("entRecordId").toString();//记录id

            String sql = "select i.id inventoryId,t." + attCode + " fileIds from " + entCode + " t " +
                    "left join prj_inventory i on i.PM_PRJ_ID = t.PM_PRJ_ID " +
                    "where t.id = ? and i.MATERIAL_INVENTORY_TYPE_ID = ? " +
                    "and t.status = 'AP' and i.status = 'AP'";
            List<Map<String, Object>> recordList = myJdbcTemplate.queryForList(sql, recordId, typeId);
            if (CollectionUtils.isEmpty(recordList)){
                continue;
            }
            Map<String, Object> recordMap = recordList.get(0);
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
                //插入清单明细
                PrjInventoryDetail prjInventoryDetail = PrjInventoryDetail.newData();
                prjInventoryDetail.setPrjInventoryId(inventoryId);
                prjInventoryDetail.setFlFileId(fileId);
                prjInventoryDetail.setWfProcessInstanceId(instanceId);
                prjInventoryDetail.insertById();
                log.info("新增明细" + prjInventoryDetail.getId());
            }
        }

    }

    /**
     * 项目清单列表
     */
    public void prjInventoryList(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        listReq listReq = JSONObject.parseObject(JSONObject.toJSONString(inputMap), listReq.class);
        List<String> prjIds = listReq.getPrjIds();

        List<Map<String, Object>> detailList = myJdbcTemplate.queryForList("select \n" +
                "i.PM_PRJ_ID prjId,\n" +
                "p.name prjName,\n" +
                "ty.FILE_MASTER_INVENTORY_TYPE_ID masterTypeId,\n" +
                "v.name masterInventoryName,\n" +
                "d.FL_FILE_ID fileId,\n" +
                "d.id detailId,\n" +
                "i.id prjInventoryId,\n" +
                "d.WF_PROCESS_INSTANCE_ID instanceId\n" +
                "from prj_inventory_detail d \n" +
                "left join prj_inventory i on i.id = d.PRJ_INVENTORY_ID\n" +
                "left join pm_prj p on p.id = i.PM_PRJ_ID\n" +
                "left join material_inventory_type ty on ty.id = i.MATERIAL_INVENTORY_TYPE_ID\n" +
                "left join gr_set_value v on v.id = ty.FILE_MASTER_INVENTORY_TYPE_ID\n" +
                "order by i.PM_PRJ_ID");

        //项目主清单应有的子清单
        List<Map<String, Object>> prjMasterShouldHave = myJdbcTemplate.queryForList("select i.PM_PRJ_ID prjId,p.name prjName,COUNT(i" +
                ".MATERIAL_INVENTORY_TYPE_ID) shouldHave,v.name masterTypeName,v.id masterTypeId from prj_inventory i \n" +
                "left join material_inventory_type t on t.id = i.MATERIAL_INVENTORY_TYPE_ID \n" +
                "left join gr_set_value v on v.id = t.FILE_MASTER_INVENTORY_TYPE_ID\n" +
                "left join pm_prj p on p.id = i.PM_PRJ_ID\n" +
                "where i.IS_INVOLVED = 1\n" +
                "group by i.PM_PRJ_ID,t.FILE_MASTER_INVENTORY_TYPE_ID");

        //项目主清单实际有的子清单
        List<Map<String, Object>> prjMasterActualHave = myJdbcTemplate.queryForList("select prjId,masterTypeId,sum(have) actualHave from (\n" +
                "select i.PM_PRJ_ID prjId,t.id typeId,t.name typeName,IF(count(d.id) > 0,1,0) have,t.FILE_MASTER_INVENTORY_TYPE_ID masterTypeId\n" +
                "from prj_inventory i \n" +
                "left join material_inventory_type t on t.id = i.MATERIAL_INVENTORY_TYPE_ID \n" +
                "left join prj_inventory_detail d on d.PRJ_INVENTORY_ID = i.id\n" +
                "where i.IS_INVOLVED = 1\n" +
                "group by i.PM_PRJ_ID,t.id\n" +
                ") temp group by prjId,masterTypeId");

        //组合
        for (Map<String, Object> shouldHaveMap : prjMasterShouldHave) {
            for (Map<String, Object> actualHaveMap : prjMasterActualHave) {
                String shouldPrjId = JdbcMapUtil.getString(shouldHaveMap, "prjId");
                String actualPrjId = JdbcMapUtil.getString(actualHaveMap, "prjId");
                String shouldMasterTypeId = JdbcMapUtil.getString(shouldHaveMap, "masterTypeId");
                String actualMasterTypeId = JdbcMapUtil.getString(actualHaveMap, "masterTypeId");
                String shouldHave = JdbcMapUtil.getString(shouldHaveMap, "shouldHave");
                String actualHave = JdbcMapUtil.getString(actualHaveMap, "actualHave");
                if (shouldPrjId.equals(actualPrjId) && shouldMasterTypeId.equals(actualMasterTypeId)){
                    if (Integer.parseInt(shouldHave) > Integer.parseInt(actualHave)){
                        shouldHaveMap.put("shouldHave",actualHave + "/" + shouldHave + "（缺少文件）");
                    }else {
                        shouldHaveMap.put("shouldHave",actualHave + "/" + shouldHave);
                    }
                }
            }
        }

        List<String> headers = new ArrayList<>();
        //
        Map<String, List<Map<String, Object>>> data =
                prjMasterShouldHave.stream().collect(Collectors.groupingBy(item -> item.get("prjId").toString()));
        ArrayList<Object> result = new ArrayList<>();
        for (String prjId : data.keySet()) {
            Map<String, Object> resultData = new HashMap<>();
            List<Map<String, Object>> list = data.get(prjId);
            for (String header : headers) {
                Optional<Object> shouldHaveOp = list.stream().filter(d -> d.get("masterTypeName").equals(header)).map(d -> d.get("shouldHave")).findAny();
                resultData.put(header,shouldHaveOp.get().toString());

            }
        }
        System.out.println(data);


    }

    /**
     * 列表请求入参
     */
    @Data
    private static class listReq{
        private List<String> prjIds;
    }

    /**
     * 列表页单条数据
     */
    @Data
    private static class inventoryData{
        private String prjId;
        private String prjName;
        //动态列
        private List<Map<String,Object>> statistic;
    }


    public static void main(String[] args) {

//        ArrayList<String> myList = new ArrayList<>();
//        Map<List<String>, Integer> map =
//                myList.stream()
//                        .collect(Collectors.groupingBy(
//                                f -> Arrays.asList(f.getType(), f.getCode()),
//                                Collectors.summingInt(Foo::getQuantity)
//                        ));
//
//        List<Foo> result =
//                map.entrySet()
//                        .stream()
//                        .map(e -> new Foo(e.getKey().get(0), e.getValue(), e.getKey().get(1)))
//                        .collect(Collectors.toList());

    }



}
