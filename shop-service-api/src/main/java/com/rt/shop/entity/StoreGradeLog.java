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
@TableName(value = "shopping_storegrade_log")
public class StoreGradeLog implements Serializable {

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
	@TableField(value = "log_edit")
	private Boolean log_edit;

	/**  */
	@TableField(value = "store_grade_info")
	private String store_grade_info;

	/**  */
	@TableField(value = "store_grade_status")
	private Integer store_grade_status;

	/**  */
	@TableField(value = "store_id")
	private Long store_id;

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

	public Boolean getLog_edit() {
		return this.log_edit;
	}

	public void setLog_edit(Boolean log_edit) {
		this.log_edit = log_edit;
	}

	public String getStore_grade_info() {
		return this.store_grade_info;
	}

	public void setStore_grade_info(String store_grade_info) {
		this.store_grade_info = store_grade_info;
	}

	public Integer getStore_grade_status() {
		return this.store_grade_status;
	}

	public void setStore_grade_status(Integer store_grade_status) {
		this.store_grade_status = store_grade_status;
	}

	public Long getStore_id() {
		return this.store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

}
