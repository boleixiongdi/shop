 package com.rt.shop.manage.admin.action;
 
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
import com.rt.shop.entity.Address;
import com.rt.shop.entity.Article;
import com.rt.shop.entity.ArticleClass;
import com.rt.shop.entity.query.ArticleQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IArticleClassService;
import com.rt.shop.service.IArticleService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class ArticleManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IArticleService articleService;
 
   @Autowired
   private IArticleClassService articleClassService;
 
   @SecurityMapping(display = false, rsequence = 0, title="文章列表", value="/admin/article_list.htm*", rtype="admin", rname="文章管理", rcode="article", rgroup="网站")
   @RequestMapping({"/admin/article_list.htm"})
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView("admin/blue/article_list.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     String params = "";
     ArticleQueryObject qo = new ArticleQueryObject(currentPage, mv, 
       orderBy, orderType);
     WebForm wf = new WebForm();
     wf.toQueryPo(request, qo, Article.class, mv);
     Page pList = this.articleService.selectPage(new Page<Article>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
  
     CommWebUtil.saveIPageList2ModelAndView(url + "/admin/article_list.htm", 
       "", params, pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="文章添加", value="/admin/article_add.htm*", rtype="admin", rname="文章管理", rcode="article", rgroup="网站")
   @RequestMapping({"/admin/article_add.htm"})
   public ModelAndView add(HttpServletRequest request, HttpServletResponse response, String currentPage, String class_id)
   {
     ModelAndView mv = new JModelAndView("admin/blue/article_add.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     ArticleClass sArticleClass=new ArticleClass();
     sArticleClass.setParent_id(null);
     List acs = this.articleClassService.selectList(sArticleClass,"sequence asc");
     Article obj = new Article();
     obj.setDisplay(true);
     if ((class_id != null) && (!class_id.equals("")))
       obj.setArticleClass(this.articleClassService.selectById(
         Long.valueOf(Long.parseLong(class_id))));
     mv.addObject("obj", obj);
     mv.addObject("acs", acs);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="文章编辑", value="/admin/article_edit.htm*", rtype="admin", rname="文章管理", rcode="article", rgroup="网站")
   @RequestMapping({"/admin/article_edit.htm"})
   public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/article_add.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       Article article = this.articleService
         .selectById(Long.valueOf(Long.parseLong(id)));
       ArticleClass sArticleClass=new ArticleClass();
       sArticleClass.setParent_id(null);
       List acs = this.articleClassService.selectList(sArticleClass,"sequence asc");
       mv.addObject("acs", acs);
       mv.addObject("obj", article);
       mv.addObject("currentPage", currentPage);
       mv.addObject("edit", Boolean.valueOf(true));
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="文章保存", value="/admin/article_save.htm*", rtype="admin", rname="文章管理", rcode="article", rgroup="网站")
   @RequestMapping({"/admin/article_save.htm"})
   public ModelAndView save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String cmd, String list_url, String add_url, String class_id, String content)
   {
     WebForm wf = new WebForm();
     Article article = null;
     if (id.equals("")) {
       article = (Article)wf.toPo(request, Article.class);
       article.setAddTime(new Date());
     } else {
       Article obj = this.articleService.selectById(Long.valueOf(Long.parseLong(id)));
       article = (Article)wf.toPo(request, obj);
     }
     article.setArticleClass(this.articleClassService.selectById(
       Long.valueOf(Long.parseLong(class_id))));
     System.out.println(content);
     if (id.equals(""))
    	 this.articleService.insertSelective(article);
     //  this.articleService.insertSelective(article);
     else
       this.articleService.updateSelectiveById(article);
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     mv.addObject("list_url", list_url);
     mv.addObject("op_title", "保存文章成功");
     if (add_url != null) {
       mv.addObject("add_url", add_url + "?currentPage=" + currentPage + 
         "&class_id=" + class_id);
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="文章删除", value="/admin/article_del.htm*", rtype="admin", rname="文章管理", rcode="article", rgroup="网站")
   @RequestMapping({"/admin/article_del.htm"})
   public String delete(HttpServletRequest request, String mulitId) { String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         Article article = this.articleService.selectById(
           Long.valueOf(Long.parseLong(id)));
         this.articleService.deleteById(Long.valueOf(Long.parseLong(id)));
    //     this.articleService.deleteById(Long.valueOf(Long.parseLong(id)));
       }
     }
     return "redirect:article_list.htm"; }
 
   @SecurityMapping(display = false, rsequence = 0, title="文章AJAX更新", value="/admin/article_ajax.htm*", rtype="admin", rname="文章管理", rcode="article", rgroup="网站")
   @RequestMapping({"/admin/article_ajax.htm"})
   public void ajax(HttpServletRequest request, HttpServletResponse response, String id, String fieldName, String value) throws ClassNotFoundException
   {
     Article obj = this.articleService.selectById(Long.valueOf(Long.parseLong(id)));
     Field[] fields = Article.class.getDeclaredFields();
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
     this.articleService.updateSelectiveById(obj);
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
 
   @RequestMapping({"/admin/article_mark.htm"})
   public void article_mark(HttpServletRequest request, HttpServletResponse response, String mark, String id)
   {
     
     String sql="where mark='"+mark.trim()+"' and id!="+CommUtil.null2Long(id);
     List arts = this.articleService.selectList(null);
      // .query("select obj from Article obj where obj.mark=:mark and obj.id!=:id", 
     boolean ret = true;
     if (arts.size() > 0) {
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


 
 
 