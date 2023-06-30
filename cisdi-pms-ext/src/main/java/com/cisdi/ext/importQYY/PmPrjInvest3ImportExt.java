package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.PmPrjInvest3Import;
import com.cisdi.ext.model.PmPrjInvest3;
import com.cisdi.ext.util.ImportUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 预算财评导入
 * @author dlt
 * @date 2023/6/30 周五
 */
public class PmPrjInvest3ImportExt extends ImportUtil {


    public void importAccount() throws Exception {
        new PmPrjInvest3ImportExt().importAccountCommon();
    }

    @Override
    public Class getImportClass() {
        return PmPrjInvest3Import.class;
    }

    @Override
    public List<Map<String, Object>> getOldData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> oldImports = myJdbcTemplate.queryForList("select id ,PM_PRJ_ID prjId from PM_PRJ_INVEST3 where status = 'AP' and IS_IMPORT = 1 ");
        return oldImports;
    }

    @Override
    public boolean doImport(Object dlt, List<Map<String, Object>> oldImportDataList) {
        PmPrjInvest3Import invest3Import = (PmPrjInvest3Import) dlt;
        PmPrjInvest3 invest3 = PmPrjInvest3.newData();
        //比对是否导入过
        boolean needUpdate = false;
        if (!CollectionUtils.isEmpty(oldImportDataList)){
            Optional<Map<String, Object>> samePrjIdData = oldImportDataList.stream()
                    .filter(singleData -> invest3Import.getPmPrjId().equals(singleData.get("prjId")))
                    .findAny();
            if (samePrjIdData.isPresent()){
                needUpdate = true;
                invest3.setId(samePrjIdData.get().get("id").toString());
            }
        }
        BeanUtils.copyProperties(invest3Import,invest3,"id");
        if (needUpdate) {
            invest3.updateById();
        }else{
            invest3.insertById();
        }
        return true;
    }


//    @Data
//    public static class User{
//        public String name;
//        public String id;
////        public String address;
//    }
//
//    @Data
//    @ToString
//    public static class Person{
//        public String name;
//        public String id;
//        public String address;
//    }
//    public static void main(String[] args) {
//        User user = new User();
//        user.setName("a");
//        user.setId("b");
////        user.setAddress("c");
//        Person person = new Person();
//        person.setName("d");
//        person.setId("e");
//        person.setAddress("f");
//        BeanUtils.copyProperties(user,person,"id");
//        System.out.println(person);
//        System.out.println(user);
//    }
}
