package com.rt.shop.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName(value = "shopping_goods")
public class Goods implements Serializable {
	@TableField(exist = false)
	private Store goods_store;
	@TableField(exist = false)
	private GoodsClass gc;

	//商品主照片
	@TableField(exist = false)
	private Accessory goods_main_photo;
	
	//货物照片
	@TableField(exist = false)
	private List<Accessory> goods_photos = new ArrayList<Accessory>();

	@TableField(exist = false)
	private List<UserGoodsClass> goods_ugcs = new ArrayList<UserGoodsClass>();

	//特价商品
	@TableField(exist = false)
	private List<GoodsSpecProperty> goods_specs = new ArrayList<GoodsSpecProperty>();
 //商品品牌
	@TableField(exist = false)
	private GoodsBrand goods_brand;
	@TableField(exist = false)
	private User ztc_admin;


	@TableField(exist = false)
	private List<GroupGoods> group_goods_list = new ArrayList<GroupGoods>();
   //团购
	@TableField(exist = false)
	private Group group;


	@TableField(exist = false)
	private List<Consult> consults = new ArrayList<Consult>();

	@TableField(exist = false)
	private List<Evaluate> evaluates = new ArrayList<Evaluate>();

	@TableField(exist = false)
	private List<Favorite> favs = new ArrayList<Favorite>();
	@TableField(exist = false)
	private List<BargainGoods> bgs = new ArrayList<BargainGoods>();
	
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	//private 
	@TableField(exist = false)
	private List<Goods> combin_goods = new ArrayList<Goods>();
	

	@TableField(exist = false)
	private DeliveryGoods dg;
	@TableField(exist = false)
	private Transport transport;

	

	@TableField(exist = false)
	private List<Dynamic> dynamics = new ArrayList<Dynamic>();

	@TableField(exist = false)
	private List<BargainGoods> bargainGoods_list = new ArrayList<BargainGoods>();

	@TableField(exist = false)
	private DeliveryGoods d_main_goods;

	@TableField(exist = false)
	private List<DeliveryGoods> d_goods_list = new ArrayList<DeliveryGoods>();

	@TableField(exist = false)
	private List<ActivityGoods> ag_goods_list = new ArrayList<ActivityGoods>();

	@TableField(exist = false)
	private GoodsReturnitem gri;

	@TableField(exist = false)
	private List<Evaluate> evas = new ArrayList<Evaluate>();
	
	public Store getGoods_store() {
		return goods_store;
	}

	public void setGoods_store(Store goods_store) {
		this.goods_store = goods_store;
	}

	public GoodsClass getGc() {
		return gc;
	}

	public void setGc(GoodsClass gc) {
		this.gc = gc;
	}

	public Accessory getGoods_main_photo() {
		return goods_main_photo;
	}

	public void setGoods_main_photo(Accessory goods_main_photo) {
		this.goods_main_photo = goods_main_photo;
	}

	public List<Accessory> getGoods_photos() {
		return goods_photos;
	}

	public void setGoods_photos(List<Accessory> goods_photos) {
		this.goods_photos = goods_photos;
	}

	public List<UserGoodsClass> getGoods_ugcs() {
		return goods_ugcs;
	}

	public void setGoods_ugcs(List<UserGoodsClass> goods_ugcs) {
		this.goods_ugcs = goods_ugcs;
	}

	public List<GoodsSpecProperty> getGoods_specs() {
		return goods_specs;
	}

	public void setGoods_specs(List<GoodsSpecProperty> goods_specs) {
		this.goods_specs = goods_specs;
	}

	public GoodsBrand getGoods_brand() {
		return goods_brand;
	}

	public void setGoods_brand(GoodsBrand goods_brand) {
		this.goods_brand = goods_brand;
	}

	public User getZtc_admin() {
		return ztc_admin;
	}

	public void setZtc_admin(User ztc_admin) {
		this.ztc_admin = ztc_admin;
	}

	public List<GroupGoods> getGroup_goods_list() {
		return group_goods_list;
	}

	public void setGroup_goods_list(List<GroupGoods> group_goods_list) {
		this.group_goods_list = group_goods_list;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Consult> getConsults() {
		return consults;
	}

	public void setConsults(List<Consult> consults) {
		this.consults = consults;
	}

	public List<Evaluate> getEvaluates() {
		return evaluates;
	}

	public void setEvaluates(List<Evaluate> evaluates) {
		this.evaluates = evaluates;
	}

	public List<Favorite> getFavs() {
		return favs;
	}

	public void setFavs(List<Favorite> favs) {
		this.favs = favs;
	}

	public List<BargainGoods> getBgs() {
		return bgs;
	}

	public void setBgs(List<BargainGoods> bgs) {
		this.bgs = bgs;
	}

	public List<Goods> getCombin_goods() {
		return combin_goods;
	}

	public void setCombin_goods(List<Goods> combin_goods) {
		this.combin_goods = combin_goods;
	}

	public DeliveryGoods getDg() {
		return dg;
	}

	public void setDg(DeliveryGoods dg) {
		this.dg = dg;
	}

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public List<Dynamic> getDynamics() {
		return dynamics;
	}

	public void setDynamics(List<Dynamic> dynamics) {
		this.dynamics = dynamics;
	}

	public List<BargainGoods> getBargainGoods_list() {
		return bargainGoods_list;
	}

	public void setBargainGoods_list(List<BargainGoods> bargainGoods_list) {
		this.bargainGoods_list = bargainGoods_list;
	}

	public DeliveryGoods getD_main_goods() {
		return d_main_goods;
	}

	public void setD_main_goods(DeliveryGoods d_main_goods) {
		this.d_main_goods = d_main_goods;
	}

	public List<DeliveryGoods> getD_goods_list() {
		return d_goods_list;
	}

	public void setD_goods_list(List<DeliveryGoods> d_goods_list) {
		this.d_goods_list = d_goods_list;
	}

	public List<ActivityGoods> getAg_goods_list() {
		return ag_goods_list;
	}

	public void setAg_goods_list(List<ActivityGoods> ag_goods_list) {
		this.ag_goods_list = ag_goods_list;
	}

	public GoodsReturnitem getGri() {
		return gri;
	}

	public void setGri(GoodsReturnitem gri) {
		this.gri = gri;
	}

	public List<Evaluate> getEvas() {
		return evas;
	}

	public void setEvas(List<Evaluate> evas) {
		this.evas = evas;
	}

	/**  */
	@TableId
	private Long id;

	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	@TableField(value = "goods_click")
	private Integer goods_click;

	/**  */
	@TableField(value = "goods_details")
	private String goods_details;

	/**  */
	@TableField(value = "goods_fee")
	private String goods_fee;

	/**  */
	@TableField(value = "goods_inventory")
	private Integer goods_inventory;

	/**  */
	@TableField(value = "goods_inventory_detail")
	private String goods_inventory_detail;

	/**  */
	@TableField(value = "goods_name")
	private String goods_name;

	/**  */
	@TableField(value = "goods_price")
	private BigDecimal goods_price;

	/**  */
	@TableField(value = "goods_property")
	private String goods_property;

	/**  */
	@TableField(value = "goods_recommend")
	private Boolean goods_recommend;

	/**  */
	@TableField(value = "goods_salenum")
	private Integer goods_salenum;

	/**  */
	@TableField(value = "goods_seller_time")
	private Date goods_seller_time;

	/**  */
	@TableField(value = "goods_serial")
	private String goods_serial;

	/**  */
	@TableField(value = "goods_status")
	private Integer goods_status;

	/**  */
	@TableField(value = "goods_transfee")
	private Integer goods_transfee;

	/**  */
	@TableField(value = "goods_weight")
	private BigDecimal goods_weight;

	/**  */
	@TableField(value = "inventory_type")
	private String inventory_type;

	/**  */
	@TableField(value = "seo_description")
	private String seo_description;

	/**  */
	@TableField(value = "seo_keywords")
	private String seo_keywords;

	/**  */
	@TableField(value = "store_price")
	private BigDecimal store_price;

	/**  */
	@TableField(value = "store_recommend")
	private Boolean store_recommend;

	/**  */
	@TableField(value = "store_recommend_time")
	private Date store_recommend_time;

	/**  */
	@TableField(value = "ztc_admin_content")
	private String ztc_admin_content;

	/**  */
	@TableField(value = "ztc_apply_time")
	private Date ztc_apply_time;

	/**  */
	@TableField(value = "ztc_begin_time")
	private Date ztc_begin_time;

	/**  */
	@TableField(value = "ztc_click_num")
	private Integer ztc_click_num;

	/**  */
	@TableField(value = "ztc_dredge_price")
	private Integer ztc_dredge_price;

	/**  */
	@TableField(value = "ztc_gold")
	private Integer ztc_gold;

	/**  */
	@TableField(value = "ztc_pay_status")
	private Integer ztc_pay_status;

	/**  */
	@TableField(value = "ztc_price")
	private Integer ztc_price;

	/**  */
	@TableField(value = "ztc_status")
	private Integer ztc_status;

	/**  */
	@TableField(value = "gc_id")
	private Long gc_id;

	/**  */
	@TableField(value = "goods_brand_id")
	private Long goods_brand_id;

	/**  */
	@TableField(value = "goods_main_photo_id")
	private Long goods_main_photo_id;

	/**  */
	@TableField(value = "goods_store_id")
	private Long goods_store_id;

	/**  */
	@TableField(value = "ztc_admin_id")
	private Long ztc_admin_id;

	/**  */
	@TableField(value = "goods_collect")
	private Integer goods_collect;

	/**  */
	@TableField(value = "group_buy")
	private Integer group_buy;

	/**  */
	@TableField(value = "goods_choice_type")
	private Integer goods_choice_type;

	/**  */
	@TableField(value = "group_id")
	private Long group_id;

	/**  */
	@TableField(value = "activity_status")
	private Integer activity_status;

	/**  */
	@TableField(value = "bargain_status")
	private Integer bargain_status;

	/**  */
	@TableField(value = "delivery_status")
	private Integer delivery_status;

	/**  */
	@TableField(value = "goods_current_price")
	private BigDecimal goods_current_price;

	/**  */
	@TableField(value = "goods_volume")
	private BigDecimal goods_volume;

	/**  */
	@TableField(value = "ems_trans_fee")
	private BigDecimal ems_trans_fee;

	/**  */
	@TableField(value = "express_trans_fee")
	private BigDecimal express_trans_fee;

	/**  */
	@TableField(value = "mail_trans_fee")
	private BigDecimal mail_trans_fee;

	/**  */
	@TableField(value = "transport_id")
	private Long transport_id;

	/**  */
	@TableField(value = "combin_status")
	private Integer combin_status;

	/**  */
	@TableField(value = "combin_begin_time")
	private Date combin_begin_time;

	/**  */
	@TableField(value = "combin_end_time")
	private Date combin_end_time;

	/**  */
	@TableField(value = "combin_price")
	private BigDecimal combin_price;

	/**  */
	@TableField(value = "description_evaluate")
	private BigDecimal description_evaluate;

	/**  */
	@TableField(value = "weixin_shop_hot")
	private Boolean weixin_shop_hot;

	/**  */
	@TableField(value = "weixin_shop_hotTime")
	private Date weixin_shop_hotTime;

	/**  */
	@TableField(value = "weixin_shop_recommend")
	private Boolean weixin_shop_recommend;

	/**  */
	@TableField(value = "weixin_shop_recommendTime")
	private Date weixin_shop_recommendTime;

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

	public Integer getGoods_click() {
		return this.goods_click;
	}

	public void setGoods_click(Integer goods_click) {
		this.goods_click = goods_click;
	}

	public String getGoods_details() {
		return this.goods_details;
	}

	public void setGoods_details(String goods_details) {
		this.goods_details = goods_details;
	}

	public String getGoods_fee() {
		return this.goods_fee;
	}

	public void setGoods_fee(String goods_fee) {
		this.goods_fee = goods_fee;
	}

	public Integer getGoods_inventory() {
		return this.goods_inventory;
	}

	public void setGoods_inventory(Integer goods_inventory) {
		this.goods_inventory = goods_inventory;
	}

	public String getGoods_inventory_detail() {
		return this.goods_inventory_detail;
	}

	public void setGoods_inventory_detail(String goods_inventory_detail) {
		this.goods_inventory_detail = goods_inventory_detail;
	}

	public String getGoods_name() {
		return this.goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public BigDecimal getGoods_price() {
		return this.goods_price;
	}

	public void setGoods_price(BigDecimal goods_price) {
		this.goods_price = goods_price;
	}

	public String getGoods_property() {
		return this.goods_property;
	}

	public void setGoods_property(String goods_property) {
		this.goods_property = goods_property;
	}

	public Boolean getGoods_recommend() {
		return this.goods_recommend;
	}

	public void setGoods_recommend(Boolean goods_recommend) {
		this.goods_recommend = goods_recommend;
	}

	public Integer getGoods_salenum() {
		return this.goods_salenum;
	}

	public void setGoods_salenum(Integer goods_salenum) {
		this.goods_salenum = goods_salenum;
	}

	public Date getGoods_seller_time() {
		return this.goods_seller_time;
	}

	public void setGoods_seller_time(Date goods_seller_time) {
		this.goods_seller_time = goods_seller_time;
	}

	public String getGoods_serial() {
		return this.goods_serial;
	}

	public void setGoods_serial(String goods_serial) {
		this.goods_serial = goods_serial;
	}

	public Integer getGoods_status() {
		return this.goods_status;
	}

	public void setGoods_status(Integer goods_status) {
		this.goods_status = goods_status;
	}

	public Integer getGoods_transfee() {
		return this.goods_transfee;
	}

	public void setGoods_transfee(Integer goods_transfee) {
		this.goods_transfee = goods_transfee;
	}

	public BigDecimal getGoods_weight() {
		return this.goods_weight;
	}

	public void setGoods_weight(BigDecimal goods_weight) {
		this.goods_weight = goods_weight;
	}

	public String getInventory_type() {
		return this.inventory_type;
	}

	public void setInventory_type(String inventory_type) {
		this.inventory_type = inventory_type;
	}

	public String getSeo_description() {
		return this.seo_description;
	}

	public void setSeo_description(String seo_description) {
		this.seo_description = seo_description;
	}

	public String getSeo_keywords() {
		return this.seo_keywords;
	}

	public void setSeo_keywords(String seo_keywords) {
		this.seo_keywords = seo_keywords;
	}

	public BigDecimal getStore_price() {
		return this.store_price;
	}

	public void setStore_price(BigDecimal store_price) {
		this.store_price = store_price;
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

	public String getZtc_admin_content() {
		return this.ztc_admin_content;
	}

	public void setZtc_admin_content(String ztc_admin_content) {
		this.ztc_admin_content = ztc_admin_content;
	}

	public Date getZtc_apply_time() {
		return this.ztc_apply_time;
	}

	public void setZtc_apply_time(Date ztc_apply_time) {
		this.ztc_apply_time = ztc_apply_time;
	}

	public Date getZtc_begin_time() {
		return this.ztc_begin_time;
	}

	public void setZtc_begin_time(Date ztc_begin_time) {
		this.ztc_begin_time = ztc_begin_time;
	}

	public Integer getZtc_click_num() {
		return this.ztc_click_num;
	}

	public void setZtc_click_num(Integer ztc_click_num) {
		this.ztc_click_num = ztc_click_num;
	}

	public Integer getZtc_dredge_price() {
		return this.ztc_dredge_price;
	}

	public void setZtc_dredge_price(Integer ztc_dredge_price) {
		this.ztc_dredge_price = ztc_dredge_price;
	}

	public Integer getZtc_gold() {
		return this.ztc_gold;
	}

	public void setZtc_gold(Integer ztc_gold) {
		this.ztc_gold = ztc_gold;
	}

	public Integer getZtc_pay_status() {
		return this.ztc_pay_status;
	}

	public void setZtc_pay_status(Integer ztc_pay_status) {
		this.ztc_pay_status = ztc_pay_status;
	}

	public Integer getZtc_price() {
		return this.ztc_price;
	}

	public void setZtc_price(Integer ztc_price) {
		this.ztc_price = ztc_price;
	}

	public Integer getZtc_status() {
		return this.ztc_status;
	}

	public void setZtc_status(Integer ztc_status) {
		this.ztc_status = ztc_status;
	}

	public Long getGc_id() {
		return this.gc_id;
	}

	public void setGc_id(Long gc_id) {
		this.gc_id = gc_id;
	}

	public Long getGoods_brand_id() {
		return this.goods_brand_id;
	}

	public void setGoods_brand_id(Long goods_brand_id) {
		this.goods_brand_id = goods_brand_id;
	}

	public Long getGoods_main_photo_id() {
		return this.goods_main_photo_id;
	}

	public void setGoods_main_photo_id(Long goods_main_photo_id) {
		this.goods_main_photo_id = goods_main_photo_id;
	}

	public Long getGoods_store_id() {
		return this.goods_store_id;
	}

	public void setGoods_store_id(Long goods_store_id) {
		this.goods_store_id = goods_store_id;
	}

	public Long getZtc_admin_id() {
		return this.ztc_admin_id;
	}

	public void setZtc_admin_id(Long ztc_admin_id) {
		this.ztc_admin_id = ztc_admin_id;
	}

	public Integer getGoods_collect() {
		return this.goods_collect;
	}

	public void setGoods_collect(Integer goods_collect) {
		this.goods_collect = goods_collect;
	}

	public Integer getGroup_buy() {
		return this.group_buy;
	}

	public void setGroup_buy(Integer group_buy) {
		this.group_buy = group_buy;
	}

	public Integer getGoods_choice_type() {
		return this.goods_choice_type;
	}

	public void setGoods_choice_type(Integer goods_choice_type) {
		this.goods_choice_type = goods_choice_type;
	}

	public Long getGroup_id() {
		return this.group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Integer getActivity_status() {
		return this.activity_status;
	}

	public void setActivity_status(Integer activity_status) {
		this.activity_status = activity_status;
	}

	public Integer getBargain_status() {
		return this.bargain_status;
	}

	public void setBargain_status(Integer bargain_status) {
		this.bargain_status = bargain_status;
	}

	public Integer getDelivery_status() {
		return this.delivery_status;
	}

	public void setDelivery_status(Integer delivery_status) {
		this.delivery_status = delivery_status;
	}

	public BigDecimal getGoods_current_price() {
		return this.goods_current_price;
	}

	public void setGoods_current_price(BigDecimal goods_current_price) {
		this.goods_current_price = goods_current_price;
	}

	public BigDecimal getGoods_volume() {
		return this.goods_volume;
	}

	public void setGoods_volume(BigDecimal goods_volume) {
		this.goods_volume = goods_volume;
	}

	public BigDecimal getEms_trans_fee() {
		return this.ems_trans_fee;
	}

	public void setEms_trans_fee(BigDecimal ems_trans_fee) {
		this.ems_trans_fee = ems_trans_fee;
	}

	public BigDecimal getExpress_trans_fee() {
		return this.express_trans_fee;
	}

	public void setExpress_trans_fee(BigDecimal express_trans_fee) {
		this.express_trans_fee = express_trans_fee;
	}

	public BigDecimal getMail_trans_fee() {
		return this.mail_trans_fee;
	}

	public void setMail_trans_fee(BigDecimal mail_trans_fee) {
		this.mail_trans_fee = mail_trans_fee;
	}

	public Long getTransport_id() {
		return this.transport_id;
	}

	public void setTransport_id(Long transport_id) {
		this.transport_id = transport_id;
	}

	public Integer getCombin_status() {
		return this.combin_status;
	}

	public void setCombin_status(Integer combin_status) {
		this.combin_status = combin_status;
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

	public BigDecimal getCombin_price() {
		return this.combin_price;
	}

	public void setCombin_price(BigDecimal combin_price) {
		this.combin_price = combin_price;
	}

	public BigDecimal getDescription_evaluate() {
		return this.description_evaluate;
	}

	public void setDescription_evaluate(BigDecimal description_evaluate) {
		this.description_evaluate = description_evaluate;
	}

	public Boolean getWeixin_shop_hot() {
		return this.weixin_shop_hot;
	}

	public void setWeixin_shop_hot(Boolean weixin_shop_hot) {
		this.weixin_shop_hot = weixin_shop_hot;
	}

	public Date getWeixin_shop_hotTime() {
		return this.weixin_shop_hotTime;
	}

	public void setWeixin_shop_hotTime(Date weixin_shop_hotTime) {
		this.weixin_shop_hotTime = weixin_shop_hotTime;
	}

	public Boolean getWeixin_shop_recommend() {
		return this.weixin_shop_recommend;
	}

	public void setWeixin_shop_recommend(Boolean weixin_shop_recommend) {
		this.weixin_shop_recommend = weixin_shop_recommend;
	}

	public Date getWeixin_shop_recommendTime() {
		return this.weixin_shop_recommendTime;
	}

	public void setWeixin_shop_recommendTime(Date weixin_shop_recommendTime) {
		this.weixin_shop_recommendTime = weixin_shop_recommendTime;
	}

	
}
