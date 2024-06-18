/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bookstore.service;

import com.bookstore.dto.OrderDTO;
import com.bookstore.dto.response.OrderResponseDTO;

/**
 *
 * @author kobe
 */
public interface OrderService {

    OrderResponseDTO addOrder(OrderDTO orderrDTO);

    OrderResponseDTO updateOrder(Long id, OrderDTO orderrDTO);

    OrderResponseDTO getOrderById(Long id);

    OrderResponseDTO getOrdersByCustomerId(Long customerId);

    OrderResponseDTO getBookById(Long bookId);
}
