package com.rt.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.rt.sys.entity.SysOffice;
import com.rt.sys.entity.SysRole;
import com.rt.sys.mapper.SysOfficeMapper;
import com.rt.sys.mapper.SysRoleMapper;
import com.rt.sys.service.ISysOfficeService;
import com.rt.sys.service.impl.support.BaseServiceImpl;
import com.rt.sys.util.SysUserUtils;

/**
 *
 * SysOffice 表数据服务层接口实现类
 *
 */
@Service
public class SysOfficeServiceImpl extends BaseServiceImpl<SysOfficeMapper, SysOffice> implements ISysOfficeService {

	@Resource
	private SysOfficeMapper sysOfficeMapper;
	@Resource
	private SysRoleMapper sysRoleMapper;
	
	/**
	 *新增或更新SysOffice
	 */
	public boolean saveSysOffice(SysOffice sysOffice){
		boolean count=false ;
		//新的parentIds
		sysOffice.setParent_ids(sysOffice.getParent_ids()+sysOffice.getParent_id()+","); 
		int grade = sysOffice.getParent_ids().split(",").length;
		sysOffice.setGrade(String.valueOf(grade));
		if(null == sysOffice.getId()){
			count = this.insertSelective(sysOffice);
			//自动赋权
			Long roleId = SysUserUtils.autoAddOfficeToRole();
			if(roleId != null){
				SysRole sysRole = new SysRole();
				sysRole.setId(roleId);
				sysRole.setOfficeIds(new Long[]{sysOffice.getId()});
				sysRoleMapper.insertRoleOffice(sysRole);
			}
		}else{
			//getParentIds() 当前选择的父节点parentIds , getParentId()父节点的id
			//先更新parentId，此节点的parentIds以更新
			count = this.updateSelectiveById(sysOffice);
			//不移动节点不更新子节点的pids
			if(!StringUtils.equals(sysOffice.getOldParentIds(), sysOffice.getParent_ids())){
				sysOfficeMapper.updateParentIds(sysOffice); //批量更新子节点的parentIds
			}
		}
		SysUserUtils.clearCacheOffice(Lists.newArrayList(SysUserUtils.getCacheLoginUser().getId()));
		return count;
	}
	
	public boolean deleteOfficeByRootId(Long id){
//		int roleCount = this.beforeDeleteTreeStructure(id, "officeId", SysRole.class,SysOffice.class);
//		if(roleCount<0) return -1;
//		int userOfficeCount = this.beforeDeleteTreeStructure(id, "officeId", SysUser.class,SysOffice.class);
//		int userCompanyCount = this.beforeDeleteTreeStructure(id, "companyId",  SysUser.class,SysOffice.class);
//		if(userOfficeCount+userCompanyCount<0) return -1;
		SysUserUtils.clearCacheOffice(Lists.newArrayList(SysUserUtils.getCacheLoginUser().getId()));
		return sysOfficeMapper.deleteOfficeByRootId(id);
	}
	
	/**
	 * 根据用户id查询用户的数据范围
	 */
	public List<Long> findUserDataScopeByUserId(Long userId){
		return sysOfficeMapper.findUserDataScopeByUserId(userId);
	}
	
	/**
	 * 根据根节点查询自身及其子孙节点
	 */
	public List<Long> findOfficeIdsByRootId(Long rootId){
		return sysOfficeMapper.findOfficeIdsByRootId(rootId);
	}
	
	
	/**
	 * 根据条件分页查询SysOffice列表
	 * @param {"pageNum":"页码","pageSize":"条数","isCount":"是否生成count sql",......}
	 */
	public Page<SysOffice> findPageInfo(Map<String,Object> params) {
	//	params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("t1", null));
        Page page = null;
        List<SysOffice> list=sysOfficeMapper.selectByMap(params);
        
        page.setRecords(this.baseMapper.selectPage(page, new EntityWrapper(
				new SysOffice() , null)));
		return page;
	}
	
}