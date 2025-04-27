package com.regi.backend.service;

import com.regi.backend.dto.OrderDTO;
import com.regi.backend.entity.Order;
import com.regi.backend.entity.OrderItem;
import com.regi.backend.entity.Product;
import com.regi.backend.repository.OrderRepository;
import com.regi.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        // Create new order
        Order order = new Order();
        order.setOrderNumber(orderDTO.getOrderNumber());
        order.setCustomerName(orderDTO.getCustomer().getName());
        order.setCustomerEmail(orderDTO.getCustomer().getEmail());
        order.setCustomerPhone(orderDTO.getCustomer().getPhone());
        order.setCustomerAddress(orderDTO.getCustomer().getAddress());
        order.setTotalAmount(orderDTO.getTotalAmount());

        // Parse order date or use current time if not provided
        if (orderDTO.getOrderDate() != null && !orderDTO.getOrderDate().isEmpty()) {
            try {
                // Assuming ISO format
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                order.setOrderDate(LocalDateTime.parse(orderDTO.getOrderDate(), formatter));
            } catch (Exception e) {
                order.setOrderDate(LocalDateTime.now());
            }
        } else {
            order.setOrderDate(LocalDateTime.now());
        }

        // Process order items
        for (OrderDTO.OrderItemDTO itemDTO : orderDTO.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductName(itemDTO.getName());
            orderItem.setProductPrice(itemDTO.getPrice());
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setSubtotal(itemDTO.getSubtotal());

            // Connect to product if exists
            if (itemDTO.getProductId() != null) {
                Optional<Product> productOpt = productRepository.findById(itemDTO.getProductId());
                productOpt.ifPresent(product -> {
                    // Update product quantity
                    int newQuantity = product.getQuantity() - itemDTO.getQuantity();
                    if (newQuantity >= 0) {
                        product.setQuantity(newQuantity);
                        productRepository.save(product);
                        orderItem.setProduct(product);
                    }
                });
            }

            // Add item to order
            order.addItem(orderItem);
        }

        // Save order to database
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> getOrderByNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    @Transactional
    public Order updateOrderStatus(Long id, Order.OrderStatus status) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        throw new RuntimeException("Order not found with id: " + id);
    }

    @Transactional
    public void deleteOrder(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();

            // Return items to inventory
            for (OrderItem item : order.getItems()) {
                if (item.getProduct() != null) {
                    Product product = item.getProduct();
                    product.setQuantity(product.getQuantity() + item.getQuantity());
                    productRepository.save(product);
                }
            }

            // Delete the order
            orderRepository.delete(order);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }
}