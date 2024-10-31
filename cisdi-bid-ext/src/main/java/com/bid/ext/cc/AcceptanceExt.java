package com.bid.ext.cc;

import com.bid.ext.entity.*;
import com.bid.ext.model.AdAtt;
import com.bid.ext.model.CcCompletionAcceptance;
import com.bid.ext.model.FlFile;
import com.bid.ext.model.FlPath;
import com.bid.ext.utils.DownloadUtils;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class AcceptanceExt {

    private static final String TEMPLATE_PATH = "templates/acceptance.docx";

    /**
     * 生成竣工验收报告（pdf）
     */
    public void genPdf() {
        InvokeActResult invokeActResult = new InvokeActResult();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());


        for (EntityRecord entityRecord : entityRecordList) {
            // 获取属性：
            Where attWhere = new Where();
            attWhere.eq(AdAtt.Cols.CODE, CcCompletionAcceptance.Cols.CC_ACCEPTANCE_NOTICE_ID);
            AdAtt adAtt = AdAtt.selectOneByWhere(attWhere);

            // 获取路径：
            Where pathWhere = new Where();
            pathWhere.eq(FlPath.Cols.ID, adAtt.getFilePathId());
            FlPath flPath = FlPath.selectOneByWhere(pathWhere);

            Map<String, Object> valueMap = entityRecord.valueMap;


            String ccPrjId = fetchNameFromTable("CC_PRJ",
                    valueMap.get("CC_PRJ_ID").toString(),
                    loginInfo.currentLangId.toString());

            String engineeringUnit = JdbcMapUtil.getString(valueMap, "ENGINEERING_UNIT");
            String acceptanceLocation = JdbcMapUtil.getString(valueMap, "ACCEPTANCE_LOCATION");


            Object projectOwner = valueMap.get("PROJECT_OWNER");
            List<Map<String, Object>> projectOwnerList1 = null;
            if (!SharedUtil.isEmpty(projectOwner)) {
                projectOwnerList1 = fetchAndFormatIssuePointNamesNoIndex("CC_COMPANY",
                        valueMap.get("PROJECT_OWNER").toString(),
                        loginInfo.currentLangId.toString());
            }

            List<Map<String, Object>> projectOwnerName = null;
            if (!SharedUtil.isEmpty(projectOwner)) {
                projectOwnerName = fetchAndFormatIssuePointNamesNoSign("CC_COMPANY",
                        valueMap.get("PROJECT_OWNER").toString(),
                        loginInfo.currentLangId.toString());
            }

            //建设单位
            Object projectOwnerChiefUserIds = valueMap.get("PROJECT_OWNER_CHIEF_USER_IDS");
            List<Map<String, Object>> projectOwnerChiefUserIdsName = null;
            if (!SharedUtil.isEmpty(projectOwnerChiefUserIds)) {
                projectOwnerChiefUserIdsName = fetchAndFormatIssuePointNamesNoSign("CC_PRJ_MEMBER",
                        valueMap.get("PROJECT_OWNER_CHIEF_USER_IDS").toString(),
                        loginInfo.currentLangId.toString());
            }

            List<ProjectOwner> projectOwnerList = new ArrayList<>();
            for (int i = 1; i <= projectOwnerName.size(); i++) {
                ProjectOwner projectOwner1 = new ProjectOwner();
                projectOwner1.setProjectOwner(projectOwnerName.get(i - 1).get("name").toString());
                projectOwner1.setProjectOwnerChiefUserIds(projectOwnerChiefUserIdsName.get(i - 1).get("name").toString());
                projectOwnerList.add(projectOwner1);
            }

            //设计单位
            Object designContractor = valueMap.get("DESIGN_CONTRACTOR");
            List<Map<String, Object>> designContractorName = null;
            if (!SharedUtil.isEmpty(designContractor)) {
                designContractorName = fetchAndFormatIssuePointNamesNoSign("CC_COMPANY",
                        valueMap.get("DESIGN_CONTRACTOR").toString(),
                        loginInfo.currentLangId.toString());
            }

            Object designContractorChiefUserIds = valueMap.get("DESIGN_CONTRACTOR_CHIEF_USER_IDS");
            List<Map<String, Object>> designContractorChiefUserIdsName = null;
            if (!SharedUtil.isEmpty(designContractorChiefUserIds)) {
                designContractorChiefUserIdsName = fetchAndFormatIssuePointNamesNoSign("CC_PRJ_MEMBER",
                        valueMap.get("DESIGN_CONTRACTOR_CHIEF_USER_IDS").toString(),
                        loginInfo.currentLangId.toString());
            }

            List<DesignContractor> designContractorList = new ArrayList<>();
            for (int i = 1; i <= designContractorName.size(); i++) {
                DesignContractor designContractor1 = new DesignContractor();
                designContractor1.setDesignContractor(designContractorName.get(i - 1).get("name").toString());
                designContractor1.setDesignContractorChiefUserIds(designContractorChiefUserIdsName.get(i - 1).get("name").toString());
                designContractorList.add(designContractor1);
            }

            //勘察单位
            Object surveyContractor = valueMap.get("SURVEY_CONTRACTOR");
            List<Map<String, Object>> surveyContractorName = null;
            if (!SharedUtil.isEmpty(surveyContractor)) {
                surveyContractorName = fetchAndFormatIssuePointNamesNoSign("CC_COMPANY",
                        valueMap.get("SURVEY_CONTRACTOR").toString(),
                        loginInfo.currentLangId.toString());
            }

            Object surveyContractorChiefUserIds = valueMap.get("SURVEY_CONTRACTOR_CHIEF_USER_IDS");
            List<Map<String, Object>> surveyContractorChiefUserIdsName = null;
            if (!SharedUtil.isEmpty(surveyContractorChiefUserIds)) {
                surveyContractorChiefUserIdsName = fetchAndFormatIssuePointNamesNoSign("CC_PRJ_MEMBER",
                        valueMap.get("SURVEY_CONTRACTOR_CHIEF_USER_IDS").toString(),
                        loginInfo.currentLangId.toString());
            }

            List<SurveyContractor> surveyContractorList = new ArrayList<>();
            for (int i = 1; i <= surveyContractorName.size(); i++) {
                SurveyContractor surveyContractor1 = new SurveyContractor();
                surveyContractor1.setSurveyContractor(surveyContractorName.get(i - 1).get("name").toString());
                surveyContractor1.setSurveyContractorChiefUserIds(surveyContractorChiefUserIdsName.get(i - 1).get("name").toString());
                surveyContractorList.add(surveyContractor1);
            }

            //施工单位
            Object constructionContractor = valueMap.get("CONSTRUCTION_CONTRACTOR");
            List<Map<String, Object>> constructionContractorName = null;
            if (!SharedUtil.isEmpty(constructionContractor)) {
                constructionContractorName = fetchAndFormatIssuePointNamesNoSign("CC_COMPANY",
                        valueMap.get("CONSTRUCTION_CONTRACTOR").toString(),
                        loginInfo.currentLangId.toString());
            }

            Object constructionContractorChiefUserIds = valueMap.get("CONSTRUCTION_CONTRACTOR_CHIEF_USER_IDS");
            List<Map<String, Object>> constructionContractorChiefUserIdsName = null;
            if (!SharedUtil.isEmpty(constructionContractorChiefUserIds)) {
                constructionContractorChiefUserIdsName = fetchAndFormatIssuePointNamesNoSign("CC_PRJ_MEMBER",
                        valueMap.get("CONSTRUCTION_CONTRACTOR_CHIEF_USER_IDS").toString(),
                        loginInfo.currentLangId.toString());
            }

            List<ConstructionContractor> constructionContractorList = new ArrayList<>();
            for (int i = 1; i <= constructionContractorName.size(); i++) {
                ConstructionContractor constructionContractor1 = new ConstructionContractor();
                constructionContractor1.setConstructionContractor(constructionContractorName.get(i - 1).get("name").toString());
                constructionContractor1.setConstructionContractorChiefUserIds(constructionContractorChiefUserIdsName.get(i - 1).get("name").toString());
                constructionContractorList.add(constructionContractor1);
            }

            //监理单位
            Object supervisingContractor = valueMap.get("SUPERVISING_CONTRACTOR");
            List<Map<String, Object>> supervisingContractorName = null;
            if (!SharedUtil.isEmpty(supervisingContractor)) {
                supervisingContractorName = fetchAndFormatIssuePointNamesNoSign("CC_COMPANY",
                        valueMap.get("SUPERVISING_CONTRACTOR").toString(),
                        loginInfo.currentLangId.toString());
            }

            Object supervisingContractorChiefUserIds = valueMap.get("SUPERVISING_CONTRACTOR_CHIEF_USER_IDS");
            List<Map<String, Object>> supervisingContractorChiefUserIdsName = null;
            if (!SharedUtil.isEmpty(supervisingContractorChiefUserIds)) {
                supervisingContractorChiefUserIdsName = fetchAndFormatIssuePointNamesNoSign("CC_PRJ_MEMBER",
                        valueMap.get("SUPERVISING_CONTRACTOR_CHIEF_USER_IDS").toString(),
                        loginInfo.currentLangId.toString());
            }

            List<SupervisingContractor> supervisingContractorList = new ArrayList<>();
            for (int i = 1; i <= supervisingContractorName.size(); i++) {
                SupervisingContractor supervisingContractor1 = new SupervisingContractor();
                supervisingContractor1.setSupervisingContractor(supervisingContractorName.get(i - 1).get("name").toString());
                supervisingContractor1.setSupervisingContractorChiefUserIds(supervisingContractorChiefUserIdsName.get(i - 1).get("name").toString());
                supervisingContractorList.add(supervisingContractor1);
            }

            String projectCompletionStatus = JdbcMapUtil.getString(valueMap, "PROJECT_COMPLETION_STATUS");
            String supervisionUnitQualityReport = JdbcMapUtil.getString(valueMap, "SUPERVISION_UNIT_QUALITY_REPORT");
            String surveyDocumentQualityReport = JdbcMapUtil.getString(valueMap, "SURVEY_DOCUMENT_QUALITY_REPORT");
            String designDocumentQualityReport = JdbcMapUtil.getString(valueMap, "DESIGN_DOCUMENT_QUALITY_REPORT");
            String completionAcceptancePlan = JdbcMapUtil.getString(valueMap, "COMPLETION_ACCEPTANCE_PLAN");
            String projectPaymentStatus = JdbcMapUtil.getString(valueMap, "PROJECT_PAYMENT_STATUS");
            String qualityWarranty = JdbcMapUtil.getString(valueMap, "QUALITY_WARRANTY");
            String foundationDivision = JdbcMapUtil.getString(valueMap, "FOUNDATION_DIVISION");
            String mainStructureDivision = JdbcMapUtil.getString(valueMap, "MAIN_STRUCTURE_DIVISION");
            String buildingEnergySavingDivision = JdbcMapUtil.getString(valueMap, "BUILDING_ENERGY_SAVING_DIVISION");
            String specializedContractingProject = JdbcMapUtil.getString(valueMap, "SPECIALIZED_CONTRACTING_PROJECT");
            String materialEquipmentInspection = JdbcMapUtil.getString(valueMap, "MATERIAL_EQUIPMENT_INSPECTION");
            String qualityTestFunctionTestReport = JdbcMapUtil.getString(valueMap, "QUALITY_TEST_FUNCTION_TEST_REPORT");
            String technicalAndManagementDocumentation = JdbcMapUtil.getString(valueMap, "TECHNICAL_AND_MANAGEMENT_DOCUMENTATION");
            String projectSupervisionDocumentation = JdbcMapUtil.getString(valueMap, "PROJECT_SUPERVISION_DOCUMENTATION");
            String energyEfficiencyAssessment = JdbcMapUtil.getString(valueMap, "ENERGY_EFFICIENCY_ASSESSMENT");
            String housingUnitAcceptance = JdbcMapUtil.getString(valueMap, "HOUSING_UNIT_ACCEPTANCE");
            String companyCreditEvaluation = JdbcMapUtil.getString(valueMap, "COMPANY_CREDIT_EVALUATION");
            String regulatoryAgencyRectification = JdbcMapUtil.getString(valueMap, "REGULATORY_AGENCY_RECTIFICATION");
            String planningFireEnvironmentalAcceptance = JdbcMapUtil.getString(valueMap, "PLANNING_FIRE_ENVIRONMENTAL_ACCEPTANCE");
            String acceptanceIssue = JdbcMapUtil.getString(valueMap, "ACCEPTANCE_ISSUE");
            String acceptanceOpinion = JdbcMapUtil.getString(valueMap, "ACCEPTANCE_OPINION");


            LocalDate acceptanceDate = JdbcMapUtil.getLocalDate(valueMap, "ACCEPTANCE_DATE");
            String projectContentScope = JdbcMapUtil.getString(valueMap, "PROJECT_CONTENT_SCOPE");
            String remark = JdbcMapUtil.getString(valueMap, "REMARK");
            String outstandingProjectIssues = JdbcMapUtil.getString(valueMap, "OUTSTANDING_PROJECT_ISSUES");

            Map<String, Object> map = new HashMap<String, Object>();

            map.put("projectOwnerList", projectOwnerList);
            map.put("designContractorList", designContractorList);
            map.put("surveyContractorList", surveyContractorList);
            map.put("constructionContractorList", constructionContractorList);
            map.put("supervisingContractorList", supervisingContractorList);

            map.put("CC_PRJ_ID", ccPrjId);
            map.put("ENGINEERING_UNIT", engineeringUnit);
            map.put("ACCEPTANCE_LOCATION", acceptanceLocation);
            map.put("PROJECT_OWNER", projectOwnerName);
//            map.put("DESIGN_CONTRACTOR", designContractorName);
//            map.put("SURVEY_CONTRACTOR", surveyContractorName);
//            map.put("CONSTRUCTION_CONTRACTOR", constructionContractorName);
//            map.put("SUPERVISING_CONTRACTOR", supervisingContractorName);
//            map.put("PROJECT_OWNER_CHIEF_USER_IDS", projectOwnerChiefUserIdsName);
//            map.put("DESIGN_CONTRACTOR_CHIEF_USER_IDS", designContractorChiefUserIdsName);
//            map.put("SURVEY_CONTRACTOR_CHIEF_USER_IDS", surveyContractorChiefUserIdsName);
//            map.put("CONSTRUCTION_CONTRACTOR_CHIEF_USER_IDS", constructionContractorChiefUserIdsName);
//            map.put("SUPERVISING_CONTRACTOR_CHIEF_USER_IDS", supervisingContractorChiefUserIdsName);
            map.put("PROJECT_COMPLETION_STATUS", projectCompletionStatus);
            map.put("SUPERVISION_UNIT_QUALITY_REPORT", supervisionUnitQualityReport);
            map.put("SURVEY_DOCUMENT_QUALITY_REPORT", surveyDocumentQualityReport);
            map.put("DESIGN_DOCUMENT_QUALITY_REPORT", designDocumentQualityReport);
            map.put("COMPLETION_ACCEPTANCE_PLAN", completionAcceptancePlan);
            map.put("PROJECT_PAYMENT_STATUS", projectPaymentStatus);
            map.put("QUALITY_WARRANTY", qualityWarranty);
            map.put("FOUNDATION_DIVISION", foundationDivision);
            map.put("MAIN_STRUCTURE_DIVISION", mainStructureDivision);
            map.put("BUILDING_ENERGY_SAVING_DIVISION", buildingEnergySavingDivision);
            map.put("SPECIALIZED_CONTRACTING_PROJECT", specializedContractingProject);
            map.put("MATERIAL_EQUIPMENT_INSPECTION", materialEquipmentInspection);
            map.put("QUALITY_TEST_FUNCTION_TEST_REPORT", qualityTestFunctionTestReport);
            map.put("TECHNICAL_AND_MANAGEMENT_DOCUMENTATION", technicalAndManagementDocumentation);
            map.put("PROJECT_SUPERVISION_DOCUMENTATION", projectSupervisionDocumentation);
            map.put("ENERGY_EFFICIENCY_ASSESSMENT", energyEfficiencyAssessment);
            map.put("HOUSING_UNIT_ACCEPTANCE", housingUnitAcceptance);
            map.put("COMPANY_CREDIT_EVALUATION", companyCreditEvaluation);
            map.put("REGULATORY_AGENCY_RECTIFICATION", regulatoryAgencyRectification);
            map.put("PLANNING_FIRE_ENVIRONMENTAL_ACCEPTANCE", planningFireEnvironmentalAcceptance);
            map.put("ACCEPTANCE_ISSUE", acceptanceIssue);
            map.put("ACCEPTANCE_OPINION", acceptanceOpinion);
            map.put("ACCEPTANCE_DATE", acceptanceDate);
            map.put("PROJECT_CONTENT_SCOPE", projectContentScope);
            map.put("REMARK", remark);
            map.put("OUTSTANDING_PROJECT_ISSUES", outstandingProjectIssues);

            System.out.println("Survey Contractor List: " + map.get("surveyContractorList"));

            byte[] word = null;

            try {
                ClassPathResource res = new ClassPathResource(TEMPLATE_PATH, DownloadUtils.class.getClassLoader());
                Configure config = Configure.builder()
                        .bind("projectOwnerList", new LoopRowTableRenderPolicy())
                        .bind("designContractorList", new LoopRowTableRenderPolicy())
                        .bind("surveyContractorList", new LoopRowTableRenderPolicy())
                        .bind("constructionContractorList", new LoopRowTableRenderPolicy())
                        .bind("supervisingContractorList", new LoopRowTableRenderPolicy())
                        .build();
                // 获取模板
                XWPFTemplate template = XWPFTemplate.compile(res.getInputStream(), config).render(map);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                template.write(out);
                word = out.toByteArray();
                out.close();
                template.close();

                // 加载 Word 文件
                ByteArrayInputStream bais = new ByteArrayInputStream(word);
                XWPFDocument document = new XWPFDocument(bais);

                // 获取第一个表格
                XWPFTable table = document.getTables().get(1);

                // 需要处理的目标文本列表
                List<String> targetTexts = Arrays.asList("勘察单位", "建设单位", "设计单位", "施工单位", "监理单位");

                for (String targetText : targetTexts) {
                    int targetRowIndex = -1;

                    // 找到包含 "建设单位" 的行
                    for (int i = 0; i < table.getNumberOfRows(); i++) {
                        XWPFTableRow row = table.getRow(i);
                        for (XWPFTableCell cell : row.getTableCells()) {
                            if (cell.getText().contains(targetText)) {
                                targetRowIndex = i;
                                break;
                            }
                        }
                        if (targetRowIndex != -1) {
                            break;
                        }
                    }

                    // 如果找到了目标行并且它不是最后一行
                    if (targetRowIndex != -1 && targetRowIndex < table.getNumberOfRows() - 1) {
                        XWPFTableRow targetRow = table.getRow(targetRowIndex);
                        XWPFTableRow nextRow = table.getRow(targetRowIndex + 1);

                        // 合并目标行和下一行的所有单元格
                        int columnCount = Math.min(targetRow.getTableCells().size(), nextRow.getTableCells().size());
                        for (int colIndex = 2; colIndex < columnCount; colIndex++) {
                            XWPFTableCell targetCell = targetRow.getCell(colIndex);
                            XWPFTableCell nextCell = nextRow.getCell(colIndex);

                            if (targetCell != null && nextCell != null) {
                                // 将下一行的文本合并到目标行的单元格中
                                targetCell.setText(targetCell.getText() + "\n" + nextCell.getText());
                            }
                        }

                        // 移除下一行，避免多余的空白行
                        table.removeRow(targetRowIndex + 1);

                        // 调整合并后的行高度为原来的一半
                        int originalHeight = targetRow.getHeight();
                        targetRow.setHeight(originalHeight / 2);
                    }
                }

                // 将修改后的文档转换为 byte[]
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                document.write(baos);
                word = baos.toByteArray();
                baos.close();
                document.close();
            } catch (Exception e) {
                throw new BaseException(e);
            }

            byte[] b = convertWordToPDF(word);

            FlFile flFile = FlFile.newData();

            // 将String写入文件，覆盖模式，字符集为UTF-8x``
            String fileId = flFile.getId();

            // 构建文件名和路径
            String path = flPath.getDir() + year + "/" + month + "/" + day + "/" + fileId + ".pdf";
            saveWordToFile(b, path);
            boolean fileExists = checkFileExists(path);
            if (fileExists) {
                //获取文件属性
                File file = new File(path);
                long bytes = file.length();
                double kilobytes = bytes / 1024.0;

                BigDecimal sizeKb = BigDecimal.valueOf(kilobytes).setScale(9, BigDecimal.ROUND_HALF_UP);
                String dspSize = String.format("%d KB", Math.round(kilobytes));
                flFile.setCrtUserId(loginInfo.userInfo.id);
                flFile.setLastModiUserId(loginInfo.userInfo.id);
                flFile.setFlPathId(flPath.getId());
                flFile.setCode(fileId);
                flFile.setName("竣工验收通知单");
                flFile.setExt("pdf");
                flFile.setDspName("竣工验收通知单.pdf");
                flFile.setFileInlineUrl(flPath.getFileInlineUrl() + "?fileId=" + fileId);
                flFile.setFileAttachmentUrl(flPath.getFileAttachmentUrl() + "?fileId=" + fileId);
                flFile.setSizeKb(sizeKb);
                flFile.setDspSize(dspSize);
                flFile.setUploadDttm(LocalDateTime.now());
                flFile.setPhysicalLocation(path);
                flFile.setOriginFilePhysicalLocation(path);
//                flFile.setIsPublicRead(flPath.getIsPublicRead());
                flFile.setIsPublicRead(false);
                flFile.insertById();

                // 将文件ID设置到CC_ACCEPTANCE_NOTICE_ID上：
                CcCompletionAcceptance ccCompletionAcceptance = CcCompletionAcceptance.selectById(EntityRecordUtil.getId(entityRecord));
                ccCompletionAcceptance.setCcAcceptanceNoticeId(fileId);
                ccCompletionAcceptance.updateById();
            } else {
                throw new BaseException("文件未找到：" + path);
            }
        }
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    private static void saveWordToFile(byte[] wordContent, String outputPath) {
        try {
            // 创建一个File对象，代表输出路径
            File outputFile = new File(outputPath);

            // 获取输出文件的父目录
            File parentDir = outputFile.getParentFile();

            // 如果父目录不存在，则创建它
            if (!parentDir.exists()) {
                parentDir.mkdirs();

            }

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(wordContent);
            }
        } catch (IOException e) {
            throw new BaseException(e);
        }
    }

    private static boolean checkFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    private static byte[] convertWordToPDF(byte[] docxBytes) {
        try {
            // 创建临时文件保存 DOCX 内容
            Path tempDocx = Files.createTempFile(null, ".docx");
            Files.write(tempDocx, docxBytes);

            // 定义 PDF 临时文件的路径
            Path tempPdf = Files.createTempFile(null, ".PDF");

            // 指定 LibreOffice 的安装路径及命令行工具
//            String libreOfficePath = "/usr/bin/libreoffice";
//            String libreOfficePath = "D:/Program Files/LibreOffice/program/soffice.exe";
            String sql = "select SETTING_VALUE from AD_SYS_SETTING where code = 'LIBRE_PATH' ";
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
            List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql);
            String libreOfficePath = (String) list.get(0).get("SETTING_VALUE");

            // 调用 LibreOffice 进行转换
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(libreOfficePath, "--convert-to", "pdf:writer_pdf_Export", tempDocx.toString(), "--outdir", tempPdf.getParent().toString());
            Process process = builder.start();
            process.waitFor();

            // 计算转换后的 PDF 文件名
            String pdfFileName = tempDocx.getFileName().toString().replaceAll("\\.docx$", ".pdf");
            Path pdfFilePath = tempPdf.getParent().resolve(pdfFileName);

            // 读取生成的 PDF 文件
            byte[] pdfBytes = Files.readAllBytes(pdfFilePath);

            // 清理临时文件
            Files.delete(tempDocx);
            Files.delete(pdfFilePath);

            return pdfBytes;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("PDF文件转换失败", e);
        }
    }

    /**
     * 从指定表中获取名称
     *
     * @param tableName
     * @param idString
     * @param currentLangId
     * @return
     */
    private static List<Map<String, Object>> fetchAndFormatIssuePointNames(String tableName, String idString, String currentLangId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        List<String> ids = Arrays.asList(idString.split(","));
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?")); // 动态创建SQL IN子句
        String sql = "SELECT IF(JSON_VALID(NAME), NAME->>'$." + currentLangId + "', NAME) as name FROM " + tableName
                + " WHERE NAME IS NOT NULL AND id IN (" + inSql + ")";
        List<Map<String, Object>> resultList = myJdbcTemplate.queryForList(sql, ids.toArray());
        List<Map<String, Object>> formattedNames = new ArrayList<Map<String, Object>>();

        int index = 1; // 开始编号
        for (Map<String, Object> result : resultList) {
            Map<String, Object> formattedEntry = new HashMap<>();
            formattedEntry.put("content", index++ + "、" + JdbcMapUtil.getString(result, "name"));
            formattedNames.add(formattedEntry);
        }

        return formattedNames;
    }

    /**
     * 从指定表中获取名称（无编号）
     *
     * @param tableName
     * @param idString
     * @param currentLangId
     * @return
     */
    private static List<Map<String, Object>> fetchAndFormatIssuePointNamesNoIndex(String tableName, String idString, String currentLangId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        List<String> ids = Arrays.asList(idString.split(","));
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?")); // 动态创建SQL IN子句
        String sql = "SELECT IF(JSON_VALID(NAME), NAME->>'$." + currentLangId + "', NAME) as name FROM " + tableName
                + " WHERE NAME IS NOT NULL AND id IN (" + inSql + ")";
        List<Map<String, Object>> resultList = myJdbcTemplate.queryForList(sql, ids.toArray());
        List<Map<String, Object>> formattedNames = new ArrayList<Map<String, Object>>();

        boolean isFirst = true; // 标识变量
        for (Map<String, Object> result : resultList) {
            Map<String, Object> formattedEntry = new HashMap<>();
            String prefix = isFirst ? "" : "、"; // 第一个元素前面不加“、”
            formattedEntry.put("content", prefix + JdbcMapUtil.getString(result, "name"));
            formattedNames.add(formattedEntry);
            isFirst = false; // 更新标识变量
        }

        return formattedNames;
    }

    /**
     * 从指定表中获取名称（无编号无符号）
     *
     * @param tableName
     * @param idString
     * @param currentLangId
     * @return
     */
    private static List<Map<String, Object>> fetchAndFormatIssuePointNamesNoSign(String tableName, String idString, String currentLangId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        List<String> ids = Arrays.asList(idString.split(","));
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?")); // 动态创建SQL IN子句
        String sql = "SELECT IF(JSON_VALID(NAME), NAME->>'$." + currentLangId + "', NAME) as name FROM " + tableName
                + " WHERE NAME IS NOT NULL AND id IN (" + inSql + ")";
        List<Map<String, Object>> resultList = myJdbcTemplate.queryForList(sql, ids.toArray());

        return resultList;
    }

    /**
     * 从指定表中获取名称
     *
     * @param tableName     表名
     * @param id            记录的ID
     * @param currentLangId 当前语言ID
     * @return 名称字段
     */
    private static String fetchNameFromTable(String tableName, String id, String currentLangId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String sql = "select IF(JSON_VALID(NAME), NAME->>'$." + currentLangId + "', NAME) as name from " + tableName + " where NAME is not null and id = ?";
        Map<String, Object> resultMap = myJdbcTemplate.queryForMap(sql, id);
        return resultMap.get("name").toString(); // 返回名称
    }
}
