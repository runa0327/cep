package com.cisdi.pms.model;

import lombok.Data;

import java.util.List;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title News
 * @package com.cisdi.pms.model
 * @description
 * @date 2023/7/17
 */
@Data
public class NewsInfo {

    private List<ArticlesInfo> articles;
}
