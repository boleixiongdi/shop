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
@TableName(value = "shopping_goodsclass")
public class GoodsClass implements Serializable {

	public List<GoodsClass> getGcList() {
		return gcList;
	}

	public void setGcList(List<GoodsClass> gcList) {
		this.gcList = gcList;
	}
	
	public List<GoodsClass> getChilds() {
		return childs;
	}

	public void setChilds(List<GoodsClass> childs) {
		this.childs = childs;
	}
	
	public GoodsType getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}

	public List<Goodsclassstaple> getGcss() {
		return gcss;
	}

	public void setGcss(List<Goodsclassstaple> gcss) {
		this.gcss = gcss;
	}

	public Accessory getIcon_acc() {
		return icon_acc;
	}

	public void setIcon_acc(Accessory icon_acc) {
		this.icon_acc = icon_acc;
	}

	
	public GoodsClass getParent() {
		return parent;
	}

	public void setParent(GoodsClass parent) {
		this.parent = parent;
	}


	@TableField(exist = false)
	private GoodsType goodsType;
	@TableField(exist = false)
	private List<Goodsclassstaple> gcss = new ArrayList<Goodsclassstaple>();
	@TableField(exist = false)
	private Accessory icon_acc;
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	@TableField(exist = false)
	private List<GoodsClass> childs = new ArrayList<GoodsClass>();
	@TableField(exist = false)
	private GoodsClass parent;
	/**  */
	@TableId
	private Long id;
	@TableField(exist = false)
	List<GoodsClass> gcList;
	
	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	private String className;

	/**  */
	private Boolean display;

	/**  */
	private Integer level;

	/**  */
	private Boolean recommend;

	/**  */
	private Integer sequence;

	/**  */
	@TableField(value = "goodsType_id")
	private Long goodsType_id;

	/**  */
	@TableField(value = "parent_id")
	private Long parent_id;

	/**  */
	@TableField(value = "seo_description")
	private String seo_description;

	/**  */
	@TableField(value = "seo_keywords")
	private String seo_keywords;

	/**  */
	@TableField(value = "icon_sys")
	private String icon_sys;

	/**  */
	@TableField(value = "icon_type")
	private Integer icon_type;

	/**  */
	@TableField(value = "icon_acc_id")
	private Long icon_acc_id;

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

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Boolean getDisplay() {
		return this.display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getRecommend() {
		return this.recommend;
	}

	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Long getGoodsType_id() {
		return this.goodsType_id;
	}

	public void setGoodsType_id(Long goodsType_id) {
		this.goodsType_id = goodsType_id;
	}

	public Long getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public String getSeo_description() {
		return this.seo_description;
	}

	public void setSeo_description(String seo_description) {
		this.seo_description = seo_description;
	}

	public String getSeo_keywords() {
		return this.seo_keywords;
	}

	public void setSeo_keywords(String seo_keywords) {
		this.seo_keywords = seo_keywords;
	}

	public String getIcon_sys() {
		return this.icon_sys;
	}

	public void setIcon_sys(String icon_sys) {
		this.icon_sys = icon_sys;
	}

	public Integer getIcon_type() {
		return this.icon_type;
	}

	public void setIcon_type(Integer icon_type) {
		this.icon_type = icon_type;
	}

	public Long getIcon_acc_id() {
		return this.icon_acc_id;
	}

	public void setIcon_acc_id(Long icon_acc_id) {
		this.icon_acc_id = icon_acc_id;
	}

	@Override
	public String toString() {
		return "GoodsClass [goodsType=" + goodsType + ", gcss=" + gcss
				+ ", icon_acc=" + icon_acc + ", childs=" + childs + ", id="
				+ id + ", gcList=" + gcList + ", addTime=" + addTime
				+ ", deleteStatus=" + deleteStatus + ", className=" + className
				+ ", display=" + display + ", level=" + level + ", recommend="
				+ recommend + ", sequence=" + sequence + ", goodsType_id="
				+ goodsType_id + ", parent_id=" + parent_id
				+ ", seo_description=" + seo_description + ", seo_keywords="
				+ seo_keywords + ", icon_sys=" + icon_sys + ", icon_type="
				+ icon_type + ", icon_acc_id=" + icon_acc_id + "]";
	}

}
