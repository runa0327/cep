package demo.ext.demos;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.*;
import com.qygly.shared.util.SharedUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Link {

    /**
     * 联动。
     * 挂接在“测试班级”实体联动上。
     * 可在“测试班级学生老师（主细）”等视图查看效果。
     * 效果：当班级触发联动时，改变班级的代码，删除班级下的所有学生，新建2条学生（其中1条改变值），改变班级下的所有老师的代码。
     */
    public void link() {
        // 当前联动的记录（及子孙记录）列表：
        // List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        // 因接口规范为批量，故从列表中获取首个即可：
        // EntityRecord entityRecord = entityRecordList.get(0);

        // 级联数据（即：主记录及子孙记录）列表：
        List<EntityRecord> casDataList = ExtJarHelper.getCasDataList();
        // 因接口规范为批量，故从列表中获取首个即可：
        EntityRecord casData = casDataList.get(0);


        LinkResult linkResult = new LinkResult();
        linkResult.attMap = new LinkedHashMap<>();

        {
            LinkResultAtt linkResultAtt = new LinkResultAtt();
            linkResult.attMap.put("CODE", linkResultAtt);

            linkResultAtt.data = new Data();
            linkResultAtt.data.value = "xyz123";
        }

        linkResult.childResultListMap = new LinkedHashMap<>();

        // 学生:
        {
            String viewPartCode = "STU";

            List<LinkResult> childResultList = new ArrayList<>();
            linkResult.childResultListMap.put(viewPartCode, childResultList);

            // 删除已有各条：
            {
                List<EntityRecord> stuList = casData.childData == null ? null : casData.childData.get(viewPartCode);
                if (SharedUtil.notEmpty(stuList)) {
                    stuList.forEach(stu -> {
                        LinkResult childResult = new LinkResult();
                        childResultList.add(childResult);

                        childResult.processType = LinkResultProcessType.DELETE;
                        childResult.csCommId = stu.csCommId;
                    });
                }
            }

            // 新建1条：
            {
                LinkResult childResult = new LinkResult();
                childResultList.add(childResult);

                childResult.processType = LinkResultProcessType.CREATE;
            }

            // 再新建1条（改变值）：
            {
                LinkResult childResult = new LinkResult();
                childResultList.add(childResult);

                childResult.processType = LinkResultProcessType.CREATE;
                childResult.attMap = new LinkedHashMap<>();

                {
                    LinkResultAtt linkResultAtt = new LinkResultAtt();
                    childResult.attMap.put("TEST_INT", linkResultAtt);
                    linkResultAtt.data = new Data();
                    linkResultAtt.data.value = "123";
                }

                {
                    LinkResultAtt linkResultAtt = new LinkResultAtt();
                    childResult.attMap.put("TEST_DOUBLE", linkResultAtt);
                    linkResultAtt.data = new Data();
                    linkResultAtt.data.value = "4.56";
                }
            }
        }
        // 学生。

        // 老师:
        {
            String viewPartCode = "TEACHER";

            List<LinkResult> childResultList = new ArrayList<>();
            linkResult.childResultListMap.put(viewPartCode, childResultList);

            // 修改已有各条（暂无效果）：
            {
                List<EntityRecord> teacherList = casData.childData == null ? null : casData.childData.get(viewPartCode);
                if (SharedUtil.notEmpty(teacherList)) {
                    teacherList.forEach(teacher -> {
                        LinkResult childResult = new LinkResult();
                        childResultList.add(childResult);

                        childResult.csCommId = teacher.csCommId;

                        // 修改时无需processType，直接设置attMap即可：
                        // childResult.processType = LinkResultProcessType.CREATE;
                        // childResult.processType = LinkResultProcessType.DELETE;
                        childResult.attMap = new LinkedHashMap<>();
                        {
                            LinkResultAtt linkResultAtt = new LinkResultAtt();
                            childResult.attMap.put("CODE", linkResultAtt);
                            linkResultAtt.data = new Data();
                            linkResultAtt.data.value = "999";
                        }

                    });
                }
            }
        }
        // 老师。

        // 若当前是某条子孙记录触发的联动，又想从根上开始应用（即：子孙记录联动主记录及子孙记录），则令startWithRoot为true：
        // linkResult.startWithRoot = true;

        ExtJarHelper.setReturnValue(linkResult);
    }

}
