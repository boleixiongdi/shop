//Powered By if, Since 2014 - 2020

package com.rt.sys.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.rt.common.base.BaseController;
import com.rt.sys.entity.SysOffice;
import com.rt.sys.service.ISysOfficeService;
import com.rt.sys.util.SysUserUtils;

/**
 * 
 * @author 
 */

@Controller
@RequestMapping("office")
public class OfficeController extends BaseController {
	
	
	@Resource
	private ISysOfficeService sysOfficeService;
	
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toSysOffice(Model model){
		model.addAttribute("treeList", JSON.toJSONString(SysUserUtils.getUserOffice()));
		return "sys/office/office";
	}
	
	@RequestMapping(value="tree",method = RequestMethod.POST)
	public @ResponseBody List<SysOffice> getOfficeTreeList(@ModelAttribute SysOffice sysOffice){
		return SysUserUtils.getUserOffice();
	}
	
	/**
	 * 分页显示
	* @param params
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		Page<SysOffice> page = sysOfficeService.findPageInfo(params);
		model.addAttribute("page", page);
		return "sys/office/office-list";
	}
	
	/**
	 * 添加或更新
	* @param params
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody boolean save(@ModelAttribute SysOffice sysOffice){
		return sysOfficeService.saveSysOffice(sysOffice);
	}
	
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody boolean del(Long id){
		return sysOfficeService.deleteOfficeByRootId(id);
	}
	
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,Long parentId,@PathVariable("mode") String mode, Model model){
		SysOffice office = null, pOffice = null;
		if(StringUtils.equalsIgnoreCase(mode, "add")){
			pOffice = sysOfficeService.selectById(parentId);
		}else if(StringUtils.equalsIgnoreCase(mode, "edit")){
			office = sysOfficeService.selectById(id);
			pOffice = sysOfficeService.selectById(parentId);
			//area = sysAreaService.selectById(office.getAreaId());
		}else if(StringUtils.equalsIgnoreCase(mode, "detail")){
			office = sysOfficeService.selectById(id);
			pOffice = sysOfficeService.selectById(office.getParent_id());
			//area = sysAreaService.selectById(office.getAreaId());
		}
		model.addAttribute("pOffice", pOffice)
			.addAttribute("office", office);
		return mode.equals("detail")?"sys/office/office-detail":"sys/office/office-save";
	}
	

}
