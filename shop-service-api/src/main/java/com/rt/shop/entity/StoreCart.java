package com.rt.shop.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName(value = "shopping_storecart")
public class StoreCart implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId
	private Long id;
	@TableField(exist = false)
	private List<GoodsCart> gcs;
	@TableField(exist = false)
	private List<Goods> goods;
	@TableField(exist = false)
	private Store store;
	@TableField(exist = false)
	private User user;
	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	@TableField(value = "cart_session_id")
	private String cart_session_id;

	/**  */
	@TableField(value = "total_price")
	private BigDecimal total_price;

	/**  */
	@TableField(value = "store_id")
	private Long store_id;

	/**  */
	@TableField(value = "user_id")
	private Long user_id;

	/**  */
	@TableField(value = "sc_status")
	private Integer sc_status;

	
	
	public List<Goods> getGoods() {
		return goods;
	}

	public void setGoods(List<Goods> goods) {
		this.goods = goods;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<GoodsCart> getGcs() {
		return gcs;
	}

	public void setGcs(List<GoodsCart> gcs) {
		this.gcs = gcs;
	}

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

	public String getCart_session_id() {
		return this.cart_session_id;
	}

	public void setCart_session_id(String cart_session_id) {
		this.cart_session_id = cart_session_id;
	}

	public BigDecimal getTotal_price() {
		return this.total_price;
	}

	public void setTotal_price(BigDecimal total_price) {
		this.total_price = total_price;
	}

	public Long getStore_id() {
		return this.store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public Long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Integer getSc_status() {
		return this.sc_status;
	}

	public void setSc_status(Integer sc_status) {
		this.sc_status = sc_status;
	}

}
