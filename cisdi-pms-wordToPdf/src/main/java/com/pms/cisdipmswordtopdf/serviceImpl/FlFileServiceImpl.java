package com.pms.cisdipmswordtopdf.serviceImpl;

import com.pms.cisdipmswordtopdf.mapper.FlFileMapper;
import com.pms.cisdipmswordtopdf.model.FlFile;
import com.pms.cisdipmswordtopdf.service.FlFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FlFileServiceImpl implements FlFileService {

    @Resource
    private FlFileMapper flFileMapper;

    /**
     * 通过fileId查询文件信息
     * @param fileId 文件id
     * @return 文件信息
     */
    @Override
    public List<FlFile> getFileMessageByFileId(String fileId) {
        fileId = fileId.replace(",","','");
        return flFileMapper.getFileMessageByFileId(fileId);
    }
}
