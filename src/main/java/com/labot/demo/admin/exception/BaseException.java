package com.labot.demo.admin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private String title;

    public BaseException(String title, String message) {
        super(message);
        this.title = title;
    }
}
