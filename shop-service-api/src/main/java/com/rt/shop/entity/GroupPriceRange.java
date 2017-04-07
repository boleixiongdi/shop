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
@TableName(value = "shopping_group_price_range")
public class GroupPriceRange implements Serializable {

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
	@TableField(value = "gpr_begin")
	private Integer gpr_begin;

	/**  */
	@TableField(value = "gpr_end")
	private Integer gpr_end;

	/**  */
	@TableField(value = "gpr_name")
	private String gpr_name;

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

	public Integer getGpr_begin() {
		return this.gpr_begin;
	}

	public void setGpr_begin(Integer gpr_begin) {
		this.gpr_begin = gpr_begin;
	}

	public Integer getGpr_end() {
		return this.gpr_end;
	}

	public void setGpr_end(Integer gpr_end) {
		this.gpr_end = gpr_end;
	}

	public String getGpr_name() {
		return this.gpr_name;
	}

	public void setGpr_name(String gpr_name) {
		this.gpr_name = gpr_name;
	}

}
