/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.service;

import com.bookstore.request.dto.BookDTO;
import com.bookstore.response.dto.BookResponseDTO;

/**
 *
 * @author kobe
 */
public interface BookService {
   BookResponseDTO addBook(BookDTO bookDTO);
   BookResponseDTO updateBook(Long id, BookDTO bookDetails);
   BookResponseDTO deleteBook(Long id);
   BookResponseDTO getBookById(Long id);
   BookResponseDTO getBookByTitle(String title);
   BookResponseDTO getAllBooks();
}
