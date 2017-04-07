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
@TableName(value = "shopping_store_stat")
public class StoreStat implements Serializable {

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
	@TableField(value = "all_goods")
	private Integer all_goods;

	/**  */
	@TableField(value = "all_store")
	private Integer all_store;

	/**  */
	@TableField(value = "all_user")
	private Integer all_user;

	/**  */
	@TableField(value = "next_time")
	private Date next_time;

	/**  */
	@TableField(value = "order_amount")
	private BigDecimal order_amount;

	/**  */
	@TableField(value = "store_update")
	private Integer store_update;

	/**  */
	@TableField(value = "week_complaint")
	private Integer week_complaint;

	/**  */
	@TableField(value = "week_goods")
	private Integer week_goods;

	/**  */
	@TableField(value = "week_order")
	private Integer week_order;

	/**  */
	@TableField(value = "week_report")
	private Integer week_report;

	/**  */
	@TableField(value = "week_store")
	private Integer week_store;

	/**  */
	@TableField(value = "week_user")
	private Integer week_user;

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

	public Integer getAll_goods() {
		return this.all_goods;
	}

	public void setAll_goods(Integer all_goods) {
		this.all_goods = all_goods;
	}

	public Integer getAll_store() {
		return this.all_store;
	}

	public void setAll_store(Integer all_store) {
		this.all_store = all_store;
	}

	public Integer getAll_user() {
		return this.all_user;
	}

	public void setAll_user(Integer all_user) {
		this.all_user = all_user;
	}

	public Date getNext_time() {
		return this.next_time;
	}

	public void setNext_time(Date next_time) {
		this.next_time = next_time;
	}

	public BigDecimal getOrder_amount() {
		return this.order_amount;
	}

	public void setOrder_amount(BigDecimal order_amount) {
		this.order_amount = order_amount;
	}

	public Integer getStore_update() {
		return this.store_update;
	}

	public void setStore_update(Integer store_update) {
		this.store_update = store_update;
	}

	public Integer getWeek_complaint() {
		return this.week_complaint;
	}

	public void setWeek_complaint(Integer week_complaint) {
		this.week_complaint = week_complaint;
	}

	public Integer getWeek_goods() {
		return this.week_goods;
	}

	public void setWeek_goods(Integer week_goods) {
		this.week_goods = week_goods;
	}

	public Integer getWeek_order() {
		return this.week_order;
	}

	public void setWeek_order(Integer week_order) {
		this.week_order = week_order;
	}

	public Integer getWeek_report() {
		return this.week_report;
	}

	public void setWeek_report(Integer week_report) {
		this.week_report = week_report;
	}

	public Integer getWeek_store() {
		return this.week_store;
	}

	public void setWeek_store(Integer week_store) {
		this.week_store = week_store;
	}

	public Integer getWeek_user() {
		return this.week_user;
	}

	public void setWeek_user(Integer week_user) {
		this.week_user = week_user;
	}

}
