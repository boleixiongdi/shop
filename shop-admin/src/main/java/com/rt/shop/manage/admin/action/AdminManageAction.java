package com.rt.shop.manage.admin.action;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.common.tools.Md5Encrypt;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Role;
import com.rt.shop.entity.Rolegroup;
import com.rt.shop.entity.User;
import com.rt.shop.entity.UserRole;
import com.rt.shop.entity.query.UserQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.IResService;
import com.rt.shop.service.IRoleGroupService;
import com.rt.shop.service.IRoleService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.tools.DatabaseTools;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;


@Controller
public class AdminManageAction implements ServletContextAware {
	private ServletContext servletContext;

	@Autowired
	private IUserService userService;

	@Autowired
	private ISysConfigService configService;

	@Autowired
	private IUserConfigService userConfigService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IOrderFormService orderFormService;

	@Autowired
	private IRoleGroupService roleGroupService;

	@Autowired
	private DatabaseTools databaseTools;

	

	@Autowired
	private IResService resService;

	@SecurityMapping(display = false, rsequence = 0, title = "管理员列表", value = "/admin/admin_list.htm*", rtype = "admin", rname = "管理员管理", rcode = "admin_manage", rgroup = "设置")
	@RequestMapping({ "/admin/admin_list.htm" })
	public ModelAndView admin_list(String currentPage, String orderBy, String orderType, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("admin/blue/admin_list.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response);
		UserQueryObject uqo = new UserQueryObject(currentPage, mv, orderBy, orderType);
		WebForm wf = new WebForm();
		wf.toQueryPo(request, uqo, User.class, mv);
		uqo.addQuery("obj.userRole", new SysMap("userRole", "ADMIN"), "=");
		uqo.addQuery("obj.userRole", new SysMap("userRole1", "ADMIN_BUYER_SELLER"), "=", "or");
		 Page pList = this.userService.selectPage(new Page<User>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
		
		String url = this.configService.getSysConfig().getAddress();
		if ((url == null) || (url.equals(""))) {
			url = CommUtil.getURL(request);
		}
		CommWebUtil.saveIPageList2ModelAndView(url + "/admin/admin_list.htm", "", "", pList, mv);
		mv.addObject("userRole", "ADMIN");
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "管理员添加", value = "/admin/admin_add.htm*", rtype = "admin", rname = "管理员管理", rcode = "admin_manage", rgroup = "设置")
	@RequestMapping({ "/admin/admin_add.htm" })
	public ModelAndView admin_add(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("admin/blue/admin_add.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response);
		
		Rolegroup sRoleGroup=new Rolegroup();
		sRoleGroup.setType("ADMIN");
		List rgs = this.roleGroupService.selectList(sRoleGroup, "sequence asc");
			//	.query("select obj from RoleGroup obj where obj.type=:type order by obj.sequence asc", params, -1, -1);
		mv.addObject("rgs", rgs);
		mv.addObject("op", "admin_add");
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "管理员编辑", value = "/admin/admin_edit.htm*", rtype = "admin", rname = "管理员管理", rcode = "admin_manage", rgroup = "设置")
	@RequestMapping({ "/admin/admin_edit.htm" })
	public ModelAndView admin_edit(HttpServletRequest request, HttpServletResponse response, String id, String op) {
		ModelAndView mv = new JModelAndView("admin/blue/admin_add.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response);
		Rolegroup sRoleGroup=new Rolegroup();
		sRoleGroup.setType("ADMIN");
		List rgs = this.roleGroupService.selectList(sRoleGroup, "sequence asc");
				//.query("select obj from RoleGroup obj where obj.type=:type order by obj.sequence asc", params, -1, -1);
		if ((id != null) && (!id.equals(""))) {
			User user = this.userService.selectById(Long.valueOf(Long.parseLong(id)));
			mv.addObject("obj", user);
		}

		mv.addObject("rgs", rgs);
		mv.addObject("op", op);
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "管理员保存", value = "/admin/admin_save.htm*", rtype = "admin", rname = "管理员管理", rcode = "admin_manage", rgroup = "设置")
	@RequestMapping({ "/admin/admin_save.htm" })
	public ModelAndView admin_save(HttpServletRequest request, HttpServletResponse response, String id, String role_ids, String list_url, String add_url, String password) {
		/*WebForm wf = new WebForm();
		User user = null;
		if (id.equals("")) {
			user = (User) wf.toPo(request, User.class);
			user.setPassword(Md5Encrypt.md5(password).toLowerCase());
			user.setAddTime(new Date());
		} else {
			User u = this.userService.selectById(Long.valueOf(Long.parseLong(id)));
			user = (User) wf.toPo(request, u);
		}
		user.getRoles().clear();
		if (user.getUserRole().equalsIgnoreCase("ADMIN")) {
			Map params = new HashMap();
			params.put("display", Boolean.valueOf(false));
			params.put("type", "ADMIN");
			params.put("type1", "BUYER");
			List roles = this.roleService.query("select obj from Role obj where (obj.display=:display and obj.type=:type) or obj.type=:type1", params, -1, -1);
			 List<Role> roles = this.roleService.selectList(sRole);
         user.setRoles(roles);
         List<UserRole> urList=new ArrayList<UserRole>();
       
           for(Role role : roles){
          	UserRole ur=new UserRole(user.getId(),role.getId()); 
          	urList.add(ur);
           }
           userService.insertBatchUserRole(urList);
		}
		String[] rids = role_ids.split(",");

		for (String rid : rids) {
			if (!rid.equals("")) {
				Role role = this.roleService.selectById(Long.valueOf(Long.parseLong(rid)));
				user.getRoles().add(role);
			}
		}
		if (id.equals("")) {
			this.userService.insertSelective(user);
		} else {
			this.userService.updateSelectiveById(user);
		}

		ModelAndView mv = new JModelAndView("admin/blue/success.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response);
		mv.addObject("list_url", list_url);
		mv.addObject("op_title", "保存管理员成功");
		if (add_url != null) {
			mv.addObject("add_url", add_url);
		}
		return mv;*/
		WebForm wf = new WebForm();
		User user = null;
		if (id.equals("")) {
			user = (User) wf.toPo(request, User.class);
			user.setAddTime(new Date());
		} else {
			User u = this.userService.selectById(Long.valueOf(Long.parseLong(id)));
			user = (User) wf.toPo(request, u);
		}
		if ((user.getPassword() == null) || (user.getPassword().equals(""))) {
			user.setPassword("123456");
			user.setPassword(Md5Encrypt.md5(user.getPassword()).toLowerCase());
		} else if (id.equals("")) {
			user.setPassword(Md5Encrypt.md5(user.getPassword()).toLowerCase());
		}
		user.getRoles().clear();
		if (user.getUserRole().equalsIgnoreCase("ADMIN")) {
			
			//"select obj from Role obj where (obj.display=:display and obj.type=:type) or obj.type=:type1", params, -1, -1);
			String sql="where (display=true and type='ADMIN') or type='BUYER'";
			List<Role> roles = this.roleService.selectList(sql, null);
	         user.setRoles(roles);
	         List<UserRole> urList=new ArrayList<UserRole>();
	           for(Role role : roles){
	          	UserRole ur=new UserRole(user.getId(),role.getId()); 
	          	urList.add(ur);
	           }
	           userService.insertBatchUserRole(urList);
		}
		String[] rids = role_ids.split(",");
		for (String rid : rids) {
			if (StringUtils.isNotBlank(rid) && StringUtils.isNumeric(rid)) {
				Role role = this.roleService.selectById(Long.valueOf(Long.parseLong(rid)));
//				 List<Role> roles = this.roleService.selectList(sRole);
//		         user.setRoles(roles);
//		         List<UserRole> urList=new ArrayList<UserRole>();
//		       
//		           for(Role role : roles){
//		          	UserRole ur=new UserRole(user.getId(),role.getId()); 
//		          	urList.add(ur);
//		           }
//		           userService.insertBatchUserRole(urList);
			}
		}
		if (id.equals("")) {
			this.userService.insertSelective(user);
		} else {
			this.userService.updateSelectiveById(user);
		}

		ModelAndView mv = new JModelAndView("admin/blue/success.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		mv.addObject("list_url", list_url);
		mv.addObject("op_title", "保存管理员成功");
		if (add_url != null) {
			mv.addObject("add_url", add_url);
		}
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "管理员删除", value = "/admin/admin_del.htm*", rtype = "admin", rname = "管理员管理", rcode = "admin_manage", rgroup = "设置")
	@RequestMapping({ "/admin/admin_del.htm" })
	public String admin_del(HttpServletRequest request, String mulitId, String currentPage) {
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
				User user = this.userService.selectById(Long.valueOf(Long.parseLong(id)));
				if (!user.getUserName().equals("admin")) {
					this.databaseTools.execute("delete from shopping_syslog where user_id=" + id);
					this.databaseTools.execute("delete from shopping_user_role where user_id=" + id);
					this.userService.deleteById(user.getId());
				}
			}
		}
		return "redirect:admin_list.htm?currentPage=" + currentPage;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "管理员修改密码", value = "/admin/admin_pws.htm*", rtype = "admin", rname = "商城后台管理", rcode = "admin_index", rgroup = "设置")
	@RequestMapping({ "/admin/admin_pws.htm" })
	public ModelAndView admin_pws(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("admin/blue/admin_pws.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response);
		mv.addObject("user", this.userService.selectById(SecurityUserHolder.getCurrentUser().getId()));
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "管理员密码保存", value = "/admin/admin_pws_save.htm*", rtype = "admin", rname = "商城后台管理", rcode = "admin_index", rgroup = "设置")
	@RequestMapping({ "/admin/admin_pws_save.htm" })
	public ModelAndView admin_pws_save(HttpServletRequest request, HttpServletResponse response, String old_password, String password) {
		ModelAndView mv = new JModelAndView("admin/blue/success.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response);
		User user = SecurityUserHolder.getCurrentUser();

		if (Md5Encrypt.md5(old_password).toLowerCase().equals(user.getPassword())) {
			user.setPassword(Md5Encrypt.md5(password).toLowerCase());
			this.userService.updateSelectiveById(user);
			mv.addObject("op_title", "修改密码成功");
		} else {
			mv = new JModelAndView("admin/blue/error.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response);
			mv.addObject("op_title", "原密码错误");
		}
		mv.addObject("list_url", CommUtil.getURL(request) + "/admin/admin_pws.htm");
		return mv;
	}

	@RequestMapping({ "/admin/init_role.htm" })
	public String init_role() {
		return null;/*
		User current_user = SecurityUserHolder.getCurrentUser();
		if ((current_user != null) && (current_user.getUserRole().indexOf("ADMIN") >= 0) && (current_user.getUserName().equals("admin"))) {
			this.databaseTools.execute("delete from shopping_role_res");
			this.databaseTools.execute("delete from shopping_res");
			this.databaseTools.execute("delete from shopping_user_role");
			this.databaseTools.execute("delete from shopping_role");
			this.databaseTools.execute("delete from shopping_rolegroup");
			List<Class> clzs = new ArrayList<Class>();

			clzs.add(BaseManageAction.class);

			clzs.add(BaseSellerAction.class);

			clzs.add(BaseBuyerAction.class);

			clzs.add(CartViewAction.class);
			int sequence = 0;
			Annotation[] annotation;
			for (Class clz : clzs) {
				try {
					Method[] ms = clz.getMethods();
					for (Method m : ms) {
						annotation = m.getAnnotations();
						for (Annotation tag : annotation) {
							if (SecurityMapping.class.isAssignableFrom(tag.annotationType())) {
								String value = ((SecurityMapping) tag).value();
								Map params = new HashMap();
								params.put("value", value);
								List ress = this.resService.query("select obj from Res obj where obj.value=:value", params, -1, -1);
								if (ress.size() == 0) {
									Res res = new Res();
									res.setResName(((SecurityMapping) tag).title());
									res.setValue(value);
									res.setType("URL");
									res.setAddTime(new Date());
									this.resService.insertSelective(res);
									String rname = ((SecurityMapping) tag).rname();
									String roleCode = ((SecurityMapping) tag).rcode();
									if (roleCode.indexOf("ROLE_") != 0) {
										roleCode = ("ROLE_" + roleCode).toUpperCase();
									}
									params.clear();
									params.put("roleCode", roleCode);
									List roles = this.roleService.query("select obj from Role obj where obj.roleCode=:roleCode", params, -1, -1);
									Role role = null;
									if (roles.size() > 0) {
										role = (Role) roles.get(0);
									}
									if (role == null) {
										role = new Role();
										role.setRoleName(((SecurityMapping) tag).rname());
										role.setRoleCode(roleCode.toUpperCase());
									}
									role.getReses().add(res);
									res.getRoles().add(role);
									role.setAddTime(new Date());
									role.setDisplay(((SecurityMapping) tag).display());
									role.setType(((SecurityMapping) tag).rtype().toUpperCase());

									String groupName = ((SecurityMapping) tag).rgroup();
									RoleGroup rg = this.roleGroupService.getObjByProperty("name", groupName);
									if (rg == null) {
										rg = new RoleGroup();
										rg.setAddTime(new Date());
										rg.setName(groupName);
										rg.setSequence(sequence);
										rg.setType(role.getType());
										this.roleGroupService.insertSelective(rg);
									}
									role.setRg(rg);
									this.roleService.insertSelective(role);
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				sequence++;
			}

			User user = this.userService.getObjByProperty("userName", "admin");
			Object params = new HashMap();
			List<Role> roles = this.roleService.query("select obj from Role obj order by obj.addTime desc", null, -1, -1);
			if (user == null) {
				user = new User();
				user.setUserName("admin");
				user.setUserRole("ADMIN");
				user.setPassword(Md5Encrypt.md5("123456").toLowerCase());
				for (Role role : roles) {
					if (!role.getType().equalsIgnoreCase("SELLER")) {
						user.getRoles().add(role);
					}
				}
				this.userService.insertSelective(user);
			} else {
				for (Role role : roles) {
					if (!role.getType().equals("SELLER")) {
						System.out.println(role.getRoleName() + " " + role.getType() + " " + role.getRoleCode());
						user.getRoles().add(role);
					}
				}
				this.userService.updateSelectiveById(user);
			}

			((Map) params).clear();
			((Map) params).put("display", Boolean.valueOf(false));
			((Map) params).put("type", "ADMIN");
			List admin_roles = this.roleService.query("select obj from Role obj where obj.display=:display and obj.type=:type", (Map) params, -1, -1);
			((Map) params).clear();
			((Map) params).put("type", "BUYER");
			List buyer_roles = this.roleService.query("select obj from Role obj where obj.type=:type", (Map) params, -1, -1);
			((Map) params).clear();
			((Map) params).put("userRole", "ADMIN");
			((Map) params).put("userName", "admin");
			List<User> admins = this.userService.query("select obj from User obj where obj.userRole=:userRole and obj.userName!=:userName", (Map) params, -1, -1);
			for (User admin : admins) {
				admin.getRoles().addAll(admin_roles);
				admin.getRoles().addAll(buyer_roles);
				this.userService.updateSelectiveById(admin);
			}

			((Map) params).clear();
			((Map) params).put("userRole", "BUYER");
			List<User> buyers = this.userService.query("select obj from User obj where obj.userRole=:userRole", (Map) params, -1, -1);
			for (User buyer : buyers) {
				buyer.getRoles().addAll(buyer_roles);
				this.userService.updateSelectiveById(buyer);
			}

			((Map) params).clear();
			((Map) params).put("type1", "BUYER");
			((Map) params).put("type2", "SELLER");
			List seller_roles = this.roleService.query("select obj from Role obj where (obj.type=:type1 or obj.type=:type2)", (Map) params, -1, -1);
			((Map) params).clear();
			((Map) params).put("userRole1", "BUYER_SELLER");
			((Map) params).put("userRole2", "ADMIN_BUYER_SELLER");
			((Map) params).put("userRole3", "ADMIN");
			((Map) params).put("userName", "admin");
			List<User> sellers = this.userService.query("select obj from User obj where (obj.userRole=:userRole1 or obj.userRole=:userRole2 or obj.userRole=:userRole3) and obj.userName!=:userName ", (Map) params, -1, -1);
			for (User seller : sellers) {
				seller.getRoles().addAll(buyer_roles);
				seller.getRoles().addAll(seller_roles);
				this.userService.updateSelectiveById(seller);
			}

			Map urlAuthorities = this.securityManager.loadUrlAuthorities();
			this.servletContext.setAttribute("urlAuthorities", urlAuthorities);
			return "redirect:admin_list.htm";
		}
		return (String) "redirect:login.htm";
	*/}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
