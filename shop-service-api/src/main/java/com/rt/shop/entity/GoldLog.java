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
@TableName(value = "shopping_gold_log")
public class GoldLog implements Serializable {
	@TableField(exist = false)
	private User gl_user;
	
	//管理
	@TableField(exist = false)
	private User gl_admin;
	
	
	
	//金币记录
	@TableField(exist = false)
	private GoldRecord gr;
	
	public User getGl_user() {
		return gl_user;
	}

	public void setGl_user(User gl_user) {
		this.gl_user = gl_user;
	}

	public User getGl_admin() {
		return gl_admin;
	}

	public void setGl_admin(User gl_admin) {
		this.gl_admin = gl_admin;
	}

	public GoldRecord getGr() {
		return gr;
	}

	public void setGr(GoldRecord gr) {
		this.gr = gr;
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
	@TableField(value = "gl_admin_content")
	private String gl_admin_content;

	/**  */
	@TableField(value = "gl_admin_time")
	private Date gl_admin_time;

	/**  */
	@TableField(value = "gl_content")
	private String gl_content;

	/**  */
	@TableField(value = "gl_count")
	private Integer gl_count;

	/**  */
	@TableField(value = "gl_money")
	private Integer gl_money;

	/**  */
	@TableField(value = "gl_payment")
	private String gl_payment;

	/**  */
	@TableField(value = "gl_type")
	private Integer gl_type;

	/**  */
	@TableField(value = "gl_admin_id")
	private Long gl_admin_id;

	/**  */
	@TableField(value = "gl_user_id")
	private Long gl_user_id;

	/**  */
	@TableField(value = "gr_id")
	private Long gr_id;

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

	public String getGl_admin_content() {
		return this.gl_admin_content;
	}

	public void setGl_admin_content(String gl_admin_content) {
		this.gl_admin_content = gl_admin_content;
	}

	public Date getGl_admin_time() {
		return this.gl_admin_time;
	}

	public void setGl_admin_time(Date gl_admin_time) {
		this.gl_admin_time = gl_admin_time;
	}

	public String getGl_content() {
		return this.gl_content;
	}

	public void setGl_content(String gl_content) {
		this.gl_content = gl_content;
	}

	public Integer getGl_count() {
		return this.gl_count;
	}

	public void setGl_count(Integer gl_count) {
		this.gl_count = gl_count;
	}

	public Integer getGl_money() {
		return this.gl_money;
	}

	public void setGl_money(Integer gl_money) {
		this.gl_money = gl_money;
	}

	public String getGl_payment() {
		return this.gl_payment;
	}

	public void setGl_payment(String gl_payment) {
		this.gl_payment = gl_payment;
	}

	public Integer getGl_type() {
		return this.gl_type;
	}

	public void setGl_type(Integer gl_type) {
		this.gl_type = gl_type;
	}

	public Long getGl_admin_id() {
		return this.gl_admin_id;
	}

	public void setGl_admin_id(Long gl_admin_id) {
		this.gl_admin_id = gl_admin_id;
	}

	public Long getGl_user_id() {
		return this.gl_user_id;
	}

	public void setGl_user_id(Long gl_user_id) {
		this.gl_user_id = gl_user_id;
	}

	public Long getGr_id() {
		return this.gr_id;
	}

	public void setGr_id(Long gr_id) {
		this.gr_id = gr_id;
	}

}
