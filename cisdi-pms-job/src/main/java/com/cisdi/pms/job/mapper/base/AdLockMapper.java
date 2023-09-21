package com.cisdi.pms.job.mapper.base;

import com.cisdi.pms.job.domain.base.AdLock;

public interface AdLockMapper {

    /**
     * 根据编码修改锁
     * @param code 编码
     * @return 修改条数
     */
    int updateLockByCodeAndTenMin(String code);

    /**
     * 根据编码修改释放锁
     * @param code 编码
     * @return 修改条数
     */
    int updateLockByCode(String code);
}
