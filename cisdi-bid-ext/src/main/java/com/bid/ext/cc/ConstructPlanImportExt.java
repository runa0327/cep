package com.bid.ext.cc;

import com.bid.ext.model.CcPrjStructNode;
import com.bid.ext.model.FlFile;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.StatusE;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.DrivenInfo;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static com.bid.ext.utils.ExcelImportUtils.*;
import static com.bid.ext.utils.ImportValueUtils.getLocalDateCellValue;

public class ConstructPlanImportExt {

    /**
     * 施工计划Excel导入
     */
    public void constructPlanImportFromExcel() {
        List<String> fileIdList = ExtJarHelper.getFileIdList();
        for (String fileId : fileIdList) {
            try {
                // 1. 获取文件物理路径
                FlFile flFile = FlFile.selectById(fileId);
                String physicalLocation = flFile.getPhysicalLocation();
                File file = new File(physicalLocation);

                // 2. 校验文件类型
                String ext = flFile.getExt();
                if (!"xls".equals(ext) && !"xlsx".equals(ext)) {
                    String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.excelFormat");
                    throw new BaseException(message);
                }

                // 3. 读取Excel并构建树形结构
                List<CcPrjStructNode> nodes = readAndBuildTreeConstruct(file);

                // 4. 保存树形数据到数据库
                saveTree(nodes);

                // 5. 返回成功结果
                InvokeActResult invokeActResult = new InvokeActResult();
                invokeActResult.reFetchData = true;
                ExtJarHelper.setReturnValue(invokeActResult);

            } catch (BaseException e) {
                throw e; // 直接抛出已知异常
            } catch (Exception e) {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileUploadFailed");
                throw new BaseException(message, e);
            }
        }
    }

    /**
     * 施工计划模板excel导入
     */
    public void constructPlanTemImportFromExcel() {
        List<String> fileIdList = ExtJarHelper.getFileIdList();
        for (String fileId : fileIdList) {
            try {
                // 1. 获取文件物理路径
                FlFile flFile = FlFile.selectById(fileId);
                String physicalLocation = flFile.getPhysicalLocation();
                File file = new File(physicalLocation);

                // 2. 校验文件类型
                String ext = flFile.getExt();
                if (!"xls".equals(ext) && !"xlsx".equals(ext)) {
                    String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.excelFormat");
                    throw new BaseException(message);
                }

                // 3. 读取Excel并构建树形结构
                List<CcPrjStructNode> nodes = readAndBuildTreeConstructTem(file);

                // 4. 保存树形数据到数据库
                saveTree(nodes);

                // 5. 返回成功结果
                InvokeActResult invokeActResult = new InvokeActResult();
                invokeActResult.reFetchData = true;
                ExtJarHelper.setReturnValue(invokeActResult);

            } catch (BaseException e) {
                throw e; // 直接抛出已知异常
            } catch (Exception e) {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileUploadFailed");
                throw new BaseException(message, e);
            }
        }
    }

    /**
     * 读取Excel并构建树形结构
     */
    private List<CcPrjStructNode> readAndBuildTreeConstruct(File file) throws IOException {
        Map<String, List<DrivenInfo>> drivenInfosMap = ExtJarHelper.getDrivenInfosMap();

        //进度计划ID
        String ccConstructProgressPlanId = null;
        for (Map.Entry<String, List<DrivenInfo>> entry : drivenInfosMap.entrySet()) {
            List<DrivenInfo> drivenInfos = entry.getValue();
            Optional<String> value = drivenInfos.stream()
                    .filter(info -> "CC_CONSTRUCT_PROGRESS_PLAN_ID".equals(info.code))
                    .map(info -> info.value)
                    .findFirst();
            if (value.isPresent()) {
                ccConstructProgressPlanId = value.get();
            }
        }

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        Map<String, Object> globalVarMap = loginInfo.globalVarMap;
        String pCcPrjIds = JdbcMapUtil.getString(globalVarMap, "P_CC_PRJ_IDS");
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0); // 第一个Sheet
        Map<String, CcPrjStructNode> seqMap = new HashMap<>(); // 序号 -> 节点
        List<CcPrjStructNode> roots = new ArrayList<>();

        // 从第3行开始读取（假设前两行为标题和说明）
        for (int rowIdx = 2; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            if (row == null) continue;

            // 解析关键字段
            String seq = getCellStringValue(row.getCell(0)); // A列：序号
            String name = getCellStringValue(row.getCell(1)); // B列：名称（必填）
            String isMilestone = getCellStringValue(row.getCell(2)); // C列：是否里程碑
            String wbsChiefUserName = getCellStringValue(row.getCell(3)); // D列：进度负责人
            String adUserId = null;
            if (wbsChiefUserName != null && !wbsChiefUserName.trim().isEmpty()) {
                adUserId = getWbsChiefUserId(myJdbcTemplate, pCcPrjIds, wbsChiefUserName, adUserId);
            }

//            Object adUserId = Crud.from("CC_PRJ_MEMBER").where().eq(CcPrjMember.Cols.NAME, wbsChiefUserName).eq(CcPrjMember.Cols.IS_PRIMARY_POS, true).eq(CcPrjMember.Cols.CC_PRJ_ID, pCcPrjIds).select().specifyCols(CcPrjMember.Cols.AD_USER_ID).execForValue();

            String planFrStr = getCellStringValue(row.getCell(4));
            LocalDate planFr = planFrStr == null || planFrStr.isEmpty() ? null : getLocalDateCellValue(row.getCell(4));

            String planToStr = getCellStringValue(row.getCell(5));
            LocalDate planTo = planToStr == null || planToStr.isEmpty() ? null : getLocalDateCellValue(row.getCell(5));

            String remark = getCellStringValue(row.getCell(6)); // D列：备注


            // 校验必填字段
            if (name == null || name.trim().isEmpty()) {
//                throw new BaseException("第 " + (rowIdx + 1) + " 行错误：名称不能为空");
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.nameCannotBeEmpty", rowIdx + 1);
                throw new BaseException(message);
            }

            // 创建节点（不依赖SEQ_NO）
            CcPrjStructNode node = CcPrjStructNode.newData();
            node.setName(name);
            node.setIsMileStone("是".equals(isMilestone));
            node.setRemark(remark);
            node.setCcPrjWbsTypeId("CONSTRUCT");
            node.setCcPrjId(pCcPrjIds);
            node.setStatus(StatusE.DR.toString());
            node.setIsWbs(true);
            node.setWbsChiefUserId(adUserId);
            node.setPlanFr(planFr);
            node.setPlanTo(planTo);
            node.setCcConstructProgressPlanId(ccConstructProgressPlanId);

            // 处理父子关系
            if (seq == null || seq.isEmpty()) {
                // 根节点（PID为空）
                roots.add(node);
            } else {
                if (seq.contains(".")) {
                    // 层级序号（如1.1.1），需绑定父节点
                    String parentSeq = getParentSeq(seq);
                    CcPrjStructNode parent = seqMap.get(parentSeq);
                    if (parent == null) {
//                        throw new BaseException("第 " + (rowIdx + 1) + " 行错误：父节点 [" + parentSeq + "] 不存在");
                        String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.parentNodeNotExist", rowIdx + 1, parentSeq);
                        throw new BaseException(message);
                    }
                    node.setCcPrjStructNodePid(parent.getId()); // 后续更新PID
                    roots.add(node);
                } else {
                    // 独立序号（如1、2），视为根节点
                    roots.add(node);
                }
            }

            // 记录当前节点（用于后续子节点查找）
            seqMap.put(seq, node);
        }

        return roots;
    }

    /**
     * 读取Excel并构建树形结构
     */
    private List<CcPrjStructNode> readAndBuildTreeConstructTem(File file) throws IOException {
        Map<String, List<DrivenInfo>> drivenInfosMap = ExtJarHelper.getDrivenInfosMap();
        //模板目录ID
        String ccPrjStructNodeDirId = null;
        for (Map.Entry<String, List<DrivenInfo>> entry : drivenInfosMap.entrySet()) {
            List<DrivenInfo> drivenInfos = entry.getValue();
            Optional<String> value = drivenInfos.stream()
                    .filter(info -> "CC_PRJ_STRUCT_NODE_DIR_ID".equals(info.code))
                    .map(info -> info.value)
                    .findFirst();
            if (value.isPresent()) {
                ccPrjStructNodeDirId = value.get();
            }
        }
//        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
//        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
//        Map<String, Object> globalVarMap = loginInfo.globalVarMap;
//        String pCcPrjIds = JdbcMapUtil.getString(globalVarMap, "P_CC_PRJ_IDS");
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0); // 第一个Sheet

        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.invalidTemplate");
            throw new BaseException(message);
        }

        // 获取标题行各列的值
        String col0 = getCellStringValue(headerRow.getCell(0));
        String col1 = getCellStringValue(headerRow.getCell(1));
        String col2 = getCellStringValue(headerRow.getCell(2));
        String col3 = getCellStringValue(headerRow.getCell(3));

        // 验证列标题是否正确
        if (!"序号".equals(col0) || !"*名称".equals(col1) || !"是否里程碑".equals(col2) || !"备注".equals(col3)) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.invalidTemplate");
            throw new BaseException(message);
        }

        Map<String, CcPrjStructNode> seqMap = new HashMap<>(); // 序号 -> 节点
        List<CcPrjStructNode> roots = new ArrayList<>();

        // 从第3行开始读取（假设前两行为标题和说明）
        for (int rowIdx = 2; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            if (row == null) continue;

            // 解析关键字段
            String seq = getCellStringValue(row.getCell(0)); // A列：序号
            String name = getCellStringValue(row.getCell(1)); // B列：名称（必填）
            String isMilestone = getCellStringValue(row.getCell(2)); // C列：是否里程碑
//            String wbsChiefUserName = getCellStringValue(row.getCell(3)); // D列：进度负责人
//            String sql = "select t.AD_USER_ID from cc_prj_member t where t.name ->'$.ZH_CN' = ? and t.is_primary_pos is true and t.CC_PRJ_ID = ?";
//            Map<String, Object> adUser = myJdbcTemplate.queryForMap(sql, wbsChiefUserName, pCcPrjIds);
//            String adUserId = JdbcMapUtil.getString(adUser, "AD_USER_ID");

//            Object adUserId = Crud.from("CC_PRJ_MEMBER").where().eq(CcPrjMember.Cols.NAME, wbsChiefUserName).eq(CcPrjMember.Cols.IS_PRIMARY_POS, true).eq(CcPrjMember.Cols.CC_PRJ_ID, pCcPrjIds).select().specifyCols(CcPrjMember.Cols.AD_USER_ID).execForValue();

//            String planFrStr = getCellStringValue(row.getCell(4));
//            LocalDate planFr = planFrStr == null || planFrStr.isEmpty() ? null : getLocalDateCellValue(row.getCell(4));
//
//            String planToStr = getCellStringValue(row.getCell(5));
//            LocalDate planTo = planToStr == null || planToStr.isEmpty() ? null : getLocalDateCellValue(row.getCell(5));

            String remark = getCellStringValue(row.getCell(3)); // D列：备注


            // 校验必填字段
            if (name == null || name.trim().isEmpty()) {
//                throw new BaseException("第 " + (rowIdx + 1) + " 行错误：名称不能为空");
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.nameCannotBeEmpty", rowIdx + 1);
                throw new BaseException(message);
            }

            // 创建节点（不依赖SEQ_NO）
            CcPrjStructNode node = CcPrjStructNode.newData();
            node.setName(name);
            node.setIsMileStone("是".equals(isMilestone));
            node.setRemark(remark);
            node.setCcPrjWbsTypeId("CONSTRUCT");
//            node.setStatus(StatusE.DR.toString());
            node.setIsWbs(true);
            node.setIsTemplate(true);
            node.setCcPrjStructNodeDirId(ccPrjStructNodeDirId);
//            node.setWbsChiefUserId(adUserId);
//            node.setPlanFr(planFr);
//            node.setPlanTo(planTo);

            // 处理父子关系
            if (seq == null || seq.isEmpty()) {
                // 根节点（PID为空）
                roots.add(node);
            } else {
                if (seq.contains(".")) {
                    // 层级序号（如1.1.1），需绑定父节点
                    String parentSeq = getParentSeq(seq);
                    CcPrjStructNode parent = seqMap.get(parentSeq);
                    if (parent == null) {
//                        throw new BaseException("第 " + (rowIdx + 1) + " 行错误：父节点 [" + parentSeq + "] 不存在");
                        String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.parentNodeNotExist", rowIdx + 1, parentSeq);
                        throw new BaseException(message);
                    }
                    node.setCcPrjStructNodePid(parent.getId()); // 后续更新PID
                    roots.add(node);
                } else {
                    // 独立序号（如1、2），视为根节点
                    roots.add(node);
                }
            }

            // 记录当前节点（用于后续子节点查找）
            seqMap.put(seq, node);
        }

        return roots;
    }

}
