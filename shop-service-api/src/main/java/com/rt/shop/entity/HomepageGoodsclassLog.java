package com.rt.shop.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName(value = "shopping_homepage_goodsclass_log")
public class HomepageGoodsclassLog implements Serializable {

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
	@TableField(value = "goodsClass_id")
	private Long goodsClass_id;

	/**  */
	@TableField(value = "homepageGoodsClass_id")
	private Long homepageGoodsClass_id;

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

	public Long getGoodsClass_id() {
		return this.goodsClass_id;
	}

	public void setGoodsClass_id(Long goodsClass_id) {
		this.goodsClass_id = goodsClass_id;
	}

	public Long getHomepageGoodsClass_id() {
		return this.homepageGoodsClass_id;
	}

	public void setHomepageGoodsClass_id(Long homepageGoodsClass_id) {
		this.homepageGoodsClass_id = homepageGoodsClass_id;
	}

}
