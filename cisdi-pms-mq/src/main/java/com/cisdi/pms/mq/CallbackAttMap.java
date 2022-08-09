package com.cisdi.pms.mq;

import com.qygly.shared.SharedConstants;
import com.qygly.shared.util.SharedUtil;
import com.qygly.shared.wf.callback.CallbackAtt;
import org.apache.tomcat.util.collections.CaseInsensitiveKeyMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallbackAttMap extends CaseInsensitiveKeyMap<CallbackAtt> {

    public String getString(String attCode) {
        if (this.containsKey(attCode)) {
            return this.get(attCode).attValue;
        } else {
            return null;
        }
    }

    public Boolean getBoolean(String attCode) {
        String s = getString(attCode);
        if (SharedUtil.isEmptyString(s)) {
            return null;
        }

        return "1".equals(s);
    }

    public Integer getInteger(String attCode) {
        String s = getString(attCode);
        if (SharedUtil.isEmptyString(s)) {
            return null;
        }

        int ret = Double.valueOf(s).intValue();
        return ret;
    }

    public Double getDouble(String attCode) {
        String s = getString(attCode);
        if (SharedUtil.isEmptyString(s)) {
            return null;
        }

        Double ret = Double.valueOf(s);
        return ret;
    }

    public Date getDate(String attCode) throws ParseException {
        String s = getString(attCode);
        if (SharedUtil.isEmptyString(s)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SharedConstants.DATE_FORMAT);
        Date ret = simpleDateFormat.parse(s);
        return ret;
    }

    public String getTime(String attCode) {
        String s = getString(attCode);
        if (SharedUtil.isEmptyString(s)) {
            return null;
        }
        return s;
    }

    public Date getDateTime(String attCode) throws ParseException {
        String s = getString(attCode);
        if (SharedUtil.isEmptyString(s)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SharedConstants.DTTM_FORMAT);
        String s2 = s.replaceFirst("T", " ");
        Date ret = simpleDateFormat.parse(s2);
        return ret;
    }

    public String getUserName(String attCode) {
        if (!this.containsKey(attCode)) {
            return null;
        }

        return this.get(attCode).refedDataCodeAttValue;
    }

    public String getUserCpmsId(String attCode) {
        if (!this.containsKey(attCode)) {
            return null;
        }

        return this.get(attCode).refedDataExtAtt1Value;
    }

    public String getDeptName(String attCode) {
        if (!this.containsKey(attCode)) {
            return null;
        }

        return this.get(attCode).refedDataNameAttValue;
    }

    public String getDeptCpmsId(String attCode) {
        if (!this.containsKey(attCode)) {
            return null;
        }

        return this.get(attCode).refedDataExtAtt1Value;
    }

    public String getDictLabel(String attCode) {
        if (!this.containsKey(attCode)) {
            return null;
        }

        return this.get(attCode).refedDataNameAttValue;
    }

    public String getDictValue(String attCode) {
        if (!this.containsKey(attCode)) {
            return null;
        }

        return this.get(attCode).refedDataExtAtt2Value;
    }

    public String getDictNameValue(String attCode) {
        if (!this.containsKey(attCode)) {
            return null;
        }

        return this.get(attCode).refedDataNameAttValue;
    }

    public String getRefedName(String attCode) {
        if (!this.containsKey(attCode)) {
            return null;
        }

        return this.get(attCode).refedDataNameAttValue;
    }
}
