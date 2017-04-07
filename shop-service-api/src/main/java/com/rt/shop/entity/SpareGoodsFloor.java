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
@TableName(value = "shopping_spare_goodsfloor")
public class SpareGoodsFloor implements Serializable {

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
	@TableField(value = "adver_id")
	private String adver_id;

	/**  */
	private Integer sequence;

	/**  */
	private String title;

	/**  */
	private Integer visable;

	/**  */
	@TableField(value = "sgc_id")
	private Long sgc_id;

	/**  */
	@TableField(value = "adver_type")
	private Integer adver_type;

	/**  */
	@TableField(value = "advert_url")
	private String advert_url;

	/**  */
	@TableField(value = "advert_id")
	private Long advert_id;

	/**  */
	@TableField(value = "advert_img_id")
	private Long advert_img_id;

	/**  */
	private Boolean display;

	/**  */
	@TableField(value = "adp_id")
	private Long adp_id;

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

	public String getAdver_id() {
		return this.adver_id;
	}

	public void setAdver_id(String adver_id) {
		this.adver_id = adver_id;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getVisable() {
		return this.visable;
	}

	public void setVisable(Integer visable) {
		this.visable = visable;
	}

	public Long getSgc_id() {
		return this.sgc_id;
	}

	public void setSgc_id(Long sgc_id) {
		this.sgc_id = sgc_id;
	}

	public Integer getAdver_type() {
		return this.adver_type;
	}

	public void setAdver_type(Integer adver_type) {
		this.adver_type = adver_type;
	}

	public String getAdvert_url() {
		return this.advert_url;
	}

	public void setAdvert_url(String advert_url) {
		this.advert_url = advert_url;
	}

	public Long getAdvert_id() {
		return this.advert_id;
	}

	public void setAdvert_id(Long advert_id) {
		this.advert_id = advert_id;
	}

	public Long getAdvert_img_id() {
		return this.advert_img_id;
	}

	public void setAdvert_img_id(Long advert_img_id) {
		this.advert_img_id = advert_img_id;
	}

	public Boolean getDisplay() {
		return this.display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public Long getAdp_id() {
		return this.adp_id;
	}

	public void setAdp_id(Long adp_id) {
		this.adp_id = adp_id;
	}

}
