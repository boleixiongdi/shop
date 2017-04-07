package com.rt.shop.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName(value = "shopping_integral_goodsorder")
public class IntegralGoodsOrder implements Serializable {
	@TableField(exist = false)
	private Address igo_addr;
	   //用户 
	@TableField(exist = false)
	   private User igo_user;
	   //商品运输集合
	   @TableField(exist = false)
	   private List<IntegralGoodsCart> igo_gcs = new ArrayList();
	   
	public Address getIgo_addr() {
		return igo_addr;
	}

	public void setIgo_addr(Address igo_addr) {
		this.igo_addr = igo_addr;
	}

	public User getIgo_user() {
		return igo_user;
	}

	public void setIgo_user(User igo_user) {
		this.igo_user = igo_user;
	}

	public List<IntegralGoodsCart> getIgo_gcs() {
		return igo_gcs;
	}

	public void setIgo_gcs(List<IntegralGoodsCart> igo_gcs) {
		this.igo_gcs = igo_gcs;
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
	@TableField(value = "igo_msg")
	private String igo_msg;

	/**  */
	@TableField(value = "igo_order_sn")
	private String igo_order_sn;

	/**  */
	@TableField(value = "igo_pay_msg")
	private String igo_pay_msg;

	/**  */
	@TableField(value = "igo_pay_time")
	private Date igo_pay_time;

	/**  */
	@TableField(value = "igo_payment")
	private String igo_payment;

	/**  */
	@TableField(value = "igo_ship_code")
	private String igo_ship_code;

	/**  */
	@TableField(value = "igo_ship_content")
	private String igo_ship_content;

	/**  */
	@TableField(value = "igo_ship_time")
	private Date igo_ship_time;

	/**  */
	@TableField(value = "igo_status")
	private Integer igo_status;

	/**  */
	@TableField(value = "igo_total_integral")
	private Integer igo_total_integral;

	/**  */
	@TableField(value = "igo_trans_fee")
	private BigDecimal igo_trans_fee;

	/**  */
	@TableField(value = "igo_addr_id")
	private Long igo_addr_id;

	/**  */
	@TableField(value = "igo_user_id")
	private Long igo_user_id;

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

	public String getIgo_msg() {
		return this.igo_msg;
	}

	public void setIgo_msg(String igo_msg) {
		this.igo_msg = igo_msg;
	}

	public String getIgo_order_sn() {
		return this.igo_order_sn;
	}

	public void setIgo_order_sn(String igo_order_sn) {
		this.igo_order_sn = igo_order_sn;
	}

	public String getIgo_pay_msg() {
		return this.igo_pay_msg;
	}

	public void setIgo_pay_msg(String igo_pay_msg) {
		this.igo_pay_msg = igo_pay_msg;
	}

	public Date getIgo_pay_time() {
		return this.igo_pay_time;
	}

	public void setIgo_pay_time(Date igo_pay_time) {
		this.igo_pay_time = igo_pay_time;
	}

	public String getIgo_payment() {
		return this.igo_payment;
	}

	public void setIgo_payment(String igo_payment) {
		this.igo_payment = igo_payment;
	}

	public String getIgo_ship_code() {
		return this.igo_ship_code;
	}

	public void setIgo_ship_code(String igo_ship_code) {
		this.igo_ship_code = igo_ship_code;
	}

	public String getIgo_ship_content() {
		return this.igo_ship_content;
	}

	public void setIgo_ship_content(String igo_ship_content) {
		this.igo_ship_content = igo_ship_content;
	}

	public Date getIgo_ship_time() {
		return this.igo_ship_time;
	}

	public void setIgo_ship_time(Date igo_ship_time) {
		this.igo_ship_time = igo_ship_time;
	}

	public Integer getIgo_status() {
		return this.igo_status;
	}

	public void setIgo_status(Integer igo_status) {
		this.igo_status = igo_status;
	}

	public Integer getIgo_total_integral() {
		return this.igo_total_integral;
	}

	public void setIgo_total_integral(Integer igo_total_integral) {
		this.igo_total_integral = igo_total_integral;
	}

	public BigDecimal getIgo_trans_fee() {
		return this.igo_trans_fee;
	}

	public void setIgo_trans_fee(BigDecimal igo_trans_fee) {
		this.igo_trans_fee = igo_trans_fee;
	}

	public Long getIgo_addr_id() {
		return this.igo_addr_id;
	}

	public void setIgo_addr_id(Long igo_addr_id) {
		this.igo_addr_id = igo_addr_id;
	}

	public Long getIgo_user_id() {
		return this.igo_user_id;
	}

	public void setIgo_user_id(Long igo_user_id) {
		this.igo_user_id = igo_user_id;
	}

}
