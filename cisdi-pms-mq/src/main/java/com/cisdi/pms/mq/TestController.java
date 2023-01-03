package com.cisdi.pms.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title TestController
 * @package com.cisdi.pms.mq
 * @description
 * @date 2022/12/30
 */
@RestController
public class TestController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("sayHello")
    public String sayHello() {
        String db = jdbcTemplate.queryForMap("select database() db").get("db").toString();
        return "连接的数据库是：" + db;
    }
}
