package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.responses.OrderDetailResponse;
import com.project.shopapp.services.OrderDetailService;
import com.project.shopapp.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/order_details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    // thêm mới một order detail //
    @PostMapping("")
    public ResponseEntity<?> createOrderDetails(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            OrderDetail orderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(orderDetail));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable Long id) {
        try {
            OrderDetail existingOrderDetail =orderDetailService.getOrderDetailById(id);
            return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(existingOrderDetail));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/order/{order_id}")
    public ResponseEntity<?> getOrderDetailByOrderId(@PathVariable("order_id") Long orderId) {
        List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails(orderId);
        List<OrderDetailResponse> orderDetailResponses = orderDetails
                .stream()
                .map(OrderDetailResponse::fromOrderDetail)
                .toList();
        return ResponseEntity.ok(orderDetailResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@PathVariable("id") Long id, @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
            return ResponseEntity.ok(orderDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable Long id) {
        try {
            orderDetailService.deleteOrderDetail(id);
            return ResponseEntity.ok("Delete order detail with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
