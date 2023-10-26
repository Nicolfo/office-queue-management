package it.polito.se2.g04.officequeuemanagement.Counters;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CounterExceptions {
    @ExceptionHandler(CounterNotFoundException.class)
    public ProblemDetail handleServiceNotFound(CounterNotFoundException e){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,e.getMessage());
    }
}
class CounterNotFoundException extends RuntimeException {
    public CounterNotFoundException(String message) {
        super(message);
    }
}
