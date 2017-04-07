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
@TableName(value = "shopping_watermark")
public class Watermark implements Serializable {

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
	@TableField(value = "wm_image_alpha")
	private Float wm_image_alpha;

	/**  */
	@TableField(value = "wm_image_open")
	private Boolean wm_image_open;

	/**  */
	@TableField(value = "wm_image_pos")
	private Integer wm_image_pos;

	/**  */
	@TableField(value = "wm_text")
	private String wm_text;

	/**  */
	@TableField(value = "wm_text_color")
	private String wm_text_color;

	/**  */
	@TableField(value = "wm_text_font")
	private String wm_text_font;

	/**  */
	@TableField(value = "wm_text_font_size")
	private Integer wm_text_font_size;

	/**  */
	@TableField(value = "wm_text_open")
	private Boolean wm_text_open;

	/**  */
	@TableField(value = "wm_text_pos")
	private Integer wm_text_pos;

	/**  */
	@TableField(value = "store_id")
	private Long store_id;

	/**  */
	@TableField(value = "wm_image_id")
	private Long wm_image_id;

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

	public Float getWm_image_alpha() {
		return this.wm_image_alpha;
	}

	public void setWm_image_alpha(Float wm_image_alpha) {
		this.wm_image_alpha = wm_image_alpha;
	}

	public Boolean getWm_image_open() {
		return this.wm_image_open;
	}

	public void setWm_image_open(Boolean wm_image_open) {
		this.wm_image_open = wm_image_open;
	}

	public Integer getWm_image_pos() {
		return this.wm_image_pos;
	}

	public void setWm_image_pos(Integer wm_image_pos) {
		this.wm_image_pos = wm_image_pos;
	}

	public String getWm_text() {
		return this.wm_text;
	}

	public void setWm_text(String wm_text) {
		this.wm_text = wm_text;
	}

	public String getWm_text_color() {
		return this.wm_text_color;
	}

	public void setWm_text_color(String wm_text_color) {
		this.wm_text_color = wm_text_color;
	}

	public String getWm_text_font() {
		return this.wm_text_font;
	}

	public void setWm_text_font(String wm_text_font) {
		this.wm_text_font = wm_text_font;
	}

	public Integer getWm_text_font_size() {
		return this.wm_text_font_size;
	}

	public void setWm_text_font_size(Integer wm_text_font_size) {
		this.wm_text_font_size = wm_text_font_size;
	}

	public Boolean getWm_text_open() {
		return this.wm_text_open;
	}

	public void setWm_text_open(Boolean wm_text_open) {
		this.wm_text_open = wm_text_open;
	}

	public Integer getWm_text_pos() {
		return this.wm_text_pos;
	}

	public void setWm_text_pos(Integer wm_text_pos) {
		this.wm_text_pos = wm_text_pos;
	}

	public Long getStore_id() {
		return this.store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public Long getWm_image_id() {
		return this.wm_image_id;
	}

	public void setWm_image_id(Long wm_image_id) {
		this.wm_image_id = wm_image_id;
	}

}
