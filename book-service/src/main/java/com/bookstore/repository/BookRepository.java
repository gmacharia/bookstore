/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bookstore.repository;

import com.bookstore.entity.BookEntity;
import com.bookstore.request.dto.BookDTO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kobe
 */
public interface BookRepository extends JpaRepository<BookEntity, Long> {
     List<BookDTO> findByBookTitle(String title);
}
