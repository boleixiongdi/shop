 package com.rt.shop.view.web.action;
 
 import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rt.shop.entity.Document;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IDocumentService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
 
 @Controller
 public class DocumentViewAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IDocumentService documentService;
 
   @RequestMapping({"/doc.htm"})
   public ModelAndView doc(HttpServletRequest request, HttpServletResponse response, String mark)
   {
     ModelAndView mv = new JModelAndView("doc.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
     Document sDocument=new Document();
     sDocument.setMark(mark);
     Document obj = this.documentService.selectOne(sDocument);
     mv.addObject("obj", obj);
     return mv;
   }
 }


 
 
 