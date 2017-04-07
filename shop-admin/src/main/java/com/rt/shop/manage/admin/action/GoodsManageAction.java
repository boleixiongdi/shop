 package com.rt.shop.manage.admin.action;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
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
import com.rt.shop.entity.Evaluate;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.GoodsBrand;
import com.rt.shop.entity.GoodsCart;
import com.rt.shop.entity.GoodsClass;
import com.rt.shop.entity.Message;
import com.rt.shop.entity.OrderForm;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.Template;
import com.rt.shop.entity.User;
import com.rt.shop.entity.query.GoodsQueryObject;
import com.rt.shop.lucene.LuceneUtil;
import com.rt.shop.lucene.LuceneVo;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IEvaluateService;
import com.rt.shop.service.IGoodsBrandService;
import com.rt.shop.service.IGoodsCartService;
import com.rt.shop.service.IGoodsClassService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IMessageService;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.IOrderLogService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.ITemplateService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.tools.MsgTools;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class GoodsManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IGoodsBrandService goodsBrandService;
 
   @Autowired
   private IGoodsClassService goodsClassService;
 
   @Autowired
   private ITemplateService templateService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IMessageService messageService;
   @Autowired
   private IStoreService storeService;
 
   @Autowired
   private MsgTools msgTools;
 
 //  @Autowired
  // private DatabaseTools databaseTools;
 
   @Autowired
   private IEvaluateService evaluateService;
 
   @Autowired
   private IGoodsCartService goodsCartService;
 
   @Autowired
   private IOrderFormService orderFormService;
 
   @Autowired
   private IOrderLogService orderFormLogService;
 
   @SecurityMapping(display = false, rsequence = 0, title="商品列表", value="/admin/goods_list.htm*", rtype="admin", rname="商品管理", rcode="admin_goods", rgroup="商品")
   @RequestMapping({"/admin/goods_list.htm"})
   public ModelAndView goods_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView("admin/blue/goods_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     String params = "";
     GoodsQueryObject qo = new GoodsQueryObject(currentPage, mv, orderBy, 
       orderType);
     WebForm wf = new WebForm();
     wf.toQueryPo(request, qo, Goods.class, mv);
     qo.addQuery("obj.goods_status", new SysMap("goods_status", Integer.valueOf(-2)), ">");
     Page pList = this.goodsService.selectPage(new Page<Goods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView(url + "/admin/goods_list.htm", "", 
       params, pList, mv);
     List gbs = this.goodsBrandService.selectList(new GoodsBrand(), "sequence asc");
     GoodsClass sGoodsClass=new GoodsClass();
     sGoodsClass.setParent_id(null);
     List gcs = this.goodsClassService.selectList(sGoodsClass,"sequence asc");
     
     mv.addObject("gcs", gcs);
     mv.addObject("gbs", gbs);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="违规商品列表", value="/admin/goods_outline.htm*", rtype="admin", rname="商品管理", rcode="admin_goods", rgroup="商品")
   @RequestMapping({"/admin/goods_outline.htm"})
   public ModelAndView goods_outline(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType) {
     ModelAndView mv = new JModelAndView("admin/blue/goods_outline.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     String params = "";
     GoodsQueryObject qo = new GoodsQueryObject(currentPage, mv, orderBy, 
       orderType);
     WebForm wf = new WebForm();
     wf.toQueryPo(request, qo, Goods.class, mv);
     qo.addQuery("obj.goods_status", new SysMap("goods_status", Integer.valueOf(-2)), "=");
     Page pList = this.goodsService.selectPage(new Page<Goods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView(url + "/admin/goods_list.htm", "", 
       params, pList, mv);
     List gbs = this.goodsBrandService.selectList(new GoodsBrand(), "sequence asc");
     GoodsClass sGoodsClass=new GoodsClass();
     sGoodsClass.setParent_id(null);
     List gcs = this.goodsClassService.selectList(sGoodsClass,"sequence asc");
      
     mv.addObject("gcs", gcs);
     mv.addObject("gbs", gbs);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="商品添加", value="/admin/goods_add.htm*", rtype="admin", rname="商品管理", rcode="admin_goods", rgroup="商品")
   @RequestMapping({"/admin/goods_add.htm"})
   public ModelAndView goods_add(HttpServletRequest request, HttpServletResponse response, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/goods_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="商品编辑", value="/admin/goods_edit.htm*", rtype="admin", rname="商品管理", rcode="admin_goods", rgroup="商品")
   @RequestMapping({"/admin/goods_edit.htm"})
   public ModelAndView goods_edit(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/goods_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       Goods goods = this.goodsService.selectById(Long.valueOf(Long.parseLong(id)));
       mv.addObject("obj", goods);
       mv.addObject("currentPage", currentPage);
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="商品保存", value="/admin/goods_save.htm*", rtype="admin", rname="商品管理", rcode="admin_goods", rgroup="商品")
   @RequestMapping({"/admin/goods_save.htm"})
   public ModelAndView goods_save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String cmd, String list_url, String add_url)
   {
     WebForm wf = new WebForm();
     Goods goods = null;
     if (id.equals("")) {
       goods = (Goods)wf.toPo(request, Goods.class);
       goods.setAddTime(new Date());
     } else {
       Goods obj = this.goodsService.selectById(Long.valueOf(Long.parseLong(id)));
       goods = (Goods)wf.toPo(request, obj);
     }
     if (id.equals(""))
       this.goodsService.insertSelective(goods);
     else
       this.goodsService.updateSelectiveById(goods);
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("list_url", list_url);
     mv.addObject("op_title", "保存商品成功");
     if (add_url != null) {
       mv.addObject("add_url", add_url + "?currentPage=" + currentPage);
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="商品删除", value="/admin/goods_del.htm*", rtype="admin", rname="商品管理", rcode="admin_goods", rgroup="商品")
   @RequestMapping({"/admin/goods_del.htm"})
   public String goods_del(HttpServletRequest request, String mulitId) throws Exception {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         Goods goods = this.goodsService.selectById(
           CommUtil.null2Long(id));
         Map map = new HashMap();
         map.put("gid", goods.getId());
         GoodsCart sGoodsCart=new GoodsCart();
         sGoodsCart.setGoods_id(goods.getId());
         List<GoodsCart> goodCarts = this.goodsCartService.selectList(sGoodsCart);
          
         Long ofid = null;
         Long of_id;
         for (GoodsCart gc : goodCarts) {
           of_id = gc.getOf().getId();
           this.goodsCartService.deleteById(gc.getId());
           OrderForm of = this.orderFormService.selectById(of_id);
           GoodsCart gc1=new GoodsCart();
           gc1.setOf_id(of.getId());
           List<GoodsCart> gcList=goodsCartService.selectList(gc1);
           if (gcList.size() == 0) {
             this.orderFormService.deleteById(of_id);
           }
         }
 
         List<Evaluate> evaluates = goods.getEvaluates();
         for (Evaluate e : evaluates) {
           this.evaluateService.deleteById(e.getId());
         }
         goods.getGoods_ugcs().clear();
         goods.getGoods_ugcs().clear();
         goods.getGoods_photos().clear();
         goods.getGoods_ugcs().clear();
         goods.getGoods_specs().clear();
         this.goodsService.deleteById(goods.getId());
 
         String goods_lucene_path = System.getProperty("user.dir") + 
           File.separator + "luence" + File.separator + "goods";
         File file = new File(goods_lucene_path);
         if (!file.exists()) {
           CommUtil.createFolder(goods_lucene_path);
         }
         LuceneUtil lucene = LuceneUtil.instance();
         LuceneUtil.setIndex_path(goods_lucene_path);
         lucene.delete_index(CommUtil.null2String(id));
         Store store=storeService.selectById(goods.getGoods_store_id());
         				User sUser=new User();
						sUser.setStore_id(store.getId());
						User storeUser=userService.selectOne(sUser);
         send_site_msg(request, 
           "msg_toseller_goods_delete_by_admin_notify", storeUser, goods, "商城存在违规");
       }
     }
     return "redirect:goods_list.htm";
   }
 
   private void send_site_msg(HttpServletRequest request, String mark, User user, Goods goods, String reason) throws Exception
   {
    Template template = this.templateService.selectByMark(mark);
     if (template.getOpen()) {
       String path = request.getSession().getServletContext().getRealPath("/") + "/vm/";
       PrintWriter pwrite = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path + "msg.vm", false), "UTF-8"));
       pwrite.print(template.getContent());
       pwrite.flush();
       pwrite.close();
 
       Properties p = new Properties();
       p.setProperty("file.resource.loader.path", request.getRealPath("/") + "vm" + File.separator);
       p.setProperty("input.encoding", "UTF-8");
       p.setProperty("output.encoding", "UTF-8");
       Velocity.init(p);
       org.apache.velocity.Template blank = Velocity.getTemplate("msg.vm", 
         "UTF-8");
       VelocityContext context = new VelocityContext();
       context.put("reason", reason);
       context.put("user", user);
       context.put("config", this.configService.getSysConfig());
       context.put("send_time", CommUtil.formatLongDate(new Date()));
       StringWriter writer = new StringWriter();
       blank.merge(context, writer);
 
       String content = writer.toString();
       User fromUser = this.userService.selectUserByUsername("admin");
       Message msg = new Message();
       msg.setAddTime(new Date());
       msg.setContent(content);
       msg.setFromUser_id(fromUser.getId());
       msg.setTitle(template.getTitle());
       msg.setToUser_id(user.getId());
       msg.setType(0);
       this.messageService.insertSelective(msg);
       CommUtil.deleteFile(path + "temp.vm");
       writer.flush();
       writer.close();
     }
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="商品AJAX更新", value="/admin/goods_ajax.htm*", rtype="admin", rname="商品管理", rcode="admin_goods", rgroup="商品")
   @RequestMapping({"/admin/goods_ajax.htm"})
   public void ajax(HttpServletRequest request, HttpServletResponse response, String id, String fieldName, String value) throws ClassNotFoundException {
     Goods obj = this.goodsService.selectById(Long.valueOf(Long.parseLong(id)));
     Field[] fields = Goods.class.getDeclaredFields();
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
     if (fieldName.equals("store_recommend")) {
       if (obj.getStore_recommend())
         obj.setStore_recommend_time(new Date());
       else
         obj.setStore_recommend_time(null);
     }
     this.goodsService.updateSelectiveById(obj);
     if (obj.getGoods_status() == 0)
     {
       String goods_lucene_path = System.getProperty("user.dir") + 
         File.separator + "luence" + File.separator + "goods";
       File file = new File(goods_lucene_path);
       if (!file.exists()) {
         CommUtil.createFolder(goods_lucene_path);
       }
       LuceneVo vo = new LuceneVo();
       vo.setVo_id(obj.getId());
       vo.setVo_title(obj.getGoods_name());
       vo.setVo_content(obj.getGoods_details());
       vo.setVo_type("goods");
       vo.setVo_store_price(CommUtil.null2Double(obj.getStore_price()));
       vo.setVo_add_time(obj.getAddTime().getTime());
       vo.setVo_goods_salenum(obj.getGoods_salenum());
       LuceneUtil lucene = LuceneUtil.instance();
       LuceneUtil.setIndex_path(goods_lucene_path);
       lucene.update(CommUtil.null2String(obj.getId()), vo);
     } else {
       String goods_lucene_path = System.getProperty("user.dir") + 
         File.separator + "luence" + File.separator + "goods";
       File file = new File(goods_lucene_path);
       if (!file.exists()) {
         CommUtil.createFolder(goods_lucene_path);
       }
       LuceneUtil lucene = LuceneUtil.instance();
       LuceneUtil.setIndex_path(goods_lucene_path);
       lucene.delete_index(CommUtil.null2String(id));
     }
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
 }


 
 
 