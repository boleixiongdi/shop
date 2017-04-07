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
@TableName(value = "shopping_predeposit_log")
public class PredepositLog implements Serializable {

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
	@TableField(value = "pd_log_amount")
	private BigDecimal pd_log_amount;

	/**  */
	@TableField(value = "pd_log_info")
	private String pd_log_info;

	/**  */
	@TableField(value = "pd_op_type")
	private String pd_op_type;

	/**  */
	@TableField(value = "pd_type")
	private String pd_type;

	/**  */
	@TableField(value = "pd_log_admin_id")
	private Long pd_log_admin_id;

	/**  */
	@TableField(value = "pd_log_user_id")
	private Long pd_log_user_id;

	/**  */
	@TableField(value = "predeposit_id")
	private Long predeposit_id;

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

	public BigDecimal getPd_log_amount() {
		return this.pd_log_amount;
	}

	public void setPd_log_amount(BigDecimal pd_log_amount) {
		this.pd_log_amount = pd_log_amount;
	}

	public String getPd_log_info() {
		return this.pd_log_info;
	}

	public void setPd_log_info(String pd_log_info) {
		this.pd_log_info = pd_log_info;
	}

	public String getPd_op_type() {
		return this.pd_op_type;
	}

	public void setPd_op_type(String pd_op_type) {
		this.pd_op_type = pd_op_type;
	}

	public String getPd_type() {
		return this.pd_type;
	}

	public void setPd_type(String pd_type) {
		this.pd_type = pd_type;
	}

	public Long getPd_log_admin_id() {
		return this.pd_log_admin_id;
	}

	public void setPd_log_admin_id(Long pd_log_admin_id) {
		this.pd_log_admin_id = pd_log_admin_id;
	}

	public Long getPd_log_user_id() {
		return this.pd_log_user_id;
	}

	public void setPd_log_user_id(Long pd_log_user_id) {
		this.pd_log_user_id = pd_log_user_id;
	}

	public Long getPredeposit_id() {
		return this.predeposit_id;
	}

	public void setPredeposit_id(Long predeposit_id) {
		this.predeposit_id = predeposit_id;
	}

}
