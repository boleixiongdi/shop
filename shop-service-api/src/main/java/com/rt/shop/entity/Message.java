package com.rt.shop.entity;

import java.io.Serializable;
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
@TableName(value = "shopping_message")
public class Message implements Serializable {

	//来自哪个用户
	@TableField(exist = false)
	   private User fromUser;
	   
	   //抵达哪个用户
	   @TableField(exist = false)
	   private User toUser;
	   @TableField(exist = false)
	   private Message parent;
	   //回复信息
	   @TableField(exist = false)
	   List<Message> replys = new ArrayList();
	   
	   
	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public Message getParent() {
		return parent;
	}

	public void setParent(Message parent) {
		this.parent = parent;
	}

	public List<Message> getReplys() {
		return replys;
	}

	public void setReplys(List<Message> replys) {
		this.replys = replys;
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
	private Integer status;

	/**  */
	private String title;

	/**  */
	private Integer type;

	/**  */
	@TableField(value = "fromUser_id")
	private Long fromUser_id;

	/**  */
	@TableField(value = "parent_id")
	private Long parent_id;

	/**  */
	@TableField(value = "toUser_id")
	private Long toUser_id;

	/**  */
	@TableField(value = "reply_status")
	private Integer reply_status;

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

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getFromUser_id() {
		return this.fromUser_id;
	}

	public void setFromUser_id(Long fromUser_id) {
		this.fromUser_id = fromUser_id;
	}

	public Long getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public Long getToUser_id() {
		return this.toUser_id;
	}

	public void setToUser_id(Long toUser_id) {
		this.toUser_id = toUser_id;
	}

	public Integer getReply_status() {
		return this.reply_status;
	}

	public void setReply_status(Integer reply_status) {
		this.reply_status = reply_status;
	}

}
