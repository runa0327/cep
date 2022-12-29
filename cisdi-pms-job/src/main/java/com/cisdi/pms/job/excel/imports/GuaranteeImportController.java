package com.cisdi.pms.job.excel.imports;

import com.cisdi.pms.job.excel.model.GuaranteeModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.Util;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 保函清单导入
 *
 * @author hgh
 * @date 2022/12/20
 * @apiNote
 */

@Slf4j
@RestController
@RequestMapping("/guaranteeImport")
public class GuaranteeImportController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @SneakyThrows(IOException.class)
    @RequestMapping(value = "/import")
    @Transactional(rollbackFor = Exception.class)
    public String importData(MultipartFile file) {

        //po_guarantee_letter_return_oa_req     保函退还申请       最下方数据
        //po_guarantee_letter_require_req          新增保函申请       全部数据
        //注意：
        //你在做导入的时候，如果那个项目没有，你要最后提示一下哟。项目在项目库里面筛选的时候，过滤条件有status=ap，sourceType那个是系统项目

        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取100条数据 然后返回过来 直接调用使用数据就行
        List<GuaranteeModel> reads = EasyExcelUtil.read(file.getInputStream(), GuaranteeModel.class);
        try {
            int index = 0;
            int cunt = 0;
            for (GuaranteeModel read : reads) {
                index++;
                //筛选项目，没有则不保存，并给出提示
                String querySql = "select count(1) num from pm_prj where status='ap' and NAME =?";
                Map<String, Object> projectCounts = jdbcTemplate.queryForMap(querySql, read.getProjectNameWr());
                log.info("index:{}", index);
                if (Integer.parseInt(projectCounts.get("num").toString()) <= 0) {
                    log.info("当前项目不存在，项目名称:{}", read.getProjectNameWr());
                    cunt++;
                    log.info("不存在项目数量:{}", cunt);
                    continue;
                }

                //保函类型Id获取
                String queryLetterTypeId = "select ID from gr_set_value where NAME = ?";
                List<Map<String, Object>> letterMap = jdbcTemplate.queryForList(queryLetterTypeId, read.getGuaranteeLetterTypeId());
                if (letterMap.size() > 0 && ObjectUtils.isEmpty(letterMap.get(0).get("ID"))) {
                    read.setGuaranteeLetterTypeId(letterMap.get(0).get("ID").toString());
                } else {
                    read.setGuaranteeLetterTypeId(null);
                }

                //费用类型Id获取
                String queryCostTypeId = "select ID from gr_set_value where NAME = ?";
                List<Map<String, Object>> costMap = jdbcTemplate.queryForList(queryCostTypeId, read.getGuaranteeLetterTypeId());
                if (costMap.size() > 0 && ObjectUtils.isEmpty(costMap.get(0).get("ID"))) {
                    read.setGuaranteeCostTypeId(costMap.get(0).get("ID").toString());
                } else {
                    read.setGuaranteeCostTypeId(null);
                }

                this.insertReq(read);
                if (index >= (reads.size() - 4)) {
                    this.insertOaReq(read);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "导入失败";
        }

        return "导入成功";
    }

    /**
     * po_guarantee_letter_return_oa_req     保函退还申请       最下方数据
     *
     * @param guaranteeModel
     */
    private void insertOaReq(GuaranteeModel guaranteeModel) {
        //SUPPLIER  GUARANTEE_AMT  缺少这个字段
        String sql = "update po_guarantee_letter_return_oa_req set GUARANTEE_LETTER_TYPE_ID=?,GUARANTEE_COST_TYPE_ID=?,PROJECT_NAME_WR=?,APPLICANT=?,GUARANTEE_MECHANISM=?,GUARANTEE_CODE=? ,AMT_WR_ONE=?,GUARANTEE_START_DATE=?,GUARANTEE_END_DATE=?,REMARK_LONG_ONE=?,AUTHOR=? where ID=?";
        String id = Util.insertData(jdbcTemplate, "po_guarantee_letter_return_oa_req");
        jdbcTemplate.update(sql,
                guaranteeModel.getGuaranteeLetterTypeId(), guaranteeModel.getGuaranteeCostTypeId(), guaranteeModel.getProjectNameWr()
                , guaranteeModel.getSupplier(), guaranteeModel.getGuaranteeMechanism(), guaranteeModel.getGuaranteeCode(),guaranteeModel.getGuaranteeAmt(),guaranteeModel.getGuaranteeStartDate()
                ,guaranteeModel.getGuaranteeEndDate(),guaranteeModel.getRemark(),guaranteeModel.getAuthor(),
                id);
    }

    /**
     * po_guarantee_letter_require_req          新增保函申请       全部数据
     *
     * @param guaranteeModel
     */
    private void insertReq(GuaranteeModel guaranteeModel) {
        //guaranteeModel.getGuaranteeCostTypeId()  缺少这个字段
        String sql = "update po_guarantee_letter_require_req set GUARANTEE_LETTER_TYPE_ID=?,PM_EXP_TYPE_IDS=?,PROJECT_NAME_WR=?,SUPPLIER=?,GUARANTEE_MECHANISM=?,GUARANTEE_CODE=?,GUARANTEE_AMT=?,GUARANTEE_START_DATE=?,GUARANTEE_END_DATE=?,REMARK_ONE=?,BENEFICIARY=? where ID=?";
        String id = Util.insertData(jdbcTemplate, "po_guarantee_letter_require_req");
        jdbcTemplate.update(sql,
                guaranteeModel.getGuaranteeLetterTypeId(), guaranteeModel.getGuaranteeCostTypeId(), guaranteeModel.getProjectNameWr()
                , guaranteeModel.getSupplier(), guaranteeModel.getGuaranteeMechanism(), guaranteeModel.getGuaranteeCode(),guaranteeModel.getGuaranteeAmt(),guaranteeModel.getGuaranteeStartDate()
                ,guaranteeModel.getGuaranteeEndDate(),guaranteeModel.getRemark(),guaranteeModel.getAuthor(),
                id);
    }

}
