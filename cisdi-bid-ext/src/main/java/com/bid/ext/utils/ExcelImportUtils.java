package com.bid.ext.utils;

import com.bid.ext.model.CcDocDir;
import com.bid.ext.model.CcPrj;
import com.bid.ext.model.CcPrjStructNode;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelImportUtils {

    /**
     * 保存树形数据到数据库
     */
    public static void saveTree(List<CcPrjStructNode> roots) {
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

    public static void saveDirTree(List<CcDocDir> roots) {
        // 1. 扁平化所有节点（按层级顺序）
        List<CcDocDir> allNodes = flattenDirTree(roots);

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
                    CcDocDir parent = CcDocDir.selectOneByWhere(
                            new Where().eq(CcDocDir.Cols.SEQ_NO, new BigDecimal(parentSeq))
                    );
                    if (parent != null) {
                        node.setCcDocDirPid(parent.getId());
                        node.updateById();
                    }
                }
            }
        });
    }


    /**
     * 获取父节点序号（如 "1.1" -> "1"）
     */
    public static String getParentSeq(String seq) {
        int lastDotIndex = seq.lastIndexOf(".");
        return lastDotIndex == -1 ? null : seq.substring(0, lastDotIndex);
    }

    /**
     * 获取单元格字符串值（通用）
     */
    public static String getCellStringValue(Cell cell) {
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
    public static BigDecimal parseSeqToBigDecimal(String seq) {
        if (seq == null || seq.isEmpty()) return null;
        try {
            BigDecimal value = new BigDecimal(seq);
            // 如果是整数（如1.0），转换为整数形式（1）
            if (value.scale() <= 0 || value.stripTrailingZeros().scale() <= 0) {
                return value.stripTrailingZeros();
            }
            return value;
        } catch (NumberFormatException e) {
//            throw new BaseException("序号转换错误: " + seq);
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.seqConversionError", seq);
            throw new BaseException(message);
        }
    }


    /**
     * 扁平化树形结构（递归）
     */
    public static List<CcPrjStructNode> flattenTree(List<CcPrjStructNode> nodes) {
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

    public static List<CcDocDir> flattenDirTree(List<CcDocDir> nodes) {
        List<CcDocDir> result = new ArrayList<>();
        for (CcDocDir node : nodes) {
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
    public static String getSeqValue(Cell cell) {
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


    public static String getWbsChiefUserId(MyJdbcTemplate myJdbcTemplate, String pCcPrjIds, String wbsChiefUserName, String adUserId) {
        String sql = "select t.AD_USER_ID from cc_prj_member t where t.name ->'$.ZH_CN' = ? and t.is_primary_pos is true and t.CC_PRJ_ID = ?";
        List<Map<String, Object>> adUserList = myJdbcTemplate.queryForList(sql, wbsChiefUserName, pCcPrjIds);
        CcPrj ccPrj = CcPrj.selectById(pCcPrjIds);
        String ccPrjName = I18nUtil.tryGetInCurrentLang(ccPrj.getName());
        if (!adUserList.isEmpty()) {
            for (Map<String, Object> adUser : adUserList) {
                adUserId = JdbcMapUtil.getString(adUser, "AD_USER_ID");
            }
        } else {
//            throw new BaseException("导入失败，【" + I18nUtil.tryGetInCurrentLang(wbsChiefUserName) + "】未加入到该项目中");
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.seqConversionError", wbsChiefUserName, ccPrjName);
            throw new BaseException(message);
        }
        return adUserId;
    }
}
