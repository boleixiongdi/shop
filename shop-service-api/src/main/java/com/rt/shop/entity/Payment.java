package com.rt.shop.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName(value = "shopping_payment")
public class Payment implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId
	private Long id;

	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	@TableField(value = "chinabank_account")
	private String chinabank_account;

	/**  */
	@TableField(value = "chinabank_key")
	private String chinabank_key;

	/**  */
	private String content;

	/**  */
	private Boolean install;

	/**  */
	private Integer interfaceType;

	/**  */
	private String mark;

	/**  */
	private String merchantAcctId;

	/**  */
	private String name;

	/**  */
	private String partner;

	/**  */
	private String pid;

	/**  */
	private String rmbKey;

	/**  */
	private String safeKey;

	/**  */
	@TableField(value = "seller_email")
	private String seller_email;

	/**  */
	private String spname;

	/**  */
	@TableField(value = "tenpay_key")
	private String tenpay_key;

	/**  */
	@TableField(value = "tenpay_partner")
	private String tenpay_partner;

	/**  */
	@TableField(value = "trade_mode")
	private Integer trade_mode;

	/**  */
	private String type;

	/**  */
	@TableField(value = "store_id")
	private Long store_id;

	/**  */
	@TableField(value = "alipay_divide_rate")
	private BigDecimal alipay_divide_rate;

	/**  */
	@TableField(value = "alipay_rate")
	private BigDecimal alipay_rate;

	/**  */
	@TableField(value = "balance_divide_rate")
	private BigDecimal balance_divide_rate;

	/**  */
	@TableField(value = "currency_code")
	private String currency_code;

	/**  */
	@TableField(value = "paypal_userId")
	private String paypal_userId;

	/**  */
	private BigDecimal poundage;

	/**  */
	@TableField(value = "lzbank_key")
	private String lzbank_key;

	/**  */
	@TableField(value = "lzbank_partner")
	private String lzbank_partner;

	/**  */
	@TableField(value = "lzbank_trade_mode")
	private String lzbank_trade_mode;

	/**  */
	@TableField(value = "weixin_appId")
	private String weixin_appId;

	/**  */
	@TableField(value = "weixin_appSecret")
	private String weixin_appSecret;

	/**  */
	@TableField(value = "weixin_partnerId")
	private String weixin_partnerId;

	/**  */
	@TableField(value = "weixin_partnerKey")
	private String weixin_partnerKey;

	/**  */
	@TableField(value = "weixin_paySignKey")
	private String weixin_paySignKey;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Boolean getDeleteStatus() {
		return this.deleteStatus;
	}

	public void setDeleteStatus(Boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getChinabank_account() {
		return this.chinabank_account;
	}

	public void setChinabank_account(String chinabank_account) {
		this.chinabank_account = chinabank_account;
	}

	public String getChinabank_key() {
		return this.chinabank_key;
	}

	public void setChinabank_key(String chinabank_key) {
		this.chinabank_key = chinabank_key;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getInstall() {
		return this.install;
	}

	public void setInstall(Boolean install) {
		this.install = install;
	}

	public Integer getInterfaceType() {
		return this.interfaceType;
	}

	public void setInterfaceType(Integer interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getMerchantAcctId() {
		return this.merchantAcctId;
	}

	public void setMerchantAcctId(String merchantAcctId) {
		this.merchantAcctId = merchantAcctId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPartner() {
		return this.partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getRmbKey() {
		return this.rmbKey;
	}

	public void setRmbKey(String rmbKey) {
		this.rmbKey = rmbKey;
	}

	public String getSafeKey() {
		return this.safeKey;
	}

	public void setSafeKey(String safeKey) {
		this.safeKey = safeKey;
	}

	public String getSeller_email() {
		return this.seller_email;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	public String getSpname() {
		return this.spname;
	}

	public void setSpname(String spname) {
		this.spname = spname;
	}

	public String getTenpay_key() {
		return this.tenpay_key;
	}

	public void setTenpay_key(String tenpay_key) {
		this.tenpay_key = tenpay_key;
	}

	public String getTenpay_partner() {
		return this.tenpay_partner;
	}

	public void setTenpay_partner(String tenpay_partner) {
		this.tenpay_partner = tenpay_partner;
	}

	public Integer getTrade_mode() {
		return this.trade_mode;
	}

	public void setTrade_mode(Integer trade_mode) {
		this.trade_mode = trade_mode;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getStore_id() {
		return this.store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public BigDecimal getAlipay_divide_rate() {
		return this.alipay_divide_rate;
	}

	public void setAlipay_divide_rate(BigDecimal alipay_divide_rate) {
		this.alipay_divide_rate = alipay_divide_rate;
	}

	public BigDecimal getAlipay_rate() {
		return this.alipay_rate;
	}

	public void setAlipay_rate(BigDecimal alipay_rate) {
		this.alipay_rate = alipay_rate;
	}

	public BigDecimal getBalance_divide_rate() {
		return this.balance_divide_rate;
	}

	public void setBalance_divide_rate(BigDecimal balance_divide_rate) {
		this.balance_divide_rate = balance_divide_rate;
	}

	public String getCurrency_code() {
		return this.currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	public String getPaypal_userId() {
		return this.paypal_userId;
	}

	public void setPaypal_userId(String paypal_userId) {
		this.paypal_userId = paypal_userId;
	}

	public BigDecimal getPoundage() {
		return this.poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	public String getLzbank_key() {
		return this.lzbank_key;
	}

	public void setLzbank_key(String lzbank_key) {
		this.lzbank_key = lzbank_key;
	}

	public String getLzbank_partner() {
		return this.lzbank_partner;
	}

	public void setLzbank_partner(String lzbank_partner) {
		this.lzbank_partner = lzbank_partner;
	}

	public String getLzbank_trade_mode() {
		return this.lzbank_trade_mode;
	}

	public void setLzbank_trade_mode(String lzbank_trade_mode) {
		this.lzbank_trade_mode = lzbank_trade_mode;
	}

	public String getWeixin_appId() {
		return this.weixin_appId;
	}

	public void setWeixin_appId(String weixin_appId) {
		this.weixin_appId = weixin_appId;
	}

	public String getWeixin_appSecret() {
		return this.weixin_appSecret;
	}

	public void setWeixin_appSecret(String weixin_appSecret) {
		this.weixin_appSecret = weixin_appSecret;
	}

	public String getWeixin_partnerId() {
		return this.weixin_partnerId;
	}

	public void setWeixin_partnerId(String weixin_partnerId) {
		this.weixin_partnerId = weixin_partnerId;
	}

	public String getWeixin_partnerKey() {
		return this.weixin_partnerKey;
	}

	public void setWeixin_partnerKey(String weixin_partnerKey) {
		this.weixin_partnerKey = weixin_partnerKey;
	}

	public String getWeixin_paySignKey() {
		return this.weixin_paySignKey;
	}

	public void setWeixin_paySignKey(String weixin_paySignKey) {
		this.weixin_paySignKey = weixin_paySignKey;
	}

}
