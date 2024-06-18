/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.service.impl;

import com.bookstore.config.BookConfig;
import com.bookstore.entity.BookEntity;
import com.bookstore.exceptions.BookNotFoundException;
import com.bookstore.exceptions.ResourceNotFoundException;
import com.bookstore.repository.BookRepository;
import com.bookstore.request.dto.BookDTO;
import com.bookstore.response.dto.BookResponseDTO;
import com.bookstore.service.BookService;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kobe
 */
@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookConfig bookConfig;

    @Override
    public BookResponseDTO updateBook(Long id, BookDTO bookDetails) {
        BookEntity updateBook = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        updateBook.setTitle(bookDetails.getTitle());
        updateBook.setAuthor(bookDetails.getAuthor());
        updateBook.setIsbn(bookDetails.getIsbn());
        updateBook.setPrice(bookDetails.getPrice());
        bookRepository.save(updateBook);

        BookResponseDTO responseDTO = BookResponseDTO.builder()
                .statuscode(bookConfig.successcode())
                .statusmessage(bookConfig.successstatusmessage())
                .response(updateBook)
                .build();

        return responseDTO;
    }

    @Override
    public BookResponseDTO deleteBook(Long id) {

        Optional<BookEntity> bookExist = bookRepository.findById(id);

        if (bookExist.isEmpty()) {
            throw new BookNotFoundException("Book not found");
        } else {
            bookRepository.deleteById(id);

            BookResponseDTO responseDTO = BookResponseDTO.builder()
                    .statuscode(bookConfig.successcode())
                    .statusmessage("Book with ID: " + id + " deleted successfully.")
                    .response(null)
                    .build();

            return responseDTO;
        }
    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        Optional<?> optionEntity = bookRepository.findById(id);

        if (optionEntity.isEmpty()) {
            throw new ResourceNotFoundException("Book not found");
        }

        BookResponseDTO responseDTO = BookResponseDTO.builder()
                .statuscode(bookConfig.successcode())
                .statusmessage(bookConfig.successstatusmessage())
                .response(optionEntity)
                .build();
         return responseDTO;
    }

    @Override
    public BookResponseDTO getAllBooks() {
        List<BookEntity> optionEntity = bookRepository.findAll();

        if (optionEntity.isEmpty()) {
            throw new ResourceNotFoundException(bookConfig.norecordfound());
        }

        BookResponseDTO responseDTO = BookResponseDTO.builder()
                .statuscode(bookConfig.successcode())
                .statusmessage(bookConfig.successstatusmessage())
                .response(optionEntity)
                .build();
         return responseDTO;

    }

    @Override
    public BookResponseDTO addBook(BookDTO bookDTO) {
        log.info("sanitized payload received before saving {}", bookDTO.toString());

        HashMap<String, Object> responseMap = new HashMap<>();

        List<?> bookTitle = bookRepository.findByTitle(bookDTO.getTitle());

        if (!bookTitle.isEmpty()) {

            responseMap.put("Message", bookDTO.getTitle() + " Already Exist");

            BookResponseDTO responseDTO = BookResponseDTO.builder()
                    .statuscode(bookConfig.successcode())
                    .statusmessage(bookConfig.successstatusmessage())
                    .response(responseMap)
                    .build();

             return responseDTO;
        }

        BookEntity bookEntity = BookEntity.builder()
                .author(bookDTO.getAuthor())
                .isbn(bookDTO.getIsbn())
                .title(bookDTO.getTitle())
                .price(bookDTO.getPrice())
                .build();

        bookRepository.save(bookEntity);

        BookResponseDTO responseDTO = BookResponseDTO.builder()
                .statuscode(bookConfig.successcode())
                .statusmessage(bookConfig.successstatusmessage())
                .response(bookEntity)
                .build();
         return responseDTO;
    }

    @Override
    public BookResponseDTO getBookByTitle(String title) {
        HashMap<String, Object> responseMap = new HashMap<>();

        List<?> bookTitle = bookRepository.findByTitle(title);

        if (bookTitle.isEmpty()) {
            responseMap.put("Title ", title + " doesnt Exist");

            BookResponseDTO responseDTO = BookResponseDTO.builder()
                    .statuscode(bookConfig.successcode())
                    .statusmessage(bookConfig.successstatusmessage())
                    .response(responseMap)
                    .build();

            return responseDTO;
        } else {

            BookResponseDTO responseDTO = BookResponseDTO.builder()
                    .statuscode(bookConfig.successcode())
                    .statusmessage(bookConfig.successstatusmessage())
                    .response(bookTitle)
                    .build();

             return responseDTO;
        }
    }
}
