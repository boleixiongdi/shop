package com.rt.shop.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Transient;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName(value = "shopping_user")
public class User implements Serializable {
	@TableField(exist = false)
	private Accessory photo;

	// 地区
	@TableField(exist = false)
	private Area area;
	@TableField(exist = false)
	private List<Role> roles = new ArrayList<Role>();
	@TableField(exist = false)
	private UserConfig config;

	// 文件附件
	@TableField(exist = false)
	private List<Accessory> files = new ArrayList();

	// 商店
	@TableField(exist = false)
	private Store store;

	// 父用户
	@TableField(exist = false)
	private User parent;

	// 子用户
	@TableField(exist = false)
	private List<User> childs = new ArrayList();
	
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Accessory getPhoto() {
		return photo;
	}

	public void setPhoto(Accessory photo) {
		this.photo = photo;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public UserConfig getConfig() {
		return config;
	}

	public void setConfig(UserConfig config) {
		this.config = config;
	}

	public List<Accessory> getFiles() {
		return files;
	}

	public void setFiles(List<Accessory> files) {
		this.files = files;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public User getParent() {
		return parent;
	}

	public void setParent(User parent) {
		this.parent = parent;
	}

	public List<User> getChilds() {
		return childs;
	}

	public void setChilds(List<User> childs) {
		this.childs = childs;
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
	private String MSN;

	/**  */
	private String QQ;

	/**  */
	private String WW;

	/**  */
	private String address;

	/**  */
	private BigDecimal availableBalance;

	/**  */
	private Date birthday;

	/**  */
	private String email;

	/**  */
	private BigDecimal freezeBlance;

	/**  */
	private Integer gold;

	/**  */
	private Integer integral;

	/**  */
	private Date lastLoginDate;

	/**  */
	private String lastLoginIp;

	/**  */
	private Integer loginCount;

	/**  */
	private Date loginDate;

	/**  */
	private String loginIp;

	/**  */
	private String mobile;

	/**  */
	private String password;

	/**  */
	private Integer report;

	/**  */
	private Integer sex;

	/**  */
	private Integer status;

	/**  */
	private String telephone;

	/**  */
	private String trueName;

	/**  */
	private String userName;

	/**  */
	private String userRole;

	
	

	/**  */
	@TableField(value = "user_credit")
	private Integer user_credit;

	/**  */
	@TableField(value = "photo_id")
	private Long photo_id;

	/**  */
	@TableField(value = "store_id")
	private Long store_id;

	/**  */
	@TableField(value = "qq_openid")
	private String qq_openid;

	/**  */
	@TableField(value = "qq_binded")
	private String qq_binded;

	/**  */
	@TableField(value = "sina_openid")
	private String sina_openid;

	/**  */
	@TableField(value = "sina_binded")
	private String sina_binded;

	/**  */
	@TableField(value = "tianyuan_openid")
	private String tianyuan_openid;

	/**  */
	@TableField(value = "tianyuan_binded")
	private String tianyuan_binded;

	/**  */
	@TableField(value = "store_quick_menu")
	private String store_quick_menu;

	/**  */
	@TableField(value = "parent_id")
	private Long parent_id;

	/**  */
	private Integer years;

	/**  */
	@TableField(value = "area_id")
	private Long area_id;

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

	public String getMSN() {
		return this.MSN;
	}

	public void setMSN(String MSN) {
		this.MSN = MSN;
	}

	public String getQQ() {
		return this.QQ;
	}

	public void setQQ(String QQ) {
		this.QQ = QQ;
	}

	public String getWW() {
		return this.WW;
	}

	public void setWW(String WW) {
		this.WW = WW;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getAvailableBalance() {
		return this.availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getFreezeBlance() {
		return this.freezeBlance;
	}

	public void setFreezeBlance(BigDecimal freezeBlance) {
		this.freezeBlance = freezeBlance;
	}

	public Integer getGold() {
		return this.gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public Integer getIntegral() {
		return this.integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Date getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getLoginCount() {
		return this.loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Date getLoginDate() {
		return this.loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getReport() {
		return this.report;
	}

	public void setReport(Integer report) {
		this.report = report;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRole() {
		return this.userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Integer getUser_credit() {
		return this.user_credit;
	}

	public void setUser_credit(Integer user_credit) {
		this.user_credit = user_credit;
	}

	public Long getPhoto_id() {
		return this.photo_id;
	}

	public void setPhoto_id(Long photo_id) {
		this.photo_id = photo_id;
	}

	public Long getStore_id() {
		return this.store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public String getQq_openid() {
		return this.qq_openid;
	}

	public void setQq_openid(String qq_openid) {
		this.qq_openid = qq_openid;
	}

	public String getQq_binded() {
		return this.qq_binded;
	}

	public void setQq_binded(String qq_binded) {
		this.qq_binded = qq_binded;
	}

	public String getSina_openid() {
		return this.sina_openid;
	}

	public void setSina_openid(String sina_openid) {
		this.sina_openid = sina_openid;
	}

	public String getSina_binded() {
		return this.sina_binded;
	}

	public void setSina_binded(String sina_binded) {
		this.sina_binded = sina_binded;
	}

	public String getTianyuan_openid() {
		return this.tianyuan_openid;
	}

	public void setTianyuan_openid(String tianyuan_openid) {
		this.tianyuan_openid = tianyuan_openid;
	}

	public String getTianyuan_binded() {
		return this.tianyuan_binded;
	}

	public void setTianyuan_binded(String tianyuan_binded) {
		this.tianyuan_binded = tianyuan_binded;
	}

	public String getStore_quick_menu() {
		return this.store_quick_menu;
	}

	public void setStore_quick_menu(String store_quick_menu) {
		this.store_quick_menu = store_quick_menu;
	}

	public Long getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public Integer getYears() {
		return this.years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public Long getArea_id() {
		return this.area_id;
	}

	public void setArea_id(Long area_id) {
		this.area_id = area_id;
	}
//	public GrantedAuthority[] get_all_Authorities() {
//		List grantedAuthorities = new ArrayList(this.roles.size());
//		for (Role role : this.roles) {
//			grantedAuthorities.add(new GrantedAuthorityImpl(role.getRoleCode()));
//		}
//		return (GrantedAuthority[]) grantedAuthorities.toArray(new GrantedAuthority[this.roles.size()]);
//	}
//
//	public GrantedAuthority[] get_common_Authorities() {
//		List grantedAuthorities = new ArrayList(this.roles.size());
//		for (Role role : this.roles) {
//			if (!role.getType().equals("ADMIN"))
//				grantedAuthorities.add(new GrantedAuthorityImpl(role.getRoleCode()));
//		}
//		return (GrantedAuthority[]) grantedAuthorities.toArray(new GrantedAuthority[grantedAuthorities.size()]);
//	}
//
//	public String getAuthoritiesString() {
//		List authorities = new ArrayList();
//		for (GrantedAuthority authority : getAuthorities()) {
//			authorities.add(authority.getAuthority());
//		}
//		return StringUtils.join(authorities.toArray(), ",");
//	}
//	public Map<String, List<Res>> getRoleResources() {
//		if (this.roleResources == null) {
//			this.roleResources = new HashMap();
//
//			for (Role role : this.roles) {
//				String roleCode = role.getRoleCode();
//				List<Res> ress = role.getReses();
//				for (Res res : ress) {
//					String key = roleCode + "_" + res.getType();
//					if (!this.roleResources.containsKey(key)) {
//						this.roleResources.put(key, new ArrayList());
//					}
//					((List) this.roleResources.get(key)).add(res);
//				}
//			}
//		}
//
//		return this.roleResources;
//	}
}
