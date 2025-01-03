package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.OrderDetailRepository;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderDetail getOrderDetailById(long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("OrderDetail not found"));
    }

    @Override
    public OrderDetail updateOrderDetail(long id, OrderDetailDTO newOrderDetailDTO) throws DataNotFoundException {
        // 1. Tim xem order detail co ton tai khong //
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("OrderDetail not found !"));
        // 2. kiem tra order id co thuoc order nao hay khong //
        Order existOrder = orderRepository.findById(newOrderDetailDTO.getOrderId())
                .orElseThrow(()-> new DataNotFoundException("Order not found !"));
        // 3. kiem tra product con ton tai hay khong //
        Product existingProduct = productRepository.findById(newOrderDetailDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        existingOrderDetail.setOrder(existOrder);
        existingOrderDetail.setProduct(existingProduct);
        existingOrderDetail.setPrice(existingProduct.getPrice());
        existingOrderDetail.setNumberOfProducts(newOrderDetailDTO.getNumberOfProducts());
        existingOrderDetail.setTotalMoney(existingProduct.getPrice()*newOrderDetailDTO.getNumberOfProducts());
        existingOrderDetail.setColor(newOrderDetailDTO.getColor());
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) {
        // 1.kiểm tra tồn tại order theo id //
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .price(product.getPrice())
                .totalMoney(product.getPrice()*orderDetailDTO.getNumberOfProducts())
                .color(orderDetailDTO.getColor())
                .build();
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteOrderDetail(long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> getAllOrderDetails(long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

}
