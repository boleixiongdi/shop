 package com.rt.shop.view.admin.sellers.action;
 
 import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.CombinLog;
import com.rt.shop.entity.GoldLog;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.User;
import com.rt.shop.entity.query.CombinLogQueryObject;
import com.rt.shop.entity.query.GoodsQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.ICombinLogService;
import com.rt.shop.service.IGoldLogService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class CombinSellerAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IGoldLogService goldLogService;
 
   @Autowired
   private IStoreService storeService;
 
   @Autowired
   private ICombinLogService combinLogService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @SecurityMapping(display = false, rsequence = 0, title="组合销售", value="/seller/combin.htm*", rtype="seller", rname="组合销售", rcode="combin_seller", rgroup="促销管理")
   @RequestMapping({"/seller/combin.htm"})
   public ModelAndView combin(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/combin.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     GoodsQueryObject qo = new GoodsQueryObject(currentPage, mv, orderBy, 
       orderType);
     qo.addQuery("obj.goods_store.id", 
       new SysMap("store_id", user
       .getStore_id()), "=");
     qo.addQuery("obj.combin_status", new SysMap("combin_status", Integer.valueOf(0)), ">");
     qo.addQuery("obj.goods_status", new SysMap("goods_status", Integer.valueOf(0)), "=");
     Page pList = this.goodsService.selectPage(new Page<Goods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="组合销售购买日志", value="/seller/combin_log.htm*", rtype="seller", rname="组合销售", rcode="combin_seller", rgroup="促销管理")
   @RequestMapping({"/seller/combin_log.htm"})
   public ModelAndView combin_log(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/combin_log.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     CombinLogQueryObject qo = new CombinLogQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.store.id", 
       new SysMap("store_id", user.getStore_id()), "=");
     Page pList = this.combinLogService.selectPage(new Page<CombinLog>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="申请组合销售", value="/seller/combin_apply.htm*", rtype="seller", rname="组合销售", rcode="combin_seller", rgroup="促销管理")
   @RequestMapping({"/seller/combin_apply.htm"})
   public ModelAndView combin_apply(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/combin_apply.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     Store store = storeService.selectById(user.getStore_id());
     if (store.getCombin_end_time() == null) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "您尚未购买'组合销售'套餐");
       mv.addObject("url", CommUtil.getURL(request) + 
         "/seller/combin_buy.htm");
       return mv;
     }
     if (store.getCombin_end_time().before(new Date())) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "您的'组合销售'套餐已经过期");
       mv.addObject("url", CommUtil.getURL(request) + 
         "/seller/combin_buy.htm");
       return mv;
     }
     Map map = CommUtil.cal_time_space(new Date(), store
       .getCombin_begin_time());
     int minDate = CommUtil.null2Int(map.get("day"));
     minDate = minDate > 0 ? minDate : 0;
     map.clear();
     map = CommUtil.cal_time_space(new Date(), store.getCombin_end_time());
     int maxDate = CommUtil.null2Int(map.get("day")) + 1;
     maxDate = maxDate > 0 ? maxDate : 0;
     mv.addObject("minDate", Integer.valueOf(minDate));
     mv.addObject("maxDate", Integer.valueOf(maxDate));
     String combin_session = CommUtil.randomString(32);
     mv.addObject("combin_session", combin_session);
     request.getSession(false)
       .setAttribute("combin_session", combin_session);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="销售组合编辑", value="/seller/combin_edit.htm*", rtype="seller", rname="组合销售", rcode="combin_seller", rgroup="促销管理")
   @RequestMapping({"/seller/combin_edit.htm"})
   public ModelAndView combin_edit(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/combin_apply.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     Store store = storeService.selectById(user.getStore_id());
     if (store.getCombin_end_time() == null) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "您尚未购买'组合销售'套餐");
       mv.addObject("url", CommUtil.getURL(request) + 
         "/seller/combin_buy.htm");
       return mv;
     }
     if (store.getCombin_end_time().before(new Date())) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "您的'组合销售'套餐已经过期");
       mv.addObject("url", CommUtil.getURL(request) + 
         "/seller/combin_buy.htm");
       return mv;
     }
     Map map = CommUtil.cal_time_space(new Date(), store
       .getCombin_begin_time());
     int minDate = CommUtil.null2Int(map.get("day"));
     minDate = minDate > 0 ? minDate : 0;
     map.clear();
     map = CommUtil.cal_time_space(new Date(), store.getCombin_end_time());
     int maxDate = CommUtil.null2Int(map.get("day")) + 1;
     maxDate = maxDate > 0 ? maxDate : 0;
     mv.addObject("minDate", Integer.valueOf(minDate));
     mv.addObject("maxDate", Integer.valueOf(maxDate));
     String combin_session = CommUtil.randomString(32);
     mv.addObject("combin_session", combin_session);
     request.getSession(false)
       .setAttribute("combin_session", combin_session);
     Goods obj = this.goodsService.selectById(CommUtil.null2Long(id));
     mv.addObject("obj", obj);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="组合销售保存", value="/seller/combin_save.htm*", rtype="seller", rname="组合销售", rcode="combin_seller", rgroup="促销管理")
   @RequestMapping({"/seller/combin_save.htm"})
   public ModelAndView combin_save(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String combin_session, String combin_begin_time, String combin_end_time, String combin_main_goods_id, String combin_goods_ids, String combin_price, String id)
   {
     ModelAndView mv = new JModelAndView("success.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
     String combin_session1 = CommUtil.null2String(request.getSession(false)
       .getAttribute("combin_session"));
     if (combin_session1.equals("")) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "组合销售保存失败");
       mv
         .addObject("url", CommUtil.getURL(request) + 
         "/seller/combin.htm");
       return mv;
     }
     if (combin_session1.equals(combin_session)) {
       request.getSession(false).removeAttribute("combin_session");
       Goods goods = this.goodsService.selectById(
         CommUtil.null2Long(combin_main_goods_id));
       goods.setCombin_begin_time(CommUtil.formatDate(combin_begin_time));
       goods.setCombin_end_time(CommUtil.formatDate(combin_end_time));
       goods.setCombin_status(1);
       goods.setCombin_price(BigDecimal.valueOf(
         CommUtil.null2Float(combin_price)));
       for (String goods_id : combin_goods_ids.split(",")) {
         Goods combin_goods = this.goodsService.selectById(
           CommUtil.null2Long(goods_id));
         goods.getCombin_goods().add(combin_goods);
       }
       this.goodsService.updateSelectiveById(goods);
       if ((id != null) && (!id.equals("")) && 
         (!goods.getId().equals(CommUtil.null2Long(id)))) {
         goods = this.goodsService
           .selectById(CommUtil.null2Long(id));
         goods.setCombin_begin_time(null);
         goods.setCombin_end_time(null);
         goods.setCombin_price(null);
         goods.setCombin_status(0);
         goods.getCombin_goods().clear();
         this.goodsService.updateSelectiveById(goods);
       }
     }
 
     mv.addObject("op_title", "组合销售保存成功");
     mv.addObject("url", CommUtil.getURL(request) + 
       "/seller/combin.htm?currentPage=" + currentPage);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="组合销售删除", value="/seller/combin_del.htm*", rtype="seller", rname="组合销售", rcode="combin_seller", rgroup="促销管理")
   @RequestMapping({"/seller/combin_del.htm"})
   public String delivery_del(HttpServletRequest request, HttpServletResponse response, String currentPage, String mulitId) {
     for (String id : mulitId.split(",")) {
       if (!CommUtil.null2String(id).equals("")) {
         Goods goods = this.goodsService.selectById(
           CommUtil.null2Long(id));
         User user = this.userService.selectById(
           SecurityUserHolder.getCurrentUser().getId());
         if (user.getStore_id() == goods.getGoods_store().getId()) {
           goods.setCombin_begin_time(null);
           goods.setCombin_end_time(null);
           goods.setCombin_price(null);
           goods.setCombin_status(0);
           goods.getCombin_goods().clear();
           this.goodsService.updateSelectiveById(goods);
         }
       }
     }
     return "redirect:combin.htm?currentPage=" + currentPage;
   }
   @SecurityMapping(display = false, rsequence = 0, title="组合销售套餐购买", value="/seller/combin_buy.htm*", rtype="seller", rname="组合销售", rcode="combin_seller", rgroup="促销管理")
   @RequestMapping({"/seller/combin_buy.htm"})
   public ModelAndView combin_buy(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/combin_buy.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     mv.addObject("user", user);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="组合销售套餐购买保存", value="/seller/combin_buy_save.htm*", rtype="seller", rname="组合销售", rcode="combin_seller", rgroup="促销管理")
   @RequestMapping({"/seller/combin_buy_save.htm"})
   public String combin_buy_save(HttpServletRequest request, HttpServletResponse response, String count) {
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     int gold = user.getGold();
     int combin_gold = CommUtil.null2Int(count) * 
       this.configService.getSysConfig().getCombin_amount();
     if (gold > combin_gold)
     {
       user.setGold(gold - combin_gold);
       this.userService.updateSelectiveById(user);
 
       GoldLog log = new GoldLog();
       log.setAddTime(new Date());
       log.setGl_content("购买组合销售套餐");
       log.setGl_count(combin_gold);
       log.setGl_user(user);
       log.setGl_type(-1);
       this.goldLogService.insertSelective(log);
 
       Store store = storeService.selectById(user.getStore_id());
       if (store.getCombin_begin_time() == null) {
         store.setCombin_begin_time(new Date());
       }
       Calendar cal = Calendar.getInstance();
       if (store.getCombin_end_time() != null) {
         cal.setTime(store.getCombin_end_time());
       }
       cal.add(2, CommUtil.null2Int(count));
       store.setCombin_end_time(cal.getTime());
       this.storeService.updateSelectiveById(store);
 
       CombinLog c_log = new CombinLog();
       c_log.setAddTime(new Date());
       c_log.setBegin_time(new Date());
       c_log.setEnd_time(cal.getTime());
       c_log.setGold(combin_gold);
       c_log.setStore(store);
       this.combinLogService.insertSelective(c_log);
       return "redirect:combin_buy_success.htm";
     }
     return "redirect:combin_buy_error.htm";
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="组合销售套餐购买成功", value="/seller/combin_buy_success.htm*", rtype="seller", rname="组合销售", rcode="combin_seller", rgroup="促销管理")
   @RequestMapping({"/seller/combin_buy_success.htm"})
   public ModelAndView combin_buy_success(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("success.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
     mv.addObject("op_title", "组合销售套餐购买成功");
     mv.addObject("url", CommUtil.getURL(request) + "/seller/combin.htm");
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="组合销售套餐购买失败", value="/seller/combin_buy_error.htm*", rtype="seller", rname="组合销售", rcode="combin_seller", rgroup="促销管理")
   @RequestMapping({"/seller/combin_buy_error.htm"})
   public ModelAndView combin_buy_error(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("error.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
     mv.addObject("op_title", "金币不足不能购买套餐");
     mv.addObject("url", CommUtil.getURL(request) + "/seller/combin.htm");
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="加载商品", value="/seller/combin_goods.htm*", rtype="seller", rname="组合销售", rcode="combin_seller", rgroup="促销管理")
   @RequestMapping({"/seller/combin_goods.htm"})
   public ModelAndView combin_goods(HttpServletRequest request, HttpServletResponse response, String goods_name, String currentPage, String target_id, String goods_type) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/combin_goods.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (target_id.equals("main_goods_list")) {
       goods_type = "main";
     }
     if (CommUtil.null2String(goods_type).equals("main")) {
       mv = new JModelAndView(
         "user/default/usercenter/combin_main_goods.html", 
         this.configService.getSysConfig(), this.userConfigService
         .getUserConfig(), 0, request, response);
     }
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     Store store = storeService.selectById(user.getStore_id());
     GoodsQueryObject qo = new GoodsQueryObject();
     qo.setCurrentPage(Integer.valueOf(CommUtil.null2Int(currentPage)));
     if (!CommUtil.null2String(goods_name).equals("")) {
       qo.addQuery("obj.goods_name", 
         new SysMap("goods_name", "%" + 
         CommUtil.null2String(goods_name) + "%"), "like");
     }
     qo.addQuery("obj.combin_status", new SysMap("combin_status", Integer.valueOf(0)), "=");
     qo.addQuery("obj.goods_store.id", 
       new SysMap("store_id", store.getId()), "=");
     qo.addQuery("obj.goods_status", new SysMap("goods_status", Integer.valueOf(0)), "=");
     qo.addQuery("obj.group_buy", new SysMap("group_buy", Integer.valueOf(0)), "=");
     qo.addQuery("obj.activity_status", new SysMap("activity_status", Integer.valueOf(0)), 
       "=");
     qo.addQuery("obj.delivery_status", new SysMap("delivery_status", Integer.valueOf(0)), 
       "=");
     qo.setPageSize(Integer.valueOf(15));
     Page pList = this.goodsService.selectPage(new Page<Goods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
    
     CommWebUtil.saveIPageList2ModelAndView(CommUtil.getURL(request) + 
       "/seller/combin_goods.htm", "", "&goods_name=" + goods_name, 
       pList, mv);
     mv.addObject("target_id", target_id);
     return mv;
   }
 }


 
 
 