package kr.lucorp.lupangcommerceuser.core.exception.defined;

import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCodes;
import kr.lucorp.lupangcommerceuser.common.client.model.ResponseError;
import kr.lucorp.lupangcommerceuser.common.client.model.ResponseObject;
import org.springframework.http.HttpStatus;

public abstract class BusinessException extends RuntimeException{

  private final transient ErrorCodes errorCodes;

  protected BusinessException(ErrorCodes errorCodes) {
    super(errorCodes.detailMessage());
    this.errorCodes = errorCodes;
  }

  // 예외 발생시 error 내용을 ResponseError에 추가
  public <T> ResponseObject<T> getBody() {
    ResponseObject<T> response = new ResponseObject<>();

    response.addError(ResponseError.builder()
        .status(Integer.toString(errorCodes.errorCode().getStatus().value()))
        .detail(errorCodes.detailMessage())
        .title(errorCodes.errorCode().getTitle())
        .code(errorCodes.errorCode().getCode())
        .build()
    );

    return response;
  }

  public HttpStatus getHttpStatus() {
    return this.errorCodes.errorCode().getStatus();
  }
}
