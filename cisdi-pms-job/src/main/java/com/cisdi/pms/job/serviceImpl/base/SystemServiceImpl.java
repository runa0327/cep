package com.cisdi.pms.job.serviceImpl.base;

import com.cisdi.pms.job.domain.base.AdUser;
import com.cisdi.pms.job.domain.process.PoOrderReq;
import com.cisdi.pms.job.domain.process.WfProcessInstance;
import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeekly;
import com.cisdi.pms.job.mapper.base.AdUserMapper;
import com.cisdi.pms.job.mapper.process.PoOrderReqMapper;
import com.cisdi.pms.job.mapper.process.WfProcessInstanceMapper;
import com.cisdi.pms.job.mapper.project.PmPrjMapper;
import com.cisdi.pms.job.mapper.weeklyReport.PmProgressWeeklyMapper;
import com.cisdi.pms.job.service.base.SystemService;
import com.cisdi.pms.job.utils.CisdiUtils;
import com.cisdi.pms.job.utils.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    private PmProgressWeeklyMapper pmProgressWeeklyMapper;

    @Resource
    private AdUserMapper adUserMapper;

    @Resource
    private WfProcessInstanceMapper wfProcessInstanceMapper;

    @Resource
    private PmPrjMapper pmPrjMapper;

    @Resource
    private PoOrderReqMapper poOrderReqMapper;

    @Resource
    private CisdiUtils cisdiUtils;

    /**
     * 根据周信息查询系统使用情况
     *
     * @param weekId 周id
     * @return 周内系统使用情况
     */
    @Override
    public Map<String, Object> querySystemUsage(String weekId) {
        Map<String,Object> map = new HashMap<>();
        PmProgressWeekly pmProgressWeekly = pmProgressWeeklyMapper.queryDataById(weekId);
        String startDate = pmProgressWeekly.getStartDate();
        String endDate = pmProgressWeekly.getEndDate();

        int allUserNum = adUserMapper.queryAllUser(); // 查询系统所有人员
        int weekUserLoginNum = adUserMapper.queryWeekUserLoginNums(startDate,endDate); // 查询时间范围内登录系统的用户数
        int loginNums = adUserMapper.queryLoginNums(); // 查询系统上线以来所有登录次数 2022-11-22上线
        int allProcessInstanceNums = wfProcessInstanceMapper.queryAllInstanceNums(); // 查询上线以来所有发起的流程
        List<WfProcessInstance> weekProcessInstanceList = wfProcessInstanceMapper.queryTimeFrameNewInstanceNums(startDate,endDate); // 查询时间范围内新增流程详情
        int needWriteNums = pmProgressWeeklyMapper.queryWeekNeedWritePrjNums(weekId); // 获取周期范围内应填报周报数量
        int writeNums = pmProgressWeeklyMapper.queryWeekWritePrjNums(weekId); // 获取周期范围内实际填报周报数量

        List<String> chargeUser = adUserMapper.queryChargeUser(); // 获取系统分管领导
        List<AdUser> chargeUserLogin = adUserMapper.queryChargeUserLoginNums(chargeUser,startDate,endDate); // 获取分管领导时间范围内登录次数
        List<WfProcessInstance> chargeUserCheckNums = wfProcessInstanceMapper.queryChargeUserCheckNums(chargeUser,startDate,endDate); // 获取分管领导时间范围内审批次数

        List<PmPrj> prjList = pmPrjMapper.queryAllSystemProject(); // 获取所有系统项目
        List<PmPrj> zaiJianPrj = prjList.stream().filter(p->"0099799190825080706".equals(p.getProjectPhaseTypeId()) || "1673502467645648896".equals(p.getProjectPhaseTypeId())).collect(Collectors.toList()); // 在建项目数 只取项目状态为 前期 施工中
        int timeFrameNewPrjNums = pmPrjMapper.queryTimeFrameNewProjectNums(startDate,endDate); // 获取时间范围内新增的项目数
        int pmPrjReqNums = pmPrjMapper.queryReqNums(); // 获取所有立项完成数量 含历史导入数据
        int invest1Nums = pmPrjMapper.queryInvest1Nums(); // 获取所有可研完成数量 含历史导入数据
        int invest2Nums = pmPrjMapper.queryInvest2Nums(); // 获取所有初设概算完成数量 含历史导入数据
        BigDecimal projectAllTotalInvest = prjList.stream().filter(p->p.getProjectTotalInvest() != null).map(PmPrj::getProjectTotalInvest).reduce(BigDecimal.ZERO,BigDecimal::add).stripTrailingZeros(); // 查询所有系统项目项目投资总额
        BigDecimal zaiJianJianAnAmt = zaiJianPrj.stream().filter(p->p.getConstructPrjAmt() != null).map(PmPrj::getConstructPrjAmt).reduce(BigDecimal.ZERO,BigDecimal::add).stripTrailingZeros(); // 在建项目数建安总额
        int orderNums = poOrderReqMapper.queryOrderNums(); // 合同总数量
        List<PoOrderReq> timeFrameOrder = poOrderReqMapper.queryTimeFrameNewOrderAmt(startDate,endDate); // 查询时间范围内新签订的合同总金额-刨除历史数据导入
        BigDecimal timeFrameOrderAmt = timeFrameOrder.stream().map(PoOrderReq::getContractAmt).reduce(BigDecimal.ZERO,BigDecimal::add).stripTrailingZeros();

        map.put("allUserNum",allUserNum); // 查询系统所有人员
        map.put("weekUserLoginNum",weekUserLoginNum); //查询时间范围内登录系统的用户数
        map.put("loginNums",loginNums); // 查询系统上线以来所有登录次数 2022-11-22上线
        map.put("allProcessInstanceNums",allProcessInstanceNums); // 查询上线以来所有发起的流程
        map.put("weekProcessInstanceList",weekProcessInstanceList); // 查询时间范围内新增流程详情
        map.put("needWriteNums",needWriteNums); // 形象进度应填报
        map.put("writeNums",writeNums); // 形象进度实际填报
        map.put("chargeUserLogin",chargeUserLogin); // 分管领导时间范围内登录次数
        map.put("chargeUserCheckNums",chargeUserCheckNums); // 分管领导时间范围内审批次数
        map.put("prjNum",prjList.size()); // 项目总数
        map.put("zaiJianPrjNum",zaiJianPrj.size()); // 在建项目数量
        map.put("timeFrameNewPrjNums",timeFrameNewPrjNums); // 时间范围内新增数
        map.put("pmPrjReqNums",pmPrjReqNums); // 立项完成数
        map.put("invest1Nums",invest1Nums); // 可研完成数
        map.put("invest2Nums",invest2Nums); // 初概完成数
        map.put("projectAllTotalInvest",projectAllTotalInvest); // 项目总投资
        map.put("zaiJianJianAnAmt",zaiJianJianAnAmt); // 在建项目建安总额
        map.put("orderNums",orderNums);
        map.put("timeFrameOrderAmt",timeFrameOrderAmt);
        map.put("startDate",startDate);
        map.put("endDate",endDate);

        return map;
    }

    /**
     * 系统使用情况导出
     *
     * @param response 响应
     * @param map      数据详情
     * @param title    标题
     */
    @Override
    public void downloadSystemUsage(HttpServletResponse response, Map<String, Object> map, String title) {
        String filePath = cisdiUtils.getDownLoadPath();
        Workbook workbook = new XSSFWorkbook();
        OutputStream outputStream = null;
        try {

            List<WfProcessInstance> weekProcessInstanceList = (List<WfProcessInstance>) map.get("weekProcessInstanceList"); // 时间范围内新增流程

            List<AdUser> chargeUserLogin = (List<AdUser>) map.get("chargeUserLogin"); // 分管领导时间范围内登录次数
            List<WfProcessInstance> chargeUserCheckNums = (List<WfProcessInstance>) map.get("chargeUserCheckNums"); // 分管领导时间范围内审批次数
            int chargeNums = chargeUserLogin.size();

            int maxCellColumn = 6;
            int processInstance = 0,processInstanceCell = 0;
            if (!CollectionUtils.isEmpty(weekProcessInstanceList)){
                processInstance = weekProcessInstanceList.size();
            } else {
                processInstanceCell = 2;
            }

            if (processInstance  > maxCellColumn){
                maxCellColumn = processInstance ;
            }

            Sheet sheet = workbook.createSheet(title);
            sheet.setDefaultColumnWidth(30);

            //设置合并
            CellRangeAddress region1 = new CellRangeAddress(0,0,0,maxCellColumn-1);
            CellRangeAddress region2 = new CellRangeAddress(1,1,0,5);
            CellRangeAddress region3 = new CellRangeAddress(4,4,0,processInstanceCell);
            CellRangeAddress region4 = new CellRangeAddress(7,7,0,1);
            CellRangeAddress region5 = new CellRangeAddress(7,7,4,5);
            CellRangeAddress region6 = new CellRangeAddress(chargeNums+9,chargeNums+9,0,5);
            sheet.addMergedRegion(region1);
            sheet.addMergedRegion(region2);
            sheet.addMergedRegion(region3);
            sheet.addMergedRegion(region4);
            sheet.addMergedRegion(region5);
            sheet.addMergedRegion(region6);

            Row row0 = sheet.createRow(0);
            row0.setHeight((short) 500);
            Cell cell00 = row0.createCell(0);
            cell00.setCellValue("周期："+ map.get("startDate") + "到" + map.get("endDate"));

            Row row1 = sheet.createRow(1);
            row1.setHeight((short) 500);
            Cell cell10 = row1.createCell(0);
            cell10.setCellValue("本周用户使用情况");

            Row row2 = sheet.createRow(2);
            row2.setHeight((short) 500);
            Cell cell20 = row2.createCell(0);
            cell20.setCellValue("整体用户数");
            Cell cell21 = row2.createCell(1);
            cell21.setCellValue(String.valueOf(map.get("allUserNum")));
            Cell cell22 = row2.createCell(2);
            cell22.setCellValue("本周登录人数");
            Cell cell23 = row2.createCell(3);
            cell23.setCellValue(String.valueOf(map.get("weekUserLoginNum")));
            Cell cell24 = row2.createCell(4);
            cell24.setCellValue("总体登录次数");
            Cell cell25 = row2.createCell(5);
            cell25.setCellValue(String.valueOf(map.get("loginNums")));

            Row row3 = sheet.createRow(3);
            row3.setHeight((short) 500);
            Cell cell30 = row3.createCell(0);
            cell30.setCellValue("发起流程总数");
            Cell cell31 = row3.createCell(1);
            cell31.setCellValue(String.valueOf(map.get("allProcessInstanceNums")));
            Cell cell32 = row3.createCell(2);
            cell32.setCellValue("本周新增流程数量");
            Cell cell33 = row3.createCell(3);
            cell33.setCellValue(processInstance);
            Cell cell34 = row3.createCell(4);
            cell34.setCellValue("项目形象进度周报填报情况");
            Cell cell35 = row3.createCell(5);
            cell35.setCellValue("需要填报： " + map.get("needWriteNums") + "  已填报： " + map.get("writeNums"));

            Row row4 = sheet.createRow(4);
            row4.setHeight((short) 500);
            Cell cell40 = row4.createCell(0);
            cell40.setCellValue("新增流程明细如下");

            Row row5 = sheet.createRow(5);
            row5.setHeight((short) 500);
            Cell cell50 = row5.createCell(0);
            cell50.setCellValue("流程名称");

            Row row6 = sheet.createRow(6);
            row6.setHeight((short) 500);
            Cell cell60 = row6.createCell(0);
            cell60.setCellValue("数量");
            if (!CollectionUtils.isEmpty(weekProcessInstanceList)){
                List<String> processName = weekProcessInstanceList.stream().map(WfProcessInstance::getProcessName).collect(Collectors.toList());
                for (int i = 0; i < processName.size(); i++) {
                    String name = processName.get(i);

                    Cell cell5x = row5.createCell(i+1);
                    cell5x.setCellValue(name);

                    Cell cell6x = row6.createCell(i+1);
                    Integer value = weekProcessInstanceList.stream().filter(p->name.equals(p.getProcessName())).map(WfProcessInstance::getInstanceNums).collect(Collectors.toList()).get(0);
                    cell6x.setCellValue(value == null ? "0" : String.valueOf(value));
                }
            }

            Row row7 = sheet.createRow(7);
            row7.setHeight((short) 500);
            Cell cell70 = row7.createCell(0);
            cell70.setCellValue("分管领导关注度");
            Cell cell74 = row7.createCell(4);
            cell74.setCellValue("分管领导流程审批数量");

            Row row8 = sheet.createRow(8);
            row8.setHeight((short) 500);
            Cell cell80 = row8.createCell(0);
            cell80.setCellValue("分管领导");
            Cell cell81 = row8.createCell(1);
            cell81.setCellValue("关注度");
            for (int i = 0; i < chargeUserLogin.size(); i++) {
                Row rowx = sheet.createRow(9+i);
                rowx.setHeight((short) 500);
                Cell cellx1 = rowx.createCell(0);
                cellx1.setCellValue(chargeUserLogin.get(i).getName());

                Cell cellx2 = rowx.createCell(1);
                cellx2.setCellValue(chargeUserLogin.get(i).getNum());
            }

            Cell cell84 = row8.createCell(4);
            cell84.setCellValue("分管领导");
            Cell cell85 = row8.createCell(5);
            cell85.setCellValue("分管领导流程审批数量");
            for (int i = 0; i < chargeUserCheckNums.size(); i++) {
                Row rowx = sheet.createRow(9+i);
                Cell cellx1 = rowx.createCell(0);
                cellx1.setCellValue(chargeUserCheckNums.get(i).getAdUserName());

                Cell cellx2 = rowx.createCell(1);
                cellx2.setCellValue(chargeUserCheckNums.get(i).getInstanceNums());
            }

            Row row12 = sheet.createRow(chargeNums+9);
            row12.setHeight((short) 500);
            Cell cell120 = row12.createCell(0);
            cell120.setCellValue("系统数据情况");

            Row row13 = sheet.createRow(chargeNums+10);
            row13.setHeight((short) 500);
            Cell cell130 = row13.createCell(0);
            cell130.setCellValue("项目总数");
            Cell cell131 = row13.createCell(1);
            cell131.setCellValue(String.valueOf(map.get("prjNum")));
            Cell cell132 = row13.createCell(2);
            cell132.setCellValue("本周新增项目数");
            Cell cell133 = row13.createCell(3);
            cell133.setCellValue(String.valueOf(map.get("timeFrameNewPrjNums")));

            Row row14 = sheet.createRow(chargeNums+11);
            row14.setHeight((short) 500);
            Cell cell140 = row14.createCell(0);
            cell140.setCellValue("立项完成数量");
            Cell cell141 = row14.createCell(1);
            cell141.setCellValue(String.valueOf(map.get("pmPrjReqNums")));
            Cell cell142 = row14.createCell(2);
            cell142.setCellValue("可研完成数量");
            Cell cell143 = row14.createCell(3);
            cell143.setCellValue(String.valueOf(map.get("invest1Nums")));
            Cell cell144 = row14.createCell(4);
            cell144.setCellValue("初概完成数量");
            Cell cell145 = row14.createCell(5);
            cell145.setCellValue(String.valueOf(map.get("invest2Nums")));

            Row row15 = sheet.createRow(chargeNums+12);
            row15.setHeight((short) 500);
            Cell cell150 = row15.createCell(0);
            cell150.setCellValue("在建项目数量");
            Cell cell151 = row15.createCell(1);
            cell151.setCellValue(String.valueOf(map.get("zaiJianPrjNum")));
            Cell cell152 = row15.createCell(2);
            cell152.setCellValue("项目投资总额");
            Cell cell153 = row15.createCell(3);
            cell153.setCellValue(String.valueOf(map.get("projectAllTotalInvest")));
            Cell cell154 = row15.createCell(4);
            cell154.setCellValue("在建项目建安总额");
            Cell cell155 = row15.createCell(5);
            cell155.setCellValue(String.valueOf(map.get("zaiJianJianAnAmt")));

            Row row16 = sheet.createRow(chargeNums+13);
            row16.setHeight((short) 500);
            Cell cell160 = row16.createCell(0);
            cell160.setCellValue("合同总数量");
            Cell cell161 = row16.createCell(1);
            cell161.setCellValue(String.valueOf(map.get("orderNums")));
            Cell cell162 = row16.createCell(2);
            cell162.setCellValue("本周新签合同总金额");
            Cell cell163 = row16.createCell(3);
            cell163.setCellValue(String.valueOf(map.get("timeFrameOrderAmt")));

            String realFileName = title + ".xls";
            String path = filePath + realFileName;
            outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            File file = new File(path);
            FileUtils.writeBytes(path,file,response.getOutputStream());
            if (file.exists()){
                file.delete();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e1){
                e1.printStackTrace();
            }
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e2){
                    e2.printStackTrace();
                }
            }
        }
    }
}
