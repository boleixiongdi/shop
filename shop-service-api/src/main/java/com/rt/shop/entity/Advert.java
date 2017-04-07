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
@TableName(value = "shopping_advert")
public class Advert implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	@TableField(exist = false)
	private Accessory ad_acc;
	/**  */
	@TableId
	private Long id;

	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	@TableField(value = "ad_begin_time")
	private Date ad_begin_time;

	/**  */
	@TableField(value = "ad_click_num")
	private Integer ad_click_num;

	/**  */
	@TableField(value = "ad_end_time")
	private Date ad_end_time;

	/**  */
	@TableField(value = "ad_gold")
	private Integer ad_gold;

	/**  */
	@TableField(value = "ad_slide_sequence")
	private Integer ad_slide_sequence;

	/**  */
	@TableField(value = "ad_status")
	private Integer ad_status;

	/**  */
	@TableField(value = "ad_text")
	private String ad_text;

	/**  */
	@TableField(value = "ad_title")
	private String ad_title;

	/**  */
	@TableField(value = "ad_url")
	private String ad_url;

	/**  */
	@TableField(value = "ad_acc_id")
	private Long ad_acc_id;

	/**  */
	@TableField(value = "ad_ap_id")
	private Long ad_ap_id;

	/**  */
	@TableField(value = "ad_user_id")
	private Long ad_user_id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Accessory getAd_acc() {
		return ad_acc;
	}

	public void setAd_acc(Accessory ad_acc) {
		this.ad_acc = ad_acc;
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

	public Date getAd_begin_time() {
		return this.ad_begin_time;
	}

	public void setAd_begin_time(Date ad_begin_time) {
		this.ad_begin_time = ad_begin_time;
	}

	public Integer getAd_click_num() {
		return this.ad_click_num;
	}

	public void setAd_click_num(Integer ad_click_num) {
		this.ad_click_num = ad_click_num;
	}

	public Date getAd_end_time() {
		return this.ad_end_time;
	}

	public void setAd_end_time(Date ad_end_time) {
		this.ad_end_time = ad_end_time;
	}

	public Integer getAd_gold() {
		return this.ad_gold;
	}

	public void setAd_gold(Integer ad_gold) {
		this.ad_gold = ad_gold;
	}

	public Integer getAd_slide_sequence() {
		return this.ad_slide_sequence;
	}

	public void setAd_slide_sequence(Integer ad_slide_sequence) {
		this.ad_slide_sequence = ad_slide_sequence;
	}

	public Integer getAd_status() {
		return this.ad_status;
	}

	public void setAd_status(Integer ad_status) {
		this.ad_status = ad_status;
	}

	public String getAd_text() {
		return this.ad_text;
	}

	public void setAd_text(String ad_text) {
		this.ad_text = ad_text;
	}

	public String getAd_title() {
		return this.ad_title;
	}

	public void setAd_title(String ad_title) {
		this.ad_title = ad_title;
	}

	public String getAd_url() {
		return this.ad_url;
	}

	public void setAd_url(String ad_url) {
		this.ad_url = ad_url;
	}

	public Long getAd_acc_id() {
		return this.ad_acc_id;
	}

	public void setAd_acc_id(Long ad_acc_id) {
		this.ad_acc_id = ad_acc_id;
	}

	public Long getAd_ap_id() {
		return this.ad_ap_id;
	}

	public void setAd_ap_id(Long ad_ap_id) {
		this.ad_ap_id = ad_ap_id;
	}

	public Long getAd_user_id() {
		return this.ad_user_id;
	}

	public void setAd_user_id(Long ad_user_id) {
		this.ad_user_id = ad_user_id;
	}

	

}
