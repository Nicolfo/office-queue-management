package it.polito.se2.g04.officequeuemanagement.Tickets;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TicketException {
    @ExceptionHandler(CreateTicketWithNoPathVariable.class)
    public ProblemDetail handleServiceNotFound(CreateTicketWithNoPathVariable e){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,e.getMessage());
    }
    @ExceptionHandler(EmptyQueueException.class)
    public ProblemDetail handleEmptyQueueException(EmptyQueueException e){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NO_CONTENT,e.getMessage());
    }
    @ExceptionHandler(NoCounterForServiceException.class)
    public ProblemDetail handleNoCounterForService(NoCounterForServiceException e){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,e.getMessage());
    }
}
class CreateTicketWithNoPathVariable extends RuntimeException {
    public CreateTicketWithNoPathVariable(String message) {
        super(message);
    }
}
class EmptyQueueException extends RuntimeException {
    public EmptyQueueException(String message) {
        super(message);
    }
}

class NoCounterForServiceException extends RuntimeException {
    public NoCounterForServiceException(String message) {
        super(message);
    }
}
