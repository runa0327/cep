package com.cisdi.ext.test;


import com.cisdi.ext.model.TestStu;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.debug.event.AsyncConfig;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

@Slf4j
public class TestStuExt {
    public void test() {
        if ("1".equals("1")) {
            f1();
        }

        // TestStu testStu = TestStu.newData();
        // testStu.name = "新学生";
        // testStu.insertById(null, null, true);

        // Where where = new Where();
        // where.contain(TestStu.Cols.NAME, "学生");
        // List<TestStu> testStuList = TestStu.selectByWhere(where, null, null);
        // if (!SharedUtil.isEmptyList(testStuList)) {
        //     for (TestStu stu : testStuList) {
        //         stu.name = stu.name.replace("学生", "我的学生");
        //         stu.updateById(null, null, true);
        //     }
        // }

        Where where = new Where();
        where.startWith(TestStu.Cols.NAME, "A");
        List<TestStu> testStus = TestStu.selectByWhere(where, null, null);
        if (!SharedUtil.isEmptyList(testStus)) {
            for (TestStu stus : testStus) {
                stus.deleteById();
            }
        }
    }

    private void f1() {
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();

        {
            List<Map<String, Object>> list = myNamedParameterJdbcTemplate.queryForList("select * from test_stu t", (Map<String, ?>) null);
            List<Map<String, Object>> list2 = myNamedParameterJdbcTemplate.queryForList("select * from test_stu t where t.crt_user_id=@uid", new HashMap<>());
            Map<String, Object> map = new HashMap<>();
            map.put("name", "123");
            List<Map<String, Object>> list3 = myNamedParameterJdbcTemplate.queryForList("select * from test_stu t where t.crt_user_id=@uid and t.name=:name", map);
        }

        {
            List<Map<String, Object>> list = myNamedParameterJdbcTemplate.queryForList("select * from test_stu t", (SqlParameterSource) null);
            List<Map<String, Object>> list2 = myNamedParameterJdbcTemplate.queryForList("select * from test_stu t where t.crt_user_id=@uid", new EmptySqlParameterSource());
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("name", "123");
            List<Map<String, Object>> list3 = myNamedParameterJdbcTemplate.queryForList("select * from test_stu t where t.crt_user_id=@uid and t.name=:name", mapSqlParameterSource);
        }

        {
            Map<String, Object> map = myNamedParameterJdbcTemplate.queryForMap("select * from test_stu t limit 1", (Map<String, ?>) null);
            Map<String, Object> map2 = myNamedParameterJdbcTemplate.queryForMap("select * from test_stu t where t.crt_user_id=@uid limit 1", new HashMap<>());
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("name", "123");
            Map<String, Object> map3 = myNamedParameterJdbcTemplate.queryForMap("select * from test_stu t where t.crt_user_id=@uid and t.name=:name limit 1", mapSqlParameterSource);
        }

        {
            Map<String, Object> map = myNamedParameterJdbcTemplate.queryForMap("select * from test_stu t limit 1", (SqlParameterSource) null);
            Map<String, Object> map2 = myNamedParameterJdbcTemplate.queryForMap("select * from test_stu t where t.crt_user_id=@uid limit 1", new EmptySqlParameterSource());
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("name", "123");
            Map<String, Object> map3 = myNamedParameterJdbcTemplate.queryForMap("select * from test_stu t where t.crt_user_id=@uid and t.name=:name limit 1", mapSqlParameterSource);
        }

        {
            int i = myNamedParameterJdbcTemplate.update("update test_stu t set t.remark='xyz'", (Map<String, ?>) null);
            int i2 = myNamedParameterJdbcTemplate.update("update test_stu t set t.remark='xyz' where t.crt_user_id=@uid", new HashMap<>());
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("name", "123");
            int i3 = myNamedParameterJdbcTemplate.update("update test_stu t set t.remark='xyz' where t.crt_user_id=@uid and t.name=:name", mapSqlParameterSource);
        }

        {
            int i = myNamedParameterJdbcTemplate.update("update test_stu t set t.remark='xyz'", (SqlParameterSource) null);
            int i2 = myNamedParameterJdbcTemplate.update("update test_stu t set t.remark='xyz' where t.crt_user_id=@uid", new EmptySqlParameterSource());

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("name", "123");
            int i3 = myNamedParameterJdbcTemplate.update("update test_stu t set t.remark='xyz' where t.crt_user_id=@uid and t.name=:name", mapSqlParameterSource);
        }

    }

    public static void main(String[] args) {
        AsyncConfig config = new AsyncConfig();
        Executor executor = config.getAsyncExecutor();
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                System.out.println("12312");
            });
        }
    }
}
