package com.cisdi.ext.model;

import java.util.List;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ResponseModel
 * @package com.cisdi.ext.model
 * @description
 * @date 2022/10/26
 */
public class ResponseModel<T> {

    public Integer total;

    public List<T> object;
}
