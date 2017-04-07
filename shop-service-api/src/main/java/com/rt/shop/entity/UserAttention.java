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
@TableName(value = "shopping_user_attention")
public class UserAttention implements Serializable {

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
	@TableField(value = "fromUser_id")
	private Long fromUser_id;

	/**  */
	@TableField(value = "toUser_id")
	private Long toUser_id;

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

	public Long getFromUser_id() {
		return this.fromUser_id;
	}

	public void setFromUser_id(Long fromUser_id) {
		this.fromUser_id = fromUser_id;
	}

	public Long getToUser_id() {
		return this.toUser_id;
	}

	public void setToUser_id(Long toUser_id) {
		this.toUser_id = toUser_id;
	}

}
