package com.labot.demo.admin.dto;

public class ErrorDTO extends ApiDTO {

    public ErrorDTO() { super(); }

    public ErrorDTO(String title, String message, int statusCode) {
        super(title, message, statusCode, false);
    }
}
