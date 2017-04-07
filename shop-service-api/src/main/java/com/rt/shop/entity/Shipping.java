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
@TableName(value = "shopping_shipping")
public class Shipping implements Serializable {

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
	private Integer sequence;

	/**  */
	@TableField(value = "shipping_add_fee")
	private BigDecimal shipping_add_fee;

	/**  */
	@TableField(value = "shipping_add_weight")
	private Integer shipping_add_weight;

	/**  */
	@TableField(value = "shipping_fee")
	private BigDecimal shipping_fee;

	/**  */
	@TableField(value = "shipping_info")
	private String shipping_info;

	/**  */
	@TableField(value = "shipping_name")
	private String shipping_name;

	/**  */
	@TableField(value = "shipping_weight")
	private Integer shipping_weight;

	/**  */
	private Integer status;

	/**  */
	@TableField(value = "store_id")
	private Long store_id;

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

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public BigDecimal getShipping_add_fee() {
		return this.shipping_add_fee;
	}

	public void setShipping_add_fee(BigDecimal shipping_add_fee) {
		this.shipping_add_fee = shipping_add_fee;
	}

	public Integer getShipping_add_weight() {
		return this.shipping_add_weight;
	}

	public void setShipping_add_weight(Integer shipping_add_weight) {
		this.shipping_add_weight = shipping_add_weight;
	}

	public BigDecimal getShipping_fee() {
		return this.shipping_fee;
	}

	public void setShipping_fee(BigDecimal shipping_fee) {
		this.shipping_fee = shipping_fee;
	}

	public String getShipping_info() {
		return this.shipping_info;
	}

	public void setShipping_info(String shipping_info) {
		this.shipping_info = shipping_info;
	}

	public String getShipping_name() {
		return this.shipping_name;
	}

	public void setShipping_name(String shipping_name) {
		this.shipping_name = shipping_name;
	}

	public Integer getShipping_weight() {
		return this.shipping_weight;
	}

	public void setShipping_weight(Integer shipping_weight) {
		this.shipping_weight = shipping_weight;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getStore_id() {
		return this.store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

}
