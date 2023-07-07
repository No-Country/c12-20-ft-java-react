package c1220ftjavareact.gym.security.controller;

import c1220ftjavareact.gym.domain.exception.ErrorDTO;
import c1220ftjavareact.gym.domain.exception.*;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manaja las respuestas de las excepciones arrojadas si se encuentra el handler de la Excepcion
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handler de Exception
     */
    @ExceptionHandler(Exception.class)
    public HttpEntity<ErrorDTO> handleResourceNotFoundException(Exception ex) {
        var errorDetails = ErrorDTO.builder()
                .message(ex.getMessage())
                .resolve("Excepcion no manejada")
                .target(ex.getClass().getTypeName())
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler de UserSaveException
     */
    @ExceptionHandler(UserSaveException.class)
    public HttpEntity<ErrorDTO> handleUserSaveException(UserSaveException ex) {
        var errorDetails = ErrorDTO.builder()
                .message(ex.getMessage())
                .resolve(ex.getResolve())
                .target(ex.getLocalizedMessage())
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler de UpdatePasswordException
     */
    @ExceptionHandler(UpdatePasswordException.class)
    public HttpEntity<ErrorDTO> handleUpdatePasswordException(UpdatePasswordException ex) {
        var errorDetails = ErrorDTO.builder()
                .message(ex.getMessage())
                .resolve(ex.getResolve())
                .target(ex.getTarget())
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler de UpdatePasswordException
     */
    @ExceptionHandler(CredentialException.class)
    public HttpEntity<ErrorDTO> handleCredentialException(CredentialException ex) {
        var errorDetails = ErrorDTO.builder()
                .message(ex.getMessage())
                .resolve(ex.getResolve())
                .target(ex.getTarget())
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     *  Handler de CustomIllegalArgsException, hereda de IllegalArgumentsException
     */
    @ExceptionHandler(CustomIllegalArgsException.class)
    public HttpEntity<ErrorDTO> handleCustomIllegalArgsException(CustomIllegalArgsException ex) {
        var errorDetails = ErrorDTO.builder()
                .message(ex.getMessage())
                .resolve(ex.getResolve())
                .target(ex.getTarget())
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler de ValueInstantiationException
     */
    @ExceptionHandler(ValueInstantiationException.class)
    public HttpEntity<ErrorDTO> handleValueInstantiationException(ValueInstantiationException ex) {
        var errorDetails = ErrorDTO.builder()
                .message(ex.getMessage())
                .resolve(ex.getOriginalMessage())
                .target(ex.getType().getTypeName())
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler de IOException
     */
    @ExceptionHandler(IOException.class)
    public HttpEntity<ErrorDTO> handleIOException(IOException ex) {
        var errorDetails = ErrorDTO.builder()
                .message(ex.getMessage())
                .resolve(ex.getLocalizedMessage())
                .target(ex.getClass().getTypeName())
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler de ResourceAlreadyExistsException
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public HttpEntity<ErrorDTO> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest webRequest) {
        var errorDetails = ErrorDTO.builder()
                .message(ex.getMessage())
                .resolve(ex.getResolve())
                .target(ex.getTarget())
                .status(String.valueOf(HttpStatus.CONFLICT.value()))
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    /**
     * Handler de ResourceNotFoundException
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public HttpEntity<ErrorDTO> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        var errorDetails = ErrorDTO.builder()
                .message(ex.getMessage())
                .resolve(ex.getResolve())
                .target(ex.getTarget())
                .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
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
