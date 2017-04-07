 package com.rt.shop.manage.admin.action;
 
 import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
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
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.Partner;
import com.rt.shop.entity.query.PartnerQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IPartnerService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class PartnerManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IPartnerService partnerService;
 
   @Autowired
   private IAccessoryService accessoryService;
 
   @SecurityMapping(display = false, rsequence = 0, title="合作伙伴列表", value="/admin/partner_list.htm*", rtype="admin", rname="合作伙伴", rcode="partner_manage", rgroup="网站")
   @RequestMapping({"/admin/partner_list.htm"})
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String title)
   {
     ModelAndView mv = new JModelAndView("admin/blue/partner_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     PartnerQueryObject qo = new PartnerQueryObject(currentPage, mv, 
       orderBy, orderType);
     if ((title != null) && (!title.equals(""))) {
       qo.addQuery("obj.title", new SysMap("title", "%" + title + "%"), 
         "like");
     }
     WebForm wf = new WebForm();
     wf.toQueryPo(request, qo, Partner.class, mv);
     qo.setOrderBy("sequence");
     qo.setOrderType("asc");
     Page pList = this.partnerService.selectPage(new Page<Partner>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     if ((title != null) && (!title.equals("")))
       CommWebUtil.saveIPageList2ModelAndView(
         url + "/admin/partner_list.htm", "", "title=" + title, 
         pList, mv);
     else {
       CommWebUtil.saveIPageList2ModelAndView(
         url + "/admin/partner_list.htm", "", "", pList, mv);
     }
 
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="合作伙伴添加", value="/admin/partner_add.htm*", rtype="admin", rname="合作伙伴", rcode="partner_manage", rgroup="网站")
   @RequestMapping({"/admin/partner_add.htm"})
   public ModelAndView add(HttpServletRequest request, HttpServletResponse response, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/partner_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="合作伙伴编辑", value="/admin/partner_edit.htm*", rtype="admin", rname="合作伙伴", rcode="partner_manage", rgroup="网站")
   @RequestMapping({"/admin/partner_edit.htm"})
   public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/partner_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       Partner partner = this.partnerService
         .selectById(Long.valueOf(Long.parseLong(id)));
       mv.addObject("obj", partner);
       mv.addObject("currentPage", currentPage);
       mv.addObject("edit", Boolean.valueOf(true));
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="合作伙伴保存", value="/admin/partner_save.htm*", rtype="admin", rname="合作伙伴", rcode="partner_manage", rgroup="网站")
   @RequestMapping({"/admin/partner_save.htm"})
   public ModelAndView save(HttpServletRequest request, HttpServletResponse response, String id, String list_url, String add_url)
   {
     WebForm wf = new WebForm();
     Partner partner = null;
     if (id.equals("")) {
       partner = (Partner)wf.toPo(request, Partner.class);
       partner.setAddTime(new Date());
     } else {
       Partner obj = this.partnerService.selectById(Long.valueOf(Long.parseLong(id)));
       partner = (Partner)wf.toPo(request, obj);
     }
 
     String uploadFilePath = this.configService.getSysConfig()
       .getUploadFilePath();
     String saveFilePathName = request.getSession().getServletContext()
       .getRealPath("/") + 
       uploadFilePath;
     Map map = new HashMap();
     try {
    	 Accessory img=accessoryService.selectById(partner.getImage_id());
       String fileName = img == null ? "" : img.getName();
       map = CommUtil.saveFileToServer(request, "image", saveFilePathName, 
         fileName, null);
       if (fileName.equals("")) {
         if (map.get("fileName") != "") {
           Accessory photo = new Accessory();
           photo.setName(CommUtil.null2String(map.get("fileName")));
           photo.setExt(CommUtil.null2String(map.get("mime")));
           photo.setSize(CommUtil.null2Float(map.get("fileSize")));
           photo.setPath(uploadFilePath);
           photo.setWidth(CommUtil.null2Int(map.get("width")));
           photo.setHeight(CommUtil.null2Int(map.get("height")));
           photo.setAddTime(new Date());
           this.accessoryService.insertSelective(photo);
           partner.setImage_id(photo.getId());
         }
       }
       else if (map.get("fileName") != "") {
         Accessory photo =img;
         photo.setName(CommUtil.null2String(map.get("fileName")));
         photo.setExt(CommUtil.null2String(map.get("mime")));
         photo.setSize(CommUtil.null2Float(map.get("fileSize")));
         photo.setPath(uploadFilePath);
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
       this.partnerService.insertSelective(partner);
     else
       this.partnerService.updateSelectiveById(partner);
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("list_url", list_url);
     mv.addObject("op_title", "保存合作伙伴成功");
     if (add_url != null) {
       mv.addObject("add_url", add_url);
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="合作伙伴删除", value="/admin/partner_del.htm*", rtype="admin", rname="合作伙伴", rcode="partner_manage", rgroup="网站")
   @RequestMapping({"/admin/partner_del.htm"})
   public String delete(HttpServletRequest request, String mulitId) { String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         Partner partner = this.partnerService.selectById(
           Long.valueOf(Long.parseLong(id)));
         Accessory img=accessoryService.selectById(partner.getImage_id());
         CommWebUtil.del_acc(request, img);
         this.partnerService.deleteById(Long.valueOf(Long.parseLong(id)));
       }
     }
     return "redirect:partner_list.htm";
   }
 }


 
 
 