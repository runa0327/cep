package com.bid.ext.cc;

import com.bid.ext.model.CcPrjStructNode;
import com.bid.ext.model.FlFile;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.StatusE;
import com.qygly.shared.interaction.InvokeActResult;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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
                List<CcPrjStructNode> nodes = readAndBuildTree(file);

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
    private List<CcPrjStructNode> readAndBuildTree(File file) throws IOException {
        // 1. 读取Excel数据
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0); // 第一个Sheet

        // 2. 存储节点和序号映射
        Map<String, CcPrjStructNode> seqMap = new HashMap<>();
        List<CcPrjStructNode> roots = new ArrayList<>();

        // 3. 遍历行（从第3行开始，假设第1-2行为标题和说明）
        for (int rowIdx = 2; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            if (row == null) continue;

            // 4. 解析关键字段
            String seq = getCellStringValue(row.getCell(0)); // A列：序号
            String name = getCellStringValue(row.getCell(1)); // B列：名称（必填）
            String isMilestone = getCellStringValue(row.getCell(2)); // C列：是否里程碑
            String remark = getCellStringValue(row.getCell(3)); // D列：备注

            // 5. 数据校验
            if (name == null || name.trim().isEmpty()) {
                throw new BaseException("第 " + (rowIdx + 1) + " 行错误：名称不能为空");
            }
            if (seq != null && !seq.isEmpty() && !seq.matches("^(\\d+\\.)*\\d+$")) {
                throw new BaseException("第 " + (rowIdx + 1) + " 行错误：序号格式错误");
            }

            // 6. 创建节点
            CcPrjStructNode node = CcPrjStructNode.newData();
            node.setName(name);
            node.setIsMileStone("是".equals(isMilestone));
            node.setRemark(remark);
            node.setSeqNo(parseSeqToBigDecimal(seq)); // 存储序号（BigDecimal）
            node.setIsTemplate(false);
            node.setIsWbs(true);
            node.setCcPrjWbsTypeId("PRE");
            node.setStatus(StatusE.DR.toString());

            // 7. 处理父子关系
            if (seq == null || seq.isEmpty()) {
                // 根节点
                roots.add(node);
            } else {
                // 查找父节点
                String parentSeq = getParentSeq(seq);
                CcPrjStructNode parent = seqMap.get(parentSeq);
                if (parent == null) {
                    throw new BaseException("第 " + (rowIdx + 1) + " 行错误：父节点不存在 [" + parentSeq + "]");
                }
                node.setCcPrjStructNodePid(parent.getId());
            }

            // 8. 记录当前节点
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
     * 解析父节点序号（如 "1.1.1" -> "1.1"）
     */
    private String getParentSeq(String seq) {
        if (seq == null || seq.isEmpty()) return null;
        String[] levels = seq.split("\\.");
        return levels.length == 1 ? null :
                String.join(".", Arrays.copyOf(levels, levels.length - 1));
    }

    /**
     * 将序号转换为BigDecimal（如 "1.1" -> 1.1）
     */
    private BigDecimal parseSeqToBigDecimal(String seq) {
        if (seq == null || seq.isEmpty()) return null;
        try {
            return new BigDecimal(seq);
        } catch (NumberFormatException e) {
            throw new BaseException("序号转换错误: " + seq);
        }
    }

    /**
     * 获取单元格字符串值
     */
    private String getCellStringValue(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue()).trim();
            case FORMULA:
                return cell.getCellFormula().trim();
            default:
                return null;
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


}
