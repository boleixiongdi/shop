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
@TableName(value = "shopping_dynamic")
public class Dynamic implements Serializable {
	@TableField(exist = false)
	private Goods goods;
	@TableField(exist = false)
	private Dynamic dissParent;
	
	//返回父类
	@TableField(exist = false)
	private Dynamic turnParent;
	
	//动态子类
	@TableField(exist = false)
	List<Dynamic> childs = new ArrayList<Dynamic>();

	//店铺
	@TableField(exist = false)
	private Store store;
	
	//用户
	@TableField(exist = false)
	private User user;
	
	
	//图片
	@TableField(exist = false)
	private Accessory img;
	
	
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Dynamic getDissParent() {
		return dissParent;
	}

	public void setDissParent(Dynamic dissParent) {
		this.dissParent = dissParent;
	}

	public Dynamic getTurnParent() {
		return turnParent;
	}

	public void setTurnParent(Dynamic turnParent) {
		this.turnParent = turnParent;
	}

	public List<Dynamic> getChilds() {
		return childs;
	}

	public void setChilds(List<Dynamic> childs) {
		this.childs = childs;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Accessory getImg() {
		return img;
	}

	public void setImg(Accessory img) {
		this.img = img;
	}

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
	private String content;

	/**  */
	private Integer discussNum;

	/**  */
	private Integer praiseNum;

	/**  */
	private Integer turnNum;

	/**  */
	@TableField(value = "dissParent_id")
	private Long dissParent_id;

	/**  */
	@TableField(value = "goods_id")
	private Long goods_id;

	/**  */
	@TableField(value = "turnParent_id")
	private Long turnParent_id;

	/**  */
	@TableField(value = "user_id")
	private Long user_id;

	/**  */
	private Boolean locked;

	/**  */
	@TableField(value = "img_id")
	private Long img_id;

	/**  */
	@TableField(value = "store_id")
	private Long store_id;

	/**  */
	private Boolean display;

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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getDiscussNum() {
		return this.discussNum;
	}

	public void setDiscussNum(Integer discussNum) {
		this.discussNum = discussNum;
	}

	public Integer getPraiseNum() {
		return this.praiseNum;
	}

	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}

	public Integer getTurnNum() {
		return this.turnNum;
	}

	public void setTurnNum(Integer turnNum) {
		this.turnNum = turnNum;
	}

	public Long getDissParent_id() {
		return this.dissParent_id;
	}

	public void setDissParent_id(Long dissParent_id) {
		this.dissParent_id = dissParent_id;
	}

	public Long getGoods_id() {
		return this.goods_id;
	}

	public void setGoods_id(Long goods_id) {
		this.goods_id = goods_id;
	}

	public Long getTurnParent_id() {
		return this.turnParent_id;
	}

	public void setTurnParent_id(Long turnParent_id) {
		this.turnParent_id = turnParent_id;
	}

	public Long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Boolean getLocked() {
		return this.locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Long getImg_id() {
		return this.img_id;
	}

	public void setImg_id(Long img_id) {
		this.img_id = img_id;
	}

	public Long getStore_id() {
		return this.store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public Boolean getDisplay() {
		return this.display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

}
