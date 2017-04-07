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
@TableName(value = "shopping_integral_goods")
public class IntegralGoods implements Serializable {
	@TableField(exist = false)
	   private Accessory ig_goods_img;
	   @TableField(exist = false)
	   private List<IntegralGoodsCart> gcs = new ArrayList();
	   
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
	@TableField(value = "ig_begin_time")
	private Date ig_begin_time;

	/**  */
	@TableField(value = "ig_click_count")
	private Integer ig_click_count;

	/**  */
	@TableField(value = "ig_content")
	private String ig_content;

	/**  */
	@TableField(value = "ig_end_time")
	private Date ig_end_time;

	/**  */
	@TableField(value = "ig_exchange_count")
	private Integer ig_exchange_count;

	/**  */
	@TableField(value = "ig_goods_count")
	private Integer ig_goods_count;

	/**  */
	@TableField(value = "ig_goods_integral")
	private Integer ig_goods_integral;

	/**  */
	@TableField(value = "ig_goods_name")
	private String ig_goods_name;

	/**  */
	@TableField(value = "ig_goods_price")
	private BigDecimal ig_goods_price;

	/**  */
	@TableField(value = "ig_goods_sn")
	private String ig_goods_sn;

	/**  */
	@TableField(value = "ig_goods_tag")
	private String ig_goods_tag;

	/**  */
	@TableField(value = "ig_limit_count")
	private Integer ig_limit_count;

	/**  */
	@TableField(value = "ig_limit_type")
	private Boolean ig_limit_type;

	/**  */
	@TableField(value = "ig_recommend")
	private Boolean ig_recommend;

	/**  */
	@TableField(value = "ig_seo_description")
	private String ig_seo_description;

	/**  */
	@TableField(value = "ig_seo_keywords")
	private String ig_seo_keywords;

	/**  */
	@TableField(value = "ig_sequence")
	private Integer ig_sequence;

	/**  */
	@TableField(value = "ig_show")
	private Boolean ig_show;

	/**  */
	@TableField(value = "ig_time_type")
	private Boolean ig_time_type;

	/**  */
	@TableField(value = "ig_transfee")
	private BigDecimal ig_transfee;

	/**  */
	@TableField(value = "ig_transfee_type")
	private Integer ig_transfee_type;

	/**  */
	@TableField(value = "ig_goods_img_id")
	private Long ig_goods_img_id;

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

	public Date getIg_begin_time() {
		return this.ig_begin_time;
	}

	public void setIg_begin_time(Date ig_begin_time) {
		this.ig_begin_time = ig_begin_time;
	}

	public Integer getIg_click_count() {
		return this.ig_click_count;
	}

	public void setIg_click_count(Integer ig_click_count) {
		this.ig_click_count = ig_click_count;
	}

	public String getIg_content() {
		return this.ig_content;
	}

	public void setIg_content(String ig_content) {
		this.ig_content = ig_content;
	}

	public Date getIg_end_time() {
		return this.ig_end_time;
	}

	public void setIg_end_time(Date ig_end_time) {
		this.ig_end_time = ig_end_time;
	}

	public Integer getIg_exchange_count() {
		return this.ig_exchange_count;
	}

	public void setIg_exchange_count(Integer ig_exchange_count) {
		this.ig_exchange_count = ig_exchange_count;
	}

	public Integer getIg_goods_count() {
		return this.ig_goods_count;
	}

	public void setIg_goods_count(Integer ig_goods_count) {
		this.ig_goods_count = ig_goods_count;
	}

	public Integer getIg_goods_integral() {
		return this.ig_goods_integral;
	}

	public void setIg_goods_integral(Integer ig_goods_integral) {
		this.ig_goods_integral = ig_goods_integral;
	}

	public String getIg_goods_name() {
		return this.ig_goods_name;
	}

	public void setIg_goods_name(String ig_goods_name) {
		this.ig_goods_name = ig_goods_name;
	}

	public BigDecimal getIg_goods_price() {
		return this.ig_goods_price;
	}

	public void setIg_goods_price(BigDecimal ig_goods_price) {
		this.ig_goods_price = ig_goods_price;
	}

	public String getIg_goods_sn() {
		return this.ig_goods_sn;
	}

	public void setIg_goods_sn(String ig_goods_sn) {
		this.ig_goods_sn = ig_goods_sn;
	}

	public String getIg_goods_tag() {
		return this.ig_goods_tag;
	}

	public void setIg_goods_tag(String ig_goods_tag) {
		this.ig_goods_tag = ig_goods_tag;
	}

	public Integer getIg_limit_count() {
		return this.ig_limit_count;
	}

	public void setIg_limit_count(Integer ig_limit_count) {
		this.ig_limit_count = ig_limit_count;
	}

	public Boolean getIg_limit_type() {
		return this.ig_limit_type;
	}

	public void setIg_limit_type(Boolean ig_limit_type) {
		this.ig_limit_type = ig_limit_type;
	}

	public Boolean getIg_recommend() {
		return this.ig_recommend;
	}

	public void setIg_recommend(Boolean ig_recommend) {
		this.ig_recommend = ig_recommend;
	}

	public String getIg_seo_description() {
		return this.ig_seo_description;
	}

	public void setIg_seo_description(String ig_seo_description) {
		this.ig_seo_description = ig_seo_description;
	}

	public String getIg_seo_keywords() {
		return this.ig_seo_keywords;
	}

	public void setIg_seo_keywords(String ig_seo_keywords) {
		this.ig_seo_keywords = ig_seo_keywords;
	}

	public Integer getIg_sequence() {
		return this.ig_sequence;
	}

	public void setIg_sequence(Integer ig_sequence) {
		this.ig_sequence = ig_sequence;
	}

	public Boolean getIg_show() {
		return this.ig_show;
	}

	public void setIg_show(Boolean ig_show) {
		this.ig_show = ig_show;
	}

	public Boolean getIg_time_type() {
		return this.ig_time_type;
	}

	public void setIg_time_type(Boolean ig_time_type) {
		this.ig_time_type = ig_time_type;
	}

	public BigDecimal getIg_transfee() {
		return this.ig_transfee;
	}

	public void setIg_transfee(BigDecimal ig_transfee) {
		this.ig_transfee = ig_transfee;
	}

	public Integer getIg_transfee_type() {
		return this.ig_transfee_type;
	}

	public void setIg_transfee_type(Integer ig_transfee_type) {
		this.ig_transfee_type = ig_transfee_type;
	}

	public Long getIg_goods_img_id() {
		return this.ig_goods_img_id;
	}

	public void setIg_goods_img_id(Long ig_goods_img_id) {
		this.ig_goods_img_id = ig_goods_img_id;
	}

}
