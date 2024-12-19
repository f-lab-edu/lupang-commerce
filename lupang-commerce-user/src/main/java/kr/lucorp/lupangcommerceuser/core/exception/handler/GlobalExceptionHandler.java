package kr.lucorp.lupangcommerceuser.core.exception.handler;

import kr.lucorp.lupangcommerceuser.common.client.model.ResponseObject;
import kr.lucorp.lupangcommerceuser.common.util.ResponseUtils;
import kr.lucorp.lupangcommerceuser.core.exception.defined.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(2)
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  protected <T> ResponseEntity<ResponseObject<T>> handleExceptionHandler(Exception e) {
    log.error("GlobalException : {}", e.getMessage());
    return ResponseUtils.createResponseEntityByGlobalException(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
