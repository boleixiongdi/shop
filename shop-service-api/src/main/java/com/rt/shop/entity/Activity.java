package com.rt.shop.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName(value = "shopping_activity")
public class Activity implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	@TableField(exist = false)
	private Accessory ac_acc;
	@TableField(exist = false)
	private List<ActivityGoods> ags = new ArrayList<ActivityGoods>();
	
	
	public Accessory getAc_acc() {
		return ac_acc;
	}

	public void setAc_acc(Accessory ac_acc) {
		this.ac_acc = ac_acc;
	}

	public List<ActivityGoods> getAgs() {
		return ags;
	}

	public void setAgs(List<ActivityGoods> ags) {
		this.ags = ags;
	}

	/**  */
	@TableId
	private Long id;

	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	@TableField(value = "ac_begin_time")
	private Date ac_begin_time;

	/**  */
	@TableField(value = "ac_content")
	private String ac_content;

	/**  */
	@TableField(value = "ac_end_time")
	private Date ac_end_time;

	/**  */
	@TableField(value = "ac_sequence")
	private Integer ac_sequence;

	/**  */
	@TableField(value = "ac_status")
	private Integer ac_status;

	/**  */
	@TableField(value = "ac_title")
	private String ac_title;

	/**  */
	@TableField(value = "ac_acc_id")
	private Long ac_acc_id;

	/**  */
	@TableField(value = "ac_rebate")
	private BigDecimal ac_rebate;

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

	public Date getAc_begin_time() {
		return this.ac_begin_time;
	}

	public void setAc_begin_time(Date ac_begin_time) {
		this.ac_begin_time = ac_begin_time;
	}

	public String getAc_content() {
		return this.ac_content;
	}

	public void setAc_content(String ac_content) {
		this.ac_content = ac_content;
	}

	public Date getAc_end_time() {
		return this.ac_end_time;
	}

	public void setAc_end_time(Date ac_end_time) {
		this.ac_end_time = ac_end_time;
	}

	public Integer getAc_sequence() {
		return this.ac_sequence;
	}

	public void setAc_sequence(Integer ac_sequence) {
		this.ac_sequence = ac_sequence;
	}

	public Integer getAc_status() {
		return this.ac_status;
	}

	public void setAc_status(Integer ac_status) {
		this.ac_status = ac_status;
	}

	public String getAc_title() {
		return this.ac_title;
	}

	public void setAc_title(String ac_title) {
		this.ac_title = ac_title;
	}

	public Long getAc_acc_id() {
		return this.ac_acc_id;
	}

	public void setAc_acc_id(Long ac_acc_id) {
		this.ac_acc_id = ac_acc_id;
	}

	public BigDecimal getAc_rebate() {
		return this.ac_rebate;
	}

	public void setAc_rebate(BigDecimal ac_rebate) {
		this.ac_rebate = ac_rebate;
	}

}
