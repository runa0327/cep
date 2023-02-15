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
        File folder = new File("C:\\Users\\11376\\Desktop\\合约模板\\20230215\\excel");
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

    //检查招标类别字典
    private void checkAndInsertDic(List<ContractImportModel> models,List<String> errorList) {
        //获取招标类别字典
        List<Map<String, Object>> buyTypeDicList = jdbcTemplate.queryForList("select va.id,va.name from gr_set_value va left join gr_set se on se.id = va.GR_SET_ID where se.code = 'buy_type'");
        //已存在的招标类别
        List<String> buyTypeExists = buyTypeDicList.stream().map(buyTypeMap -> buyTypeMap.get("name").toString()).collect(Collectors.toList());
        //找出待添加的招标类别
        Set<String> buyTypeSet = models.stream().map(model -> model.getBuyType()).filter(buyType -> !Strings.isNullOrEmpty(buyType)).collect(Collectors.toSet());
        List<String> buyTypeNotExists = buyTypeSet.stream().filter(buyType -> !buyTypeExists.contains(buyType)).collect(Collectors.toList());
//        //添加字典
//        for (String buyTypeNotExist : buyTypeNotExists) {
//            String buyTypeId = Util.insertData(jdbcTemplate, "gr_set_value");
//            jdbcTemplate.update("update gr_set_value set name = ?,GR_SET_ID = '0099952822476385220',VER = 101 where id = ?",buyTypeNotExist,buyTypeId);
//            errorList.add("'新增合同招标类别buy_type：'" + buyTypeNotExist + "'请检查字典是否有类似项");
//        }
        //提示字典不存在
        for (String buyTypeNotExist : buyTypeNotExists) {
            errorList.add("没有找到合同招标类别buy_type：'" + buyTypeNotExist +"'");
        }
        //替换model中的buyType
        List<Map<String, Object>> newBuyTypeDicList = jdbcTemplate.queryForList("select va.id,va.name from gr_set_value va left join gr_set se on se.id = va.GR_SET_ID where se.code = 'buy_type'");
        for (ContractImportModel model : models) {
            if (Strings.isNullOrEmpty(model.getBuyType())){
                continue;
            }
            //获取招标类别id
            String buyTypeId = "";
            Optional<Map<String, Object>> buyTypeMap =
                    newBuyTypeDicList.stream().filter(buyType -> buyType.get("name").toString().equals(model.getBuyType())).findAny();
            if (buyTypeMap.isPresent()){//字典有就用
                buyTypeId = JdbcMapUtil.getString(buyTypeMap.get(),"id");
            }else {
                buyTypeId = null;
            }
            model.setBuyType(buyTypeId);
        }
    }

    /**
     * 插入 PO_ORDER_REQ合同签订、CONTRACT_SIGNING_CONTACT联系人明细
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
                        jdbcTemplate.update("update PO_ORDER_REQ set CUSTOMER_UNIT_ONE = null,PM_PRJ_ID = ?, CONTRACT_NAME = ?, SIGN_DATE = ?, AMT_TWO = ?, REMARK_LONG_ONE = ?, ESTIMATED_AMOUNT = ?, FINANCIAL_AMOUNT = ?, PAYED_AMT = ?, CUMULATIVE_PAYED_PERCENT = ?,BUY_TYPE_ID = ?,FILE_ID_FIVE = ?,STATUS = 'AP',VER = 101,CRT_USER_ID = '0099250247095871681' where id = ?",
                                model.getProjectId(),model.getContractName(),model.getSignDate(),model.getAmtIncludeTax(),model.getRemark(),model.getEstimateAmt(),model.getFinancialAmt(),model.getPayedAmt(),model.getPayedPercent(),model.getBuyType(),fileIds,orderId);

                        //插入CONTRACT_SIGNING_CONTACT联系人明细
                        String contactId = Util.insertData(jdbcTemplate, "CONTRACT_SIGNING_CONTACT");
                        jdbcTemplate.update("update CONTRACT_SIGNING_CONTACT set PARENT_ID = ?, WIN_BID_UNIT_ONE = ?, OPPO_SITE_LINK_MAN = ?,STATUS = 'AP',VER = 101,CRT_USER_ID = '0099250247095871681' where id = ?"
                                ,orderId,model.getWinBidUnit(),model.getLinkMan(),contactId);
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
                    if ("/".equals(model.getBuyType())){
                        model.setBuyType(null);
                    }
                    if (!StringUtils.isDouble(model.getEstimateAmt())){
                        model.setEstimateAmt(null);
                    }
                    if (!StringUtils.isDouble(model.getFinancialAmt())){
                        model.setFinancialAmt(null);
                    }
                    if (!StringUtils.isDouble(model.getAmtIncludeTax())){
                        model.setAmtIncludeTax(null);
                    }
                    if (!StringUtils.isDouble(model.getPayedAmt())){
                        model.setPayedAmt(null);
                    }
                    //累计支付比例整理
                    if (!Strings.isNullOrEmpty(model.getPayedPercent())){
                        String payPercent = model.getPayedPercent().replace("%", "");
                        if (!StringUtils.isDouble(payPercent)){
                            payPercent = null;
                        }
                        model.setPayedPercent(payPercent);
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
