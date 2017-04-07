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
@TableName(value = "shopping_order_log")
public class OrderLog implements Serializable {
	@TableField(exist = false)
	 private OrderForm of;
	
	public OrderForm getOf() {
		return of;
	}

	public void setOf(OrderForm of) {
		this.of = of;
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
	@TableField(value = "log_info")
	private String log_info;

	/**  */
	@TableField(value = "state_info")
	private String state_info;

	/**  */
	@TableField(value = "log_user_id")
	private Long log_user_id;

	/**  */
	@TableField(value = "of_id")
	private Long of_id;

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

	public String getLog_info() {
		return this.log_info;
	}

	public void setLog_info(String log_info) {
		this.log_info = log_info;
	}

	public String getState_info() {
		return this.state_info;
	}

	public void setState_info(String state_info) {
		this.state_info = state_info;
	}

	public Long getLog_user_id() {
		return this.log_user_id;
	}

	public void setLog_user_id(Long log_user_id) {
		this.log_user_id = log_user_id;
	}

	public Long getOf_id() {
		return this.of_id;
	}

	public void setOf_id(Long of_id) {
		this.of_id = of_id;
	}

}
