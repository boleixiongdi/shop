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
@TableName(value = "shopping_storegrade")
public class StoreGrade implements Serializable {

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
	@TableField(value = "add_funciton")
	private String add_funciton;

	/**  */
	private Boolean audit;

	/**  */
	private String content;

	/**  */
	private Integer goodsCount;

	/**  */
	private Integer gradeLevel;

	/**  */
	private String gradeName;

	/**  */
	private String price;

	/**  */
	private Integer sequence;

	/**  */
	private Float spaceSize;

	/**  */
	private Boolean sysGrade;

	/**  */
	private String templates;

	/**  */
	@TableField(value = "acount_num")
	private Integer acount_num;

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

	public String getAdd_funciton() {
		return this.add_funciton;
	}

	public void setAdd_funciton(String add_funciton) {
		this.add_funciton = add_funciton;
	}

	public Boolean getAudit() {
		return this.audit;
	}

	public void setAudit(Boolean audit) {
		this.audit = audit;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getGoodsCount() {
		return this.goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	public Integer getGradeLevel() {
		return this.gradeLevel;
	}

	public void setGradeLevel(Integer gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	public String getGradeName() {
		return this.gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Float getSpaceSize() {
		return this.spaceSize;
	}

	public void setSpaceSize(Float spaceSize) {
		this.spaceSize = spaceSize;
	}

	public Boolean getSysGrade() {
		return this.sysGrade;
	}

	public void setSysGrade(Boolean sysGrade) {
		this.sysGrade = sysGrade;
	}

	public String getTemplates() {
		return this.templates;
	}

	public void setTemplates(String templates) {
		this.templates = templates;
	}

	public Integer getAcount_num() {
		return this.acount_num;
	}

	public void setAcount_num(Integer acount_num) {
		this.acount_num = acount_num;
	}

}
