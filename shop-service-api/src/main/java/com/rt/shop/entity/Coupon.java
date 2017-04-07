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
@TableName(value = "shopping_coupon")
public class Coupon implements Serializable {

	@TableField(exist = false)
	private Accessory coupon_acc;
	
	//优惠信息
	@TableField(exist = false)
	private List<CouponInfo> couponinfos = new ArrayList<CouponInfo>();
	
	public Accessory getCoupon_acc() {
		return coupon_acc;
	}

	public void setCoupon_acc(Accessory coupon_acc) {
		this.coupon_acc = coupon_acc;
	}

	public List<CouponInfo> getCouponinfos() {
		return couponinfos;
	}

	public void setCouponinfos(List<CouponInfo> couponinfos) {
		this.couponinfos = couponinfos;
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
	@TableField(value = "coupon_amount")
	private BigDecimal coupon_amount;

	/**  */
	@TableField(value = "coupon_begin_time")
	private Date coupon_begin_time;

	/**  */
	@TableField(value = "coupon_count")
	private Integer coupon_count;

	/**  */
	@TableField(value = "coupon_end_time")
	private Date coupon_end_time;

	/**  */
	@TableField(value = "coupon_name")
	private String coupon_name;

	/**  */
	@TableField(value = "coupon_order_amount")
	private BigDecimal coupon_order_amount;

	/**  */
	@TableField(value = "coupon_acc_id")
	private Long coupon_acc_id;

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

	public BigDecimal getCoupon_amount() {
		return this.coupon_amount;
	}

	public void setCoupon_amount(BigDecimal coupon_amount) {
		this.coupon_amount = coupon_amount;
	}

	public Date getCoupon_begin_time() {
		return this.coupon_begin_time;
	}

	public void setCoupon_begin_time(Date coupon_begin_time) {
		this.coupon_begin_time = coupon_begin_time;
	}

	public Integer getCoupon_count() {
		return this.coupon_count;
	}

	public void setCoupon_count(Integer coupon_count) {
		this.coupon_count = coupon_count;
	}

	public Date getCoupon_end_time() {
		return this.coupon_end_time;
	}

	public void setCoupon_end_time(Date coupon_end_time) {
		this.coupon_end_time = coupon_end_time;
	}

	public String getCoupon_name() {
		return this.coupon_name;
	}

	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}

	public BigDecimal getCoupon_order_amount() {
		return this.coupon_order_amount;
	}

	public void setCoupon_order_amount(BigDecimal coupon_order_amount) {
		this.coupon_order_amount = coupon_order_amount;
	}

	public Long getCoupon_acc_id() {
		return this.coupon_acc_id;
	}

	public void setCoupon_acc_id(Long coupon_acc_id) {
		this.coupon_acc_id = coupon_acc_id;
	}

}
