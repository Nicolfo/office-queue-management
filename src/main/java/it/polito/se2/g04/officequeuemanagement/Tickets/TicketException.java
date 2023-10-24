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
}
class CreateTicketWithNoPathVariable extends RuntimeException {
    public CreateTicketWithNoPathVariable(String message) {
        super(message);
    }
}
