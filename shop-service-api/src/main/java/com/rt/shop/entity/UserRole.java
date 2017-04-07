package com.rt.shop.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName(value = "shopping_user_role")
public class UserRole implements Serializable {
		public UserRole(){}
		public UserRole(Long user_id,Long role_id){
			this.user_id=user_id;
			this.role_id=role_id;
		}
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;
	/**  */
	
	private Long user_id;

	/**  */
	
	
	private Long role_id;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getRole_id() {
		return this.role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

}
