package com.bookstore.response.dto;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTResponse implements Serializable {
    private String statusCode;
    private String token;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
