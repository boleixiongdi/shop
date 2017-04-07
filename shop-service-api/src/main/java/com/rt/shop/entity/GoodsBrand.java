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
@TableName(value = "shopping_goodsbrand")
public class GoodsBrand implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	@TableField(exist = false)
	private Accessory brandLogo;
	@TableField(exist = false)
	private User user;
	
	
	@TableField(exist = false)
	private List<GoodsType> types = new ArrayList<GoodsType>();
	
	//商品品牌种类
	@TableField(exist = false)
	private Brandcategory category;
	
	//商品集合
	@TableField(exist = false)
	private List<Goods> goods_list = new ArrayList<Goods>();
	
	
	public Accessory getBrandLogo() {
		return brandLogo;
	}

	public void setBrandLogo(Accessory brandLogo) {
		this.brandLogo = brandLogo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<GoodsType> getTypes() {
		return types;
	}

	public void setTypes(List<GoodsType> types) {
		this.types = types;
	}

	public Brandcategory getCategory() {
		return category;
	}

	public void setCategory(Brandcategory category) {
		this.category = category;
	}

	public List<Goods> getGoods_list() {
		return goods_list;
	}

	public void setGoods_list(List<Goods> goods_list) {
		this.goods_list = goods_list;
	}

	/**  */
	@TableId
	private Long id;

	/**  */
	private Date addTime;

	/**  */
	private Boolean deleteStatus;

	/**  */
	private int audit;

	/**  */
	private String name;

	/**  */
	private Boolean recommend;

	/**  */
	private Integer sequence;

	/**  */
	@TableField(value = "brandLogo_id")
	private Long brandLogo_id;

	/**  */
	@TableField(value = "category_id")
	private Long category_id;

	/**  */
	private String remark;

	/**  */
	private Integer userStatus;

	/**  */
	@TableField(value = "user_id")
	private Long user_id;

	/**  */
	@TableField(value = "weixin_shop_recommend")
	private Boolean weixin_shop_recommend;

	/**  */
	@TableField(value = "weixin_shop_recommendTime")
	private Date weixin_shop_recommendTime;

	/**  */
	@TableField(value = "first_word")
	private String first_word;

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

	public int getAudit() {
		return this.audit;
	}

	public void setAudit(int audit) {
		this.audit = audit;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getRecommend() {
		return this.recommend;
	}

	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Long getBrandLogo_id() {
		return this.brandLogo_id;
	}

	public void setBrandLogo_id(Long brandLogo_id) {
		this.brandLogo_id = brandLogo_id;
	}

	public Long getCategory_id() {
		return this.category_id;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public Long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Boolean getWeixin_shop_recommend() {
		return this.weixin_shop_recommend;
	}

	public void setWeixin_shop_recommend(Boolean weixin_shop_recommend) {
		this.weixin_shop_recommend = weixin_shop_recommend;
	}

	public Date getWeixin_shop_recommendTime() {
		return this.weixin_shop_recommendTime;
	}

	public void setWeixin_shop_recommendTime(Date weixin_shop_recommendTime) {
		this.weixin_shop_recommendTime = weixin_shop_recommendTime;
	}

	public String getFirst_word() {
		return this.first_word;
	}

	public void setFirst_word(String first_word) {
		this.first_word = first_word;
	}

}
