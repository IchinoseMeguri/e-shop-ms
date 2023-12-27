package com.example.eshopms.service.impl;

import com.example.eshopms.entity.Order;
import com.example.eshopms.dao.OrderDao;
import com.example.eshopms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Override
    public List<Order> getOrderList() {
        return orderDao.getOrderList();
    }

    @Override
    public Order getOrderById(int id) {
        return orderDao.getOrderById(id);
    }

    @Override
    public List<Order> getOrderListByUser(int user) {
        return orderDao.getOrderListByUser(user);
    }

    @Override
    public boolean addOrder(Order order) {
        int result= orderDao.addOrder(order);
        return result>0;
    }

    @Override
    public boolean deleteOrder(int id) {
        int result= orderDao.deleteOrder(id);
        return result>0;
    }

    @Override
    public boolean modifyOrder(Order order) {
        int result= orderDao.modifyOrder(order);
        return result>0;
    }
}
