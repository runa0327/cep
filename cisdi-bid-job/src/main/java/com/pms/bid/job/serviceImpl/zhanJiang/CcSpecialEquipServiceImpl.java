package com.pms.bid.job.serviceImpl.zhanJiang;

import cn.hutool.core.util.IdUtil;
import com.pms.bid.job.domain.json.Internationalization;
import com.pms.bid.job.domain.processInstance.*;
import com.pms.bid.job.domain.zhanJiang.*;
import com.pms.bid.job.mapper.processInstance.WfNodeInstanceMapper;
import com.pms.bid.job.mapper.processInstance.WfNodeMapper;
import com.pms.bid.job.mapper.processInstance.WfProcessInstanceMapper;
import com.pms.bid.job.mapper.processInstance.WfProcessMapper;
import com.pms.bid.job.mapper.zhanJiang.*;
import com.pms.bid.job.service.processInstance.WfTaskService;
import com.pms.bid.job.service.zhanJiang.CcElevatorService;
import com.pms.bid.job.service.zhanJiang.CcHoistingMachineryService;
import com.pms.bid.job.service.zhanJiang.CcSpecialEquipService;
import com.pms.bid.job.service.zhanJiang.PressurePipelineService;
import com.pms.bid.job.util.DateUtil;
import com.pms.bid.job.util.JsonUtil;
import com.qygly.shared.BaseException;
import lombok.experimental.PackagePrivate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class CcSpecialEquipServiceImpl implements CcSpecialEquipService {


    @Resource
    private WfTaskService wfTaskService;

    @Resource
    private WfNodeInstanceMapper wfNodeInstanceMapper;

    @Resource
    private WfNodeMapper wfNodeMapper;

    @Resource
    private WfProcessMapper wfProcessMapper;

    @Resource
    private WfProcessInstanceMapper wfProcessInstanceMapper;

    @Resource
    private CcEarlyWarningMapper earlyWarningMapper;

    @Resource
    private CcSpecialEquipTodoMapper specialEquipTodoMapper;

    @Resource
    private CcHoistingMachineryMapper hoistingMachineryMapper;

    @Resource
    private CcElevatorMapper elevatorMapper;

    @Resource
    private CcAssemblingPressureVesselsMapper assemblingPressureVesselsMapper;


    /**
     * 将时间段内时间以七天为一周期取出
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return List<FillingCycle>
     */
    @Override
    public List<FillingCycle> generateDateRanges(Date start, Date end, int type) {

        List<FillingCycle> dateRanges = new ArrayList<>();
        LocalDate currentStart = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (currentStart.isBefore(endDate) || currentStart.isEqual(endDate)) {
            LocalDate currentEnd = currentStart.plusDays(6); // 加上六天

            if (type == 2) {//按填写取得监督检验合格报告时间
                if (currentEnd.isAfter(endDate)) {
                    currentEnd = endDate;
                }
            }
            FillingCycle cycle = new FillingCycle();
            cycle.setStart(currentStart.format(formatter));
            cycle.setEnd(currentEnd.format(formatter));
            dateRanges.add(cycle);
            currentStart = currentEnd.plusDays(1); // 开始下一周
        }
        return dateRanges;
    }

    /**
     * 发起流程并提醒
     */
    @Override
    @Async("threadPoolTaskExecutor")
    public void remind(String specialEquipId, String toUserId, String specialEquipCategory, String taskType, CcSpecialEquip specialEquip) {

        if (StringUtils.isEmpty(toUserId)) {
            log.error("人员信息为空");
            throw new BaseException("人员信息为空");
        }
        //  校验是否已发起流程
        String wfId = specialEquip.getLkWfInstId();
        String now = DateUtil.getNormalTimeStr(new Date());
        String endNodeId = null;

        if (!org.springframework.util.StringUtils.hasText(wfId)) {
            if (specialEquip instanceof CcHoistingMachinery) {
                // 发起流程
                Map<String, String> result =
                        createWfProcessToSendNode(specialEquip, "1833686205846016000", "1833686205812461568", "1831889238912516096", "CC_HOISTING_MACHINERY", toUserId,"起重机械-"+specialEquip.getName()+"-"+now);
                wfId = result.get("wfProcessInstanceId");
                String taskId = result.get("taskId");
                hoistingMachineryMapper.updateWfInstIdById(specialEquip.getId(), wfId); //设置流程实例id、
                wfTaskService.closeTask(taskId);
            } else if(specialEquip instanceof CcElevator){

                // 发起流程
                Map<String, String> result =
                        createWfProcessToSendNode(specialEquip, "1834511626925842432", "1834511626879705088", "1834415628236935168", "CC_SPECIAL_EQUIP_ELEVATOR", toUserId,"电梯-"+specialEquip.getName()+"-"+now);
                wfId = result.get("wfProcessInstanceId");
                String taskId = result.get("taskId");
                elevatorMapper.updateWfInstIdById(specialEquip.getId(), wfId); //设置流程实例id、
                wfTaskService.closeTask(taskId);

            }else if(specialEquip instanceof CcAssemblingPressureVessels){
                // 发起流程
                Map<String, String> result =
                        createWfProcessToSendNode(specialEquip, "1836587553704947712", "1836587553663004672", "1836314106420527104", "CC_SPECIAL_EQUIP_ASSEMBLING_PRESSURE_VESSELS", toUserId,"拼装压力容器-"+specialEquip.getName()+"-"+now);
                wfId = result.get("wfProcessInstanceId");
                String taskId = result.get("taskId");
                assemblingPressureVesselsMapper.updateWfInstIdById(specialEquip.getId(), wfId); //设置流程实例id、
                wfTaskService.closeTask(taskId);

            }else {
                log.error("特种设备类型错误！");
            }
        }
        //流程结束节点
        if (specialEquip instanceof CcHoistingMachinery) {
            endNodeId = "1833686205883764736";
        }else if (specialEquip instanceof CcElevator){
            endNodeId = "1834511626951008256";
        }else if (specialEquip instanceof CcAssemblingPressureVessels){
            endNodeId = "1836587553746890752";
        }

        if(StringUtils.isEmpty(endNodeId)){
            throw new BaseException("不存在的特种设备，待办节点错误");
        }

        //  发起新的通知
        //检查是否有未完成的待办
        List<CcSpecialEquipTodo> ccSpecialEquipTodos = specialEquipTodoMapper.queryIncompleteList(specialEquipId, specialEquipCategory, taskType);

        if (ccSpecialEquipTodos==null || ccSpecialEquipTodos.size()<1 ){
            String taskId = createUserTaskMsg(wfId,endNodeId, toUserId, 1, now, "0099250247095871681");
            //新曾待办
            CcSpecialEquipTodo ccSpecialEquipTodo = new CcSpecialEquipTodo();
            ccSpecialEquipTodo.setId(IdUtil.getSnowflakeNextIdStr());
            ccSpecialEquipTodo.setVer("1");
            ccSpecialEquipTodo.setTs(now);
            ccSpecialEquipTodo.setCreateBy("0099250247095871681");
            ccSpecialEquipTodo.setLastUpdateBy("0099250247095871681");
            ccSpecialEquipTodo.setCreateDate(now);
            ccSpecialEquipTodo.setLastUpdateDate(now);
            ccSpecialEquipTodo.setStatus("AP");

            ccSpecialEquipTodo.setCcSpecialEquipId(specialEquipId);
            ccSpecialEquipTodo.setCcSpecialEquipCtg(specialEquipCategory);
            ccSpecialEquipTodo.setCcSpecialEquipTodoType(taskType);
            ccSpecialEquipTodo.setCcSpecialEquipTodoTaskId(taskId);
            specialEquipTodoMapper.addTodo(ccSpecialEquipTodo);
        }

    }


    /**
     *
     * @param processInstanceId 流程实例id
     * @param nodeId 指定节点
     * @param taskUser 指定接收人
     * @param isCurrent 是否未当前
     * @param now 当前时间
     * @param createBy 创建人
     * @return 待办ID
     */
    private String createUserTaskMsg(String processInstanceId, String nodeId,String taskUser, Integer isCurrent, String now, String createBy) {
        String nodeInstanceId = wfNodeInstanceMapper.getNodeInstanceId(processInstanceId,nodeId);
        WfProcessInstance wfProcessInstance = wfProcessInstanceMapper.queryWfNodeInstanceIdById(processInstanceId);



        WfTask wfTask = new WfTask();
        wfTask.setId(IdUtil.getSnowflakeNextIdStr());
        wfTask.setVer("1");
        wfTask.setTs(now);
        wfTask.setCreateBy(createBy);
        wfTask.setLastUpdateBy(createBy);
        wfTask.setCreateDate(now);
        wfTask.setLastUpdateDate(now);
        wfTask.setStatus("AP");

        wfTask.setWfProcessId(wfProcessInstance.getWfProcessId());
        wfTask.setWfProcessInstanceId(processInstanceId);
        wfTask.setWfNodeId(nodeId);

        wfTask.setWfNodeInstanceId(nodeInstanceId);
        wfTask.setAdUserId(taskUser);
        wfTask.setIsClosed(0);
        wfTask.setReceiveDateTime(now);
        wfTask.setWfTaskTypeId("TODO");
        wfTask.setIsCurrentRound(isCurrent);
        wfTask.setSeqNo(IdUtil.getSnowflakeNextIdStr());
        wfTaskService.create(wfTask);

        return wfTask.getId();
    }

    /**
     * 判断时间是否超过30天
     *
     * @param date
     * @return
     */
    @Override
    public boolean isMoreThan30DaysOld(Date date) {
        if (date == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 30);

        Date thirtyDaysLater = calendar.getTime();
        Date now = new Date();
        return now.after(thirtyDaysLater);
    }

    /**
     * 判断时间是否超过30天
     *
     * @param date
     * @return
     */
    @Override
    public boolean isMoreThanDaysOld(Date date,int days) {
        if (date == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);

        Date thirtyDaysLater = calendar.getTime();
        Date now = new Date();
        return now.after(thirtyDaysLater);
    }

    /**
     * 判断当前时间是否在目标时间之前（30天）
     *
     * @param date
     * @return
     */
    @Override
    public boolean before30DaysLater(Date date, Date target) {
        if (date == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 30);

        Date thirtyDaysLater = calendar.getTime();
        return !target.after(thirtyDaysLater);
    }

    /**
     * 发起流程
     */
    @Override
    public Map<String, String> createWfProcessToSendNode(CcSpecialEquip specialEquip, String firstNodeId, String wfProcessId, String entId, String entCode, String adUserId,String name) {
        String now = DateUtil.getNormalTimeStr(new Date());
        String createBy = "0099250247095871681";

        //  创建流程实例
        String wfProcessInstanceId = IdUtil.getSnowflakeNextIdStr();
        String wfNodeInstanceId = createNodeInstanceById(IdUtil.getSnowflakeNextIdStr());
        WfNode firstNode = wfNodeMapper.queryNodeById(firstNodeId);
        WfNode secondNode = wfNodeMapper.queryNodeById(firstNode.getToNodeId());
        WfProcess wfProcess = wfProcessMapper.queryNameById(wfProcessId);
        wfProcess.setAdEntId(entId);
        wfProcess.setEntCode(entCode);

        //  创建流程实例
        createWfProcessInstance(specialEquip, wfProcessInstanceId, wfNodeInstanceId, secondNode, wfProcess, now, createBy,adUserId,name);
        //  创建第一步节点实例
        createNodeInstance(wfProcessInstanceId, firstNode, adUserId, 0, IdUtil.getSnowflakeNextIdStr(), now, createBy);
        //  创建第二步节点实例
        String taskId = updateNodeInstance(wfProcessInstanceId, secondNode, adUserId, 1, wfNodeInstanceId, now, createBy);

        Map<String, String> result = new HashMap<String, String>();
        result.put("wfProcessInstanceId", wfProcessInstanceId);
        result.put("taskId", taskId);
        return result;
    }

    /**
     * 根据id创建空节点实例
     *
     * @param snowflakeNextIdStr id
     * @return id
     */

    private String createNodeInstanceById(String snowflakeNextIdStr) {
        WfNodeInstance wfNodeInstance = new WfNodeInstance();
        wfNodeInstance.setId(snowflakeNextIdStr);
        wfNodeInstanceMapper.create(wfNodeInstance);
        return snowflakeNextIdStr;
    }

    /**
     * 创建流程实例信息
     * @param specialEquip 特种是设备
     * @param wfProcessInstanceId 流程实例
     * @param wfNodeInstanceId  节点实例
     * @param secondNode 第二节点id
     * @param wfProcess 待办id
     * @param now 时间
     * @param createBy 创建人
     * @param adUserId 接收人
     * @param name 消息内容
     */
    private void createWfProcessInstance(CcSpecialEquip specialEquip, String wfProcessInstanceId, String wfNodeInstanceId, WfNode secondNode, WfProcess wfProcess, String now, String createBy,String adUserId,String name) {
        WfProcessInstance wfProcessInstance = new WfProcessInstance();
        wfProcessInstance.setId(wfProcessInstanceId);
        wfProcessInstance.setVer("1");
        wfProcessInstance.setTs(now);
        wfProcessInstance.setCreateBy(createBy);
        wfProcessInstance.setLastUpdateBy(createBy);
        wfProcessInstance.setCreateDate(now);
        wfProcessInstance.setLastUpdateDate(now);
        wfProcessInstance.setStatus("AP");
//        String name = "压力管道" + "-" + specialEquip.getName() + "-" + now;
        name = JsonUtil.toJson(new Internationalization(null, name, null));
        wfProcessInstance.setWfProcessInstanceName(name);
        wfProcessInstance.setStartUserId(adUserId);
        wfProcessInstance.setStartDate(now);
        wfProcessInstance.setAdEntId(wfProcess.getAdEntId());
        wfProcessInstance.setAdEntCode(wfProcess.getEntCode());
        wfProcessInstance.setEntityRecordId(specialEquip.getId());
        wfProcessInstance.setUrgent(0);
        wfProcessInstance.setCurrentNodeId(secondNode.getId());
        wfProcessInstance.setWfNodeInstanceId(wfNodeInstanceId);
        wfProcessInstance.setAdUserId(adUserId);
        wfProcessInstance.setCurrentViewId(secondNode.getViewId());
        wfProcessInstance.setWfProcessId(secondNode.getWfProcessId());
        wfProcessInstanceMapper.create(wfProcessInstance);
    }

    /**
     * 创建发起节点实例
     *
     * @param wfProcessInstanceId 流程实例id
     * @param firstNode           节点信息
     * @param createBy            创建人
     */
    private void createNodeInstance(String wfProcessInstanceId, WfNode firstNode, String taskUser, Integer isCurrent, String wfNodeInstanceId, String now, String createBy) {
        WfNodeInstance wfNodeInstance = new WfNodeInstance();
        wfNodeInstance.setId(wfNodeInstanceId);
        wfNodeInstance.setVer("1");
        wfNodeInstance.setTs(now);
        wfNodeInstance.setCreateBy(createBy);
        wfNodeInstance.setLastUpdateBy(createBy);
        wfNodeInstance.setCreateDate(now);
        wfNodeInstance.setLastUpdateDate(now);
        wfNodeInstance.setStatus("AP");
        wfNodeInstance.setWfNodeInstanceName(JsonUtil.toJson(new Internationalization(null, firstNode.getWfNodeName(), null)));
        wfNodeInstance.setWfProcessId(firstNode.getWfProcessId());
        wfNodeInstance.setWfProcessInstanceId(wfProcessInstanceId);
        wfNodeInstance.setWfNodeId(firstNode.getId());
        wfNodeInstance.setStartDateTime(now);
        wfNodeInstance.setIsCurrentRound(1);
        wfNodeInstance.setIsCurrent(isCurrent);
        if (isCurrent == 0) {
            wfNodeInstance.setEndDateTime(now);
            wfNodeInstance.setAdActId(firstNode.getAdActId());
        }
        wfNodeInstance.setSeqNo(IdUtil.getSnowflakeNextIdStr());
        wfNodeInstanceMapper.create(wfNodeInstance);

        //  生成任务
        createUserTask(wfNodeInstance, taskUser, isCurrent, now, createBy);
    }

    /**
     * 判断并生成流程实例任务
     *
     * @param wfNodeInstance 节点实例信息
     * @param taskUser       代办任务用户
     * @param isCurrent      是否当前
     * @param now            当前时间
     * @param createBy       创建人
     */
    @Override
    public void createUserTask(WfNodeInstance wfNodeInstance, String taskUser, Integer isCurrent, String now, String createBy) {
        String[] taskUserArr = taskUser.split(",");
        for (String userId : taskUserArr) {
            WfTask wfTask = new WfTask();
            wfTask.setId(IdUtil.getSnowflakeNextIdStr());
            wfTask.setVer("1");
            wfTask.setTs(now);
            wfTask.setCreateBy(createBy);
            wfTask.setLastUpdateBy(createBy);
            wfTask.setCreateDate(now);
            wfTask.setLastUpdateDate(now);
            wfTask.setStatus("AP");
            wfTask.setWfProcessId(wfNodeInstance.getWfProcessId());
            wfTask.setWfProcessInstanceId(wfNodeInstance.getWfProcessInstanceId());
            wfTask.setWfNodeId(wfNodeInstance.getWfNodeId());
            wfTask.setWfNodeInstanceId(wfNodeInstance.getId());
            wfTask.setAdUserId(userId);
            if (isCurrent == 0) {
                wfTask.setIsClosed(1);
                wfTask.setViewDateTime(now);
                wfTask.setActDateTime(now);
                wfTask.setAdActId(wfNodeInstance.getAdActId());
            } else {
                wfTask.setIsClosed(0);
            }
            wfTask.setReceiveDateTime(now);
            wfTask.setWfTaskTypeId("TODO");
            wfTask.setIsCurrentRound(isCurrent);
            wfTask.setSeqNo(IdUtil.getSnowflakeNextIdStr());

            wfTaskService.create(wfTask);
        }
    }


    private String updateNodeInstance(String wfProcessInstanceId, WfNode firstNode, String taskUser, Integer isCurrent, String wfNodeInstanceId, String now, String createBy) {
        WfNodeInstance wfNodeInstance = new WfNodeInstance();
        wfNodeInstance.setId(wfNodeInstanceId);
        wfNodeInstance.setVer("1");
        wfNodeInstance.setTs(now);
        wfNodeInstance.setCreateBy(createBy);
        wfNodeInstance.setLastUpdateBy(createBy);
        wfNodeInstance.setCreateDate(now);
        wfNodeInstance.setLastUpdateDate(now);
        wfNodeInstance.setStatus("AP");
        wfNodeInstance.setWfNodeInstanceName(JsonUtil.toJson(new Internationalization(null, firstNode.getWfNodeName(), null)));
        wfNodeInstance.setWfProcessId(firstNode.getWfProcessId());
        wfNodeInstance.setWfProcessInstanceId(wfProcessInstanceId);
        wfNodeInstance.setWfNodeId(firstNode.getId());
        wfNodeInstance.setStartDateTime(now);
        wfNodeInstance.setIsCurrentRound(1);
        wfNodeInstance.setIsCurrent(isCurrent);
        if (isCurrent == 0) {
            wfNodeInstance.setEndDateTime(now);
            wfNodeInstance.setAdActId(firstNode.getAdActId());
        }
        wfNodeInstance.setSeqNo(IdUtil.getSnowflakeNextIdStr());
        wfNodeInstanceMapper.updateById(wfNodeInstance);

        //  生成任务
        return createTask(wfNodeInstance, taskUser, isCurrent, now, createBy);
    }

    private String createTask(WfNodeInstance wfNodeInstance, String userId, Integer isCurrent, String now, String createBy) {
        String taskId = IdUtil.getSnowflakeNextIdStr();
        WfTask wfTask = new WfTask();
        wfTask.setId(taskId);
        wfTask.setVer("1");
        wfTask.setTs(now);
        wfTask.setCreateBy(createBy);
        wfTask.setLastUpdateBy(createBy);
        wfTask.setCreateDate(now);
        wfTask.setLastUpdateDate(now);
        wfTask.setStatus("AP");
        wfTask.setWfProcessId(wfNodeInstance.getWfProcessId());
        wfTask.setWfProcessInstanceId(wfNodeInstance.getWfProcessInstanceId());
        wfTask.setWfNodeId(wfNodeInstance.getWfNodeId());
        wfTask.setWfNodeInstanceId(wfNodeInstance.getId());
        wfTask.setAdUserId(userId);
        if (isCurrent == 0) {
            wfTask.setIsClosed(1);
            wfTask.setViewDateTime(now);
            wfTask.setActDateTime(now);
            wfTask.setAdActId(wfNodeInstance.getAdActId());
        } else {
            wfTask.setIsClosed(0);
        }
        wfTask.setReceiveDateTime(now);
        wfTask.setWfTaskTypeId("TODO");
        wfTask.setIsCurrentRound(isCurrent);
        wfTask.setSeqNo(IdUtil.getSnowflakeNextIdStr());

        wfTaskService.create(wfTask);

        return taskId;
    }

    /**
     * 检查预警提醒是否发送
     *
     * @param warningDay 逾期提醒天数
     * @param userIds 用户id
     * @param planDate 计划时间
     * @param message  预警消息
     * @param moreThanDay 允许超过计划时间天数
     */
    @Override
    public void checkWarningDay(Integer warningDay, String userIds, Date planDate, String message, int moreThanDay) {
        if (warningDay != null && userIds != null && !StringUtils.isEmpty(message)) {

            String s = earlyWarningMapper.selectByName(message);
            if (s != null) { //存在消息不提醒
                return;
            }
            warningDay += moreThanDay;
            String nowStr = DateUtil.getNormalTimeStr(new Date());
            String createBy = "0099250247095871681";
            if (isSlippage(planDate, warningDay)) {
                CcEarlyWarning ccEarlyWarning = new CcEarlyWarning();
                ccEarlyWarning.setId(IdUtil.getSnowflakeNextIdStr());
                ccEarlyWarning.setCrtDt(nowStr);
                ccEarlyWarning.setVer("1");
                ccEarlyWarning.setCode(null);
                ccEarlyWarning.setAdUserIds(userIds);
                ccEarlyWarning.setFastCode(null);
                ccEarlyWarning.setStatus("AP");
                ccEarlyWarning.setLastModiDt(nowStr);
                ccEarlyWarning.setLastModiUserId(createBy);
                ccEarlyWarning.setCrtUserId(createBy);
                ccEarlyWarning.setName(message);
                ccEarlyWarning.setLkWfInstId(null);
                ccEarlyWarning.setTs(nowStr);
                earlyWarningMapper.insertRecord(ccEarlyWarning);
            }
        }
    }


    /**
     * 判断时间是否超期
     *
     * @param date
     * @return
     */
    private boolean isSlippage(Date date, Integer days) {
        if (date == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);

        Date thirtyDaysLater = calendar.getTime();
        Date now = new Date();

        return now.after(thirtyDaysLater);
    }
}
