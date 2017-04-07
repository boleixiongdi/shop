 package com.rt.shop.view.web.action;

 import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rt.shop.common.tools.Coder;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.Album;
import com.rt.shop.entity.IntegralLog;
import com.rt.shop.entity.Role;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.User;
import com.rt.shop.entity.UserRole;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAlbumService;
import com.rt.shop.service.IIntegralLogService;
import com.rt.shop.service.IRoleService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.uc.api.UCClient;
import com.rt.shop.uc.api.UCTools;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
//import com.rt.shop.util.WebCacheUtil;
import com.rt.shop.view.web.tools.ImageViewTools;

 @Controller
 public class LoginViewAction
 {

   @Autowired
   private ISysConfigService configService;

   @Autowired
   private IUserConfigService userConfigService;

   @Autowired
   private IRoleService roleService;
   @Autowired
   private IStoreService storeService;

   @Autowired
   private IUserService userService;

   @Autowired
   private IIntegralLogService integralLogService;

   @Autowired
   private IAlbumService albumService;

   @Autowired
   private ImageViewTools imageViewTools;

   @Autowired
   private UCTools ucTools;

   @RequestMapping(method = RequestMethod.GET,value="/user/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
	   ModelAndView mv = new JModelAndView("login.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response);
	   
	   String shopping_view_type = CommUtil.isMobileDeviceValue(request.getHeader("user-agent"));
	   mv.addObject("imageViewTools", this.imageViewTools);
	   

	     if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
	    	 mv = new JModelAndView("/wap/login.html", this.configService.getSysConfig(),
						this.userConfigService.getUserConfig(), 1, request, response);
	     }
	     User user = SecurityUserHolder.getCurrentUser();
	     if (user != null) {
	       mv.addObject("user", user);
	     }

		 return mv;
		 
	}
   /**
	 * 用户登录跳转页面
	 * @param request
	 * @param response
	 * @param url
	 * @return
	 */
   @RequestMapping(method = RequestMethod.POST,value="/user/login")
   public String login(@RequestParam("userName") String userName, @RequestParam("password") String password,String url,
			@RequestParam(value="rememberMe",required=false,defaultValue="false") boolean remember,HttpServletRequest request,HttpServletResponse response){

     ModelAndView mv = new JModelAndView("index.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response);
	   String shopping_view_type = CommUtil.isMobileDeviceValue(request.getHeader("user-agent"));
     if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
    	 mv = new JModelAndView("/wap/index.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
     }

     request.getSession(false).removeAttribute("verify_code");
     boolean domain_error = CommWebUtil.null2Boolean(request.getSession(false).getAttribute("domain_error"));
     if ((url != null) && (!url.equals(""))) {
       request.getSession(false).setAttribute("refererUrl", url);
     }
     if (domain_error) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response);
       if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
    	   mv = new JModelAndView("wap/error.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response);
       }
     }
     else {
       mv.addObject("imageViewTools", this.imageViewTools);
     }
     HttpSession session = request.getSession();
     mv.addObject("uc_logout_js", request.getSession(false).getAttribute("uc_logout_js"));
    	
    	User sUser=new User();
    	sUser.setUserName(userName);
    	sUser.setPassword(Coder.encryptMD5(userName+password));
    	User user=userService.selectOne(sUser);
    	if(user!=null){
    		session.setAttribute("user", user);
//    		if(user.getStore_id()!=null){
//    			Store store=storeService.selectById(user.getStore_id());
//    		}
    		
    	}else{
    		
    		 mv = new JModelAndView("login.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response);
    		 return "redirect:user/login.htm";
    	}
    	return "redirect:/index.htm";
 	
   }

   /**
	 * 注册跳转页面
	 * @param request
	 * @param response
	 * @return
	 */
   @RequestMapping({"/register.htm"})
   public ModelAndView register(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("register.html", this.configService.getSysConfig(),
       this.userConfigService.getUserConfig(), 1, request, response);

     
	   String shopping_view_type = CommUtil.isMobileDeviceValue(request.getHeader("user-agent"));

	 if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
		 mv = new JModelAndView("wap/register.html", this.configService.getSysConfig(),
			       this.userConfigService.getUserConfig(), 1, request, response);
	 }
     request.getSession(false).removeAttribute("verify_code");
     return mv;
   }

   /**
	 * 注册保存
	 * @param request
	 * @param response
	 * @param userName
	 * @param password
	 * @param email
	 * @param code
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
   @RequestMapping({"/register_finish.htm"})
   public String register_finish(HttpServletRequest request, HttpServletResponse response, String userName, String password, String email, String code)
     throws HttpException, IOException
   {
     boolean reg = true;
     if ((code != null) && (!code.equals(""))) {
       code = CommWebUtil.filterHTML(code);
     }
     //System.out.println(this.configService.getSysConfig().isSecurityCodeRegister());
     if (this.configService.getSysConfig().getSecurityCodeRegister())
     {
       if (!request.getSession(false).getAttribute("verify_code").equals(code)) {
         reg = false;
       }
     }
    
     User sUser=new User();
     sUser.setUserName(userName);
     
     List<User> users = this.userService.selectList(sUser);
    		 //query("select obj from User obj where obj.userName=:userName", params, -1, -1);
     if ((users != null) && (users.size() > 0)) {
       reg = false;
     }
     if (reg) {
       User user = new User();
       user.setUserName(userName);
       user.setUserRole("BUYER");
       user.setAddTime(new Date());
       user.setEmail(email);
       user.setPassword(Coder.encryptMD5(userName+password));
      
       Role sRole=new Role();
       sRole.setType("BUYER");
      
       List<Role> roles = this.roleService.selectList(sRole);
       user.setRoles(roles);
       
       if (this.configService.getSysConfig().getIntegral()) {
         user.setIntegral(this.configService.getSysConfig().getMemberRegister());
         this.userService.insertSelective(user);
         IntegralLog log = new IntegralLog();
         log.setAddTime(new Date());
         log.setContent("用户注册增加" + this.configService.getSysConfig().getMemberRegister() + "分");
         log.setIntegral(this.configService.getSysConfig().getMemberRegister());
      //   log.setIntegral_user(user);
         log.setIntegral_user_id(user.getId());
         log.setType("reg");
         this.integralLogService.insertSelective(log);
       } else {
         this.userService.insertSelective(user);
       }

       List<UserRole> urList=new ArrayList<UserRole>();
       
       for(Role role : roles){
      	UserRole ur=new UserRole(user.getId(),role.getId()); 
      	urList.add(ur);
       }
       userService.insertBatchUserRole(urList);
       Album album = new Album();
       album.setAddTime(new Date());
       album.setAlbum_default(true);
       album.setAlbum_name("默认相册");
       album.setAlbum_sequence(-10000);
       //album.setUser(user);
       album.setUser_id(user.getId());
       this.albumService.insertSelective(album);
       request.getSession(false).removeAttribute("verify_code");
       if (this.configService.getSysConfig().getUc_bbs()) {
         UCClient client = new UCClient();
         String ret = client.uc_user_register(userName, password, email);
         int uid = Integer.parseInt(ret);
         if (uid <= 0) {
           if (uid == -1)
             System.out.print("用户名不合法");
           else if (uid == -2)
             System.out.print("包含要允许注册的词语");
           else if (uid == -3)
             System.out.print("用户名已经存在");
           else if (uid == -4)
             System.out.print("Email 格式有误");
           else if (uid == -5)
             System.out.print("Email 不允许注册");
           else if (uid == -6)
             System.out.print("该 Email 已经被注册");
           else
             System.out.print("未定义");
         }
         else {
           this.ucTools.active_user(userName, user.getPassword(), email);
         }
       }
       
       request.getSession().setAttribute("user", user);
       return "redirect:index.htm";
     }
     return "redirect:register.htm";
   }

   /**
	 * 登录操作成功之后跳转判断
	 * @param request
	 * @param response
	 * @return
	 */
   @RequestMapping({"/user_login_success.htm"})
   public ModelAndView user_login_success(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("success.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response);
     String url = CommWebUtil.getURL(request) + "/index.htm";
     
	   String shopping_view_type = CommUtil.isMobileDeviceValue(request.getHeader("user-agent"));
     //跳转到微信端
     if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
       String store_id = CommWebUtil.null2String(request.getSession(false).getAttribute("store_id"));
       mv = new JModelAndView("wap/success.html",
         this.configService.getSysConfig(),
         this.userConfigService.getUserConfig(), 1, request, response);
       url = CommWebUtil.getURL(request) + "/wap/index.htm?store_id=" + store_id;
     }
     HttpSession session = request.getSession(false);
     if ((session.getAttribute("refererUrl") != null) &&
       (!session.getAttribute("refererUrl").equals(""))) {
       url = (String)session.getAttribute("refererUrl");
       session.removeAttribute("refererUrl");
     }
     if (this.configService.getSysConfig().getUc_bbs()) {
       String uc_login_js = CommWebUtil.null2String(request.getSession(false).getAttribute("uc_login_js"));
       mv.addObject("uc_login_js", uc_login_js);
     }
     //第三方登录：QQ、新浪等
     String bind = CommWebUtil.null2String(request.getSession(false).getAttribute("bind"));
     if (!bind.equals("")) {
    	 mv = new JModelAndView(bind + "_login_bind.html",
         this.configService.getSysConfig(),
         this.userConfigService.getUserConfig(), 1, request, response);
       User user = SecurityUserHolder.getCurrentUser();
       mv.addObject("user", user);
       request.getSession(false).removeAttribute("bind");
     }
       //-主要用于APP中的web view, APP中的web view是不需要登陆的
       if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
           mv.setViewName("redirect:" + url);
           return  mv;
       }
       //-end

     mv.addObject("op_title", "登录成功");
     mv.addObject("url", url);
     return mv;
   }
   /**
	 * 登录模态窗口
	 * @param request
	 * @param response
	 * @return
	 */
   @RequestMapping({"/user_dialog_login.htm"})
   public ModelAndView user_dialog_login(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("user_dialog_login.html", this.configService.getSysConfig(),
       this.userConfigService.getUserConfig(), 1, request, response);
     return mv;
   }


   /** wap登录业务逻辑begin */

    @RequestMapping({ "/user/wap/login.htm" })
	public ModelAndView waplogin(HttpServletRequest request, HttpServletResponse response, String url) {

		ModelAndView mv = new JModelAndView("wap/login.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		request.getSession(false).removeAttribute("verify_code");

		boolean domain_error = CommWebUtil.null2Boolean(request.getSession(false).getAttribute("domain_error"));
		if ((url != null) && (!url.equals(""))) {
			request.getSession(false).setAttribute("refererUrl", url);
		}
		if (domain_error)
			mv = new JModelAndView("wap/error.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response);
		else {
			mv.addObject("imageViewTools", this.imageViewTools);
		}
		mv.addObject("uc_logout_js", request.getSession(false).getAttribute("uc_logout_js"));

		/*String shopping_view_type = CommUtil.null2String(request.getSession(false).getAttribute("shopping_view_type"));

		if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
			//String store_id = CommUtil.null2String(request.getSession(false).getAttribute("store_id"));
			mv = new JModelAndView("wap/success.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			mv.addObject("op_title", "成功！");
			mv.addObject("url", CommUtil.getURL(request) + "/wap/index.htm");
		}*/

		return mv;
	}

    /**
	 * 用户退出
	 * 
	 * @return 跳转到登录页面
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		
		request.getSession().invalidate();
		return "redirect:/index.htm";
	}
}




