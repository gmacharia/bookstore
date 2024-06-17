/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.service.impl;

import com.bookstore.entity.BookEntity;
import com.bookstore.exceptions.BookNotFoundException;
import com.bookstore.exceptions.ResourceNotFoundException;
import com.bookstore.repository.BookRepository;
import com.bookstore.request.dto.BookDTO;
import com.bookstore.service.BookService;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
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
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public ResponseEntity<?> updateBook(Long id, BookDTO bookDetails) {
        BookEntity updateBook = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        updateBook.setTitle(bookDetails.getTitle());
        updateBook.setAuthor(bookDetails.getAuthor());
        updateBook.setIsbn(bookDetails.getIsbn());
        updateBook.setPrice(bookDetails.getPrice());
        bookRepository.save(updateBook);

        return new ResponseEntity<>(updateBook, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<?> deleteBook(Long id) {

        HashMap<String, Object> responseMap = new HashMap<>();
        Optional<BookEntity> bookExist = bookRepository.findById(id);

        if (bookExist.isEmpty()) {
            throw new BookNotFoundException("Book not found");
        } else {
            bookRepository.deleteById(id);

            responseMap.put("Book with ID: ", id + " deleted successfully.");
            return new ResponseEntity<>(responseMap, HttpStatus.ACCEPTED);
        }
    }

    @Override
    public BookEntity getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    @Override
    public List<?> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public ResponseEntity<?> addBook(BookDTO bookDTO) {
        log.info("sanitized payload received before saving {}", bookDTO.toString());

        HashMap<String, Object> responseMap = new HashMap<>();

        List<?> bookTitle = bookRepository.findByTitle(bookDTO.getTitle());

        if (!bookTitle.isEmpty()) {
            responseMap.put("Message", bookDTO.getTitle() + " Already Exist");
            return new ResponseEntity<>(responseMap, HttpStatus.PRECONDITION_FAILED);
        }

        BookEntity bookEntity = BookEntity.builder()
                .author(bookDTO.getAuthor())
                .isbn(bookDTO.getIsbn())
                .title(bookDTO.getTitle())
                .price(bookDTO.getPrice())
                .build();

        bookRepository.save(bookEntity);
        return new ResponseEntity<>(bookEntity, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<?> getBookByTitle(String title) {
        HashMap<String, Object> responseMap = new HashMap<>();

        List<?> bookTitle = bookRepository.findByTitle(title);

        if (bookTitle.isEmpty()) {
            responseMap.put("Title ", title + " doesnt Exist");
            return new ResponseEntity<>(responseMap, HttpStatus.ACCEPTED);
        }else{
         return new ResponseEntity<>(bookTitle, HttpStatus.ACCEPTED);
        }
    }
}
