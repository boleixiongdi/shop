package com.rt.shop.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName(value = "shopping_store")
public class Store implements Serializable {
	@TableField(exist = false)
	private User user;
	@TableField(exist = false)
	private Accessory store_logo;
	//商店级别
	@TableField(exist = false)
	private StoreGrade grade;

	//商店类型
	@TableField(exist = false)
	private StoreClass sc;

	//商店地址
	@TableField(exist = false)
	private Area area;
	@TableField(exist = false)
	private Accessory store_banner;
	@TableField(exist = false)
	private Accessory card;
	@TableField(exist = false)
	private Accessory store_license;
	@TableField(exist = false)
	private List<Goods> goods_list = new ArrayList();
	@TableField(exist = false)
	private List<StoreSlide> slides = new ArrayList();
	@TableField(exist = false)
	private List<Payment> payments = new ArrayList();
	@TableField(exist = false)
	private List<StoreNav> navs = new ArrayList();
	@TableField(exist = false)
	private List<Favorite> favs = new ArrayList();

	//商品类型主题
	@TableField(exist = false)
	private List<Goodsclassstaple> gcss = new ArrayList();

	//订单表
	@TableField(exist = false)
	private List<OrderForm> ofs = new ArrayList();

	//投递记录
	@TableField(exist = false)
	private List<DeliveryLog> delivery_logs = new ArrayList();

	//结合记录
	@TableField(exist = false)
	private List<CombinLog> combin_logs = new ArrayList();

	//运输集合
	@TableField(exist = false)
	private List<Transport> transport_list = new ArrayList();
	@TableField(exist = false)
	private List<Dynamic> dynamics = new ArrayList();

	@TableField(exist = false)
	private StorePoint sp;
	
	
	public List<StoreSlide> getSlides() {
		return slides;
	}

	public void setSlides(List<StoreSlide> slides) {
		this.slides = slides;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public List<StoreNav> getNavs() {
		return navs;
	}

	public void setNavs(List<StoreNav> navs) {
		this.navs = navs;
	}

	public List<Favorite> getFavs() {
		return favs;
	}

	public void setFavs(List<Favorite> favs) {
		this.favs = favs;
	}

	public List<Goodsclassstaple> getGcss() {
		return gcss;
	}

	public void setGcss(List<Goodsclassstaple> gcss) {
		this.gcss = gcss;
	}

	public List<OrderForm> getOfs() {
		return ofs;
	}

	public void setOfs(List<OrderForm> ofs) {
		this.ofs = ofs;
	}

	public List<DeliveryLog> getDelivery_logs() {
		return delivery_logs;
	}

	public void setDelivery_logs(List<DeliveryLog> delivery_logs) {
		this.delivery_logs = delivery_logs;
	}

	public List<CombinLog> getCombin_logs() {
		return combin_logs;
	}

	public void setCombin_logs(List<CombinLog> combin_logs) {
		this.combin_logs = combin_logs;
	}

	public List<Transport> getTransport_list() {
		return transport_list;
	}

	public void setTransport_list(List<Transport> transport_list) {
		this.transport_list = transport_list;
	}

	public List<Dynamic> getDynamics() {
		return dynamics;
	}

	public void setDynamics(List<Dynamic> dynamics) {
		this.dynamics = dynamics;
	}

	public StorePoint getSp() {
		return sp;
	}

	public void setSp(StorePoint sp) {
		this.sp = sp;
	}

	public Accessory getStore_banner() {
		return store_banner;
	}

	public void setStore_banner(Accessory store_banner) {
		this.store_banner = store_banner;
	}

	public Accessory getCard() {
		return card;
	}

	public void setCard(Accessory card) {
		this.card = card;
	}

	public Accessory getStore_license() {
		return store_license;
	}

	public void setStore_license(Accessory store_license) {
		this.store_license = store_license;
	}

	public List<Goods> getGoods_list() {
		return goods_list;
	}

	public void setGoods_list(List<Goods> goods_list) {
		this.goods_list = goods_list;
	}

	public Accessory getStore_logo() {
		return store_logo;
	}

	public void setStore_logo(Accessory store_logo) {
		this.store_logo = store_logo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public StoreGrade getGrade() {
		return grade;
	}

	public void setGrade(StoreGrade grade) {
		this.grade = grade;
	}

	public StoreClass getSc() {
		return sc;
	}

	public void setSc(StoreClass sc) {
		this.sc = sc;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

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
	@TableField(value = "card_approve")
	private Boolean card_approve;

	/**  */
	@TableField(value = "realstore_approve")
	private Boolean realstore_approve;

	/**  */
	@TableField(value = "store_address")
	private String store_address;

	/**  */
	@TableField(value = "store_credit")
	private Integer store_credit;

	/**  */
	@TableField(value = "store_info")
	private String store_info;

	/**  */
	@TableField(value = "store_msn")
	private String store_msn;

	/**  */
	@TableField(value = "store_name")
	private String store_name;

	/**  */
	@TableField(value = "store_ower")
	private String store_ower;

	/**  */
	@TableField(value = "store_ower_card")
	private String store_ower_card;

	/**  */
	@TableField(value = "store_qq")
	private String store_qq;

	/**  */
	@TableField(value = "store_recommend")
	private Boolean store_recommend;

	/**  */
	@TableField(value = "store_recommend_time")
	private Date store_recommend_time;

	/**  */
	@TableField(value = "store_seo_description")
	private String store_seo_description;

	/**  */
	@TableField(value = "store_seo_keywords")
	private String store_seo_keywords;

	/**  */
	@TableField(value = "store_status")
	private Integer store_status;

	/**  */
	@TableField(value = "store_telephone")
	private String store_telephone;

	/**  */
	@TableField(value = "store_zip")
	private String store_zip;

	/**  */
	private String template;

	/**  */
	private Date validity;

	/**  */
	@TableField(value = "violation_reseaon")
	private String violation_reseaon;

	/**  */
	@TableField(value = "area_id")
	private Long area_id;

	/**  */
	@TableField(value = "card_id")
	private Long card_id;

	/**  */
	@TableField(value = "grade_id")
	private Long grade_id;

	/**  */
	@TableField(value = "sc_id")
	private Long sc_id;

	/**  */
	@TableField(value = "store_banner_id")
	private Long store_banner_id;

	/**  */
	@TableField(value = "store_license_id")
	private Long store_license_id;

	/**  */
	@TableField(value = "store_logo_id")
	private Long store_logo_id;

	/**  */
	@TableField(value = "update_grade_id")
	private Long update_grade_id;

	/**  */
	@TableField(value = "domain_modify_count")
	private Integer domain_modify_count;

	/**  */
	@TableField(value = "store_second_domain")
	private String store_second_domain;

	/**  */
	@TableField(value = "favorite_count")
	private Integer favorite_count;

	/**  */
	@TableField(value = "store_lat")
	private BigDecimal store_lat;

	/**  */
	@TableField(value = "store_lng")
	private BigDecimal store_lng;

	/**  */
	@TableField(value = "store_ww")
	private String store_ww;

	/**  */
	@TableField(value = "map_type")
	private String map_type;

	/**  */
	@TableField(value = "delivery_begin_time")
	private Date delivery_begin_time;

	/**  */
	@TableField(value = "delivery_end_time")
	private Date delivery_end_time;

	/**  */
	@TableField(value = "combin_begin_time")
	private Date combin_begin_time;

	/**  */
	@TableField(value = "combin_end_time")
	private Date combin_end_time;

	/**  */
	@TableField(value = "weixin_begin_time")
	private Date weixin_begin_time;

	/**  */
	@TableField(value = "weixin_end_time")
	private Date weixin_end_time;

	/**  */
	@TableField(value = "weixin_status")
	private Integer weixin_status;

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
	@TableField(value = "weixin_qr_img_id")
	private Long weixin_qr_img_id;

	/**  */
	@TableField(value = "weixin_account")
	private String weixin_account;

	/**  */
	@TableField(value = "store_weixin_logo_id")
	private Long store_weixin_logo_id;

	/**  */
	@TableField(value = "weixin_store_name")
	private String weixin_store_name;

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

	public Boolean getCard_approve() {
		return this.card_approve;
	}

	public void setCard_approve(Boolean card_approve) {
		this.card_approve = card_approve;
	}

	public Boolean getRealstore_approve() {
		return this.realstore_approve;
	}

	public void setRealstore_approve(Boolean realstore_approve) {
		this.realstore_approve = realstore_approve;
	}

	public String getStore_address() {
		return this.store_address;
	}

	public void setStore_address(String store_address) {
		this.store_address = store_address;
	}

	public Integer getStore_credit() {
		return this.store_credit;
	}

	public void setStore_credit(Integer store_credit) {
		this.store_credit = store_credit;
	}

	public String getStore_info() {
		return this.store_info;
	}

	public void setStore_info(String store_info) {
		this.store_info = store_info;
	}

	public String getStore_msn() {
		return this.store_msn;
	}

	public void setStore_msn(String store_msn) {
		this.store_msn = store_msn;
	}

	public String getStore_name() {
		return this.store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getStore_ower() {
		return this.store_ower;
	}

	public void setStore_ower(String store_ower) {
		this.store_ower = store_ower;
	}

	public String getStore_ower_card() {
		return this.store_ower_card;
	}

	public void setStore_ower_card(String store_ower_card) {
		this.store_ower_card = store_ower_card;
	}

	public String getStore_qq() {
		return this.store_qq;
	}

	public void setStore_qq(String store_qq) {
		this.store_qq = store_qq;
	}

	public Boolean getStore_recommend() {
		return this.store_recommend;
	}

	public void setStore_recommend(Boolean store_recommend) {
		this.store_recommend = store_recommend;
	}

	public Date getStore_recommend_time() {
		return this.store_recommend_time;
	}

	public void setStore_recommend_time(Date store_recommend_time) {
		this.store_recommend_time = store_recommend_time;
	}

	public String getStore_seo_description() {
		return this.store_seo_description;
	}

	public void setStore_seo_description(String store_seo_description) {
		this.store_seo_description = store_seo_description;
	}

	public String getStore_seo_keywords() {
		return this.store_seo_keywords;
	}

	public void setStore_seo_keywords(String store_seo_keywords) {
		this.store_seo_keywords = store_seo_keywords;
	}

	public Integer getStore_status() {
		return this.store_status;
	}

	public void setStore_status(Integer store_status) {
		this.store_status = store_status;
	}

	public String getStore_telephone() {
		return this.store_telephone;
	}

	public void setStore_telephone(String store_telephone) {
		this.store_telephone = store_telephone;
	}

	public String getStore_zip() {
		return this.store_zip;
	}

	public void setStore_zip(String store_zip) {
		this.store_zip = store_zip;
	}

	public String getTemplate() {
		return this.template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Date getValidity() {
		return this.validity;
	}

	public void setValidity(Date validity) {
		this.validity = validity;
	}

	public String getViolation_reseaon() {
		return this.violation_reseaon;
	}

	public void setViolation_reseaon(String violation_reseaon) {
		this.violation_reseaon = violation_reseaon;
	}

	public Long getArea_id() {
		return this.area_id;
	}

	public void setArea_id(Long area_id) {
		this.area_id = area_id;
	}

	public Long getCard_id() {
		return this.card_id;
	}

	public void setCard_id(Long card_id) {
		this.card_id = card_id;
	}

	public Long getGrade_id() {
		return this.grade_id;
	}

	public void setGrade_id(Long grade_id) {
		this.grade_id = grade_id;
	}

	public Long getSc_id() {
		return this.sc_id;
	}

	public void setSc_id(Long sc_id) {
		this.sc_id = sc_id;
	}

	public Long getStore_banner_id() {
		return this.store_banner_id;
	}

	public void setStore_banner_id(Long store_banner_id) {
		this.store_banner_id = store_banner_id;
	}

	public Long getStore_license_id() {
		return this.store_license_id;
	}

	public void setStore_license_id(Long store_license_id) {
		this.store_license_id = store_license_id;
	}

	public Long getStore_logo_id() {
		return this.store_logo_id;
	}

	public void setStore_logo_id(Long store_logo_id) {
		this.store_logo_id = store_logo_id;
	}

	public Long getUpdate_grade_id() {
		return this.update_grade_id;
	}

	public void setUpdate_grade_id(Long update_grade_id) {
		this.update_grade_id = update_grade_id;
	}

	public Integer getDomain_modify_count() {
		return this.domain_modify_count;
	}

	public void setDomain_modify_count(Integer domain_modify_count) {
		this.domain_modify_count = domain_modify_count;
	}

	public String getStore_second_domain() {
		return this.store_second_domain;
	}

	public void setStore_second_domain(String store_second_domain) {
		this.store_second_domain = store_second_domain;
	}

	public Integer getFavorite_count() {
		return this.favorite_count;
	}

	public void setFavorite_count(Integer favorite_count) {
		this.favorite_count = favorite_count;
	}

	public BigDecimal getStore_lat() {
		return this.store_lat;
	}

	public void setStore_lat(BigDecimal store_lat) {
		this.store_lat = store_lat;
	}

	public BigDecimal getStore_lng() {
		return this.store_lng;
	}

	public void setStore_lng(BigDecimal store_lng) {
		this.store_lng = store_lng;
	}

	public String getStore_ww() {
		return this.store_ww;
	}

	public void setStore_ww(String store_ww) {
		this.store_ww = store_ww;
	}

	public String getMap_type() {
		return this.map_type;
	}

	public void setMap_type(String map_type) {
		this.map_type = map_type;
	}

	public Date getDelivery_begin_time() {
		return this.delivery_begin_time;
	}

	public void setDelivery_begin_time(Date delivery_begin_time) {
		this.delivery_begin_time = delivery_begin_time;
	}

	public Date getDelivery_end_time() {
		return this.delivery_end_time;
	}

	public void setDelivery_end_time(Date delivery_end_time) {
		this.delivery_end_time = delivery_end_time;
	}

	public Date getCombin_begin_time() {
		return this.combin_begin_time;
	}

	public void setCombin_begin_time(Date combin_begin_time) {
		this.combin_begin_time = combin_begin_time;
	}

	public Date getCombin_end_time() {
		return this.combin_end_time;
	}

	public void setCombin_end_time(Date combin_end_time) {
		this.combin_end_time = combin_end_time;
	}

	public Date getWeixin_begin_time() {
		return this.weixin_begin_time;
	}

	public void setWeixin_begin_time(Date weixin_begin_time) {
		this.weixin_begin_time = weixin_begin_time;
	}

	public Date getWeixin_end_time() {
		return this.weixin_end_time;
	}

	public void setWeixin_end_time(Date weixin_end_time) {
		this.weixin_end_time = weixin_end_time;
	}

	public Integer getWeixin_status() {
		return this.weixin_status;
	}

	public void setWeixin_status(Integer weixin_status) {
		this.weixin_status = weixin_status;
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

	public Long getWeixin_qr_img_id() {
		return this.weixin_qr_img_id;
	}

	public void setWeixin_qr_img_id(Long weixin_qr_img_id) {
		this.weixin_qr_img_id = weixin_qr_img_id;
	}

	public String getWeixin_account() {
		return this.weixin_account;
	}

	public void setWeixin_account(String weixin_account) {
		this.weixin_account = weixin_account;
	}

	public Long getStore_weixin_logo_id() {
		return this.store_weixin_logo_id;
	}

	public void setStore_weixin_logo_id(Long store_weixin_logo_id) {
		this.store_weixin_logo_id = store_weixin_logo_id;
	}

	public String getWeixin_store_name() {
		return this.weixin_store_name;
	}

	public void setWeixin_store_name(String weixin_store_name) {
		this.weixin_store_name = weixin_store_name;
	}

}
