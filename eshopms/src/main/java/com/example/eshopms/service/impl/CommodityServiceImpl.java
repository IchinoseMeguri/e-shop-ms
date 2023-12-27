package com.example.eshopms.service.impl;

import com.example.eshopms.entity.Commodity;
import com.example.eshopms.dao.CommodityDao;
import com.example.eshopms.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service("commodityService")
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    CommodityDao commodityDao;

    @Override
    public List<Commodity> getCommodityList() {
        return commodityDao.getCommodityList();
    }

    @Override
    public Commodity getCommodityById(int id) {
        return commodityDao.getCommodityById(id);
    }

    @Override
    public List<Commodity> getCommodityByName(String name) {
        return commodityDao.getCommodityByName(name);
    }

    @Override
    public boolean addCommodity(Commodity commodity) {
        int result= commodityDao.addCommodity(commodity);
        return result>0;
    }

    @Override
    public boolean deleteCommodity(int id) {
        int result= commodityDao.deleteCommodity(id);
        return result>0;
    }

    @Override
    public boolean modifyCommodity(Commodity commodity) {
        int result= commodityDao.modifyCommodity(commodity);
        return result>0;
    }
}
