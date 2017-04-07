

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.entity.Area;
import com.rt.shop.entity.OrderForm;
import com.rt.shop.service.IAreaService;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.IRoleService;
import com.rt.shop.service.IUserService;

@ContextConfiguration(locations = { "classpath*:/config/spring/web-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestServiceTest {

	
	@Autowired
	IAreaService areaService;
	@Autowired
	IRoleService roleService;
	
	
	@Autowired
	IUserService userService;
	@Autowired
	IOrderFormService orderFormService;

	
	
	@Test
	public void transferTest() throws Exception {
//	List<Area> dd=	areaService.selectList(null);
//	System.out.println(dd);
//		String sql="where (display=true and type='ADMIN') or type='BUYER";
//		List<Role> roles = this.roleService.selectList(sql, null);
//		System.out.println(roles);
		
//		String className="商城新闻";
//		String id="1";
//		 String sql="where className='"+className+"' and id!="+CommUtil.null2Long(id);
//	     List gcs = this.articleClassService.selectList(sql,null);
//		System.out.println(gcs);
//		Set<Long> s=new HashSet();
//		s.add(1L);
//		s.add(2L);
//		String x=CommUtil.exactSetToString(s);
//		 String sql="where id in ("+x+") ";
//		 List ss = this.userService.selectList(sql,null);
//		 System.out.println(ss);
//		 
//		 List<Long> s1=new ArrayList<Long>();
//			s1.add(1L);
//			s1.add(2L);
//			String x1=CommUtil.exactListToString(s1);
//			 String sql1="where id in ("+x1+") ";
//			 List ss1 = this.userService.selectList(sql1,null);
//			 System.out.println(ss1);
		OrderForm o=new OrderForm();
		o.setOrder_status(10);
		List<OrderForm> l =orderFormService.selectSumPriceByUserId(o);
		for(OrderForm f : l){
			System.out.println(f.getUser_id()+","+f.getTotalPrice());
		}
	
	}
	
	@Test
	public void transferTestMap() throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("areaname", "北京市");
//	List<Area> dd=	areaMapper.selectByMap(map);
//	System.out.println(dd);
	}
	
	@Test
	public void transferTestLike() throws Exception {
		Page<Area> page = new Page<Area>(1, 2);
		EntityWrapper<Area> ew = new EntityWrapper<Area>(new Area());
		/**

		 * 查询条件，支持 sql 片段

		 */
		ew.setSqlSegment(" where areaname like '北京%'");
//		List<Area> paginList = areaMapper.selectPage(page, ew);
//	
//		System.out.println(paginList);
		String sql=" where  areaname like '北京%'";
		List<Area> ll=areaService.selectList(sql,null);
		System.out.println(ll);
	}
	@Test
	public void transferTestBatch() throws Exception {
		List<Long> idList = new ArrayList<Long>();
		idList.add(4521987L);
		idList.add(4521988L);
	List<Area> dd=	areaService.selectBatchIds(idList);
	System.out.println(dd);
	}
	
	
}
