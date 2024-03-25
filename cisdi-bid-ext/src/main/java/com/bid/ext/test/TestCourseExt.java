package com.bid.ext.test;

import com.bid.ext.model.TestCourse;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;

import java.util.UUID;

public class TestCourseExt {
    public void gen100() {

        deleteAll();
        for (int i = 0; i < 100; i++) {
            // f1();
            // f2();
            f3();
        }
    }

    /**
     * 方法一。SQL，不推荐。
     */
    private void f1() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        String newId = ExtJarHelper.insertData("TEST_COURSE");
        myJdbcTemplate.update("UPDATE TEST_COURSE T SET T.NAME=? WHERE T.ID=?", getRandomName(), newId);

        // MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        //
        // String newId = ExtJarHelper.insertData("TEST_COURSE");
        // HashMap<String, Object> paramMap = new HashMap<>();
        // paramMap.put("name", getRandomName());
        // paramMap.put("id2", newId);
        // paramMap.put("ids", Arrays.asList("1", "2", "3"));
        // myNamedParameterJdbcTemplate.update("UPDATE TEST_COURSE T SET T.NAME=:name WHERE T.ID in :ids", paramMap);
    }

    private String getRandomName() {
        return "课程" + UUID.randomUUID();
    }

    private void f2() {
        String newId = Crud.from("TEST_COURSE").insertData();
        Crud.from("TEST_COURSE").where().eq("ID", newId).update().set("NAME", getRandomName() + "方法2").exec();
    }

    private void deleteAll() {
        // Where where = new Where();
        // where.startWith(TestCourse.Cols.NAME, "课程0");
        // TestCourse.deleteByWhere(where);

        TestCourse.deleteByWhere(null);
    }

    private void f3() {
        TestCourse testCourse = TestCourse.newData();
        testCourse.setName(getRandomName() + "方法3");
        testCourse.insertById();
    }
}
