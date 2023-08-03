package com.cisdi.pms.job.service;

import com.cisdi.pms.job.domain.process.PoOrderReq;

public interface WordToPdfService {

    /**
     * word转pdf
     * @param poOrderReq
     */
    void wordToPdf(PoOrderReq poOrderReq);
}
