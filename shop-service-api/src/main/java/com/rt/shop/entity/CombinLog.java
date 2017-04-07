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
@TableName(value = "shopping_combin_log")
public class CombinLog implements Serializable {
private User from_user;
	
	//被举报人
@TableField(exist = false)
	private User to_user;
	
	//举报商品
	@TableField(exist = false)
	private List<ComplaintGoods> cgs = new ArrayList<ComplaintGoods>();
	
	//举报主题
	@TableField(exist = false)
	private ComplaintSubject cs;
	@TableField(exist = false)
	private User handle_user;
	
	//证据1
	@TableField(exist = false)
	private Accessory from_acc1;
	
	//证据2
	@TableField(exist = false)
	private Accessory from_acc2;
	
	//证据3
	@TableField(exist = false)
	private Accessory from_acc3;
	
	//举证1
	@TableField(exist = false)
	private Accessory to_acc1;

	//举证2
	@TableField(exist = false)
	private Accessory to_acc2;

	//举证3
	@TableField(exist = false)
	private Accessory to_acc3;
	
	//被举报订单
	@TableField(exist = false)
	private OrderForm of;
	
	@TableField(exist = false)
	private Store store;
	
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
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
	@TableField(value = "begin_time")
	private Date begin_time;

	/**  */
	@TableField(value = "end_time")
	private Date end_time;

	/**  */
	private Integer gold;

	/**  */
	@TableField(value = "store_id")
	private Long store_id;

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

	public Date getBegin_time() {
		return this.begin_time;
	}

	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}

	public Date getEnd_time() {
		return this.end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public Integer getGold() {
		return this.gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public Long getStore_id() {
		return this.store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

}
