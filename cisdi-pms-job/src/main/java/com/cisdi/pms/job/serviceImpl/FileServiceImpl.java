package com.cisdi.pms.job.serviceImpl;

import com.cisdi.pms.job.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据文件id、文件地址校验文件信息真实性，并取得文件名称
     * @param fileId 文件id
     * @param fileAddress 文件地址
     * @return 文件名称
     */
    @Override
    public String getFileName(String fileId, String fileAddress) {
        String sql = "";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,fileId,fileAddress);
        if (!CollectionUtils.isEmpty(list)){

        } else {

        }
        return null;
    }
}
