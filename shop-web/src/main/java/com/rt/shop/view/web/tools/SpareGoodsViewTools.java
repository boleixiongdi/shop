 package com.rt.shop.view.web.tools;
 
 import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.shop.entity.SpareGoodsClass;
import com.rt.shop.entity.SpareGoodsFloor;
import com.rt.shop.service.ISpareGoodsClassService;
 
 @Component
 public class SpareGoodsViewTools
 {
 
   @Autowired
   private ISpareGoodsClassService sgcService;
 
   public List<SpareGoodsClass> query_childclass(SpareGoodsClass sgc)
   {
     List list = new ArrayList();
     SpareGoodsClass sSpareGoodsClass=new SpareGoodsClass();
     sSpareGoodsClass.setParent_id(sgc.getId());
     List<SpareGoodsClass> child1List=sgcService.selectList(sSpareGoodsClass);
     if(child1List!=null && child1List.size()>0){
    	 for (SpareGoodsClass child : child1List) {
    	       list.add(child);
    	       SpareGoodsClass sSpareGoodsClass1=new SpareGoodsClass();
    	       sSpareGoodsClass1.setParent_id(child.getId());
    	       List<SpareGoodsClass> child2List=sgcService.selectList(sSpareGoodsClass1);
    	       for (SpareGoodsClass c : child2List) {
    	         list.add(c);
    	       }
    	     }
     }
     
     return list;
   }
 
   public List<SpareGoodsClass> query_floorClass(SpareGoodsFloor sgf)
   {
     List list = new ArrayList();
     SpareGoodsClass sgcP=sgcService.selectById(sgf.getSgc_id());
     
     SpareGoodsClass sSpareGoodsClass=new SpareGoodsClass();
     sSpareGoodsClass.setParent_id(sgcP.getId());
     List<SpareGoodsClass> child1List=sgcService.selectList(sSpareGoodsClass);
     for (SpareGoodsClass child : child1List) {
       if (child.getViewInFloor()) {
         list.add(child);
       }
       SpareGoodsClass sSpareGoodsClass1=new SpareGoodsClass();
       sSpareGoodsClass1.setParent_id(child.getId());
       List<SpareGoodsClass> child2List=sgcService.selectList(sSpareGoodsClass1);
       for (SpareGoodsClass c : child2List) {
         if (c.getViewInFloor()) {
           list.add(c);
         }
       }
     }
 
     return list;
   }
 
   public String ClearContent(String inputString)
   {
     String htmlStr = inputString;
     String textStr = "";
     try
     {
       String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>";
       String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>";
       String regEx_html = "<[^>]+>";
       String regEx_html1 = "<[^>]+";
       Pattern p_script = Pattern.compile(regEx_script, 2);
       Matcher m_script = p_script.matcher(htmlStr);
       htmlStr = m_script.replaceAll("");
 
       Pattern p_style = Pattern.compile(regEx_style, 2);
       Matcher m_style = p_style.matcher(htmlStr);
       htmlStr = m_style.replaceAll("");
 
       Pattern p_html = Pattern.compile(regEx_html, 2);
       Matcher m_html = p_html.matcher(htmlStr);
       htmlStr = m_html.replaceAll("");
 
       Pattern p_html1 = Pattern.compile(regEx_html1, 2);
       Matcher m_html1 = p_html1.matcher(htmlStr);
       htmlStr = m_html1.replaceAll("");
 
       textStr = htmlStr;
     } catch (Exception e) {
       System.err.println("Html2Text: " + e.getMessage());
     }
     return textStr;
   }
 }


 
 
 