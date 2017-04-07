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
@TableName(value = "shopping_album")
public class Album implements Serializable {

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
	@TableField(value = "alblum_info")
	private String alblum_info;

	/**  */
	@TableField(value = "album_default")
	private Boolean album_default;

	/**  */
	@TableField(value = "album_name")
	private String album_name;

	/**  */
	@TableField(value = "album_sequence")
	private Integer album_sequence;

	/**  */
	@TableField(value = "album_cover_id")
	private Long album_cover_id;

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

	public String getAlblum_info() {
		return this.alblum_info;
	}

	public void setAlblum_info(String alblum_info) {
		this.alblum_info = alblum_info;
	}

	public Boolean getAlbum_default() {
		return this.album_default;
	}

	public void setAlbum_default(Boolean album_default) {
		this.album_default = album_default;
	}

	public String getAlbum_name() {
		return this.album_name;
	}

	public void setAlbum_name(String album_name) {
		this.album_name = album_name;
	}

	public Integer getAlbum_sequence() {
		return this.album_sequence;
	}

	public void setAlbum_sequence(Integer album_sequence) {
		this.album_sequence = album_sequence;
	}

	public Long getAlbum_cover_id() {
		return this.album_cover_id;
	}

	public void setAlbum_cover_id(Long album_cover_id) {
		this.album_cover_id = album_cover_id;
	}

	public Long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

}
