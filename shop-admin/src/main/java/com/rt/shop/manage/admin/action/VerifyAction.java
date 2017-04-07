 package com.rt.shop.manage.admin.action;
 
 import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rt.shop.entity.Store;
import com.rt.shop.entity.User;
import com.rt.shop.service.IGroupService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class VerifyAction
 {
 
	 @Autowired
private HttpSession session;
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IStoreService storeService;
 
   @Autowired
   private IGroupService groupService;
 
   @RequestMapping({"/verify_code.htm"})
   public void validate_code(HttpServletRequest request, HttpServletResponse response, String code, String code_name)
   {
     HttpSession session = request.getSession(false);
     String verify_code = "";
     if (CommWebUtil.null2String(code_name).equals(""))
       verify_code = (String)session.getAttribute("verify_code");
     else {
       verify_code = (String)session.getAttribute(code_name);
     }
     boolean ret = true;
     if ((verify_code != null) && (!verify_code.equals("")) && 
       (!CommWebUtil.null2String(code.toUpperCase()).equals(verify_code))) {
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
 
   @RequestMapping({"/verify_username.htm"})
   public void verify_username(HttpServletRequest request, HttpServletResponse response, String userName, String id)
   {
     boolean ret = true;
    
     String sql=" where userName='"+userName+"' and id!="+CommWebUtil.null2Long(id);
     List<User> users = this.userService.selectList(sql,null);
     
     if ((users != null) && (users.size() > 0)) {
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
 
   @RequestMapping({"/verify_email.htm"})
   public void verify_email(HttpServletRequest request, HttpServletResponse response, String email, String id)
   {
     boolean ret = true;
    

     String sql=" where email='"+email+"' and id!="+CommWebUtil.null2Long(id);
     List<User> users = this.userService.selectList(sql,null);
     if ((users != null) && (users.size() > 0)) {
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
 
   @RequestMapping({"/verify_storename.htm"})
   public void verify_storename(HttpServletRequest request, HttpServletResponse response, String store_name, String id)
   {
     boolean ret = true;
//     Map params = new HashMap();
//     params.put("store_name", store_name);
//     params.put("id", CommWebUtil.null2Long(id));
//     List users = this.storeService
//       .query("select obj from Store obj where obj.store_name=:store_name and obj.id!=:id", 
//       params, -1, -1);
     Store sStore=new Store();
     sStore.setStore_name(store_name);
     Store s=storeService.selectOne(sStore);
     
     String sql=" where store_id="+s.getId()+" and id!="+CommWebUtil.null2Long(id);
     List<User> users = this.userService.selectList(sql,null);
     
     
     if ((users != null) && (users.size() > 0)) {
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
 
   @RequestMapping({"/verify_mobile.htm"})
   public void verify_mobile(HttpServletRequest request, HttpServletResponse response, String mobile, String id)
   {
     boolean ret = true;
     
     String sql=" where mobile='"+mobile+"' and id!="+CommWebUtil.null2Long(id);
     List<User> users = this.userService.selectList(sql,null);
     
     if ((users != null) && (users.size() > 0)) {
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
 
   @RequestMapping({"/verify.htm"})
   public void verify(HttpServletRequest request, HttpServletResponse response, String name)
     throws IOException
   {
     response.setContentType("image/jpeg");
     response.setHeader("Pragma", "No-cache");
     response.setHeader("Cache-Control", "no-cache");
     response.setDateHeader("Expires", 0L);
   //  HttpSession session = request.getSession(false);
 
     int width = 73; int height = 27;
     BufferedImage image = new BufferedImage(width, height, 
       1);
 
     Graphics g = image.getGraphics();
 
     Random random = new Random();
 
     g.setColor(getRandColor(200, 250));
     g.fillRect(0, 0, width, height);
 
     g.setFont(new Font("Times New Roman", 0, 24));
 
     g.setColor(getRandColor(160, 200));
     for (int i = 0; i < 155; i++) {
       int x = random.nextInt(width);
       int y = random.nextInt(height);
       int xl = random.nextInt(12);
       int yl = random.nextInt(12);
       g.drawLine(x, y, x + xl, y + yl);
     }
 
     String sRand = "";
     for (int i = 0; i < 4; i++) {
       String rand = CommWebUtil.randomInt(1).toUpperCase();
       sRand = sRand + rand;
 
       g.setColor(
         new Color(20 + random.nextInt(110), 20 + random
         .nextInt(110), 20 + random.nextInt(110)));
       g.drawString(rand, 13 * i + 6, 24);
     }
 
     if (CommWebUtil.null2String(name).equals(""))
       session.setAttribute("verify_code", sRand);
     else {
       session.setAttribute(name, sRand);
     }
 
     g.dispose();
     ServletOutputStream responseOutputStream = response.getOutputStream();
 
     ImageIO.write(image, "JPEG", responseOutputStream);
 
     responseOutputStream.flush();
     responseOutputStream.close();
   }
 
   private Color getRandColor(int fc, int bc) {
     Random random = new Random();
     if (fc > 255)
       fc = 255;
     if (bc > 255)
       bc = 255;
     int r = fc + random.nextInt(bc - fc);
     int g = fc + random.nextInt(bc - fc);
     int b = fc + random.nextInt(bc - fc);
     return new Color(r, g, b);
   }
 }


 
 
 