 package com.rt.shop.view.web.action;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IAreaService;
import com.rt.shop.service.IBargainGoodsService;
import com.rt.shop.service.IGoodsService;

@ContextConfiguration(locations = { "classpath*:/config/spring/web-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestServiceTest1 {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceTest1.class);
	@Resource
	IAreaService areaService;
	@Resource
	IBargainGoodsService bargainGoodsService;
	
	@Resource
	IGoodsService goodsServer;
	@Resource
	IAccessoryService accessoryService;

	
	
	@Test
	public void transferTest() throws Exception {
//	List<Area> dd=	areaService.selectList(null);
//	System.out.println(dd);
	//System.out.println(bargainGoodsService.selectBargainGoodsPage(null, null));
//	List<Goods> xx =bargainGoodsService.selectBargainGoodsPage(null, null);
//	System.out.println(xx);
//		Goods g=new Goods();
//		g.setId(20L);
//		g.setGoods_name("xxxx");
//		goodsServer.updateSelectiveById(g);System.out.println(g.getGoods_name());
	//	System.out.println(goodsServer.selectList(null));
		System.out.println(accessoryService);
		System.out.println(accessoryService.selectById(1L));
	}
	
	public static void main(String[] args) {
		for(int i=0;i<1000;i++){
		//	System.out.println((int)(Math.random()*10000));
		}
		
	}
}
