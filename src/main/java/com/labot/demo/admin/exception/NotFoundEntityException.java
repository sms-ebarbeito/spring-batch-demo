package com.labot.demo.admin.exception;

import lombok.Getter;

@Getter
public class NotFoundEntityException extends BaseException {

    public NotFoundEntityException(String title, String message) {
        super(title, message);
    }
}
