package demo.ext.demos;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.interaction.EntityRecord;
import demo.ext.model.TestStu;

import java.util.List;

public class CRUD {

    public void crud() {
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            // 3种CRUD方式，按需选用：
            crudType1(entityRecord);
            crudType2(entityRecord);
            crudType3(entityRecord);
        }
    }

    /**
     * SQL（或称jdbc）方式。最为灵活（如：表名可以使用变量拼接；任意复杂的SQL都可写出来【建议少写复杂SQL】）。
     */
    public void crudType1(EntityRecord entityRecordStu) {
        String stuId = entityRecordStu.csCommId;

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        // t.ver=t.ver+1,t.ts=now()属于规范、不要忘写：
        myJdbcTemplate.update("update test_stu t set t.ver=t.ver+1,t.ts=now(),t.code=?,t.test_bool=? where t.id=?", "123", true, stuId);

        // 还可使用MyNamedParameterJdbcTemplate访问：
        // MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.getMyNamedParameterJdbcTemplate();
    }

    /**
     * Crud方式。伪SQL。比较灵活。
     */
    public void crudType2(EntityRecord entityRecordStu) {
        String stuId = entityRecordStu.csCommId;

        Crud.from("TEST_STU").where().eq("id", stuId).update().set("code", "123").set("test_bool", true).exec();
    }

    /**
     * ORM方式（面向对象方式）。尽管无SQL，但不灵活。
     */
    public void crudType3(EntityRecord entityRecordStu) {
        String stuId = entityRecordStu.csCommId;

        TestStu stu = TestStu.selectById(stuId);
        stu.setCode("123");
        stu.setTestBool(true);
        stu.updateById();
    }

}
