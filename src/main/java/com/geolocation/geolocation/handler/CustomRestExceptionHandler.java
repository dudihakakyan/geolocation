package com.geolocation.geolocation.handler;

import com.geolocation.geolocation.handler.exceptions.ApiParametersAreNotAlphaStringException;
import com.geolocation.geolocation.handler.exceptions.ExternalServiceException;
import com.geolocation.geolocation.handler.exceptions.InvalidSourceOrDestinationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler
{
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        String error = ex.getParameterName() + " parameter is missing";

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request)
    {
        StringBuilder returnMessage = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = "'" + ((FieldError) error).getField() + "'";
            String errorMessage = error.getDefaultMessage();
            returnMessage.append(fieldName).append(" ").append(errorMessage).append(System.lineSeparator());
        });

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), returnMessage.toString());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ApiParametersAreNotAlphaStringException.class})
    public ResponseEntity<Object> apiParametersAreNotAlphaStringException(ApiParametersAreNotAlphaStringException ex, WebRequest request)
    {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ExternalServiceException.class})
    public ResponseEntity<Object> externalServiceException(ExternalServiceException ex, WebRequest request)
    {
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidSourceOrDestinationException.class})
    public ResponseEntity<Object> externalServiceException(InvalidSourceOrDestinationException ex, WebRequest request)
    {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Invalid request", ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> generalException(Exception ex, WebRequest request)
    {
        ex.printStackTrace();
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "An internal error has occurred",
                ex.getLocalizedMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}
