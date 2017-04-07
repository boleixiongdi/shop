 package com.rt.shop.manage.admin.action;
 
 import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
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

import com.baomidou.mybatisplus.plugins.Page;
import com.easyjf.beans.BeanUtils;
import com.easyjf.beans.BeanWrapper;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.Brandcategory;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.GoodsBrand;
import com.rt.shop.entity.GoodsType;
import com.rt.shop.entity.query.GoodsBrandQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IBrandcategoryService;
import com.rt.shop.service.IGoodsBrandService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class GoodsBrandManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IGoodsBrandService goodsBrandService;
 
   @Autowired
   private IAccessoryService accessoryService;
 
   @Autowired
   private IBrandcategoryService goodsBrandCategoryService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @SecurityMapping(display = false, rsequence = 0, title="商品品牌列表", value="/admin/goods_brand_list.htm*", rtype="admin", rname="品牌管理", rcode="goods_brand", rgroup="商品")
   @RequestMapping({"/admin/goods_brand_list.htm"})
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String name, String category)
   {
     ModelAndView mv = new JModelAndView("admin/blue/goods_brand_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
    
     GoodsBrand sGoodsBrand=new GoodsBrand();
     sGoodsBrand.setAudit(1);
     Page<GoodsBrand> pList = this.goodsBrandService.selectPage(new Page<GoodsBrand>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
    for(GoodsBrand br :pList.getRecords()){
    	Accessory ac=accessoryService.selectById(br.getBrandLogo_id());
    	br.setBrandLogo(ac);
    }
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("name", name);
     mv.addObject("category", category);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="商品品牌待审核列表", value="/admin/goods_brand_audit.htm*", rtype="admin", rname="品牌管理", rcode="goods_brand", rgroup="商品")
   @RequestMapping({"/admin/goods_brand_audit.htm"})
   public ModelAndView audit(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String name, String category)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/goods_brand_audit.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     GoodsBrandQueryObject qo = new GoodsBrandQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.audit", new SysMap("audit", Integer.valueOf(0)), "=");
     qo.addQuery("obj.userStatus", new SysMap("userStatus", Integer.valueOf(1)), "=");
     if (!CommUtil.null2String(name).equals("")) {
       qo.addQuery("obj.name", 
         new SysMap("name", "%" + name.trim() + "%"), "like");
     }
     if (!CommUtil.null2String(category).equals("")) {
       qo.addQuery("obj.category.name", 
         new SysMap("category", "%" + 
         category.trim() + "%"), "like");
     }
     Page pList = this.goodsBrandService.selectPage(new Page<GoodsBrand>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
    
     CommWebUtil.saveIPageList2ModelAndView("/admin/goods_brand_audit.htm", "", 
       "", pList, mv);
     mv.addObject("name", name);
     mv.addObject("category", category);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="商品品牌审核通过", value="/admin/goods_brands_pass.htm*", rtype="admin", rname="品牌管理", rcode="goods_brand", rgroup="商品")
   @RequestMapping({"/admin/goods_brands_pass.htm"})
   public String goods_brands_pass(HttpServletRequest request, String id) { if (!id.equals("")) {
       GoodsBrand goodsBrand = this.goodsBrandService.selectById(
         Long.valueOf(Long.parseLong(id)));
       goodsBrand.setAudit(1);
       this.goodsBrandService.updateSelectiveById(goodsBrand);
     }
     return "redirect:goods_brand_audit.htm"; } 
   @SecurityMapping(display = false, rsequence = 0, title="商品品牌审核拒绝", value="/admin/goods_brands_refuse.htm*", rtype="admin", rname="品牌管理", rcode="goods_brand", rgroup="商品")
   @RequestMapping({"/admin/goods_brands_refuse.htm"})
   public String goods_brands_refuse(HttpServletRequest request, String id) {
     if (!id.equals("")) {
       GoodsBrand goodsBrand = this.goodsBrandService.selectById(
         Long.valueOf(Long.parseLong(id)));
       goodsBrand.setAudit(-1);
       this.goodsBrandService.updateSelectiveById(goodsBrand);
     }
     return "redirect:goods_brand_audit.htm";
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="商品品牌添加", value="/admin/goods_brand_add.htm*", rtype="admin", rname="品牌管理", rcode="goods_brand", rgroup="商品")
   @RequestMapping({"/admin/goods_brand_add.htm"})
   public ModelAndView add(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("admin/blue/goods_brand_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     List categorys = this.goodsBrandCategoryService.selectList(null);
      
     mv.addObject("categorys", categorys);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="商品品牌编辑", value="/admin/goods_brand_edit.htm*", rtype="admin", rname="品牌管理", rcode="goods_brand", rgroup="商品")
   @RequestMapping({"/admin/goods_brand_edit.htm"})
   public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, String id)
   {
     ModelAndView mv = new JModelAndView("admin/blue/goods_brand_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       GoodsBrand goodsBrand = this.goodsBrandService.selectById(
         Long.valueOf(Long.parseLong(id)));
       mv.addObject("obj", goodsBrand);
     }
     List categorys = this.goodsBrandCategoryService.selectList(null);
      
     mv.addObject("categorys", categorys);
     mv.addObject("edit", Boolean.valueOf(true));
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="商品品牌保存", value="/admin/goods_band_save.htm*", rtype="admin", rname="品牌管理", rcode="goods_brand", rgroup="商品")
   @RequestMapping({"/admin/goods_band_save.htm"})
   public ModelAndView save(HttpServletRequest request, HttpServletResponse response, String id, String cmd, String cat_name, String list_url, String add_url)
   {
     WebForm wf = new WebForm();
     GoodsBrand goodsBrand = null;
     if (id.equals("")) {
       goodsBrand = (GoodsBrand)wf.toPo(request, GoodsBrand.class);
       goodsBrand.setAddTime(new Date());
       goodsBrand.setAudit(1);
       goodsBrand.setUserStatus(0);
     } else {
       GoodsBrand obj = this.goodsBrandService.selectById(
         Long.valueOf(Long.parseLong(id)));
       goodsBrand = (GoodsBrand)wf.toPo(request, obj);
     }
     Brandcategory sBrandcategory=new Brandcategory();
     sBrandcategory.setName(cat_name);
     Brandcategory cat = this.goodsBrandCategoryService.selectOne(sBrandcategory);
     if (cat == null) {
       cat = new Brandcategory();
       cat.setAddTime(new Date());
       cat.setName(cat_name);
       this.goodsBrandCategoryService.insertSelective(cat);
       goodsBrand.setCategory(cat);
     } else {
       goodsBrand.setCategory(cat);
     }
 
     String uploadFilePath = this.configService.getSysConfig()
       .getUploadFilePath();
     String saveFilePathName = request.getSession().getServletContext()
       .getRealPath("/") + 
       uploadFilePath + File.separator + "brand";
     Map map = new HashMap();
     try {
       String fileName = goodsBrand.getBrandLogo() == null ? "" : 
         goodsBrand.getBrandLogo().getName();
       map = CommUtil.saveFileToServer(request, "brandLogo", 
         saveFilePathName, fileName, null);
       if (fileName.equals("")) {
         if (map.get("fileName") != "") {
           Accessory photo = new Accessory();
           photo.setName(CommUtil.null2String(map.get("fileName")));
           photo.setExt(CommUtil.null2String(map.get("mime")));
           photo.setSize(CommUtil.null2Float(map.get("fileSize")));
           photo.setPath(uploadFilePath + "/brand");
           photo.setWidth(CommUtil.null2Int(map.get("width")));
           photo.setHeight(CommUtil.null2Int(map.get("height")));
           photo.setAddTime(new Date());
           this.accessoryService.insertSelective(photo);
           goodsBrand.setBrandLogo(photo);
         }
       }
       else if (map.get("fileName") != "") {
         Accessory photo = goodsBrand.getBrandLogo();
         photo.setName(CommUtil.null2String(map.get("fileName")));
         photo.setExt(CommUtil.null2String(map.get("mime")));
         photo.setSize(CommUtil.null2Float(map.get("fileSize")));
         photo.setPath(uploadFilePath + "/brand");
         photo.setWidth(CommUtil.null2Int(map.get("width")));
         photo.setHeight(CommUtil.null2Int(map.get("height")));
         photo.setAddTime(new Date());
         this.accessoryService.updateSelectiveById(photo);
       }
 
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
     if (id.equals(""))
       this.goodsBrandService.insertSelective(goodsBrand);
     else
       this.goodsBrandService.updateSelectiveById(goodsBrand);
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("list_url", list_url);
     mv.addObject("op_title", "保存品牌成功");
     if (add_url != null) {
       mv.addObject("add_url", add_url);
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="商品品牌删除", value="/admin/goods_brand_del.htm*", rtype="admin", rname="品牌管理", rcode="goods_brand", rgroup="商品")
   @RequestMapping({"/admin/goods_brand_del.htm"})
   public String delete(HttpServletRequest request, String mulitId, String audit, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         GoodsBrand brand = this.goodsBrandService.selectById(
           Long.valueOf(Long.parseLong(id)));
         CommWebUtil.del_acc(request, brand.getBrandLogo());
         for (Goods goods : brand.getGoods_list()) {
           this.goodsService.updateSelectiveById(goods);
         }
         for (GoodsType type : brand.getTypes()) {
           type.getGbs().remove(brand);
         }
         this.goodsBrandService.deleteById(Long.valueOf(Long.parseLong(id)));
       }
     }
     String returnUrl = "redirect:goods_brand_list.htm?currentPage=" + 
       currentPage;
     if ((audit != null) && (!audit.equals(""))) {
       returnUrl = "redirect:goods_brand_audit.htm?currentPage=" + 
         currentPage;
     }
     return returnUrl;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="商品品牌AJAX更新", value="/admin/goods_brand_ajax.htm*", rtype="admin", rname="品牌管理", rcode="goods_brand", rgroup="商品")
   @RequestMapping({"/admin/goods_brand_ajax.htm"})
   public void ajax(HttpServletRequest request, HttpServletResponse response, String id, String fieldName, String value) throws ClassNotFoundException {
     GoodsBrand obj = this.goodsBrandService.selectById(Long.valueOf(Long.parseLong(id)));
     Field[] fields = GoodsBrand.class.getDeclaredFields();
     BeanWrapper wrapper = new BeanWrapper(obj);
     Object val = null;
     for (Field field : fields) {
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
     this.goodsBrandService.updateSelectiveById(obj);
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
 
   @RequestMapping({"/admin/goods_brand_verify.htm"})
   public void goods_brand_verify(HttpServletRequest request, HttpServletResponse response, String name, String id) {
     boolean ret = true;
     
     String sql="where name='"+name+"' and id!="+CommUtil.null2Long(id);
     List gcs = this.goodsBrandService.selectList(sql,null);

     if ((gcs != null) && (gcs.size() > 0)) {
       ret = false;
     }
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(ret);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 }


 
 
 