package com.labot.demo.admin.exception;

import com.labot.demo.admin.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BatchGlobalExceptionMapping {

    @ExceptionHandler(JobExecutionNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleJobExecutionNotFoundException(JobExecutionNotFoundException ex) {
        return new ResponseEntity<>(new ErrorDTO(ex.getTitle(), ex.getLocalizedMessage(), HttpStatus.NOT_FOUND.value()),
                HttpStatus.NOT_FOUND);
    }
}
