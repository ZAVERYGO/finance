package com.kozich.finance.account_service.controller.exceptionHandler;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class DefaultAdvice {

   @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class,
                       HttpMessageNotReadableException.class , ValueInstantiationException.class,
                       MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
   public ResponseEntity<ErrorResponse> validaException(Exception e){
        ErrorResponse errorResponse = new ErrorResponse("error", "Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler({IllegalArgumentException.class})
   public ResponseEntity<ErrorResponse> illegalArgException(Exception e){
        ErrorResponse errorResponse = new ErrorResponse("error", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler({Exception.class})
   public ResponseEntity<ErrorResponse> exception(Exception e){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
   }
}
