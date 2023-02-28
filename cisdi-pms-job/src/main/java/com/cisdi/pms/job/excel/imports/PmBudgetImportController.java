package com.cisdi.pms.job.excel.imports;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.excel.export.BaseController;
import com.cisdi.pms.job.excel.model.PmBudgetModel;
import com.cisdi.pms.job.excel.model.PmInvestEstDtl;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.ReflectUtil;
import com.cisdi.pms.job.utils.Util;
import com.qygly.shared.BaseException;
import lombok.SneakyThrows;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmBudgetImportController
 * @package com.cisdi.pms.job.excel.imports
 * @description 项目预算导入
 * @date 2023/2/27
 */
@RestController
@RequestMapping("/budget")
public class PmBudgetImportController extends BaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 项目预算模板下载
     *
     * @param response
     */
    @SneakyThrows(IOException.class)
    @GetMapping("budgetTemplate")
    public void investTemplate(HttpServletResponse response) {
        super.setExcelRespProp(response, "项目预算");
        EasyExcel.write(response.getOutputStream())
                .head(PmBudgetModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("项目预算")
                .doWrite(new ArrayList<>());
    }


    /**
     * 项目投资导入
     *
     * @param file
     * @return
     */
    @SneakyThrows(IOException.class)
    @RequestMapping("/import")
    public Map<String, Object> importData(MultipartFile file, HttpServletResponse response) {
        List<String> res = new ArrayList<>();
        List<PmBudgetModel> budgetModelList = EasyExcelUtil.read(file.getInputStream(), PmBudgetModel.class);
        //去除空行
        List<PmBudgetModel> modelList = budgetModelList.stream().filter(p -> !ReflectUtil.isObjectNull(p)).collect(Collectors.toList());
        List<Map<String, Object>> prjList = jdbcTemplate.queryForList("select id,name from pm_prj where status = 'AP'");

        Map<String, List<PmBudgetModel>> projectMap = modelList.stream().collect(Collectors.groupingBy(PmBudgetModel::getProjectName));
        for (String s : projectMap.keySet()) {
            //项目 先判断项目没有对应项目直接返回
            Optional<Map<String, Object>> optional = prjList.stream().filter(p -> String.valueOf(p.get("name")).equals(s)).findAny();
            if (!optional.isPresent()) {
                res.add("项目名称为:" + s + "不存在，未导入！");
                continue;
            }
            String projectId = String.valueOf(optional.get().get("ID"));

            Map<String, List<PmBudgetModel>> typeMap = projectMap.get(s).stream().collect(Collectors.groupingBy(PmBudgetModel::getType));
            for (String type : typeMap.keySet()) {
                List<Map<String, Object>> typeList = jdbcTemplate.queryForList("select gsv.* from gr_set_value gsv left join gr_set gs on gsv.GR_SET_ID = gs.id where gs.`CODE`='invest_est_type' and gsv.name=?", type);
                if (CollectionUtils.isEmpty(typeList)) {
                    res.add("项目名称为:" + s + "类型为：" + type + "的数据有误，类型错误！");
                    continue;
                }
                String typeId = String.valueOf(typeList.get(0).get("ID"));
                List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from PM_INVEST_EST where PM_PRJ_ID=? and INVEST_EST_TYPE_ID=?", projectId, typeId);
                String investId = "";
                if (CollectionUtils.isEmpty(list)) {
                    investId = Util.insertData(jdbcTemplate, "PM_INVEST_EST");
                } else {
                    investId = String.valueOf(list.get(0).get("ID"));
                }
                List<PmBudgetModel> models = typeMap.get(type);

                BigDecimal total = models.stream().map(PmBudgetModel::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
                jdbcTemplate.update("update PM_INVEST_EST set PM_PRJ_ID=?,IS_TEMPLATE=?,INVEST_EST_TYPE_ID=?,PRJ_TOTAL_INVEST=? where id=?", projectId, "0", typeId, total, investId);


                //先删明细
                jdbcTemplate.update("delete from PM_INVEST_EST_DTL where PM_INVEST_EST_ID=?", investId);

                List<Map<String, Object>> expTypeList = jdbcTemplate.queryForList("select ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,SEQ_NO,ifnull(PM_EXP_TYPE_PID,0) as PM_EXP_TYPE_PID,CALC_BY_PAYMENT,CALC_BY_PROGRESS from PM_EXP_TYPE");
                for (PmBudgetModel model : models) {
                    List<PmInvestEstDtl> dtlList = new ArrayList<>();
                    expTypeList.stream().filter(p -> Objects.equals("0", String.valueOf(p.get("PM_EXP_TYPE_PID")))).peek(m -> {
                        String id = Util.insertData(jdbcTemplate, "PM_INVEST_EST_DTL");
                        PmInvestEstDtl dtl = new PmInvestEstDtl();
                        dtl.setId(id);
                        dtl.setPid(null);
                        dtl.setAmt(null);
                        dtl.setInvestEstId(null);
                        dtl.setExpTypeId(String.valueOf(m.get("ID")));
                        dtl.setCode(String.valueOf(m.get("CODE")));
                        dtl.setSeq(String.valueOf(m.get("SEQ_NO")));
                        dtlList.add(dtl);
                        getChildren(m, expTypeList, dtlList, id);
                    }).collect(Collectors.toList());

                    for (PmInvestEstDtl p : dtlList) {
                        String amtData = getAmt(p.getCode(), model);
                        BigDecimal amt = new BigDecimal(amtData);
                        jdbcTemplate.update("update PM_INVEST_EST_DTL set PM_INVEST_EST_ID=?,PM_INVEST_EST_DTL_PID=?,PM_EXP_TYPE_ID=?,AMT=?,SEQ_NO=? where id=?",
                                investId, p.getPid(), p.getExpTypeId(), amt, p.getSeq(), p.getId());
                    }
                }

            }
        }

        Map<String, Object> result = new HashMap<>();
        if (CollectionUtils.isEmpty(res)) {
            result.put("code", 200);
            result.put("message", "导入成功！");
            return result;
        } else {
            super.exportTxt(response, res, "项目纳统填报日志");
            return null;
        }
    }

    private List<Map<String, Object>> getChildren(Map<String, Object> root, List<Map<String, Object>> allData, List<PmInvestEstDtl> dtlList, String pId) {
        return allData.stream().filter(p -> Objects.equals(String.valueOf(p.get("PM_EXP_TYPE_PID")), String.valueOf(root.get("ID")))).peek(m -> {
            String id = Util.insertData(jdbcTemplate, "PM_INVEST_EST_DTL");
            PmInvestEstDtl dtl = new PmInvestEstDtl();
            dtl.setId(id);
            dtl.setPid(pId);
            dtl.setAmt(null);
            dtl.setInvestEstId(null);
            dtl.setExpTypeId(String.valueOf(m.get("ID")));
            dtl.setCode(String.valueOf(m.get("CODE")));
            dtl.setSeq(String.valueOf(m.get("SEQ_NO")));
            dtlList.add(dtl);
            getChildren(m, allData, dtlList, id);
        }).collect(Collectors.toList());
    }

    private String getAmt(String code, PmBudgetModel model) {
        String res = null;
        Class clazz = model.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            if (codeMap(fieldName) == null) {
                continue;
            }
            String mapCode = codeMap(fieldName);
            if (code.equals(mapCode)) {
                try {
                    field.setAccessible(true);
                    res = field.get(model).toString();
                } catch (Exception e) {
                    throw new BaseException("值获取错误");
                }
            }
        }
        return res;
    }


    private String codeMap(String fieldName) {
        Map<String, String> obj = new HashMap<>();
        obj.put("projectName", null);
        obj.put("type", null);
        obj.put("total", "PRJ_TOTAL_INVEST");
        obj.put("gcAmt", "PROJECT_AMT");
        obj.put("jaAmt", "CONSTRUCT_AMT");
        obj.put("sbAmt", "EQUIP_AMT");
        obj.put("kyAmt", "SCIENTIFIC_EQUIPMENT_AMT");
        obj.put("gcqtAmt", "PROJECT_AMT-OTHER");
        obj.put("gcqtfAmt", "PROJECT_OTHER_AMT");
        obj.put("tdcqAmt", "LAND_AMT");
        obj.put("qtgcqtAmt", "PROJECT_OTHER_AMT-OTHER");
        obj.put("ybAmt", "PREPARE_AMT");
        obj.put("jslxAmt", "CONSTRUCT_PERIOD_INTEREST");
        obj.put("qtTotal", "PRJ_TOTAL_INVEST-OTHER");
        return obj.get(fieldName);
    }
}