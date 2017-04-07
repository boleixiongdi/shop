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
@TableName(value = "shopping_navigation")
public class Navigation implements Serializable {

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
	private Boolean display;

	/**  */
	private Integer location;

	/**  */
	@TableField(value = "new_win")
	private Integer new_win;

	/**  */
	private Integer sequence;

	/**  */
	private Boolean sysNav;

	/**  */
	private String title;

	/**  */
	private String type;

	/**  */
	@TableField(value = "type_id")
	private Long type_id;

	/**  */
	private String url;

	/**  */
	@TableField(value = "original_url")
	private String original_url;

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

	public Boolean getDisplay() {
		return this.display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public Integer getLocation() {
		return this.location;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

	public Integer getNew_win() {
		return this.new_win;
	}

	public void setNew_win(Integer new_win) {
		this.new_win = new_win;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Boolean getSysNav() {
		return this.sysNav;
	}

	public void setSysNav(Boolean sysNav) {
		this.sysNav = sysNav;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getType_id() {
		return this.type_id;
	}

	public void setType_id(Long type_id) {
		this.type_id = type_id;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOriginal_url() {
		return this.original_url;
	}

	public void setOriginal_url(String original_url) {
		this.original_url = original_url;
	}

}
