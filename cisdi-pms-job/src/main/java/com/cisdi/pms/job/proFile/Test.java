package com.cisdi.pms.job.proFile;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * @author dlt
 * @date 2022/11/3 周四
 */
@Slf4j
@Controller
@RequestMapping("/test")
public class Test {

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @GetMapping("/requestTest")
    public List<String> requestTest() throws ExecutionException, InterruptedException {
        List<String> ids = Lists.newArrayList();
        ids.add("1");
        ids.add("2");
        ids.add("3");
        ids.add("4");
        //有返回值的情况，定义接收返回值
        List<String> futureList2 = Lists.newArrayList();
        //分布式计数器，若业务不需要则可以不定义
        CountDownLatch countDownLatch = new CountDownLatch(ids.size());
        for (String id : ids) {
            //调用线程池的线程执行任务
            threadPoolTaskExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    test(Lists.newArrayList(id),futureList2);
                    //计数器-1
                    countDownLatch.countDown();
                }
            });
        }
        //await阻塞，直到计数器为0
        countDownLatch.await();

        System.out.println("主线程"+"====");
        return futureList2;
    }

    public List<String> test(List<String> ids, List<String> list2){
        //随便写的业务逻辑代码，无实际意义，仅作演示
        System.out.println("线程体" + "====");
        List<String> accountRecordVOS = Lists.newArrayList();
        int i = 0;
//        AccountRecordVO accountRecordVO = new AccountRecordVO();
//        accountRecordVO.setUserId("123");
//        accountRecordVO.setAmount(12333);
//        for (String id : ids){
//            accountRecordVOS.add(accountRecordVO);
//            list2.add(accountRecordVO);
//        }
        try{
            Thread.sleep(Long.valueOf("1000"));
        }catch (Exception e){
            log.error(e.getMessage());
        }
        System.out.println("线程体结束" + "====");
        return accountRecordVOS;
    }
}
