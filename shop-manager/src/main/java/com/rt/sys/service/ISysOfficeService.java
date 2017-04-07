package com.rt.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.framework.service.ISuperService;
import com.baomidou.mybatisplus.plugins.Page;
import com.rt.sys.entity.SysOffice;

/**
 *
 * SysOffice 表数据服务层接口
 *
 */
public interface ISysOfficeService extends ISuperService<SysOffice> {

	/**
	 *新增或更新SysOffice
	 */
	public boolean saveSysOffice(SysOffice sysOffice);
	
	public boolean deleteOfficeByRootId(Long id);
	
	/**
	 * 根据用户id查询用户的数据范围
	 */
	public List<Long> findUserDataScopeByUserId(Long userId);
	
	/**
	 * 根据根节点查询自身及其子孙节点
	 */
	public List<Long> findOfficeIdsByRootId(Long rootId);
	
	
	/**
	 * 根据条件分页查询SysOffice列表
	 * @param {"pageNum":"页码","pageSize":"条数","isCount":"是否生成count sql",......}
	 */
	public Page<SysOffice> findPageInfo(Map<String,Object> params) ;
}