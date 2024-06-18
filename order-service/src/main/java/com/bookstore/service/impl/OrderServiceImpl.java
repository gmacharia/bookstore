/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.service.impl;

import com.bookstore.config.OrderConfig;
import com.bookstore.dto.OrderDTO;
import com.bookstore.dto.response.OrderResponseDTO;
import com.bookstore.entity.OrderEntity;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.http.HttpRequestService;
import com.bookstore.repository.OrderRepository;
import com.bookstore.service.OrderService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final OrderConfig orderConfig;
    private final HttpRequestService httpRequestService;

    @Override
    public OrderResponseDTO addOrder(OrderDTO orderrDTO) {

        OrderEntity orderEntity = OrderEntity.builder()
                .bookId(orderrDTO.getBookId())
                .customerId(orderrDTO.getCustomerId())
                .mobileNumber(orderrDTO.getMobileNumber())
                .build();

        orderRepository.save(orderEntity);

        OrderResponseDTO responseDTO = OrderResponseDTO.builder()
                .statuscode(orderConfig.successcode())
                .statusmessage(orderConfig.successstatusmessage())
                .response(orderEntity)
                .build();
        return responseDTO;
    }

    @Override
    public OrderResponseDTO updateOrder(Long id, OrderDTO orderrDTO) {
        OrderEntity updateOrder = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        updateOrder.setBookId(orderrDTO.getBookId());
        updateOrder.setCustomerId(orderrDTO.getCustomerId());
        updateOrder.setMobileNumber(orderrDTO.getMobileNumber());

        orderRepository.save(updateOrder);

        OrderResponseDTO responseDTO = OrderResponseDTO.builder()
                .statuscode(orderConfig.successcode())
                .statusmessage(orderConfig.successstatusmessage())
                .response(updateOrder)
                .build();
        return responseDTO;
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        Optional<?> optionEntity = orderRepository.findById(id);

        if (optionEntity.isEmpty()) {
            throw new ResourceNotFoundException("Order not found");
        }

        OrderResponseDTO responseDTO = OrderResponseDTO.builder()
                .statuscode(orderConfig.successcode())
                .statusmessage(orderConfig.successstatusmessage())
                .response(optionEntity)
                .build();
        return responseDTO;
    }

    @Override
    public OrderResponseDTO getOrdersByCustomerId(Long customerId) {
        Optional<?> optionEntity = orderRepository.findByCustomerId(customerId);

        if (optionEntity.isEmpty()) {
            throw new ResourceNotFoundException("Customer not found");
        }

        OrderResponseDTO responseDTO = OrderResponseDTO.builder()
                .statuscode(orderConfig.successcode())
                .statusmessage(orderConfig.successstatusmessage())
                .response(optionEntity)
                .build();
        return responseDTO;
    }

    @Override
    public OrderResponseDTO getBookById(Long bookId) {
        //lets call book service
        OrderResponseDTO responseDTO = httpRequestService.getBookById(bookId);

        log.info("Book API response {}", responseDTO);
        return responseDTO;
    }
}
