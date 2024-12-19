package kr.lucorp.lupangcommerceuser.core.exception.defined;

import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCode;
import kr.lucorp.lupangcommerceuser.common.client.model.ResponseError;
import kr.lucorp.lupangcommerceuser.common.client.model.ResponseObject;
import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException{

  private final transient ErrorCode errorCode;
  private final String errorInfo;

  public BusinessException(ErrorCode errorCode, String errorInfo) {
    super(errorCode.toString());
    this.errorCode = errorCode;
    this.errorInfo = errorInfo;
  }

  public BusinessException(ErrorCode errorCode) {
    this(errorCode, null);
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

  //로그 수준 : 4XX = true
  public boolean isClientError() {
    return this.errorCode.getStatus().is4xxClientError();
  }

  public HttpStatus getHttpStatus() {
    return this.errorCode.getStatus();
  }

  @Override
  public String toString() {
    return "BusinessException{" +
        "errorCode=" + errorCode +
        ", errorInfo='" + errorInfo + '\'' +
        '}';
  }
}
