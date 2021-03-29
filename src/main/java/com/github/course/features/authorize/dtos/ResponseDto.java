package com.github.course.features.authorize.dtos;

public class ResponseDto {
    private String token;

    public ResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
