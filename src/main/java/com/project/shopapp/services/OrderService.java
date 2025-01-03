package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderStatus;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        // 1. Kiem tra ton tai user //
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(()-> new DataNotFoundException("User not found"));
        // 2. Them don hang cho user da ton tai //
        // 2.1 convert OrderDTO to Order chuan bi luu xuong database//
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        // Dung thu vien ModalMapper //
        Order order = new Order();
        // Cap nhat ca truong ben OrderDTO sang Order //
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);

        // Shipping date >= Order date
        Date shippingdate = order.getShippingDate() == null ? new Date() : order.getShippingDate();
        order.setShippingDate(shippingdate);
        if(shippingdate.before(order.getOrderDate())) {
            throw new RuntimeException("Shipping Date is after Order Date");
        }
        order.setActive(true);
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse getOrder(Long id) throws DataNotFoundException {
        // 1. lay order theo id //
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Order not found !"));
        return modelMapper.map(existingOrder, OrderResponse.class);
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        // 1. Lay ra don hang theo userId //
        Order order = orderRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Order not found !"));
        // 2. Check valid user //
        User existingUser = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(()->new DataNotFoundException("User not found !"));
        // 3. Convert OrderDTO to Order //
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);
        order.setActive(true);
        order.setStatus(OrderStatus.PENDING);
        // 4. Cap nhat order's user //
        Order updateOrder = orderRepository.save(order);
        // 5 Convert from Order to OrderResponse and return //
        return modelMapper.map(updateOrder, OrderResponse.class);
    }

    @Override
    public void deleteOrder(Long id) throws DataNotFoundException {
        // Xoa mem khong xoa cung //
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Order not found !"));
        existingOrder.setActive(false);
        orderRepository.save(existingOrder);
    }

    @Override
    public List<OrderResponse> getAllOrders(long userId) {
        Stream<OrderResponse> list = orderRepository.findByUserId(userId)
                 .stream()
                 .map(order -> modelMapper.map(order, OrderResponse.class));
        return list.toList();
    }
}
