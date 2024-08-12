package com.bid.ext.cc;

import com.bid.ext.model.CcEquipIot;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.DateTimeUtil;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ZJIOTEquipExt {


    /**
     * 检查更新基坑点位状态
     */
    public void equipIsOnlineCheck(){


    }


    /**
     * 检查更新基坑点位更新设备是否正常状态
     */
    public void updateEquipStatus(){

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for (EntityRecord entityRecord:entityRecordList){

            if (entityRecord.valueMap.get("POINT_NAME")==null){
                continue;
            }
            String pointName = entityRecord.valueMap.get("POINT_NAME").toString();

            String outOfLimit =  "0";
            if (entityRecord.valueMap.get("OUT_OF_LIMINT")!=null){
                outOfLimit = entityRecord.valueMap.get("OUT_OF_LIMINT").toString();
            }

            Where queryEquip =  new Where();
            queryEquip.sql("T.STATUS = 'AP' AND  T.POINT_NAME='"+pointName+"'");

            CcEquipIot ccEquipIot = null;
            try{
                ccEquipIot = CcEquipIot.selectOneByWhere(queryEquip);
            }catch (Exception e){
                log.error(new Date()+"设备点名"+pointName+"查询失败:"+e.getMessage());
            }

            if (ccEquipIot!=null){
                ccEquipIot.setIsOnline(true);
                if ("1".equals(outOfLimit)){

                }else{

                }

            }

        }

    }


    public void updateEquipStatus2(){

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for (EntityRecord entityRecord:entityRecordList){

            if (entityRecord.valueMap.get("POINT_NAME")==null){
                continue;
            }

            Where queryEquip = new Where();

        }

    }

}
