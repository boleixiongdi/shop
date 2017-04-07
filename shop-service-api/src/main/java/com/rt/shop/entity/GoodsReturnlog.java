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
@TableName(value = "shopping_goods_returnlog")
public class GoodsReturnlog implements Serializable {
	@TableField(exist = false)
	 private OrderForm of;
	   
	   //返回货物
	 @TableField(exist = false)
	   private GoodsReturn gr;
	   //返回人
	   @TableField(exist = false)
	   private User return_user;
	   
	   
	public OrderForm getOf() {
		return of;
	}

	public void setOf(OrderForm of) {
		this.of = of;
	}

	public GoodsReturn getGr() {
		return gr;
	}

	public void setGr(GoodsReturn gr) {
		this.gr = gr;
	}

	public User getReturn_user() {
		return return_user;
	}

	public void setReturn_user(User return_user) {
		this.return_user = return_user;
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
	@TableField(value = "gr_id")
	private Long gr_id;

	/**  */
	@TableField(value = "of_id")
	private Long of_id;

	/**  */
	@TableField(value = "return_user_id")
	private Long return_user_id;

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

	public Long getGr_id() {
		return this.gr_id;
	}

	public void setGr_id(Long gr_id) {
		this.gr_id = gr_id;
	}

	public Long getOf_id() {
		return this.of_id;
	}

	public void setOf_id(Long of_id) {
		this.of_id = of_id;
	}

	public Long getReturn_user_id() {
		return this.return_user_id;
	}

	public void setReturn_user_id(Long return_user_id) {
		this.return_user_id = return_user_id;
	}

}
