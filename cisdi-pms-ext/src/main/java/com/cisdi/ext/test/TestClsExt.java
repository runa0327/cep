package com.cisdi.ext.test;

import com.qygly.ext.jar.helper.ExtJarHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestClsExt {
    public void test() {
        String flowId = ExtJarHelper.flowId.get();
        log.info(flowId);
    }
}
