package com.cisdi.pms.serviceImpl;

import com.cisdi.pms.domain.ContractApi;
import com.cisdi.pms.domain.ContractCostDetailApi;
import com.cisdi.pms.domain.ContractSigningApi;
import com.cisdi.pms.domain.FileApi;
import com.cisdi.pms.service.ContractService;
import com.cisdi.pms.util.StringUtilsNew;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ContractServiceImpl implements ContractService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ContractApi> getOrder(ContractApi contractApi) {
        String projectId = contractApi.getProjectId();
        String projectName = null;
        StringBuilder sb = new StringBuilder("select id,CONTRACT_CODE,CONTRACT_NAME,CRT_DT,LAST_MODI_DT,SIGN_DATE,FILE_ID_FIVE from PO_ORDER_REQ where status = 'ap' ");
        //查询项目名称
        String sql2 = "select name from pm_prj where id = ? and PROJECT_SOURCE_TYPE_ID = '0099952822476441375'";
        List<Map<String,Object>> list2 = jdbcTemplate.queryForList(sql2,projectId);
        if (!CollectionUtils.isEmpty(list2)){
            projectName = JdbcMapUtil.getString(list2.get(0),"name");
        }
        sb.append(" and (PM_PRJ_ID = '").append(projectId).append("' or PROJECT_NAME_WR = '").append(projectName).append("')");
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString());
        List<ContractApi> contractApiList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            for (Map<String, Object> tmp : list) {
                ContractApi contractApi1 = new ContractApi();
                String contractId = JdbcMapUtil.getString(tmp,"id");
                contractApi1.setContractId(contractId);
                contractApi1.setContractName(JdbcMapUtil.getString(tmp,"CONTRACT_NAME"));
                contractApi1.setContractCode(JdbcMapUtil.getString(tmp,"CONTRACT_CODE"));
                contractApi1.setContractStartTime(StringUtilsNew.replaceByCode(JdbcMapUtil.getString(tmp,"CRT_DT"),"T"," "));
                contractApi1.setContractEndTime(StringUtilsNew.replaceByCode(JdbcMapUtil.getString(tmp,"LAST_MODI_DT"),"T"," "));
                contractApi1.setContractSignTime(StringUtilsNew.replaceByCode(JdbcMapUtil.getString(tmp,"SIGN_DATE"),"T"," "));
                //相对方联系人明细
                List<ContractSigningApi> contractSigningApis = getContractAwardList(contractId);
                if (!CollectionUtils.isEmpty(contractSigningApis)){
                    contractApi1.setContractAwardList(contractSigningApis);
                }
                //费用明细
                List<ContractCostDetailApi> costDetailList = getCostDetailList(contractId);
                if (!CollectionUtils.isEmpty(costDetailList)){
                    contractApi1.setCostDetailList(costDetailList);
                }
                //附件明细
                String fileId = JdbcMapUtil.getString(tmp,"FILE_ID_FIVE");
                List<FileApi> fileList = getFile(fileId);
                if (!CollectionUtils.isEmpty(fileList)){
                    contractApi1.setContractFileList(fileList);
                }
                contractApiList.add(contractApi1);
            }
        }
        return contractApiList;
    }

    /**
     * 合同明细文件信息查询
     * @param fileId 文件id
     * @return 合同文件明细信息
     */
    private List<FileApi> getFile(String fileId) {
        fileId = StringUtilsNew.replaceByCode(fileId,",","','");
        String sql = "select a.id,a.DSP_NAME,a.PHYSICAL_LOCATION,a.UPLOAD_DTTM,a.CRT_USER_ID,(select name from ad_user where id = a.CRT_USER_ID) as userName from fl_file a where a.id in ('"+fileId+"')";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        List<FileApi> fileList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            for (Map<String, Object> tmp : list) {
                FileApi fileApi = new FileApi();
                fileApi.setFileId(JdbcMapUtil.getString(tmp,"id"));
                fileApi.setFileName(JdbcMapUtil.getString(tmp,"DSP_NAME"));
                fileApi.setFileAddress(JdbcMapUtil.getString(tmp,"PHYSICAL_LOCATION"));
                fileApi.setUploadTime(StringUtilsNew.replaceByCode(JdbcMapUtil.getString(tmp,"UPLOAD_DTTM"),"T"," "));
                fileApi.setUserId(JdbcMapUtil.getString(tmp,"CRT_USER_ID"));
                fileApi.setUserName(JdbcMapUtil.getString(tmp,"userName"));
                fileList.add(fileApi);
            }
        }
        return fileList;
    }

    /**
     * 合同费用明细查询
     * @param contractId 合同id
     * @return 合同费用明细信息
     */
    private List<ContractCostDetailApi> getCostDetailList(String contractId) {
        String sql = "select a.CONTRACT_ID,a.COST_TYPE_TREE_ID,a.FEE_DETAIL,a.AMT_ONE,a.AMT_THREE,a.AMT_TWO,b.name from PM_ORDER_COST_DETAIL a left join GR_SET_VALUE b on a.COST_TYPE_TREE_ID = b.id where a.CONTRACT_ID = ?";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,contractId);
        List<ContractCostDetailApi> list2 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            for (Map<String, Object> tmp : list) {
                ContractCostDetailApi contractCostDetailApi = new ContractCostDetailApi();
                contractCostDetailApi.setContractId(contractId);
                contractCostDetailApi.setCostTypeId(JdbcMapUtil.getString(tmp,"COST_TYPE_TREE_ID"));
                contractCostDetailApi.setCostTypeName(JdbcMapUtil.getString(tmp,"name"));
                contractCostDetailApi.setCostDetailName(JdbcMapUtil.getString(tmp,"FEE_DETAIL"));
                contractCostDetailApi.setDutyInclude(new BigDecimal(JdbcMapUtil.getString(tmp,"AMT_ONE")));
                contractCostDetailApi.setDutyLv(new BigDecimal(JdbcMapUtil.getString(tmp,"AMT_THREE")));
                contractCostDetailApi.setDutyNoInclude(new BigDecimal(JdbcMapUtil.getString(tmp,"AMT_TWO")));
                list2.add(contractCostDetailApi);
            }
        }
        return list2;
    }

    /**
     * 合同相对方联系人明细查询
     * @param contractId 合同id
     * @return 合同相对方联系明细
     */
    private List<ContractSigningApi> getContractAwardList(String contractId) {
        String sql = "select PARENT_ID,WIN_BID_UNIT_ONE,OPPO_SITE_LINK_MAN,OPPO_SITE_CONTACT from CONTRACT_SIGNING_CONTACT where PARENT_ID = ?";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,contractId);
        List<ContractSigningApi> list2 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            for (Map<String, Object> tmp : list) {
                ContractSigningApi contractSigningApi = new ContractSigningApi();
                contractSigningApi.setContractId(contractId);
                contractSigningApi.setCounterpartyCompanyName(JdbcMapUtil.getString(tmp,"WIN_BID_UNIT_ONE"));
                contractSigningApi.setCounterpartyContact(JdbcMapUtil.getString(tmp,"OPPO_SITE_LINK_MAN"));
                contractSigningApi.setCounterpartyContactTel(JdbcMapUtil.getString(tmp,"OPPO_SITE_CONTACT"));
                list2.add(contractSigningApi);
            }
        }
        return list2;
    }
}
