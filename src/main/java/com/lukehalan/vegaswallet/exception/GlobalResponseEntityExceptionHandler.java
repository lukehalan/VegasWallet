package com.lukehalan.vegaswallet.exception;

import com.networknt.schema.ValidationMessage;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ValidationException.class)
  public final ResponseEntity<Object> handleValidationException(
      ValidationException ex, WebRequest request) {
    log.error(" validation Error -  {}", ex.getMessage(), ex);
    return new ResponseEntity<>(
        new CustomExceptionResponse(ex.getMessage(), new Date()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DataNotFundException.class)
  public final ResponseEntity<Object> handleDateValidationException(
      DataNotFundException ex, WebRequest request) {
    log.error("DataNotFundException  Error -  {}", ex.getMessage(), ex);
    return new ResponseEntity<>(
        new CustomExceptionResponse(ex.getMessage(), new Date()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(TransactionException.class)
  public final ResponseEntity<Object> handleDateValidationException(
          TransactionException ex, WebRequest request) {
    log.error("TransactionException  Error -  {}", ex.getMessage(), ex);
    return new ResponseEntity<>(
            new CustomExceptionResponse(ex.getMessage(), new Date()), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(JsonValidationException.class)
  public ResponseEntity<Map<String, Object>> onJsonValidationException(JsonValidationException ex) {
    List<String> messages = ex.getValidationMessages().stream()
            .map(ValidationMessage::getMessage)
            .collect(Collectors.toList());
    return ResponseEntity.unprocessableEntity().body(Map.of(
            "message", "Json validation failed",
            "details", messages
    ));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    log.error("MethodArgumentNotValidException Message {}", ex.getMessage(), ex);
    return new ResponseEntity<>(
        new CustomExceptionResponse(ex.getLocalizedMessage(), new Date()), HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    log.error("MissingServletRequestParameterException  Error -  {}", ex.getMessage(), ex);
    String name = ex.getParameterName();
    return new ResponseEntity<>(
        new CustomExceptionResponse(name + " parameter is missing", new Date()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NullPointerException.class)
  public final ResponseEntity<Object> handleNullPointerException(
      NullPointerException ex, WebRequest request) {
    log.error(" NullPointerException -  {}", ex.getMessage(), ex);
    return new ResponseEntity<>(
        new CustomExceptionResponse("Something went wrong", new Date()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error("HttpMessageNotReadableException  Error -  {}", ex.getMessage(), ex);
    return new ResponseEntity<>(
            new CustomExceptionResponse("Json Format is not valid", new Date()), HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
