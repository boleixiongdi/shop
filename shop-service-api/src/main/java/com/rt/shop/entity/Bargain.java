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
@TableName(value = "shopping_bargain")
public class Bargain implements Serializable {

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
	@TableField(value = "bargain_time")
	private Date bargain_time;

	/**  */
	private Integer maximum;

	/**  */
	private BigDecimal rebate;

	/**  */
	private String state;

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

	public Date getBargain_time() {
		return this.bargain_time;
	}

	public void setBargain_time(Date bargain_time) {
		this.bargain_time = bargain_time;
	}

	public Integer getMaximum() {
		return this.maximum;
	}

	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}

	public BigDecimal getRebate() {
		return this.rebate;
	}

	public void setRebate(BigDecimal rebate) {
		this.rebate = rebate;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
