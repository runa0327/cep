package com.pms.cisdipmswordtopdf.serviceImpl;

import com.pms.cisdipmswordtopdf.mapper.PoOrderReqMapper;
import com.pms.cisdipmswordtopdf.model.PoOrderReq;
import com.pms.cisdipmswordtopdf.service.FlFileService;
import com.pms.cisdipmswordtopdf.service.PoOrderReqService;
import com.pms.cisdipmswordtopdf.service.WordToPdfService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PoOrderReqServiceImpl implements PoOrderReqService {

    @Resource
    private PoOrderReqMapper poOrderReqMapper;

    @Resource
    private WordToPdfService wordToPdfService;

    @Resource
    private FlFileService flFileService;

    /**
     * 转换某一时间段内word
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    @Override
    public void toPdfByDate(String startTime, String endTime) {
        List<String> wfProcessInstanceList = new ArrayList<>();
        List<String> processInstanceList;
        // 查询时间范围内的流程信息 发起时间
        List<String> startList = poOrderReqMapper.queryCreateProcessListByDate(startTime,endTime);
        // 查询时间范围内的流程信息 分管领导审批、总经理审批
        List<String> chargeList = poOrderReqMapper.queryChargeCheckListByDate(startTime,endTime);
        // 查询时间范围内的流程信息 总经理审批
        List<String> leaderList = poOrderReqMapper.queryLeaderCheckListByDate(startTime,endTime);

        if (!CollectionUtils.isEmpty(startList)){
            wfProcessInstanceList.addAll(startList);
        }
        if (!CollectionUtils.isEmpty(chargeList)){
            wfProcessInstanceList.addAll(chargeList);
        }
        if (!CollectionUtils.isEmpty(leaderList)){
            wfProcessInstanceList.addAll(leaderList);
        }

        if (!CollectionUtils.isEmpty(wfProcessInstanceList)){
            processInstanceList = wfProcessInstanceList.stream().distinct().collect(Collectors.toList());
            List<PoOrderReq> list = poOrderReqMapper.queryListByInstanceId(processInstanceList);
            if (!CollectionUtils.isEmpty(list)){
                for (PoOrderReq tmp : list) {
                    checkAndToPdf(tmp);
                }
            }
        }
    }

    /**
     * 通过某一个id查询合同签订信息并转pdf
     *
     * @param id id
     */
    @Override
    public void toPdfById(String id) {
        PoOrderReq poOrderReq = poOrderReqMapper.queryOneById(id);
        if (poOrderReq != null){
            checkAndToPdf(poOrderReq);
        }
    }

    /**
     * 数据判断并转pdf
     * @param tmp 实体信息
     */
    private void checkAndToPdf(PoOrderReq tmp) {
        Map<String,String> map = new HashMap<>();
        List<Map<String,String>> list1 = new ArrayList<>();
        String fileId;
        // 是否标准模板 0099799190825080669=是 0099799190825080670=否
        String isModel = tmp.getIsModel();
        if ("0099799190825080669".equals(isModel)){
            fileId = tmp.getContractTxt();
            if (StringUtils.hasText(fileId)){
                boolean res = flFileService.checkPdf(fileId); // true=有pdf文件
                if (!res){
                    map.put("code","ATT_FILE_GROUP_ID");
                    map.put("file",tmp.getContractTxt());
                }
            }
        } else {
            fileId = tmp.getContractRevise();
            if (StringUtils.hasText(fileId)){
                boolean res = flFileService.checkPdf(fileId); // true=有pdf文件
                if (!res){
                    map.put("code","FILE_ID_ONE");
                    map.put("file",tmp.getContractRevise());
                }
            }
        }
        if (!map.isEmpty()){
            list1.add(map);
        }
        if (!CollectionUtils.isEmpty(list1)){
            tmp.setColMap(list1);
            wordToPdfService.wordToPdf(tmp);
        }
    }
}
