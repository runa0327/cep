package com.cisdi.pms.job.archive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ArchiveGenerationJob {

    @Autowired
    ArchiveGenerationService archiveGenerationService;


    /**
     * 10分钟1次。
     */
    @Scheduled(fixedDelayString = "600000")
    public void execute() {
        // archiveGenerationService.createProcInstForImportedData();
        archiveGenerationService.execute();
    }
}