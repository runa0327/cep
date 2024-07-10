package com.bid.ext;

import com.bid.ext.model.CcPo;
import com.bid.ext.model.CcPrjStructNode;
import com.bid.ext.model.FlFile;
import com.bid.ext.utils.SysSettingUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.ext.UrlToOpen;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.bid.ext.utils.ImportValueUtils.*;

public class TestExt {
    public void importContract() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        FlFile flFile = FlFile.selectById(varMap.get("P_CC_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();
        if (!"xls".equals(flFile.getExt()) && !"xlsx".equals(flFile.getExt())) {
            throw new BaseException("请上传'xls'或'xlsx'格式的Excel文件");
        }
        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            // 遍历每一行
            for (int i = 1; i <= Objects.requireNonNull(sheet).getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                // 检查行是否为空
                if (row == null) {
                    continue; // 如果为空，跳过该行
                }

                String bidCode = getStringCellValue(row.getCell(0)); //合同编号
                String prjStructNodeCode = getStringCellValue(row.getCell(1)); //单元工程编号
                if (prjStructNodeCode != null && prjStructNodeCode.endsWith(".0")) {
                    prjStructNodeCode = prjStructNodeCode.substring(0, prjStructNodeCode.length() - 2);
                }
                CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectOneByWhere(new Where().eq(CcPrjStructNode.Cols.CODE, prjStructNodeCode));
                String ccPrjStructNodeId = ccPrjStructNode.getId(); //单元工程名称
                String ccPrjId = ccPrjStructNode.getCcPrjId(); //项目名称
                String bidName = getStringCellValue(row.getCell(3)); //合同名称

                String ccCurrencyTypeId = getStringCellValue(row.getCell(6)); //币种
                switch (ccCurrencyTypeId) {
                    case "人民币":
                        ccCurrencyTypeId = "CNY";
                        break;
                }
                String projectUnit = getStringCellValue(row.getCell(7)); //项目单位
                String partyB = getStringCellValue(row.getCell(8)); //乙方
                String ccPoTypeId = getStringCellValue(row.getCell(10)); //合同类型

                switch (ccPoTypeId) {
                    case "技术服务合同":
                        ccPoTypeId = "TechnicalServiceContract";
                        break;
                    case "工程设计合同":
                        ccPoTypeId = "EngineeringDesignContract";
                        break;
                    case "技术咨询合同":
                        ccPoTypeId = "TechnicalConsultingContract";
                        break;
                    case "监理合同":
                        ccPoTypeId = "SupervisionContract";
                        break;
                    case "勘察合同":
                        ccPoTypeId = "SurveyContract";
                        break;
                    case "施工合同":
                        ccPoTypeId = "ConstructionContract";
                        break;
                    case "施工准备合同":
                        ccPoTypeId = "ConstructionPreparationContract";
                        break;
                }

                String ccPoStatusId = getStringCellValue(row.getCell(11)); //合同状态
                switch (ccPoStatusId) {
                    case "生效":
                        ccPoStatusId = "Effective";
                        break;
                    case "已发送":
                        ccPoStatusId = "Sent";
                        break;
                    case "关闭":
                        ccPoStatusId = "Closed";
                        break;
                    case "结案":
                        ccPoStatusId = "Closed";
                        break;
                }

                String ccRegisteredStatusId = getStringCellValue(row.getCell(16)); //备案状态
                switch (ccRegisteredStatusId) {
                    case "备案成功":
                        ccRegisteredStatusId = "SUCC";
                        break;
                    case "待备案":
                        ccRegisteredStatusId = "TODO";
                        break;
                }

                CcPo ccPo = CcPo.selectOneByWhere(new Where().eq(CcPo.Cols.CODE, bidCode));
                if (SharedUtil.isEmpty(ccPo)) {

                    CcPo po = CcPo.newData();
                    po.setCcPrjId(ccPrjId);
                    po.setCode(bidCode);
                    po.setCcPrjStructNodeId(ccPrjStructNodeId);
                    po.setName(bidName);
                    po.setTrxAmt(BigDecimal.valueOf(getNumericCellValue(row.getCell(4))));
                    po.setSettlementAmt(BigDecimal.valueOf(getNumericCellValue(row.getCell(5))));
                    po.setCcCurrencyTypeId(ccCurrencyTypeId);
                    po.setProjectUnit(projectUnit);
                    po.setPartyB(partyB);
                    po.setTrxDate(getLocalDateCellValue(row.getCell(9)));
                    po.setCcPoTypeId(ccPoTypeId);
                    po.setCcPoStatusId(ccPoStatusId);
                    po.setPlanFr(getLocalDateCellValue(row.getCell(12)));
                    po.setPlanTo(getLocalDateCellValue(row.getCell(13)));
                    po.setCcBidCreateUserId(getStringCellValue(row.getCell(14)));
                    po.setIsRegistered(getBooleanCellValue(row.getCell(15)));
                    po.setCcRegisteredStatusId(ccRegisteredStatusId);

                    po.insertById();
                } else {
                    ccPo.setCcPrjId(ccPrjId);
                    ccPo.setCode(bidCode);
                    ccPo.setCcPrjStructNodeId(ccPrjStructNodeId);
                    ccPo.setName(bidName);
                    ccPo.setTrxAmt(BigDecimal.valueOf(getNumericCellValue(row.getCell(4))));
                    ccPo.setSettlementAmt(BigDecimal.valueOf(getNumericCellValue(row.getCell(5))));
                    ccPo.setCcCurrencyTypeId(ccCurrencyTypeId);
                    ccPo.setProjectUnit(projectUnit);
                    ccPo.setPartyB(partyB);
                    ccPo.setTrxDate(getLocalDateCellValue(row.getCell(9)));
                    ccPo.setCcPoTypeId(ccPoTypeId);
                    ccPo.setCcPoStatusId(ccPoStatusId);
                    ccPo.setPlanFr(getLocalDateCellValue(row.getCell(12)));
                    ccPo.setPlanTo(getLocalDateCellValue(row.getCell(13)));
                    ccPo.setCcBidCreateUserId(getStringCellValue(row.getCell(14)));
                    ccPo.setIsRegistered(getBooleanCellValue(row.getCell(15)));
                    ccPo.setCcRegisteredStatusId(ccRegisteredStatusId);
                    ccPo.updateById();
                }
            }

        } catch (IOException e) {
            throw new BaseException("上传文件失败", e);
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }


    /**
     * 简化版预览文件。采用KK进行预览。
     *
     * @throws UnsupportedEncodingException
     */
    public void simplePreview() throws UnsupportedEncodingException {
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.urlToOpenList = new ArrayList<>();

        String fileDownloadUrl1 = SysSettingUtil.getValue("FILE_DOWNLOAD_URL");
        String kkPreviewUrl = SysSettingUtil.getValue("KK_PREVIEW_URL");

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            Object ccAttachment = entityRecord.valueMap.get("CC_ATTACHMENT");
            if (SharedUtil.isEmpty(ccAttachment)) {
                throw new BaseException("资料文件的CC_ATTACHMENT字段为空！");
            }

            List<Map<String, Object>> fileList = ExtJarHelper.getMyJdbcTemplate().queryForList("select * from fl_file f where f.id=?", ccAttachment);
            if (SharedUtil.isEmpty(fileList)) {
                throw new BaseException("资料文件的CC_ATTACHMENT字段对应FL_FILE记录不存在！");
            }

            Map<String, Object> file = fileList.get(0);

            String fileId = JdbcMapUtil.getString(file, "ID");
            String fileExt = JdbcMapUtil.getString(file, "EXT");

            String fileDownloadUrl = fileDownloadUrl1 + "?fileId=" + fileId + "&qygly-session-id=" + ExtJarHelper.getLoginInfo().sessionId + "&fullfilename=" + fileId + (SharedUtil.isEmpty(fileExt) ? "" : ("." + fileExt));

            // 采用KK进行预览时，要对url部分做2次编码。第1次是Base64编码、第2次是URL编码：
            String previewUrl = kkPreviewUrl + "?url=" + URLEncoder.encode(cn.hutool.core.codec.Base64.encode(fileDownloadUrl), "UTF-8");

            UrlToOpen urlToOpen = new UrlToOpen();
            urlToOpen.url = previewUrl;
            urlToOpen.title = "预览";
            invokeActResult.urlToOpenList.add(urlToOpen);
        }

        ExtJarHelper.setReturnValue(invokeActResult);
    }

}
