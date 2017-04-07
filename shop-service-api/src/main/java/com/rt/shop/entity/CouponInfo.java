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
@TableName(value = "shopping_coupon_info")
public class CouponInfo implements Serializable {
	@TableField(exist = false)
	private User user;
	
	//优惠券
	@TableField(exist = false)
	private Coupon coupon;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
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
	@TableField(value = "coupon_sn")
	private String coupon_sn;

	/**  */
	private Integer status;

	/**  */
	@TableField(value = "coupon_id")
	private Long coupon_id;

	/**  */
	@TableField(value = "user_id")
	private Long user_id;

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

	public String getCoupon_sn() {
		return this.coupon_sn;
	}

	public void setCoupon_sn(String coupon_sn) {
		this.coupon_sn = coupon_sn;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCoupon_id() {
		return this.coupon_id;
	}

	public void setCoupon_id(Long coupon_id) {
		this.coupon_id = coupon_id;
	}

	public Long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

}
