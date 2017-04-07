package com.rt.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 角色-机构
 *
 */
@TableName(value = "sys_role_office")
public class SysRoleOffice implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 角色编号 */
	@TableField(value = "role_id")
	private Long role_id;

	/** 机构编号 */
	@TableField(value = "office_id")
	private Long office_id;

	/**  */
	@TableId
	private Long id;

	/**  */
	@TableField(value = "create_by")
	private String create_by;

	/**  */
	@TableField(value = "create_date")
	private Date create_date;

	/**  */
	@TableField(value = "update_by")
	private String update_by;

	/**  */
	@TableField(value = "update_date")
	private Date update_date;

	/**  */
	@TableField(value = "del_flag")
	private String del_flag;

	public Long getRole_id() {
		return this.role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

	public Long getOffice_id() {
		return this.office_id;
	}

	public void setOffice_id(Long office_id) {
		this.office_id = office_id;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDel_flag() {
		return this.del_flag;
	}

	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}

}
