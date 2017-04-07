package com.rt.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.rt.common.base.BaseEntity;

/**
 *
 * 
 *
 */
@TableName(value = "sys_resource")
public class SysResource extends BaseEntity {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	@TableField(exist = false)
    private String oldParentIds; //旧的pids,非表中字段，用作更新用
	
	public String getOldParentIds() {
		return oldParentIds;
	}

	public void setOldParentIds(String oldParentIds) {
		this.oldParentIds = oldParentIds;
	}

	/**  */
	@TableId
	private Long id;

	/** 资源名称 */
	private String name;

	/** 是否是公共资源(0.不是 1.是) */
	private String common;

	/** 图标 */
	private String icon;

	/** 排序号 */
	private Integer sort;

	/** 父级id */
	@TableField(value = "parent_id")
	private Long parent_id;

	/** 类型(0.菜单 1.按钮) */
	private String type;

	/** 链接 */
	private String url;

	/** 描述 */
	private String description;

	/** 状态(0.正常 1.禁用) */
	private String status;

	/** 父级集合 */
	@TableField(value = "parent_ids")
	private String parent_ids;

	/**  */
	@TableField(value = "create_date")
	private Date create_date;

	/**  */
	@TableField(value = "update_date")
	private Date update_date;

	/**  */
	@TableField(value = "create_by")
	private String create_by;

	/**  */
	@TableField(value = "update_by")
	private String update_by;

	/**  */
	@TableField(value = "del_flag")
	private String del_flag;

	/**  */
	@TableField(value = "permission_str")
	private String permission_str;

	public Long getId() {
		return this.getLong("id");
    }
   
    public void setId(Long id) {
		this.set("id", id);
    }
	public String getCommon() {
		return this.getString("common");
    }
   
    public void setCommon(String common) {
		this.set("common", common);
    }

	public String getDescription() {
		return this.getString("description");
    }
   
    public void setDescription(String description) {
		this.set("description", description);
    }

	public String getIcon() {
		return this.getString("icon");
    }
   
    public void setIcon(String icon) {
		this.set("icon", icon);
    }

	public String getName() {
		return this.getString("name");
    }
   
    public void setName(String name) {
		this.set("name", name);
    }

	public Long getParentId() {
		return this.getLong("parentId");
    }
   
    public void setParentId(Long parentId) {
		this.set("parentId", parentId);
    }

	public Integer getSort() {
		return this.getInteger("sort");
    }
   
    public void setSort(Integer sort) {
		this.set("sort", sort);
    }

	public String getStatus() {
		return this.getString("status");
    }
   
    public void setStatus(String status) {
		this.set("status", status);
    }

	public String getType() {
		return this.getString("type");
    }
   
    public void setType(String type) {
		this.set("type", type);
    }

	public String getUrl() {
		return this.getString("url");
    }
   
    public void setUrl(String url) {
		this.set("url", url);
    }

    public String getParentIds() {
		return this.getString("parentIds");
    }
   
    public void setParentIds(String parentIds) {
		this.set("parentIds", parentIds);
    }
    
    
    
    public String getCreateBy() {
		return this.getString("createBy");
    }
   
    public void setCreateBy(String createBy) {
		this.set("createBy", createBy);
    }

	public Date getCreateDate() {
		return this.getDate("createDate");
    }
   
    public void setCreateDate(Date createDate) {
		this.set("createDate", createDate);
    }
    public String getUpdateBy() {
		return this.getString("updateBy");
    }
   
    public void setUpdateBy(String updateBy) {
		this.set("updateBy", updateBy);
    }

	public Date getUpdateDate() {
		return this.getDate("updateDate");
    }
   
    public void setUpdateDate(Date updateDate) {
		this.set("updateDate", updateDate);
    }

    public String getDelFlag() {
		return this.getString("delFlag");
    }
   
    public void setDelFlag(String delFlag) {
		this.set("delFlag", delFlag);
    }
}
