 package com.rt.shop.plug.login.action;
 
 import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.common.tools.Md5Encrypt;
import com.rt.shop.entity.Album;
import com.rt.shop.entity.IntegralLog;
import com.rt.shop.entity.Role;
import com.rt.shop.entity.SysConfig;
import com.rt.shop.entity.User;
import com.rt.shop.entity.UserRole;
import com.rt.shop.service.IAlbumService;
import com.rt.shop.service.IIntegralLogService;
import com.rt.shop.service.IRoleService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class QQLoginPlug
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IRoleService roleService;
 
   @Autowired
   private IAlbumService albumService;
 
   @Autowired
   private IIntegralLogService integralLogService;
   //获取Authorization Code，pc接口已经适配wap网站
   private String qq_login_url = "https://graph.qq.com/oauth2.0/authorize";
   //PC网站：
   private String qq_access_token = "https://graph.qq.com/oauth2.0/authorize";
   //wap网站
   private String qq_wapaccess_token="https://graph.z.qq.com/moc2/token"; 
   
   
     // qq_login_api.htm，请求登录api，response_type：授权类型，此值固定为“code”。 client_id :申请QQ登录成功后，分配给应用的appid。;redirect_uri:成功授权后的回调地址，必须是注册appid时填写的主域名下的地址，建议设置为网站首页或网站的用户中心。注意需要将url进行URLEncode
    // state ：client端的状态值。用于第三方应用防止CSRF攻击，成功授权后回调时会原样带回。请务必严格按照流程检查用户与state参数状态的绑定。
   //scope：请求用户授权时向用户显示的可进行授权的列表。不传则默认请求对接口get_user_info进行授权。
   //建议控制授权项的数量，只传入必要的接口名称，因为授权项越多，用户越可能拒绝进行任何授权。 
   
   @RequestMapping({"/qq_login_api.htm"})
   public void qq_login_api(HttpServletRequest request, HttpServletResponse response)
     throws IOException
   {
     String redirect_uri = CommUtil.encode(CommUtil.getURL(request) + "/qq_login_bind.htm");
     
     String auth_url = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=" + 
       this.configService.getSysConfig().getQq_login_id() + 
       "&redirect_uri=" + redirect_uri + "&state=rt.shop&scope=get_user_info";
     
     response.sendRedirect(auth_url);
   }
  //qq绑定登录  通过Authorization Code获取Access Token；
   //grant_type：授权类型，在本步骤中，此值为“authorization_code”。   
   //client_id：申请QQ登录成功后，分配给网站的appid
   //client_secret：申请QQ登录成功后，分配给网站的appkey
   /**
    * code：上一步返回的authorization code。 

如果用户成功登录并授权，则会跳转到指定的回调地址，并在URL中带上Authorization Code。

例如，回调地址为www.qq.com/my.php，则跳转到：

http://www.qq.com/my.php?code=520DD95263C1CFEA087******

注意此code会在10分钟内过期。
    */
   /**
    * redirect_uri：与上面一步中传入的redirect_uri保持一致。
    */
   @RequestMapping({"/qq_login_bind.htm"})
   public String qq_login_bind(HttpServletRequest request, HttpServletResponse response, String code)
   {
     String redirect_uri = CommUtil.encode(CommUtil.getURL(request) + "/qq_login_bind.htm");
     
     String token_url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=" + 
       this.configService.getSysConfig().getQq_login_id() + 
       "&client_secret=" + this.configService.getSysConfig().getQq_login_key() + 
       "&code=" + code + "&redirect_uri=" + redirect_uri;
 
     String[] access_token_callback = CommUtil.null2String(
       getHttpContent(token_url, "UTF-8", "GET")).split("&");
     String access_token = access_token_callback[0].split("=")[1];
     String me_url = "https://graph.qq.com/oauth2.0/me?access_token=" + access_token;
 
     String me_callback = CommUtil.null2String(
       getHttpContent(me_url, "UTF-8", "GET")).replaceAll("callback\\(", "").replaceAll("\\);", "");
     Map me_map = (Map)Json.fromJson(HashMap.class, me_callback);
     
     String qq_openid = CommUtil.null2String(me_map.get("openid"));
     
     String user_info_url = "https://graph.qq.com/user/get_user_info?access_token=" + 
       access_token + "&oauth_consumer_key=" + me_map.get("client_id") + "&openid=" + qq_openid;
     String user_info_callback = getHttpContent(user_info_url, "UTF-8", "GET");
     
     Map user_map = (Map)Json.fromJson(HashMap.class, user_info_callback);
     
     System.out.println("用户名：" + user_map.get("nickname"));
     
     if (SecurityUserHolder.getCurrentUser() == null) {
       String userName = generic_username(CommUtil.null2String(user_map.get("nickname")));
       User sUser=new User();
       sUser.setQq_openid(qq_openid);
       User user = this.userService.selectOne(sUser);
    		   //.getObjByProperty("qq_openid", qq_openid);
       if (user == null) {
         user = new User();
         user.setUserName(userName);
         user.setUserRole("BUYER");
         user.setQq_openid(qq_openid);
         user.setQq_binded(qq_openid);
         user.setAddTime(new Date());
         user.setPassword(Md5Encrypt.md5("123456").toLowerCase());
        
         Role sRole=new Role();
         sRole.setType("BUYER");
         List<Role> roles = this.roleService.selectList(sRole);
        
         user.setRoles(roles);
         List<UserRole> urList=new ArrayList<UserRole>();
       
           for(Role role : roles){
          	UserRole ur=new UserRole(user.getId(),role.getId()); 
          	urList.add(ur);
           }
           userService.insertBatchUserRole(urList);
         if (this.configService.getSysConfig().getIntegral()) {
           user.setIntegral(this.configService.getSysConfig().getMemberRegister());
           this.userService.insertSelective(user);
           IntegralLog log = new IntegralLog();
           log.setAddTime(new Date());
           log.setContent("注册赠送积分:" + this.configService.getSysConfig().getMemberRegister());
           log.setIntegral(this.configService.getSysConfig().getMemberRegister());
        //   log.setIntegral_user(user);
           log.setIntegral_user_id(user.getId());
           log.setType("reg");
           this.integralLogService.insertSelective(log);
         } else {
           this.userService.insertSelective(user);
         }
 
         Album album = new Album();
         album.setAddTime(new Date());
         album.setAlbum_default(true);
         album.setAlbum_name("默认相册");
         album.setAlbum_sequence(-10000);
         //album.setUser(user);
         album.setUser_id(user.getId());
         this.albumService.insertSelective(album);
         request.getSession(false).removeAttribute("verify_code");
         request.getSession(false).setAttribute("bind", "qq");
         return "redirect:" + CommUtil.getURL(request) + "/rt.shop_login.htm?username=" + 
           CommUtil.encode(user.getUserName()) + "&password=123456";
       }
       request.getSession(false).removeAttribute("verify_code");
       user.setQq_openid(qq_openid);
       user.setQq_binded(qq_openid);
       this.userService.updateSelectiveById(user);
       return "redirect:" + CommUtil.getURL(request) + "/rt.shop_login.htm?username=" + 
         CommUtil.encode(user.getUserName()) + "&password=" + "rt.shop_thid_login_" + user.getPassword();
     }
 
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     user.setQq_openid(qq_openid);
     user.setQq_binded(qq_openid);
     this.userService.updateSelectiveById(user);
     return "redirect:" + CommUtil.getURL(request) + "/buyer/account_bind.htm";
   }
 
   @RequestMapping({"/qq_login_bind_finish.htm"})
   public String qq_login_bind_finish(HttpServletRequest request, HttpServletResponse response, String userName, String password, String bind_already)
   {
     String url = "redirect:" + CommUtil.getURL(request) + "/index.htm";
     if (!CommUtil.null2String(bind_already).equals("")) {
    	 User sUser=new User();
    	 sUser.setUserName(userName);
       User user = this.userService.selectOne(sUser);
       if (user == null) {
         request.getSession(false).setAttribute("op_title", "用户绑定失败");
         request.getSession(false).setAttribute("url", url);
         url = "redirect:" + CommUtil.getURL(request) + "/error.htm";
       }
       else if (Md5Encrypt.md5(password).toLowerCase().equals(
         user.getPassword())) {
         user.setQq_openid(SecurityUserHolder.getCurrentUser().getQq_openid());
         request.getSession(false).removeAttribute("verify_code");
 
         this.userService.deleteById(SecurityUserHolder.getCurrentUser().getId());
         url = "redirect:" + CommUtil.getURL(request) + "/rt.shop_login.htm?username=" + 
           CommUtil.encode(user.getUserName()) + "&password=" + password;
       } else {
         request.getSession(false).setAttribute("op_title", "用户绑定失败");
         request.getSession(false).setAttribute("url", CommUtil.getURL(request) + "/index.htm");
         url = "redirect:" + CommUtil.getURL(request) + "/error.htm";
       }
     }
     else
     {
       User user = SecurityUserHolder.getCurrentUser();
       user.setUserName(userName);
       user.setPassword(Md5Encrypt.md5(password).toLowerCase());
       this.userService.updateSelectiveById(user);
     }
     request.getSession(false).removeAttribute("verify_code");
     request.getSession(false).removeAttribute("bind");
     return url;
   }
 
   public static String getHttpContent(String url, String charSet, String method)
   {
     HttpURLConnection connection = null;
     String content = "";
     try {
       URL address_url = new URL(url);
       connection = (HttpURLConnection)address_url.openConnection();
       connection.setRequestMethod("GET");
 
       connection.setConnectTimeout(1000000);
       connection.setReadTimeout(1000000);
 
       int response_code = connection.getResponseCode();
       if (response_code == 200) {
         InputStream in = connection.getInputStream();
         BufferedReader reader = new BufferedReader(
           new InputStreamReader(in, charSet));
         String line = null;
         while ((line = reader.readLine()) != null) {
           content = content + line;
         }
         String str1 = content;
         return str1;
       }
     } catch (MalformedURLException e) {
       e.printStackTrace();
     } catch (IOException e) {
       e.printStackTrace();
     } finally {
       if (connection != null)
         connection.disconnect();
     }
     if (connection != null) {
       connection.disconnect();
     }
 
     return "";
   }
 
   private String generic_username(String userName)
   {
     String name = userName;
     User sUser=new User();
	 sUser.setUserName(name);
   User user = this.userService.selectOne(sUser);
     if (user != null) {
       for (int i = 1; i < 1000000; i++) {
         name = name + i;
         User sUser1=new User();
    	 sUser1.setUserName(name);
         user = this.userService.selectOne(sUser1);
         if (user == null) {
           break;
         }
       }
     }
     return name;
   }
 
   public static void main(String[] args)
   {
     SysConfig config = new SysConfig();
     config.setQq_login_id("100359491");
     config.setQq_login_key("a34bcaef0487e650238983abc0fbae7c");
     String redirect_uri = 
       CommUtil.encode("http:///qq_login_bind.htm");
     String auth_url = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=" + 
       config.getQq_login_id() + "&redirect_uri=" + redirect_uri + "&state=rt.shop&scope=get_user_info";
     System.out.println(auth_url);
 
     String token_url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=" + 
       config.getQq_login_id() + "&client_secret=" + 
       config.getQq_login_key() + "&code=9873676D49030659CF025A0B9FF9F0B8&redirect_uri=" + redirect_uri;
 
     String me_url = "https://graph.qq.com/oauth2.0/me?access_token=1CA359B424836978AAA1424B83C1B5A3";
 
     String user_info_url = "https://graph.qq.com/user/get_user_info?access_token=1CA359B424836978AAA1424B83C1B5A3&oauth_consumer_key=100359491&openid=9A6383AD4B58E8B1ACF65DC68E0B3B68";
 
     System.out.println("返回值为：" + getHttpContent(user_info_url, "UTF-8", "GET"));
   }
 }

