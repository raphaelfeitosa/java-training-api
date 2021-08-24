package br.com.training.v1.controller.exceptionHandler;

import br.com.training.v1.service.exceptions.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ValidationError> userExceptionHandler(UserException userException, HttpServletRequest request) {
        HttpStatus status = userException.getHttpStatus();
        Integer code = userException.getHttpStatus().value();
        ValidationError err = new ValidationError(Instant.now(), code, status, userException.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Integer code = status.value();
        ValidationError err = new ValidationError(Instant.now(), code, status, "Error validation", request.getRequestURI());
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            err.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }

}
