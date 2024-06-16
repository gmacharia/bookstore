/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.service;

import com.bookstore.entity.BookEntity;
import com.bookstore.request.dto.BookDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author kobe
 */
public interface BookService {
   ResponseEntity<?> addBook(BookDTO bookDTO);
   ResponseEntity<?> updateBook(Long id, BookDTO bookDetails);
   ResponseEntity<?> deleteBook(Long id);
   BookEntity getBookById(Long id);
   List<?> getAllBooks();
}
