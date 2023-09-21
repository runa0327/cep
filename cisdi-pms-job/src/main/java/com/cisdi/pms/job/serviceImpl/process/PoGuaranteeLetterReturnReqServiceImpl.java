package com.cisdi.pms.job.serviceImpl.process;

import com.cisdi.pms.job.domain.process.PoGuaranteeLetterReturnReq;
import com.cisdi.pms.job.mapper.base.HrDeptMapper;
import com.cisdi.pms.job.mapper.process.PoGuaranteeLetterReturnReqMapper;
import com.cisdi.pms.job.mapper.project.PmPrjMapper;
import com.cisdi.pms.job.service.process.PoGuaranteeLetterReturnReqService;
import com.cisdi.pms.job.utils.CisdiUtils;
import com.cisdi.pms.job.utils.FileUtils;
import com.cisdi.pms.job.utils.PoiExcelUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;

@Service
public class PoGuaranteeLetterReturnReqServiceImpl implements PoGuaranteeLetterReturnReqService {

    @Resource
    private PoGuaranteeLetterReturnReqMapper poGuaranteeLetterReturnReqMapper;

    @Resource
    private PmPrjMapper pmPrjMapper;

    @Resource
    private HrDeptMapper hrDeptMapper;

    @Resource
    private CisdiUtils cisdiUtils;

    /**
     * 查询符合条件的所有数据
     *
     * @param poGuaranteeLetterReturnReq poGuaranteeLetterReturnReq实体
     * @return 查询结果
     */
    @Override
    public List<PoGuaranteeLetterReturnReq> getAllList(PoGuaranteeLetterReturnReq poGuaranteeLetterReturnReq) {
        List<PoGuaranteeLetterReturnReq> resList = new ArrayList<>();
        String projectId = poGuaranteeLetterReturnReq.getProjectId();
        if (StringUtils.hasText(projectId)){
            String projectName = pmPrjMapper.getProjectNameById(projectId);
            poGuaranteeLetterReturnReq.setProjectName(projectName);
        }
        String deptId = poGuaranteeLetterReturnReq.getDeptId();
        if (StringUtils.hasText(deptId)){
            String deptIds = hrDeptMapper.getDeptIdByName(deptId).replace(",","','");
            poGuaranteeLetterReturnReq.setDeptId(deptIds);
        }
        // 查询系统项目
        List<PoGuaranteeLetterReturnReq> list1 = poGuaranteeLetterReturnReqMapper.getSysMesList(poGuaranteeLetterReturnReq);
        // 查询非系统项目
        List<PoGuaranteeLetterReturnReq> list2 = poGuaranteeLetterReturnReqMapper.getNotSysMesList(poGuaranteeLetterReturnReq);
        if (!CollectionUtils.isEmpty(list1)){
            resList.addAll(list1);
        }
        if (!CollectionUtils.isEmpty(list2)){
            resList.addAll(list2);
        }
        if (!CollectionUtils.isEmpty(resList)){
            for (PoGuaranteeLetterReturnReq tmp : resList) {

                // 保函类型-其他保函处理 0099902212142023018
                String guaranteeTypeLetterId = tmp.getGuaranteeLetterTypeId();
                if ("0099902212142023018".equals(guaranteeTypeLetterId)){
                    tmp.setGuaranteeLetterTypeName(tmp.getOtherGuaranteeLetterName());
                }

                // 费用类型-其他费用类型处理 0100058954591131275
                String costId = tmp.getGuaranteeCostTypeId();
                if ("0100058954591131275".equals(costId)){
                    tmp.setGuaranteeCostTypeName(tmp.getOtherGuaranteeCostName());
                }
            }
        }
        return resList;
    }

    /**
     * 保函退还导出
     *
     * @param list     数据详细
     * @param response 响应
     * @param title 文件名
     */
    @Override
    public void exportListMsg(List<PoGuaranteeLetterReturnReq> list, HttpServletResponse response, String title) {
        String filePath = cisdiUtils.getDownLoadPath();
        List<String> header = getListHeader();
        OutputStream outputStream = null;
        Workbook workbook = new XSSFWorkbook();
        try {
            Sheet sheet = workbook.createSheet(title);
            sheet.setDefaultColumnWidth(30);
            Row row = sheet.createRow(0);
            CellStyle cs = PoiExcelUtils.getTableHeaderStyle(workbook);

            for (int i = 0; i < header.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header.get(i));
                cell.setCellStyle(cs);
            }
            if (!CollectionUtils.isEmpty(list)){
                CellStyle cellStyle = PoiExcelUtils.getTableCellStyle(workbook);
                listCellValue(sheet,list,cellStyle);
            }

            FileUtils.downLoadFile(filePath,title,workbook,outputStream,response);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 保函台账导出-值赋值
     * @param sheet 页
     * @param list 数据详细
     * @param style 样式
     */
    private void listCellValue(Sheet sheet, List<PoGuaranteeLetterReturnReq> list,CellStyle style) {
        for (int i = 0; i < list.size(); i++) {
            Row row = sheet.createRow(i+1);

            // 序号
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(i+1);
            cell1.setCellStyle(style);

            // 项目名称
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(list.get(i).getProjectName());
            cell2.setCellStyle(style);

            // 保函名称
            Cell cell3 = row.createCell(2);
            cell3.setCellStyle(style);
            cell3.setCellValue(list.get(i).getGuaranteeLetterTypeName());

            // 费用类型
            Cell cell4 = row.createCell(3);
            cell4.setCellStyle(style);
            cell4.setCellValue(list.get(i).getGuaranteeCostTypeName());

            // 担保银行/保险公司
            Cell cell5 = row.createCell(4);
            cell5.setCellStyle(style);
            cell5.setCellValue(list.get(i).getGuaranteeMechanism());

            // 保函编号/保单号
            Cell cell6 = row.createCell(5);
            cell6.setCellStyle(style);
            cell6.setCellValue(list.get(i).getGuaranteeCode());

            // 担保金额
            Cell cell7 = row.createCell(6);
            BigDecimal amt = list.get(i).getGuaranteeAmt();
            if (amt == null){
                cell7.setCellValue("-");
            } else {
                cell7.setCellValue(amt.stripTrailingZeros().toPlainString());
            }
            cell7.setCellStyle(style);

            // 签订日期
            Cell cell8 = row.createCell(7);
            cell8.setCellValue(list.get(i).getGuaranteeStartDate());
            cell8.setCellStyle(style);

            // 担保期限
            Cell cell9 = row.createCell(8);
            cell9.setCellValue(list.get(i).getGuaranteeEndDate());
            cell9.setCellStyle(style);

            // 备注
            Cell cell10 = row.createCell(9);
            cell10.setCellValue(list.get(i).getRemarkLongOne());
            cell10.setCellStyle(style);

            // 受益人
            Cell cell11 = row.createCell(10);
            cell11.setCellValue(list.get(i).getAuthor());
            cell11.setCellStyle(style);

            // 创建人
            Cell cell12 = row.createCell(11);
            cell12.setCellValue(list.get(i).getCreateUserName());
            cell12.setCellStyle(style);

            // 所属部门
            Cell cell13 = row.createCell(12);
            cell13.setCellValue(list.get(i).getDeptName());
            cell13.setCellStyle(style);

            // 创建日期
            Cell cell14 = row.createCell(13);
            cell14.setCellValue(list.get(i).getCreateDate());
            cell14.setCellStyle(style);

            // 合同签订公司
            Cell cell15 = row.createCell(14);
            cell15.setCellValue(list.get(i).getCompanyName());
            cell15.setCellStyle(style);
        }
    }

    /**
     * 保函全字段导出
     *
     * @param list     数据详细
     * @param response 响应
     * @param title 文件名
     */
    @Override
    public void exportAllMsg(List<PoGuaranteeLetterReturnReq> list, HttpServletResponse response, String title) {
        String filePath = cisdiUtils.getDownLoadPath();
        List<String> header = getAllListHeader();
        List<String> header2 = getAllMsgHeader();
        OutputStream outputStream = null;
        Workbook workbook = new XSSFWorkbook();
        try {
            Sheet sheet = workbook.createSheet(title);
            sheet.setDefaultColumnWidth(30);

            // 合并行列
            CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 5);
            CellRangeAddress region2 = new CellRangeAddress(0, 0, 6, 8);
            CellRangeAddress region3 = new CellRangeAddress(0, 0, 9, 22);
            CellRangeAddress region5 = new CellRangeAddress(0, 0, 24, 25);
            sheet.addMergedRegion(region1);
            sheet.addMergedRegion(region2);
            sheet.addMergedRegion(region3);
            sheet.addMergedRegion(region5);

            Row row = sheet.createRow(0);
            CellStyle cs = PoiExcelUtils.getTableHeaderStyle(workbook);
            headerCellValue(row,cs);
            Row row2 = sheet.createRow(1);
            for (int i = 0; i < header2.size(); i++) {
                Cell cell = row2.createCell(i);
                cell.setCellValue(header2.get(i));
                cell.setCellStyle(cs);
            }
            if (!CollectionUtils.isEmpty(list)){
                CellStyle cellStyle = PoiExcelUtils.getTableCellStyle(workbook);
                allMsgCellValue(sheet,list,cellStyle);
            }

            FileUtils.downLoadFile(filePath,title,workbook,outputStream,response);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 保函全字段导出-合并表头
     * @param row 行
     * @param style 样式
     */
    private void headerCellValue(Row row, CellStyle style) {
        Cell cell1 = row.createCell(0);
        cell1.setCellStyle(style);
        cell1.setCellValue("单据信息");

        Cell cell2 = row.createCell(6);
        cell2.setCellValue("项目信息");
        cell2.setCellStyle(style);

        Cell cell3 = row.createCell(9);
        cell3.setCellStyle(style);
        cell3.setCellValue("保函内容");

        Cell cell4 = row.createCell(23);
        cell4.setCellValue("经办部门负责人意见");
        cell4.setCellStyle(style);

        Cell cell5 = row.createCell(24);
        cell5.setCellValue("财务金融部会计意见");
        cell5.setCellStyle(style);

        Cell cell6 = row.createCell(26);
        cell6.setCellValue("财务金融部负责人意见");
        cell6.setCellStyle(style);

        Cell cell7 = row.createCell(27);
        cell7.setCellValue("财务分管领导意见");
        cell7.setCellStyle(style);
    }

    /**
     * 保函全字段导出
     * @param sheet sheet
     * @param list list
     * @param style 样式
     */
    private void allMsgCellValue(Sheet sheet, List<PoGuaranteeLetterReturnReq> list, CellStyle style) {
        for (int i = 0; i < list.size(); i++) {
            Row row = sheet.createRow(i+2);

            // 序号
            Cell cell = row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue(i+1);

            // 流程标题
            Cell cell1 = row.createCell(1);
            cell1.setCellStyle(style);
            cell1.setCellValue(list.get(i).getProcessInstanceName());

            // 创建人
            Cell cell2 = row.createCell(2);
            cell2.setCellValue(list.get(i).getCreateUserName());
            cell2.setCellStyle(style);

            // 所属部门
            Cell cell3 = row.createCell(3);
            cell3.setCellValue(list.get(i).getDeptName());
            cell3.setCellStyle(style);

            // 创建日期
            Cell cell4 = row.createCell(4);
            cell4.setCellValue(list.get(i).getCreateDate());
            cell4.setCellStyle(style);

            // 审批状态
            Cell cell5 = row.createCell(5);
            cell5.setCellValue(list.get(i).getStatus());
            cell5.setCellStyle(style);

            // 合同签订公司
            Cell cell6 = row.createCell(6);
            cell6.setCellValue(list.get(i).getCompanyName());
            cell6.setCellStyle(style);

            // 项目来源
            Cell cell7 = row.createCell(7);
            cell7.setCellValue(list.get(i).getProjectSource());
            cell7.setCellStyle(style);

            // 项目名称
            Cell cell8 = row.createCell(8);
            cell8.setCellValue(list.get(i).getProjectName());
            cell8.setCellStyle(style);

            // 保函申请人
            Cell cell9 = row.createCell(9);
            cell9.setCellValue(list.get(i).getApplicant());
            cell9.setCellStyle(style);

            // 受益人
            Cell cell10 = row.createCell(10);
            cell10.setCellValue(list.get(i).getAuthor());
            cell10.setCellStyle(style);

            // 保函名称
            Cell cell11 = row.createCell(11);
            cell11.setCellStyle(style);
            cell11.setCellValue(list.get(i).getGuaranteeLetterTypeName());

            // 费用类型
            Cell cell12 = row.createCell(12);
            cell12.setCellStyle(style);
            cell12.setCellValue(list.get(i).getGuaranteeCostTypeName());

            // 保函开立机构
            Cell cell13 = row.createCell(13);
            cell13.setCellStyle(style);
            cell13.setCellValue(list.get(i).getGuaranteeMechanism());

            // 保函编号/保单号
            Cell cell14 = row.createCell(14);
            cell14.setCellStyle(style);
            cell14.setCellValue(list.get(i).getGuaranteeCode());

            // 担保金额
            Cell cell15 = row.createCell(15);
            BigDecimal amt = list.get(i).getGuaranteeAmt();
            if (amt == null){
                cell15.setCellValue("-");
            } else {
                cell15.setCellValue(amt.stripTrailingZeros().toPlainString());
            }
            cell15.setCellStyle(style);

            // 人民币大写
            Cell cell16 = row.createCell(16);
            cell16.setCellValue(list.get(i).getGuaranteeAmtChina());
            cell16.setCellStyle(style);

            // 签订日期
            Cell cell17 = row.createCell(17);
            cell17.setCellValue(list.get(i).getGuaranteeStartDate());
            cell17.setCellStyle(style);

            // 到期类型
            Cell cell18 = row.createCell(18);
            cell18.setCellValue(list.get(i).getGuaranteeEndTypeName());
            cell18.setCellStyle(style);

            // 担保期限
            Cell cell19 = row.createCell(19);
            cell19.setCellValue(list.get(i).getGuaranteeEndDate());
            cell19.setCellStyle(style);

            // 到期说明
            Cell cell20 = row.createCell(20);
            cell20.setCellValue(list.get(i).getGuaranteeEndRemark());
            cell20.setCellStyle(style);

            // 退还原因
            Cell cell21 = row.createCell(21);
            cell21.setCellValue(list.get(i).getReason());
            cell21.setCellStyle(style);

            // 备注
            Cell cell22 = row.createCell(22);
            cell22.setCellValue(list.get(i).getRemarkLongOne());
            cell22.setCellStyle(style);

            // 经办部门意见
            Cell cell23 = row.createCell(23);
            cell23.setCellValue(list.get(i).getCommentOne());
            cell23.setCellStyle(style);

            // 财务金融部会计意见
            Cell cell24 = row.createCell(24);
            cell24.setCellValue(list.get(i).getCommentTwo());
            cell24.setCellStyle(style);

            // 财务金融部会计意见
            Cell cell25 = row.createCell(25);
            cell25.setCellValue(list.get(i).getCommentThree());
            cell25.setCellStyle(style);

            // 财务金融部负责人意见
            Cell cell26 = row.createCell(26);
            cell26.setCellValue(list.get(i).getCommentFour());
            cell26.setCellStyle(style);

            // 财务分管领导意见
            Cell cell27 = row.createCell(27);
            cell27.setCellValue(list.get(i).getCommentFive());
            cell27.setCellStyle(style);

        }
    }

    /**
     * 保函导出表头
     * @return 表头数据
     */
    private List<String> getListHeader() {
        List<String> list = new ArrayList<>();
        list.add("序号");
        list.add("项目名称");
        list.add("保函名称");
        list.add("费用类型");
        list.add("担保银行/保险公司");
        list.add("保函编号/保单号");
        list.add("担保金额(元)");
        list.add("签订日期");
        list.add("担保期限");
        list.add("备注");
        list.add("受益人");
        list.add("创建人");
        list.add("所属部门");
        list.add("创建日期");
        list.add("合同签订公司");
        return list;
    }

    /**
     * 保函全字段导出-标题分组信息
     * @return 分组集合
     */
    private List<String> getAllListHeader() {
        List<String> list = new ArrayList<>();
        list.add("单据信息");
        list.add("项目信息");
        list.add("保函内容");
        list.add("经办部门负责人意见");
        list.add("财务金融部会计意见");
        list.add("财务金融部负责人意见");
        list.add("财务分管领导意见");
        return list;
    }

    /**
     * 保函全字段导出-标题详细信息
     * @return 标题集合
     */
    private List<String> getAllMsgHeader() {
        List<String> list = new ArrayList<>();
        list.add("序号");
        list.add("流程标题");
        list.add("创建人");
        list.add("所属部门");
        list.add("创建时间");
        list.add("审批状态");
        list.add("合同签订公司");
        list.add("项目来源");
        list.add("项目名称");
        list.add("保函申请人");
        list.add("保函受益人");
        list.add("保函类型");
        list.add("费用类型");
        list.add("保函开立机构");
        list.add("保函编号(保单号)");
        list.add("保函担保金额(元)");
        list.add("人民币(大写)");
        list.add("保函开立日期");
        list.add("保函到期类型");
        list.add("保函到期日");
        list.add("到期说明");
        list.add("申请保函退还原因");
        list.add("备注");
        list.add("经办部门意见");
        list.add("财务金融部会计意见");
        list.add("财务金融部会计意见");
        list.add("财务金融部负责人意见");
        list.add("财务分管领导意见");
        return list;
    }
}
