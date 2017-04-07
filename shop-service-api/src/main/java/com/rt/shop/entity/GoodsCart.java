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
@TableName(value = "shopping_goodscart")
public class GoodsCart implements Serializable {
	@TableField(exist = false)
	private Goods goods;
	@TableField(exist = false)
	private List<GoodsSpecProperty> gsps = new ArrayList<GoodsSpecProperty>();
	
    //商品规格
	
     //订单
	@TableField(exist = false)
	private OrderForm of;
	
	//商店运输
	@TableField(exist = false)
	private StoreCart sc;
	
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public List<GoodsSpecProperty> getGsps() {
		return gsps;
	}

	public void setGsps(List<GoodsSpecProperty> gsps) {
		this.gsps = gsps;
	}

	public OrderForm getOf() {
		return of;
	}

	public void setOf(OrderForm of) {
		this.of = of;
	}

	public StoreCart getSc() {
		return sc;
	}

	public void setSc(StoreCart sc) {
		this.sc = sc;
	}

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
	private BigDecimal price;

	/**  */
	@TableField(value = "spec_info")
	private String spec_info;

	/**  */
	@TableField(value = "goods_id")
	private Long goods_id;

	/**  */
	@TableField(value = "of_id")
	private Long of_id;

	/**  */
	@TableField(value = "cart_type")
	private String cart_type;

	/**  */
	@TableField(value = "sc_id")
	private Long sc_id;

	/**  */
	private Integer status;

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

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getSpec_info() {
		return this.spec_info;
	}

	public void setSpec_info(String spec_info) {
		this.spec_info = spec_info;
	}

	public Long getGoods_id() {
		return this.goods_id;
	}

	public void setGoods_id(Long goods_id) {
		this.goods_id = goods_id;
	}

	public Long getOf_id() {
		return this.of_id;
	}

	public void setOf_id(Long of_id) {
		this.of_id = of_id;
	}

	public String getCart_type() {
		return this.cart_type;
	}

	public void setCart_type(String cart_type) {
		this.cart_type = cart_type;
	}

	public Long getSc_id() {
		return this.sc_id;
	}

	public void setSc_id(Long sc_id) {
		this.sc_id = sc_id;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
