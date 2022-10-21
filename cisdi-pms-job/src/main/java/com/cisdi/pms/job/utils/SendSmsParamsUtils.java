package com.cisdi.pms.job.utils;

import com.google.common.base.Joiner;

import java.util.List;
import java.util.Map;

/**
 * @author Lenovo
 *
 */
public class SendSmsParamsUtils {

	/**
	 * 获取多个参数
	 * @param parmsList
	 * @return
	 */
	public byte[] getParmas(Map<String,String> parmsList) {
		String reqmsg="{\"authCode\":\""+ parmsList.get("authCode") +"\",\"msgType\":\""+ parmsList.get("msgType") +"\",\"receiverUser\":\""+ parmsList.get("receiverUser") +"\",\"content\":\"{\\\""+ parmsList.get("content") + "\\\"}\",\"senderSystem\":\""+ parmsList.get("senderSystem") +"\"}";
		byte[] bytes = reqmsg.getBytes();
		return bytes;
	}

	/**
	 * 获取单个参数
	 * @param parmsList
	 * @return
	 */
	public byte[] getOneParam(List<String> parmsList) {
		//参数拼接，相当buffer循环拼接
		String paramsStr = Joiner.on("!@#$%^&*").join(parmsList);
		String reqmsg="{\"authCode\":\"95b7aa968ff7427cb3f7071f22df1ddb\",\"msgType\":\"2\",\"receiverUser\":\"+8615023436971\",\"content\":\"{\\\"templateId\\\":1365335,\\\"params\\\":\\\""+paramsStr+"\\\",\\\"1510977537772093440\\\"}\",\"senderSystem\":\"9a6bdcde547a43aeaaef3da577f9d0bc\"}";
		byte[] bytes1 = reqmsg.getBytes();
		return bytes1;
	}


}


