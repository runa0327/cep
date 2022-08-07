package com.cisdi.data.transfer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("TestToProdController")
@RestController()
@Slf4j
public class TestToProdController {

    @Autowired
    @Qualifier("testJdbcTemplate")
    JdbcTemplate testJdbcTemplate;

    @Autowired
    @Qualifier("prodJdbcTemplate")
    JdbcTemplate prodJdbcTemplate;

    /**
     * http://localhost:11116/TestToProdController/helloTest
     *
     * @return
     */
    @GetMapping("helloTest")
    public void helloTest() {
        JdbcTemplate jdbcTemplate = testJdbcTemplate;
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select database()");
        for (Map<String, Object> map : list) {
            log.info(map.toString());
        }
    }

    /**
     * http://localhost:11116/TestToProdController/helloProd
     *
     * @return
     */
    @GetMapping("helloProd")
    public void helloProd() {
        JdbcTemplate jdbcTemplate = prodJdbcTemplate;
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select database()");
        for (Map<String, Object> map : list) {
            log.info(map.toString());
        }
    }

    /**
     * http://localhost:11116/TestToProdController/testToProd
     *
     * @return
     */
    @GetMapping("transferData")
    public String testToProd() {
        return "完成！";
    }

}
