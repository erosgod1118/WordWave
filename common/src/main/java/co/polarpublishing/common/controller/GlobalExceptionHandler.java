package co.polarpublishing.common.controller;

import co.polarpublishing.common.constant.ExceptionCode;
import co.polarpublishing.common.dto.ErrorResponse;
import co.polarpublishing.common.exception.CustomRuntimeException;
import co.polarpublishing.common.exception.UnauthorizedOperationException;
import co.polarpublishing.common.exception.UserNotFoundException;
import co.polarpublishing.common.util.DateAndTimeUtil;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.Date;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handle(MethodArgumentNotValidException exception) {
    return ErrorResponse.builder()
        .timestamp(new Date().getTime())
        .status(HttpStatus.BAD_REQUEST.value())
        .code(ExceptionCode.BAD_REQUEST.getCode())
        .message(exception.getBindingResult().getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .findFirst().orElse(""))
        .build();
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handle(InvalidFormatException exception) {
    return ErrorResponse.builder()
        .timestamp(new Date().getTime())
        .status(HttpStatus.BAD_REQUEST.value())
        .code(ExceptionCode.BAD_REQUEST.getCode())
        .message(exception.getOriginalMessage())
        .build();
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handle(ConstraintViolationException exception) {
    return ErrorResponse.builder()
        .timestamp(DateAndTimeUtil.getCurrentEpochTime())
        .status(HttpStatus.BAD_REQUEST.value())
        .message(exception.getConstraintViolations()
            .stream()
            .map(ConstraintViolation::getMessage)
            .findFirst().orElse(""))
        .code(ExceptionCode.BAD_REQUEST.getCode())
        .build();
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handle(JsonParseException exception) {
    return ErrorResponse.builder()
        .timestamp(DateAndTimeUtil.getCurrentEpochTime())
        .status(HttpStatus.BAD_REQUEST.value())
        .message(exception.getMessage())
        .code(ExceptionCode.BAD_REQUEST.getCode())
        .build();
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handle(HttpMessageNotReadableException exception) {
    System.out.println(exception.getClass());
    return ErrorResponse.builder()
        .timestamp(DateAndTimeUtil.getCurrentEpochTime())
        .status(HttpStatus.BAD_REQUEST.value())
        .message(exception.getMessage())
        .code(ExceptionCode.BAD_REQUEST.getCode())
        .build();
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handle(UserNotFoundException exception) {
    return ErrorResponse.builder()
        .timestamp(DateAndTimeUtil.getCurrentEpochTime())
        .status(HttpStatus.BAD_REQUEST.value())
        .message(exception.getMessage())
        .code(exception.getCode())
        .build();
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handle(CustomRuntimeException exception) {
    return ErrorResponse.builder()
        .timestamp(System.currentTimeMillis())
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message(exception.getMessage())
        .code(exception.getCode())
        .build();
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorResponse handle(UnauthorizedOperationException exception) {
    return ErrorResponse.builder().timestamp(System.currentTimeMillis())
        .status(HttpStatus.UNAUTHORIZED.value()).message(exception.getMessage()).displayMessage(exception.getMessage())
        .code(exception.getCode()).build();
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handle(UnsupportedOperationException exception) {
    return ErrorResponse.builder().displayMessage(exception.getMessage())
        .status(HttpStatus.BAD_REQUEST.value()).timestamp(System.currentTimeMillis())
        .message(exception.getMessage()).build();
  }

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handle(Exception exception) {
    System.out.println(exception);
    return ErrorResponse.builder().timestamp(System.currentTimeMillis())
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .displayMessage("Oops! Something went wrong. Please try again later or contact support.")
        .build();
  }

}
