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
@TableName(value = "shopping_chattinglog")
public class ChattingLog implements Serializable {
	@TableField(exist = false)
	private Chatting chatting;
	
	//用户
	@TableField(exist = false)
	private User user;
	
	
	public Chatting getChatting() {
		return chatting;
	}

	public void setChatting(Chatting chatting) {
		this.chatting = chatting;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	@TableField(value = "User_id")
	private Long User_id;

	/**  */
	@TableField(value = "chatting_id")
	private Long chatting_id;

	/**  */
	private Integer mark;

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

	public Long getUser_id() {
		return this.User_id;
	}

	public void setUser_id(Long User_id) {
		this.User_id = User_id;
	}

	public Long getChatting_id() {
		return this.chatting_id;
	}

	public void setChatting_id(Long chatting_id) {
		this.chatting_id = chatting_id;
	}

	public Integer getMark() {
		return this.mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

}
