/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.service.impl;

import com.bookstore.config.CustomerConfig;
import com.bookstore.dto.CustomerDTO;
import com.bookstore.dto.response.CustomerResponseDTO;
import com.bookstore.entity.CustomerEntity;
import com.bookstore.exceptions.ResourceNotFoundException;
import com.bookstore.repository.CustomerRepository;
import com.bookstore.service.CustomerService;
import java.util.HashMap;
import java.util.List;
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
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConfig customerConfig;

    @Override
    public CustomerResponseDTO updateCustomer(Long id, CustomerDTO customerDetails) {
        CustomerEntity updateCustomer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        updateCustomer.setCustomerOthertNames(customerDetails.getCustomerOthertNames());
        updateCustomer.setCustomerSurName(customerDetails.getCustomerSurName());
        updateCustomer.setEmailAddress(customerDetails.getEmailAddress());
        updateCustomer.setMobileNumber(customerDetails.getMobileNumber());

        customerRepository.save(updateCustomer);

        CustomerResponseDTO responseDTO = CustomerResponseDTO.builder()
                .statuscode(customerConfig.successcode())
                .statusmessage(customerConfig.successstatusmessage())
                .response(updateCustomer)
                .build();

        return responseDTO;
    }

    @Override
    public CustomerResponseDTO getCustomerById(Long id) {
        Optional<?> optionEntity = customerRepository.findById(id);

        if (optionEntity.isEmpty()) {
            throw new ResourceNotFoundException("Book not found");
        }

        CustomerResponseDTO responseDTO = CustomerResponseDTO.builder()
                .statuscode(customerConfig.successcode())
                .statusmessage(customerConfig.successstatusmessage())
                .response(optionEntity)
                .build();
        return responseDTO;
    }

    @Override
    public CustomerResponseDTO addCustomer(CustomerDTO customerDTO) {

        HashMap<String, Object> responseMap = new HashMap<>();

        List<?> customerNumber = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());

        if (!customerNumber.isEmpty()) {
            responseMap.put("Message", customerDTO.getMobileNumber() + " Already Exist");

            CustomerResponseDTO responseDTO = CustomerResponseDTO.builder()
                    .statuscode(customerConfig.successcode())
                    .statusmessage(customerConfig.successstatusmessage())
                    .response(responseMap)
                    .build();

            return responseDTO;
        }

        CustomerEntity bookEntity = CustomerEntity.builder()
                .customerOthertNames(customerDTO.getCustomerOthertNames())
                .customerSurName(customerDTO.getCustomerSurName())
                .emailAddress(customerDTO.getEmailAddress())
                .mobileNumber(customerDTO.getMobileNumber())
                .build();

        customerRepository.save(bookEntity);
        
        CustomerResponseDTO responseDTO = CustomerResponseDTO.builder()
                .statuscode(customerConfig.successcode())
                .statusmessage(customerConfig.successstatusmessage())
                .response(bookEntity)
                .build();

        return responseDTO;
    }
}
