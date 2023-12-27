package com.example.eshopms.controller;

import com.example.eshopms.entity.Commodity;
import com.example.eshopms.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShoppingController {

    @Autowired
    CommodityService commodityService;

    @RequestMapping(value = "/getcommoditylist",method = RequestMethod.GET)
    public List<Commodity> getCommodityList(){
        return commodityService.getCommodityList();
    }

    @RequestMapping(value = "/getcommodityname",method = RequestMethod.GET)
    public String getCommodityName(int id){
        return commodityService.getCommodityById(id).getName();
    }
}
