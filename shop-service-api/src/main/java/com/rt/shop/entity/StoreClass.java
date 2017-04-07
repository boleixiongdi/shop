package com.rt.shop.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName(value = "shopping_storeclass")
public class StoreClass implements Serializable {
	@TableField(exist = false)
	 private StoreClass parent;
	 
	   //商店子类型
	 @TableField(exist = false)
	   private List<StoreClass> childs = new ArrayList();
	   
	 
	public StoreClass getParent() {
		return parent;
	}

	public void setParent(StoreClass parent) {
		this.parent = parent;
	}

	public List<StoreClass> getChilds() {
		return childs;
	}

	public void setChilds(List<StoreClass> childs) {
		this.childs = childs;
	}

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
	private String className;

	/**  */
	private Integer level;

	/**  */
	private Integer sequence;

	/**  */
	@TableField(value = "parent_id")
	private Long parent_id;

	/**  */
	@TableField(value = "description_evaluate")
	private BigDecimal description_evaluate;

	/**  */
	@TableField(value = "service_evaluate")
	private BigDecimal service_evaluate;

	/**  */
	@TableField(value = "ship_evaluate")
	private BigDecimal ship_evaluate;

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

	public BigDecimal getDescription_evaluate() {
		return this.description_evaluate;
	}

	public void setDescription_evaluate(BigDecimal description_evaluate) {
		this.description_evaluate = description_evaluate;
	}

	public BigDecimal getService_evaluate() {
		return this.service_evaluate;
	}

	public void setService_evaluate(BigDecimal service_evaluate) {
		this.service_evaluate = service_evaluate;
	}

	public BigDecimal getShip_evaluate() {
		return this.ship_evaluate;
	}

	public void setShip_evaluate(BigDecimal ship_evaluate) {
		this.ship_evaluate = ship_evaluate;
	}

}
