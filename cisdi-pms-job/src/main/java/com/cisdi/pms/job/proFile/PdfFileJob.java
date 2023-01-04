package com.cisdi.pms.job.proFile;

import cn.hutool.core.util.IdUtil;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.cisdi.pms.job.utils.ListUtils;
import com.cisdi.pms.job.utils.StringUtils;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.qygly.ext.rest.helper.feign.client.DataFeignClient;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dlt
 * @date 2023/1/3 周二
 */
@Component
@Slf4j
public class PdfFileJob {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataFeignClient dataFeignClient;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Value("${spring.task.execution.pool.core-size}")
    private Integer coreSize;

    /**
     * 合同最终版 word转pdf 不经过法务
     */
//    @Scheduled(fixedDelayString = "3000")
//    @Scheduled(cron = "0/10 * * * * ?")
//    @Scheduled(cron = "00 50 13 ? * *")
    @Async("taskExecutor")
    public void wordToPdf(){
        //合同签订中需要转pdf的文件信息
//        List<Map<String, Object>> contractFileList = jdbcTemplate.queryForList("select r.ID,r.name PROCESS_NAME,r.ATT_FILE_GROUP_ID,r.CRT_USER_ID,u.name USER_NAME from po_order_req r\n" +
//                "left join ad_user u on u.id = r.CRT_USER_ID\n" +
//                "where (r.IS_PDF_CONVERTED = 0 or r.IS_PDF_CONVERTED is null) and (r.STATUS = 'AP' or r.STATUS = 'APING')\n");
        List<Map<String, Object>> contractFileList = jdbcTemplate.queryForList("select r.ID,r.name PROCESS_NAME,r.ATT_FILE_GROUP_ID,r.CRT_USER_ID,u.name USER_NAME from po_order_req r\n" +
                "left join ad_user u on u.id = r.CRT_USER_ID\n" +
                "where (r.IS_PDF_CONVERTED = 0 or r.IS_PDF_CONVERTED is null) and (r.STATUS = 'AP' or r.STATUS = 'APING') and r.id = '0099952822476439131'\n");
        int size = contractFileList.size() / coreSize + 1;//线程数
        List<List<Map<String, Object>>> contractFileLists = ListUtils.split(contractFileList, size);
        AtomicInteger count = new AtomicInteger(1);
        for (List<Map<String, Object>> contractFiles : contractFileLists) {
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    for (Map<String, Object> contractFile : contractFiles) {
                        log.info("转换第"+count.getAndIncrement()+"份文件");
                        //用户姓名
                        String waterMark = "工程项目协调系统-" + JdbcMapUtil.getString(contractFile,"USER_NAME");
                        //用户id
                        String userId = JdbcMapUtil.getString(contractFile,"CRT_USER_ID");
                        //流程id
                        String csId = JdbcMapUtil.getString(contractFile,"ID");
                        //文件id
                        String fileIds = JdbcMapUtil.getString(contractFile,"ATT_FILE_GROUP_ID");
                        if (SharedUtil.isEmptyString(fileIds)){
                            throw new BaseException(JdbcMapUtil.getString(contractFile,"PROCESS_NAME") + ":合同文本不能为空，请上传合同文件");
                        }
                        List<Map<String, Object>> fileInfos = jdbcTemplate.queryForList("select ID,PHYSICAL_LOCATION,EXT,NAME from fl_file where " +
                                "FIND_IN_SET(ID,?)", fileIds);
                        if (CollectionUtils.isEmpty(fileInfos)){
                            throw new BaseException("没有找到文件！");
                        }
                        for (Map<String, Object> fileInfo : fileInfos) {
                            //原文件id
                            String id = JdbcMapUtil.getString(fileInfo, "ID");
                            //插入fl_file
                            String newId = IdUtil.getSnowflakeNextIdStr();
                            jdbcTemplate.update("insert into fl_file (ID,CRT_DT,CRT_USER_ID) values (?,(now()),?)",newId,userId);
                            //未转换文件存储地址
                            String address = JdbcMapUtil.getString(fileInfo, "PHYSICAL_LOCATION");
                            //转换后文件存储地址(临时)
                            String tempAddress = address.substring(0,address.lastIndexOf("/")) + "temp.pdf";
                            //转换后文件存储地址(加水印后)
                            String newAddress = address.substring(0,address.lastIndexOf("/")) + newId + ".pdf";
                            //文件名称
                            String fileName = JdbcMapUtil.getString(fileInfo, "NAME");
                            //文件类型
                            String fileType = JdbcMapUtil.getString(fileInfo, "EXT");
                            Map<Object, Object> sizeAndName = new HashMap<>();
                            if ("doc".equals(fileType) || "docx".equals(fileType)){//是word格式转pdf
                                try {
                                    //转换
                                    sizeAndName = toPdf(address, tempAddress,newAddress);
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                                //文件大小
                                String fileSize = StringUtils.formatFileSize(Long.valueOf(sizeAndName.get("size").toString()));
                                //展示名称
                                String showName = sizeAndName.get("name").toString();
                                //更新fl_file
                                jdbcTemplate.update("update fl_file set code = ?,name = ?,ver = '1',FL_PATH_ID = '0099250247095872690',EXT = 'pdf'," +
                                                "STATUS = 'AP',CRT_DT = (now()),CRT_USER_ID = ?,LAST_MODI_DT = (now()),LAST_MODI_USER_ID = ?,SIZE_KB = ?," +
                                                "TS = (now()),UPLOAD_DTTM = (now()),PHYSICAL_LOCATION = ?,DSP_NAME = ?,DSP_SIZE = ? where id = ?",
                                        newId,fileName,userId,userId,fileSize,newAddress,showName,fileSize,newId);
                            }else {//不是word格式，复制
                                jdbcTemplate.update("update fl_file f1,fl_file f2 set f1.code = f2.code,f1.name = f2.name,f1.ver = '1',f1.FL_PATH_ID = '0099250247095872690'," +
                                        "f1.EXT = f2.EXT,f1.status = f2.status,f1.CRT_DT = NOW(),f1.CRT_USER_ID = f2.CRT_USER_ID,f1.LAST_MODI_DT = NOW()," +
                                        "f1.LAST_MODI_USER_ID = f2.LAST_MODI_USER_ID,f1.SIZE_KB = f2.SIZE_KB,f1.TS = NOW(),f1.UPLOAD_DTTM = NOW()," +
                                        "f1.PHYSICAL_LOCATION = f2.PHYSICAL_LOCATION,f1.DSP_NAME = f2.DSP_NAME,f1.DSP_SIZE = f2.DSP_SIZE " +
                                        "where f1.id = ? and f2.id = ?",id,newId);
                            }
                            //将生成的pdf写入流程新字段 FILE_ID_FIVE
                            jdbcTemplate.update("update PO_ORDER_REQ set FILE_ID_FIVE = ?,IS_PDF_CONVERTED = 1 where id = ?",newId,csId);
                        }
                    }
                }
            });
        }

    }

    /**
     * word转pdf 返回文件大小
     */
    private Map toPdf(String input,String temp, String output) throws IOException {

        OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1",8100);//本地
//        OpenOfficeConnection connection = new SocketOpenOfficeConnection("139.159.138.11",8100);//测试
//        OpenOfficeConnection connection = new SocketOpenOfficeConnection("124.222.60.191",8100);//正式
        connection.connect();

        File inputFile = new File(input);
        File outputFile = new File(output);

        StreamOpenOfficeDocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
        //转pdf
        converter.convert(inputFile, outputFile);
        //加水印
        this.addFooterAndWater("无痕水印",temp,output);
        File file = new File(output);
        if (!file.exists() || !file.isFile()){
            throw new BaseException("'"+output+"'该文件不存在");
        }
        float length = file.length();
        String fileName = file.getName();
        Map map = new HashMap();
        map.put("size",length);
        map.put("name",fileName);
        return map;
    }


    private void addFooterAndWater(String str, String output, String outputWaterMark) {
        try {
            PdfReader reader = new PdfReader(output);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputWaterMark));
            //这里的字体设置比较关键，这个设置是支持中文的写法
            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);// 使用系统字体
            int total = reader.getNumberOfPages() + 1;
            PdfContentByte under;
            Rectangle pageRect = null;
            for (int i = 1; i < total; i++) {
                for (int i1 = 1; i1 <= 3; i1++) {
                    pageRect = stamper.getReader().getPageSizeWithRotation(i);
                    // 计算水印X,Y坐标
                    float x = (pageRect.getWidth()/3)*i1;
                    float y = (pageRect.getHeight()/3)*i1;
                    // 获得PDF最顶层
                    under = stamper.getOverContent(i);
                    under.saveState();
                    // set Transparency
                    PdfGState gs = new PdfGState();
                    // 设置透明度为0.2
                    gs.setFillOpacity(1.f);
                    under.setGState(gs);
                    under.restoreState();
                    under.beginText();
                    under.setFontAndSize(base, 60);
                    under.setColorFill(BaseColor.ORANGE);
                    // 水印文字成45度角倾斜
                    under.showTextAligned(Element.ALIGN_CENTER , str, x, y, 55);
                    // 添加水印文字
                    under.endText();
                    under.setLineWidth(1f);
                    under.stroke();
                }

            }
            stamper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
