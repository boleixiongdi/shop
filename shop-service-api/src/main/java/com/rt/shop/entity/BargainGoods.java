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
@TableName(value = "shopping_bargain_goods")
public class BargainGoods implements Serializable {
	@TableField(exist = false)
	private Goods bg_goods;
	@TableField(exist = false)
	private User bg_admin_user;
	
	public Goods getBg_goods() {
		return bg_goods;
	}

	public void setBg_goods(Goods bg_goods) {
		this.bg_goods = bg_goods;
	}

	public User getBg_admin_user() {
		return bg_admin_user;
	}

	public void setBg_admin_user(User bg_admin_user) {
		this.bg_admin_user = bg_admin_user;
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
	@TableField(value = "bg_price")
	private BigDecimal bg_price;

	/**  */
	@TableField(value = "bg_status")
	private Integer bg_status;

	/**  */
	@TableField(value = "bg_time")
	private Date bg_time;

	/**  */
	@TableField(value = "bg_admin_user_id")
	private Long bg_admin_user_id;

	/**  */
	@TableField(value = "bg_goods_id")
	private Long bg_goods_id;

	/**  */
	@TableField(value = "bg_count")
	private Integer bg_count;

	/**  */
	@TableField(value = "bg_rebate")
	private BigDecimal bg_rebate;

	/**  */
	@TableField(value = "audit_time")
	private Date audit_time;

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

	public BigDecimal getBg_price() {
		return this.bg_price;
	}

	public void setBg_price(BigDecimal bg_price) {
		this.bg_price = bg_price;
	}

	public Integer getBg_status() {
		return this.bg_status;
	}

	public void setBg_status(Integer bg_status) {
		this.bg_status = bg_status;
	}

	public Date getBg_time() {
		return this.bg_time;
	}

	public void setBg_time(Date bg_time) {
		this.bg_time = bg_time;
	}

	public Long getBg_admin_user_id() {
		return this.bg_admin_user_id;
	}

	public void setBg_admin_user_id(Long bg_admin_user_id) {
		this.bg_admin_user_id = bg_admin_user_id;
	}

	public Long getBg_goods_id() {
		return this.bg_goods_id;
	}

	public void setBg_goods_id(Long bg_goods_id) {
		this.bg_goods_id = bg_goods_id;
	}

	public Integer getBg_count() {
		return this.bg_count;
	}

	public void setBg_count(Integer bg_count) {
		this.bg_count = bg_count;
	}

	public BigDecimal getBg_rebate() {
		return this.bg_rebate;
	}

	public void setBg_rebate(BigDecimal bg_rebate) {
		this.bg_rebate = bg_rebate;
	}

	public Date getAudit_time() {
		return this.audit_time;
	}

	public void setAudit_time(Date audit_time) {
		this.audit_time = audit_time;
	}

}
