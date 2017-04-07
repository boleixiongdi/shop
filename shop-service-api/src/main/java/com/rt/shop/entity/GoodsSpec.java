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
@TableName(value = "shopping_goods_spec")
public class GoodsSpec implements Serializable {

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
	@TableField(value = "goods_id")
	private Long goods_id;

	/**  */
	@TableField(value = "spec_id")
	private Long spec_id;

	public Long getGoods_id() {
		return this.goods_id;
	}

	public void setGoods_id(Long goods_id) {
		this.goods_id = goods_id;
	}

	public Long getSpec_id() {
		return this.spec_id;
	}

	public void setSpec_id(Long spec_id) {
		this.spec_id = spec_id;
	}

}
