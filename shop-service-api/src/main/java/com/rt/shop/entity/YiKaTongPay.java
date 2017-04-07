package com.rt.shop.entity;
/**
 * 一卡通请求参数
 * @author 张成
 * */
public class YiKaTongPay {	
	private Integer ID;//id
	private String shopIds;//商铺号
	private String merchantId;//商户号
	private String orderNo;//用户订单号
	private String orderNos;//用户订单号
	private String WIDsubject;//订单名称
	private Long orderAmounts;//付款金额
	private Long orderAmount;//付款合计金额
	private String orderDatetime;//订单日期
	private String payerTelephone;//客户电话
	private String orderCurrency;//货币
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getShopIds() {
		return shopIds;
	}
	public void setShopIds(String shopIds) {
		this.shopIds = shopIds;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderNos() {
		return orderNos;
	}
	public void setOrderNos(String orderNos) {
		this.orderNos = orderNos;
	}
	public String getWIDsubject() {
		return WIDsubject;
	}
	public void setWIDsubject(String wIDsubject) {
		WIDsubject = wIDsubject;
	}
	public Long getOrderAmounts() {
		return orderAmounts;
	}
	public void setOrderAmounts(Long orderAmounts) {
		this.orderAmounts = orderAmounts;
	}
	public Long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getOrderDatetime() {
		return orderDatetime;
	}
	public void setOrderDatetime(String orderDatetime) {
		this.orderDatetime = orderDatetime;
	}
	public String getPayerTelephone() {
		return payerTelephone;
	}
	public void setPayerTelephone(String payerTelephone) {
		this.payerTelephone = payerTelephone;
	}
	public String getOrderCurrency() {
		return orderCurrency;
	}
	public void setOrderCurrency(String orderCurrency) {
		this.orderCurrency = orderCurrency;
	}
	
	@Override
	public String toString() {
		return "YiKaTongPay [ID=" + ID + ", shopIds=" + shopIds + ", merchantId=" + merchantId + ", orderNo=" + orderNo
				+ ", orderNos=" + orderNos + ", WIDsubject=" + WIDsubject + ", orderAmounts="
				+ orderAmounts + ", orderAmount=" + orderAmount + ", orderDatetime=" + orderDatetime
				+ ", payerTelephone=" + payerTelephone + ", orderCurrency=" + orderCurrency + "]";
	}
	
}
