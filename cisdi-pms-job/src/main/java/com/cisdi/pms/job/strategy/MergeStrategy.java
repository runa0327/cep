package com.cisdi.pms.job.strategy;

/**
 * @author dlt
 * @date 2023/3/21 周二
 */

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 合并单元格处理类
 */
public class MergeStrategy extends AbstractMergeStrategy {

    /**
     * 合并开始行
     */
    private Integer startRow = 0;
    private Integer startCol = 0;
    /**
     * list表格所有的合并列集合
     */
    private List<CellRangeAddress> cellRangeAddressList = null;

    public MergeStrategy() {
    }

    public MergeStrategy(int startRow, int startCol, List<CellRangeAddress> cellRangeAddressList) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.cellRangeAddressList = cellRangeAddressList;
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {

        //在这里判断从哪一行开始调用合并的方法
        if (cell.getRowIndex() >= this.startRow && cell.getColumnIndex() >= startCol) {
            if (relativeRowIndex == null || relativeRowIndex == 0) {
                return;
            }
            List<CellRangeAddress> mergeList = this.cellRangeAddressList;
            if (CollectionUtils.isEmpty(mergeList)) {
                return;
            }
            for (CellRangeAddress cellRangeAddress : mergeList) {
                if (cell.getRowIndex() == cellRangeAddress.getFirstRow() && cell.getColumnIndex() == cellRangeAddress.getFirstColumn()) {
                    doMerge(sheet, cell, head, relativeRowIndex, cellRangeAddress);
                }
            }
        }
    }

    /**
     * 合并单元格
     *
     * @param sheet
     * @param cell
     * @param head
     * @param relativeRowIndex
     */
    protected void doMerge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex, CellRangeAddress cellRangeAddress) {

        sheet = cell.getSheet();
        sheet.addMergedRegion(cellRangeAddress);
        return;
    }


}
