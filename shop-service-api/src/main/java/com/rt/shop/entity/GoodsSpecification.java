package com.rt.shop.entity;

import java.io.Serializable;
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
@TableName(value = "shopping_goodsspecification")
public class GoodsSpecification implements Serializable {
	@TableField(exist = false)
	  private List<GoodsType> types = new ArrayList();
	   //货物规格集合
	  @TableField(exist = false)
	   private List<GoodsSpecProperty> properties = new ArrayList();
	   
	public List<GoodsType> getTypes() {
		return types;
	}

	public void setTypes(List<GoodsType> types) {
		this.types = types;
	}

	public List<GoodsSpecProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<GoodsSpecProperty> properties) {
		this.properties = properties;
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
	private String name;

	/**  */
	private Integer sequence;

	/**  */
	private String type;

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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
