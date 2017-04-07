 package com.rt.shop.view.web.action;
 
 import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.Article;
import com.rt.shop.entity.ArticleClass;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IArticleClassService;
import com.rt.shop.service.IArticleService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
import com.rt.shop.view.web.tools.ArticleViewTools;
 
 @Controller
 public class ArticleViewAction extends BaseController
 
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IArticleService articleService;
 
   @Autowired
   private IArticleClassService articleClassService;
 
   @Autowired
   private ArticleViewTools articleTools;
 
   @RequestMapping({"/articlelist.htm"})
   public ModelAndView articlelist(HttpServletRequest request, HttpServletResponse response, String param, String currentPage)
   {
     ModelAndView mv = new JModelAndView("articlelist.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
     ArticleClass ac = null;
     Article lArticle=new Article();
     Page<Article> page = getPage();
    // aqo.setCurrentPage(Integer.valueOf(CommUtil.null2Int(currentPage)));
     Long id = CommUtil.null2Long(param);
     String mark = "";
     if (id.longValue() == -1L) {
       mark = param;
     }
     if (!mark.equals("")) {
//       aqo.addQuery("obj.articleClass.mark", 
//         new SysMap("mark", mark), "=");
       ArticleClass sArticleClass=new ArticleClass();
       sArticleClass.setMark(mark);
       ac = this.articleClassService.selectOne(sArticleClass);
     }
     if (id.longValue() != -1L) {
    //   aqo.addQuery("obj.articleClass.id", new SysMap("id", id), "=");
       ac = this.articleClassService.selectById(id);
     }
    
     lArticle.setDisplay(Boolean.valueOf(true));
     Page pList = this.articleService.selectPage(new Page<Article>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     
     String url = CommUtil.getURL(request) + "/articlelist_" + ac.getId();
     CommWebUtil.saveIPageList2ModelAndView("", url, "", pList, mv);
    
     List acs = this.articleClassService.selectList("where parent_id is null", "sequence asc");
     Article sArticle=new Article();
     
     List<Article> articles = this.articleService.selectPage(new Page<Article>(0, 6), new Article(), "addTime desc").getRecords();
      
     mv.addObject("ac", ac);
     mv.addObject("articles", articles);
     mv.addObject("acs", acs);
     return mv;
   }
 
   @RequestMapping({"/article.htm"})
   public ModelAndView article(HttpServletRequest request, HttpServletResponse response, String param) {
     ModelAndView mv = new JModelAndView("article.html", this.configService.getSysConfig(), 
    		 this.userConfigService.getUserConfig(), 1, request, response);
     Article obj = null;
     Long id = CommUtil.null2Long(param);
     String mark = "";
     if (id.longValue() == -1L) {
       mark = param;
     }
     if (id.longValue() != -1L) {
       obj = this.articleService.selectById(id);
     }
     if (!mark.equals("")) {
    	 Article sArticle=new Article();
         sArticle.setMark(mark);
         obj = this.articleService.selectOne(sArticle);
     }
    
     List acs = this.articleClassService.selectList("where   parent_id is null", "sequence asc");
     //  "select obj from ArticleClass obj where obj.parent.id is null order by obj.sequence asc", 
     Article sArticle=new Article();
     
     List<Article> articles = this.articleService.selectPage(new Page<Article>(0, 6), new Article(), "addTime desc").getRecords();
     
     mv.addObject("articles", articles);
     mv.addObject("acs", acs);
     mv.addObject("obj", obj);
     mv.addObject("articleTools", this.articleTools);
     return mv;
   }
 }


 
 
 