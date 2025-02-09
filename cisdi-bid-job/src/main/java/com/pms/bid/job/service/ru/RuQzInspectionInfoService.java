package com.pms.bid.job.service.ru;

import cn.hutool.json.JSONObject;

import java.io.IOException;

public interface RuQzInspectionInfoService {

    public void  syncQzInspectionInfo();

    public JSONObject getInspectionDetail(String infoId, String token);

}
