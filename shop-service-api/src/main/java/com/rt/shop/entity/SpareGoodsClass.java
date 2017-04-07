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
@TableName(value = "shopping_spare_goodsclass")
public class SpareGoodsClass implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	@TableField(exist = false)
	SpareGoodsClass parent;
	/**  */
	@TableId
	private Long id;

	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	private String className;

	/**  */
	private Integer level;

	/**  */
	private Integer sequence;

	/**  */
	@TableField(value = "parent_id")
	private Long parent_id;

	
	public SpareGoodsClass getParent() {
		return parent;
	}

	public void setParent(SpareGoodsClass parent) {
		this.parent = parent;
	}

	/**  */
	private Boolean viewInFloor;

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

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Long getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public Boolean getViewInFloor() {
		return this.viewInFloor;
	}

	public void setViewInFloor(Boolean viewInFloor) {
		this.viewInFloor = viewInFloor;
	}

}
