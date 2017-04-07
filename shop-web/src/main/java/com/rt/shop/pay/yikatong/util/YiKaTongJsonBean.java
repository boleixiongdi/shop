package com.rt.shop.pay.yikatong.util;
/**
 * 一卡通支付回调消息类
 * */
public class YiKaTongJsonBean {
//	private String resultCode;
//	private String resultMsg;
//	private String signMsg;
	private String resultCode;//消息合法标示   “true”  不合法标示“false”
	private String resultMsg;//消息
	private String signMsg;//签名信息
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getSignMsg() {
		return signMsg;
	}
	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}
	@Override
	public String toString() {
		return "YiKaTongJsonBean [resultCode=" + resultCode + ", resultMsg="
				+ resultMsg + ", signMsg=" + signMsg + "]";
	}
	
}
