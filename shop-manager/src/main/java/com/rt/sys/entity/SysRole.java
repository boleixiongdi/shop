package com.rt.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 角色表
 *
 */
@TableName(value = "sys_role")
public class SysRole implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	@TableField(exist = false)
	    private Long[] resourceIds; //持有的资源
	@TableField(exist = false)
	    private Long[] officeIds; //机构
	@TableField(exist = false)
	    private Long[] userIds; //绑定的用户
	/** 编号 */
	@TableId
	private Long id;

	/** 归属机构 */
	@TableField(value = "office_id")
	private Long officeId;

	/** 角色名称 */
	private String name;

	/** 数据范围 */
	@TableField(value = "data_scope")
	private String data_scope;

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

	
	public Long[] getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(Long[] resourceIds) {
		this.resourceIds = resourceIds;
	}

	public Long[] getOfficeIds() {
		return officeIds;
	}

	public void setOfficeIds(Long[] officeIds) {
		this.officeIds = officeIds;
	}

	public Long[] getUserIds() {
		return userIds;
	}

	public void setUserIds(Long[] userIds) {
		this.userIds = userIds;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Long officeId) {
		this.officeId = officeId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getData_scope() {
		return this.data_scope;
	}

	public void setData_scope(String data_scope) {
		this.data_scope = data_scope;
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
