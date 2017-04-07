 package com.rt.shop.view.web.action;
 
 import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.AdvPos;
import com.rt.shop.entity.Advert;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IAdvPosService;
import com.rt.shop.service.IAdvertService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
 
 @Controller
 public class AdvertViewAction
 {
 
	 @Autowired
	   private IAccessoryService accessoryService;
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IAdvPosService advertPositionService;
 
   @Autowired
   private IAdvertService advertService;
 
   @RequestMapping({"/advert_invoke.htm"})
   public ModelAndView advert_invoke(HttpServletRequest request, HttpServletResponse response, String id)
   {
     ModelAndView mv = new JModelAndView("advert_invoke.html", this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     
     String shopping_view_type = CommUtil.isMobileDeviceValue(request.getHeader("user-agent"));
     
	 if( (shopping_view_type != null) && (!shopping_view_type.equals( "" )) && (shopping_view_type.equals( "wap" )) ) {
		 mv = new JModelAndView("wap/advert_invoke.html", this.configService.getSysConfig(), 
			       this.userConfigService.getUserConfig(), 1, request, response);
	 }
	 
     if ((id != null) && (!id.equals(""))) {
    	 AdvPos ap = this.advertPositionService.selectById(CommUtil.null2Long(id));
       if (ap != null) {
    	   AdvPos obj = new AdvPos();
         obj.setAp_type(ap.getAp_type());
         obj.setAp_status(ap.getAp_status());
         obj.setAp_show_type(ap.getAp_show_type());
         obj.setAp_width(ap.getAp_width());
         obj.setAp_height(ap.getAp_height());
         List<Advert> advs = new ArrayList();
         Advert sAdvert=new Advert();
         sAdvert.setAd_ap_id(CommUtil.null2Long(id));
         List<Advert> childs=advertService.selectList(sAdvert);
         for (Advert temp_adv : childs) {
           if ((temp_adv.getAd_status() != 1) || 
             (!temp_adv.getAd_begin_time().before(new Date())) || 
             (!temp_adv.getAd_end_time().after(new Date()))) continue;
           Accessory ac=accessoryService.selectById(temp_adv.getAd_acc_id());
           temp_adv.setAd_acc(ac);
           advs.add(temp_adv);
         }
 
         if (advs.size() > 0) {
           if (obj.getAp_type().equals("text")) {
             if (obj.getAp_show_type() == 0) {
               obj.setAp_text(((Advert)advs.get(0)).getAd_text());
               obj.setAp_acc_url(((Advert)advs.get(0)).getAd_url());
               obj.setAdv_id(CommUtil.null2String(((Advert)advs.get(0)).getId()));
             }
             if (obj.getAp_show_type() == 1) {
               Random random = new Random();
               int i = random.nextInt(advs.size());
               obj.setAp_text(((Advert)advs.get(i)).getAd_text());
               obj.setAp_acc_url(((Advert)advs.get(i)).getAd_url());
               obj.setAdv_id(CommUtil.null2String(((Advert)advs.get(i)).getId()));
             }
           }
           if (obj.getAp_type().equals("img")) {
             if (obj.getAp_show_type() == 0) {
            	 obj.setAp_acc_id(advs.get(0).getAd_acc_id());
            	 Accessory ac=accessoryService.selectById(advs.get(0).getAd_acc_id());
            	 obj.setAp_acc(ac);
               obj.setAp_acc_url(((Advert)advs.get(0)).getAd_url());
               obj.setAdv_id(CommUtil.null2String(((Advert)advs.get(0)).getId()));
             }
             if (obj.getAp_show_type() == 1) {
               Random random = new Random();
               int i = random.nextInt(advs.size());
               Accessory ac=accessoryService.selectById(advs.get(i).getAd_acc_id());
               obj.setAp_acc(ac);
               obj.setAp_acc_id(advs.get(i).getAd_acc_id());
               obj.setAp_acc_url(((Advert)advs.get(i)).getAd_url());
               obj.setAdv_id(CommUtil.null2String(((Advert)advs.get(i)).getId()));
             }
           }
           Iterator localIterator2;
           if (obj.getAp_type().equals("slide")) {
             if (obj.getAp_show_type() == 0) {
               obj.setAdvs(advs);
             }
             if (obj.getAp_show_type() == 1) {
               Random random = new Random();
               Set list = CommUtil.randomInt(advs.size(), 8);
               for (localIterator2 = list.iterator(); localIterator2.hasNext(); ) { 
            	   int i = ((Integer)localIterator2.next()).intValue();
            	   obj.getAdvs().add((Advert)advs.get(i));
               }
             }
           }
           if (obj.getAp_type().equals("wapslide")) {
               if (obj.getAp_show_type() == 0) {
                 obj.setAdvs(advs);
               }
               if (obj.getAp_show_type() == 1) {
                 Random random = new Random();
                 Set list = CommUtil.randomInt(advs.size(), 8);
                 for (localIterator2 = list.iterator(); localIterator2.hasNext(); ) { 
                	 int i = ((Integer)localIterator2.next()).intValue();
                	 obj.getAdvs().add((Advert)advs.get(i));
                 }
               }
           }
           if (obj.getAp_type().equals("indexslide")) {
               if (obj.getAp_show_type() == 0) {
                 obj.setAdvs(advs);
               }
               if (obj.getAp_show_type() == 1) {
                 Random random = new Random();
                 Set list = CommUtil.randomInt(advs.size(), 8);
                 for (localIterator2 = list.iterator(); localIterator2.hasNext(); ) { 
                	 int i = ((Integer)localIterator2.next()).intValue();
                	 obj.getAdvs().add((Advert)advs.get(i));
                 }
               }
           }
           if (obj.getAp_type().equals("scroll")) {
             if (obj.getAp_show_type() == 0) {
               obj.setAdvs(advs);
             }
             if (obj.getAp_show_type() == 1) {
               Random random = new Random();
               Set list = CommUtil.randomInt(advs.size(), 12);
               for (localIterator2 = list.iterator(); localIterator2.hasNext(); ) { 
            	   int i = ((Integer)localIterator2.next()).intValue();
            	   obj.getAdvs().add((Advert)advs.get(i)); }
             }
           }
         }
         else {
        	 obj.setAp_acc_id(ap.getAp_acc_id());	
         //  obj.setAp_acc(ap.getAp_acc());
           obj.setAp_text(ap.getAp_text());
           obj.setAp_acc_url(ap.getAp_acc_url());
           Advert adv = new Advert();
           adv.setAd_url(obj.getAp_acc_url());
         //  adv.setAd_acc(ap.getAp_acc());
           adv.setAd_acc_id(ap.getAp_acc_id());
           obj.getAdvs().add(adv);
         }
         
         if (obj.getAp_status()==1){
           mv.addObject("obj", obj);
         }
         else {
           mv.addObject("obj", new AdvPos());
         }
       }
     }
     return mv;
   }
 
   @RequestMapping({"/advert_redirect.htm"})
   public void advert_redirect(HttpServletRequest request, HttpServletResponse response, String id)
   {
     try
     {
       Advert adv = this.advertService.selectById(CommUtil.null2Long(id));
       if (adv != null) {
         adv.setAd_click_num(adv.getAd_click_num() + 1);
         this.advertService.updateSelectiveById(adv);
       }
       if (adv != null) {
         String url = adv.getAd_url();
         response.sendRedirect(url);
       } else {
         response.sendRedirect(CommUtil.getURL(request));
       }
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
   
   
 }


 
 
 