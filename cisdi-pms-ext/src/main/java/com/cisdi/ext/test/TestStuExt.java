package com.cisdi.ext.test;


import com.cisdi.ext.model.TestStu;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TestStuExt {
    public void test() {
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
}
