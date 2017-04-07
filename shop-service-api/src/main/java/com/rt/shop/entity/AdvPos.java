package com.rt.shop.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName(value = "shopping_adv_pos")
public class AdvPos implements Serializable {
	@TableField(exist = false)
	private List<Accessory> photos = new ArrayList<Accessory>();
	//相册
	@TableField(exist = false)
	private Accessory album_cover;
	
	//相册所属人
	@TableField(exist = false)
	private User user;
	
	
	public List<Accessory> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Accessory> photos) {
		this.photos = photos;
	}

	public Accessory getAlbum_cover() {
		return album_cover;
	}

	public void setAlbum_cover(Accessory album_cover) {
		this.album_cover = album_cover;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId
	private Long id;
	@TableField(exist = false)
	private Accessory ap_acc;
	@TableField(exist = false)
	private List<Advert> advs = new ArrayList<Advert>();
	
	public Accessory getAp_acc() {
		return ap_acc;
	}

	public void setAp_acc(Accessory ap_acc) {
		this.ap_acc = ap_acc;
	}


	@TableField(exist = false)
	private String adv_id;
	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	@TableField(value = "ap_acc_url")
	private String ap_acc_url;

	/**  */
	@TableField(value = "ap_code")
	private String ap_code;

	/**  */
	@TableField(value = "ap_content")
	private String ap_content;

	/**  */
	@TableField(value = "ap_height")
	private Integer ap_height;

	/**  */
	@TableField(value = "ap_price")
	private Integer ap_price;

	/**  */
	@TableField(value = "ap_sale_type")
	private Integer ap_sale_type;

	/**  */
	@TableField(value = "ap_show_type")
	private Integer ap_show_type;

	/**  */
	@TableField(value = "ap_status")
	private Integer ap_status;

	/**  */
	@TableField(value = "ap_sys_type")
	private Integer ap_sys_type;

	/**  */
	@TableField(value = "ap_text")
	private String ap_text;

	/**  */
	@TableField(value = "ap_title")
	private String ap_title;

	/**  */
	@TableField(value = "ap_type")
	private String ap_type;

	/**  */
	@TableField(value = "ap_use_status")
	private Integer ap_use_status;

	/**  */
	@TableField(value = "ap_width")
	private Integer ap_width;

	
	public List<Advert> getAdvs() {
		return advs;
	}

	public void setAdvs(List<Advert> advs) {
		this.advs = advs;
	}

	
	public String getAdv_id() {
		return adv_id;
	}

	public void setAdv_id(String adv_id) {
		this.adv_id = adv_id;
	}


	/**  */
	@TableField(value = "ap_acc_id")
	private Long ap_acc_id;

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

	public String getAp_acc_url() {
		return this.ap_acc_url;
	}

	public void setAp_acc_url(String ap_acc_url) {
		this.ap_acc_url = ap_acc_url;
	}

	public String getAp_code() {
		return this.ap_code;
	}

	public void setAp_code(String ap_code) {
		this.ap_code = ap_code;
	}

	public String getAp_content() {
		return this.ap_content;
	}

	public void setAp_content(String ap_content) {
		this.ap_content = ap_content;
	}

	public Integer getAp_height() {
		return this.ap_height;
	}

	public void setAp_height(Integer ap_height) {
		this.ap_height = ap_height;
	}

	public Integer getAp_price() {
		return this.ap_price;
	}

	public void setAp_price(Integer ap_price) {
		this.ap_price = ap_price;
	}

	public Integer getAp_sale_type() {
		return this.ap_sale_type;
	}

	public void setAp_sale_type(Integer ap_sale_type) {
		this.ap_sale_type = ap_sale_type;
	}

	public Integer getAp_show_type() {
		return this.ap_show_type;
	}

	public void setAp_show_type(Integer ap_show_type) {
		this.ap_show_type = ap_show_type;
	}

	public Integer getAp_status() {
		return this.ap_status;
	}

	public void setAp_status(Integer ap_status) {
		this.ap_status = ap_status;
	}

	public Integer getAp_sys_type() {
		return this.ap_sys_type;
	}

	public void setAp_sys_type(Integer ap_sys_type) {
		this.ap_sys_type = ap_sys_type;
	}

	public String getAp_text() {
		return this.ap_text;
	}

	public void setAp_text(String ap_text) {
		this.ap_text = ap_text;
	}

	public String getAp_title() {
		return this.ap_title;
	}

	public void setAp_title(String ap_title) {
		this.ap_title = ap_title;
	}

	public String getAp_type() {
		return this.ap_type;
	}

	public void setAp_type(String ap_type) {
		this.ap_type = ap_type;
	}

	public Integer getAp_use_status() {
		return this.ap_use_status;
	}

	public void setAp_use_status(Integer ap_use_status) {
		this.ap_use_status = ap_use_status;
	}

	public Integer getAp_width() {
		return this.ap_width;
	}

	public void setAp_width(Integer ap_width) {
		this.ap_width = ap_width;
	}

	public Long getAp_acc_id() {
		return this.ap_acc_id;
	}

	public void setAp_acc_id(Long ap_acc_id) {
		this.ap_acc_id = ap_acc_id;
	}

}
