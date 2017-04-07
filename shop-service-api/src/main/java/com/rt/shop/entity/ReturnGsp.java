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
@TableName(value = "shopping_return_gsp")
public class ReturnGsp implements Serializable {

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
	@TableField(value = "item_id")
	private Long item_id;

	/**  */
	@TableField(value = "gsp_id")
	private Long gsp_id;

	public Long getItem_id() {
		return this.item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}

	public Long getGsp_id() {
		return this.gsp_id;
	}

	public void setGsp_id(Long gsp_id) {
		this.gsp_id = gsp_id;
	}

}
