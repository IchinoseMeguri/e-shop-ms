package com.example.eshopms.service;


import com.example.eshopms.entity.Commodity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CommodityService {

    public List<Commodity> getCommodityList();
    public Commodity getCommodityById(int id);
    public List<Commodity> getCommodityByName(String name);
    public boolean addCommodity(Commodity commodity);
    public boolean deleteCommodity(int id);
    public boolean modifyCommodity(Commodity commodity);
}
