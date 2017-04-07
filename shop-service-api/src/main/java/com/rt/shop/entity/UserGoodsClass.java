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
@TableName(value = "shopping_usergoodsclass")
public class UserGoodsClass implements Serializable {
	@TableField(exist = false)
	private UserGoodsClass parent;
	 
	   //用户自类型
	@TableField(exist = false)
	   private List<UserGoodsClass> childs = new ArrayList();
	 
	   //商品列表
	   @TableField(exist = false)
	   private List<Goods> goods_list = new ArrayList();
	 
	   
	public UserGoodsClass getParent() {
		return parent;
	}

	public void setParent(UserGoodsClass parent) {
		this.parent = parent;
	}

	public List<UserGoodsClass> getChilds() {
		return childs;
	}

	public void setChilds(List<UserGoodsClass> childs) {
		this.childs = childs;
	}

	public List<Goods> getGoods_list() {
		return goods_list;
	}

	public void setGoods_list(List<Goods> goods_list) {
		this.goods_list = goods_list;
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
	private String className;

	/**  */
	private Boolean display;

	/**  */
	private Integer level;

	/**  */
	private Integer sequence;

	/**  */
	@TableField(value = "parent_id")
	private Long parent_id;

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

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Boolean getDisplay() {
		return this.display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Long getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public Long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

}
