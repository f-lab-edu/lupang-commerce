package kr.lucorp.lupangcommerceuser.core.exception.handler;


import kr.lucorp.lupangcommerceuser.common.client.model.ResponseObject;
import kr.lucorp.lupangcommerceuser.common.util.ResponseUtils;
import kr.lucorp.lupangcommerceuser.core.exception.defined.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(1)
public class BusinessExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  protected <T> ResponseEntity<ResponseObject<T>> handleBusinessException(BusinessException e) {
    log.error("BusinessException : {}", e.getMessage());
    return ResponseUtils.createResponseEntityByException(e);
  }
}
