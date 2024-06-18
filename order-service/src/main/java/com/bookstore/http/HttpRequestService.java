/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.http;

import com.bookstore.dto.BookDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author kobe
 */
@Slf4j
@Service
public class HttpRequestService {

    @Autowired
    private RestTemplate restTemplate;

    public BookDTO getBookById(Long id) {
        String url = "http://localhost:8090/api/v1/books/" + id;
        return restTemplate.getForObject(url, BookDTO.class);
    }
}
