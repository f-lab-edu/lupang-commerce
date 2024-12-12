package kr.lucorp.lupangcommerceuser.core.exception.defined;

import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCode;
import kr.lucorp.lupangcommerceuser.common.client.model.ResponseError;
import kr.lucorp.lupangcommerceuser.common.client.model.ResponseObject;
import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException{

  private final transient ErrorCode errorCode;

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.toString());
    this.errorCode = errorCode;
  }

  // 예외 발생시 error 내용을 ResponseError에 추가
  public <T> ResponseObject<T> getBody() {
    ResponseObject<T> response = new ResponseObject<>();

    response.addError(ResponseError.builder()
        .status(Integer.toString(errorCode.getStatus().value()))
        .code(errorCode.getCode())
        .build()
    );

    return response;
  }

  public HttpStatus getHttpStatus() {
    return this.errorCode.getStatus();
  }
}
