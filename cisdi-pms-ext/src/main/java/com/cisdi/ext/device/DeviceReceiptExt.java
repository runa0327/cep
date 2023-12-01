package com.cisdi.ext.device;

import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title DeviceReceiptExt
 * @package com.cisdi.ext.device
 * @description 设备验收单
 * @date 2023/11/24
 */
public class DeviceReceiptExt {

    /**
     * 验收单列表查询
     */
    public void deviceReceiptList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam requestParam = JsonUtil.fromJson(json, RequestParam.class);
        int pageSize = requestParam.pageSize;
        int pageIndex = requestParam.pageIndex;
        StringBuilder sb = new StringBuilder();
        sb.append("select dr.id,pm.`NAME` as prjName,SUPPLIER,pp.`NAME` as unit,USE_UNIT,plp.`NAME` as pointName,SIGN_DATE,gsv.`NAME` as izSign,ACCEPTANCE_DATE,ACCEPTANCE_DOCUMENTS,DIRECTION from DEVICE_RECEIPT dr \n" +
                "left join pm_prj pm on dr.PM_PRJ_ID = pm.id \n" +
                "left join pm_party pp on pp.id = dr.CUSTOMER_UNIT \n" +
                "left join PM_LANDING_POINT plp on plp.id = dr.PM_LANDING_POINT_ID \n" +
                "left join gr_set_value gsv on gsv.id = dr.IZ_SIGN_ID where 1=1 ");
        if (StringUtils.hasText(requestParam.prjName)) {
            sb.append(" and pm.`NAME` like '%").append(requestParam.prjName).append("%'");
        }
        if (StringUtils.hasText(requestParam.supplier)) {
            sb.append(" and SUPPLIER like '%").append(requestParam.supplier).append("%'");
        }
        if (StringUtils.hasText(requestParam.point)) {
            sb.append(" and PM_LANDING_POINT_ID  ='").append(requestParam.point).append("'");
        }
        if (StringUtils.hasText(requestParam.izSgin)) {
            sb.append(" and IZ_SIGN_ID  ='").append(requestParam.izSgin).append("'");
        }
        if (StringUtils.hasText(requestParam.signDateB) && StringUtils.hasText(requestParam.signDateE)) {
            sb.append(" and SIGN_DATE between '").append(requestParam.signDateB).append("' and '").append(requestParam.signDateE).append("'");
        }
        sb.append(" order by dr.CRT_DT desc ");
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<ObjInfo> objInfos = list.stream().map(this::convertData).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(objInfos)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide resData = new OutSide();
            resData.total = totalList.size();
            resData.objInfos = objInfos;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 验收单详情
     */
    public void deviceReceiptView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        String sql = "select dr.id,pm.`NAME` as prjName,SUPPLIER,pp.`NAME` as unit,USE_UNIT,plp.`NAME` as pointName,SIGN_DATE,gsv.`NAME` as izSign,ACCEPTANCE_DATE,ACCEPTANCE_DOCUMENTS,DIRECTION from DEVICE_RECEIPT dr \n" +
                "left join pm_prj pm on dr.PM_PRJ_ID = pm.id \n" +
                "left join pm_party pp on pp.id = dr.CUSTOMER_UNIT \n" +
                "left join PM_LANDING_POINT plp on plp.id = dr.PM_LANDING_POINT_ID \n" +
                "left join gr_set_value gsv on gsv.id = dr.IZ_SIGN_ID " +
                " where dr.id=?";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, map.get("id"));
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            Map<String, Object> dataMap = list.get(0);
            ObjInfo info = convertData(dataMap);
            String fileIds = JdbcMapUtil.getString(dataMap, "ACCEPTANCE_DOCUMENTS");
            if (StringUtils.hasText(fileIds)) {
                List<String> ids = Arrays.asList(fileIds.split(","));
                Map<String, Object> queryFileParams = new HashMap<>();// 创建入参map
                queryFileParams.put("ids", ids);
                List<Map<String, Object>> fileList = myNamedParameterJdbcTemplate.queryForList("select ff.ID as ID, DSP_NAME,SIZE_KB,UPLOAD_DTTM,au.`NAME` as USER_NAME,FILE_INLINE_URL,FILE_ATTACHMENT_URL from fl_file ff left join ad_user au on ff.CRT_USER_ID = au.id  where ff.id in (:ids)", queryFileParams);
                AtomicInteger index = new AtomicInteger(0);
                info.fileObjList = fileList.stream().map(p -> {
                    FileObj obj = new FileObj();
                    obj.num = index.getAndIncrement() + 1;
                    obj.fileName = JdbcMapUtil.getString(p, "DSP_NAME");
                    obj.fileSize = JdbcMapUtil.getString(p, "SIZE_KB");
                    obj.uploadUser = JdbcMapUtil.getString(p, "USER_NAME");
                    obj.uploadDate = StringUtil.withOutT(JdbcMapUtil.getString(p, "UPLOAD_DTTM"));
                    obj.id = JdbcMapUtil.getString(p, "ID");
                    obj.viewUrl = JdbcMapUtil.getString(p, "FILE_INLINE_URL");
                    obj.downloadUrl = JdbcMapUtil.getString(p, "FILE_ATTACHMENT_URL");
                    return obj;
                }).collect(Collectors.toList());
            }
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(info), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 设备列表
     */
    public void deviceList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String json = JsonUtil.toJson(map);
        reParam param = JsonUtil.fromJson(json, reParam.class);
        StringBuilder sb = new StringBuilder();
        sb.append("select dl.*,gsv.`NAME` as signStatus,gv.`NAME` as checkStatus,round(ifnull(QTY_ONE,0),2) as qty,round(ifnull(TONG_DJ,0),2) as dj,round(ifnull(TONG_ZJ,0),2) as zj from DEVICE_LIST dl left join gr_set_value gsv on gsv.id = dl.SIGN_STATUS_ID left join gr_set_value gv on gv.id = dl.CHECK_STATUS_ID where dl.DEVICE_RECEIPT_ID='");
        sb.append(param.receiptId).append("' ");
        if (StringUtils.hasText(param.goodsName)) {
            sb.append(" and GOODS_NAME like '%").append(param.goodsName).append("%'");
        }
        if (StringUtils.hasText(param.brand)) {
            sb.append(" and BRAND like '%").append(param.brand).append("%'");
        }
        if (StringUtils.hasText(param.modelNumber)) {
            sb.append(" and MODEL_NUMBER like '%").append(param.modelNumber).append("%'");
        }
        if (StringUtils.hasText(param.signBegin) && StringUtils.hasText(param.signEnd)) {
            sb.append(" and SIGNING_TIME between '").append(param.signBegin).append("' and '").append(param.signEnd).append("'");
        }
        if (StringUtils.hasText(param.signStatus)) {
            sb.append(" and SIGN_STATUS_ID = '").append(param.signStatus).append("'");
        }
        List<Map<String, Object>> list1 = myJdbcTemplate.queryForList(sb.toString());
        List<GoodsInfo> goodsInfoList = list1.stream().map(this::convertGoodsData).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(goodsInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide resData = new OutSide();
            resData.goodsInfoList = goodsInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 设备详情
     */
    public void deviceView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select dl.*,gsv.`NAME` as signStatus,gv.`NAME` as checkStatus,au.`name` as cuser,round(ifnull(QTY_ONE,0),2) as qty,round(ifnull(TONG_DJ,0),2) as dj,round(ifnull(TONG_ZJ,0),2) as zj from DEVICE_LIST dl " +
                "left join gr_set_value gsv on gsv.id = dl.SIGN_STATUS_ID " +
                "left join gr_set_value gv on gv.id = dl.CHECK_STATUS_ID " +
                "left join ad_user au on au.id = dl.AD_USER_ID "+
                "where dl.id=?", map.get("id"));
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            Map<String, Object> dataMap = list.get(0);
            GoodsInfo info = convertGoodsData(dataMap);
            String fileIds = JdbcMapUtil.getString(dataMap, "ACCEPTANCE_DOCUMENTS");
            if (StringUtils.hasText(fileIds)) {
                List<String> ids = Arrays.asList(fileIds.split(","));
                Map<String, Object> queryFileParams = new HashMap<>();// 创建入参map
                queryFileParams.put("ids", ids);
                List<Map<String, Object>> fileList = myNamedParameterJdbcTemplate.queryForList("select ff.ID as ID, DSP_NAME,SIZE_KB,UPLOAD_DTTM,au.`NAME` as USER_NAME,FILE_INLINE_URL,FILE_ATTACHMENT_URL from fl_file ff left join ad_user au on ff.CRT_USER_ID = au.id  where ff.id in (:ids)", queryFileParams);
                AtomicInteger index = new AtomicInteger(0);
                info.fileObjList = fileList.stream().map(p -> {
                    FileObj obj = new FileObj();
                    obj.num = index.getAndIncrement() + 1;
                    obj.fileName = JdbcMapUtil.getString(p, "DSP_NAME");
                    obj.fileSize = JdbcMapUtil.getString(p, "SIZE_KB");
                    obj.uploadUser = JdbcMapUtil.getString(p, "USER_NAME");
                    obj.uploadDate = StringUtil.withOutT(JdbcMapUtil.getString(p, "UPLOAD_DTTM"));
                    obj.id = JdbcMapUtil.getString(p, "ID");
                    obj.viewUrl = JdbcMapUtil.getString(p, "FILE_INLINE_URL");
                    obj.downloadUrl = JdbcMapUtil.getString(p, "FILE_ATTACHMENT_URL");
                    return obj;
                }).collect(Collectors.toList());
            }
            //签收列表
            List<Map<String, Object>> list1 = myJdbcTemplate.queryForList("select dsl.*,round(ifnull(dsl.QTY_ONE*dl.TONG_DJ,0),2) as dhAmt,au.name as user,round(ifnull(dsl.QTY_ONE,0),2) as qty from DEVICE_SIGN_LIST dsl \n" +
                    "left join DEVICE_LIST dl on dsl.DEVICE_LIST_ID = dl.id \n" +
                    "left join ad_user au on au.id = dsl.AD_USER_ID where DEVICE_LIST_ID = ?", map.get("id"));
            info.signInfoList = list1.stream().map(q -> {
                SignInfo signInfo = new SignInfo();
                signInfo.signId = JdbcMapUtil.getString(q, "ID");
                signInfo.amt = JdbcMapUtil.getString(q, "qty");
                signInfo.money = JdbcMapUtil.getString(q, "dhAmt");
                signInfo.remark = JdbcMapUtil.getString(q, "REMARK");
                signInfo.user = JdbcMapUtil.getString(q, "user");
                signInfo.arriveTime = StringUtil.withOutT(JdbcMapUtil.getString(q, "ARRIVAL_TIME"));

                String fileIdsa = JdbcMapUtil.getString(q, "ACCEPTANCE_DOCUMENTS");
                if (StringUtils.hasText(fileIdsa)) {
                    List<String> ids = Arrays.asList(fileIdsa.split(","));
                    Map<String, Object> queryFileParams = new HashMap<>();// 创建入参map
                    queryFileParams.put("ids", ids);
                    List<Map<String, Object>> fileList = myNamedParameterJdbcTemplate.queryForList("select ff.ID as ID, DSP_NAME,SIZE_KB,UPLOAD_DTTM,au.`NAME` as USER_NAME,FILE_INLINE_URL,FILE_ATTACHMENT_URL from fl_file ff left join ad_user au on ff.CRT_USER_ID = au.id  where ff.id in (:ids)", queryFileParams);
                    AtomicInteger index = new AtomicInteger(0);
                    signInfo.fileObjList = fileList.stream().map(p -> {
                        FileObj obj = new FileObj();
                        obj.num = index.getAndIncrement() + 1;
                        obj.fileName = JdbcMapUtil.getString(p, "DSP_NAME");
                        obj.fileSize = JdbcMapUtil.getString(p, "SIZE_KB");
                        obj.uploadUser = JdbcMapUtil.getString(p, "USER_NAME");
                        obj.uploadDate = StringUtil.withOutT(JdbcMapUtil.getString(p, "UPLOAD_DTTM"));
                        obj.id = JdbcMapUtil.getString(p, "ID");
                        obj.viewUrl = JdbcMapUtil.getString(p, "FILE_INLINE_URL");
                        obj.downloadUrl = JdbcMapUtil.getString(p, "FILE_ATTACHMENT_URL");
                        return obj;
                    }).collect(Collectors.toList());
                }
                return signInfo;
            }).collect(Collectors.toList());

            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(info), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }

    }

    /**
     * 签收单-签收/编辑
     */
    public void signModify() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String json = JsonUtil.toJson(map);
        SignInput input = JsonUtil.fromJson(json, SignInput.class);
        String id = input.id;
        if (!StringUtils.hasText(id)) {
            id = Crud.from("DEVICE_SIGN_LIST").insertData();
        }
        String userId = ExtJarHelper.loginInfo.get().userId;
        StringBuilder sb = new StringBuilder();
        sb.append("update DEVICE_SIGN_LIST set LAST_MODI_DT =NOW() , DEVICE_LIST_ID= '").append(input.dataId).append("'");
        sb.append(" , AD_USER_ID='").append(userId).append("'");
        if (StringUtils.hasText(input.arriveAmt)) {
            sb.append(" ,QTY_ONE='").append(input.arriveAmt).append("'");
        } else {
            sb.append(" ,QTY_ONE=null");
        }
        if (StringUtils.hasText(input.arriveTime)) {
            sb.append(" ,ARRIVAL_TIME='").append(input.arriveTime).append("'");
        } else {
            sb.append(" ,ARRIVAL_TIME=null");
        }
        if (StringUtils.hasText(input.remark)) {
            sb.append(" ,REMARK='").append(input.remark).append("'");
        } else {
            sb.append(" ,REMARK=null");
        }
        if (StringUtils.hasText(input.fileIds)) {
            sb.append(" ,ACCEPTANCE_DOCUMENTS='").append(input.fileIds).append("'");
        } else {
            sb.append(" ,ACCEPTANCE_DOCUMENTS=null");
        }
        sb.append(" where id='").append(id).append("'");
        myJdbcTemplate.update(sb.toString());
        myJdbcTemplate.update("update DEVICE_LIST set CHECK_STATUS_ID='1729023835722448896' where id=?", input.dataId);
        checkStatus(input.dataId);
    }

    /**
     * 签收单删除
     */
    public void signDel() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("delete from DEVICE_SIGN_LIST where id=?", map.get("id"));
    }

    /**
     * 设备验收信息验收/修改/删除
     */
    public void deviceSignModify() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String json = JsonUtil.toJson(map);
        DataInput input = JsonUtil.fromJson(json, DataInput.class);
        StringBuilder sb = new StringBuilder();
        sb.append("update DEVICE_LIST set LAST_MODI_DT =NOW() ");
        String userId = ExtJarHelper.loginInfo.get().userId;
        sb.append(" , AD_USER_ID='").append(userId).append("'");
        if (StringUtils.hasText(input.arriveTime)) {
            sb.append(" ,ACCEPTANCE_DATE='").append(input.arriveTime).append("'");
        } else {
            sb.append(" ,ACCEPTANCE_DATE=null");
        }
        if (StringUtils.hasText(input.remark)) {
            sb.append(" ,REMARK='").append(input.remark).append("'");
        } else {
            sb.append(" ,REMARK=null");
        }
        if (StringUtils.hasText(input.fileIds)) {
            sb.append(" ,ACCEPTANCE_DOCUMENTS='").append(input.fileIds).append("'");
        } else {
            sb.append(" ,ACCEPTANCE_DOCUMENTS=null");
        }
        sb.append(" where id='").append(input.id).append("'");
        myJdbcTemplate.update(sb.toString());
    }


    /**
     * 终验前的检查
     */
    public void chargeFinalAcceptanceCheck() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select if(\n" +
                "(select count(*) from DEVICE_LIST where SIGN_STATUS_ID='1727858208051806208' and DEVICE_RECEIPT_ID=? )\n" +
                "=\n" +
                "(select count(*) from DEVICE_LIST where DEVICE_RECEIPT_ID=?),1,0) as reslut", map.get("id"), map.get("id"));
        Map<String, Object> res = new HashMap<>();
        String status = "0";
        if (CollectionUtils.isEmpty(list)) {
            status = JdbcMapUtil.getString(list.get(0), "reslut");
        }
        res.put("status", status);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(res), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 设备终验
     */
    public void deviceFinalAcceptanceCheck() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String json = JsonUtil.toJson(map);
        DataInput input = JsonUtil.fromJson(json, DataInput.class);
        StringBuilder sb = new StringBuilder();
        sb.append("update DEVICE_RECEIPT set LAST_MODI_DT =NOW(),IZ_SIGN_ID='1727858208051806208'  ");
        if (StringUtils.hasText(input.arriveTime)) {
            sb.append(" ,ACCEPTANCE_DATE='").append(input.arriveTime).append("'");
        } else {
            sb.append(" ,ACCEPTANCE_DATE=null");
        }
        if (StringUtils.hasText(input.remark)) {
            sb.append(" ,REMARK='").append(input.remark).append("'");
        } else {
            sb.append(" ,REMARK=null");
        }
        if (StringUtils.hasText(input.fileIds)) {
            sb.append(" ,ACCEPTANCE_DOCUMENTS='").append(input.fileIds).append("'");
        } else {
            sb.append(" ,ACCEPTANCE_DOCUMENTS=null");
        }
        sb.append(" where id='").append(input.id).append("'");
        myJdbcTemplate.update(sb.toString());
    }

    /**
     * 设备筛选
     */
    public void equipmentSearch() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        String json = JsonUtil.toJson(map);
        SearchParam param = JsonUtil.fromJson(json, SearchParam.class);
        StringBuilder sb = new StringBuilder();
        sb.append("select pm.`NAME` as prjName,dr.USE_UNIT,dl.*,gsv.`NAME` as sign_status,gs.`NAME` as check_status from DEVICE_SIGN_LIST dsl \n" +
                "left join DEVICE_LIST dl on dsl.DEVICE_LIST_ID = dl.id \n" +
                "left join DEVICE_RECEIPT dr on dr.id = dl.DEVICE_RECEIPT_ID \n" +
                "left join pm_prj pm on pm.id = dr.PM_PRJ_ID \n" +
                "left join gr_set_value gsv on gsv.id = dl.SIGN_STATUS_ID \n" +
                "left join gr_set_value gs on gs.id = dl.CHECK_STATUS_ID where dsl.status='ap' ");
        Map<String, Object> queryFileParams = new HashMap<>();// 创建入参map
        if (StringUtils.hasText(param.prjIds)) {
            List<String> ids = Arrays.asList(param.prjIds.split(","));
            queryFileParams.put("ids", ids);
            sb.append(" and dr.PM_PRJ_ID  in (:ids)");
        }
        if (StringUtils.hasText(param.goodsName)) {
            sb.append(" and dl.GOODS_NAME like '%").append(param.goodsName).append("%'");
        }
        if (StringUtils.hasText(param.begin) && StringUtils.hasText(param.end)) {
            sb.append(" and dsl.ARRIVAL_TIME between '").append(param.begin).append("' and '").append(param.end).append("'");
        }
        if (StringUtils.hasText(param.cgWay)) {
            sb.append(" and dl.PROCUREMENT_APPROACH like '%").append(param.cgWay).append("%'");
        }
        List<Map<String, Object>> list = myNamedParameterJdbcTemplate.queryForList(sb.toString(), queryFileParams);
        Map<String, Map<String, List<Map<String, Object>>>> collect = list.stream().collect(Collectors.groupingBy(p -> JdbcMapUtil.getString(p, "prjName"), Collectors.groupingBy(m -> JdbcMapUtil.getString(m, "USE_UNIT"))));
        List<SearchDataInfo> dataList = new ArrayList<>();
        for (String key : collect.keySet()) {
            SearchDataInfo info = new SearchDataInfo();
            info.prjName = key;
            Map<String, List<Map<String, Object>>> objMap = collect.get(key);
            List<Obj> objList = new ArrayList<>();
            for (String ikey : objMap.keySet()) {
                Obj o = new Obj();
                o.useUnit = ikey;
                List<Map<String, Object>> dataMap = objMap.get(ikey);
                o.goodsInfoList = dataMap.stream().map(this::convertGoodsData).collect(Collectors.toList());
                objList.add(o);
            }
            info.objs = objList;
            dataList.add(info);
        }
        if (CollectionUtils.isEmpty(dataList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide resData = new OutSide();
            resData.dataList = dataList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    private ObjInfo convertData(Map<String, Object> dataMap) {
        ObjInfo info = new ObjInfo();
        info.id = JdbcMapUtil.getString(dataMap, "id");
        info.prjName = JdbcMapUtil.getString(dataMap, "prjName");
        info.supplier = JdbcMapUtil.getString(dataMap, "SUPPLIER");
        info.unit = JdbcMapUtil.getString(dataMap, "unit");
        info.useUnit = JdbcMapUtil.getString(dataMap, "USE_UNIT");
        info.pointName = JdbcMapUtil.getString(dataMap, "pointName");
        info.signDate = JdbcMapUtil.getString(dataMap, "SIGN_DATE");
        info.izSign = JdbcMapUtil.getString(dataMap, "izSign");
        info.acceptanceDate = JdbcMapUtil.getString(dataMap, "ACCEPTANCE_DATE");
        info.direction = JdbcMapUtil.getString(dataMap, "DIRECTION");
        return info;
    }

    private GoodsInfo convertGoodsData(Map<String, Object> dataMap) {
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.id = JdbcMapUtil.getString(dataMap, "ID");
        goodsInfo.dataId = JdbcMapUtil.getString(dataMap, "DEVICE_RECEIPT_ID");//验收单ID
        goodsInfo.goodsName = JdbcMapUtil.getString(dataMap, "GOODS_NAME");//货物名称
        goodsInfo.brand = JdbcMapUtil.getString(dataMap, "BRAND");//品牌
        goodsInfo.modelNumber = JdbcMapUtil.getString(dataMap, "MODEL_NUMBER");//规格
        goodsInfo.qtyOne = JdbcMapUtil.getString(dataMap, "qty");//数量
        goodsInfo.tongDj = JdbcMapUtil.getString(dataMap, "dj");//单价
        goodsInfo.tongZj = JdbcMapUtil.getString(dataMap, "zj");//总价
        goodsInfo.deliveryPeriod = JdbcMapUtil.getString(dataMap, "DELIVERY_PERIOD");//交付期限
        goodsInfo.shelfLife = JdbcMapUtil.getString(dataMap, "SHELF_LIFE");//保质期
        goodsInfo.procurementApproach = JdbcMapUtil.getString(dataMap, "PROCUREMENT_APPROACH");//采购途径
        goodsInfo.signingTime =StringUtil.withOutT(JdbcMapUtil.getString(dataMap, "SIGNING_TIME"));//签收日期
        goodsInfo.remark = JdbcMapUtil.getString(dataMap, "REMARK");
        goodsInfo.signStatus = JdbcMapUtil.getString(dataMap, "signStatus");
        goodsInfo.checkStatus = JdbcMapUtil.getString(dataMap, "checkStatus");
        goodsInfo.receiveAmt = getReceiveAmt(JdbcMapUtil.getString(dataMap, "ID"));
        goodsInfo.remark = getReceiveAmt(JdbcMapUtil.getString(dataMap, "remark"));
        goodsInfo.user = getReceiveAmt(JdbcMapUtil.getString(dataMap, "cuser"));
        return goodsInfo;
    }

    private String getReceiveAmt(String dataId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select sum(QTY_ONE) as amt from DEVICE_SIGN_LIST where DEVICE_LIST_ID=?", dataId);
        if (CollectionUtils.isEmpty(list)) {
            return JdbcMapUtil.getString(list.get(0), "amt");
        }
        return "0";
    }

    private void checkStatus(String dataId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select if((select QTY_ONE from DEVICE_LIST where id = ?) = (select sum(QTY_ONE) from DEVICE_SIGN_LIST where DEVICE_LIST_ID= ?),1,0) as finsh ", dataId, dataId);
        if (!CollectionUtils.isEmpty(list)) {
            String finsh = JdbcMapUtil.getString(list.get(0), "finsh");
            if ("1".equals(finsh)) {
                myJdbcTemplate.update("update DEVICE_LIST set CHECK_STATUS_ID='1729023835722448896' where id=? ", dataId);
            }
        }


    }

    public static class RequestParam {
        public String prjName;
        public String supplier;
        public String signDateB;
        public String signDateE;
        public String point;
        public String izSgin;
        public Integer pageSize;
        public Integer pageIndex;
    }

    public static class OutSide {
        public Integer total;
        public List<ObjInfo> objInfos;
        public List<GoodsInfo> goodsInfoList;

        public List<SearchDataInfo> dataList;
    }

    public static class ObjInfo {
        public String id;
        public String prjName;
        public String supplier;
        public String unit;
        public String useUnit;
        public String pointName;
        public String signDate;
        public String acceptanceDate;

        public String direction;

        public String izSign;
        public List<FileObj> fileObjList;
    }

    public static class FileObj {
        // 序号
        public Integer num;
        // 文件名称
        public String fileName;
        // 文件大小
        public String fileSize;
        // 上传人
        public String uploadUser;
        // 上传时间
        public String uploadDate;

        public String id;

        public String viewUrl;

        public String downloadUrl;

    }

    public static class GoodsInfo {
        public String id;
        public String dataId;//验收单ID
        public String goodsName;//货物名称
        public String brand;//品牌
        public String modelNumber;//规格
        public String qtyOne;//数量
        public String tongDj;//单价
        public String tongZj;//总价
        public String deliveryPeriod;//交付期限
        public String shelfLife;//保质期
        public String procurementApproach;//采购途径
        public String signingTime;//签收日期
        public String remark;
        public String signStatus;
        public String receiveAmt;

        public String checkStatus;

        public String user;//操作人

        public List<FileObj> fileObjList;//验收文件

        public List<SignInfo> signInfoList;
    }

    public static class SignInfo {
        public String signId;
        public String amt;
        public String money;
        public String remark;
        public String user;
        public String arriveTime;
        public List<FileObj> fileObjList;//签收文件

    }

    public static class reParam {
        public String receiptId;
        public String goodsName;
        public String brand;
        public String modelNumber;
        public String signBegin;
        public String signEnd;
        public String signStatus;
    }

    public static class SignInput {
        public String id;
        public String dataId;
        public String arriveAmt;
        public String arriveTime;
        public String remark;
        public String fileIds;
    }

    public static class DataInput {
        public String id;
        public String arriveTime;
        public String remark;
        public String fileIds;
    }

    public static class SearchParam {
        public String prjIds;
        public String goodsName;
        public String begin;
        public String end;
        public String cgWay;
    }

    public static class SearchDataInfo {
        public String prjName;
        public List<Obj> objs;
    }

    public static class Obj {
        public String useUnit;
        public List<GoodsInfo> goodsInfoList;
    }


}
