/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.controller;

import com.bookstore.dto.OrderDTO;
import com.bookstore.dto.response.OrderResponseDTO;
import com.bookstore.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kobe
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponseDTO addCustomer(@RequestBody OrderDTO orderDTO) {
        return orderService.addOrder(orderDTO);
    }

    @PutMapping("/{id}")
    public OrderResponseDTO updateCustomer(@PathVariable Long id, @RequestBody OrderDTO orderDetails) {
        return orderService.updateOrder(id, orderDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @GetMapping("/customers/{customerId}/orders")
    public OrderResponseDTO getOrdersByCustomerId(@PathVariable Long customerId) {
        return orderService.getOrdersByCustomerId(customerId);
    }

    @GetMapping("/books/{bookId}/orders")
    public OrderResponseDTO getBookById(@PathVariable Long bookId) {
        return orderService.getBookById(bookId);
    }
}
