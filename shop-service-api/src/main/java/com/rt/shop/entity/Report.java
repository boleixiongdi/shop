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
@TableName(value = "shopping_report")
public class Report implements Serializable {

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
	private String content;

	/**  */
	@TableField(value = "handle_Time")
	private Date handle_Time;

	/**  */
	@TableField(value = "handle_info")
	private String handle_info;

	/**  */
	private Integer result;

	/**  */
	private Integer status;

	/**  */
	@TableField(value = "acc1_id")
	private Long acc1_id;

	/**  */
	@TableField(value = "acc2_id")
	private Long acc2_id;

	/**  */
	@TableField(value = "acc3_id")
	private Long acc3_id;

	/**  */
	@TableField(value = "goods_id")
	private Long goods_id;

	/**  */
	@TableField(value = "subject_id")
	private Long subject_id;

	/**  */
	@TableField(value = "user_id")
	private Long user_id;

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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getHandle_Time() {
		return this.handle_Time;
	}

	public void setHandle_Time(Date handle_Time) {
		this.handle_Time = handle_Time;
	}

	public String getHandle_info() {
		return this.handle_info;
	}

	public void setHandle_info(String handle_info) {
		this.handle_info = handle_info;
	}

	public Integer getResult() {
		return this.result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getAcc1_id() {
		return this.acc1_id;
	}

	public void setAcc1_id(Long acc1_id) {
		this.acc1_id = acc1_id;
	}

	public Long getAcc2_id() {
		return this.acc2_id;
	}

	public void setAcc2_id(Long acc2_id) {
		this.acc2_id = acc2_id;
	}

	public Long getAcc3_id() {
		return this.acc3_id;
	}

	public void setAcc3_id(Long acc3_id) {
		this.acc3_id = acc3_id;
	}

	public Long getGoods_id() {
		return this.goods_id;
	}

	public void setGoods_id(Long goods_id) {
		this.goods_id = goods_id;
	}

	public Long getSubject_id() {
		return this.subject_id;
	}

	public void setSubject_id(Long subject_id) {
		this.subject_id = subject_id;
	}

	public Long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

}
