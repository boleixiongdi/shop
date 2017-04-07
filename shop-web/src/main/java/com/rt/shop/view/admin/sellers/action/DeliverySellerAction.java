 package com.rt.shop.view.admin.sellers.action;
 
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
import com.rt.shop.entity.DeliveryGoods;
import com.rt.shop.entity.DeliveryLog;
import com.rt.shop.entity.GoldLog;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.User;
import com.rt.shop.entity.query.DeliveryGoodsQueryObject;
import com.rt.shop.entity.query.DeliveryLogQueryObject;
import com.rt.shop.entity.query.GoodsQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IDeliveryGoodsService;
import com.rt.shop.service.IDeliveryLogService;
import com.rt.shop.service.IGoldLogService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class DeliverySellerAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IDeliveryGoodsService deliveryGoodsService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IStoreService storeService;
 
   @Autowired
   private IGoldLogService goldLogService;
 
   @Autowired
   private IDeliveryLogService deliveryLogService;
 
   @SecurityMapping(display = false, rsequence = 0, title="买就送", value="/seller/delivery.htm*", rtype="seller", rname="买就送", rcode="delivery_seller", rgroup="促销管理")
   @RequestMapping({"/seller/delivery.htm"})
   public ModelAndView delivery(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/delivery.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     DeliveryGoodsQueryObject qo = new DeliveryGoodsQueryObject(currentPage, 
       mv, orderBy, orderType);
     qo.addQuery("obj.d_goods.goods_store.id", 
       new SysMap("store_id", user
       .getStore_id()), "=");
     Page pList = this.deliveryGoodsService.selectPage(new Page<DeliveryGoods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="买就送套餐购买日志", value="/seller/delivery_log.htm*", rtype="seller", rname="买就送", rcode="delivery_seller", rgroup="促销管理")
   @RequestMapping({"/seller/delivery_log.htm"})
   public ModelAndView delivery_log(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/delivery_log.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     DeliveryLogQueryObject qo = new DeliveryLogQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.store.id", 
       new SysMap("store_id", user.getStore_id()), "=");
     Page pList = this.deliveryLogService.selectPage(new Page<DeliveryLog>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="买就送套餐购买", value="/seller/delivery_buy.htm*", rtype="seller", rname="买就送", rcode="delivery_seller", rgroup="促销管理")
   @RequestMapping({"/seller/delivery_buy.htm"})
   public ModelAndView delivery_buy(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/delivery_buy.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     mv.addObject("user", user);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="买就送套餐购买保存", value="/seller/delivery_buy_save.htm*", rtype="seller", rname="买就送", rcode="delivery_seller", rgroup="促销管理")
   @RequestMapping({"/seller/delivery_buy_save.htm"})
   public String delivery_buy_save(HttpServletRequest request, HttpServletResponse response, String count)
   {
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     int gold = user.getGold();
     int delivery_gold = CommUtil.null2Int(count) * 
       this.configService.getSysConfig().getDelivery_amount();
     if (gold > delivery_gold)
     {
       user.setGold(gold - delivery_gold);
       this.userService.updateSelectiveById(user);
 
       GoldLog log = new GoldLog();
       log.setAddTime(new Date());
       log.setGl_content("购买买就送套餐");
       log.setGl_count(delivery_gold);
       log.setGl_user(user);
       log.setGl_type(-1);
       this.goldLogService.insertSelective(log);
 
       Store store = storeService.selectById(user.getStore_id());
       if (store.getDelivery_begin_time() == null) {
         store.setDelivery_begin_time(new Date());
       }
       Calendar cal = Calendar.getInstance();
       if (store.getDelivery_end_time() != null) {
         cal.setTime(store.getDelivery_end_time());
       }
       cal.add(2, CommUtil.null2Int(count));
       store.setDelivery_end_time(cal.getTime());
       this.storeService.updateSelectiveById(store);
 
       DeliveryLog d_log = new DeliveryLog();
       d_log.setAddTime(new Date());
       d_log.setBegin_time(new Date());
       d_log.setEnd_time(cal.getTime());
       d_log.setGold(delivery_gold);
       d_log.setStore(store);
       this.deliveryLogService.insertSelective(d_log);
       return "redirect:delivery_buy_success.htm";
     }
     return "redirect:delivery_buy_error.htm";
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="买就送套餐购买成功", value="/seller/delivery_buy_success.htm*", rtype="seller", rname="买就送", rcode="delivery_seller", rgroup="促销管理")
   @RequestMapping({"/seller/delivery_buy_success.htm"})
   public ModelAndView delivery_buy_success(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("success.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
     mv.addObject("op_title", "买就送套餐购买成功");
     mv.addObject("url", CommUtil.getURL(request) + "/seller/delivery.htm");
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="买就送套餐购买失败", value="/seller/delivery_buy_error.htm*", rtype="seller", rname="买就送", rcode="delivery_seller", rgroup="促销管理")
   @RequestMapping({"/seller/delivery_buy_error.htm"})
   public ModelAndView delivery_buy_error(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("error.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
     mv.addObject("op_title", "金币不足不能购买套餐");
     mv.addObject("url", CommUtil.getURL(request) + "/seller/delivery.htm");
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="申请买就送", value="/seller/delivery_apply.htm*", rtype="seller", rname="买就送", rcode="delivery_seller", rgroup="促销管理")
   @RequestMapping({"/seller/delivery_apply.htm"})
   public ModelAndView delivery_apply(HttpServletRequest request, HttpServletResponse response, String id)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/delivery_apply.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     Store store = storeService.selectById(user.getStore_id());
     if (store.getDelivery_end_time() == null) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "您尚未购买买就送套餐");
       mv.addObject("url", CommUtil.getURL(request) + 
         "/seller/delivery_buy.htm");
       return mv;
     }
     if (store.getDelivery_end_time().before(new Date())) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "您的买就送套餐已经过期");
       mv.addObject("url", CommUtil.getURL(request) + 
         "/seller/delivery_buy.htm");
       return mv;
     }
     Map map = CommUtil.cal_time_space(new Date(), store
       .getDelivery_begin_time());
     int minDate = CommUtil.null2Int(map.get("day"));
     minDate = minDate > 0 ? minDate : 0;
     map.clear();
     map = CommUtil.cal_time_space(new Date(), store.getDelivery_end_time());
     int maxDate = CommUtil.null2Int(map.get("day")) + 1;
     maxDate = maxDate > 0 ? maxDate : 0;
     mv.addObject("minDate", Integer.valueOf(minDate));
     mv.addObject("maxDate", Integer.valueOf(maxDate));
     String delivery_session = CommUtil.randomString(32);
     mv.addObject("delivery_session", delivery_session);
     request.getSession(false).setAttribute("delivery_session", 
       delivery_session);
     if (!CommUtil.null2String(id).equals("")) {
       DeliveryGoods obj = this.deliveryGoodsService.selectById(
         CommUtil.null2Long(id));
       mv.addObject("obj", obj);
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="加载买就送商品", value="/seller/delivery_goods.htm*", rtype="seller", rname="买就送", rcode="delivery_seller", rgroup="促销管理")
   @RequestMapping({"/seller/delivery_goods.htm"})
   public ModelAndView delivery_goods(HttpServletRequest request, HttpServletResponse response, String goods_name, String currentPage, String node_id)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/delivery_goods.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
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
     qo.addQuery("obj.delivery_status", new SysMap("delivery_status", Integer.valueOf(0)), 
       "=");
     qo.addQuery("obj.goods_store.id", 
       new SysMap("store_id", store.getId()), "=");
     qo.addQuery("obj.goods_status", new SysMap("goods_status", Integer.valueOf(0)), "=");
     qo.addQuery("obj.goods_status", new SysMap("goods_status", Integer.valueOf(0)), "=");
     qo.addQuery("obj.group_buy", new SysMap("group_buy", Integer.valueOf(0)), "=");
     qo.addQuery("obj.activity_status", new SysMap("activity_status", Integer.valueOf(0)), "=");
     qo.addQuery("obj.combin_status", new SysMap("combin_status", Integer.valueOf(0)), "=");
     qo.setPageSize(Integer.valueOf(15));
     Page pList = this.goodsService.selectPage(new Page<Goods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
   
     CommWebUtil.saveIPageList2ModelAndView(CommUtil.getURL(request) + 
       "/seller/delivery_goods.htm", "", 
       "&goods_name=" + goods_name, pList, mv);
     mv.addObject("node_id", node_id);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="买就送商品保存", value="/seller/delivery_apply_save.htm*", rtype="seller", rname="买就送", rcode="delivery_seller", rgroup="促销管理")
   @RequestMapping({"/seller/delivery_apply_save.htm"})
   public ModelAndView delivery_apply_save(HttpServletRequest request, HttpServletResponse response,DeliveryGoods obj, String main_goods_id, String give_goods_id, String delivery_session)
   {
     ModelAndView mv = new JModelAndView("success.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
     String delivery_session1 = CommUtil.null2String(request.getSession(
       false).getAttribute("delivery_session"));
     if ((!delivery_session1.equals("")) && 
       (delivery_session1.equals(delivery_session))) {
       request.getSession(false).removeAttribute("delivery_session");
       obj.setAddTime(new Date());
       Goods d_goods = this.goodsService.selectById(
         CommUtil.null2Long(main_goods_id));
       obj.setD_goods(d_goods);
       Goods d_delivery_goods = this.goodsService.selectById(
         CommUtil.null2Long(give_goods_id));
       obj.setD_delivery_goods(d_delivery_goods);
       this.deliveryGoodsService.insertSelective(obj);
       d_goods.setDelivery_status(1);
       this.goodsService.updateSelectiveById(d_goods);
       mv.addObject("op_title", "买就送申请成功");
       mv.addObject("url", CommUtil.getURL(request) + 
         "/seller/delivery.htm");
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "禁止重复提交特送申请");
       mv.addObject("url", CommUtil.getURL(request) + 
         "/seller/delivery.htm");
     }
 
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="买就送删除", value="/seller/delivery_del.htm*", rtype="seller", rname="买就送", rcode="delivery_seller", rgroup="促销管理")
   @RequestMapping({"/seller/delivery_del.htm"})
   public String delivery_del(HttpServletRequest request, HttpServletResponse response, String currentPage, String mulitId) {
     for (String id : mulitId.split(",")) {
       if (!CommUtil.null2String(id).equals("")) {
         DeliveryGoods obj = this.deliveryGoodsService
           .selectById(CommUtil.null2Long(id));
         if (obj.getD_status() != 1) {
           this.deliveryGoodsService.deleteById(obj.getId());
           Goods goods = obj.getD_goods();
           goods.setDelivery_status(0);
           this.goodsService.updateSelectiveById(goods);
         }
       }
     }
     return "redirect:delivery.htm?currentPage=" + currentPage;
   }
 }


 
 
 