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
@TableName(value = "shopping_group_class")
public class GroupClass implements Serializable {
	@TableField(exist = false)
	private GroupClass parent;
	   
	   //团购子类型
	@TableField(exist = false)
	   private List<GroupClass> childs = new ArrayList();
	   @TableField(exist = false)
	   private List<GroupGoods> ggs = new ArrayList();
	   
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	public GroupClass getParent() {
		return parent;
	}

	public void setParent(GroupClass parent) {
		this.parent = parent;
	}

	public List<GroupClass> getChilds() {
		return childs;
	}

	public void setChilds(List<GroupClass> childs) {
		this.childs = childs;
	}

	public List<GroupGoods> getGgs() {
		return ggs;
	}

	public void setGgs(List<GroupGoods> ggs) {
		this.ggs = ggs;
	}

	/**  */
	@TableId
	private Long id;

	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	@TableField(value = "gc_level")
	private Integer gc_level;

	/**  */
	@TableField(value = "gc_name")
	private String gc_name;

	/**  */
	@TableField(value = "gc_sequence")
	private Integer gc_sequence;

	/**  */
	@TableField(value = "parent_id")
	private Long parent_id;

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

	public Integer getGc_level() {
		return this.gc_level;
	}

	public void setGc_level(Integer gc_level) {
		this.gc_level = gc_level;
	}

	public String getGc_name() {
		return this.gc_name;
	}

	public void setGc_name(String gc_name) {
		this.gc_name = gc_name;
	}

	public Integer getGc_sequence() {
		return this.gc_sequence;
	}

	public void setGc_sequence(Integer gc_sequence) {
		this.gc_sequence = gc_sequence;
	}

	public Long getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

}
