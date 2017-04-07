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
@TableName(value = "shopping_gold_record")
public class GoldRecord implements Serializable {
	@TableField(exist = false)
	private GoldLog log;
	@TableField(exist = false)
	private User gold_admin;
	@TableField(exist = false)
	private User gold_user;
	
	public GoldLog getLog() {
		return log;
	}

	public void setLog(GoldLog log) {
		this.log = log;
	}

	public User getGold_admin() {
		return gold_admin;
	}

	public void setGold_admin(User gold_admin) {
		this.gold_admin = gold_admin;
	}

	public User getGold_user() {
		return gold_user;
	}

	public void setGold_user(User gold_user) {
		this.gold_user = gold_user;
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
	@TableField(value = "gold_admin_info")
	private String gold_admin_info;

	/**  */
	@TableField(value = "gold_admin_time")
	private Date gold_admin_time;

	/**  */
	@TableField(value = "gold_count")
	private Integer gold_count;

	/**  */
	@TableField(value = "gold_exchange_info")
	private String gold_exchange_info;

	/**  */
	@TableField(value = "gold_money")
	private Integer gold_money;

	/**  */
	@TableField(value = "gold_pay_status")
	private Integer gold_pay_status;

	/**  */
	@TableField(value = "gold_payment")
	private String gold_payment;

	/**  */
	@TableField(value = "gold_sn")
	private String gold_sn;

	/**  */
	@TableField(value = "gold_status")
	private Integer gold_status;

	/**  */
	@TableField(value = "gold_admin_id")
	private Long gold_admin_id;

	/**  */
	@TableField(value = "gold_user_id")
	private Long gold_user_id;

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

	public String getGold_admin_info() {
		return this.gold_admin_info;
	}

	public void setGold_admin_info(String gold_admin_info) {
		this.gold_admin_info = gold_admin_info;
	}

	public Date getGold_admin_time() {
		return this.gold_admin_time;
	}

	public void setGold_admin_time(Date gold_admin_time) {
		this.gold_admin_time = gold_admin_time;
	}

	public Integer getGold_count() {
		return this.gold_count;
	}

	public void setGold_count(Integer gold_count) {
		this.gold_count = gold_count;
	}

	public String getGold_exchange_info() {
		return this.gold_exchange_info;
	}

	public void setGold_exchange_info(String gold_exchange_info) {
		this.gold_exchange_info = gold_exchange_info;
	}

	public Integer getGold_money() {
		return this.gold_money;
	}

	public void setGold_money(Integer gold_money) {
		this.gold_money = gold_money;
	}

	public Integer getGold_pay_status() {
		return this.gold_pay_status;
	}

	public void setGold_pay_status(Integer gold_pay_status) {
		this.gold_pay_status = gold_pay_status;
	}

	public String getGold_payment() {
		return this.gold_payment;
	}

	public void setGold_payment(String gold_payment) {
		this.gold_payment = gold_payment;
	}

	public String getGold_sn() {
		return this.gold_sn;
	}

	public void setGold_sn(String gold_sn) {
		this.gold_sn = gold_sn;
	}

	public Integer getGold_status() {
		return this.gold_status;
	}

	public void setGold_status(Integer gold_status) {
		this.gold_status = gold_status;
	}

	public Long getGold_admin_id() {
		return this.gold_admin_id;
	}

	public void setGold_admin_id(Long gold_admin_id) {
		this.gold_admin_id = gold_admin_id;
	}

	public Long getGold_user_id() {
		return this.gold_user_id;
	}

	public void setGold_user_id(Long gold_user_id) {
		this.gold_user_id = gold_user_id;
	}

}
