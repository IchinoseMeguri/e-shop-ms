package com.example.eshopms.controller;

import com.example.eshopms.entity.Commodity;
import com.example.eshopms.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminCommodityController {

    @Autowired
    CommodityService commodityService;

    @RequestMapping(value = "/deletecommodity",method = RequestMethod.GET)
    public int deleteCommodity(int id){
        boolean b= commodityService.deleteCommodity(id);
        return b?1:0;
    }

    @RequestMapping(value = "/addcommodity",method = RequestMethod.GET)
    public int addCommodity(String name,float price,int quantity){
        List<Commodity> commodityList=commodityService.getCommodityByName(name);
        if(commodityList.size()>0) return -1;
        Commodity commodity=new Commodity();
        commodity.setName(name);
        commodity.setPrice(price);
        commodity.setQuantity(quantity);
        boolean b= commodityService.addCommodity(commodity);
        return b?1:0;
    }

    @RequestMapping(value = "/updatecommodity",method = RequestMethod.GET)
    public int updateCommodity(int id,String name,float price,int quantity){
        List<Commodity> commodityList=commodityService.getCommodityByName(name);
        if(commodityList.size()>0)
            if(commodityList.get(0).getId()!=id) return -1;
        Commodity commodity= commodityService.getCommodityById(id);
        commodity.setName(name);
        commodity.setPrice(price);
        commodity.setQuantity(quantity);
        boolean b= commodityService.modifyCommodity(commodity);
        return b?1:0;
    }
}
