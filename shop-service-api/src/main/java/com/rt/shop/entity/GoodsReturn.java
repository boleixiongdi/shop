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
@TableName(value = "shopping_goods_return")
public class GoodsReturn implements Serializable {

	@TableField(exist = false)
	private OrderForm of;
	//商品退货项目
	@TableField(exist = false)
	private List<GoodsReturnitem> items = new ArrayList<GoodsReturnitem>();
	//使用者
	@TableField(exist = false)
	private User user;
	
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	public OrderForm getOf() {
		return of;
	}

	public void setOf(OrderForm of) {
		this.of = of;
	}

	

	public List<GoodsReturnitem> getItems() {
		return items;
	}

	public void setItems(List<GoodsReturnitem> items) {
		this.items = items;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



	/**  */
	@TableId
	private Long id;

	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	@TableField(value = "return_id")
	private String return_id;

	/**  */
	@TableField(value = "return_info")
	private String return_info;

	/**  */
	@TableField(value = "of_id")
	private Long of_id;

	/**  */
	@TableField(value = "user_id")
	private Long user_id;

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

	public String getReturn_id() {
		return this.return_id;
	}

	public void setReturn_id(String return_id) {
		this.return_id = return_id;
	}

	public String getReturn_info() {
		return this.return_info;
	}

	public void setReturn_info(String return_info) {
		this.return_info = return_info;
	}

	public Long getOf_id() {
		return this.of_id;
	}

	public void setOf_id(Long of_id) {
		this.of_id = of_id;
	}

	public Long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

}
