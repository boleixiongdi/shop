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
import com.rt.sys.entity.SysResource;
import com.rt.sys.entity.SysRole;
import com.rt.sys.entity.SysUser;
import com.rt.sys.service.ISysOfficeService;
import com.rt.sys.service.ISysRoleService;
import com.rt.sys.service.ISysUserService;

/**
 * 
 * @author 
 */

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
	
	
	@Resource
	private ISysRoleService sysRoleService;
	@Resource
	private ISysUserService sysUserService;
	@Resource
	private ISysOfficeService sysOfficeService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toSysRole(Model model){
		return "sys/role/role";
	}
	
	/**
	 * 绑定用户界面
	* @return
	 */
	@RequestMapping(value="binduser",method=RequestMethod.POST)
	public String toBindUser(Long id,Model model){
		List<SysUser> users = sysRoleService.findUserByRoleId(id);
		model.addAttribute("users", users).addAttribute("roleId", id);
		return "sys/role/role-user";
	}
	
	/**
	 * 部门的人员
	* @param officeId
	* @return
	 */
	@RequestMapping(value="officeuser",method=RequestMethod.POST)
	public @ResponseBody List<SysUser> officeUser(Long officeId){
		SysUser sysUser = new SysUser();
		sysUser.setOfficeId(officeId);
		return sysUserService.selectList(sysUser);
	}
	
	/**
	 * 保存角色绑定的用户
	* @return
	 */
	@RequestMapping(value="saveuser",method=RequestMethod.POST)
	public @ResponseBody Integer saveUserRole(@ModelAttribute SysRole sysRole){
		return sysRoleService.saveUserRole(sysRole);
	}
	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params,Model model){
		Page<SysRole> page = sysRoleService.findPageInfo(params);
		model.addAttribute("page", page);
		return "sys/role/role-list";
	}
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysRole sysRole){
		return sysRoleService.saveSysRole(sysRole);
	}
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return sysRoleService.deleteSysRole(id);
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		SysRole sysRole = null;
		if(StringUtils.equals("edit", mode)){
			sysRole = sysRoleService.selectById(id);
			List<Long> resIds = sysRoleService.findResourceIdsByRoleId(id);
			//if(sysRole.getDataScope().equals("9")){
				List<Long> officeIds = sysRoleService.findOfficeIdsByRoleId(id);
				model.addAttribute("officeIds", JSON.toJSON(officeIds));
			//}
			model.addAttribute("resIds",JSON.toJSONString(resIds));
		}
		if(StringUtils.equals("detail", mode)){
			sysRole = sysRoleService.selectById(id);
			List<SysUser> users = sysRoleService.findUserByRoleId(id);
			List<SysResource> resources = sysRoleService.findResourceByRoleId(id);
			model.addAttribute("users", users)
				.addAttribute("resources", resources);
		}
		model.addAttribute("sysrole", sysRole);
		return mode.equals("detail")?"sys/role/role-detail":"sys/role/role-save";
	}
	

}
