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
@TableName(value = "shopping_integrallog")
public class IntegralLog implements Serializable {
	@TableField(exist = false)
	 private User integral_user;
	   
	   //操作者
	 @TableField(exist = false)
	   private User operate_user;
	   
	public User getIntegral_user() {
		return integral_user;
	}

	public void setIntegral_user(User integral_user) {
		this.integral_user = integral_user;
	}

	public User getOperate_user() {
		return operate_user;
	}

	public void setOperate_user(User operate_user) {
		this.operate_user = operate_user;
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
	private String content;

	/**  */
	private Integer integral;

	/**  */
	private String type;

	/**  */
	@TableField(value = "integral_user_id")
	private Long integral_user_id;

	/**  */
	@TableField(value = "operate_user_id")
	private Long operate_user_id;

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

	public Integer getIntegral() {
		return this.integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getIntegral_user_id() {
		return this.integral_user_id;
	}

	public void setIntegral_user_id(Long integral_user_id) {
		this.integral_user_id = integral_user_id;
	}

	public Long getOperate_user_id() {
		return this.operate_user_id;
	}

	public void setOperate_user_id(Long operate_user_id) {
		this.operate_user_id = operate_user_id;
	}

}
