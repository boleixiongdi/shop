package com.rt.shop.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName(value = "shopping_store_point")
public class StorePoint implements Serializable {

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
	@TableField(value = "description_evaluate")
	private BigDecimal description_evaluate;

	/**  */
	@TableField(value = "description_evaluate_halfyear")
	private BigDecimal description_evaluate_halfyear;

	/**  */
	@TableField(value = "description_evaluate_halfyear_count1")
	private Integer description_evaluate_halfyear_count1;

	/**  */
	@TableField(value = "description_evaluate_halfyear_count2")
	private Integer description_evaluate_halfyear_count2;

	/**  */
	@TableField(value = "description_evaluate_halfyear_count3")
	private Integer description_evaluate_halfyear_count3;

	/**  */
	@TableField(value = "description_evaluate_halfyear_count4")
	private Integer description_evaluate_halfyear_count4;

	/**  */
	@TableField(value = "description_evaluate_halfyear_count5")
	private Integer description_evaluate_halfyear_count5;

	/**  */
	@TableField(value = "service_evaluate")
	private BigDecimal service_evaluate;

	/**  */
	@TableField(value = "service_evaluate_halfyear")
	private BigDecimal service_evaluate_halfyear;

	/**  */
	@TableField(value = "service_evaluate_halfyear_count1")
	private Integer service_evaluate_halfyear_count1;

	/**  */
	@TableField(value = "service_evaluate_halfyear_count2")
	private Integer service_evaluate_halfyear_count2;

	/**  */
	@TableField(value = "service_evaluate_halfyear_count3")
	private Integer service_evaluate_halfyear_count3;

	/**  */
	@TableField(value = "service_evaluate_halfyear_count4")
	private Integer service_evaluate_halfyear_count4;

	/**  */
	@TableField(value = "service_evaluate_halfyear_count5")
	private Integer service_evaluate_halfyear_count5;

	/**  */
	@TableField(value = "ship_evaluate")
	private BigDecimal ship_evaluate;

	/**  */
	@TableField(value = "ship_evaluate_halfyear")
	private BigDecimal ship_evaluate_halfyear;

	/**  */
	@TableField(value = "ship_evaluate_halfyear_count1")
	private Integer ship_evaluate_halfyear_count1;

	/**  */
	@TableField(value = "ship_evaluate_halfyear_count2")
	private Integer ship_evaluate_halfyear_count2;

	/**  */
	@TableField(value = "ship_evaluate_halfyear_count3")
	private Integer ship_evaluate_halfyear_count3;

	/**  */
	@TableField(value = "ship_evaluate_halfyear_count4")
	private Integer ship_evaluate_halfyear_count4;

	/**  */
	@TableField(value = "ship_evaluate_halfyear_count5")
	private Integer ship_evaluate_halfyear_count5;

	/**  */
	@TableField(value = "store_evaluate1")
	private BigDecimal store_evaluate1;

	/**  */
	@TableField(value = "store_id")
	private Long store_id;

	/**  */
	private Date statTime;

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

	public BigDecimal getDescription_evaluate() {
		return this.description_evaluate;
	}

	public void setDescription_evaluate(BigDecimal description_evaluate) {
		this.description_evaluate = description_evaluate;
	}

	public BigDecimal getDescription_evaluate_halfyear() {
		return this.description_evaluate_halfyear;
	}

	public void setDescription_evaluate_halfyear(BigDecimal description_evaluate_halfyear) {
		this.description_evaluate_halfyear = description_evaluate_halfyear;
	}

	public Integer getDescription_evaluate_halfyear_count1() {
		return this.description_evaluate_halfyear_count1;
	}

	public void setDescription_evaluate_halfyear_count1(Integer description_evaluate_halfyear_count1) {
		this.description_evaluate_halfyear_count1 = description_evaluate_halfyear_count1;
	}

	public Integer getDescription_evaluate_halfyear_count2() {
		return this.description_evaluate_halfyear_count2;
	}

	public void setDescription_evaluate_halfyear_count2(Integer description_evaluate_halfyear_count2) {
		this.description_evaluate_halfyear_count2 = description_evaluate_halfyear_count2;
	}

	public Integer getDescription_evaluate_halfyear_count3() {
		return this.description_evaluate_halfyear_count3;
	}

	public void setDescription_evaluate_halfyear_count3(Integer description_evaluate_halfyear_count3) {
		this.description_evaluate_halfyear_count3 = description_evaluate_halfyear_count3;
	}

	public Integer getDescription_evaluate_halfyear_count4() {
		return this.description_evaluate_halfyear_count4;
	}

	public void setDescription_evaluate_halfyear_count4(Integer description_evaluate_halfyear_count4) {
		this.description_evaluate_halfyear_count4 = description_evaluate_halfyear_count4;
	}

	public Integer getDescription_evaluate_halfyear_count5() {
		return this.description_evaluate_halfyear_count5;
	}

	public void setDescription_evaluate_halfyear_count5(Integer description_evaluate_halfyear_count5) {
		this.description_evaluate_halfyear_count5 = description_evaluate_halfyear_count5;
	}

	public BigDecimal getService_evaluate() {
		return this.service_evaluate;
	}

	public void setService_evaluate(BigDecimal service_evaluate) {
		this.service_evaluate = service_evaluate;
	}

	public BigDecimal getService_evaluate_halfyear() {
		return this.service_evaluate_halfyear;
	}

	public void setService_evaluate_halfyear(BigDecimal service_evaluate_halfyear) {
		this.service_evaluate_halfyear = service_evaluate_halfyear;
	}

	public Integer getService_evaluate_halfyear_count1() {
		return this.service_evaluate_halfyear_count1;
	}

	public void setService_evaluate_halfyear_count1(Integer service_evaluate_halfyear_count1) {
		this.service_evaluate_halfyear_count1 = service_evaluate_halfyear_count1;
	}

	public Integer getService_evaluate_halfyear_count2() {
		return this.service_evaluate_halfyear_count2;
	}

	public void setService_evaluate_halfyear_count2(Integer service_evaluate_halfyear_count2) {
		this.service_evaluate_halfyear_count2 = service_evaluate_halfyear_count2;
	}

	public Integer getService_evaluate_halfyear_count3() {
		return this.service_evaluate_halfyear_count3;
	}

	public void setService_evaluate_halfyear_count3(Integer service_evaluate_halfyear_count3) {
		this.service_evaluate_halfyear_count3 = service_evaluate_halfyear_count3;
	}

	public Integer getService_evaluate_halfyear_count4() {
		return this.service_evaluate_halfyear_count4;
	}

	public void setService_evaluate_halfyear_count4(Integer service_evaluate_halfyear_count4) {
		this.service_evaluate_halfyear_count4 = service_evaluate_halfyear_count4;
	}

	public Integer getService_evaluate_halfyear_count5() {
		return this.service_evaluate_halfyear_count5;
	}

	public void setService_evaluate_halfyear_count5(Integer service_evaluate_halfyear_count5) {
		this.service_evaluate_halfyear_count5 = service_evaluate_halfyear_count5;
	}

	public BigDecimal getShip_evaluate() {
		return this.ship_evaluate;
	}

	public void setShip_evaluate(BigDecimal ship_evaluate) {
		this.ship_evaluate = ship_evaluate;
	}

	public BigDecimal getShip_evaluate_halfyear() {
		return this.ship_evaluate_halfyear;
	}

	public void setShip_evaluate_halfyear(BigDecimal ship_evaluate_halfyear) {
		this.ship_evaluate_halfyear = ship_evaluate_halfyear;
	}

	public Integer getShip_evaluate_halfyear_count1() {
		return this.ship_evaluate_halfyear_count1;
	}

	public void setShip_evaluate_halfyear_count1(Integer ship_evaluate_halfyear_count1) {
		this.ship_evaluate_halfyear_count1 = ship_evaluate_halfyear_count1;
	}

	public Integer getShip_evaluate_halfyear_count2() {
		return this.ship_evaluate_halfyear_count2;
	}

	public void setShip_evaluate_halfyear_count2(Integer ship_evaluate_halfyear_count2) {
		this.ship_evaluate_halfyear_count2 = ship_evaluate_halfyear_count2;
	}

	public Integer getShip_evaluate_halfyear_count3() {
		return this.ship_evaluate_halfyear_count3;
	}

	public void setShip_evaluate_halfyear_count3(Integer ship_evaluate_halfyear_count3) {
		this.ship_evaluate_halfyear_count3 = ship_evaluate_halfyear_count3;
	}

	public Integer getShip_evaluate_halfyear_count4() {
		return this.ship_evaluate_halfyear_count4;
	}

	public void setShip_evaluate_halfyear_count4(Integer ship_evaluate_halfyear_count4) {
		this.ship_evaluate_halfyear_count4 = ship_evaluate_halfyear_count4;
	}

	public Integer getShip_evaluate_halfyear_count5() {
		return this.ship_evaluate_halfyear_count5;
	}

	public void setShip_evaluate_halfyear_count5(Integer ship_evaluate_halfyear_count5) {
		this.ship_evaluate_halfyear_count5 = ship_evaluate_halfyear_count5;
	}

	public BigDecimal getStore_evaluate1() {
		return this.store_evaluate1;
	}

	public void setStore_evaluate1(BigDecimal store_evaluate1) {
		this.store_evaluate1 = store_evaluate1;
	}

	public Long getStore_id() {
		return this.store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public Date getStatTime() {
		return this.statTime;
	}

	public void setStatTime(Date statTime) {
		this.statTime = statTime;
	}

}
