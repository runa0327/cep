package com.cisdi.pms.job.proFile;

import com.cisdi.pms.job.utils.ListUtils;
import com.cisdi.pms.job.utils.Util;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author dlt
 * @date 2022/11/1 周二
 */

@Slf4j
@Controller
@RequestMapping("/folder")
public class PrjFolderController {

    @Value("${spring.task.execution.pool.core-size}")
    private Integer coreSize;

    @Autowired
    JdbcTemplate testJdbcTemplate;
    
    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor asyncExecutor;

    @ResponseBody
    @GetMapping("/refresh")
    public String refreshFolder() throws InterruptedException {
        testJdbcTemplate.update("delete from pf_file_copy1");
        testJdbcTemplate.update("delete from pf_folder_copy1");
        //删除pf_folder_copy1数据
        //将pf_folder模板数据同步到pf_folder_copy1
        testJdbcTemplate.update("insert into pf_folder_copy1 (ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,PM_PRJ_ID,SEQ_NO,IS_TEMPLATE,FILE_SIZE,FILE_COUNT,PF_FOLDER_PID_C) select ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,PM_PRJ_ID,SEQ_NO,IS_TEMPLATE,FILE_SIZE,FILE_COUNT,PF_FOLDER_PID from pf_folder where IS_TEMPLATE = 1");
        //在pf_folder_copy1中建立pf_folder存在的项目的文件夹
        List<Map<String, Object>> prjIdList = testJdbcTemplate.queryForList("select id from pm_prj");
        //异步创建项目文件夹
        CountDownLatch createFolderLatch = this.createPrjFolder(prjIdList);
        createFolderLatch.await();

        //复制pf_file到pf_file_copy1
        testJdbcTemplate.update("insert into pf_file_copy1 (ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,FL_FILE_ID,CPMS_UUID,CPMS_ID,CHIEF_USER_ID,PF_FOLDER_ID_C) select ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,FL_FILE_ID,CPMS_UUID,CPMS_ID,CHIEF_USER_ID,PF_FOLDER_ID from pf_file");
        //将pf_folder和pf_folder_copy1中code和项目相同的数据写到pf_folder_copy1
        List<Map<String, Object>> prjFolderList = testJdbcTemplate.queryForList("select * from pf_folder where PM_PRJ_ID is not null");
        List<Map<String, Object>> folderCopyList = testJdbcTemplate.queryForList("select * from pf_folder_copy1 where PM_PRJ_ID is not null");
        //异步比较并更新备份表
        CountDownLatch compareLatch = this.copyFolder(prjFolderList, folderCopyList);
        compareLatch.await();

        //pf_folder_copy1同步到pf_folder
        testJdbcTemplate.update("delete from pf_file");
        testJdbcTemplate.update("delete from pf_folder");
        List<Map<String, Object>> folderList = testJdbcTemplate.queryForList("select * from pf_folder_copy1");
        List<Map<String, Object>> rootFolderList = folderList.stream().filter(folder -> folder.get("PF_FOLDER_PID_C") == null).collect(Collectors.toList());
        //异步回写数据到pf_folder
        CountDownLatch writeBackLatch = this.writeBack(folderList, rootFolderList);
        writeBackLatch.await();
        //回写数据到pf_file,如果找不到文件夹删除数据
        testJdbcTemplate.update("delete from pf_file_copy1 where PF_FOLDER_ID_C not in (select id from pf_folder)");
        testJdbcTemplate.update("insert into pf_file (ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,FL_FILE_ID,CPMS_UUID,CPMS_ID,CHIEF_USER_ID,PF_FOLDER_ID) select ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,FL_FILE_ID,CPMS_UUID,CPMS_ID,CHIEF_USER_ID,PF_FOLDER_ID_C from pf_file_copy1");

        return "success";
    }

    /**
     * 异步回写数据到pf_folder
     * @param folderList 备份文件夹所有数据
     * @param rootFolderList 备份文件夹根文件夹集合
     */
    public CountDownLatch writeBack(List<Map<String, Object>> folderList, List<Map<String, Object>> rootFolderList) {
        int size = rootFolderList.size() / coreSize + 1;
        List<List<Map<String, Object>>> rootFolderLists = ListUtils.split(rootFolderList, size);
        CountDownLatch latch = new CountDownLatch(rootFolderLists.size());
        AtomicInteger rootIndex = new AtomicInteger(0);
        for (List<Map<String, Object>> rootFolders : rootFolderLists) {
            asyncExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (Map<String, Object> pFolder : rootFolders) {
                            log.info("正在更新第"+ rootIndex.getAndIncrement() +"条根文件夹数据");
                            insertChildFolder(pFolder, folderList);
                        }
                    }finally {
                        latch.countDown();
                    }
                }
            });
        }

        return latch;

    }

    /**
     * 异步比较项目和code并更新pf_folder_copy1和pf_file_copy1
     * @param prjFolderList 源文件夹数据
     * @param folderCopyList 备份文件夹数据
     */
    public CountDownLatch copyFolder(List<Map<String, Object>> prjFolderList, List<Map<String, Object>> folderCopyList) {
        int size = prjFolderList.size() / coreSize + 1;
        List<List<Map<String, Object>>> prjFolderLists = ListUtils.split(prjFolderList, size);
        CountDownLatch latch = new CountDownLatch(prjFolderLists.size());
        AtomicInteger folderIndex = new AtomicInteger(0);
        for (List<Map<String, Object>> prjFolders : prjFolderLists) {
            asyncExecutor.execute(() -> {
                try {
                    prjFolders.stream().forEach(f -> {
                        log.info("比对源文件夹和备份文件夹----源文件夹中第" + folderIndex.getAndIncrement() + "条");
                        Optional<Map<String, Object>> copyOptional = folderCopyList.stream().filter(c -> String.valueOf(c.get("CODE")).equals(String.valueOf(f.get("CODE"
                        ))) && String.valueOf(c.get("PM_PRJ_ID")).equals(String.valueOf(f.get("PM_PRJ_ID")))).findAny();
                        if (copyOptional.isPresent()){
                            //更新pf_folder_copy1
                            testJdbcTemplate.update("update pf_folder_copy1 set VER = ?,TS = ?,IS_PRESET = ?,CRT_DT = ?,CRT_USER_ID = ?,LAST_MODI_DT = ?,LAST_MODI_USER_ID = ?,STATUS = ?," +
                                            "LK_WF_INST_ID = ?,REMARK = ?,IS_TEMPLATE = ?,FILE_SIZE = ?,FILE_COUNT = ? where PM_PRJ_ID = ? and CODE = ?",
                                    f.get("VER"),f.get("TS"),f.get("IS_PRESET"),f.get("CRT_DT"),f.get("CRT_USER_ID"),f.get("LAST_MODI_DT"),f.get("LAST_MODI_USER_ID"),
                                    f.get("STATUS"),f.get("LK_WF_INST_ID"),f.get("REMARK"),f.get("IS_TEMPLATE"),f.get("FILE_SIZE"),f.get("FILE_COUNT"),f.get("PM_PRJ_ID"),f.get("CODE"));
                            //更新pf_file_copy1关系表
                            testJdbcTemplate.update("update pf_file_copy1 set PF_FOLDER_ID_C = ? where PF_FOLDER_ID_C = ?",copyOptional.get().get("ID"),f.get("ID"));
                        }
                    });
                }finally {
                    latch.countDown();
                }
            });
        }
        return latch;
    }

    /**
     * 异步创建项目文件夹
     */
    public CountDownLatch createPrjFolder(List<Map<String, Object>> prjIdList) {
        int size = prjIdList.size() / coreSize + 1;
        List<List<Map<String, Object>>> prjIdLists = ListUtils.split(prjIdList, size);
        AtomicInteger index = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(prjIdLists.size());
        for (List<Map<String, Object>> idList : prjIdLists) {
            asyncExecutor.execute(() -> {
                try {
                    for (Map<String, Object> prjIdMap : idList) {
                        log.info("创建第" + index.getAndIncrement() + "个项目文件夹");
                        //创建项目文件夹
                        this.createFolder(JdbcMapUtil.getString(prjIdMap, "id"), testJdbcTemplate);
                    }
                }finally {
                    latch.countDown();
                }
            });
        }
        return latch;
    }

   

    /**
     * 查找并同步子集文件夹
     * @param pFolder 父文件夹
     * @param folderList 所有文件夹
     */
    private void insertChildFolder(Map<String,Object> pFolder,List<Map<String,Object>> folderList) {
        this.insertFolder(pFolder);
        List<Map<String, Object>> childFolders = folderList.stream()
                .filter(folder -> Objects.equals(folder.get("PF_FOLDER_PID_C"),pFolder.get("ID")))
                .collect(Collectors.toList());
        for (Map<String, Object> childFolder : childFolders) {
            this.insertChildFolder(childFolder,folderList);
        }

    }

    /**
     * 单条pf_folder_copy1同步到pf_folder
     * @param folder
     */
    private void insertFolder(Map<String,Object> folder){
        testJdbcTemplate.update(
                "insert into pf_folder (ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,PM_PRJ_ID,SEQ_NO,IS_TEMPLATE,FILE_SIZE,FILE_COUNT,PF_FOLDER_PID) select ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,PM_PRJ_ID,SEQ_NO,IS_TEMPLATE,FILE_SIZE,FILE_COUNT,PF_FOLDER_PID_C from pf_folder_copy1 where ID = ?",folder.get("ID"));
    }

    /**
     * 新增项目资料文件夹层级
     */
    private void createFolder(String projectId, JdbcTemplate jdbcTemplate) {

        // 查询已经有的文件夹
        List<Map<String, Object>> folderList = jdbcTemplate.queryForList("select * from pf_folder_copy1 where  PM_PRJ_ID=?", projectId);

        //查询模板
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select ID, `CODE`,`NAME`,REMARK,PM_PRJ_ID,SEQ_NO,ifnull(PF_FOLDER_PID_C,'0') as " +
                "PF_FOLDER_PID_C from pf_folder_copy1 where IS_TEMPLATE ='1';");

        // 新增项目文件夹目录
        list.stream().filter(p -> Objects.equals("0", String.valueOf(p.get("PF_FOLDER_PID_C")))).peek(m -> {
            String id = "";
            Optional<Map<String, Object>> optional = folderList.stream().filter(o -> Objects.equals(String.valueOf(m.get("CODE")),
                    String.valueOf(o.get("CODE")))).findAny();
            if (optional.isPresent()) {
                id = String.valueOf(optional.get().get("ID"));
            } else {
                id = Util.insertData(jdbcTemplate, "pf_folder_copy1");
                jdbcTemplate.update("update pf_folder_copy1 set PM_PRJ_ID = ?,NAME = ?,SEQ_NO = ?,CODE = ?,IS_TEMPLATE = '0' where id = ?", projectId,
                        m.get("NAME"), m.get("SEQ_NO"), m.get("CODE"), id);
            }
            createSonFolder(m, list, id, projectId, folderList, jdbcTemplate);
        }).collect(Collectors.toList());
    }

    /**
     * @param root         父模板
     * @param allData      所有模板
     * @param pid          文件夹父id
     * @param projectId    项目id
     * @param folderList   已存在的文件夹
     * @param jdbcTemplate
     * @return
     */
    private List<Map<String, Object>> createSonFolder(Map<String, Object> root, List<Map<String, Object>> allData, String pid,
                                                      String projectId, List<Map<String, Object>> folderList, JdbcTemplate jdbcTemplate) {
        List<Map<String, Object>> collect = allData.stream()
                .filter(p -> Objects.equals(String.valueOf(root.get("ID")), String.valueOf(p.get("PF_FOLDER_PID_C"))))
                .peek(m -> {
                    String id = "";
                    Optional<Map<String, Object>> optional = folderList.stream().filter(o -> Objects.equals(String.valueOf(m.get("CODE")),
                            String.valueOf(o.get("CODE")))).findAny();
                    if (optional.isPresent()) {
                        id = String.valueOf(optional.get().get("ID"));
                    } else {
                        id = Util.insertData(jdbcTemplate, "pf_folder_copy1");
                        jdbcTemplate.update("update pf_folder_copy1 set PM_PRJ_ID = ?,NAME = ?,SEQ_NO = ?,CODE = ?,IS_TEMPLATE = '0', PF_FOLDER_PID_C" +
                                " = ? where id = ?", projectId, m.get("NAME"), m.get("SEQ_NO"), m.get("CODE"), pid, id);
                    }
                    createSonFolder(m, allData, id, projectId, folderList, jdbcTemplate);
                })
                .collect(Collectors.toList());
        return collect;
    }

}
