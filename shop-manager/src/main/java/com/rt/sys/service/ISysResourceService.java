package com.rt.sys.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.framework.service.ISuperService;
import com.baomidou.mybatisplus.plugins.Page;
import com.rt.sys.entity.SysResource;
import com.rt.sys.entity.SysUser;

/**
 *
 * SysResource 表数据服务层接口
 *
 */
public interface ISysResourceService extends ISuperService<SysResource> {
	/**
	 * 新增or更新SysResource
	 */
	public int saveSysResource(SysResource sysResource);

	/**
	 * 根据父id删除自身已经所有子节点
	 * 
	 * @param id
	 * @return
	 */
	public int deleteResourceByRootId(Long id);

	/**
	 * 根据用户id得到用户持有的资源
	 * @param userId
	 * @return
	 */
	public List<SysResource> findUserResourceByUserId(SysUser sysUser) ;
	public List<SysResource> findUserResourceByUserIdAndPid(Long userId,Long parentId) ;
	/**
	 * 菜单管理分页显示筛选查询
	 * @param params {"name":"菜单名字","id":"菜单id"}
	 * @return
	 */
	public Page<SysResource> findPageInfo(Map<String, Object> params) ;

	/**
	 * 获取全部资源map形式
	 * @return
	 */
	public LinkedHashMap<String, SysResource> getAllResourcesMap() ;

	/**
	 * 获取全部资源list形式
	 * @return
	 */
	public List<SysResource> getAllResourcesList() ;
	
	/**
	 * 获取菜单树
	 */
	public List<SysResource> getMenuTree();

	public List<SysResource> selectTop(long parentId) ;

	public List<SysResource> findUserResourceByPid(Long id) ;

}