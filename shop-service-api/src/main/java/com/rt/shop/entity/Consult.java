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
@TableName(value = "shopping_consult")
public class Consult implements Serializable {

	@TableField(exist = false)
	private Goods goods;
	@TableField(exist = false)
	private User consult_user;
	
	//回复用户
	@TableField(exist = false)
	private User reply_user;
	
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public User getConsult_user() {
		return consult_user;
	}

	public void setConsult_user(User consult_user) {
		this.consult_user = consult_user;
	}

	public User getReply_user() {
		return reply_user;
	}

	public void setReply_user(User reply_user) {
		this.reply_user = reply_user;
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
	@TableField(value = "consult_content")
	private String consult_content;

	/**  */
	@TableField(value = "consult_email")
	private String consult_email;

	/**  */
	@TableField(value = "consult_reply")
	private String consult_reply;

	/**  */
	private Boolean reply;

	/**  */
	@TableField(value = "reply_time")
	private Date reply_time;

	/**  */
	@TableField(value = "consult_user_id")
	private Long consult_user_id;

	/**  */
	@TableField(value = "goods_id")
	private Long goods_id;

	/**  */
	@TableField(value = "reply_user_id")
	private Long reply_user_id;

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

	public String getConsult_content() {
		return this.consult_content;
	}

	public void setConsult_content(String consult_content) {
		this.consult_content = consult_content;
	}

	public String getConsult_email() {
		return this.consult_email;
	}

	public void setConsult_email(String consult_email) {
		this.consult_email = consult_email;
	}

	public String getConsult_reply() {
		return this.consult_reply;
	}

	public void setConsult_reply(String consult_reply) {
		this.consult_reply = consult_reply;
	}

	public Boolean getReply() {
		return this.reply;
	}

	public void setReply(Boolean reply) {
		this.reply = reply;
	}

	public Date getReply_time() {
		return this.reply_time;
	}

	public void setReply_time(Date reply_time) {
		this.reply_time = reply_time;
	}

	public Long getConsult_user_id() {
		return this.consult_user_id;
	}

	public void setConsult_user_id(Long consult_user_id) {
		this.consult_user_id = consult_user_id;
	}

	public Long getGoods_id() {
		return this.goods_id;
	}

	public void setGoods_id(Long goods_id) {
		this.goods_id = goods_id;
	}

	public Long getReply_user_id() {
		return this.reply_user_id;
	}

	public void setReply_user_id(Long reply_user_id) {
		this.reply_user_id = reply_user_id;
	}

}
