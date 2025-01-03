package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {

    OrderDetail getOrderDetailById(long id) throws DataNotFoundException;

    OrderDetail updateOrderDetail(long id, OrderDetailDTO newOrderDetailDTO) throws DataNotFoundException;

    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO);

    void deleteOrderDetail(long id);

    List<OrderDetail> getAllOrderDetails(long orderId);
}
