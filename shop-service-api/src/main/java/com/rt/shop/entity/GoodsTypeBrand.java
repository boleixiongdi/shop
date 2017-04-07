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
@TableName(value = "shopping_goodstype_brand")
public class GoodsTypeBrand implements Serializable {

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
	@TableField(value = "type_id")
	private Long type_id;

	/**  */
	@TableField(value = "brand_id")
	private Long brand_id;

	public Long getType_id() {
		return this.type_id;
	}

	public void setType_id(Long type_id) {
		this.type_id = type_id;
	}

	public Long getBrand_id() {
		return this.brand_id;
	}

	public void setBrand_id(Long brand_id) {
		this.brand_id = brand_id;
	}

}
