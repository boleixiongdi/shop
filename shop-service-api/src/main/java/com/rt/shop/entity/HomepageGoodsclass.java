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
@TableName(value = "shopping_homepage_goodsclass")
public class HomepageGoodsclass implements Serializable {
	@TableField(exist = false)
	 private User user;
	   //商品类型
		@TableField(exist = false)
	   private GoodsClass gc;
	   
	public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public GoodsClass getGc() {
			return gc;
		}

		public void setGc(GoodsClass gc) {
			this.gc = gc;
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
	@TableField(value = "user_id")
	private Long user_id;

	/**  */
	@TableField(value = "gc_id")
	private Long gc_id;

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

	public Long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getGc_id() {
		return this.gc_id;
	}

	public void setGc_id(Long gc_id) {
		this.gc_id = gc_id;
	}

}
