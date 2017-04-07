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
@TableName(value = "shopping_role_res")
public class RoleRes implements Serializable {

	@TableId
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableField(value = "role_id")
	private Long role_id;

	/**  */
	@TableField(value = "res_id")
	private Long res_id;

	public Long getRole_id() {
		return this.role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

	public Long getRes_id() {
		return this.res_id;
	}

	public void setRes_id(Long res_id) {
		this.res_id = res_id;
	}

}
