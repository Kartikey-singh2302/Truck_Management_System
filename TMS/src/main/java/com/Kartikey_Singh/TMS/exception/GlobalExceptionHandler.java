package com.Kartikey_Singh.TMS.exception;

import com.Kartikey_Singh.TMS.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {


        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
            return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
        }

        @ExceptionHandler(InvalidStatusTransitionException.class)
        public ResponseEntity<ErrorResponse> handleInvalidStatus(InvalidStatusTransitionException ex) {
            return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        @ExceptionHandler(InsufficientCapacityException.class)
        public ResponseEntity<ErrorResponse> handleCapacityError(InsufficientCapacityException ex) {
            return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        @ExceptionHandler(LoadAlreadyBookedException.class)
        public ResponseEntity<ErrorResponse> handleLoadBooked(LoadAlreadyBookedException ex) {
            return buildError(HttpStatus.CONFLICT, ex.getMessage());
        }

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
            return buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }

    private ResponseEntity<ErrorResponse> buildError(HttpStatus status, String message) {
       ErrorResponse error = new ErrorResponse(message, status.value(), Instant.now());
       return new ResponseEntity<>(error, status);
        }

 }




