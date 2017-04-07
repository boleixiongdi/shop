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
@TableName(value = "shopping_chattingfriend")
public class ChattingFriend implements Serializable {
	@TableField(exist = false)
	private User user;
	
	//朋友
	@TableField(exist = false)
	private User friendUser;
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getFriendUser() {
		return friendUser;
	}

	public void setFriendUser(User friendUser) {
		this.friendUser = friendUser;
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
	private Integer type;

	/**  */
	@TableField(value = "friend_id")
	private Long friend_id;

	/**  */
	@TableField(value = "user_id")
	private Long user_id;

	/**  */
	@TableField(value = "friendUser_id")
	private Long friendUser_id;

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

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getFriend_id() {
		return this.friend_id;
	}

	public void setFriend_id(Long friend_id) {
		this.friend_id = friend_id;
	}

	public Long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getFriendUser_id() {
		return this.friendUser_id;
	}

	public void setFriendUser_id(Long friendUser_id) {
		this.friendUser_id = friendUser_id;
	}

}
