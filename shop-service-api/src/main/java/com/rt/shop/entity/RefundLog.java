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
@TableName(value = "shopping_refund_log")
public class RefundLog implements Serializable {

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
	private BigDecimal refund;

	/**  */
	@TableField(value = "refund_id")
	private String refund_id;

	/**  */
	@TableField(value = "refund_log")
	private String refund_log;

	/**  */
	@TableField(value = "refund_type")
	private String refund_type;

	/**  */
	@TableField(value = "of_id")
	private Long of_id;

	/**  */
	@TableField(value = "refund_user_id")
	private Long refund_user_id;

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

	public BigDecimal getRefund() {
		return this.refund;
	}

	public void setRefund(BigDecimal refund) {
		this.refund = refund;
	}

	public String getRefund_id() {
		return this.refund_id;
	}

	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}

	public String getRefund_log() {
		return this.refund_log;
	}

	public void setRefund_log(String refund_log) {
		this.refund_log = refund_log;
	}

	public String getRefund_type() {
		return this.refund_type;
	}

	public void setRefund_type(String refund_type) {
		this.refund_type = refund_type;
	}

	public Long getOf_id() {
		return this.of_id;
	}

	public void setOf_id(Long of_id) {
		this.of_id = of_id;
	}

	public Long getRefund_user_id() {
		return this.refund_user_id;
	}

	public void setRefund_user_id(Long refund_user_id) {
		this.refund_user_id = refund_user_id;
	}

}
