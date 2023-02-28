package com.cisdi.ext.importQYY;

import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.interaction.EntityRecord;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author dlt
 * @date 2023/2/27 周一
 */
public class PrjReqImport {

    private List<Map<String, Object>> prjMaps;
    private List<Map<String, Object>> customerMaps;
    private List<Map<String, Object>> manageModeMaps;
    private List<Map<String, Object>> locationMaps;
    private List<Map<String, Object>> prjTypeMaps;
    private List<Map<String, Object>> scaleTypeMaps;
    private List<Map<String, Object>> investSourceMaps;
    private List<Map<String, Object>> investPrjMaps;
    private List<Map<String, Object>> investDtlMaps;
    private List<Map<String, Object>> prjReqMaps;

    public void importPrj(){
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //提前查询项目相关数据
        prjMaps = jdbcTemplate.queryForList("select id,name from pm_prj where status = 'AP'");
        customerMaps = jdbcTemplate.queryForList("select id,name from pm_party where IS_CUSTOMER = 1 and status = 'AP'");
        manageModeMaps = jdbcTemplate.queryForList("select gv.id,gv.name from gr_set_value gv left join gr_set gs on gv.GR_SET_ID = gs.id where gs.code = 'management_unit' and gv.status = 'AP'");
        locationMaps = jdbcTemplate.queryForList("select gv.id,gv.name from gr_set_value gv left join gr_set gs on gv.GR_SET_ID = gs.id where gs.code = 'base_location' and gv.status = 'AP'");
        prjTypeMaps = jdbcTemplate.queryForList("select gv.id,gv.name from gr_set_value gv left join gr_set gs on gv.GR_SET_ID = gs.id where gs.code = 'project_type' and gv.status = 'AP'");
        scaleTypeMaps = jdbcTemplate.queryForList("select gv.id,gv.name from gr_set_value gv left join gr_set gs on gv.GR_SET_ID = gs.id where gs.code = 'con_scale_type' and gv.status = 'AP'");
        investSourceMaps = jdbcTemplate.queryForList("select gv.id,gv.name from gr_set_value gv left join gr_set gs on gv.GR_SET_ID = gs.id where gs.code = 'investment_source' and gv.status = 'AP'");
        //投资测算
        investPrjMaps = jdbcTemplate.queryForList("select id,PM_PRJ_ID prjId from pm_invest_est");
        //立项申请
        prjReqMaps = jdbcTemplate.queryForList("select id,prj_name from pm_prj_req");

        for (EntityRecord entityRecord : entityRecordList) {
            List<String> errorList = new ArrayList<>();
            importSinglePrj(entityRecord.valueMap,errorList);
            if (CollectionUtils.isEmpty(errorList)){
                //设置更新成功
                Crud.from("PRJ_REQ_IMPORT").where().eq("ID",String.valueOf(entityRecord.valueMap.get("ID"))).update()
                        .set("IS_SUCCESS",1).exec();
            }else {
                String errorInfo = String.join(",", errorList);
                Crud.from("prj_req_import").where().eq("ID",String.valueOf(entityRecord.valueMap.get("ID"))).update()
                        .set("ERR_INFO",errorInfo).exec();
            }
        }
    }

    private void importSinglePrj(Map<String,Object> valueMap,List<String> errorList){
        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String prjId = this.getPrjId(valueMap);
        if (prjId == null){
            errorList.add("没有找到项目：" + valueMap.get("PRJ_NAME"));
            return;
        }

        //todo 更新立项表

        //业主单位
        String customerUnitId = this.getCustomerUnitId(valueMap);
        if (customerUnitId == null){
            errorList.add("没有找到业主单位：" + valueMap.get("CUSTOMER_UNIT_TXT"));
        }
        //管理模式
        String modeId = this.getItemId(valueMap, "MANAGE_MODE", manageModeMaps);
        if (modeId == null){
            errorList.add("没有找到管理模式：" + valueMap.get("MANAGE_MODE"));
        }
        //建设地点
        String locationId = this.getItemId(valueMap, "BASE_LACATION", locationMaps);
        if (locationId == null){
            errorList.add("没有找到建设地点：" + valueMap.get("BASE_LACATION"));
        }
        //项目类型
        String prjTypeId = this.getItemId(valueMap, "PROJECT_TYPE_NAME", prjTypeMaps);
        if (prjTypeId == null){
            errorList.add("没有找到项目类型：" + valueMap.get("PROJECT_TYPE_NAME"));
        }
        //建设规模类型
        String scaleTypeId = this.getItemId(valueMap, "CON_SCALE_TYPE_NAME", scaleTypeMaps);
        if (scaleTypeId == null){
            errorList.add("没有找到建设规模类型：" + valueMap.get("CON_SCALE_TYPE_NAME"));
        }
        //投资来源
        String investSourceId = this.getItemId(valueMap, "INVESTMENT_SOURCE_NAME", investSourceMaps);
        if (investSourceId == null){
            errorList.add("没有找到投资来源：" + valueMap.get("INVESTMENT_SOURCE_NAME"));
        }


        //更新 pm_prj
        Crud.from("pm_prj").where().eq("ID",prjId).update()
                .set("CUSTOMER_UNIT",customerUnitId)
                .set("PRJ_MANAGE_MODE_ID",modeId)
                .set("BASE_LOCATION_ID",locationId)
                .set("PROJECT_TYPE_ID",prjTypeId)
                .set("PRJ_SITUATION",valueMap.get("PRJ_SITUATION"))
                .set("CON_SCALE_TYPE_ID",scaleTypeId)
                .set("FLOOR_AREA",valueMap.get("FLOOR_AREA"))
                .set("BUILDING_AREA",valueMap.get("BUILDING_AREA"))
                .set("CON_SCALE_QTY",valueMap.get("ROAD_LENGTH"))
                .set("CON_SCALE_QTY2",valueMap.get("ROAD_WIDTH"))
                .set("QTY_TWO",valueMap.get("QTY_TWO"))
                .set("INVESTMENT_SOURCE_ID",investSourceId)
                .set("PROJECT_PROPOSAL_ACTUAL_DATE",valueMap.get("PROJECT_PROPOSAL_DATE"))
                .set("AUTHOR",valueMap.get("PROJECT_PROPOSAL_AUTHOR"))
                .set("PRJ_CODE",valueMap.get("PRJ_CODE"))
                .set("PRJ_REPLY_NO",valueMap.get("REPLY_NO"))
                .set("PRJ_REPLY_DATE",valueMap.get("REPLY_DATE"))
                .exec();



        //插入项目投资测算 PM_INVEST_EST
        String investId = this.getInvestId(prjId, investPrjMaps);
        if (investId == null){
            investId = Crud.from("PM_INVEST_EST").insertData();
        }
        Crud.from("PM_INVEST_EST").where().eq("ID",investId).update()
                .set("IS_TEMPLATE",0)
                .set("PM_PRJ_ID",prjId)
                .set("INVEST_EST_TYPE_ID","0099799190825099302")//立项匡算
                .set("PRJ_TOTAL_INVEST",valueMap.get("ESTIMATED_TOTAL_INVEST"))
                .set("REPLY_NO",valueMap.get("REPLY_NO"))
                .set("REPLY_ACTUAL_DATE",valueMap.get("REPLY_DATE"))
                .exec();

        //插入 项目投资测算明细 PM_INVEST_EST_DTL
        //先清空对应的明细
        Crud.from("PM_INVEST_EST_DTL").where().eq("PM_INVEST_EST_ID",investId).delete().exec();
        //重新构建投资测算树
        this.buildPrjInvestDlt(investId);
        //投资测算明细
        investDtlMaps = jdbcTemplate.queryForList("select id,PM_INVEST_EST_ID investId,PM_EXP_TYPE_ID feeTypeId from pm_invest_est_dtl");
        //匡算总投资
        String totalInvestDtlId = this.getInvestDltId(investId, "0099799190825099546", investDtlMaps);
        Crud.from("PM_INVEST_EST_DTL").where().eq("ID",totalInvestDtlId).update()
                .set("PM_EXP_TYPE_ID","0099799190825099546")
                .set("AMT",valueMap.get("ESTIMATED_TOTAL_INVEST"))
                .set("PM_INVEST_EST_ID",investId)
                .exec();
        //建安工程费
        String constructInvestDltId = this.getInvestDltId(investId, "0099799190825099548", investDtlMaps);
        Crud.from("PM_INVEST_EST_DTL").where().eq("ID",constructInvestDltId).update()
                .set("PM_EXP_TYPE_ID","0099799190825099548")
                .set("AMT",valueMap.get("CONSTRUCT_PRJ_AMT"))
                .set("PM_INVEST_EST_ID",investId)
                .exec();
        //设备采购费(万)(EQUIP_BUY_AMT)
        String equipInvestDltId = this.getInvestDltId(investId, "0099799190825099549", investDtlMaps);
        Crud.from("PM_INVEST_EST_DTL").where().eq("ID",equipInvestDltId).update()
                .set("PM_EXP_TYPE_ID","0099799190825099549")
                .set("AMT",valueMap.get("EQUIP_BUY_AMT"))
                .set("PM_INVEST_EST_ID",investId)
                .exec();
        //科研设备费(万)(EQUIPMENT_COST),双精度浮点
        String equipmentInvestDltId = this.getInvestDltId(investId, "1628674132377997312", investDtlMaps);
        Crud.from("PM_INVEST_EST_DTL").where().eq("ID",equipmentInvestDltId).update()
                .set("PM_EXP_TYPE_ID","1628674132377997312")
                .set("AMT",valueMap.get("EQUIPMENT_COST"))
                .set("PM_INVEST_EST_ID",investId)
                .exec();
        //工程其他费用(万)(PROJECT_OTHER_AMT),双精度浮点
        String prjOtherDltId = this.getInvestDltId(investId, "0099799190825099550", investDtlMaps);
        Crud.from("PM_INVEST_EST_DTL").where().eq("ID",prjOtherDltId).update()
                .set("PM_EXP_TYPE_ID","0099799190825099550")
                .set("AMT",valueMap.get("PROJECT_OTHER_AMT"))
                .set("PM_INVEST_EST_ID",investId)
                .exec();
        //土地征拆费用(万)(LAND_BUY_AMT),浮点数（32位，小数2位）
        String landBuyInvestDtlId = this.getInvestDltId(investId, "0099799190825099551", investDtlMaps);
        Crud.from("PM_INVEST_EST_DTL").where().eq("ID",landBuyInvestDtlId).update()
                .set("PM_EXP_TYPE_ID","0099799190825099551")
                .set("AMT",valueMap.get("LAND_BUY_AMT"))
                .set("PM_INVEST_EST_ID",investId)
                .exec();
        //预备费(万)(PREPARE_AMT),双精度浮点
        String prepareInvestDltId = this.getInvestDltId(investId, "0099799190825099552", investDtlMaps);
        Crud.from("PM_INVEST_EST_DTL").where().eq("ID",prepareInvestDltId).update()
                .set("PM_EXP_TYPE_ID","0099799190825099552")
                .set("AMT",valueMap.get("PREPARE_AMT"))
                .set("PM_INVEST_EST_ID",investId)
                .exec();

    }

    private String getPrjId(Map<String,Object> valueMap){
        Optional<String> prjIdOp = prjMaps.stream()
                .filter(prj -> String.valueOf(prj.get("name")).equals(String.valueOf(valueMap.get("PRJ_NAME"))))
                .map(prj -> String.valueOf(prj.get("id")))
                .findAny();
        if (prjIdOp.isPresent()){
            return prjIdOp.get();
        }
        return null;
    }

    private String getCustomerUnitId(Map<String,Object> valueMap){
        Optional<String> customerUnitOp = customerMaps.stream()
                .filter(customer -> String.valueOf(customer.get("name")).equals(String.valueOf(valueMap.get("CUSTOMER_UNIT_TXT"))))
                .map(customer -> String.valueOf(customer.get("id")))
                .findAny();
        if (customerUnitOp.isPresent()){
            return customerUnitOp.get();
        }
        return null;
    }

    //获取字段对应的id
    private String getItemId(Map<String,Object> valueMap,String fieldName,List<Map<String, Object>> itemMaps){
        Optional<String> itemIdOp = itemMaps.stream()
                .filter(item -> String.valueOf(item.get("name")).equals(String.valueOf(valueMap.get(fieldName))))
                .map(customer -> String.valueOf(customer.get("id")))
                .findAny();
        if (itemIdOp.isPresent()){
            return itemIdOp.get();
        }
        return null;
    }

    //投资测算id
    private String getInvestId(String prjId,List<Map<String, Object>> itemMaps){
        Optional<String> itemIdOp = itemMaps.stream()
                .filter(item -> String.valueOf(item.get("prjId")).equals(prjId))
                .map(item -> String.valueOf(item.get("id")))
                .findAny();
        if (itemIdOp.isPresent()){
            return itemIdOp.get();
        }
        return null;
    }

    //投资测算明细id
    private String getInvestDltId(String investId,String feeTypeId,List<Map<String, Object>> itemMaps){
        Optional<String> itemIdOp = itemMaps.stream()
                .filter(item -> String.valueOf(item.get("investId")).equals(investId))
                .filter(item -> String.valueOf(item.get("feeTypeId")).equals(feeTypeId))
                .map(item -> String.valueOf(item.get("id")))
                .findAny();
        if (itemIdOp.isPresent()){
            return itemIdOp.get();
        }
        return null;
    }

    //根据模板建立投资测算明细树
    private void buildPrjInvestDlt(String investId){
        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> feeModels = jdbcTemplate.queryForList("select id,code,name,SEQ_NO,ifnull(PM_EXP_TYPE_PID,'0') PM_EXP_TYPE_PID from PM_EXP_TYPE");
        feeModels.stream().filter(model -> String.valueOf(model.get("PM_EXP_TYPE_PID")).equals("0")).peek(model -> {
            String dltId = Crud.from("pm_invest_est_dtl").insertData();
            Crud.from("pm_invest_est_dtl").where().eq("id",dltId).update()
                    .set("PM_INVEST_EST_ID",investId)
                    .set("PM_EXP_TYPE_ID",model.get("id"))
                    .set("PM_INVEST_EST_DTL_PID",null)
                    .set("SEQ_NO",model.get("SEQ_NO"))
                    .exec();
            this.buildInvestChild(feeModels,model,investId);
        }).collect(Collectors.toList());
    }

    //插入子测算明细
    private void buildInvestChild(List<Map<String, Object>> feeModels,Map<String, Object> parentModel,String investId){
        feeModels.stream()
                .filter(model -> String.valueOf(model.get("PM_EXP_TYPE_PID")).equals(parentModel.get("id").toString()))
                .peek(model -> {
                    String dltId = Crud.from("pm_invest_est_dtl").insertData();
                    Crud.from("pm_invest_est_dtl").where().eq("id",dltId).update()
                            .set("PM_INVEST_EST_ID",investId)
                            .set("PM_EXP_TYPE_ID",model.get("id"))
                            .set("PM_INVEST_EST_DTL_PID",dltId)
                            .set("SEQ_NO",model.get("SEQ_NO"))
                            .exec();
                    this.buildInvestChild(feeModels,model,investId);
                }).collect(Collectors.toList());
    }
}
