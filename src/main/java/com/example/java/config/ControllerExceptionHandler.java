package com.example.java.config;

import com.example.java.exception.ApiError;
import com.example.java.exception.ApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<ApiError> handleAllApiException(Exception e) {

        String error = "Api Error";
        String message = e.getLocalizedMessage();
        Integer statusCode = 500;

        if (e instanceof ApiException) {
            error = ((ApiException) e).getCode();
            message = ((ApiException) e).getDescription();
            statusCode = ((ApiException) e).getStatusCode();
        }

        if (e instanceof EntityNotFoundException) {
            statusCode = 404;
        }

        ApiError apiError = new ApiError(error, message, statusCode);
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }


    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ApiError> handleUnknownException(Exception e) {

        ApiError apiError = new ApiError("internal_error", "Please contact the administrator.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ApiError error = new ApiError("Validation Failed", details.stream().collect(Collectors.joining("\n")), 422);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
