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
@TableName(value = "shopping_group")
public class Group implements Serializable {
	@TableField(exist = false)
	 private List<Goods> goods_list = new ArrayList();
	 
	   //分组货物集合
	 @TableField(exist = false)
	   private List<GroupGoods> gg_list = new ArrayList();
	   
	public List<Goods> getGoods_list() {
		return goods_list;
	}

	public void setGoods_list(List<Goods> goods_list) {
		this.goods_list = goods_list;
	}

	public List<GroupGoods> getGg_list() {
		return gg_list;
	}

	public void setGg_list(List<GroupGoods> gg_list) {
		this.gg_list = gg_list;
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
	private Date beginTime;

	/**  */
	private Date endTime;

	/**  */
	@TableField(value = "group_name")
	private String group_name;

	/**  */
	private Date joinEndTime;

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

	public Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getGroup_name() {
		return this.group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public Date getJoinEndTime() {
		return this.joinEndTime;
	}

	public void setJoinEndTime(Date joinEndTime) {
		this.joinEndTime = joinEndTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
