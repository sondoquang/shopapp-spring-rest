package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/order_details")
public class OrderDetailController {
    // thêm mới một order detail //
    @PostMapping("")
    public ResponseEntity<?> createOrderDetails(@Valid @RequestBody OrderDetailDTO orderDetailDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok("Create order success");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable Long id) {
        return ResponseEntity.ok("Get order detail with id: " + id);
    }

    @GetMapping("/order/{order_id}")
    public ResponseEntity<?> getOrderDetailByOrderId(@PathVariable("order_id") Long orderId) {
        return ResponseEntity.ok("Get order detail by order_id: " + orderId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@PathVariable("id") Long id, @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        return ResponseEntity.ok("Update order detail with id: " + id +" New data: " + orderDetailDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable Long id) {
        return ResponseEntity.ok("Delete order detail with id: " + id);
    }
}
