package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.bid.ext.utils.ProcessCommon;
import com.pms.bid.job.util.CcSpecialEquipConstant;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 电梯扩展
 */
@Slf4j
public class RuQzInspectionExt {


    /**
     * 启动节点修改数据状态
     */
    public void startNode(){

    }

    /**
     * 结束节点修改数据状态
     */
    public void endNode(){

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        if (entityRecordList != null) {
            for (EntityRecord entityRecord : entityRecordList) {
                String csCommId = entityRecord.csCommId;
//                Map<String, Object> valueMap = entityRecord.valueMap;
//                int update = myJdbcTemplate.update("update " + entityCode + " t set t.CC_QS_CURRENT_STATE_ID = ? where t.id=?", STATE_ID, csCommId);
//                log.info("已更新：{}", update);
            }
        }

    }

}
