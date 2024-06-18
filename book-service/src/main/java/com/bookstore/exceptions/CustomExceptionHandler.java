package com.bookstore.exceptions;

import com.bookstore.config.BookConfig;
import com.bookstore.response.dto.BookResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    BookConfig bookConfig;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        BookResponseDTO responseDTO = BookResponseDTO.builder()
                .statuscode(bookConfig.failurecode())
                .statusmessage(ex.getMessage())
                .response(null)
                .build();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
