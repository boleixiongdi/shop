 package com.rt.shop.view.admin.buyer.actions;
 
 import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.easyjf.web.WebForm;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Dynamic;
import com.rt.shop.entity.Favorite;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.HomepageGoodsclass;
import com.rt.shop.entity.Message;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.UserAttention;
import com.rt.shop.entity.UserFriend;
import com.rt.shop.entity.query.DynamicQueryObject;
import com.rt.shop.entity.query.FavoriteQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IDynamicService;
import com.rt.shop.service.IFavoriteService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IHomepageGoodsclassService;
import com.rt.shop.service.IMessageService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserAttentionService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserFriendService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
import com.rt.shop.view.web.tools.OrderViewTools;
import com.rt.shop.view.web.tools.StoreViewTools;
 
 @Controller
 public class BaseBuyerAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IMessageService messageService;
 
   @Autowired
   private StoreViewTools storeViewTools;
 
   @Autowired
   private OrderViewTools orderViewTools;
 
   @Autowired
   private IDynamicService dynamicService;
 
   @Autowired
   private IUserFriendService snsFriendService;
 
   @Autowired
   private IFavoriteService favService;
 
   @Autowired
   private IStoreService storeService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IUserAttentionService SnsAttentionService;
 
   @Autowired
   private IHomepageGoodsclassService HomeGoodsClassService;
 
   @SecurityMapping(display = false, rsequence = 0, title="买家中心", value="/buyer/index.htm*", rtype="buyer", rname="买家中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/index.htm"})
   public ModelAndView index(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String type)
   {
     ModelAndView mv = new JModelAndView("user/default/usercenter/buyer_index.html", this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     String shopping_view_type = CommUtil.isMobileDeviceValue(request.getHeader("user-agent"));
	 if( (shopping_view_type != null) && (!shopping_view_type.equals( "" )) && (shopping_view_type.equals( "wap" )) ) {
		 mv = new JModelAndView("wap/buyer_index.html", this.configService.getSysConfig(), 
			 this.userConfigService.getUserConfig(), 1, request, response);
	 }
     List msgs = new ArrayList();
     if (SecurityUserHolder.getCurrentUser() != null) {
       
       msgs = this.messageService.selectList("where status=0 and touser_id='"+SecurityUserHolder.getCurrentUser().getId()+"' and  parent_id is null",null);
      //   "select obj from Message obj where obj.status=:status and obj.toUser.id=:user_id and obj.parent.id is null", params, -1, -1);
     mv.addObject("msgs", msgs);
     mv.addObject("storeViewTools", this.storeViewTools);
     mv.addObject("orderViewTools", this.orderViewTools);
     }
 
     DynamicQueryObject qo = new DynamicQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.display", new SysMap("display", Boolean.valueOf(true)), "=");
     if ((type == null) || (type.equals(""))) {
       type = "2";
     }
     if (type.equals("1")) {
       qo.addQuery("obj.user.id", 
         new SysMap("uid", 
         SecurityUserHolder.getCurrentUser().getId()), "=");
     }
     if (type.equals("2")) {
       Map map = new HashMap();
       map.put("f_uid", SecurityUserHolder.getCurrentUser().getId());
       UserFriend sUserFriend=new UserFriend();
       sUserFriend.setFromUser_id(SecurityUserHolder.getCurrentUser().getId());
       List myFriends = this.snsFriendService.selectList(sUserFriend);
       
       //  "select obj from SnsFriend obj where obj.fromUser.id=:f_uid", 
        
       Set ids = getSnsFriendToUserIds(myFriends);
       Map paras = new HashMap();
       paras.put("ids", null);
       if (myFriends.size() > 0) {
         paras.put("ids", ids);
       }
       qo.addQuery("obj.user.id in (:ids)", paras);
     }
     if (type.equals("3")) {
       
       UserAttention sUserAttention=new UserAttention();
       sUserAttention.setFromUser_id(SecurityUserHolder.getCurrentUser().getId());
       List SnsAttentions = this.SnsAttentionService.selectList(sUserAttention);
        // "select obj from SnsAttention obj where obj.fromUser.id=:uid ", 
       Set ids = getSnsAttentionToUserIds(SnsAttentions);
       Map paras = new HashMap();
       paras.clear();
       paras.put("ids", ids);
       if ((ids != null) && (ids.size() > 0)) {
         qo.addQuery("obj.user.id in (:ids)", paras);
       }
     }
     if (type.equals("4")) {
       qo.addQuery("obj.user.id", 
         new SysMap("uid", 
         SecurityUserHolder.getCurrentUser().getId()), "=");
       qo.addQuery("obj.store.id is not null", null);
     }
     qo.addQuery("obj.locked", new SysMap("locked", Boolean.valueOf(false)), "=");
     qo.addQuery("obj.dissParent.id is null", null);
     qo.setOrderBy("addTime");
     qo.setOrderType("desc");
     qo.setPageSize(Integer.valueOf(10));
     Page pList = this.dynamicService.selectPage(new Page<Dynamic>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
    
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     List list = new ArrayList();
     for (int i = 1; i <= 120; i++) {
       list.add(Integer.valueOf(i));
     }
     mv.addObject("type", type);
     mv.addObject("emoticons", list);
     return mv;
   }
   
 
   private Set<Long> getSnsAttentionToUserIds(List<UserAttention> SnsAttentions) {
     Set ids = new HashSet();
     for (UserAttention attention : SnsAttentions) {
       ids.add(attention.getToUser_id());
     }
     return ids;
   }
   @SecurityMapping(display = false, rsequence = 0, title="买家中心导航", value="/buyer/nav.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/nav.htm"})
   public ModelAndView nav(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/buyer_nav.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     String op = CommUtil.null2String(request.getAttribute("op"));
     mv.addObject("op", op);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="买家中心导航", value="/buyer/head.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/head.htm"})
   public ModelAndView head(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/buyer_head.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     return mv;
   }
 
   @RequestMapping({"/buyer/authority.htm"})
   public ModelAndView authority(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView("error.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
     mv.addObject("op_title", "您没有该项操作权限");
     mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
     return mv;
   }
 
   private Set<Long> getSnsFriendToUserIds(List<UserFriend> myfriends) {
     Set ids = new HashSet();
     if (myfriends.size() > 0) {
       for (UserFriend friend : myfriends) {
         ids.add(friend.getToUser_id());
       }
     }
     return ids;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="动态发布保存", value="/buyer/dynamic_save.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/dynamic_save.htm"})
   public ModelAndView dynamic_save(HttpServletRequest request, HttpServletResponse response, String content, String currentPage, String orderBy, String orderType, String store_id, String goods_id)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/dynamic_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     WebForm wf = new WebForm();
     Dynamic dynamic = (Dynamic)wf.toPo(Dynamic.class);
     dynamic.setAddTime(new Date());
     dynamic.setUser(SecurityUserHolder.getCurrentUser());
     dynamic.setContent(content);
     dynamic.setDisplay(true);
     if ((store_id != null) && (!store_id.equals(""))) {
       Store store = this.storeService.selectById(
         CommUtil.null2Long(store_id));
       dynamic.setStore(store);
     }
     if ((goods_id != null) && (!goods_id.equals(""))) {
       Goods goods = this.goodsService.selectById(
         CommUtil.null2Long(goods_id));
       dynamic.setGoods(goods);
 
      
       HomepageGoodsclass hf=new HomepageGoodsclass();
       hf.setUser_id(SecurityUserHolder.getCurrentUser().getId());
       hf.setGc_id(goods.getGc_id());
       List hgcs = this.HomeGoodsClassService.selectList(hf);
        // "select obj from HomePageGoodsClass obj where obj.user.id=:uid and obj.gc.id=:gc_id", 
       if (hgcs.size() == 0) {
       
         HomepageGoodsclass hpgc = new HomepageGoodsclass();
         hpgc.setAddTime(new Date());
         hpgc.setUser(SecurityUserHolder.getCurrentUser());
         hpgc.setGc(goods.getGc());
         this.HomeGoodsClassService.insertSelective(hpgc);
       }
     }
     this.dynamicService.insertSelective(dynamic);
 
     DynamicQueryObject qo = new DynamicQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.dissParent.id is null", null);
     qo.setOrderBy("addTime");
     qo.setOrderType("desc");
     qo.setPageSize(Integer.valueOf(10));
     Page pList = this.dynamicService.selectPage(new Page<Dynamic>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="删除动态", value="/buyer/dynamic_del.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/dynamic_del.htm"})
   public ModelAndView dynamic_ajax_del(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String orderBy, String orderType)
   {
     if (!id.equals("")) {
       Dynamic dynamic = this.dynamicService
         .selectById(Long.valueOf(Long.parseLong(id)));
       this.dynamicService.deleteById(Long.valueOf(Long.parseLong(id)));
     }
 
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/dynamic_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     DynamicQueryObject qo = new DynamicQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.dissParent.id is null", null);
     qo.setOrderBy("addTime");
     qo.setOrderType("desc");
     qo.setPageSize(Integer.valueOf(10));
     Page pList = this.dynamicService.selectPage(new Page<Dynamic>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="ajax回复保存方法", value="/buyer/dynamic_ajax_reply.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/dynamic_ajax_reply.htm"})
   public ModelAndView dynamic_ajax_reply(HttpServletRequest request, HttpServletResponse response, String parent_id, String fieldName, String reply_content)
     throws ClassNotFoundException
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/dynamic_childs_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     WebForm wf = new WebForm();
     Dynamic dynamic = (Dynamic)wf.toPo(Dynamic.class);
     Dynamic parent = null;
     if ((parent_id != null) && (!parent_id.equals(""))) {
       parent = this.dynamicService.selectById(Long.valueOf(Long.parseLong(parent_id)));
       dynamic.setDissParent(parent);
       this.dynamicService.updateSelectiveById(parent);
       dynamic.setDissParent(parent);
     }
     dynamic.setAddTime(new Date());
     dynamic.setUser(SecurityUserHolder.getCurrentUser());
     dynamic.setContent(reply_content);
     this.dynamicService.insertSelective(dynamic);
     mv.addObject("obj", parent);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="ajax赞动态方法", value="/buyer/dynamic_ajax_praise.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/dynamic_ajax_praise.htm"})
   public void dynamic_ajax_praise(HttpServletRequest request, HttpServletResponse response, String dynamic_id)
     throws ClassNotFoundException
   {
     Dynamic dynamic = this.dynamicService.selectById(
       Long.valueOf(Long.parseLong(dynamic_id)));
     dynamic.setPraiseNum(dynamic.getPraiseNum() + 1);
     this.dynamicService.updateSelectiveById(dynamic);
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(dynamic.getPraiseNum());
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="ajax转发动态保存方法", value="/buyer/dynamic_ajax_turn.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/dynamic_ajax_turn.htm"})
   public ModelAndView dynamic_ajax_turn(HttpServletRequest request, HttpServletResponse response, String dynamic_id, String content, String currentPage, String orderType, String orderBy)
     throws ClassNotFoundException
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/dynamic_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Dynamic dynamic = this.dynamicService.selectById(
       Long.valueOf(Long.parseLong(dynamic_id)));
     dynamic.setTurnNum(dynamic.getTurnNum() + 1);
     this.dynamicService.updateSelectiveById(dynamic);
     Dynamic turn = new Dynamic();
     turn.setAddTime(new Date());
     turn.setContent(content + "//转自" + dynamic.getUser().getUserName() + 
       ":" + dynamic.getContent());
     turn.setUser(SecurityUserHolder.getCurrentUser());
     this.dynamicService.insertSelective(turn);
 
     DynamicQueryObject qo = new DynamicQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.dissParent.id is null", null);
     qo.setOrderBy("addTime");
     qo.setOrderType("desc");
     qo.setPageSize(Integer.valueOf(10));
     Page pList = this.dynamicService.selectPage(new Page<Dynamic>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="删除动态下方自己发布的评论", value="/buyer/dynamic_reply_del.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/dynamic_reply_del.htm"})
   public ModelAndView dynamic_reply_del(HttpServletRequest request, HttpServletResponse response, String id, String parent_id)
   {
     if (!id.equals("")) {
       Dynamic dynamic = this.dynamicService
         .selectById(Long.valueOf(Long.parseLong(id)));
       this.dynamicService.deleteById(Long.valueOf(Long.parseLong(id)));
     }
 
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/dynamic_childs_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if ((parent_id != null) && (!parent_id.equals(""))) {
       Dynamic obj = this.dynamicService.selectById(
         CommUtil.null2Long(parent_id));
       mv.addObject("obj", obj);
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="用户分享收藏店铺列表", value="/buyer/fav_store_list.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/fav_store_list.htm"})
   public ModelAndView fav_store_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/fav_store_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     FavoriteQueryObject qo = new FavoriteQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.user.id", 
       new SysMap("uid", 
       SecurityUserHolder.getCurrentUser().getId()), "=");
     qo.addQuery("obj.type", new SysMap("type", Integer.valueOf(1)), "=");
     qo.setPageSize(Integer.valueOf(4));
     Page pList = this.favService.selectPage(new Page<Favorite>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     mv.addObject("objs", pList.getRecords());
     String Ajax_url = CommUtil.getURL(request) + 
       "/buyer/fav_store_list_ajax.htm";
     mv.addObject("gotoPageAjaxHTML", CommUtil.showPageAjaxHtml(Ajax_url, 
       "", pList.getCurrent(), pList.getPages()));
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="用户分享收藏店铺ajax列表", value="/buyer/fav_store_list_ajax.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/fav_store_list_ajax.htm"})
   public ModelAndView fav_store_list_ajax(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/fav_store_list_ajax.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     FavoriteQueryObject qo = new FavoriteQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.user.id", 
       new SysMap("uid", 
       SecurityUserHolder.getCurrentUser().getId()), "=");
     qo.addQuery("obj.type", new SysMap("type", Integer.valueOf(1)), "=");
     qo.setPageSize(Integer.valueOf(4));
     
     Page pList = this.favService.selectPage(new Page<Favorite>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     mv.addObject("objs", pList.getRecords());
     String Ajax_url = CommUtil.getURL(request) + 
       "/buyer/fav_store_list_ajax.htm";
     mv.addObject("gotoPageAjaxHTML", CommUtil.showPageAjaxHtml(Ajax_url, 
       "", pList.getCurrent(), pList.getPages()));
     return mv;
   }
   
 
   @SecurityMapping(display = false, rsequence = 0, title="用户分享收藏商品列表", value="/buyer/fav_goods_list.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/fav_goods_list.htm"})
   public ModelAndView fav_goods_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/fav_goods_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     FavoriteQueryObject qo = new FavoriteQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.user.id", 
       new SysMap("uid", 
       SecurityUserHolder.getCurrentUser().getId()), "=");
     qo.addQuery("obj.type", new SysMap("type", Integer.valueOf(0)), "=");
     qo.setPageSize(Integer.valueOf(4));
     Page pList = this.favService.selectPage(new Page<Favorite>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     mv.addObject("objs", pList.getRecords());
     String Ajax_url = CommUtil.getURL(request) + 
       "/buyer/fav_goods_list_ajax.htm";
     mv.addObject("gotoPageAjaxHTML", CommUtil.showPageAjaxHtml(Ajax_url, 
       "", pList.getCurrent(), pList.getPages()));
     return mv;
   
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="用户分享收藏商品ajax列表", value="/buyer/fav_goods_list_ajax.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/fav_goods_list_ajax.htm"})
   public ModelAndView fav_goods_list_ajax(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/fav_goods_list_ajax.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     FavoriteQueryObject qo = new FavoriteQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.user.id", 
       new SysMap("uid", 
       SecurityUserHolder.getCurrentUser().getId()), "=");
     qo.addQuery("obj.type", new SysMap("type", Integer.valueOf(0)), "=");
     qo.setPageSize(Integer.valueOf(4));
     Page pList = this.favService.selectPage(new Page<Favorite>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     mv.addObject("objs", pList.getRecords());
     String Ajax_url = CommUtil.getURL(request) + 
       "/buyer/fav_goods_list_ajax.htm";
     mv.addObject("gotoPageAjaxHTML", CommUtil.showPageAjaxHtml(Ajax_url, 
       "", pList.getCurrent(), pList.getPages()));
     return mv;
   }
 }


 
 
 