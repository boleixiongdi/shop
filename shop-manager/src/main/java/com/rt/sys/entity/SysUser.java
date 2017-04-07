package com.rt.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.rt.common.constant.Constant;

/**
 *
 * 用户表
 *
 */
@TableName(value = "sys_user")
public class SysUser implements Serializable {

	@TableField(exist = false)
    private Long[] roleIds; //角色
    //是否是超级管理员
    public boolean isAdmin(){
    	return Constant.SUPER_ADMIN.equals(this.getUser_type())?true:false;
    }
    
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	
	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	/** 编号 */
	@TableId
	private Long id;

	/** 归属公司 */
	@TableField(value = "company_id")
	private Long companyId;

	/** 归属部门 */
	@TableField(value = "office_id")
	private Long officeId;

	/** 登录名 */
	private String username;

	/** 密码 */
	private String password;

	/** 工号 */
	private String no;

	/** 姓名 */
	private String name;

	/** 邮箱 */
	private String email;

	/** 电话 */
	private String phone;

	/** 手机 */
	private String mobile;

	/** 用户类型(0.普通 1.系统超级管理员) */
	@TableField(value = "user_type")
	private String user_type;

	/** 最后登陆IP */
	@TableField(value = "login_ip")
	private String login_ip;

	/** 最后登陆时间 */
	@TableField(value = "login_date")
	private Date login_date;

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
	private String status;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Long officeId) {
		this.officeId = officeId;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUser_type() {
		return this.user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getLogin_ip() {
		return this.login_ip;
	}

	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}

	public Date getLogin_date() {
		return this.login_date;
	}

	public void setLogin_date(Date login_date) {
		this.login_date = login_date;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
