package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.responses.OrderResponse;

import java.util.List;


public interface IOrderService {

    OrderResponse createOrder(OrderDTO orderDTO) throws DataNotFoundException;

    OrderResponse getOrder(Long id) throws DataNotFoundException;

    OrderResponse updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;

    void deleteOrder(Long id) throws DataNotFoundException;

    List<OrderResponse> getAllOrders(long userId);
}
