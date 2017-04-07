package com.rt.shop.entity;

import java.io.Serializable;
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
@TableName(value = "shopping_accessory")
public class Accessory implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	
	@TableField(exist = false)
	private User user;
	
	//相册
	@TableField(exist = false)
	private Album album;
	
	@TableField(exist = false)
	private Album cover_album;
	
	//系统配置
	@TableField(exist = false)
	private SysConfig config;

	//goods主列表
	@TableField(exist = false)
	private List<Goods> goods_main_list = new ArrayList<Goods>();

	//goods列表
	@TableField(exist = false)
	private List<Goods> goods_list = new ArrayList<Goods>();
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public Album getCover_album() {
		return cover_album;
	}

	public void setCover_album(Album cover_album) {
		this.cover_album = cover_album;
	}

	public SysConfig getConfig() {
		return config;
	}

	public void setConfig(SysConfig config) {
		this.config = config;
	}

	public List<Goods> getGoods_main_list() {
		return goods_main_list;
	}

	public void setGoods_main_list(List<Goods> goods_main_list) {
		this.goods_main_list = goods_main_list;
	}

	public List<Goods> getGoods_list() {
		return goods_list;
	}

	public void setGoods_list(List<Goods> goods_list) {
		this.goods_list = goods_list;
	}

	/**  */
	@TableId
	private Long id;

	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	private String ext;

	/**  */
	private Integer height;

	/**  */
	private String info;

	/**  */
	private String name;

	/**  */
	private String path;

	/**  */
	private Float size;

	/**  */
	private Integer width;

	/**  */
	@TableField(value = "album_id")
	private Long album_id;

	/**  */
	@TableField(value = "user_id")
	private Long user_id;

	/**  */
	@TableField(value = "config_id")
	private Long config_id;

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

	public String getExt() {
		return this.ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Float getSize() {
		return this.size;
	}

	public void setSize(Float size) {
		this.size = size;
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Long getAlbum_id() {
		return this.album_id;
	}

	public void setAlbum_id(Long album_id) {
		this.album_id = album_id;
	}

	public Long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getConfig_id() {
		return this.config_id;
	}

	public void setConfig_id(Long config_id) {
		this.config_id = config_id;
	}

}
