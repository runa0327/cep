package com.cisdi.ext.test;


import com.cisdi.ext.model.TestStu;
import com.qygly.ext.jar.helper.debug.event.AsyncConfig;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Executor;

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
