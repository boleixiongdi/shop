 package com.rt.shop.view.admin.sellers.action;
 
 import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.easyjf.web.WebForm;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.Area;
import com.rt.shop.entity.Role;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.StoreClass;
import com.rt.shop.entity.StoreGrade;
import com.rt.shop.entity.StoreGradeLog;
import com.rt.shop.entity.StorePoint;
import com.rt.shop.entity.StoreSlide;
import com.rt.shop.entity.User;
import com.rt.shop.entity.UserRole;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IAreaService;
import com.rt.shop.service.IRoleService;
import com.rt.shop.service.IStoreClassService;
import com.rt.shop.service.IStoreGradeService;
import com.rt.shop.service.IStorePointService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.IStoreSlideService;
import com.rt.shop.service.IStoregradeLogService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.QRCodeEncoderHandler;
import com.rt.shop.util.SecurityUserHolder;
import com.rt.shop.view.web.tools.AreaViewTools;
import com.rt.shop.view.web.tools.StoreViewTools;
 
 @Controller
 public class StoreSellerAction
 {
 
	 @Autowired
	private IStorePointService storePointService;
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IStoreGradeService storeGradeService;
 
   @Autowired
   private IAreaService areaService;
 
   @Autowired
   private IStoreClassService storeClassService;
 
   @Autowired
   private IStoreService storeService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IRoleService roleService;
 
   @Autowired
   private IAccessoryService accessoryService;
 
   @Autowired
   private IStoregradeLogService storeGradeLogService;
 
   @Autowired
   private IStoreSlideService storeSlideService;
 
   @Autowired
   private StoreViewTools storeTools;
 
   @Autowired
   private AreaViewTools areaViewTools;
 
   @SecurityMapping(display = false, rsequence = 0, title="申请店铺第一步", value="/seller/store_create_first.htm*", rtype="buyer", rname="申请店铺", rcode="create_store", rgroup="申请店铺")
   @RequestMapping({"/seller/store_create_first.htm"})
   public ModelAndView store_create_first(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = null;
     int store_status = 0;
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     if (store != null) {
       store_status = store.getStore_status();
     }
     if (this.configService.getSysConfig().getStore_allow()) {
       if (store_status == 0) {
         mv = new JModelAndView("store_create_first.html", this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, response);
         List sgs = this.storeGradeService.selectList(new StoreGrade(),"sequence asc");
         mv.addObject("sgs", sgs);
         mv.addObject("storeTools", this.storeTools);
       }
       if (store_status == 1) {
         mv = new JModelAndView("error.html", 
           this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         mv.addObject("op_title", "您的店铺正在审核中");
         mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
       }
       if (store_status == 2) {
         mv = new JModelAndView("error.html", 
           this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         mv.addObject("op_title", "您已经开通店铺");
         mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
       }
       if (store_status == 3) {
         mv = new JModelAndView("error.html", 
           this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         mv.addObject("op_title", "您的店铺已经被关闭");
         mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
       }
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统暂时关闭了申请店铺");
       mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="申请店铺第二步", value="/seller/store_create_second.htm*", rtype="buyer", rname="申请店铺", rcode="create_store", rgroup="申请店铺")
   @RequestMapping({"/seller/store_create_second.htm"})
   public ModelAndView store_create_second(HttpServletRequest request, HttpServletResponse response, String grade_id) {
     ModelAndView mv = null;
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     int store_status = store == null ? 0 : store.getStore_status();
     if (this.configService.getSysConfig().getStore_allow()) {
       if ((grade_id == null) || (grade_id.equals(""))) {
         mv = new JModelAndView("store_create_first.html", 
           this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, response);
         List sgs = this.storeGradeService.selectList(new StoreGrade(), "sequence asc");
         mv.addObject("sgs", sgs);
         mv.addObject("storeTools", this.storeTools);
       } else {
         if (store_status == 0) {
           mv = new JModelAndView("store_create_second.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 1, request, 
             response);
          
           List areas = this.areaService.selectList("where parent_id is null",null);
        
           List scs = this.storeClassService.selectList("where parent_id is null",null);
           String store_create_session = CommUtil.randomString(32);
           request.getSession(false).setAttribute(
             "store_create_session", store_create_session);
           mv.addObject("store_create_session", store_create_session);
           mv.addObject("scs", scs);
           mv.addObject("areas", areas);
           mv.addObject("grade_id", grade_id);
         }
         if (store_status == 1) {
           mv = new JModelAndView("error.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 1, request, 
             response);
           mv.addObject("op_title", "您的店铺正在审核中");
           mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
         }
         if (store_status == 2) {
           mv = new JModelAndView("error.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 1, request, 
             response);
           mv.addObject("op_title", "您已经开通店铺");
           mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
         }
         if (store_status == 3) {
           mv = new JModelAndView("error.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 1, request, 
             response);
           mv.addObject("op_title", "您的店铺已经被关闭");
           mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
         }
       }
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统暂时关闭了申请店铺");
       mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="申请店铺完成", value="/seller/store_create_finish.htm*", rtype="buyer", rname="申请店铺", rcode="create_store", rgroup="申请店铺")
   @RequestMapping({"/seller/store_create_finish.htm"})
   public ModelAndView store_create_finish(HttpServletRequest request, HttpServletResponse response, Store store, String sc_id, String grade_id, String area_id, String store_create_session) {
     ModelAndView mv = new JModelAndView("success.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     String store_create_session1 = CommUtil.null2String(request.getSession(
       false).getAttribute("store_create_session"));
     if ((!store_create_session1.equals("")) && 
       (store_create_session1.equals(store_create_session))) {
    	 Store user_store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
       int store_status = user_store == null ? 0 : user_store.getStore_status();
       if (store_status == 0) {
//         WebForm wf = new WebForm();
//         Store store = (Store)wf.toPo(Store.class);
         StoreClass sc = this.storeClassService.selectById(Long.valueOf(Long.parseLong(sc_id)));
         store.setSc(sc);
         StoreGrade grade = this.storeGradeService.selectById(Long.valueOf(Long.parseLong(grade_id)));
         store.setGrade(grade);
         Area area = this.areaService.selectById(Long.valueOf(Long.parseLong(area_id)));
         store.setArea(area);
         store.setTemplate("default");
         store.setAddTime(new Date());
         store.setStore_second_domain("shop" + SecurityUserHolder.getCurrentUser().getId().toString());
         if (store.getGrade().getAudit())
             store.setStore_status(1);
           else {
             store.setStore_status(2);
           }
         this.storeService.insertSelective(store);
         StorePoint point=new StorePoint();
         point.setAddTime(new Date());
         point.setStore_id(store.getId());
         this.storePointService.insertSelective(point);
 
         String path = request.getSession().getServletContext().getRealPath("/") + 
           File.separator + "upload" + File.separator + "store" + File.separator + store.getId();
         CommUtil.createFolder(path);
 
         String store_url = CommUtil.getURL(request) + "/store_" + store.getId() + ".htm";
         QRCodeEncoderHandler handler = new QRCodeEncoderHandler();
         handler.encoderQRCode(store_url, path + "/code.png");
         User user = this.userService.selectById(SecurityUserHolder.getCurrentUser().getId());
         user.setStore_id(store.getId());
         SecurityUserHolder.getCurrentUser().setStore(store);
        
         if (user.getUserRole().equals("BUYER")) {
           user.setUserRole("BUYER_SELLER");
         }
         if (user.getUserRole().equals("ADMIN")) {
           user.setUserRole("ADMIN_BUYER_SELLER");
         }
 
       
         Role sRole=new Role();
         sRole.setType("SELLER");
        
         this.userService.updateSelectiveById(user);
         List<Role> roles = this.roleService.selectList(sRole);
         user.setRoles(roles);
         List<UserRole> urList=new ArrayList<UserRole>();
       
           for(Role role : roles){
          	UserRole ur=new UserRole(user.getId(),role.getId()); 
          	urList.add(ur);
           }
           userService.insertBatchUserRole(urList);
       
         
         mv.addObject("op_title", "店铺申请成功");
       } else {
         mv = new JModelAndView("error.html", 
           this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         if (store_status == 1) {
           mv = new JModelAndView("error.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 1, request, 
             response);
           mv.addObject("op_title", "您的店铺正在审核中");
           mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
         }
         if (store_status == 2) {
           mv = new JModelAndView("error.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 1, request, response);
           mv.addObject("op_title", "您已经开通店铺");
           mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
         }
         if (store_status == 3) {
           mv = new JModelAndView("error.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 1, request, response);
           mv.addObject("op_title", "您的店铺已经被关闭");
           mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
         }
       }
       mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
       request.getSession(false).removeAttribute("store_create_session");
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, response);
       mv.addObject("op_title", "表单已经失效");
       mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="店铺设置", value="/seller/store_set.htm*", rtype="seller", rname="店铺设置", rcode="store_set_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_set.htm"})
   public ModelAndView store_set(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/store_set.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     Accessory ac=accessoryService.selectById(store.getStore_logo_id());
     store.setStore_logo(ac);
     mv.addObject("store", store);
     mv.addObject("areaViewTools", this.areaViewTools);
    
     List areas = this.areaService.selectList("where parent_id is null",null);
     mv.addObject("areas", areas);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="店铺设置保存", value="/seller/store_set_save.htm*", rtype="seller", rname="店铺设置", rcode="store_set_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_set_save.htm"})
   public ModelAndView store_set_save(HttpServletRequest request, HttpServletResponse response, String area_id, String store_second_domain) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/success.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     WebForm wf = new WebForm();
     wf.toPo(store);
 
     String uploadFilePath = this.configService.getSysConfig()
       .getUploadFilePath();
     String saveFilePathName = request.getSession().getServletContext()
       .getRealPath("/") + 
       uploadFilePath + "/store_logo";
     Map map = new HashMap();
     try {
    	 Accessory storeLogo=accessoryService.selectById(store.getStore_logo_id());
       String fileName = storeLogo == null ? "" : storeLogo.getName();
       map = CommUtil.saveFileToServer(request, "logo", saveFilePathName, 
         fileName, null);
       if (fileName.equals("")) {
         if (map.get("fileName") != "") {
           Accessory store_logo = new Accessory();
           store_logo
             .setName(CommUtil.null2String(map.get("fileName")));
           store_logo.setExt(CommUtil.null2String(map.get("mime")));
           store_logo
             .setSize(CommUtil.null2Float(map.get("fileSize")));
           store_logo.setPath(uploadFilePath + "/store_logo");
           store_logo.setWidth(CommUtil.null2Int(map.get("width")));
           store_logo.setHeight(CommUtil.null2Int(map.get("height")));
           store_logo.setAddTime(new Date());
           this.accessoryService.insertSelective(store_logo);
           store.setStore_logo_id(store_logo.getId());
         }
       }
       else if (map.get("fileName") != "") {
         Accessory store_logo = storeLogo;
         store_logo
           .setName(CommUtil.null2String(map.get("fileName")));
         store_logo.setExt(CommUtil.null2String(map.get("mime")));
         store_logo
           .setSize(CommUtil.null2Float(map.get("fileSize")));
         store_logo.setPath(uploadFilePath + "/store_logo");
         store_logo.setWidth(CommUtil.null2Int(map.get("width")));
         store_logo.setHeight(CommUtil.null2Int(map.get("height")));
         this.accessoryService.updateSelectiveById(store_logo);
       }
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
 
     saveFilePathName = request.getSession().getServletContext()
       .getRealPath("/") + 
       this.configService.getSysConfig().getUploadFilePath() + 
       "/store_banner";
     try {
    	 Accessory storeban=accessoryService.selectById(store.getStore_banner_id());
       String fileName = storeban == null ? "" : storeban.getName();
       map = CommUtil.saveFileToServer(request, "banner", 
         saveFilePathName, fileName, null);
       if (fileName.equals("")) {
         if (map.get("fileName") != "") {
           Accessory store_banner = new Accessory();
           store_banner.setName(CommUtil.null2String(map
             .get("fileName")));
           store_banner.setExt(CommUtil.null2String(map.get("mime")));
           store_banner.setSize(CommUtil.null2Float(map
             .get("fileSize")));
           store_banner.setPath(uploadFilePath + "/store_banner");
           store_banner.setWidth(CommUtil.null2Int(map.get("width")));
           store_banner
             .setHeight(CommUtil.null2Int(map.get("height")));
           store_banner.setAddTime(new Date());
           this.accessoryService.insertSelective(store_banner);
           store.setStore_banner_id(store_banner.getId());
         }
       }
       else if (map.get("fileName") != "") {
         Accessory store_banner = storeban;
         store_banner.setName(CommUtil.null2String(map
           .get("fileName")));
         store_banner.setExt(CommUtil.null2String(map.get("mime")));
         store_banner.setSize(CommUtil.null2Float(map
           .get("fileSize")));
         store_banner.setPath(uploadFilePath + "/store_banner");
         store_banner.setWidth(CommUtil.null2Int(map.get("width")));
         store_banner
           .setHeight(CommUtil.null2Int(map.get("height")));
         this.accessoryService.updateSelectiveById(store_banner);
       }
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
     Area area = this.areaService.selectById(CommUtil.null2Long(area_id));
     store.setArea(area);
     if (this.configService.getSysConfig().getSecond_domain_open())
     {
       if ((this.configService.getSysConfig().getDomain_allow_count() > store
         .getDomain_modify_count()) && 
         (!CommUtil.null2String(store_second_domain).equals("")))
       {
         if (!store_second_domain.equals(store
           .getStore_second_domain())) {
           store.setStore_second_domain(store_second_domain);
           store.setDomain_modify_count(store.getDomain_modify_count() + 1);
         }
       }
     }
     this.storeService.updateSelectiveById(store);
     mv.addObject("op_title", "店铺设置成功");
     mv.addObject("url", CommUtil.getURL(request) + "/seller/store_set.htm");
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="店铺地图", value="/seller/store_map.htm*", rtype="seller", rname="店铺设置", rcode="store_set_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_map.htm"})
   public ModelAndView store_map(HttpServletRequest request, HttpServletResponse response, String map_type) {
	   Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     if (CommUtil.null2String(map_type).equals("")) {
       if ((store.getMap_type() != null) && (!store.getMap_type().equals("")))
         map_type = store.getMap_type();
       else {
         map_type = "baidu";
       }
     }
 
     ModelAndView mv = new JModelAndView("user/default/usercenter/store_" + 
       map_type + "_map.html", this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     mv.addObject("store", store);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="店铺地图保存", value="/seller/store_map_save.htm*", rtype="seller", rname="店铺设置", rcode="store_set_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_map_save.htm"})
   public ModelAndView store_map_save(HttpServletRequest request, HttpServletResponse response, String store_lat, String store_lng) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/success.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     store.setStore_lat(BigDecimal.valueOf(CommUtil.null2Double(store_lat)));
     store.setStore_lng(BigDecimal.valueOf(CommUtil.null2Double(store_lng)));
     this.storeService.updateSelectiveById(store);
     mv.addObject("op_title", "店铺设置成功");
     mv.addObject("url", CommUtil.getURL(request) + "/seller/store_map.htm");
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="主题设置", value="/seller/store_theme.htm*", rtype="seller", rname="主题设置", rcode="store_theme_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_theme.htm"})
   public ModelAndView store_theme(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/store_theme.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     StoreGrade sg=storeGradeService.selectById(store.getGrade_id());
     store.setGrade(sg);
     mv.addObject("store", store);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="主题设置", value="/seller/store_theme_save.htm*", rtype="seller", rname="主题设置", rcode="store_theme_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_theme_save.htm"})
   public String store_theme_set(HttpServletRequest request, HttpServletResponse response, String theme) {
	   Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     if (store.getGrade().getTemplates().indexOf(theme) >= 0) {
       store.setTemplate(theme);
       this.storeService.updateSelectiveById(store);
     }
     return "redirect:store_theme.htm";
   }
   @SecurityMapping(display = false, rsequence = 0, title="店铺认证", value="/seller/store_approve.htm*", rtype="seller", rname="店铺设置", rcode="store_set_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_approve.htm"})
   public ModelAndView store_approve(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/store_approve.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     mv.addObject("store", store);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="店铺认证保存", value="/seller/store_approve_save.htm*", rtype="seller", rname="店铺设置", rcode="store_set_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_approve_save.htm"})
   public String store_approve_save(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/store_approve.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
 
     String uploadFilePath = this.configService.getSysConfig()
       .getUploadFilePath();
     String saveFilePathName = request.getSession().getServletContext()
       .getRealPath("/") + 
       uploadFilePath;
     Map map = new HashMap();
     try {
    	 Accessory card=accessoryService.selectById(store.getCard_id());
       String fileName = card == null ? "" : card
         .getName();
       map = CommUtil.saveFileToServer(request, "card_img", 
         saveFilePathName + File.separator + "card", fileName, null);
       if (fileName.equals("")) {
         if (map.get("fileName") != "") {
         //  Accessory card = new Accessory();
           card.setName(CommUtil.null2String(map.get("fileName")));
           card.setExt(CommUtil.null2String(map.get("mime")));
           card.setSize(CommUtil.null2Float(map.get("fileSize")));
           card.setPath(uploadFilePath + "/card");
           card.setWidth(CommUtil.null2Int(map.get("width")));
           card.setHeight(CommUtil.null2Int(map.get("height")));
           card.setAddTime(new Date());
           this.accessoryService.insertSelective(card);
           store.setCard_id(card.getId());
         }
       }
       else if (map.get("fileName") != "") {
         //Accessory card = store.getCard();
         card.setName(CommUtil.null2String(map.get("fileName")));
         card.setExt(CommUtil.null2String(map.get("mime")));
         card.setSize(CommUtil.null2Float(map.get("fileSize")));
         card.setPath(uploadFilePath + "/card");
         card.setWidth(CommUtil.null2Int(map.get("width")));
         card.setHeight(CommUtil.null2Int(map.get("height")));
         this.accessoryService.updateSelectiveById(card);
       }
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
     try
     {
    	 Accessory license=accessoryService.selectById(store.getStore_license_id());
       String fileName = license == null ? "" :license.getName();
       map = CommUtil.saveFileToServer(request, "license_img", 
         saveFilePathName + File.separator + "license", fileName, 
         null);
       if (fileName.equals("")) {
         if (map.get("fileName") != "") {
           Accessory store_license = new Accessory();
           store_license.setName(CommUtil.null2String(map
             .get("fileName")));
           store_license.setExt(CommUtil.null2String(map.get("mime")));
           store_license.setSize(CommUtil.null2Float(map
             .get("fileSize")));
           store_license.setPath(uploadFilePath + "/license");
           store_license.setWidth(CommUtil.null2Int(map.get("width")));
           store_license
             .setHeight(CommUtil.null2Int(map.get("height")));
           store_license.setAddTime(new Date());
           this.accessoryService.insertSelective(store_license);
           store.setStore_license_id(store_license.getId());
         }
       }
       else if (map.get("fileName") != "") {
         Accessory store_license = license;
         store_license.setName(CommUtil.null2String(map
           .get("fileName")));
         store_license.setExt(CommUtil.null2String(map.get("mime")));
         store_license.setSize(CommUtil.null2Float(map
           .get("fileSize")));
         store_license.setPath(uploadFilePath + "/license");
         store_license.setWidth(CommUtil.null2Int(map.get("width")));
         store_license
           .setHeight(CommUtil.null2Int(map.get("height")));
         this.accessoryService.updateSelectiveById(store_license);
       }
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
     this.storeService.updateSelectiveById(store);
     return "redirect:store_approve.htm";
   }
   @SecurityMapping(display = false, rsequence = 0, title="店铺升级", value="/seller/store_grade.htm*", rtype="seller", rname="店铺设置", rcode="store_set_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_grade.htm"})
   public ModelAndView store_grade(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/store_grade.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     if (store.getUpdate_grade_id() == null) {
    	 
       List sgs = this.storeGradeService.selectList(new StoreGrade(), "sequence asc");
       mv.addObject("sgs", sgs);
       mv.addObject("store", store);
     } else {
       mv = new JModelAndView("user/default/usercenter/error.html", 
         this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 0, request, 
         response);
       mv.addObject("op_title", "您的店铺升级正在审核中");
       mv.addObject("url", CommUtil.getURL(request) + 
         "/seller/store_set.htm");
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="店铺升级申请完成", value="/seller/store_grade_finish.htm*", rtype="seller", rname="店铺设置", rcode="store_set_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_grade_finish.htm"})
   public ModelAndView store_grade_finish(HttpServletRequest request, HttpServletResponse response, String grade_id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/success.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
 
//     store.setUpdate_grade(this.storeGradeService.selectById(
//       CommUtil.null2Long(grade_id)));
     store.setUpdate_grade_id(
    	       CommUtil.null2Long(grade_id));
     this.storeService.updateSelectiveById(store);
     StoreGradeLog grade_log = new StoreGradeLog();
     grade_log.setAddTime(new Date());
     grade_log.setStore_id(store.getId());
     this.storeGradeLogService.insertSelective(grade_log);
     mv.addObject("op_title", "申请提交成功");
     mv.addObject("url", CommUtil.getURL(request) + "/seller/store_set.htm");
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="店铺幻灯", value="/seller/store_slide.htm*", rtype="seller", rname="店铺设置", rcode="store_set_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_slide.htm"})
   public ModelAndView store_slide(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/store_slide.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     mv.addObject("store", store);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="店铺幻灯保存", value="/seller/store_slide_save.htm*", rtype="seller", rname="店铺设置", rcode="store_set_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_slide_save.htm"})
   public ModelAndView store_slide_save(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/store_slide.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     String uploadFilePath = this.configService.getSysConfig()
       .getUploadFilePath();
     String saveFilePathName = request.getSession().getServletContext()
       .getRealPath("/") + 
       uploadFilePath + File.separator + "store_slide";
     for (int i = 1; i <= 5; i++) {
       Map map = new HashMap();
       String fileName = "";
       StoreSlide slide = null;
       StoreSlide sStoreSlide=new StoreSlide();
       sStoreSlide.setStore_id(store.getId());
       List<StoreSlide> slidList=storeSlideService.selectList(sStoreSlide);
       if (slidList.size() >= i) {
    	   Accessory acc=accessoryService.selectById(slidList.get(i - 1).getAcc_id());
         fileName = acc.getName();
         slide = slidList.get(i - 1);
       }
       try {
         map = CommUtil.saveFileToServer(request, "acc" + i, 
           saveFilePathName, fileName, null);
         Accessory acc = null;
         if (fileName.equals("")) {
           if (map.get("fileName") != "") {
             acc = new Accessory();
             acc.setName(CommUtil.null2String(map.get("fileName")));
             acc.setExt(CommUtil.null2String(map.get("mime")));
             acc.setSize(CommUtil.null2Float(map.get("fileSize")));
             acc.setPath(uploadFilePath + "/store_slide");
             acc.setWidth(CommUtil.null2Int(map.get("width")));
             acc.setHeight(CommUtil.null2Int(map.get("height")));
             acc.setAddTime(new Date());
             this.accessoryService.insertSelective(acc);
           }
         }
         else if (map.get("fileName") != "") {
           acc = accessoryService.selectById(slide.getAcc_id());
           acc.setName(CommUtil.null2String(map.get("fileName")));
           acc.setExt(CommUtil.null2String(map.get("mime")));
           acc.setSize(CommUtil.null2Float(map.get("fileSize")));
           acc.setPath(uploadFilePath + "/store_slide");
           acc.setWidth(CommUtil.null2Int(map.get("width")));
           acc.setHeight(CommUtil.null2Int(map.get("height")));
           acc.setAddTime(new Date());
           this.accessoryService.updateSelectiveById(acc);
         }
 
         if (acc != null) {
           if (slide == null) {
             slide = new StoreSlide();
             slide.setAcc_id(acc.getId());
             slide.setAddTime(new Date());
             slide.setStore_id(store.getId());
           }
           slide.setUrl(request.getParameter("acc_url" + i));
           if (slide == null)
             this.storeSlideService.insertSelective(slide);
           else
             this.storeSlideService.updateSelectiveById(slide);
         }
       }
       catch (IOException e)
       {
         e.printStackTrace();
       }
     }
     mv.addObject("store", store);
     return mv;
   }
 }


 
 
 