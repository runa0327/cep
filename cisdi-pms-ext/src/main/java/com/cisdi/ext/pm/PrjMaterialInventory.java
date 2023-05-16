package com.cisdi.ext.pm;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.*;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
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
        //清空历史数据
        emptyInventory();

        List<PmPrj> pmPrjs = PmPrj.selectByWhere(new Where().eq(PmPrj.Cols.STATUS, "AP"));
        List<MaterialInventoryType> materialInventoryTypes = MaterialInventoryType.selectByWhere(new Where().eq(MaterialInventoryType.Cols.STATUS, "AP"));
        List<PrjInventory> prjInventories = PrjInventory.selectByWhere(new Where().eq(PrjInventory.Cols.STATUS, "AP"));
        for (PmPrj pmPrj : pmPrjs) {
            doAddPrjInventory(pmPrj,materialInventoryTypes,prjInventories);
        }

        //初始化清单明细
        initInventoryDetail();
    }

    /**
     * 初始化清单明细
     * 将流程中的文件填入清单明细
     */
    private void initInventoryDetail() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        //流程表的项目字段
        List<Map<String, Object>> processPrjFields = myJdbcTemplate.queryForList("select e.code entCode,GROUP_CONCAT(a.code) attCodes from wf_process p\n" +
                "left join AD_SINGLE_ENT_VIEW ev on ev.id = p.STARTABLE_SEV_IDS\n" +
                "left join ad_ent e on e.id = ev.AD_ENT_ID\n" +
                "left join ad_ent_att ea on ea.AD_ENT_ID = e.id\n" +
                "left join ad_att a on a.id = ea.AD_ATT_ID\n" +
                "where p.status = 'AP' and (a.code = 'PM_PRJ_ID' or a.code = 'PM_PRJ_IDS')\n" +
                "group by e.code");

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
            String sql = "";
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

            List<Map<String, Object>> recordList = myJdbcTemplate.queryForList(sql, recordId, typeId);
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

    }

    /**
     * 判断流程表是否是单项目
     * @param processPrjFields
     * @param entCode 表code
     */
    private boolean isSinglePrj(List<Map<String, Object>> processPrjFields,String entCode){
        Map<String, Object> processPrjField = processPrjFields.stream()
                .filter(item -> entCode.equals(item.get("entCode").toString()))
                .findAny()
                .get();
        String attCodes = processPrjField.get("attCodes").toString();
        //包含s，false；不包含s，ture
        return !attCodes.contains("PM_PRJ_IDS");
    }

    /**
     * 清空项目清单和清单明细
     */
    private void emptyInventory(){
        Crud.from(PrjInventoryDetail.ENT_CODE).delete().exec();
        //暂时不需要清除项目清单表，因为doAddPrjInventory做了重复判断
//        Crud.from(PrjInventory.ENT_CODE).delete().exec();
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
        int total = myNamedParameterJdbcTemplate.queryForList(sql,queryParams).size();
        sql += " order by id desc limit :start,:pageSize";
        List<Map<String, Object>> prjIdMaps = myNamedParameterJdbcTemplate.queryForList(sql,queryParams);
        List<String> prjIdList = prjIdMaps.stream().map(m -> m.get("id").toString()).collect(Collectors.toList());
        //获取动态表头
        List<Map<String, Object>> headerMaps = myJdbcTemplate.queryForList("select v.id,v.name from gr_set_value v left join gr_set s on s.id = v.GR_SET_ID " +
                "where s.code = 'file_master_list_type' and v.status = 'AP'");
        List<String> headers = headerMaps.stream().map(m -> m.get("name").toString()).collect(Collectors.toList());

        String orgSql = "select sv.prjId,sv.prjName,sv.masterTypeId,sv.masterTypeName,sv" +
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
                ") temp group by prjId,masterTypeId) lt on lt.prjId = sv.prjId and lt.masterTypeId = sv.masterTypeId where sv.prjId in (:prjIdList) order by sv.prjId desc";
        HashMap<String, Object> params = new HashMap<>();
        params.put("prjIdList",prjIdList);
        List<Map<String, Object>> originList = myNamedParameterJdbcTemplate.queryForList(orgSql,params);

        //封装
        Map<String, List<Map<String, Object>>> data = originList.stream().collect(Collectors.groupingBy(item -> item.get("prjId").toString()));
        List<InventoryData> inventoryDataList = new ArrayList<>();//列表数据
        for (String prjId : prjIdList) {
            InventoryData inventoryData = new InventoryData();//单条列表数据
            Map<String,Object> cellData = new HashMap<>();//单条动态数据
            List<Map<String, Object>> prjOriginList = data.get(prjId);//单条项目维度的初始数据
            if (CollectionUtils.isEmpty(prjOriginList)){//可能有项目没有刷出清单模板
                continue;
            }
            for (String header : headers) {
                Map<String, Object> partCell = new HashMap<>();//单个单元格的一部分数据
                Optional<Map<String, Object>> masterTypeOp = prjOriginList.stream().filter(d -> d.get("masterTypeName").equals(header)).findAny();
                if (masterTypeOp.isPresent()){
                    partCell = masterTypeOp.get();
                }else {//表头没有对应的资料，也要将项目、清单信息赋值
                    partCell.put("prjId",prjOriginList.get(0).get("prjId"));
                    partCell.put("prjName",prjOriginList.get(0).get("prjName"));
                    Map<String, Object> foundHeaderMap =
                            headerMaps.stream().filter(headerMap -> header.equals(headerMap.get("name").toString())).findAny().get();
                    partCell.put("masterTypeId",foundHeaderMap.get("id"));
                    partCell.put("masterTypeName",foundHeaderMap.get("name"));
                }
                cellData.put(header,partCell);
            }
            inventoryData.cellData = cellData;
            inventoryData.prjId = prjId;
            inventoryData.prjName = JdbcMapUtil.getString(data.get(prjId).get(0),"prjName");
            inventoryDataList.add(inventoryData);
        }

        //返回
        ListResp listResp = new ListResp();
        listResp.inventoryDataList = inventoryDataList;
        listResp.total = total;
        listResp.headerMaps = headerMaps;
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
                "prj_inventory i \n" +
                "left join prj_inventory_detail d on i.id = d.PRJ_INVENTORY_ID\n" +
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
        sql += " order by ty.SEQ_NO limit " + start + "," + dltReq.pageSize;
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
    private static void doAddPrjInventory(PmPrj pmPrj,List<MaterialInventoryType> materialInventoryTypes,List<PrjInventory> prjInventories){
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
     * 添加项目清单明细
     * @param prjIds 项目ids，英文逗号连接
     * @param processId 流程id
     * @param processIncId 流程实例id
     */
    public static void addInventoryDtl(String prjIds,String processId,String processIncId){
        if (Strings.isNullOrEmpty(prjIds)){
            throw new BaseException("项目ids不能为空");
        }

        //先将流程实例对应的清单明细删除，避免重复添加
        PrjInventoryDetail.deleteByWhere(new Where().eq(PrjInventoryDetail.Cols.WF_PROCESS_INSTANCE_ID,processIncId));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //流程实例
        WfProcessInstance processInc = WfProcessInstance.selectById(processIncId);
        //找出流程中需要映射的字段名
        List<Map<String, Object>> attMaps = myJdbcTemplate.queryForList("select a.code attCode,ea.id entAttId from material_inventory_type t \n" +
                "left join ad_ent_att ea on ea.id = t.AD_ENT_ATT_V_ID\n" +
                "left join ad_att a on a.id = ea.AD_ATT_ID\n" +
                "where t.WF_PROCESS_ID = ?", processId);
        if (CollectionUtils.isEmpty(attMaps)){//没有对应的清单字段
            return;
        }
        List<Attribute> attributes = attMaps.stream().map(attMap -> JSONObject.parseObject(JSONObject.toJSONString(attMap), Attribute.class)).collect(Collectors.toList());

        //申请单
        List<Map<String, Object>> applyDataMaps = myJdbcTemplate.queryForList("select * from " + processInc.getEntCode() + " where id = ?", processInc.getEntityRecordId());
        if (CollectionUtils.isEmpty(applyDataMaps)){
            return;
        }
        Map<String, Object> applyData = applyDataMaps.get(0);

        String[] prjIdArr = prjIds.split(",");
        for (String prjId : prjIdArr) {//每个项目
            for (Attribute att : attributes) {
                //项目清单id
                List<Map<String, Object>> prjInventoryIdMaps = myJdbcTemplate.queryForList("select i.id from prj_inventory i \n" +
                        "left join material_inventory_type t on t.id = i.MATERIAL_INVENTORY_TYPE_ID\n" +
                        "where i.PM_PRJ_ID = ? and t.AD_ENT_ATT_V_ID = ? and WF_PROCESS_ID = ?", prjId, att.entAttId, processId);
                if (CollectionUtils.isEmpty(prjInventoryIdMaps)){
                    continue;
                }

                for (Map<String, Object> prjInventoryIdMap : prjInventoryIdMaps) {
                    String prjInventoryId = JdbcMapUtil.getString(prjInventoryIdMap, "id");

                    //从申请单取出字段值(文件ids)
                    String fileIds = JdbcMapUtil.getString(applyData, att.attCode);
                    if (Strings.isNullOrEmpty(fileIds)){//字段为空，跳过
                        continue;
                    }
                    String[] fileIdArr = fileIds.split(",");
                    for (String fileId : fileIdArr) {
                        //插入清单明细
                        PrjInventoryDetail prjInventoryDetail = PrjInventoryDetail.newData();
                        prjInventoryDetail.setPrjInventoryId(prjInventoryId);
                        prjInventoryDetail.setFlFileId(fileId);
                        prjInventoryDetail.setWfProcessInstanceId(processIncId);
                        prjInventoryDetail.insertById();
                    }
                }
            }
        }


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
        private List<Map<String, Object>> headerMaps;
    }

    /**
     * 列表页单条数据
     */
    @Data
    private static class InventoryData{
        private String prjId;
        private String prjName;
        //动态列
        private Map<String,Object> cellData;
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

    @Data
    private static class Attribute{
        //属性代码
        private String attCode;
        //实体属性id
        private String entAttId;
    }


    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<>();
        list.add(null);
        list.add(null);
        System.out.println(list);
    }



}
