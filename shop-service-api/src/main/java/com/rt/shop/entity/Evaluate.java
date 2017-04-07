package com.rt.shop.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName(value = "shopping_evaluate")
public class Evaluate implements Serializable {
	@TableField(exist = false)
	private Goods evaluate_goods;
	@TableField(exist = false)
	private User evaluate_user;
	
	//卖家评价
	@TableField(exist = false)
	private User evaluate_seller_user;
	
	public Goods getEvaluate_goods() {
		return evaluate_goods;
	}

	public void setEvaluate_goods(Goods evaluate_goods) {
		this.evaluate_goods = evaluate_goods;
	}

	public User getEvaluate_user() {
		return evaluate_user;
	}

	public void setEvaluate_user(User evaluate_user) {
		this.evaluate_user = evaluate_user;
	}

	public User getEvaluate_seller_user() {
		return evaluate_seller_user;
	}

	public void setEvaluate_seller_user(User evaluate_seller_user) {
		this.evaluate_seller_user = evaluate_seller_user;
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
	@TableField(value = "evaluate_admin_info")
	private String evaluate_admin_info;

	/**  */
	@TableField(value = "evaluate_buyer_val")
	private Integer evaluate_buyer_val;

	/**  */
	@TableField(value = "evaluate_info")
	private String evaluate_info;

	/**  */
	@TableField(value = "evaluate_seller_info")
	private String evaluate_seller_info;

	/**  */
	@TableField(value = "evaluate_seller_time")
	private Date evaluate_seller_time;

	/**  */
	@TableField(value = "evaluate_seller_val")
	private Integer evaluate_seller_val;

	/**  */
	@TableField(value = "evaluate_status")
	private Integer evaluate_status;

	/**  */
	@TableField(value = "evaluate_type")
	private String evaluate_type;

	/**  */
	@TableField(value = "goods_spec")
	private String goods_spec;

	/**  */
	@TableField(value = "evaluate_goods_id")
	private Long evaluate_goods_id;

	/**  */
	@TableField(value = "evaluate_seller_user_id")
	private Long evaluate_seller_user_id;

	/**  */
	@TableField(value = "evaluate_user_id")
	private Long evaluate_user_id;

	/**  */
	@TableField(value = "of_id")
	private Long of_id;

	/**  */
	@TableField(value = "description_evaluate")
	private BigDecimal description_evaluate;

	/**  */
	@TableField(value = "service_evaluate")
	private BigDecimal service_evaluate;

	/**  */
	@TableField(value = "ship_evaluate")
	private BigDecimal ship_evaluate;

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

	public String getEvaluate_admin_info() {
		return this.evaluate_admin_info;
	}

	public void setEvaluate_admin_info(String evaluate_admin_info) {
		this.evaluate_admin_info = evaluate_admin_info;
	}

	public Integer getEvaluate_buyer_val() {
		return this.evaluate_buyer_val;
	}

	public void setEvaluate_buyer_val(Integer evaluate_buyer_val) {
		this.evaluate_buyer_val = evaluate_buyer_val;
	}

	public String getEvaluate_info() {
		return this.evaluate_info;
	}

	public void setEvaluate_info(String evaluate_info) {
		this.evaluate_info = evaluate_info;
	}

	public String getEvaluate_seller_info() {
		return this.evaluate_seller_info;
	}

	public void setEvaluate_seller_info(String evaluate_seller_info) {
		this.evaluate_seller_info = evaluate_seller_info;
	}

	public Date getEvaluate_seller_time() {
		return this.evaluate_seller_time;
	}

	public void setEvaluate_seller_time(Date evaluate_seller_time) {
		this.evaluate_seller_time = evaluate_seller_time;
	}

	public Integer getEvaluate_seller_val() {
		return this.evaluate_seller_val;
	}

	public void setEvaluate_seller_val(Integer evaluate_seller_val) {
		this.evaluate_seller_val = evaluate_seller_val;
	}

	public Integer getEvaluate_status() {
		return this.evaluate_status;
	}

	public void setEvaluate_status(Integer evaluate_status) {
		this.evaluate_status = evaluate_status;
	}

	public String getEvaluate_type() {
		return this.evaluate_type;
	}

	public void setEvaluate_type(String evaluate_type) {
		this.evaluate_type = evaluate_type;
	}

	public String getGoods_spec() {
		return this.goods_spec;
	}

	public void setGoods_spec(String goods_spec) {
		this.goods_spec = goods_spec;
	}

	public Long getEvaluate_goods_id() {
		return this.evaluate_goods_id;
	}

	public void setEvaluate_goods_id(Long evaluate_goods_id) {
		this.evaluate_goods_id = evaluate_goods_id;
	}

	public Long getEvaluate_seller_user_id() {
		return this.evaluate_seller_user_id;
	}

	public void setEvaluate_seller_user_id(Long evaluate_seller_user_id) {
		this.evaluate_seller_user_id = evaluate_seller_user_id;
	}

	public Long getEvaluate_user_id() {
		return this.evaluate_user_id;
	}

	public void setEvaluate_user_id(Long evaluate_user_id) {
		this.evaluate_user_id = evaluate_user_id;
	}

	public Long getOf_id() {
		return this.of_id;
	}

	public void setOf_id(Long of_id) {
		this.of_id = of_id;
	}

	public BigDecimal getDescription_evaluate() {
		return this.description_evaluate;
	}

	public void setDescription_evaluate(BigDecimal description_evaluate) {
		this.description_evaluate = description_evaluate;
	}

	public BigDecimal getService_evaluate() {
		return this.service_evaluate;
	}

	public void setService_evaluate(BigDecimal service_evaluate) {
		this.service_evaluate = service_evaluate;
	}

	public BigDecimal getShip_evaluate() {
		return this.ship_evaluate;
	}

	public void setShip_evaluate(BigDecimal ship_evaluate) {
		this.ship_evaluate = ship_evaluate;
	}

}
