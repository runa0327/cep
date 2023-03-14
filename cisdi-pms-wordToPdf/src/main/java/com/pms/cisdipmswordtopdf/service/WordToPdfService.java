package com.pms.cisdipmswordtopdf.service;

import com.pms.cisdipmswordtopdf.model.PoOrderReq;

public interface WordToPdfService {

    /**
     * word转pdf
     * @param poOrderReq
     */
    void wordToPdf(PoOrderReq poOrderReq);
}
