/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bookstore.service;

import com.bookstore.dto.CustomerDTO;
import com.bookstore.entity.CustomerEntity;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author kobe
 */
public interface CustomerService {
   ResponseEntity<?> addCustomer(CustomerDTO customerDTO);
   ResponseEntity<?> updateCustomer(Long id, CustomerDTO customerDetails);
   CustomerEntity getCustomerById(Long id);
}
