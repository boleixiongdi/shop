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
@TableName(value = "shopping_vmessage")
public class Vmessage implements Serializable {

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
	@TableField(value = "store_id")
	private Long store_id;

	/**  */
	private String FromUserName;

	/**  */
	private String MsgType;

	/**  */
	@TableField(value = "json_map")
	private String json_map;

	/**  */
	private String reply;

	/**  */
	private Integer status;

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

	public Long getStore_id() {
		return this.store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public String getFromUserName() {
		return this.FromUserName;
	}

	public void setFromUserName(String FromUserName) {
		this.FromUserName = FromUserName;
	}

	public String getMsgType() {
		return this.MsgType;
	}

	public void setMsgType(String MsgType) {
		this.MsgType = MsgType;
	}

	public String getJson_map() {
		return this.json_map;
	}

	public void setJson_map(String json_map) {
		this.json_map = json_map;
	}

	public String getReply() {
		return this.reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
