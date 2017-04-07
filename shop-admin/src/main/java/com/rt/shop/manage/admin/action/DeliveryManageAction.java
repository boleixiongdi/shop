 package com.rt.shop.manage.admin.action;
 
 import java.util.Date;
import java.util.List;

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
import com.rt.shop.entity.Address;
import com.rt.shop.entity.DeliveryGoods;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.Navigation;
import com.rt.shop.entity.SysConfig;
import com.rt.shop.entity.query.DeliveryGoodsQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IDeliveryGoodsService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.INavigationService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class DeliveryManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IAccessoryService accessoryService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IDeliveryGoodsService deliveryGoodsService;
 
   @Autowired
   private INavigationService navigationService;
 
   @SecurityMapping(display = false, rsequence = 0, title="买就送设置", value="/admin/set_delivery.htm*", rtype="admin", rname="买就送", rcode="delivery_admin", rgroup="运营")
   @RequestMapping({"/admin/set_delivery.htm"})
   public ModelAndView set_delivery(HttpServletRequest request, HttpServletResponse response, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/set_delivery.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
 
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="买就送设置保存", value="/admin/set_delivery_save.htm*", rtype="admin", rname="买就送", rcode="delivery_admin", rgroup="运营")
   @RequestMapping({"/admin/set_delivery_save.htm"})
   public ModelAndView set_delivery_save(HttpServletRequest request, HttpServletResponse response, String id) {
     SysConfig obj = this.configService.getSysConfig();
     WebForm wf = new WebForm();
     SysConfig sysConfig = null;
     if (id.equals("")) {
       sysConfig = (SysConfig)wf.toPo(request, SysConfig.class);
       sysConfig.setAddTime(new Date());
     } else {
       sysConfig = (SysConfig)wf.toPo(request, obj);
     }
     if (id.equals(""))
       this.configService.insertSelective(sysConfig);
     else {
       this.configService.updateSelectiveById(sysConfig);
     }
     if (sysConfig.getDelivery_status() == 1) {
      
       Navigation sNavigation=new Navigation();
       sNavigation.setUrl("delivery.htm");
       List navs = this.navigationService.selectList(sNavigation);
         
       if (navs.size() == 0) {
         Navigation nav = new Navigation();
         nav.setAddTime(new Date());
         nav.setDisplay(true);
         nav.setLocation(0);
         nav.setNew_win(1);
         nav.setSequence(6);
         nav.setSysNav(true);
         nav.setTitle("买就送");
         nav.setType("diy");
         nav.setUrl("delivery.htm");
         nav.setOriginal_url("delivery.htm");
         this.navigationService.insertSelective(nav);
       }
     } else {
    	 Navigation sNavigation=new Navigation();
         sNavigation.setUrl("delivery.htm");
       List<Navigation> navs = this.navigationService.selectList(sNavigation);
       for (Navigation nav : navs) {
         this.navigationService.deleteById(nav.getId());
       }
     }
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
 
     mv.addObject("op_title", "买就送设置成功");
     mv.addObject("list_url", CommUtil.getURL(request) + 
       "/admin/set_delivery.htm");
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="买就送商品列表", value="/admin/delivery_goods_list.htm*", rtype="admin", rname="买就送", rcode="delivery_admin", rgroup="运营")
   @RequestMapping({"/admin/delivery_goods_list.htm"})
   public ModelAndView delivery_goods_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String goods_name, String d_status) {
     ModelAndView mv = new JModelAndView(
       "admin/blue/delivery_goods_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     DeliveryGoodsQueryObject qo = new DeliveryGoodsQueryObject(currentPage, 
       mv, orderBy, orderType);
     if (!CommUtil.null2String(d_status).equals("")) {
       qo.addQuery("obj.d_status", 
         new SysMap("d_status", 
         Integer.valueOf(CommUtil.null2Int(d_status))), "=");
     }
     if (!CommUtil.null2String(goods_name).equals("")) {
       qo.addQuery("obj.d_goods.goods_name", 
         new SysMap("goods_name", "%" + 
         goods_name.trim() + "goods_name"), "=");
     }
     Page pList = this.deliveryGoodsService.selectPage(new Page<DeliveryGoods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("d_status", d_status);
     mv.addObject("goods_name", goods_name);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="买就送商品审核", value="/admin/delivery_goods_audit.htm*", rtype="admin", rname="买就送", rcode="delivery_admin", rgroup="运营")
   @RequestMapping({"/admin/delivery_goods_audit.htm"})
   public String delivery_goods_audit(HttpServletRequest request, HttpServletResponse response, String mulitId, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!CommUtil.null2String(id).equals("")) {
         DeliveryGoods obj = this.deliveryGoodsService
           .selectById(CommUtil.null2Long(id));
         obj.setD_admin_user_id(SecurityUserHolder.getCurrentUser().getId());
         obj.setD_status(1);
         obj.setD_audit_time(new Date());
         this.deliveryGoodsService.updateSelectiveById(obj);
         Goods goods = obj.getD_goods();
         goods.setDelivery_status(2);
         this.goodsService.updateSelectiveById(goods);
       }
     }
     return "redirect:delivery_goods_list.htm?currentPage=" + currentPage;
   }
   @SecurityMapping(display = false, rsequence = 0, title="买就送拒绝", value="/admin/delivery_goods_refuse.htm*", rtype="admin", rname="买就送", rcode="delivery_admin", rgroup="运营")
   @RequestMapping({"/admin/delivery_goods_refuse.htm"})
   public String delivery_goods_refuse(HttpServletRequest request, HttpServletResponse response, String mulitId, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!CommUtil.null2String(id).equals("")) {
         DeliveryGoods obj = this.deliveryGoodsService
           .selectById(CommUtil.null2Long(id));
         obj.setD_admin_user_id(SecurityUserHolder.getCurrentUser().getId());
         obj.setD_status(-1);
         obj.setD_refuse_time(new Date());
         this.deliveryGoodsService.updateSelectiveById(obj);
       }
     }
     return "redirect:delivery_goods_list.htm?currentPage=" + currentPage;
   }
 }


 
 
 