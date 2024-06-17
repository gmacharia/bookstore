/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.service.impl;

import com.bookstore.dto.CustomerDTO;
import com.bookstore.entity.CustomerEntity;
import com.bookstore.exceptions.ResourceNotFoundException;
import com.bookstore.repository.CustomerRepository;
import com.bookstore.service.CustomerService;
import java.util.HashMap;
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
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public ResponseEntity<?> updateCustomer(Long id, CustomerDTO customerDetails) {
        CustomerEntity updateCustomer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        updateCustomer.setCustomerOthertNames(customerDetails.getCustomerOthertNames());
        updateCustomer.setCustomerSurName(customerDetails.getCustomerSurName());
        updateCustomer.setEmailAddress(customerDetails.getEmailAddress());
        updateCustomer.setMobileNumber(customerDetails.getMobileNumber());

        customerRepository.save(updateCustomer);

        return new ResponseEntity<>(updateCustomer, HttpStatus.ACCEPTED);
    }

    @Override
    public CustomerEntity getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    @Override
    public ResponseEntity<?> addCustomer(CustomerDTO customerDTO) {

        HashMap<String, Object> responseMap = new HashMap<>();

        List<?> customerNumber = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());

        if (!customerNumber.isEmpty()) {
            responseMap.put("Message", customerDTO.getMobileNumber() + " Already Exist");
            return new ResponseEntity<>(responseMap, HttpStatus.PRECONDITION_FAILED);
        }

        CustomerEntity bookEntity = CustomerEntity.builder()
                .customerOthertNames(customerDTO.getCustomerOthertNames())
                .customerSurName(customerDTO.getCustomerSurName())
                .emailAddress(customerDTO.getEmailAddress())
                .mobileNumber(customerDTO.getMobileNumber())
                .build();

        customerRepository.save(bookEntity);
        return new ResponseEntity<>(bookEntity, HttpStatus.ACCEPTED);
    }
}
