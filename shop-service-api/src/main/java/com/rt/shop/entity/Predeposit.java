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
@TableName(value = "shopping_predeposit")
public class Predeposit implements Serializable {

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
	@TableField(value = "pd_admin_info")
	private String pd_admin_info;

	/**  */
	@TableField(value = "pd_amount")
	private BigDecimal pd_amount;

	/**  */
	@TableField(value = "pd_pay_status")
	private Integer pd_pay_status;

	/**  */
	@TableField(value = "pd_payment")
	private String pd_payment;

	/**  */
	@TableField(value = "pd_remittance_bank")
	private String pd_remittance_bank;

	/**  */
	@TableField(value = "pd_remittance_info")
	private String pd_remittance_info;

	/**  */
	@TableField(value = "pd_remittance_time")
	private Date pd_remittance_time;

	/**  */
	@TableField(value = "pd_remittance_user")
	private String pd_remittance_user;

	/**  */
	@TableField(value = "pd_sn")
	private String pd_sn;

	/**  */
	@TableField(value = "pd_status")
	private Integer pd_status;

	/**  */
	@TableField(value = "pd_admin_id")
	private Long pd_admin_id;

	/**  */
	@TableField(value = "pd_user_id")
	private Long pd_user_id;

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

	public String getPd_admin_info() {
		return this.pd_admin_info;
	}

	public void setPd_admin_info(String pd_admin_info) {
		this.pd_admin_info = pd_admin_info;
	}

	public BigDecimal getPd_amount() {
		return this.pd_amount;
	}

	public void setPd_amount(BigDecimal pd_amount) {
		this.pd_amount = pd_amount;
	}

	public Integer getPd_pay_status() {
		return this.pd_pay_status;
	}

	public void setPd_pay_status(Integer pd_pay_status) {
		this.pd_pay_status = pd_pay_status;
	}

	public String getPd_payment() {
		return this.pd_payment;
	}

	public void setPd_payment(String pd_payment) {
		this.pd_payment = pd_payment;
	}

	public String getPd_remittance_bank() {
		return this.pd_remittance_bank;
	}

	public void setPd_remittance_bank(String pd_remittance_bank) {
		this.pd_remittance_bank = pd_remittance_bank;
	}

	public String getPd_remittance_info() {
		return this.pd_remittance_info;
	}

	public void setPd_remittance_info(String pd_remittance_info) {
		this.pd_remittance_info = pd_remittance_info;
	}

	public Date getPd_remittance_time() {
		return this.pd_remittance_time;
	}

	public void setPd_remittance_time(Date pd_remittance_time) {
		this.pd_remittance_time = pd_remittance_time;
	}

	public String getPd_remittance_user() {
		return this.pd_remittance_user;
	}

	public void setPd_remittance_user(String pd_remittance_user) {
		this.pd_remittance_user = pd_remittance_user;
	}

	public String getPd_sn() {
		return this.pd_sn;
	}

	public void setPd_sn(String pd_sn) {
		this.pd_sn = pd_sn;
	}

	public Integer getPd_status() {
		return this.pd_status;
	}

	public void setPd_status(Integer pd_status) {
		this.pd_status = pd_status;
	}

	public Long getPd_admin_id() {
		return this.pd_admin_id;
	}

	public void setPd_admin_id(Long pd_admin_id) {
		this.pd_admin_id = pd_admin_id;
	}

	public Long getPd_user_id() {
		return this.pd_user_id;
	}

	public void setPd_user_id(Long pd_user_id) {
		this.pd_user_id = pd_user_id;
	}

}
