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
@TableName(value = "shopping_spare_goods")
public class SpareGoods implements Serializable {

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
	private String contact;

	/**  */
	private String content;

	/**  */
	private String errorMessage;

	/**  */
	@TableField(value = "goods_old_price")
	private Integer goods_old_price;

	/**  */
	@TableField(value = "goods_price")
	private Integer goods_price;

	/**  */
	private String name;

	/**  */
	private Integer oldAndnew;

	/**  */
	private Integer sellMethod;

	/**  */
	private String seodescribe;

	/**  */
	private String seokeyword;

	/**  */
	private Integer status;

	/**  */
	private String title;

	/**  */
	@TableField(value = "area_id")
	private Long area_id;

	/**  */
	@TableField(value = "img_id")
	private Long img_id;

	/**  */
	@TableField(value = "spareGoodsClass_id")
	private Long spareGoodsClass_id;

	/**  */
	@TableField(value = "user_id")
	private Long user_id;

	/**  */
	private String phone;

	/**  */
	@TableField(value = "img1_id")
	private Long img1_id;

	/**  */
	@TableField(value = "img2_id")
	private Long img2_id;

	/**  */
	@TableField(value = "img3_id")
	private Long img3_id;

	/**  */
	@TableField(value = "img4_id")
	private Long img4_id;

	/**  */
	@TableField(value = "main_img_id")
	private Long main_img_id;

	/**  */
	@TableField(value = "img5_id")
	private Long img5_id;

	/**  */
	private Boolean viewInFloor;

	/**  */
	@TableField(value = "sgf_id")
	private Long sgf_id;

	/**  */
	private String QQ;

	/**  */
	private Boolean recommend;

	/**  */
	private Integer down;

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

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getGoods_old_price() {
		return this.goods_old_price;
	}

	public void setGoods_old_price(Integer goods_old_price) {
		this.goods_old_price = goods_old_price;
	}

	public Integer getGoods_price() {
		return this.goods_price;
	}

	public void setGoods_price(Integer goods_price) {
		this.goods_price = goods_price;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOldAndnew() {
		return this.oldAndnew;
	}

	public void setOldAndnew(Integer oldAndnew) {
		this.oldAndnew = oldAndnew;
	}

	public Integer getSellMethod() {
		return this.sellMethod;
	}

	public void setSellMethod(Integer sellMethod) {
		this.sellMethod = sellMethod;
	}

	public String getSeodescribe() {
		return this.seodescribe;
	}

	public void setSeodescribe(String seodescribe) {
		this.seodescribe = seodescribe;
	}

	public String getSeokeyword() {
		return this.seokeyword;
	}

	public void setSeokeyword(String seokeyword) {
		this.seokeyword = seokeyword;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getArea_id() {
		return this.area_id;
	}

	public void setArea_id(Long area_id) {
		this.area_id = area_id;
	}

	public Long getImg_id() {
		return this.img_id;
	}

	public void setImg_id(Long img_id) {
		this.img_id = img_id;
	}

	public Long getSpareGoodsClass_id() {
		return this.spareGoodsClass_id;
	}

	public void setSpareGoodsClass_id(Long spareGoodsClass_id) {
		this.spareGoodsClass_id = spareGoodsClass_id;
	}

	public Long getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getImg1_id() {
		return this.img1_id;
	}

	public void setImg1_id(Long img1_id) {
		this.img1_id = img1_id;
	}

	public Long getImg2_id() {
		return this.img2_id;
	}

	public void setImg2_id(Long img2_id) {
		this.img2_id = img2_id;
	}

	public Long getImg3_id() {
		return this.img3_id;
	}

	public void setImg3_id(Long img3_id) {
		this.img3_id = img3_id;
	}

	public Long getImg4_id() {
		return this.img4_id;
	}

	public void setImg4_id(Long img4_id) {
		this.img4_id = img4_id;
	}

	public Long getMain_img_id() {
		return this.main_img_id;
	}

	public void setMain_img_id(Long main_img_id) {
		this.main_img_id = main_img_id;
	}

	public Long getImg5_id() {
		return this.img5_id;
	}

	public void setImg5_id(Long img5_id) {
		this.img5_id = img5_id;
	}

	public Boolean getViewInFloor() {
		return this.viewInFloor;
	}

	public void setViewInFloor(Boolean viewInFloor) {
		this.viewInFloor = viewInFloor;
	}

	public Long getSgf_id() {
		return this.sgf_id;
	}

	public void setSgf_id(Long sgf_id) {
		this.sgf_id = sgf_id;
	}

	public String getQQ() {
		return this.QQ;
	}

	public void setQQ(String QQ) {
		this.QQ = QQ;
	}

	public Boolean getRecommend() {
		return this.recommend;
	}

	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}

	public Integer getDown() {
		return this.down;
	}

	public void setDown(Integer down) {
		this.down = down;
	}

}
