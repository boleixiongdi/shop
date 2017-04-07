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
@TableName(value = "shopping_sysconfig")
public class SysConfig implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	@TableField(exist = false)
	private Accessory websiteLogo;
	@TableField(exist = false)
	private Accessory storeImage;

	// 商品图片
	@TableField(exist = false)
	private Accessory goodsImage;

	// 成员图标
	@TableField(exist = false)
	private Accessory memberIcon;
	
	public Accessory getWebsiteLogo() {
		return websiteLogo;
	}

	public void setWebsiteLogo(Accessory websiteLogo) {
		this.websiteLogo = websiteLogo;
	}

	public Accessory getStoreImage() {
		return storeImage;
	}

	public void setStoreImage(Accessory storeImage) {
		this.storeImage = storeImage;
	}

	public Accessory getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(Accessory goodsImage) {
		this.goodsImage = goodsImage;
	}

	public Accessory getMemberIcon() {
		return memberIcon;
	}

	public void setMemberIcon(Accessory memberIcon) {
		this.memberIcon = memberIcon;
	}

	/**  */
	@TableId
	private Long id;

	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	private String address;

	/**  */
	private Integer bigHeight;

	/**  */
	private Integer bigWidth;

	/**  */
	private String closeReason;

	/**  */
	private String codeStat;

	/**  */
	@TableField(value = "complaint_time")
	private Integer complaint_time;

	/**  */
	private Integer consumptionRatio;

	/**  */
	private String copyRight;

	/**  */
	private String creditrule;

	/**  */
	private Boolean deposit;

	/**  */
	private String description;

	/**  */
	private Boolean emailEnable;

	/**  */
	private String emailHost;

	/**  */
	private Integer emailPort;

	/**  */
	private String emailPws;

	/**  */
	private String emailTest;

	/**  */
	private String emailUser;

	/**  */
	private String emailUserName;

	/**  */
	private Integer everyIndentLimit;

	/**  */
	private Boolean gold;

	/**  */
	private Integer goldMarketValue;

	/**  */
	private Boolean groupBuy;

	/**  */
	private String hotSearch;

	/**  */
	private Integer imageFilesize;

	/**  */
	private String imageSaveType;

	/**  */
	private String imageSuffix;

	/**  */
	private Integer indentComment;

	/**  */
	private Boolean integral;

	/**  */
	private Integer integralRate;

	/**  */
	private Boolean integralStore;

	/**  */
	private String keywords;

	/**  */
	private Integer memberDayLogin;

	/**  */
	private Integer memberRegister;

	/**  */
	private Integer middleHeight;

	/**  */
	private Integer middleWidth;

	/**  */
	private Boolean securityCodeConsult;

	/**  */
	private Boolean securityCodeLogin;

	/**  */
	private Boolean securityCodeRegister;

	/**  */
	private String securityCodeType;

	/**  */
	@TableField(value = "share_code")
	private String share_code;

	/**  */
	private Integer smallHeight;

	/**  */
	private Integer smallWidth;

	/**  */
	private Boolean smsEnbale;

	/**  */
	private String smsPassword;

	/**  */
	private String smsTest;

	/**  */
	private String smsURL;

	/**  */
	private String smsUserName;

	/**  */
	@TableField(value = "store_allow")
	private Boolean store_allow;

	/**  */
	@TableField(value = "store_payment")
	private String store_payment;

	/**  */
	private String sysLanguage;

	/**  */
	private String templates;

	/**  */
	private String title;

	/**  */
	private String uploadFilePath;

	/**  */
	@TableField(value = "user_creditrule")
	private String user_creditrule;

	/**  */
	private Boolean visitorConsult;

	/**  */
	private Boolean voucher;

	/**  */
	private String websiteName;

	/**  */
	private Boolean websiteState;

	/**  */
	@TableField(value = "ztc_price")
	private Integer ztc_price;

	/**  */
	@TableField(value = "ztc_status")
	private Boolean ztc_status;

	/**  */
	@TableField(value = "goodsImage_id")
	private Long goodsImage_id;

	/**  */
	@TableField(value = "memberIcon_id")
	private Long memberIcon_id;

	/**  */
	@TableField(value = "storeImage_id")
	private Long storeImage_id;

	/**  */
	@TableField(value = "websiteLogo_id")
	private Long websiteLogo_id;

	/**  */
	@TableField(value = "domain_allow_count")
	private Integer domain_allow_count;

	/**  */
	@TableField(value = "second_domain_open")
	private Boolean second_domain_open;

	/**  */
	@TableField(value = "sys_domain")
	private String sys_domain;

	/**  */
	@TableField(value = "qq_login")
	private Boolean qq_login;

	/**  */
	@TableField(value = "qq_login_id")
	private String qq_login_id;

	/**  */
	@TableField(value = "qq_login_key")
	private String qq_login_key;

	/**  */
	@TableField(value = "qq_domain_code")
	private String qq_domain_code;

	/**  */
	@TableField(value = "sina_domain_code")
	private String sina_domain_code;

	/**  */
	@TableField(value = "sina_login")
	private Boolean sina_login;

	/**  */
	@TableField(value = "sina_login_id")
	private String sina_login_id;

	/**  */
	@TableField(value = "sina_login_key")
	private String sina_login_key;

	/**  */
	@TableField(value = "tianyuan_domain_code")
	private String tianyuan_domain_code;

	/**  */
	@TableField(value = "tianyuan_login")
	private Boolean tianyuan_login;

	/**  */
	@TableField(value = "tianyuan_login_id")
	private String tianyuan_login_id;

	/**  */
	@TableField(value = "tianyuan_login_key")
	private String tianyuan_login_key;

	/**  */
	private String imageWebServer;

	/**  */
	@TableField(value = "lucene_update")
	private Date lucene_update;

	/**  */
	@TableField(value = "alipay_fenrun")
	private Integer alipay_fenrun;

	/**  */
	@TableField(value = "balance_fenrun")
	private Integer balance_fenrun;

	/**  */
	@TableField(value = "auto_order_confirm")
	private Integer auto_order_confirm;

	/**  */
	@TableField(value = "auto_order_notice")
	private Integer auto_order_notice;

	/**  */
	@TableField(value = "bargain_maximum")
	private Integer bargain_maximum;

	/**  */
	@TableField(value = "bargain_rebate")
	private BigDecimal bargain_rebate;

	/**  */
	@TableField(value = "bargain_state")
	private String bargain_state;

	/**  */
	@TableField(value = "bargain_status")
	private Integer bargain_status;

	/**  */
	@TableField(value = "bargain_title")
	private String bargain_title;

	/**  */
	@TableField(value = "service_qq_list")
	private String service_qq_list;

	/**  */
	@TableField(value = "service_telphone_list")
	private String service_telphone_list;

	/**  */
	@TableField(value = "sys_delivery_maximum")
	private Integer sys_delivery_maximum;

	/**  */
	@TableField(value = "uc_bbs")
	private Boolean uc_bbs;

	/**  */
	@TableField(value = "kuaidi_id")
	private String kuaidi_id;

	/**  */
	@TableField(value = "uc_api")
	private String uc_api;

	/**  */
	@TableField(value = "uc_appid")
	private String uc_appid;

	/**  */
	@TableField(value = "uc_database")
	private String uc_database;

	/**  */
	@TableField(value = "uc_database_port")
	private String uc_database_port;

	/**  */
	@TableField(value = "uc_database_pws")
	private String uc_database_pws;

	/**  */
	@TableField(value = "uc_database_url")
	private String uc_database_url;

	/**  */
	@TableField(value = "uc_database_username")
	private String uc_database_username;

	/**  */
	@TableField(value = "uc_ip")
	private String uc_ip;

	/**  */
	@TableField(value = "uc_key")
	private String uc_key;

	/**  */
	@TableField(value = "uc_table_preffix")
	private String uc_table_preffix;

	/**  */
	@TableField(value = "currency_code")
	private String currency_code;

	/**  */
	@TableField(value = "bargain_validity")
	private Integer bargain_validity;

	/**  */
	@TableField(value = "delivery_amount")
	private Integer delivery_amount;

	/**  */
	@TableField(value = "delivery_status")
	private Integer delivery_status;

	/**  */
	@TableField(value = "delivery_title")
	private String delivery_title;

	/**  */
	private String websiteCss;

	/**  */
	@TableField(value = "combin_amount")
	private Integer combin_amount;

	/**  */
	@TableField(value = "combin_count")
	private Integer combin_count;

	/**  */
	@TableField(value = "ztc_goods_view")
	private Integer ztc_goods_view;

	/**  */
	@TableField(value = "auto_order_evaluate")
	private Integer auto_order_evaluate;

	/**  */
	@TableField(value = "auto_order_return")
	private Integer auto_order_return;

	/**  */
	@TableField(value = "weixin_store")
	private Boolean weixin_store;

	/**  */
	@TableField(value = "weixin_amount")
	private Integer weixin_amount;

	/**  */
	@TableField(value = "config_payment_type")
	private Integer config_payment_type;

	/**  */
	@TableField(value = "weixin_account")
	private String weixin_account;

	/**  */
	@TableField(value = "weixin_appId")
	private String weixin_appId;

	/**  */
	@TableField(value = "weixin_appSecret")
	private String weixin_appSecret;

	/**  */
	@TableField(value = "weixin_token")
	private String weixin_token;

	/**  */
	@TableField(value = "weixin_welecome_content")
	private String weixin_welecome_content;

	/**  */
	@TableField(value = "store_weixin_logo_id")
	private Long store_weixin_logo_id;

	/**  */
	@TableField(value = "weixin_qr_img_id")
	private Long weixin_qr_img_id;

	/**  */
	@TableField(value = "site_url")
	private String site_url;

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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getBigHeight() {
		return this.bigHeight;
	}

	public void setBigHeight(Integer bigHeight) {
		this.bigHeight = bigHeight;
	}

	public Integer getBigWidth() {
		return this.bigWidth;
	}

	public void setBigWidth(Integer bigWidth) {
		this.bigWidth = bigWidth;
	}

	public String getCloseReason() {
		return this.closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	public String getCodeStat() {
		return this.codeStat;
	}

	public void setCodeStat(String codeStat) {
		this.codeStat = codeStat;
	}

	public Integer getComplaint_time() {
		return this.complaint_time;
	}

	public void setComplaint_time(Integer complaint_time) {
		this.complaint_time = complaint_time;
	}

	public Integer getConsumptionRatio() {
		return this.consumptionRatio;
	}

	public void setConsumptionRatio(Integer consumptionRatio) {
		this.consumptionRatio = consumptionRatio;
	}

	public String getCopyRight() {
		return this.copyRight;
	}

	public void setCopyRight(String copyRight) {
		this.copyRight = copyRight;
	}

	public String getCreditrule() {
		return this.creditrule;
	}

	public void setCreditrule(String creditrule) {
		this.creditrule = creditrule;
	}

	public Boolean getDeposit() {
		return this.deposit;
	}

	public void setDeposit(Boolean deposit) {
		this.deposit = deposit;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEmailEnable() {
		return this.emailEnable;
	}

	public void setEmailEnable(Boolean emailEnable) {
		this.emailEnable = emailEnable;
	}

	public String getEmailHost() {
		return this.emailHost;
	}

	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}

	public Integer getEmailPort() {
		return this.emailPort;
	}

	public void setEmailPort(Integer emailPort) {
		this.emailPort = emailPort;
	}

	public String getEmailPws() {
		return this.emailPws;
	}

	public void setEmailPws(String emailPws) {
		this.emailPws = emailPws;
	}

	public String getEmailTest() {
		return this.emailTest;
	}

	public void setEmailTest(String emailTest) {
		this.emailTest = emailTest;
	}

	public String getEmailUser() {
		return this.emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public String getEmailUserName() {
		return this.emailUserName;
	}

	public void setEmailUserName(String emailUserName) {
		this.emailUserName = emailUserName;
	}

	public Integer getEveryIndentLimit() {
		return this.everyIndentLimit;
	}

	public void setEveryIndentLimit(Integer everyIndentLimit) {
		this.everyIndentLimit = everyIndentLimit;
	}

	public Boolean getGold() {
		return this.gold;
	}

	public void setGold(Boolean gold) {
		this.gold = gold;
	}

	public Integer getGoldMarketValue() {
		return this.goldMarketValue;
	}

	public void setGoldMarketValue(Integer goldMarketValue) {
		this.goldMarketValue = goldMarketValue;
	}

	public Boolean getGroupBuy() {
		return this.groupBuy;
	}

	public void setGroupBuy(Boolean groupBuy) {
		this.groupBuy = groupBuy;
	}

	public String getHotSearch() {
		return this.hotSearch;
	}

	public void setHotSearch(String hotSearch) {
		this.hotSearch = hotSearch;
	}

	public Integer getImageFilesize() {
		return this.imageFilesize;
	}

	public void setImageFilesize(Integer imageFilesize) {
		this.imageFilesize = imageFilesize;
	}

	public String getImageSaveType() {
		return this.imageSaveType;
	}

	public void setImageSaveType(String imageSaveType) {
		this.imageSaveType = imageSaveType;
	}

	public String getImageSuffix() {
		return this.imageSuffix;
	}

	public void setImageSuffix(String imageSuffix) {
		this.imageSuffix = imageSuffix;
	}

	public Integer getIndentComment() {
		return this.indentComment;
	}

	public void setIndentComment(Integer indentComment) {
		this.indentComment = indentComment;
	}

	public Boolean getIntegral() {
		return this.integral;
	}

	public void setIntegral(Boolean integral) {
		this.integral = integral;
	}

	public Integer getIntegralRate() {
		return this.integralRate;
	}

	public void setIntegralRate(Integer integralRate) {
		this.integralRate = integralRate;
	}

	public Boolean getIntegralStore() {
		return this.integralStore;
	}

	public void setIntegralStore(Boolean integralStore) {
		this.integralStore = integralStore;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Integer getMemberDayLogin() {
		return this.memberDayLogin;
	}

	public void setMemberDayLogin(Integer memberDayLogin) {
		this.memberDayLogin = memberDayLogin;
	}

	public Integer getMemberRegister() {
		return this.memberRegister;
	}

	public void setMemberRegister(Integer memberRegister) {
		this.memberRegister = memberRegister;
	}

	public Integer getMiddleHeight() {
		return this.middleHeight;
	}

	public void setMiddleHeight(Integer middleHeight) {
		this.middleHeight = middleHeight;
	}

	public Integer getMiddleWidth() {
		return this.middleWidth;
	}

	public void setMiddleWidth(Integer middleWidth) {
		this.middleWidth = middleWidth;
	}

	public Boolean getSecurityCodeConsult() {
		return this.securityCodeConsult;
	}

	public void setSecurityCodeConsult(Boolean securityCodeConsult) {
		this.securityCodeConsult = securityCodeConsult;
	}

	public Boolean getSecurityCodeLogin() {
		return this.securityCodeLogin;
	}

	public void setSecurityCodeLogin(Boolean securityCodeLogin) {
		this.securityCodeLogin = securityCodeLogin;
	}

	public Boolean getSecurityCodeRegister() {
		return this.securityCodeRegister;
	}

	public void setSecurityCodeRegister(Boolean securityCodeRegister) {
		this.securityCodeRegister = securityCodeRegister;
	}

	public String getSecurityCodeType() {
		return this.securityCodeType;
	}

	public void setSecurityCodeType(String securityCodeType) {
		this.securityCodeType = securityCodeType;
	}

	public String getShare_code() {
		return this.share_code;
	}

	public void setShare_code(String share_code) {
		this.share_code = share_code;
	}

	public Integer getSmallHeight() {
		return this.smallHeight;
	}

	public void setSmallHeight(Integer smallHeight) {
		this.smallHeight = smallHeight;
	}

	public Integer getSmallWidth() {
		return this.smallWidth;
	}

	public void setSmallWidth(Integer smallWidth) {
		this.smallWidth = smallWidth;
	}

	public Boolean getSmsEnbale() {
		return this.smsEnbale;
	}

	public void setSmsEnbale(Boolean smsEnbale) {
		this.smsEnbale = smsEnbale;
	}

	public String getSmsPassword() {
		return this.smsPassword;
	}

	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}

	public String getSmsTest() {
		return this.smsTest;
	}

	public void setSmsTest(String smsTest) {
		this.smsTest = smsTest;
	}

	public String getSmsURL() {
		return this.smsURL;
	}

	public void setSmsURL(String smsURL) {
		this.smsURL = smsURL;
	}

	public String getSmsUserName() {
		return this.smsUserName;
	}

	public void setSmsUserName(String smsUserName) {
		this.smsUserName = smsUserName;
	}

	public Boolean getStore_allow() {
		return this.store_allow;
	}

	public void setStore_allow(Boolean store_allow) {
		this.store_allow = store_allow;
	}

	public String getStore_payment() {
		return this.store_payment;
	}

	public void setStore_payment(String store_payment) {
		this.store_payment = store_payment;
	}

	public String getSysLanguage() {
		return this.sysLanguage;
	}

	public void setSysLanguage(String sysLanguage) {
		this.sysLanguage = sysLanguage;
	}

	public String getTemplates() {
		return this.templates;
	}

	public void setTemplates(String templates) {
		this.templates = templates;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUploadFilePath() {
		return this.uploadFilePath;
	}

	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}

	public String getUser_creditrule() {
		return this.user_creditrule;
	}

	public void setUser_creditrule(String user_creditrule) {
		this.user_creditrule = user_creditrule;
	}

	public Boolean getVisitorConsult() {
		return this.visitorConsult;
	}

	public void setVisitorConsult(Boolean visitorConsult) {
		this.visitorConsult = visitorConsult;
	}

	public Boolean getVoucher() {
		return this.voucher;
	}

	public void setVoucher(Boolean voucher) {
		this.voucher = voucher;
	}

	public String getWebsiteName() {
		return this.websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public Boolean getWebsiteState() {
		return this.websiteState;
	}

	public void setWebsiteState(Boolean websiteState) {
		this.websiteState = websiteState;
	}

	public Integer getZtc_price() {
		return this.ztc_price;
	}

	public void setZtc_price(Integer ztc_price) {
		this.ztc_price = ztc_price;
	}

	public Boolean getZtc_status() {
		return this.ztc_status;
	}

	public void setZtc_status(Boolean ztc_status) {
		this.ztc_status = ztc_status;
	}

	public Long getGoodsImage_id() {
		return this.goodsImage_id;
	}

	public void setGoodsImage_id(Long goodsImage_id) {
		this.goodsImage_id = goodsImage_id;
	}

	public Long getMemberIcon_id() {
		return this.memberIcon_id;
	}

	public void setMemberIcon_id(Long memberIcon_id) {
		this.memberIcon_id = memberIcon_id;
	}

	public Long getStoreImage_id() {
		return this.storeImage_id;
	}

	public void setStoreImage_id(Long storeImage_id) {
		this.storeImage_id = storeImage_id;
	}

	public Long getWebsiteLogo_id() {
		return this.websiteLogo_id;
	}

	public void setWebsiteLogo_id(Long websiteLogo_id) {
		this.websiteLogo_id = websiteLogo_id;
	}

	public Integer getDomain_allow_count() {
		return this.domain_allow_count;
	}

	public void setDomain_allow_count(Integer domain_allow_count) {
		this.domain_allow_count = domain_allow_count;
	}

	public Boolean getSecond_domain_open() {
		return this.second_domain_open;
	}

	public void setSecond_domain_open(Boolean second_domain_open) {
		this.second_domain_open = second_domain_open;
	}

	public String getSys_domain() {
		return this.sys_domain;
	}

	public void setSys_domain(String sys_domain) {
		this.sys_domain = sys_domain;
	}

	public Boolean getQq_login() {
		return this.qq_login;
	}

	public void setQq_login(Boolean qq_login) {
		this.qq_login = qq_login;
	}

	public String getQq_login_id() {
		return this.qq_login_id;
	}

	public void setQq_login_id(String qq_login_id) {
		this.qq_login_id = qq_login_id;
	}

	public String getQq_login_key() {
		return this.qq_login_key;
	}

	public void setQq_login_key(String qq_login_key) {
		this.qq_login_key = qq_login_key;
	}

	public String getQq_domain_code() {
		return this.qq_domain_code;
	}

	public void setQq_domain_code(String qq_domain_code) {
		this.qq_domain_code = qq_domain_code;
	}

	public String getSina_domain_code() {
		return this.sina_domain_code;
	}

	public void setSina_domain_code(String sina_domain_code) {
		this.sina_domain_code = sina_domain_code;
	}

	public Boolean getSina_login() {
		return this.sina_login;
	}

	public void setSina_login(Boolean sina_login) {
		this.sina_login = sina_login;
	}

	public String getSina_login_id() {
		return this.sina_login_id;
	}

	public void setSina_login_id(String sina_login_id) {
		this.sina_login_id = sina_login_id;
	}

	public String getSina_login_key() {
		return this.sina_login_key;
	}

	public void setSina_login_key(String sina_login_key) {
		this.sina_login_key = sina_login_key;
	}

	public String getTianyuan_domain_code() {
		return this.tianyuan_domain_code;
	}

	public void setTianyuan_domain_code(String tianyuan_domain_code) {
		this.tianyuan_domain_code = tianyuan_domain_code;
	}

	public Boolean getTianyuan_login() {
		return this.tianyuan_login;
	}

	public void setTianyuan_login(Boolean tianyuan_login) {
		this.tianyuan_login = tianyuan_login;
	}

	public String getTianyuan_login_id() {
		return this.tianyuan_login_id;
	}

	public void setTianyuan_login_id(String tianyuan_login_id) {
		this.tianyuan_login_id = tianyuan_login_id;
	}

	public String getTianyuan_login_key() {
		return this.tianyuan_login_key;
	}

	public void setTianyuan_login_key(String tianyuan_login_key) {
		this.tianyuan_login_key = tianyuan_login_key;
	}

	public String getImageWebServer() {
		return this.imageWebServer;
	}

	public void setImageWebServer(String imageWebServer) {
		this.imageWebServer = imageWebServer;
	}

	public Date getLucene_update() {
		return this.lucene_update;
	}

	public void setLucene_update(Date lucene_update) {
		this.lucene_update = lucene_update;
	}

	public Integer getAlipay_fenrun() {
		return this.alipay_fenrun;
	}

	public void setAlipay_fenrun(Integer alipay_fenrun) {
		this.alipay_fenrun = alipay_fenrun;
	}

	public Integer getBalance_fenrun() {
		return this.balance_fenrun;
	}

	public void setBalance_fenrun(Integer balance_fenrun) {
		this.balance_fenrun = balance_fenrun;
	}

	public Integer getAuto_order_confirm() {
		return this.auto_order_confirm;
	}

	public void setAuto_order_confirm(Integer auto_order_confirm) {
		this.auto_order_confirm = auto_order_confirm;
	}

	public Integer getAuto_order_notice() {
		return this.auto_order_notice;
	}

	public void setAuto_order_notice(Integer auto_order_notice) {
		this.auto_order_notice = auto_order_notice;
	}

	public Integer getBargain_maximum() {
		return this.bargain_maximum;
	}

	public void setBargain_maximum(Integer bargain_maximum) {
		this.bargain_maximum = bargain_maximum;
	}

	public BigDecimal getBargain_rebate() {
		return this.bargain_rebate;
	}

	public void setBargain_rebate(BigDecimal bargain_rebate) {
		this.bargain_rebate = bargain_rebate;
	}

	public String getBargain_state() {
		return this.bargain_state;
	}

	public void setBargain_state(String bargain_state) {
		this.bargain_state = bargain_state;
	}

	public Integer getBargain_status() {
		return this.bargain_status;
	}

	public void setBargain_status(Integer bargain_status) {
		this.bargain_status = bargain_status;
	}

	public String getBargain_title() {
		return this.bargain_title;
	}

	public void setBargain_title(String bargain_title) {
		this.bargain_title = bargain_title;
	}

	public String getService_qq_list() {
		return this.service_qq_list;
	}

	public void setService_qq_list(String service_qq_list) {
		this.service_qq_list = service_qq_list;
	}

	public String getService_telphone_list() {
		return this.service_telphone_list;
	}

	public void setService_telphone_list(String service_telphone_list) {
		this.service_telphone_list = service_telphone_list;
	}

	public Integer getSys_delivery_maximum() {
		return this.sys_delivery_maximum;
	}

	public void setSys_delivery_maximum(Integer sys_delivery_maximum) {
		this.sys_delivery_maximum = sys_delivery_maximum;
	}

	public Boolean getUc_bbs() {
		return this.uc_bbs;
	}

	public void setUc_bbs(Boolean uc_bbs) {
		this.uc_bbs = uc_bbs;
	}

	public String getKuaidi_id() {
		return this.kuaidi_id;
	}

	public void setKuaidi_id(String kuaidi_id) {
		this.kuaidi_id = kuaidi_id;
	}

	public String getUc_api() {
		return this.uc_api;
	}

	public void setUc_api(String uc_api) {
		this.uc_api = uc_api;
	}

	public String getUc_appid() {
		return this.uc_appid;
	}

	public void setUc_appid(String uc_appid) {
		this.uc_appid = uc_appid;
	}

	public String getUc_database() {
		return this.uc_database;
	}

	public void setUc_database(String uc_database) {
		this.uc_database = uc_database;
	}

	public String getUc_database_port() {
		return this.uc_database_port;
	}

	public void setUc_database_port(String uc_database_port) {
		this.uc_database_port = uc_database_port;
	}

	public String getUc_database_pws() {
		return this.uc_database_pws;
	}

	public void setUc_database_pws(String uc_database_pws) {
		this.uc_database_pws = uc_database_pws;
	}

	public String getUc_database_url() {
		return this.uc_database_url;
	}

	public void setUc_database_url(String uc_database_url) {
		this.uc_database_url = uc_database_url;
	}

	public String getUc_database_username() {
		return this.uc_database_username;
	}

	public void setUc_database_username(String uc_database_username) {
		this.uc_database_username = uc_database_username;
	}

	public String getUc_ip() {
		return this.uc_ip;
	}

	public void setUc_ip(String uc_ip) {
		this.uc_ip = uc_ip;
	}

	public String getUc_key() {
		return this.uc_key;
	}

	public void setUc_key(String uc_key) {
		this.uc_key = uc_key;
	}

	public String getUc_table_preffix() {
		return this.uc_table_preffix;
	}

	public void setUc_table_preffix(String uc_table_preffix) {
		this.uc_table_preffix = uc_table_preffix;
	}

	public String getCurrency_code() {
		return this.currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	public Integer getBargain_validity() {
		return this.bargain_validity;
	}

	public void setBargain_validity(Integer bargain_validity) {
		this.bargain_validity = bargain_validity;
	}

	public Integer getDelivery_amount() {
		return this.delivery_amount;
	}

	public void setDelivery_amount(Integer delivery_amount) {
		this.delivery_amount = delivery_amount;
	}

	public Integer getDelivery_status() {
		return this.delivery_status;
	}

	public void setDelivery_status(Integer delivery_status) {
		this.delivery_status = delivery_status;
	}

	public String getDelivery_title() {
		return this.delivery_title;
	}

	public void setDelivery_title(String delivery_title) {
		this.delivery_title = delivery_title;
	}

	public String getWebsiteCss() {
		return this.websiteCss;
	}

	public void setWebsiteCss(String websiteCss) {
		this.websiteCss = websiteCss;
	}

	public Integer getCombin_amount() {
		return this.combin_amount;
	}

	public void setCombin_amount(Integer combin_amount) {
		this.combin_amount = combin_amount;
	}

	public Integer getCombin_count() {
		return this.combin_count;
	}

	public void setCombin_count(Integer combin_count) {
		this.combin_count = combin_count;
	}

	public Integer getZtc_goods_view() {
		return this.ztc_goods_view;
	}

	public void setZtc_goods_view(Integer ztc_goods_view) {
		this.ztc_goods_view = ztc_goods_view;
	}

	public Integer getAuto_order_evaluate() {
		return this.auto_order_evaluate;
	}

	public void setAuto_order_evaluate(Integer auto_order_evaluate) {
		this.auto_order_evaluate = auto_order_evaluate;
	}

	public Integer getAuto_order_return() {
		return this.auto_order_return;
	}

	public void setAuto_order_return(Integer auto_order_return) {
		this.auto_order_return = auto_order_return;
	}

	public Boolean getWeixin_store() {
		return this.weixin_store;
	}

	public void setWeixin_store(Boolean weixin_store) {
		this.weixin_store = weixin_store;
	}

	public Integer getWeixin_amount() {
		return this.weixin_amount;
	}

	public void setWeixin_amount(Integer weixin_amount) {
		this.weixin_amount = weixin_amount;
	}

	public Integer getConfig_payment_type() {
		return this.config_payment_type;
	}

	public void setConfig_payment_type(Integer config_payment_type) {
		this.config_payment_type = config_payment_type;
	}

	public String getWeixin_account() {
		return this.weixin_account;
	}

	public void setWeixin_account(String weixin_account) {
		this.weixin_account = weixin_account;
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

	public String getWeixin_token() {
		return this.weixin_token;
	}

	public void setWeixin_token(String weixin_token) {
		this.weixin_token = weixin_token;
	}

	public String getWeixin_welecome_content() {
		return this.weixin_welecome_content;
	}

	public void setWeixin_welecome_content(String weixin_welecome_content) {
		this.weixin_welecome_content = weixin_welecome_content;
	}

	public Long getStore_weixin_logo_id() {
		return this.store_weixin_logo_id;
	}

	public void setStore_weixin_logo_id(Long store_weixin_logo_id) {
		this.store_weixin_logo_id = store_weixin_logo_id;
	}

	public Long getWeixin_qr_img_id() {
		return this.weixin_qr_img_id;
	}

	public void setWeixin_qr_img_id(Long weixin_qr_img_id) {
		this.weixin_qr_img_id = weixin_qr_img_id;
	}

	public String getSite_url() {
		return this.site_url;
	}

	public void setSite_url(String site_url) {
		this.site_url = site_url;
	}

}
