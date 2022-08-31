package com.cisdi.data.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmImgProController
 * @package com.cisdi.data.transfer
 * @description 形象进度数据同步
 * @date 2022/8/31
 */
@RestController
@RequestMapping("pmImg")
public class PmImgProController {

    @Autowired
    @Qualifier("cpmsJdbcTemplate")
    JdbcTemplate cpmsJdbcTemplate;

    @Autowired
    @Qualifier("testJdbcTemplate")
    JdbcTemplate testJdbcTemplate;


    @GetMapping("transferData")
    public String transferData() {
        //------------形象进度分类BEGIN----------------------
        //首先清除已经同步过的数据
        testJdbcTemplate.update("delete from PM_IMG_PRO where CPMS_ID is not null");
        testJdbcTemplate.update("delete from PM_IMG_PRO_TYPE where CPMS_ID is not null");
        List<Map<String, Object>> typeList = cpmsJdbcTemplate.queryForList("select id,type_id,type_name,ifnull(parent_id,'0') as parent_id,project_id,del_flag,create_by,create_time,update_by,update_time,version,remark from see_scene_type where del_flag = '0'");
        List<Map<String, Object>> projectList = testJdbcTemplate.queryForList("select * from PM_PRJ where `STATUS` = 'ap'");

        typeList.stream().filter(p -> Objects.equals("0", String.valueOf(p.get("parent_id")))).peek(m -> {
            String projectId = null;
            Optional<Map<String, Object>> optional = projectList.stream().filter(p -> Objects.equals(String.valueOf(m.get("project_id")), String.valueOf(p.get("CPMS_UUID")))).findAny();
            if (optional.isPresent()) {
                projectId = String.valueOf(optional.get().get("ID"));
            }
            String id = Util.insertData(testJdbcTemplate, "PM_IMG_PRO_TYPE");
            testJdbcTemplate.update("update PM_IMG_PRO_TYPE set `NAME`=?,REMARK=?,CPMS_ID=?,CPMS_UUID=?,PM_PRJ_ID=? where ID=?", m.get("type_name"), m.get("remark"), m.get("id"), m.get("type_id"), projectId, id);
            getChildren(m, typeList, id, projectId);
        }).collect(Collectors.toList());
        //------------形象进度分类END----------------------

        //------------形象进度BEGIN----------------------
        List<Map<String, Object>> phoneList = cpmsJdbcTemplate.queryForList("select * from see_scene_photo where del_flag = '0'");
        //同步文件后--根据关联查询出文件ID
        List<Map<String, Object>> fileList = testJdbcTemplate.queryForList("select fl.*,CPMS_ID,CPMS_UUID from pf_file pf left join fl_file fl on pf.FL_FILE_ID = fl.ID where pf.`STATUS`='ap'");
        //形象进度分类
        List<Map<String, Object>> typeLists = testJdbcTemplate.queryForList("select * from PM_IMG_PRO_TYPE where `STATUS` = 'ap'");

        phoneList.forEach(item -> {
            String typeId = null;
            String projectId = null;
            String coverImage = null;
            String panorama = null;

            Optional<Map<String, Object>> optional = typeLists.stream().filter(o -> Objects.equals(String.valueOf(item.get("type_id")), String.valueOf(o.get("CPMS_UUID")))).findAny();
            if (optional.isPresent()) {
                typeId = String.valueOf(optional.get().get("ID"));
                projectId = String.valueOf(optional.get().get("PM_PRJ_ID"));
                projectId = "null".equals(projectId) ? null : projectId;
            }
            //处理文件，取到ID值
            Optional<Map<String, Object>> fileOptional = fileList.stream().filter(n -> Objects.equals(String.valueOf(n.get("CPMS_UUID")), String.valueOf(item.get("small_file_id")))).findAny();
            if (fileOptional.isPresent()) {
                coverImage = String.valueOf(fileOptional.get().get("ID"));
            }

            Optional<Map<String, Object>> fileaOptional = fileList.stream().filter(n -> Objects.equals(String.valueOf(n.get("CPMS_UUID")), String.valueOf(item.get("big_file_id")))).findAny();
            if (fileaOptional.isPresent()) {
                panorama = String.valueOf(fileaOptional.get().get("ID"));
            }

            String id = Util.insertData(testJdbcTemplate, "PM_IMG_PRO");
            testJdbcTemplate.update("update PM_IMG_PRO set `NAME`=?,CPMS_ID=?,CPMS_UUID=?,PM_PRJ_ID=?,PM_IMG_PRO_TYPE_ID=?,COVER_IMAGE=?,PANORAMA=?,PHOTO_DATE=?,VISUAL_PROGRESS=?,REMARK=? WHERE ID=?",
                    item.get("photo_name"), item.get("id"), item.get("photo_id"), projectId, typeId, coverImage, panorama, item.get("photo_date"), item.get("visual_progress"), item.get("remark"), id);
        });

        //------------形象进度END----------------------

        return "success";
    }

    private List<Map<String, Object>> getChildren(Map<String, Object> root, List<Map<String, Object>> allData, String pid, String projectId) {
        return allData.stream().filter(p -> Objects.equals(String.valueOf(root.get("id")), String.valueOf(p.get("parent_id")))).peek(m -> {
            String id = Util.insertData(testJdbcTemplate, "PM_IMG_PRO_TYPE");
            testJdbcTemplate.update("update PM_IMG_PRO_TYPE set `NAME`=?,REMARK=?,CPMS_ID=?,CPMS_UUID=?,PM_PRJ_ID=?,PM_IMG_PRO_TYPE_PID=? where ID=?", m.get("type_name"), m.get("remark"), m.get("id"), m.get("type_id"), projectId, pid, id);
            getChildren(m, allData, id, projectId);
        }).collect(Collectors.toList());
    }
}
