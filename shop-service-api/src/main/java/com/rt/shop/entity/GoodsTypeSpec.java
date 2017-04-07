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
@TableName(value = "shopping_goodstype_spec")
public class GoodsTypeSpec implements Serializable {

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
	@TableField(value = "spec_id")
	private Long spec_id;

	public Long getType_id() {
		return this.type_id;
	}

	public void setType_id(Long type_id) {
		this.type_id = type_id;
	}

	public Long getSpec_id() {
		return this.spec_id;
	}

	public void setSpec_id(Long spec_id) {
		this.spec_id = spec_id;
	}

}
