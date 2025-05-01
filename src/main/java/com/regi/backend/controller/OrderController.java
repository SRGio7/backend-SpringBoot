package com.regi.backend.controller;

import com.regi.backend.dto.OrderDTO;
import com.regi.backend.dto.OrderDetailDTO;
import com.regi.backend.entity.Order;
import com.regi.backend.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            Order createdOrder = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(createdOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDetailDTO> orderDTOs = orders.stream()
                .map(OrderDetailDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getOrderById(@PathVariable Long id) {
        Optional<Order> orderOpt = orderService.getOrderById(id);
        return orderOpt.map(order -> ResponseEntity.ok(OrderDetailDTO.fromEntity(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<OrderDetailDTO> getOrderByNumber(@PathVariable String orderNumber) {
        Optional<Order> orderOpt = orderService.getOrderByNumber(orderNumber);
        return orderOpt.map(order -> ResponseEntity.ok(OrderDetailDTO.fromEntity(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDetailDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam Order.OrderStatus status) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok(OrderDetailDTO.fromEntity(updatedOrder));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}