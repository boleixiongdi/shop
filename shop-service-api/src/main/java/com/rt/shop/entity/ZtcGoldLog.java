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
@TableName(value = "shopping_ztc_gold_log")
public class ZtcGoldLog implements Serializable {

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
	@TableField(value = "zgl_content")
	private String zgl_content;

	/**  */
	@TableField(value = "zgl_gold")
	private Integer zgl_gold;

	/**  */
	@TableField(value = "zgl_type")
	private Integer zgl_type;

	/**  */
	@TableField(value = "zgl_goods_id")
	private Long zgl_goods_id;

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

	public String getZgl_content() {
		return this.zgl_content;
	}

	public void setZgl_content(String zgl_content) {
		this.zgl_content = zgl_content;
	}

	public Integer getZgl_gold() {
		return this.zgl_gold;
	}

	public void setZgl_gold(Integer zgl_gold) {
		this.zgl_gold = zgl_gold;
	}

	public Integer getZgl_type() {
		return this.zgl_type;
	}

	public void setZgl_type(Integer zgl_type) {
		this.zgl_type = zgl_type;
	}

	public Long getZgl_goods_id() {
		return this.zgl_goods_id;
	}

	public void setZgl_goods_id(Long zgl_goods_id) {
		this.zgl_goods_id = zgl_goods_id;
	}

}
