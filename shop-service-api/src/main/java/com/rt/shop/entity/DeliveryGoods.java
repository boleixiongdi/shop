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
@TableName(value = "shopping_delivery_goods")
public class DeliveryGoods implements Serializable {
	@TableField(exist = false)
	private Goods d_goods;
	
	//商品
	@TableField(exist = false)
	private Goods d_delivery_goods;
	
	public Goods getD_goods() {
		return d_goods;
	}

	public void setD_goods(Goods d_goods) {
		this.d_goods = d_goods;
	}

	public Goods getD_delivery_goods() {
		return d_delivery_goods;
	}

	public void setD_delivery_goods(Goods d_delivery_goods) {
		this.d_delivery_goods = d_delivery_goods;
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
	@TableField(value = "d_status")
	private Integer d_status;

	/**  */
	@TableField(value = "d_admin_user_id")
	private Long d_admin_user_id;

	/**  */
	@TableField(value = "d_delivery_goods_id")
	private Long d_delivery_goods_id;

	/**  */
	@TableField(value = "d_goods_id")
	private Long d_goods_id;

	/**  */
	@TableField(value = "d_begin_time")
	private Date d_begin_time;

	/**  */
	@TableField(value = "d_end_time")
	private Date d_end_time;

	/**  */
	@TableField(value = "d_audit_time")
	private Date d_audit_time;

	/**  */
	@TableField(value = "d_refuse_time")
	private Date d_refuse_time;

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

	public Integer getD_status() {
		return this.d_status;
	}

	public void setD_status(Integer d_status) {
		this.d_status = d_status;
	}

	public Long getD_admin_user_id() {
		return this.d_admin_user_id;
	}

	public void setD_admin_user_id(Long d_admin_user_id) {
		this.d_admin_user_id = d_admin_user_id;
	}

	public Long getD_delivery_goods_id() {
		return this.d_delivery_goods_id;
	}

	public void setD_delivery_goods_id(Long d_delivery_goods_id) {
		this.d_delivery_goods_id = d_delivery_goods_id;
	}

	public Long getD_goods_id() {
		return this.d_goods_id;
	}

	public void setD_goods_id(Long d_goods_id) {
		this.d_goods_id = d_goods_id;
	}

	public Date getD_begin_time() {
		return this.d_begin_time;
	}

	public void setD_begin_time(Date d_begin_time) {
		this.d_begin_time = d_begin_time;
	}

	public Date getD_end_time() {
		return this.d_end_time;
	}

	public void setD_end_time(Date d_end_time) {
		this.d_end_time = d_end_time;
	}

	public Date getD_audit_time() {
		return this.d_audit_time;
	}

	public void setD_audit_time(Date d_audit_time) {
		this.d_audit_time = d_audit_time;
	}

	public Date getD_refuse_time() {
		return this.d_refuse_time;
	}

	public void setD_refuse_time(Date d_refuse_time) {
		this.d_refuse_time = d_refuse_time;
	}

}
