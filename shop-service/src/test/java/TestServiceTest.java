

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rt.shop.entity.Area;
import com.rt.shop.entity.Goods;
import com.rt.shop.service.IAreaService;
import com.rt.shop.service.IBargainGoodsService;

@ContextConfiguration(locations = { "classpath*:applicationContext-configuration.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceTest.class);
	@Resource
	IAreaService areaService;
	@Resource
	IBargainGoodsService bargainGoodsService;

	
	
	@Test
	public void transferTest() throws Exception {
//	List<Area> dd=	areaService.selectList(null);
//	System.out.println(dd);
//	
//	List<Goods> xx =bargainGoodsService.selectBargainGoodsPage(null, null).getRecords();
//	System.out.println(xx);
	}
	
	
}
