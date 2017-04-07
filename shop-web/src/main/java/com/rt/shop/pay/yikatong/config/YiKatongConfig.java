package com.rt.shop.pay.yikatong.config;

public class YiKatongConfig {

	private String partner = "";

	public String seller_email = "";

	private String key = "admin888";

	public String getSeller_email() {
		return seller_email;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	private String notify_url = "";
	// 当前页面跳转后的页面
	private String return_url = "";

	public static String log_path = "D:\\logs\\";

	public static String input_charset = "gbk";

	public static String sign_type = "MD5";

	private String transport = "http";

	public static String private_key = "";

	public static String ali_public_key = "";

	public static String getPrivate_key() {
		return private_key;
	}

	public static void setPrivate_key(String private_key) {
		YiKatongConfig.private_key = private_key;
	}

	public static String getAli_public_key() {
		return ali_public_key;
	}

	public static void setAli_public_key(String ali_public_key) {
		YiKatongConfig.ali_public_key = ali_public_key;
	}

	public String getPartner() {
		return this.partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getNotify_url() {
		return this.notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getReturn_url() {
		return this.return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getLog_path() {
		return log_path;
	}

	public void setLog_path(String log_path) {
		YiKatongConfig.log_path = log_path;
	}

	public String getInput_charset() {
		return input_charset;
	}

	public void setInput_charset(String input_charset) {
		YiKatongConfig.input_charset = input_charset;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		YiKatongConfig.sign_type = sign_type;
	}

	public String getTransport() {
		return this.transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

}
