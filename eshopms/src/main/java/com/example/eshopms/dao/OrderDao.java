package com.example.eshopms.dao;

import com.example.eshopms.entity.Order;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao {
    @Select("select * from order_list")
    public List<Order> getOrderList();
    @Select("select * from order_list where id=#{id}")
    public Order getOrderById(int id);
    @Select("select * from order_list where userid=#{user}")
    public List<Order> getOrderListByUser(int user);
    @Insert("insert into order_list(userid,time,price) values(#{userId},#{time},#{price})")
    public int addOrder(Order order);
    @Delete("delete from order_list where id=#{id}")
    public int deleteOrder(int id);
    @Update("update order_list set userid=#{userId},time=#{time},price=#{price} where id=#{id}")
    public int modifyOrder(Order order);
}
