package com.example.eshopms.dao;

import com.example.eshopms.entity.OrderDetail;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailDao {
    @Select("select * from order_detail")
    public List<OrderDetail> getOrderDetailList();
    @Select("select * from order_detail where orderid=#{orderid}")
    public List<OrderDetail> getOrderDetailListByOrderId(int orderid);
    @Select("select * from order_detail where commodityid=#{commodityid}")
    public List<OrderDetail> getOrderDetailListByCommodityId(int commodityid);
    @Select("select * from order_detail where id=#{id}")
    public OrderDetail getOrderDetailById(int id);
    @Insert("insert into order_detail(orderid,commodityid,quantity,price) values(#{orderId},#{commodityId},#{quantity},#{price})")
    public int addOrderDetail(OrderDetail orderDetail);
    @Delete("delete from order_detail where id=#{id}")
    public int deleteOrderDetail(int id);
    @Update("update order_detail set orderid=#{orderId},commodityid=#{commodityId},quantity=#{quantity},price=#{price} where id=#{id}")
    public int modifyOrderDetail(OrderDetail orderDetail);
}
