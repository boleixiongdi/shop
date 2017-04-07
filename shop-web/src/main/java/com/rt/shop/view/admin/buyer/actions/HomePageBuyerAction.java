 package com.rt.shop.view.admin.buyer.actions;
 
 import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Dynamic;
import com.rt.shop.entity.Favorite;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.Homepage;
import com.rt.shop.entity.HomepageGoodsclass;
import com.rt.shop.entity.OrderForm;
import com.rt.shop.entity.User;
import com.rt.shop.entity.UserAttention;
import com.rt.shop.entity.UserFriend;
import com.rt.shop.entity.Visit;
import com.rt.shop.entity.query.DynamicQueryObject;
import com.rt.shop.entity.query.SnsAttentionQueryObject;
import com.rt.shop.entity.query.SnsFriendQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IDynamicService;
import com.rt.shop.service.IFavoriteService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IHomepageGoodsclassService;
import com.rt.shop.service.IHomepageService;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserAttentionService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserFriendService;
import com.rt.shop.service.IUserService;
import com.rt.shop.service.IVisitService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class HomePageBuyerAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IVisitService visitService;
 
   @Autowired
   private IHomepageService homePageService;
 
   @Autowired
   private IDynamicService dynamicService;
 
   @Autowired
   private IUserAttentionService attentionService;
 
   @Autowired
   private IUserFriendService snsFriendService;
 
   @Autowired
   private IFavoriteService favoriteService;
 
   @Autowired
   private IOrderFormService orderFormService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IHomepageGoodsclassService HomeGoodsClassService;
 
   @SecurityMapping(display = false, rsequence = 0, title="个人主页头部", value="/buyer/homepage_head.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage_head.htm"})
   public ModelAndView homepage_head(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/homepage_head.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     String uid = request.getParameter("uid");
     User user = new User();
     if ((uid != null) && (!uid.equals("")))
       user = this.userService.selectById(CommUtil.null2Long(uid));
     else {
       user = SecurityUserHolder.getCurrentUser();
     }
     mv.addObject("owner", user);
    
     Homepage sHomePage=new Homepage();
     sHomePage.setOwner_id(user.getId());
     List HomePages = this.homePageService.selectList(sHomePage);
   //    "select obj from HomePage obj where obj.owner.id=:uid", map, 
     if (HomePages.size() > 0) {
       mv.addObject("homePage", HomePages.get(0));
     }
     
     UserAttention sUserAttention=new UserAttention();
     sUserAttention.setToUser_id(user.getId());
     List fans = this.attentionService.selectList(sUserAttention);
       //"select obj from SnsAttention obj where obj.toUser.id=:uid", 
    
     UserAttention ssUserAttention=new UserAttention();
     ssUserAttention.setFromUser_id(user.getId());
     List attentions = this.attentionService.selectList(ssUserAttention);
    //   "select obj from SnsAttention obj where obj.fromUser.id=:uid", 
     mv.addObject("attentions_num", Integer.valueOf(attentions.size()));
     mv.addObject("fans_num", Integer.valueOf(fans.size()));
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="个人主页", value="/buyer/homepage.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage.htm"})
   public ModelAndView homepage(HttpServletRequest request, HttpServletResponse response, String type, String currentPage, String orderBy, String orderType, String uid, String goodclass_id)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/homepage.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Homepage home = new Homepage();
     User user = new User();
     if ((uid != null) && (!uid.equals("")))
       user = this.userService.selectById(CommUtil.null2Long(uid));
     else {
       user = SecurityUserHolder.getCurrentUser();
     }
     mv.addObject("owner", user);
     Map map = new HashMap();
     map.put("uid", user.getId());
     Homepage sHomePage=new Homepage();
     sHomePage.setOwner_id(user.getId());
     List homePages = this.homePageService.selectList(sHomePage);
      // "select obj from HomePage obj where obj.owner.id=:uid", map, 
     if (homePages.size() > 0) {
       home = (Homepage)homePages.get(0);
     } else {
       home.setOwner_id(SecurityUserHolder.getCurrentUser().getId());
       home.setAddTime(new Date());
       this.homePageService.insertSelective(home);
     }
 
     DynamicQueryObject qo = new DynamicQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.display", new SysMap("display", Boolean.valueOf(true)), "=");
     if ((type != null) && (!type.equals(""))) {
       mv.addObject("type", type);
       if (type.equals("1")) {
         qo.addQuery("obj.user.id", 
           new SysMap("uid", home.getOwner_id()
           ), "=");
         qo.addQuery("obj.store.id is not null", null);
        
         
         Dynamic sDynamic=new Dynamic();
         sDynamic.setStore_id(null);
         sDynamic.setUser_id(home.getOwner_id());
         List dynamics = this.dynamicService.selectList(sDynamic);
        //   "select obj from Dynamic obj where obj.store.id is not null and obj.user.id=:uid", 
         if (dynamics.size() > 0) {
           mv.addObject("allNum", Integer.valueOf(CommUtil.null2Int(Integer.valueOf(dynamics.size()))));
         }
       }
       if (type.equals("2")) {
         qo.addQuery("obj.user.id", 
           new SysMap("uid", home.getOwner_id()
          ), "=");
         qo.addQuery("obj.store.id is null", null);
         qo.addQuery("obj.goods.id is null", null);
         qo.addQuery("obj.dissParent.id is null", null);
         Map params = new HashMap();
         params.put("uid", home.getOwner_id());
         Dynamic sDynamic=new Dynamic();
         sDynamic.setStore_id(null);
         sDynamic.setUser_id(home.getOwner_id());
         List dynamics = this.dynamicService.selectList(sDynamic);
           
         if (dynamics.size() > 0)
           mv.addObject("allNum", Integer.valueOf(CommUtil.null2Int(Integer.valueOf(dynamics.size()))));
       }
     }
     else {
       qo.addQuery("obj.user.id", 
         new SysMap("uid", home.getOwner_id()
        ), "=");
       qo.addQuery("obj.goods.id is not null", null);
 
       Dynamic sDynamic=new Dynamic();
       sDynamic.setStore_id(null);
       sDynamic.setUser_id(home.getOwner_id());
       List dynamics = this.dynamicService.selectList(sDynamic);
       if (dynamics.size() > 0) {
         mv.addObject("allNum", Integer.valueOf(CommUtil.null2Int(Integer.valueOf(dynamics.size()))));
       }
       if ((goodclass_id != null) && (!goodclass_id.equals(""))) {
         mv.addObject("goodclass_id", goodclass_id);
         qo.addQuery("obj.goods.gc.id", 
           new SysMap("goodClass_id", 
           CommUtil.null2Long(goodclass_id)), "=");
       }
 
      
    
       HomepageGoodsclass hp=new HomepageGoodsclass();
       hp.setUser_id(home.getOwner_id());
       List hgcs = this.HomeGoodsClassService.selectList(hp);
      
 
       mv.addObject("hgcs", hgcs);
     }
 
     if ((uid != null) && (!uid.equals(""))) {
      
       Visit sVisit=new Visit();
       sVisit.setHomepage_id(home.getId());
       List custs =visitService.selectList(sVisit);
       if (custs.size() == 0) {
         Visit visit = new Visit();
         visit.setAddTime(new Date());
         visit.setHomepage_id(home.getId());
         visit.setUser_id(SecurityUserHolder.getCurrentUser().getId());
         this.visitService.insertSelective(visit);
       } else {
         map.clear();
         map.put("home_owner_id", home.getOwner_id());
         map.put("uid", SecurityUserHolder.getCurrentUser().getId());
         Visit ssVisit=new Visit();
         ssVisit.setUser_id(SecurityUserHolder.getCurrentUser().getId());
         
         List visits = this.visitService.selectList(ssVisit);//TODO
         //  "select obj from Visit obj where obj.user.id=:uid and obj.homepage.owner.id=:home_owner_id", 
         if (visits.size() > 0) {
           ((Visit)visits.get(0)).setAddTime(new Date());
           this.visitService.updateSelectiveById((Visit)visits.get(0));
         } else {
           Visit visit = new Visit();
           visit.setAddTime(new Date());
           visit.setHomepage_id(home.getId());
           visit.setUser_id(SecurityUserHolder.getCurrentUser().getId());
           this.visitService.insertSelective(visit);
         }
       }
     }
     map.clear();
     map.put("uid", home.getOwner_id());
     List visits = this.visitService.selectList(null);//TODO
      
//       "select obj from Visit obj where obj.homepage.owner.id=:uid order by addTime desc", 
//       map, -1, 10);
     mv.addObject("visits", visits);
     Page pList = this.dynamicService.selectPage(new Page<Dynamic>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="个人主页删除动态", value="/buyer/homepage_dynamic_del.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage_dynamic_del.htm"})
   public void homepage_dynamic_del(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String orderBy, String orderType, String type)
   {
     boolean flag = false;
     if ((id != null) && (!id.equals(""))) {
       Dynamic dynamic = this.dynamicService
         .selectById(Long.valueOf(Long.parseLong(id)));
       flag = this.dynamicService.deleteById(Long.valueOf(Long.parseLong(id)));
     }
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(flag);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="个人主页新鲜事加密", value="/buyer/homepage_dynamic_lock.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage_dynamic_lock.htm"})
   public void homepage_dynamic_lock(HttpServletRequest request, HttpServletResponse response, String dynamic_id)
   {
     Dynamic dynamic = this.dynamicService.selectById(
       CommUtil.null2Long(dynamic_id));
     boolean locked = dynamic.getLocked();
     if (!locked)
       dynamic.setLocked(true);
     else {
       dynamic.setLocked(false);
     }
     this.dynamicService.updateSelectiveById(dynamic);
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(dynamic.getLocked());
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="个人主页添加关注", value="/buyer/homepage_add_attention.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage_add_attention.htm"})
   public void homepage_add_attention(HttpServletRequest request, HttpServletResponse response, String user_id)
   {
     boolean flag = false;
    
     UserAttention sAttention = new UserAttention();
     sAttention.setFromUser_id(SecurityUserHolder.getCurrentUser().getId());
     sAttention.setToUser_id(CommUtil.null2Long(user_id));
     List SnsAttentions = this.attentionService.selectList(sAttention);
//       "select obj from SnsAttention obj where obj.fromUser.id=:uid and obj.toUser.id=:user_id ", 
     if (SnsAttentions.size() == 0) {
       User atted = this.userService.selectById(
         CommUtil.null2Long(user_id));
       UserAttention attention = new UserAttention();
       attention.setAddTime(new Date());
       attention.setFromUser_id(SecurityUserHolder.getCurrentUser().getId());
       attention.setToUser_id(atted.getId());
       flag = this.attentionService.insertSelective(attention);
     }
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(flag);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="个人主页添加关注", value="/buyer/homepage_remove_attention.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage_remove_attention.htm"})
   public void homepage_remove_attention(HttpServletRequest request, HttpServletResponse response, String id)
   {
     boolean flag = false;
     flag = this.attentionService.deleteById(CommUtil.null2Long(id));
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(flag);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="好友列表", value="/buyer/homepage/myfriends.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage/myfriends.htm"})
   public ModelAndView homepage_myfriends(HttpServletRequest request, HttpServletResponse response, String uid, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/homepage_myfriends.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     SnsFriendQueryObject qo = new SnsFriendQueryObject(currentPage, mv, 
       orderBy, orderType);
     User user = new User();
     if ((uid != null) && (!uid.equals("")))
       user = this.userService.selectById(CommUtil.null2Long(uid));
     else {
       user = SecurityUserHolder.getCurrentUser();
     }
     mv.addObject("owner", user);
     qo.addQuery("obj.fromUser.id", new SysMap("fromUser_id", user.getId()), 
       "=");
     Page pList = this.snsFriendService.selectPage(new Page<UserFriend>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="关注列表", value="/buyer/homepage/myattention.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage/myattention.htm"})
   public ModelAndView homepage_myattention(HttpServletRequest request, HttpServletResponse response, String uid, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/homepage_myattention.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     SnsAttentionQueryObject qo = new SnsAttentionQueryObject(currentPage, 
       mv, orderBy, orderType);
     User user = new User();
     if ((uid != null) && (!uid.equals("")))
       user = this.userService.selectById(CommUtil.null2Long(uid));
     else {
       user = SecurityUserHolder.getCurrentUser();
     }
     mv.addObject("owner", user);
     qo
       .addQuery("obj.fromUser.id", 
       new SysMap("user_id", user.getId()), "=");
     Page pList = this.attentionService.selectPage(new Page<UserAttention>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="关注列表", value="/buyer/homepage/myfans.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage/myfans.htm"})
   public ModelAndView homepage_myfans(HttpServletRequest request, HttpServletResponse response, String uid, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/homepage_myfans.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     SnsAttentionQueryObject qo = new SnsAttentionQueryObject(currentPage, 
       mv, orderBy, orderType);
     User user = new User();
     if ((uid != null) && (!uid.equals("")))
       user = this.userService.selectById(CommUtil.null2Long(uid));
     else {
       user = SecurityUserHolder.getCurrentUser();
     }
     mv.addObject("owner", user);
     qo.addQuery("obj.toUser.id", new SysMap("user_id", user.getId()), "=");
     Page pList = this.attentionService.selectPage(new Page<UserAttention>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="最近访客", value="/buyer/homepage_visit.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage_visit.htm"})
   public ModelAndView homepage_visit(HttpServletRequest request, HttpServletResponse response, String orderBy, String orderType, String currentPage)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/homepage_visit.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     String uid = request.getParameter("uid");
     User user = new User();
     if ((uid != null) && (!uid.equals("")))
       user = this.userService.selectById(CommUtil.null2Long(uid));
     else {
       user = SecurityUserHolder.getCurrentUser();
     }
     mv.addObject("owner", user);
     Map map = new HashMap();
     map.put("uid", user.getId());
     List visits = this.visitService.selectList(null);//TODO
       
     //  "select obj from Visit obj where obj.homepage.owner.id=:uid order by addTime desc", 
     //  map, -1, 10);
     mv.addObject("visits", visits);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="删除访客ajax", value="/buyer/homepage_visit_dele.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage_visit_dele.htm"})
   public void homepage_visit_dele(HttpServletRequest request, HttpServletResponse response, String visit_id)
   {
     boolean flag = false;
     Map params = new HashMap();
     params.put("custom_id", CommUtil.null2Long(visit_id));
     params.put("uid", SecurityUserHolder.getCurrentUser().getId());
     Visit sVisit=new Visit();
     sVisit.setUser_id(CommUtil.null2Long(visit_id));
     List customer = this.visitService.selectList(sVisit);//TODO
//       .query(
//       "select obj from Visit obj where obj.user.id=:custom_id and obj.homepage.owner.id=:uid", 
//       params, -1, -1);
     if (customer.size() > 0) {
       flag = this.visitService.deleteById(((Visit)customer.get(0)).getId());
     }
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(flag);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="查询已经购买宝贝和已经收藏宝贝", value="/buyer/homepage_query_goods.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage_query_goods.htm"})
   public ModelAndView homepage_query_goods(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/homepage_query_goods.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     int fcount = 0;
     int ocount = 0;
     Map map = new HashMap();
     map.put("uid", SecurityUserHolder.getCurrentUser().getId());
     map.put("type", Integer.valueOf(0));
     Favorite sFavorite=new Favorite();
     sFavorite.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     sFavorite.setType(Integer.valueOf(0));
     List favorites = this.favoriteService.selectPage(new Page<Favorite>(0, 7), sFavorite, "addTime desc").getRecords();
//       .query(
//       "select obj from Favorite obj where obj.user.id=:uid and obj.type=:type order by addTime desc", 
//       map, fcount, 7);
     
     List Allfavorites = this.favoriteService.selectList(sFavorite);
      
     mv.addObject("favorites", favorites);
     map.clear();
     map.put("uid", SecurityUserHolder.getCurrentUser().getId());
     map.put("order_status", Integer.valueOf(50));
     OrderForm sOrderForm=new OrderForm();
     sOrderForm.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     sOrderForm.setOrder_status(Integer.valueOf(50));
     List orders = this.orderFormService.selectPage(new Page<OrderForm>(ocount, 7), sOrderForm, "finishTime desc").getRecords();
//       .query(
//       "select obj from OrderForm obj where obj.user.id=:uid and obj.order_status=:order_status order by finishTime desc", 
//       map, ocount, 7);
    
     
     OrderForm sOrder=new OrderForm();
     sOrder.setUser_id(SecurityUserHolder.getCurrentUser().getId());
sOrder.setOrder_status(Integer.valueOf(50));
     List Allorders = this.orderFormService.selectList(sOrder, "finishTime desc");
      // "select obj from OrderForm obj where obj.user.id=:uid and obj.order_status=:order_status order by finishTime desc", 
     mv.addObject("favorite_Allsize", Integer.valueOf(Allfavorites.size()));
     mv.addObject("order_Allsize", Integer.valueOf(Allorders.size()));
     mv.addObject("orders", orders);
     mv.addObject("fcurrentCount", Integer.valueOf(fcount));
     mv.addObject("ocurrentCount", Integer.valueOf(ocount));
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="查询收藏宝贝ajax分页", value="/buyer/homepage_query_goods_favorite_ajax.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage_query_goods_favorite_ajax.htm"})
   public ModelAndView homepage_query_goods_favorite_ajax(HttpServletRequest request, HttpServletResponse response, String fcurrentCount)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/homepage_query_goods_favorite_ajax.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     int fcount = 0;
     if ((fcurrentCount != null) && (!fcurrentCount.equals(""))) {
       fcount = CommUtil.null2Int(fcurrentCount);
     }
     
     Favorite sFavorite=new Favorite();
     sFavorite.setType(Integer.valueOf(0));
     sFavorite.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     List favorites = this.favoriteService.selectPage(new Page<Favorite>(fcount, 7), sFavorite, "addTime desc").getRecords();
//       .query(
//       "select obj from Favorite obj where obj.user.id=:uid and obj.type=:type order by addTime desc", 
//       map, fcount, 7);
     mv.addObject("favorites", favorites);
     mv.addObject("fcurrentCount", Integer.valueOf(fcount));
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="查询已经购买宝贝ajax分页", value="/buyer/homepage_query_goods_order_ajax.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage_query_goods_order_ajax.htm"})
   public ModelAndView homepage_query_goods_order_ajax(HttpServletRequest request, HttpServletResponse response, String ocurrentCount)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/homepage_query_goods_order_ajax.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     int ocount = 0;
     if ((ocurrentCount != null) && (!ocurrentCount.equals(""))) {
       ocount = CommUtil.null2Int(ocurrentCount);
     }
     Map map = new HashMap();
     map.put("uid", SecurityUserHolder.getCurrentUser().getId());
     map.put("order_status", Integer.valueOf(50));
     OrderForm sOrderForm=new OrderForm();
     sOrderForm.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     sOrderForm.setOrder_status(Integer.valueOf(50));
     List orders = this.orderFormService.selectPage(new Page<OrderForm>(ocount, 7), sOrderForm, "finishTime desc").getRecords();
   //    "select obj from OrderForm obj where obj.user.id=:uid and obj.order_status=:order_status order by finishTime desc", 
     mv.addObject("orders", orders);
     mv.addObject("ocurrentCount", Integer.valueOf(ocount));
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="查询已经收藏店铺", value="/buyer/homepage_query_stores.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage_query_stores.htm"})
   public ModelAndView homepage_query_stores(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/homepage_query_stores.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     int currentCount = 0;
    
     Favorite sFavorite=new Favorite();
     sFavorite.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     sFavorite.setType(Integer.valueOf(1));
     List favorites = this.favoriteService.selectPage(new Page<Favorite>(currentCount, 7), sFavorite, "addTime desc").getRecords();
//       .query(
//       "select obj from Favorite obj where obj.user.id=:uid and obj.type=:type order by addTime desc", 
//       map, currentCount, 7);
    
     List Allfavorites = this.favoriteService.selectList(sFavorite,"addTime desc");
//       .query(
//       "select obj from Favorite obj where obj.user.id=:uid and obj.type=:type order by addTime desc", 
//       map, -1, -1);
     mv.addObject("favorites", favorites);
     mv.addObject("favorite_Allsize", Integer.valueOf(Allfavorites.size()));
     mv.addObject("currentCount", Integer.valueOf(currentCount));
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="查询已关注店铺ajax分页", value="/buyer/homepage_query_stores_ajax.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage_query_stores_ajax.htm"})
   public ModelAndView homepage_query_stores_ajax(HttpServletRequest request, HttpServletResponse response, String currentCount)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/homepage_query_stores_ajax.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     int count = 0;
     if ((currentCount != null) && (!currentCount.equals(""))) {
       count = CommUtil.null2Int(currentCount);
     }
     Favorite sFavorite=new Favorite();
     sFavorite.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     sFavorite.setType(Integer.valueOf(1));
     List favorites = this.favoriteService.selectPage(new Page<Favorite>(count, 7), sFavorite, "addTime desc").getRecords();
//       "select obj from Favorite obj where obj.user.id=:uid and obj.type=:type order by addTime desc", 
//       map, count, 7);
     mv.addObject("favorites", favorites);
     mv.addObject("currentCount", Integer.valueOf(count));
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="个人主页添加关注", value="/buyer/homepage_goods_url_add.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/homepage_goods_url_add.htm"})
   public void homepage_goods_url_add(HttpServletRequest request, HttpServletResponse response, String url)
   {
     boolean flag = true;
     Goods goods = null;
     String str = null;
     String address = CommUtil.getURL(request) + "/goods";
     String[] urls = url.split("_");
     if (urls.length == 2) {
       if (!address.equals(urls[0])) {
         flag = false;
       }
       String[] ids = urls[1].split("\\.");
       if (ids.length == 2) {
         if (!ids[1].equals("htm")) {
           flag = false;
         }
         if (flag) {
           goods = this.goodsService.selectById(
             CommUtil.null2Long(ids[0]));
         }
       }
     }
     if (goods != null) {
       String img_url = CommUtil.getURL(request) + "/" + 
         goods.getGoods_main_photo().getPath() + "/" + 
         goods.getGoods_main_photo().getName() + "_small" + "." + 
         goods.getGoods_main_photo().getExt();
       str = img_url + "," + goods.getId();
     }
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(str);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 }


 
 
 