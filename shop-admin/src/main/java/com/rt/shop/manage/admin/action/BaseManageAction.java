 package com.rt.shop.manage.admin.action;
 
 import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.rt.shop.common.annotation.Log;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.constant.Constants;
import com.rt.shop.common.enums.LogType;
import com.rt.shop.common.tools.Coder;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.IntegralLog;
import com.rt.shop.entity.StoreStat;
import com.rt.shop.entity.SysConfig;
import com.rt.shop.entity.User;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IIntegralLogService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.IStoreStatService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.tools.DatabaseTools;
import com.rt.shop.tools.MsgTools;
import com.rt.shop.tools.StatTools;
import com.rt.shop.uc.api.UCClient;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class BaseManageAction
 {
 
	 @Autowired
	private HttpSession session;
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IAccessoryService accessoryService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IStoreService storeService;
 
   @Autowired
   private IIntegralLogService integralLogService;
 
   @Autowired
   private DatabaseTools databaseTools;
 
   @Autowired
   private IStoreStatService storeStatService;
 
   @Autowired
   private MsgTools msgTools;
 
   @Autowired
   private StatTools statTools;
 
   @Log(title="用户登陆", type=LogType.LOGIN, description = "", entityName = "", ip = "")
   @RequestMapping({"/login_success.htm"})
   public void login_success(HttpServletRequest request, HttpServletResponse response)
     throws IOException
   {
     User user = this.userService.selectById(SecurityUserHolder.getCurrentUser().getId());
     if ((this.configService.getSysConfig().getIntegral()) && ((user.getLoginDate() == null) || 
       (user.getLoginDate().before(CommUtil.formatDate(CommUtil.formatShortDate(new Date())))))) {
       user.setIntegral(user.getIntegral() + 
         this.configService.getSysConfig().getMemberDayLogin());
       IntegralLog log = new IntegralLog();
       log.setAddTime(new Date());
       log.setContent("用户" + CommUtil.formatLongDate(new Date()) + 
         "登录增加" + this.configService.getSysConfig().getMemberDayLogin() + "分");
       log.setIntegral(this.configService.getSysConfig().getMemberRegister());
       log.setIntegral_user(user);
       log.setType("login");
       this.integralLogService.insertSelective(log);
     }
 
     user.setLoginDate(new Date());
     user.setLoginIp(CommUtil.getIpAddr(request));
     user.setLoginCount(user.getLoginCount() + 1);
     this.userService.updateSelectiveById(user);
     HttpSession session = request.getSession(false);
     session.setAttribute("user", user);
     session.setAttribute("lastLoginDate", new Date());
     session.setAttribute("loginIp", CommUtil.getIpAddr(request));
     session.setAttribute("login", Boolean.valueOf(true));
     String role = user.getUserRole();
     String url = CommUtil.getURL(request) + "/user_login_success.htm";
     String login_role = (String)session.getAttribute("login_role");
 
     if (this.configService.getSysConfig().getSecond_domain_open()) {
       Cookie shopping_user_session = new Cookie("shopping_user_session", user.getId().toString());
       shopping_user_session.setDomain(CommUtil.generic_domain(request));
       response.addCookie(shopping_user_session);
     }
     boolean ajax_login = CommUtil.null2Boolean(session.getAttribute("ajax_login"));
     if (ajax_login) {
       response.setContentType("text/plain");
       response.setHeader("Cache-Control", "no-cache");
       response.setCharacterEncoding("UTF-8");
       try
       {
         PrintWriter writer = response.getWriter();
         writer.print("success");
       }
       catch (IOException e) {
         e.printStackTrace();
       }
     } else {
       if ((login_role.equals("admin")) && 
         (role.indexOf("ADMIN") >= 0)) {
         url = CommUtil.getURL(request) + "/admin/index.htm";
         request.getSession(false).setAttribute("admin_login", Boolean.valueOf(true));
       }
 
       response.sendRedirect(url);
     }
   }
 
   @RequestMapping({"/logout_success.htm"})
   public void logout_success(HttpServletRequest request, HttpServletResponse response) throws IOException {
     HttpSession session = request.getSession(false);
     String targetUrl = CommUtil.getURL(request) + "/user/login.htm";
     session.removeAttribute("user");
     session.removeAttribute("login");
     session.removeAttribute("role");
     session.removeAttribute("cart");
     ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
       .getRequest().getSession(false).removeAttribute("user");
 
     if (this.configService.getSysConfig().getSecond_domain_open()) {
       Cookie[] cookies = request.getCookies();
       for (Cookie cookie : cookies) {
         if (cookie.getName().equals("shopping_user_session")) {
           cookie.setMaxAge(0);
           cookie.setValue("");
           cookie.setDomain(CommUtil.generic_domain(request));
           response.addCookie(cookie);
         }
       }
     }
 
     if (this.configService.getSysConfig().getUc_bbs()) {
       UCClient uc = new UCClient();
       String uc_logout_js = uc.uc_user_synlogout();
       request.getSession(false).setAttribute("uc_logout_js", uc_logout_js);
     }
     /*
     String shopping_view_type = CommUtil.null2String(request.getSession(false).getAttribute("shopping_view_type"));
     //跳转到微信端
     if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
    	 targetUrl = CommUtil.getURL(request) + "/user/login.htm";
     }*/
     response.sendRedirect(targetUrl);
   }
 
   @RequestMapping({"/login_error.htm"})
   public ModelAndView login_error(HttpServletRequest request, HttpServletResponse response) {
     String login_role = (String)request.getSession(false).getAttribute("login_role");
     ModelAndView mv = null;
     String shopping_view_type = CommUtil.null2String(request.getSession(false).getAttribute("shopping_view_type"));
     
     if (login_role == null)
         login_role = "user";
     if (login_role.equals("admin")) {
         mv = new JModelAndView("admin/blue/login_error.html", this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 0, request, response);
     } else {
    	 if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
    	         mv = new JModelAndView("wap/error.html", this.configService.getSysConfig(), 
    	           this.userConfigService.getUserConfig(), 1, request, response);
    	         mv.addObject("url", CommUtil.getURL(request) + "/wap/index.htm");
    	 } 
         mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, response);
         mv.addObject("url", CommUtil.getURL(request) + "/user/login.htm");
     }
     mv.addObject("op_title", "登录失败");
     return mv;
   }
 
   @SecurityMapping( rsequence = 0, title="商城后台管理", value="/admin/index.htm*", rtype="admin", rname="商城后台管理", rcode="admin_index", display=false, rgroup="设置")
   @RequestMapping({"/admin/index.htm"})
   public ModelAndView manage(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("admin/blue/manage.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (this.configService.getSysConfig().getUc_bbs()) {
       String uc_login_js = CommUtil.null2String(request.getSession(false)
         .getAttribute("uc_login_js"));
       mv.addObject("uc_login_js", uc_login_js);
     }
     return mv;
   }
   @SecurityMapping( rsequence = 0, title="欢迎页面", value="/admin/welcome.htm*", rtype="admin", rname="欢迎页面", rcode="admin_index", display=false, rgroup="设置")
   @RequestMapping({"/admin/welcome.htm"})
   public ModelAndView welcome(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView("admin/blue/welcome.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Properties props = System.getProperties();
     mv.addObject("os", props.getProperty("os.name"));
     mv.addObject("java_version", props.getProperty("java.version"));
     mv.addObject("shop_version", Integer.valueOf(20140301));
     mv.addObject("database_version", 
       this.databaseTools.queryDatabaseVersion());
     mv.addObject("web_server_version", request.getSession(false)
       .getServletContext().getServerInfo());
     List stats = this.storeStatService.selectList(null);
     StoreStat stat = null;
     if (stats.size() > 0)
       stat = (StoreStat)stats.get(0);
     else {
       stat = new StoreStat();
     }
     mv.addObject("stat", stat);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="关于我们", value="/admin/aboutus.htm*", rtype="admin", rname="关于我们", rcode="admin_index",  rgroup="设置")
   @RequestMapping({"/admin/aboutus.htm"})
   public ModelAndView aboutus(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView("admin/blue/aboutus.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="站点设置", value="/admin/set_site.htm*", rtype="admin", rname="站点设置", rcode="admin_set_site", rgroup="设置")
   @RequestMapping({"/admin/set_site.htm"})
   public ModelAndView site_set(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView("admin/blue/set_site_setting.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="上传设置", value="/admin/set_image.htm*", rtype="admin", rname="上传设置", rcode="admin_set_image", rgroup="设置")
   @RequestMapping({"/admin/set_image.htm"})
   public ModelAndView set_image(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView("admin/blue/set_image_setting.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="保存商城配置", value="/admin/sys_config_save.htm*", rtype="admin",  rname="保存商城配置", rcode="admin_config_save", rgroup="设置")
   @RequestMapping({"/admin/sys_config_save.htm"})
   public ModelAndView sys_config_save(HttpServletRequest request, HttpServletResponse response, String id, String list_url, String op_title) {
     SysConfig obj = this.configService.getSysConfig();
     WebForm wf = new WebForm();
     SysConfig sysConfig = null;
     if (id.equals("")) {
       sysConfig = (SysConfig)wf.toPo(request, SysConfig.class);
       sysConfig.setAddTime(new Date());
     } else {
       sysConfig = (SysConfig)wf.toPo(request, obj);
     }
 
     String uploadFilePath = this.configService.getSysConfig().getUploadFilePath();
     String saveFilePathName = request.getSession().getServletContext().getRealPath("/") + 
       uploadFilePath + File.separator + "system";
     Map map = new HashMap();
     try {
    	 Accessory logo1=accessoryService.selectById(this.configService.getSysConfig().getWebsiteLogo_id());
       String fileName = this.configService.getSysConfig().getWebsiteLogo_id() == null ? 
         "" : logo1.getName();
       map = CommUtil.saveFileToServer(request, "websiteLogo", saveFilePathName, fileName, null);
       if (fileName.equals("")) {
         if (map.get("fileName") != "") {
           Accessory logo = new Accessory();
           logo.setName(CommUtil.null2String(map.get("fileName")));
           logo.setExt((String)map.get("mime"));
           logo.setSize(((Float)map.get("fileSize")).floatValue());
           logo.setPath(uploadFilePath + "/system");
           logo.setWidth(((Integer)map.get("width")).intValue());
           logo.setHeight(((Integer)map.get("height")).intValue());
           logo.setAddTime(new Date());
           this.accessoryService.insertSelective(logo);
           sysConfig.setWebsiteLogo_id(logo.getId());
         }else if (map.get("fileName") != "") {
             Accessory logo = logo1;
             logo.setName(CommUtil.null2String(map.get("fileName")));
             logo.setExt(CommUtil.null2String(map.get("mime")));
             logo.setSize(CommUtil.null2Float(map.get("fileSize")));
             logo.setPath(uploadFilePath + "/system");
             logo.setWidth(CommUtil.null2Int(map.get("width")));
             logo.setHeight(CommUtil.null2Int(map.get("height")));
             this.accessoryService.updateSelectiveById(logo);
           }
       }
       
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
 
     map.clear();
     try {
    	 Accessory goodsImage=accessoryService.selectById(sysConfig.getGoodsImage_id());
       map = CommUtil.saveFileToServer(request, "goodsImage", saveFilePathName, null, null);
       if(goodsImage!=null){
           String fileName = goodsImage.getName();
           if (fileName.equals("")) {
             if (map.get("fileName") != "") {
               Accessory photo = new Accessory();
               photo.setName(CommUtil.null2String(map.get("fileName")));
               photo.setExt(CommUtil.null2String(map.get("mime")));
               photo.setSize(CommUtil.null2Float(map.get("fileSize")));
               photo.setPath(uploadFilePath);
               photo.setWidth(CommUtil.null2Int(map.get("width")));
               photo.setHeight(CommUtil.null2Int(map.get("heigh")));
               photo.setAddTime(new Date());
               this.accessoryService.insertSelective(photo);
               sysConfig.setGoodsImage_id(photo.getId());
             }
           } else if (map.get("fileName") != "") {
               Accessory photo = goodsImage;
               System.out.println(map.get("fileName"));
               photo.setName(CommUtil.null2String(map.get("fileName")));
               photo.setExt(CommUtil.null2String(map.get("mime")));
               photo.setSize(CommUtil.null2Float(map.get("fileSize")));
               photo.setPath(uploadFilePath + "/system");
               photo.setWidth(CommUtil.null2Int(map.get("width")));
               photo.setHeight(CommUtil.null2Int(map.get("height")));
               this.accessoryService.updateSelectiveById(photo);
             }
       } 
     }
     catch (IOException e){
       e.printStackTrace();
     }
 
     map.clear();
     try {
    	 Accessory storeImage=accessoryService.selectById(sysConfig.getStoreImage_id());
       map = CommUtil.saveFileToServer(request, "storeImage", saveFilePathName, null, null);
       if(storeImage!=null){
           String fileName = storeImage.getName();
           if (fileName.equals("")) {
             if (map.get("fileName") != "") {
               Accessory photo = new Accessory();
               photo.setName((String)map.get("fileName"));
               photo.setExt((String)map.get("mime"));
               photo.setSize(((Float)map.get("fileSize")).floatValue());
               photo.setPath(uploadFilePath);
               photo.setWidth(((Integer)map.get("width")).intValue());
               photo.setHeight(((Integer)map.get("heigh")).intValue());
               photo.setAddTime(new Date());
               this.accessoryService.insertSelective(photo);
               sysConfig.setStoreImage_id(photo.getId());
             }
           } 
       }
       else if (map.get("fileName") != "") {
         Accessory photo = storeImage;
         photo.setName(CommUtil.null2String(map.get("fileName")));
         photo.setExt(CommUtil.null2String(map.get("mime")));
         photo.setSize(CommUtil.null2Float(map.get("fileSize")));
         photo.setPath(uploadFilePath + "/system");
         photo.setWidth(CommUtil.null2Int(map.get("width")));
         photo.setHeight(CommUtil.null2Int(map.get("height")));
         this.accessoryService.updateSelectiveById(photo);
       }
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
 
     map.clear();
     try {
    	 Accessory memberImage=accessoryService.selectById(sysConfig.getMemberIcon_id());
       map = CommUtil.saveFileToServer(request, "memberIcon", saveFilePathName, null, null);
       if(memberImage!=null){
           String fileName = memberImage.getName();
           if (fileName.equals("")) {
             if (map.get("fileName") != "") {
               Accessory photo = new Accessory();
               photo.setName((String)map.get("fileName"));
               photo.setExt((String)map.get("mime"));
               photo.setSize(((Float)map.get("fileSize")).floatValue());
               photo.setPath(uploadFilePath);
               photo.setWidth(((Integer)map.get("width")).intValue());
               photo.setHeight(((Integer)map.get("heigh")).intValue());
               photo.setAddTime(new Date());
               this.accessoryService.insertSelective(photo);
               sysConfig.setMemberIcon_id(photo.getId());
             }
           }
            
       } else if (map.get("fileName") != "") {
         Accessory photo = memberImage;
         photo.setName(CommUtil.null2String(map.get("fileName")));
         photo.setExt(CommUtil.null2String(map.get("mime")));
         photo.setSize(CommUtil.null2Float(map.get("fileSize")));
         photo.setPath(uploadFilePath + "/system");
         photo.setWidth(CommUtil.null2Int(map.get("width")));
         photo.setHeight(CommUtil.null2Int(map.get("height")));
         this.accessoryService.updateSelectiveById(photo);
       }
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
     if (id.equals(""))
       this.configService.insertSelective(sysConfig);
     else {
       this.configService.updateSelectiveById(sysConfig);
     }
     for (int i = 0; i < 4; i++) {
       try {
         map.clear();
         String fileName = "";
         Accessory sAccessory=new Accessory();
         sAccessory.setConfig_id(sysConfig.getId());
         List<Accessory> loginImage=accessoryService.selectList(sAccessory);
         if (loginImage!=null && loginImage.size() > i) {
           fileName = ((Accessory)loginImage.get(i)).getName();
         }
         map = CommUtil.saveFileToServer(request, "img" + i, 
           saveFilePathName, fileName, null);
         if (fileName.equals("")) {
           if (map.get("fileName") != "") {
             Accessory img = new Accessory();
             img.setName(CommUtil.null2String(map.get("fileName")));
             img.setExt((String)map.get("mime"));
             img.setSize(((Float)map.get("fileSize")).floatValue());
             img.setPath(uploadFilePath + "/system");
             img.setWidth(((Integer)map.get("width")).intValue());
             img.setHeight(((Integer)map.get("height")).intValue());
             img.setAddTime(new Date());
             img.setConfig(sysConfig);
             this.accessoryService.insertSelective(img);
           }
         }
         else if (map.get("fileName") != "") {
           Accessory img = (Accessory)loginImage.get(i);
           img.setName(CommUtil.null2String(map.get("fileName")));
           img.setExt(CommUtil.null2String(map.get("mime")));
           img.setSize(CommUtil.null2Float(map.get("fileSize")));
           img.setPath(uploadFilePath + "/system");
           img.setWidth(CommUtil.null2Int(map.get("width")));
           img.setHeight(CommUtil.null2Int(map.get("height")));
           img.setConfig(sysConfig);
           this.accessoryService.updateSelectiveById(img);
         }
       }
       catch (IOException e)
       {
         e.printStackTrace();
       }
     }
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     mv.addObject("op_title", op_title);
     mv.addObject("list_url", list_url);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="Email设置", value="/admin/set_email.htm*", rtype="admin", rname="Email设置", rcode="admin_set_email", rgroup="设置")
   @RequestMapping({"/admin/set_email.htm"})
   public ModelAndView set_email(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView(
       "admin/blue/set_email_setting.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="短信设置", value="/admin/set_sms.htm*", rtype="admin", rname="短信设置", rcode="admin_set_sms", rgroup="设置")
   @RequestMapping({"/admin/set_sms.htm"})
   public ModelAndView set_sms(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView("admin/blue/set_sms_setting.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="SEO设置", value="/admin/set_seo.htm*", rtype="admin", rname="SEO设置", rcode="admin_set_seo", rgroup="设置")
   @RequestMapping({"/admin/set_seo.htm"})
   public ModelAndView set_seo(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView("admin/blue/set_seo_setting.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="二级域名设置", value="/admin/set_second_domain.htm*", rtype="admin", rname="二级域名设置", rcode="admin_set_second_domain", rgroup="设置")
   @RequestMapping({"/admin/set_second_domain.htm"})
   public ModelAndView set_second_domain(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView(
       "admin/blue/set_second_domain.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="二级域名设置保存", value="/admin/set_second_domain_save.htm*", rtype="admin", rname="二级域名设置", rcode="admin_set_second_domain", rgroup="设置")
   @RequestMapping({"/admin/set_second_domain_save.htm"})
   public ModelAndView set_second_domain_save(HttpServletRequest request, HttpServletResponse response, String id, String domain_allow_count, String sys_domain, String second_domain_open) {
     String serverName = request.getServerName().toLowerCase();
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (serverName.indexOf(".") > 0) {
       SysConfig config = this.configService.getSysConfig();
       config.setDomain_allow_count(CommUtil.null2Int(domain_allow_count));
       config.setSys_domain(sys_domain);
       config.setSecond_domain_open(
         CommUtil.null2Boolean(second_domain_open));
       if (id.equals(""))
         this.configService.insertSelective(config);
       else
         this.configService.updateSelectiveById(config);
       mv.addObject("op_title", "二级域名保存成功");
       mv.addObject("list_url", CommUtil.getURL(request) + 
         "/admin/set_second_domain.htm");
     } else {
       SysConfig config = this.configService.getSysConfig();
       config.setDomain_allow_count(CommUtil.null2Int(domain_allow_count));
       config.setSys_domain(sys_domain);
       config.setSecond_domain_open(false);
       if (id.equals(""))
         this.configService.insertSelective(config);
       else
         this.configService.updateSelectiveById(config);
       mv = new JModelAndView("admin/blue/error.html", 
         this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 0, request, 
         response);
       mv.addObject("op_title", "当前网站无法开启二级域名");
       mv.addObject("list_url", CommUtil.getURL(request) + 
         "/admin/set_second_domain.htm");
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="QQ互联登录", value="/admin/set_site_qq.htm*", rtype="admin", rname="二级域名设置", rcode="admin_set_second_domain", rgroup="设置")
   @RequestMapping({"/admin/set_site_qq.htm"})
   public ModelAndView set_site_qq(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView("admin/blue/set_second_domain.html", 
       this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="分润设置", value="/admin/set_fenrun.htm*", rtype="admin", rname="分润管理", rcode="admin_set_fenrun", rgroup="设置")
   @RequestMapping({"/admin/set_fenrun.htm"})
   public ModelAndView set_fenrun(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("admin/blue/set_fenrun.html", 
       this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="保存分润设置", value="/admin/set_fenrun_save.htm*", rtype="admin", rname="分润管理", rcode="admin_set_fenrun", rgroup="设置")
   @RequestMapping({"/admin/set_fenrun_save.htm"})
   public ModelAndView set_fenrun_save(HttpServletRequest request, HttpServletResponse response, String id, String alipay_fenrun, String balance_fenrun)
   {
     SysConfig obj = this.configService.getSysConfig();
     WebForm wf = new WebForm();
     SysConfig config = null;
     if (id.equals("")) {
       config = (SysConfig)wf.toPo(request, SysConfig.class);
       config.setAddTime(new Date());
     } else {
       config = (SysConfig)wf.toPo(request, obj);
     }
     config.setAlipay_fenrun(CommUtil.null2Int(alipay_fenrun));
     config.setBalance_fenrun(CommUtil.null2Int(balance_fenrun));
     if (id.equals(""))
       this.configService.insertSelective(config);
     else {
       this.configService.updateSelectiveById(config);
     }
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response);
     mv.addObject("op_title", "分润设置成功");
     mv.addObject("list_url", CommUtil.getURL(request) + "/admin/set_fenrun.htm");
     return mv;
   }
 
   @RequestMapping({"/admin/logout.htm"})
   public String logout(HttpServletRequest request, HttpServletResponse response)
   {
	   User curUser = (User) session.getAttribute(Constants.CURRENT_USER);
		SecurityUtils.getSubject().logout();
		
//		return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/login";
//     }
     return "redirect:../index.htm";
   }
 
	@RequestMapping(method = RequestMethod.GET,value="/admin/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		 ModelAndView mv = new JModelAndView("admin/blue/login.html", this.configService.getSysConfig(), 
			       this.userConfigService.getUserConfig(), 0, request, response);
	     User user = SecurityUserHolder.getCurrentUser();
	     if (user != null) {
	       mv.addObject("user", user);
	     }

		 return mv;
		 
	}
	@RequestMapping(method = RequestMethod.POST,value="/admin/login.htm")
   public ModelAndView login(@RequestParam("userName") String userName, @RequestParam("password") String password,
			@RequestParam(value="rememberMe",required=false,defaultValue="false") boolean remember,HttpServletRequest request,HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("admin/blue/manage.html", this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     //设置为PC访问
   	 request.getSession().setAttribute("shopping_view_type", "pc");
   	 
   	UsernamePasswordToken token = new UsernamePasswordToken(userName, Coder.encryptMD5(userName+password));
	//token.setRememberMe(remember);
	Subject currentUser = SecurityUtils.getSubject();
	try {
		currentUser.login(token);
	} catch (UnknownAccountException uae) {
		System.out.println("对用户[" + userName + "]进行登录验证..验证未通过,未知账户");
		request.setAttribute("message_login", "未知账户");
	} catch (IncorrectCredentialsException ice) {
		System.out.println("对用户[" + userName + "]进行登录验证..验证未通过,错误的凭证");
		request.setAttribute("message_login", "密码不正确");
	} catch (LockedAccountException lae) {
		System.out.println("对用户[" + userName + "]进行登录验证..验证未通过,账户已锁定");
		request.setAttribute("message_login", "账户已锁定");
	} catch (ExcessiveAttemptsException eae) {
		System.out.println("对用户[" + userName + "]进行登录验证..验证未通过,错误次数过多");
		request.setAttribute("message_login", "用户名或密码错误次数过多");
	} catch (AuthenticationException ae) {
		// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
		System.out.println("对用户[" + userName + "]进行登录验证..验证未通过,堆栈轨迹如下");
		ae.printStackTrace();
		request.setAttribute("message_login", "用户名或密码不正确");
	}
	// 验证是否登录成功
	if (currentUser.isAuthenticated()) {
		try {
			User curUser = userService.selectUserByUsername(userName);
		     if (curUser != null) {
			       mv.addObject("user", curUser);
			     }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	} else {
		token.clear();
		 mv = new JModelAndView("admin/blue/login.html", this.configService.getSysConfig(), 
			       this.userConfigService.getUserConfig(), 0, request, response);
	}
	
	return mv;
	
//     User user = SecurityUserHolder.getCurrentUser();
//     if (user != null) {
//       mv.addObject("user", user);
//     }
//     return mv;
   }
 
   @RequestMapping({"/success.htm"})
   public ModelAndView success(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView("success.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     mv.addObject("op_title", 
       request.getSession(false).getAttribute("op_title"));
     mv.addObject("url", request.getSession(false).getAttribute("url"));
     request.getSession(false).removeAttribute("op_title");
     request.getSession(false).removeAttribute("url");
     return mv;
   }
 
   @RequestMapping({"/error.htm"})
   public ModelAndView error(HttpServletRequest request, HttpServletResponse response)
   {
     User user = SecurityUserHolder.getCurrentUser();
     ModelAndView mv = new JModelAndView("error.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     if ((user != null) && (user.getUserRole().equalsIgnoreCase("ADMIN"))) {
       mv = new JModelAndView("admin/blue/error.html", 
         this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 0, request, 
         response);
     }
 
     mv.addObject("op_title", 
       request.getSession(false).getAttribute("op_title"));
     mv.addObject("list_url", request.getSession(false).getAttribute("url"));
     mv.addObject("url", request.getSession(false).getAttribute("url"));
     request.getSession(false).removeAttribute("op_title");
     request.getSession(false).removeAttribute("url");
     return mv;
   }
 
   @RequestMapping({"/exception.htm"})
   public ModelAndView exception(HttpServletRequest request, HttpServletResponse response)
   {
     User user = (User)request.getSession().getAttribute("user");
     ModelAndView mv = new JModelAndView("error.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     if ((user != null) && (user.getUserRole().equalsIgnoreCase("ADMIN"))) {
       mv = new JModelAndView("admin/blue/exception.html", 
         this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 0, request, 
         response);
     } else {
       mv.addObject("op_title", "系统出现异常");
       mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
     }
     return mv;
   }
 
   @RequestMapping({"/authority.htm"})
   public ModelAndView authority(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("admin/blue/authority.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     boolean domain_error = CommUtil.null2Boolean(request.getSession(false)
       .getAttribute("domain_error"));
     if (domain_error) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "域名绑定错误");
     }
     return mv;
   }
 
   @RequestMapping({"/voice.htm"})
   public ModelAndView voice(HttpServletRequest request, HttpServletResponse response)
   {
     return new JModelAndView("include/flash/soundPlayer.swf", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), request, response);
   }
 
   @RequestMapping({"/getCode.htm"})
   public void getCode(HttpServletRequest request, HttpServletResponse response)
     throws IOException
   {
     HttpSession session = request.getSession(false);
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     PrintWriter writer = response.getWriter();
     writer.print("result=true&code=" + 
       (String)session.getAttribute("verify_code"));
   }
 
   @RequestMapping({"/editor.htm"})
   public ModelAndView editor(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("admin/blue/editor_test.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     return mv;
   }
 
   @RequestMapping({"/upload.htm"})
   public void upload(HttpServletRequest request, HttpServletResponse response)
     throws ClassNotFoundException
   {
     String saveFilePathName = request.getSession().getServletContext().getRealPath("/") + 
       this.configService.getSysConfig().getUploadFilePath() + File.separator + "common";
     
     String webPath = request.getContextPath().equals("/") ? "" : request.getContextPath();
     
     if ((this.configService.getSysConfig().getAddress() != null) && 
       (!this.configService.getSysConfig().getAddress().equals(""))) {
       webPath = this.configService.getSysConfig().getAddress() + webPath;
     }
     JSONObject obj = new JSONObject();
     try {
       Map map = CommUtil.saveFileToServer(request, "imgFile", saveFilePathName, null, null);
       String url = webPath + "/" + this.configService.getSysConfig().getUploadFilePath() + "/common/" + map.get("fileName");
       obj.put("error", Integer.valueOf(0));
       obj.put("url", url);
     } catch (IOException e) {
       obj.put("error", Integer.valueOf(1));
       obj.put("message", e.getMessage());
       e.printStackTrace();
     }
     response.setContentType("text/html");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(obj.toJSONString());
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   @RequestMapping({"/js.htm"})
   public ModelAndView js(HttpServletRequest request, HttpServletResponse response, String js)
   {
     ModelAndView mv = new JModelAndView("resources/js/" + js + ".js", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 2, request, response);
     return mv;
   }
   @RequestMapping({"/admin/test_mail.htm"})
   public void test_email(HttpServletResponse response, String email) {
     String subject = this.configService.getSysConfig().getTitle() + "测试邮件";
     boolean ret = this.msgTools.sendEmail(email, subject, subject);
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(ret);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   @RequestMapping({"/admin/test_sms.htm"})
   public void test_sms(HttpServletResponse response, String mobile) throws UnsupportedEncodingException {
     String content = this.configService.getSysConfig().getTitle() + 
       "测试短信,如果您收到短信，说明发送成功！";
     boolean ret = this.msgTools.sendSMS(mobile, content);
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(ret);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   @RequestMapping({"/admin/user_msg_save.htm"})
   public void user_msg_save(HttpServletResponse response, String msg) throws HttpException, IOException {
     HttpClient client = new HttpClient();
     PostMethod method = new PostMethod(
       "#");
     method.addParameter("msg", msg);
     int status = client.executeMethod(method);
     boolean ret = false;
     if (status == 200) {
       ret = true;
     }
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(ret);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="websiteCss设置", value="/admin/set_websiteCss.htm*", rtype="admin", rname="Email设置", rcode="admin_set_websiteCss", rgroup="设置")
   @RequestMapping({"/admin/set_websiteCss.htm"})
   public void set_websiteCss(HttpServletRequest request, HttpServletResponse response, String webcss)
   {
     SysConfig obj = this.configService.getSysConfig();
     if ((!webcss.equals("blue")) && (!webcss.equals("black"))) {
       webcss = "blue";
     }
     obj.setWebsiteCss(webcss);
     this.configService.updateSelectiveById(obj);
   }
 }


 
 
 