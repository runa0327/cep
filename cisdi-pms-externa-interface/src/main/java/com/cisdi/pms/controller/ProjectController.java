package com.cisdi.pms.controller;

import com.cisdi.pms.domain.PmPrj;
import com.cisdi.pms.resultCommen.AjaxResult;
import com.cisdi.pms.service.PmPrjService;
import com.qygly.shared.util.SharedUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProjectController
 * @package com.cisdi.pms.controller
 * @description 项目库基础信息
 * @date 2023/1/13
 */
@RestController
@RequestMapping("prj")
public class ProjectController {

    @Resource
    private PmPrjService pmPrjService;

    @GetMapping("aa")
    public String test(){
        return "adas";
    }

    @GetMapping(value = "/getBaseProject")
    public AjaxResult getBaseProject(PmPrj pmPrj){
        StringBuilder sb = new StringBuilder();
        if (pmPrj.getPageIndex() == null){
            sb.append("当前页码不能为空!");
        }
        if (pmPrj.getPageSize() == null){
            sb.append("每页条数不能为空!");
        }
        if (SharedUtil.isEmptyString(sb.toString())){
            List<PmPrj> list = pmPrjService.getBaseProject(pmPrj);
            int total = pmPrjService.getBaseProjectCount(pmPrj);
            Map<String,Object> map = new HashMap<>();
            map.put("data",list);
            map.put("total",total);
            return AjaxResult.success(map);
        } else {
            return AjaxResult.error(sb.toString());
        }
    }
}
