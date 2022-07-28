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

@RequestMapping("InitController")
@RestController
@Slf4j
public class InitController {

    public static final String CPMS = "CPMS";
    @Autowired
    @Qualifier("cpmsJdbcTemplate")
    JdbcTemplate cpmsJdbcTemplate;

    @Autowired
    @Qualifier("testJdbcTemplate")
    JdbcTemplate testJdbcTemplate;

    @Autowired
    @Qualifier("seedJdbcTemplate")
    JdbcTemplate seedJdbcTemplate;


    @GetMapping("helloCpms")
    public void helloCpms() {
        JdbcTemplate jdbcTemplate = cpmsJdbcTemplate;
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select database()");
        for (Map<String, Object> map : list) {
            log.info(map.toString());
        }
    }

    @GetMapping("helloTest")
    public void helloTest() {
        JdbcTemplate jdbcTemplate = testJdbcTemplate;
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select database()");
        for (Map<String, Object> map : list) {
            log.info(map.toString());
        }
    }

    @GetMapping("helloSeed")
    public void helloSeed() {
        JdbcTemplate jdbcTemplate = seedJdbcTemplate;
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select database()");
        for (Map<String, Object> map : list) {
            log.info(map.toString());
        }
    }

    @GetMapping("tranferUserToSeed")
    public String tranferUserToSeed() {
        List<Map<String, Object>> list = cpmsJdbcTemplate.queryForList("select t.user_id,t.user_name,t.nick_name,t.phonenumber from sys_user t where t.user_name!='admin'");
        for (Map<String, Object> row : list) {
            String userId = Util.insertData(seedJdbcTemplate, "AD_USER");
            seedJdbcTemplate.update("update ad_user t set t.code=?,t.name=?,t.mobile=?,t.SRC=?,t.SRC_RECORD_ID=?,t.SYNC_DTTM=now() where t.id=?",
                    row.get("user_name"),
                    row.get("nick_name"),
                    row.get("phonenumber"),
                    CPMS,
                    row.get("user_id"),
                    userId);
        }
        return "完成！";
        // TODO 还要手工批量修改密码为1。
    }

    @GetMapping("tranferUserToTest")
    public String tranferUserToTest() {
        List<Map<String, Object>> seedUserList = seedJdbcTemplate.queryForList("select * from ad_user t where t.SRC=?", CPMS);
        for (Map<String, Object> seedUser : seedUserList) {
            String userId = Util.insertData(testJdbcTemplate, "AD_USER");
            testJdbcTemplate.update("update ad_user t set t.code=?,t.name=?,t.mobile=?,t.SRC=?,t.SRC_RECORD_ID=?,t.SYNC_DTTM=now(),t.id=? where t.id=?",
                    seedUser.get("code"),
                    seedUser.get("name"),
                    seedUser.get("mobile"),
                    CPMS,
                    seedUser.get("SRC_RECORD_ID"),
                    seedUser.get("id"),
                    userId);
        }
        return "完成！";
    }

    @GetMapping("allowUserToTest")
    public String allowUserToTest() {
        List<Map<String, Object>> seedUserList = seedJdbcTemplate.queryForList("select * from ad_user t where t.SRC=?", CPMS);
        for (Map<String, Object> seedUser : seedUserList) {
            String newId = Util.insertData(seedJdbcTemplate, "ad_user_org");
            seedJdbcTemplate.update("update ad_user_org t set t.ad_user_id=?,t.ad_org_id='test',t.IS_DEFAULT=1 where t.id=?",
                    seedUser.get("id"),
                    newId);
        }
        return "完成！";
    }

    @GetMapping("tranferDeptToTest")
    public String tranferDeptToTest() {
        List<Map<String, Object>> cpmsUserList = cpmsJdbcTemplate.queryForList("select t.dept_id,t.parent_id,t.dept_name,t.order_num from sys_dept t");
        for (Map<String, Object> cpmsUser : cpmsUserList) {
            String newId = Util.insertData(testJdbcTemplate, "hr_dept");
            testJdbcTemplate.update("update hr_dept t set t.name=?,t.remark=?,t.seq_no=? where t.id=?",
                    cpmsUser.get("dept_name"),
                    cpmsUser.get("dept_id"),
                    cpmsUser.get("order_num"),
                    newId);
        }
        return "完成！";
    }

    @GetMapping("organizeDeptInTest")
    public String organizeDeptInTest() {
        List<Map<String, Object>> list = cpmsJdbcTemplate.queryForList("select t.dept_id,t.parent_id,t.dept_name,t.order_num from sys_dept t");

        for (Map<String, Object> row : list) {
            testJdbcTemplate.update("update hr_dept c join hr_dept p on c.remark=? and p.remark=? set c.hr_dept_pid=p.id",
                    row.get("dept_id").toString(),
                    row.get("parent_id").toString());
        }

        return "完成！";
    }

    @GetMapping("relateDeptWithUserInTest")
    public String relateDeptWithUserInTest() {
        List<Map<String, Object>> list = cpmsJdbcTemplate.queryForList("select t.user_id,t.dept_id from sys_user t where t.user_name!='admin'");

        for (Map<String, Object> row : list) {
            String newId = Util.insertData(testJdbcTemplate, "hr_dept_user");
//            testJdbcTemplate.update("update hr_dept_user t join hr_dept d on t.id=? and d.remark=? join ad_user u on u.SRC_RECORD_ID=? and u.src=? set t.hr_dept_id=d.id and t.ad_user_id=u.id",
//                    newId,
//                    row.get("dept_id").toString(),
//                    row.get("user_id").toString(),
//                    CPMS
//            );
            int update = testJdbcTemplate.update("update hr_dept_user t set t.HR_DEPT_ID=(select d.id from hr_dept d where d.remark=?),t.AD_USER_ID=(select u.id from ad_user u where u.SRC_RECORD_ID=? and u.src='CPMS') where t.id=?",
                    row.get("dept_id").toString(),
                    row.get("user_id").toString(),
                    newId
            );

            log.info("已更新：{}", update);
        }

        return "完成！";
    }

    @GetMapping("tranferSetToTest")
    public String tranferSetToTest() {
        List<Map<String, Object>> list = cpmsJdbcTemplate.queryForList("select t.dict_id,t.dict_type,t.dict_name from sys_dict_type t");
        for (Map<String, Object> row : list) {
            String newId = Util.insertData(testJdbcTemplate, "gr_set");
            testJdbcTemplate.update("update gr_set t set t.code=?,t.name=?,t.remark=?,t.cpms_id=?,t.cpms_code=? where t.id=?",
                    row.get("dict_type"),
                    row.get("dict_name"),
                    row.get("dict_id"),
                    row.get("dict_id"),
                    row.get("dict_type"),
                    newId);
        }

        List<Map<String, Object>> list2 = cpmsJdbcTemplate.queryForList("select t.dict_code,t.dict_value,t.dict_label,t.dict_sort,t.dict_type from sys_dict_data t");
        for (Map<String, Object> row2 : list2) {
            String newId = Util.insertData(testJdbcTemplate, "gr_set_value");
            testJdbcTemplate.update("update gr_set_value t set t.code=?,t.name=?,t.remark=?,t.cpms_id=?,t.cpms_code=?,t.seq_no=? where t.id=?",
                    row2.get("dict_value"),
                    row2.get("dict_label"),
                    row2.get("dict_code"),
                    row2.get("dict_code"),
                    row2.get("dict_value"),
                    row2.get("dict_sort"),
                    newId);

            String dict_type = row2.get("dict_type").toString();
            int update = testJdbcTemplate.update("update gr_set_value v join gr_set s on v.id=? and s.cpms_code=? set v.gr_set_id=s.id", newId, dict_type);
            log.info("已更新：{}", update);
        }

        return "完成！";
    }

    @GetMapping("createRoleForUserInTest")
    public String createRoleForUserInTest() {
        List<Map<String, Object>> list = testJdbcTemplate.queryForList("select * from ad_user t where t.SRC='CPMS'");
        for (Map<String, Object> row : list) {
            String newId = Util.insertData(testJdbcTemplate, "ad_role");
            String newId2 = Util.insertData(testJdbcTemplate, "ad_role_user");

            String deptName = testJdbcTemplate.queryForList("select ifnull(hd.NAME,'未分配部门') DEPT_NAME from ad_user u join hr_dept_user du on u.id=? and u.id=du.AD_USER_ID left join hr_dept hd on du.HR_DEPT_ID = hd.ID limit 1", row.get("id")).get(0).get("DEPT_NAME").toString();

            int update = testJdbcTemplate.update("update ad_role r join ad_role_user ru on r.id=? and ru.id=? set r.name=?,ru.AD_ROLE_ID=r.id,ru.AD_USER_ID=?",
                    newId,
                    newId2,
                    deptName + "-" + row.get("name").toString(),
                    row.get("id"));

            log.info("已更新：{}", update);
        }

        return "完成！";
    }

}
