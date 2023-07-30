package c1220ftjavareact.gym.security.controller;

import c1220ftjavareact.gym.activity.exception.ActivityException;
import c1220ftjavareact.gym.payment.exception.PaymentException;
import c1220ftjavareact.gym.room.exception.RoomException;
import c1220ftjavareact.gym.security.exception.ApiException;
import c1220ftjavareact.gym.security.exception.ExceptionDTO;
import c1220ftjavareact.gym.security.exception.ResourceAlreadyExistsException;
import c1220ftjavareact.gym.security.exception.ResourceNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Manaja las respuestas de las excepciones arrojadas si se encuentra el handler de la Excepcion
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ActivityException.class})
    protected ResponseEntity<Object> handleConflict(
            ActivityException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), ex.getHttpStatus(), request);
    }

    @ExceptionHandler(value = {RoomException.class})
    protected ResponseEntity<Object> handleConflict(
            RoomException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), ex.getHttpStatus(), request);
    }

    @ExceptionHandler(value = {PaymentException.class})
    protected ResponseEntity<Object> handleConflict(
            PaymentException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), ex.getHttpStatus(), request);
    }


    /**
     * Handler de Exception
     */

/*    @ExceptionHandler(Exception.class)
    public HttpEntity<ExceptionDTO> handleResourceNotFoundException(Exception ex) {
        var errorDetails = ExceptionDTO.builder()
                .title("Excepcion no manejada")
                .detail(ex.getMessage())
                .type(ex.getClass().getTypeName())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }*/


    @ExceptionHandler(ApiException.class)
    public HttpEntity<ExceptionDTO> handleResourceNotFoundException(ApiException ex) {
        var errorDetails = ExceptionDTO.builder()
                .title(ex.getTitle())
                .detail(ex.getMessage())
                .type(ex.getClass().getTypeName())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler de ResourceAlreadyExistsException
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public HttpEntity<ExceptionDTO> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        var errorDetails = ExceptionDTO.builder()
                .title(ex.getTitle())
                .detail(ex.getMessage())
                .type(ex.getClass().getTypeName())
                .status(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    /**
     * Handler de ResourceNotFoundException
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public HttpEntity<ExceptionDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        var errorDetails = ExceptionDTO.builder()
                .title(ex.getTitle())
                .detail(ex.getMessage())
                .type(ex.getClass().getTypeName())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Sobre escribe el metodo que maneja la excepcion MethodArgumentNotValidException
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String name = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(name, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
