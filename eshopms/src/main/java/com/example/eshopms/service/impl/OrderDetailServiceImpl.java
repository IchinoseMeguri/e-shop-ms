package com.example.eshopms.service.impl;

import com.example.eshopms.entity.OrderDetail;
import com.example.eshopms.dao.OrderDetailDao;
import com.example.eshopms.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service("orderDetailService")
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    OrderDetailDao orderDetailDao;

    @Override
    public List<OrderDetail> getOrderDetailList() {
        return orderDetailDao.getOrderDetailList();
    }

    @Override
    public List<OrderDetail> getOrderDetailListByOrderId(int id) {
        return orderDetailDao.getOrderDetailListByOrderId(id);
    }

    @Override
    public List<OrderDetail> getOrderDetailListByCommodityId(int id) {
        return orderDetailDao.getOrderDetailListByCommodityId(id);
    }

    @Override
    public OrderDetail getOrderDetailById(int id) {
        return orderDetailDao.getOrderDetailById(id);
    }

    @Override
    public boolean addOrderDetail(OrderDetail orderDetail) {
        int result= orderDetailDao.addOrderDetail(orderDetail);
        return result>0;
    }

    @Override
    public boolean deleteOrderDetail(int id) {
        int result= orderDetailDao.deleteOrderDetail(id);
        return result>0;
    }

    @Override
    public boolean modifyOrderDetail(OrderDetail orderDetail) {
        int result= orderDetailDao.modifyOrderDetail(orderDetail);
        return result>0;
    }
}
