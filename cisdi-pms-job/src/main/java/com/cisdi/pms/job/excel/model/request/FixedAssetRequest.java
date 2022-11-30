package com.cisdi.pms.job.excel.model.request;

import lombok.Data;

import java.util.List;

/**
 * @author dlt
 * @date 2022/11/29 周二
 */

@Data
public class FixedAssetRequest {
    //项目id
    private List<String> projectIds;
}
