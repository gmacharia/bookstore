/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bookstore.service;

import com.bookstore.dto.CustomerDTO;
import com.bookstore.dto.response.CustomerResponseDTO;


/**
 *
 * @author kobe
 */
public interface CustomerService {
   CustomerResponseDTO addCustomer(CustomerDTO customerDTO);
   CustomerResponseDTO updateCustomer(Long id, CustomerDTO customerDetails);
   CustomerResponseDTO getCustomerById(Long id);
}
