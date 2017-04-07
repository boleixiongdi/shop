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
@TableName(value = "shopping_group_area")
public class GroupArea implements Serializable {
	@TableField(exist = false)
	 private GroupArea parent;
	   
	   //团购子地区
	@TableField(exist = false)
	   private List<GroupArea> childs = new ArrayList();
	   
	public GroupArea getParent() {
		return parent;
	}

	public void setParent(GroupArea parent) {
		this.parent = parent;
	}

	public List<GroupArea> getChilds() {
		return childs;
	}

	public void setChilds(List<GroupArea> childs) {
		this.childs = childs;
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
	@TableField(value = "ga_level")
	private Integer ga_level;

	/**  */
	@TableField(value = "ga_name")
	private String ga_name;

	/**  */
	@TableField(value = "ga_sequence")
	private Integer ga_sequence;

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

	public Integer getGa_level() {
		return this.ga_level;
	}

	public void setGa_level(Integer ga_level) {
		this.ga_level = ga_level;
	}

	public String getGa_name() {
		return this.ga_name;
	}

	public void setGa_name(String ga_name) {
		this.ga_name = ga_name;
	}

	public Integer getGa_sequence() {
		return this.ga_sequence;
	}

	public void setGa_sequence(Integer ga_sequence) {
		this.ga_sequence = ga_sequence;
	}

	public Long getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

}
