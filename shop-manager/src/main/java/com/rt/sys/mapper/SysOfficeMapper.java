package com.rt.sys.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.rt.sys.entity.SysOffice;

/**
 *
 * SysOffice 表数据库控制层接口
 *
 */
public interface SysOfficeMapper extends AutoMapper<SysOffice> {
public List<SysOffice> findPageInfo(Map<String, Object> params);
	
	public boolean deleteOfficeByRootId(Long id);

	public boolean updateParentIds(SysOffice sysOffice);
	
	public SysOffice findOfficeCompanyIdByDepId(Long depId);
	
	//得到用户数据范围
	public List<Long> findUserDataScopeByUserId(Long userId);
	
	public List<Long> findOfficeIdsByRootId(Long rootId);

}