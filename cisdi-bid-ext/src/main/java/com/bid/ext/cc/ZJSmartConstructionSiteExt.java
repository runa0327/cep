package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.bid.ext.utils.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 质量评估扩展
 */
public class ZJSmartConstructionSiteExt {





    //检测日期检查
    public void checkRecord() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for (EntityRecord record : entityRecordList) {

            String year = record.valueMap.get("CC_YEAR_NAME").toString();
            String month = record.valueMap.get("CC_MONGTH_NAME").toString();
            String nodeId = record.valueMap.get("CC_PRJ_STRUCT_NODE_ID").toString();
            String mainBodyId = record.valueMap.get("CC_QUALITY_CHECK_MAIN_BODY_ID").toString();
            String checkTypeId = record.valueMap.get("CC_QUALITY_CHECK_TYPE_ID").toString();
            String typeContentId = record.valueMap.get("CC_QUALITY_CHECK_TYPE_CONTENT_ID").toString();
            String batchNum = record.valueMap.get("CC_QULITY_CHECK_BATCH_NUM").toString();
            String qulifiedNum = record.valueMap.get("CC_QULITY_CHECK_BATCH_QULIFIED_NUM").toString();

            if (Integer.parseInt(batchNum) < 1 ) {
                throw new BaseException("送检批次小于1");
            }
            if (Integer.parseInt(qulifiedNum) < 0 ) {
                throw new BaseException("合格批次小于0");
            }

            if (Integer.parseInt(batchNum) < Integer.parseInt(qulifiedNum)) {
                throw new BaseException("合格批次大于送检批次");
            }


            Where query = new Where();
            query.sql("T.STATUS='AP' AND  T.CC_PRJ_STRUCT_NODE_ID='" + nodeId + "' AND T.CC_QUALITY_CHECK_MAIN_BODY_ID='" + mainBodyId + "'" + " AND T.CC_QUALITY_CHECK_TYPE_ID='" + checkTypeId + "' AND T.CC_QUALITY_CHECK_TYPE_CONTENT_ID='" + typeContentId + "' AND T.CC_YEAR_NAME = '" + year + "' AND  T.CC_MONGTH_NAME='" + month + "'");
            CcQualityCheckRecord ccQualityCheckRecord = CcQualityCheckRecord.selectOneByWhere(query);

            if (ccQualityCheckRecord != null) {
                throw new BaseException("存在同一时期检测数据");
            }

        }

    }


}
