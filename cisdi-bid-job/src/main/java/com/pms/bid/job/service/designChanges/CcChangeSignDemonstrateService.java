package com.pms.bid.job.service.designChanges;

import com.pms.bid.job.domain.qbq.QbqCallbackRequest;
import com.pms.bid.job.domain.qbq.QbqCallbackResponse;

public interface CcChangeSignDemonstrateService {

    QbqCallbackResponse checkChangeSignStatus(QbqCallbackRequest request);


}
