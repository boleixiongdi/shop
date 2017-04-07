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
@TableName(value = "shopping_express_company")
public class ExpressCompany implements Serializable {
	@TableField(exist = false)
	List<OrderForm> ofs = new ArrayList<OrderForm>();
	
	public List<OrderForm> getOfs() {
		return ofs;
	}

	public void setOfs(List<OrderForm> ofs) {
		this.ofs = ofs;
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
	@TableField(value = "company_mark")
	private String company_mark;

	/**  */
	@TableField(value = "company_name")
	private String company_name;

	/**  */
	@TableField(value = "company_status")
	private Integer company_status;

	/**  */
	@TableField(value = "company_sequence")
	private Integer company_sequence;

	/**  */
	@TableField(value = "company_type")
	private String company_type;

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

	public String getCompany_mark() {
		return this.company_mark;
	}

	public void setCompany_mark(String company_mark) {
		this.company_mark = company_mark;
	}

	public String getCompany_name() {
		return this.company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public Integer getCompany_status() {
		return this.company_status;
	}

	public void setCompany_status(Integer company_status) {
		this.company_status = company_status;
	}

	public Integer getCompany_sequence() {
		return this.company_sequence;
	}

	public void setCompany_sequence(Integer company_sequence) {
		this.company_sequence = company_sequence;
	}

	public String getCompany_type() {
		return this.company_type;
	}

	public void setCompany_type(String company_type) {
		this.company_type = company_type;
	}

}
