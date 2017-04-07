 package com.rt.shop.manage.admin.action;
 
 import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
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
import com.easyjf.beans.BeanUtils;
import com.easyjf.beans.BeanWrapper;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.GoodsBrand;
import com.rt.shop.entity.SpareGoods;
import com.rt.shop.entity.SpareGoodsClass;
import com.rt.shop.entity.SpareGoodsFloor;
import com.rt.shop.entity.query.SpareGoodsClassQueryObject;
import com.rt.shop.entity.query.SpareGoodsQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.ISpareGoodsClassService;
import com.rt.shop.service.ISpareGoodsFloorService;
import com.rt.shop.service.ISpareGoodsService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class SpareGoodsManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private ISpareGoodsClassService sparegoodsclassService;
 
   @Autowired
   private ISpareGoodsService sparegoodsService;
 
   @Autowired
   private ISpareGoodsFloorService spareGoodsFloorService;
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品列表", value="/admin/sparegoods_list.htm*", rtype="admin", rname="闲置商品", rcode="sparegoods_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoods_list.htm"})
   public ModelAndView spare_goods(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String type)
   {
     ModelAndView mv = new JModelAndView("admin/blue/sparegoods_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     SpareGoodsQueryObject qo = new SpareGoodsQueryObject(currentPage, mv, 
       orderBy, orderType);
     if ((type != null) && (!type.equals(""))) {
       qo.addQuery("obj.status", 
         new SysMap("obj_status", 
         Integer.valueOf(CommUtil.null2Int(type))), "=");
       mv.addObject("type", type);
     } else {
       qo.addQuery("obj.status", new SysMap("obj_status", Integer.valueOf(0)), "=");
     }
     qo.setPageSize(Integer.valueOf(15));
     qo.setOrderBy("addTime");
     qo.setOrderType("desc");
     Page pList = this.sparegoodsService.selectPage(new Page<SpareGoods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品下架上架切换", value="/admin/sparegoods_switch.htm*", rtype="admin", rname="闲置商品", rcode="sparegoods_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoods_switch.htm"})
   public String sparegoods_switch(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String type)
   {
     if ((id != null) && (!id.equals(""))) {
       SpareGoods sg = this.sparegoodsService.selectById(
         CommUtil.null2Long(id));
       if (sg.getStatus() == 0)
         sg.setStatus(-1);
       else {
         sg.setStatus(0);
       }
       this.sparegoodsService.updateSelectiveById(sg);
     }
     return "redirect:sparegoods_list.htm?currentPage=" + currentPage + (
       type != null ? "&type=" + type : "");
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品信息删除", value="/admin/sparegoods_dele.htm*", rtype="admin", rname="闲置商品", rcode="sparegoods_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoods_dele.htm"})
   public String sparegoods_delete(HttpServletRequest request, HttpServletResponse response, String mulitId, String currentPage)
   {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       this.sparegoodsService.deleteById(CommUtil.null2Long(id));
     }
     return "redirect:sparegoods_list.htm?currentPage=" + currentPage;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品AJAX更新", value="/admin/sparegoods_ajax.htm*", rtype="admin", rname="闲置商品", rcode="sparegoods_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoods_ajax.htm"})
   public void sparegoods_ajax(HttpServletRequest request, HttpServletResponse response, String id, String fieldName, String value) throws ClassNotFoundException {
     SpareGoods obj = this.sparegoodsService.selectById(Long.valueOf(Long.parseLong(id)));
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
     this.sparegoodsService.updateSelectiveById(obj);
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
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品分类列表", value="/admin/sparegoods_class_list.htm*", rtype="admin", rname="闲置分类", rcode="sparegoods_class_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoods_class_list.htm"})
   public ModelAndView sparegoods_class_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/sparegoods_class_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Page pList = this.sparegoodsclassService.selectPage(new Page<SpareGoodsClass>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null,"where parent_id is null","sequence ");
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品分类添加", value="/admin/sparegoods_class_add.htm*", rtype="admin", rname="闲置分类", rcode="sparegoods_class_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoods_class_add.htm"})
   public ModelAndView sparegoodsclass_add(HttpServletRequest request, HttpServletResponse response, String pid)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/sparegoods_class_add.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     SpareGoodsClass sSpareGoodsClass=new SpareGoodsClass();
     sSpareGoodsClass.setParent_id(null);
     List sparegoodsClass = this.sparegoodsclassService.selectList(sSpareGoodsClass);
     mv.addObject("sgcs", sparegoodsClass);
     if ((pid != null) && (!pid.equals(""))) {
       SpareGoodsClass parent = this.sparegoodsclassService
         .selectById(CommUtil.null2Long(pid));
       SpareGoodsClass obj = new SpareGoodsClass();
       obj.setParent_id(parent.getId());
       mv.addObject("obj", obj);
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品分类编辑", value="/admin/sparegoods_class_edit.htm*", rtype="admin", rname="闲置分类", rcode="sparegoods_class_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoods_class_edit.htm"})
   public ModelAndView sparegoods_class_edit(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/sparegoods_class_add.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       SpareGoodsClass sparegoodsclass = this.sparegoodsclassService
         .selectById(Long.valueOf(Long.parseLong(id)));
       mv.addObject("obj", sparegoodsclass);
       mv.addObject("currentPage", currentPage);
       mv.addObject("edit", Boolean.valueOf(true));
     }SpareGoodsClass sSpareGoodsClass=new SpareGoodsClass();
     sSpareGoodsClass.setParent_id(null);
     List sparegoodsClass = this.sparegoodsclassService.selectList(sSpareGoodsClass);
     mv.addObject("sgcs", sparegoodsClass);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品分类保存", value="/admin/sparegoods_class_save.htm*", rtype="admin", rname="闲置分类", rcode="sparegoods_class_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoods_class_save.htm"})
   public ModelAndView sparegoods_class_save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String list_url, String add_url, String pid)
   {
     WebForm wf = new WebForm();
     SpareGoodsClass sparegoodsclass = null;
     if (id.equals("")) {
       sparegoodsclass = (SpareGoodsClass)wf.toPo(request, SpareGoodsClass.class);
       sparegoodsclass.setAddTime(new Date());
       sparegoodsclass.setLevel(1);
     } else {
       SpareGoodsClass obj = this.sparegoodsclassService.selectById(
         Long.valueOf(Long.parseLong(id)));
       sparegoodsclass = (SpareGoodsClass)wf.toPo(request, obj);
     }
     if ((pid != null) && (!pid.equals(""))) {
       SpareGoodsClass parent = this.sparegoodsclassService
         .selectById(CommUtil.null2Long(pid));
       sparegoodsclass.setParent_id(parent.getId());
       sparegoodsclass.setLevel(parent.getLevel() + 1);
     }
     if (id.equals(""))
       this.sparegoodsclassService.insertSelective(sparegoodsclass);
     else
       this.sparegoodsclassService.updateSelectiveById(sparegoodsclass);
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("list_url", list_url);
     mv.addObject("op_title", "闲置商品分类保存成功!");
     if (add_url != null) {
       mv.addObject("add_url", add_url + "?pid=" + pid + "&currentPage=" + 
         currentPage);
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品分类删除", value="/admin/sparegoods_class_del.htm*", rtype="admin", rname="闲置分类", rcode="sparegoods_class_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoods_class_del.htm"})
   public String sparegoods_class_del(HttpServletRequest request, HttpServletResponse response, String mulitId, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         Set list = genericIds(this.sparegoodsclassService
           .selectById(Long.valueOf(Long.parseLong(id))));
         Map params = new HashMap();
         params.put("ids", list);
         if(list!=null && list.size()>0){
        	 String x=CommUtil.exactSetToString(list);
        	 String sql="where id in("+x+") order by level desc";
             List<SpareGoodsClass> sgcs = this.sparegoodsclassService.selectList(sql,null);

             for (SpareGoodsClass gc : sgcs) {
            	 SpareGoods sSpareGoods=new SpareGoods();
            	 sSpareGoods.setSpareGoodsClass_id(gc.getId());
            	 List<SpareGoods> childs=sparegoodsService.selectList(sSpareGoods);
               for (SpareGoods sg : childs) {
                 sg.setSpareGoodsClass_id(null);
                 this.sparegoodsService.updateSelectiveById(sg);
               }
               SpareGoodsFloor sSpareGoodsFloor=new SpareGoodsFloor();
               sSpareGoodsFloor.setSgc_id(gc.getId());
               SpareGoodsFloor floor = spareGoodsFloorService.selectOne(sSpareGoodsFloor);
               if (floor != null) {
                 floor.setSgc_id(null);
                 this.spareGoodsFloorService.updateSelectiveById(floor);
               }
               gc.setParent_id(null);
               this.sparegoodsclassService.deleteById(gc.getId());
             } 
         }
        
       }
     }
     return "redirect:sparegoods_class_list.htm?currentPage=" + currentPage;
   }
 

   private Set<Long> genericIds(SpareGoodsClass gc) {
	   if(gc!=null){
		   Set ids = new HashSet();
		     ids.add(gc.getId());
		     SpareGoodsClass sGoodsClass=new SpareGoodsClass();
		     sGoodsClass.setParent_id(gc.getId());
		     List<SpareGoodsClass> childs=sparegoodsclassService.selectList(sGoodsClass);
		     if(childs!=null && childs.size()>0){
		    	 for (SpareGoodsClass child : childs) {
				       Set<Long> cids = genericIds(child);
				       for (Long cid : cids) {
				         ids.add(cid);
				       }
				       ids.add(child.getId());
				     }
		     }
		     
		     return ids;
	   }
	return null;
	}
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品分类Ajax更新", value="/admin/sparegoods_class_ajax.htm*", rtype="admin", rname="闲置分类", rcode="sparegoods_class_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoods_class_ajax.htm"})
   public void sparegoods_class_ajax(HttpServletRequest request, HttpServletResponse response, String id, String fieldName, String value) throws ClassNotFoundException {
     SpareGoodsClass obj = this.sparegoodsclassService.selectById(
       Long.valueOf(Long.parseLong(id)));
     Field[] fields = SpareGoodsClass.class.getDeclaredFields();
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
     this.sparegoodsclassService.updateSelectiveById(obj);
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
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品分类下级加载", value="/admin/sparegoods_class_data.htm*", rtype="admin", rname="闲置分类", rcode="sparegoods_class_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoods_class_data.htm"})
   public ModelAndView sparegoods_class_data(HttpServletRequest request, HttpServletResponse response, String pid) {
     ModelAndView mv = new JModelAndView(
       "admin/blue/sparegoods_class_data.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Map map = new HashMap();
     map.put("pid", Long.valueOf(Long.parseLong(pid)));
     SpareGoodsClass sSpareGoodsClass=new SpareGoodsClass();
     sSpareGoodsClass.setParent_id(Long.valueOf(Long.parseLong(pid)));
     List<SpareGoodsClass> gcs = this.sparegoodsclassService.selectList(sSpareGoodsClass);
     for(SpareGoodsClass sgc : gcs){
    	 SpareGoodsClass parent=sparegoodsclassService.selectById(sgc.getParent_id());
    	 sgc.setParent(parent);
     }
     
     mv.addObject("gcs", gcs);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品分类验证是否存在", value="/admin/sparegoods_class_verify.htm*", rtype="admin", rname="闲置分类", rcode="sparegoods_class_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoods_class_verify.htm"})
   public void sparegoods_class_verify(HttpServletRequest request, HttpServletResponse response, String className, String pid) {
     boolean ret = true;
     List sgcs = null;
     SpareGoodsClass sSpareGoodsClass=new SpareGoodsClass();
	 sSpareGoodsClass.setClassName(className);
     sgcs = this.sparegoodsclassService.selectList(sSpareGoodsClass);
     if (pid.equals("")) {
       sgcs = this.sparegoodsclassService.selectList(sSpareGoodsClass);
     } else {
       sSpareGoodsClass.setParent_id(CommUtil.null2Long(pid));
       sgcs = this.sparegoodsclassService.selectList(sSpareGoodsClass);
     }
     if (sgcs.size() > 0) {
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


 
 
 