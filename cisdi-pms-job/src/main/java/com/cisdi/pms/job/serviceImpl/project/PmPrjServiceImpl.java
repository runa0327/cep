package com.cisdi.pms.job.serviceImpl.project;

import com.cisdi.pms.job.domain.base.DataCheck;
import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.mapper.project.PmPrjMapper;
import com.cisdi.pms.job.service.project.PmPrjService;
import com.cisdi.pms.job.utils.CisdiUtils;
import com.cisdi.pms.job.utils.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class PmPrjServiceImpl implements PmPrjService {

    @Resource
    private PmPrjMapper pmPrjMapper;

    @Resource
    private CisdiUtils cisdiUtils;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 通过Map对象获取map中项目id信息
     * @param map 数据源
     * @return 项目id
     */
    public static String getProjectId(Map<String, Object> map) {
        String projectId = "";
        if (map.containsKey("PM_PRJ_IDS")){
            projectId = map.get("PM_PRJ_IDS").toString();
        } else if (map.containsKey("PM_PRJ_ID")){
            projectId = map.get("PM_PRJ_ID").toString();
        }
        return projectId;
    }

    /**
     * 形象进度工程周报-需要自动生成周报的项目
     * @return 项目集合
     */
    @Override
    public List<PmPrj> getNeedWeekPrj() {
        return pmPrjMapper.getNeedWeekPrj();
    }

    /**
     * 刷新系统项目资金信息
     *
     * @param pmPrj 项目实体
     * @return 刷新条数
     */
    @Override
    public int refreshAmt(PmPrj pmPrj) {

        // 创建一个包含 10 个线程的线程池
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ExecutorCompletionService<Map<String,Object>> service = new ExecutorCompletionService<Map<String,Object>>(taskExecutor);

        List<PmPrj> list = pmPrjMapper.queryProject(pmPrj);
        List<DataCheck> checkList = new ArrayList<>();
        int count = 0;
        if (!CollectionUtils.isEmpty(list)){
            try {
                for (PmPrj tmp : list) {
                    service.submit(() -> {
                        String projectId = tmp.getProjectId();
                        return checkAndUpdatePrjAmt(projectId);
                    });
                }
                for (PmPrj tmp : list) {
                    Map<String,Object> tmpMap = service.take().get();
                    boolean f = (boolean)tmpMap.get("res");
                    if (f){
                        PmPrj oldPmPrj = (PmPrj)tmpMap.get("old");
                        PmPrj newPmPrj = (PmPrj)tmpMap.get("new");
                        DataCheck dataCheck = new DataCheck();
                        dataCheck.setNewData(getAmtStr(newPmPrj));
                        dataCheck.setOldData(getAmtStr(oldPmPrj));
                        checkList.add(dataCheck);
                        count++;
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        if (!CollectionUtils.isEmpty(checkList)){
            exportCheckList(checkList);
        }
        return count;
    }

    /**
     * 从实体中获取资金信息转为字符串
     * @param pmPrj 实体信息
     * @return 字符串
     */
    private String getAmtStr(PmPrj pmPrj) {
        StringBuilder stringBuilder = new StringBuilder("项目id：").append(pmPrj.getProjectId());
        BigDecimal oldAmt1 = pmPrj.getProjectTotalInvest(); // 总投资
        BigDecimal oldAmt2 = pmPrj.getProjectAmt(); // 工程费用
        BigDecimal oldAmt3 = pmPrj.getConstructPrjAmt(); // 建安工程费
        BigDecimal oldAmt4 = pmPrj.getEquipBuyAmt(); // 设备采购费
        BigDecimal oldAmt5 = pmPrj.getEquipmentCost(); // 科研设备费
        BigDecimal oldAmt6 = pmPrj.getProjectOtherAmt(); // 工程其他费
        BigDecimal oldAmt7 = pmPrj.getLandBuyAmt(); // 土地征拆费
        BigDecimal oldAmt8 = pmPrj.getPrepareAmt(); // 预备费
        BigDecimal oldAmt9 = pmPrj.getConstructPeriodInterest(); // 建设期利息
        if (oldAmt1 != null){
            stringBuilder.append(" 总投资：").append(oldAmt1.stripTrailingZeros().toPlainString());
        }
        if (oldAmt2 != null){
            stringBuilder.append(" 工程费用：").append(oldAmt2.stripTrailingZeros().toPlainString());
        }
        if (oldAmt3 != null){
            stringBuilder.append(" 建安工程费：").append(oldAmt3.stripTrailingZeros().toPlainString());
        }
        if (oldAmt4 != null){
            stringBuilder.append(" 设备采购费：").append(oldAmt4.stripTrailingZeros().toPlainString());
        }
        if (oldAmt5 != null){
            stringBuilder.append(" 科研设备费：").append(oldAmt5.stripTrailingZeros().toPlainString());
        }
        if (oldAmt6 != null){
            stringBuilder.append(" 工程其他费：").append(oldAmt6.stripTrailingZeros().toPlainString());
        }
        if (oldAmt7 != null){
            stringBuilder.append(" 土地征拆费：").append(oldAmt7.stripTrailingZeros().toPlainString());
        }
        if (oldAmt8 != null){
            stringBuilder.append(" 预备费：").append(oldAmt8.stripTrailingZeros().toPlainString());
        }
        if (oldAmt9 != null){
            stringBuilder.append(" 建设期利息：").append(oldAmt9.stripTrailingZeros().toPlainString());
        }
        return stringBuilder.toString();
    }

    /**
     * 将修改记录导出
     * @param checkList 修改记录
     */
    private void exportCheckList(List<DataCheck> checkList) {
        HttpServletResponse response = null;
        String filePath = cisdiUtils.getDownLoadPath();
        Workbook workbook = new XSSFWorkbook();
        OutputStream outputStream = null;
        try {
            Sheet sheet = workbook.createSheet("项目资金信息修改记录");
            Row topRow = sheet.createRow(0);
            // 固定表头信息
            List<String> headerList = getHeaderList();
            for (int i = 0; i < headerList.size(); i++) {
                Cell cell = topRow.createCell(i);
                cell.setCellValue(headerList.get(i));
            }
            for (int i = 0; i < checkList.size(); i++) {
                Row row = sheet.createRow(i+1);
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(i+1);

                Cell cell1 = row.createCell(1);
                cell1.setCellValue(checkList.get(i).getOldData());

                Cell cell2 = row.createCell(2);
                cell2.setCellValue(checkList.get(i).getNewData());
            }

            String realFileName =  "项目资金信息修改记录.xls";
            String path = filePath + realFileName;
            outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
            assert response != null;
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            File file = new File(path);
            FileUtils.writeBytes(path,file,response.getOutputStream());
            if (file.exists()){
                file.delete();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 项目资金信息修改结果导出表头
     * @return 表头
     */
    private List<String> getHeaderList() {
        List<String> list = new ArrayList<>();
        list.add("序号");
        list.add("旧信息");
        list.add("新信息");
        return list;
    }

    /**
     * 查询修改项目库项目资金信息
     * @param projectId 项目id
     */
    public Map<String,Object> checkAndUpdatePrjAmt(String projectId) {
        Map<String,Object> map = new HashMap<>();
        PmPrj pmPrj = pmPrjMapper.queryPrjAmtBySettle(projectId); // 结算信息
        if (pmPrj == null){
            pmPrj = pmPrjMapper.queryPrjAmtByInvest3(projectId); // 预算财评
            if (pmPrj == null){
                pmPrj = pmPrjMapper.queryPrjAmtByInvest2(projectId); // 初设概算
                if (pmPrj == null){
                    pmPrj = pmPrjMapper.queryPrjAmtByInvest1(projectId); // 可研估算
                    if (pmPrj == null){
                        pmPrj = pmPrjMapper.queryPrjAmtByPmPrjReq(projectId); // 立项审批
                        if (pmPrj != null){
                            pmPrj.setInvestPriority("1631460206540161024");
                        }
                    } else {
                        pmPrj.setInvestPriority("1631460270226472960");
                    }
                } else {
                    pmPrj.setInvestPriority("1631460343433854976");
                }
            } else {
                pmPrj.setInvestPriority("1631460383644647424");
            }
        } else {
            pmPrj.setInvestPriority("1640251041104666624");
        }
        if (pmPrj != null){
            PmPrj oldPmPrj = pmPrjMapper.queryById(projectId);
            boolean check = checkPrjAmt(oldPmPrj,pmPrj);
            if (check){
                pmPrjMapper.updateConditionById(pmPrj);
                map.put("res",true);
                map.put("old",oldPmPrj);
                map.put("new",pmPrj);
            } else {
                map.put("res",false);
            }
        } else {
            map.put("res",false);
        }
        return map;
    }

    /**
     * 判断项目新旧两次金额是否相同
     * @param oldPmPrj 项目原始金额
     * @param pmPrj 项目待更新金额
     * @return 判断比较结果
     */
    private boolean checkPrjAmt(PmPrj oldPmPrj, PmPrj pmPrj) {
        boolean res;

        BigDecimal oldAmt1 = oldPmPrj.getProjectTotalInvest(); // 总投资
        BigDecimal newAmt1 = pmPrj.getProjectTotalInvest();

        BigDecimal oldAmt2 = oldPmPrj.getProjectAmt(); // 工程费用
        BigDecimal newAmt2 = pmPrj.getProjectAmt();

        BigDecimal oldAmt3 = oldPmPrj.getConstructPrjAmt(); // 建安工程费
        BigDecimal newAmt3 = pmPrj.getConstructPrjAmt();

        BigDecimal oldAmt4 = oldPmPrj.getEquipBuyAmt(); // 设备采购费
        BigDecimal newAmt4 = pmPrj.getEquipBuyAmt();

        BigDecimal oldAmt5 = oldPmPrj.getEquipmentCost(); // 科研设备费
        BigDecimal newAmt5 = pmPrj.getEquipmentCost();

        BigDecimal oldAmt6 = oldPmPrj.getProjectOtherAmt(); // 工程其他费
        BigDecimal newAmt6 = pmPrj.getProjectOtherAmt();

        BigDecimal oldAmt7 = oldPmPrj.getLandBuyAmt(); // 土地征拆费
        BigDecimal newAmt7 = pmPrj.getLandBuyAmt();

        BigDecimal oldAmt8 = oldPmPrj.getPrepareAmt(); // 预备费
        BigDecimal newAmt8 = pmPrj.getPrepareAmt();

        BigDecimal oldAmt9 = oldPmPrj.getConstructPeriodInterest(); // 建设期利息
        BigDecimal newAmt9 = pmPrj.getConstructPeriodInterest();

        res = checkAmt(oldAmt1,newAmt1);
        if (res){
            res = checkAmt(oldAmt2,newAmt2);
            if (res){
                res = checkAmt(oldAmt3,newAmt3);
                if (res){
                    res = checkAmt(oldAmt4,newAmt4);
                    if (res){
                        res = checkAmt(oldAmt5,newAmt5);
                        if (res){
                            res = checkAmt(oldAmt6,newAmt6);
                            if (res){
                                res = checkAmt(oldAmt7,newAmt7);
                                if (res){
                                    res = checkAmt(oldAmt8,newAmt8);
                                    if (res){
                                        res = checkAmt(oldAmt9,newAmt9);
                                        return !res;
                                    } else {
                                        return true;
                                    }
                                } else {
                                    return true;
                                }
                            } else {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }

    }

    /**
     * 数值比较
     * @param oldAmt 旧值
     * @param newAmt 新值
     * @return 比较结果 相同为true 不同为false 方便后面判断是否修改
     */
    private boolean checkAmt(BigDecimal oldAmt, BigDecimal newAmt) {
        if (oldAmt != null && newAmt != null){
            return oldAmt.compareTo(newAmt) == 0;
        } else {
            return false;
        }
    }
}
