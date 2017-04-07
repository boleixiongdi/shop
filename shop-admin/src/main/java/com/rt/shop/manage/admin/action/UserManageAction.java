 package com.rt.shop.manage.admin.action;
 
 import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.common.tools.Md5Encrypt;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Album;
import com.rt.shop.entity.Evaluate;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.GoodsCart;
import com.rt.shop.entity.Message;
import com.rt.shop.entity.Role;
import com.rt.shop.entity.StoreGrade;
import com.rt.shop.entity.SysConfig;
import com.rt.shop.entity.User;
import com.rt.shop.entity.UserRole;
import com.rt.shop.entity.query.UserQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAlbumService;
import com.rt.shop.service.IEvaluateService;
import com.rt.shop.service.IGoodsCartService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IMessageService;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.IOrderLogService;
import com.rt.shop.service.IPredepositService;
import com.rt.shop.service.IRoleService;
import com.rt.shop.service.IStoreGradeService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserRoleService;
import com.rt.shop.service.IUserService;
import com.rt.shop.tools.StoreTools;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class UserManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
   @Autowired
   private IUserRoleService userRoleService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IRoleService roleService;
 
   @Autowired
   private IStoreGradeService storeGradeService;
 
   @Autowired
   private IMessageService messageService;
 
   @Autowired
   private IAlbumService albumService;
 
   @Autowired
   private IPredepositService predepositService;
 
   @Autowired
   private IEvaluateService evaluateService;
 
   @Autowired
   private IGoodsCartService goodsCartService;
 
   @Autowired
   private IOrderFormService orderFormService;
 
   @Autowired
   private IOrderLogService orderFormLogService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private StoreTools storeTools;
 
   @SecurityMapping(display = false, rsequence = 0, title="会员添加", value="/admin/user_add.htm*", rtype="admin", rname="会员管理", rcode="user_manage", rgroup="会员")
   @RequestMapping({"/admin/user_add.htm"})
   public ModelAndView user_add(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("admin/blue/user_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="会员编辑", value="/admin/user_edit.htm*", rtype="admin", rname="会员管理", rcode="user_manage", rgroup="会员")
   @RequestMapping({"/admin/user_edit.htm"})
   public ModelAndView user_edit(HttpServletRequest request, HttpServletResponse response, String id, String op) {
     ModelAndView mv = new JModelAndView("admin/blue/user_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("obj", this.userService.selectById(Long.valueOf(Long.parseLong(id))));
     mv.addObject("edit", Boolean.valueOf(true));
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="会员列表", value="/admin/user_list.htm*", rtype="admin", rname="会员管理", rcode="user_manage", rgroup="会员")
   @RequestMapping({"/admin/user_list.htm"})
   public ModelAndView user_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String condition, String value) {
     ModelAndView mv = new JModelAndView("admin/blue/user_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     UserQueryObject uqo = new UserQueryObject(currentPage, mv, orderBy, 
       orderType);
     WebForm wf = new WebForm();
     wf.toQueryPo(request, uqo, User.class, mv);
     uqo.addQuery("obj.userRole", new SysMap("userRole", "ADMIN"), "!=");
     if (condition != null) {
       if (condition.equals("userName")) {
         uqo
           .addQuery("obj.userName", 
           new SysMap("userName", value), "=");
       }
       if (condition.equals("email")) {
         uqo.addQuery("obj.email", new SysMap("email", value), "=");
       }
       if (condition.equals("trueName")) {
         uqo
           .addQuery("obj.trueName", 
           new SysMap("trueName", value), "=");
       }
     }
     uqo.addQuery("obj.parent.id is null", null);
     Page pList = this.userService.selectPage(new Page<User>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     CommWebUtil.saveIPageList2ModelAndView(url + "/admin/user_list.htm", "", 
       "", pList, mv);
     mv.addObject("userRole", "USER");
     mv.addObject("storeTools", this.storeTools);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="会员保存", value="/admin/user_save.htm*", rtype="admin", rname="会员管理", rcode="user_manage", rgroup="会员")
   @RequestMapping({"/admin/user_save.htm"})
   public ModelAndView user_save(HttpServletRequest request, HttpServletResponse response,User user, String id, String role_ids, String list_url, String add_url, String password) {
     
  
     if (id.equals("")) {
     
       user.setAddTime(new Date());
     } else {
    	 user.setId(Long.parseLong(id));
      //  user = this.userService.selectById(Long.valueOf(Long.parseLong(id)));
     }
     if ((password != null) && (!password.equals(""))) {
       user.setPassword(Md5Encrypt.md5(user.getUserName()+password).toLowerCase());
     }
     if (id.equals("")) {
       user.setUserRole("BUYER");
       user.getRoles().clear();
      
       Role sRole=new Role();
       sRole.setType("BUYER");
       this.userService.insertSelective(user);
       
       List<Role> roles = this.roleService.selectList(sRole);
       user.setRoles(roles);
       List<UserRole> urList=new ArrayList<UserRole>();
     
         for(Role role : roles){
        	UserRole ur=new UserRole(user.getId(),role.getId()); 
        	urList.add(ur);
         }
         userService.insertBatchUserRole(urList);
       
      
 
       Album album = new Album();
       album.setAddTime(new Date());
       album.setAlbum_default(true);
       album.setAlbum_name("默认相册");
       album.setAlbum_sequence(-10000);
       album.setUser_id(user.getId());
       this.albumService.insertSelective(album);
     } else {
       this.userService.updateSelectiveById(user);
     }
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("list_url", list_url);
     mv.addObject("op_title", "保存用户成功");
     if (add_url != null) {
       mv.addObject("add_url", add_url);
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="会员删除", value="/admin/user_del.htm*", rtype="admin", rname="会员管理", rcode="user_manage", rgroup="会员")
   @RequestMapping({"/admin/user_del.htm"})
   public String user_del(HttpServletRequest request, String mulitId, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         User parent = this.userService.selectById(Long.valueOf(Long.parseLong(id)));
         if (!parent.getUserName().equals("admin"))
         {
           Long ofid;
           User sUser=new User();
           sUser.setParent_id(parent.getId());
           List<User> childs=userService.selectList(sUser);
           for (User user : childs) {
             user.getRoles().clear();
             
             if (user.getStore_id() != null) {
            	List<Goods> goodsList=goodsService.selectGoodsByStore(user.getStore_id());
               for (Goods goods : goodsList) {
                
                 GoodsCart sGoodsCart=new GoodsCart();
                 sGoodsCart.setGoods_id(goods.getId());
                 List<GoodsCart> goodCarts = this.goodsCartService.selectList(sGoodsCart);
                 ofid = null;
                 Map map2;
                 for (GoodsCart gc : goodCarts) {
                   this.goodsCartService.deleteById(gc.getId());
                   GoodsCart ssGoodsCart=new GoodsCart();
                   ssGoodsCart.setOf_id(gc.getOf_id());
                   List goodCarts2 = this.goodsCartService.selectList(ssGoodsCart);
                   if (goodCarts2.size() == 0) {
                     this.orderFormService.deleteById(ofid);
                   }
                 }
                 List<Evaluate> evaluates = goods.getEvaluates();
                 for (Evaluate e : evaluates) {
                   this.evaluateService.deleteById(e.getId());
                 }
                 goods.getGoods_ugcs().clear();
                 this.goodsService.deleteById(goods.getId());
               }
             }
             this.userService.deleteById(user.getId());
           }
           
           if (parent.getStore_id() != null) {
        	   List<Goods> goodsList=goodsService.selectGoodsByStore(parent.getStore_id());
             for (Goods goods : goodsList) {
               GoodsCart ssGoodsCart=new GoodsCart();
               ssGoodsCart.setGoods_id(goods.getId());
               List<GoodsCart> goodCarts = this.goodsCartService.selectList(ssGoodsCart);
               Long ofid1 = null;
               Map map2;
               for (GoodsCart gc : goodCarts) {
                 this.goodsCartService.deleteById(gc.getId());
                 GoodsCart sssGoodsCart=new GoodsCart();
                 sssGoodsCart.setOf_id(gc.getOf_id());
                 List goodCarts2 = this.goodsCartService.selectList(sssGoodsCart);
                 if (goodCarts2.size() == 0) {
                   this.orderFormService.deleteById(ofid1);
                 }
               }
 
               List<Evaluate> evaluates = goods.getEvaluates();
               for (Evaluate e : evaluates) {
                 this.evaluateService.deleteById(e.getId());
               }
               goods.getGoods_ugcs().clear();
               this.goodsService.deleteById(goods.getId());
             }
           }
           this.userService.deleteById(parent.getId());
         }
       }
     }
     return "redirect:user_list.htm?currentPage=" + currentPage;
   }
   @SecurityMapping(display = false, rsequence = 0, title="会员通知", value="/admin/user_msg.htm*", rtype="admin", rname="会员通知", rcode="user_msg", rgroup="会员")
   @RequestMapping({"/admin/user_msg.htm"})
   public ModelAndView user_msg(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView("admin/blue/user_msg.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     List grades = this.storeGradeService.selectList(new StoreGrade(), "sequence asc");
     mv.addObject("grades", grades);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="会员通知发送", value="/admin/user_msg_send.htm*", rtype="admin", rname="会员通知", rcode="user_msg", rgroup="会员")
   @RequestMapping({"/admin/user_msg_send.htm"})
   public ModelAndView user_msg_send(HttpServletRequest request, HttpServletResponse response, String type, String list_url, String users, String grades, String content) throws IOException {
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     List<User> user_list = new ArrayList();
     if (type.equals("all_user")) {
       
       String sql="where obj.userRole!='ADMIN' order by obj.addTime desc";
       user_list = this.userService.selectList(sql,null);

     }
     User user;
     if (type.equals("the_user")) {
       List<String> user_names = CommUtil.str2list(users);
       for (String user_name : user_names) {
         user = this.userService.selectUserByUsername(user_name);
         user_list.add(user);
       }
     }
     if (type.equals("all_store")){
    	
    	 User sUser=new User();
        sUser.setStore_id(null);
       user_list = this.userService.selectList(sUser,"addTime desc");
     }
     Set store_ids;
     if (type.equals("the_store")) {
       Map params = new HashMap();
       store_ids = new TreeSet();
       String[] arrayOfString= grades.split(",");
       int localUser1 = arrayOfString.length; 
				for (int i = 0; i < localUser1; i++) { String grade = arrayOfString[i];
			         store_ids.add(Long.valueOf(Long.parseLong(grade)));
			       }
				if(store_ids!=null && store_ids.size()>0){
					String x=CommUtil.exactSetToString(store_ids);
				       String sql="where store_id in ("+x+") ";
				       user_list = this.userService.selectList(sql,null);

				     for (User user1 : user_list) {
				       Message msg = new Message();
				       msg.setAddTime(new Date());
				       msg.setContent(content);
				       msg.setFromUser_id(SecurityUserHolder.getCurrentUser().getId());
				       msg.setToUser_id(user1.getId());
				       this.messageService.insertSelective(msg);
				     }
				}
     }
     mv.addObject("op_title", "会员通知发送成功");
     mv.addObject("list_url", list_url);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="会员信用", value="/admin/user_creditrule.htm*", rtype="admin", rname="会员信用", rcode="user_creditrule", rgroup="会员")
   @RequestMapping({"/admin/user_creditrule.htm"})
   public ModelAndView user_creditrule(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView("admin/blue/user_creditrule.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="买家信用保存", value="/admin/user_creditrule_save.htm*", rtype="admin", rname="会员信用", rcode="user_creditrule", rgroup="会员")
   @RequestMapping({"/admin/user_creditrule_save.htm"})
   public ModelAndView user_creditrule_save(HttpServletRequest request, HttpServletResponse response, String id, String list_url) {
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     SysConfig sc = this.configService.getSysConfig();
     Map map = new HashMap();
     for (int i = 0; i <= 29; i++) {
       map.put("creditrule" + i, Integer.valueOf(CommUtil.null2Int(request
         .getParameter("creditrule" + i))));
     }
     String user_creditrule = Json.toJson(map, JsonFormat.compact());
     sc.setUser_creditrule(user_creditrule);
     if (id.equals(""))
       this.configService.insertSelective(sc);
     else
       this.configService.updateSelectiveById(sc);
     mv.addObject("list_url", list_url);
     mv.addObject("op_title", "保存会员信用成功");
     return mv;
   }
 }


 
 
 