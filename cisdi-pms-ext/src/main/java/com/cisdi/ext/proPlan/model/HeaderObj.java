package com.cisdi.ext.proPlan.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title HeaderObj
 * @package com.cisdi.ext.proPlan.model
 * @description
 * @date 2023/5/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeaderObj {
    public String name;
    public String izDisplay;
}
