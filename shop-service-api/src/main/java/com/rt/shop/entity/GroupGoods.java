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
@TableName(value = "shopping_group_goods")
public class GroupGoods implements Serializable {
	@TableField(exist = false)
	 private Accessory gg_img;
	@TableField(exist = false)
	private Group group;
	   //团购类型
	@TableField(exist = false)
	   private GroupClass gg_gc;
	   //团购地区
	   @TableField(exist = false)
	   private GroupArea gg_ga;
	   
	   //团购商品
	   @TableField(exist = false)
	   private Goods gg_goods;
	   
	   
	public Accessory getGg_img() {
		return gg_img;
	}

	public void setGg_img(Accessory gg_img) {
		this.gg_img = gg_img;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public GroupClass getGg_gc() {
		return gg_gc;
	}

	public void setGg_gc(GroupClass gg_gc) {
		this.gg_gc = gg_gc;
	}

	public GroupArea getGg_ga() {
		return gg_ga;
	}

	public void setGg_ga(GroupArea gg_ga) {
		this.gg_ga = gg_ga;
	}

	public Goods getGg_goods() {
		return gg_goods;
	}

	public void setGg_goods(Goods gg_goods) {
		this.gg_goods = gg_goods;
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
	@TableField(value = "gg_audit_time")
	private Date gg_audit_time;

	/**  */
	@TableField(value = "gg_content")
	private String gg_content;

	/**  */
	@TableField(value = "gg_count")
	private Integer gg_count;

	/**  */
	@TableField(value = "gg_def_count")
	private Integer gg_def_count;

	/**  */
	@TableField(value = "gg_group_count")
	private Integer gg_group_count;

	/**  */
	@TableField(value = "gg_max_count")
	private Integer gg_max_count;

	/**  */
	@TableField(value = "gg_min_count")
	private Integer gg_min_count;

	/**  */
	@TableField(value = "gg_name")
	private String gg_name;

	/**  */
	@TableField(value = "gg_price")
	private BigDecimal gg_price;

	/**  */
	@TableField(value = "gg_rebate")
	private BigDecimal gg_rebate;

	/**  */
	@TableField(value = "gg_recommend")
	private Integer gg_recommend;

	/**  */
	@TableField(value = "gg_recommend_time")
	private Date gg_recommend_time;

	/**  */
	@TableField(value = "gg_status")
	private Integer gg_status;

	/**  */
	@TableField(value = "gg_vir_count")
	private Integer gg_vir_count;

	/**  */
	@TableField(value = "gg_ga_id")
	private Long gg_ga_id;

	/**  */
	@TableField(value = "gg_gc_id")
	private Long gg_gc_id;

	/**  */
	@TableField(value = "gg_goods_id")
	private Long gg_goods_id;

	/**  */
	@TableField(value = "gg_img_id")
	private Long gg_img_id;

	/**  */
	@TableField(value = "group_id")
	private Long group_id;

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

	public Date getGg_audit_time() {
		return this.gg_audit_time;
	}

	public void setGg_audit_time(Date gg_audit_time) {
		this.gg_audit_time = gg_audit_time;
	}

	public String getGg_content() {
		return this.gg_content;
	}

	public void setGg_content(String gg_content) {
		this.gg_content = gg_content;
	}

	public Integer getGg_count() {
		return this.gg_count;
	}

	public void setGg_count(Integer gg_count) {
		this.gg_count = gg_count;
	}

	public Integer getGg_def_count() {
		return this.gg_def_count;
	}

	public void setGg_def_count(Integer gg_def_count) {
		this.gg_def_count = gg_def_count;
	}

	public Integer getGg_group_count() {
		return this.gg_group_count;
	}

	public void setGg_group_count(Integer gg_group_count) {
		this.gg_group_count = gg_group_count;
	}

	public Integer getGg_max_count() {
		return this.gg_max_count;
	}

	public void setGg_max_count(Integer gg_max_count) {
		this.gg_max_count = gg_max_count;
	}

	public Integer getGg_min_count() {
		return this.gg_min_count;
	}

	public void setGg_min_count(Integer gg_min_count) {
		this.gg_min_count = gg_min_count;
	}

	public String getGg_name() {
		return this.gg_name;
	}

	public void setGg_name(String gg_name) {
		this.gg_name = gg_name;
	}

	public BigDecimal getGg_price() {
		return this.gg_price;
	}

	public void setGg_price(BigDecimal gg_price) {
		this.gg_price = gg_price;
	}

	public BigDecimal getGg_rebate() {
		return this.gg_rebate;
	}

	public void setGg_rebate(BigDecimal gg_rebate) {
		this.gg_rebate = gg_rebate;
	}

	public Integer getGg_recommend() {
		return this.gg_recommend;
	}

	public void setGg_recommend(Integer gg_recommend) {
		this.gg_recommend = gg_recommend;
	}

	public Date getGg_recommend_time() {
		return this.gg_recommend_time;
	}

	public void setGg_recommend_time(Date gg_recommend_time) {
		this.gg_recommend_time = gg_recommend_time;
	}

	public Integer getGg_status() {
		return this.gg_status;
	}

	public void setGg_status(Integer gg_status) {
		this.gg_status = gg_status;
	}

	public Integer getGg_vir_count() {
		return this.gg_vir_count;
	}

	public void setGg_vir_count(Integer gg_vir_count) {
		this.gg_vir_count = gg_vir_count;
	}

	public Long getGg_ga_id() {
		return this.gg_ga_id;
	}

	public void setGg_ga_id(Long gg_ga_id) {
		this.gg_ga_id = gg_ga_id;
	}

	public Long getGg_gc_id() {
		return this.gg_gc_id;
	}

	public void setGg_gc_id(Long gg_gc_id) {
		this.gg_gc_id = gg_gc_id;
	}

	public Long getGg_goods_id() {
		return this.gg_goods_id;
	}

	public void setGg_goods_id(Long gg_goods_id) {
		this.gg_goods_id = gg_goods_id;
	}

	public Long getGg_img_id() {
		return this.gg_img_id;
	}

	public void setGg_img_id(Long gg_img_id) {
		this.gg_img_id = gg_img_id;
	}

	public Long getGroup_id() {
		return this.group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
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
