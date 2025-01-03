package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.responses.OrderResponse;
import com.project.shopapp.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody OrderDTO OrderDTO,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            OrderResponse orderResponse = orderService.createOrder(OrderDTO);
            return ResponseEntity.ok(orderResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrderByUserId(@PathVariable("user_id") Long userId){
        try {
            List<OrderResponse> list = orderService.getAllOrders(userId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long orderId){
        try {
            OrderResponse existingOrder = orderService.getOrder(orderId);
            return ResponseEntity.ok(existingOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @PathVariable("id") Long id,
            @RequestBody OrderDTO orderDTO) {
        try {
            OrderResponse orderResponse = orderService.updateOrder(id,orderDTO);
            return ResponseEntity.ok(orderResponse);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        // Xóa mềm đơn hàng => active = 0
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok().body("Order deleted");
        } catch (DataNotFoundException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
