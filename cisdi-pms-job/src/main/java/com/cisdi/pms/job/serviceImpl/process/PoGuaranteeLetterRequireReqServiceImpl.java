package com.cisdi.pms.job.serviceImpl.process;

import com.cisdi.pms.job.domain.process.PoGuaranteeLetterRequireReq;
import com.cisdi.pms.job.mapper.base.PmExpTypeMapper;
import com.cisdi.pms.job.mapper.process.PoGuaranteeLetterRequireReqMapper;
import com.cisdi.pms.job.mapper.process.PoOrderReqMapper;
import com.cisdi.pms.job.mapper.project.PmPrjMapper;
import com.cisdi.pms.job.service.process.PoGuaranteeLetterRequireReqService;
import com.cisdi.pms.job.utils.CisdiUtils;
import com.cisdi.pms.job.utils.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;

@Service
public class PoGuaranteeLetterRequireReqServiceImpl implements PoGuaranteeLetterRequireReqService {

    @Resource
    private PoGuaranteeLetterRequireReqMapper poGuaranteeLetterRequireReqMapper;

    @Resource
    private PmPrjMapper pmPrjMapper;

    @Resource
    private PoOrderReqMapper poOrderReqMapper;

    @Resource
    private PmExpTypeMapper pmExpTypeMapper;

    @Resource
    private CisdiUtils cisdiUtils;

    /**
     * 查询条件内所有信息
     * @param poGuaranteeLetterRequireReq 新增保函实体
     * @return 查询结果
     */
    @Override
    public List<PoGuaranteeLetterRequireReq> selectAllMess(PoGuaranteeLetterRequireReq poGuaranteeLetterRequireReq) {
        String projectId = poGuaranteeLetterRequireReq.getProjectNameWr();
        String projectName = pmPrjMapper.getProjectNameById(projectId);
        poGuaranteeLetterRequireReq.setProjectId(projectId);
        poGuaranteeLetterRequireReq.setProjectName(projectName);
        // 查询系统项目
        List<PoGuaranteeLetterRequireReq> list1 = poGuaranteeLetterRequireReqMapper.selectAllMess(poGuaranteeLetterRequireReq);
        // 查询非系统项目
        List<PoGuaranteeLetterRequireReq> list2 = poGuaranteeLetterRequireReqMapper.selectNotSysAllMessage(poGuaranteeLetterRequireReq);

        List<PoGuaranteeLetterRequireReq> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list1)){
            list.addAll(list1);
        }
        if (!CollectionUtils.isEmpty(list2)){
            list.addAll(list2);
        }
        if (!CollectionUtils.isEmpty(list)){
            for (PoGuaranteeLetterRequireReq tmp : list) {

                // 项目名称处理
                String projectNameWr = tmp.getProjectName();
                if (!StringUtils.hasText(projectNameWr)){
                    String projectIds = tmp.getProjectId();
                    if (StringUtils.hasText(projectIds)){
                        String[] arr = projectIds.split(",");
                        projectNameWr = pmPrjMapper.getProjectNameByIdArr(arr);
                        tmp.setProjectName(projectNameWr);
                    }
                }

                // 合同名称处理
                String contractNameWr = tmp.getContractName();
                if (!StringUtils.hasText(contractNameWr)){
                    String contractId = tmp.getContractId();
                    if (StringUtils.hasText(contractId)){
                        String[] arr = contractId.split(",");
                        contractNameWr = poOrderReqMapper.getContractNameByIdArr(arr);
                        tmp.setContractName(contractNameWr);
                    }
                }

                // 费用名称
                String feeName = tmp.getFeeTypeName();
                if (!StringUtils.hasText(feeName)){
                    String feeId = tmp.getPmExpTypeIds();
                    if (StringUtils.hasText(feeId)){
                        String[] arr = feeId.split(",");
                        feeName = pmExpTypeMapper.getFeeNameByIdArr(arr);
                        tmp.setFeeTypeName(feeName);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 新增保函台账-列表导出
     *
     * @param list  数据名称
     * @param title 文件名称
     * @param response response
     */
    @Override
    public void exportList(List<PoGuaranteeLetterRequireReq> list, String title, HttpServletResponse response) {
        String filePath = cisdiUtils.getDownLoadPath();
        Workbook workbook = new XSSFWorkbook();
        OutputStream outputStream;
        try {
            Sheet sheet = workbook.createSheet(title);
            sheet.setDefaultColumnWidth(30);
            Row topRow = sheet.createRow(0);
            CellStyle cs = workbook.createCellStyle();
            // 设置固定表头背景色（灰色）
            cs.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            // 设置填充样式
            cs.setFillPattern(SOLID_FOREGROUND);
            // 水平居中
            cs.setAlignment(HorizontalAlignment.CENTER);
            // 垂直居中
            cs.setVerticalAlignment(VerticalAlignment.CENTER);
            // 固定表头信息
            List<String> headerList = getHeaderList();
            for (int i = 0; i < headerList.size(); i++) {
                Cell cell = topRow.createCell(i);
                cell.setCellValue(headerList.get(i));
                cell.setCellStyle(cs);
            }

            CellStyle content = workbook.createCellStyle();
            content.setAlignment(HorizontalAlignment.CENTER);
            content.setVerticalAlignment(VerticalAlignment.CENTER);


            for (int i = 0; i < list.size(); i++) {
                Row row = sheet.createRow(i+1);
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(i+1);
                cell0.setCellStyle(content);

                // 项目名称
                Cell cell1 = row.createCell(1);
                cell1.setCellStyle(content);
                cell1.setCellValue(list.get(i).getProjectName());

                // 保函类型名称
                Cell cell2 = row.createCell(2);
                cell2.setCellStyle(content);
                cell2.setCellValue(list.get(i).getGuaranteeLetterTypeName());

                // 费用类型名称
                Cell cell3 = row.createCell(3);
                cell3.setCellStyle(content);
                cell3.setCellValue(list.get(i).getFeeTypeName());

                // 供应商完整名称
                Cell cell4 = row.createCell(4);
                cell4.setCellStyle(content);
                cell4.setCellValue(list.get(i).getSupplier());

                // 担保银行
                Cell cell5 = row.createCell(5);
                cell5.setCellStyle(content);
                cell5.setCellValue(list.get(i).getGuaranteeMechanism());

                // 担保金额(元)
                Cell cell6 = row.createCell(6);
                cell6.setCellStyle(content);
                cell6.setCellValue(list.get(i).getGuaranteeAmt().doubleValue());

                // 签订日期
                Cell cell7 = row.createCell(7);
                cell7.setCellStyle(content);
                cell7.setCellValue(list.get(i).getGuaranteeStartDate());

                // 担保期限
                Cell cell8 = row.createCell(8);
                cell8.setCellStyle(content);
                cell8.setCellValue(list.get(i).getGuaranteeEndDate());

                // 受益人
                Cell cell9 = row.createCell(9);
                cell9.setCellStyle(content);
                cell9.setCellValue(list.get(i).getBeneficiary());

                // 创建人
                Cell cell10 = row.createCell(10);
                cell10.setCellStyle(content);
                cell10.setCellValue(list.get(i).getProcessInstanceCreateBy());

                // 部门
                Cell cell11 = row.createCell(11);
                cell11.setCellStyle(content);
                cell11.setCellValue(list.get(i).getDeptName());

                // 创建日期
                Cell cell12 = row.createCell(12);
                cell12.setCellStyle(content);
                cell12.setCellValue(list.get(i).getProcessInstanceCreateDate());

                // 合同签订公司
                Cell cell13 = row.createCell(13);
                cell13.setCellStyle(content);
                cell13.setCellValue(list.get(i).getCustomerUnitName());
            }

            String realFileName = "新增保函台账.xls";
            String path = filePath + title + ".xls";
            outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            File file = new File(path);
            if (file.exists()){
                file.delete();
            }
            FileUtils.writeBytes(path, response.getOutputStream());

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取导出台账表头信息
     * @return 表头信息
     */
    private List<String> getHeaderList() {
        List<String> list = new ArrayList<>();
        list.add("序号");
        list.add("项目名称");
        list.add("保函名称");
        list.add("费用类型");
        list.add("供应商完整名称");
        list.add("担保银行/保险公司");
        list.add("担保金额(元)");
        list.add("签订日期");
        list.add("担保期限");
        list.add("受益人");
        list.add("创建人");
        list.add("部门");
        list.add("创建日期");
        list.add("合同签订公司");
        return list;
    }
}
