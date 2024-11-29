package kr.lucorp.lupangcommerceuser.common.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_DEFAULT)
public class ResponseObject<T> {

  @JsonInclude(Include.NON_NULL)
  private T data;              // 반환할 데이터

  @JsonInclude(Include.NON_NULL)
  private List<ResponseError> errors; // 에러 출력 List 데이터

  public void addError(ResponseError error) {
    if(this.errors == null) {
      errors = new ArrayList<>();
    }
    this.errors.add(error);
  }
}

