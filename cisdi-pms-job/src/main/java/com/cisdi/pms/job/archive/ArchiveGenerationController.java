package com.cisdi.pms.job.archive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("archiveGeneration")
public class ArchiveGenerationController {

    @Autowired
    ArchiveGenerationService archiveGenerationService;

    /**
     * http://localhost:11115/cisdi-pms-job/archiveGeneration/execute
     */
    @GetMapping("execute")
    public void execute() {
        archiveGenerationService.execute();
    }

    /**
     * http://localhost:11115/cisdi-pms-job/archiveGeneration/createProcInstForImportedData
     */
    @GetMapping("createProcInstForImportedData")
    public void createProcInstForImportedData() {
        archiveGenerationService.createProcInstForImportedData();
    }

    /**
     * http://localhost:11115/cisdi-pms-job/archiveGeneration/createNodeInstListForImportedData
     */
    @GetMapping("createNodeInstListForImportedData")
    public void createNodeInstListForImportedData() {
        archiveGenerationService.createNodeInstListForImportedData();
    }
}
