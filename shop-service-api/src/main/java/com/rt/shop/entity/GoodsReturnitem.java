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
@TableName(value = "shopping_goods_returnitem")
public class GoodsReturnitem implements Serializable {
	@TableField(exist = false)
	 private Goods goods;
	   //返回商品
	 @TableField(exist = false)
	   private GoodsReturn gr;
	 @TableField(exist = false)
	   private List<GoodsSpecProperty> gsps = new ArrayList<GoodsSpecProperty>();
	 
	   
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public GoodsReturn getGr() {
		return gr;
	}

	public void setGr(GoodsReturn gr) {
		this.gr = gr;
	}

	public List<GoodsSpecProperty> getGsps() {
		return gsps;
	}

	public void setGsps(List<GoodsSpecProperty> gsps) {
		this.gsps = gsps;
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
	private Integer count;

	/**  */
	@TableField(value = "spec_info")
	private String spec_info;

	/**  */
	@TableField(value = "goods_id")
	private Long goods_id;

	/**  */
	@TableField(value = "gr_id")
	private Long gr_id;

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

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getSpec_info() {
		return this.spec_info;
	}

	public void setSpec_info(String spec_info) {
		this.spec_info = spec_info;
	}

	public Long getGoods_id() {
		return this.goods_id;
	}

	public void setGoods_id(Long goods_id) {
		this.goods_id = goods_id;
	}

	public Long getGr_id() {
		return this.gr_id;
	}

	public void setGr_id(Long gr_id) {
		this.gr_id = gr_id;
	}

}
