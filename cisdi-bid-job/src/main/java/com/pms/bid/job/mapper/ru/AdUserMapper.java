package com.pms.bid.job.mapper.ru;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pms.bid.job.domain.ru.AdUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdUserMapper extends BaseMapper<AdUser> {

    public AdUser  selectUserByPrjMemberId(String memberId);

    public List<AdUser>  selectUserByPrjMemberIds(@Param("memberIds") List<String> memberIds);

    public AdUser  selectByWfProcessInstanceId(@Param("processInstanceId") String processInstanceId);

}
