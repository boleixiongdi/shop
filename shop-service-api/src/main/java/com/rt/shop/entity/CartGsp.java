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
@TableName(value = "shopping_cart_gsp")
public class CartGsp implements Serializable {

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
	@TableField(value = "cart_id")
	private Long cart_id;

	/**  */
	@TableField(value = "gsp_id")
	private Long gsp_id;

	public Long getCart_id() {
		return this.cart_id;
	}

	public void setCart_id(Long cart_id) {
		this.cart_id = cart_id;
	}

	public Long getGsp_id() {
		return this.gsp_id;
	}

	public void setGsp_id(Long gsp_id) {
		this.gsp_id = gsp_id;
	}

}
