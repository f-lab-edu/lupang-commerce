package kr.lucorp.lupangcommerceuser.user.domain.dto;

import kr.lucorp.lupangcommerceuser.common.client.model.ErrorCode;
import kr.lucorp.lupangcommerceuser.common.util.CheckValidation;
import kr.lucorp.lupangcommerceuser.common.util.Validatable;
import kr.lucorp.lupangcommerceuser.core.exception.defined.BusinessException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CertificationSmsVerifyRequest implements Validatable {
  private String phoneNumber;
  private String smsCode;

  @Override
  public void validate() {

    if(this.getSmsCode() == null) {
      throw new BusinessException(ErrorCode.INVALID_INPUT_SMS_CODE);
    }

    if(this.getPhoneNumber() == null || !CheckValidation.isValidPhoneNumber(this.getPhoneNumber())) {
      throw new BusinessException(ErrorCode.INVALID_INPUT_PHONE_NUMBER);
    }

    this.phoneNumber = this.phoneNumber.replaceAll("\\D", "");
  }
}
