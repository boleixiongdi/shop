package com.rt.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rt.shop.common.constant.CacheConstans;
import com.rt.shop.common.ehcache.EHCacheUtil;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.SysConfig;
import com.rt.shop.mapper.AccessoryMapper;
import com.rt.shop.mapper.SysconfigMapper;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.impl.support.BaseServiceImpl;

/**
 *
 * Sysconfig 表数据服务层接口实现类
 *
 */
@Service
public class SysconfigServiceImpl extends BaseServiceImpl<SysconfigMapper, SysConfig> implements ISysConfigService {
	 
	 @Autowired
	 private AccessoryMapper accessoryMapper;
	 public SysConfig getSysConfig()
	   {
		 SysConfig sysConfig=baseMapper.selectById(1L);
		 Accessory goodsImage = accessoryMapper.selectById(sysConfig.getGoodsImage_id());
         sysConfig.setGoodsImage(goodsImage);
         
         Accessory storeImage = accessoryMapper.selectById(sysConfig.getStoreImage_id());
         sysConfig.setStoreImage(storeImage);
         
         Accessory mermberImage = accessoryMapper.selectById(sysConfig.getMemberIcon_id());
         sysConfig.setMemberIcon(mermberImage);
		 
         return sysConfig;
//		 SysConfig configs1=(SysConfig)EHCacheUtil.get(CacheConstans.SYSCONFIG_CACHE);
//		 if(configs1==null){
//			 List<SysConfig> configs=baseMapper.selectList(null);
//
//		     if ((configs != null) && (configs.size() > 0)) {
//		       SysConfig sc = (SysConfig)configs.get(0);
//		       if (sc.getUploadFilePath() == null) {
//		         sc.setUploadFilePath("upload");
//		       }
//		       if (sc.getSysLanguage() == null) {
//		         sc.setSysLanguage("zh_cn");
//		       }
//		       if ((sc.getWebsiteName() == null) || (sc.getWebsiteName().equals(""))) {
//		         sc.setWebsiteName("shopping");
//		       }
//		       if ((sc.getCloseReason() == null) || (sc.getCloseReason().equals(""))) {
//		         sc.setCloseReason("系统维护中...");
//		       }
//		       if ((sc.getTitle() == null) || (sc.getTitle().equals(""))) {
//		         sc.setTitle("zsCat多用户商城系统V2.0版");
//		       }
//		       if ((sc.getImageSaveType() == null) || 
//		         (sc.getImageSaveType().equals(""))) {
//		         sc.setImageSaveType("sidImg");
//		       }
//		       if (sc.getImageFilesize() == 0) {
//		         sc.setImageFilesize(1024);
//		       }
//		       if (sc.getSmallWidth() == 0) {
//		         sc.setSmallWidth(160);
//		       }
//		       if (sc.getSmallHeight() == 0) {
//		         sc.setSmallHeight(160);
//		       }
//		       if (sc.getMiddleWidth() == 0) {
//		         sc.setMiddleWidth(300);
//		       }
//		       if (sc.getMiddleHeight() == 0) {
//		         sc.setMiddleHeight(300);
//		       }
//		       if (sc.getBigHeight() == 0) {
//		         sc.setBigHeight(1024);
//		       }
//		       if (sc.getBigWidth() == 0) {
//		         sc.setBigWidth(1024);
//		       }
//		       if ((sc.getImageSuffix() == null) || (sc.getImageSuffix().equals(""))) {
//		         sc.setImageSuffix("gif|jpg|jpeg|bmp|png|tbi");
//		       }
//		    //   if(accessoryService.selectById(id))
//		       if (sc.getStoreImage_id() == null) {
//		         Accessory storeImage = new Accessory();
//		         storeImage.setPath("resources/style/common/images");
//		         storeImage.setName("store.jpg");
//		         sc.setStoreImage_id(storeImage.getId());
//		         sc.setStoreImage(storeImage);
//		       }
//		       if (sc.getGoodsImage_id() == null) {
//		         Accessory goodsImage = new Accessory();
//		         goodsImage.setPath("resources/style/common/images");
//		         goodsImage.setName("good.jpg");
//		         sc.setGoodsImage_id(goodsImage.getId());
//		         sc.setGoodsImage(goodsImage);
//		       }
//		       if (sc.getMemberIcon_id() == null) {
//		         Accessory memberIcon = new Accessory();
//		         memberIcon.setPath("resources/style/common/images");
//		         memberIcon.setName("member.jpg");
//		         sc.setMemberIcon_id(memberIcon.getId());
//		         sc.setMemberIcon(memberIcon);
//		       }
//		       if ((sc.getSecurityCodeType() == null) || 
//		         (sc.getSecurityCodeType().equals(""))) {
//		         sc.setSecurityCodeType("normal");
//		       }
//		       if ((sc.getWebsiteCss() == null) || (sc.getWebsiteCss().equals(""))) {
//		         sc.setWebsiteCss("default");
//		       }
//		       EHCacheUtil.set(CacheConstans.SYSCONFIG_CACHE, sc,CacheConstans.SYSCONFIG_CACHE_TIME);
//		       return sc;
//		     }
//		     SysConfig sc = new SysConfig();
//		     sc.setUploadFilePath("upload");
//		     sc.setWebsiteName("shopping");
//		     sc.setSysLanguage("zh_cn");
//		     sc.setTitle("zsCat多用户商城系统V2.0版");
//		     sc.setSecurityCodeType("normal");
//		     sc.setEmailEnable(true);
//		     sc.setCloseReason("系统维护中...");
//		     sc.setImageSaveType("sidImg");
//		     sc.setImageFilesize(1024);
//		     sc.setSmallWidth(160);
//		     sc.setSmallHeight(160);
//		     sc.setMiddleHeight(300);
//		     sc.setMiddleWidth(300);
//		     sc.setBigHeight(1024);
//		     sc.setBigWidth(1024);
//		     sc.setImageSuffix("gif|jpg|jpeg|bmp|png|tbi");
//		     sc.setComplaint_time(30);
//		     sc.setWebsiteCss("default");
//		     Accessory goodsImage = new Accessory();
//		     goodsImage.setPath("resources/style/common/images");
//		     goodsImage.setName("good.jpg");
//		     sc.setGoodsImage(goodsImage);
//		     
//		     
//		     sc.setGoodsImage_id(1L);
//		     Accessory storeImage = new Accessory();
//		     storeImage.setPath("resources/style/common/images");
//		     storeImage.setName("store.jpg");
//		     sc.setStoreImage_id(3L);
//		     sc.setStoreImage(storeImage);
//		     
//		     Accessory memberIcon = new Accessory();
//		     memberIcon.setPath("resources/style/common/images");
//		     memberIcon.setName("member.jpg");
//		     sc.setMemberIcon_id(2L);
//		     sc.setMemberIcon(memberIcon);
//		     baseMapper.insertSelective(sc);
//		     EHCacheUtil.set(CacheConstans.SYSCONFIG_CACHE, sc,CacheConstans.SYSCONFIG_CACHE_TIME);
//		     return sc;
//		 }else{
//			 return configs1;
//		 }
		 
	     
	   }
}