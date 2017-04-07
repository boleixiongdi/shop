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
@TableName(value = "shopping_predeposit_cash")
public class PredepositCash implements Serializable {

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
	@TableField(value = "cash_account")
	private String cash_account;

	/**  */
	@TableField(value = "cash_admin_info")
	private String cash_admin_info;

	/**  */
	@TableField(value = "cash_amount")
	private BigDecimal cash_amount;

	/**  */
	@TableField(value = "cash_bank")
	private String cash_bank;

	/**  */
	@TableField(value = "cash_info")
	private String cash_info;

	/**  */
	@TableField(value = "cash_pay_status")
	private Integer cash_pay_status;

	/**  */
	@TableField(value = "cash_payment")
	private String cash_payment;

	/**  */
	@TableField(value = "cash_sn")
	private String cash_sn;

	/**  */
	@TableField(value = "cash_status")
	private Integer cash_status;

	/**  */
	@TableField(value = "cash_userName")
	private String cash_userName;

	/**  */
	@TableField(value = "cash_admin_id")
	private Long cash_admin_id;

	/**  */
	@TableField(value = "cash_user_id")
	private Long cash_user_id;

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

	public String getCash_account() {
		return this.cash_account;
	}

	public void setCash_account(String cash_account) {
		this.cash_account = cash_account;
	}

	public String getCash_admin_info() {
		return this.cash_admin_info;
	}

	public void setCash_admin_info(String cash_admin_info) {
		this.cash_admin_info = cash_admin_info;
	}

	public BigDecimal getCash_amount() {
		return this.cash_amount;
	}

	public void setCash_amount(BigDecimal cash_amount) {
		this.cash_amount = cash_amount;
	}

	public String getCash_bank() {
		return this.cash_bank;
	}

	public void setCash_bank(String cash_bank) {
		this.cash_bank = cash_bank;
	}

	public String getCash_info() {
		return this.cash_info;
	}

	public void setCash_info(String cash_info) {
		this.cash_info = cash_info;
	}

	public Integer getCash_pay_status() {
		return this.cash_pay_status;
	}

	public void setCash_pay_status(Integer cash_pay_status) {
		this.cash_pay_status = cash_pay_status;
	}

	public String getCash_payment() {
		return this.cash_payment;
	}

	public void setCash_payment(String cash_payment) {
		this.cash_payment = cash_payment;
	}

	public String getCash_sn() {
		return this.cash_sn;
	}

	public void setCash_sn(String cash_sn) {
		this.cash_sn = cash_sn;
	}

	public Integer getCash_status() {
		return this.cash_status;
	}

	public void setCash_status(Integer cash_status) {
		this.cash_status = cash_status;
	}

	public String getCash_userName() {
		return this.cash_userName;
	}

	public void setCash_userName(String cash_userName) {
		this.cash_userName = cash_userName;
	}

	public Long getCash_admin_id() {
		return this.cash_admin_id;
	}

	public void setCash_admin_id(Long cash_admin_id) {
		this.cash_admin_id = cash_admin_id;
	}

	public Long getCash_user_id() {
		return this.cash_user_id;
	}

	public void setCash_user_id(Long cash_user_id) {
		this.cash_user_id = cash_user_id;
	}

}
