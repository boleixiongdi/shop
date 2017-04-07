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
@TableName(value = "shopping_complaint")
public class Complaint implements Serializable {
	@TableField(exist = false)
	private Goods goods;
	
	@TableField(exist = false)
	private Complaint complaint;
	
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Complaint getComplaint() {
		return complaint;
	}

	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
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
	@TableField(value = "appeal_time")
	private Date appeal_time;

	/**  */
	@TableField(value = "from_user_content")
	private String from_user_content;

	/**  */
	@TableField(value = "handle_content")
	private String handle_content;

	/**  */
	@TableField(value = "handle_time")
	private Date handle_time;

	/**  */
	private Integer status;

	/**  */
	@TableField(value = "talk_content")
	private String talk_content;

	/**  */
	@TableField(value = "to_user_content")
	private String to_user_content;

	/**  */
	private String type;

	/**  */
	@TableField(value = "cs_id")
	private Long cs_id;

	/**  */
	@TableField(value = "from_acc1_id")
	private Long from_acc1_id;

	/**  */
	@TableField(value = "from_acc2_id")
	private Long from_acc2_id;

	/**  */
	@TableField(value = "from_acc3_id")
	private Long from_acc3_id;

	/**  */
	@TableField(value = "from_user_id")
	private Long from_user_id;

	/**  */
	@TableField(value = "handle_user_id")
	private Long handle_user_id;

	/**  */
	@TableField(value = "of_id")
	private Long of_id;

	/**  */
	@TableField(value = "to_acc1_id")
	private Long to_acc1_id;

	/**  */
	@TableField(value = "to_acc2_id")
	private Long to_acc2_id;

	/**  */
	@TableField(value = "to_acc3_id")
	private Long to_acc3_id;

	/**  */
	@TableField(value = "to_user_id")
	private Long to_user_id;

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

	public Date getAppeal_time() {
		return this.appeal_time;
	}

	public void setAppeal_time(Date appeal_time) {
		this.appeal_time = appeal_time;
	}

	public String getFrom_user_content() {
		return this.from_user_content;
	}

	public void setFrom_user_content(String from_user_content) {
		this.from_user_content = from_user_content;
	}

	public String getHandle_content() {
		return this.handle_content;
	}

	public void setHandle_content(String handle_content) {
		this.handle_content = handle_content;
	}

	public Date getHandle_time() {
		return this.handle_time;
	}

	public void setHandle_time(Date handle_time) {
		this.handle_time = handle_time;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTalk_content() {
		return this.talk_content;
	}

	public void setTalk_content(String talk_content) {
		this.talk_content = talk_content;
	}

	public String getTo_user_content() {
		return this.to_user_content;
	}

	public void setTo_user_content(String to_user_content) {
		this.to_user_content = to_user_content;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCs_id() {
		return this.cs_id;
	}

	public void setCs_id(Long cs_id) {
		this.cs_id = cs_id;
	}

	public Long getFrom_acc1_id() {
		return this.from_acc1_id;
	}

	public void setFrom_acc1_id(Long from_acc1_id) {
		this.from_acc1_id = from_acc1_id;
	}

	public Long getFrom_acc2_id() {
		return this.from_acc2_id;
	}

	public void setFrom_acc2_id(Long from_acc2_id) {
		this.from_acc2_id = from_acc2_id;
	}

	public Long getFrom_acc3_id() {
		return this.from_acc3_id;
	}

	public void setFrom_acc3_id(Long from_acc3_id) {
		this.from_acc3_id = from_acc3_id;
	}

	public Long getFrom_user_id() {
		return this.from_user_id;
	}

	public void setFrom_user_id(Long from_user_id) {
		this.from_user_id = from_user_id;
	}

	public Long getHandle_user_id() {
		return this.handle_user_id;
	}

	public void setHandle_user_id(Long handle_user_id) {
		this.handle_user_id = handle_user_id;
	}

	public Long getOf_id() {
		return this.of_id;
	}

	public void setOf_id(Long of_id) {
		this.of_id = of_id;
	}

	public Long getTo_acc1_id() {
		return this.to_acc1_id;
	}

	public void setTo_acc1_id(Long to_acc1_id) {
		this.to_acc1_id = to_acc1_id;
	}

	public Long getTo_acc2_id() {
		return this.to_acc2_id;
	}

	public void setTo_acc2_id(Long to_acc2_id) {
		this.to_acc2_id = to_acc2_id;
	}

	public Long getTo_acc3_id() {
		return this.to_acc3_id;
	}

	public void setTo_acc3_id(Long to_acc3_id) {
		this.to_acc3_id = to_acc3_id;
	}

	public Long getTo_user_id() {
		return this.to_user_id;
	}

	public void setTo_user_id(Long to_user_id) {
		this.to_user_id = to_user_id;
	}

}
