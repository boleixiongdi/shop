 package com.rt.shop.manage.admin.action;
 
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
import com.rt.shop.entity.Evaluate;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.User;
import com.rt.shop.entity.query.EvaluateQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IEvaluateService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class EvaluateManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IEvaluateService evaluateService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IStoreService storeService;
 
   @SecurityMapping(display = false, rsequence = 0, title="商品评价列表", value="/admin/evaluate_list.htm*", rtype="admin", rname="商品评价", rcode="evaluate_admin", rgroup="交易")
   @RequestMapping({"/admin/evaluate_list.htm"})
   public ModelAndView evaluate_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String goods_name, String userName)
   {
     ModelAndView mv = new JModelAndView("admin/blue/evaluate_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     EvaluateQueryObject qo = new EvaluateQueryObject(currentPage, mv, 
       orderBy, orderType);
     if (!CommUtil.null2String(goods_name).equals("")) {
       qo.addQuery("obj.evaluate_goods.goods_name", 
         new SysMap("goods_name", "%" + goods_name + "%"), "like");
     }
     if (!CommUtil.null2String(userName).equals("")) {
       qo.addQuery("obj.evaluate_user.userName", 
         new SysMap("evaluate_user", userName), "=");
     }
     mv.addObject("goods_name", goods_name);
     mv.addObject("userName", userName);
     Page pList = this.evaluateService.selectPage(new Page<Evaluate>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="商品评价编辑", value="/admin/evaluate_edit.htm*", rtype="admin", rname="商品评价", rcode="evaluate_admin", rgroup="交易")
   @RequestMapping({"/admin/evaluate_edit.htm"})
   public ModelAndView evaluate_edit(HttpServletRequest request, HttpServletResponse response, String currentPage, String id) {
     ModelAndView mv = new JModelAndView("admin/blue/evaluate_edit.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     Evaluate obj = this.evaluateService.selectById(CommUtil.null2Long(id));
     mv.addObject("obj", obj);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="商品评价编辑", value="/admin/evaluate_save.htm*", rtype="admin", rname="商品评价", rcode="evaluate_admin", rgroup="交易")
   @RequestMapping({"/admin/evaluate_save.htm"})
   public ModelAndView evaluate_save(HttpServletRequest request, HttpServletResponse response, String currentPage, String id, String evaluate_status, String evaluate_admin_info, String list_url, String edit)
   {
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     Evaluate obj = this.evaluateService.selectById(CommUtil.null2Long(id));
     obj.setEvaluate_admin_info(evaluate_admin_info);
     obj.setEvaluate_status(CommUtil.null2Int(evaluate_status));
     this.evaluateService.updateSelectiveById(obj);
     if ((CommUtil.null2Boolean(edit)) && (obj.getEvaluate_status() == 2)) {
       User user = userService.selectById(obj.getEvaluate_seller_user_id());
       Store store=storeService.selectById(user.getStore_id());
       
       user.setUser_credit(user.getUser_credit() - 
         obj.getEvaluate_buyer_val());
       this.userService.updateSelectiveById(user);
       store.setStore_credit(store.getStore_credit() - 
         obj.getEvaluate_seller_val());
       this.storeService.updateSelectiveById(store);
     }
     mv.addObject("list_url", list_url);
     mv.addObject("op_title", "商品评价编辑成功");
     return mv;
   }
 }


 
 
 