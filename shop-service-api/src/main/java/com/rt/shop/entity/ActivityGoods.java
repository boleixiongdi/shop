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
@TableName(value = "shopping_activity_goods")
public class ActivityGoods implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	@TableField(exist = false)
	private Goods ag_goods;
	
	
	@TableField(exist = false)
	private User ag_admin;
	
	//活动
	@TableField(exist = false)
	private Activity act;
	
	
	public Goods getAg_goods() {
		return ag_goods;
	}

	public void setAg_goods(Goods ag_goods) {
		this.ag_goods = ag_goods;
	}

	public User getAg_admin() {
		return ag_admin;
	}

	public void setAg_admin(User ag_admin) {
		this.ag_admin = ag_admin;
	}

	public Activity getAct() {
		return act;
	}

	public void setAct(Activity act) {
		this.act = act;
	}

	/**  */
	@TableId
	private Long id;

	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	@TableField(value = "ag_status")
	private Integer ag_status;

	/**  */
	@TableField(value = "act_id")
	private Long act_id;

	/**  */
	@TableField(value = "ag_admin_id")
	private Long ag_admin_id;

	/**  */
	@TableField(value = "ag_goods_id")
	private Long ag_goods_id;

	/**  */
	@TableField(value = "ag_price")
	private BigDecimal ag_price;

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

	public Integer getAg_status() {
		return this.ag_status;
	}

	public void setAg_status(Integer ag_status) {
		this.ag_status = ag_status;
	}

	public Long getAct_id() {
		return this.act_id;
	}

	public void setAct_id(Long act_id) {
		this.act_id = act_id;
	}

	public Long getAg_admin_id() {
		return this.ag_admin_id;
	}

	public void setAg_admin_id(Long ag_admin_id) {
		this.ag_admin_id = ag_admin_id;
	}

	public Long getAg_goods_id() {
		return this.ag_goods_id;
	}

	public void setAg_goods_id(Long ag_goods_id) {
		this.ag_goods_id = ag_goods_id;
	}

	public BigDecimal getAg_price() {
		return this.ag_price;
	}

	public void setAg_price(BigDecimal ag_price) {
		this.ag_price = ag_price;
	}

}
