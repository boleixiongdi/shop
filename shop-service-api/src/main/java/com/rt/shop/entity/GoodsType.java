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
@TableName(value = "shopping_goodstype")
public class GoodsType implements Serializable {
	@TableField(exist = false)
	 private List<GoodsSpecification> gss = new ArrayList();
	   
	   //商品品牌集合
	 @TableField(exist = false)
	   private List<GoodsBrand> gbs = new ArrayList();
	 
	   //商品类型属性集合
	   @TableField(exist = false)
	   private List<GoodsTypeProperty> properties = new ArrayList();
	 
	   //商品类型
	   @TableField(exist = false)
	   private List<GoodsClass> gcs = new ArrayList();
	 
	   
	public List<GoodsSpecification> getGss() {
		return gss;
	}

	public void setGss(List<GoodsSpecification> gss) {
		this.gss = gss;
	}

	public List<GoodsBrand> getGbs() {
		return gbs;
	}

	public void setGbs(List<GoodsBrand> gbs) {
		this.gbs = gbs;
	}

	public List<GoodsTypeProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<GoodsTypeProperty> properties) {
		this.properties = properties;
	}

	public List<GoodsClass> getGcs() {
		return gcs;
	}

	public void setGcs(List<GoodsClass> gcs) {
		this.gcs = gcs;
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

}
