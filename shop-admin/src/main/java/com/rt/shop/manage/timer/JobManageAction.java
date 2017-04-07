package com.rt.shop.manage.timer;
 
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.BargainGoods;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.GoodsCart;
import com.rt.shop.entity.Message;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.StoreCart;
import com.rt.shop.entity.SysConfig;
import com.rt.shop.entity.Template;
import com.rt.shop.entity.User;
import com.rt.shop.entity.ZtcGoldLog;
import com.rt.shop.lucene.LuceneThread;
import com.rt.shop.lucene.LuceneVo;
import com.rt.shop.service.IBargainGoodsService;
import com.rt.shop.service.IGoodsCartService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IMessageService;
import com.rt.shop.service.IStoreCartService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.ITemplateService;
import com.rt.shop.service.IUserService;
import com.rt.shop.service.IZtcGoldLogService;
 
 @Component("shop_job")
 @Transactional
 public class JobManageAction
 {
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IZtcGoldLogService ztcGoldLogService;
 
   @Autowired
   private IStoreService storeService;
 
   @Autowired
   private ITemplateService templateService;
 
   @Autowired
   private IMessageService messageService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IBargainGoodsService bargainGoodsService;
 
   @Autowired
   private IStoreCartService storeCartService;
 
   @Autowired
   private IGoodsCartService goodsCartService;
 
   public void execute()
   {
    
     Goods sGoods=new Goods();
     sGoods.setZtc_status(Integer.valueOf(2));
     List<Goods> goods_audit_list = this.goodsService.selectList(sGoods);
     //  "select obj from Goods obj where obj.ztc_status=:ztc_status", 
     
     for (Goods goods : goods_audit_list) {
       if (goods.getZtc_begin_time().before(new Date())) {
         goods.setZtc_dredge_price(goods.getZtc_price());
         goods.setZtc_status(3);
         this.goodsService.updateSelectiveById(goods);
       }
     }
     Goods ssGoods=new Goods();
     ssGoods.setZtc_status(Integer.valueOf(3));
     goods_audit_list = this.goodsService.selectList(ssGoods);
     ZtcGoldLog log;
     for (Goods goods : goods_audit_list) {
       if (goods.getZtc_gold() > goods.getZtc_price()) {
         goods.setZtc_gold(goods.getZtc_gold() - goods.getZtc_price());
         goods.setZtc_dredge_price(goods.getZtc_price());
         this.goodsService.updateSelectiveById(goods);
         log = new ZtcGoldLog();
         log.setAddTime(new Date());
         log.setZgl_content("直通车消耗金币");
         log.setZgl_gold(goods.getZtc_price());
         log.setZgl_goods_id(goods.getId());
         log.setZgl_type(1);
         this.ztcGoldLogService.insertSelective(log);
       } else {
         goods.setZtc_status(0);
         goods.setZtc_dredge_price(0);
         goods.setZtc_pay_status(0);
         goods.setZtc_apply_time(null);
         this.goodsService.updateSelectiveById(goods);
       }
     }
 
    String sql="where validity is not null";
     List<Store> stores = this.storeService.selectList(sql,null);
     Message msg;
     for (Store store : stores) {
       if (store.getValidity().before(new Date())) {
         store.setStore_status(4);
         this.storeService.updateSelectiveById(store);
         Template sTemplate=new Template();
         sTemplate.setMark("msg_toseller_store_auto_closed_notify");
         Template template = this.templateService.selectOne(sTemplate);
         if ((template != null) && (template.getOpen())) {
        	 User sUser=new User();
        	 sUser.setUserName("admin");
           msg = new Message();
           msg.setAddTime(new Date());
           msg.setContent(template.getContent());
           msg.setFromUser_id(this.userService.selectOne(sUser).getId());
           msg.setStatus(0);
           msg.setTitle(template.getTitle());
           User ssUser=new User();
           ssUser.setStore_id(store.getId());
           msg.setToUser_id(userService.selectOne(ssUser).getId());
           msg.setType(0);
           this.messageService.insertSelective(msg);
         }
       }
     }
 
     Goods sssGoods=new Goods();
     sssGoods.setZtc_status(Integer.valueOf(0));
     List<Goods> goods_list = this.goodsService.selectList(sssGoods);
     
     List goods_vo_list = new ArrayList();
     for (Goods goods : goods_list) {
       LuceneVo vo = new LuceneVo();
       vo.setVo_id(goods.getId());
       vo.setVo_title(goods.getGoods_name());
       vo.setVo_content(goods.getGoods_details());
       vo.setVo_type("goods");
       vo.setVo_store_price(CommUtil.null2Double(goods.getStore_price()));
       vo.setVo_add_time(goods.getAddTime().getTime());
       vo.setVo_goods_salenum(goods.getGoods_salenum());
       goods_vo_list.add(vo);
     }
     String goods_lucene_path = System.getProperty("user.dir") + 
       File.separator + "luence" + File.separator + "goods";
     File file = new File(goods_lucene_path);
     if (!file.exists()) {
       CommUtil.createFolder(goods_lucene_path);
     }
     LuceneThread goods_thread = new LuceneThread(goods_lucene_path, 
       goods_vo_list);
     goods_thread.run();
     SysConfig config = this.configService.getSysConfig();
     config.setLucene_update(new Date());
     this.configService.updateSelectiveById(config);
 
    
     Calendar cal = Calendar.getInstance();
     cal.add(6, -1);
    
     BargainGoods sBargainGoods=new BargainGoods();
     sBargainGoods.setBg_time(CommUtil.formatDate(CommUtil.formatShortDate(cal
       .getTime())));
     List<BargainGoods> bgs = this.bargainGoodsService.selectList(sBargainGoods);
//       "select obj from BargainGoods obj where obj.bg_time=:bg_time", 
//       params, -1, -1);
     for (BargainGoods bg : bgs) {
       bg.setBg_status(-2);
       this.bargainGoodsService.updateSelectiveById(bg);
       Goods goods = bg.getBg_goods();
       goods.setBargain_status(0);
       goods.setGoods_current_price(goods.getStore_price());
       this.goodsService.updateSelectiveById(goods);
     }
 
     BargainGoods ssBargainGoods=new BargainGoods();
     ssBargainGoods.setBg_time(CommUtil.formatDate(CommUtil.formatShortDate(cal
       .getTime())));
     ssBargainGoods.setBg_status(Integer.valueOf(1));
      bgs = this.bargainGoodsService.selectList(ssBargainGoods);
    
      
     Goods goods;
     for (BargainGoods bg : bgs) {
       goods = bg.getBg_goods();
       goods.setBargain_status(2);
       goods.setGoods_current_price(bg.getBg_price());
       this.goodsService.updateSelectiveById(goods);
     }
 
    
     cal = Calendar.getInstance();
     cal.add(6, -1);
     String sql1="where user_id is null and addTime<='"+cal.getTime()+"' and sc_status="+Integer.valueOf(0);
     List<StoreCart> cart_list = this.storeCartService.selectList(sql1,null);
     //  "select obj from StoreCart obj where obj.user.id is null and obj.addTime<=:addTime and obj.sc_status=:sc_status", 
     for (StoreCart cart : cart_list) {
       for (GoodsCart gc : cart.getGcs()) {
         gc.getGsps().clear();
         this.goodsCartService.deleteById(gc.getId());
       }
       this.storeCartService.deleteById(cart.getId());
     }
 
    
     cal = Calendar.getInstance();
     cal.add(6, -7);

     String sql2="where user_id is null and addTime<='"+cal.getTime()+"' and sc_status="+Integer.valueOf(0);
     cart_list = this.storeCartService.selectList(sql2,null);

     for (StoreCart cart : cart_list) {
       for (GoodsCart gc : cart.getGcs()) {
         gc.getGsps().clear();
         this.goodsCartService.deleteById(gc.getId());
       }
       this.storeCartService.deleteById(cart.getId());
     }
   }
 }


 
 
 