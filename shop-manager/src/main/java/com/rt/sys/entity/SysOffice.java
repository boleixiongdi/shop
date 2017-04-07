package com.rt.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 机构表
 *
 */
@TableName(value = "sys_office")
public class SysOffice implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	@TableField(exist = false)
	    private String oldParentIds; //旧的pids,非表中字段，用作更新用
	/** 编号 */
	@TableId
	private Long id;

	/** 父级编号 */
	@TableField(value = "parent_id")
	private Long parent_id;

	/** 所有父级编号 */
	@TableField(value = "parent_ids")
	private String parent_ids;

	/** 归属区域 */
	@TableField(value = "area_id")
	private Long area_id;

	/** 区域编码 */
	private String code;

	/** 机构名称 */
	private String name;

	/** 机构类型 */
	private String type;

	/** 机构等级 */
	private String grade;

	/** 联系地址 */
	private String address;

	/** 邮政编码 */
	@TableField(value = "zip_code")
	private String zip_code;

	
	public String getOldParentIds() {
		return oldParentIds;
	}

	public void setOldParentIds(String oldParentIds) {
		this.oldParentIds = oldParentIds;
	}

	/** 负责人 */
	private String master;

	/** 电话 */
	private String phone;

	/** 传真 */
	private String fax;

	/** 邮箱 */
	private String email;

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

	/**  */
	private String icon;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public String getParent_ids() {
		return this.parent_ids;
	}

	public void setParent_ids(String parent_ids) {
		this.parent_ids = parent_ids;
	}

	public Long getArea_id() {
		return this.area_id;
	}

	public void setArea_id(Long area_id) {
		this.area_id = area_id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip_code() {
		return this.zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public String getMaster() {
		return this.master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
