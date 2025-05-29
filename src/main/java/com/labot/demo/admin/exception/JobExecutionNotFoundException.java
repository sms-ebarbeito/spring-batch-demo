package com.labot.demo.admin.exception;

import lombok.Getter;

@Getter
public class JobExecutionNotFoundException extends BaseException {

    public JobExecutionNotFoundException(String title, String message) {
        super(title, message);
    }
}
