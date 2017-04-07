 package com.rt.shop.view.admin.sellers.action;
 
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
import com.rt.shop.entity.UserGoodsClass;
import com.rt.shop.entity.query.UserGoodsClassQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserGoodsClassService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class GoodsClassSellerAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IUserGoodsClassService usergoodsclassService;
 
   @SecurityMapping(display = false, rsequence = 0, title="卖家商品分类列表", value="/seller/usergoodsclass_list.htm*", rtype="seller", rname="商品分类", rcode="usergoodsclass_seller", rgroup="商品管理")
   @RequestMapping({"/seller/usergoodsclass_list.htm"})
   public ModelAndView usergoodsclass_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/usergoodsclass_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     String params = "";
     UserGoodsClassQueryObject qo = new UserGoodsClassQueryObject(
       currentPage, mv, orderBy, orderType);
     qo.setPageSize(Integer.valueOf(20));
   //  wf.toQueryPo(request, qo, UserGoodsClass.class, mv);
     qo.addQuery("obj.parent.id is null", null);
     qo.addQuery("obj.user.id", 
       new SysMap("user_id", 
       SecurityUserHolder.getCurrentUser().getId()), "=");
     qo.setOrderBy("sequence");
     qo.setOrderType("asc");
     Page pList = this.usergoodsclassService.selectPage(new Page<UserGoodsClass>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     
     CommWebUtil.saveIPageList2ModelAndView(url + 
       "/seller/usergoodsclass_list.htm", "", params, pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="卖家商品分类保存", value="/seller/usergoodsclass_save.htm*", rtype="seller", rname="商品分类", rcode="usergoodsclass_seller", rgroup="商品管理")
   @RequestMapping({"/seller/usergoodsclass_save.htm"})
   public String usergoodsclass_save(HttpServletRequest request, HttpServletResponse response,UserGoodsClass usergoodsclass, String id, String pid)
   {
     if (id.equals("")) {
       usergoodsclass.setAddTime(new Date());
     } else {
       UserGoodsClass obj = this.usergoodsclassService.selectById(
         Long.valueOf(Long.parseLong(id)));
     }
     usergoodsclass.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     if (!pid.equals("")) {
       UserGoodsClass parent = this.usergoodsclassService.selectById(
         Long.valueOf(Long.parseLong(pid)));
       usergoodsclass.setParent(parent);
     }
     boolean ret = true;
     if (id.equals(""))
       ret = this.usergoodsclassService.insertSelective(usergoodsclass);
     else
       ret = this.usergoodsclassService.updateSelectiveById(usergoodsclass);
     return "redirect:usergoodsclass_list.htm";
   }
   @SecurityMapping(display = false, rsequence = 0, title="卖家商品分类删除", value="/seller/usergoodsclass_del.htm*", rtype="seller", rname="商品分类", rcode="usergoodsclass_seller", rgroup="商品管理")
   @RequestMapping({"/seller/usergoodsclass_del.htm"})
   public String usergoodsclass_del(HttpServletRequest request, String mulitId) { String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         UserGoodsClass usergoodsclass = this.usergoodsclassService
           .selectById(Long.valueOf(Long.parseLong(id)));
         this.usergoodsclassService.deleteById(Long.valueOf(Long.parseLong(id)));
       }
     }
     return "redirect:usergoodsclass_list.htm"; }
 
   @SecurityMapping(display = false, rsequence = 0, title="新增卖家商品分类", value="/seller/address_add.htm*", rtype="seller", rname="商品分类", rcode="usergoodsclass_seller", rgroup="商品管理")
   @RequestMapping({"/seller/usergoodsclass_add.htm"})
   public ModelAndView usergoodsclass_add(HttpServletRequest request, HttpServletResponse response, String currentPage, String pid) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/usergoodsclass_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
    
    
     List ugcs = this.usergoodsclassService.selectList("where user_id='"+SecurityUserHolder.getCurrentUser().getId()+"' and parent_id is null","sequence asc");
      
      // "select obj from UserGoodsClass obj where obj.parent.id is null and obj.user.id = :uid order by obj.sequence asc", 
     if (!CommUtil.null2String(pid).equals("")) {
       UserGoodsClass parent = this.usergoodsclassService
         .selectById(CommUtil.null2Long(pid));
       UserGoodsClass obj = new UserGoodsClass();
       obj.setParent(parent);
       mv.addObject("obj", obj);
     }
     mv.addObject("ugcs", ugcs);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="编辑卖家商品分类", value="/seller/usergoodsclass_edit.htm*", rtype="seller", rname="商品分类", rcode="usergoodsclass_seller", rgroup="商品管理")
   @RequestMapping({"/seller/usergoodsclass_edit.htm"})
   public ModelAndView usergoodsclass_edit(HttpServletRequest request, HttpServletResponse response, String currentPage, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/usergoodsclass_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     
     List ugcs = this.usergoodsclassService.selectList("where parent_id is null","sequence asc");
     UserGoodsClass obj = this.usergoodsclassService.selectById(
       CommUtil.null2Long(id));
     mv.addObject("obj", obj);
     mv.addObject("ugcs", ugcs);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 }


 
 
 