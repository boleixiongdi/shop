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
@TableName(value = "shopping_transport")
public class Transport implements Serializable {

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
	@TableField(value = "trans_ems")
	private Boolean trans_ems;

	/**  */
	@TableField(value = "trans_ems_info")
	private String trans_ems_info;

	/**  */
	@TableField(value = "trans_express")
	private Boolean trans_express;

	/**  */
	@TableField(value = "trans_express_info")
	private String trans_express_info;

	/**  */
	@TableField(value = "trans_mail")
	private Boolean trans_mail;

	/**  */
	@TableField(value = "trans_mail_info")
	private String trans_mail_info;

	/**  */
	@TableField(value = "trans_name")
	private String trans_name;

	/**  */
	@TableField(value = "store_id")
	private Long store_id;

	/**  */
	@TableField(value = "trans_time")
	private Integer trans_time;

	/**  */
	@TableField(value = "trans_type")
	private Integer trans_type;

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

	public Boolean getTrans_ems() {
		return this.trans_ems;
	}

	public void setTrans_ems(Boolean trans_ems) {
		this.trans_ems = trans_ems;
	}

	public String getTrans_ems_info() {
		return this.trans_ems_info;
	}

	public void setTrans_ems_info(String trans_ems_info) {
		this.trans_ems_info = trans_ems_info;
	}

	public Boolean getTrans_express() {
		return this.trans_express;
	}

	public void setTrans_express(Boolean trans_express) {
		this.trans_express = trans_express;
	}

	public String getTrans_express_info() {
		return this.trans_express_info;
	}

	public void setTrans_express_info(String trans_express_info) {
		this.trans_express_info = trans_express_info;
	}

	public Boolean getTrans_mail() {
		return this.trans_mail;
	}

	public void setTrans_mail(Boolean trans_mail) {
		this.trans_mail = trans_mail;
	}

	public String getTrans_mail_info() {
		return this.trans_mail_info;
	}

	public void setTrans_mail_info(String trans_mail_info) {
		this.trans_mail_info = trans_mail_info;
	}

	public String getTrans_name() {
		return this.trans_name;
	}

	public void setTrans_name(String trans_name) {
		this.trans_name = trans_name;
	}

	public Long getStore_id() {
		return this.store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public Integer getTrans_time() {
		return this.trans_time;
	}

	public void setTrans_time(Integer trans_time) {
		this.trans_time = trans_time;
	}

	public Integer getTrans_type() {
		return this.trans_type;
	}

	public void setTrans_type(Integer trans_type) {
		this.trans_type = trans_type;
	}

}
