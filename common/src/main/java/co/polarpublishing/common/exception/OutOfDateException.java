package co.polarpublishing.common.exception;

import co.polarpublishing.common.constant.ExceptionCode;

public class OutOfDateException extends CustomException {

  public OutOfDateException(String message) {
    super(ExceptionCode.OUT_OF_DATE_EXCEPTION.getCode(), message);
  }

}
