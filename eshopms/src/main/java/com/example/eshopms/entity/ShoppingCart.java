package com.example.eshopms.entity;

import java.util.HashMap;

public class ShoppingCart {
    private HashMap<Commodity,Integer> commodityNumHashMap;

    public HashMap<Commodity, Integer> getCommodityNumHashMap() {
        return commodityNumHashMap;
    }

    public void setCommodityNumHashMap(HashMap<Commodity, Integer> commodityNumHashMap) {
        this.commodityNumHashMap = commodityNumHashMap;
    }
}
