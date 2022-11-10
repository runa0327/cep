package com.cisdi.pms.job.localExcel;

import com.cisdi.pms.job.utils.Util;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地处理excel-立项申请
 *
 * @author hgh
 * @date 2022/11/7
 * @apiNote
 */

@RestController
@RequestMapping("/handleExcel")
public class handleExcel {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void constructor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final static Logger logger = LoggerFactory.getLogger(handleExcel.class);

    @GetMapping("/doExcel")
    public void doExcel(@RequestParam("url") String url) throws Exception {

        logger.info("立项申请文件读取...");

        boolean b = this.printFiles(new File(url));

        String result = b == true ? "立项申请文件成功" : "立项申请文件失败";

        logger.info(result);

    }

    public boolean printFiles(File dir) {
        boolean directory = dir.isDirectory();
        boolean aaaa = dir.isFile();
        if (dir.exists() && dir.isDirectory()) {
            File next[] = dir.listFiles();
            File file = next[0];
            String path = next[0].getPath();
            for (int i = 0; i < next.length; i++) {
                try {
                    //获取当前文件的绝对路径
                    String url = next[0].getPath();
                    //保存文件
                    this.saveFile(url);
                    //递归
                    if (next[i].isDirectory()) {
                        printFiles(next[i]);
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private void saveFile(String url) throws IOException {
        //获取到文件
        FileInputStream fileInputStream = new FileInputStream(url);

        //转为工作表对象
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        Map<String, Object> mapTemp = new LinkedHashMap<>();

        // 获取最后一行的num，即总行数。此处从0开始计数
        int maxRow = sheet.getLastRowNum();
        for (int row = 0; row <= maxRow; row++) {
            String temp = new String();
            //跳过第一行标头
            if (row == 0) continue;

            for (int cell = 0; cell <= sheet.getRow(row).getLastCellNum() - 1; cell++) {
                if (cell == 5) {
                    logger.info("属性：{}", sheet.getRow(row).getCell(cell));
                    temp = String.valueOf(sheet.getRow(row).getCell(cell));
                }
                if (cell == 10) {
                    logger.info("具体内容：{}", sheet.getRow(row).getCell(cell));
                    mapTemp.put(temp, sheet.getRow(row).getCell(cell));
                }
            }
        }

        //数据封装
        LinkedHashMap<String, Object> map = null;
        try {
            map = extracted(mapTemp, new File(url));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //保存
        String pmPrjReqId = Util.insertData(jdbcTemplate, "pm_prj_req");
        jdbcTemplate.update("update pm_prj_req set PRJ_NAME=?,CUSTOMER_UNIT=?,PRJ_MANAGE_MODE_ID=?,BASE_LOCATION_ID=?,PROJECT_TYPE_ID=?,CON_SCALE_TYPE_ID=?,CON_SCALE_QTY=?,FLOOR_AREA=?,QTY_ONE=?,QTY_THREE=?,CON_SCALE_QTY2=?,QTY_TWO=?,CON_SCALE_UOM_ID=?,PRJ_SITUATION=?,INVESTMENT_SOURCE_ID=?,PRJ_TOTAL_INVEST=?,PROJECT_AMT=?,CONSTRUCT_AMT=?,EQUIP_AMT=?,PROJECT_OTHER_AMT=?,LAND_AMT=?,EQUIP_AMT=?,PRJ_REQ_FILE=?,PRJ_REQ_REMARK=?,COMPL_PLAN_DATE=?,COMPL_DATE=?,AUTHOR=?,STAMPED_PRJ_REQ_FILE=?,PRJ_REPLY_PLAN_DATE=?,PRJ_REPLY_DATE=?,PRJ_REPLY_NO=?,CONSTRUCTION_PROJECT_CODE=?,REPLY_FILE=?,PRJ_DESIGN_USER_ID=?,PRJ_COST_USER_ID=? where ID=?",
                map.get("PRJ_NAME"), map.get("CUSTOMER_UNIT"), map.get("PRJ_MANAGE_MODE_ID"), map.get("BASE_LOCATION_ID"), map.get("PROJECT_TYPE_ID"), map.get("CON_SCALE_TYPE_ID"), map.get("CON_SCALE_QTY"), map.get("FLOOR_AREA"), map.get("QTY_ONE"), map.get("QTY_THREE"), map.get("CON_SCALE_QTY2"), map.get("QTY_TWO"), map.get("CON_SCALE_UOM_ID"), map.get("PRJ_SITUATION"), map.get("INVESTMENT_SOURCE_ID"), map.get("PRJ_TOTAL_INVEST"), map.get("PROJECT_AMT"), map.get("CONSTRUCT_AMT"), map.get("EQUIP_AMT"), map.get("PROJECT_OTHER_AMT"), map.get("LAND_AMT"), map.get("EQUIP_AMT"), map.get("PRJ_REQ_FILE"), map.get("PRJ_REQ_REMARK"), map.get("COMPL_PLAN_DATE"), map.get("COMPL_DATE"), map.get("AUTHOR"), map.get("STAMPED_PRJ_REQ_FILE"), map.get("PRJ_REPLY_PLAN_DATE"), map.get("PRJ_REPLY_DATE"), map.get("PRJ_REPLY_NO"), map.get("CONSTRUCTION_PROJECT_CODE"), map.get("REPLY_FILE"), map.get("PRJ_DESIGN_USER_ID"), map.get("PRJ_COST_USER_I"),
                pmPrjReqId);
    }

    private LinkedHashMap<String, Object> extracted(Map<String, Object> map, File file) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<>();

        for (String key : map.keySet()) {
            String result = map.get(key).toString();
            if (key.equals("项目名称")) {
                List<Map<String, Object>> prjName = jdbcTemplate.queryForList("SELECT DISTINCT ID FROM PM_PRJ where NAME=?", result);
                //未查询出数据
                if (CollectionUtils.isEmpty(prjName)) {
                    resultMap.put("PRJ_NAME", "");
                    continue;
                }
                resultMap.put("PRJ_NAME", prjName.get(0).get("ID"));
                continue;
            }
            if (key.equals("业主单位")) {
                List<Map<String, Object>> customerUnit = jdbcTemplate.queryForList("SELECT DISTINCT ID FROM PM_PARTY t  WHERE T.IS_CUSTOMER=1 and NAME=?", result);
                //未查询出数据
                if (CollectionUtils.isEmpty(customerUnit)) {
                    resultMap.put("CUSTOMER_UNIT", null);
                    continue;
                }
                resultMap.put("CUSTOMER_UNIT", customerUnit.get(0).get("ID"));
                continue;
            }
            if (key.equals("项目管理模式")) {
                List<Map<String, Object>> prjManageModeId = jdbcTemplate.queryForList("SELECT DISTINCT ID FROM GR_SET_VALUE t WHERE exists(select 1 from gr_set s where t.gr_set_id=s.id and s.code='management_unit' and NAME=?)", result);
                //未查询出数据
                if (CollectionUtils.isEmpty(prjManageModeId)) {
                    resultMap.put("PRJ_MANAGE_MODE_ID", null);
                    continue;
                }
                resultMap.put("PRJ_MANAGE_MODE_ID", prjManageModeId.get(0).get("ID"));
                continue;
            }
            if (key.equals("建设地点")) {
                List<Map<String, Object>> baseLocationId = jdbcTemplate.queryForList("SELECT DISTINCT ID FROM GR_SET_VALUE t WHERE exists(select 1 from gr_set s where t.gr_set_id=s.id and s.code='base_location' and NAME=?)", result);
                //未查询出数据
                if (CollectionUtils.isEmpty(baseLocationId)) {
                    resultMap.put("BASE_LOCATION_ID", null);
                    continue;
                }
                resultMap.put("BASE_LOCATION_ID", baseLocationId.get(0).get("ID"));
                continue;
            }
            if (key.equals("项目类型")) {
                List<Map<String, Object>> projectTypeId = jdbcTemplate.queryForList("SELECT DISTINCT ID FROM GR_SET_VALUE t WHERE exists(select 1 from gr_set s where t.gr_set_id=s.id and s.code='project_type' and NAME=?)", result);
                //未查询出数据
                if (CollectionUtils.isEmpty(projectTypeId)) {
                    resultMap.put("PROJECT_TYPE_ID", null);
                    continue;
                }
                resultMap.put("PROJECT_TYPE_ID", projectTypeId.get(0).get("ID"));
                continue;
            }
            if (key.equals("建设规模类型")) {
                List<Map<String, Object>> conScaleTypeId = jdbcTemplate.queryForList("SELECT DISTINCT ID FROM GR_SET_VALUE t WHERE exists(select 1 from gr_set s where t.gr_set_id=s.id and s.code='con_scale_type' and NAME=?)", result);
                //未查询出数据
                if (CollectionUtils.isEmpty(conScaleTypeId)) {
                    resultMap.put("CON_SCALE_TYPE_ID", null);
                    continue;
                }
                resultMap.put("CON_SCALE_TYPE_ID", conScaleTypeId.get(0).get("ID"));
                continue;
            }
            if (key.equals("道路长度(m)")) {
                if (StringUtils.isNumeric(result)) {
                    resultMap.put("CON_SCALE_QTY", result);
                    continue;
                }
                resultMap.put("CON_SCALE_QTY", null);
                continue;
            }

            if (key.equals("占地面积(㎡)")) {
                if (StringUtils.isNumeric(result)) {
                    resultMap.put("FLOOR_AREA", result);
                    continue;
                }
                resultMap.put("FLOOR_AREA", null);
                continue;
            }

            if (key.equals("建筑面积(㎡)")) {
                if (StringUtils.isNumeric(result)) {
                    resultMap.put("QTY_ONE", result);
                    continue;
                }
                resultMap.put("QTY_ONE", null);
                continue;
            }

            if (key.equals("海域面积(㎡)")) {
                if (StringUtils.isNumeric(result)) {
                    resultMap.put("QTY_THREE", result);
                    continue;
                }
                resultMap.put("QTY_THREE", null);
                continue;
            }

            if (key.equals("道路宽度(m)")) {
                if (StringUtils.isNumeric(result)) {
                    resultMap.put("CON_SCALE_QTY2", result);
                    continue;
                }
                resultMap.put("CON_SCALE_QTY2", null);
                continue;
            }

            if (key.equals("其他")) {
                if (StringUtils.isNumeric(result)) {
                    resultMap.put("QTY_TWO", result);
                    continue;
                }
                resultMap.put("QTY_TWO", null);
                continue;
            }

            if (key.equals("建设规模单位")) {
                List<Map<String, Object>> conScaleUomId = jdbcTemplate.queryForList("SELECT DISTINCT ID FROM GR_SET_VALUE t WHERE exists(select 1 from gr_set s where t.gr_set_id=s.id and s.code='con_scale_uom' and NAME=?)", result);
                //未查询出数据
                if (CollectionUtils.isEmpty(conScaleUomId)) {
                    resultMap.put("CON_SCALE_UOM_ID", null);
                    continue;
                }
                resultMap.put("CON_SCALE_UOM_ID", conScaleUomId.get(0).get("ID"));
                continue;
            }
            if (key.equals("建设规模及内容")) {
                resultMap.put("PRJ_SITUATION", result);
                continue;
            }

            if (key.equals("投资来源")) {
                List<Map<String, Object>> investmentSourceId = jdbcTemplate.queryForList("SELECT DISTINCT ID FROM GR_SET_VALUE t WHERE exists(select 1 from gr_set s where t.gr_set_id=s.id and s.code='investment_source' and NAME=?)", result);
                //未查询出数据
                if (CollectionUtils.isEmpty(investmentSourceId)) {
                    resultMap.put("INVESTMENT_SOURCE_ID", null);
                    continue;
                }
                resultMap.put("INVESTMENT_SOURCE_ID", investmentSourceId.get(0).get("ID"));
                continue;
            }
            if (key.equals("总投资（万）")) {
                resultMap.put("PRJ_TOTAL_INVEST", result);
                continue;
            }
            if (key.equals("工程费用（万）")) {
                resultMap.put("PROJECT_AMT", result);
                continue;
            }
            if (key.equals("建安工程费(万)")) {
                if (StringUtils.isNumeric(result)) {
                    resultMap.put("CONSTRUCT_AMT", result);
                    continue;
                }
                resultMap.put("CONSTRUCT_AMT", null);
                continue;
            }
            if (key.equals("设备采购费(万)")) {
                resultMap.put("EQUIP_AMT", result);
                continue;
            }
            if (key.equals("工程其他费用(万")) {
                resultMap.put("PROJECT_OTHER_AMT", result);
                continue;
            }
            if (key.equals("土地征拆费用(万")) {
                resultMap.put("LAND_AMT", result);
                continue;
            }
            if (key.equals("预备费(万)")) {
                resultMap.put("EQUIP_AMT", result);
                continue;
            }
            if (key.equals("立项申请材料")) {
                resultMap.put("PRJ_REQ_FILE", result);
                continue;
            }
            if (key.equals("立项申请备注")) {
                resultMap.put("PRJ_REQ_REMARK", result);
                continue;
            }
            if (key.equals("计划完成日期")) {
                if (ObjectUtils.isEmpty(result)) {
                    resultMap.put("COMPL_PLAN_DATE", null);
                    continue;
                }
                resultMap.put("COMPL_PLAN_DATE", format.parse(result));
                continue;
            }
            if (key.equals("完成日期")) {
                if (ObjectUtils.isEmpty(result)) {
                    resultMap.put("COMPL_DATE", null);
                    continue;
                }
                resultMap.put("COMPL_DATE", format.parse(result));
                continue;
            }
            if (key.equals("编制人")) {
                resultMap.put("AUTHOR", result);
                continue;
            }
            if (key.equals("项目建议书")) {
                resultMap.put("STAMPED_PRJ_REQ_FILE", result);
                continue;
            }
            if (key.equals("计划批复日期")) {
                if (ObjectUtils.isEmpty(result)) {
                    resultMap.put("PRJ_REPLY_PLAN_DATE", null);
                    continue;
                }
                resultMap.put("PRJ_REPLY_PLAN_DATE", format.parse(result));
                continue;
            }
            if (key.equals("批复日期")) {
                if (ObjectUtils.isEmpty(result)) {
                    resultMap.put("PRJ_REPLY_DATE", null);
                    continue;
                }
                System.out.println(result);
                resultMap.put("PRJ_REPLY_DATE", format.parse(result));
                continue;
            }
            if (key.equals("批复文号")) {
                resultMap.put("PRJ_REPLY_NO", result);
                continue;
            }
            if (key.equals("工程项目编码")) {
                resultMap.put("CONSTRUCTION_PROJECT_CODE", result);
                continue;
            }
            if (key.equals("批复文件")) {
                //文件存储
                fileSave(result, file);

                resultMap.put("REPLY_FILE", result);
                continue;
            }
            if (key.equals("项目设计岗")) {
                List<Map<String, Object>> maps = jdbcTemplate.queryForList("select ID from AD_USER t where   t.id in (SELECT a.AD_USER_ID from hr_dept_user a left join HR_DEPT b on a.HR_DEPT_ID = b.id WHERE  b.name = '设计管理部') and NAME=?", result);
                if (CollectionUtils.isEmpty(maps)) {
                    resultMap.put("PRJ_DESIGN_USER_ID", null);
                    continue;
                }
                resultMap.put("PRJ_DESIGN_USER_ID", maps.get(0).get("ID"));
                continue;
            }
            if (key.equals("项目成本岗")) {
                List<Map<String, Object>> maps = jdbcTemplate.queryForList("select ID from AD_USER t where  t.id in (SELECT a.AD_USER_ID from hr_dept_user a left join HR_DEPT b on a.HR_DEPT_ID = b.id WHERE  b.name = '成本合约部') and NAME=?", result);
                if (CollectionUtils.isEmpty(maps)) {
                    resultMap.put("PRJ_DESIGN_USER_ID", null);
                    continue;
                }
                resultMap.put("PRJ_COST_USER_ID", maps.get(0).get("ID"));
                continue;
            }
        }
        return resultMap;
    }

    @SneakyThrows
    private void fileSave(String result, File file) {
        String str = result.substring(result.lastIndexOf("/") + 1);
        String[] split = StringUtils.split(str, ".");
        String fileName = split[0];
        String fileType = split[1];

        //获取文件大小
        long length = (file.length()) / 1024;
        //获取当前时间
        String parse = getTime();

        //保存文件记录
        String pmPrjReqId = Util.insertData(jdbcTemplate, "fl_file");
        jdbcTemplate.update("update fl_file set CODE=?,NAME=?,FL_PATH_ID=?,EXT=?,CRT_USER_ID=?,LAST_MODI_USER_ID=?,SIZE_KB=?,UPLOAD_DTTM=?,PHYSICAL_LOCATION=?,DSP_NAME=?,DSP_SIZE=? where ID=?",
                pmPrjReqId, fileName, "99250247095872688", fileType, "99250247095871681", "99250247095871681", length, parse, "/data/qygly/file/test/FileStore/" + str, fileName, length,
                pmPrjReqId);

    }

    private String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String parse = dateFormat.format(date);
        return parse;
    }

}
