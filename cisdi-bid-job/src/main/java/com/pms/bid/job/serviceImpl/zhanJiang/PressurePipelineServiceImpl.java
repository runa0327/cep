package com.pms.bid.job.serviceImpl.zhanJiang;

import cn.hutool.core.util.IdUtil;
import com.pms.bid.job.domain.json.Internationalization;
import com.pms.bid.job.domain.process.SpecialEquipPreVe;
import com.pms.bid.job.domain.processInstance.*;
import com.pms.bid.job.domain.zhanJiang.FillingCycle;
import com.pms.bid.job.domain.zhanJiang.PressurePipeline;
import com.pms.bid.job.mapper.processInstance.WfNodeInstanceMapper;
import com.pms.bid.job.mapper.processInstance.WfNodeMapper;
import com.pms.bid.job.mapper.processInstance.WfProcessInstanceMapper;
import com.pms.bid.job.mapper.processInstance.WfProcessMapper;
import com.pms.bid.job.mapper.zhanJiang.PressurePipelineMapper;
import com.pms.bid.job.service.processInstance.WfTaskService;
import com.pms.bid.job.service.zhanJiang.PressurePipelineService;
import com.pms.bid.job.util.DateUtil;
import com.pms.bid.job.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PressurePipelineServiceImpl implements PressurePipelineService {

    @Autowired
    private PressurePipelineMapper pressurePipelineMapper;

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

    /**
     * 检查是否需要推送消息
     */
    @Override
    public void pushMessage() {
        List<PressurePipeline> list = pressurePipelineMapper.selectList();
        list.forEach(item->{
            String userId = pressurePipelineMapper.selectUserByPrjId(item.getYjwAcceptanceManager());
            item.setYjwAcceptanceManager(userId);
            //计划施工告知时间
            if (null == item.getYjwConstructionNoticeTimePlan()){
                if (isMoreThan30DaysOld(item.getYjwDesignTime())){
                    remind("1",item);
                }

            }else {
                //完成施工告知时间，施工告知回执
                if (null == item.getYjwConstructionNoticeTimeComplete() || null == item.getYjwConstructionReceipt()){
                    Date timePlan = item.getYjwConstructionNoticeTimePlan();
                    if (new Date().after(timePlan)){
                        remind("8",item);
                    }
                }
            }
            //计划安装时间
            if (null == item.getYjwInstallationTimePlan()){
                if (isMoreThan30DaysOld(item.getYjwDesignTime())){
                    remind("2",item);
                }
            }else {
                //实际安装时间
                if (null == item.getYjwInstallationTime()){
                    Date timePlan = item.getYjwInstallationTimePlan();
                    if (new Date().after(timePlan)){
                        remind("9",item);
                    }
                }
            }
            //监督检验计划报检时间
            if (null == item.getYjwReportInsuranceTimePlan()){
                if (isMoreThan30DaysOld(item.getYjwDesignTime())){
                    remind("3",item);
                }
            }else {
                //完成报检时间，上传报检单
                if (null == item.getYjwReportInsuranceTime() || null == item.getYjwReportInsurance()){
                    Date timePlan = item.getYjwReportInsuranceTimePlan();
                    if (new Date().after(timePlan)){
                        remind("10",item);
                    }
                }

            }
            //竣工资料提交特检院受理计划时间
            if (null == item.getYjwAcceptanceTimePlan()){
                if (isMoreThan30DaysOld(item.getYjwDesignTime())){
                    remind("4",item);
                }
            }else {
                //竣工资料提交特检院受理时间
                if (null == item.getYjwAcceptanceTime()){
                    Date timePlan = item.getYjwAcceptanceTimePlan();
                    if (new Date().after(timePlan)){
                        remind("12",item);
                    }
                }
            }
            //具备现场试压条件的计划时间
            if(null == item.getYjwQualifiedTimePlan()){
                if (isMoreThan30DaysOld(item.getYjwDesignTime())){
                    remind("11",item);
                }
            }
            //计划投用带介质时间
            if (null != item.getYjwUsageTimePlan()){
                Date yjwUsageTimePlan = item.getYjwUsageTimePlan();
                //现场试压通过监检机构见证的时间
                if(null == item.getYjwInstitutionTime()){
                    if (new Date().after(yjwUsageTimePlan)){
                        remind("5",item);
                    }
                }else {
                    //填报竣工资料
                    /*if (new Date().after(item.getYjwUsageTimePlan()) && (null == item.getYjwQualifiedReportTime() || new Date().before(item.getYjwQualifiedReportTime()))){
                        Date date = pressurePipelineMapper.selectDateByPipingId(item.getId());
                        //七天为一周期，一周期可多次填写
                        if (before30DaysLater(date)){
                            remind("19",item);
                        }

                    }*/
                }
                //上传耐压试验报告
                if (StringUtils.isEmpty(item.getYjwTestReport())){
                    if (new Date().after(yjwUsageTimePlan)){
                        remind("6",item);
                    }
                }
                //上传监督检验合格报告
                if (null == item.getYjwQualifiedReport()){
                    if (new Date().after(yjwUsageTimePlan)){
                        remind("14",item);
                    }
                }
                //实际投用时间
                if (null == item.getYjwUsageTime()){
                    if (new Date().after(yjwUsageTimePlan)){
                        remind("15",item);
                    }
                }
                //项目单位计划办理使用登记的时间****
                if (null == item.getYjwRegistrationTime()){
                    if (before30DaysLater(new Date(), item.getYjwUsageTimePlan())){
                        remind("16",item);
                    }
                }
                //取得监督检验报告时间
                if (null == item.getYjwQualifiedReportTime()){
                    if (before30DaysLater(new Date(), item.getYjwUsageTimePlan())){
                        remind("17",item);
                    }
                }else {
                    //项目单位办结使用登记的时间****
                    if (null == item.getYjwCompleteRegistrationTime()){
                        remind("18",item);
                    }
                }
            }else {
                //
                if (isMoreThan30DaysOld(item.getYjwDesignTime())){
                    remind("13",item);
                }
            }

            //填报竣工资料
            if (null != item.getYjwInstitutionTime()&& (null == item.getYjwQualifiedReportTime() || new Date().before(item.getYjwQualifiedReportTime()))){
                List<FillingCycle> fillingCycles = generateDateRanges(item.getYjwInstitutionTime(), new Date());
                fillingCycles.forEach(fillingCycle -> {
                    List<String> contents = pressurePipelineMapper.selectContentByTime(fillingCycle.getStart(), fillingCycle.getEnd(),item.getId());
                    if (contents.isEmpty() && new Date().after(getDate(fillingCycle.getEnd()))){
                        pressurePipelineMapper.updateFilling(item.getId(),fillingCycle.getStart()+"————"+fillingCycle.getEnd());
                        remind("19",item);
                    }
                });
            }
            //竣工资料提交特检院受理时间
            if(null != item.getYjwInstitutionTime()){
                if (null == item.getYjwAcceptanceTime()){
                    if (new Date().after(item.getYjwInstitutionTime())){
                        remind("7",item);
                    }
                }
            }
        });
    }

    private Date getDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 将时间段内时间以七天为一周期取出
     * @param start 开始时间
     * @param end  结束时间
     * @return List<FillingCycle>
     */
    public static List<FillingCycle> generateDateRanges(Date start, Date end) {

        List<FillingCycle> dateRanges = new ArrayList<>();
        LocalDate currentStart = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (currentStart.isBefore(endDate) || currentStart.isEqual(endDate)) {
            LocalDate currentEnd = currentStart.plusDays(6); // 加上六天
            if (currentEnd.isAfter(endDate)) {
                currentEnd = endDate;
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
     * @param number
     */
    private void remind(String number,PressurePipeline item){
        if (number.equals("18") || number.equals("16")){
            String userId = pressurePipelineMapper.selectUserByPrjId(item.getYjwConstructionManager());
            item.setYjwAcceptanceManager(userId);
        }
        //  校验是否已发起流程
        String proid = item.getLkWfInstId();
        String now = DateUtil.getNormalTimeStr(new Date());
        String wfId = pressurePipelineMapper.selectWfId(item.getId());
        if (org.springframework.util.StringUtils.hasText(wfId)) {
            //  发起新的通知
            if ("1".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask1())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("2".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask2())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("3".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask3())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("4".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask4())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("5".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask5())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("6".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask6())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("7".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask7())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("8".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask8())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("9".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask9())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("10".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask10())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("11".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask11())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("12".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask12())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("13".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask13())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("14".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask14())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("15".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask15())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("16".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask16())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("17".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask17())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("18".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask18())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }else if ("19".equals(number)){
                if (!org.springframework.util.StringUtils.hasText(item.getYjwTask19())){
                    String taskId = createUserTaskMsg(wfId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
                    //修改时间节点状态
                    pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
                }
            }



        }else {
            // 发起流程
            Map<String, String> result =
                    createWfProcessToSendNode(item, "1820723379691139072", "1816043591190458368", "1819203639730610176", "YJW_PRESSURE_PIPELINE", item.getYjwAcceptanceManager());
            String wfProcessInstanceId = result.get("wfProcessInstanceId");
            pressurePipelineMapper.updateInstIdById(item.getId(),wfProcessInstanceId); //设置流程实例id
            //发任务提醒
            String taskId = createUserTaskMsg(wfProcessInstanceId, item.getYjwAcceptanceManager(), 1, now, "0099250247095871681");
            //修改时间节点状态
            pressurePipelineMapper.updateTaskById(item.getId(),taskId,number);
        }

    }

    private String createUserTaskMsg(String  processInstanceId, String taskUser, Integer isCurrent, String now, String createBy) {
        String nodeInstanceId = wfNodeInstanceMapper.queryYjwNodeInstanceId(processInstanceId);
        WfTask wfTask = new WfTask();
        wfTask.setId(IdUtil.getSnowflakeNextIdStr());
        wfTask.setVer("1");
        wfTask.setTs(now);
        wfTask.setCreateBy(createBy);
        wfTask.setLastUpdateBy(createBy);
        wfTask.setCreateDate(now);
        wfTask.setLastUpdateDate(now);
        wfTask.setStatus("AP");
        wfTask.setWfProcessId("1820723045543522304");
        wfTask.setWfProcessInstanceId(processInstanceId);
        wfTask.setWfNodeId("1820723379691139072");
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
     * @param date
     * @return
     */
    private  boolean isMoreThan30DaysOld(Date date) {
        if (date == null){
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
     * 判断当前时间是否在目标时间之前（30天）
     * @param date
     * @return
     */
    private  boolean before30DaysLater(Date date,Date target) {
        if (date == null){
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 30);

        Date thirtyDaysLater = calendar.getTime();


        return !target.after(thirtyDaysLater);
    }

    /**
     * 今日时间是否在目标时间七天之后
     * @param date
     * @return
     */
    private  boolean before30DaysLater(Date date) {
        if (date == null){
            return true;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 7);

        Date thirtyDaysLater = calendar.getTime();


        return !thirtyDaysLater.after(date);
    }



    /**
     * 发起流程
     */
    public Map<String,String> createWfProcessToSendNode(PressurePipeline tmp, String firstNodeId, String wfProcessId, String entId, String entCode, String adUserId) {
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
        createWfProcessInstance(tmp,wfProcessInstanceId,wfNodeInstanceId,secondNode,wfProcess,now,createBy);
        //  创建第一步节点实例
        createNodeInstance(wfProcessInstanceId,firstNode,tmp.getYjwAcceptanceManager(),0,IdUtil.getSnowflakeNextIdStr(),now,createBy);
        //  创建第二步节点实例
        String taskId = updateNodeInstance(wfProcessInstanceId, secondNode, tmp.getYjwAcceptanceManager(), 1, wfNodeInstanceId, now, createBy);

        Map<String,String>  result=  new HashMap<String,String>();
        result.put("wfProcessInstanceId",wfProcessInstanceId);
        result.put("taskId",taskId);
        return result;
    }

    /**
     * 根据id创建空节点实例
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
     * @param tmp 业务表单数据信息
     * @param wfProcessInstanceId 流程实例id
     * @param wfNodeInstanceId 当前节点实例id
     * @param secondNode 当前节点信息
     */
    private void createWfProcessInstance(PressurePipeline tmp, String wfProcessInstanceId, String wfNodeInstanceId, WfNode secondNode, WfProcess wfProcess, String now, String createBy) {
        WfProcessInstance wfProcessInstance = new WfProcessInstance();
        wfProcessInstance.setId(wfProcessInstanceId);
        wfProcessInstance.setVer("1");
        wfProcessInstance.setTs(now);
        wfProcessInstance.setCreateBy(createBy);
        wfProcessInstance.setLastUpdateBy(createBy);
        wfProcessInstance.setCreateDate(now);
        wfProcessInstance.setLastUpdateDate(now);
        wfProcessInstance.setStatus("AP");
        String name = "压力管道" + "-" + tmp.getYjwPipingName() + "-" + now;
        name = JsonUtil.toJson(new Internationalization(null,name,null));
        wfProcessInstance.setWfProcessInstanceName(name);
        wfProcessInstance.setStartUserId(tmp.getYjwAcceptanceManager());
        wfProcessInstance.setStartDate(now);
        wfProcessInstance.setAdEntId(wfProcess.getAdEntId());
        wfProcessInstance.setAdEntCode(wfProcess.getEntCode());
        wfProcessInstance.setEntityRecordId(tmp.getId());
        wfProcessInstance.setUrgent(0);
        wfProcessInstance.setCurrentNodeId(secondNode.getId());
        wfProcessInstance.setWfNodeInstanceId(wfNodeInstanceId);
        wfProcessInstance.setAdUserId(tmp.getYjwAcceptanceManager());
        wfProcessInstance.setCurrentViewId(secondNode.getViewId());
        wfProcessInstance.setWfProcessId(secondNode.getWfProcessId());
        wfProcessInstanceMapper.create(wfProcessInstance);
    }

    /**
     * 创建发起节点实例
     * @param wfProcessInstanceId 流程实例id
     * @param firstNode 节点信息
     * @param createBy 创建人
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
        wfNodeInstance.setWfNodeInstanceName(JsonUtil.toJson(new Internationalization(null,firstNode.getWfNodeName(),null)));
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
        createUserTask(wfNodeInstance,taskUser,isCurrent,now,createBy);
    }

    /**
     * 判断并生成流程实例任务
     * @param wfNodeInstance 节点实例信息
     * @param taskUser 代办任务用户
     * @param isCurrent 是否当前
     * @param now 当前时间
     * @param createBy 创建人
     */
    private void createUserTask(WfNodeInstance wfNodeInstance, String taskUser, Integer isCurrent, String now, String createBy) {
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


    private String  updateNodeInstance(String wfProcessInstanceId, WfNode firstNode, String taskUser, Integer isCurrent, String wfNodeInstanceId, String now, String createBy) {
        WfNodeInstance wfNodeInstance = new WfNodeInstance();
        wfNodeInstance.setId(wfNodeInstanceId);
        wfNodeInstance.setVer("1");
        wfNodeInstance.setTs(now);
        wfNodeInstance.setCreateBy(createBy);
        wfNodeInstance.setLastUpdateBy(createBy);
        wfNodeInstance.setCreateDate(now);
        wfNodeInstance.setLastUpdateDate(now);
        wfNodeInstance.setStatus("AP");
        wfNodeInstance.setWfNodeInstanceName(JsonUtil.toJson(new Internationalization(null,firstNode.getWfNodeName(),null)));
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
        return createTask(wfNodeInstance,taskUser,isCurrent,now,createBy);
    }

    private String   createTask(WfNodeInstance wfNodeInstance, String userId, Integer isCurrent, String now, String createBy) {
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

        return   taskId;
    }
}
