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
@TableName(value = "shopping_goods_combin")
public class GoodsCombin implements Serializable {

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
	@TableField(value = "shopping_goods_id")
	private Long shopping_goods_id;

	/**  */
	@TableField(value = "combin_goods_id")
	private Long combin_goods_id;

	public Long getShopping_goods_id() {
		return this.shopping_goods_id;
	}

	public void setShopping_goods_id(Long shopping_goods_id) {
		this.shopping_goods_id = shopping_goods_id;
	}

	public Long getCombin_goods_id() {
		return this.combin_goods_id;
	}

	public void setCombin_goods_id(Long combin_goods_id) {
		this.combin_goods_id = combin_goods_id;
	}

}
