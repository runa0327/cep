package com.cisdi.pms.job.serviceImpl.project;

import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.mapper.project.PmPrjMapper;
import com.cisdi.pms.job.service.project.PmPrjService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.*;

@Service
public class PmPrjServiceImpl implements PmPrjService {

    @Resource
    private PmPrjMapper pmPrjMapper;

    /**
     * 形象进度工程周报-需要自动生成周报的项目
     * @return 项目集合
     */
    @Override
    public List<PmPrj> getNeedWeekPrj() {
        return pmPrjMapper.getNeedWeekPrj();
    }

    /**
     * 刷新系统项目资金信息
     *
     * @param pmPrj 项目实体
     * @return 刷新条数
     */
    @Override
    public int refreshAmt(PmPrj pmPrj) {

        // 创建一个包含 10 个线程的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ExecutorCompletionService<Boolean> service = new ExecutorCompletionService<Boolean>(executorService);

        List<PmPrj> list = pmPrjMapper.queryProject(pmPrj);
        int count = 0;
        if (!CollectionUtils.isEmpty(list)){
            try {
                for (PmPrj tmp : list) {
                    service.submit(() -> {
                        String projectId = tmp.getProjectId();
                        return checkAndUpdatePrjAmt(projectId);
                    });
                }
                for (PmPrj tmp : list) {
                    boolean f = service.take().get();
                    if (f){
                        count++;
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return count;
    }

    /**
     * 查询修改项目库项目资金信息
     * @param projectId 项目id
     */
    public boolean checkAndUpdatePrjAmt(String projectId) {
        PmPrj pmPrj = pmPrjMapper.queryPrjAmtBySettle(projectId); // 结算信息
        if (pmPrj == null){
            pmPrj = pmPrjMapper.queryPrjAmtByInvest3(projectId); // 预算财评
            if (pmPrj == null){
                pmPrj = pmPrjMapper.queryPrjAmtByInvest2(projectId); // 初设概算
                if (pmPrj == null){
                    pmPrj = pmPrjMapper.queryPrjAmtByInvest1(projectId); // 可研估算
                    if (pmPrj == null){
                        pmPrj = pmPrjMapper.queryPrjAmtByPmPrjReq(projectId); // 立项审批
                        if (pmPrj != null){
                            pmPrj.setInvestPriority("1631460206540161024");
                        }
                    } else {
                        pmPrj.setInvestPriority("1631460270226472960");
                    }
                } else {
                    pmPrj.setInvestPriority("1631460343433854976");
                }
            } else {
                pmPrj.setInvestPriority("1631460383644647424");
            }
        } else {
            pmPrj.setInvestPriority("1640251041104666624");
        }
        if (pmPrj != null){
            int num = pmPrjMapper.updateConditionById(pmPrj);
            if (num > 0){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
