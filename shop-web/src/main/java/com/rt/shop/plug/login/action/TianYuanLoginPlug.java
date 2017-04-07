
package com.rt.shop.plug.login.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
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

/**
 * @author 徐明磊{xu.minglei@rhxtune.com}.
 * @time 2016年5月30日，下午7:29:24.
 * @description 甜园生活第三方登录.
 */
@Controller
public class TianYuanLoginPlug {

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
	// private String sina_login_url = "https://api.weibo.com/oauth2/authorize";
	// private String sina_token_url =
	// "https://api.weibo.com/oauth2/access_token";
	// private String sina_token_info_url =
	// "https://api.weibo.com/oauth2/get_token_info";
	// private final static String sina_login_url =
	// "http://10.2.10.102:8080/open-account/oauth/authorize";
	// private final static String sina_token_url =
	// "http://10.2.10.102:8080/open-account/oauth/token";
	// private final static String sina_token_info_url =
	// "http://10.2.10.102:8080/open-accoun/oauth2/rest_token";
	// private final static String sina_user_info_url =
	// "http://10.2.10.102:8080/open-account/account/info";

	private final static String sina_login_url = "http://open.5itianyuan.com/oauth/authorize";
	private final static String sina_token_url = "http://open.5itianyuan.com/oauth/token";
	// private final static String sina_token_info_url =
	// "http://open.5itianyuan.com/oauth2/rest_token";
	private final static String sina_user_info_url = "http://open.5itianyuan.com/account/info";

	@RequestMapping({ "/tianyuan_login_api.htm" })
	public void tianyuan_login_api(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SysConfig config = this.configService.getSysConfig();
		String url = sina_login_url + "?client_id=" + config.getTianyuan_login_id() + "&response_type=code"
				+ "&scope=read" + "&redirect_uri=" + CommUtil.getURL(request) + "/tianyuan_login_bind.htm";
		response.sendRedirect(url);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping({ "/tianyuan_login_bind.htm" })
	public String tianyuan_login_bind(HttpServletRequest request, HttpServletResponse response, String code)
			throws HttpException, IOException {
		String tianyuan_openid = "-1";
		String userName = LoginUtil.randomUserName();
		String redirect_uri = CommUtil.encode(CommUtil.getURL(request) + "/tianyuan_login_bind.htm");
		// String auth_url = sina_login_url + "?client_id=" +
		// this.configService.getSysConfig().getTianyuan_login_id()
		// + "&response_type=code&redirect_uri=" + redirect_uri;
		String token_url = sina_token_url + "?client_id=" + this.configService.getSysConfig().getTianyuan_login_id()
				+ "&client_secret=" + this.configService.getSysConfig().getTianyuan_login_key()
				+ "&grant_type=authorization_code&redirect_uri=" + redirect_uri + "&code=" + code;
		HttpClient client = new HttpClient();

		PostMethod method = new PostMethod(token_url);
		int status = client.executeMethod(method);
		if (status == 200) {
			Map map = (Map) Json.fromJson(HashMap.class, method.getResponseBodyAsString());
			String access_token = CommUtil.null2String(map.get("access_token"));

			String user_info_url = sina_user_info_url + "?access_token=" + access_token;

			GetMethod get = new GetMethod(user_info_url);
			status = client.executeMethod(get);
			if (status == 200) {
				map = (HashMap) Json.fromJson(HashMap.class, get.getResponseBodyAsString());
//				userName = CommUtil.null2String(map.get("displayName"));
				tianyuan_openid = CommUtil.null2String(map.get("uid"));

				userName = generic_username(userName);
			}
		} else {
			request.getSession(false).setAttribute("op_title", "甜园账户登录失败");
			request.getSession(false).setAttribute("url", CommUtil.getURL(request) + "/index.htm");
			return "redirect:" + CommUtil.getURL(request) + "/error.htm";
		}
		if (SecurityUserHolder.getCurrentUser() == null) {
			User sUser=new User();
	    	 sUser.setTianyuan_openid(tianyuan_openid);
	       User user = this.userService.selectOne(sUser);
			if (user == null) {
				user = new User();
				user.setUserName(userName);
				user.setUserRole("BUYER");
				user.setTianyuan_openid(tianyuan_openid);
				user.setTianyuan_binded(tianyuan_openid);
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
				//	log.setIntegral_user(user);
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
			//	album.setUser(user);
				album.setUser_id(user.getId());
				this.albumService.insertSelective(album);
				request.getSession(false).removeAttribute("verify_code");
				request.getSession(false).setAttribute("bind", "tianyuan");
				return "redirect:" + CommUtil.getURL(request) + "/rt.shop_login.htm?username="
						+ CommUtil.encode(user.getUserName()) + "&password=123456";
			}
			request.getSession(false).removeAttribute("verify_code");
			user.setTianyuan_openid(tianyuan_openid);
			user.setTianyuan_binded(tianyuan_openid);
//			user.setUserName(userName);
			this.userService.updateSelectiveById(user);
			return "redirect:" + CommUtil.getURL(request) + "/rt.shop_login.htm?username="
					+ CommUtil.encode(user.getUserName()) + "&password=" + "rt.shop_thid_login_" + user.getPassword();
		}

		User user = this.userService.selectById(SecurityUserHolder.getCurrentUser().getId());
		user.setTianyuan_openid(tianyuan_openid);
		user.setTianyuan_binded(tianyuan_openid);
//		user.setUserName(userName);
		this.userService.updateSelectiveById(user);
		return "redirect:" + CommUtil.getURL(request) + "/buyer/account_bind.htm";
	}

	@RequestMapping({ "/tianyuan_login_bind_finish.htm" })
	public String tianyuan_login_bind_finish(HttpServletRequest request, HttpServletResponse response, String userName,
			String password, String bind_already) {
		String url = "redirect:" + CommUtil.getURL(request) + "/index.htm";
		if (!CommUtil.null2String(bind_already).equals("")) {
			User sUser=new User();
	    	 sUser.setUserName(userName);
	       User user = this.userService.selectOne(sUser);
			if (user == null) {
				request.getSession(false).setAttribute("op_title", "用户绑定失败");
				request.getSession(false).setAttribute("url", url);
				url = "redirect:" + CommUtil.getURL(request) + "/error.htm";
			} else if (Md5Encrypt.md5(password).toLowerCase().equals(user.getPassword())) {
				user.setTianyuan_openid(SecurityUserHolder.getCurrentUser().getTianyuan_openid());
				request.getSession(false).removeAttribute("verify_code");

				this.userService.deleteById(SecurityUserHolder.getCurrentUser().getId());
				url = "redirect:" + CommUtil.getURL(request) + "/rt.shop_login.htm?username="
						+ CommUtil.encode(user.getUserName()) + "&password=" + password;
			} else {
				request.getSession(false).setAttribute("op_title", "用户绑定失败");
				request.getSession(false).setAttribute("url", CommUtil.getURL(request) + "/index.htm");
				url = "redirect:" + CommUtil.getURL(request) + "/error.htm";
			}
		} else {
			User user = SecurityUserHolder.getCurrentUser();
			user.setUserName(userName);
			user.setPassword(Md5Encrypt.md5(password).toLowerCase());
			this.userService.updateSelectiveById(user);
		}
		request.getSession(false).removeAttribute("verify_code");
		request.getSession(false).removeAttribute("bind");
		return url;
	}

	private String generic_username(String userName) {
		String name = userName;
		User sUser1=new User();
		sUser1.setUserName(name);
      User user = this.userService.selectOne(sUser1);
		if (user != null) {
			for (int i = 1; i < 1000000; i++) {
				name = name + i;
				User sUser=new User();
		    	 sUser.setUserName(name);
		        user = this.userService.selectOne(sUser);
				if (user == null) {
					break;
				}
			}
		}
		return name;
	}
}
