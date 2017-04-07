 package com.rt.shop.view.web.tools;
 
 import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.GoodsClass;
import com.rt.shop.entity.GoodsSpecProperty;
import com.rt.shop.entity.GoodsSpecification;
import com.rt.shop.entity.UserGoodsClass;
import com.rt.shop.service.IGoodsClassService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IGoodsSpecPropertyService;
import com.rt.shop.service.IGoodsSpecificationService;
import com.rt.shop.service.IUserGoodsClassService;
import com.rt.shop.util.SecurityUserHolder;
 
 @Component
 public class GoodsViewTools
 {
 
   @Autowired
   private IGoodsService goodsService;
   @Autowired
   private IGoodsSpecPropertyService goodsSpecPropertyService;
 
   @Autowired
   private IGoodsClassService goodsClassService;
 
   @Autowired
   private IUserGoodsClassService userGoodsClassService;
 
   @Autowired
   private IGoodsSpecificationService goodsSpecificationService;
   
   @SuppressWarnings("unchecked")
public List<GoodsSpecification> generic_spec(String id)
   {
     List specs = new ArrayList();
     if ((id != null) && (!id.equals(""))) {
       Goods goods = this.goodsService.selectById(Long.valueOf(Long.parseLong(id)));
       GoodsSpecProperty sGoodsSpecProperty=new GoodsSpecProperty();
     List<GoodsSpecProperty> gspList=goodsSpecPropertyService.selectGspByGoodsId(goods.getId());
     if(gspList!=null && gspList.size()>0){
    	 for (GoodsSpecProperty gsp : gspList) {
             GoodsSpecification spec = goodsSpecificationService.selectById(gsp.getSpec_id1());
             if (!specs.contains(spec)) {
               specs.add(spec);
             }
           } 
    	 Collections.sort(specs, new Comparator()
         {
           public int compare(Object gs1, Object gs2)
           {
             return (((GoodsSpecification)gs1).getSequence()) - (((GoodsSpecification)gs2).getSequence());
           }
        });
       }    
     }
     
     return specs;
   }
 
   public List<UserGoodsClass> query_user_class(String pid)
   {
     List list = new ArrayList();
     if ((pid == null) || (pid.equals(""))) {
       
       //  .query("select obj from UserGoodsClass obj where obj.parent.id is null and obj.user.id = :uid order by obj.sequence asc", 
      
       list = this.userGoodsClassService.selectList("where user_id='"+SecurityUserHolder.getCurrentUser().getId()+"' and  parent_id is null", "sequence asc");
     } else {
       
       UserGoodsClass sUserGoodsClass=new UserGoodsClass();
       sUserGoodsClass.setParent_id(Long.valueOf(Long.parseLong(pid)));
       sUserGoodsClass.setUser_id(SecurityUserHolder.getCurrentUser().getId());
       list = this.userGoodsClassService.selectList(sUserGoodsClass, "sequence asc");
        // .query("select obj from UserGoodsClass obj where obj.parent.id=:pid and obj.user.id = :uid order by obj.sequence asc", 
     }
     return list;
   }
 
   public List<Goods> query_with_gc(String gc_id, int count)
   {
     List list = new ArrayList();
     GoodsClass gc = this.goodsClassService.selectById(
       CommUtil.null2Long(gc_id));
     if (gc != null) {
       Set ids = genericIds(gc);
       Map params = new HashMap();
       params.put("ids", ids);
       params.put("goods_status", Integer.valueOf(0));
       list = null;
       //  .query("select obj from Goods obj where obj.gc.id in (:ids) and obj.goods_status=:goods_status order by obj.goods_click desc", 
         //params, 0, count);
     }
     return list;
   }
 
//   private Set<Long> genericIds(GoodsClass gc) {
//     Set ids = new HashSet();
//     ids.add(gc.getId());
//     for (GoodsClass child : gc.getChilds()) {
//       Set<Long> cids = genericIds(child);
//       for (Long cid : cids) {
//         ids.add(cid);
//       }
//       ids.add(child.getId());
//     }
//     return ids;
//   }
   private Set<Long> genericIds(GoodsClass gc) {
	   if(gc!=null){
		   Set ids = new HashSet();
		     ids.add(gc.getId());
		     GoodsClass sGoodsClass=new GoodsClass();
		     sGoodsClass.setParent_id(gc.getId());
		     List<GoodsClass> childs=goodsClassService.selectList(sGoodsClass);
		     if(childs!=null && childs.size()>0){
		    	 for (GoodsClass child : childs) {
				       Set<Long> cids = genericIds(child);
				       for (Long cid : cids) {
				         ids.add(cid);
				       }
				       ids.add(child.getId());
				     }
		     }
		     
		     return ids;
	   }
	return null;
	}
   public List<Goods> sort_sale_goods(String store_id, int count) {
     List list = new ArrayList();
     Goods sGoods=new Goods();
     sGoods.setGoods_status(Integer.valueOf(0));
     sGoods.setGoods_store_id(CommUtil.null2Long(store_id));
     
     list = this.goodsService.selectPage(new Page<Goods>(0, count), sGoods, "goods_salenum desc").getRecords();
     //  .query("select obj from Goods obj where obj.goods_store.id=:store_id and obj.goods_status=:goods_status order by obj.goods_salenum desc", 
     return list;
   }
 
   public List<Goods> sort_collect_goods(String store_id, int count) {
     List list = new ArrayList();
     Goods sGoods=new Goods();
     sGoods.setGoods_status(Integer.valueOf(0));
     sGoods.setGoods_store_id(CommUtil.null2Long(store_id));
     
     list = this.goodsService.selectPage(new Page<Goods>(0, count), sGoods, "goods_collect desc").getRecords();
     return list;
   }
 
   public List<Goods> query_combin_goods(String id) {
//     return this.goodsService.selectById(CommUtil.null2Long(id))
//       .getCombin_goods();
     //shopping_goods_combin
	   return null;
	   //TODO
    
   }
 }


 
 
 