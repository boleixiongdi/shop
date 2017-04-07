package com.rt.shop.manage.admin.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.easyjf.beans.BeanUtils;
import com.easyjf.beans.BeanWrapper;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Area;
import com.rt.shop.entity.query.AreaQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAreaService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.tools.DatabaseTools;
import com.rt.shop.util.CommWebUtil;

@Controller
public class AreaManageAction {

	@Autowired
	private ISysConfigService configService;

	@Autowired
	private IUserConfigService userConfigService;

	@Autowired
	private IAreaService areaService;

	@Autowired
	private DatabaseTools databaseTools;

	@SecurityMapping( display = false, rsequence = 0, title = "地区列表", value = "/admin/area_list.htm*", rtype = "admin", rname = "常用地区", rcode = "admin_area_set", rgroup = "设置" )
	@RequestMapping( { "/admin/area_list.htm" } )
	public ModelAndView area_list( HttpServletRequest request, HttpServletResponse response, String currentPage, String pid, String orderBy, String orderType ) {
		ModelAndView mv = new JModelAndView( "admin/blue/area_setting.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response );
		String url = this.configService.getSysConfig().getAddress();
		if( (url == null) || (url.equals( "" )) ) {
			url = CommUtil.getURL( request );
		}
		String params = "";
		AreaQueryObject qo = null;
		if( (pid == null) || (pid.equals( "" )) ) {
			qo = new AreaQueryObject( currentPage, mv, orderBy, orderType );
			qo.addQuery( "obj.parent.id is null", null );
		}
		else {
			qo = new AreaQueryObject( currentPage, mv, orderBy, orderType );
			qo.addQuery( "obj.parent.id", new SysMap( "pid", Long.valueOf( Long.parseLong( pid ) ) ), "=" );
			params = "&pid=" + pid;
			Area parent = this.areaService.selectById( Long.valueOf( Long.parseLong( pid ) ) );
			mv.addObject( "parent", parent );
			if( parent.getLevel() == 0 ) {
				
				Area sArea=new Area();
				sArea.setParent_id(parent.getId());
				List seconds = this.areaService.selectList(sArea);
						//.query( "select obj from Area obj where obj.parent.id=:pid", map, -1, -1 );
				mv.addObject( "seconds", seconds );
				mv.addObject( "first", parent );
			}
			if( parent.getLevel() == 1 ) {
				Area sArea=new Area();
				sArea.setParent_id(parent.getId());
				List thirds = this.areaService.selectList(sArea);
				
				Area ssArea=new Area();
				ssArea.setParent_id(parent.getId());
				Area pArea=areaService.selectById(parent.getId());
				
				Area area=new Area();
				area.setParent_id(pArea.getId());
				List seconds = this.areaService.selectList(area);
				mv.addObject( "thirds", thirds );
				mv.addObject( "seconds", seconds );
				mv.addObject( "second", parent );
				mv.addObject( "first", parent.getParent() );
			}
			if( parent.getLevel() == 2 ) {

				Area ssArea=new Area();
				ssArea.setParent_id(parent.getId());
				Area pArea=areaService.selectById(parent.getId());
				
				Area area=new Area();
				area.setParent_id(pArea.getParent_id());
				List thirds = this.areaService.selectList(area);
				
				Area ppArea=areaService.selectById(area.getParent_id());
				Area a=new Area();
				a.setParent_id(ppArea.getId());
				List seconds = this.areaService.selectList(a);
				mv.addObject( "thirds", thirds );
				mv.addObject( "seconds", seconds );
				mv.addObject( "third", parent );
				mv.addObject( "second", parent.getParent() );
				mv.addObject( "first", parent.getParent().getParent() );
			}
		}
		WebForm wf = new WebForm();
		wf.toQueryPo( request, qo, Area.class, mv );
		 Page pList = this.areaService.selectPage(new Page<Area>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
		 CommWebUtil.saveIPageList2ModelAndView( url + "/admin/area_list.htm", "", params, pList, mv );
		 Area sArea=new Area();
		 sArea.setParent_id(null);
		List areas = this.areaService.selectList(sArea);
				//.query( "select obj from Area obj where obj.parent.id is null", null, -1, -1 );
		mv.addObject( "areas", areas );
		return mv;
	}

	@SecurityMapping( display = false, rsequence = 0, title = "地区保存", value = "/admin/area_save.htm*", rtype = "admin", rname = "常用地区", rcode = "admin_area_set", rgroup = "设置" )
	@RequestMapping( { "/admin/area_save.htm" } )
	public ModelAndView area_save( HttpServletRequest request, HttpServletResponse response, String areaId, String pid, String count, String list_url, String currentPage ) {
		if( areaId != null ) {
			String[] ids = areaId.split( "," );
			int i = 1;
			for( String id : ids ) {
				String areaName = request.getParameter( "areaName_" + i );
				Area area = this.areaService.selectById( Long.valueOf( Long.parseLong( request.getParameter( "id_" + i ) ) ) );
				area.setAreaName( areaName );
				area.setSequence( CommUtil.null2Int( request.getParameter( "sequence_" + i ) ) );
				this.areaService.updateSelectiveById( area );
				i++;
			}

		}

		Area parent = null;
		if( !pid.equals( "" ) )
			parent = this.areaService.selectById( Long.valueOf( Long.parseLong( pid ) ) );
		for( int i = 1; i <= CommUtil.null2Int( count ); i++ ) {
			Area area = new Area();
			area.setAddTime( new Date() );
			String areaName = request.getParameter( "new_areaName_" + i );
			int sequence = CommUtil.null2Int( request.getParameter( "new_sequence_" + i ) );
			if( parent != null ) {
				area.setLevel( parent.getLevel() + 1 );
				area.setParent( parent );
			}
			area.setAreaName( areaName );
			area.setSequence( sequence );
			this.areaService.insertSelective( area );
		}

		ModelAndView mv = new JModelAndView( "admin/blue/success.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response );
		mv.addObject( "op_title", "更新区域成功" );
		mv.addObject( "list_url", list_url + "?currentPage=" + currentPage + "&pid=" + pid );
		return mv;
	}

	private List<Long> genericIds( Area obj ) {
		List<Long> ids = new ArrayList<Long>();
		ids.add( obj.getId() );
		for( Area child : obj.getChilds() ) {
			List<Long> cids = genericIds(child );
			for( Long cid : cids ) {
				ids.add( cid );
			}
			ids.add( child.getId() );
		}
		return ids;
	}

	@SecurityMapping( display = false, rsequence = 0, title = "地区删除", value = "/admin/area_del.htm*", rtype = "admin", rname = "常用地区", rcode = "admin_area_set", rgroup = "设置" )
	@RequestMapping( { "/admin/area_del.htm" } )
	public String area_del( HttpServletRequest request, String mulitId, String currentPage, String pid ) {
		String[] ids = mulitId.split( "," );
		for( String id : ids ) {
			if( !id.equals( "" ) ) {
				List list = genericIds( this.areaService.selectById( Long.valueOf( Long.parseLong( id ) ) ) );
				
				List<Area> objs = this.areaService.selectBatchIds(list);
						
				for( Area obj : objs ) {
					obj.setParent( null );
					this.areaService.deleteById( obj.getId() );
				}
			}
		}
		return "redirect:area_list.htm?pid=" + pid + "&currentPage=" + currentPage;
	}

	@SecurityMapping( display = false, rsequence = 0, title = "地区导入", value = "/admin/area_import.htm*", rtype = "admin", rname = "常用地区", rcode = "admin_area_set", rgroup = "设置" )
	@RequestMapping( { "/admin/area_import.htm" } )
	public ModelAndView area_import( HttpServletRequest request, HttpServletResponse response, String list_url ) throws Exception {
		ModelAndView mv = null;
		this.databaseTools.execute( "update shopping_store set area_id=null" );

		this.databaseTools.execute( "update shopping_address set area_id=null" );

		this.databaseTools.execute( "update shopping_area set parent_id=null" );

		this.databaseTools.execute( "delete from shopping_area" );

		String filePath = request.getSession().getServletContext().getRealPath( "/" ) + "resources/data/area.sql";
		File file = new File( filePath );
		boolean ret = true;
		if( file.exists() )
			ret = this.databaseTools.executSqlScript( filePath );
		else {
			ret = false;
		}
		if( ret ) {
			mv = new JModelAndView( "admin/blue/success.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response );
			CacheManager manager = CacheManager.create();
			manager.clearAll();
			mv.addObject( "op_title", "数据导入成功" );
		}
		else {
			mv = new JModelAndView( "admin/blue/error.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response );
			mv.addObject( "op_title", "数据导入失败" );
		}
		mv.addObject( "list_url", list_url );
		return mv;
	}

	@RequestMapping( { "/admin/area_export.htm" } )
	public ModelAndView area_export( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		ModelAndView mv = null;
		String path = request.getSession().getServletContext().getRealPath( "/" ) + "resources" + File.separator + "data" + File.separator + "base.sql";
		String tables = "shopping_accessory,shopping_adv_pos,shopping_advert,shopping_articleclass,shopping_article,shopping_document,shopping_navigation,shopping_template,shopping_sysconfig";
		boolean ret = this.databaseTools.export( tables, path );
		if( ret ) {
			mv = new JModelAndView( "admin/blue/success.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response );
			CacheManager manager = CacheManager.create();
			manager.clearAll();
		}
		else {
			mv = new JModelAndView( "admin/blue/error.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response );
		}
		mv.addObject( "op_title", "数据导出" );
		mv.addObject( "list_url", CommUtil.getURL( request ) + "/admin/area_list.htm" );
		return mv;
	}

	@SecurityMapping( display = false, rsequence = 0, title = "地区Ajax编辑", value = "/admin/area_ajax.htm*", rtype = "admin", rname = "常用地区", rcode = "admin_area_set", rgroup = "设置" )
	@RequestMapping( { "/admin/area_ajax.htm" } )
	public void area_ajax( HttpServletRequest request, HttpServletResponse response, String id, String fieldName, String value ) throws ClassNotFoundException {
		Area obj = this.areaService.selectById( Long.valueOf( Long.parseLong( id ) ) );
		Field[] fields = Area.class.getDeclaredFields();
		BeanWrapper wrapper = new BeanWrapper( obj );
		Object val = null;
		for( Field field : fields ) {
			if( field.getName().equals( fieldName ) ) {
				Class clz = Class.forName( "java.lang.String" );
				if( field.getType().getName().equals( "int" ) ) {
					clz = Class.forName( "java.lang.Integer" );
				}
				if( field.getType().getName().equals( "boolean" ) ) {
					clz = Class.forName( "java.lang.Boolean" );
				}
				if( !value.equals( "" ) )
					val = BeanUtils.convertType( value, clz );
				else {
					val = Boolean.valueOf( !CommUtil.null2Boolean( wrapper.getPropertyValue( fieldName ) ) );
				}
				wrapper.setPropertyValue( fieldName, val );
			}
		}
		this.areaService.updateSelectiveById( obj );
		response.setContentType( "text/plain" );
		response.setHeader( "Cache-Control", "no-cache" );
		response.setCharacterEncoding( "UTF-8" );
		try {
			PrintWriter writer = response.getWriter();
			writer.print( val.toString() );
		}
		catch( IOException e ) {
			e.printStackTrace();
		}
	}
}
