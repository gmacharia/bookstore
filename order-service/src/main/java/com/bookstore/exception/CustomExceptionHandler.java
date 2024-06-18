package com.bookstore.exception;

import com.bookstore.config.OrderConfig;
import com.bookstore.dto.response.OrderResponseDTO;
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
    OrderConfig orderConfig;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        OrderResponseDTO responseDTO = OrderResponseDTO.builder()
                .statuscode(orderConfig.failurecode())
                .statusmessage(ex.getMessage())
                .response(null)
                .build();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
