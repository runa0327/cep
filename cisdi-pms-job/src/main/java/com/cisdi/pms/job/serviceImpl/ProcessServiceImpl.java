package com.cisdi.pms.job.serviceImpl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSONObject;

import com.cisdi.pms.job.domain.ProcessModel;
import com.cisdi.pms.job.domain.ProcessReq;
import com.cisdi.pms.job.service.ProcessService;
import com.cisdi.pms.job.strategy.MergeStrategy;
import com.cisdi.pms.job.utils.DateUtil;
import com.cisdi.pms.job.utils.ExportUtil;
import com.google.common.base.Strings;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author dlt
 * @date 2023/3/21 周二
 */
@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @SneakyThrows
    public void export(ProcessReq processReq, HttpServletResponse response) {
        if (Strings.isNullOrEmpty(processReq.getStartDate())){
            processReq.setStartDate("2022-11-22 00:00:00");
        }
        if (Strings.isNullOrEmpty(processReq.getEndDate())){
            processReq.setEndDate(DateUtil.getNormalTimeStr(new Date()));
        }

        List<ProcessModel> processModels = this.getProcessModels(processReq);
        List<List<String>> heads = this.getHeads(processReq);
        //其他信息拼接在数据最下方
        String otherInfo = this.getOtherInfo(processReq);
        ProcessModel otherData = new ProcessModel();
        otherData.setProcessName(otherInfo);
        processModels.add(otherData);
        //设置合并
        List<CellRangeAddress> cellRangeAddresses = new ArrayList<>();
        cellRangeAddresses.add(new CellRangeAddress(processModels.size(),processModels.size(),0,4));
        //设置头居中
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition","attachment; filename=" + URLEncoder.encode("流程使用情况" + ".xlsx","utf-8"));
        EasyExcel.write(response.getOutputStream())
                .head(heads)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("流程使用情况")//文件名作为sheet名
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(25))
                .registerWriteHandler(new MergeStrategy(processModels.size(),0,cellRangeAddresses))
                .registerWriteHandler(horizontalCellStyleStrategy)
                .doWrite(processModels);
//        ExportUtil.exportExcel(response,"流程使用情况",processModels,heads);
    }

    public List<ProcessModel> getProcessModels(ProcessReq processReq) {
        List<Map<String, Object>> processMaps = jdbcTemplate.queryForList("select p.name processName,IFNULL(p.num,0) processNum,IFNULL(ep.num,0) " +
                "endProcessNum,IFNULL(sp.num,0) datedProcessNum,IFNULL(sep.num,0) datedEndProcessNum from \n" +
                "(select a.WF_PROCESS_ID,b.name,count(*) num from wf_process_instance a left join wf_process b on a.WF_PROCESS_ID = b.id WHERE " +
                "START_DATETIME >= '2022-11-22 00:00:00' and b.STATUS in ('VDING','AP')  and a.START_USER_ID != '0099250247095871681' and a.status " +
                "not in ('VD','VDING') GROUP BY a.WF_PROCESS_ID) p \n" +
                "left join (select a.WF_PROCESS_ID,b.name,count(*) num from wf_process_instance a left join wf_process b on a.WF_PROCESS_ID = b.id " +
                "WHERE a.START_DATETIME >= '2022-11-22 00:00:00' and a.END_DATETIME is not null and b.STATUS in ('VDING','AP') and a.START_USER_ID " +
                "!= '0099250247095871681' and a.status not in ('VD','VDING')  GROUP BY a.WF_PROCESS_ID) ep on ep.WF_PROCESS_ID = p.WF_PROCESS_ID\n" +
                "left join (select a.WF_PROCESS_ID,b.name,count(*) num from wf_process_instance a left join wf_process b on a.WF_PROCESS_ID = b.id " +
                "WHERE START_DATETIME >= ? and START_DATETIME <= ? and b.STATUS in ('VDING','AP') and a.START_USER_ID != '0099250247095871681' and a" +
                ".status not in ('VD','VDING') GROUP BY a.WF_PROCESS_ID) sp on sp.WF_PROCESS_ID = p.WF_PROCESS_ID\n" +
                "left join (select a.WF_PROCESS_ID,b.name,count(*) num from wf_process_instance a left join wf_process b on a.WF_PROCESS_ID = b.id " +
                "WHERE a.START_DATETIME >= ? and a.START_DATETIME <= ? and a.END_DATETIME is not null and b.STATUS in ('VDING','AP') and a.START_USER_ID " +
                "!= '0099250247095871681' and a.status not in ('VD','VDING') GROUP BY a.WF_PROCESS_ID) sep on sep.WF_PROCESS_ID = p.WF_PROCESS_ID",
                processReq.getStartDate(),processReq.getEndDate(),processReq.getStartDate(),processReq.getEndDate());
        List<ProcessModel> processModels = new ArrayList<>();
        int allProcessNum = 0;//上线发起总数
        int allEndProcessNum = 0;//上线结束总数
        int allDatedProcessNum = 0;//日期至今发起总数
        int allDatedEndProcessNum = 0;//日期至今结束总数
        for (Map<String, Object> processMap : processMaps) {
            ProcessModel processModel = JSONObject.parseObject(JSONObject.toJSONString(processMap), ProcessModel.class);
            allProcessNum += Integer.parseInt(processModel.getProcessNum());
            allEndProcessNum += Integer.parseInt(processModel.getEndProcessNum());
            allDatedProcessNum += Integer.parseInt(processModel.getDatedProcessNum());
            allDatedEndProcessNum += Integer.parseInt(processModel.getDatedEndProcessNum());
            processModels.add(processModel);
        }

        //总计
        ProcessModel sumData = new ProcessModel();
        sumData.setProcessName("总计");
        sumData.setProcessNum(String.valueOf(allProcessNum));
        sumData.setEndProcessNum(String.valueOf(allEndProcessNum));
        sumData.setDatedProcessNum(String.valueOf(allDatedProcessNum));
        sumData.setDatedEndProcessNum(String.valueOf(allDatedEndProcessNum));
        processModels.add(sumData);
        return processModels;
    }

    //获取其他信息
    private String getOtherInfo(ProcessReq processReq){
        List<Map<String, Object>> otherList = jdbcTemplate.queryForList("select count(*) num from wf_task where CRT_DT >= ? and CRT_DT <= ? and WF_TASK_TYPE_ID = " +
                "'TODO' AND AD_USER_ID != '0099250247095871681' union all\n" +
                "select count(*) from wf_task where CRT_DT >= ? and CRT_DT <= ? and WF_TASK_TYPE_ID = 'TODO' AND AD_USER_ID != '0099250247095871681' and " +
                "IS_CLOSED = 1 union all\n" +
                "select count(*) from wf_node_instance where CRT_DT >= ? and CRT_DT <= ? ",
                processReq.getStartDate(),processReq.getEndDate(),processReq.getStartDate(),processReq.getEndDate(),processReq.getStartDate(),processReq.getEndDate());

        String otherInfo = "";
        otherInfo += "共计发起节点代办任务" + otherList.get(0).get("num") + "条,";
        otherInfo += "已完成" + otherList.get(1).get("num") + "条。";
        otherInfo += "共发起生成节点" + otherList.get(2).get("num") + "个。";
        return otherInfo;
    }


    /**
     * 设置表头
     */
    private List<List<String>> getHeads(ProcessReq processReq){
        ArrayList<String> headNames = new ArrayList<>(Arrays.asList("流程名称", "上线至今发起数", "上线至今结束数",
                processReq.getStartDate().substring(0,10) + "至" + processReq.getEndDate().substring(0,10) + "发起数",
                processReq.getStartDate().substring(0,10) + "至" + processReq.getEndDate().substring(0,10) + "结束数"));

        List<List<String>> heads = ExportUtil.covertHead(headNames);
        return heads;
    }
}
