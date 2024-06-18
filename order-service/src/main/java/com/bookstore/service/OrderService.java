/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bookstore.service;

import com.bookstore.dto.BookDTO;
import com.bookstore.dto.OrderDTO;
import com.bookstore.entity.OrderEntity;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author kobe
 */
public interface OrderService {

    ResponseEntity<?> addOrder(OrderDTO orderrDTO);

    ResponseEntity<?> updateOrder(Long id, OrderDTO orderrDTO);

    OrderEntity getOrderById(Long id);

    List<?> getOrdersByCustomerId(Long customerId);

    BookDTO getBookById(Long bookId);
}
