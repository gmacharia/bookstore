/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author kobe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponseDTO {

    private String title;
    private String author;
    private String isbn;
    private double price;
}
