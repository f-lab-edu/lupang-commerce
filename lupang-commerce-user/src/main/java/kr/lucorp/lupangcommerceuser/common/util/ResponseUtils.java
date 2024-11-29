package kr.lucorp.lupangcommerceuser.common.util;

import java.nio.charset.StandardCharsets;
import kr.lucorp.lupangcommerceuser.common.client.model.ResponseObject;
import kr.lucorp.lupangcommerceuser.core.exception.defined.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ResponseUtils {

  private ResponseUtils() {
    throw new IllegalStateException("ResponseUtil 인스턴스 생성 제한");
  }

  public static HttpHeaders getDefaultHttpHeaders() {
    HttpHeaders httpHeaders = new HttpHeaders();
    MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8);
    httpHeaders.setContentType(mediaType);
    return httpHeaders;
  }

  public static <T> ResponseEntity<ResponseObject<T>> createResponseEntityByException(BusinessException ex) {
    return new ResponseEntity<>(ex.getBody(), getDefaultHttpHeaders(), ex.getHttpStatus());
  }

  public static <T> ResponseEntity<ResponseObject<T>> createResponseEntity(T data, HttpStatus status) {
    ResponseObject<T> responseObject = new ResponseObject<>();
    responseObject.setData(data);
    return new ResponseEntity<>(responseObject, status);
  }
}

