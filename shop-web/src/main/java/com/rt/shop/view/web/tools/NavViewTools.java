 package com.rt.shop.view.web.tools;
 
 import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.entity.Article;
import com.rt.shop.entity.Navigation;
import com.rt.shop.service.IActivityService;
import com.rt.shop.service.IArticleService;
import com.rt.shop.service.IGoodsClassService;
import com.rt.shop.service.INavigationService;
 
 @Component
 public class NavViewTools
 {
 
   @Autowired
   private INavigationService navService;
 
   @Autowired
   private IArticleService articleService;
 
   @Autowired
   private IActivityService activityService;
 
   @Autowired
   private IGoodsClassService goodsClassService;
 
   public List<Navigation> queryNav(int location, int count)
   {
     List navs = new ArrayList();
    String sql="where display=1 and location='"+location+"' and type!='sparegoods' order by sequence asc";
     navs = this.navService.selectList(sql, null);
     return navs;
   }
 }


 
 
 