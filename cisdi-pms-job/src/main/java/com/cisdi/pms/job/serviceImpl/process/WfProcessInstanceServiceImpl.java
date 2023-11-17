package com.cisdi.pms.job.serviceImpl.process;

import cn.hutool.core.util.IdUtil;
import com.cisdi.pms.job.commons.ProcessCommons;
import com.cisdi.pms.job.domain.base.AdUser;
import com.cisdi.pms.job.domain.base.HrDept;
import com.cisdi.pms.job.domain.process.WfNode;
import com.cisdi.pms.job.domain.process.common.WfProcess;
import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.mapper.base.AdUserMapper;
import com.cisdi.pms.job.domain.process.WfProcessInstance;
import com.cisdi.pms.job.mapper.process.WfNodeMapper;
import com.cisdi.pms.job.mapper.process.WfProcessInstanceMapper;
import com.cisdi.pms.job.service.base.AdRoleService;
import com.cisdi.pms.job.service.base.HrDeptService;
import com.cisdi.pms.job.service.process.WfProcessInstanceService;
import com.cisdi.pms.job.utils.CisdiUtils;
import com.cisdi.pms.job.utils.FileUtils;
import com.cisdi.pms.job.utils.PoiExcelUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WfProcessInstanceServiceImpl implements WfProcessInstanceService {

    @Resource
    private WfProcessInstanceMapper wfProcessInstanceMapper;

    @Resource
    private AdUserMapper adUserMapper;

    @Resource
    private WfNodeMapper wfNodeMapper;

    @Resource
    private AdRoleService adRoleService;

    @Resource
    private HrDeptService hrDeptService;

    @Resource
    private CisdiUtils cisdiUtils;

    /**
     * 创建流程实例，发起流程至下一步
     *
     * @param wfProcessInstanceId 流程实例id
     * @param map      业务记录map
     * @param pmPrj               项目信息
     * @param wfProcess           流程相关信息
     * @param userId              发起人
     * @param now                 当前时间
     * @param urgent              是否紧急 1紧急 0不紧急
     */
    @Override
    public void createAllInstance(String wfProcessInstanceId, Map<String,Object> map, PmPrj pmPrj, WfProcess wfProcess, String userId, String now, int urgent) {
        String wfProcessId = wfProcess.getId();
        AdUser adUser = adUserMapper.queryById(userId);
        String processEntCode = wfProcess.getAdEntCode(); // 流程主表单
        String entityRecordId = map.get("id").toString();

        String wfProcessInstanceName = ProcessCommons.getProcessInstanceName(pmPrj.getProjectName(),wfProcess.getName(),entityRecordId,adUser.getAdUserName(),now,urgent);
        WfNode wfNodeStart = wfNodeMapper.queryStartNodeByProcess(wfProcessId); // 发起节点/第一步节点相关信息
        if (wfNodeStart != null){

            String startNodeId = wfNodeStart.getWfNodeId(); // 发起节点id
            String nextNodeId = wfNodeStart.getToNodeId(); // 下一步节点id 暂未考虑有分支走向情况
            WfNode wfNodeSecond = wfNodeMapper.queryNodeMsgByNodeId(nextNodeId); // 节点信息
            if (wfNodeSecond != null){
                List<String> userNextList = new ArrayList<>(); // 代办用户
                String adRoleIds = wfNodeSecond.getAdRoleId();
                if (StringUtils.hasText(adRoleIds)){
                    String[] roleList = adRoleIds.split(",");
                    for (String roleId : roleList) {
                        List<String> nextNodeUser = adRoleService.queryUserByRoleId(roleId,processEntCode,map); // 当前节点代办用户
                        if (!CollectionUtils.isEmpty(nextNodeUser)){
                            userNextList.addAll(nextNodeUser);
                        }
                    }
                }


                if (!CollectionUtils.isEmpty(userNextList)){

                    String wfNodeInstanceSecondId = IdUtil.getSnowflakeNextIdStr(); // 当前节点实例id
                    String wfNodeInstanceStartId = IdUtil.getSnowflakeNextIdStr(); // 第一步节点实例id

                    WfProcessInstance wfNodeStanceNext = new WfProcessInstance(); // 节点实例-第二步节点
                    setCommonValue(wfNodeStanceNext,userId,now,wfProcessId);
                    wfNodeStanceNext.setId(wfNodeInstanceSecondId);
                    wfProcessInstanceMapper.insertNodeInstance(wfNodeStanceNext);


                    WfProcessInstance wfProcessInstance = new WfProcessInstance(); // 创建流程实例第一步实例
                    setCommonValue(wfProcessInstance,userId,now,wfProcessId);
                    wfProcessInstance.setId(wfProcessInstanceId);
                    wfProcessInstance.setWfProcessInstanceName(wfProcessInstanceName);
                    wfProcessInstance.setStartUserId(userId);
                    wfProcessInstance.setStartDate(now);
                    wfProcessInstance.setAdEntId(wfProcess.getAdEntId()); // 业务表id
                    wfProcessInstance.setAdEntCode(processEntCode);
                    wfProcessInstance.setEntityRecordId(entityRecordId);
                    wfProcessInstance.setUrgent(urgent);
                    wfProcessInstance.setWfNodeId(nextNodeId); // 当前节点id
                    wfProcessInstance.setWfNodeInstanceId(wfNodeInstanceSecondId); // 当前节点实例id
                    wfProcessInstance.setAdUserId(String.join(",",userNextList)); // 当前代办用户
                    wfProcessInstance.setCurrentViewId(wfNodeSecond.getViewId()); // 当前代办视图
                    wfProcessInstanceMapper.updateProcessInstanceById(wfProcessInstance);

                    // 创建节点实例信息
                    WfProcessInstance wfNodeStanceStart = new WfProcessInstance(); // 节点实例-发起节点
                    setCommonValue(wfNodeStanceStart,userId,now,wfProcessId);
                    wfNodeStanceStart.setWfNodeInstanceName(wfNodeStart.getWfNodeName()); // 节点实例名称
                    wfNodeStanceStart.setId(wfNodeInstanceStartId); // 节点实例id
                    wfNodeStanceStart.setWfProcessInstanceId(wfProcessInstanceId); // 流程实例id
                    wfNodeStanceStart.setWfNodeId(startNodeId); // 节点id
                    wfNodeStanceStart.setStartDate(now); // 开始时间
                    wfNodeStanceStart.setEndDate(now); // 结束时间
                    wfNodeStanceStart.setActId(wfNodeStart.getActId()); // 操作
                    wfNodeStanceStart.setSeqNo(IdUtil.getSnowflakeNextIdStr()); // 序号
                    wfNodeStanceStart.setIsCurrentRound(1); // 是否本轮
                    wfNodeStanceStart.setIsCurrent(0); // 是否当前
                    wfProcessInstanceMapper.insertNodeInstance(wfNodeStanceStart);



                    wfNodeStanceNext.setWfNodeInstanceName(wfNodeSecond.getWfNodeName()); // 节点实例名称
                    wfNodeStanceNext.setWfProcessInstanceId(wfProcessInstanceId); // 流程实例id
                    wfNodeStanceNext.setWfNodeId(nextNodeId); // 节点id
                    wfNodeStanceNext.setStartDate(now); // 开始时间
                    wfNodeStanceNext.setEndDate(now); // 结束时间
                    wfNodeStanceNext.setSeqNo(IdUtil.getSnowflakeNextIdStr()); // 序号
                    wfNodeStanceNext.setIsCurrentRound(1); // 是否本轮
                    wfNodeStanceNext.setIsCurrent(1); // 是否当前
                    wfProcessInstanceMapper.updateNodeInstanceById(wfNodeStanceNext);

                    // 创建用户任务
                    WfProcessInstance startUserTask = new WfProcessInstance();
                    setCommonValue(startUserTask,userId,now,wfProcessId);
                    startUserTask.setId(IdUtil.getSnowflakeNextIdStr()); // id
                    startUserTask.setWfNodeInstanceId(wfNodeInstanceStartId); // 节点实例id
                    startUserTask.setAdUserId(userId); // 用户
                    startUserTask.setReceiveDate(now); // 接收时间
                    startUserTask.setActDate(now); // 操作时间
                    startUserTask.setViewDate(now); // 查看时间
                    startUserTask.setActId(wfNodeStart.getActId()); // 操作
                    startUserTask.setIsClosed(1); // 是否关闭
                    startUserTask.setWfTaskTypeId("TODO"); // 任务类型 TOTO-待办
                    startUserTask.setSeqNo(IdUtil.getSnowflakeNextIdStr()); // 序号
                    startUserTask.setIsCurrentRound(1); // 是否本轮
                    startUserTask.setWfProcessInstanceId(wfProcessInstanceId); // 流程实例
                    startUserTask.setWfNodeId(startNodeId); // 节点信息
                    startUserTask.setIsFirstTask(1); // 是否第一个任务
                    wfProcessInstanceMapper.insertUserTask(startUserTask);

                    for (String user : userNextList) {
                        WfProcessInstance userTask = new WfProcessInstance();
                        setCommonValue(userTask,userId,now,wfProcessId);
                        userTask.setId(IdUtil.getSnowflakeNextIdStr()); // id
                        userTask.setWfNodeInstanceId(wfNodeInstanceSecondId); // 节点实例id
                        userTask.setAdUserId(user); // 用户
                        userTask.setReceiveDate(now); // 接收时间
                        userTask.setIsClosed(0); // 是否关闭
                        userTask.setWfTaskTypeId("TODO"); // 任务类型 TOTO-待办
                        userTask.setSeqNo(IdUtil.getSnowflakeNextIdStr()); // 序号
                        userTask.setIsCurrentRound(1); // 是否本轮
                        userTask.setWfProcessInstanceId(wfProcessInstanceId); // 流程实例
                        userTask.setWfNodeId(startNodeId); // 节点信息
                        wfProcessInstanceMapper.insertUserTask(userTask);
                    }
                }
            }
        }
    }

    /**
     * 新增
     *
     * @param wfProcessInstance 流程实例
     */
    @Override
    public void insert(WfProcessInstance wfProcessInstance) {
        wfProcessInstanceMapper.insert(wfProcessInstance);
    }

    /**
     * 设置共用属性
     * @param wfProcessInstance 实体
     * @param userId 人员信息
     * @param now 时间信息
     * @param wfProcessId 流程id
     */
    public void setCommonValue(WfProcessInstance wfProcessInstance, String userId, String now, String wfProcessId) {
        wfProcessInstance.setStatus("AP");
        wfProcessInstance.setCreateBy(userId);
        wfProcessInstance.setCreateDate(now);
        wfProcessInstance.setLastUpdateBy(userId);
        wfProcessInstance.setLastUpdateDate(now);
        wfProcessInstance.setTs(now);
        wfProcessInstance.setVer("1");
        wfProcessInstance.setWfProcessId(wfProcessId);
    }

    /**
     * 查询所有符合条件数据
     * @param wfProcessInstance 流程监控实体信息
     * @return 查询结果
     */
    @Override
    public List<WfProcessInstance> queryAllList(WfProcessInstance wfProcessInstance) {
        String currentToDoUserNames = wfProcessInstance.getCurrentToDoUserNames(); // 待办用户
        if (StringUtils.hasText(currentToDoUserNames)){
            String userId = adUserMapper.queryUserIdByName(currentToDoUserNames);
            if (!StringUtils.hasText(userId)){
                wfProcessInstance.setCurrentTodoUserIds(currentToDoUserNames); // 未匹配到人，不能查出数据，此条件默认一直查不出数据
            } else {
                wfProcessInstance.setCurrentTodoUserIds(userId);
            }
        }
        String deptId = wfProcessInstance.getDeptId(); // 审批部门
        if (StringUtils.hasText(deptId)){
            List<HrDept> deptList = hrDeptService.getDeptByParentId(deptId);
            List<String> userId = new ArrayList<>();
            if (!CollectionUtils.isEmpty(deptList)){
                List<String> nameList = deptList.stream().map(HrDept::getDeptName).collect(Collectors.toList());
                userId = hrDeptService.getDeptUserByDeptName(nameList);
                wfProcessInstance.setCheckUserIdList(userId);
            } else {
                userId.add("条件查不出数据，需要查不出数据！");
                wfProcessInstance.setCheckUserIdList(userId);
            }
        }
        String checkUserId = wfProcessInstance.getCheckUserId(); // 审批用户
        if (StringUtils.hasText(checkUserId)){
            List<String> userId = new ArrayList<>();
            userId.add(checkUserId);
            wfProcessInstance.setCheckUserIdList(userId);
        }
        List<WfProcessInstance> list = wfProcessInstanceMapper.queryAllList(wfProcessInstance);
        return list;
    }

    /**
     * 流程监控导出
     *
     * @param list     数据详情
     * @param response 响应信息
     * @param title    标题
     */
    @Override
    public void download(List<WfProcessInstance> list, HttpServletResponse response, String title) {
        String filePath = cisdiUtils.getDownLoadPath();
        List<String> header = getListHeader();
        OutputStream outputStream = null;
        Workbook workbook = new XSSFWorkbook();
        try {
            Sheet sheet = workbook.createSheet(title);
            sheet.setDefaultColumnWidth(20);
            sheet.setColumnWidth(1,60*256);
            Row row = sheet.createRow(0);
            CellStyle cs = PoiExcelUtils.getTableHeaderStyle(workbook);

            for (int i = 0; i < header.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header.get(i));
                cell.setCellStyle(cs);
            }
            if (!CollectionUtils.isEmpty(list)){
                CellStyle cellStyle = PoiExcelUtils.getTableCellStyle(workbook);
                listCellValue(sheet,list,cellStyle);
            }

            FileUtils.downLoadFile(filePath,title,workbook,outputStream,response);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 流程监控-导出详情每行赋值
     * @param sheet 页码
     * @param list 数据详情
     * @param style 样式
     */
    private void listCellValue(Sheet sheet, List<WfProcessInstance> list, CellStyle style) {
        for (int i = 0; i < list.size(); i++) {
            Row row = sheet.createRow(i+1);

            // 序号
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(i+1);
            cell1.setCellStyle(style);

            // 名称
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(list.get(i).getWfProcessInstanceName());
            cell2.setCellStyle(style);

            // 流程
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(list.get(i).getProcessName());
            cell3.setCellStyle(style);

            // 启动用户
            Cell cell4 = row.createCell(3);
            cell4.setCellValue(list.get(i).getStartUserName());
            cell4.setCellStyle(style);

            // 开始日期时间
            Cell cell5 = row.createCell(4);
            cell5.setCellValue(list.get(i).getStartDate());
            cell5.setCellStyle(style);

            // 结束日期时间
            Cell cell6 = row.createCell(5);
            cell6.setCellValue(list.get(i).getEndDate());
            cell6.setCellStyle(style);

            // 是否紧急
            Cell cell7 = row.createCell(6);
            Integer urgent = list.get(i).getUrgent();
            if (urgent == 0){
                cell7.setCellValue("否");
            } else {
                cell7.setCellValue("是");
            }
            cell7.setCellStyle(style);

            // 当前节点
            Cell cell8 = row.createCell(7);
            cell8.setCellValue(list.get(i).getWfNodeName());
            cell8.setCellStyle(style);

            // 当前节点实例
            Cell cell9 = row.createCell(8);
            cell9.setCellValue(list.get(i).getWfNodeInstanceName());
            cell9.setCellStyle(style);

            // 当前节点实例
            Cell cell10 = row.createCell(9);
            String userIds = list.get(i).getCurrentTodoUserIds();
            if (StringUtils.hasText(userIds)){
                userIds = userIds.replace(",","','");
                String userName = adUserMapper.queryNameByIds(userIds);
                cell10.setCellValue(userName);
            }
            cell10.setCellStyle(style);
        }
    }

    /**
     * 流程监控-导出表头信息
     * @return 表头信息
     */
    public List<String> getListHeader() {
        List<String> list = new ArrayList<>();
        list.add("序号");
        list.add("名称");
        list.add("流程");
        list.add("启动用户");
        list.add("开始日期时间");
        list.add("结束日期时间");
        list.add("是否紧急");
        list.add("当前节点");
        list.add("当前节点实例");
        list.add("当前待办用户");
        return list;
    }
}
