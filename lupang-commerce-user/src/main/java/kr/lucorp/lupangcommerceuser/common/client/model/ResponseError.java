package kr.lucorp.lupangcommerceuser.common.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_DEFAULT)
public class ResponseError {

  private String status;  // HTTP status code
  private String code;    // 세부적인 에러 구분을 위한 error 코드
  private String title;   // error 제목
  private String detail; // error 상세 메시지

  @Builder
  public ResponseError(String status, String code, String title, String detail) {
    this.status = status;
    this.code = code;
    this.title = title;
    this.detail = detail;
  }
}
