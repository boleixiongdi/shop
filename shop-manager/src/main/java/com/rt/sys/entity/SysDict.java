package com.rt.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 字典表
 *
 */
@TableName(value = "sys_dict")
public class SysDict implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 编号 */
	@TableId
	private Long id;

	/** 标签名 */
	private String label;

	/** 数据值 */
	private String value;

	/** 类型 */
	private String type;

	/** 描述 */
	private String description;

	/** 排序（升序） */
	private Integer sort;

	/** 创建者 */
	@TableField(value = "create_by")
	private String create_by;

	/** 创建时间 */
	@TableField(value = "create_date")
	private Date create_date;

	/** 更新者 */
	@TableField(value = "update_by")
	private String update_by;

	/** 更新时间 */
	@TableField(value = "update_date")
	private Date update_date;

	/** 备注信息 */
	private String remarks;

	/** 删除标记 */
	@TableField(value = "del_flag")
	private String del_flag;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCreate_by() {
		return this.create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	public Date getCreate_date() {
		return this.create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getUpdate_by() {
		return this.update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public Date getUpdate_date() {
		return this.update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDel_flag() {
		return this.del_flag;
	}

	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}

}
