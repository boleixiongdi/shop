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
@TableName(value = "shopping_articleclass")
public class ArticleClass implements Serializable {
	@TableField(exist = false)
	private ArticleClass parent;
	
	//子类
	@TableField(exist = false)
	private List<ArticleClass> childs = new ArrayList<ArticleClass>();
	
	//文章
	@TableField(exist = false)
	private List<Article> articles = new ArrayList<Article>();
	
	
	public ArticleClass getParent() {
		return parent;
	}

	public void setParent(ArticleClass parent) {
		this.parent = parent;
	}

	public List<ArticleClass> getChilds() {
		return childs;
	}

	public void setChilds(List<ArticleClass> childs) {
		this.childs = childs;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
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
	private String className;

	/**  */
	private Integer level;

	/**  */
	private String mark;

	/**  */
	private Integer sequence;

	/**  */
	private Boolean sysClass;

	/**  */
	@TableField(value = "parent_id")
	private Long parent_id;

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

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Boolean getSysClass() {
		return this.sysClass;
	}

	public void setSysClass(Boolean sysClass) {
		this.sysClass = sysClass;
	}

	public Long getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

}
