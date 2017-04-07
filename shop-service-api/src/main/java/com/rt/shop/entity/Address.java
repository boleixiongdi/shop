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
@TableName(value = "shopping_address")
public class Address implements Serializable {
	
	@TableField(exist = false)
	private AdvPos ad_ap;
	//广告附件
	@TableField(exist = false)
	private Accessory ad_acc;
	@TableField(exist = false)
	private User ad_user;
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	@TableField(exist = false)
	private Area area;
	
	

	public Area getArea() {
		return area;
	}

	public AdvPos getAd_ap() {
		return ad_ap;
	}

	public void setAd_ap(AdvPos ad_ap) {
		this.ad_ap = ad_ap;
	}

	public Accessory getAd_acc() {
		return ad_acc;
	}

	public void setAd_acc(Accessory ad_acc) {
		this.ad_acc = ad_acc;
	}

	public User getAd_user() {
		return ad_user;
	}

	public void setAd_user(User ad_user) {
		this.ad_user = ad_user;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	/**  */
	@TableId
	private Long id;

	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	@TableField(value = "area_info")
	private String area_info;

	/**  */
	private String mobile;

	/**  */
	private String telephone;

	/**  */
	private String trueName;

	/**  */
	private String zip;

	/**  */
	@TableField(value = "area_id")
	private Long area_id;

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

	public String getArea_info() {
		return this.area_info;
	}

	public void setArea_info(String area_info) {
		this.area_info = area_info;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Long getArea_id() {
		return this.area_id;
	}

	public void setArea_id(Long area_id) {
		this.area_id = area_id;
	}

	public Long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

}
