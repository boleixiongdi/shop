/*package com.rt.shop.view.web.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.rt.shop.common.tools.Md5Encrypt;
import com.rt.shop.entity.SysConfig;
import com.rt.shop.entity.User;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IResService;
import com.rt.shop.service.IRoleGroupService;
import com.rt.shop.service.IRoleService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.CommWebUtil;

@Controller
public class InstallViewAction implements ServletContextAware {
	private ServletContext servletContext;

	@Autowired
	private ISysConfigService configService;

	@Autowired
	private IUserConfigService userConfigService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IRoleGroupService roleGroupService;

	@Autowired
	private IResService resService;

	@Autowired
	SecurityManager securityManager;

	@Autowired
	private DatabaseTools databaseTools;

	@RequestMapping( { "/install.htm" } )
	public ModelAndView install( HttpServletRequest request, HttpServletResponse response, String install_status, String title, String pws, String test_data ) {
		ModelAndView mv = new JModelAndView( "WEB-INF/templates/install/install1.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 2, request, response );
		if( CommWebUtil.null2String( install_status ).equals( "" ) ) {
			mv.addObject( "install_status", "install1" );
		}
		if( CommWebUtil.null2String( install_status ).equals( "install1" ) ) {
			mv = new JModelAndView( "WEB-INF/templates/install/install2.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 2, request, response );
			mv.addObject( "install_status", "install2" );
		}
		if( CommWebUtil.null2String( install_status ).equals( "install2" ) ) {
			mv = new JModelAndView( "WEB-INF/templates/install/install3.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 2, request, response );
			mv.addObject( "install_status", "install3" );
			mv.addObject( "test_data", CommWebUtil.null2String( test_data ) );
			String shop_url = CommWebUtil.getURL( request );
			mv.addObject( "shop_url", shop_url );
			mv.addObject( "title", CommWebUtil.null2String( title ) );
			mv.addObject( "pws", CommWebUtil.null2String( pws ) );
			String shop_manage_url = CommWebUtil.getURL( request ) + "/admin/index.htm";
			mv.addObject( "shop_manage_url", shop_manage_url );
		}
		mv.addObject( "version", "V2.0" );
		return mv;
	}

	@RequestMapping( { "/install_over.htm" } )
	public ModelAndView install_over( HttpServletRequest request, HttpServletResponse response ) {
		ModelAndView mv = new JModelAndView( "success.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response );
		mv.addObject( "op_title", "件" );
		return mv;
	}

	@RequestMapping( { "/install_view.htm" } )
	public ModelAndView install_view( HttpServletRequest request, HttpServletResponse response ) {
		ModelAndView mv = new JModelAndView( "WEB-INF/templates/install/install_view.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 2, request, response );
		return mv;
	}

	@RequestMapping( { "/install_init_test.htm" } )
	public void install_init_test( HttpServletRequest request, HttpServletResponse response, String title, String pws ) throws Exception {
		String path = request.getSession().getServletContext().getRealPath( "/" ) + "install.lock";
		File file = new File( path );
		if( !file.exists() ) {
			String filePath = request.getSession().getServletContext().getRealPath( "/" ) + "resources/data/test.sql";
			File sql_file = new File( filePath );
			boolean ret = true;
			if( sql_file.exists() ) {
				this.databaseTools.executSqlScript( filePath );
			}
			CacheManager manager = CacheManager.create();
			manager.clearAll();
			SysConfig config = this.configService.getSysConfig();
			config.setAddTime( new Date() );
			config.setTitle( title );
			config.setWebsiteState( true );
			this.configService.updateSelectiveById( config );
			User sUser=new User();
			sUser.setUserName("admin");
			User admin = this.userService.selectOne(sUser);
			admin.setPassword( Md5Encrypt.md5( pws ).toLowerCase() );
			this.userService.updateSelectiveById( admin );
			manager = CacheManager.create();
			manager.clearAll();

			Map urlAuthorities = null;
			//TODO
					//this.securityManager.loadUrlAuthorities();
			this.servletContext.setAttribute( "urlAuthorities", urlAuthorities );
			response.setContentType( "text/plain" );
			response.setHeader( "Cache-Control", "no-cache" );
			response.setCharacterEncoding( "UTF-8" );
			try {
				file.createNewFile();
				PrintWriter writer = response.getWriter();
				writer.print( true );
			}
			catch( IOException e ) {
				e.printStackTrace();
			}
		}
		else {
			response.setContentType( "text/plain" );
			response.setHeader( "Cache-Control", "no-cache" );
			response.setCharacterEncoding( "UTF-8" );
			try {
				PrintWriter writer = response.getWriter();
				writer.print( false );
			}
			catch( IOException e ) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping( { "/install_init_base.htm" } )
	public void install_init_base( HttpServletRequest request, HttpServletResponse response, String title, String pws ) throws Exception {
		String path = request.getSession().getServletContext().getRealPath( "/" ) + "install.lock";
		File file = new File( path );
		if( !file.exists() ) {
			String filePath = request.getSession().getServletContext().getRealPath( "/" ) + "resources/data/base.sql";
			File sql_file = new File( filePath );
			if( sql_file.exists() ) {
				this.databaseTools.executSqlScript( filePath );
			}
			CacheManager manager = CacheManager.create();
			manager.clearAll();
			SysConfig config = this.configService.getSysConfig();
			config.setAddTime( new Date() );
			config.setTitle( title );
			config.setWebsiteState( true );
			this.configService.updateSelectiveById( config );
			User sUser=new User();
			sUser.setUserName("admin");
			User admin = this.userService.selectOne(sUser);
			admin.setPassword( Md5Encrypt.md5( pws ).toLowerCase() );
			this.userService.updateSelectiveById( admin );
			manager = CacheManager.create();
			manager.clearAll();

			Map urlAuthorities = null;
			//TODO
					//this.securityManager.loadUrlAuthorities();
			this.servletContext.setAttribute( "urlAuthorities", urlAuthorities );
			response.setContentType( "text/plain" );
			response.setHeader( "Cache-Control", "no-cache" );
			response.setCharacterEncoding( "UTF-8" );
			try {
				PrintWriter writer = response.getWriter();
				file.createNewFile();
				writer.print( true );
			}
			catch( IOException e ) {
				e.printStackTrace();
			}
		}
		else {
			response.setContentType( "text/plain" );
			response.setHeader( "Cache-Control", "no-cache" );
			response.setCharacterEncoding( "UTF-8" );
			try {
				PrintWriter writer = response.getWriter();
				writer.print( false );
			}
			catch( IOException e ) {
				e.printStackTrace();
			}
		}
	}

	public void setServletContext( ServletContext servletContext ) {
		this.servletContext = servletContext;
	}
}*/