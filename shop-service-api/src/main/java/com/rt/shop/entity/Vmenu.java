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
@TableName(value = "shopping_vmenu")
public class Vmenu implements Serializable {

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
	@TableField(value = "menu_key")
	private String menu_key;

	/**  */
	@TableField(value = "menu_name")
	private String menu_name;

	/**  */
	@TableField(value = "menu_type")
	private String menu_type;

	/**  */
	@TableField(value = "menu_url")
	private String menu_url;

	/**  */
	@TableField(value = "parent_id")
	private Long parent_id;

	/**  */
	@TableField(value = "store_id")
	private Long store_id;

	/**  */
	@TableField(value = "menu_sequence")
	private Integer menu_sequence;

	/**  */
	@TableField(value = "menu_key_content")
	private String menu_key_content;

	/**  */
	@TableField(value = "menu_cat")
	private String menu_cat;

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

	public String getMenu_key() {
		return this.menu_key;
	}

	public void setMenu_key(String menu_key) {
		this.menu_key = menu_key;
	}

	public String getMenu_name() {
		return this.menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getMenu_type() {
		return this.menu_type;
	}

	public void setMenu_type(String menu_type) {
		this.menu_type = menu_type;
	}

	public String getMenu_url() {
		return this.menu_url;
	}

	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}

	public Long getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public Long getStore_id() {
		return this.store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public Integer getMenu_sequence() {
		return this.menu_sequence;
	}

	public void setMenu_sequence(Integer menu_sequence) {
		this.menu_sequence = menu_sequence;
	}

	public String getMenu_key_content() {
		return this.menu_key_content;
	}

	public void setMenu_key_content(String menu_key_content) {
		this.menu_key_content = menu_key_content;
	}

	public String getMenu_cat() {
		return this.menu_cat;
	}

	public void setMenu_cat(String menu_cat) {
		this.menu_cat = menu_cat;
	}

}
