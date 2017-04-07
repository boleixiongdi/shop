 package com.rt.shop.view.web.tools;
 
 import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.GroupGoods;
import com.rt.shop.service.IGroupGoodsService;
import com.rt.shop.service.IGroupService;
 
 @Component
 public class GroupViewTools
 {
 
   @Autowired
   private IGroupService groupService;
 
   @Autowired
   private IGroupGoodsService groupGoodsService;
 
   public List<GroupGoods> query_goods(String group_id, int count)
   {
     List<GroupGoods> list = new ArrayList();
     GroupGoods sGroupGoods=new GroupGoods();
     sGroupGoods.setGroup_id(CommUtil.null2Long(group_id));
    
     list = this.groupGoodsService.selectList(sGroupGoods, "addTime desc");
   //    "select obj from GroupGoods obj where obj.group.id=:group_id order by obj.addTime desc", 
     return list;
   }
 }


 
 
 