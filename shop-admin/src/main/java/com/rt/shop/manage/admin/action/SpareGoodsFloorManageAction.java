 package com.rt.shop.manage.admin.action;
 
 import java.io.File;
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
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.AdvPos;
import com.rt.shop.entity.SpareGoods;
import com.rt.shop.entity.SpareGoodsClass;
import com.rt.shop.entity.SpareGoodsFloor;
import com.rt.shop.entity.query.SpareGoodsClassQueryObject;
import com.rt.shop.entity.query.SpareGoodsFloorQueryObject;
import com.rt.shop.entity.query.SpareGoodsQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IAdvPosService;
import com.rt.shop.service.IAdvertService;
import com.rt.shop.service.ISpareGoodsClassService;
import com.rt.shop.service.ISpareGoodsFloorService;
import com.rt.shop.service.ISpareGoodsService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class SpareGoodsFloorManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private ISpareGoodsFloorService sparegoodsfloorService;
 
   @Autowired
   private ISpareGoodsClassService sparegoodsclassService;
 
   @Autowired
   private ISpareGoodsService sparegoodsService;
 
   @Autowired
   private IAccessoryService accessoryService;
 
   @Autowired
   private IAdvertService advertService;
 
   @Autowired
   private IAdvPosService advertPositionService;
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品楼层列表", value="/admin/sparegoodsfloor_list.htm*", rtype="admin", rname="闲置楼层", rcode="sparegoodsfloor_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoodsfloor_list.htm"})
   public ModelAndView floor_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/sparegoodsfloor_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     SpareGoodsFloorQueryObject qo = new SpareGoodsFloorQueryObject(
       currentPage, mv, orderBy, orderType);
     qo.setOrderBy("sequence");
     qo.setOrderType("asc");
     Page pList = this.sparegoodsfloorService.selectPage(new Page<SpareGoodsFloor>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品楼层添加", value="/admin/sparegoodsfloor_add.htm*", rtype="admin", rname="闲置楼层", rcode="sparegoodsfloor_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoodsfloor_add.htm"})
   public ModelAndView floor_add(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/sparegoodsfloor_add.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     SpareGoodsClass sSpareGoodsClass=new SpareGoodsClass();
     sSpareGoodsClass.setParent_id(null);
     List sgcs = this.sparegoodsclassService.selectList(sSpareGoodsClass);
      
    
     AdvPos advPos=new AdvPos();
     advPos.setAp_type("img");
     advPos.setAp_height(Integer.valueOf(240));
     advPos.setAp_width(Integer.valueOf(200));
     List advertposition = this.advertPositionService.selectList(advPos,"addTime asc");
       
     mv.addObject("advertposition", advertposition);
     mv.addObject("sgcs", sgcs);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品楼层编辑", value="/admin/sparegoodsfloor_edit.htm*", rtype="admin", rname="闲置楼层", rcode="sparegoodsfloor_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoodsfloor_edit.htm"})
   public ModelAndView floor_edit(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/sparegoodsfloor_add.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       SpareGoodsFloor sparegoodsfloor = this.sparegoodsfloorService
         .selectById(Long.valueOf(Long.parseLong(id)));
       mv.addObject("obj", sparegoodsfloor);
       mv.addObject("currentPage", currentPage);
       mv.addObject("edit", Boolean.valueOf(true));
     }
     SpareGoodsClass sSpareGoodsClass=new SpareGoodsClass();
     sSpareGoodsClass.setParent_id(null);
     List sgcs = this.sparegoodsclassService.selectList(sSpareGoodsClass);
     
     AdvPos advPos=new AdvPos();
     advPos.setAp_type("img");
     advPos.setAp_height(Integer.valueOf(250));
     advPos.setAp_width(Integer.valueOf(200));
     List advertposition = this.advertPositionService.selectList(advPos,"addTime asc");
     mv.addObject("advertposition", advertposition);
     mv.addObject("sgcs", sgcs);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品楼层保存", value="/admin/sparegoodsfloor_save.htm*", rtype="admin", rname="闲置楼层", rcode="sparegoodsfloor_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoodsfloor_save.htm"})
   public ModelAndView floor_save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String cmd, String list_url, String add_url, String cid, String advert_url, String ap_id, String advert_type)
   {
     WebForm wf = new WebForm();
     SpareGoodsFloor sparegoodsfloor = null;
     if (id.equals("")) {
       sparegoodsfloor = (SpareGoodsFloor)wf.toPo(request, SpareGoodsFloor.class);
       sparegoodsfloor.setAddTime(new Date());
     } else {
       SpareGoodsFloor obj = this.sparegoodsfloorService.selectById(
         Long.valueOf(Long.parseLong(id)));
       sparegoodsfloor = (SpareGoodsFloor)wf.toPo(request, obj);
     }
     SpareGoodsClass sgc = this.sparegoodsclassService.selectById(
       CommUtil.null2Long(cid));
     sparegoodsfloor.setSgc_id(sgc.getId());
     if ((ap_id != null) && (!ap_id.equals(""))) {
       AdvPos adp = this.advertPositionService.selectById(
         CommUtil.null2Long(ap_id));
       sparegoodsfloor.setAdp_id(adp.getId());
     }
     if ((advert_url != null) && (!advert_url.equals(""))) {
       sparegoodsfloor.setAdvert_url(advert_url);
     }
     String uploadFilePath = this.configService.getSysConfig()
       .getUploadFilePath();
     String saveFilePathName = request.getSession().getServletContext()
       .getRealPath("/") + 
       uploadFilePath + File.separator + "advert";
     Map map = new HashMap();
     String fileName = "";
     Accessory advImg=accessoryService.selectById(sparegoodsfloor.getAdvert_img_id());
     if (advImg != null)
       fileName = advImg.getName();
     try
     {
       map = CommUtil.saveFileToServer(request, "advert_img", 
         saveFilePathName, fileName, null);
       Accessory advert_img = null;
       if (fileName.equals("")) {
         if (map.get("fileName") != "") {
           advert_img = new Accessory();
           advert_img.setName(
             CommUtil.null2String(map.get("fileName")));
           advert_img.setExt(CommUtil.null2String(map.get("mime")));
           advert_img
             .setSize(CommUtil.null2Float(map.get("fileSize")));
           advert_img.setPath(uploadFilePath + "/advert");
           advert_img.setWidth(CommUtil.null2Int(map.get("width")));
           advert_img.setHeight(CommUtil.null2Int(map.get("height")));
           advert_img.setAddTime(new Date());
           this.accessoryService.insertSelective(advert_img);
           sparegoodsfloor.setAdvert_img_id(advert_img.getId());
         }
       }
       else if (map.get("fileName") != "") {
         advert_img = advImg;
         advert_img.setName(
           CommUtil.null2String(map.get("fileName")));
         advert_img.setExt(CommUtil.null2String(map.get("mime")));
         advert_img
           .setSize(CommUtil.null2Float(map.get("fileSize")));
         advert_img.setPath(uploadFilePath + "/advert");
         advert_img.setWidth(CommUtil.null2Int(map.get("width")));
         advert_img.setHeight(CommUtil.null2Int(map.get("height")));
         advert_img.setAddTime(new Date());
         this.accessoryService.updateSelectiveById(advert_img);
       }
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
 
     if (id.equals(""))
       this.sparegoodsfloorService.insertSelective(sparegoodsfloor);
     else
       this.sparegoodsfloorService.updateSelectiveById(sparegoodsfloor);
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("list_url", list_url);
     mv.addObject("op_title", "楼层保存成功");
     if (add_url != null) {
       mv.addObject("add_url", add_url + "?currentPage=" + currentPage);
     }
 
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品楼层ajax更新", value="/admin/sparegoodsfloor_ajax.htm*", rtype="admin", rname="闲置楼层", rcode="sparegoodsfloor_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoodsfloor_ajax.htm"})
   public void floor_ajax(HttpServletRequest request, HttpServletResponse response, String id, String fieldName, String value) throws ClassNotFoundException {
     SpareGoodsFloor obj = this.sparegoodsfloorService.selectById(
       Long.valueOf(Long.parseLong(id)));
     Field[] fields = SpareGoodsFloor.class.getDeclaredFields();
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
     this.sparegoodsfloorService.updateSelectiveById(obj);
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
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品楼层删除", value="/admin/sparegoodsfloor_del.htm*", rtype="admin", rname="闲置楼层", rcode="sparegoodsfloor_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoodsfloor_del.htm"})
   public String floor_delete(HttpServletRequest request, HttpServletResponse response, String mulitId, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         SpareGoodsFloor sparegoodsfloor = this.sparegoodsfloorService
           .selectById(Long.valueOf(Long.parseLong(id)));
         this.sparegoodsfloorService.deleteById(Long.valueOf(Long.parseLong(id)));
       }
     }
     return "redirect:sparegoodsfloor_list.htm?currentPage=" + currentPage;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品楼层分类设置", value="/admin/sparegoodsfloor_setclass.htm*", rtype="admin", rname="闲置楼层", rcode="sparegoodsfloor_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoodsfloor_setclass.htm"})
   public ModelAndView floor_setclass(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String fid)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/sparegoodsfloor_setclass.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     SpareGoodsClassQueryObject qo = new SpareGoodsClassQueryObject(
       currentPage, mv, orderBy, orderType);
     SpareGoodsFloor sgf = this.sparegoodsfloorService.selectById(
       CommUtil.null2Long(fid));
     SpareGoodsClass sgc=sparegoodsclassService.selectById(sgf.getSgc_id());
     Set ids = getSpareGoodsClassChildIds(sgc);
     Map map = new HashMap();
     map.put("ids", ids);
     qo.addQuery("obj.id in(:ids)", map);
     qo.addQuery("obj.id", new SysMap("obj_id", sgf.getSgc_id()), "!=");
     qo.setOrderBy("level");
     qo.setOrderType("asc");
     qo.setPageSize(Integer.valueOf(15));
     Page pList = this.sparegoodsclassService.selectPage(new Page<SpareGoodsClass>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("fid", fid);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="商品楼层分类ajax更新", value="/admin/sparegoodsfloor_class_ajax.htm*", rtype="admin", rname="闲置楼层", rcode="sparegoodsfloor_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoodsfloor_class_ajax.htm"})
   public void floor_class_ajax(HttpServletRequest request, HttpServletResponse response, String id, String fieldName, String value) throws ClassNotFoundException {
     SpareGoodsClass obj = this.sparegoodsclassService.selectById(
       Long.valueOf(Long.parseLong(id)));
     Field[] fields = SpareGoodsClass.class.getDeclaredFields();
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
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品楼层分类设置是否显示", value="/admin/sparegoodsfloor_setclass_switch.htm*", rtype="admin", rname="闲置楼层", rcode="sparegoodsfloor_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoodsfloor_setclass_switch.htm"})
   public String floor_setclass_switch(HttpServletRequest request, HttpServletResponse response, String currentPage, String fid, String mulitId, String type) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if ((id != null) && (!id.equals(""))) {
         SpareGoodsClass sgc = this.sparegoodsclassService
           .selectById(CommUtil.null2Long(id));
         if ((type != null) && (!type.equals(""))) {
           if (type.equals("show"))
             sgc.setViewInFloor(true);
           else {
             sgc.setViewInFloor(false);
           }
         }
         else if (!sgc.getViewInFloor())
           sgc.setViewInFloor(true);
         else {
           sgc.setViewInFloor(false);
         }
 
         this.sparegoodsclassService.updateSelectiveById(sgc);
       }
     }
     return "redirect:sparegoodsfloor_setclass.htm?currentPage=" + 
       currentPage + "&fid=" + fid;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品楼层商品设置", value="/admin/sparegoodsfloor_setgoods.htm*", rtype="admin", rname="闲置楼层", rcode="sparegoodsfloor_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoodsfloor_setgoods.htm"})
   public ModelAndView floor_setgoods(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String fid)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/sparegoodsfloor_setgoods.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     SpareGoodsFloor sgf = this.sparegoodsfloorService.selectById(
       CommUtil.null2Long(fid));
     SpareGoodsClass sgc=sparegoodsclassService.selectById(sgf.getSgc_id());
     Set ids = getSpareGoodsClassChildIds(sgc);
     Map map = new HashMap();
     map.put("ids", ids);
     SpareGoodsQueryObject qo = new SpareGoodsQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.status", new SysMap("obj_status", Integer.valueOf(0)), "=");
     qo.addQuery("obj.down", new SysMap("obj_down", Integer.valueOf(0)), "=");
     qo.addQuery("obj.spareGoodsClass.id in (:ids)", map);
     Page pList = this.sparegoodsService.selectPage(new Page<SpareGoods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("fid", fid);
     if(ids!=null &&  ids.size()>0){
    	 String x=CommUtil.exactSetToString(ids);
         String sql="where sgf_id is null and  spareGoodsClass_id in ("+x+")";
         List<SpareGoods> sgs = this.sparegoodsService.selectList(sql,null);
//           .query(
//           "select obj from SpareGoods obj where obj.sgf.id is null and  obj.spareGoodsClass.id in (:ids)", 
//           map, -1, -1);
         for (SpareGoods sg : sgs) {
           sg.setSgf_id(sgf.getId());
           this.sparegoodsService.updateSelectiveById(sg);
         }
     }
    
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="闲置商品楼层商品设置是否显示", value="/admin/sparegoodsfloor_setgoods_switch.htm*", rtype="admin", rname="闲置楼层", rcode="sparegoodsfloor_admin", rgroup="闲置")
   @RequestMapping({"/admin/sparegoodsfloor_setgoods_switch.htm"})
   public String floor_setgoods_switch(HttpServletRequest request, HttpServletResponse response, String currentPage, String mulitId, String fid, String type) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if ((id != null) && (!id.equals(""))) {
         SpareGoods sg = this.sparegoodsService.selectById(
           CommUtil.null2Long(id));
         if ((type != null) && (!type.equals(""))) {
           if (type.equals("show"))
             sg.setViewInFloor(true);
           else {
             sg.setViewInFloor(false);
           }
         }
         else if (!sg.getViewInFloor())
           sg.setViewInFloor(true);
         else {
           sg.setViewInFloor(false);
         }
 
         this.sparegoodsService.updateSelectiveById(sg);
       }
     }
     return "redirect:sparegoodsfloor_setgoods.htm?currentPage=" + 
       currentPage + "&fid=" + fid;
   }
 
   private Set<Long> getSpareGoodsClassChildIds(SpareGoodsClass obj) {
     Set ids = new HashSet();
     ids.add(obj.getId());
     SpareGoodsClass sSpareGoodsClass=new SpareGoodsClass();
     sSpareGoodsClass.setParent_id(obj.getId());
     List<SpareGoodsClass> sgcList=sparegoodsclassService.selectList(sSpareGoodsClass);
     if (sgcList.size() > 0) {
       for (SpareGoodsClass child : sgcList) {
         Set<Long> cids = getSpareGoodsClassChildIds(child);
         for (Long cid : cids) {
           ids.add(cid);
         }
         ids.add(child.getId());
       }
     }
     return ids;
   }
 }


 
 
 