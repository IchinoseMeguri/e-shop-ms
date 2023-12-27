package com.example.eshopms.dao;

import com.example.eshopms.entity.Commodity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityDao {
    @Select("select * from commodity")
    public List<Commodity> getCommodityList();
    @Select("select * from commodity where id=#{id}")
    public Commodity getCommodityById(int id);
    @Select("select * from commodity where name=#{name}")
    public List<Commodity> getCommodityByName(String name);
    @Insert("insert into commodity(name,price,quantity) values(#{name},#{price},#{quantity})")
    public int addCommodity(Commodity commodity);
    @Delete("delete from commodity where id=#{id}")
    public int deleteCommodity(int id);
    @Update("update commodity set name=#{name},price=#{price},quantity=#{quantity} where id=#{id}")
    public int modifyCommodity(Commodity commodity);
}
