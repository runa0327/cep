package com.cisdi.pms.controller;

import com.cisdi.pms.api.ContractApi;
import com.cisdi.pms.resultCommen.AjaxResult;
import com.cisdi.pms.service.ContractService;
import com.qygly.shared.util.SharedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ContractController
 * @package com.cisdi.pms.controller
 * @description 合同
 * @date 2023/1/13
 */
@RestController
@RequestMapping("contract")
public class ContractController {

    @Resource
    private ContractService contractService;

    @GetMapping(value = "/getOrder")
    public AjaxResult getOrder(ContractApi contractApi){
        StringBuilder sb = new StringBuilder();
        if (SharedUtil.isEmptyString(contractApi.getProjectId())){
            sb.append("项目id为空");
        }
        if (sb.length() > 0 || !"".equals(sb.toString())){
            return AjaxResult.error(sb.toString());
        } else {
            List<ContractApi> list = contractService.getOrder(contractApi);
            return AjaxResult.success(list);
        }
    }
}
