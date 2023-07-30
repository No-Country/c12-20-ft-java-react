package c1220ftjavareact.gym.active.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

    @RestControllerAdvice
    public class ActiveUserExceptionHandler extends ResponseEntityExceptionHandler {

        @org.springframework.web.bind.annotation.ExceptionHandler(value = {ActiveUserException.class})
        protected ResponseEntity<Object> handleConflict(
                ActiveUserException ex, WebRequest request) {
            String bodyOfResponse = ex.getMessage();
            return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), ex.getHttpStatus(), request);
        }
    }

