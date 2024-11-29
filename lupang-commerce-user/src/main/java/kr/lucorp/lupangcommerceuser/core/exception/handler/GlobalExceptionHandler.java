package kr.lucorp.lupangcommerceuser.core.exception.handler;

import kr.lucorp.lupangcommerceuser.common.client.model.ResponseObject;
import kr.lucorp.lupangcommerceuser.common.util.ResponseUtils;
import kr.lucorp.lupangcommerceuser.core.exception.defined.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  protected <T> ResponseEntity<ResponseObject<T>> handleBusinessException(BusinessException e) {
    log.error("BusinessException 예외 테스트 : {}", e.getMessage());
    return ResponseUtils.createResponseEntityByException(e);
  }
}
