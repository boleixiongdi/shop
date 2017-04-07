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
@TableName(value = "shopping_goodsspecproperty")
public class GoodsSpecProperty implements Serializable {
	@TableField(exist = false)
	 private Accessory specImage;
	   //商品规格
	 @TableField(exist = false)
	   private GoodsSpecification spec;
	   
	public Accessory getSpecImage() {
		return specImage;
	}

	public void setSpecImage(Accessory specImage) {
		this.specImage = specImage;
	}

	public GoodsSpecification getSpec() {
		return spec;
	}

	public void setSpec(GoodsSpecification spec) {
		this.spec = spec;
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
	private Integer sequence;

	/**  */
	private String value;

	/**  */
	@TableField(value = "spec_id1")
	private Long spec_id1;

	/**  */
	@TableField(value = "specImage_id")
	private Long specImage_id;

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

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	

	public Long getSpec_id1() {
		return spec_id1;
	}

	public void setSpec_id1(Long spec_id1) {
		this.spec_id1 = spec_id1;
	}

	public Long getSpecImage_id() {
		return this.specImage_id;
	}

	public void setSpecImage_id(Long specImage_id) {
		this.specImage_id = specImage_id;
	}

}
