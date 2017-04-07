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
@TableName(value = "shopping_home_addention")
public class HomeAddention implements Serializable {

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
	@TableField(value = "attention_homepage_id")
	private Long attention_homepage_id;

	/**  */
	@TableField(value = "attentioned_id")
	private Long attentioned_id;

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

	public Long getAttention_homepage_id() {
		return this.attention_homepage_id;
	}

	public void setAttention_homepage_id(Long attention_homepage_id) {
		this.attention_homepage_id = attention_homepage_id;
	}

	public Long getAttentioned_id() {
		return this.attentioned_id;
	}

	public void setAttentioned_id(Long attentioned_id) {
		this.attentioned_id = attentioned_id;
	}

}
