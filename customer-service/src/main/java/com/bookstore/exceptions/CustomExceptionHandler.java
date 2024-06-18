package com.bookstore.exceptions;

import com.bookstore.config.CustomerConfig;
import com.bookstore.dto.response.CustomerResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    CustomerConfig customerConfig;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
         CustomerResponseDTO responseDTO = CustomerResponseDTO.builder()
                .statuscode(customerConfig.failurecode())
                .statusmessage(ex.getMessage())
                .response(null)
                .build();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
