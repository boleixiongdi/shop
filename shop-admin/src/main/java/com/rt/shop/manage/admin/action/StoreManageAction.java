 package com.rt.shop.manage.admin.action;
 
 import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.easyjf.beans.BeanUtils;
import com.easyjf.beans.BeanWrapper;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.Album;
import com.rt.shop.entity.Area;
import com.rt.shop.entity.Evaluate;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.GoodsCart;
import com.rt.shop.entity.Message;
import com.rt.shop.entity.OrderForm;
import com.rt.shop.entity.Role;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.StoreClass;
import com.rt.shop.entity.StoreGrade;
import com.rt.shop.entity.StoreGradeLog;
import com.rt.shop.entity.SysConfig;
import com.rt.shop.entity.Template;
import com.rt.shop.entity.User;
import com.rt.shop.entity.UserRole;
import com.rt.shop.entity.query.StoreGradeLogQueryObject;
import com.rt.shop.entity.query.StoreQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IAlbumService;
import com.rt.shop.service.IAreaService;
import com.rt.shop.service.IConsultService;
import com.rt.shop.service.IEvaluateService;
import com.rt.shop.service.IGoodsCartService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IMessageService;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.IOrderLogService;
import com.rt.shop.service.IRoleService;
import com.rt.shop.service.IStoreClassService;
import com.rt.shop.service.IStoreGradeService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.IStoregradeLogService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.ITemplateService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.tools.AreaManageTools;
import com.rt.shop.tools.StoreTools;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class StoreManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IStoreService storeService;
 
   @Autowired
   private IStoreGradeService storeGradeService;
 
   @Autowired
   private IStoreClassService storeClassService;
 
   @Autowired
   private IAreaService areaService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IRoleService roleService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IConsultService consultService;
 
   @Autowired
   private AreaManageTools areaManageTools;
 
   @Autowired
   private StoreTools storeTools;
 

 
   @Autowired
   private ITemplateService templateService;
 
   @Autowired
   private IMessageService messageService;
 
   @Autowired
   private IStoregradeLogService storeGradeLogService;
 
   @Autowired
   private IEvaluateService evaluateService;
 
   @Autowired
   private IGoodsCartService goodsCartService;
 
   @Autowired
   private IOrderFormService orderFormService;
 
   @Autowired
   private IOrderLogService orderFormLogService;
 
   @Autowired
   private IAccessoryService accessoryService;
 
   @Autowired
   private IAlbumService albumService;
 
   @SecurityMapping(display = false, rsequence = 0, title="店铺列表", value="/admin/store_list.htm*", rtype="admin", rname="店铺管理", rcode="admin_store_set", rgroup="店铺")
   @RequestMapping({"/admin/store_list.htm"})
   public ModelAndView store_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView("admin/blue/store_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     String params = "";
     StoreQueryObject qo = new StoreQueryObject(currentPage, mv, orderBy, 
       orderType);
     WebForm wf = new WebForm();
     wf.toQueryPo(request, qo, Store.class, mv);
     Page pList = this.storeService.selectPage(new Page<Store>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView(url + "/admin/store_list.htm", "", 
       params, pList, mv);
     List grades = this.storeGradeService.selectList(new StoreGrade(),"sequence asc");
     mv.addObject("grades", grades);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="店铺添加1", value="/admin/store_add.htm*", rtype="admin", rname="店铺管理", rcode="admin_store_set", rgroup="店铺")
   @RequestMapping({"/admin/store_add.htm"})
   public ModelAndView store_add(HttpServletRequest request, HttpServletResponse response, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/store_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="店铺添加2", value="/admin/store_new.htm*", rtype="admin", rname="店铺管理", rcode="admin_store_set", rgroup="店铺")
   @RequestMapping({"/admin/store_new.htm"})
   public ModelAndView store_new(HttpServletRequest request, HttpServletResponse response, String currentPage, String userName, String list_url, String add_url)
   {
     ModelAndView mv = new JModelAndView("admin/blue/store_new.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     User user = this.userService.selectUserByUsername(userName);
     Store store = null;
     if (user != null)
       store = this.storeService.selectById(user.getStore_id());
     if (user == null) {
       mv = new JModelAndView("admin/blue/tip.html", this.configService
         .getSysConfig(), this.userConfigService.getUserConfig(), 0, 
         request, response);
       mv.addObject("op_tip", "不存在该用户");
       mv.addObject("list_url", list_url);
     }
     else if (store == null) {
    	 StoreClass sStoreClass=new StoreClass();
    	 sStoreClass.setParent_id(null);
       List scs = this.storeClassService.selectList(sStoreClass,"sequence asc");
       Area sArea=new Area();
       sArea.setParent_id(null);
       List areas = this.areaService.selectList(sArea);
       
       List grades = this.storeGradeService.selectList(new StoreGrade(), "sequence asc");
       mv.addObject("grades", grades);
       mv.addObject("areas", areas);
       mv.addObject("scs", scs);
       mv.addObject("currentPage", currentPage);
       mv.addObject("user", user);
     } else {
       mv = new JModelAndView("admin/blue/tip.html", this.configService
         .getSysConfig(), 
         this.userConfigService.getUserConfig(), 0, request, 
         response);
       mv.addObject("op_tip", "该用户已经开通店铺");
       mv.addObject("list_url", add_url);
     }
 
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="店铺编辑", value="/admin/store_edit.htm*", rtype="admin", rname="店铺管理", rcode="admin_store_set", rgroup="店铺")
   @RequestMapping({"/admin/store_edit.htm"})
   public ModelAndView store_edit(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/store_edit.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       Store store = this.storeService.selectById(Long.valueOf(Long.parseLong(id)));
      
       StoreClass sStoreClass=new StoreClass();
  	 sStoreClass.setParent_id(null);
       List scs = this.storeClassService.selectList(sStoreClass,"sequence asc");
       Area sArea=new Area();
       sArea.setParent_id(null);
       List areas = this.areaService.selectList(sArea);
       
       List grades = this.storeGradeService.selectList(new StoreGrade(), "sequence asc");
       mv.addObject("areas", areas);
       mv.addObject("scs", scs);
       mv.addObject("obj", store);
       mv.addObject("currentPage", currentPage);
       mv.addObject("edit", Boolean.valueOf(true));
       if (store.getArea_id() != null) {
         mv.addObject("area_info", this.areaManageTools
           .generic_area_info(areaService.selectById(store.getArea_id())));
       }
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="店铺保存", value="/admin/store_save.htm*", rtype="admin", rname="店铺管理", rcode="admin_store_set", rgroup="店铺")
   @RequestMapping({"/admin/store_save.htm"})
   public ModelAndView store_save(HttpServletRequest request, HttpServletResponse response, String id, String area_id, String sc_id, String grade_id, String user_id, String store_status, String currentPage, String cmd, String list_url, String add_url)
     throws Exception
   {
     WebForm wf = new WebForm();
     Store store = null;
     if (id.equals("")) {
       store = (Store)wf.toPo(request, Store.class);
       store.setAddTime(new Date());
     } else {
       Store obj = this.storeService.selectById(Long.valueOf(Long.parseLong(id)));
       store = (Store)wf.toPo(request, obj);
     }
     Area area = this.areaService.selectById(CommUtil.null2Long(area_id));
     store.setArea_id(area.getId());
     StoreClass sc = this.storeClassService
       .selectById(Long.valueOf(Long.parseLong(sc_id)));
     store.setSc_id(sc.getId());
     store.setTemplate("default");
     if ((grade_id != null) && (!grade_id.equals(""))) {
       store.setGrade(this.storeGradeService.selectById(
         Long.valueOf(Long.parseLong(grade_id))));
     }
     if ((store_status != null) && (!store_status.equals("")))
       store.setStore_status(CommUtil.null2Int(store_status));
     else
       store.setStore_status(2);
     if (store.getStore_recommend())
       store.setStore_recommend_time(new Date());
     else
       store.setStore_recommend_time(null);
     if (id.equals(""))
       this.storeService.insertSelective(store);
     else
       this.storeService.updateSelectiveById(store);
     if ((user_id != null) && (!user_id.equals(""))) {
       User user = this.userService.selectById(Long.valueOf(Long.parseLong(user_id)));
       user.setStore_id(store.getId());
       user.setUserRole("BUYER_SELLER");
 
      
       Role sRole=new Role();
       sRole.setType("SELLER");
    
       List<Role> roles = this.roleService.selectList(sRole);
       user.setRoles(roles);
       List<UserRole> urList=new ArrayList<UserRole>();
     
         for(Role role : roles){
        	UserRole ur=new UserRole(user.getId(),role.getId()); 
        	urList.add(ur);
         }
         userService.insertBatchUserRole(urList);
       this.userService.updateSelectiveById(user);
     }
 
     if ((!id.equals("")) && (store.getStore_status() == 3)) {
       send_site_msg(request, "msg_toseller_store_closed_notify", 
         store);
     }
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("list_url", list_url);
     mv.addObject("op_title", "保存店铺成功");
     if (add_url != null) {
       mv.addObject("add_url", add_url + "?currentPage=" + currentPage);
     }
     return mv;
   }
 
   private void send_site_msg(HttpServletRequest request, String mark, Store store) throws Exception
   {
     Template template = this.templateService.selectByMark(mark);
     if (template.getOpen()) {
       String path = request.getRealPath("/") + "/vm/";
       PrintWriter pwrite = new PrintWriter(
         new OutputStreamWriter(new FileOutputStream(path + "msg.vm", false), "UTF-8"));
       pwrite.print(template.getContent());
       pwrite.flush();
       pwrite.close();
 
       Properties p = new Properties();
       p.setProperty("file.resource.loader.path", request
         .getRealPath("/") + 
         "vm" + File.separator);
       p.setProperty("input.encoding", "UTF-8");
       p.setProperty("output.encoding", "UTF-8");
       Velocity.init(p);
       org.apache.velocity.Template blank = Velocity.getTemplate("msg.vm", 
         "UTF-8");
       	User sUser=new User();
		sUser.setStore_id(store.getId());
		User storeUser=userService.selectOne(sUser);
		
       VelocityContext context = new VelocityContext();
       context.put("reason", store.getViolation_reseaon());
       context.put("user", storeUser);
       context.put("config", this.configService.getSysConfig());
       context.put("send_time", CommUtil.formatLongDate(new Date()));
       StringWriter writer = new StringWriter();
       blank.merge(context, writer);
       String content = writer.toString();
       User fromUser = this.userService.selectUserByUsername("admin");
       Message msg = new Message();
       msg.setAddTime(new Date());
       msg.setContent(content);
       msg.setFromUser_id(fromUser.getId());
       msg.setTitle(template.getTitle());
       msg.setToUser_id(storeUser.getId());
       msg.setType(0);
       this.messageService.insertSelective(msg);
       CommUtil.deleteFile(path + "msg.vm");
       writer.flush();
       writer.close();
     }
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="店铺删除", value="/admin/store_del.htm*", rtype="admin", rname="店铺管理", rcode="admin_store_set", rgroup="店铺")
   @RequestMapping({"/admin/store_del.htm"})
   public String store_del(HttpServletRequest request, String mulitId) throws Exception {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         Store store = this.storeService.selectById(Long.valueOf(Long.parseLong(id)));
     	User sUser=new User();
		sUser.setStore_id(Long.parseLong(id));
		User storeUser=userService.selectOne(sUser);
         if (storeUser != null)
        	 storeUser.setStore_id(null);
         List<GoodsCart> goodCarts;
         Goods sGoods=new Goods();
        List<Goods> goodsList= goodsService.selectGoodsByStore(Long.parseLong(id));
         for (Goods goods : goodsList) {
          
           GoodsCart sGoodsCart=new GoodsCart();
           sGoodsCart.setGoods_id(goods.getId());
           goodCarts = this.goodsCartService.selectList(sGoodsCart);
           Long ofid = null;
           Map map2;
           List goodCarts2;
           for (GoodsCart gc : goodCarts) {
             gc.getGsps().clear();
             this.goodsCartService.deleteById(gc.getId());
             if (gc.getOf() != null) {
               
             
               GoodsCart ssGoodsCart=new GoodsCart();
               ssGoodsCart.setOf_id(gc.getOf_id());
               goodCarts2 = this.goodsCartService.selectList(ssGoodsCart);
               if (goodCarts2.size() == 0) {
                 this.orderFormService.deleteById(ofid);
               }
             }
           }
 
           List<Evaluate> evaluates = goods.getEvaluates();
           for (Evaluate e : evaluates) {
             this.evaluateService.deleteById(e.getId());
           }
           goods.getGoods_ugcs().clear();
           Accessory acc = goods.getGoods_main_photo();
           if (acc != null) {
             acc.setAlbum(null);
             Album album = acc.getCover_album();
             if (album != null) {
               album.setAlbum_cover_id(null);
               this.albumService.updateSelectiveById(album);
             }
             this.accessoryService.updateSelectiveById(acc);
           }
           for (Accessory acc1 : goods.getGoods_photos()) {
             if (acc1 != null) {
               acc1.setAlbum(null);
               Album album = acc1.getCover_album();
               if (album != null) {
                 album.setAlbum_cover_id(null);
                 this.albumService.updateSelectiveById(album);
               }
               acc1.setCover_album(null);
               this.accessoryService.updateSelectiveById(acc1);
             }
           }
           goods.getCombin_goods().clear();
           this.goodsService.deleteById(goods.getId());
         }
         OrderForm sOrderForm=new OrderForm();
         sOrderForm.setStore_id(Long.valueOf(Long.parseLong(id)));
         List<OrderForm> orderList=orderFormService.selectList(sOrderForm);
         for (OrderForm of : orderList) {
        	 GoodsCart sGoodsCart=new GoodsCart();
        	 sGoodsCart.setOf_id(of.getId());
        	 List<GoodsCart> gcList=goodsCartService.selectList(sGoodsCart);
           for (GoodsCart gc : gcList) {
             gc.getGsps().clear();
             this.goodsCartService.deleteById(gc.getId());
           }
           this.orderFormService.deleteById(of.getId());
         }
         this.storeService.deleteById(CommUtil.null2Long(id));
         send_site_msg(request, 
           "msg_toseller_goods_delete_by_admin_notify", store);
       }
     }
     return "redirect:store_list.htm";
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="店铺AJAX更新", value="/admin/store_ajax.htm*", rtype="admin", rname="店铺管理", rcode="admin_store_set", rgroup="店铺")
   @RequestMapping({"/admin/store_ajax.htm"})
   public void store_ajax(HttpServletRequest request, HttpServletResponse response, String id, String fieldName, String value) throws ClassNotFoundException {
     Store obj = this.storeService.selectById(Long.valueOf(Long.parseLong(id)));
     Field[] fields = Store.class.getDeclaredFields();
     BeanWrapper wrapper = new BeanWrapper(obj);
     Object val = null;
     for (Field field : fields)
     {
       if (field.getName().equals(fieldName)) {
         Class clz = Class.forName("java.lang.String");
         if (field.getType().getName().equals("int")) {
           clz = Class.forName("java.lang.Integer");
         }
         if (field.getType().getName().equals("boolean")) {
           clz = Class.forName("java.lang.Boolean");
         }
         if (!value.equals(""))
           val = BeanUtils.convertType(value, clz);
         else {
           val = Boolean.valueOf(
             !CommUtil.null2Boolean(wrapper
             .getPropertyValue(fieldName)));
         }
         wrapper.setPropertyValue(fieldName, val);
       }
     }
     if (fieldName.equals("store_recommend")) {
       if (obj.getStore_recommend())
         obj.setStore_recommend_time(new Date());
       else {
         obj.setStore_recommend_time(null);
       }
     }
     this.storeService.updateSelectiveById(obj);
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(val.toString());
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="卖家信用", value="/admin/store_base.htm*", rtype="admin", rname="基本设置", rcode="admin_store_base", rgroup="店铺")
   @RequestMapping({"/admin/store_base.htm"})
   public ModelAndView store_base(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView("admin/blue/store_base_set.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="卖家信用保存", value="/admin/store_set_save.htm*", rtype="admin", rname="基本设置", rcode="admin_store_base", rgroup="店铺")
   @RequestMapping({"/admin/store_set_save.htm"})
   public ModelAndView store_set_save(HttpServletRequest request, HttpServletResponse response, String id, String list_url, String store_allow) {
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     SysConfig sc = this.configService.getSysConfig();
     sc.setStore_allow(CommUtil.null2Boolean(store_allow));
     Map map = new HashMap();
     for (int i = 0; i <= 29; i++) {
       map.put("creditrule" + i, Integer.valueOf(CommUtil.null2Int(request
         .getParameter("creditrule" + i))));
     }
     String creditrule = Json.toJson(map, JsonFormat.compact());
     sc.setCreditrule(creditrule);
     if (id.equals(""))
       this.configService.insertSelective(sc);
     else
       this.configService.updateSelectiveById(sc);
     mv.addObject("list_url", list_url);
     mv.addObject("op_title", "保存店铺设置成功");
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="店铺模板", value="/admin/store_template.htm*", rtype="admin", rname="店铺模板", rcode="admin_store_template", rgroup="店铺")
   @RequestMapping({"/admin/store_template.htm"})
   public ModelAndView store_template(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView("admin/blue/store_template.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("path", request.getRealPath("/"));
     mv.addObject("separator", File.separator);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="店铺模板增加", value="/admin/store_template_add.htm*", rtype="admin", rname="店铺模板", rcode="admin_store_template", rgroup="店铺")
   @RequestMapping({"/admin/store_template_add.htm"})
   public ModelAndView store_template_add(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView(
       "admin/blue/store_template_add.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="店铺模板保存", value="/admin/store_template_save.htm*", rtype="admin", rname="店铺模板", rcode="admin_store_template", rgroup="店铺")
   @RequestMapping({"/admin/store_template_save.htm"})
   public ModelAndView store_template_save(HttpServletRequest request, HttpServletResponse response, String id, String list_url, String templates) {
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     SysConfig sc = this.configService.getSysConfig();
     sc.setTemplates(templates);
     if (id.equals(""))
       this.configService.insertSelective(sc);
     else
       this.configService.updateSelectiveById(sc);
     mv.addObject("list_url", list_url);
     mv.addObject("op_title", "店铺模板设置成功");
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="店铺升级列表", value="/admin/store_gradelog_list.htm*", rtype="admin", rname="店铺管理", rcode="admin_store_set", rgroup="店铺")
   @RequestMapping({"/admin/store_gradelog_list.htm"})
   public ModelAndView store_gradelog_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String store_name, String grade_id, String store_grade_status)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/store_gradelog_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     String params = "";
     StoreGradeLogQueryObject qo = new StoreGradeLogQueryObject(currentPage, 
       mv, orderBy, orderType);
     if (!CommUtil.null2String(store_name).equals("")) {
       qo.addQuery("obj.store.store_name", 
         new SysMap("store_name", "%" + 
         store_name + "%"), "like");
       mv.addObject("store_name", store_name);
     }
     if (CommUtil.null2Long(grade_id).longValue() != -1L) {
       qo.addQuery("obj.store.update_grade.id", 
         new SysMap("grade_id", 
         CommUtil.null2Long(grade_id)), "=");
       mv.addObject("grade_id", grade_id);
     }
     if (!CommUtil.null2String(store_grade_status).equals("")) {
       qo.addQuery("obj.store_grade_status", 
         new SysMap("store_grade_status", 
         Integer.valueOf(CommUtil.null2Int(store_grade_status))), "=");
       mv.addObject("store_grade_status", store_grade_status);
     }
     Page pList = this.storeGradeLogService.selectPage(new Page<StoreGradeLog>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView(url + "/admin/store_list.htm", "", 
       params, pList, mv);
     List grades = this.storeGradeService.selectList(new StoreGrade(),"sequence asc");
     mv.addObject("grades", grades);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="店铺升级编辑", value="/admin/store_gradelog_edit.htm*", rtype="admin", rname="店铺管理", rcode="admin_store_set", rgroup="店铺")
   @RequestMapping({"/admin/store_gradelog_edit.htm"})
   public ModelAndView store_gradelog_edit(HttpServletRequest request, HttpServletResponse response, String currentPage, String id) {
     ModelAndView mv = new JModelAndView(
       "admin/blue/store_gradelog_edit.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     StoreGradeLog obj = this.storeGradeLogService.selectById(
       CommUtil.null2Long(id));
     mv.addObject("obj", obj);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="店铺升级保存", value="/admin/store_gradelog_save.htm*", rtype="admin", rname="店铺管理", rcode="admin_store_set", rgroup="店铺")
   @RequestMapping({"/admin/store_gradelog_save.htm"})
   public ModelAndView store_gradelog_save(HttpServletRequest request, HttpServletResponse response, String currentPage, String id, String cmd, String list_url) throws Exception {
     WebForm wf = new WebForm();
     StoreGradeLog obj = this.storeGradeLogService.selectById(
       CommUtil.null2Long(id));
     StoreGradeLog log = (StoreGradeLog)wf.toPo(request, obj);
     log.setLog_edit(true);
     log.setAddTime(new Date());
     boolean ret = this.storeGradeLogService.updateSelectiveById(log);
     Store store = storeService.selectById(log.getStore_id());
     if (ret) {
      
       if (log.getStore_grade_status() == 1) {
         store.setGrade_id(store.getUpdate_grade_id());
       }
       store.setUpdate_grade_id(null);
       this.storeService.updateSelectiveById(store);
     }
 
     if (log.getStore_grade_status() == 1) {
       send_site_msg(request, 
         "msg_toseller_store_update_allow_notify", store);
     }
     if (log.getStore_grade_status() == -1) {
       send_site_msg(request, 
         "msg_toseller_store_update_refuse_notify", store);
     }
     send_site_msg(request, "msg_toseller_store_update_allow_notify", 
       store);
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("list_url", list_url);
     mv.addObject("op_title", "保存店铺成功");
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="店铺升级日志查看", value="/admin/store_gradelog_view.htm*", rtype="admin", rname="店铺管理", rcode="admin_store_set", rgroup="店铺")
   @RequestMapping({"/admin/store_gradelog_view.htm"})
   public ModelAndView store_gradelog_view(HttpServletRequest request, HttpServletResponse response, String currentPage, String id) {
     ModelAndView mv = new JModelAndView(
       "admin/blue/store_gradelog_view.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     StoreGradeLog obj = this.storeGradeLogService.selectById(
       CommUtil.null2Long(id));
     mv.addObject("obj", obj);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 }


 
 
 