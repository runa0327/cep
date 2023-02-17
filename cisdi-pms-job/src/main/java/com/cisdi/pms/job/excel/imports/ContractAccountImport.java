package com.cisdi.pms.job.excel.imports;


import com.cisdi.pms.job.config.UploadParamConfig;
import com.cisdi.pms.job.excel.export.BaseController;
import com.cisdi.pms.job.excel.model.ContractImportModel;
import com.cisdi.pms.job.utils.*;
import com.google.common.base.Strings;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * @author dlt
 * @date 2023/1/6 周五
 */
@RestController
@RequestMapping("/contractAccount")
public class ContractAccountImport extends BaseController {

    @Value("${spring.task.execution.pool.core-size}")
    private Integer coreSize;

    @Value("${history-data.contract-path-prefix}")
    private String pathPrefix;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor asyncExecutor;

    @Autowired
    private UploadParamConfig config;

    @SneakyThrows
    @PostMapping("/import")
    public Map<String,Object> importData(HttpServletResponse response){
        //报错信息
        List<String> errorList = new ArrayList<>();
        //获取合同数据文件夹
        List<File> files = new ArrayList<>();
        File folder = new File("C:\\Users\\11376\\Desktop\\合约模板\\20230217\\excel");
        if (folder.exists()&&folder.isDirectory()){
            File[] fileArray = folder.listFiles();
            assert fileArray != null;
            files = Arrays.asList(fileArray);
        }
        //遍历所有的excel
        for (File file : files) {
            //读取所有的sheet页,表头为第3行,key为项目名
            Map<String,List<ContractImportModel>> sheets = EasyExcelUtil.readSheets(new FileInputStream(file), ContractImportModel.class, 2);
            //整理为list
            List<ContractImportModel> models = this.arrange(sheets, errorList);
            //检查招标类别字典
            checkAndInsertDic(models,errorList);
            //插入数据
            CountDownLatch latch = this.insert(models, errorList);
            latch.await();
        }

        //组装提示
        Map<String, Object> result = new HashMap<>();
        if (CollectionUtils.isEmpty(errorList)){
            result.put("code",200);
            result.put("message","导入成功！");
            return result;
        }else {
            super.exportTxt(response,errorList,"合同台账导入");
            return null;
        }
    }

    //检查字典
    private void checkAndInsertDic(List<ContractImportModel> models,List<String> errorList) {
        //获取合同类型字典
        List<Map<String, Object>> contractCategoryDicList = jdbcTemplate.queryForList("select va.id,va.name from gr_set_value va left join gr_set se on se.id = va.GR_SET_ID where se.code = 'contract_type_one'");
        //已存在的合同类型
        List<String> contractCategoryExists = contractCategoryDicList.stream().map(contractCategoryMap -> contractCategoryMap.get("name").toString()).collect(Collectors.toList());
        //找出字典中不存在的合同类型
        List<String> contractCategoryNotExists = models.stream()
                .map(model -> model.getContractCategoryName())
                .distinct()
                .filter(category -> !contractCategoryExists.contains(category))
                .collect(Collectors.toList());
        //提示字典不存在
        for (String contractCategoryNotExist : contractCategoryNotExists) {
            errorList.add("没有找到合同类型contract_type_one:'" + contractCategoryNotExist +"'");
        }
        //公司
        List<Map<String, Object>> unitList = jdbcTemplate.queryForList("select id,name from PM_PARTY where status = 'AP'");
        List<String> unitExists = unitList.stream().map(unitMap -> String.valueOf(unitMap.get("name"))).collect(Collectors.toList());
        List<String> unitNotExists = models.stream()
                .map(model -> model.getCustomerUnitName())
                .distinct()
                .filter(unitName -> !unitExists.contains(unitName))
                .collect(Collectors.toList());
        for (String unitNotExist : unitNotExists) {
            errorList.add("没有找到公司PM_PARTY:'" + unitNotExist + "'");
        }
        //替换model中的contractCategoryId
        for (ContractImportModel model : models) {
//            if (Strings.isNullOrEmpty(model.getContractCategoryName())){
//                continue;
//            }
            //获取合同类型id
            String categoryId = "";
            Optional<Map<String, Object>> contractCategoryMap =
                    contractCategoryDicList.stream().filter(category -> category.get("name").toString().equals(model.getContractCategoryName())).findAny();
            if (contractCategoryMap.isPresent()){//字典有就用
                categoryId = JdbcMapUtil.getString(contractCategoryMap.get(),"id");
            }else {
                categoryId = null;
            }
            model.setContractCategoryId(categoryId);
            //获取签订公司id
            String unitId = "";
            Optional<Map<String, Object>> unitMap =
                    unitList.stream().filter(unit -> String.valueOf(unit.get("name")).equals(model.getCustomerUnitName())).findAny();
            if (unitMap.isPresent()){
                unitId = String.valueOf(unitMap.get().get("id"));
            }else {
                unitId = null;
            }
            model.setCustomerUnitId(unitId);
        }
    }

    /**
     * 插入 PO_ORDER_REQ合同签订、CONTRACT_SIGNING_CONTACT联系人明细、PM_ORDER_COST_DETAIL合同签订费用明细（流程内）
     * @param models
     */
    private CountDownLatch insert(List<ContractImportModel> models, List<String> errorList){

        int size = models.size() / coreSize + 1;
        List<List<ContractImportModel>> modelLists = ListUtils.split(models, size);
        CountDownLatch latch = new CountDownLatch(modelLists.size());
        for (List<ContractImportModel> modelList : modelLists) {
            asyncExecutor.execute(() -> {
                try {
                    for (ContractImportModel model : modelList) {
                        ThreadLocal<Boolean> tl = new ThreadLocal<>();
                        tl.set(true);
                        String fileIds = this.uploadGetFileIds(model.getFilePath(),model,errorList,tl);
                        if (!tl.get()){//如果上传文件失败，不导入数据
                            continue;
                        }
                        //插入PO_ORDER_REQ合同签订
                        String orderId = Util.insertData(jdbcTemplate, "PO_ORDER_REQ");
                        jdbcTemplate.update("update po_order_req set PM_PRJ_ID = ?,CONTRACT_NAME = ?,CUSTOMER_UNIT_ONE = ?,CONTRACT_CATEGORY_ONE_ID = ?,AMT_THREE = ?,AMT_TWO = ?,AMT_FOUR = ?,SIGN_DATE = ?,FILE_ID_FIVE = ?,STATUS = 'AP',VER = 101,CRT_USER_ID = '0099250247095871681' where id = ?",
                                model.getProjectId(),model.getContractName(),model.getCustomerUnitId(),model.getContractCategoryId(),model.getAmtThree(),model.getAmtTwo(),model.getAmtFour(),model.getSignDate(),fileIds,orderId);

                        //插入CONTRACT_SIGNING_CONTACT联系人明细
                        String contactId = Util.insertData(jdbcTemplate, "CONTRACT_SIGNING_CONTACT");
                        jdbcTemplate.update("update CONTRACT_SIGNING_CONTACT set PARENT_ID = ?, WIN_BID_UNIT_ONE = ?,STATUS = 'AP',VER = 101,CRT_USER_ID = '0099250247095871681' where id = ?"
                                ,orderId,model.getWinBidUnit(),contactId);

                        //插入PM_ORDER_COST_DETAIL合同签订费用明细
                        String costId = Util.insertData(jdbcTemplate, "PM_ORDER_COST_DETAIL");
                        jdbcTemplate.update("update  PM_ORDER_COST_DETAIL set CONTRACT_ID = ?,AMT_ONE = ?,AMT_TWO = ?,AMT_THREE = ?,STATUS = 'AP',VER = 101,CRT_USER_ID = '0099250247095871681' where id = ?",
                                orderId,model.getAmtTwo(),model.getAmtThree(),model.getAmtFour(),costId);
                    }
                } catch (Exception e){
                    errorList.add(e.toString());
                } finally {
                    latch.countDown();
                }
            });
        }
        return latch;
    }

    /**
     * 检查是否有对应项目并整理为list
     * @param sheets 读出的sheet页
     * @param errorList 报错信息
     * @return
     */
    private List<ContractImportModel> arrange(Map<String,List<ContractImportModel>> sheets,List<String> errorList){
        List<Map<String, Object>> prjList = jdbcTemplate.queryForList("select p.ID,p.NAME from pm_prj p left join gr_set_value v on v.ID = p.PROJECT_SOURCE_TYPE_ID where p.STATUS = 'AP' and v.code = 'system' and p.name is not null");
        List<String> prjNameList = prjList.stream().map(prj -> JdbcMapUtil.getString(prj, "NAME")).collect(Collectors.toList());
        //整理后的数据
        List<ContractImportModel> dataList = new ArrayList<>();
        for (String prjName:sheets.keySet()){
            if (!prjNameList.contains(prjName)){//没有找到该项目
                errorList.add("没有找到项目：" + prjName);
            }else {
                //同一个项目的合同
                List<ContractImportModel> models = sheets.get(prjName);
                for (ContractImportModel model : models) {
                    model.setProjectId(null);
                    if ("合计".equals(model.getContractName()) || "其他合同".equals(model.getContractName())){
                        continue;
                    }
                    if (ReflectUtil.isObjectNull(model)){
                        continue;
                    }
                    if ("/".equals(model.getSignDate())){
                        model.setSignDate(null);
                    }
                    if (!Strings.isNullOrEmpty(model.getFilePath())){
                        model.setFilePath(model.getFilePath().replaceAll("\\.\\.",""));
                    }
                    model.setProjectName(prjName);
                    model.setProjectId(this.getPrjIdByName(prjList,prjName));
                    dataList.add(model);
                }
            }
        }
        return dataList;
    }

    /**
     * 通过项目名获取项目id
     * @param prjList
     * @return
     */
    private String getPrjIdByName(List<Map<String, Object>> prjList,String prjName){
        for (Map<String, Object> prjMap : prjList) {
            if (prjName.equals(JdbcMapUtil.getString(prjMap,"NAME"))){
                return JdbcMapUtil.getString(prjMap,"ID");
            }
        }
        return null;
    }

    /**
     * 根据excel的文件路径上传文件，并返回文件id字符串
     * @param filePaths
     * @param model
     * @param errorList
     * @param tl
     * @return
     */
    private String uploadGetFileIds(String filePaths, ContractImportModel model, List<String> errorList, ThreadLocal<Boolean> tl) throws IOException {
        List<String> ids = new ArrayList<>();
        if (!Strings.isNullOrEmpty(filePaths)){
            String[] filePathArr = filePaths.split(",");
            for (String filepath : filePathArr) {
                //找到合同文件路径，上传
                String id = HttpUtils.uploadFile(pathPrefix + filepath, config);
                if (!"500".equals(id)){
                    ids.add(id);
                }else {
                    tl.set(false);
                    errorList.add("项目:" + model.getProjectName() + "中合同:" + model.getContractName() + model.getFilePath() + "上传失败");
                }
            }
            return String.join(",",ids);
        }
        return null;
    }
}
