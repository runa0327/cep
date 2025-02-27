package com.pms.bid.job.mapper.ru;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pms.bid.job.domain.ru.AdUser;

public interface AdUserMapper extends BaseMapper<AdUser> {

    public AdUser  selectUserByPrjMemberId(String memberId);

}
