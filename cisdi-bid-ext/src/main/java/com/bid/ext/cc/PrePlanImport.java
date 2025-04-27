package com.bid.ext.cc;

import com.bid.ext.model.CcPrjStructNode;
import com.bid.ext.model.FlFile;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.StatusE;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.DrivenInfo;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static com.bid.ext.utils.ImportValueUtils.getLocalDateCellValue;

public class PrePlanImport {

    /**
     * 前期计划Excel导入
     */
    public void PrePlanImportFromExcel() {
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
                List<CcPrjStructNode> nodes = readAndBuildTreePre(file);

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
     * 前期计划模板excel导入
     */
    public void PrePlanTemImportFromExcel() {
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
                List<CcPrjStructNode> nodes = readAndBuildTreePreTem(file);

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
    private List<CcPrjStructNode> readAndBuildTreePre(File file) throws IOException {
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
            String sql = "select t.AD_USER_ID from cc_prj_member t where t.name ->'$.ZH_CN' = ? and t.is_primary_pos is true and t.CC_PRJ_ID = ?";
            Map<String, Object> adUser = myJdbcTemplate.queryForMap(sql, wbsChiefUserName, pCcPrjIds);
            String adUserId = JdbcMapUtil.getString(adUser, "AD_USER_ID");

//            Object adUserId = Crud.from("CC_PRJ_MEMBER").where().eq(CcPrjMember.Cols.NAME, wbsChiefUserName).eq(CcPrjMember.Cols.IS_PRIMARY_POS, true).eq(CcPrjMember.Cols.CC_PRJ_ID, pCcPrjIds).select().specifyCols(CcPrjMember.Cols.AD_USER_ID).execForValue();

            String planFrStr = getCellStringValue(row.getCell(4));
            LocalDate planFr = planFrStr == null || planFrStr.isEmpty() ? null : getLocalDateCellValue(row.getCell(4));

            String planToStr = getCellStringValue(row.getCell(5));
            LocalDate planTo = planToStr == null || planToStr.isEmpty() ? null : getLocalDateCellValue(row.getCell(5));

            String remark = getCellStringValue(row.getCell(7)); // D列：备注


            // 校验必填字段
            if (name == null || name.trim().isEmpty()) {
                throw new BaseException("第 " + (rowIdx + 1) + " 行错误：名称不能为空");
            }

            // 创建节点（不依赖SEQ_NO）
            CcPrjStructNode node = CcPrjStructNode.newData();
            node.setName(name);
            node.setIsMileStone("是".equals(isMilestone));
            node.setRemark(remark);
            node.setCcPrjWbsTypeId("PRE");
            node.setCcPrjId(pCcPrjIds);
            node.setStatus(StatusE.DR.toString());
            node.setIsWbs(true);
            node.setWbsChiefUserId(adUserId);
            node.setPlanFr(planFr);
            node.setPlanTo(planTo);

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
                        throw new BaseException("第 " + (rowIdx + 1) + " 行错误：父节点 [" + parentSeq + "] 不存在");
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
    private List<CcPrjStructNode> readAndBuildTreePreTem(File file) throws IOException {
        Map<String, List<DrivenInfo>> drivenInfosMap = ExtJarHelper.getDrivenInfosMap();
        //进度计划ID
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
                throw new BaseException("第 " + (rowIdx + 1) + " 行错误：名称不能为空");
            }

            // 创建节点（不依赖SEQ_NO）
            CcPrjStructNode node = CcPrjStructNode.newData();
            node.setName(name);
            node.setIsMileStone("是".equals(isMilestone));
            node.setRemark(remark);
            node.setCcPrjWbsTypeId("PRE");
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
                        throw new BaseException("第 " + (rowIdx + 1) + " 行错误：父节点 [" + parentSeq + "] 不存在");
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
     * 保存树形数据到数据库
     */
    private void saveTree(List<CcPrjStructNode> roots) {
        // 1. 扁平化所有节点（按层级顺序）
        List<CcPrjStructNode> allNodes = flattenTree(roots);

        // 2. 先插入所有节点（生成ID）
        allNodes.forEach(node -> {
            node.insertById();
        });

        // 3. 更新父节点ID（若需要）
        allNodes.forEach(node -> {
            String seq = node.getSeqNo() != null ? node.getSeqNo().toString() : null;
            if (seq != null && !seq.isEmpty()) {
                String parentSeq = getParentSeq(seq);
                if (parentSeq != null) {
                    // 查询父节点ID（根据SEQ_NO）
                    CcPrjStructNode parent = CcPrjStructNode.selectOneByWhere(
                            new Where().eq(CcPrjStructNode.Cols.SEQ_NO, new BigDecimal(parentSeq))
                    );
                    if (parent != null) {
                        node.setCcPrjStructNodePid(parent.getId());
                        node.updateById();
                    }
                }
            }
        });
    }


    /**
     * 获取父节点序号（如 "1.1" -> "1"）
     */
    private String getParentSeq(String seq) {
        int lastDotIndex = seq.lastIndexOf(".");
        return lastDotIndex == -1 ? null : seq.substring(0, lastDotIndex);
    }

    /**
     * 获取单元格字符串值（通用）
     */
    private String getCellStringValue(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return new DataFormatter().formatCellValue(cell); // 按原格式返回字符串
            case FORMULA:
                return cell.getCellFormula().trim();
            default:
                return null;
        }
    }

    /**
     * 将序号字符串转换为BigDecimal（确保1 -> 1，1.1 -> 1.1）
     */
    private BigDecimal parseSeqToBigDecimal(String seq) {
        if (seq == null || seq.isEmpty()) return null;
        try {
            BigDecimal value = new BigDecimal(seq);
            // 如果是整数（如1.0），转换为整数形式（1）
            if (value.scale() <= 0 || value.stripTrailingZeros().scale() <= 0) {
                return value.stripTrailingZeros();
            }
            return value;
        } catch (NumberFormatException e) {
            throw new BaseException("序号转换错误: " + seq);
        }
    }


    /**
     * 扁平化树形结构（递归）
     */
    private List<CcPrjStructNode> flattenTree(List<CcPrjStructNode> nodes) {
        List<CcPrjStructNode> result = new ArrayList<>();
        for (CcPrjStructNode node : nodes) {
            result.add(node);
            // 递归处理子节点（需根据业务补充子节点获取逻辑）
            // if (node.getChildren() != null) {
            //     result.addAll(flattenTree(node.getChildren()));
            // }
        }
        return result;
    }

    /**
     * 获取单元格的序号值（自动处理数字格式问题）
     */
    private String getSeqValue(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                // 如果是整数，返回不带小数的字符串（如1.0 -> "1"）
                double num = cell.getNumericCellValue();
                if (num == (int) num) {
                    return String.valueOf((int) num);
                } else {
                    return String.valueOf(num);
                }
            case FORMULA:
                // 处理公式计算结果
                FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                CellValue cellValue = evaluator.evaluate(cell);
                switch (cellValue.getCellType()) {
                    case NUMERIC:
                        return cellValue.formatAsString(); // 根据格式返回
                    case STRING:
                        return cellValue.getStringValue();
                    default:
                        return null;
                }
            default:
                return null;
        }
    }


}
