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
@TableName(value = "shopping_chatting")
public class Chatting implements Serializable {

private User user1;
	
	//用户
@TableField(exist = false)
	private User user2;
	
	
	//日志
	@TableField(exist = false)
	private List<ChattingLog> logs = new ArrayList<ChattingLog>();
	
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	public User getUser1() {
		return user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public List<ChattingLog> getLogs() {
		return logs;
	}

	public void setLogs(List<ChattingLog> logs) {
		this.logs = logs;
	}

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
	@TableField(value = "user1_id")
	private Long user1_id;

	/**  */
	@TableField(value = "user2_id")
	private Long user2_id;

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

	public Long getUser1_id() {
		return this.user1_id;
	}

	public void setUser1_id(Long user1_id) {
		this.user1_id = user1_id;
	}

	public Long getUser2_id() {
		return this.user2_id;
	}

	public void setUser2_id(Long user2_id) {
		this.user2_id = user2_id;
	}

}
