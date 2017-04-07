package com.rt.shop.entity;

import java.io.Serializable;
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
@TableName(value = "shopping_goods_floor")
public class GoodsFloor implements Serializable {
	@TableField(exist = false)
	private List<GoodsFloor> childs = new ArrayList<GoodsFloor>();

	@TableField(exist = false)
	private GoodsFloor parent;
	
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	public List<GoodsFloor> getChilds() {
		return childs;
	}

	public void setChilds(List<GoodsFloor> childs) {
		this.childs = childs;
	}

	public GoodsFloor getParent() {
		return parent;
	}

	public void setParent(GoodsFloor parent) {
		this.parent = parent;
	}

	/**  */
	@TableId
	private Long id;

	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	@TableField(value = "gf_css")
	private String gf_css;

	/**  */
	@TableField(value = "gf_display")
	private Boolean gf_display;

	/**  */
	@TableField(value = "gf_goods_count")
	private Integer gf_goods_count;

	/**  */
	@TableField(value = "gf_level")
	private Integer gf_level;

	/**  */
	@TableField(value = "gf_name")
	private String gf_name;

	/**  */
	@TableField(value = "gf_sequence")
	private Integer gf_sequence;

	/**  */
	@TableField(value = "parent_id")
	private Long parent_id;

	/**  */
	@TableField(value = "gf_gc_goods")
	private String gf_gc_goods;

	/**  */
	@TableField(value = "gf_gc_list")
	private String gf_gc_list;

	/**  */
	@TableField(value = "gf_left_adv")
	private String gf_left_adv;

	/**  */
	@TableField(value = "gf_list_goods")
	private String gf_list_goods;

	/**  */
	@TableField(value = "gf_right_adv")
	private String gf_right_adv;

	/**  */
	@TableField(value = "gf_brand_list")
	private String gf_brand_list;

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

	public String getGf_css() {
		return this.gf_css;
	}

	public void setGf_css(String gf_css) {
		this.gf_css = gf_css;
	}

	public Boolean getGf_display() {
		return this.gf_display;
	}

	public void setGf_display(Boolean gf_display) {
		this.gf_display = gf_display;
	}

	public Integer getGf_goods_count() {
		return this.gf_goods_count;
	}

	public void setGf_goods_count(Integer gf_goods_count) {
		this.gf_goods_count = gf_goods_count;
	}

	public Integer getGf_level() {
		return this.gf_level;
	}

	public void setGf_level(Integer gf_level) {
		this.gf_level = gf_level;
	}

	public String getGf_name() {
		return this.gf_name;
	}

	public void setGf_name(String gf_name) {
		this.gf_name = gf_name;
	}

	public Integer getGf_sequence() {
		return this.gf_sequence;
	}

	public void setGf_sequence(Integer gf_sequence) {
		this.gf_sequence = gf_sequence;
	}

	public Long getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public String getGf_gc_goods() {
		return this.gf_gc_goods;
	}

	public void setGf_gc_goods(String gf_gc_goods) {
		this.gf_gc_goods = gf_gc_goods;
	}

	public String getGf_gc_list() {
		return this.gf_gc_list;
	}

	public void setGf_gc_list(String gf_gc_list) {
		this.gf_gc_list = gf_gc_list;
	}

	public String getGf_left_adv() {
		return this.gf_left_adv;
	}

	public void setGf_left_adv(String gf_left_adv) {
		this.gf_left_adv = gf_left_adv;
	}

	public String getGf_list_goods() {
		return this.gf_list_goods;
	}

	public void setGf_list_goods(String gf_list_goods) {
		this.gf_list_goods = gf_list_goods;
	}

	public String getGf_right_adv() {
		return this.gf_right_adv;
	}

	public void setGf_right_adv(String gf_right_adv) {
		this.gf_right_adv = gf_right_adv;
	}

	public String getGf_brand_list() {
		return this.gf_brand_list;
	}

	public void setGf_brand_list(String gf_brand_list) {
		this.gf_brand_list = gf_brand_list;
	}

}
