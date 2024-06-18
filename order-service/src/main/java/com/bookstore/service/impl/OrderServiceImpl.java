/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.service.impl;

import com.bookstore.dto.BookDTO;
import com.bookstore.dto.OrderDTO;
import com.bookstore.entity.OrderEntity;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.http.HttpRequestService;
import com.bookstore.repository.OrderRepository;
import com.bookstore.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kobe
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final HttpRequestService httpRequestService;

    @Override
    public ResponseEntity<?> addOrder(OrderDTO orderrDTO) {

        OrderEntity orderEntity = OrderEntity.builder()
                .bookId(orderrDTO.getBookId())
                .customerId(orderrDTO.getCustomerId())
                .mobileNumber(orderrDTO.getMobileNumber())
                .build();

        orderRepository.save(orderEntity);
        return new ResponseEntity<>(orderEntity, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateOrder(Long id, OrderDTO orderrDTO) {
        OrderEntity updateOrder = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        updateOrder.setBookId(orderrDTO.getBookId());
        updateOrder.setCustomerId(orderrDTO.getCustomerId());
        updateOrder.setMobileNumber(orderrDTO.getMobileNumber());

        orderRepository.save(updateOrder);

        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }

    @Override
    public OrderEntity getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }
    
    @Override
    public List<?> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public BookDTO getBookById(Long bookId) {
        //lets call book service
        BookDTO bookDTO =  httpRequestService.getBookById(bookId);
        
        log.info("Book API response {}", bookDTO);
        
        return bookDTO;
    }

}
