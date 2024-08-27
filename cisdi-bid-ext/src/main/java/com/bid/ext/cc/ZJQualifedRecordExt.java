package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
public class ZJQualifedRecordExt {


    /**
     * 检查压力管道状态更新
     */
    public void uploadAtt() {

        Map<String, Object> varMap = ExtJarHelper.getVarMap();


        String attIds = varMap.get("ATT_IDS").toString();


        //获取上传的附件
//        FlFile.selectByIds();
//        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
//
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();


        for (EntityRecord entityRecord : entityRecordList) {

            Object id = entityRecord.valueMap.get("ID");
            if (id==null){
                throw  new BaseException("请选择记录！");
            }
            CcQualityCheckUnqualifiedRecord record = CcQualityCheckUnqualifiedRecord.selectById(id.toString());

            if (record == null){
                continue;
            }

            //加上新上传的附件
            String attachments = record.getCcAttachments(); //一起的附件id
            if(StringUtils.hasText(attachments)){
                attachments = attachments+","+attIds;

            }else{
                attachments = attIds;
            }
            //更新附件
            record.setCcAttachments(attachments);
            record.updateById();
        }
    }

}
