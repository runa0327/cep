package com.cisdi.ext.pm;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.MaterialInventoryType;
import com.cisdi.ext.model.PmPrj;
import com.cisdi.ext.model.PrjInventory;
import com.cisdi.ext.model.PrjInventoryDetail;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
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
        List<MaterialInventoryType> materialInventoryTypes = MaterialInventoryType.selectByWhere(new Where().eq(MaterialInventoryType.Cols.STATUS, "AP"));
        List<PrjInventory> prjInventories = PrjInventory.selectByWhere(new Where().eq(PrjInventory.Cols.STATUS, "AP"));
        for (PmPrj pmPrj : pmPrjs) {
            doAddPrjInventory(pmPrj,materialInventoryTypes,prjInventories);
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
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        ListReq ListReq = JSONObject.parseObject(JSONObject.toJSONString(inputMap), ListReq.class);
        List<String> prjIds = ListReq.getPrjIds();
        int pageSize = ListReq.getPageSize();
        int pageIndex = ListReq.getPageIndex();
        int start = pageSize * (pageIndex - 1);


        String sql = "select id from pm_prj where status = 'AP' ";
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("start",start);
        queryParams.put("pageSize",pageSize);
        if (!CollectionUtils.isEmpty(prjIds)){
            sql += " and id in (:prjId)";
            queryParams.put("prjId",prjIds);
        }
        String totalSql = sql;
        int total = myJdbcTemplate.queryForList(totalSql).size();
        sql += " order by id desc limit :start,:pageSize";
        List<Map<String, Object>> prjIdMaps = myNamedParameterJdbcTemplate.queryForList(sql,queryParams);
        List<String> prjIdList = prjIdMaps.stream().map(m -> m.get("id").toString()).collect(Collectors.toList());
        //获取动态表头
        List<Map<String, Object>> headerMaps = myJdbcTemplate.queryForList("select v.name from gr_set_value v left join gr_set s on s.id = v.GR_SET_ID " +
                "where s.code = 'file_master_list_type' and v.status = 'AP'");
        List<String> headers = headerMaps.stream().map(m -> m.get("name").toString()).collect(Collectors.toList());

        List<Map<String, Object>> originList = new ArrayList<>();
        //给分页后的每个项目组装数据
        for (String prjId : prjIdList) {
            List<Map<String, Object>> partOriginList = myJdbcTemplate.queryForList("select sv.prjId,sv.prjName,sv.masterTypeId,sv.masterTypeName,sv" +
                    ".shouldHave,ah.actualHave,lt.lackTypeName from (\n" +
                    "select i.PM_PRJ_ID prjId,p.name prjName,COUNT(i.MATERIAL_INVENTORY_TYPE_ID) shouldHave,v.name masterTypeName,v.id masterTypeId " +
                    "from prj_inventory i \n" +
                    "left join material_inventory_type t on t.id = i.MATERIAL_INVENTORY_TYPE_ID \n" +
                    "left join gr_set_value v on v.id = t.FILE_MASTER_INVENTORY_TYPE_ID\n" +
                    "left join pm_prj p on p.id = i.PM_PRJ_ID\n" +
                    "where i.IS_INVOLVED = 1 and p.status = 'AP'\n" +
                    "group by i.PM_PRJ_ID,t.FILE_MASTER_INVENTORY_TYPE_ID\n" +
                    ") sv\n" +
                    "left join \n" +
                    "(select prjId,masterTypeId,sum(have) actualHave from (\n" +
                    "select i.PM_PRJ_ID prjId,t.id typeId,t.name typeName,IF(count(d.id) > 0,1,0) have,t.FILE_MASTER_INVENTORY_TYPE_ID " +
                    "masterTypeId\n" +
                    "from prj_inventory i \n" +
                    "left join material_inventory_type t on t.id = i.MATERIAL_INVENTORY_TYPE_ID \n" +
                    "left join prj_inventory_detail d on d.PRJ_INVENTORY_ID = i.id\n" +
                    "where i.IS_INVOLVED = 1\n" +
                    "group by i.PM_PRJ_ID,t.id\n" +
                    ") temp group by prjId,masterTypeId) ah on ah.prjId = sv.prjId and ah.masterTypeId = sv.masterTypeId\n" +
                    "left join \n" +
                    "(select prjId,masterTypeId,GROUP_CONCAT(typeName) lackTypeName from (\n" +
                    "select i.PM_PRJ_ID prjId,\n" +
                    "v.name masterTypeName,\n" +
                    "v.id masterTypeId,\n" +
                    "t.name typeName,\n" +
                    "count(d.id)\n" +
                    "from prj_inventory i \n" +
                    "left join material_inventory_type t on t.id= i.MATERIAL_INVENTORY_TYPE_ID\n" +
                    "left join gr_set_value v on v.id = t.FILE_MASTER_INVENTORY_TYPE_ID\n" +
                    "left join prj_inventory_detail d on d.PRJ_INVENTORY_ID = i.id\n" +
                    "where i.IS_INVOLVED = 1\n" +
                    "group by i.PM_PRJ_ID,t.FILE_MASTER_INVENTORY_TYPE_ID,t.id\n" +
                    "having count(d.id) = 0\n" +
                    ") temp group by prjId,masterTypeId) lt on lt.prjId = sv.prjId and lt.masterTypeId = sv.masterTypeId where sv.prjId = ?", prjId);
            originList.addAll(partOriginList);
        }
        //封装
        Map<String, List<Map<String, Object>>> data = originList.stream().collect(Collectors.groupingBy(item -> item.get("prjId").toString()));
        List<InventoryData> inventoryDataList = new ArrayList<>();//列表数据
        for (String prjId : prjIdList) {
            InventoryData inventoryData = new InventoryData();//单条列表数据
            List<Map<String,Object>> statistic = new ArrayList<>();//单条动态数据
            List<Map<String, Object>> prjOriginList = data.get(prjId);//单条项目维度的初始数据
            if (CollectionUtils.isEmpty(prjOriginList)){//可能有项目没有刷出清单模板
                continue;
            }
            for (String header : headers) {
                Map<String,Object> cellData = new HashMap<>();//单个单元格
                Map<String, Object> partCell = new HashMap<>();//单个单元格的一部分数据
                Optional<Map<String, Object>> masterTypeOp = prjOriginList.stream().filter(d -> d.get("masterTypeName").equals(header)).findAny();
                if (masterTypeOp.isPresent()){
                    partCell = masterTypeOp.get();
                }
                cellData.put(header,partCell);
                statistic.add(cellData);
            }
            inventoryData.statistic = statistic;
            inventoryData.prjId = prjId;
            inventoryData.prjName = JdbcMapUtil.getString(data.get(prjId).get(0),"prjName");
            inventoryDataList.add(inventoryData);
        }

        //返回
        ListResp listResp = new ListResp();
        listResp.inventoryDataList = inventoryDataList;
        listResp.total = total;
        Map output = JsonUtil.fromJson(JsonUtil.toJson(listResp), Map.class);
        ExtJarHelper.returnValue.set(output);

    }

    /**
     * 清单详情列表
     */
    public void inventoryDltList(){
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        DltReq dltReq = JSONObject.parseObject(JSONObject.toJSONString(params), DltReq.class);
        String sql = "select ty.name typeName,f.id fileId,f.DSP_NAME fileName,f.DSP_SIZE fileSize,f.UPLOAD_DTTM uploadTime,u.name uploadUser from " +
                "prj_inventory_detail d \n" +
                "left join prj_inventory i on i.id = d.PRJ_INVENTORY_ID\n" +
                "left join material_inventory_type ty on ty.id = i.MATERIAL_INVENTORY_TYPE_ID\n" +
                "left join fl_file f on f.id = d.FL_FILE_ID\n" +
                "left join ad_user u on u.id = f.CRT_USER_ID\n" +
                "where i.PM_PRJ_ID = '" + dltReq.prjId + "'";
        //主清单类型 不传查所有
        if (!Strings.isNullOrEmpty(dltReq.masterTypeId)){
            sql += " and ty.FILE_MASTER_INVENTORY_TYPE_ID = '" + dltReq.masterTypeId + "'";
        }
        //清单名称
        if (!Strings.isNullOrEmpty(dltReq.typeName)){
            sql += " and ty.name like '%" + dltReq.typeName + "%'";
        }
        //资料名称
        if (!Strings.isNullOrEmpty(dltReq.fileName)){
            sql += " and f.DSP_NAME like '%" + dltReq.fileName + "%'";
        }
        int start = (dltReq.pageIndex - 1) * dltReq.pageSize;
        List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(sql);
        sql += " order by ty.name limit " + start + "," + dltReq.pageSize;
        List<Map<String, Object>> dtlList = myJdbcTemplate.queryForList(sql);

        //封装返回
        List<InventoryDtl> inventoryDtls = dtlList.stream()
                .map(m -> {
                    InventoryDtl inventoryDtl = JSONObject.parseObject(JSONObject.toJSONString(m), InventoryDtl.class);
                    inventoryDtl.uploadTime = StringUtil.withOutT(inventoryDtl.uploadTime);
                    return inventoryDtl;
                })
                .collect(Collectors.toList());

        DtlResp dtlResp = new DtlResp();
        dtlResp.inventoryDtls = inventoryDtls;
        dtlResp.total = totalList.size();
        Map result = JsonUtil.fromJson(JsonUtil.toJson(dtlResp), Map.class);
        ExtJarHelper.returnValue.set(result);
    }

    /**
     * 提供给外部的，为项目添加清单
     * @param prjId
     */
    public static void addPrjInventory(String prjId){
        //项目对象
        PmPrj pmPrj = PmPrj.selectById(prjId);
        //清单类型
        List<MaterialInventoryType> materialInventoryTypes = MaterialInventoryType.selectByWhere(new Where().eq(MaterialInventoryType.Cols.STATUS, "AP"));
        //所有的项目清单
        List<PrjInventory> prjInventories = PrjInventory.selectByWhere(new Where().eq(PrjInventory.Cols.STATUS, "AP"));

        doAddPrjInventory(pmPrj,materialInventoryTypes,prjInventories);
    }

    /**
     * 真正添加项目清单
     * @param pmPrj 项目实体
     * @param materialInventoryTypes 清单类型集合
     * @param prjInventories 已有的项目清单
     */
    public static void doAddPrjInventory(PmPrj pmPrj,List<MaterialInventoryType> materialInventoryTypes,List<PrjInventory> prjInventories){
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

    /**
     * @param prjId 项目id
     * @param processId 流程id
     * @param processIncId 流程实例id
     */
    public void addInventoryDtl(String prjId,String processId,String processIncId){
        //根据流程实例id，获取申请单


    }

    /**
     * 列表请求入参
     */
    @Data
    private static class ListReq{
        private List<String> prjIds;
        private Integer pageSize;
        private Integer pageIndex;
    }
    
    @Data
    private static class ListResp {
        private List<InventoryData> inventoryDataList;
        private Integer total;
    }

    /**
     * 列表页单条数据
     */
    @Data
    private static class InventoryData{
        private String prjId;
        private String prjName;
        //动态列
        private List<Map<String,Object>> statistic;
    }

    /**
     * 明细请求入参
     */
    @Data
    private static class DltReq{
        private Integer pageIndex;
        private Integer pageSize;
        //项目id
        private String prjId;
        //主清单类型id
        private String masterTypeId;
        //清单名称
        private String typeName;
        //资料名称
        private String fileName;
    }

    /**
     * 清单明细
     */
    @Data
    private static class InventoryDtl{
        //清单名称
        private String typeName;
        //文件id
        private String fileId;
        //文件名称
        private String fileName;
        //文件大小
        private String fileSize;
        //上传时间
        private String uploadTime;
        //上传人
        private String uploadUser;
    }

    /**
     * 明细响应
     */
    @Data
    private static class DtlResp{
        private List<InventoryDtl> inventoryDtls;
        //总数
        private Integer total;
    }


    public static void main(String[] args) {
        
    }



}
