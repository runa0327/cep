package com.pms.cisdipmswordtopdf.service;

public interface PoOrderReqService {

    /**
     * 转换某一时间段内word
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    void toPdfByDate(String startTime, String endTime);

    /**
     * 通过某一个id查询合同签订信息并转pdf
     * @param id id
     */
    void toPdfById(String id);
}
