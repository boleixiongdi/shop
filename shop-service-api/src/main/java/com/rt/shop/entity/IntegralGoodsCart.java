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
@TableName(value = "shopping_integral_goodscart")
public class IntegralGoodsCart implements Serializable {
	@TableField(exist = false)
	 private IntegralGoods goods;
	  
	   //订单
	 @TableField(exist = false)
	   private IntegralGoodsOrder order;
	   
	public IntegralGoods getGoods() {
		return goods;
	}

	public void setGoods(IntegralGoods goods) {
		this.goods = goods;
	}

	public IntegralGoodsOrder getOrder() {
		return order;
	}

	public void setOrder(IntegralGoodsOrder order) {
		this.order = order;
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
	private Integer count;

	/**  */
	private Integer integral;

	/**  */
	@TableField(value = "trans_fee")
	private BigDecimal trans_fee;

	/**  */
	@TableField(value = "goods_id")
	private Long goods_id;

	/**  */
	@TableField(value = "order_id")
	private Long order_id;

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

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getIntegral() {
		return this.integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public BigDecimal getTrans_fee() {
		return this.trans_fee;
	}

	public void setTrans_fee(BigDecimal trans_fee) {
		this.trans_fee = trans_fee;
	}

	public Long getGoods_id() {
		return this.goods_id;
	}

	public void setGoods_id(Long goods_id) {
		this.goods_id = goods_id;
	}

	public Long getOrder_id() {
		return this.order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}

}
