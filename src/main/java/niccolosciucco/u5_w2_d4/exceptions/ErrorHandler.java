package niccolosciucco.u5_w2_d4.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NotValidAttribute.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionsDTO handleNotValidAttribute(NotValidAttribute ex) {
        return new ExceptionsDTO(LocalDateTime.now(), ex.getMessage());
    }

    @ExceptionHandler(BodyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionsDTO handleBodyException(BodyException ex) {
        return new ExceptionsDTO(LocalDateTime.now(), ex.getMessage());
    }

    @ExceptionHandler(NotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionsDTO handleNotFound(NotFound ex) {
        return new ExceptionsDTO(LocalDateTime.now(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionsDTO handleGenericExceptions(Exception ex) {
        ex.printStackTrace();
        return new ExceptionsDTO(LocalDateTime.now(), "Errore Interno");
    }
}
